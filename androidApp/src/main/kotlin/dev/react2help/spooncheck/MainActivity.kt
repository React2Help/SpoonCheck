package dev.react2help.spooncheck

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import dev.react2help.spooncheck.repositories.InMemoryTaskRepository

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        val spoonCheckApplication = application as SpoonCheckApplication

        setContent {
            App(
                taskRepository = spoonCheckApplication.taskRepository
            )
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    App(taskRepository = InMemoryTaskRepository())
}