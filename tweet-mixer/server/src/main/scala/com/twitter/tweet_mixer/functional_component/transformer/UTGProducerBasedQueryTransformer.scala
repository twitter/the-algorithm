package com.twitter.tweet_mixer.functional_component.transformer

import com.twitter.product_mixer.core.functional_component.transformer.CandidatePipelineQueryTransformer
import com.twitter.product_mixer.core.model.common.identifier.TransformerIdentifier
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.timelines.configapi.FSBoundedParam
import com.twitter.tweet_mixer.candidate_source.UTG.UTGProducerBasedRequest
import com.twitter.tweet_mixer.feature.EntityTypes.UserId
import com.twitter.tweet_mixer.param.TweetMixerGlobalParams.MaxCandidateNumPerSourceKeyParam
import com.twitter.tweet_mixer.param.TweetMixerGlobalParams.MaxTweetAgeHoursParam
import com.twitter.tweet_mixer.param.UTGParams.EnableUTGCacheParam
import com.twitter.tweet_mixer.param.UTGParams.MaxNumFollowersParam
import com.twitter.tweet_mixer.param.UTGParams.MinCoOccurrenceParam
import com.twitter.tweet_mixer.param.UTGParams.SimilarityAlgorithm
import com.twitter.tweet_mixer.param.UTGParams.SimilarityAlgorithmEnum

case class UTGProducerBasedQueryTransformer(
  override val identifier: TransformerIdentifier,
  signalsFn: PipelineQuery => Seq[UserId],
  minScoreParam: FSBoundedParam[Double])
    extends CandidatePipelineQueryTransformer[PipelineQuery, UTGProducerBasedRequest] {

  override def transform(inputQuery: PipelineQuery): UTGProducerBasedRequest = {
    val params = inputQuery.params
    val tweetSignals = signalsFn(inputQuery)
    UTGProducerBasedRequest(
      tweetSignals,
      maxResults = Some(params(MaxCandidateNumPerSourceKeyParam)),
      minCooccurrence = Some(params(MinCoOccurrenceParam)),
      minScore = Some(params(minScoreParam)),
      maxTweetAgeInHours = Some(params(MaxTweetAgeHoursParam).inHours),
      maxNumFollowers = Some(params(MaxNumFollowersParam)),
      similarityAlgorithm =
        Some(SimilarityAlgorithmEnum.enumToSimilarityAlgorithmMap(params(SimilarityAlgorithm))),
      enableCache = inputQuery.params(EnableUTGCacheParam)
    )
  }
}
