package com.teamwork.sample.maicon.util.extension

import rx.Subscription
import rx.subscriptions.CompositeSubscription

fun Subscription.addTo(compositeSubscription: CompositeSubscription) {
    compositeSubscription.add(this)
}
