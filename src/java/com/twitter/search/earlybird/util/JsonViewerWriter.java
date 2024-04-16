package com.tw ter.search.earlyb rd.ut l;

 mport java. o. OExcept on;
 mport java. o.Wr er;

 mport com.google.gson.stream.JsonWr er;

/**
 * Wrapper class for JsonWr er that  mple nts t 
 * V e rWr er  nterface.
 */
publ c class JsonV e rWr er  mple nts V e rWr er {

  pr vate f nal JsonWr er wr er;
  pr vate f nal Wr er out;

  publ c JsonV e rWr er(Wr er out) {
    t .out = out;
    t .wr er = new JsonWr er(out);
  }


  @Overr de
  publ c V e rWr er beg nArray() throws  OExcept on {
    wr er.beg nArray();
    return t ;
  }

  @Overr de
  publ c V e rWr er beg nObject() throws  OExcept on {
    wr er.beg nObject();
    return t ;
  }

  @Overr de
  publ c V e rWr er endArray() throws  OExcept on {
    wr er.endArray();
    return t ;
  }

  @Overr de
  publ c V e rWr er endObject() throws  OExcept on {
    wr er.endObject();
    return t ;
  }

  @Overr de
  publ c V e rWr er na (Str ng f eld) throws  OExcept on {
    wr er.na (f eld);
    return t ;
  }

  @Overr de
  publ c V e rWr er value(Str ng s) throws  OExcept on {
    wr er.value(s);
    return t ;
  }

  @Overr de
  publ c V e rWr er newl ne() throws  OExcept on {
    out.append('\n');
    return t ;
  }

  publ c vo d flush() throws  OExcept on {
    out.flush();
  }
}
