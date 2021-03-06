package rip.coan.applemusictest.util;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.AllArgsConstructor;
import lombok.Data;
import okhttp3.Request;
import okhttp3.Response;
import rip.coan.applemusictest.AMTest;

import java.util.Objects;

@Data @AllArgsConstructor
public class RequestCreator {

    private boolean successful;
    private String errorMessage;
    private String response;
    Request.Builder requestBuilder;

    public static RequestCreator get(String endpoint) {
        Request.Builder builder = new Request.Builder();
        builder.get();
        builder.url(endpoint);

        try {
            Response response = AMTest.getOkHttpClient().newCall(builder.build()).execute();
            if (response.code() >= 500) {
                return new RequestCreator(false, "Failed to contact endpoint", null, builder);
            }

            return new RequestCreator(true, null, Objects.requireNonNull(response.body()).string(), builder);
        } catch (Exception e) {
            e.printStackTrace();
            return new RequestCreator(false, "Could not connect to endpoint", null, builder);
        }
    }

    public JsonObject asJSONObject() {
        return JsonParser.parseString(this.response).getAsJsonObject();
    }

}
