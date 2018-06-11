package com.websystique.springmvc;

import java.util.Arrays;
import java.util.LinkedHashMap;

import com.websystique.springmvc.model.DummyTigerUserData;
import org.apache.commons.codec.binary.Base64;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;

import com.websystique.springmvc.model.AuthTokenInfo;

public class SpringRestClient {

    public static final String REST_SERVICE_URI = "http://localhost:8080/SpringSecurityOAuth2Example";

    public static final String AUTH_SERVER_URI = "http://localhost:8080/SpringSecurityOAuth2Example/oauth/token";

    public static final String QPM_PASSWORD_GRANT = "?grant_type=password&username=bill&password=abc123";

    public static final String QPM_ACCESS_TOKEN = "?access_token=";

    /*
     * Prepare HTTP Headers.
     */
    private static HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        return headers;
    }

    /*
     * Add HTTP Authorization header, using Basic-Authentication to send client-credentials.
     */
    private static HttpHeaders getHeadersWithClientCredentials() {
        String plainClientCredentials = "my-trusted-client:secret";
        String base64ClientCredentials = new String(Base64.encodeBase64(plainClientCredentials.getBytes()));

        HttpHeaders headers = getHeaders();
        headers.add("Authorization", "Basic " + base64ClientCredentials);
        return headers;
    }

    /*
     * Send a POST request [on /oauth/token] to get an access-token, which will then be send with each request.
     */
    @SuppressWarnings({"unchecked"})
    private static AuthTokenInfo sendTokenRequest() {
        RestTemplate restTemplate = new RestTemplate();

        HttpEntity<String> request = new HttpEntity<String>(getHeadersWithClientCredentials());
        ResponseEntity<Object> response = restTemplate.exchange(AUTH_SERVER_URI + QPM_PASSWORD_GRANT, HttpMethod.POST, request, Object.class);
        LinkedHashMap<String, Object> map = (LinkedHashMap<String, Object>) response.getBody();
        AuthTokenInfo tokenInfo = null;

        if (map != null) {
            tokenInfo = new AuthTokenInfo();
            tokenInfo.setAccess_token((String) map.get("access_token"));
            tokenInfo.setToken_type((String) map.get("token_type"));
            tokenInfo.setRefresh_token((String) map.get("refresh_token"));
            tokenInfo.setExpires_in((int) map.get("expires_in"));
            tokenInfo.setScope((String) map.get("scope"));
            System.out.println(tokenInfo);
            //System.out.println("access_token ="+map.get("access_token")+", token_type="+map.get("token_type")+", refresh_token="+map.get("refresh_token")
            //+", expires_in="+map.get("expires_in")+", scope="+map.get("scope"));;
        } else {
            System.out.println("No user exist----------");

        }
        return tokenInfo;
    }

    /*
         * Send a GET request to get a specific user.
         */
    private static void getUser(AuthTokenInfo tokenInfo) {
        Assert.notNull(tokenInfo, "Authenticate first please......");
        System.out.println("\nTesting getUser API----------");
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<String> request = new HttpEntity<String>(getHeaders());
        ResponseEntity<DummyTigerUserData> response = restTemplate.exchange(REST_SERVICE_URI + "/dummyTigerUserData/1" + QPM_ACCESS_TOKEN + tokenInfo.getAccess_token(),
                HttpMethod.GET, request, DummyTigerUserData.class);
        DummyTigerUserData dummyTigerUserData = response.getBody();
        System.out.println(dummyTigerUserData);
    }


    public static void main(String args[]) {
        AuthTokenInfo tokenInfo = sendTokenRequest();

        getUser(tokenInfo);
    }
}