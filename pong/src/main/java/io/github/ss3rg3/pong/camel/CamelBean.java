package io.github.ss3rg3.pong.camel;

import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.Startup;
import org.apache.camel.CamelContext;
import org.apache.camel.FluentProducerTemplate;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

@Startup
@ApplicationScoped
public class CamelBean {

    private final CamelContext context;
    private final FluentProducerTemplate fluentProducerTemplate;
    private final ProducerTemplate producerTemplate;


    @Inject
    public CamelBean(CamelRoutes camelRoutes) {
        this.context = new DefaultCamelContext();
        this.addRoutesToContext(camelRoutes);
        this.context.start();

        this.fluentProducerTemplate = this.context.createFluentProducerTemplate();
        this.producerTemplate = this.context.createProducerTemplate();
    }

    public FluentProducerTemplate to(String endpointUri) {
        return this.fluentProducerTemplate.to(endpointUri);
    }

    public ProducerTemplate template() {
        return this.producerTemplate;
    }

    public CamelContext getContext() {
        return this.context;
    }

    void onStop(@Observes ShutdownEvent ev) {
        this.context.stop();
    }

    private void addRoutesToContext(CamelRoutes camelRoutes) {
        try {
            for (RouteBuilder routeBuilder : camelRoutes.asList()) {
                this.context.addRoutes(routeBuilder);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
