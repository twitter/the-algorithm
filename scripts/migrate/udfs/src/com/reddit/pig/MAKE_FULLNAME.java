package com.reddit.pig;

import java.io.IOException;
import org.apache.pig.EvalFunc;
import org.apache.pig.data.Tuple;


public class MAKE_FULLNAME extends EvalFunc<String> {
    public String exec(Tuple input) throws IOException {
        String name = (String)input.get(0);
        Long id = (Long)input.get(1);
        TypeID typeId = this.getTypeID(name);
        if (typeId == null)
            return null;
        return String.format("t%d_%s", typeId.ordinal(), Long.toString(id, 36));
    }

    private TypeID getTypeID(String thingName) {
        String enumName = thingName.toUpperCase();
        return TypeID.valueOf(enumName);
    }
}
