package info.degirona.compose2048.game

import android.content.Context
import android.content.SharedPreferences

interface StorageManager {
    fun getBestScore(): Int
    fun setBestScore(score: Int)
}

class StorageManagerImpl(
    context: Context,
) : StorageManager {
    private val sharedPreferences: SharedPreferences

    init {
        sharedPreferences = context.getSharedPreferences(GAME_DATA, Context.MODE_PRIVATE)
    }

    override fun getBestScore(): Int =
        sharedPreferences.getInt(BEST_SCORE_KEY, 0)

    override fun setBestScore(bestScore: Int) {
        with (sharedPreferences.edit()) {
            putInt(BEST_SCORE_KEY, bestScore)
            apply()
        }
    }

    private companion object {
        const val GAME_DATA = "gameData"
        const val BEST_SCORE_KEY = "bestScoreKey"
    }
}
