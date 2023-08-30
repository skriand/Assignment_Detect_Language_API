package com.example.assignment_detect_language_api.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.assignment_detect_language_api.MainViewModel
import com.example.assignment_detect_language_api.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun History(modifier: Modifier, navigateTo: () -> Unit, button: Boolean, model: MainViewModel) {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(
        rememberTopAppBarState()
    )

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            LargeTopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.history),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                navigationIcon = {
                    if (button)
                        IconButton(
                            onClick = { navigateTo() }) {
                            Icon(
                                imageVector = Icons.Filled.Close,
                                contentDescription = stringResource(id = R.string.close)
                            )
                        }
                },
                scrollBehavior = scrollBehavior,
            )
        },
        content = { innerPadding ->
            val listItems by model.responseListLiveData.observeAsState(emptyList())

            LazyColumn(
                modifier = Modifier.padding(vertical = 10.dp),
                contentPadding = innerPadding
            ) {
                items(listItems) { item ->
                    Card(modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 6.dp)
                        .fillMaxWidth()){
                        Column(modifier = Modifier.padding(10.dp)) {
                            item.title?.let { Text(text = it) }
                            item.description?.let { Text(text = it) }
                        }
                    }
                }
            }
        }
    )
}