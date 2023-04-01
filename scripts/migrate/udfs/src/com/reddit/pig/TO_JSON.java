package com.reddit.pig;

import java.lang.*;
import java.io.IOException;
import org.apache.pig.EvalFunc;
import org.apache.pig.data.Tuple;


public class TO_JSON extends EvalFunc<String> {
    public String exec(Tuple input) throws IOException {
        StringBuilder builder = new StringBuilder("[");

        int size = input.size();
        for (int i = 0; i < size; i++) {
            Object obj = input.get(i);

            // TODO: support integers and strings
            if (obj == null) {
                builder.append("null");
            } else if (obj instanceof Double || obj instanceof Float) {
                String formatted = String.format("%f", obj);
                builder.append(formatted);
            } else {
                throw new UnsupportedOperationException("can only encode nulls and floating point numbers");
            }

            if (i + 1 != size)
                builder.append(",");
        }

        builder.append("]");
        return builder.toString();
    }
}
