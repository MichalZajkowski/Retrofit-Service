package retrofit;

import com.google.gson.*;
import org.json.JSONArray;
import org.junit.jupiter.api.Test;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class RetrofitService {
    private static final String API_URL = "https://api.github.com";

    public static class RepoData {
        final String name;
        final String html_url;
        final Object owner;

        public RepoData(String name, String html_url, Object owner) {
            this.name = name;
            this.html_url = html_url;
            this.owner = owner;
        }
    }

    public interface GitHub {
        @GET("/users/{owner}/repos")
        Call<List<RepoData>> contributors(
                @Path("owner") String owner);
    }

    String main() throws IOException {
        // Create a very simple REST adapter which points the GitHub API.
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // Create an instance of our GitHub API interface.
        GitHub github = retrofit.create(GitHub.class);


        // Create a call instance for looking up Retrofit contributors.
        Call<List<RepoData>> call = github.contributors("MichalZajkowski");
        // Fetch and print a list of the contributors to the library.
        List<RepoData> contributors = call.execute().body();


        assert contributors != null;
        ArrayList<Object> list = new ArrayList<Object>();
        JSONArray jsonArray = new JSONArray();
        for (RepoData repoData : contributors) {
            if(repoData.name.equals("RetrofitService")){
                jsonArray.put(repoData.name);
                jsonArray.put(repoData.html_url);
                jsonArray.put(repoData.owner);

            }
        }

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonParser jp = new JsonParser();
        JsonElement je = jp.parse(jsonArray.toString());
        return gson.toJson(je);
    }

    @Test
    void aaa() throws IOException {
        String aa = main();


            System.out.println(aa);

    }
}
