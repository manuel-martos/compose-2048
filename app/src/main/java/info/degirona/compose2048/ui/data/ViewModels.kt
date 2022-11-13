package info.degirona.compose2048.ui.data

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable

@Stable
data class BoardModel(
    val newTiles: List<TileModel> = emptyList(),
    val staticTiles: List<TileModel> = emptyList(),
    val swipedTiles: List<TileModel> = emptyList(),
    val mergedTiles: List<TileModel> = emptyList(),
)

@Immutable
data class TilePosition(
    val row: Float,
    val col: Float,
)

@Immutable
data class TileModel(
    val id: String,
    val curValue: Int,
    val curPosition: TilePosition,
    val prevPosition: TilePosition? = null,
)
