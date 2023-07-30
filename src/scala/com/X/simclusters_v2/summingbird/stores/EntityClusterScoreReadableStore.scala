package com.X.simclusters_v2.summingbird.stores

import com.X.finagle.mtls.authentication.ServiceIdentifier
import com.X.frigate.common.store.strato.StratoStore
import com.X.simclusters_v2.summingbird.common.Implicits.clustersWithScoreMonoid
import com.X.simclusters_v2.summingbird.common.Implicits.clustersWithScoresCodec
import com.X.storehaus.algebra.MergeableStore
import com.X.simclusters_v2.summingbird.common.ClientConfigs
import com.X.simclusters_v2.summingbird.common.Implicits
import com.X.simclusters_v2.thriftscala.ClustersWithScores
import com.X.simclusters_v2.thriftscala.FullClusterIdBucket
import com.X.simclusters_v2.thriftscala.MultiModelClustersWithScores
import com.X.simclusters_v2.thriftscala.SimClusterEntity
import com.X.storehaus.Store
import com.X.storehaus_internal.memcache.Memcache
import com.X.strato.client.Client
import com.X.summingbird.batch.BatchID
import com.X.summingbird_internal.bijection.BatchPairImplicits
import com.X.util.Future
import com.X.strato.thrift.ScroogeConvImplicits._

object EntityClusterScoreReadableStore {

  private[simclusters_v2] final lazy val onlineMergeableStore: (
    String,
    ServiceIdentifier
  ) => MergeableStore[
    ((SimClusterEntity, FullClusterIdBucket), BatchID),
    ClustersWithScores
  ] = { (path: String, serviceIdentifier: ServiceIdentifier) =>
    Memcache
      .getMemcacheStore[((SimClusterEntity, FullClusterIdBucket), BatchID), ClustersWithScores](
        ClientConfigs.entityClusterScoreMemcacheConfig(path, serviceIdentifier)
      )(
        BatchPairImplicits.keyInjection[(SimClusterEntity, FullClusterIdBucket)](
          Implicits.entityWithClusterInjection
        ),
        clustersWithScoresCodec,
        clustersWithScoreMonoid
      )
  }

}

object MultiModelEntityClusterScoreReadableStore {

  private[simclusters_v2] def MultiModelEntityClusterScoreReadableStore(
    stratoClient: Client,
    column: String
  ): Store[EntityClusterId, MultiModelClustersWithScores] = {
    StratoStore
      .withUnitView[(SimClusterEntity, Int), MultiModelClustersWithScores](stratoClient, column)
      .composeKeyMapping(_.toTuple)
  }

  case class EntityClusterId(
    simClusterEntity: SimClusterEntity,
    clusterIdBucket: Int) {
    lazy val toTuple: (SimClusterEntity, Int) =
      (simClusterEntity, clusterIdBucket)
  }
}
