package com.twitter.tweet_mixer.candidate_pipeline

import com.twitter.product_mixer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mixer.core.functional_component.candidate_source.CandidateSource
import com.twitter.product_mixer.core.functional_component.common.alert.Alert
import com.twitter.product_mixer.core.functional_component.gate.Gate
import com.twitter.product_mixer.core.functional_component.transformer.CandidateFeatureTransformer
import com.twitter.product_mixer.core.functional_component.transformer.CandidatePipelineQueryTransformer
import com.twitter.product_mixer.core.functional_component.transformer.CandidatePipelineResultsTransformer
import com.twitter.product_mixer.core.gate.ParamGate
import com.twitter.product_mixer.core.model.common.identifier.CandidatePipelineIdentifier
import com.twitter.product_mixer.core.model.common.identifier.TransformerIdentifier
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.product_mixer.core.pipeline.candidate.CandidatePipelineConfig
import com.twitter.timelines.configapi.FSBoundedParam
import com.twitter.timelines.configapi.FSParam
import com.twitter.tweet_mixer.candidate_source.content_embedding_ann.ContentEmbeddingAnnCandidateSource
import com.twitter.tweet_mixer.candidate_source.content_embedding_ann.ContentEmbeddingAnnQuery
import com.twitter.tweet_mixer.functional_component.transformer.TweetMixerCandidateFeatureTransformer
import com.twitter.tweet_mixer.model.response.TweetMixerCandidate
import com.twitter.tweet_mixer.param.ContentEmbeddingAnnParams
import com.twitter.tweet_mixer.param.TweetMixerGlobalParams.EnableContentEmbeddingAnnTweets
import com.twitter.tweet_mixer.service.TweetMixerNotificationConfig.BusinessHours
import com.twitter.tweet_mixer.service.TweetMixerNotificationConfig.ForYouGroupMap
import com.twitter.tweet_mixer.service.TweetMixerNotificationConfig.defaultEmptyResponseRateAlert
import com.twitter.tweet_mixer.service.TweetMixerNotificationConfig.defaultSuccessRateAlert
import com.twitter.tweet_mixer.utils.CandidatePipelineConstants
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ContentAnnTweetBasedCandidatePipelineConfigFactory @Inject() (
  contentEmbeddingAnnCandidateSource: ContentEmbeddingAnnCandidateSource) {
  def build[Query <: PipelineQuery](
    identifierPrefix: String,
    signalsFn: PipelineQuery => Seq[Long],
  ): ContentAnnTweetBasedCandidatePipelineConfig[Query] = {
    new ContentAnnTweetBasedCandidatePipelineConfig(
      identifierPrefix,
      signalsFn,
      contentEmbeddingAnnCandidateSource
    )
  }
}

@Singleton
class ContentAnnTweetBasedCandidatePipelineConfig[Query <: PipelineQuery] @Inject() (
  identifierPrefix: String,
  signalFn: PipelineQuery => Seq[Long],
  contentEmbeddingAnnCandidateSource: ContentEmbeddingAnnCandidateSource)
    extends CandidatePipelineConfig[
      Query,
      ContentEmbeddingAnnQuery,
      TweetMixerCandidate,
      TweetCandidate
    ] {

  override val identifier: CandidatePipelineIdentifier = CandidatePipelineIdentifier(
    identifierPrefix + CandidatePipelineConstants.ContentEmbeddingAnn)

  override val gates: Seq[Gate[PipelineQuery]] = Seq(
    ParamGate(
      name = "EnableContentEmbeddingAnnTweets",
      param = EnableContentEmbeddingAnnTweets
    )
  )

  private def signalFnSelector(query: PipelineQuery): Seq[Long] = {
    signalFn(query)
  }

  override val queryTransformer: CandidatePipelineQueryTransformer[
    Query,
    ContentEmbeddingAnnQuery
  ] = {
    ContentEmbeddingAnnQueryTransformer(
      TransformerIdentifier("ContentEmbeddingAnnTweetBased"),
      query => signalFnSelector(query),
      ContentEmbeddingAnnParams.MinScoreThreshold,
      ContentEmbeddingAnnParams.MaxScoreThreshold,
      ContentEmbeddingAnnParams.NumberOfCandidatesPerPost,
      ContentEmbeddingAnnParams.IncludeMediaSource,
      ContentEmbeddingAnnParams.IncludeTextSource,
      ContentEmbeddingAnnParams.DecayByCountry
    )
  }

  override def candidateSource: CandidateSource[
    ContentEmbeddingAnnQuery,
    TweetMixerCandidate
  ] = contentEmbeddingAnnCandidateSource

  override def featuresFromCandidateSourceTransformers: Seq[
    CandidateFeatureTransformer[TweetMixerCandidate]
  ] = Seq(TweetMixerCandidateFeatureTransformer)

  override val resultTransformer: CandidatePipelineResultsTransformer[
    TweetMixerCandidate,
    TweetCandidate
  ] = { candidate => TweetCandidate(id = candidate.tweetId) }

  override val alerts: Seq[Alert] = Seq(
    defaultSuccessRateAlert(threshold = 95, notificationType = BusinessHours)(ForYouGroupMap),
    defaultEmptyResponseRateAlert()(ForYouGroupMap)
  )
}

case class ContentEmbeddingAnnQueryTransformer(
  override val identifier: TransformerIdentifier,
  signalsFn: PipelineQuery => Seq[Long],
  minScoreParam: FSBoundedParam[Double],
  maxScoreParam: FSBoundedParam[Double],
  numCandidatesPerPost: FSBoundedParam[Int],
  includeMediaSourceParam: FSParam[Boolean],
  includeTextSourceParam: FSParam[Boolean],
  decayByCountryParam: FSParam[Boolean])
    extends CandidatePipelineQueryTransformer[PipelineQuery, ContentEmbeddingAnnQuery] {
  override def transform(inputQuery: PipelineQuery): ContentEmbeddingAnnQuery = {
    val minScore = inputQuery.params(minScoreParam)
    val maxScore = inputQuery.params(maxScoreParam)
    val numCandidates = inputQuery.params(numCandidatesPerPost)
    val countryCode = inputQuery.clientContext.countryCode.getOrElse("US")
    val languageCode = inputQuery.clientContext.languageCode.getOrElse("en")
    val decayByCountry = inputQuery.params(decayByCountryParam)
    val includeMediaSource = inputQuery.params(includeMediaSourceParam)
    val includeTextSource = inputQuery.params(includeTextSourceParam)
    ContentEmbeddingAnnQuery(
      signalsFn(inputQuery),
      numCandidates,
      minScore,
      maxScore,
      countryCode,
      languageCode,
      decayByCountry,
      includeMediaSource,
      includeTextSource
    )
  }
}
