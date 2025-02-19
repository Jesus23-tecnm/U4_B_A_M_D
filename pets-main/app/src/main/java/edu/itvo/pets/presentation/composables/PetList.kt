package edu.itvo.pets.presentation.composables

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import edu.itvo.pets.data.models.PetModel
import androidx.compose.animation.core.*



@Composable
fun PetList(
    modifier: Modifier = Modifier,
    pets: List<PetModel>,
    onPetClick: (PetModel) -> Unit
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(pets) { pet ->
            PetListItem(
                pet = pet,
                onClick = { onPetClick(pet) }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PetListItem(pet: PetModel) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp) // Altura de la tarjeta
            .animateContentSize() // Anima el tamaño del contenido de la tarjeta
            .padding(8.dp), // Espaciado dentro de la tarjeta
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant // Cambio en el color de fondo
        ),
        elevation = CardDefaults.cardElevation(8.dp) // Elevación para dar profundidad
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {
            // Imagen de la mascota
            Image(
                painter = rememberAsyncImagePainter(model = pet.image),
                contentDescription = "Imagen de ${pet.name}",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(100.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .border(2.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(8.dp)) // Borde animado
            )

            Spacer(modifier = Modifier.width(8.dp))

            // Información de la mascota
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = pet.name,
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = MaterialTheme.colorScheme.onSurfaceVariant // Color del texto
                )
                Text(
                    text = "Raza: ${pet.race}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = "Tipo: ${pet.type}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = pet.description,
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}


