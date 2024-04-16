package com.tw ter.search.earlyb rd_root;

 mport javax. nject. nject;

 mport com.tw ter.search.common.part  on ng.base.Part  onMapp ngManager;
 mport com.tw ter.search.common.sc ma.earlyb rd.Earlyb rdCluster;
 mport com.tw ter.search.earlyb rd_root.common.Earlyb rdFeatureSc ma rger;

/**
 * T  Earlyb rdServ ceScatterGat rSupport  mple ntat on used to fan out requests to t  earlyb rd
 * part  ons  n t  realt   cluster.
 */
publ c class Earlyb rdRealt  ScatterGat rSupport extends Earlyb rdServ ceScatterGat rSupport {
  /** Creates a new Earlyb rdRealt  ScatterGat rSupport  nstance. */
  @ nject
  Earlyb rdRealt  ScatterGat rSupport(
      Part  onMapp ngManager part  onMapp ngManager,
      Earlyb rdFeatureSc ma rger featureSc ma rger) {
    super(part  onMapp ngManager, Earlyb rdCluster.REALT ME, featureSc ma rger);
  }
}
