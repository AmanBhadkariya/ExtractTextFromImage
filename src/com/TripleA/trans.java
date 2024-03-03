package com.TripleA;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.simple.JSONObject;  
import org.json.simple.JSONValue;  

public class trans{

	public static void main(String[] args) {
		try {
			HttpResponse<String> response = Unirest.post("https://google-translate113.p.rapidapi.com/api/v1/translator/text")
					.header("content-type", "application/x-www-form-urlencoded")
					.header("X-RapidAPI-Key", "f7be1438a8msh3610c888918e030p1f325djsn88b329fe550c")
					.header("X-RapidAPI-Host", "google-translate113.p.rapidapi.com")
					.body("from=auto&to=hi&text=food")
					.asString();
			String responseBody = response.getBody();
            JSONObject jsonResponse = (JSONObject) JSONValue.parse(responseBody);
            String translatedText = (String) jsonResponse.get("trans");
            System.out.println(translatedText);
		} catch (UnirestException e) {
			e.printStackTrace();
		}
    }
}
