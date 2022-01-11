package com.android.tools.Activities.Banners;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class SliderList implements Serializable {

    @SerializedName("event_id")
    private String event_id;

    @SerializedName("cat_id")
    private String cat_id;

    @SerializedName("event_type")
    private String event_type;

    @SerializedName("event_title")
    private String event_title;

    @SerializedName("event_address")
    private String event_address;

    @SerializedName("event_banner_thumb")
    private String event_banner_thumb;

    @SerializedName("external_link")
    private String external_link;

    public String getEvent_id() {
        return event_id;
    }

    public String getCat_id() {
        return cat_id;
    }

    public String getEvent_type() {
        return event_type;
    }

    public String getEvent_title() {
        return event_title;
    }

    public String getEvent_address() {
        return event_address;
    }

    public String getEvent_banner_thumb() {
        return event_banner_thumb;
    }

    public String getExternal_link() {
        return external_link;
    }
}
