package com.example.githubapi

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ListRepo(index: Int, repoName: MutableState<List<DataRepos>>) {
    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(top = 1.dp, end = 8.dp, start = 4.dp, bottom = 5.dp),
        shape = RoundedCornerShape(7.dp),
        elevation = 5.dp
    ) {
        Column(modifier = Modifier
            .background(Color.Green)
            .padding(top = 5.dp),
        verticalArrangement = Arrangement.SpaceBetween) {
            Text(text = if (repoName.value[index].name == "")
                ""
            else
                "Repo's name: ${repoName.value[index].name}",
                fontSize = 16.sp,
            modifier = Modifier.padding(2.dp))

            Text(text = if (repoName.value[index].description == "")
                ""
            else
                "Repo's discription: ${repoName.value[index].description}",
                fontSize = 16.sp,
                modifier = Modifier.padding(2.dp))

            Text(text = if (repoName.value[index].id == null)
                ""
            else
                "Id: ${repoName.value[index].id.toString()}",
                fontSize = 16.sp,
                modifier = Modifier.padding(2.dp))
        }
    }
}