package dev.vladyour.tset.repository

import dev.vladyour.tset.model.ServiceVersion

interface VersionRepository {
    fun getServiceVersion(name: String): Int?
    fun addServiceVersion(serviceVersion: ServiceVersion): Int
    fun getSystemVersion(): Int
    fun getServiceVersions(systemVersionNumber: Int): List<ServiceVersion>
}
