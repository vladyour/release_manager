package dev.vladyour.tset.service

import dev.vladyour.tset.model.ServiceVersion

interface ReleaseManager {
    fun deploy(serviceVersion: ServiceVersion): Int
    fun getServices(systemVersion: Int): List<ServiceVersion>
}
