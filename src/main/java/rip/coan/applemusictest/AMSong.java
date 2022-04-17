package rip.coan.applemusictest;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor @Data
public class AMSong {

    private String trackName;
    private String artistName;
    private String collectionName;
    private String trackExplicitness;
    private String previewUrl;

    public boolean isValidTrack() {
        return artistName != null && trackName != null && collectionName != null && trackExplicitness != null && previewUrl != null;
    }

    public boolean isExplicit() {
        return trackExplicitness.equalsIgnoreCase("explicit");
    }

}
