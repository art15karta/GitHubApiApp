package com.example.githubapi

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.githubapi.ui.theme.Purple200


@Composable
fun MainCard(textField: MutableState<String>, usersData: MutableState<DataUser>, onClear: () -> Unit,
             onClickSearch: ()-> Unit) {

    Card(modifier = Modifier.padding(bottom = 15.dp),
    shape = RoundedCornerShape(8.dp),
        elevation = 7.dp,
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Row(modifier = Modifier

                .padding(top = 10.dp)) {

                TextField(value = textField.value , onValueChange = {
                        textField.value = it
                },     modifier = Modifier
                    .padding(start = 9.dp, end = 23.dp, top = 10.dp)
                    .size(280.dp, 68.dp),
                    shape = RoundedCornerShape(8.dp),
                    textStyle = TextStyle(fontSize = 30.sp)
                )

                Column(modifier = Modifier.fillMaxWidth()) {
                    TextButton(modifier = Modifier
                        .border(3.dp, Color.Red, RoundedCornerShape(10.dp))
                        .padding(start = 5.dp, bottom = 3.dp)
                        .size(73.dp, 40.dp),
                        onClick = {

                            onClickSearch.invoke()

                        }) {
                        Text(text = "Search"
                        )
                    }

                    TextButton(modifier = Modifier
                        .border(3.dp, Color.Red, RoundedCornerShape(10.dp))
                        .padding(start = 5.dp)
                        .size(73.dp, 40.dp),
                        onClick = {
                            onClear.invoke()
                        }) {
                        Text(text = "Clear"
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun BodyCard(usersData: MutableState<DataUser>, listRepo: MutableState<List<DataRepos>>, repoData: MutableState<String>, onGetRepos: () -> Unit) {

    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(start = 20.dp, top = 15.dp, end = 10.dp)
        .background(color = Purple200),
        elevation = 5.dp) {

        Column() {
            Row(modifier = Modifier.fillMaxWidth()) {
                AsyncImage(
                    model = usersData.value.avatar_url,
                    contentDescription = "avatar",
                    modifier = Modifier
                        .size(90f.dp, 90f.dp)
                        .border(1.dp, color = Color.Black))

                Column() {
                    Text(text = if (usersData.value.login == null)
                        ""
                    else
                    "Login: ${usersData.value.login}",
                            modifier = Modifier
                            .padding(start = 8.dp))

                    Text(text = if (usersData.value.name == null)
                        ""
                    else
                    "Name: ${usersData.value.name}",
                            modifier = Modifier
                            .padding(start = 8.dp))

                    Text(text = if (usersData.value.email == null)
                        ""
                    else
                    "Email: ${usersData.value.email}",
                            modifier = Modifier
                            .padding(start = 8.dp))

                    TextButton(onClick = {
                        onGetRepos.invoke()

                    }) {
                        Text(text =
                        "Public repos: ${usersData.value.public_repos}",
                        )
                    }
                }
            }
            Box(modifier = Modifier
                .fillMaxWidth()) {
                ReposCard(repoName = listRepo)
            }
    }

            }
}
@Composable
fun ReposCard(repoName: MutableState<List<DataRepos>>) {


    LazyColumn(modifier = Modifier
        .fillMaxWidth()
        .padding(top = 8.dp)) {
        itemsIndexed(
            repoName.value
        ) {

            index, item ->
            if (repoName.value[0].name.isNotEmpty())
            ListRepo(index = index, repoName = repoName)

        }
    }
}