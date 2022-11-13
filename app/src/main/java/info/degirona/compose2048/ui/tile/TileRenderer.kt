package info.degirona.compose2048.ui.tile

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.key
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import info.degirona.compose2048.ui.board.BoardScope
import info.degirona.compose2048.ui.board.BoardScopeInstance
import info.degirona.compose2048.ui.board.Layer
import info.degirona.compose2048.ui.data.TileModel

private const val NEW_DURATION = 100
private const val SWIPE_DURATION = 100
private const val MERGE_DURATION = 200

interface TileRenderer {
    @Composable
    fun TileModel.RenderNewTile(fraction: Float)

    @Composable
    fun TileModel.RenderStaticTile(fraction: Float)

    @Composable
    fun TileModel.RenderSwipedTile(fraction: Float)

    @Composable
    fun TileModel.RenderMergedTile(fraction: Float)
}

object TileRendererInstance :
    TileRenderer,
    BoardScope by BoardScopeInstance {

    @Composable
    override fun TileModel.RenderNewTile(fraction: Float) {
        key(this.id) {
            val scale = remember { Animatable(0f) }
            val alpha = remember { Animatable(0f) }
            Tile(
                fraction = fraction,
                value = curValue.toString(),
                color = curValue.toTextColor(),
                fontSize = curValue.toFontSize(),
                backgroundColor = curValue.toBackgroundColor(),
                modifier = Modifier
                    .boardCell(
                        row = curPosition.row,
                        col = curPosition.col,
                        layer = Layer.AnimationLayer
                    )
                    .scale(scale.value)
                    .alpha(alpha.value)
            )
            LaunchedEffect(this) {
                scale.animateTo(
                    targetValue = 1f,
                    animationSpec = tween(
                        delayMillis = SWIPE_DURATION,
                        durationMillis = NEW_DURATION
                    )
                )
            }
            LaunchedEffect(this) {
                alpha.animateTo(
                    targetValue = 1f,
                    animationSpec = tween(
                        delayMillis = SWIPE_DURATION,
                        durationMillis = NEW_DURATION
                    )
                )
            }
        }
    }

    @Composable
    override fun TileModel.RenderStaticTile(fraction: Float) {
        key(this.id) {
            Tile(
                fraction = fraction,
                value = curValue.toString(),
                color = curValue.toTextColor(),
                fontSize = curValue.toFontSize(),
                backgroundColor = curValue.toBackgroundColor(),
                modifier = Modifier
                    .boardCell(
                        row = curPosition.row,
                        col = curPosition.col,
                        layer = Layer.CellLayer
                    )
            )
        }
    }

    @Composable
    override fun TileModel.RenderSwipedTile(fraction: Float) {
        key(this.id) {
            val row = remember { Animatable(prevPosition!!.row) }
            val col = remember { Animatable(prevPosition!!.col) }
            Tile(
                fraction = fraction,
                value = curValue.toString(),
                color = curValue.toTextColor(),
                fontSize = curValue.toFontSize(),
                backgroundColor = curValue.toBackgroundColor(),
                modifier = Modifier
                    .boardCell(
                        row = row.value,
                        col = col.value,
                        layer = Layer.AnimationLayer
                    )
            )
            if (row.value != curPosition.row) {
                LaunchedEffect(this) {
                    row.animateTo(
                        targetValue = curPosition.row,
                        animationSpec = tween(durationMillis = SWIPE_DURATION)
                    )
                }
            }
            if (col.value != curPosition.col) {
                LaunchedEffect(this) {
                    col.animateTo(
                        targetValue = curPosition.col,
                        animationSpec = tween(durationMillis = SWIPE_DURATION)
                    )
                }
            }
        }
    }

    @Composable
    override fun TileModel.RenderMergedTile(fraction: Float) {
        key(this.id) {
            val scale = remember { Animatable(0f) }
            Tile(
                fraction = fraction,
                value = curValue.toString(),
                color = curValue.toTextColor(),
                fontSize = curValue.toFontSize(),
                backgroundColor = curValue.toBackgroundColor(),
                modifier = Modifier
                    .boardCell(
                        row = curPosition.row,
                        col = curPosition.col,
                        layer = Layer.MergeLayer
                    )
                    .scale(scale.value),
            )
            LaunchedEffect(this) {
                scale.animateTo(
                    targetValue = 1f,
                    animationSpec = keyframes {
                        delayMillis = SWIPE_DURATION
                        durationMillis = MERGE_DURATION
                        0f atFraction 0f
                        1.2f atFraction 0.5f
                        1f atFraction 1f
                    }
                )
            }
        }
    }

    private fun Int.toBackgroundColor() =
        when {
            this == 2 -> Color(0xffeee4da)
            this == 4 -> Color(0xffede0c8)
            this == 8 -> Color(0xfff2b179)
            this == 16 -> Color(0xfff59563)
            this == 32 -> Color(0xfff67c5f)
            this == 64 -> Color(0xfff65e3b)
            this == 128 -> Color(0xffedcf72)
            this == 256 -> Color(0xffedcc61)
            this == 512 -> Color(0xffedc850)
            this == 1024 -> Color(0xffedc53f)
            this == 2048 -> Color(0xffedc22e)
            else -> Color(0xff3c3a32)
        }

    private fun Int.toTextColor() =
        if (this < 8) Color(0xff776e65) else Color(0xfff8f6f2)

    private fun Int.toFontSize() =
        when {
            this == 2 -> 40.sp
            this == 4 -> 40.sp
            this == 8 -> 40.sp
            this == 16 -> 40.sp
            this == 32 -> 40.sp
            this == 64 -> 40.sp
            this == 128 -> 32.sp
            this == 256 -> 32.sp
            this == 512 -> 32.sp
            this == 1024 -> 24.sp
            this == 2048 -> 24.sp
            else -> 20.sp
        }
}
