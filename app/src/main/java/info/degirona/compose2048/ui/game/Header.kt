package info.degirona.compose2048.ui.game

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import info.degirona.compose2048.R
import info.degirona.compose2048.resolve
import info.degirona.compose2048.ui.theme.Compose2048Theme

@Composable
fun Header(
    score: Int,
    bestScore: Int,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth()
    ) {
        HeaderTitle()
        HeaderPanel(title = R.string.header_score.resolve(), value = score.toString())
        Spacer(modifier = Modifier.requiredWidth(headerSpacing.dp))
        HeaderPanel(title = R.string.header_best.resolve(), value = bestScore.toString())
    }
}

@Composable
fun RowScope.HeaderTitle(
    modifier: Modifier = Modifier
) {
    Text(
        modifier = modifier.weight(1f),
        text = R.string.app_name_short.resolve(),
        style = MaterialTheme.typography.h3,
        color = Color(titleColor)
    )
}

@Composable
fun HeaderPanel(
    title: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .wrapContentWidth(unbounded = true)
            .requiredHeight(panelHeight.dp)
            .background(Color(panelColor), RoundedCornerShape(panelCornerRadius.dp))
            .padding(horizontal = panelPadding.dp),
        contentAlignment = Alignment.BottomCenter
    ) {
        Text(
            text = title.uppercase(),
            style = MaterialTheme.typography.body2,
            color = Color(panelTitleColor),
            modifier = Modifier.padding(bottom = panelTitlePaddingBottom.dp)
        )
        Text(
            text = value,
            style = MaterialTheme.typography.body1,
            fontWeight = FontWeight.Bold,
            color = Color(panelValueColor)
        )
    }
}

@Preview
@Composable
private fun HeaderPreview() {
    Compose2048Theme {
        Header(score = 128, bestScore = 65536)
    }
}

private const val headerSpacing = 4
private const val panelHeight = 48
private const val panelPadding = 8
private const val panelCornerRadius = 2
private const val panelColor = 0xffbfab9d
private const val panelTitlePaddingBottom = 28
private const val panelTitleColor = 0xfff0e4d9
private const val panelValueColor = 0xffffffff
private const val titleColor = 0xff776e65
