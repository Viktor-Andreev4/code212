package com.trading212.code212.core;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.trading212.code212.core.models.SubmissionRequest;
import com.trading212.code212.core.models.SubmissionResponse;
import com.trading212.code212.core.models.TokenResponse;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;

@Service
public class JudgeOImpl implements JudgeOApi {

    private final ObjectMapper mapper;
    private final String tokenSeparator;
    private final Gson gson;

    public JudgeOImpl(Gson gson) {
        this.gson = gson;
        this.mapper = new ObjectMapper();
        tokenSeparator = ",";
    }


    @Override
    public List<TokenResponse> executeBatchCode(List<SubmissionRequest> submissions) {
        String requestBody = generateSubmissionJson(submissions);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://judge0-ce.p.rapidapi.com/submissions/batch?base64_encoded=true"))
                .header("content-type", "application/json")
                .header("Content-Type", "application/json")
                .header("X-RapidAPI-Key", "bdeda95a1dmsh52a5dcfa61ac38cp1095fdjsn5d333499eee4")
                .header("X-RapidAPI-Host", "judge0-ce.p.rapidapi.com")
                .method("POST", HttpRequest
                        .BodyPublishers
                        .ofString(requestBody))
                .build();
        HttpResponse<String> response = null;
        try {
            response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }

        return parseBodyToTokenResponse(response.body());
    }

