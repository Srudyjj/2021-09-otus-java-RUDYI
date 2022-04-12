package ru.otus.protobuf;

import io.grpc.ManagedChannelBuilder;
import ru.otus.protobuf.generated.Request;
import ru.otus.protobuf.generated.ServerServiceGrpc;

public class GRPCClient {

    private static final String SERVER_HOST = "localhost";
    private static final int SERVER_PORT = 8190;

    public static void main(String[] args) throws InterruptedException {
        var channel = ManagedChannelBuilder.forAddress(SERVER_HOST, SERVER_PORT)
                .usePlaintext()
                .build();

        var stub = ServerServiceGrpc.newBlockingStub(channel);
        var request = Request.newBuilder()
                .setFirstValue(0)
                .setLastValue(30)
                .build();
        var generatedValue = stub.generateValues(request);

        generatedValue.forEachRemaining(response -> {
            System.out.println(response.getServerValue());
        });
    }
}
