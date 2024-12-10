package com.app.fetchlist.ui.mainscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.fetchlist.viewmodel.ItemListViewModel
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import com.app.fetchlist.model.Item

//This UI screen will go inside SetContent of MainActivity.
@Composable
fun MainActivityScreen(
    viewModel: ItemListViewModel
) {
    val items = viewModel.items.collectAsState().value
    val isLoading = viewModel.isLoading.collectAsState().value

    //calling api
    LaunchedEffect(Unit) {
        viewModel.fetchItems()
    }

    Box(modifier = Modifier.fillMaxSize()) {
        when {
            //loading state -> display progressbar to user
            isLoading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
            //when list is not empty, use lazycolumn to display items in a list
            items.isNotEmpty() -> {
                LazyColumn (Modifier.fillMaxSize()) {
                    items(items) {item ->
                        ItemRow(item = item)
                    }
                }
            }
            //when items are empty, display items are empty to the user
            else -> {
                Text(
                    text = "Items Empty",
                    modifier = Modifier.align(Alignment.Center))
            }
        }
    }
}

@Composable
fun ItemRow(item: Item) {
    Box(modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp, vertical = 8.dp)
        .background(
            color = Color.LightGray,
            shape = RoundedCornerShape(8.dp)
        )
        .padding(16.dp)
    ) {
        Column {
            Text(
                text = "List ID - ${item.listId}",
                color = Color.Black,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "Name - ${item.name}",
                color = Color.Black,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}