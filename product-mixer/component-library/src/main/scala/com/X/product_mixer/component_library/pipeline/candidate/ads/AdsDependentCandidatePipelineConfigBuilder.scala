package com.X.product_mixer.component_library.pipeline.candidate.ads

import com.X.adserver.thriftscala.AdImpression
import com.X.adserver.thriftscala.AdRequestParams
import com.X.product_mixer.component_library.decorator.urt.UrtItemCandidateDecorator
import com.X.product_mixer.component_library.decorator.urt.builder.item.ad.AdsCandidateUrtItemBuilder
import com.X.product_mixer.component_library.model.candidate.ads.AdsCandidate
import com.X.product_mixer.component_library.model.query.ads.AdsQuery
import com.X.product_mixer.core.functional_component.candidate_source.CandidateSource
import com.X.product_mixer.core.functional_component.common.alert.Alert
import com.X.product_mixer.core.functional_component.decorator.CandidateDecorator
import com.X.product_mixer.core.functional_component.feature_hydrator.BaseCandidateFeatureHydrator
import com.X.product_mixer.core.functional_component.filter.Filter
import com.X.product_mixer.core.functional_component.gate.BaseGate
import com.X.product_mixer.core.model.common.identifier.CandidatePipelineIdentifier
import com.X.product_mixer.core.pipeline.PipelineQuery
import com.X.timelines.configapi.FSParam
import com.X.timelines.configapi.decider.DeciderParam
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AdsDependentCandidatePipelineConfigBuilder @Inject() () {

  /**
   * Build a AdsDependentCandidatePipelineConfig
   */
  def build[Query <: PipelineQuery with AdsQuery](
    adsCandidateSource: CandidateSource[AdRequestParams, AdImpression],
    identifier: CandidatePipelineIdentifier = CandidatePipelineIdentifier("Ads"),
    adsDisplayLocationBuilder: AdsDisplayLocationBuilder[Query],
    getOrganicItemIds: GetOrganicItemIds = EmptyOrganicItemIds,
    countNumOrganicItems: CountNumOrganicItems[Query] = CountAllCandidates,
    enabledDeciderParam: Option[DeciderParam[Boolean]] = None,
    supportedClientParam: Option[FSParam[Boolean]] = None,
    gates: Seq[BaseGate[Query]] = Seq.empty,
    filters: Seq[Filter[Query, AdsCandidate]] = Seq.empty,
    postFilterFeatureHydration: Seq[BaseCandidateFeatureHydrator[Query, AdsCandidate, _]] =
      Seq.empty,
    decorator: Option[CandidateDecorator[Query, AdsCandidate]] =
      Some(UrtItemCandidateDecorator(AdsCandidateUrtItemBuilder())),
    alerts: Seq[Alert] = Seq.empty,
    urtRequest: Option[Boolean] = None,
  ): AdsDependentCandidatePipelineConfig[Query] = new AdsDependentCandidatePipelineConfig[Query](
    identifier = identifier,
    enabledDeciderParam = enabledDeciderParam,
    supportedClientParam = supportedClientParam,
    gates = gates,
    candidateSource = adsCandidateSource,
    filters = filters,
    postFilterFeatureHydration = postFilterFeatureHydration,
    decorator = decorator,
    alerts = alerts,
    adsDisplayLocationBuilder = adsDisplayLocationBuilder,
    getOrganicItemIds = getOrganicItemIds,
    countNumOrganicItems = countNumOrganicItems,
    urtRequest = urtRequest)
}
