package com.ourshipsgame.mainmenu;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Scores {
    protected class Node {
        protected int id;
        protected String name;
        protected float scoreValue;
        protected float timeElapsed;
        protected float accuracyRatio;

        protected Node(String name, float score, float time, float accuracy) {
            this.name = name;
            this.scoreValue = score;
            this.timeElapsed = time;
            this.accuracyRatio = accuracy;
        }
    }

    protected class SortByScore implements Comparator<Node> {

        @Override
        public int compare(Node o1, Node o2) {
            return (int) (o2.scoreValue - o1.scoreValue);
        }

    }

    List<Node> scoresList;

    public Scores() {
        this.scoresList = new ArrayList<Node>();
        try {
            loadScores();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadScores() throws IOException {
        File file = new File("core/assets/files/scores.txt");
        if (!file.exists()) {
            file.createNewFile();
        } else {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNext()) {
                String name = scanner.next().trim();
                float score = Float.valueOf(scanner.next().trim());
                float time = Float.valueOf(scanner.next().trim());
                float accuracy = Float.valueOf(scanner.next().trim());
                scoresList.add(new Node(name, score, time, accuracy));
            }
            scanner.close();
            scoresList.sort(new SortByScore());
        }
    }

    public void drawScores(SpriteBatch batch, BitmapFont font, float gameH, float gameW) {
        Node node;
        for (int i = 0; i < scoresList.size(); i++) {
            node = scoresList.get(i);
            String text = i + " - " + node.name + " Score: " + node.scoreValue + " Time: "
                    + String.format("%.2f", node.timeElapsed) + " Shots accuracy: " + node.accuracyRatio;
            font.draw(batch, text, gameW / 2 - 300, gameH - 50 - (i * 50));
        }
    }
}
