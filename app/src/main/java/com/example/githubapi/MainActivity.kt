package com.example.githubapi

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.githubapi.ui.theme.GitHubApiTheme
import com.example.githubapi.ui.theme.Teal200
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import org.json.JSONArray


const val URL = "https://api.github.com/"
const val TAG = "MyLogTag"

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        suspend fun getDataFromApi(userState: MutableState<DataUser>, name: String) {

            coroutineScope {
                launch {
                    val product = downloadData().getData.getUsersData(name)

                    runOnUiThread {
                        userState.value = product
                    }
                }
            }
        }

        setContent {
            val userData = remember {
                mutableStateOf(DataUser(
                    "",
                    "",
                    0,
                    "",
                    ""
                )
                )
            }
            val coroutineScope = rememberCoroutineScope()
            val textWithRepoName = remember { mutableStateOf("") }
            val textField = remember { mutableStateOf("") }
            val reposData = remember {
                mutableStateOf(listOf(DataRepos()))

            }

            GitHubApiTheme {

                Column(modifier = Modifier
                    .fillMaxSize()
                    .background(Teal200)) {
                    MainCard(textField, userData, onClear = {
                        getClear(userData, textField)
                        getReposClear(reposData)

                    }, onClickSearch = {

                        if (textField.value.isNotEmpty()) {
                            coroutineScope.launch(Dispatchers.IO) {
                                getDataFromApi(userData, textField.value)
                            }
                        } else {
                            val toast = Toast.makeText(this@MainActivity, "Enter login", Toast.LENGTH_LONG)
                            toast.show()
                        }

                    })

                    if (userData.value.login.isNotEmpty()) {
                        BodyCard(usersData = userData, reposData, repoData = textWithRepoName, onGetRepos = {

                            coroutineScope.launch(Dispatchers.IO) {
                                getData(userData.value.login, this@MainActivity, reposData, userData.value.public_repos, textWithRepoName)

                            }

                            if (userData.value.login.isEmpty()) {
                                val toast = Toast.makeText(this@MainActivity, "Can't find repo", Toast.LENGTH_LONG)
                                toast.show()
                            }
                        })
                    }
                    }
                }
            }
        }
    }

private fun getData(name: String, context: Context, dataRepos: MutableState<List<DataRepos>>, pubRepo: Int, getRepoName: MutableState<String>) {

    val URL_REPO = "https://api.github.com/users/$name/repos"
    val queue = Volley.newRequestQueue(context)
    val sRequest = StringRequest(
        Request.Method.GET,
        URL_REPO, {
            response ->
        val list = getDataRepos(response, pubRepo)
            dataRepos.value = list
            getRepoName.value = dataRepos.value[0].name
            Log.d(TAG, "getData is /${dataRepos.value[0].id}/")
        },
        {
            Log.d(TAG, "getData error is /${it}/")
        }
    )
    queue.add(sRequest)
}

private fun getDataRepos(response: String, pubRepo: Int) :List<DataRepos> {

    if (response.isEmpty() || pubRepo == 0)
        return listOf()

    val mainObject = JSONArray(response)
    val list = ArrayList<DataRepos>()

    for (i in 0 until pubRepo) {
        val item = mainObject.getJSONObject(i)
        list.add(
            DataRepos(
                item.getString("name"),
                item.getString("description"),
                item.getInt("id")
            )
        )

        Log.d(TAG, "getDataRepos ${list[i].name} / ${item.getString("name")}")
    }
    return list
}

private fun getClear(userData: MutableState<DataUser>, textFiled: MutableState<String>) {

    val emptyUserData = (DataUser(
            "",
            "",
            0,
            "",
        ""
        ))

    if (userData.value.login.isNotEmpty()) {
        userData.value = emptyUserData
        textFiled.value = ""
    }
}

private fun getReposClear(reposData: MutableState<List<DataRepos>>) {
    val emptyRepos = mutableStateOf(listOf(DataRepos("", "", null)))

    reposData.value = emptyRepos.value
}

