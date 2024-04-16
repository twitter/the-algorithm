package com.tw ter.search.common.sc ma;

 mport java. o. OExcept on;
 mport java.ut l.L st;
 mport java.ut l.logg ng.Level;
 mport java.ut l.logg ng.Logger;

 mport javax.annotat on.Nullable;

 mport com.tw ter.common.text.ut l.Pos  on ncre ntAttr buteSer al zer;
 mport com.tw ter.common.text.ut l.TokenStreamSer al zer;
 mport com.tw ter.search.common.sc ma.base.F eldNa To dMapp ng;
 mport com.tw ter.search.common.sc ma.thr ftjava.Thr ftDocu nt;
 mport com.tw ter.search.common.sc ma.thr ftjava.Thr ftF eld;
 mport com.tw ter.search.common.sc ma.thr ftjava.Thr ftF eldData;
 mport com.tw ter.search.common.sc ma.thr ftjava.Thr ftGeoCoord nate;
 mport com.tw ter.search.common.ut l.analys s.CharTermAttr buteSer al zer;
 mport com.tw ter.search.common.ut l.analys s.LongTermAttr buteSer al zer;
 mport com.tw ter.search.common.ut l.analys s.LongTermsTokenStream;
 mport com.tw ter.search.common.ut l.analys s.PayloadAttr buteSer al zer;
 mport com.tw ter.search.common.ut l.analys s.Payload  ghtedToken zer;
 mport com.tw ter.search.common.ut l.spat al.GeoUt l;

/**
 * Bu lder class for bu ld ng Thr ftDocu nts.
 */
