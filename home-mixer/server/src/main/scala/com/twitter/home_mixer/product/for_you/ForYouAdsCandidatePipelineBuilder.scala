package com.twitter.home_mixer.product.for_you

import com.twitter.adserver.{thriftscala => ads}
import com.twitter.home_mixer.functional_component.decorator.builder.HomeAdsClientEventDetailsBuilder
import com.twitter.home_mixer.functional_component.feature_hydrator.NoAdsTierFeature
import com.twitter.home_mixer.functional_component.gate.ExcludeSoftUserGate
import com.twitter.home_mixer.functional_component.gate.ExcludeSyntheticUserGate
import com.twitter.home_mixer.param.HomeGlobalParams
import com.twitter.home_mixer.param.HomeGlobalParams.EnableAdvertiserBrandSafetySettingsFeatureHydratorParam
import com.twitter.home_mixer.product.for_you.model.ForYouQuery
import com.twitter.home_mixer.product.for_you.param.ForYouParam.AdsNumOrganicItemsParam
import com.twitter.home_mixer.service.HomeMixerAlertConfig
import com.twitter.home_mixer.{thriftscala => hmt}
import com.twitter.product_mixer.component_library.candidate_source.ads.AdsProdThriftCandidateSource
import com.twitter.product_mixer.component_library.decorator.urt.UrtItemCandidateDecorator
import com.twitter.product_mixer.component_library.decorator.urt.builder.contextual_ref.ContextualTweetRefBuilder
import com.twitter.product_mixer.component_library.decorator.urt.builder.item.ad.AdsCandidateUrtItemBuilder
import com.twitter.product_mixer.component_library.decorator.urt.builder.metadata.ClientEventInfoBuilder
import com.twitter.product_mixer.component_library.feature_hydrator.candidate.ads.AdvertiserBrandSafetySettingsFeatureHydrator
import com.twitter.product_mixer.component_library.feature_hydrator.candidate.param_gated.ParamGatedCandidateFeatureHydrator
import com.twitter.product_mixer.component_library.gate.FeatureGate
import com.twitter.product_mixer.component_library.model.candidate.ads.AdsCandidate
import com.twitter.product_mixer.component_library.pipeline.candidate.ads.AdsCandidatePipelineConfig
import com.twitter.product_mixer.component_library.pipeline.candidate.ads.AdsCandidatePipelineConfigBuilder
import com.twitter.product_mixer.component_library.pipeline.candidate.ads.StaticAdsDisplayLocationBuilder
import com.twitter.product_mixer.component_library.pipeline.candidate.ads.ValidAdImpressionIdFilter
import com.twitter.product_mixer.core.gate.ParamNotGate
import com.twitter.product_mixer.core.model.common.identifier.CandidatePipelineIdentifier
import com.twitter.product_mixer.core.model.marshalling.response.rtf.safety_level.TimelineHomePromotedHydrationSafetyLevel
import com.twitter.product_mixer.core.model.marshalling.response.urt.contextual_ref.TweetHydrationContext
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

  private val servedType = hmt.ServedType.ForYouPromoted

  private val clientEventInfoBuilder = ClientEventInfoBuilder(
    component = servedType.originalName,
    detailsBuilder = Some(HomeAdsClientEventDetailsBuilder(Some(servedType.name)))
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

  def build(): AdsCandidatePipelineConfig[ForYouQuery] =
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
        ExcludeSoftUserGate,
        ExcludeSyntheticUserGate,
        FeatureGate.fromNegatedFeature(NoAdsTierFeature)
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
      urtRequest = Some(true)
    )
}
