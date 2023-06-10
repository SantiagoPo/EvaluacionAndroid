package com.example.myapplication



import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.example.myapplication.ui.theme.*


class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val todoList = remember {
                mutableStateListOf(
                    TodoItemData("Opcion 1", false),
                    TodoItemData("Opcion 2", false)
                )
            }

            val newTaskText = remember { mutableStateOf("") }

            MyApplicationTheme {
                Column(modifier = Modifier.fillMaxSize() ) {
                    TopAppBar(
                        title = {
                            Text(
                                text = "Tareas",
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .scale(2f)
                                    .padding(vertical = 10.dp),
                                textAlign = TextAlign.Center,
                                style = Typography1.titleLarge
                            )
                        }

                    )
                    TextField(
                        value = newTaskText.value,
                        onValueChange = { newTaskText.value = it },
                        label = { Text(text = "Nueva tarea") },
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(vertical = 40.dp),



                        )
                    Button(
                        onClick = {
                            val newTask = newTaskText.value.trim()
                            if (newTask.isNotEmpty()) {
                                todoList.add(TodoItemData(newTask, false))
                                newTaskText.value = ""
                            }
                        },
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(vertical = 1.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Teal)
                    ) {
                        Text(text = "Agregar tarea")
                    }
                    Button(
                        onClick = {
                            todoList.removeAll { it.checked }
                        },
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(vertical = 10.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Blue)
                    ) {
                        Text(text = "Eliminar tareas")
                    }
                    LazyColumn(modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)) {
                        items(todoList) { todo ->
                            TodoItem(todo = todo)

                        }
                    }

                }
            }
        }
    }
}


@Composable
fun TodoItem(todo: TodoItemData) {
    var checkedState by remember { mutableStateOf(todo.checked) }

    LaunchedEffect(todo.checked) {
        checkedState = todo.checked
    }


    Row(modifier = Modifier
        .padding(15.dp)
        .padding(vertical = 19.dp)) {
        Checkbox(
            checked = checkedState,
            onCheckedChange = { isChecked ->
                checkedState = isChecked
                todo.checked = isChecked
            },
            modifier = Modifier.padding(start = 10.dp)

        )
        Text(text = todo.text,
        modifier = Modifier.padding(vertical = 12.dp))

    }
}

data class TodoItemData(
    val text: String,
    var checked: Boolean
)

