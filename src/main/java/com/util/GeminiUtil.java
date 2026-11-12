package com.util;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class GeminiUtil {

    public static String askGemini(String question) throws Exception {

        String apiKey = "PASTE YUR KEY HERE";
        String apiUrl = "https://generativelanguage.googleapis.com/v1beta/models/gemini-flash-lite-latest:generateContent";

        // 1. make the question safe for JSON
        String safeQuestion = question.replace("\\", "\\\\").replace("\"", "\\\"").replace("\n", " ");

        // 2. create the JSON request body
        String body = "{\"contents\":[{\"parts\":[{\"text\":\"Explain simply for a student: "
                + safeQuestion + "\"}]}]}";

        // 3. open connection to Gemini API
        URL url = new URL(apiUrl);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json");
        con.setRequestProperty("x-goog-api-key", apiKey);
        con.setDoOutput(true);

        // 4. send the request body
        OutputStream os = con.getOutputStream();
        os.write(body.getBytes("UTF-8"));
        os.close();

        // 5. read the response from Gemini
        int code = con.getResponseCode();
        if (code != 200) {
            throw new Exception("Gemini API error. HTTP code: " + code + " (check API key and model name)");
        }

        BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
        StringBuilder response = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            response.append(line);
        }
        reader.close();

        // 6. take the answer text out of the JSON response (using Gson)
        JsonParser parser = new JsonParser();
        JsonObject json = parser.parse(response.toString()).getAsJsonObject();
        JsonArray candidates = json.getAsJsonArray("candidates");
        JsonObject firstCandidate = candidates.get(0).getAsJsonObject();
        JsonObject content = firstCandidate.getAsJsonObject("content");
        JsonArray parts = content.getAsJsonArray("parts");
        String answer = parts.get(0).getAsJsonObject().get("text").getAsString();

        return answer;
    }
}