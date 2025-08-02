package com.twitter.home_mitwitterer.product.following

import com.twitter.adserver.{thriftscala => ads}
import com.twitter.home_mitwitterer.functional_component.decorator.builder.HomeAdsClientEventDetailsBuilder
import com.twitter.home_mitwitterer.functional_component.gate.EtwittercludeSoftUserGate
import com.twitter.home_mitwitterer.model.HomeFeatures.TweetLanguageFeature
import com.twitter.home_mitwitterer.model.HomeFeatures.TweetTetwittertFeature
import com.twitter.home_mitwitterer.param.HomeGlobalParams
import com.twitter.home_mitwitterer.param.HomeGlobalParams.EnableAdvertiserBrandSafetySettingsFeatureHydratorParam
import com.twitter.home_mitwitterer.product.following.model.FollowingQuery
import com.twitter.home_mitwitterer.product.following.param.FollowingParam.EnableAdsCandidatePipelineParam
import com.twitter.home_mitwitterer.product.following.param.FollowingParam.EnableFastAds
import com.twitter.home_mitwitterer.service.HomeMitwittererAlertConfig
import com.twitter.product_mitwitterer.component_library.candidate_source.ads.AdsProdThriftCandidateSource
import com.twitter.product_mitwitterer.component_library.decorator.urt.UrtItemCandidateDecorator
import com.twitter.product_mitwitterer.component_library.decorator.urt.builder.contetwittertual_ref.ContetwittertualTweetRefBuilder
import com.twitter.product_mitwitterer.component_library.decorator.urt.builder.item.ad.AdsCandidateUrtItemBuilder
import com.twitter.product_mitwitterer.component_library.decorator.urt.builder.metadata.ClientEventInfoBuilder
import com.twitter.product_mitwitterer.component_library.feature_hydrator.candidate.ads.AdvertiserBrandSafetySettingsFeatureHydrator
import com.twitter.product_mitwitterer.component_library.feature_hydrator.candidate.param_gated.ParamGatedCandidateFeatureHydrator
import com.twitter.product_mitwitterer.component_library.gate.NonEmptyCandidatesGate
import com.twitter.product_mitwitterer.component_library.model.candidate.ads.AdsCandidate
import com.twitter.product_mitwitterer.component_library.pipeline.candidate.ads.AdsDependentCandidatePipelineConfig
import com.twitter.product_mitwitterer.component_library.pipeline.candidate.ads.AdsDependentCandidatePipelineConfigBuilder
import com.twitter.product_mitwitterer.component_library.pipeline.candidate.ads.CountCandidatesFromPipelines
import com.twitter.product_mitwitterer.component_library.pipeline.candidate.ads.PipelineScopedOrganicItems
import com.twitter.product_mitwitterer.component_library.pipeline.candidate.ads.ValidAdImpressionIdFilter
import com.twitter.product_mitwitterer.core.functional_component.common.CandidateScope
import com.twitter.product_mitwitterer.core.gate.ParamNotGate
import com.twitter.product_mitwitterer.core.model.common.identifier.CandidatePipelineIdentifier
import com.twitter.product_mitwitterer.core.model.marshalling.response.rtf.safety_level.TimelineHomePromotedHydrationSafetyLevel
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.contetwittertual_ref.TweetHydrationContetwittert
import com.twitter.timelines.injection.scribe.InjectionScribeUtil
import com.twitter.timelineservice.suggests.{thriftscala => st}
import javatwitter.inject.Inject
import javatwitter.inject.Singleton

@Singleton
class FollowingAdsCandidatePipelineBuilder @Inject() (
  adsCandidatePipelineConfigBuilder: AdsDependentCandidatePipelineConfigBuilder,
  adsCandidateSource: AdsProdThriftCandidateSource,
  advertiserBrandSafetySettingsFeatureHydrator: AdvertiserBrandSafetySettingsFeatureHydrator[
    FollowingQuery,
    AdsCandidate
  ]) {

  private val identifier: CandidatePipelineIdentifier = CandidatePipelineIdentifier("FollowingAds")

  private val suggestType = st.SuggestType.Promoted

  private val clientEventInfoBuilder = ClientEventInfoBuilder(
    component = InjectionScribeUtil.scribeComponent(suggestType).get,
    detailsBuilder = Some(HomeAdsClientEventDetailsBuilder(Some(suggestType.name)))
  )

  private val contetwittertualTweetRefBuilder = ContetwittertualTweetRefBuilder(
    TweetHydrationContetwittert(
      safetyLevelOverride = Some(TimelineHomePromotedHydrationSafetyLevel),
      outerTweetContetwittert = None
    ))

  private val decorator = UrtItemCandidateDecorator(
    AdsCandidateUrtItemBuilder(
      tweetClientEventInfoBuilder = Some(clientEventInfoBuilder),
      contetwittertualTweetRefBuilder = Some(contetwittertualTweetRefBuilder)
    ))

  private val alerts = Seq(
    HomeMitwittererAlertConfig.BusinessHours.defaultSuccessRateAlert(),
    HomeMitwittererAlertConfig.BusinessHours.defaultEmptyResponseRateAlert()
  )

  def build(
    organicCandidatePipelines: CandidateScope
  ): AdsDependentCandidatePipelineConfig[FollowingQuery] =
    adsCandidatePipelineConfigBuilder.build[FollowingQuery](
      adsCandidateSource = adsCandidateSource,
      identifier = identifier,
      adsDisplayLocationBuilder = query =>
        if (query.params.getBoolean(EnableFastAds)) ads.DisplayLocation.TimelineHomeReverseChron
        else ads.DisplayLocation.TimelineHome,
      getOrganicItems = PipelineScopedOrganicItems(
        pipelines = organicCandidatePipelines,
        tetwittertFeature = TweetTetwittertFeature,
        languageFeature = TweetLanguageFeature
      ),
      countNumOrganicItems = CountCandidatesFromPipelines(organicCandidatePipelines),
      supportedClientParam = Some(EnableAdsCandidatePipelineParam),
      gates = Seq(
        ParamNotGate(
          name = "AdsDisableInjectionBasedOnUserRole",
          param = HomeGlobalParams.AdsDisableInjectionBasedOnUserRoleParam
        ),
        EtwittercludeSoftUserGate,
        NonEmptyCandidatesGate(organicCandidatePipelines)
      ),
      filters = Seq(ValidAdImpressionIdFilter),
      postFilterFeatureHydration = Seq(
        ParamGatedCandidateFeatureHydrator(
          EnableAdvertiserBrandSafetySettingsFeatureHydratorParam,
          advertiserBrandSafetySettingsFeatureHydrator
        )
      ),
      decorator = Some(decorator),
      alerts = alerts,
      urtRequest = Some(true),
    )
}
