package com.tw ter.s mclusters_v2.score

 mport com.tw ter.s mclusters_v2.thr ftscala.{Score d => Thr ftScore d, Score => Thr ftScore}
 mport com.tw ter.storehaus.ReadableStore

/**
 * A wrapper class, used to aggregate t  scores calculated by ot r score stores.   rel es on t 
 * results of ot r ScoreStores reg stered  n t  ScoreFacadeStore.
 */
tra  AggregatedScoreStore extends ReadableStore[Thr ftScore d, Thr ftScore] {

  // T  underly ngScoreStore rel es on [[ScoreFacadeStore]] to f n sh t  dependency  nject on.
  protected var scoreFacadeStore: ReadableStore[Thr ftScore d, Thr ftScore] = ReadableStore.empty

  /**
   * W n reg ster ng t  store  n a ScoreFacadeStore, t  facade store calls t  funct on to
   * prov de references to ot r score stores.
   */
  pr vate[score] def set(facadeStore: ReadableStore[Thr ftScore d, Thr ftScore]): Un  = {
    t .synchron zed {
      scoreFacadeStore = facadeStore
    }
  }
}
