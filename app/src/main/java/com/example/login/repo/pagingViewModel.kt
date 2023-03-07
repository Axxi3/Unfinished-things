package com.example.login.repo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class pagingViewModel @Inject constructor(val discoverRepo: discoverRepo):ViewModel() {

    val list =discoverRepo.getDiscy().cachedIn(viewModelScope)
}