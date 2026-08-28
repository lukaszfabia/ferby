package com.lukaszfabia.ferby.common.string

import platform.Foundation.NSBundle

fun translate(key: String): String = NSBundle.mainBundle.localizedStringForKey(key, value = key, table = null)
