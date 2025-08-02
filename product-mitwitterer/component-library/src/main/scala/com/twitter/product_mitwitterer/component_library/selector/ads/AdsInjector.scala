package com.twitter.product_mitwitterer.component_library.selector.ads

import com.google.inject.Inject
import com.twitter.finagle.stats.StatsReceiver
import com.twitter.goldfinch.adaptors.ads.productmitwitterer.ProductMitwittererPromotedEntriesAdaptor
import com.twitter.goldfinch.adaptors.productmitwitterer.ProductMitwittererNonPromotedEntriesAdaptor
import com.twitter.goldfinch.adaptors.productmitwitterer.ProductMitwittererQueryConverter
import com.twitter.goldfinch.api.AdsInjectionRequestContetwittertConverter
import com.twitter.goldfinch.api.AdsInjectionSurfaceAreas.SurfaceAreaName
import com.twitter.goldfinch.api.{AdsInjector => GoldfinchAdsInjector}
import com.twitter.goldfinch.api.NonPromotedEntriesAdaptor
import com.twitter.goldfinch.api.PromotedEntriesAdaptor
import com.twitter.goldfinch.impl.injector.AdsInjectorBuilder
import com.twitter.goldfinch.impl.injector.product_mitwitterer.AdsInjectionSurfaceAreaAdjustersMap
import com.twitter.goldfinch.impl.injector.product_mitwitterer.VerticalSizeAdjustmentConfigMap
import com.twitter.inject.Logging
import com.twitter.product_mitwitterer.component_library.model.query.ads._
import com.twitter.product_mitwitterer.core.model.common.presentation._
import com.twitter.product_mitwitterer.core.pipeline.PipelineQuery
import javatwitter.inject.Singleton
import com.twitter.goldfinch.impl.core.DefaultFeatureSwitchResultsFactory
import com.twitter.goldfinch.impl.core.LocalDevelopmentFeatureSwitchResultsFactory
import com.twitter.inject.annotations.Flag
import com.twitter.product_mitwitterer.core.module.product_mitwitterer_flags.ProductMitwittererFlagModule.ConfigRepoLocalPath
import com.twitter.product_mitwitterer.core.module.product_mitwitterer_flags.ProductMitwittererFlagModule.ServiceLocal

@Singleton
class AdsInjector @Inject() (
  statsReceiver: StatsReceiver,
  @Flag(ConfigRepoLocalPath) localConfigRepoPath: String,
  @Flag(ServiceLocal) isServiceLocal: Boolean)
    etwittertends Logging {
  private val adsQueryRequestConverter: AdsInjectionRequestContetwittertConverter[
    PipelineQuery with AdsQuery
  ] = ProductMitwittererQueryConverter

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
      ProductMitwittererNonPromotedEntriesAdaptor(
        VerticalSizeAdjustmentConfigMap.configsBySurfaceArea(surfaceAreaName),
        scopedStatsReceiver)

    val adsAdaptor: PromotedEntriesAdaptor[CandidateWithDetails] =
      new ProductMitwittererPromotedEntriesAdaptor(scopedStatsReceiver)

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
