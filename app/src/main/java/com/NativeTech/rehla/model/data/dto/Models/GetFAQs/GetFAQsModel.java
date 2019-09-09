package com.NativeTech.rehla.model.data.dto.Models.GetFAQs;

public class GetFAQsModel {
    private String Id;
    private String Qeustion;
    private String Answer;
    private String QeustionLT;
    private String AnswerLT;

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getQeustion() {
        return Qeustion;
    }

    public void setQeustion(String qeustion) {
        Qeustion = qeustion;
    }

    public String getAnswer() {
        return Answer;
    }

    public void setAnswer(String answer) {
        Answer = answer;
    }

    public String getQeustionLT() {
        return QeustionLT;
    }

    public void setQeustionLT(String qeustionLT) {
        QeustionLT = qeustionLT;
    }

    public String getAnswerLT() {
        return AnswerLT;
    }

    public void setAnswerLT(String answerLT) {
        AnswerLT = answerLT;
    }
}
