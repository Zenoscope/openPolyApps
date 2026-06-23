package com.bit603.a3;

import java.util.List;

public class YoutubeResponse {
    public List<Item> items;

    public class Item {
        public String kind;
        public String etag;
        public Id id;
        public Snippet snippet;
    }

    public class Id {
        public String kind;
        public String videoId;
    }

    public class Snippet {
        public String title;
        public String description;
        public String channelTitle;
        public Thumbnails thumbnails;
    }

    public class Thumbnails {
        public Thumbnail medium;

        public class Thumbnail {
            public String url;
        }
    }


}