package info.degirona.compose2048.ui.board

import androidx.compose.runtime.Composable
import info.degirona.compose2048.ui.data.BoardModel
import info.degirona.compose2048.ui.tile.TileRenderer
import info.degirona.compose2048.ui.tile.TileRendererInstance

interface BoardRenderer {
    @Composable
    fun BoardModel.Render(fraction: Float)
}

internal object BoardRendererImpl :
    BoardRenderer,
    TileRenderer by TileRendererInstance {

    @Composable
    override fun BoardModel.Render(fraction: Float) {
        staticTiles.forEach { tileModel -> tileModel.RenderStaticTile(fraction) }
        swipedTiles.forEach { tileModel -> tileModel.RenderSwipedTile(fraction) }
        mergedTiles.forEach { tileModel -> tileModel.RenderMergedTile(fraction) }
        newTiles.forEach { tileModel -> tileModel.RenderNewTile(fraction) }
    }
}
