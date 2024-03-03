package com.TripleA;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.simple.JSONObject;  
import org.json.simple.JSONValue;  

public class testOCR {
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		ITesseract tesseract = new Tesseract();
		String readyToTranslate = "";
		System.out.print("Enter the name of image : ");
		String text = scan.nextLine();

		String imgName = "img3.jpg";
		String inputImg = "C:\\Users\\amanb\\OneDrive\\Desktop\\testdata\\" + imgName;
		String outputFile = "C:\\Users\\amanb\\OneDrive\\Desktop\\testdata\\text.txt";
		try {
		tesseract.setDatapath("C:\\Program Files\\Tesseract-OCR\\tessdata");
			
			text = tesseract.doOCR(new File(inputImg));
			

			readyToTranslate = text.replace(" ","%20");
			
		} catch (TesseractException e ) {
			e.printStackTrace();
		} 
		String translatedText = "";
		String[] langCodes = {"en","hi","sa","de","fr","es","ja","zh","ko"};
		String[] langNames = {"English", "Hindi","Sanskrit","German","Frech","Spanish","Japanese","Chinese","Korean"};
		String selectedLang = "hi";
		System.out.println("Enter language : ");
		for(int i = 0 ; i < 9 ; i++) {
			System.out.println(langNames[i]+" : "+langCodes[i]);
		}
		System.out.print("Enter the code for language : ");
		selectedLang = scan.nextLine();

		
		
		try {
			HttpResponse<String> response = Unirest.post("https://google-translate113.p.rapidapi.com/api/v1/translator/text")
					.header("content-type", "application/x-www-form-urlencoded")
					.header("X-RapidAPI-Key", "f7be1438a8msh3610c888918e030p1f325djsn88b329fe550c")
					.header("X-RapidAPI-Host", "google-translate113.p.rapidapi.com")
					.body("from=auto&to="+selectedLang+"&text="+readyToTranslate)
					.asString();
			String responseBody = response.getBody();
            JSONObject jsonResponse = (JSONObject) JSONValue.parse(responseBody);
            translatedText = (String) jsonResponse.get("trans");
            
            FileWriter filewriter = new FileWriter(outputFile);
			BufferedWriter bufferedwriter = new BufferedWriter(filewriter);
			bufferedwriter.write("Text in en : \n" + text + "\n Text in " + selectedLang + " : \n"+ translatedText);
			bufferedwriter.close();
            
		} catch (UnirestException | IOException e) {
			e.printStackTrace();
		}
		System.out.println("\n\nText in English \n<---------------------------------------------->");
		System.out.println(text);
		System.out.println("<---------------------------------------------->\n");
		System.out.println("Text in "+ selectedLang+"\n<---------------------------------------------->\n");

        System.out.println(translatedText);
        System.out.println("<---------------------------------------------->");
		System.out.println("\nHello world");
		scan.close();
	}
}
