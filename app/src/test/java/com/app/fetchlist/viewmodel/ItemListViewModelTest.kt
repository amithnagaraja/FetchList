package com.app.fetchlist.viewmodel

import com.app.fetchlist.model.Item
import com.app.fetchlist.network.ListRepository
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain

class ItemListViewModelTest : DescribeSpec({
    val testDispatcher = StandardTestDispatcher()
    val mockRepository = mockk<ListRepository>()
    lateinit var viewModel: ItemListViewModel

    beforeSpec {
        // setting up main dispatcher for testing dispatcher
        Dispatchers.setMain(testDispatcher)
    }

    beforeTest {
        // init viewmodel while running each test
        viewModel = ItemListViewModel(mockRepository)
    }

    afterSpec {
        // resetting main dispatcher
        Dispatchers.resetMain()
    }

    describe("Testing ItemListViewModel") {

        it("should update items and loading state during fetch") {
            val flow = MutableStateFlow<List<Item>>(emptyList())

            // mocking repository to emit a flow of item list
            coEvery { mockRepository.getListItems() } returns flow

            runTest(testDispatcher) {

                //fetchItems
                viewModel.fetchItems()

                flow.value = listOf(Item(id = 1, listId = 1, name = "Item A"))

                //dispatcher to process all coroutines
                advanceUntilIdle()

                // ensuring items are updated
                viewModel.items.value shouldBe listOf(Item(id = 1, listId = 1, name = "Item A"))

                // ensuring isLoading is updated to false after fetching list is completed
                viewModel.isLoading.value shouldBe false
            }
        }

        it("should handle an empty data response gracefully") {
            // mocking repository to emit an empty flow
            coEvery { mockRepository.getListItems() } returns MutableStateFlow(emptyList())

            runTest(testDispatcher) {
                //fetchItems
                viewModel.fetchItems()

                // dispatcher to process all coroutines
                advanceUntilIdle()

                // verifying items are empty
                viewModel.items.value shouldBe emptyList()

                // verifying isLoading is false after fetching is complete
                viewModel.isLoading.value shouldBe false
            }
        }

        it("should handle exceptions by emitting an empty list") {
            // mocking repository to throw an exception
            coEvery { mockRepository.getListItems() } throws RuntimeException("Network error")

            runTest(testDispatcher) {
                // fetchItems
                viewModel.fetchItems()

                //dispatcher to process all coroutines
                advanceUntilIdle()

                // verifying that list items are empty
                viewModel.items.value shouldBe emptyList()

                // verifying isLoading is false after fetching is complete
                viewModel.isLoading.value shouldBe false
            }
        }
    }

})
