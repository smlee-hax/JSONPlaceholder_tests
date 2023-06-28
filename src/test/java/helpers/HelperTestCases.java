package helpers;

import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.net.http.HttpResponse;
import java.util.ArrayList;

public class HelperTestCases {

    String testUrl;
    @BeforeTest
    @Parameters({"baseUrl"})
    public void setup(String url){
        this.testUrl = this.testUrl == null? url : this.testUrl;
    }

    @Test
    public void testCanSubmitGetRequestNoHeadersNoBodySuccess(){
        HttpResponse<String> response = TestHelpers.sendRequest(testUrl, TestHelpers.HttpMethodRequests.GET, null, null);
        Assert.assertEquals(200, response.statusCode(), "GET web requests without body or headers not sent properly!");
    }

    @Test
    public void testCanSubmitGetRequestWithHeaderNoBodySuccess(){
        ArrayList<String> headers = new ArrayList<>();
        headers.add("Content-Type");
        headers.add("application/json");

        HttpResponse<String> response = TestHelpers.sendRequest(
                testUrl, TestHelpers.HttpMethodRequests.GET, null, headers);
        Assert.assertEquals(200, response.statusCode(), "GET web requests with headers and no body not sent properly!");
    }

    @Test
    public void testCanSubmitGetRequestWithHeaderWithBodySuccess(){
        ArrayList<String> headers = new ArrayList<>();
        headers.add("Content-Type");
        headers.add("application/json");

        JSONObject body = new JSONObject();
        body.put("foo", "bar");

        HttpResponse<String> response = TestHelpers.sendRequest(
                testUrl, TestHelpers.HttpMethodRequests.GET, body.toString(), headers);
        Assert.assertEquals(200, response.statusCode(), "GET web requests with headers and body not sent properly!");
    }


    @Test
    public void testCanSubmitPostWithNoContentFails(){
        HttpResponse<String> response = TestHelpers.sendRequest(
                testUrl, TestHelpers.HttpMethodRequests.POST, null, null);
        Assert.assertNull(response, "Recieved response when expecting null value!");
    }

    @Test
    public void testCanSubmitPostWithContentNoHeadersSuccess(){
        JSONObject body = new JSONObject();
        body.put("foo", "bar");

        HttpResponse<String> response = TestHelpers.sendRequest(
                testUrl, TestHelpers.HttpMethodRequests.GET, body.toString(), null);
        Assert.assertEquals(200, response.statusCode(), "GET web requests with headers and body not sent properly!");
    }

    @Test
    public void testCanSubmitPostWithContentWithHeadersSuccess(){
        ArrayList<String> headers = new ArrayList<>();
        headers.add("Content-Type");
        headers.add("application/json");

        JSONObject body = new JSONObject();
        body.put("foo", "bar");

        HttpResponse<String> response = TestHelpers.sendRequest(
                testUrl, TestHelpers.HttpMethodRequests.GET, body.toString(), null);
        Assert.assertEquals(200, response.statusCode(), "GET web requests with headers and body not sent properly!");
    }


}
