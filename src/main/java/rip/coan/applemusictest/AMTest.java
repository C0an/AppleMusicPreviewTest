package rip.coan.applemusictest;

import lombok.Getter;
import okhttp3.OkHttpClient;
import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import rip.coan.applemusictest.util.AACPlayer;
import rip.coan.applemusictest.util.RequestCreator;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import sun.net.www.ParseUtil;

public class AMTest {

    @Getter
    private static final OkHttpClient okHttpClient = new OkHttpClient.Builder().connectTimeout(2L, TimeUnit.SECONDS).writeTimeout(2L, TimeUnit.SECONDS).readTimeout(2L, TimeUnit.SECONDS).build();

    public static void main(String[] args) {
        String searchSong = getAnswer("What song do you wish to find a preview for? (eg. Kanye West POWER)");
        System.out.println("Sending a search request to iTunes API.");
        RequestCreator searchRequest = RequestCreator.get("https://itunes.apple.com/search?term=" + ParseUtil.encodePath(searchSong) + "&limit=10");
        if(!searchRequest.isSuccessful()) {
            System.out.println("Sorry - your search was unsuccessful (ERROR: " + searchRequest.getResponse() + ") - " + searchRequest.getErrorMessage());
            return;
        }

        JSONObject jsonResults = searchRequest.asJSONObject();
        System.out.println("We found: " + jsonResults.getInt("resultCount") + " - filtering now.");
        JSONArray jsonArray = jsonResults.getJSONArray("results");

        List<AMSong> foundSounds = new ArrayList<>();

        for (Object object : jsonArray) {
            JSONObject jsonObject = (JSONObject)object;

            if(isValidTrack(jsonObject)) {
                foundSounds.add(new AMSong(jsonObject.getString("trackName"),
                        jsonObject.getString("artistName"),
                        jsonObject.getString("collectionName"),
                        jsonObject.getString("contentAdvisoryRating").equalsIgnoreCase("Explicit"),
                        jsonObject.getString("previewUrl")));
                }
            }
        

        System.out.println("We found " + foundSounds.size() + " songs - here's what we found:");
        System.out.println();
        int index = 0;
        for (AMSong foundSound : foundSounds) {
            System.out.println(index + ". " + foundSound.getTrackName() + (foundSound.isExplicit() ? " [E]" : ""));
            System.out.println(foundSound.getArtistName() + " - " + foundSound.getCollectionName());
            System.out.println();
            index++;
        }

        int integer;
        try {
            integer = Integer.parseInt(getAnswer("Type a number of which song you wish to preview"));
        }catch (Exception e) {
            System.out.println("Invalid number.");
            return;
        }

        if(integer < 0) {
            System.out.println("Number too small.");
            return;
        }

        if(integer > index) {
            System.out.println("That isn't a valid entry.");
            return;
        }

        AMSong selectedSong = foundSounds.get(integer);

        try {
            System.out.println("### PLAYING PREVIEW FOR ###");
            System.out.println(selectedSong.getTrackName() + (selectedSong.isExplicit() ? " [E]" : ""));
            System.out.println(selectedSong.getArtistName() + " - " + selectedSong.getCollectionName());

            URL url = new URL(
                    selectedSong.getPreviewUrl());

            InputStream in = new URL(selectedSong.getPreviewUrl()).openStream();
            File file = createTempFile(in);

            AACPlayer aacPlayer = new AACPlayer(file);
            aacPlayer.play();
            aacPlayer.disableRepeat();

        }catch (Exception e) {
            System.out.println("### FAILED TO PLAY PREVIEW ###");
            e.printStackTrace();
        }
    }

    public static String getAnswer(String text) {
        Scanner myObj = new Scanner(System.in);
        System.out.println(text);

        return myObj.nextLine();
    }
    
    public static boolean isValidTrack(JSONObject jsonObject) {
        return jsonObject.has("artistName") && jsonObject.has("trackName") && jsonObject.has("collectionName") && jsonObject.has("contentAdvisoryRating") && jsonObject.has("previewUrl");
    }

    public static File createTempFile(InputStream in) throws IOException {
        final File tempFile = File.createTempFile("previewSong", "m4a");
        tempFile.deleteOnExit();
        try (FileOutputStream out = new FileOutputStream(tempFile)) {
            IOUtils.copy(in, out);
        }
        return tempFile;
    }

}
