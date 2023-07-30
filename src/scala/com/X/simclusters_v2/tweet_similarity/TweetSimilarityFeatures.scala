package com.X.simclusters_v2.tweet_similarity

import com.X.ml.api.Feature.{Binary, Continuous, Discrete, SparseContinuous}
import com.X.ml.api.util.FDsl._
import com.X.ml.api.{DataRecord, FeatureContext, IRecordOneToOneAdapter}
import com.X.ml.featurestore.catalog.features.recommendations.ProducerSimClustersEmbedding
import com.X.ml.featurestore.lib.UserId
import com.X.ml.featurestore.lib.data.{PredictionRecord, PredictionRecordAdapter}
import com.X.ml.featurestore.lib.entity.Entity
import com.X.ml.featurestore.lib.feature.BoundFeatureSet

object TweetSimilarityFeatures {
  val QueryTweetId = new Discrete("query_tweet.id")
  val CandidateTweetId = new Discrete("candidate_tweet.id")
  val QueryTweetEmbedding = new SparseContinuous("query_tweet.simclusters_embedding")
  val CandidateTweetEmbedding = new SparseContinuous("candidate_tweet.simclusters_embedding")
  val QueryTweetEmbeddingNorm = new Continuous("query_tweet.embedding_norm")
  val CandidateTweetEmbeddingNorm = new Continuous("candidate_tweet.embedding_norm")
  val QueryTweetTimestamp = new Discrete("query_tweet.timestamp")
  val CandidateTweetTimestamp = new Discrete("candidate_tweet.timestamp")
  val TweetPairCount = new Discrete("popularity_count.tweet_pair")
  val QueryTweetCount = new Discrete("popularity_count.query_tweet")
  val CosineSimilarity = new Continuous("meta.cosine_similarity")
  val Label = new Binary("co-engagement.label")

  val FeatureContext: FeatureContext = new FeatureContext(
    QueryTweetId,
    CandidateTweetId,
    QueryTweetEmbedding,
    CandidateTweetEmbedding,
    QueryTweetEmbeddingNorm,
    CandidateTweetEmbeddingNorm,
    QueryTweetTimestamp,
    CandidateTweetTimestamp,
    TweetPairCount,
    QueryTweetCount,
    CosineSimilarity,
    Label
  )

  def isCoengaged(dataRecord: DataRecord): Boolean = {
    dataRecord.getFeatureValue(Label)
  }
}

class TweetSimilarityFeaturesStoreConfig(identifier: String) {
  val bindingIdentifier: Entity[UserId] = Entity[UserId](identifier)

  val featureStoreBoundFeatureSet: BoundFeatureSet = BoundFeatureSet(
    ProducerSimClustersEmbedding.FavBasedEmbedding20m145kUpdated.bind(bindingIdentifier))

  val predictionRecordAdapter: IRecordOneToOneAdapter[PredictionRecord] =
    PredictionRecordAdapter.oneToOne(featureStoreBoundFeatureSet)
}
