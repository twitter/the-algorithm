package com.twitter.home_mixer.product.for_you

import com.twitter.home_mixer.functional_component.decorator.urt.builder.RelevancePromptCandidateUrtItemBuilder
import com.twitter.home_mixer.functional_component.gate.PersistenceStoreDurationValidationGate
import com.twitter.home_mixer.functional_component.gate.TimelinesPersistenceStoreLastInjectionGate
import com.twitter.home_mixer.product.for_you.model.ForYouQuery
import com.twitter.home_mixer.product.for_you.param.ForYouParam.RelevancePromptEnableParam
import com.twitter.home_mixer.product.for_you.param.ForYouParam.RelevancePromptMinInjectionIntervalParam
import com.twitter.home_mixer.product.for_you.param.ForYouParam.RelevancePromptNegativeParam
import com.twitter.home_mixer.product.for_you.param.ForYouParam.RelevancePromptNeutralParam
import com.twitter.home_mixer.product.for_you.param.ForYouParam.RelevancePromptPositiveParam
import com.twitter.home_mixer.product.for_you.param.ForYouParam.RelevancePromptTitleParam
import com.twitter.product_mixer.component_library.decorator.urt.UrtItemCandidateDecorator
import com.twitter.product_mixer.component_library.model.candidate.RelevancePromptCandidate
import com.twitter.product_mixer.core.functional_component.candidate_source.CandidateSource
import com.twitter.product_mixer.core.functional_component.candidate_source.StaticCandidateSource
import com.twitter.product_mixer.core.functional_component.decorator.CandidateDecorator
import com.twitter.product_mixer.core.functional_component.transformer.CandidatePipelineQueryTransformer
import com.twitter.product_mixer.core.functional_component.transformer.CandidatePipelineResultsTransformer
import com.twitter.product_mixer.core.model.common.identifier.CandidatePipelineIdentifier
import com.twitter.product_mixer.core.model.common.identifier.CandidateSourceIdentifier
import com.twitter.product_mixer.core.pipeline.candidate.CandidatePipelineConfig
import com.twitter.timelines.configapi.FSParam
import com.twitter.timelineservice.model.rich.EntityIdType
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ForYouRelevancePromptCandidatePipelineConfig @Inject() ()
    extends CandidatePipelineConfig[ForYouQuery, Unit, Unit, RelevancePromptCandidate] {

  override val identifier: CandidatePipelineIdentifier =
    CandidatePipelineIdentifier("ForYouRelevancePrompt")

  override val supportedClientParam: Option[FSParam[Boolean]] = Some(RelevancePromptEnableParam)

  override val gates = Seq(
    PersistenceStoreDurationValidationGate(),
    TimelinesPersistenceStoreLastInjectionGate(
      RelevancePromptMinInjectionIntervalParam,
      EntityIdType.Annotation
    )
  )

  override val queryTransformer: CandidatePipelineQueryTransformer[ForYouQuery, Unit] = _ => Unit

  override def candidateSource: CandidateSource[Unit, Unit] = StaticCandidateSource[Unit](
    identifier = CandidateSourceIdentifier(identifier.name),
    result = Seq(Unit)
  )

  override val resultTransformer: CandidatePipelineResultsTransformer[
    Unit,
    RelevancePromptCandidate
  ] = _ => RelevancePromptCandidate(id = "feed-survey-prompt")

  override val decorator: Option[CandidateDecorator[ForYouQuery, RelevancePromptCandidate]] = Some(
    UrtItemCandidateDecorator(
      RelevancePromptCandidateUrtItemBuilder(
        RelevancePromptTitleParam,
        RelevancePromptPositiveParam,
        RelevancePromptNegativeParam,
        RelevancePromptNeutralParam
      ))
  )
}
