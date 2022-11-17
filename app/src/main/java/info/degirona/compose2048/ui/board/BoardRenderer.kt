package info.degirona.compose2048.ui.board

import androidx.compose.runtime.Composable
import info.degirona.compose2048.ui.data.BoardModel
import info.degirona.compose2048.ui.tile.TileRenderer
import info.degirona.compose2048.ui.tile.TileRendererInstance

interface BoardRenderer {
    @Composable
    fun BoardScope.Render(boardModel: BoardModel)
}

internal object BoardRendererInstance :
    BoardRenderer,
    TileRenderer by TileRendererInstance {

    @Composable
    override fun BoardScope.Render(boardModel: BoardModel) {
        boardModel.staticTiles.forEach { tileModel -> RenderStaticTile(tileModel) }
        boardModel.swipedTiles.forEach { tileModel -> RenderSwipedTile(tileModel) }
        boardModel.mergedTiles.forEach { tileModel -> RenderMergedTile(tileModel) }
        boardModel.newTiles.forEach { tileModel -> RenderNewTile(tileModel) }
    }
}
