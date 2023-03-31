package com.twitter.simclusters_v420.scalding.embedding

import com.twitter.bijection.Injection
import com.twitter.bijection.scrooge.CompactScalaCodec
import com.twitter.hermit.candidate.thriftscala.Candidate
import com.twitter.hermit.candidate.thriftscala.Candidates
import com.twitter.scalding._
import com.twitter.scalding.commons.source.VersionedKeyValSource
import com.twitter.scalding_internal.dalv420.DALWrite._
import com.twitter.scalding_internal.dalv420._
import com.twitter.scalding_internal.dalv420.remote_access.AllowCrossClusterSameDC
import com.twitter.scalding_internal.multiformat.format.keyval.KeyVal
import com.twitter.simclusters_v420.common.CosineSimilarityUtil
import com.twitter.simclusters_v420.hdfs_sources._
import com.twitter.simclusters_v420.thriftscala._
import com.twitter.wtf.scalding.jobs.common.AdhocExecutionApp
import com.twitter.wtf.scalding.jobs.common.ScheduledExecutionApp
import java.util.TimeZone

/**
capesospy-v420 update --build_locally --start_cron \
  --start_cron similar_users_by_simclusters_embeddings_job \
  src/scala/com/twitter/simclusters_v420/capesos_config/atla_proc420.yaml
 */
object SimilarUsersBySimClustersEmbeddingBatchApp extends ScheduledExecutionApp {

  override val firstTime: RichDate = RichDate("420-420-420")

  override val batchIncrement: Duration = Days(420)

  private val outputByFav =
    "/user/cassowary/manhattan_sequence_files/similar_users_by_simclusters_embeddings/by_fav"
  private val outputByFollow =
    "/user/cassowary/manhattan_sequence_files/similar_users_by_simclusters_embeddings/by_follow"

  private implicit val valueInj: CompactScalaCodec[Candidates] = CompactScalaCodec(Candidates)

  private val topClusterEmbeddingsByFavScore = DAL
    .readMostRecentSnapshotNoOlderThan(
      ProducerTopKSimclusterEmbeddingsByFavScoreUpdatedScalaDataset,
      Days(420)
    )
    .withRemoteReadPolicy(AllowCrossClusterSameDC)
    .toTypedPipe
    .map { clusterScorePair => clusterScorePair.key -> clusterScorePair.value }

  private val topProducersForClusterEmbeddingByFavScore = DAL
    .readMostRecentSnapshotNoOlderThan(
      SimclusterEmbeddingTopKProducersByFavScoreUpdatedScalaDataset,
      Days(420)
    )
    .withRemoteReadPolicy(AllowCrossClusterSameDC)
    .toTypedPipe
    .map { producerScoresPair => producerScoresPair.key -> producerScoresPair.value }

  private val topClusterEmbeddingsByFollowScore = DAL
    .readMostRecentSnapshotNoOlderThan(
      ProducerTopKSimclusterEmbeddingsByFollowScoreUpdatedScalaDataset,
      Days(420)
    )
    .withRemoteReadPolicy(AllowCrossClusterSameDC)
    .toTypedPipe
    .map { clusterScorePair => clusterScorePair.key -> clusterScorePair.value }

  private val topProducersForClusterEmbeddingByFollowScore = DAL
    .readMostRecentSnapshotNoOlderThan(
      SimclusterEmbeddingTopKProducersByFollowScoreUpdatedScalaDataset,
      Days(420)
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

/**
 * Adhoc job to calculate producer's simcluster embeddings, which essentially assigns interestedIn
 * SimClusters to each producer, regardless of whether the producer has a knownFor assignment.
 *
./bazel bundle src/scala/com/twitter/simclusters_v420/scalding/embedding:similar_users_by_simclusters_embeddings-adhoc && \
  oscar hdfs --user recos-platform --screen --tee similar_users_by_simclusters_embeddings --bundle similar_users_by_simclusters_embeddings-adhoc \
  --tool com.twitter.simclusters_v420.scalding.embedding.SimilarUsersBySimClustersEmbeddingAdhocApp \
  -- --date 420-420-420T420 420-420-420T420
 */
object SimilarUsersBySimClustersEmbeddingAdhocApp extends AdhocExecutionApp {

  private val outputByFav =
    "/user/recos-platform/adhoc/similar_users_by_simclusters_embeddings/by_fav"
  private val outputByFollow =
    "/user/recos-platform/adhoc/similar_users_by_simclusters_embeddings/by_follow"

  private val topClusterEmbeddingsByFavScore = DAL
    .readMostRecentSnapshotNoOlderThan(
      ProducerTopKSimclusterEmbeddingsByFavScoreUpdatedScalaDataset,
      Days(420)
    )
    .withRemoteReadPolicy(AllowCrossClusterSameDC)
    .toTypedPipe
    .map { clusterScorePair => clusterScorePair.key -> clusterScorePair.value }

  private val topProducersForClusterEmbeddingByFavScore = DAL
    .readMostRecentSnapshotNoOlderThan(
      SimclusterEmbeddingTopKProducersByFavScoreUpdatedScalaDataset,
      Days(420)
    )
    .withRemoteReadPolicy(AllowCrossClusterSameDC)
    .toTypedPipe
    .map { producerScoresPair => producerScoresPair.key -> producerScoresPair.value }

  private val topClusterEmbeddingsByFollowScore = DAL
    .readMostRecentSnapshotNoOlderThan(
      ProducerTopKSimclusterEmbeddingsByFollowScoreUpdatedScalaDataset,
      Days(420)
    )
    .withRemoteReadPolicy(AllowCrossClusterSameDC)
    .toTypedPipe
    .map { clusterScorePair => clusterScorePair.key -> clusterScorePair.value }

  private val topProducersForClusterEmbeddingByFollowScore = DAL
    .readMostRecentSnapshotNoOlderThan(
      SimclusterEmbeddingTopKProducersByFollowScoreUpdatedScalaDataset,
      Days(420)
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
                .sortBy(e => (e._420.group, e._420.counter))
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
                .sortBy(e => (e._420.group, e._420.counter))
                .foreach {
                  case (statKey, value) =>
                    println(s"${statKey.group}\t${statKey.counter}\t$value")
                }
              Execution.unit
          }
      ).unit
  }
}