    private String generateSubmissionJson(List<SubmissionRequest> submissions) {
        Map<String, Object> body = new HashMap<>();
        body.put("submissions", submissions);
        String requestBody = null;
        try {
            requestBody = mapper.writeValueAsString(body);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return requestBody;
    }


    private List<TokenResponse> parseBodyToTokenResponse(String body) {
        try {
            return mapper.readValue(
                    body,
                    new TypeReference<List<TokenResponse>>() {
            });
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<SubmissionResponse> getBatchCodeResponse(List<TokenResponse> tokens) {
        String separatedTokens = concatenateTokenValues(tokens, tokenSeparator);
//        HttpRequest request = HttpRequest.newBuilder()
//                .uri(URI.create("https://judge0-ce.p.rapidapi.com/submissions/batch?tokens=" + separatedTokens + "&fields=*"))
//                .header("X-RapidAPI-Key", "bdeda95a1dmsh52a5dcfa61ac38cp1095fdjsn5d333499eee4")
//                .header("X-RapidAPI-Host", "judge0-ce.p.rapidapi.com")
//                .method("GET", HttpRequest.BodyPublishers.noBody())
//                .build();
//        HttpResponse<String> response = null;
//        try {
//            response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
//        } catch (IOException | InterruptedException e) {
//            throw new RuntimeException(e);
//        }

        String body = """
                {"submissions":[{"source_code":"import java.util.Scanner;\n\n public class Main {\n    public static void main(String[] args) {\n        // Create a Scanner object\n        Scanner myScanner = new Scanner(System.in);\n\n        // Ask the user for input\n        System.out.println(\"Enter something:\");\n\n        // Read the user's input\n        String userInput = myScanner.nextLine();\n\n        // Close the scanner\n        myScanner.close();\n\n        // Print the user's input\n        System.out.println(\"You entered: \" + userInput);\n    }\n}","language_id":62,"stdin":"1","expected_output":null,"stdout":"Enter something:\nYou entered: 1\n","status_id":3,"created_at":"2023-08-04T10:52:24.135Z","finished_at":"2023-08-04T10:52:27.162Z","time":"0.227","memory":14148,"stderr":null,"token":"f00d8b0f-819b-4263-bdff-b558ac1ed305","number_of_runs":1,"cpu_time_limit":"5.0","cpu_extra_time":"1.0","wall_time_limit":"10.0","memory_limit":128000,"stack_limit":64000,"max_processes_and_or_threads":60,"enable_per_process_and_thread_time_limit":false,"enable_per_process_and_thread_memory_limit":false,"max_file_size":1024,"compile_output":null,"exit_code":0,"exit_signal":null,"message":null,"wall_time":"0.252","compiler_options":null,"command_line_arguments":null,"redirect_stderr_to_stdout":false,"callback_url":null,"additional_files":null,"enable_network":false,"status":{"id":3,"description":"Accepted"},"language":{"id":62,"name":"Java (OpenJDK 13.0.1)"}},{"source_code":"import java.util.Scanner;\n\npublic class Main {\n    public static void main(String[] args) {\n        // Create a Scanner object\n        Scanner myScanner = new Scanner(System.in);\n\n        // Ask the user for input\n        System.out.println(\"Enter something:\");\n\n        // Read the user's input\n        String userInput = myScanner.nextLine();\n\n        // Close the scanner\n        myScanner.close();\n\n        // Print the user's input\n        System.out.println(\"You entered: \" + userInput);\n    }\n}","language_id":62,"stdin":"2","expected_output":null,"stdout":"Enter something:\nYou entered: 2\n","status_id":3,"created_at":"2023-08-04T10:52:24.148Z","finished_at":"2023-08-04T10:52:27.294Z","time":"0.223","memory":14016,"stderr":null,"token":"d67cc9a1-7fda-4b53-b9a3-30cc95519f4c","number_of_runs":1,"cpu_time_limit":"5.0","cpu_extra_time":"1.0","wall_time_limit":"10.0","memory_limit":128000,"stack_limit":64000,"max_processes_and_or_threads":60,"enable_per_process_and_thread_time_limit":false,"enable_per_process_and_thread_memory_limit":false,"max_file_size":1024,"compile_output":null,"exit_code":0,"exit_signal":null,"message":null,"wall_time":"0.267","compiler_options":null,"command_line_arguments":null,"redirect_stderr_to_stdout":false,"callback_url":null,"additional_files":null,"enable_network":false,"status":{"id":3,"description":"Accepted"},"language":{"id":62,"name":"Java (OpenJDK 13.0.1)"}},{"source_code":"import java.util.Scanner;\n\npublic class Main {\n    public static void main(String[] args) {\n        // Create a Scanner object\n        Scanner myScanner = new Scanner(System.in);\n\n        // Ask the user for input\n        System.out.println(\"Enter something:\");\n\n        // Read the user's input\n        String userInput = myScanner.nextLine();\n\n        // Close the scanner\n        myScanner.close();\n\n        // Print the user's input\n        System.out.println(\"You entered: \" + userInput);\n    }\n}","language_id":62,"stdin":"3","expected_output":null,"stdout":"Enter something:\nYou entered: 3\n","status_id":3,"created_at":"2023-08-04T10:52:24.158Z","finished_at":"2023-08-04T10:52:27.193Z","time":"0.239","memory":13880,"stderr":null,"token":"e2577a9e-c363-44e9-b6e1-6fdac8f2508f","number_of_runs":1,"cpu_time_limit":"5.0","cpu_extra_time":"1.0","wall_time_limit":"10.0","memory_limit":128000,"stack_limit":64000,"max_processes_and_or_threads":60,"enable_per_process_and_thread_time_limit":false,"enable_per_process_and_thread_memory_limit":false,"max_file_size":1024,"compile_output":null,"exit_code":0,"exit_signal":null,"message":null,"wall_time":"0.321","compiler_options":null,"command_line_arguments":null,"redirect_stderr_to_stdout":false,"callback_url":null,"additional_files":null,"enable_network":false,"status":{"id":3,"description":"Accepted"},"language":{"id":62,"name":"Java (OpenJDK 13.0.1)"}}]}
                """;
        List<SubmissionResponse> submissionResponses = parseJsonToSubmissionResponse(body);
        System.out.println(submissionResponses);
        return submissionResponses;
    }

    private List<SubmissionResponse> parseJsonToSubmissionResponse(String json) {
        Type submissionResponseType = new TypeToken<List<SubmissionResponse>>(){}.getType();
        return gson.fromJson(json, submissionResponseType);
    }
    private String concatenateTokenValues(List<TokenResponse> tokens, String separator) {
        return String.join(separator, tokens.stream()
                .map(TokenResponse::getToken)
                .toArray(String[]::new));
    }



}
