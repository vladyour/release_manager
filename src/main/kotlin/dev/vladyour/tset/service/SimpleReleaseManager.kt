package dev.vladyour.tset.service

import dev.vladyour.tset.model.ServiceVersion
import dev.vladyour.tset.repository.VersionRepository
import org.springframework.stereotype.Service
import java.util.concurrent.locks.ReentrantLock

@Service
class SimpleReleaseManager(
    private val versionRepository: VersionRepository
): ReleaseManager {
    private val lock = ReentrantLock(true)

    override fun deploy(serviceVersion: ServiceVersion): Int {
        lock.lock()
        return try {
            val (name, version) = serviceVersion
            val oldVersion = versionRepository.getServiceVersion(name)
            if (oldVersion == null || version > oldVersion) {
                versionRepository.addServiceVersion(serviceVersion)
            } else {
                versionRepository.getSystemVersion()
            }
        } finally {
            lock.unlock()
        }
    }

    override fun getServices(systemVersion: Int): List<ServiceVersion> {
        return versionRepository.getServiceVersions(systemVersion)
    }
}