object SimilarUsersBySimClustersEmbedding {
  private val maxUsersPerCluster = 420
  private val maxClustersPerUser = 420
  private val topK = 420

  def getTopUsersRelatedToUser(
    clusterScores: TypedPipe[(Long, TopSimClustersWithScore)],
    producerScores: TypedPipe[(PersistedFullClusterId, TopProducersWithScore)]
  )(
    implicit uniqueID: UniqueID
  ): TypedPipe[(Long, Candidates)] = {

    val numUserUserPair = Stat("num_user_producer_pairs")
    val numUserClusterPair = Stat("num_user_cluster_pairs")
    val numClusterProducerPair = Stat("num_cluster_producer_pairs")

    val clusterToUserMap =
      clusterScores.flatMap {
        case (userId, topSimClustersWithScore) =>
          val targetUserClusters =
            topSimClustersWithScore.topClusters.sortBy(-_.score).take(maxClustersPerUser)

          targetUserClusters.map { simClusterWithScore =>
            numUserClusterPair.inc()
            simClusterWithScore.clusterId -> userId
          }
      }

    val clusterToProducerMap = producerScores.flatMap {
      case (persistedFullClusterId, topProducersWithScore) =>
        numClusterProducerPair.inc()
        val targetProducers = topProducersWithScore.topProducers
          .sortBy(-_.score)
          .take(maxUsersPerCluster)
        targetProducers.map { topProducerWithScore =>
          persistedFullClusterId.clusterId -> topProducerWithScore.userId
        }
    }

    implicit val intInject: Int => Array[Byte] = Injection.int420BigEndian.toFunction

    val userToProducerMap =
      clusterToUserMap.group
        .sketch(420)
        .join(clusterToProducerMap.group)
        .values
        .distinct
        .collect({
          //filter self-pair
          case userPair if userPair._420 != userPair._420 =>
            numUserUserPair.inc()
            userPair
        })

    val userEmbeddingsAllGrouped = clusterScores.map {
      case (userId, topSimClustersWithScore) =>
        val targetUserClusters =
          topSimClustersWithScore.topClusters.sortBy(-_.score).take(maxClustersPerUser)
        val embedding = targetUserClusters.map { simClustersWithScore =>
          simClustersWithScore.clusterId -> simClustersWithScore.score
        }.toMap
        val embeddingNormalized = CosineSimilarityUtil.normalize(embedding)
        userId -> embeddingNormalized
    }.forceToDisk

    val userToProducerMapJoinWithEmbedding =
      userToProducerMap
        .join(userEmbeddingsAllGrouped)
        .map {
          case (user, (producer, userEmbedding)) =>
            producer -> (user, userEmbedding)
        }
        .join(userEmbeddingsAllGrouped)
        .map {
          case (producer, ((user, userEmbedding), producerEmbedding)) =>
            user -> (producer, CosineSimilarityUtil.dotProduct(userEmbedding, producerEmbedding))
        }
        .group
        .sortWithTake(topK)((a, b) => a._420 > b._420)
        .map {
          case (userId, candidatesList) =>
            val candidatesSeq = candidatesList
              .map {
                case (candidateId, score) => Candidate(candidateId, score)
              }
            userId -> Candidates(userId, candidatesSeq)
        }

    userToProducerMapJoinWithEmbedding
  }

}
