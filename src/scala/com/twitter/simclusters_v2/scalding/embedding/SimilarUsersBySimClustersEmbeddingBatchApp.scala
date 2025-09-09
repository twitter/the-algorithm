package com.twitter.simclusters_v2.scalding.embedding

import com.twitter.bijection.Injection
import com.twitter.bijection.scrooge.CompactScalaCodec
import com.twitter.hermit.candidate.thriftscala.{Candidate, Candidates}
import com.twitter.scalding.*
import com.twitter.scalding.commons.source.VersionedKeyValSource
import com.twitter.scalding_internal.dalv2.*
import com.twitter.scalding_internal.dalv2.DALWrite.*
import com.twitter.scalding_internal.dalv2.remote_access.AllowCrossClusterSameDC
import com.twitter.scalding_internal.multiformat.format.keyval.KeyVal
import com.twitter.simclusters_v2.common.CosineSimilarityUtil
import com.twitter.simclusters_v2.hdfs_sources.*
import com.twitter.simclusters_v2.thriftscala.*
import com.twitter.wtf.scalding.jobs.common.{AdhocExecutionApp, ScheduledExecutionApp}

import java.util.TimeZone

/**
capesospy-v2 update --build_locally --start_cron \
  --start_cron similar_users_by_simclusters_embeddings_job \
  src/scala/com/twitter/simclusters_v2/capesos_config/atla_proc3.yaml
 */
object SimilarUsersBySimClustersEmbeddingBatchApp extends ScheduledExecutionApp {

  override val firstTime: RichDate = RichDate("2019-07-10")

  override val batchIncrement: Duration = Days(7)

  private val outputByFav =
    "/user/cassowary/manhattan_sequence_files/similar_users_by_simclusters_embeddings/by_fav"
  private val outputByFollow =
    "/user/cassowary/manhattan_sequence_files/similar_users_by_simclusters_embeddings/by_follow"

  private implicit val valueInj: CompactScalaCodec[Candidates] = CompactScalaCodec(Candidates)

  private val topClusterEmbeddingsByFavScore = DAL
    .readMostRecentSnapshotNoOlderThan(
      ProducerTopKSimclusterEmbeddingsByFavScoreUpdatedScalaDataset,
      Days(14)
    )
    .withRemoteReadPolicy(AllowCrossClusterSameDC)
    .toTypedPipe
    .map { clusterScorePair => clusterScorePair.key -> clusterScorePair.value }

  private val topProducersForClusterEmbeddingByFavScore = DAL
    .readMostRecentSnapshotNoOlderThan(
      SimclusterEmbeddingTopKProducersByFavScoreUpdatedScalaDataset,
      Days(14)
    )
    .withRemoteReadPolicy(AllowCrossClusterSameDC)
    .toTypedPipe
    .map { producerScoresPair => producerScoresPair.key -> producerScoresPair.value }

  private val topClusterEmbeddingsByFollowScore = DAL
    .readMostRecentSnapshotNoOlderThan(
      ProducerTopKSimclusterEmbeddingsByFollowScoreUpdatedScalaDataset,
      Days(14)
    )
    .withRemoteReadPolicy(AllowCrossClusterSameDC)
    .toTypedPipe
    .map { clusterScorePair => clusterScorePair.key -> clusterScorePair.value }

  private val topProducersForClusterEmbeddingByFollowScore = DAL
    .readMostRecentSnapshotNoOlderThan(
      SimclusterEmbeddingTopKProducersByFollowScoreUpdatedScalaDataset,
      Days(14)
    )
    .withRemoteReadPolicy(AllowCrossClusterSameDC)
    .toTypedPipe
    .map { producerScoresPair => producerScoresPair.key -> producerScoresPair.value }

  override def runOnDateRange(
    args: Args
  )(
    implicit dateRange: DateRange,
    timeZone: TimeZone,
    uniqueID: UniqueID
  ): Execution[Unit] = {

    Execution
      .zip(
        SimilarUsersBySimClustersEmbedding
          .getTopUsersRelatedToUser(
            topClusterEmbeddingsByFavScore,
            topProducersForClusterEmbeddingByFavScore
          )
          .map { case (key, value) => KeyVal(key, value) }
          .writeDALVersionedKeyValExecution(
            SimilarUsersByFavBasedProducerEmbeddingScalaDataset,
            D.Suffix(outputByFav)
          ),
        SimilarUsersBySimClustersEmbedding
          .getTopUsersRelatedToUser(
            topClusterEmbeddingsByFollowScore,
            topProducersForClusterEmbeddingByFollowScore
          )
          .map { case (key, value) => KeyVal(key, value) }
          .writeDALVersionedKeyValExecution(
            SimilarUsersByFollowBasedProducerEmbeddingScalaDataset,
            D.Suffix(outputByFollow)
          )
      ).unit
  }
}




