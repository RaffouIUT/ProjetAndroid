package com.example.projetandroid;

public class ScoreInfo {
    private int score;
    private String difficulte;

    public ScoreInfo(int score, String difficulty) {
        this.score = score;
        this.difficulte = difficulty;
    }

    public int getScore() {
        return score;
    }

    public String getDifficulty() {
        return difficulte;
    }
}
