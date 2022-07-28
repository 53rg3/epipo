package io.github.ss3rg3.ping.models;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class Status {

    public int pongCount;
    public Health health;

    public Status(int pongCount, Health health) {
        this.pongCount = pongCount;
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
