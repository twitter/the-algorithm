package com.tw ter.search. ngester.p pel ne.ut l;

 mport com.google.common.base.Str ngs;

 mport com.tw ter.expandodo.thr ftjava.B nd ngValue;
 mport com.tw ter.expandodo.thr ftjava.B nd ngValueType;
 mport com.tw ter.expandodo.thr ftjava.Card2;
 mport com.tw ter.search.common.ut l.text.Language dent f er lper;
 mport com.tw ter.search. ngester.model. ngesterTw ter ssage;

publ c f nal class CardF eldUt l {

  pr vate CardF eldUt l() {
    /* prevent  nstant at on */
  }

  /**
   * B nd ng Keys for card f elds
   */
  publ c stat c f nal Str ng T TLE_B ND NG_KEY = "t le";
  publ c stat c f nal Str ng DESCR PT ON_B ND NG_KEY = "descr pt on";

  /**
   * g ven a b nd ngKey and card, w ll return t  b nd ngValue of t  g ven b nd ngKey
   *  f present  n card.getB nd ng_values().  f no match  s found return null.
   */
  publ c stat c Str ng extractB nd ngValue(Str ng b nd ngKey, Card2 card) {
    for (B nd ngValue b nd ngValue : card.getB nd ng_values()) {
       f ((b nd ngValue != null)
          && b nd ngValue. sSetType()
          && (b nd ngValue.getType() == B nd ngValueType.STR NG)
          && b nd ngKey.equals(b nd ngValue.getKey())) {
        return b nd ngValue.getStr ng_value();
      }
    }
    return null;
  }

  /**
   * der ves card lang from t le + descr pt on and sets    n Tw ter ssage.
   */
  publ c stat c vo d der veCardLang( ngesterTw ter ssage  ssage) {
     ssage.setCardLang(Language dent f er lper. dent fyLanguage(Str ng.format("%s %s",
        Str ngs.nullToEmpty( ssage.getCardT le()),
        Str ngs.nullToEmpty( ssage.getCardDescr pt on()))).getLanguage());
  }
}

