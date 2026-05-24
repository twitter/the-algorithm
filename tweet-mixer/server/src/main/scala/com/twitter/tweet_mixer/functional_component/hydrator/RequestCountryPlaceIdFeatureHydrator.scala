package com.twitter.tweet_mixer.functional_component.hydrator

import com.twitter.finagle.stats.StatsReceiver
import com.twitter.geoduck.util.country.CountryInfo
import com.twitter.product_mixer.core.feature.Feature
import com.twitter.product_mixer.core.feature.featuremap.FeatureMap
import com.twitter.product_mixer.core.feature.featuremap.FeatureMapBuilder
import com.twitter.product_mixer.core.functional_component.feature_hydrator.QueryFeatureHydrator
import com.twitter.product_mixer.core.model.common.Conditionally
import com.twitter.product_mixer.core.model.common.identifier.FeatureHydratorIdentifier
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.stitch.Stitch
import com.twitter.tweet_mixer.feature.RequestCountryPlaceIdFeature
import com.twitter.tweet_mixer.param.TweetMixerGlobalParams.EnableRequestCountryPlaceIdHydrator
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RequestPlaceIdsQueryFeatureHydrator @Inject() (statsReceiver: StatsReceiver)
    extends QueryFeatureHydrator[PipelineQuery]
    with Conditionally[PipelineQuery] {
  private def UnknownCountryPlaceId: Long = -1L

  private val scopedStats = statsReceiver.scope(getClass.getSimpleName)
  private val emptyPlaceIdStats = scopedStats.counter("emptyPlaceIdStats")
  private val noCountryCodeStats = scopedStats.counter("noCountryCodeStats")

  override val identifier: FeatureHydratorIdentifier = FeatureHydratorIdentifier("RequestPlaceIds")

  override def onlyIf(query: PipelineQuery): Boolean =
    query.params(EnableRequestCountryPlaceIdHydrator)

  override def features: Set[Feature[_, _]] = Set(RequestCountryPlaceIdFeature)

  override def hydrate(query: PipelineQuery): Stitch[FeatureMap] = {
    query.getCountryCode match {
      case Some(countryCode) =>
        val placeId: Long = getCountryPlaceId(countryCode).getOrElse(UnknownCountryPlaceId)
        if (placeId == UnknownCountryPlaceId) emptyPlaceIdStats.incr()
        Stitch.value(FeatureMapBuilder().add(RequestCountryPlaceIdFeature, placeId).build())

      case _ =>
        noCountryCodeStats.incr()
        Stitch.value(
          FeatureMapBuilder().add(RequestCountryPlaceIdFeature, UnknownCountryPlaceId).build())
    }
  }

  private def getCountryPlaceId(countryCode: String): Option[Long] = {
    CountryInfo.lookupByCode(countryCode).map { countryInfo =>
      countryInfo.placeIdLong
    }
  }
}
