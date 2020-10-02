package retrofit;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

import java.io.IOException;
import java.util.List;

class GitHubClient {

    private final GitHubHTTPClient gitHubHTTPClient = new GitHubHTTPClient();
    private final GitHub github = gitHubHTTPClient.getRetrofitInstance().create(GitHub.class);

    JsonArray getRepoData() throws IOException {
        Call<List<RepoData>> call = github.reposData("MichalZajkowski");
        List<RepoData> reposData = call.execute().body();

        assert reposData != null;
        JsonArray data = new JsonArray();
        for (RepoData repoData : reposData) {
            if (repoData.name.equals("Retrofit-Service")) {
                data.add(repoData.name);
                data.add(repoData.html_url);
                data.add(repoData.owner);
            }
        }
        return data;
    }

    private interface GitHub {
        @GET("/users/{owner}/repos")
        Call<List<RepoData>> reposData(
                @Path("owner") String owner);
    }

    private static class RepoData {
        final String name;
        final String html_url;
        final String node_name;
        final String fullName;
        final JsonObject owner;

        public RepoData(String name, String html_url, String node_name, String fullName, JsonObject owner) {
            this.name = name;
            this.html_url = html_url;
            this.node_name = node_name;
            this.fullName = fullName;
            this.owner = owner;
        }
    }
}
