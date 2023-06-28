package posts;//explicit imports to avoid bloat
import helpers.TestHelpers;
import org.json.JSONArray;
import org.testng.Assert;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.json.JSONObject;
import java.net.http.HttpResponse;
import helpers.TestHelpers.HttpMethodRequests;

public class PostsGetTests {

    //TODO get url from testng xml document
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
    public void testEndUserGetPostSuccess(){
        //ideal setup would create the post before we fetch it, because we cannot guarantee it exists
        //unfortunately with faked data from JSONPlaceholder, the actual post will not exist
        int postId = 1;
        HttpResponse<String> response = TestHelpers.sendRequest(baseUrl+postId, HttpMethodRequests.GET, null, null);

        JSONObject responseBody = new JSONObject(response.body().toString());
        Assert.assertEquals(postId, responseBody.get("id"), "Unable to fetch post!");
    }

    @Test
    public void testEndUserCanGetAllPostsSuccess(){
        //realistically we could not hard-code this value.
        //Ideally, we would have a clean environment with no posts that we could then generate and fetch for this test
        //But, also, realistically, it's impossible to confirm the total number of posts at any given time during automated tests
        //would be better to test specific cases (i.e. pagination places)
        int numPosts = 100;

        HttpResponse<String> response = TestHelpers.sendRequest("https://jsonplaceholder.typicode.com/posts/", HttpMethodRequests.GET, null, null);
        JSONArray responseBody = new JSONArray(response.body().trim());
        Assert.assertEquals(numPosts, responseBody.length(),"Unable to fetch all posts, fetched "+responseBody.length());
    }

}
