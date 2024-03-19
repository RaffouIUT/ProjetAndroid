package com.example.projetandroid;
import java.util.HashMap;
import java.util.Map;

public class ScoreSingleton {
    private static ScoreSingleton instance;
    private Map<String, ScoreInfo> scores;

    private ScoreSingleton() {
        scores = new HashMap<>();
    }

    public static ScoreSingleton getInstance() {
        if (instance == null) {
            instance = new ScoreSingleton();
        }
        return instance;
    }

    public void setScore(String key, int score, String difficulte) {
        scores.put(key, new ScoreInfo(score, difficulte));
    }

    public ScoreInfo getScoreInfo(String key) {
        return scores.get(key);
    }
}
