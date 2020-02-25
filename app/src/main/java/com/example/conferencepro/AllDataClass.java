package com.example.conferencepro;

import androidx.room.Embedded;

public class AllDataClass {
    @Embedded
    private EntrantData ed;
    @Embedded
    private ApplicantInfo ai;

    public ApplicantInfo getAi() {
        return ai;
    }

    public void setAi(ApplicantInfo ai) {
        this.ai = ai;
    }

    public EntrantData getEd() {
        return ed;
    }

    public void setEd(EntrantData ed) {
        this.ed = ed;
    }
}
