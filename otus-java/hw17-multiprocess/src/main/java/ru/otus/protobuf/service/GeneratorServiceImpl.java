package ru.otus.protobuf.service;

import io.grpc.stub.StreamObserver;
import ru.otus.protobuf.generated.Request;
import ru.otus.protobuf.generated.Response;

import static ru.otus.protobuf.generated.ServerServiceGrpc.*;

public class GeneratorServiceImpl extends ServerServiceImplBase {

    @Override
    public void generateValues(Request request, StreamObserver<Response> responseObserver) {
        int firstValue = request.getFirstValue();
        int lastValue = request.getLastValue();
        int i = firstValue;
        while (i <= lastValue) {
            System.out.println(i);
            responseObserver.onNext(intToResponse(i));
            i++;
            sleep();
        }
        responseObserver.onCompleted();
    }

    private void sleep() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static Response intToResponse(int i) {
        return Response.newBuilder()
                .setServerValue(i)
                .build();
    }
}
