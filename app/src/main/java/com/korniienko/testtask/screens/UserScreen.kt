package com.korniienko.testtask.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.TabRowDefaults.Divider
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.korniienko.testtask.R
import com.korniienko.testtask.api.User
import com.korniienko.testtask.screens.vm.UserViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.korniienko.testtask.views.UserItem
import androidx.compose.foundation.lazy.rememberLazyListState

@Composable
fun UsersScreen(viewModel: UserViewModel = viewModel()) {
    val users = viewModel.users
    val errorMessage = viewModel.errorMessage
    val isLoading = viewModel.isLoading
    val isLoadingMore = viewModel.isLoadingMore

    val listState = rememberLazyListState()

    LaunchedEffect(Unit) {
        if (viewModel.currentPage == 1 && users.isEmpty()) {
            viewModel.fetchUsers(page = 1)
        }
    }

    LaunchedEffect(listState) {
        snapshotFlow { listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index }
            .collect { lastVisibleItemIndex ->
                val totalItemsCount = listState.layoutInfo.totalItemsCount
                if (lastVisibleItemIndex == totalItemsCount - 1 && !isLoadingMore.value && viewModel.currentPage < viewModel.totalPages) {
                    viewModel.fetchNextPage() // Завантаження наступної сторінки
                }
            }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        when {
            isLoading.value -> {
                CircularProgressIndicator(color = colorResource(id = R.color.blue), modifier = Modifier.align(Alignment.Center))
            }
            errorMessage.value.isNotEmpty() -> {
                Text(
                    text = errorMessage.value,
                    color = Color.Red,
                    fontSize = 20.sp,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            users.isEmpty() -> {
                NoUsersScreen()
            }
            else -> {
                LazyColumn(
                    state = listState,
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(users) { user ->
                        UserItem(user = user)
                    }

                    // Індикатор завантаження наступної сторінки
                    if (isLoadingMore.value) {
                        item {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator(color = colorResource(id = R.color.blue))
                            }
                        }
                    }
                }
            }
        }
    }
}




@Composable
fun NoUsersScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                painter = painterResource(id = R.drawable.ic_no_users),
                contentDescription = "Лого",
                modifier = Modifier.size(100.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                "There are no users yet",
                color = colorResource(id = R.color.black),
                fontSize = 20.sp,
                fontFamily = FontFamily(Font(R.font.regular))
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun UsersScreenPreview() {
    UsersScreen()
}