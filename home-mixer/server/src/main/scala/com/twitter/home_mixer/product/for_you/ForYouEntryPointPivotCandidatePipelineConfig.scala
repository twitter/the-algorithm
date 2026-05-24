package com.twitter.home_mixer.product.for_you

import com.twitter.home_mixer.functional_component.gate.RateLimitGate
import com.twitter.home_mixer.functional_component.gate.TimelinesPersistenceStoreLastInjectionGate
import com.twitter.product_mixer.component_library.gate.DefinedUserIdGate
import com.twitter.product_mixer.component_library.model.candidate.pivot.PivotCandidate
import com.twitter.product_mixer.core.functional_component.candidate_source.CandidateSource
import com.twitter.product_mixer.core.functional_component.candidate_source.strato.StratoKeyView
import com.twitter.product_mixer.core.functional_component.decorator.CandidateDecorator
import com.twitter.product_mixer.core.functional_component.gate.Gate
import com.twitter.product_mixer.core.functional_component.transformer.CandidatePipelineQueryTransformer
import com.twitter.product_mixer.core.functional_component.transformer.CandidatePipelineResultsTransformer
import com.twitter.product_mixer.core.model.common.identifier.CandidatePipelineIdentifier
import com.twitter.product_mixer.core.pipeline.HasPipelineCursor
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.product_mixer.core.pipeline.candidate.CandidatePipelineConfig
import com.twitter.timelines.configapi.FSParam
import com.twitter.timelines.configapi.Param
import com.twitter.timelines.render.{thriftscala => t}
import com.twitter.timelineservice.model.rich.EntityIdType
import com.twitter.util.Duration
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ForYouEntryPointPivotCandidatePipelineConfig @Inject() (
  override val identifier: CandidatePipelineIdentifier,
  override val supportedClientParam: Option[FSParam[Boolean]],
  override val candidateSource: CandidateSource[
    StratoKeyView[String, Unit],
    t.Pivot
  ],
  override val decorator: Option[CandidateDecorator[PipelineQuery, PivotCandidate]],
  pivotMinInjectionIntervalParam: Param[Duration],
  extraGates: Seq[Gate[PipelineQuery]])
    extends CandidatePipelineConfig[
      PipelineQuery with HasPipelineCursor[_],
      StratoKeyView[String, Unit],
      t.Pivot,
      PivotCandidate
    ] {

  override val gates = Seq(
    DefinedUserIdGate,
    RateLimitGate,
    TimelinesPersistenceStoreLastInjectionGate(
      pivotMinInjectionIntervalParam,
      EntityIdType.EntryPointPivot
    )
  ) ++ extraGates

  override val queryTransformer: CandidatePipelineQueryTransformer[
    PipelineQuery,
    StratoKeyView[String, Unit]
  ] = { query =>
    StratoKeyView(
      key = query.getCountryCode.getOrElse("US"),
      view = None
    )
  }

  override val resultTransformer: CandidatePipelineResultsTransformer[
    t.Pivot,
    PivotCandidate
  ] = { sourceResult =>
    PivotCandidate(
      // We only expect one item per "messageprompt" entryNamespace per URT response.
      // As a result id=0L will always be unique in the entryNamespace.
      id = identifier.name,
      url = sourceResult.url,
      displayType = sourceResult.displayType,
      titleText = sourceResult.titleText,
      detailText = sourceResult.detailText,
      image = sourceResult.image,
      badge = sourceResult.badge,
      categoryText = sourceResult.categoryText,
      detailTextImage = sourceResult.detailTextImage,
      element = None
    )
  }
}
