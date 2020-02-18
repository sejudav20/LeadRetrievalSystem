package com.example.conferencepro;

import androidx.room.Embedded;

public class AllDataClass {
    @Embedded
    EntrantData ed;
    @Embedded
    ApplicantInfo ai;

    public EntrantData getEd() {
        return ed;
    }

    public void setEd(EntrantData ed) {
        this.ed = ed;
    }
}
