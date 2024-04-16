package com.tw ter.search.common.sc ma.earlyb rd;

 mport com.google.common.base.Precond  ons;
 mport com.google.common.collect. mmutableL st;

 mport com.tw ter.common.text.ut l.TokenStreamSer al zer;
 mport com.tw ter.search.common.sc ma.Sc maBu lder;
 mport com.tw ter.search.common.sc ma.base.F eldNa To dMapp ng;
 mport com.tw ter.search.common.sc ma.thr ftjava.Thr ftF eldConf gurat on;
 mport com.tw ter.search.common.sc ma.thr ftjava.Thr ftF eldSett ngs;
 mport com.tw ter.search.common.sc ma.thr ftjava.Thr ftTokenStreamSer al zer;
 mport com.tw ter.search.common.ut l.analys s.CharTermAttr buteSer al zer;
 mport com.tw ter.search.common.ut l.analys s.TermPayloadAttr buteSer al zer;

/**
 * Bu ld class used to bu ld a Thr ftSc ma
 */
publ c class Earlyb rdSc maBu lder extends Sc maBu lder {
  pr vate f nal Earlyb rdCluster cluster;

  publ c Earlyb rdSc maBu lder(F eldNa To dMapp ng  dMapp ng,
                                Earlyb rdCluster cluster,
                                TokenStreamSer al zer.Vers on tokenStreamSer al zerVers on) {
    super( dMapp ng, tokenStreamSer al zerVers on);
    t .cluster = cluster;
  }

  /**
   * Conf gure t  spec f ed f eld to be Out-of-order.
   *  n t  realt   cluster, t  causes Earlyb rd to used t  sk p l st post ng format.
   */
  publ c f nal Earlyb rdSc maBu lder w hOutOfOrderEnabledForF eld(Str ng f eldNa ) {
     f (!should ncludeF eld(f eldNa )) {
      return t ;
    }
    Thr ftF eldSett ngs sett ngs =
        sc ma.getF eldConf gs().get( dMapp ng.getF eld D(f eldNa )).getSett ngs();
    Precond  ons.c ckState(sett ngs. sSet ndexedF eldSett ngs(),
                             "Out of order f eld must be  ndexed");
    sett ngs.get ndexedF eldSett ngs().setSupportOutOfOrderAppends(true);
    return t ;
  }

  /**
   * T  turns on t et spec f c normal zat ons. T  turns on t  follow ng two token processors:
   * {@l nk com.tw ter.search.common.ut l.text.spl ter.Hashtag nt onPunctuat onSpl ter}
   * {@l nk com.tw ter.search.common.ut l.text.f lter.Normal zedTokenF lter}
   * <p/>
   * Hashtag nt onPunctuat onSpl ter would break a  nt on or hashtag l ke @ab_cd or #ab_cd  nto
   * tokens {ab, cd}.
   * Normal zedTokenF lter str ps out t  # @ $ from t  tokens.
   */
  publ c f nal Earlyb rdSc maBu lder w hT etSpec f cNormal zat on(Str ng f eldNa ) {
     f (!should ncludeF eld(f eldNa )) {
      return t ;
    }
    Thr ftF eldSett ngs sett ngs =
        sc ma.getF eldConf gs().get( dMapp ng.getF eld D(f eldNa )).getSett ngs();
    Precond  ons.c ckState(sett ngs. sSet ndexedF eldSett ngs(),
                             "T et text f eld must be  ndexed.");
    sett ngs.get ndexedF eldSett ngs().setDeprecated_performT etSpec f cNormal zat ons(true);
    return t ;
  }

  /**
   * Add a tw ter photo facet f eld.
   */
  publ c f nal Earlyb rdSc maBu lder w hPhotoUrlFacetF eld(Str ng f eldNa ) {
     f (!should ncludeF eld(f eldNa )) {
      return t ;
    }
    Thr ftF eldSett ngs photoF eldSett ngs = getNoPos  onNoFreqSett ngs();
    Thr ftTokenStreamSer al zer tokenStreamSer al zer =
        new Thr ftTokenStreamSer al zer(tokenStreamSer al zerVers on);
    tokenStreamSer al zer.setAttr buteSer al zerClassNa s(
         mmutableL st.<Str ng>of(
            CharTermAttr buteSer al zer.class.getNa (),
            TermPayloadAttr buteSer al zer.class.getNa ()));
    photoF eldSett ngs
        .get ndexedF eldSett ngs()
        .setTokenStreamSer al zer(tokenStreamSer al zer)
        .setToken zed(true);
    put ntoF eldConf gs( dMapp ng.getF eld D(f eldNa ),
                        new Thr ftF eldConf gurat on(f eldNa ).setSett ngs(photoF eldSett ngs));
    return t ;
  }

  /**
   * Returns w t r t  g ven f eld should be  ncluded or dropped.
   */
  @Overr de
  protected boolean should ncludeF eld(Str ng f eldNa ) {
    return Earlyb rdF eldConstants.getF eldConstant(f eldNa ). sVal dF eld nCluster(cluster);
  }
}

