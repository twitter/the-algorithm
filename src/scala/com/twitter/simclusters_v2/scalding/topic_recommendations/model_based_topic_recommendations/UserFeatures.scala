package com.twitter.simclusters_v2.scalding.topic_recommendations.model_based_topic_recommendations

import com.twitter.ml.api.{Feature, FeatureContext}
import com.twitter.ml.api.constant.SharedFeatures

object UserFeatures {
  val UserIdFeature = SharedFeatures.USER_ID // User-id

  val UserSimClusterFeatures =
    new Feature.SparseContinuous(
      "user.simclusters.interested_in"
    ) // User's interestedIn simcluster embeddding

  val UserCountryFeature = new Feature.Text("user.country") // user's country code

  val UserLanguageFeature = new Feature.Text("user.language") // user's language

  val FollowedTopicIdFeatures =
    new Feature.SparseBinary(
      "followed_topics.id"
    ) // SparseBinary features for the set of followed topics

  val NotInterestedTopicIdFeatures =
    new Feature.SparseBinary(
      "not_interested_topics.id"
    ) // SparseBinary features for the set of not-interested topics

  val FollowedTopicSimClusterAvgFeatures =
    new Feature.SparseContinuous(
      "followed_topics.simclusters.avg"
    ) // Average SimCluster Embedding of the followed topics

  val NotInterestedTopicSimClusterAvgFeatures =
    new Feature.SparseContinuous(
      "not_interested_topics.simclusters.avg"
    ) // Average SimCluster Embedding of the followed topics

  val TargetTopicIdFeatures = new Feature.Discrete("target_topic.id") // target topic-id

  val TargetTopicSimClustersFeature =
    new Feature.SparseContinuous(
      "target_topic.simclusters"
    ) // SimCluster embedding of the target topic

  val FeatureContext = new FeatureContext(
    UserIdFeature,
    UserSimClusterFeatures,
    UserCountryFeature,
    UserLanguageFeature,
    FollowedTopicIdFeatures,
    NotInterestedTopicIdFeatures,
    FollowedTopicSimClusterAvgFeatures,
    NotInterestedTopicSimClusterAvgFeatures,
    TargetTopicIdFeatures,
    TargetTopicSimClustersFeature
  )
}
