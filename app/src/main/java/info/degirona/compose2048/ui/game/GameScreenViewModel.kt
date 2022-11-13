package info.degirona.compose2048.ui.game

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Offset
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import info.degirona.compose2048.game.Direction
import info.degirona.compose2048.game.GameManager
import info.degirona.compose2048.game.Grid
import info.degirona.compose2048.ui.data.BoardModel
import info.degirona.compose2048.ui.data.TileModel
import info.degirona.compose2048.ui.data.TilePosition
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.UUID
import kotlin.math.abs

class GameScreenViewModel(
    private val gameManager: GameManager
) : ViewModel() {
    var boardModel by mutableStateOf(gameManager.toBoardModel())
        private set

    var score by mutableStateOf(0)
        private set

    var bestScore by mutableStateOf(gameManager.getBestScore())
        private set

    var won by mutableStateOf(gameManager.getWon())
        private set

    var over by mutableStateOf(gameManager.getOver())
        private set

    fun restartGame() {
        viewModelScope.launch(Dispatchers.Default) {
            gameManager.restart()
            score = 0
            boardModel = gameManager.toBoardModel()
            won = gameManager.getWon()
            over = gameManager.getOver()
        }
    }

    fun applyDragGesture(dragOffset: Offset) {
        viewModelScope.launch(Dispatchers.Default) {
            val direction = if (abs(dragOffset.x) > abs(dragOffset.y)) {
                if (dragOffset.x > 0) {
                    Direction.Right
                } else {
                    Direction.Left
                }
            } else {
                if (dragOffset.y > 0) {
                    Direction.Down
                } else {
                    Direction.Up
                }
            }
            gameManager.move(direction)
            boardModel = gameManager.toBoardModel()
            score = gameManager.getScore()
            bestScore = gameManager.getBestScore()
            won = gameManager.getWon()
            over = gameManager.getOver()
        }
    }

    private companion object {
        fun GameManager.toBoardModel() =
            BoardModel(
                newTiles = getGrid().toNewTiles(),
                staticTiles = getGrid().toStaticTiles(),
                swipedTiles = getGrid().toSwipedTiles(),
                mergedTiles = getGrid().toMergedTiles(),
            )

        fun Grid.toNewTiles() = cells
            .flatten()
            .filterNotNull()
            .filter { it.mergedFrom == null && it.prevPosition == null }
            .map {
                TileModel(
                    id = it.id,
                    curPosition = TilePosition(
                        row = it.y.toFloat(),
                        col = it.x.toFloat(),
                    ),
                    curValue = it.value,
                )
            }

        fun Grid.toStaticTiles() = cells
            .flatten()
            .filterNotNull()
            .filter { it.mergedFrom == null && it.prevPosition != null && it.curPosition == it.prevPosition }
            .map {
                TileModel(
                    id = it.id,
                    curPosition = TilePosition(
                        row = it.y.toFloat(),
                        col = it.x.toFloat(),
                    ),
                    curValue = it.value,
                )
            }

        fun Grid.toSwipedTiles() = cells
            .flatten()
            .filterNotNull()
            .flatMap {
                if (it.prevPosition != null && it.curPosition != it.prevPosition) {
                    listOf(
                        TileModel(
                            id = it.id,
                            curPosition = TilePosition(
                                row = it.y.toFloat(),
                                col = it.x.toFloat(),
                            ),
                            prevPosition = TilePosition(
                                row = it.prevPosition.y.toFloat(),
                                col = it.prevPosition.x.toFloat(),
                            ),
                            curValue = it.value,
                        )
                    )
                } else if (it.mergedFrom != null) {
                    val prevValue = it.mergedFrom.first.value
                    listOf(
                        TileModel(
                            id = UUID.randomUUID().toString(),
                            curPosition = TilePosition(
                                row = it.y.toFloat(),
                                col = it.x.toFloat(),
                            ),
                            prevPosition = TilePosition(
                                row = it.mergedFrom.first.curPosition.y.toFloat(),
                                col = it.mergedFrom.first.curPosition.x.toFloat(),
                            ),
                            curValue = prevValue,
                        ),
                        TileModel(
                            id = UUID.randomUUID().toString(),
                            curPosition = TilePosition(
                                row = it.y.toFloat(),
                                col = it.x.toFloat(),
                            ),
                            prevPosition = TilePosition(
                                row = it.mergedFrom.second.prevPosition!!.y.toFloat(),
                                col = it.mergedFrom.second.prevPosition!!.x.toFloat(),
                            ),
                            curValue = prevValue,
                        ),
                    )
                } else emptyList()
            }

        fun Grid.toMergedTiles() = cells
            .flatten()
            .filterNotNull()
            .filter { it.mergedFrom != null }
            .map {
                TileModel(
                    id = it.id,
                    curPosition = TilePosition(
                        row = it.y.toFloat(),
                        col = it.x.toFloat(),
                    ),
                    curValue = it.value,
                )
            }
    }
}

class GameScreenViewModelFactory(
    private val gameManager: GameManager,
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        GameScreenViewModel(gameManager) as T
}
