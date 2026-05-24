package com.twitter.tweet_mixer.candidate_pipeline

import com.twitter.product_mixer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mixer.core.functional_component.candidate_source.CandidateSource
import com.twitter.product_mixer.core.functional_component.common.alert.Alert
import com.twitter.product_mixer.core.functional_component.common.alert.NotificationGroup
import com.twitter.product_mixer.core.functional_component.feature_hydrator.BaseQueryFeatureHydrator
import com.twitter.product_mixer.core.functional_component.gate.Gate
import com.twitter.product_mixer.core.functional_component.transformer.CandidateFeatureTransformer
import com.twitter.product_mixer.core.functional_component.transformer.CandidatePipelineQueryTransformer
import com.twitter.product_mixer.core.functional_component.transformer.CandidatePipelineResultsTransformer
import com.twitter.product_mixer.core.gate.ParamGate
import com.twitter.product_mixer.core.model.common.identifier.CandidatePipelineIdentifier
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.product_mixer.core.pipeline.candidate.CandidatePipelineConfig
import com.twitter.timelines.configapi.FSBoundedParam
import com.twitter.timelines.configapi.FSParam
import com.twitter.tweet_mixer.candidate_source.ndr_ann.DRANNKey
import com.twitter.tweet_mixer.candidate_source.ndr_ann.DRMultipleANNQuery
import com.twitter.tweet_mixer.candidate_source.ndr_ann.MediaDeepRetrievalUserTweetANNCandidateSourceFactory
import com.twitter.tweet_mixer.functional_component.hydrator.MediaDeepRetrievalUserEmbeddingFeature
import com.twitter.tweet_mixer.functional_component.hydrator.MediaDeepRetrievalUserEmbeddingQueryFeatureHydrator
import com.twitter.tweet_mixer.functional_component.hydrator.MediaEvergreenUserEmbeddingQueryFeatureHydrator
import com.twitter.tweet_mixer.functional_component.hydrator.MediaPromotedCreatorUserEmbeddingQueryFeatureHydrator
import com.twitter.tweet_mixer.functional_component.transformer.TweetMixerCandidateFeatureTransformer
import com.twitter.tweet_mixer.model.response.TweetMixerCandidate
import com.twitter.tweet_mixer.param.TweetMixerGlobalParams.MediaDeepRetrievalIsHighQualityEnabled
import com.twitter.tweet_mixer.param.TweetMixerGlobalParams.MediaDeepRetrievalIsLowNegEngRatioEnabled
import com.twitter.tweet_mixer.param.TweetMixerGlobalParams.MediaDeepRetrievalUserTweetANNEnabled
import com.twitter.tweet_mixer.param.TweetMixerGlobalParams.MediaDeepRetrievalUserTweetANNMaxCandidates
import com.twitter.tweet_mixer.param.TweetMixerGlobalParams.MediaDeepRetrievalVectorDBCollectionName
import com.twitter.tweet_mixer.param.TweetMixerGlobalParams.MediaEvergreenDeepRetrievalUserTweetANNEnabled
import com.twitter.tweet_mixer.param.TweetMixerGlobalParams.MediaEvergreenDeepRetrievalUserTweetANNMaxCandidates
import com.twitter.tweet_mixer.param.TweetMixerGlobalParams.MediaEvergreenDeepRetrievalVectorDBCollectionName
import com.twitter.tweet_mixer.param.TweetMixerGlobalParams.MediaPromotedCreatorDeepRetrievalUserTweetANNEnabled
import com.twitter.tweet_mixer.param.TweetMixerGlobalParams.MediaPromotedCreatorDeepRetrievalUserTweetANNMaxCandidates
import com.twitter.tweet_mixer.param.TweetMixerGlobalParams.MediaPromotedCreatorDeepRetrievalVectorDBCollectionName
import com.twitter.tweet_mixer.utils.CandidatePipelineConstants
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MediaDeepRetrievalUserTweetSimilarityCandidatePipelineConfigFactory @Inject() (
  mediaDeepRetrievalUserEmbeddingFeatureHydrator: MediaDeepRetrievalUserEmbeddingQueryFeatureHydrator,
  mediaDeepRetrievalUserTweetANNCandidateSourceFactory: MediaDeepRetrievalUserTweetANNCandidateSourceFactory) {

  def build[Query <: PipelineQuery](
    identifierPrefix: String,
    contentCategorySignalFn: Query => Option[Seq[Long]] = (q: Query) => None,
    alerts: Seq[Alert] = Seq.empty
  )(
    implicit notificationGroup: Map[String, NotificationGroup]
  ): MediaDeepRetrievalUserTweetSimilarityCandidatePipelineConfig[Query] = {
    new MediaDeepRetrievalUserTweetSimilarityCandidatePipelineConfig(
      mediaDeepRetrievalUserEmbeddingFeatureHydrator,
      mediaDeepRetrievalUserTweetANNCandidateSourceFactory,
      identifierPrefix + CandidatePipelineConstants.MediaDeepRetrievalUserTweetSimilarity,
      MediaDeepRetrievalVectorDBCollectionName,
      MediaDeepRetrievalUserTweetANNEnabled,
      MediaDeepRetrievalUserTweetANNMaxCandidates,
      contentCategorySignalFn,
      alerts = alerts
    )
  }
}

