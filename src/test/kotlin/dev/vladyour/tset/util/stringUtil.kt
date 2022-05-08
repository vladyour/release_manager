package dev.vladyour.tset.util

import org.apache.commons.lang3.RandomStringUtils

val randomString: String
    get() = RandomStringUtils.random(10, true, false)
