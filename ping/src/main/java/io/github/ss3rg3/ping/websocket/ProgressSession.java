package io.github.ss3rg3.ping.websocket;

import javax.enterprise.context.ApplicationScoped;
import javax.websocket.Session;
import java.io.IOException;

@ApplicationScoped
public class ProgressSession {

    private Session session;

    public Session get() {
        return this.session;
    }

    public void set(Session session) {
        this.session = session;
    }

    public void close() {
        if (this.session != null) {
            try {
                this.session.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
