package edu.itvo.pets.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.content.MediaType.Companion.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Pets
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.rememberAsyncImagePainter
import dagger.hilt.android.AndroidEntryPoint
import edu.itvo.pets.presentation.composables.Pet
import edu.itvo.pets.presentation.composables.PetDetails
import edu.itvo.pets.presentation.composables.PetList
import edu.itvo.pets.presentation.ui.theme.PetsTheme
import edu.itvo.pets.presentation.viewmodel.PetViewModel
import kotlinx.coroutines.delay

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PetsTheme {
                AppNavigation()
            }
        }
    }
}

@Composable
fun AppNavigation() {
    val currentScreen = remember { mutableStateOf(Screen.Splash) } // Estado inicial en la pantalla de inicio

    // Cambia a la pantalla principal después de unos segundos
    LaunchedEffect(true) {
        delay(2000) // Esperar 2 segundos en la pantalla de inicio
        currentScreen.value = Screen.List // Cambiar a la pantalla principal
    }

    when (currentScreen.value) {
        Screen.Splash -> SplashScreen() // Pantalla de inicio
        Screen.List -> MainScreen() // Pantalla principal (Lista de Mascotas)
        Screen.Register -> MainScreen() // Registrar nueva mascota
        Screen.Details -> MainScreen() // Detalles de mascota
    }
}

@Composable
fun SplashScreen() {
    val logoUrl = "https://www.zarla.com/images/zarla-mascota-co-1x1-2400x2400-20220111-gwkrbdqqcydm9yxwr636.png?crop=1:1,smart&width=250&dpr=2" // URL de la imagen del logotipo

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = rememberAsyncImagePainter(model = logoUrl),
            contentDescription = "Logo de Pets App",
            modifier = Modifier.size(200.dp) // Ajusta el tamaño del logo como prefieras
        )

    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(viewModel: PetViewModel = hiltViewModel()) {
    val state = viewModel.uiState.collectAsStateWithLifecycle().value
    val currentScreen = remember { mutableStateOf(Screen.List) } // Estado para alternar entre pantallas

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = when (currentScreen.value) {
                            Screen.List -> "Lista de Mascotas"
                            Screen.Register -> "Registrar Mascota"
                            Screen.Details -> "Detalles de Mascota"
                            else -> ""
                        },
                        color = Color.White,
                        style = MaterialTheme.typography.titleLarge
                    )
                },
               // backgroundColor = MaterialTheme.colorScheme.primary, // Usamos directamente un color
                navigationIcon = {
                    if (currentScreen.value != Screen.List) {
                        IconButton(onClick = { currentScreen.value = Screen.List }) {
                            Icon(
                                imageVector = Icons.Filled.ArrowBack,
                                contentDescription = "Volver",
                                tint = Color.White
                            )
                        }
                    }
                }
            )
        },
        floatingActionButton = {
            if (currentScreen.value == Screen.List) {
                FloatingActionButton(
                    onClick = { currentScreen.value = Screen.Register },
                    containerColor = MaterialTheme.colorScheme.secondary,
                    contentColor = Color.White
                ) {
                    Icon(
                        imageVector = Icons.Filled.Pets,
                        contentDescription = "Agregar Mascota",
                        tint = Color.White
                    )
                }
            }
        },
        content = { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFFF5F5F5)) // Fondo claro
                    .padding(innerPadding)
            ) {
                when (currentScreen.value) {
                    Screen.List -> PetList(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 16.dp),
                        pets = state.pets,
                        onPetClick = { pet ->
                            viewModel.onEvent(PetViewModel.PetEvent.SelectPet(pet))
                            currentScreen.value = Screen.Details
                        }
                    )
                    Screen.Register -> Pet(
                        modifier = Modifier.fillMaxSize(),
                        viewModel = viewModel
                    )
                    Screen.Details -> {
                        state.selectedPet?.let { pet ->
                            PetDetails(
                                pet = pet,
                                onDelete = {
                                    viewModel.onEvent(PetViewModel.PetEvent.DeletePet(pet))
                                    currentScreen.value = Screen.List
                                },
                                onUpdate = {
                                    viewModel.onEvent(PetViewModel.PetEvent.LoadPetForUpdate(pet))
                                    currentScreen.value = Screen.Register
                                },
                                onBack = { currentScreen.value = Screen.List }
                            )
                        }
                    }

                    Screen.Splash -> TODO()
                }
            }
        }
    )
}






// Enum para definir las pantallas
enum class Screen {
    Splash, // Pantalla de inicio
    List, Register, Details
}



