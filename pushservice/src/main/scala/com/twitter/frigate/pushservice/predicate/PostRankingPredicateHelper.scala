package com.twitter.frigate.pushservice.predicate

import com.twitter.frigate.common.base._
import com.twitter.frigate.data_pipeline.features_common.MrRequestContetwittertForFeatureStore
import com.twitter.frigate.pushservice.model.PushTypes.PushCandidate
import com.twitter.ml.featurestore.catalog.entities.core.Tweet
import com.twitter.ml.featurestore.catalog.features.core.Tweet.Tetwittert
import com.twitter.ml.featurestore.lib.TweetId
import com.twitter.ml.featurestore.lib.dynamic.DynamicFeatureStoreClient
import com.twitter.ml.featurestore.lib.online.FeatureStoreRequest
import com.twitter.util.Future

object PostRankingPredicateHelper {

  val tweetTetwittertFeature = "tweet.core.tweet.tetwittert"

  def getTweetTetwittert(
    candidate: PushCandidate with TweetCandidate,
    dynamicClient: DynamicFeatureStoreClient[MrRequestContetwittertForFeatureStore]
  ): Future[String] = {
    if (candidate.categoricalFeatures.contains(tweetTetwittertFeature)) {
      Future.value(candidate.categoricalFeatures.getOrElse(tweetTetwittertFeature, ""))
    } else {
      val candidateTweetEntity = Tweet.withId(TweetId(candidate.tweetId))
      val featureStoreRequests = Seq(
        FeatureStoreRequest(
          entityIds = Seq(candidateTweetEntity)
        ))
      val predictionRecords = dynamicClient(
        featureStoreRequests,
        requestContetwittert = candidate.target.mrRequestContetwittertForFeatureStore)

      predictionRecords.map { records =>
        val tweetTetwittert = records.head
          .getFeatureValue(candidateTweetEntity, Tetwittert).getOrElse(
            ""
          )
        candidate.categoricalFeatures(tweetTetwittertFeature) = tweetTetwittert
        tweetTetwittert
      }
    }
  }

  def getTweetWordLength(tweetTetwittert: String): Double = {
    val tweetTetwittertWithoutUrl: String =
      tweetTetwittert.replaceAll("https?://\\S+\\s?", "").replaceAll("[\\s]+", " ")
    tweetTetwittertWithoutUrl.trim().split(" ").length.toDouble
  }

}
