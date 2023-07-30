package com.X.product_mixer.component_library.pipeline.candidate.ads

import com.X.adserver.{thriftscala => ads}
import com.X.product_mixer.component_library.model.candidate.ads.AdsCandidate
import com.X.product_mixer.component_library.model.query.ads.AdsQuery
import com.X.product_mixer.core.functional_component.candidate_source.CandidateSource
import com.X.product_mixer.core.functional_component.common.alert.Alert
import com.X.product_mixer.core.functional_component.decorator.CandidateDecorator
import com.X.product_mixer.core.functional_component.feature_hydrator.BaseCandidateFeatureHydrator
import com.X.product_mixer.core.functional_component.filter.Filter
import com.X.product_mixer.core.functional_component.gate.Gate
import com.X.product_mixer.core.functional_component.transformer.CandidatePipelineQueryTransformer
import com.X.product_mixer.core.functional_component.transformer.CandidatePipelineResultsTransformer
import com.X.product_mixer.core.model.common.identifier.CandidatePipelineIdentifier
import com.X.product_mixer.core.pipeline.PipelineQuery
import com.X.product_mixer.core.pipeline.candidate.CandidatePipelineConfig
import com.X.timelines.configapi.FSParam
import com.X.timelines.configapi.decider.DeciderParam
import javax.inject.Inject

class AdsCandidatePipelineConfig[Query <: PipelineQuery with AdsQuery] @Inject() (
  override val identifier: CandidatePipelineIdentifier,
  override val enabledDeciderParam: Option[DeciderParam[Boolean]],
  override val supportedClientParam: Option[FSParam[Boolean]],
  override val gates: Seq[Gate[Query]],
  override val candidateSource: CandidateSource[
    ads.AdRequestParams,
    ads.AdImpression
  ],
  override val filters: Seq[Filter[Query, AdsCandidate]],
  override val postFilterFeatureHydration: Seq[
    BaseCandidateFeatureHydrator[Query, AdsCandidate, _]
  ],
  override val decorator: Option[CandidateDecorator[Query, AdsCandidate]],
  override val alerts: Seq[Alert],
  adsDisplayLocationBuilder: AdsDisplayLocationBuilder[Query],
  estimateNumOrganicItems: EstimateNumOrganicItems[Query],
  urtRequest: Option[Boolean],
) extends CandidatePipelineConfig[
      Query,
      ads.AdRequestParams,
      ads.AdImpression,
      AdsCandidate
    ] {

  override val queryTransformer: CandidatePipelineQueryTransformer[Query, ads.AdRequestParams] =
    AdsCandidatePipelineQueryTransformer(
      adsDisplayLocationBuilder = adsDisplayLocationBuilder,
      estimatedNumOrganicItems = estimateNumOrganicItems,
      urtRequest = urtRequest)

  override val resultTransformer: CandidatePipelineResultsTransformer[
    ads.AdImpression,
    AdsCandidate
  ] = AdsCandidatePipelineResultsTransformer
}
