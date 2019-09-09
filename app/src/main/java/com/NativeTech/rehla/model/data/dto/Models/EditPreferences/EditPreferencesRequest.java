package com.NativeTech.rehla.model.data.dto.Models.EditPreferences;

public class EditPreferencesRequest {
    private boolean LikeSpeaking;

    private boolean HaveAirCondition;

    private boolean LikePets;

    private boolean LikeMusic;

    private boolean LikeSmoking;

    private boolean HaveChargeMobile;

    public boolean isLikeSpeaking() {
        return LikeSpeaking;
    }

    public void setLikeSpeaking(boolean likeSpeaking) {
        LikeSpeaking = likeSpeaking;
    }

    public boolean isHaveAirCondition() {
        return HaveAirCondition;
    }

    public void setHaveAirCondition(boolean haveAirCondition) {
        HaveAirCondition = haveAirCondition;
    }

    public boolean isLikePets() {
        return LikePets;
    }

    public void setLikePets(boolean likePets) {
        LikePets = likePets;
    }

    public boolean isLikeMusic() {
        return LikeMusic;
    }

    public void setLikeMusic(boolean likeMusic) {
        LikeMusic = likeMusic;
    }

    public boolean isLikeSmoking() {
        return LikeSmoking;
    }

    public void setLikeSmoking(boolean likeSmoking) {
        LikeSmoking = likeSmoking;
    }

    public boolean isHaveChargeMobile() {
        return HaveChargeMobile;
    }

    public void setHaveChargeMobile(boolean haveChargeMobile) {
        HaveChargeMobile = haveChargeMobile;
    }
}
