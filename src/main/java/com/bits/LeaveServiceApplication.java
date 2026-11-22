
package com.bits;

import com.sun.net.httpserver.HttpServer;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;

public class LeaveServiceApplication {

    public static void main(String[] args) throws Exception {

        String environment =
            System.getenv().getOrDefault("APP_ENV", "local");

int port = Integer.parseInt(
    System.getenv().getOrDefault("PORT", "8080")
);

HttpServer server = HttpServer.create(
    new InetSocketAddress(port), 0
);

        server.createContext("/", exchange -> {

            String response =
                "{\"service\":\"Employee Leave Service\","
                + "\"environment\":\"" + environment + "\","
                + "\"status\":\"UP\"}";

            byte[] bytes =
                response.getBytes(StandardCharsets.UTF_8);

            exchange.getResponseHeaders().set(
                "Content-Type", "application/json"
            );

            exchange.sendResponseHeaders(200, bytes.length);

            try (OutputStream os = exchange.getResponseBody()) {
                os.write(bytes);
            }
        });

        server.start();

        System.out.println(
            "Employee Leave Service started in "
            + environment + " environment on port "+port
        );
    }
}
