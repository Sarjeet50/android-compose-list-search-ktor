package com.sar.ktor_sample1.ui.screen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.ui.Modifier
import com.sar.ktor_sample1.data.ApiService
import com.sar.ktor_sample1.model.Post
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sar.ktor_sample1.ui.theme.ComposeSampleTheme

@Composable
fun PostsScreen(
    modifier: Modifier = Modifier

) {

    var posts by remember { mutableStateOf<List<Post>>(emptyList()) }
    var isLoading by remember { mutableStateOf(false) }
    var query by remember { mutableStateOf("") }

    val filteredPosts by remember(query) {
        derivedStateOf {
            if (query.isEmpty()) posts
            else posts.filter { it.title.contains(query, ignoreCase = true) }
        }
    }

    LaunchedEffect(Unit) {
        isLoading = true
        ApiService().getPosts().fold(onSuccess = { postList ->
            posts = postList
        }, onFailure = {
            Log.e("", it.message ?: "Something went wrong!")
        }).also {
            isLoading = false
        }
    }
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Column(
            modifier = modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(color = Color(0xFFF5F5F5)),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Search field
            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = query,
                onValueChange = { query = it },
                placeholder = {
                    Text("Search Posts...")
                })
            // List
            PostList(filteredPosts)
            // Loading view
            if (isLoading) LoadingView()
        }
    }
}

@Composable
private fun SearchView(modifier: Modifier = Modifier) {

}

@Composable
private fun LoadingView(modifier: Modifier = Modifier) {
    CircularProgressIndicator()
}

@Composable
private fun PostList(posts: List<Post>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
    ) {
        items(posts) { post ->
            PostCardView(post)
        }
    }
}

@Composable
private fun PostCardView(post: Post) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(10.dp)
        ) {
            Text(post.title, style = MaterialTheme.typography.titleLarge, maxLines = 2)
            Spacer(modifier = Modifier.height(10.dp))
            Text(post.body, style = MaterialTheme.typography.bodyMedium, maxLines = 3)
        }
    }
}

@Preview
@Composable
private fun PostCardPreview() {
    ComposeSampleTheme {
        PostCardView(
            post = Post(
                userId = 1,
                id = 1,
                title = "sunt aut facere repellat provident occaecati excepturi optio reprehenderit",
                body = "quia et suscipit\\nsuscipit recusandae consequuntur expedita et cum\\nreprehenderit molestiae ut ut quas totam\\nnostrum rerum est autem sunt rem eveniet architecto"
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PostListPreview() {
    ComposeSampleTheme {
        PostList(
            posts = listOf(
                Post(
                    id = 1, title = "Some title", body = "some body", userId = 1
                ), Post(
                    id = 2, title = "Some title2", body = "some body2", userId = 2
                )
            )
        )
    }
}