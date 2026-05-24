package com.twitter.home_mixer.product.for_you

import com.twitter.candidateservice.core_io.FetchJobRecommendationsView
import com.twitter.home_mixer.functional_component.decorator.urt.builder.FeedbackStrings
import com.twitter.home_mixer.functional_component.gate.DismissFatigueGate
import com.twitter.home_mixer.functional_component.gate.RateLimitGate
import com.twitter.home_mixer.functional_component.gate.TimelinesPersistenceStoreLastInjectionGate
import com.twitter.home_mixer.model.HomeFeatures.DismissInfoFeature
import com.twitter.home_mixer.model.HomeFeatures.ViewerHasJobRecommendationsEnabled
import com.twitter.home_mixer.product.following.model.HomeMixerExternalStrings
import com.twitter.home_mixer.product.for_you.candidate_source.RecommendedJobsCandidateSource
import com.twitter.home_mixer.product.for_you.param.ForYouParam.EnableRecommendedJobsParam
import com.twitter.home_mixer.product.for_you.param.ForYouParam.MaxRecommendedJobCandidatesParam
import com.twitter.home_mixer.product.for_you.param.ForYouParam.RecommendedJobMinInjectionIntervalParam
import com.twitter.product_mixer.component_library.gate.DefinedUserIdGate
import com.twitter.product_mixer.component_library.gate.FeatureGate
import com.twitter.product_mixer.component_library.model.candidate.JobCandidate
import com.twitter.product_mixer.component_library.pipeline.candidate.job.RecommendedJobsCandidateDecorator
import com.twitter.product_mixer.core.functional_component.candidate_source.BaseCandidateSource
import com.twitter.product_mixer.core.functional_component.candidate_source.strato.StratoKeyView
import com.twitter.product_mixer.core.functional_component.decorator.CandidateDecorator
import com.twitter.product_mixer.core.functional_component.transformer.CandidatePipelineQueryTransformer
import com.twitter.product_mixer.core.functional_component.transformer.CandidatePipelineResultsTransformer
import com.twitter.product_mixer.core.model.common.identifier.CandidatePipelineIdentifier
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.product_mixer.core.pipeline.candidate.CandidatePipelineConfig
import com.twitter.product_mixer.core.product.guice.scope.ProductScoped
import com.twitter.stringcenter.client.StringCenter
import com.twitter.timelines.configapi.FSParam
import com.twitter.timelineservice.model.rich.EntityIdType
import com.twitter.timelineservice.suggests.{thriftscala => st}
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton

@Singleton
class ForYouRecommendedJobsCandidatePipelineConfig @Inject() (
  recommendedJobsProductCandidateSource: RecommendedJobsCandidateSource,
  @ProductScoped stringCenterProvider: Provider[StringCenter],
  externalStrings: HomeMixerExternalStrings,
  feedbackStrings: FeedbackStrings)
    extends CandidatePipelineConfig[
      PipelineQuery,
      StratoKeyView[Long, FetchJobRecommendationsView],
      Long,
      JobCandidate
    ] {

  private val stringCenter = stringCenterProvider.get()

  override val supportedClientParam: Option[FSParam[Boolean]] =
    Some(EnableRecommendedJobsParam)

  override val identifier: CandidatePipelineIdentifier =
    CandidatePipelineIdentifier("ForYouRecommendedJobs")

  override val gates = Seq(
    DefinedUserIdGate,
    FeatureGate.fromFeature(ViewerHasJobRecommendationsEnabled),
    RateLimitGate,
    TimelinesPersistenceStoreLastInjectionGate(
      RecommendedJobMinInjectionIntervalParam,
      EntityIdType.JobModule
    ),
    DismissFatigueGate(st.SuggestType.Job, DismissInfoFeature)
  )

  override val queryTransformer: CandidatePipelineQueryTransformer[
    PipelineQuery,
    StratoKeyView[Long, FetchJobRecommendationsView]
  ] = { query =>
    StratoKeyView(
      query.getRequiredUserId,
      FetchJobRecommendationsView(count = Some(query.params(MaxRecommendedJobCandidatesParam))))
  }

  override val candidateSource: BaseCandidateSource[
    StratoKeyView[Long, FetchJobRecommendationsView],
    Long
  ] = recommendedJobsProductCandidateSource

  override val resultTransformer: CandidatePipelineResultsTransformer[
    Long,
    JobCandidate
  ] = { jobResult => JobCandidate(id = jobResult) }

  override val decorator: Option[CandidateDecorator[PipelineQuery, JobCandidate]] = {
    Some(
      RecommendedJobsCandidateDecorator(
        stringCenter = stringCenter,
        headerString = externalStrings.RecommendedJobHeaderString,
        footerString = externalStrings.RecommendedJobFooterString,
        seeLessOftenString = feedbackStrings.seeLessOftenFeedbackString,
        seeLessOftenConfirmationString = feedbackStrings.seeLessOftenConfirmationFeedbackString
      ))
  }
}
