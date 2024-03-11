package com.qooke.papagoapp.model;

import java.io.Serializable;

public class History implements Serializable {

    //멤버변수

    public String sentence;
    public String result;
    public String target;

    // 생성자 만들기
    public History(String sentence, String result, String target) {
        this.sentence = sentence;
        this.result = result;
        this.target = target;
    }
}
