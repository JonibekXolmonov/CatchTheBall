package com.catchthisball.catcher.ui.screen.score

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.catchthisball.catcher.utils.Contants.FIFTH
import com.catchthisball.catcher.utils.Contants.FIRST
import com.catchthisball.catcher.utils.Contants.FORTH
import com.catchthisball.catcher.utils.Contants.SECOND
import com.catchthisball.catcher.utils.Contants.THIRD
import com.catchthisball.catcher.utils.SharedPref
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ScoreViewModel @Inject constructor(
    private val sharedPref: SharedPref
) : ViewModel() {

    val top5Score = mutableStateOf<List<Int>>(emptyList())
    private val list = mutableListOf<Int>()


    fun getScores() {
        viewModelScope.launch {
            list.add(sharedPref.getIntScore(FIRST))
            list.add(sharedPref.getIntScore(SECOND))
            list.add(sharedPref.getIntScore(THIRD))
            list.add(sharedPref.getIntScore(FORTH))
            list.add(sharedPref.getIntScore(FIFTH))

            top5Score.value = list.filter { it != 0 }
        }
    }
}