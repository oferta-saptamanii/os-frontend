package com.example.os_frontend

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import com.example.os_frontend.firestore.FirestoreRepository
import com.example.os_frontend.graph.SetupNavGraph
import com.example.os_frontend.screens.splash.SplashState
import com.example.os_frontend.screens.splash.SplashViewModel
import com.example.os_frontend.ui.theme.OsfrontendTheme
import com.google.firebase.FirebaseApp

class MainActivity : ComponentActivity() {

   // private val splashViewModel: SplashViewModel by viewModels()
   private lateinit var splashViewModel: SplashViewModel

    @SuppressLint("CoroutineCreationDuringComposition")
    override fun onCreate(savedInstanceState: Bundle?) {
        splashViewModel = SplashViewModel(FirestoreRepository(this@MainActivity))
      //  enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)
        installSplashScreen().apply {
          setKeepOnScreenCondition{
              splashViewModel.splashState.value is SplashState.Loading
          }
        }
        setContent {
            OsfrontendTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    val navController = rememberNavController()
                   SetupNavGraph(navController = navController, this@MainActivity)
                }
            }
        }
    }

}


