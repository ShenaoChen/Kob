package com.kob.backend.consumer.utils;

import java.util.Random;

public class Game {
    private final Integer rows;
    private final Integer cols;
    private final Integer inner_wall_count;
    private final int[][] g;
    private static final int[] dx = {-1, 0, 1, 0}, dy = {0, 1, 0, -1};

    public Game() {
        this.rows = 13;
        this.cols = 14;
        this.inner_wall_count = 20;
        this.g = new int[this.rows][this.cols];
    }

    public int[][] getG() {
        return this.g;
    }

    boolean check(int sx, int sy, int tx, int ty) {
        if (sx == tx && sy == ty) {
            return true;
        }
        g[sx][sy] = 1;
        for (int i = 0; i < 4; i++) {
            int x = sx + dx[i];
            int y = sy + dy[i];
            if (x >= 0 && x < this.rows && y >= 0 && y < this.cols && g[x][y] == 0) {
                if (check(x, y, tx, ty)) {
                    g[sx][sy] = 0;
                    return true;
                }
            }
        }
        g[sx][sy] = 0;
        return false;
    }
    private boolean draw(){
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.cols; j++) {
                g[i][j] = 0;
            }
        }

        for (int i = 0; i < this.rows; i++) {
            g[i][0] = g[i][this.cols - 1] = 1;
        }
        for (int j = 0; j < this.cols; j++) {
            g[0][j] = g[this.rows - 1][j] = 1;
        }

        Random random = new Random();
        for (int i = 0; i < this.inner_wall_count / 2; i++) {
            for (int j = 0; j < 1000; j++) {
                int r = random.nextInt(this.rows);
                int c = random.nextInt(this.cols);
                if (r == 1 && c == this.cols - 2 || c == 1 && r == this.rows - 2) {
                    continue;
                }
                if (g[r][c] == 0 && g[this.rows - r - 1][this.cols - c -1] == 0) {
                    g[r][c] = g[this.rows - r - 1][this.cols - c - 1] = 1;
                    break;
                }
            }
        }
        return check(this.rows - 2, 1, 1, this.cols - 2);
    }

    public void createMap() {
        for (int i = 0; i < 1000; i++) {
            if (draw()) {
                break;
            }
        }
    }
}
