package com.javarush.games.snake;

import com.javarush.engine.cell.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Snake {

    private List<GameObject> snakeParts = new ArrayList<>();
    private static final String HEAD_SIGN = "\uD83D\uDC7E";
    private static final String BODY_SIGN = "\u26AB";
    public boolean isAlive = true;
    private Direction direction = Direction.LEFT;

    public Snake(int x, int y) {
        Collections.addAll(snakeParts,
                new GameObject(x, y),
                new GameObject(x + 1, y),
                new GameObject(x + 2, y));
    }

    public void draw(Game game) {
        for (int i = 0; i < snakeParts.size(); i++) {
            GameObject snakePart = snakeParts.get(i);
            game.setCellValueEx(
                    snakePart.x,
                    snakePart.y,
                    Color.NONE, i == 0 ? HEAD_SIGN : BODY_SIGN,
                    isAlive ? Color.GREEN : Color.RED,
                    75
            );
        }
    }

    public void setDirection(Direction direction) {
        switch (this.direction) {
            case UP:
            case DOWN:
                if (snakeParts.get(0).y == snakeParts.get(1).y)
                    return;
                break;
            case RIGHT:
            case LEFT:
                if (snakeParts.get(0).x == snakeParts.get(1).x)
                    return;
        }
        if (direction.ordinal() % 2 != this.direction.ordinal() % 2)
            this.direction = direction;
    }

    public void move(Apple apple) {
        GameObject newHead = createNewHead();
        if (newHead.x < 0 || newHead.x >= SnakeGame.WIDTH || newHead.y < 0 || newHead.y >= SnakeGame.HEIGHT ||
                checkCollision(newHead)) {
            isAlive = false;
        } else {
            snakeParts.add(0, newHead);
            if (newHead.x == apple.x && newHead.y == apple.y)
                apple.isAlive = false;
            else
                removeTail();
        }
    }

    public GameObject createNewHead() {
        GameObject snakeHead = snakeParts.get(0);
        switch (direction) {
            case UP:
                return new GameObject(snakeHead.x, snakeHead.y - 1);
            case RIGHT:
                return new GameObject(snakeHead.x + 1, snakeHead.y);
            case DOWN:
                return new GameObject(snakeHead.x, snakeHead.y + 1);
            case LEFT:
                return new GameObject(snakeHead.x - 1, snakeHead.y);
        }
        return null;
    }

    public void removeTail() {
        snakeParts.remove(snakeParts.size() - 1);
    }

    public boolean checkCollision(GameObject body) {
        for (GameObject snakePart : snakeParts) {
            if (body.x == snakePart.x && body.y == snakePart.y)
                return true;
        }
        return false;
    }

    public int getLength() {
        return snakeParts.size();
    }

}