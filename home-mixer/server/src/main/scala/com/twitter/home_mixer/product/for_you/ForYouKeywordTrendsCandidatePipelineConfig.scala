package com.twitter.home_mixer.product.for_you

import com.twitter.events.recos.thriftscala.GetUnfiedCandidatesRequest
import com.twitter.events.recos.{thriftscala => t}
import com.twitter.home_mixer.functional_component.decorator.KeywordTrendsModuleCandidateDecorator
import com.twitter.home_mixer.functional_component.gate.RateLimitGate
import com.twitter.home_mixer.functional_component.gate.TimelinesPersistenceStoreLastInjectionGate
import com.twitter.home_mixer.product.for_you.candidate_source.TrendCandidate
import com.twitter.home_mixer.product.for_you.candidate_source.UnifiedTrendsCandidateSource
import com.twitter.home_mixer.product.for_you.filter.PromotedTrendFilter
import com.twitter.home_mixer.product.for_you.param.ForYouParam.EnableKeywordTrendsParam
import com.twitter.home_mixer.product.for_you.param.ForYouParam.KeywordTrendsModuleMinInjectionIntervalParam
import com.twitter.home_mixer.product.for_you.param.ForYouParam.MaxNumberKeywordTrendsParam
import com.twitter.home_mixer.product.for_you.query_transformer.UnifiedCandidatesQueryTransformer
import com.twitter.home_mixer.product.for_you.response_transformer.KeywordTrendsFeatureTransformer
import com.twitter.product_mixer.component_library.gate.DefinedUserIdGate
import com.twitter.product_mixer.component_library.model.candidate.trends_events.UnifiedTrendCandidate
import com.twitter.product_mixer.core.functional_component.candidate_source.CandidateSource
import com.twitter.product_mixer.core.functional_component.decorator.CandidateDecorator
import com.twitter.product_mixer.core.functional_component.filter.Filter
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
class ForYouKeywordTrendsCandidatePipelineConfig @Inject() (
  unifiedTrendsCandidateSource: UnifiedTrendsCandidateSource,
  keywordTrendsModuleCandidateDecorator: KeywordTrendsModuleCandidateDecorator)
    extends CandidatePipelineConfig[
      PipelineQuery,
      t.GetUnfiedCandidatesRequest,
      TrendCandidate,
      UnifiedTrendCandidate
    ] {

  override val identifier: CandidatePipelineIdentifier = CandidatePipelineIdentifier(
    "ForYouKeywordTrends")

  override val supportedClientParam: Option[FSParam[Boolean]] = Some(EnableKeywordTrendsParam)

  override val gates = Seq(
    RateLimitGate,
    DefinedUserIdGate,
    TimelinesPersistenceStoreLastInjectionGate(
      KeywordTrendsModuleMinInjectionIntervalParam,
      EntityIdType.Trends
    )
  )

  override val queryTransformer: CandidatePipelineQueryTransformer[
    PipelineQuery,
    GetUnfiedCandidatesRequest
  ] = UnifiedCandidatesQueryTransformer(
    maxResultsParam = MaxNumberKeywordTrendsParam,
    candidatePipelineIdentifier = identifier)

  override val candidateSource: CandidateSource[
    t.GetUnfiedCandidatesRequest,
    TrendCandidate
  ] = unifiedTrendsCandidateSource

  override val featuresFromCandidateSourceTransformers: Seq[
    CandidateFeatureTransformer[TrendCandidate]
  ] = Seq(KeywordTrendsFeatureTransformer)

  override val resultTransformer: CandidatePipelineResultsTransformer[
    TrendCandidate,
    UnifiedTrendCandidate
  ] = { result => UnifiedTrendCandidate(id = result.candidate.trendName) }

  override val filters: Seq[Filter[PipelineQuery, UnifiedTrendCandidate]] = Seq(PromotedTrendFilter)

  override val decorator: Option[CandidateDecorator[PipelineQuery, UnifiedTrendCandidate]] =
    Some(keywordTrendsModuleCandidateDecorator.moduleDecorator)

}
