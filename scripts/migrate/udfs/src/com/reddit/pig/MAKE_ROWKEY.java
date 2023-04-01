package com.reddit.pig;

import java.io.IOException;
import org.apache.pig.EvalFunc;
import org.apache.pig.data.Tuple;


public class MAKE_ROWKEY extends EvalFunc<String> {
    public String exec(Tuple input) throws IOException {
        String rel = (String)input.get(0);
        String name = (String)input.get(1);
        Long id = (Long)input.get(2);

        String queryName = MAKE_ROWKEY.getQueryName(rel, name);
        if (queryName == null) {
            return null;
        }

        return queryName + "." + Long.toString(id, 36);
    }

    private static String getQueryName(String rel, String name) {
        if (name.equals("1")) {
            if (rel.equals("vote_account_link"))
                return "liked";
        } else if (name.equals("-1")) {
            if (rel.equals("vote_account_link"))
                return "disliked";
        } else if (name.equals("save")) {
            if (rel.equals("savehide"))
                return "saved";
        } else if (name.equals("hide")) {
            if (rel.equals("savehide"))
                return "hidden";
        } else if (name.equals("inbox")) {
            if (rel.equals("inbox_account_comment")) {
                return "inbox_comments";
            } else if (rel.equals("inbox_account_message")) {
                return "inbox_messages";
            } else if (rel.equals("moderatorinbox")) {
                return "subreddit_messages";
            } else if (rel.equals("inbox_account_comment:unread")) {
                return "unread_comments";
            } else if (rel.equals("inbox_account_message:unread")) {
                return "unread_messages";
            } else if (rel.equals("moderatorinbox:unread")) {
                return "unread_subreddit_messages";
            }
        } else if (name.equals("selfreply")) {
            if (rel.equals("inbox_account_comment")) {
                return "inbox_selfreply";
            } else if (rel.equals("inbox_account_comment:unread")) {
                return "unread_selfreply";
            }
        }

        return null;
    }
}
