# Apple Music Song Preview Player

A Java Test to try and play Song Previews found by Apple Music/iTunes' Public API

## How does it work?

The program contacts the endpoint below in order to obtain search results in order to find a ```previewUrl```

#### Example Endpoint URL

```
https://itunes.apple.com/search?term=Kanye+West+Champion&limit=1&entity=song
```

#### Example Endpoint Result

```json
{
   "resultCount":1,
   "results":[
      {
         "wrapperType":"track",
         "kind":"song",
         "artistId":2715720,
         "collectionId":1451901307,
         "trackId":1451902183,
         "artistName":"Kanye West",
         "collectionName":"Graduation",
         "trackName":"Champion",
         "collectionCensoredName":"Graduation",
         "trackCensoredName":"Champion",
         "artistViewUrl":"https://music.apple.com/us/artist/kanye-west/2715720?uo=4",
         "collectionViewUrl":"https://music.apple.com/us/album/champion/1451901307?i=1451902183&uo=4",
         "trackViewUrl":"https://music.apple.com/us/album/champion/1451901307?i=1451902183&uo=4",
         "previewUrl":"https://audio-ssl.itunes.apple.com/itunes-assets/AudioPreview125/v4/3c/38/37/3c383720-5595-b91e-b9d6-8c83611673dc/mzaf_2368399614446130746.plus.aac.p.m4a",
         "artworkUrl30":"https://is5-ssl.mzstatic.com/image/thumb/Music114/v4/34/a4/b0/34a4b05d-b049-c30a-461f-3b41ef1d352f/source/30x30bb.jpg",
         "artworkUrl60":"https://is5-ssl.mzstatic.com/image/thumb/Music114/v4/34/a4/b0/34a4b05d-b049-c30a-461f-3b41ef1d352f/source/60x60bb.jpg",
         "artworkUrl100":"https://is5-ssl.mzstatic.com/image/thumb/Music114/v4/34/a4/b0/34a4b05d-b049-c30a-461f-3b41ef1d352f/source/100x100bb.jpg",
         "collectionPrice":9.99,
         "trackPrice":1.29,
         "releaseDate":"2007-09-05T12:00:00Z",
         "collectionExplicitness":"explicit",
         "trackExplicitness":"explicit",
         "discCount":1,
         "discNumber":1,
         "trackCount":14,
         "trackNumber":2,
         "trackTimeMillis":167600,
         "country":"USA",
         "currency":"USD",
         "primaryGenreName":"Hip-Hop/Rap",
         "contentAdvisoryRating":"Explicit",
         "isStreamable":true
      }
   ]
}
```

## Dependencies
This program uses a few dependencies in order to achieve its use, here are the used depends:
- okhttp3
- lombok
- gson
- guava
- JAAD
- commons-io
- java-aac-player