package helpers;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

public class TestHelpers {


    public static enum HttpMethodRequests {
        POST("POST"),
        GET("GET"),
        DELETE("DELETE");

        private String method;
        HttpMethodRequests(String method) {
            this.method = method;
        }

        public String getMethod(){
            return this.method;
        }
    }

    public static HttpResponse<String> sendRequest(String urlString, HttpMethodRequests requestMethod, String jsonBody, List<String> headers) {
        if(headers == null) {
            headers = new ArrayList<>(4);
        }
        if(!headers.contains("Content-Type")) {
            headers.add("Content-Type");
            headers.add("application/json");
            headers.add("Accept");
            headers.add("application/json");
        }

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request;
        HttpRequest.Builder builder = HttpRequest.newBuilder();
        builder.uri(URI.create(urlString));
        if(headers != null) {
            for(int i = 0; i < headers.size()-1; i+=2) {
                builder.header(headers.get(i), headers.get(i+1));
            }
        }

        //TODO: cannot use one liner when want to pass a specific unknown quantity of headers, must write out
        switch(requestMethod) {
            case POST ->
                    request = jsonBody == null? null :
                        builder.POST(HttpRequest.BodyPublishers.ofString(jsonBody)).build();
//            case DELETE ->
//                    request =  builder.DELETE().build();
            default ->
                    request =  builder.GET().build();
        }

        try {
            if(request == null) return null;
            return client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch(Exception e) {
            System.err.println("Unable to handle request to " + urlString + "Details: " + e.toString());
        }
        return null;
    }

//
//    public static void sendRequest(String urlString, String requestMethod, String jsonBody, String jsonHeader){
//        try {
//            URL url = new URL(urlString);
//            HttpURLConnection con = (HttpURLConnection)url.openConnection();
//            con.setRequestMethod(requestMethod);
//
//            //TODO add header stuff
//            con.addRequestProperty("Content-Type", "application/json");
//
//            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"));
//            StringBuilder response = new StringBuilder();
//            String responseLine = null;
//            while ((responseLine = br.readLine()) != null) {
//                response.append(responseLine.trim());
//            }
//            System.out.println(response.toString());
//        } catch (Exception e){
//            //TODO format the string better
//            System.err.println("Unable to handle request to " + urlString + "Details: " + e.toString());
//        }
//
//    }

}
