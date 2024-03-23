package com.ExTwitter.product_mixer.component_library.pipeline.candidate.who_to_follow_module

import com.ExTwitter.peoplediscovery.api.{thriftscala => t}
import com.ExTwitter.product_mixer.component_library.candidate_source.people_discovery.PeopleDiscoveryCandidateSource
import com.ExTwitter.product_mixer.component_library.model.candidate.UserCandidate
import com.ExTwitter.product_mixer.core.feature.Feature
import com.ExTwitter.product_mixer.core.functional_component.candidate_source.BaseCandidateSource
import com.ExTwitter.product_mixer.core.functional_component.common.alert.Alert
import com.ExTwitter.product_mixer.core.functional_component.decorator.CandidateDecorator
import com.ExTwitter.product_mixer.core.functional_component.decorator.urt.builder.metadata.BaseFeedbackActionInfoBuilder
import com.ExTwitter.product_mixer.core.functional_component.decorator.urt.builder.timeline_module.BaseModuleDisplayTypeBuilder
import com.ExTwitter.product_mixer.core.functional_component.filter.Filter
import com.ExTwitter.product_mixer.core.functional_component.gate.Gate
import com.ExTwitter.product_mixer.core.functional_component.transformer.CandidateFeatureTransformer
import com.ExTwitter.product_mixer.core.functional_component.transformer.CandidatePipelineQueryTransformer
import com.ExTwitter.product_mixer.core.functional_component.transformer.CandidatePipelineResultsTransformer
import com.ExTwitter.product_mixer.core.model.common.identifier.CandidatePipelineIdentifier
import com.ExTwitter.product_mixer.core.pipeline.PipelineQuery
import com.ExTwitter.product_mixer.core.pipeline.candidate.CandidatePipelineConfig
import com.ExTwitter.timelines.configapi.FSParam
import com.ExTwitter.timelines.configapi.Param
import com.ExTwitter.timelines.configapi.decider.DeciderParam

object WhoToFollowCandidatePipelineConfig {
  val MinCandidatesSize = 3
  val MaxCandidatesSize = 20

  val identifier: CandidatePipelineIdentifier = CandidatePipelineIdentifier("WhoToFollow")
}

class WhoToFollowCandidatePipelineConfig[Query <: PipelineQuery](
  override val identifier: CandidatePipelineIdentifier,
  override val enabledDeciderParam: Option[DeciderParam[Boolean]],
  override val supportedClientParam: Option[FSParam[Boolean]],
  override val alerts: Seq[Alert],
  override val gates: Seq[Gate[Query]],
  whoToFollowCandidateSource: PeopleDiscoveryCandidateSource,
  override val filters: Seq[Filter[Query, UserCandidate]],
  moduleDisplayTypeBuilder: BaseModuleDisplayTypeBuilder[Query, UserCandidate],
  feedbackActionInfoBuilder: Option[
    BaseFeedbackActionInfoBuilder[PipelineQuery, UserCandidate]
  ],
  displayLocationParam: Param[String],
  supportedLayoutsParam: Param[Seq[String]],
  layoutVersionParam: Param[Int],
  excludedUserIdsFeature: Option[Feature[PipelineQuery, Seq[Long]]],
) extends CandidatePipelineConfig[
      Query,
      t.GetModuleRequest,
      t.RecommendedUser,
      UserCandidate
    ] {

  override val candidateSource: BaseCandidateSource[t.GetModuleRequest, t.RecommendedUser] =
    whoToFollowCandidateSource

  override val queryTransformer: CandidatePipelineQueryTransformer[
    PipelineQuery,
    t.GetModuleRequest
  ] = WhoToFollowCandidatePipelineQueryTransformer(
    displayLocationParam,
    supportedLayoutsParam,
    layoutVersionParam,
    excludedUserIdsFeature)

  override val featuresFromCandidateSourceTransformers: Seq[
    CandidateFeatureTransformer[t.RecommendedUser]
  ] = Seq(WhoToFollowResponseFeatureTransformer)

  override val resultTransformer: CandidatePipelineResultsTransformer[
    t.RecommendedUser,
    UserCandidate
  ] = { user => UserCandidate(user.userId) }

  override val decorator: Option[CandidateDecorator[Query, UserCandidate]] =
    Some(WhoToFollowCandidateDecorator(moduleDisplayTypeBuilder, feedbackActionInfoBuilder))
}
