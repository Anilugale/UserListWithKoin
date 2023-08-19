package com.example.userlistwithkoin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.Spring.StiffnessLow
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.userlistwithkoin.model.Data
import com.example.userlistwithkoin.ui.theme.UserListWithKoinTheme
import com.example.userlistwithkoin.viewmodel.UserState
import com.example.userlistwithkoin.viewmodel.UserViewModel
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            UserListWithKoinTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    Scaffold(
                        topBar = { TopAppBar(title = { Text(text = "User List Koin") }) }
                    ) {
                        ShowList(it)
                    }
                }
            }
        }
    }
}

@Composable
fun ShowList(modifier: PaddingValues) {
    val viewModel: UserViewModel = koinViewModel()
    var selectedUser = remember {
       mutableStateOf<Data?>(null)
    }

    when (val collectAsState = viewModel.state.collectAsState().value) {
        UserState.Progress -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(modifier),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        is UserState.Success -> {

            LazyColumn(Modifier.padding(modifier)) {
                items(collectAsState.list.size) {
                    val user = collectAsState.list[it]
                    Card(
                        modifier = Modifier
                            .padding(10.dp)
                            .fillMaxWidth()
                            .wrapContentHeight()
                            .clickable {
                                selectedUser.value = user
                            }
                    ) {

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Image(
                                painter = rememberAsyncImagePainter(user.avatar),
                                contentDescription = "",
                                modifier = Modifier
                                    .padding(vertical = 10.dp)
                                    .padding(start = 10.dp)
                                    .animateContentSize(
                                        animationSpec =

                                        spring(
                                            dampingRatio = Spring.DampingRatioMediumBouncy,
                                            stiffness = StiffnessLow

                                        )
                                        /*tween(durationMillis = 5,
                                            easing = LinearOutSlowInEasing)*/)
                                    .size(if(selectedUser.value!=null && selectedUser.value!!.id == user.id){100.dp}else{60.dp})
                                    .clip(CircleShape)
                            )

                            Column(modifier = Modifier.padding(10.dp)) {
                                Text(text = "ID : ${user.id}")
                                Text(
                                    text = "Name : ${user.first_name} ${user.last_name}",
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.SemiBold

                                )
                                Text(
                                    text = "Email : ${user.email}",
                                    color = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f)
                                )
                            }
                        }

                    }
                }

            }

        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    UserListWithKoinTheme {
        //   Greeting()
    }
}