package com.ExTwitter.product_mixer.component_library.pipeline.candidate.ads

import com.ExTwitter.adserver.{thriftscala => ads}
import com.ExTwitter.product_mixer.component_library.model.candidate.ads.AdsCandidate
import com.ExTwitter.product_mixer.component_library.model.query.ads.AdsQuery
import com.ExTwitter.product_mixer.core.functional_component.candidate_source.CandidateSource
import com.ExTwitter.product_mixer.core.functional_component.common.alert.Alert
import com.ExTwitter.product_mixer.core.functional_component.decorator.CandidateDecorator
import com.ExTwitter.product_mixer.core.functional_component.feature_hydrator.BaseCandidateFeatureHydrator
import com.ExTwitter.product_mixer.core.functional_component.filter.Filter
import com.ExTwitter.product_mixer.core.functional_component.gate.BaseGate
import com.ExTwitter.product_mixer.core.functional_component.transformer.CandidatePipelineResultsTransformer
import com.ExTwitter.product_mixer.core.functional_component.transformer.DependentCandidatePipelineQueryTransformer
import com.ExTwitter.product_mixer.core.model.common.identifier.CandidatePipelineIdentifier
import com.ExTwitter.product_mixer.core.pipeline.PipelineQuery
import com.ExTwitter.product_mixer.core.pipeline.candidate.DependentCandidatePipelineConfig
import com.ExTwitter.timelines.configapi.FSParam
import com.ExTwitter.timelines.configapi.decider.DeciderParam

class AdsDependentCandidatePipelineConfig[Query <: PipelineQuery with AdsQuery](
  override val identifier: CandidatePipelineIdentifier,
  override val enabledDeciderParam: Option[DeciderParam[Boolean]],
  override val supportedClientParam: Option[FSParam[Boolean]],
  override val gates: Seq[BaseGate[Query]],
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
  urtRequest: Option[Boolean],
  getOrganicItemIds: GetOrganicItemIds,
  countNumOrganicItems: CountNumOrganicItems[Query],
) extends DependentCandidatePipelineConfig[
      Query,
      ads.AdRequestParams,
      ads.AdImpression,
      AdsCandidate
    ] {

  override def queryTransformer: DependentCandidatePipelineQueryTransformer[
    Query,
    ads.AdRequestParams
  ] = AdsDependentCandidatePipelineQueryTransformer(
    adsDisplayLocationBuilder = adsDisplayLocationBuilder,
    getOrganicItemIds = getOrganicItemIds,
    countNumOrganicItems = countNumOrganicItems,
    urtRequest = urtRequest
  )

  override val resultTransformer: CandidatePipelineResultsTransformer[
    ads.AdImpression,
    AdsCandidate
  ] = AdsCandidatePipelineResultsTransformer
}
