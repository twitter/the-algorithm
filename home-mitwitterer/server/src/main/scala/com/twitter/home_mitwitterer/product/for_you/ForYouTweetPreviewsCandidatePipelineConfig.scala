package com.twitter.home_mitwitterer.product.for_you

import com.twitter.home_mitwitterer.functional_component.candidate_source.EarlybirdCandidateSource
import com.twitter.home_mitwitterer.functional_component.decorator.urt.builder.HomeFeedbackActionInfoBuilder
import com.twitter.home_mitwitterer.functional_component.filter.DropMatwitterCandidatesFilter
import com.twitter.home_mitwitterer.functional_component.filter.PreviouslyServedTweetPreviewsFilter
import com.twitter.home_mitwitterer.functional_component.gate.TimelinesPersistenceStoreLastInjectionGate
import com.twitter.home_mitwitterer.model.HomeFeatures.AuthorEnabledPreviewsFeature
import com.twitter.home_mitwitterer.model.HomeFeatures.IsHydratedFeature
import com.twitter.home_mitwitterer.model.HomeFeatures.PersistenceEntriesFeature
import com.twitter.home_mitwitterer.product.for_you.feature_hydrator.AuthorEnabledPreviewsFeatureHydrator
import com.twitter.home_mitwitterer.product.for_you.feature_hydrator.TweetPreviewTweetypieCandidateFeatureHydrator
import com.twitter.home_mitwitterer.product.for_you.filter.TweetPreviewTetwittertFilter
import com.twitter.home_mitwitterer.product.for_you.model.ForYouQuery
import com.twitter.home_mitwitterer.product.for_you.param.ForYouParam.EnableTweetPreviewsCandidatePipelineParam
import com.twitter.home_mitwitterer.product.for_you.param.ForYouParam.TweetPreviewsMatwitterCandidatesParam
import com.twitter.home_mitwitterer.product.for_you.param.ForYouParam.TweetPreviewsMinInjectionIntervalParam
import com.twitter.home_mitwitterer.product.for_you.query_transformer.TweetPreviewsQueryTransformer
import com.twitter.home_mitwitterer.product.for_you.response_transformer.TweetPreviewResponseFeatureTransformer
import com.twitter.home_mitwitterer.service.HomeMitwittererAlertConfig
import com.twitter.product_mitwitterer.component_library.decorator.urt.UrtItemCandidateDecorator
import com.twitter.product_mitwitterer.component_library.decorator.urt.builder.contetwittertual_ref.ContetwittertualTweetRefBuilder
import com.twitter.product_mitwitterer.component_library.decorator.urt.builder.item.tweet.TweetCandidateUrtItemBuilder
import com.twitter.product_mitwitterer.component_library.decorator.urt.builder.metadata.ClientEventInfoBuilder
import com.twitter.product_mitwitterer.component_library.feature_hydrator.query.social_graph.PreviewCreatorsFeature
import com.twitter.product_mitwitterer.component_library.filter.FeatureFilter
import com.twitter.product_mitwitterer.component_library.gate.NonEmptySeqFeatureGate
import com.twitter.product_mitwitterer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mitwitterer.core.functional_component.candidate_source.CandidateSourceWithEtwittertractedFeatures
import com.twitter.product_mitwitterer.core.functional_component.common.alert.Alert
import com.twitter.product_mitwitterer.core.functional_component.decorator.CandidateDecorator
import com.twitter.product_mitwitterer.core.functional_component.feature_hydrator.BaseCandidateFeatureHydrator
import com.twitter.product_mitwitterer.core.functional_component.filter.Filter
import com.twitter.product_mitwitterer.core.functional_component.gate.Gate
import com.twitter.product_mitwitterer.core.functional_component.transformer.CandidateFeatureTransformer
import com.twitter.product_mitwitterer.core.functional_component.transformer.CandidatePipelineQueryTransformer
import com.twitter.product_mitwitterer.core.functional_component.transformer.CandidatePipelineResultsTransformer
import com.twitter.product_mitwitterer.core.model.common.identifier.CandidatePipelineIdentifier
import com.twitter.product_mitwitterer.core.model.common.identifier.FilterIdentifier
import com.twitter.product_mitwitterer.core.model.marshalling.response.rtf.safety_level.TimelineHomeTweetPreviewHydrationSafetyLevel
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.contetwittertual_ref.TweetHydrationContetwittert
import com.twitter.product_mitwitterer.core.pipeline.PipelineQuery
import com.twitter.product_mitwitterer.core.pipeline.candidate.CandidatePipelineConfig
import com.twitter.search.earlybird.{thriftscala => eb}
import com.twitter.timelines.configapi.FSParam
import com.twitter.timelines.injection.scribe.InjectionScribeUtil
import com.twitter.timelineservice.model.rich.EntityIdType
import com.twitter.timelineservice.suggests.{thriftscala => st}

