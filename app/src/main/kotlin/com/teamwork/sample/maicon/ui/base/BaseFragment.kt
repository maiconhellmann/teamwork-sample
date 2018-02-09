package com.teamwork.sample.maicon.ui.base

import android.content.Intent
import android.os.Bundle
import android.support.annotation.CallSuper
import android.support.v4.app.Fragment
import com.teamwork.sample.maicon.SampleApplication
import com.teamwork.sample.maicon.injection.component.ConfigPersistentComponent
import com.teamwork.sample.maicon.injection.module.PresenterModule
import timber.log.Timber
import java.util.concurrent.atomic.AtomicLong


open class BaseFragment: Fragment(){
    lateinit var baseActivity: BaseActivity

    companion object {
        @JvmStatic private val KEY_FRAGMENT_ID = "KEY_FRAGMENT_ID"
        @JvmStatic private val NEXT_ID = AtomicLong(0)
        @JvmStatic private val componentsMap = HashMap<Long, ConfigPersistentComponent>()
    }

    private var fragmentId: Long = 0
    lateinit var component: ConfigPersistentComponent
        get

    @Suppress("UsePropertyAccessSyntax")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Create the ActivityComponent and reuses cached ConfigPersistentComponent if this is
        // being called startAnimations a configuration change.
        fragmentId = savedInstanceState?.getLong(BaseFragment.KEY_FRAGMENT_ID) ?: BaseFragment.NEXT_ID.getAndIncrement()

        if (BaseFragment.componentsMap[fragmentId] != null)
            Timber.i("Reusing ConfigPersistentComponent fragmentId = $fragmentId ${this::class.java.simpleName}")


        component = BaseFragment.componentsMap.getOrPut(fragmentId, {
            Timber.i("Creating new ConfigPersistentComponent fragmentId=$fragmentId ${this::class.java.simpleName}")
            (context?.applicationContext as SampleApplication).applicationComponent.plus(PresenterModule())
        })
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putLong(BaseFragment.KEY_FRAGMENT_ID, fragmentId)
    }

    override fun onDestroy() {
        if (activity?.isChangingConfigurations != true) {
            Timber.i("Clearing ConfigPersistentComponent fragmentId=$fragmentId ${this::class.java.simpleName}")
            BaseFragment.componentsMap.remove(fragmentId)
            getPresenter()?.detachView()
        }

        super.onDestroy()
    }

    @CallSuper
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        if(activity is BaseActivity){
            baseActivity = activity as BaseActivity
        }else{
            throw Throwable("Fragment doesn't implements ${BaseActivity::javaClass.name}")
        }
    }

    open fun showProgressIndicator(){
        baseActivity.showProgressIndicator()
    }

    open fun hideProgressIndicator(){
        baseActivity.hideProgressIndicator()
    }

    open fun showError(error: String){
        baseActivity.showError(error)
    }

    open fun showError(t: Throwable){
        baseActivity.showError(t)
    }

    fun finish(){
        activity?.finish()
    }

    fun setResult(resultCode: Int, data: Intent){
        activity?.setResult(resultCode, data)
    }

    open fun getPresenter(): BasePresenter<out MvpView>? = null

    fun replaceFragment(fragment: BaseFragment, addToBackStack: Boolean?= false){
        baseActivity.replaceFragment(fragment, addToBackStack)
    }
}