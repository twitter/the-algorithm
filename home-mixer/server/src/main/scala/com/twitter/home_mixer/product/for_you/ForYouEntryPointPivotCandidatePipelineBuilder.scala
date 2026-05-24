package com.twitter.home_mixer.product.for_you

import com.twitter.home_mixer.functional_component.decorator.EntryPointPivotModuleCandidateDecorator
import com.twitter.home_mixer.functional_component.decorator.StrEntryPointPivotCategoryText
import com.twitter.home_mixer.product.for_you.gate.FollowingSportsUserGate
import com.twitter.product_mixer.component_library.candidate_source.entry_point_pivot.EntryPointPivotCandidateSource
import com.twitter.product_mixer.component_library.candidate_source.entry_point_pivot.GrokEntryPointPivotCandidateSource
import com.twitter.product_mixer.component_library.model.candidate.pivot.PivotCandidate
import com.twitter.product_mixer.core.functional_component.candidate_source.CandidateSource
import com.twitter.product_mixer.core.functional_component.candidate_source.strato.StratoKeyView
import com.twitter.product_mixer.core.functional_component.decorator.CandidateDecorator
import com.twitter.product_mixer.core.functional_component.gate.Gate
import com.twitter.product_mixer.core.model.common.identifier.CandidatePipelineIdentifier
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.timelines.configapi.FSParam
import com.twitter.timelines.configapi.Param
import com.twitter.timelines.render.{thriftscala => t}
import com.twitter.util.Duration
import javax.inject.Inject
import javax.inject.Singleton

object ForYouEntryPointPivotCandidatePipelineBuilder {
  sealed abstract class EntryPointPivotType()

  object EntryPointPivotType {
    case object Events extends EntryPointPivotType()

    case object Grok extends EntryPointPivotType()
  }
}
@Singleton
class ForYouEntryPointPivotCandidatePipelineBuilder @Inject() (
  entryPointPivotCandidateSource: EntryPointPivotCandidateSource,
  grokEntryPointPivotCandidateSource: GrokEntryPointPivotCandidateSource) {

  import ForYouEntryPointPivotCandidatePipelineBuilder._

  def build(
    entryPointPivotType: EntryPointPivotType,
    supportedClientParam: FSParam[Boolean],
    pivotMinInjectionIntervalParam: Param[Duration]
  ): ForYouEntryPointPivotCandidatePipelineConfig =
    new ForYouEntryPointPivotCandidatePipelineConfig(
      identifier = getCandidateIdentifier(entryPointPivotType),
      supportedClientParam = Some(supportedClientParam),
      candidateSource = getCandidateSource(entryPointPivotType),
      decorator = Some(getDecorator(entryPointPivotType)),
      pivotMinInjectionIntervalParam = pivotMinInjectionIntervalParam,
      extraGates = getPipelineGates(entryPointPivotType)
    )

  private def getCandidateSource(entryPointPivotType: EntryPointPivotType): CandidateSource[
    StratoKeyView[String, Unit],
    t.Pivot
  ] =
    entryPointPivotType match {
      case EntryPointPivotType.Grok =>
        grokEntryPointPivotCandidateSource
      case EntryPointPivotType.Events =>
        entryPointPivotCandidateSource
    }

  private def getDecorator(
    entryPointPivotType: EntryPointPivotType
  ): CandidateDecorator[PipelineQuery, PivotCandidate] =
    entryPointPivotType match {
      case EntryPointPivotType.Grok =>
        EntryPointPivotModuleCandidateDecorator(
          component = "grok_pivot",
          headerText = StrEntryPointPivotCategoryText("")
        ).moduleDecorator
      case EntryPointPivotType.Events =>
        EntryPointPivotModuleCandidateDecorator(
          component = "sport_entry_point",
          headerText = StrEntryPointPivotCategoryText("Play now!")
        ).moduleDecorator
    }

  private def getPipelineGates(entryPointPivotType: EntryPointPivotType): Seq[Gate[PipelineQuery]] =
    entryPointPivotType match {
      case EntryPointPivotType.Grok =>
        Seq.empty
      case EntryPointPivotType.Events =>
        Seq(FollowingSportsUserGate)
    }

  private def getCandidateIdentifier(
    entryPointPivotType: EntryPointPivotType
  ): CandidatePipelineIdentifier =
    CandidatePipelineIdentifier(s"ForYouEntryPointPivot${entryPointPivotType.toString}")
}