publ c class Thr ftDocu ntBu lder {
  pr vate stat c f nal Logger LOG = Logger.getLogger(Thr ftDocu ntBu lder.class.getNa ());

  protected f nal Thr ftDocu nt doc = new Thr ftDocu nt();
  protected f nal F eldNa To dMapp ng  dMapp ng;

  pr vate stat c f nal ThreadLocal<TokenStreamSer al zer> PAYLOAD_WE GHTED_SER AL ZER_PER_THREAD =
      new ThreadLocal<TokenStreamSer al zer>() {
        @Overr de
        protected TokenStreamSer al zer  n  alValue() {
          return TokenStreamSer al zer.bu lder()
              .add(new CharTermAttr buteSer al zer())
              .add(new Pos  on ncre ntAttr buteSer al zer())
              .add(new PayloadAttr buteSer al zer())
              .bu ld();
        }
      };

  pr vate stat c f nal ThreadLocal<TokenStreamSer al zer> LONG_TERM_SER AL ZER_PER_THREAD =
          new ThreadLocal<TokenStreamSer al zer>() {
            @Overr de
            protected TokenStreamSer al zer  n  alValue() {
              return TokenStreamSer al zer.bu lder()
                  .add(new LongTermAttr buteSer al zer())
                  .bu ld();
            }
          };

  publ c Thr ftDocu ntBu lder(F eldNa To dMapp ng  dMapp ng) {
    t . dMapp ng =  dMapp ng;
  }

  protected vo d prepareToBu ld() {
    // left empty, subclass can overr de t .
  }

  publ c Thr ftDocu nt bu ld() {
    prepareToBu ld();
    return doc;
  }

  /**
   * Add a long f eld. T   s  ndexed as a
   * {@l nk com.tw ter.search.common.ut l.analys s.LongTermAttr bute}
   */
  publ c f nal Thr ftDocu ntBu lder w hLongF eld(Str ng f eldNa , long value) {
    Thr ftF eldData f eldData = new Thr ftF eldData().setLongValue(value);
    Thr ftF eld f eld = new Thr ftF eld()
        .setF eldConf g d( dMapp ng.getF eld D(f eldNa )).setF eldData(f eldData);
    doc.addToF elds(f eld);
    return t ;
  }

  /**
   * Add an  nt f eld. T   s  ndexed as a
   * {@l nk com.tw ter.search.common.ut l.analys s. ntTermAttr bute}
   */
  publ c f nal Thr ftDocu ntBu lder w h ntF eld(Str ng f eldNa ,  nt value) {
    Thr ftF eldData f eldData = new Thr ftF eldData().set ntValue(value);
    Thr ftF eld f eld = new Thr ftF eld()
        .setF eldConf g d( dMapp ng.getF eld D(f eldNa )).setF eldData(f eldData);
    doc.addToF elds(f eld);
    return t ;
  }

  /**
   * Add a f eld whose value  s a s ngle byte.
   */
  publ c f nal Thr ftDocu ntBu lder w hByteF eld(Str ng f eldNa , byte value) {
    Thr ftF eldData f eldData = new Thr ftF eldData().setByteValue(value);
    Thr ftF eld f eld = new Thr ftF eld()
        .setF eldConf g d( dMapp ng.getF eld D(f eldNa )).setF eldData(f eldData);
    doc.addToF elds(f eld);
    return t ;
  }

  /**
   * Add a f eld whose value  s a byte array.
   */
  publ c f nal Thr ftDocu ntBu lder w hBytesF eld(Str ng f eldNa , byte[] value) {
    Thr ftF eldData f eldData = new Thr ftF eldData().setBytesValue(value);
    Thr ftF eld f eld = new Thr ftF eld()
        .setF eldConf g d( dMapp ng.getF eld D(f eldNa )).setF eldData(f eldData);
    doc.addToF elds(f eld);
    return t ;
  }

  /**
   * Add a f eld whose value  s a float.
   */
  publ c f nal Thr ftDocu ntBu lder w hFloatF eld(Str ng f eldNa , float value) {
    Thr ftF eldData f eldData = new Thr ftF eldData().setFloatValue(value);
    Thr ftF eld f eld = new Thr ftF eld()
        .setF eldConf g d( dMapp ng.getF eld D(f eldNa )).setF eldData(f eldData);
    doc.addToF elds(f eld);
    return t ;
  }

  /**
   * Added a f eld whose value  s a Lucene TokenStream.
   * T  Lucene TokenStream  s ser al zed us ng Tw ter's
   * {@l nk com.tw ter.common.text.ut l.TokenStreamSer al zer}
   */
  publ c f nal Thr ftDocu ntBu lder w hTokenStreamF eld(Str ng f eldNa ,
                                                          @Nullable Str ng tokenStreamText,
                                                          byte[] tokenStream) {
     f (tokenStream == null) {
      return t ;
    }
    Thr ftF eldData f eldData = new Thr ftF eldData()
        .setStr ngValue(tokenStreamText).setTokenStreamValue(tokenStream);
    Thr ftF eld f eld = new Thr ftF eld()
        .setF eldConf g d( dMapp ng.getF eld D(f eldNa )).setF eldData(f eldData);
    doc.addToF elds(f eld);
    return t ;
  }

  /**
   * Add a f eld whose value  s a Str ng.
   * @param f eldNa  Na  of t  f eld w re t  str ng w ll be added.
   * @param text T  str ng  s  ndexed as  s (not analyzed).
   */
  publ c f nal Thr ftDocu ntBu lder w hStr ngF eld(Str ng f eldNa , Str ng text) {
     f (text == null || text. sEmpty()) {
      return t ;
    }

    Thr ftF eldData f eldData = new Thr ftF eldData().setStr ngValue(text);
    Thr ftF eld f eld = new Thr ftF eld()
        .setF eldConf g d( dMapp ng.getF eld D(f eldNa )).setF eldData(f eldData);
    doc.addToF elds(f eld);
    return t ;
  }

  /**
   * Add a f eld whose value  s a geo coord nate.
   * Earlyb rd w ll process t  coord nates  nto geo has s before  ndex ng.
   */
  publ c f nal Thr ftDocu ntBu lder w hGeoF eld(Str ng f eldNa ,
                                                  double lat, double lon,  nt acc) {
     f (!GeoUt l.val dateGeoCoord nates(lat, lon)) {
      //  f t  geo coord nates are  nval d, don't add any f eld.
      return t ;
    }
    Thr ftGeoCoord nate coord = new Thr ftGeoCoord nate();
    coord.setLat(lat);
    coord.setLon(lon);
    coord.setAccuracy(acc);

    Thr ftF eldData f eldData = new Thr ftF eldData().setGeoCoord nate(coord);
    Thr ftF eld f eld = new Thr ftF eld()
        .setF eldConf g d( dMapp ng.getF eld D(f eldNa )).setF eldData(f eldData);
    doc.addToF elds(f eld);
    return t ;
  }

  /**
   * Added a l st of tokens that are   ghted. T    ghts are stored  ns de payload.
   * See {@l nk com.tw ter.search.common.ut l.analys s.Payload  ghtedToken zer} for more deta ls.
   */
  publ c f nal Thr ftDocu ntBu lder w hPayload  ghtTokenStreamF eld(Str ng f eldNa ,
                                                                       Str ng tokens) {
    byte[] ser al zed;
    try {
      Payload  ghtedToken zer token zer = new Payload  ghtedToken zer(tokens);
      ser al zed = PAYLOAD_WE GHTED_SER AL ZER_PER_THREAD.get().ser al ze(token zer);
      token zer.close();
    } catch ( OExcept on e) {
      LOG.log(Level.WARN NG,
          "Fa led to add Payload  ghtedToken zer f eld. Bad token   ght l st: " + tokens, e);
      return t ;
    } catch (NumberFormatExcept on e) {
      LOG.log(Level.WARN NG,
          "Fa led to add Payload  ghtedToken zer f eld. Cannot parse token   ght: " + tokens, e);
      return t ;
    }
    w hTokenStreamF eld(f eldNa , tokens, ser al zed);
    return t ;
  }

  /**
   * Add a f eld whose value  s a l st of longs.
   * Each long  s encoded  nto a LongTermAttr bute.
   * T  f eld w ll conta n a LongTermTokenStream.
   */
  publ c f nal Thr ftDocu ntBu lder w hLong DsF eld(Str ng f eldNa ,
      L st<Long> longL st)  throws  OExcept on {

     f (longL st == null || longL st. sEmpty()) {
        return t ;
    }
    LongTermsTokenStream stream = new LongTermsTokenStream(longL st);
    stream.reset();
    byte[] ser al zedStream = LONG_TERM_SER AL ZER_PER_THREAD.get().ser al ze(stream);

    Thr ftF eldData f eldData = new Thr ftF eldData().setTokenStreamValue(ser al zedStream);
    Thr ftF eld f eld = new Thr ftF eld()
        .setF eldConf g d( dMapp ng.getF eld D(f eldNa )).setF eldData(f eldData);
    doc.addToF elds(f eld);
    return t ;
  }
}
