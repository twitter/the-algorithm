package com.tw ter.search.earlyb rd.docu nt;

 mport java. o. OExcept on;

 mport com.google.common.annotat ons.V s bleForTest ng;
 mport com.google.common.base.Precond  ons;

 mport org.apac .lucene.docu nt.Docu nt;

 mport com.tw ter.dec der.Dec der;
 mport com.tw ter.search.common.sc ma.Sc maDocu ntFactory;
 mport com.tw ter.search.common.sc ma.base.F eldNa To dMapp ng;
 mport com.tw ter.search.common.sc ma.base. mmutableSc ma nterface;
 mport com.tw ter.search.common.sc ma.base.Sc ma;
 mport com.tw ter.search.common.sc ma.base.Thr ftDocu ntUt l;
 mport com.tw ter.search.common.sc ma.earlyb rd.Earlyb rdCluster;
 mport com.tw ter.search.common.sc ma.earlyb rd.Earlyb rdF eldConstants;
 mport com.tw ter.search.common.sc ma.earlyb rd.Earlyb rdF eldConstants.Earlyb rdF eldConstant;
 mport com.tw ter.search.common.sc ma.thr ftjava.Thr ftDocu nt;
 mport com.tw ter.search.common.sc ma.thr ftjava.Thr ft ndex ngEvent;
 mport com.tw ter.search.earlyb rd.except on.Cr  calExcept onHandler;

/**
 * Bu lds a Lucene Docu nt from a Thr ft ndex ngEvent. A s mpl f ed vers on of
 * {@l nk Thr ft ndex ngEventDocu ntFactory} that can be used for update events, wh ch exclude
 * many f elds that t  t et  ndex ng events conta n.
 */
publ c class Thr ft ndex ngEventUpdateFactory extends Docu ntFactory<Thr ft ndex ngEvent> {
  pr vate stat c f nal F eldNa To dMapp ng  D_MAPP NG = new Earlyb rdF eldConstants();

  pr vate f nal Sc maDocu ntFactory sc maDocu ntFactory;
  pr vate f nal Earlyb rdCluster cluster;
  pr vate f nal Sc ma sc ma;

  publ c Thr ft ndex ngEventUpdateFactory(
      Sc ma sc ma,
      Earlyb rdCluster cluster,
      Dec der dec der,
      Cr  calExcept onHandler cr  calExcept onHandler) {
    t (
        sc ma,
        Thr ft ndex ngEventDocu ntFactory.getSc maDocu ntFactory(sc ma, cluster, dec der),
        cluster,
        cr  calExcept onHandler
    );
  }

  @V s bleForTest ng
  protected Thr ft ndex ngEventUpdateFactory(
      Sc ma sc ma,
      Sc maDocu ntFactory sc maDocu ntFactory,
      Earlyb rdCluster cluster,
      Cr  calExcept onHandler cr  calExcept onHandler) {
    super(cr  calExcept onHandler);
    t .sc ma = sc ma;
    t .sc maDocu ntFactory = sc maDocu ntFactory;
    t .cluster = cluster;
  }

  @Overr de
  publ c long getStatus d(Thr ft ndex ngEvent event) {
    Precond  ons.c ckNotNull(event);
    Precond  ons.c ckState(
        event. sSetDocu nt(), "Thr ftDocu nt  s null  ns de Thr ft ndex ngEvent.");

    Thr ftDocu nt thr ftDocu nt;
    try {
      //  deally,   should not call getSc maSnapshot()  re.  But, as t   s called only to
      // retr eve status  d and t   D f eld  s stat c, t   s f ne for t  purpose.
      thr ftDocu nt = Thr ftDocu ntPreprocessor.preprocess(
          event.getDocu nt(), cluster, sc ma.getSc maSnapshot());
    } catch ( OExcept on e) {
      throw new  llegalStateExcept on("Unable to obta n t et  D from Thr ftDocu nt: " + event, e);
    }
    return Thr ftDocu ntUt l.getLongValue(
        thr ftDocu nt, Earlyb rdF eldConstant. D_F ELD.getF eldNa (),  D_MAPP NG);
  }

  @Overr de
  protected Docu nt  nnerNewDocu nt(Thr ft ndex ngEvent event) throws  OExcept on {
    Precond  ons.c ckNotNull(event);
    Precond  ons.c ckNotNull(event.getDocu nt());

     mmutableSc ma nterface sc maSnapshot = sc ma.getSc maSnapshot();

    Thr ftDocu nt docu nt = Thr ftDocu ntPreprocessor.preprocess(
        event.getDocu nt(), cluster, sc maSnapshot);

    return sc maDocu ntFactory.newDocu nt(docu nt);
  }
}
