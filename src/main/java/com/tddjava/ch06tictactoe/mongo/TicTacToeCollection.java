package com.tddjava.ch06tictactoe.mongo;

import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.tddjava.ch06tictactoe.mongo.model.TicTacToeBean;
import org.jongo.Jongo;
import org.jongo.MongoCollection;

import java.net.UnknownHostException;

/**
 * tic-tac-toe momgodb
 *
 * @auther ouxx
 * @create 2018/5/30 10:12
 */
public class TicTacToeCollection {
    private MongoCollection mongoCollection;

    public TicTacToeCollection() throws UnknownHostException{
        DB db = new MongoClient().getDB("tic-tac-toe");
        mongoCollection = new Jongo(db).getCollection("game");
    }

    public MongoCollection getMongoCollection() {
        return mongoCollection;
    }

    public boolean saveMove(TicTacToeBean bean) {
        try {
            getMongoCollection().save(bean);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean drop() {
        try {
            getMongoCollection().drop();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
