package com.tddjava.ch06tictactoe.mongo;


import com.tddjava.ch06tictactoe.mongo.model.TicTacToeBean;

import java.net.UnknownHostException;

/**
 * tic-tac-toe
 *
 * @auther ouxx
 * @create 2018/5/27 18:01
 */
public class TicTacToe {

    /**
     * 初始棋盘
     */
    private Character[][] board = {{'\0', '\0', '\0'},
            {'\0', '\0', '\0'},
            {'\0', '\0', '\0'}
    };

    private char lastPlayer;
    private static final int SIZE = 3;
    private TicTacToeCollection ticTacToeCollection;
    private int turn = 0;

    protected TicTacToe(TicTacToeCollection collection){
        this.ticTacToeCollection = collection;
    }

    public TicTacToe() throws UnknownHostException{
        this(new TicTacToeCollection());
    }


    public String play(int x, int y) {
        checkAxis(x);
        checkAxis(y);
        //设置上一个玩家
        lastPlayer = nextPlayer();
        setBox(new TicTacToeBean(++turn, x, y, lastPlayer));
        if (isWin(x, y)) {
            return lastPlayer + " is the Winner";
        } else if (isDraw()) {//平局
            return "The result is draw";
        }
        return "No Winner";
    }

    private boolean isDraw() {
        for (int x = 0; x < SIZE; ++x) {
            for (int y = 0; y < SIZE; ++y) {
                //如果棋盘还未填满，则不算平局
                if (board[x][y] == '\0') {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean isWin(int x, int y) {
        final int playTotal = SIZE * lastPlayer;
        //对角线
        char diagonal1 = '\0';
        char diagonal2 = '\0';
        //垂直于水平
        char horizontal = '\0';
        char vertical = '\0';
        for(int i = 0; i < SIZE; ++i) {
            horizontal += board[i][y - 1];
            vertical += board[x - 1][i];
            diagonal1 += board[i][i];
            diagonal2 += board[i][SIZE - i - 1];
        }
        if (horizontal == playTotal
                || vertical == playTotal
                || diagonal1 == playTotal
                || diagonal2 == playTotal) {
            return true;
        }
        return false;
    }

    private void checkAxis(int axis) {
        if (axis < 1 || axis > 3) {
            throw new RuntimeException("outside board");
        }
    }

    private void setBox(TicTacToeBean bean) {
        int x = bean.getX();
        int y = bean.getY();
        if ('\0' != board[x - 1][y - 1]) {
            throw new RuntimeException("Box is occupied");
        }else{
            board[x - 1][y - 1] = lastPlayer;
            if(!getTicTacToeCollection().saveMove(bean)){
                throw new RuntimeException("Saving to DB failed");
            }
        }
    }

    /**
     * 下一个玩家
     * @return
     */
    public char nextPlayer() {
        if ('X' == lastPlayer) {
            return 'O';
        }
        return 'X';
    }

    public TicTacToeCollection getTicTacToeCollection() {
        return ticTacToeCollection;
    }
}