@Singleton
class MediaEvergreenDeepRetrievalUserTweetSimilarityCandidatePipelineConfigFactory @Inject() (
  mediaEvergreenUserEmbeddingQueryFeatureHydrator: MediaEvergreenUserEmbeddingQueryFeatureHydrator,
  mediaDeepRetrievalUserTweetANNCandidateSourceFactory: MediaDeepRetrievalUserTweetANNCandidateSourceFactory) {

  def build[Query <: PipelineQuery](
    identifierPrefix: String,
    alerts: Seq[Alert] = Seq.empty
  )(
    implicit notificationGroup: Map[String, NotificationGroup]
  ): MediaDeepRetrievalUserTweetSimilarityCandidatePipelineConfig[Query] = {
    new MediaDeepRetrievalUserTweetSimilarityCandidatePipelineConfig(
      mediaEvergreenUserEmbeddingQueryFeatureHydrator,
      mediaDeepRetrievalUserTweetANNCandidateSourceFactory,
      identifierPrefix + CandidatePipelineConstants.MediaEvergreenDeepRetrievalUserTweetSimilarity,
      MediaEvergreenDeepRetrievalVectorDBCollectionName,
      MediaEvergreenDeepRetrievalUserTweetANNEnabled,
      MediaEvergreenDeepRetrievalUserTweetANNMaxCandidates,
      alerts = alerts
    )
  }
}

@Singleton
class MediaPromotedCreatorDeepRetrievalUserTweetSimilarityCandidatePipelineConfigFactory @Inject() (
  mediaPromotedCreatorUserEmbeddingQueryFeatureHydrator: MediaPromotedCreatorUserEmbeddingQueryFeatureHydrator,
  mediaDeepRetrievalUserTweetANNCandidateSourceFactory: MediaDeepRetrievalUserTweetANNCandidateSourceFactory) {

  def build[Query <: PipelineQuery](
    identifierPrefix: String,
    alerts: Seq[Alert] = Seq.empty
  )(
    implicit notificationGroup: Map[String, NotificationGroup]
  ): MediaDeepRetrievalUserTweetSimilarityCandidatePipelineConfig[Query] = {
    new MediaDeepRetrievalUserTweetSimilarityCandidatePipelineConfig(
      mediaPromotedCreatorUserEmbeddingQueryFeatureHydrator,
      mediaDeepRetrievalUserTweetANNCandidateSourceFactory,
      identifierPrefix + CandidatePipelineConstants.MediaPromotedCreatorDeepRetrievalUserTweetSimilarity,
      MediaPromotedCreatorDeepRetrievalVectorDBCollectionName,
      MediaPromotedCreatorDeepRetrievalUserTweetANNEnabled,
      MediaPromotedCreatorDeepRetrievalUserTweetANNMaxCandidates,
      alerts = alerts
    )
  }
}

