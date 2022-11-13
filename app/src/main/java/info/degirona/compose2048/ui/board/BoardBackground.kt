package info.degirona.compose2048.ui.board

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

internal fun Modifier.boardBackground(
    maxRows: Int,
    maxCols: Int,
) = drawBehind {
    val fixFactorInPx = fixFactor.dp.toPx()
    val cellWidth = size.width / maxCols
    val cellHeight = size.height / maxRows
    for (row in 0 until maxRows) {
        for (col in 0 until maxCols) {
            val cellOffset =
                Offset(
                    x = row * cellWidth + fixFactorInPx,
                    y = col * cellHeight + fixFactorInPx
                )
            val cellSize = Size(
                width = cellWidth - 2f * fixFactorInPx,
                height = cellHeight - 2f * fixFactorInPx,
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
