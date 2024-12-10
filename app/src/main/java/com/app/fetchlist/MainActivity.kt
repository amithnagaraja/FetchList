package com.app.fetchlist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModelProvider
import com.app.fetchlist.network.ListApiService
import com.app.fetchlist.network.ListRepository
import com.app.fetchlist.ui.theme.FetchListTheme
import com.app.fetchlist.utils.Constants
import com.app.fetchlist.viewmodel.ItemListViewModel
import com.app.fetchlist.viewmodel.ItemListViewModelFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val repository = ListRepository(createApiService())

        val viewModel = ViewModelProvider(
            this,
            ItemListViewModelFactory(repository)
        )[ItemListViewModel::class.java]

        //testing if api call is getting the data
        viewModel.fetchItems()

        setContent {
            FetchListTheme {
                // A surface container using the 'background' color from the theme

            }
        }
    }
}

private fun createApiService(): ListApiService {
    val okHttpLogging = HttpLoggingInterceptor()
    okHttpLogging.level = HttpLoggingInterceptor.Level.BODY

    val client = OkHttpClient.Builder()
        .addInterceptor(okHttpLogging)
        .build()

    val retrofit = Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    return retrofit.create(ListApiService::class.java)
}