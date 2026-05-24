package com.twitter.tweet_mixer.candidate_pipeline

import com.twitter.product_mixer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mixer.core.functional_component.candidate_source.CandidateSource
import com.twitter.product_mixer.core.functional_component.common.alert.Alert
import com.twitter.product_mixer.core.functional_component.feature_hydrator.BaseQueryFeatureHydrator
import com.twitter.product_mixer.core.functional_component.gate.Gate
import com.twitter.product_mixer.core.functional_component.transformer.CandidateFeatureTransformer
import com.twitter.product_mixer.core.functional_component.transformer.CandidatePipelineQueryTransformer
import com.twitter.product_mixer.core.functional_component.transformer.CandidatePipelineResultsTransformer
import com.twitter.product_mixer.core.gate.ParamGate
import com.twitter.product_mixer.core.model.common.identifier.CandidatePipelineIdentifier
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.product_mixer.core.pipeline.candidate.CandidatePipelineConfig
import com.twitter.tweet_mixer.candidate_source.ndr_ann.DRANNKey
import com.twitter.tweet_mixer.candidate_source.ndr_ann.DRMultipleANNQuery
import com.twitter.tweet_mixer.candidate_source.ndr_ann.DeepRetrievalUserTweetANNCandidateSourceFactory
import com.twitter.tweet_mixer.functional_component.hydrator.ContentExplorationDRUserEmbeddingFeature
import com.twitter.tweet_mixer.functional_component.hydrator.ContentExplorationDRUserEmbeddingQueryFeatureHydrator
import com.twitter.tweet_mixer.functional_component.transformer.TweetMixerCandidateFeatureTransformer
import com.twitter.tweet_mixer.model.response.TweetMixerCandidate
import com.twitter.tweet_mixer.param.TweetMixerGlobalParams.ContentExplorationDRUserTweetEnabled
import com.twitter.tweet_mixer.param.TweetMixerGlobalParams.ContentExplorationDRUserTweetVectorDBCollectionName
import com.twitter.tweet_mixer.param.TweetMixerGlobalParams.ContentExplorationDRUserTweetMaxCandidates
import com.twitter.tweet_mixer.service.TweetMixerNotificationConfig.ForYouGroupMap
import com.twitter.tweet_mixer.service.TweetMixerNotificationConfig.defaultEmptyResponseRateAlert
import com.twitter.tweet_mixer.service.TweetMixerNotificationConfig.defaultSuccessRateAlert
import com.twitter.tweet_mixer.utils.CandidatePipelineConstants
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ContentExplorationDRUserTweetCandidatePipelineConfig @Inject() (
  contentExplorationDRUserEmbeddingFeatureHydrator: ContentExplorationDRUserEmbeddingQueryFeatureHydrator,
  deepRetrievalUserTweetANNCandidateSourceFactory: DeepRetrievalUserTweetANNCandidateSourceFactory,
  identifierPrefix: String)
    extends CandidatePipelineConfig[
      PipelineQuery,
      DRMultipleANNQuery,
      TweetMixerCandidate,
      TweetCandidate
    ] {

  override val identifier: CandidatePipelineIdentifier = CandidatePipelineIdentifier(
    identifierPrefix + CandidatePipelineConstants.ContentExplorationDRUserTweet)

  override val gates: Seq[Gate[PipelineQuery]] = Seq(
    ParamGate(
      name = "ContentExplorationDeepRetrievalUserTweetSimilarity",
      param = ContentExplorationDRUserTweetEnabled
    )
  )

  override val queryFeatureHydration: Seq[BaseQueryFeatureHydrator[PipelineQuery, _]] = Seq(
    contentExplorationDRUserEmbeddingFeatureHydrator,
  )

  override val queryTransformer: CandidatePipelineQueryTransformer[
    PipelineQuery,
    DRMultipleANNQuery
  ] = { query =>
    val defaultKey = DRANNKey(
      id = query.getRequiredUserId,
      embedding = query.features
        .flatMap(_.getOrElse(ContentExplorationDRUserEmbeddingFeature, None)),
      collectionName = query.params(ContentExplorationDRUserTweetVectorDBCollectionName),
      maxCandidates = query.params(ContentExplorationDRUserTweetMaxCandidates),
      tier = Some("tier1")
    )
    val keys = Seq(defaultKey)
    DRMultipleANNQuery(keys, false)
  }

  override def candidateSource: CandidateSource[
    DRMultipleANNQuery,
    TweetMixerCandidate
  ] = deepRetrievalUserTweetANNCandidateSourceFactory.build(
    CandidatePipelineConstants.ContentExplorationDRUserTweet)

  override def featuresFromCandidateSourceTransformers: Seq[
    CandidateFeatureTransformer[TweetMixerCandidate]
  ] = Seq(TweetMixerCandidateFeatureTransformer)

  override val resultTransformer: CandidatePipelineResultsTransformer[
    TweetMixerCandidate,
    TweetCandidate
  ] = { candidate =>
    TweetCandidate(id = candidate.tweetId)
  }

  override val alerts: Seq[Alert] = Seq(
    defaultSuccessRateAlert()(ForYouGroupMap),
    defaultEmptyResponseRateAlert()(ForYouGroupMap)
  )
}
