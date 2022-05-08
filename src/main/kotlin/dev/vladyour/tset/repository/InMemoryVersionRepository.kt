package dev.vladyour.tset.repository

import dev.vladyour.tset.model.ServiceVersion
import org.springframework.stereotype.Repository

@Repository
class InMemoryVersionRepository: VersionRepository {
    private val serviceVersions = mutableListOf<ServiceVersion>()

    override fun getServiceVersion(name: String) = serviceVersions
        .lastOrNull { it.name == name }?.version

    override fun addServiceVersion(serviceVersion: ServiceVersion): Int {
        serviceVersions.add(serviceVersion)
        return serviceVersions.size
    }

    override fun getSystemVersion() = serviceVersions.size

    override fun getServiceVersions(systemVersionNumber: Int): List<ServiceVersion> {
        return if (serviceVersions.size < systemVersionNumber) {
            emptyList()
        } else {
            serviceVersions
                .subList(0, systemVersionNumber)
                .groupBy { it.name }
                .map { (name, serviceVersions) ->
                    val version = serviceVersions.maxOf { it.version }
                    ServiceVersion(name, version)
                }
        }
    }
}
