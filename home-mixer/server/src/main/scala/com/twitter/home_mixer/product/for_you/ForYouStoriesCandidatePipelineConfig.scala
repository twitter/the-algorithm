package com.twitter.home_mixer.product.for_you

import com.twitter.home_mixer.functional_component.decorator.StoriesModuleCandidateDecorator
import com.twitter.home_mixer.functional_component.gate.RateLimitGate
import com.twitter.home_mixer.functional_component.gate.TimelinesPersistenceStoreLastInjectionGate
import com.twitter.home_mixer.product.for_you.candidate_source.StoriesModuleCandidateSource
import com.twitter.home_mixer.product.for_you.candidate_source.StoryCandidate
import com.twitter.home_mixer.product.for_you.param.ForYouParam.EnableTrendsParam
import com.twitter.home_mixer.product.for_you.param.ForYouParam.TrendsModuleMinInjectionIntervalParam
import com.twitter.home_mixer.product.for_you.response_transformer.StoriesModuleResponseFeatureTransformer
import com.twitter.product_mixer.component_library.gate.DefinedUserIdGate
import com.twitter.product_mixer.component_library.model.candidate.trends_events.UnifiedTrendCandidate
import com.twitter.product_mixer.core.functional_component.candidate_source.BaseCandidateSource
import com.twitter.product_mixer.core.functional_component.decorator.CandidateDecorator
import com.twitter.product_mixer.core.functional_component.transformer.CandidateFeatureTransformer
import com.twitter.product_mixer.core.functional_component.transformer.CandidatePipelineQueryTransformer
import com.twitter.product_mixer.core.functional_component.transformer.CandidatePipelineResultsTransformer
import com.twitter.product_mixer.core.model.common.identifier.CandidatePipelineIdentifier
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.product_mixer.core.pipeline.candidate.CandidatePipelineConfig
import com.twitter.timelines.configapi.FSParam
import com.twitter.timelineservice.model.rich.EntityIdType
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ForYouStoriesCandidatePipelineConfig @Inject() (
  storiesModule: StoriesModuleCandidateSource,
  storiesModuleCandidateDecorator: StoriesModuleCandidateDecorator)
    extends CandidatePipelineConfig[
      PipelineQuery,
      Long,
      StoryCandidate,
      UnifiedTrendCandidate
    ] {

  override val supportedClientParam: Option[FSParam[Boolean]] = Some(EnableTrendsParam)

  override val identifier: CandidatePipelineIdentifier =
    CandidatePipelineIdentifier("ForYouStories")

  override val gates = Seq(
    RateLimitGate,
    DefinedUserIdGate,
    TimelinesPersistenceStoreLastInjectionGate(
      TrendsModuleMinInjectionIntervalParam,
      EntityIdType.Trends
    )
  )

  override val queryTransformer: CandidatePipelineQueryTransformer[
    PipelineQuery,
    Long
  ] = { query => query.getRequiredUserId }

  override def candidateSource: BaseCandidateSource[Long, StoryCandidate] = storiesModule

  override val featuresFromCandidateSourceTransformers: Seq[
    CandidateFeatureTransformer[StoryCandidate]
  ] = Seq(StoriesModuleResponseFeatureTransformer)

  override val resultTransformer: CandidatePipelineResultsTransformer[
    StoryCandidate,
    UnifiedTrendCandidate
  ] = { story => UnifiedTrendCandidate(id = story.id.toString) }

  override val decorator: Option[CandidateDecorator[PipelineQuery, UnifiedTrendCandidate]] =
    Some(storiesModuleCandidateDecorator.moduleDecorator)
}
