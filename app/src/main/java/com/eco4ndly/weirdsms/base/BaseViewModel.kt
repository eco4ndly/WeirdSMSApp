package com.eco4ndly.weirdsms.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.eco4ndly.weirdsms.infra.SingleLiveEvent

/**
 * A Sayan Porya code on 2020-01-18
 */
abstract class BaseViewModel: ViewModel() {

  protected val _showToastLiveData: SingleLiveEvent<String> = SingleLiveEvent()
  val showToastLiveDataEvent: LiveData<String> = _showToastLiveData

  protected val _showLoaderLiveData: SingleLiveEvent<Void> = SingleLiveEvent()
  fun showLoaderLiveData(): LiveData<Void> = _showLoaderLiveData

  protected val _hideLoaderLiveData: SingleLiveEvent<Void> = SingleLiveEvent()
  fun hideLoaderLiveData(): LiveData<Void> = _hideLoaderLiveData
}