class MediaDeepRetrievalUserTweetSimilarityCandidatePipelineConfig[Query <: PipelineQuery](
  mediaDeepRetrievalUserEmbeddingFeatureHydrator: MediaDeepRetrievalUserEmbeddingQueryFeatureHydrator,
  mediaDeepRetrievalUserTweetANNCandidateSourceFactory: MediaDeepRetrievalUserTweetANNCandidateSourceFactory,
  identifierFullName: String,
  vecDBCollectionName: FSParam[String],
  enableMediaDeepRetrievalAnn: FSParam[Boolean],
  candidate: FSBoundedParam[Int],
  contentCategorySignalFn: Query => Option[Seq[Long]] = (q: Query) => None,
  override val alerts: Seq[Alert]
)(
  implicit notificationGroup: Map[String, NotificationGroup])
    extends CandidatePipelineConfig[
      Query,
      DRMultipleANNQuery,
      TweetMixerCandidate,
      TweetCandidate
    ] {

  override val identifier: CandidatePipelineIdentifier =
    CandidatePipelineIdentifier(identifierFullName)

  override val gates: Seq[Gate[PipelineQuery]] = Seq(
    ParamGate(
      name = "MediaDeepRetrievalUserTweetANN",
      param = enableMediaDeepRetrievalAnn
    )
  )

  override val queryFeatureHydration: Seq[BaseQueryFeatureHydrator[Query, _]] = Seq(
    mediaDeepRetrievalUserEmbeddingFeatureHydrator)

  override val queryTransformer: CandidatePipelineQueryTransformer[
    Query,
    DRMultipleANNQuery
  ] = { query =>
    val defaultKey = DRANNKey(
      id = query.getRequiredUserId,
      embedding = query.features.flatMap(_.getOrElse(MediaDeepRetrievalUserEmbeddingFeature, None)),
      collectionName = query.params(vecDBCollectionName),
      maxCandidates = query.params(candidate),
      isHighQuality =
        if (query.params(MediaDeepRetrievalIsHighQualityEnabled)) Some(true) else None,
      isLowNegEngRatio =
        if (query.params(MediaDeepRetrievalIsLowNegEngRatioEnabled)) Some(false) else None
    )
    val keysOptSeq = contentCategorySignalFn(query).map(_.map(_.toString)).map { categories =>
      categories.map { category =>
        DRANNKey(
          id = query.getRequiredUserId,
          embedding =
            query.features.flatMap(_.getOrElse(MediaDeepRetrievalUserEmbeddingFeature, None)),
          collectionName = query.params(vecDBCollectionName),
          maxCandidates = query.params(candidate),
          category = Some(category),
          isHighQuality =
            if (query.params(MediaDeepRetrievalIsHighQualityEnabled)) Some(true) else None,
          isLowNegEngRatio =
            if (query.params(MediaDeepRetrievalIsLowNegEngRatioEnabled)) Some(false) else None
        )
      }
    }
    val keys = keysOptSeq.getOrElse(Seq(defaultKey))
    DRMultipleANNQuery(keys, false)
  }

  override def candidateSource: CandidateSource[
    DRMultipleANNQuery,
    TweetMixerCandidate
  ] = mediaDeepRetrievalUserTweetANNCandidateSourceFactory.build(identifierFullName)

  override def featuresFromCandidateSourceTransformers: Seq[
    CandidateFeatureTransformer[TweetMixerCandidate]
  ] = Seq(TweetMixerCandidateFeatureTransformer)

  override val resultTransformer: CandidatePipelineResultsTransformer[
    TweetMixerCandidate,
    TweetCandidate
  ] = { candidate => TweetCandidate(id = candidate.tweetId) }
}
