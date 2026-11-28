package dev.martinez.spooncheck

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val spoonCheckApplication = application as SpoonCheckApplication

        setContent {
            App(
                taskRepository = spoonCheckApplication.taskRepository,
                eventRepository = spoonCheckApplication.eventRepository,
            )
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier,
    )
}

/*
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
SpoonCheckTheme {
Greeting("Android")
}
}
*/
