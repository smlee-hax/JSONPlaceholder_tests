package posts;

import helpers.TestHelpers;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.net.http.HttpResponse;

public class PostsPostTests {
    String baseUrl;
    @BeforeTest
    @Parameters({"baseUrl","route"})
    public void testSetup(String baseUrl, String route){
        if(this.baseUrl == null) {
            StringBuilder builder = new StringBuilder();
            builder.append(baseUrl).append(route);
            this.baseUrl = builder.toString();
        }
    }

    @Test
    public void testUserMakesPostSuccessfullyWithTitleWithBody(){
        JSONObject body = new JSONObject();
        body.append("title","A Test Title!");
        body.append("body", "A Test Body!");
        body.append("user", 1);

        HttpResponse<String> response = TestHelpers.sendRequest(baseUrl,
                TestHelpers.HttpMethodRequests.POST, body.toString(), null);

        JSONObject responseBody = new JSONObject(response.body().toString());
        Assert.assertEquals(body.get("title").toString(), responseBody.get("title").toString(), "Posted and response title do not match");
        Assert.assertEquals(body.get("body").toString(), responseBody.get("body").toString(), "Posted and response text bodies do not match");
    }
}
