package info.degirona.compose2048.game

import info.degirona.compose2048.game.Direction.Companion.toVector
import java.util.UUID
import kotlin.random.Random

internal data class Vector(
    val x: Int,
    val y: Int,
)

internal data class Traversals(
    val x: IntProgression,
    val y: IntProgression,
)

internal data class FarthestPosition(
    val farthest: Position,
    val next: Position,
)

enum class Direction {
    Left, Right, Up, Down;

    companion object {
        internal fun Direction.toVector() =
            when (this) {
                Left -> Vector(x = -1, y = 0)
                Right -> Vector(x = 1, y = 0)
                Up -> Vector(x = 0, y = -1)
                Down -> Vector(x = 0, y = 1)
            }
    }
}

data class GameData(
    var score: Int = 0,
    var over: Boolean = false,
    var won: Boolean = false,
)

class GameManager(
    private val size: Int,
    private val storageManager: StorageManager,
    private val startTiles: Int = 2
) {
    private var grid = Grid(size)
    private val gameData = GameData()

    init {
        grid.addStartTiles(startTiles)
    }

    fun getGrid(): Grid = grid

    fun restart() {
        grid = Grid(size)
        grid.addStartTiles(startTiles)
    }

    fun move(direction: Direction) {
        if (isGameTerminated()) {
            return
        }

        val vector = direction.toVector()
        val traversals = buildTraversals(vector)
        var moved = false

        prepareTiles()

        traversals.x.forEach { x ->
            traversals.y.forEach { y ->
                val position = Position(x = x, y = y)
                grid.cellContent(position)?.let { tile ->
                    val positions = findFarthestPosition(position, vector)
                    val next = grid.cellContent(positions.next)
                    val newPosition =
                        if (next != null && next.value == tile.value && next.mergedFrom == null) {
                            val merged = Tile(
                                id = UUID.randomUUID().toString(),
                                curPosition = positions.next,
                                value = tile.value * 2,
                                mergedFrom = tile to next
                            )
                            grid.insertTile(merged)
                            grid.removeTile(tile)
                            gameData.score += merged.value
                            if (storageManager.getBestScore() < gameData.score) {
                                storageManager.setBestScore(gameData.score)
                            }
                            if (merged.value == 2048) {
                                gameData.won = true
                            }
                            merged.curPosition
                        } else {
                            moveTile(tile, positions.farthest)
                            positions.farthest
                        }

                    if (position != newPosition) {
                        moved = true
                    }
                }
            }
        }
        if (moved) {
            grid.addRandomTile()
            if (!movesAvailable()) {
                gameData.over = true
            }
        }
    }

    private fun movesAvailable(): Boolean =
        grid.areCellsAvailable() || tileMatchesAvailable()

    private fun tileMatchesAvailable(): Boolean {
        for (x in 0 until size) {
            for (y in 0 until size) {
                val position = Position(x, y)
                val tile = grid.cellContent(position)
                if (tile != null) {
                    for (direction in Direction.values()) {
                        val vector = direction.toVector()
                        val otherPosition = Position(x + vector.x, y + vector.y)
                        val otherTile = grid.cellContent(otherPosition)
                        if (otherTile != null && otherTile.value == tile.value) {
                            return true
                        }
                    }
                }
            }
        }
        return false
    }

    private fun prepareTiles() {
        grid.cells
            .flatten()
            .filterNotNull()
            .forEach {
                grid.cells[it.x][it.y] = it.copy(
                    prevPosition = it.curPosition,
                    mergedFrom = null,
                )
            }
    }

    private fun moveTile(tile: Tile, position: Position) {
        grid.cells[tile.x][tile.y] = null
        grid.cells[position.x][position.y] = tile.copy(curPosition = position)
    }

    private fun findFarthestPosition(position: Position, vector: Vector): FarthestPosition {
        var curPosition: Position = position
        var prevPosition: Position
        do {
            prevPosition = curPosition
            curPosition = Position(x = prevPosition.x + vector.x, y = prevPosition.y + vector.y)
        } while (grid.withinBounds(curPosition) && grid.isCellAvailable(
                curPosition
            )
        )
        return FarthestPosition(
            farthest = prevPosition,
            next = curPosition,
        )
    }

    private fun buildTraversals(vector: Vector) =
        Traversals(
            x = if (vector.x == 1) (size - 1 downTo 0) else (0 until size),
            y = if (vector.y == 1) (size - 1 downTo 0) else (0 until size),
        )

    private fun Grid.addStartTiles(startTiles: Int) {
        repeat(startTiles) {
            addRandomTile()
        }
    }

    private fun isGameTerminated() = gameData.over

    private fun Grid.addRandomTile() {
        if (availableCells().isNotEmpty()) {
            val value = if (Random.nextFloat() < 0.9f) 2 else 4
            randomAvailableCell()?.let {
                insertTile(
                    Tile(
                        id = UUID.randomUUID().toString(),
                        curPosition = it,
                        value = value
                    )
                )
            }
        }
    }

    fun getScore(): Int = gameData.score

    fun getBestScore(): Int = storageManager.getBestScore()

    fun getWon(): Boolean = gameData.won

    fun getOver(): Boolean = gameData.over
}
