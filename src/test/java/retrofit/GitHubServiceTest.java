package retrofit;

import com.google.gson.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;

class GitHubServiceTest {

    private final GitHubClient gitHubClient = new GitHubClient();
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private final JsonParser jp = new JsonParser();

    @Test
    void testRepoRetroService() throws IOException {
        JsonArray data = gitHubClient.getRepoData();
        // Print downloaded data
        JsonElement je = jp.parse(String.valueOf(data));
        String prettyJsonString = gson.toJson(je);
        System.out.println(prettyJsonString);
        // Test downloaded data
        Assertions.assertNotNull(data);
    }
}
