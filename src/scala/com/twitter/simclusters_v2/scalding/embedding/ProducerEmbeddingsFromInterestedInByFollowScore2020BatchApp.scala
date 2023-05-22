package com.twitter.simclusters_v2.scalding.embedding

import com.twitter.dal.client.dataset.KeyValDALDataset
import com.twitter.scalding.*
import com.twitter.scalding_internal.dalv2.DALWrite.*
import com.twitter.scalding_internal.multiformat.format.keyval.KeyVal
import com.twitter.simclusters_v2.common.ModelVersions
import com.twitter.simclusters_v2.hdfs_sources.*
import com.twitter.simclusters_v2.scalding.embedding.common.EmbeddingUtil.*
import com.twitter.simclusters_v2.scalding.embedding.common.SimClustersEmbeddingJob
import com.twitter.simclusters_v2.thriftscala.*
import com.twitter.wtf.scalding.jobs.common.{AdhocExecutionApp, ScheduledExecutionApp}

import java.util.TimeZone

/**
capesospy-v2 update --build_locally --start_cron \
 --start_cron producer_embeddings_from_interested_in_by_follow_score_2020 \
 src/scala/com/twitter/simclusters_v2/capesos_config/atla_proc3.yaml
 */
object ProducerEmbeddingsFromInterestedInByFollowScore2020BatchApp
    extends ProducerEmbeddingsFromInterestedInByFollowScoreBase {
  override val firstTime: RichDate = RichDate("2021-03-01")
  override val batchIncrement: Duration = Days(7)

  override def modelVersion: ModelVersion = ModelVersion.Model20m145k2020

  override def getInterestedInFn: (
    DateRange,
    TimeZone
  ) => TypedPipe[(UserId, ClustersUserIsInterestedIn)] =
    InterestedInSources.simClustersInterestedIn2020Source

  override def producerTopKSimclusterEmbeddingsByFollowScoreDataset: KeyValDALDataset[
    KeyVal[Long, TopSimClustersWithScore]
  ] =
    ProducerTopKSimclusterEmbeddingsByFollowScore2020ScalaDataset

  override def simclusterEmbeddingTopKProducersByFollowScoreDataset: KeyValDALDataset[
    KeyVal[PersistedFullClusterId, TopProducersWithScore]
  ] =
    SimclusterEmbeddingTopKProducersByFollowScore2020ScalaDataset
}






