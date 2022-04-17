package rip.coan.applemusictest;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor @Data
public class AMSong {

    private String trackName;
    private String artistName;
    private String collectionName;
    private boolean explicit;
    private String previewUrl;

}
