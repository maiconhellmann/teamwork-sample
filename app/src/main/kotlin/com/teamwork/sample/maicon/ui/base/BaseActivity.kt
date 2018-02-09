package com.teamwork.sample.maicon.ui.base

import android.app.ProgressDialog
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.support.annotation.DrawableRes
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.WindowManager
import com.teamwork.sample.maicon.R
import com.teamwork.sample.maicon.SampleApplication
import com.teamwork.sample.maicon.injection.component.ActivityComponent
import com.teamwork.sample.maicon.injection.component.ConfigPersistentComponent
import com.teamwork.sample.maicon.injection.module.ActivityModule
import com.teamwork.sample.maicon.injection.module.PresenterModule
import com.teamwork.sample.maicon.util.extension.snackbar
import com.teamwork.sample.maicon.util.extension.toast
import timber.log.Timber
import java.util.*
import java.util.concurrent.atomic.AtomicLong

@Suppress("DEPRECATION")
open class BaseActivity: AppCompatActivity() {
    private var  progressDialog: ProgressDialog? = null

    companion object {
        @JvmStatic private val KEY_ACTIVITY_ID = "KEY_ACTIVITY_ID"
        @JvmStatic private val NEXT_ID = AtomicLong(0)
        @JvmStatic private val componentsMap = HashMap<Long, ConfigPersistentComponent>()
    }

    private var activityId: Long = 0
    lateinit var activityComponent: ActivityComponent
        get

    @Suppress("UsePropertyAccessSyntax")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Create the ActivityComponent and reuses cached ConfigPersistentComponent if this is
        // being called startAnimations a configuration change.
        activityId = savedInstanceState?.getLong(BaseActivity.KEY_ACTIVITY_ID) ?: BaseActivity.NEXT_ID.getAndIncrement()

        if (BaseActivity.componentsMap[activityId] != null)
            Timber.i("Reusing ConfigPersistentComponent activityId = $activityId ${this::class.java.simpleName}")

        val configPersistentComponent = BaseActivity.componentsMap.getOrPut(activityId, {
            Timber.i("Creating new ConfigPersistentComponent activityId=$activityId ${this::class.java.simpleName}")

            val component = (applicationContext as SampleApplication).applicationComponent

            component + PresenterModule()
        })

        activityComponent = configPersistentComponent + ActivityModule(this)
    }


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putLong(BaseActivity.KEY_ACTIVITY_ID, activityId)
    }

    override fun onDestroy() {
        if (!isChangingConfigurations) {
            Timber.i("Clearing ConfigPersistentComponent activityId=$activityId ${this::class.java.simpleName}")
            BaseActivity.componentsMap.remove(activityId)

            getPresenter()?.detachView()
            hideProgressIndicator()
        }

        super.onDestroy()
    }

    override fun setTitle(titleId: Int) {
        actionBar?.title = getString(titleId)
        supportActionBar?.title = getString(titleId)
    }

    fun setTitle(title: String) {
        actionBar?.title = title
        supportActionBar?.title = title
    }

    fun displayHomeAsCancelaEnabled(){
        displayHomeAsUpEnabled(R.drawable.ic_close_white_24dp)
    }

    fun displayHomeAsUpEnabled(){
        displayHomeAsUpEnabled(true)
    }
    fun displayHomeAsUpDisabled(){
        displayHomeAsUpEnabled(false)
    }

    fun displayHomeAsUpEnabled(b: Boolean){
        actionBar?.setDisplayHomeAsUpEnabled(b)
        actionBar?.setDisplayShowHomeEnabled(b)

        supportActionBar?.setDisplayHomeAsUpEnabled(b)
        supportActionBar?.setDisplayShowHomeEnabled(b)
    }

    fun displayHomeAsUpEnabled(@DrawableRes iconRes: Int){
        displayHomeAsUpEnabled()
        supportActionBar?.setHomeAsUpIndicator(iconRes)
    }

    open fun hideProgressIndicator() {
        progressDialog?.dismiss()
        progressDialog = null
    }


    open fun showProgressIndicator(title: String? = null, message: String? = null, cancelable: Boolean = true) {
        if(progressDialog == null || progressDialog?.isShowing == false) {
            val dialog = ProgressDialog(this)

            if (title != null) {
                dialog.setTitle(title)
            }

            if (message != null) {
                dialog.setMessage(message)
            }

            dialog.isIndeterminate = true
            dialog.setCancelable(cancelable)
            dialog.show()

            progressDialog = dialog
        }
    }
    open fun showProgressIndicator() {
        showProgressIndicator(message = getString(R.string.wait), cancelable = false)
    }

    override fun onLowMemory() {
        super.onLowMemory()
    }

    fun getParentView(): View? {
        return findViewById(android.R.id.content)
    }

    fun snack(resId: Int, duration: Int = Snackbar.LENGTH_SHORT){
        getParentView()?.snackbar(resId, duration)
    }

    fun snack(msg: String, duration: Int = Snackbar.LENGTH_SHORT){
        getParentView()?.snackbar(msg, duration)
    }

    fun longSnack(resId: Int){
        getParentView()?.snackbar(resId, Snackbar.LENGTH_LONG)
    }
    fun longSnack(msg: String){
        getParentView()?.snackbar(msg, Snackbar.LENGTH_LONG)
    }
    open fun showError(error: String){
        toast(error)
    }

    open fun showError(t: Throwable){
        showError(t.toString())
    }

    fun setFullScreen(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = Color.TRANSPARENT
        }
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN)
    }

    fun hideNavigationBar(){
        val decorView = window.decorView
        val uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_FULLSCREEN
        decorView.systemUiVisibility = uiOptions
    }

    open fun getPresenter(): BasePresenter<out com.teamwork.sample.maicon.ui.base.MvpView>? = null

    fun replaceFragment(fragment: BaseFragment, addToBackStack: Boolean?= false){
        performNoBackStackTransaction(fragment)
    }

    fun performNoBackStackTransaction(fragment: Fragment) {
        val newBackStackLength = fragmentManager.backStackEntryCount + 1
        val tag = fragment::class.java.simpleName

        supportFragmentManager.beginTransaction()
                .replace(R.id.container, fragment, tag)
                .addToBackStack(tag)
                .commit()

        supportFragmentManager.addOnBackStackChangedListener(object : FragmentManager.OnBackStackChangedListener {
            override fun onBackStackChanged() {
                val nowCount = supportFragmentManager.backStackEntryCount
                if (newBackStackLength != nowCount) {
                    // we don't really care if going back or forward. we already performed the logic here.
                    supportFragmentManager.removeOnBackStackChangedListener(this)

                    if (newBackStackLength > nowCount) { // user pressed back
                        supportFragmentManager.popBackStack()
                    }
                }
            }
        })
    }
}
