package com.twitter.simclusters_v2.score

import com.twitter.simclusters_v2.thriftscala.{ScoreId => ThriftScoreId, Score => ThriftScore}
import com.twitter.storehaus.ReadableStore

/**
 * A wrapper class, used to aggregate the scores calculated by other score stores. It relies on the
 * results of other ScoreStores registered in the ScoreFacadeStore.
 */
trait AggregatedScoreStore extends ReadableStore[ThriftScoreId, ThriftScore] {

  // The underlyingScoreStore relies on [[ScoreFacadeStore]] to finish the dependency injection.
  protected var scoreFacadeStore: ReadableStore[ThriftScoreId, ThriftScore] = ReadableStore.empty

  /**
   * When registering this store in a ScoreFacadeStore, the facade store calls this function to
   * provide references to other score stores.
   */
  private[score] def set(facadeStore: ReadableStore[ThriftScoreId, ThriftScore]): Unit = {
    this.synchronized {
      scoreFacadeStore = facadeStore
    }
  }
}
