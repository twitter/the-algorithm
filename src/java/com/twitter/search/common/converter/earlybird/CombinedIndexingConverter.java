package com.tw ter.search.common.converter.earlyb rd;

 mport java. o. OExcept on;
 mport java.ut l.L st;

 mport javax.annotat on.concurrent.NotThreadSafe;

 mport com.google.common.base.Precond  ons;

 mport com.tw ter.common_ nternal.text.vers on.Pengu nVers on;
 mport com.tw ter.search.common. ndex ng.thr ftjava.Thr ftVers onedEvents;
 mport com.tw ter.search.common.relevance.ent  es.Tw ter ssage;
 mport com.tw ter.search.common.sc ma.base. mmutableSc ma nterface;
 mport com.tw ter.search.common.sc ma.base.Sc ma;
 mport com.tw ter.search.common.sc ma.earlyb rd.Earlyb rdCluster;
 mport com.tw ter.search.common.sc ma.earlyb rd.Earlyb rdThr ftDocu ntBu lder;
 mport com.tw ter.search.common.sc ma.thr ftjava.Thr ftDocu nt;
 mport com.tw ter.search.common.sc ma.thr ftjava.Thr ft ndex ngEvent;
 mport com.tw ter.search.common.sc ma.thr ftjava.Thr ft ndex ngEventType;

/**
 * Comb ned ndex ngConverter bu lds objects from Tw ter ssage to Thr ftVers onedEvent.
 *
 *    s used  n tests and  n offl ne jobs, so all data  s ava lable on t  Tw ter ssage. T 
 *  ans that   don't need to spl  up t  Thr ftVers onedEvents  nto bas c events and update
 * events, l ke   do  n t  realt   p pel ne us ng t  Bas c ndex ngConverter and t 
 * Delayed ndex ngConverter.
 */
@NotThreadSafe
publ c class Comb ned ndex ngConverter {
  pr vate f nal EncodedFeatureBu lder featureBu lder;
  pr vate f nal Sc ma sc ma;
  pr vate f nal Earlyb rdCluster cluster;

  publ c Comb ned ndex ngConverter(Sc ma sc ma, Earlyb rdCluster cluster) {
    t .featureBu lder = new EncodedFeatureBu lder();
    t .sc ma = sc ma;
    t .cluster = cluster;
  }

  /**
   * Converts a Tw ter ssage to a Thr ft representat on.
   */
  publ c Thr ftVers onedEvents convert ssageToThr ft(
      Tw ter ssage  ssage,
      boolean str ct,
      L st<Pengu nVers on> pengu nVers ons) throws  OExcept on {
    Precond  ons.c ckNotNull( ssage);
    Precond  ons.c ckNotNull(pengu nVers ons);

    Thr ftVers onedEvents vers onedEvents = new Thr ftVers onedEvents()
        .set d( ssage.get d());

     mmutableSc ma nterface sc maSnapshot = sc ma.getSc maSnapshot();

    for (Pengu nVers on pengu nVers on : pengu nVers ons) {
      Thr ftDocu nt docu nt =
          bu ldDocu ntForPengu nVers on(sc maSnapshot,  ssage, str ct, pengu nVers on);

      Thr ft ndex ngEvent thr ft ndex ngEvent = new Thr ft ndex ngEvent()
          .setDocu nt(docu nt)
          .setEventType(Thr ft ndex ngEventType. NSERT)
          .setSort d( ssage.get d());
       ssage.getFromUserTw ter d().map(thr ft ndex ngEvent::setU d);
      vers onedEvents.putToVers onedEvents(pengu nVers on.getByteValue(), thr ft ndex ngEvent);
    }

    return vers onedEvents;
  }

  pr vate Thr ftDocu nt bu ldDocu ntForPengu nVers on(
       mmutableSc ma nterface sc maSnapshot,
      Tw ter ssage  ssage,
      boolean str ct,
      Pengu nVers on pengu nVers on) throws  OExcept on {
    EncodedFeatureBu lder.T etFeatureW hEncodeFeatures t etFeature =
        featureBu lder.createT etFeaturesFromTw ter ssage(
             ssage, pengu nVers on, sc maSnapshot);

    Earlyb rdThr ftDocu ntBu lder bu lder =
        Bas c ndex ngConverter.bu ldBas cF elds( ssage, sc maSnapshot, cluster, t etFeature);

    Bas c ndex ngConverter
        .bu ldUserF elds(bu lder,  ssage, t etFeature.vers onedFeatures, pengu nVers on);
    Bas c ndex ngConverter.bu ldGeoF elds(bu lder,  ssage, t etFeature.vers onedFeatures);
    Delayed ndex ngConverter.bu ldURLF elds(bu lder,  ssage, t etFeature.encodedFeatures);
    Bas c ndex ngConverter.bu ldRet etAndReplyF elds(bu lder,  ssage, str ct);
    Bas c ndex ngConverter.bu ldQuotesF elds(bu lder,  ssage);
    Bas c ndex ngConverter.bu ldVers onedFeatureF elds(bu lder, t etFeature.vers onedFeatures);
    Delayed ndex ngConverter.bu ldCardF elds(bu lder,  ssage, pengu nVers on);
    Bas c ndex ngConverter.bu ldAnnotat onF elds(bu lder,  ssage);
    Bas c ndex ngConverter.bu ldNormal zedM nEngage ntF elds(
        bu lder, t etFeature.encodedFeatures, cluster);
    Delayed ndex ngConverter.bu ldNa dEnt yF elds(bu lder,  ssage);
    Bas c ndex ngConverter.bu ldD rectedAtF elds(bu lder,  ssage);

    return bu lder.bu ld();
  }
}
