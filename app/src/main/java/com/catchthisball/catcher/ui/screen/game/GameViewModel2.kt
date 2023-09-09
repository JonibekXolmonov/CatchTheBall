package com.catchthisball.catcher.ui.screen.game

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.IntSize
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.catchthisball.catcher.utils.Contants
import com.catchthisball.catcher.utils.Contants.BALL
import com.catchthisball.catcher.utils.MyToOffsetAnim
import com.catchthisball.catcher.utils.SharedPref
import com.frt.cardsuits.R
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class GameViewModel2 @Inject constructor(private val sharedPref: SharedPref) : ViewModel() {

    private var _list = arrayOf(0, 0, 0, 0, 0, 0)


    private var _ball = mutableStateOf(R.drawable.soccer_ball)
    val ball get() = _ball

    private var _score = mutableStateOf(0)
    val score get() = _score

    private var _ballOffset = mutableStateOf(MyToOffsetAnim())
    val ballOffset get() = _ballOffset

    private var _ballFailCatchOffset = mutableStateOf(MyToOffsetAnim())
    val ballFailCatchOffset get() = _ballFailCatchOffset

    private var _ballCatch = mutableStateOf(true)

    private var _rank = mutableStateOf(0)
    val rank get() = _rank

    private var _isPaused = mutableStateOf(false)
    val isPaused get() = _isPaused

    private var _isFinished = mutableStateOf(false)
    val isFinished get() = _isFinished

    private var _isStart = mutableStateOf(false)
    var job: Job? = null

    fun checkIfBallCatch(
        ballTopLeft: Offset,
        gateTopLeft: Offset,
        gateSize: IntSize,
        ballSize: IntSize,
    ) {
        viewModelScope.launch {
            if (_isStart.value) {
                val isWidthInside =
                    ballTopLeft.x in gateTopLeft.x..gateTopLeft.x + gateSize.width &&
                            ballTopLeft.x + ballSize.width <= gateTopLeft.x + gateSize.width

                val isHeightInside =
                    ballTopLeft.y in gateTopLeft.y..gateTopLeft.y + gateSize.height &&
                            ballTopLeft.y + ballSize.height <= gateTopLeft.y + gateSize.width

                _ballCatch.value = isWidthInside && isHeightInside
                if (_ballCatch.value) {
                    increaseScore()
                } else {
                    if (_ballOffset.value.y != _ballOffset.value.x)
                        stopGame()
                }
            }
        }
    }

    private fun increaseScore() {
        viewModelScope.launch {
            _score.value++
        }
    }

    private fun getBall() {
        viewModelScope.launch {
            _ball.value = sharedPref.getInt(BALL)
        }
    }

    fun startGame() {
        viewModelScope.launch {
            _isPaused.value = false
            _isFinished.value = false
            _ballCatch.value = true
            _rank.value = 0
            _list = arrayOf(0, 0, 0, 0, 0, 0)
            _score.value = 0
            _ballFailCatchOffset.value = MyToOffsetAnim()
            _ballOffset.value = MyToOffsetAnim()
            delay(500)
            _isStart.value = true
            generate()
        }
    }

    fun pauseGame() {
        viewModelScope.launch {
            val runningTime = _ballOffset.value.time
            _ballFailCatchOffset.value = _ballOffset.value
            _ballOffset.value = MyToOffsetAnim(
                time = runningTime
            )

            _isPaused.value = true
            job?.cancel()
        }
    }

    fun resumeGame() {
        generate()
        _isPaused.value = false
    }

    private fun stopGame() {
        viewModelScope.launch {
            _ballOffset.value = MyToOffsetAnim()
            _isFinished.value = true
            _isStart.value = false
            job?.cancel()
        }
    }

    private fun generate() {
        job = viewModelScope.launch {
            while (isActive) {
                while (true) {
                    val time = _ballOffset.value.time
                    delay(time.toLong())
                    _ballFailCatchOffset.value = _ballOffset.value
                    _ballOffset.value = MyToOffsetAnim(
                        x = Random.nextInt(-150, 150).toFloat(),
                        y = Random.nextInt(-300, -270).toFloat(),
                        time = if (time > 850 && (!_isFinished.value || !_isPaused.value)) time - 25 else 850
                    )
                }
            }
        }
    }

    fun saveAndGetRank(score: Int) {
        var rank: Int
        viewModelScope.launch {
            val scores = getScores()
            rank = when (score) {
                in scores[3]..scores[4] -> {
                    5
                }
                in scores[2]..scores[3] -> {
                    4
                }
                in scores[1]..scores[2] -> {
                    3
                }
                in scores[0]..scores[1] -> {
                    2
                }
                else -> 1
            }

            scores[5] = score
            Log.d("TAG", "saveAndGetRank: ${scores.contentToString()}")
            scores.sortDescending()
            Log.d("TAG", "saveAndGetRank: ${scores.contentToString()}")

            scores.forEachIndexed { index, value ->
                when (index) {
                    0 -> sharedPref.saveInt(Contants.FIRST, value)
                    1 -> sharedPref.saveInt(Contants.SECOND, value)
                    2 -> sharedPref.saveInt(Contants.THIRD, value)
                    3 -> sharedPref.saveInt(Contants.FORTH, value)
                    4 -> sharedPref.saveInt(Contants.FIFTH, value)
                }
            }
            delay(800)
            _rank.value = rank
        }
    }

    private fun getScores(): Array<Int> {
        _list[0] = (sharedPref.getIntScore(Contants.FIRST))
        _list[1] = (sharedPref.getIntScore(Contants.SECOND))
        _list[2] = (sharedPref.getIntScore(Contants.THIRD))
        _list[3] = (sharedPref.getIntScore(Contants.FORTH))
        _list[4] = (sharedPref.getIntScore(Contants.FIFTH))
        return _list
    }

    init {
        viewModelScope.launch {
            getBall()
        }
    }
}