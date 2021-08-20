package com.javarush.games.snake;

import com.javarush.engine.cell.*;

public class SnakeGame extends Game {

    public static final int WIDTH = 15;
    public static final int HEIGHT = 15;
    private static final int GOAL = 28;
    private Snake snake;
    private Apple apple;
    private int turnDelay;
    private boolean isGameStopped;
    private int score;

    @Override
    public void initialize() {
        setScreenSize(WIDTH, HEIGHT);
        createGame();
    }

    private void drawScene () {
        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                setCellValueEx(x, y, Color.DARKSEAGREEN, "");
            }
        }

        snake.draw(this);
        apple.draw(this);
    }

    private void createGame() {
        snake = new Snake(WIDTH / 2, HEIGHT / 2);
        isGameStopped = false;
        turnDelay = 300;
        score = 0;

        createNewApple();
        drawScene();
        setTurnTimer(turnDelay);
        setScore(score);
    }

    @Override
    public void onTurn(int step) {
        snake.move(apple);
        if (!apple.isAlive) {
            score += 5;
            turnDelay -= 10;
            createNewApple();
            setScore(score);
            setTurnTimer(turnDelay);
        }
        if (!snake.isAlive)
            gameOver();
        if (snake.getLength() > GOAL)
            win();
        drawScene();
    }

    @Override
    public void onKeyPress(Key key) {
        switch (key) {
            case UP:
                snake.setDirection(Direction.UP);
                break;
            case RIGHT:
                snake.setDirection(Direction.RIGHT);
                break;
            case DOWN:
                snake.setDirection(Direction.DOWN);
                break;
            case LEFT:
                snake.setDirection(Direction.LEFT);
                break;
            case SPACE:
                if (isGameStopped)
                    createGame();
        }
    }

    private void createNewApple() {
        do {
            apple = new Apple(getRandomNumber(WIDTH), getRandomNumber(HEIGHT));
        } while (snake.checkCollision(apple));
    }

    private void gameOver () {
        stopTurnTimer();
        isGameStopped = true;
        showMessageDialog(Color.NONE, "GAME OVER", Color.RED, 75);
    }

    private void win() {
        stopTurnTimer();
        isGameStopped = true;
        showMessageDialog(Color.NONE, "YOU WIN", Color.GREEN, 80);
    }

}
