package com.tw ter.search.common.sc ma.base;

 mport java.ut l.ArrayL st;
 mport java.ut l.HashSet;
 mport java.ut l.L st;
 mport java.ut l.Set;

 mport com.tw ter.search.common.sc ma.thr ftjava.Thr ftDocu nt;
 mport com.tw ter.search.common.sc ma.thr ftjava.Thr ftF eld;

/**
 * Ut l y AP s for Thr ftDocu nt.
 */
publ c f nal class Thr ftDocu ntUt l {
  pr vate Thr ftDocu ntUt l() {
  }

  /**
   * Get Thr ftF eld out of a Thr ftDocu nt.
   */
  publ c stat c Thr ftF eld getF eld(Thr ftDocu nt thr ftDoc,
                                     Str ng f eldNa ,
                                     F eldNa To dMapp ng  dMap) {
     nt  d =  dMap.getF eld D(f eldNa );
    for (Thr ftF eld f eld : thr ftDoc.getF elds()) {
       nt f eld d = f eld.getF eldConf g d();
       f (f eld d ==  d) {
        return f eld;
      }
    }

    return null;
  }

  /**
   * Get all f elds out of a Thr ftDocu nt that match t  g ven f eld na .
   */
  publ c stat c L st<Thr ftF eld> getF elds(
      Thr ftDocu nt thr ftDoc, Str ng f eldNa , F eldNa To dMapp ng  dMap) {

     nt  d =  dMap.getF eld D(f eldNa );
    L st<Thr ftF eld> result = new ArrayL st<>();

    for (Thr ftF eld f eld : thr ftDoc.getF elds()) {
       nt f eld d = f eld.getF eldConf g d();
       f (f eld d ==  d) {
        result.add(f eld);
      }
    }

    return result;
  }


  /**
   * Retr eve t  long value from a thr ft f eld
   */
  publ c stat c long getLongValue(Thr ftDocu nt thr ftDoc,
                                  Str ng f eldNa ,
                                  F eldNa To dMapp ng  dMap) {
    Thr ftF eld f = getF eld(thr ftDoc, f eldNa ,  dMap);
    return f == null ? 0L : f.getF eldData().getLongValue();
  }

  /**
   * Retr eve t  byte value from a thr ft f eld
   */
  publ c stat c byte getByteValue(Thr ftDocu nt thr ftDoc,
                                  Str ng f eldNa ,
                                  F eldNa To dMapp ng  dMap) {
    Thr ftF eld f = getF eld(thr ftDoc, f eldNa ,  dMap);
    return f == null ? (byte) 0 : f.getF eldData().getByteValue();
  }

  /**
   * Retr eve t  bytes value from a thr ft f eld
   */
  publ c stat c byte[] getBytesValue(Thr ftDocu nt thr ftDoc,
                                     Str ng f eldNa ,
                                     F eldNa To dMapp ng  dMap) {
    Thr ftF eld f = getF eld(thr ftDoc, f eldNa ,  dMap);
    return f == null ? null : f.getF eldData().getBytesValue();
  }

  /**
   * Retr eve t   nt value from a thr ft f eld
   */
  publ c stat c  nt get ntValue(Thr ftDocu nt thr ftDoc,
                                Str ng f eldNa ,
                                F eldNa To dMapp ng  dMap) {
    Thr ftF eld f = getF eld(thr ftDoc, f eldNa ,  dMap);
    return f == null ? 0 : f.getF eldData().get ntValue();
  }

  /**
   * Retr eve t  str ng value from a thr ft f eld
   */
  publ c stat c Str ng getStr ngValue(Thr ftDocu nt thr ftDoc,
                                      Str ng f eldNa ,
                                      F eldNa To dMapp ng  dMap) {
    Thr ftF eld f = getF eld(thr ftDoc, f eldNa ,  dMap);
    return f == null ? null : f.getF eldData().getStr ngValue();
  }

  /**
   * Retr eve t  str ng values from all thr ft f elds w h t  g ven f eldNa .
   */
  publ c stat c L st<Str ng> getStr ngValues(
      Thr ftDocu nt thr ftDoc,
      Str ng f eldNa ,
      F eldNa To dMapp ng  dMap) {
    L st<Thr ftF eld> f elds = getF elds(thr ftDoc, f eldNa ,  dMap);
    L st<Str ng> f eldStr ngs = new ArrayL st<>();

    for (Thr ftF eld f eld : f elds) {
      f eldStr ngs.add(f eld.getF eldData().getStr ngValue());
    }
    return f eldStr ngs;
  }

  /**
   * Returns w t r t  spec f ed docu nt has dupl cate f elds.
   */
  publ c stat c boolean hasDupl cateF elds(Thr ftDocu nt thr ftDoc) {
    Set< nteger> seen = new HashSet<>();
    for (Thr ftF eld f eld : thr ftDoc.getF elds()) {
       f (!seen.add(f eld.getF eldConf g d())) {
        return true;
      }
    }
    return false;
  }

  /**
   * Get Thr ftF eld out of a Thr ftDocu nt.
   */
  publ c stat c Thr ftF eld getF eld(Thr ftDocu nt thr ftDoc,  nt f eld d) {
    for (Thr ftF eld f eld : thr ftDoc.getF elds()) {
       f (f eld.getF eldConf g d() == f eld d) {
        return f eld;
      }
    }

    return null;
  }
}
