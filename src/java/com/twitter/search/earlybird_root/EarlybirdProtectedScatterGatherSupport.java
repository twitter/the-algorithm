package com.tw ter.search.earlyb rd_root;

 mport javax. nject. nject;

 mport com.tw ter.search.common.part  on ng.base.Part  onMapp ngManager;
 mport com.tw ter.search.common.sc ma.earlyb rd.Earlyb rdCluster;
 mport com.tw ter.search.earlyb rd_root.common.Earlyb rdFeatureSc ma rger;

/**
 * T  Earlyb rdServ ceScatterGat rSupport  mple ntat on used to fan out requests to t  earlyb rd
 * part  ons  n t  protected cluster.
 */
publ c class Earlyb rdProtectedScatterGat rSupport extends Earlyb rdServ ceScatterGat rSupport {
  /**
   * Construct a Earlyb rdProtectedScatterGat rSupport to do m nUserFanOut,
   * used only by protected. T  ma n d fference from t  base class  s that
   *  f t  from user  D  s not set, except on  s thrown.
   */
  @ nject
  Earlyb rdProtectedScatterGat rSupport(
      Part  onMapp ngManager part  onMapp ngManager,
      Earlyb rdFeatureSc ma rger featureSc ma rger) {
    super(part  onMapp ngManager, Earlyb rdCluster.PROTECTED, featureSc ma rger);
  }
}
