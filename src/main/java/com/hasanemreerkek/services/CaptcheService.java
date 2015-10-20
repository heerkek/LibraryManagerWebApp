package com.hasanemreerkek.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;

import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

/**
 * Created by Emre on 19.10.2015.
 *
 * Captche Service
 */
@Service
public class CaptcheService {
    /**
     * get status of the Captcha is valid or is not valid
     * @param grecaptchaResponse the responce of Google ReCaptche
     * @return status of the Captcha is valid or is not valid
     * @throws IOException
     */
    public boolean isSuccess(String grecaptchaResponse) throws IOException {
        String secretKey = "6LffLA8TAAAAAFUqxEzl_IPCtemoHUt1fuDeRXNQ";
        String url = "https://www.google.com/recaptcha/api/siteverify?secret=" + secretKey + "&response=" + grecaptchaResponse;

        HttpClient client = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost(url);
        HttpResponse response = client.execute(post);
        BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

        StringBuffer result = new StringBuffer();
        String line = "";
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }
        ObjectMapper mapper = new ObjectMapper();
        HashMap map = mapper.readValue(result.toString(), HashMap.class);
        return  (Boolean) map.get("success");
    }
}
