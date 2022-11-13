package info.degirona.compose2048.ui.game

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import info.degirona.compose2048.R
import info.degirona.compose2048.resolve

@Composable
fun SubHeader(
    onResetClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.End,
    ) {
        Button(
            onClick = onResetClicked
        ) {
            Text(R.string.sub_header_new_game.resolve())
        }
    }
}
