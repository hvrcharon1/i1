package com.getinfocia.infocia.item;

import java.util.ArrayList;
import java.util.List;

public class Travels
{
    
        
        public final String description;
        public final int guid;
        public final String imgurl;
        public final String postdate;
        public final int readed;
        public final String source_link;
        public final String source_title;
        public final String title;
        public final String video_id;

        public Travels(int i, int j, String s1, String s2, String s4, 
                String s5,  String s7, String s8, String s9)
        {
            guid = i;
            readed = j;
            title = s1;
            description = s2;
            postdate = s4;
            imgurl = s5;
            source_title = s7;
            source_link = s8;
            video_id = s9;
        }
    


    public static List<Travels> IMG_DESCRIPTIONS = new ArrayList<Travels>();

     

}
