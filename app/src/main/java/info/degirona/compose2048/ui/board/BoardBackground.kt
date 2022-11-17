package info.degirona.compose2048.ui.board

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlin.math.min

internal fun Modifier.boardBackground(
    maxRows: Int,
    maxCols: Int,
) = drawBehind {
    val fixFactorInPx = fixFactor.dp.toPx()
    val cellDim = min(size.width / maxCols, size.height / maxRows)
    for (row in 0 until maxRows) {
        for (col in 0 until maxCols) {
            val cellOffset =
                Offset(
                    x = col * cellDim + fixFactorInPx,
                    y = row * cellDim + fixFactorInPx
                )
            val cellSize = Size(
                width = cellDim - 2f * fixFactorInPx,
                height = cellDim - 2f * fixFactorInPx,
            )
            drawRoundRect(
                color = Color(bgTileColor),
                topLeft = cellOffset,
                size = cellSize,
                cornerRadius = CornerRadius(cornerRadius.dp.toPx())
            )
        }
    }
}

private const val fixFactor = 5
private const val cornerRadius = 4
private const val bgTileColor = 0xffcec1b2
