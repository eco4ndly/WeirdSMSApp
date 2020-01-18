package com.eco4ndly.weirdsms.base

import android.app.Activity
import android.app.role.RoleManager
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.Telephony
import android.provider.Telephony.Sms.Intents
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.eco4ndly.weirdsms.utl.Constants
import dagger.android.AndroidInjection

/**
 * A Sayan Porya code on 2020-01-18
 */
abstract class BaseActivity<V: BaseViewModel> : AppCompatActivity() {
  lateinit var viewModel: V

  /**
   * The subclass must override this method to provide the corresponding viewModel variable
   *
   * @return = viewModel Variable
   */
  protected abstract fun provideViewModel(): V

  /**
   * Override to return layout resource id
   *
   * @return = layout resource id
   */
  @LayoutRes
  protected abstract fun getLayoutRes(): Int

  /**
   * The subclass must implement this method.
   * This method is the startup method for activity subclasses
   *
   * The subclass should start its works from this method
   */
  protected abstract fun takeOff()

  protected abstract fun showLoader()
  protected abstract fun hideLoader()

  override fun onCreate(savedInstanceState: Bundle?) {
    AndroidInjection.inject(this)
    super.onCreate(savedInstanceState)
    setContentView(getLayoutRes())
    viewModel = provideViewModel()
    setUpCommonObservers()
    takeOff()
  }


  private fun setUpCommonObservers() {
    viewModel.showToastLiveDataEvent.observe(this, Observer {
      Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
    })

    viewModel.showLoaderLiveData().observe(this, Observer {
      showLoader()
    })

    viewModel.hideLoaderLiveData().observe(this, Observer {
      hideLoader()
    })
  }


  fun showMakeDefaultSmsAppDialog() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
      val roleManager = getSystemService(RoleManager::class.java) as RoleManager
      val intent = roleManager.createRequestRoleIntent(RoleManager.ROLE_SMS)
      startActivityForResult(intent, Constants.MAKE_DEFAULT_SMS_APP_DIALOG_REQ_CODE)
    } else {
      Intent(Intents.ACTION_CHANGE_DEFAULT).apply {
        putExtra(Intents.EXTRA_PACKAGE_NAME, packageName)
        startActivity(this)
      }
    }
  }
}