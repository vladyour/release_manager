package dev.vladyour.tset.service

import dev.vladyour.tset.model.ServiceVersion
import dev.vladyour.tset.repository.InMemoryVersionRepository
import dev.vladyour.tset.util.randomString
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.random.Random

internal class SimpleReleaseManagerTest {
    private lateinit var releaseManager: ReleaseManager

    @BeforeEach
    fun resetReleaseManager() {
        releaseManager = SimpleReleaseManager(InMemoryVersionRepository())
    }

    @Test
    fun `should return 1 when the first service was deployed`() {
        val expectedSystemVersion = 1
        val serviceVersion = ServiceVersion(randomString, 1)

        val actualSystemVersion = releaseManager.deploy(serviceVersion)

        Assertions.assertThat(actualSystemVersion).isEqualTo(expectedSystemVersion)
    }

    @Test
    fun `should not change the system version if no service was updated`() {
        val serviceVersion = ServiceVersion(randomString, 1)

        val expectedSystemVersion = releaseManager.deploy(serviceVersion)
        val actualSystemVersion = releaseManager.deploy(serviceVersion)

        Assertions.assertThat(actualSystemVersion).isEqualTo(expectedSystemVersion)
    }

    @Test
    fun `should increase the system version when a service was updated`() {
        val serviceVersion = ServiceVersion(randomString, 1)

        val firstSystemVersion = releaseManager.deploy(serviceVersion)
        val expectedSystemVersion = firstSystemVersion + 1
        val actualSystemVersion = releaseManager.deploy(serviceVersion.copy(version = serviceVersion.version + 1))

        Assertions.assertThat(actualSystemVersion).isEqualTo(expectedSystemVersion)
    }

    @Test
    fun `should increase the system version when a new service was deployed`() {
        val serviceVersion1 = ServiceVersion(randomString, 1)
        val serviceVersion2 = ServiceVersion(randomString, 1)

        val firstSystemVersion = releaseManager.deploy(serviceVersion1)
        val expectedSystemVersion = firstSystemVersion + 1
        val actualSystemVersion = releaseManager.deploy(serviceVersion2)

        Assertions.assertThat(actualSystemVersion).isEqualTo(expectedSystemVersion)
    }

    @Test
    fun `should not change the system number if a new service was deployed with the same version`() {
        val serviceVersion1 = ServiceVersion(randomString, 1)
        val serviceVersion2 = ServiceVersion(randomString, 1)

        val firstSystemVersion = releaseManager.deploy(serviceVersion1)
        val expectedSystemVersion = firstSystemVersion + 1
        releaseManager.deploy(serviceVersion2)
        val actualSystemVersion = releaseManager.deploy(serviceVersion2)

        Assertions.assertThat(actualSystemVersion).isEqualTo(expectedSystemVersion)
    }

    @Test
    fun `should return empty list if there is no a given system version`() {
        val services = releaseManager.getServices(Random.nextInt(1, 100))
        Assertions.assertThat(services).isEmpty()
    }

    @Test
    fun `should return all deployed services`() {
        val serviceVersion1 = ServiceVersion(randomString, 1)
        val serviceVersion2 = ServiceVersion(randomString, 1)

        releaseManager.deploy(serviceVersion1)
        val lastSystemVersion = releaseManager.deploy(serviceVersion2)

        val actualServices = releaseManager.getServices(lastSystemVersion)
        Assertions.assertThat(actualServices).containsExactlyInAnyOrder(serviceVersion1, serviceVersion2)
    }

    @Test
    fun `should return all deployed services with the last versions`() {
        val serviceVersion11 = ServiceVersion(randomString, 1)
        releaseManager.deploy(serviceVersion11)

        val serviceVersion21 = ServiceVersion(randomString, 1)
        releaseManager.deploy(serviceVersion21)

        val serviceVersion12 = serviceVersion11.copy(version = 2)
        releaseManager.deploy(serviceVersion12)

        val serviceVersion13 = serviceVersion11.copy(version = 3)
        releaseManager.deploy(serviceVersion13)

        val serviceVersion22 = serviceVersion21.copy(version = 2)
        val lastSystemVersion = releaseManager.deploy(serviceVersion22)

        val actualServices = releaseManager.getServices(lastSystemVersion)
        Assertions.assertThat(actualServices).containsExactlyInAnyOrder(serviceVersion13, serviceVersion22)
    }
}
