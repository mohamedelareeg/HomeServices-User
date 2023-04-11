package com.rovaindu.homeservice.model.chat;

import java.io.Serializable;
import java.util.List;

public class Smartreply implements Serializable {
    private int id;
    private int userID;
    private String question;
    private List<BotReplys> botReplys;

    public Smartreply(int id, int userID, String question, List<BotReplys> botReplys) {
        this.id = id;
        this.userID = userID;
        this.question = question;
        this.botReplys = botReplys;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public List<BotReplys> getBotReplys() {
        return botReplys;
    }

    public void setBotReplys(List<BotReplys> botReplys) {
        this.botReplys = botReplys;
    }

    public static class BotReplys implements Serializable{
        private String reply;
        private int type;
        private List<BotReplys> botReplys;

        public BotReplys(String reply, int type, List<BotReplys> botReplys) {
            this.reply = reply;
            this.type = type;
            this.botReplys = botReplys;
        }

        public String getReply() {
            return reply;
        }

        public void setReply(String reply) {
            this.reply = reply;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public List<BotReplys> getBotReplys() {
            return botReplys;
        }

        public void setBotReplys(List<BotReplys> botReplys) {
            this.botReplys = botReplys;
        }
    }
}
