package com.tw ter.search.earlyb rd_root.f lters;

 mport java.ut l.Collect ons;
 mport java.ut l.Map;

 mport javax. nject. nject;

 mport com.google.common.collect.Maps;

 mport com.tw ter.f nagle.Serv ce;
 mport com.tw ter.f nagle.S mpleF lter;
 mport com.tw ter.search.common.dec der.SearchDec der;
 mport com.tw ter.search.common. tr cs.SearchCounter;
 mport com.tw ter.search.common.sc ma.earlyb rd.Earlyb rdCluster;
 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdResponse;
 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdResponseCode;
 mport com.tw ter.search.earlyb rd_root.common.Earlyb rdRequestContext;
 mport com.tw ter.search.earlyb rd_root.common.Earlyb rdRequestType;
 mport com.tw ter.ut l.Future;

/**
 * A F nagle f lter that determ nes  f a certa n cluster  s ava lable to t  SuperRoot.
 *
 * Normally, all clusters should be ava lable. Ho ver,  f t re's a problem w h   systems, and
 *   search clusters are caus ng  ssues for ot r serv ces (t   outs, for example), t n   m ght
 * want to be d sable t m, and return errors to   cl ents.
 */
publ c class Earlyb rdClusterAva lableF lter
    extends S mpleF lter<Earlyb rdRequestContext, Earlyb rdResponse> {
  pr vate f nal SearchDec der dec der;
  pr vate f nal Earlyb rdCluster cluster;
  pr vate f nal Str ng allRequestsDec derKey;
  pr vate f nal Map<Earlyb rdRequestType, Str ng> requestTypeDec derKeys;
  pr vate f nal Map<Earlyb rdRequestType, SearchCounter> d sabledRequests;

  /**
   * Creates a new Earlyb rdClusterAva lableF lter  nstance.
   *
   * @param dec der T  dec der to use to determ ne  f t  cluster  s ava lable.
   * @param cluster T  cluster.
   */
  @ nject
  publ c Earlyb rdClusterAva lableF lter(SearchDec der dec der, Earlyb rdCluster cluster) {
    t .dec der = dec der;
    t .cluster = cluster;

    Str ng clusterNa  = cluster.getNa ForStats();
    t .allRequestsDec derKey = "superroot_" + clusterNa  + "_cluster_ava lable_for_all_requests";

    Map<Earlyb rdRequestType, Str ng> tempDec derKeys = Maps.newEnumMap(Earlyb rdRequestType.class);
    Map<Earlyb rdRequestType, SearchCounter> tempCounters =
      Maps.newEnumMap(Earlyb rdRequestType.class);
    for (Earlyb rdRequestType requestType : Earlyb rdRequestType.values()) {
      Str ng requestTypeNa  = requestType.getNormal zedNa ();
      tempDec derKeys.put(requestType, "superroot_" + clusterNa  + "_cluster_ava lable_for_"
                          + requestTypeNa  + "_requests");
      tempCounters.put(requestType, SearchCounter.export(
                           "cluster_ava lable_f lter_" + clusterNa  + "_"
                           + requestTypeNa  + "_d sabled_requests"));
    }
    requestTypeDec derKeys = Collect ons.unmod f ableMap(tempDec derKeys);
    d sabledRequests = Collect ons.unmod f ableMap(tempCounters);
  }

  @Overr de
  publ c Future<Earlyb rdResponse> apply(
      Earlyb rdRequestContext requestContext,
      Serv ce<Earlyb rdRequestContext, Earlyb rdResponse> serv ce) {
    Earlyb rdRequestType requestType = requestContext.getEarlyb rdRequestType();
     f (!dec der. sAva lable(allRequestsDec derKey)
        || !dec der. sAva lable(requestTypeDec derKeys.get(requestType))) {
      d sabledRequests.get(requestType). ncre nt();
      return Future.value(
          errorResponse("T  " + cluster.getNa ForStats() + " cluster  s not ava lable for "
                        + requestType.getNormal zedNa () + " requests."));
    }

    return serv ce.apply(requestContext);
  }

  pr vate Earlyb rdResponse errorResponse(Str ng debug ssage) {
    return new Earlyb rdResponse(Earlyb rdResponseCode.PERS STENT_ERROR, 0)
      .setDebugStr ng(debug ssage);
  }
}
