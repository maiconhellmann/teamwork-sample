package com.teamwork.sample.maicon.ui.base

/**
 * Base interface that any class that wants to act as a View in the MVP (AgendamentoData View Presenter)
 * pattern must implement. Generally this interface will be extended by a more specific interface
 * that then usually will be implemented by an Activity or Fragment.
 */
interface MvpView{
    fun showProgressIndicator()
    fun hideProgressIndicator()
    fun showError(error: String)
    fun showError(t: Throwable)
}
