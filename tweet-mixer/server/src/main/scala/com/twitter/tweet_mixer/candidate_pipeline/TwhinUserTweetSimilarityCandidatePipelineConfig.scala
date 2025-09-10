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
import com.twitter.tweet_mixer.candidate_source.ndr_ann.EmbeddingANNKey
import com.twitter.tweet_mixer.candidate_source.ndr_ann.EmbeddingMultipleANNQuery
import com.twitter.tweet_mixer.candidate_source.ndr_ann.EmbeddingANNCandidateSourceFactory
import com.twitter.tweet_mixer.functional_component.hydrator.TwhinUserPositiveEmbeddingFeature
import com.twitter.tweet_mixer.functional_component.hydrator.TwhinUserPositiveEmbeddingQueryFeatureHydrator
import com.twitter.tweet_mixer.functional_component.transformer.TweetMixerCandidateFeatureTransformer
import com.twitter.tweet_mixer.model.response.TweetMixerCandidate
import com.twitter.tweet_mixer.param.TweetMixerGlobalParams.TwhinRebuildUserTweetSimilarityEnabled
import com.twitter.tweet_mixer.param.TweetMixerGlobalParams.TwhinRebuildUserTweetSimilarityMaxCandidates
import com.twitter.tweet_mixer.param.TweetMixerGlobalParams.TwhinRebuildUserTweetVectorDBName
import com.twitter.tweet_mixer.service.TweetMixerNotificationConfig.ForYouGroupMap
import com.twitter.tweet_mixer.service.TweetMixerNotificationConfig.defaultEmptyResponseRateAlert
import com.twitter.tweet_mixer.service.TweetMixerNotificationConfig.defaultSuccessRateAlert
import com.twitter.tweet_mixer.utils.CandidatePipelineConstants
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TwhinUserTweetSimilarityCandidatePipelineConfig @Inject() (
  twhinUserPositiveEmbeddingQueryFeatureHydrator: TwhinUserPositiveEmbeddingQueryFeatureHydrator,
  embeddingANNCandidateSourceFactory: EmbeddingANNCandidateSourceFactory,
  identifierPrefix: String)
    extends CandidatePipelineConfig[
      PipelineQuery,
      EmbeddingMultipleANNQuery,
      TweetMixerCandidate,
      TweetCandidate
    ] {

  override val identifier: CandidatePipelineIdentifier = CandidatePipelineIdentifier(
    identifierPrefix + CandidatePipelineConstants.TwhinRebuildUserTweetSimilarity)

  override val gates: Seq[Gate[PipelineQuery]] = Seq(
    ParamGate(
      name = "TwhinUserTweetANN",
      param = TwhinRebuildUserTweetSimilarityEnabled
    )
  )

  override val queryFeatureHydration: Seq[BaseQueryFeatureHydrator[PipelineQuery, _]] = Seq(
    twhinUserPositiveEmbeddingQueryFeatureHydrator,
  )

  override val queryTransformer: CandidatePipelineQueryTransformer[
    PipelineQuery,
    EmbeddingMultipleANNQuery
  ] = { query =>
    val defaultKey = EmbeddingANNKey(
      id = query.getRequiredUserId,
      embedding = query.features
        .flatMap(_.getOrElse(TwhinUserPositiveEmbeddingFeature, None)),
      collectionName = query.params(TwhinRebuildUserTweetVectorDBName),
      maxCandidates = query.params(TwhinRebuildUserTweetSimilarityMaxCandidates),
    )

    EmbeddingMultipleANNQuery(Seq(defaultKey), false)
  }

  override def candidateSource: CandidateSource[
    EmbeddingMultipleANNQuery,
    TweetMixerCandidate
  ] = embeddingANNCandidateSourceFactory.build(
    identifierPrefix + CandidatePipelineConstants.TwhinRebuildUserTweetSimilarity)

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
