package dev.vladyour.tset

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class TsetApplication

fun main(args: Array<String>) {
    runApplication<TsetApplication>(*args)
}
