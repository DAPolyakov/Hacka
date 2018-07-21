package ru.yandexschool.hackathon.mvp


interface MvpPresenter<in V : MvpView> {
    fun attachView(mvpView: V)
    fun viewIsReady()
    fun detachView()
    fun destroy()
}