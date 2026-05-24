package com.twitter.tweet_mixer.functional_component.transformer

import com.twitter.product_mixer.core.functional_component.transformer.CandidatePipelineQueryTransformer
import com.twitter.product_mixer.core.model.common.identifier.TransformerIdentifier
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.snowflake.id.SnowflakeId
import com.twitter.timelines.configapi.FSBoundedParam
import com.twitter.tweet_mixer.candidate_source.UTG.UTGTweetBasedRequest
import com.twitter.tweet_mixer.feature.EntityTypes.TweetId
import com.twitter.tweet_mixer.functional_component.hydrator.UTGOutlierSignalsFeature
import com.twitter.tweet_mixer.param.TweetMixerGlobalParams.MaxCandidateNumPerSourceKeyParam
import com.twitter.tweet_mixer.param.TweetMixerGlobalParams.MaxTweetAgeHoursParam
import com.twitter.tweet_mixer.param.UTGParams.CoverageExpansionOldTweetEnabledParam
import com.twitter.tweet_mixer.param.UTGParams.EnableUTGCacheParam
import com.twitter.tweet_mixer.param.UTGParams.MaxConsumerSeedsNumParam
import com.twitter.tweet_mixer.param.UTGParams.MinCoOccurrenceParam
import com.twitter.tweet_mixer.param.UTGParams.SimilarityAlgorithm
import com.twitter.tweet_mixer.param.UTGParams.SimilarityAlgorithmEnum
import com.twitter.util.Duration
import com.twitter.util.Time
import scala.concurrent.duration.HOURS

case class UTGTweetBasedQueryTransformer(
  override val identifier: TransformerIdentifier,
  signalsFn: PipelineQuery => Seq[TweetId],
  isExpansionQuery: Boolean,
  minScoreParam: FSBoundedParam[Double],
  degreeExponent: FSBoundedParam[Double])
    extends CandidatePipelineQueryTransformer[PipelineQuery, UTGTweetBasedRequest] {

  private val oldTweetThreshold: Duration = Duration(48, HOURS)

  override def transform(inputQuery: PipelineQuery): UTGTweetBasedRequest = {
    val params = inputQuery.params
    val tweetSignals = signalsFn(inputQuery)
    val expansionModeEnabled = params(CoverageExpansionOldTweetEnabledParam)
    val tweetSignalsPostExpansion = {
      if (expansionModeEnabled && isExpansionQuery) tweetSignals.filter(isOldTweet)
      else if (expansionModeEnabled && !isExpansionQuery) tweetSignals.filterNot(isOldTweet)
      else if (!isExpansionQuery) tweetSignals
      else Nil
    }

    val outliers = inputQuery.features.get.getOrElse(UTGOutlierSignalsFeature, Set.empty[Long])

    val filteredPosts = tweetSignalsPostExpansion.filter { id =>
      !outliers.contains(id)
    }

    UTGTweetBasedRequest(
      filteredPosts,
      maxResults = Some(params(MaxCandidateNumPerSourceKeyParam)),
      minCooccurrence = Some(params(MinCoOccurrenceParam)),
      minScore = Some(params(minScoreParam)),
      maxTweetAgeInHours = Some(params(MaxTweetAgeHoursParam).inHours),
      maxConsumerSeeds = Some(params(MaxConsumerSeedsNumParam)),
      similarityAlgorithm =
        Some(SimilarityAlgorithmEnum.enumToSimilarityAlgorithmMap(params(SimilarityAlgorithm))),
      enableCache = inputQuery.params(EnableUTGCacheParam),
      degreeExponent = Some(params(degreeExponent))
    )
  }

  private def isOldTweet(tweetId: TweetId): Boolean = {
    SnowflakeId.timeFromIdOpt(tweetId).exists(_ < Time.now - oldTweetThreshold)
  }
}
