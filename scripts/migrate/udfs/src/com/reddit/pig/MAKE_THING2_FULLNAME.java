package com.reddit.pig;

import java.io.IOException;
import org.apache.pig.EvalFunc;
import org.apache.pig.data.Tuple;


public class MAKE_THING2_FULLNAME extends MAKE_FULLNAME {
    private TypeID getTypeID(String rel) {
        if (rel.equals("savehide")) {
            return TypeID.LINK;
        } else if (rel.startsWith("inbox_account_comment")) {
            return TypeID.COMMENT;
        } else if (rel.startsWith("inbox_account_message")) {
            return TypeID.MESSAGE;
        } else if (rel.startsWith("moderatorinbox")) {
            return TypeID.MESSAGE;
        } else if (rel.equals("vote_account_link")) {
            return TypeID.LINK;
        }

        return null;
    }
}
