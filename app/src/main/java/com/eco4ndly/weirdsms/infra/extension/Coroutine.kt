package com.eco4ndly.weirdsms.infra.extension

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

/**
 * A Sayan Porya code on 2020-01-18
 */
fun launchUI(block: suspend CoroutineScope.() -> Unit): Job =
    MainScope().launch(block = block)