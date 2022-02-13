package com.example.wakemeup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class BuddiesViewModel @Inject constructor(private val buddiesRepository: BuddiesRepository) :
  ViewModel() {

  private val _buddies: MutableLiveData<List<String>> = MutableLiveData()
  val buddies: LiveData<List<String>> = _buddies

  fun getBuddies() = viewModelScope.launch {
    val buddies = buddiesRepository.getBuddies()
    if (buddies is Result.Success) {
      _buddies.value = buddies.data
    } else {
      Timber.e("No data received")
    }
  }
}