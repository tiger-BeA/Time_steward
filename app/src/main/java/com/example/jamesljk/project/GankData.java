package com.example.jamesljk.project;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

class GankData {
    private String _id, desc, source, type, url, who;
    private boolean used;
    private JSONArray images;
    private Date createAt, publishedAt;

    public String getID(){ return this._id; }
    public String getDescription(){ return this.desc; }
    public String getUrl() { return this.url; }
    public Date getCreateTime() { return this.createAt; }
    public Date getPublishedTime() { return this.publishedAt; }

    public String getImage(int index){
        if (images == null) return null;
        else {
            if ((index < 0) || (index > images.length())) return null;
            else {
                String ret;
                try{
                    ret = images.getString(index);
                } catch(JSONException e) {
                    ret = null;
                }
                return ret;
            }
        }
    }

    @SuppressWarnings("unchecked")
    private static <T> T safeGet(JSONObject json, String name) {
        if (!json.has(name)) return null;
        Object obj;
        try {
            obj = json.get(name);
        } catch (JSONException e) {
            obj = null;
        }
        if (!JSONObject.NULL.equals(obj)) return (T)obj;
        else return null;
    }

    private GankData(JSONObject json) throws JSONException {
        this._id = safeGet(json, "_id");
        String _createAt = safeGet(json,"createdAt");
        this.desc = safeGet(json,"desc");
        String _publishedAt = safeGet(json,"publishedAt");
        this.source = safeGet(json,"source");
        this.type = safeGet(json,"type");
        this.url = safeGet(json,"url");
        this.used = safeGet(json, "used");
        this.images = safeGet(json, "images");
        this.who = safeGet(json,"who");

        DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        try {
            this.createAt = format.parse(_createAt);
        } catch (Exception e) {
            this.createAt = new Date();
        }
        try {
            this.publishedAt = format.parse(_publishedAt);
        } catch (Exception e) {
            this.publishedAt = new Date();
        }
    }

    public static List<GankData> Parse(String json) throws JSONException, IllegalArgumentException {
        JSONObject obj = new JSONObject(json);
        System.out.println(json);
        ArrayList<GankData> ret = new ArrayList<>();
        if (obj.getBoolean("error")) {
            throw new IllegalArgumentException();
        } else {
            JSONArray arr = obj.getJSONArray("results");
            for(int i = 0;i < arr.length();i++) {
                JSONObject temp = arr.getJSONObject(i);
                ret.add(new GankData(temp));
            }
        }
        return ret;
    }
}