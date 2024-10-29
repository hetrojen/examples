package com.example.demo.instana1;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class RestRoute extends RouteBuilder{
    @Override
    public void configure() throws Exception {
        restConfiguration()
            .component("servlet").port("8081");
   

        rest("/api/currency")
            .get("/{code}")
             .consumes("text/plain")
            .produces("application/json")
            .to("direct:getCurrencyRate");

        from("direct:getCurrencyRate")
            .setHeader("code", simple("${header.code}")).log("YESSS")
            .toD("https://api.exchangerate-api.com/v4/latest/${header.code}?bridgeEndpoint=true")
            .convertBodyTo(String.class);
    }

}
