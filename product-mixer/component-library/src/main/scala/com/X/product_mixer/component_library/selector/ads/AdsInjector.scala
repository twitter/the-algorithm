package com.X.product_mixer.component_library.selector.ads

import com.google.inject.Inject
import com.X.finagle.stats.StatsReceiver
import com.X.goldfinch.adaptors.ads.productmixer.ProductMixerPromotedEntriesAdaptor
import com.X.goldfinch.adaptors.productmixer.ProductMixerNonPromotedEntriesAdaptor
import com.X.goldfinch.adaptors.productmixer.ProductMixerQueryConverter
import com.X.goldfinch.api.AdsInjectionRequestContextConverter
import com.X.goldfinch.api.AdsInjectionSurfaceAreas.SurfaceAreaName
import com.X.goldfinch.api.{AdsInjector => GoldfinchAdsInjector}
import com.X.goldfinch.api.NonPromotedEntriesAdaptor
import com.X.goldfinch.api.PromotedEntriesAdaptor
import com.X.goldfinch.impl.injector.AdsInjectorBuilder
import com.X.goldfinch.impl.injector.product_mixer.AdsInjectionSurfaceAreaAdjustersMap
import com.X.goldfinch.impl.injector.product_mixer.VerticalSizeAdjustmentConfigMap
import com.X.inject.Logging
import com.X.product_mixer.component_library.model.query.ads._
import com.X.product_mixer.core.model.common.presentation._
import com.X.product_mixer.core.pipeline.PipelineQuery
import javax.inject.Singleton
import com.X.goldfinch.impl.core.DefaultFeatureSwitchResultsFactory
import com.X.goldfinch.impl.core.LocalDevelopmentFeatureSwitchResultsFactory
import com.X.inject.annotations.Flag
import com.X.product_mixer.core.module.product_mixer_flags.ProductMixerFlagModule.ConfigRepoLocalPath
import com.X.product_mixer.core.module.product_mixer_flags.ProductMixerFlagModule.ServiceLocal

@Singleton
class AdsInjector @Inject() (
  statsReceiver: StatsReceiver,
  @Flag(ConfigRepoLocalPath) localConfigRepoPath: String,
  @Flag(ServiceLocal) isServiceLocal: Boolean)
    extends Logging {
  private val adsQueryRequestConverter: AdsInjectionRequestContextConverter[
    PipelineQuery with AdsQuery
  ] = ProductMixerQueryConverter

  def forSurfaceArea(
    surfaceAreaName: SurfaceAreaName
  ): GoldfinchAdsInjector[
    PipelineQuery with AdsQuery,
    CandidateWithDetails,
    CandidateWithDetails
  ] = {

    val scopedStatsReceiver: StatsReceiver =
      statsReceiver.scope("goldfinch", surfaceAreaName.toString)

    val nonAdsAdaptor: NonPromotedEntriesAdaptor[CandidateWithDetails] =
      ProductMixerNonPromotedEntriesAdaptor(
        VerticalSizeAdjustmentConfigMap.configsBySurfaceArea(surfaceAreaName),
        scopedStatsReceiver)

    val adsAdaptor: PromotedEntriesAdaptor[CandidateWithDetails] =
      new ProductMixerPromotedEntriesAdaptor(scopedStatsReceiver)

    val featureSwitchFactory = if (isServiceLocal) {
      new LocalDevelopmentFeatureSwitchResultsFactory(
        surfaceAreaName.toString,
        configRepoAbsPath = localConfigRepoPath)
    } else new DefaultFeatureSwitchResultsFactory(surfaceAreaName.toString)

    new AdsInjectorBuilder[PipelineQuery with AdsQuery, CandidateWithDetails, CandidateWithDetails](
      requestAdapter = adsQueryRequestConverter,
      nonPromotedEntriesAdaptor = nonAdsAdaptor,
      promotedEntriesAdaptor = adsAdaptor,
      adjusters =
        AdsInjectionSurfaceAreaAdjustersMap.getAdjusters(surfaceAreaName, scopedStatsReceiver),
      featureSwitchFactory = featureSwitchFactory,
      statsReceiver = scopedStatsReceiver,
      logger = logger
    ).build()
  }
}
