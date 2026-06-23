package com.bit603.a3;

import java.util.List;

/**
 * BIT603 Assignemnt 3
 *
 * Name: Ryan McArthur
 * ID: 5105426
 * Created 31/05/2026
 * youtube response
 */

public class ChannelResponse {
    public List<Item> items;

    public class Item {
        public String id;
        public Snippet snippet;
        public Statistics statistics;
    }

    public class Snippet {
        public String title;
        public String description;
    }

    public class Statistics {
        public String subscriberCount;
        public String videoCount;
    }
}