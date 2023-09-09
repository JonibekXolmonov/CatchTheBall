package com.catchthisball.catcher.ui.screen.select

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.catchthisball.catcher.utils.Contants.BALL
import com.catchthisball.catcher.utils.SharedPref
import com.frt.cardsuits.R
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BallSelectionViewModel @Inject constructor(private val sharedPref: SharedPref) : ViewModel() {

    private var _ball = mutableStateOf(R.drawable.soccer_ball)
    val ball get() = _ball

    fun saveSelectedBall(id: Int) {
        viewModelScope.launch {
            sharedPref.saveInt(BALL, id)
            _ball.value = id
        }
    }

    init {
        viewModelScope.launch {
            _ball.value = sharedPref.getInt(BALL)
        }
    }
}