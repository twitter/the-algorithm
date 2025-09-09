package com.twitter.simclusters_v2.scalding.embedding

import com.twitter.bijection.Injection
import com.twitter.bijection.scrooge.CompactScalaCodec
import com.twitter.hermit.candidate.thriftscala.Candidate
import com.twitter.hermit.candidate.thriftscala.Candidates
import com.twitter.scalding._
import com.twitter.scalding.commons.source.VersionedKeyValSource
import com.twitter.scalding_internal.dalv2.DALWrite._
import com.twitter.scalding_internal.dalv2._
import com.twitter.scalding_internal.dalv2.remote_access.AllowCrossClusterSameDC
import com.twitter.scalding_internal.multiformat.format.keyval.KeyVal
import com.twitter.simclusters_v2.common.CosineSimilarityUtil
import com.twitter.simclusters_v2.hdfs_sources._
import com.twitter.simclusters_v2.thriftscala._
import com.twitter.wtf.scalding.jobs.common.AdhocExecutionApp
import com.twitter.wtf.scalding.jobs.common.ScheduledExecutionApp
import java.util.TimeZone

object SimilarUsersBySimClustersEmbedding {
  private val maxUsersPerCluster = 300
  private val maxClustersPerUser = 50
  private val topK = 100

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

    implicit val intInject: Int => Array[Byte] = Injection.int2BigEndian.toFunction

    val userToProducerMap =
      clusterToUserMap.group
        .sketch(2000)
        .join(clusterToProducerMap.group)
        .values
        .distinct
        .collect({
          //filter self-pair
          case userPair if userPair._1 != userPair._2 =>
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
        .sortWithTake(topK)((a, b) => a._2 > b._2)
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
