package com.reddit.pig;

import java.util.Map;
import java.util.HashMap;
import java.lang.System;
import java.io.IOException;
import org.apache.pig.EvalFunc;
import org.apache.pig.data.Tuple;
import org.apache.pig.data.DataBag;


public class MAKE_MAP extends EvalFunc<Map<Object, Object>> {
    public Map<Object, Object> exec(Tuple input) throws IOException {
        Map<Object, Object> map = new HashMap<Object, Object>();

        DataBag bag = (DataBag)input.get(0);
        for (Tuple tuple : bag) {
            String key = (String)tuple.get(0);
            Object value = tuple.get(1);

            map.put(key, value.toString());
        }

        return map;
    }
}
