package info.degirona.compose2048.ui.board

import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layout
import info.degirona.compose2048.ui.board.Layer.Companion.toZIndex
import kotlin.math.max
import kotlin.math.roundToInt

interface BoardScope {
    @Stable
    fun Modifier.boardCell(row: Float, col: Float, layer: Layer = Layer.CellLayer): Modifier

    val tileFraction: Float
}

class BoardScopeImpl(
    private val maxRows: Int,
    private val maxCols: Int,
) : BoardScope {

    @Stable
    override fun Modifier.boardCell(row: Float, col: Float, layer: Layer): Modifier =
        layout { measurable, constraints ->
            val placeable = measurable.measure(constraints)
            layout(placeable.width, placeable.height) {
                placeable.place(
                    x = (placeable.width * col).roundToInt(),
                    y = (placeable.height * row).roundToInt(),
                    zIndex = layer.toZIndex()
                )
            }
        }

    override val tileFraction: Float get() = 1f / max(maxRows, maxCols)
}
