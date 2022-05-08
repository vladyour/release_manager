package dev.vladyour.tset.controller

import dev.vladyour.tset.model.ServiceVersion
import dev.vladyour.tset.service.ReleaseManager
import org.springframework.web.bind.annotation.*

@RestController
class ReleaseManagerController(
    private val releaseManager: ReleaseManager
) {
    @GetMapping("/services")
    fun services(@RequestParam systemVersion: Int): List<ServiceVersion> {
        if (systemVersion < 1) throw IllegalArgumentException("Parameter 'systemVersion' should be greater that zero")
        return releaseManager.getServices(systemVersion)
    }

    @PostMapping("/deploy")
    fun deploy(@RequestBody serviceVersion: ServiceVersion): Int {
        if (serviceVersion.name.isBlank()) throw IllegalArgumentException("Name should not be blank")
        if (serviceVersion.version <= 0) throw IllegalArgumentException("Version should be greater that zero")
        return releaseManager.deploy(serviceVersion)
    }
}