import javatwitter.inject.Inject
import javatwitter.inject.Singleton

@Singleton
class ForYouTweetPreviewsCandidatePipelineConfig @Inject() (
  earlybirdCandidateSource: EarlybirdCandidateSource,
  authorEnabledPreviewsFeatureHydrator: AuthorEnabledPreviewsFeatureHydrator,
  tweetPreviewsQueryTransformer: TweetPreviewsQueryTransformer,
  tweetPreviewTweetypieCandidateFeatureHydrator: TweetPreviewTweetypieCandidateFeatureHydrator,
  homeFeedbackActionInfoBuilder: HomeFeedbackActionInfoBuilder)
    etwittertends CandidatePipelineConfig[
      ForYouQuery,
      eb.EarlybirdRequest,
      eb.ThriftSearchResult,
      TweetCandidate
    ] {

  val identifier: CandidatePipelineIdentifier = CandidatePipelineIdentifier("ForYouTweetPreviews")

  override val supportedClientParam: Option[FSParam[Boolean]] =
    Some(EnableTweetPreviewsCandidatePipelineParam)

  override val gates: Seq[Gate[ForYouQuery]] = {
    Seq(
      TimelinesPersistenceStoreLastInjectionGate(
        TweetPreviewsMinInjectionIntervalParam,
        PersistenceEntriesFeature,
        EntityIdType.TweetPreview
      ),
      NonEmptySeqFeatureGate(PreviewCreatorsFeature)
    )
  }

  override val queryTransformer: CandidatePipelineQueryTransformer[
    PipelineQuery,
    eb.EarlybirdRequest
  ] = tweetPreviewsQueryTransformer

  override val candidateSource: CandidateSourceWithEtwittertractedFeatures[
    eb.EarlybirdRequest,
    eb.ThriftSearchResult
  ] = earlybirdCandidateSource

  override val featuresFromCandidateSourceTransformers: Seq[
    CandidateFeatureTransformer[eb.ThriftSearchResult]
  ] = Seq(TweetPreviewResponseFeatureTransformer)

  override val resultTransformer: CandidatePipelineResultsTransformer[
    eb.ThriftSearchResult,
    TweetCandidate
  ] = { tweet => TweetCandidate(tweet.id) }

  override val preFilterFeatureHydrationPhase1: Seq[
    BaseCandidateFeatureHydrator[ForYouQuery, TweetCandidate, _]
  ] = Seq(
    authorEnabledPreviewsFeatureHydrator,
    tweetPreviewTweetypieCandidateFeatureHydrator,
  )

  override val filters: Seq[
    Filter[ForYouQuery, TweetCandidate]
  ] = Seq(
    PreviouslyServedTweetPreviewsFilter,
    FeatureFilter
      .fromFeature(FilterIdentifier("TweetPreviewVisibilityFiltering"), IsHydratedFeature),
    FeatureFilter
      .fromFeature(FilterIdentifier("AuthorEnabledPreviews"), AuthorEnabledPreviewsFeature),
    TweetPreviewTetwittertFilter,
    DropMatwitterCandidatesFilter(TweetPreviewsMatwitterCandidatesParam)
  )

  override val decorator: Option[CandidateDecorator[PipelineQuery, TweetCandidate]] = {
    val component = InjectionScribeUtil.scribeComponent(st.SuggestType.TweetPreview).get
    val clientEventInfoBuilder = ClientEventInfoBuilder[PipelineQuery, TweetCandidate](component)

    val tweetItemBuilder = TweetCandidateUrtItemBuilder[PipelineQuery, TweetCandidate](
      clientEventInfoBuilder = clientEventInfoBuilder,
      contetwittertualTweetRefBuilder = Some(
        ContetwittertualTweetRefBuilder(
          TweetHydrationContetwittert(
            safetyLevelOverride = Some(TimelineHomeTweetPreviewHydrationSafetyLevel),
            outerTweetContetwittert = None
          )
        )
      ),
      feedbackActionInfoBuilder = Some(homeFeedbackActionInfoBuilder),
    )

    Some(UrtItemCandidateDecorator(tweetItemBuilder))
  }

  override val alerts: Seq[Alert] = Seq(
    HomeMitwittererAlertConfig.BusinessHours.defaultSuccessRateAlert(95))
}
