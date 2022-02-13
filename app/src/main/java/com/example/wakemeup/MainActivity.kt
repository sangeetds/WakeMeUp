package com.example.wakemeup

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.wakemeup.ui.theme.WakeMeUpTheme
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      WakeMeUpTheme {
        // A surface container using the 'background' color from the theme
        Surface(color = MaterialTheme.colors.background) {
          Greeting()
        }
      }
    }
  }

  @Composable
  fun Greeting(model: BuddiesViewModel = hiltViewModel()) {
    val buddies = model.buddies.observeAsState(initial = listOf())
    Buddies(buddies.value)
  }

  @Composable
  fun Buddies(buddies: List<String>) {
    var visible by remember { mutableStateOf(true) }
    var chosenBuddy by remember { mutableStateOf("") }

    if (visible) {
      Timber.i("Showing ${buddies.size} contacts")
      LazyColumn(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(100.dp)

      ) {
        items(items = buddies) { buddy ->
          Text(
            text = buddy,
            color = Color.Red,
            modifier = Modifier.clickable { visible = false; chosenBuddy = buddy })
        }
      }
    } else {
      Timber.i("Configuring the alarm for $chosenBuddy")
      var hourText by rememberSaveable { mutableStateOf("") }
      var minuteText by rememberSaveable { mutableStateOf("") }
      var secondText by rememberSaveable { mutableStateOf("") }

      Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(100.dp)
      ) {
        TextField(
          value = hourText,
          onValueChange = {
            hourText = it
          },
          label = { Text("HH:") }
        )
        TextField(
          value = minuteText,
          onValueChange = {
            minuteText = it
          },
          label = { Text("MM:") }
        )
        TextField(
          value = secondText,
          onValueChange = {
            secondText = it
          },
          label = { Text("DD:") }
        )
        Button(onClick = { /*TODO*/ }) {
          Text("Set")
        }
      }
    }
  }

  @Preview(showBackground = true)
  @Composable
  fun DefaultPreview() {
    WakeMeUpTheme {
      Buddies(listOf("Sangeet", "Piyush", "Saket"))
    }
  }
}