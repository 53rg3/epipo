package io.github.ss3rg3.ping.metrics;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tags;
import io.micrometer.core.instrument.Timer;
import io.quarkus.runtime.Startup;
import io.quarkus.scheduler.Scheduled;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@Startup
@ApplicationScoped
public class Meters {

    private final MeterRegistry registry;
    private final Counter pingsSent;
    private final Counter pongsReceived;
    private double lastPongCount = 0;
    private double pongPerSecond = 0;

    @Inject
    public Meters(MeterRegistry registry) {
        this.registry = registry;
        this.pingsSent = registry.counter("pings.sent", Tags.empty());
        this.pongsReceived = registry.counter("pongs.received", Tags.empty());
    }

    @Scheduled(every = "1s")
    void measureTransactionsPerSecond() {
        double pongsReceivedCount = this.pongsReceived.count();
        this.pongPerSecond = pongsReceivedCount - this.lastPongCount;
        this.lastPongCount = pongsReceivedCount;
    }

    public Counter getPingsSent() {
        return this.pingsSent;
    }

    public Counter getPongsReceived() {
        return this.pongsReceived;
    }

    public MeterRegistry getRegistry() {
        return this.registry;
    }

    public double getPongPerSecond() {
        return this.pongPerSecond;
    }
}
