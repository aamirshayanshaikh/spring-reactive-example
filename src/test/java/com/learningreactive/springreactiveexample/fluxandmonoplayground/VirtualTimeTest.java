package com.learningreactive.springreactiveexample.fluxandmonoplayground;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;
import reactor.test.scheduler.VirtualTimeScheduler;

import java.time.Duration;

public class VirtualTimeTest {

    //https://github.com/dilipsundarraj1/Teach-ReactiveSpring
    //https://github.com/Gypsophilax/book/blob/Master/Reactive%20Spring.pdf
    //https://github.com/code-with-dilip/spring-webclient

    @Test
    public void testingWithoutVirtualTime(){

        Flux<Long> longFlux = Flux.interval(Duration.ofSeconds(1))
                .take(3);

        StepVerifier.create(longFlux.log())
                .expectSubscription()
                .expectNext(0l,1l,2l)
                .verifyComplete();
    }

    @Test
    public void testingWithVirtualTime(){

        VirtualTimeScheduler.getOrSet();

        Flux<Long> longFlux = Flux.interval(Duration.ofSeconds(1))
                .take(3);

        StepVerifier.withVirtualTime(() -> longFlux.log())
                .expectSubscription()
                .thenAwait(Duration.ofSeconds(3))
                .expectNext(0l,1l,2l)
                .verifyComplete();

    }
}
