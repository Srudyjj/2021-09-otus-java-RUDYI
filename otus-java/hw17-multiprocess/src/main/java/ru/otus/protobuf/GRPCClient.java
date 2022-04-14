package ru.otus.protobuf;

import io.grpc.ManagedChannelBuilder;
import ru.otus.protobuf.generated.Request;
import ru.otus.protobuf.generated.ServerServiceGrpc;
import ru.otus.protobuf.service.GeneratorClient;

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

        var generator = new GeneratorClient(generatedValue);
        generator.start();
        var i = 0;
        while (i <= 50) {
            int generatorCurrentValue = generator.getCurrentValue();
            System.out.println("Generator value -> " + generatorCurrentValue);
            System.out.println("Value -> " + (i + generatorCurrentValue + 1));

            i++;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
