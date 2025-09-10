package com.twitter.home_mixer.product.for_you

import com.twitter.adserver.{thriftscala => ads}
import com.twitter.home_mixer.functional_component.decorator.builder.HomeAdsClientEventDetailsBuilder
import com.twitter.home_mixer.functional_component.feature_hydrator.NoAdsTierFeature
import com.twitter.home_mixer.functional_component.gate.ExcludeSoftUserGate
import com.twitter.home_mixer.functional_component.gate.ExcludeSyntheticUserGate
import com.twitter.home_mixer.model.HomeFeatures.TweetLanguageFeature
import com.twitter.home_mixer.model.HomeFeatures.TweetTextFeature
import com.twitter.home_mixer.param.HomeGlobalParams
import com.twitter.home_mixer.param.HomeGlobalParams.EnableAdvertiserBrandSafetySettingsFeatureHydratorParam
import com.twitter.home_mixer.product.for_you.model.ForYouQuery
import com.twitter.home_mixer.service.HomeMixerAlertConfig
import com.twitter.home_mixer.util.CandidatesUtil
import com.twitter.home_mixer.{thriftscala => hmt}
import com.twitter.product_mixer.component_library.candidate_source.ads.AdsProdThriftCandidateSource
import com.twitter.product_mixer.component_library.decorator.urt.UrtItemCandidateDecorator
import com.twitter.product_mixer.component_library.decorator.urt.builder.contextual_ref.ContextualTweetRefBuilder
import com.twitter.product_mixer.component_library.decorator.urt.builder.item.ad.AdsCandidateUrtItemBuilder
import com.twitter.product_mixer.component_library.decorator.urt.builder.metadata.ClientEventInfoBuilder
import com.twitter.product_mixer.component_library.feature_hydrator.candidate.ads.AdvertiserBrandSafetySettingsFeatureHydrator
import com.twitter.product_mixer.component_library.feature_hydrator.candidate.param_gated.ParamGatedCandidateFeatureHydrator
import com.twitter.product_mixer.component_library.gate.FeatureGate
import com.twitter.product_mixer.component_library.gate.NonEmptyCandidatesGate
import com.twitter.product_mixer.component_library.model.candidate.ads.AdsCandidate
import com.twitter.product_mixer.component_library.pipeline.candidate.ads.AdsDependentCandidatePipelineConfig
import com.twitter.product_mixer.component_library.pipeline.candidate.ads.AdsDependentCandidatePipelineConfigBuilder
import com.twitter.product_mixer.component_library.pipeline.candidate.ads.CountTruncatedItemCandidatesFromPipelines
import com.twitter.product_mixer.component_library.pipeline.candidate.ads.StaticAdsDisplayLocationBuilder
import com.twitter.product_mixer.component_library.pipeline.candidate.ads.TruncatedPipelineScopedOrganicItems
import com.twitter.product_mixer.component_library.pipeline.candidate.ads.ValidAdImpressionIdFilter
import com.twitter.product_mixer.core.functional_component.common.CandidateScope
import com.twitter.product_mixer.core.gate.ParamNotGate
import com.twitter.product_mixer.core.model.common.identifier.CandidatePipelineIdentifier
import com.twitter.product_mixer.core.model.marshalling.response.rtf.safety_level.TimelineHomePromotedHydrationSafetyLevel
import com.twitter.product_mixer.core.model.marshalling.response.urt.contextual_ref.TweetHydrationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ForYouAdsDependentCandidatePipelineBuilder @Inject() (
  adsCandidatePipelineConfigBuilder: AdsDependentCandidatePipelineConfigBuilder,
  adsCandidateSource: AdsProdThriftCandidateSource,
  advertiserBrandSafetySettingsFeatureHydrator: AdvertiserBrandSafetySettingsFeatureHydrator[
    ForYouQuery,
    AdsCandidate
  ]) {

  private val identifier: CandidatePipelineIdentifier =
    CandidatePipelineIdentifier("ForYouAdsDependent")

  private val servedType = hmt.ServedType.ForYouPromoted

  private val MaxOrganicTweets = 35

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

  def build(
    organicCandidatePipelines: CandidateScope
  ): AdsDependentCandidatePipelineConfig[ForYouQuery] =
    adsCandidatePipelineConfigBuilder.build[ForYouQuery](
      adsCandidateSource = adsCandidateSource,
      identifier = identifier,
      adsDisplayLocationBuilder = StaticAdsDisplayLocationBuilder(ads.DisplayLocation.TimelineHome),
      getOrganicItems = TruncatedPipelineScopedOrganicItems(
        pipelines = organicCandidatePipelines,
        textFeature = TweetTextFeature,
        languageFeature = TweetLanguageFeature,
        ordering = CandidatesUtil.scoreOrdering,
        maxCount = MaxOrganicTweets
      ),
      countNumOrganicItems =
        CountTruncatedItemCandidatesFromPipelines(organicCandidatePipelines, MaxOrganicTweets),
      gates = Seq(
        ParamNotGate(
          name = "AdsDisableInjectionBasedOnUserRole",
          param = HomeGlobalParams.AdsDisableInjectionBasedOnUserRoleParam
        ),
        ExcludeSoftUserGate,
        ExcludeSyntheticUserGate,
        FeatureGate.fromNegatedFeature(NoAdsTierFeature),
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
      urtRequest = Some(true)
    )
}
