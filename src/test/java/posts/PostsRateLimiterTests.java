package posts;

import helpers.TestHelpers;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;


public class PostsRateLimiterTests {

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

//    @Test
    public void testCannotPostExceedHitRateLimit(){
        List<String> headers = new ArrayList<String>();
        headers.add("X-Ratelimit-Limit");
        headers.add("0");

        JSONObject body = new JSONObject();
        body.append("title","A Test Title!");
        body.append("body", "A Test Body!");
        body.append("user", 1);

        HttpResponse<String> response = TestHelpers.sendRequest(baseUrl,
                TestHelpers.HttpMethodRequests.POST, body.toString(), null);

        //Will result in a 201 unauthorized. RateLimit will set itself back to 1
        Assert.assertEquals(429, response.statusCode());

    }

//    @Test
    public void testCannotGetExceedHitRateLimit(){

    }
}
