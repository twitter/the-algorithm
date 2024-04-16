package com.tw ter.search.earlyb rd.factory;

 mport org.slf4j.Logger;
 mport org.slf4j.LoggerFactory;

 mport com.tw ter.search.earlyb rd.common.conf g.Earlyb rdProperty;
 mport com.tw ter.search.earlyb rd.conf g.T erConf g;
 mport com.tw ter.search.earlyb rd.conf g.T er nfo;
 mport com.tw ter.search.earlyb rd.part  on.Part  onConf g;

publ c f nal class Part  onConf gUt l {
  pr vate stat c f nal Logger LOG = LoggerFactory.getLogger(Part  onConf gUt l.class);

  pr vate Part  onConf gUt l() {
  }

  /**
   *  n  ate Part  onConf g for earlyb rds runn ng on Aurora
   */
  publ c stat c Part  onConf g  n Part  onConf gForAurora( nt numOf nstances) {
    Str ng t er = Earlyb rdProperty.EARLYB RD_T ER.get();
     nt part  on d = Earlyb rdProperty.PART T ON_ D.get();
     nt repl ca d = Earlyb rdProperty.REPL CA_ D.get();
     f (t er.equals(Part  onConf g.DEFAULT_T ER_NAME)) {
      // realt   or protected earlyb rd
      return new Part  onConf g(
          part  on d,
          Earlyb rdProperty.SERV NG_T MESL CES.get(),
          repl ca d,
          numOf nstances,
          Earlyb rdProperty.NUM_PART T ONS.get());
    } else {
      // arch ve earlyb rd
      T er nfo t er nfo = T erConf g.getT er nfo(t er);
      return new Part  onConf g(t er, t er nfo.getDataStartDate(), t er nfo.getDataEndDate(),
          part  on d, t er nfo.getMaxT  sl ces(), repl ca d, numOf nstances,
          t er nfo.getNumPart  ons());
    }
  }

  /**
   * Tr es to create a new Part  onConf g  nstance based on t  Aurora flags
   */
  publ c stat c Part  onConf g  n Part  onConf g() {
    return  n Part  onConf gForAurora(Earlyb rdProperty.NUM_ NSTANCES.get());
  }
}
