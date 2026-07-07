package org.catrobat.aitutor

import android.content.ContentProvider
import org.robolectric.Robolectric

fun initComposeResourcesContext() {
    val providerClass =
        Class.forName("org.jetbrains.compose.resources.AndroidContextProvider")
            as Class<out ContentProvider>
    Robolectric.buildContentProvider(providerClass).create()
}
