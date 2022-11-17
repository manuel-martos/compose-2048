package info.degirona.compose2048.ui.board

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import info.degirona.compose2048.R
import info.degirona.compose2048.resolve
import info.degirona.compose2048.ui.board.Layer.Companion.toZIndex
import info.degirona.compose2048.ui.theme.Compose2048Theme

@Composable
fun Board(
    won: Boolean,
    over: Boolean,
    onTryAgainClicked: () -> Unit,
    modifier: Modifier = Modifier,
    maxRows: Int = 4,
    maxCols: Int = 4,
    content: @Composable BoardScope.() -> Unit
) {
    Box(
        modifier = modifier
            .aspectRatio(1f)
    ) {
        Box(
            modifier = Modifier
                .aspectRatio(1f)
                .background(Color(backgroundColor), RoundedCornerShape(cornerRadius.dp))
                .padding(innerPadding.dp)
                .boardBackground(maxRows, maxCols),
            content = { BoardScopeImpl(maxRows, maxCols).content() }
        )
        AnimatedVisibility(
            visible = won || over,
            enter = fadeIn(),
            exit = fadeOut(),
        ) {
            Popup(
                text = won.toPopupMessage(),
                textColor = won.toPopupMessageColor(),
                backgroundColor = won.toPopupBackgroundColor(),
                onTryAgainClicked = onTryAgainClicked
            )
        }
    }
}

@Composable
fun Popup(
    text: String,
    textColor: Color,
    backgroundColor: Color,
    onTryAgainClicked: () -> Unit,
    modifier: Modifier = Modifier,
    layer: Layer = Layer.PopupLayer,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .zIndex(layer.toZIndex())
            .background(backgroundColor, RoundedCornerShape(cornerRadius.dp)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = text,
            fontSize = popupMessageFontSize.sp,
            fontWeight = FontWeight.Bold,
            color = textColor,
        )
        Button(
            onClick = onTryAgainClicked
        ) {
            Text(R.string.popup_try_again.resolve())
        }
    }
}

@Composable
private fun Boolean.toPopupMessage() =
    (if (this) R.string.popup_message_win else R.string.popup_message_game_over).resolve()

private fun Boolean.toPopupMessageColor() =
    Color(if (this) popupMessageWinColor else popupMessageOverColor)

private fun Boolean.toPopupBackgroundColor() =
    Color(if (this) popupBackgroundWinColor else popupBackgroundOverColor)

@Preview
@Composable
private fun BoardEmptyPreview() {
    Compose2048Theme {
        Board(
            won = false,
            over = false,
            onTryAgainClicked = { },
            content = {}
        )
    }
}

@Preview
@Composable
private fun BoardWonPreview() {
    Compose2048Theme {
        Board(
            won = true,
            over = false,
            onTryAgainClicked = { },
            content = {}
        )
    }
}

@Preview
@Composable
private fun BoardGameOverPreview() {
    Compose2048Theme {
        Board(
            won = false,
            over = true,
            onTryAgainClicked = { },
            content = {}
        )
    }
}

private const val innerPadding = 4
private const val cornerRadius = 4
private const val backgroundColor = 0xffbfab9dL
private const val popupMessageFontSize = 56
private const val popupMessageWinColor = 0xffffffffL
private const val popupMessageOverColor = 0xff776e65L
private const val popupBackgroundWinColor = 0xa0edc301L
private const val popupBackgroundOverColor = 0x80ffffffL
