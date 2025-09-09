package com.twitter.simclusters_v2.scalding.embedding

import com.twitter.onboarding.relevance.candidates.thriftscala.InterestBasedUserRecommendations
import com.twitter.onboarding.relevance.candidates.thriftscala.UTTInterest
import com.twitter.onboarding.relevance.source.UttAccountRecommendationsScalaDataset
import com.twitter.scalding.Args
import com.twitter.scalding.DateRange
import com.twitter.scalding.Days
import com.twitter.scalding.Duration
import com.twitter.scalding.Execution
import com.twitter.scalding.RichDate
import com.twitter.scalding.UniqueID
import com.twitter.scalding.typed.TypedPipe
import com.twitter.scalding.typed.UnsortedGrouped
import com.twitter.scalding_internal.dalv2.DAL
import com.twitter.scalding_internal.dalv2.DALWrite._
import com.twitter.scalding_internal.dalv2.remote_access.ExplicitLocation
import com.twitter.scalding_internal.dalv2.remote_access.ProcAtla
import com.twitter.scalding_internal.multiformat.format.keyval.KeyVal
import com.twitter.simclusters_v2.common.ModelVersions
import com.twitter.simclusters_v2.common.SimClustersEmbedding
import com.twitter.simclusters_v2.hdfs_sources.AdhocKeyValSources
import com.twitter.simclusters_v2.hdfs_sources.ProducerEmbeddingSources
import com.twitter.simclusters_v2.hdfs_sources.SemanticCoreEmbeddingsFromProducerScalaDataset
import com.twitter.simclusters_v2.scalding.embedding.common.EmbeddingUtil._
import com.twitter.simclusters_v2.thriftscala
import com.twitter.simclusters_v2.thriftscala.EmbeddingType
import com.twitter.simclusters_v2.thriftscala.InternalId
import com.twitter.simclusters_v2.thriftscala.ModelVersion
import com.twitter.simclusters_v2.thriftscala.SimClusterWithScore
import com.twitter.simclusters_v2.thriftscala.SimClustersEmbeddingId
import com.twitter.simclusters_v2.thriftscala.TopSimClustersWithScore
import com.twitter.wtf.scalding.jobs.common.AdhocExecutionApp
import com.twitter.wtf.scalding.jobs.common.ScheduledExecutionApp
import com.twitter.wtf.scalding.jobs.common.StatsUtil._
import java.util.TimeZone


private object EntityEmbeddingFromProducerEmbeddingJob {
  def computeEmbedding(
    producersEmbeddings: TypedPipe[(Long, TopSimClustersWithScore)],
    entityKnownForProducers: TypedPipe[(Long, (Long, Double))],
    topK: Int,
    modelVersion: ModelVersion,
    embeddingType: EmbeddingType
  ): UnsortedGrouped[SimClustersEmbeddingId, thriftscala.SimClustersEmbedding] = {
    producersEmbeddings
      .hashJoin(entityKnownForProducers).flatMap {
        case (_, (topSimClustersWithScore, (entityId, producerScore))) => {
          val entityEmbedding = topSimClustersWithScore.topClusters
          entityEmbedding.map {
            case SimClusterWithScore(clusterId, score) =>
              (
                (
                  SimClustersEmbeddingId(
                    embeddingType,
                    modelVersion,
                    InternalId.EntityId(entityId)),
                  clusterId),
                score * producerScore)
          }
        }
      }.sumByKey.map {
        case ((embeddingId, clusterId), clusterScore) =>
          (embeddingId, (clusterId, clusterScore))
      }.group.sortedReverseTake(topK)(Ordering.by(_._2)).mapValues(SimClustersEmbedding
        .apply(_).toThrift)
  }

  def getNormalizedEntityProducerMatrix(
    implicit dateRange: DateRange
  ): TypedPipe[(Long, Long, Double)] = {
    val uttRecs: TypedPipe[(UTTInterest, InterestBasedUserRecommendations)] =
      DAL
        .readMostRecentSnapshot(UttAccountRecommendationsScalaDataset).withRemoteReadPolicy(
          ExplicitLocation(ProcAtla)).toTypedPipe.map {
          case KeyVal(interest, candidates) => (interest, candidates)
        }

    uttRecs
      .flatMap {
        case (interest, candidates) => {
          // current populated features
          val top20Producers = candidates.recommendations.sortBy(-_.score.getOrElse(0.0d)).take(20)
          val producerScorePairs = top20Producers.map { producer =>
            (producer.candidateUserID, producer.score.getOrElse(0.0))
          }
          val scoreSum = producerScorePairs.map(_._2).sum
          producerScorePairs.map {
            case (producerId, score) => (interest.uttID, producerId, score / scoreSum)
          }
        }
      }
  }

}
