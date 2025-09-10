package com.twitter.tweet_mixer.candidate_pipeline

import com.twitter.product_mixer.component_library.feature_hydrator.query.param_gated.ParamGatedQueryFeatureHydrator
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
import com.twitter.tweet_mixer.functional_component.hydrator.DeepRetrievalUserEmbeddingFeature
import com.twitter.tweet_mixer.functional_component.hydrator.DeepRetrievalUserEmbeddingQueryFeatureHydrator
import com.twitter.tweet_mixer.functional_component.hydrator.GrokCategoriesFeature
import com.twitter.tweet_mixer.functional_component.hydrator.GrokCategoriesQueryFeatureHydrator
import com.twitter.tweet_mixer.functional_component.transformer.TweetMixerCandidateFeatureTransformer
import com.twitter.tweet_mixer.model.response.TweetMixerCandidate
import com.twitter.tweet_mixer.param.TweetMixerGlobalParams.DeepRetrievalCategoricalUserTweetANNEnabled
import com.twitter.tweet_mixer.param.TweetMixerGlobalParams.DeepRetrievalEnableGPU
import com.twitter.tweet_mixer.param.TweetMixerGlobalParams.DeepRetrievalIsHighQualityEnabled
import com.twitter.tweet_mixer.param.TweetMixerGlobalParams.DeepRetrievalIsLowNegEngRatioEnabled
import com.twitter.tweet_mixer.param.TweetMixerGlobalParams.DeepRetrievalUserTweetANNEnabled
import com.twitter.tweet_mixer.param.TweetMixerGlobalParams.DeepRetrievalUserTweetANNMaxCandidates
import com.twitter.tweet_mixer.param.TweetMixerGlobalParams.DeepRetrievalUserTweetANNScoreThreshold
import com.twitter.tweet_mixer.param.TweetMixerGlobalParams.DeepRetrievalVectorDBCollectionName
import com.twitter.tweet_mixer.service.TweetMixerNotificationConfig.ForYouGroupMap
import com.twitter.tweet_mixer.service.TweetMixerNotificationConfig.defaultEmptyResponseRateAlert
import com.twitter.tweet_mixer.service.TweetMixerNotificationConfig.defaultSuccessRateAlert
import com.twitter.tweet_mixer.utils.CandidatePipelineConstants
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DeepRetrievalUserTweetSimilarityCandidatePipelineConfig @Inject() (
  deepRetrievalUserEmbeddingFeatureHydrator: DeepRetrievalUserEmbeddingQueryFeatureHydrator,
  deepRetrievalUserTweetANNCandidateSourceFactory: DeepRetrievalUserTweetANNCandidateSourceFactory,
  grokCategoriesQueryFeatureHydrator: GrokCategoriesQueryFeatureHydrator,
  identifierPrefix: String)
    extends CandidatePipelineConfig[
      PipelineQuery,
      DRMultipleANNQuery,
      TweetMixerCandidate,
      TweetCandidate
    ] {

  override val identifier: CandidatePipelineIdentifier = CandidatePipelineIdentifier(
    identifierPrefix + CandidatePipelineConstants.DeepRetrievalUserTweetSimilarity)

  override val gates: Seq[Gate[PipelineQuery]] = Seq(
    ParamGate(
      name = "DeepRetrievalUserTweetANN",
      param = DeepRetrievalUserTweetANNEnabled
    )
  )

  override val queryFeatureHydration: Seq[BaseQueryFeatureHydrator[PipelineQuery, _]] = Seq(
    deepRetrievalUserEmbeddingFeatureHydrator,
    ParamGatedQueryFeatureHydrator(
      DeepRetrievalCategoricalUserTweetANNEnabled,
      grokCategoriesQueryFeatureHydrator
    )
  )

  override val queryTransformer: CandidatePipelineQueryTransformer[
    PipelineQuery,
    DRMultipleANNQuery
  ] = { query =>
    val defaultKey = DRANNKey(
      id = query.getRequiredUserId,
      embedding = query.features
        .flatMap(_.getOrElse(DeepRetrievalUserEmbeddingFeature, None)),
      collectionName = query.params(DeepRetrievalVectorDBCollectionName),
      maxCandidates = query.params(DeepRetrievalUserTweetANNMaxCandidates),
      scoreThreshold = query.params(DeepRetrievalUserTweetANNScoreThreshold),
      enableGPU = query.params(DeepRetrievalEnableGPU),
      isHighQuality = if (query.params(DeepRetrievalIsHighQualityEnabled)) Some(true) else None,
      isLowNegEngRatio =
        if (query.params(DeepRetrievalIsLowNegEngRatioEnabled)) Some(false) else None
    )
    val categories =
      if (query.params(DeepRetrievalCategoricalUserTweetANNEnabled))
        query.features.get.getOrElse(GrokCategoriesFeature, None)
      else None
    val keysOptSeq = categories.map(_.map(_.toString)).map { categories =>
      categories.map { category =>
        DRANNKey(
          id = query.getRequiredUserId,
          embedding = query.features
            .flatMap(_.getOrElse(DeepRetrievalUserEmbeddingFeature, None)),
          collectionName = query.params(DeepRetrievalVectorDBCollectionName),
          maxCandidates = query.params(DeepRetrievalUserTweetANNMaxCandidates),
          category = Some(category),
          isHighQuality = if (query.params(DeepRetrievalIsHighQualityEnabled)) Some(true) else None,
          isLowNegEngRatio =
            if (query.params(DeepRetrievalIsLowNegEngRatioEnabled)) Some(false) else None
        )
      }
    }
    val keys = keysOptSeq.getOrElse(Seq(defaultKey))
    DRMultipleANNQuery(keys, false)
  }

  override def candidateSource: CandidateSource[
    DRMultipleANNQuery,
    TweetMixerCandidate
  ] = deepRetrievalUserTweetANNCandidateSourceFactory.build(
    identifierPrefix + CandidatePipelineConstants.DeepRetrievalUserTweetSimilarity)

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
