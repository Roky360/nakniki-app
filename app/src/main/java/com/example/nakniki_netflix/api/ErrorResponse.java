package com.example.nakniki_netflix.api;


import androidx.room.TypeConverters;

import com.example.nakniki_netflix.db.converters.ListConverter;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;

import retrofit2.Response;


// Define a class to match the error response format
public class ErrorResponse {
    private String error;  // or "errors" key depending on the response
    @TypeConverters(ListConverter.class)
    private List<String> errors;  // Use this if the key is "errors"

    public String getError() {
        return error != null ? error : String.join("\n", errors);
    }

    // Utility method to extract error message from response body
    public static String getErrorMessage(Response<?> response) {
        try {
            // Check if errorBody is not null
            if (response.errorBody() != null) {
                // Parse the error response
                String errorResponse = response.errorBody().string();
                Gson gson = new Gson();
                ErrorResponse error = gson.fromJson(errorResponse, ErrorResponse.class);

                // Return the error message depending on the structure of the response (either "error" or "errors")
                return error.getError();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Unknown error occurred";  // Return a fallback message if parsing fails
    }
}