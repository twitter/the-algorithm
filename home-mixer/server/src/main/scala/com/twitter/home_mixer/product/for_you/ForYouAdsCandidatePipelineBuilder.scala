package com.ExTwitter.home_mixer.product.for_you

import com.ExTwitter.product_mixer.component_library.feature_hydrator.candidate.param_gated.ParamGatedCandidateFeatureHydrator
import com.ExTwitter.adserver.{thriftscala => ads}
import com.ExTwitter.home_mixer.functional_component.decorator.builder.HomeAdsClientEventDetailsBuilder
import com.ExTwitter.home_mixer.functional_component.gate.ExcludeSoftUserGate
import com.ExTwitter.home_mixer.param.HomeGlobalParams
import com.ExTwitter.home_mixer.param.HomeGlobalParams.EnableAdvertiserBrandSafetySettingsFeatureHydratorParam
import com.ExTwitter.home_mixer.product.for_you.model.ForYouQuery
import com.ExTwitter.home_mixer.product.for_you.param.ForYouParam.AdsNumOrganicItemsParam
import com.ExTwitter.home_mixer.service.HomeMixerAlertConfig
import com.ExTwitter.product_mixer.component_library.candidate_source.ads.AdsProdThriftCandidateSource
import com.ExTwitter.product_mixer.component_library.decorator.urt.UrtItemCandidateDecorator
import com.ExTwitter.product_mixer.component_library.decorator.urt.builder.contextual_ref.ContextualTweetRefBuilder
import com.ExTwitter.product_mixer.component_library.decorator.urt.builder.item.ad.AdsCandidateUrtItemBuilder
import com.ExTwitter.product_mixer.component_library.decorator.urt.builder.metadata.ClientEventInfoBuilder
import com.ExTwitter.product_mixer.component_library.feature_hydrator.candidate.ads.AdvertiserBrandSafetySettingsFeatureHydrator
import com.ExTwitter.product_mixer.component_library.model.candidate.ads.AdsCandidate
import com.ExTwitter.product_mixer.component_library.pipeline.candidate.ads.AdsCandidatePipelineConfig
import com.ExTwitter.product_mixer.component_library.pipeline.candidate.ads.AdsCandidatePipelineConfigBuilder
import com.ExTwitter.product_mixer.component_library.pipeline.candidate.ads.StaticAdsDisplayLocationBuilder
import com.ExTwitter.product_mixer.component_library.pipeline.candidate.ads.ValidAdImpressionIdFilter
import com.ExTwitter.product_mixer.core.functional_component.common.CandidateScope
import com.ExTwitter.product_mixer.core.gate.ParamNotGate
import com.ExTwitter.product_mixer.core.model.common.identifier.CandidatePipelineIdentifier
import com.ExTwitter.product_mixer.core.model.marshalling.response.rtf.safety_level.TimelineHomePromotedHydrationSafetyLevel
import com.ExTwitter.product_mixer.core.model.marshalling.response.urt.contextual_ref.TweetHydrationContext
import com.ExTwitter.timelines.injection.scribe.InjectionScribeUtil
import com.ExTwitter.timelineservice.suggests.{thriftscala => st}
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ForYouAdsCandidatePipelineBuilder @Inject() (
  adsCandidatePipelineConfigBuilder: AdsCandidatePipelineConfigBuilder,
  adsCandidateSource: AdsProdThriftCandidateSource,
  advertiserBrandSafetySettingsFeatureHydrator: AdvertiserBrandSafetySettingsFeatureHydrator[
    ForYouQuery,
    AdsCandidate
  ]) {

  private val identifier: CandidatePipelineIdentifier = CandidatePipelineIdentifier("ForYouAds")

  private val suggestType = st.SuggestType.Promoted

  private val clientEventInfoBuilder = ClientEventInfoBuilder(
    component = InjectionScribeUtil.scribeComponent(suggestType).get,
    detailsBuilder = Some(HomeAdsClientEventDetailsBuilder(Some(suggestType.name)))
  )

  private val contextualTweetRefBuilder = ContextualTweetRefBuilder(
    TweetHydrationContext(
      safetyLevelOverride = Some(TimelineHomePromotedHydrationSafetyLevel),
      outerTweetContext = None
    ))

  private val decorator = UrtItemCandidateDecorator(
    AdsCandidateUrtItemBuilder(
      tweetClientEventInfoBuilder = Some(clientEventInfoBuilder),
      contextualTweetRefBuilder = Some(contextualTweetRefBuilder)
    ))

  private val alerts = Seq(
    HomeMixerAlertConfig.BusinessHours.defaultSuccessRateAlert(),
    HomeMixerAlertConfig.BusinessHours.defaultEmptyResponseRateAlert()
  )

  def build(
    organicCandidatePipelines: Option[CandidateScope] = None
  ): AdsCandidatePipelineConfig[ForYouQuery] =
    adsCandidatePipelineConfigBuilder.build[ForYouQuery](
      adsCandidateSource = adsCandidateSource,
      identifier = identifier,
      adsDisplayLocationBuilder = StaticAdsDisplayLocationBuilder(ads.DisplayLocation.TimelineHome),
      estimateNumOrganicItems = _.params(AdsNumOrganicItemsParam).toShort,
      gates = Seq(
        ParamNotGate(
          name = "AdsDisableInjectionBasedOnUserRole",
          param = HomeGlobalParams.AdsDisableInjectionBasedOnUserRoleParam
        ),
        ExcludeSoftUserGate
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
