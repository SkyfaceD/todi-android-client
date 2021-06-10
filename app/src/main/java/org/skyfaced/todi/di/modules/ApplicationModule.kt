package org.skyfaced.todi.di.modules

import android.content.Context
import android.content.SharedPreferences
import org.koin.android.ext.koin.androidContext
import org.koin.core.scope.Scope
import org.koin.dsl.module
import org.skyfaced.todi.markdown.Markdown
import org.skyfaced.todi.markdown.MarkdownImpl
import org.skyfaced.todi.utils.SP_NAME

val applicationModule = module(createdAtStart = true) {
    single { sharedPreferences }

    single<Markdown> { MarkdownImpl(androidContext()) }
}

private val Scope.sharedPreferences
    get(): SharedPreferences {
        return androidContext().getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
    }