package io.github.ss3rg3.ping.models;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class Status {

    public double pingsSent;
    public double pongsReceived;
    public double pongsPerSecond;
    public Health health;

    public Status(double pingsSent, double pongsReceived, double pongsPerSecond, Health health) {
        this.pingsSent = pingsSent;
        this.pongsReceived = pongsReceived;
        this.pongsPerSecond = pongsPerSecond;
        this.health = health;
    }

    public static class Health {

        public String status;
        public List<Check> checks;

        public static Health asEmpty() {
            Health health = new Health();
            health.status = "NOT INITIALIZED";
            health.checks = Collections.emptyList();
            return health;
        }
    }

    public static class Check {

        public String name;
        public String status;
        public Map<String, Object> data;

    }

}
