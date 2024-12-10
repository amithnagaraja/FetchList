package com.app.fetchlist.network

import com.app.fetchlist.model.Item
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.collections.shouldContainExactly
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

class ListRepositoryTest : DescribeSpec({
    val mockApiService = mockk<ListApiService>()
    val repository = ListRepository(mockApiService)

    describe("Testing ListRepository") {
        it("should return filtered and sorted items") {
            val mockItems = listOf(
                Item(id = 1, listId = 1, name = "Item A"),
                Item(id = 2, listId = 1, name = ""),
                Item(id = 3, listId = 2, name = "Item B"),
                Item(id = 4, listId = 2, name = null)
            )

            coEvery { mockApiService.fetchList() } returns mockItems
            val result = runBlocking { repository.getListItems().first() }

            result shouldContainExactly listOf(
                Item(id = 1, listId = 1, name = "Item A"),
                Item(id = 3, listId = 2, name = "Item B")
            )
        }

        it("should return an empty list if no valid items are found") {
            val mockItems = listOf(
                Item(id = 1, listId = 1, name = ""),
                Item(id = 2, listId = 1, name = null)
            )

            coEvery { mockApiService.fetchList() } returns mockItems
            val result = runBlocking { repository.getListItems().first() }

            result.shouldBeEmpty()
        }
    }

    it("should emit an empty list when an exception occurs") {
        coEvery { mockApiService.fetchList() } throws RuntimeException("Network error")
        val result = runBlocking { repository.getListItems().first() }

        result.shouldBeEmpty()
    }
})
