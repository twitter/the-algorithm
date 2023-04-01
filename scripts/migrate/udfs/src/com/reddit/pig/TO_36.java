package com.reddit.pig;

import java.lang.Number;
import java.io.IOException;
import org.apache.pig.EvalFunc;
import org.apache.pig.data.Tuple;


public class TO_36 extends EvalFunc<String> {
    public String exec(Tuple input) throws IOException {
        Object obj = input.get(0);
        Number number;

        if (obj instanceof Number) {
            number = (Number)obj;
        } else {
            number = Long.decode(obj.toString());
        }

        return Long.toString(number.longValue(), 36);
    }
}
