package com.example.assignment_detect_language_api

import com.example.assignment_detect_language_api.data.DataProvider
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.assignment_detect_language_api.data.AppDatabase
import com.example.assignment_detect_language_api.data.DataProvider.dao
import com.example.assignment_detect_language_api.ui.screens.History
import com.example.assignment_detect_language_api.ui.screens.Home
import com.example.assignment_detect_language_api.ui.screens.ResponseItem
import com.example.assignment_detect_language_api.ui.theme.Assignment_Detect_Language_APITheme
import com.microsoft.device.dualscreen.twopanelayout.Screen
import com.microsoft.device.dualscreen.twopanelayout.TwoPaneLayoutNav
import com.microsoft.device.dualscreen.twopanelayout.TwoPaneMode
import com.microsoft.device.dualscreen.twopanelayout.twopanelayoutnav.composable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //DataProvider.detectLanguage(this)
        val viewModel: MainViewModel by viewModels()

        CoroutineScope(Dispatchers.Default).launch {
            dao = AppDatabase.getDatabase(applicationContext)?.taskDao()!!
            if (dao.allRepos.isNullOrEmpty()) dao.insert(*viewModel.responseListItems.toTypedArray())
            else {
                viewModel.setItems(dao.allRepos)
            }
        }

        setContent {
            Assignment_Detect_Language_APITheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ScreenMain { text -> DataProvider.detectLanguage(this, text, viewModel) }
                    //ScreenDemo()
                    //Greeting("Android")
                }
            }
        }
    }
}

@Composable
fun ScreenMain(model: MainViewModel = viewModel(), detectLanguage: (Any) -> Unit) {

    val navController = rememberNavController()
    model.setList()

    TwoPaneLayoutNav(
        navController = navController,
        paneMode = TwoPaneMode.HorizontalSingle,
        singlePaneStartDestination = Routes.Home.route,
        pane1StartDestination = Routes.History.route,
        pane2StartDestination = Routes.Home.route
    ) {

        // First route : Home
        composable(Routes.Home.route) {

            // Lay down the Home Composable
            // and pass the navController

            if (isSinglePane)
                Home(
                    modifier = Modifier.weight(.6f),
                    { navController.navigateTo(Routes.ResponseItem.route, Screen.Pane2) },
                    { navController.navigateTo(Routes.HistorySingle.route, Screen.Pane2) },
                    true,
                    detectLanguage,
                    model
                )
            else {
                Home(
                    modifier = Modifier.weight(.6f),
                    { navController.navigateTo(Routes.ResponseItem.route, Screen.Pane2) },
                    { navController.navigateTo(Routes.HistorySingle.route, Screen.Pane2) },
                    false,
                    detectLanguage,
                    model
                )
                navController.navigateTo(Routes.History.route, Screen.Pane1)
            }

        }

        // Another Route : AddItem
        composable(Routes.History.route) {
            // AddItem Screen
            History(modifier = Modifier.weight(.4f), {
                navController.navigateTo(Routes.Home.route, Screen.Pane2)
            }, false, model)
            if (isSinglePane)
                navController.navigateTo(Routes.Home.route, Screen.Pane2)
        }

        composable(Routes.HistorySingle.route) {
            // AddItem Screen
            History(modifier = Modifier.weight(.4f), {
                navController.navigateTo(
                    Routes.Home.route,
                    Screen.Pane2
                )
            }, true, model)
            if (!isSinglePane)
                navController.navigateTo(Routes.Home.route, Screen.Pane2)
        }

        composable(Routes.ResponseItem.route) {
            ResponseItem(modifier = Modifier.weight(.6f), {
                navController.navigateTo(
                    Routes.Home.route,
                    Screen.Pane2
                )
            }, model            )
        }

        // Items Route, Notice the "/{id}" in last,
        // its the argument passed down from homeScreen
        /*composable(Routes.ResponseItem.route + "/{text}") { twoPaneBackStack ->

            // Extracting the argument
            val text = twoPaneBackStack.arguments?.getString("text")

            // Pass the extracted Counter
            ResponseItem(
                modifier = Modifier.weight(.6f),
                { navController.navigateTo(Routes.ResponseItem.route, Screen.Pane2) },
                text = text,
                detectLanguage
            )
        }*/
    }
}


/*@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScreenDemo(model: MainViewModel = viewModel()) {
    val count by model.languageLiveData.observeAsState(0)

    var text by remember { mutableStateOf(TextFieldValue("")) }
    TextField(modifier = Modifier.fillMaxWidth(),
        value = text,
        label = { Text(text = "Description") },
        onValueChange = { newText ->
            text = newText
        })

    Demo("This is $count") { model.getLanguage() }
}

@Composable
fun Demo(text: String, onClick: () -> Unit = {}) {
    Column {
        Text(text)
        Button(
            onClick = onClick,
        ) {
            Text(text = "Add 1")
        }
    }
}

@Preview
@Composable
fun PreviewDemo() {
    Demo("Preview")
}*/


@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Assignment_Detect_Language_APITheme {
        Greeting("Android")
    }
}