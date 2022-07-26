package io.github.ss3rg3.pong.utils;

import io.smallrye.mutiny.Uni;

import javax.ws.rs.core.Response;

public class UniResponse {
    private UniResponse() {
    }

    public static Uni<Response> ok() {
        return Uni.createFrom().item(
                Response.ok()
                        .build());
    }

    public static Uni<Response> ok(Object body) {
        return Uni.createFrom().item(
                Response.ok(body)
                        .build());
    }

    public static Uni<Response> as(int statusCode, Object body) {
        return Uni.createFrom().item(
                Response.status(statusCode)
                        .entity(body)
                        .build());
    }



}
