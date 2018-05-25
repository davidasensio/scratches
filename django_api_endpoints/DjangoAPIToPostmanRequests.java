import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class for read and parse documentation of a Django API in order to
 * generate all postman requests. With this kind of text generated it is possible
 * to copy and paste it into a postman collection file and import it to get all
 * requests autogenerated
 */
class DjangoAPIToPostmanRequests {
    final static String API_URL = "http://nuubo-api.develapps.es/";
    final static String API_DOCS_URL = "http://nuubo-api.develapps.es/doc/_sources/";
    final static String API_DOCS_EXTENSION = ".txt";

    //Postman vars
    public static final String ENV_API_URL = "{{URL}}";
    public static final String ENV_DOCTOR_TOKEN = "{{doctor_token}}";
    public static final String ENV_USER_TOKEN = "{{user_token}}";

    static Map<String, String> mapUrlContent = new HashMap<>();
    static List<PostmanRequest> listPostmanRequests = new ArrayList<>();

    //Add an event field containing a test that checks that the response is OK (Code 200) and the body contains an empty string (always true)
    private static boolean addDefaultTest = true;

    public static void main(String[] args) throws Exception {

        //Generate postman requests
        generatePostmanRequests();
    }

    public static void generatePostmanRequests() {
        String[] docUrls = getTargetDocUrls();
        for (int i = 0; i < docUrls.length; i++) {
            final String docUrl = docUrls[i];
            System.out.println("Reading " + docUrl + "...");
            readDoc(docUrl);
        }

        //Process, match and convert to PostmanRequests all endpoints
        processAllReadedDocs();

        //Process and match endpoints
        printGeneratedPostmanRequests();
    }

    static String[] getTargetDocUrls() {
        String[] docs = new String[]{"users", "physicians", "patients", "patient-answers", "patient-goodzone-answers", "cycles", "sessions", "session-alarms", "pathologies", "notifications", "questions"};

        String[] docUrls = new String[docs.length];
        for (int i = 0; i < docs.length; i++) {
            docUrls[i] = String.format("%s%s%s", API_DOCS_URL, docs[i], API_DOCS_EXTENSION);
        }

        return docUrls;
    }

    /**
     * Reading and caching api docs
     * @param url
     */
    static void readDoc(String url) {
        String urlContent = mapUrlContent.get(url);
        if (urlContent == null) {
            try {
                URL docUrl = new URL(url);
                BufferedReader in = new BufferedReader(new InputStreamReader(docUrl.openStream()));

                urlContent = "";
                String inputLine;
                while ((inputLine = in.readLine()) != null)
                    urlContent += inputLine + "\n";
                //System.out.println(inputLine);

                in.close();
                mapUrlContent.put(url, urlContent);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Process and parse api docs
     */
    static void processAllReadedDocs() {
        String endpoint = "\\.\\. http:(\\w+):: (.*)";
        Pattern endpointPattern = Pattern.compile(endpoint);

        Iterator<Map.Entry<String, String>> it = mapUrlContent.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, String> entry = it.next();
            final String content = entry.getValue();

            final String keySection = entry.getKey();
            System.out.println(String.format("\nParsing %s", keySection));
            Matcher endpointMatcher = endpointPattern.matcher(content);
            while (endpointMatcher.find()) {
                final String group = endpointMatcher.group();
                addPostmanRequest(group, keySection);
                System.out.println(group);
            }
        }
    }

    /**
     * Generate postman requests
     * @param endpointMatched
     * @param sectionKey
     */
    static void addPostmanRequest(String endpointMatched, String sectionKey) {
        String section = sectionKey.substring(sectionKey.lastIndexOf("/") + 1).replace(API_DOCS_EXTENSION, "");
        String method = endpointMatched.substring(8, endpointMatched.lastIndexOf("::")).toUpperCase();
        String endpoint = endpointMatched.substring(endpointMatched.lastIndexOf("::") + 4);

        String endpointName = String.format("%s %s %s", section.toUpperCase(), method, endpoint);
        PostmanRequest postmanRequest = new PostmanRequest(endpointName, method, endpoint);
        listPostmanRequests.add(postmanRequest);
    }

    /**
     * Print generated requests
     */
    static void printGeneratedPostmanRequests() {
        System.out.println("\nList of generated requests:");
        String separator = "";
        for (PostmanRequest postmanRequest : listPostmanRequests) {
            System.out.println(separator + postmanRequest.toString());
            separator = ",";
        }
    }

    /**
     * Postman request structure class
     */
    static class PostmanRequest {

        public String name;
        public String event = addDefaultTest ? "[{\"listen\": \"test\",\"script\": {\"type\": \"text/javascript\",\"exec\": [\"tests[\\\"OK (200) and Body matches string\\\"] = responseCode.code === 200 && responseBody.has(\\\"\\\");\"]}}]" : "";
        public Request request;
        public String response = "[]";

        public PostmanRequest(String name, String method, String requestEndpoint) {
            this.name = name;
            this.request = new Request();
            this.request.method = method;
            this.request.url = this.request.url + requestEndpoint;
        }

        @Override
        public String toString() {
            Gson gson = new Gson();
            String result = gson.toJson(this, PostmanRequest.class);
            result = result.replace("\",\"header\":\"[", "\",\"header\":[");
            result = result.replace("\",\"body\":\"", ",\"body\":");
            result = result.replace("\",\"description\":\"", ",\"description\":\"");
            result = result.replace("\"response\":\"[]\"", "\"response\":[]");
            result = result.replaceAll("\\\\\"", "\"");

            if (addDefaultTest) {
                result = result.replaceAll("\\\\u0026", "&");
                result = result.replace("\\u003d", "=");
                result = result.replace("\"event\":\"[", "\"event\":[");
                result = result.replace("]\",\"request\"", "],\"request\"");
                result = result.replace("\\\\\"", "\\\"");
            }
            return result;
        }

        class Request {
            public String url = ENV_API_URL;
            public String method;
            public String header = "[{\"key\":\"Authorization\",\"value\":\"Token " + ENV_DOCTOR_TOKEN + "\",\"description\":\"\"},{\"key\":\"Content-Type\",\"value\":\"application/json\",\"description\":\"\"}]";
            public String body = "{\"mode\":\"raw\",\"raw\":\"\"}";
            public String description = "";
        }
    }
}