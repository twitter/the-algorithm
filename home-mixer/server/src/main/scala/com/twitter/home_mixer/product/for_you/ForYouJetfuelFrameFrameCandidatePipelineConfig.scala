package com.twitter.home_mixer.product.for_you

import com.twitter.strato.columns.jetfuel.thriftscala.JetfuelRouteData
import com.twitter.home_mixer.product.for_you.candidate_source.JetfuelFrameCandidateSource
import com.twitter.home_mixer.product.for_you.param.ForYouParam.EnableJetfuelFramePipelineParam
import com.twitter.home_mixer.product.for_you.gate.FollowingSportsUserGate
import com.twitter.product_mixer.component_library.pipeline.candidate.jetfuel_entry_point.JetfuelCandidateFeatureTransformer
import com.twitter.product_mixer.component_library.decorator.urt.UrtItemCandidateDecorator
import com.twitter.product_mixer.component_library.decorator.urt.builder.item.frame.FrameCandidateUrtItemBuilder
import com.twitter.product_mixer.component_library.gate.FirstPageGate
import com.twitter.product_mixer.component_library.model.candidate.FrameCandidate
import com.twitter.product_mixer.component_library.feature_hydrator.candidate.frame.JetfuelPayloadFeatureHydrator
import com.twitter.product_mixer.core.functional_component.candidate_source.strato.StratoKeyView
import com.twitter.product_mixer.core.functional_component.decorator.CandidateDecorator
import com.twitter.product_mixer.core.functional_component.feature_hydrator.BaseCandidateFeatureHydrator
import com.twitter.product_mixer.core.functional_component.gate.Gate
import com.twitter.product_mixer.core.functional_component.transformer.CandidateFeatureTransformer
import com.twitter.product_mixer.core.functional_component.transformer.CandidatePipelineQueryTransformer
import com.twitter.product_mixer.core.functional_component.transformer.CandidatePipelineResultsTransformer
import com.twitter.product_mixer.core.gate.ParamGate
import com.twitter.product_mixer.core.model.common.identifier.CandidatePipelineIdentifier
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.product_mixer.core.pipeline.HasPipelineCursor
import com.twitter.product_mixer.core.pipeline.candidate.CandidatePipelineConfig
import com.twitter.strato.generated.client.events.entryPoint.JetfuelEntryPointByCountryClientColumn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ForYouJetfuelFrameCandidatePipelineConfig @Inject() (
  jetfuelFrameCandidateSource: JetfuelFrameCandidateSource,
  jetfuelPayloadFeatureHydrator: JetfuelPayloadFeatureHydrator)
    extends CandidatePipelineConfig[
      PipelineQuery with HasPipelineCursor[_],
      StratoKeyView[
        JetfuelEntryPointByCountryClientColumn.Key,
        JetfuelEntryPointByCountryClientColumn.View
      ],
      JetfuelRouteData,
      FrameCandidate
    ] {

  override val identifier: CandidatePipelineIdentifier =
    CandidatePipelineIdentifier("ForYouJetfuelFrame")

  private val FrameId = "ForYouJetfuelFrame"

  override val candidateSource: JetfuelFrameCandidateSource = jetfuelFrameCandidateSource

  override val postFilterFeatureHydration: Seq[
    BaseCandidateFeatureHydrator[PipelineQuery, FrameCandidate, _]
  ] = Seq(jetfuelPayloadFeatureHydrator)

  override val gates: Seq[Gate[PipelineQuery with HasPipelineCursor[_]]] = Seq(
    FirstPageGate,
    ParamGate(name = "JetfuelFrameEnabled", param = EnableJetfuelFramePipelineParam),
    FollowingSportsUserGate
  )

  override val queryTransformer: CandidatePipelineQueryTransformer[
    PipelineQuery,
    StratoKeyView[
      JetfuelEntryPointByCountryClientColumn.Key,
      JetfuelEntryPointByCountryClientColumn.View
    ]
  ] = { query => StratoKeyView(key = query.getCountryCode.getOrElse("US"), view = None) }

  override val featuresFromCandidateSourceTransformers: Seq[
    CandidateFeatureTransformer[JetfuelRouteData]
  ] = Seq(JetfuelCandidateFeatureTransformer)

  override val resultTransformer: CandidatePipelineResultsTransformer[
    JetfuelRouteData,
    FrameCandidate
  ] = { sourceResult => FrameCandidate(id = sourceResult.route) }

  override val decorator: Option[
    CandidateDecorator[PipelineQuery, FrameCandidate]
  ] = {
    Some(
      UrtItemCandidateDecorator(
        FrameCandidateUrtItemBuilder(
          frameId = FrameId,
          clientEventInfoBuilder = None
        )
      )
    )
  }
}
