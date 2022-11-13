package info.degirona.compose2048.ui.tile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp

@Composable
fun Tile(
    fraction: Float,
    value: String,
    color: Color,
    fontSize: TextUnit,
    backgroundColor: Color,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxSize(fraction)
            .padding(tilePadding.dp)
            .background(backgroundColor, RoundedCornerShape(tileCornerRadius.dp)),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = value,
            fontSize = fontSize,
            fontWeight = FontWeight.Bold,
            color = color,
        )
    }
}

private const val tilePadding = 4
private const val tileCornerRadius = 4
