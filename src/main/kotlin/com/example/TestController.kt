package com.example

import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.reactivex.Flowable
import io.reactivex.schedulers.Schedulers

@Controller("/test")
class TestController {

    @Get("/")
    fun test () : Flowable<Int> {
        return Flowable.range(1, 10)
            .map {
                println("Start on thread ${Thread.currentThread().name}")
                println("Item: $it")
                it
            }
            .parallel()
            .runOn(Schedulers.io())
            .map { n ->
                println("Processing number $n on thread ${Thread.currentThread().name}")
                Thread.sleep(1000)
                n * 2
            }
            .sequential()
    }
}
