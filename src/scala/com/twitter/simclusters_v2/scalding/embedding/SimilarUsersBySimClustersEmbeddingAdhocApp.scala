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
 * Adhoc job to calculate producer's simcluster embeddings, which essentially assigns interestedIn
 * SimClusters to each producer, regardless of whether the producer has a knownFor assignment.
 *
./bazel bundle src/scala/com/twitter/simclusters_v2/scalding/embedding:similar_users_by_simclusters_embeddings-adhoc && \
  oscar hdfs --user recos-platform --screen --tee similar_users_by_simclusters_embeddings --bundle similar_users_by_simclusters_embeddings-adhoc \
  --tool com.twitter.simclusters_v2.scalding.embedding.SimilarUsersBySimClustersEmbeddingAdhocApp \
  -- --date 2019-07-10T00 2019-07-10T23
 */
object SimilarUsersBySimClustersEmbeddingAdhocApp extends AdhocExecutionApp {

  private val outputByFav =
    "/user/recos-platform/adhoc/similar_users_by_simclusters_embeddings/by_fav"
  private val outputByFollow =
    "/user/recos-platform/adhoc/similar_users_by_simclusters_embeddings/by_follow"

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

  implicit val candidatesInj: CompactScalaCodec[Candidates] = CompactScalaCodec(Candidates)

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
            topProducersForClusterEmbeddingByFavScore).writeExecution(
            VersionedKeyValSource[Long, Candidates](outputByFav))
          .getCounters
          .flatMap {
            case (_, counters) =>
              counters.toMap.toSeq
                .sortBy(e => (e._1.group, e._1.counter))
                .foreach {
                  case (statKey, value) =>
                    println(s"${statKey.group}\t${statKey.counter}\t$value")
                }
              Execution.unit
          },
        SimilarUsersBySimClustersEmbedding
          .getTopUsersRelatedToUser(
            topClusterEmbeddingsByFollowScore,
            topProducersForClusterEmbeddingByFollowScore).writeExecution(
            VersionedKeyValSource[Long, Candidates](outputByFollow))
          .getCounters
          .flatMap {
            case (_, counters) =>
              counters.toMap.toSeq
                .sortBy(e => (e._1.group, e._1.counter))
                .foreach {
                  case (statKey, value) =>
                    println(s"${statKey.group}\t${statKey.counter}\t$value")
                }
              Execution.unit
          }
      ).unit
  }
}


