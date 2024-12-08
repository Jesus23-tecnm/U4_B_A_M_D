package edu.itvo.pets.presentation.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Serif, // Cambia a un estilo más clásico y elegante
        fontWeight = FontWeight.Medium, // Un peso intermedio para dar sofisticación
        fontSize = 18.sp, // Un tamaño ligeramente mayor para resaltar
        lineHeight = 28.sp, // Incrementa la altura de línea para un aspecto más espacioso
        letterSpacing = 0.2.sp // Menor espaciado para un acabado más refinado
    ),
    titleLarge = TextStyle(
        fontFamily = FontFamily.Cursive, // Fuente cursiva para títulos llamativos
        fontWeight = FontWeight.Bold, // Más énfasis en los títulos
        fontSize = 24.sp, // Mayor tamaño para destacar
        lineHeight = 32.sp,
        letterSpacing = 0.1.sp
    )
)

    /* Other default text styles to override
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
    */
