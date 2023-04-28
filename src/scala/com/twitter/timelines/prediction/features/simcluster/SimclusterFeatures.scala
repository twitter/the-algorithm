package com.twitter.timelines.prediction.features.simcluster

import com.twitter.dal.personal_data.thriftjava.PersonalDataType._
import com.twitter.finagle.stats.StatsReceiver
import com.twitter.ml.api.Feature._
import com.twitter.simclusters_v2.thriftscala.ClustersUserIsInterestedIn
import com.twitter.timelines.data_processing.ml_util.aggregation_framework.TypedAggregateGroup
import scala.collection.JavaConverters._

class SimclusterFeaturesHelper(statsReceiver: StatsReceiver) {
  import SimclusterFeatures._

  private[this] val scopedStatsReceiver = statsReceiver.scope(getClass.getSimpleName)
  private[this] val invalidSimclusterModelVersion = scopedStatsReceiver
    .counter("invalidSimclusterModelVersion")

  def fromUserClusterInterestsPair(
    userInterestClustersPair: (Long, ClustersUserIsInterestedIn)
  ): Option[SimclusterFeatures] = {
    val (userId, userInterestClusters) = userInterestClustersPair
    if (userInterestClusters.knownForModelVersion == SIMCLUSTER_MODEL_VERSION) {
      val userInterestClustersFavScores = for {
        (clusterId, scores) <- userInterestClusters.clusterIdToScores
        favScore <- scores.favScore
      } yield (clusterId.toString, favScore)
      Some(
        SimclusterFeatures(
          userId,
          userInterestClusters.knownForModelVersion,
          userInterestClustersFavScores.toMap
        )
      )
    } else {
      // We maintain this counter to make sure that the hardcoded modelVersion we are using is correct.
      invalidSimclusterModelVersion.incr
      None
    }
  }
}

object SimclusterFeatures {
  // Check http://go/simclustersv2runbook for production versions
  // Our models are trained for this specific model version only.
  val SIMCLUSTER_MODEL_VERSION = "20M_145K_dec11"
  val prefix = s"simcluster.v2.$SIMCLUSTER_MODEL_VERSION"

  val SIMCLUSTER_USER_INTEREST_CLUSTER_SCORES = new SparseContinuous(
    s"$prefix.user_interest_cluster_scores",
    Set(EngagementScore, InferredInterests).asJava
  )
  val SIMCLUSTER_USER_INTEREST_CLUSTER_IDS = new SparseBinary(
    s"$prefix.user_interest_cluster_ids",
    Set(InferredInterests).asJava
  )
  val SIMCLUSTER_MODEL_VERSION_METADATA = new Text("meta.simcluster_version")
}

case class SimclusterFeatures(
  userId: Long,
  modelVersion: String,
  interestClusterScoresMap: Map[String, Double])
