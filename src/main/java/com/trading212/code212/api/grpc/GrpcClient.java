//package com.trading212.code212.api.grpc;
//
//import com.trading212.code212.api.grpc.test.PerformIOMatchRequest;
//import com.trading212.code212.api.grpc.test.PerformIOMatchResponse;
//import com.trading212.code212.api.grpc.test.ServerOuterClass;
//import io.grpc.ManagedChannel;
//import io.grpc.ManagedChannelBuilder;
//
//
//public class GrpcClient {
//
//    public void performIOMatch() {
//
//        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 50051)
//                .usePlaintext()
//                .build();
//
//        ServerGrpc.ServerBlockingStub stub = ServerGrpc.newBlockingStub(channel);
//
//        ServerOuterClass.PerformIOMatchRequest performIOMatchRequest = ServerOuterClass.PerformIOMatchRequest.newBuilder()
//                .setLanguage("Java")
//                .setSource("public class HelloWorld { public static void main(String[] args) { System.out.println(\"Hello, World\"); }}")
//                .build();
//
//        ServerOuterClass.PerformIOMatchResponse performIOMatchResponse;
//
//        try {
//            performIOMatchResponse = stub.performIOMatch(performIOMatchRequest);
//            System.out.println(performIOMatchResponse);
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            channel.shutdown();
//        }
//    }
//}
