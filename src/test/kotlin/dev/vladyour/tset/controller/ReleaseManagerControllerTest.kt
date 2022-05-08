package dev.vladyour.tset.controller

import dev.vladyour.tset.model.ServiceVersion
import dev.vladyour.tset.service.ReleaseManager
import dev.vladyour.tset.util.randomString
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import kotlin.random.Random

internal class ReleaseManagerControllerTest {
    private val mockedReleaseManager = mock(ReleaseManager::class.java)

    @Test
    fun `should throw an exception when service name is blank`() {
        val controller = ReleaseManagerController(mockedReleaseManager)
        Assertions.assertThatThrownBy {
            val serviceVersion = ServiceVersion("", Random.nextInt(1, 100))
            controller.deploy(serviceVersion)
        }.isInstanceOf(IllegalArgumentException::class.java)
    }

    @Test
    fun `should throw an exception when service version is less than 1`() {
        val controller = ReleaseManagerController(mockedReleaseManager)
        Assertions.assertThatThrownBy {
            val serviceVersion = ServiceVersion(randomString, Random.nextInt(0))
            controller.deploy(serviceVersion)
        }.isInstanceOf(IllegalArgumentException::class.java)
    }

    @Test
    fun `should throw an exception when system version is less than 1`() {
        val controller = ReleaseManagerController(mockedReleaseManager)
        Assertions.assertThatThrownBy {
            controller.services(Random.nextInt(0))
        }.isInstanceOf(IllegalArgumentException::class.java)
    }
}
