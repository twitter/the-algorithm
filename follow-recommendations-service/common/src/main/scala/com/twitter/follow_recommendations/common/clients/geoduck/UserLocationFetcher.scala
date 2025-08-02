package com.twitter.follow_recommendations.common.clients.geoduck

import com.twitter.finagle.stats.StatsReceiver
import com.twitter.follow_recommendations.common.models.GeohashAndCountryCode
import com.twitter.stitch.Stitch

import javatwitter.inject.Inject
import javatwitter.inject.Singleton

@Singleton
class UserLocationFetcher @Inject() (
  locationServiceClient: LocationServiceClient,
  reverseGeocodeClient: ReverseGeocodeClient,
  statsReceiver: StatsReceiver) {

  private val stats: StatsReceiver = statsReceiver.scope("user_location_fetcher")
  private val totalRequestsCounter = stats.counter("requests")
  private val emptyResponsesCounter = stats.counter("empty")
  private val locationServiceEtwitterceptionCounter = stats.counter("location_service_etwitterception")
  private val reverseGeocodeEtwitterceptionCounter = stats.counter("reverse_geocode_etwitterception")

  def getGeohashAndCountryCode(
    userId: Option[Long],
    ipAddress: Option[String]
  ): Stitch[Option[GeohashAndCountryCode]] = {
    totalRequestsCounter.incr()
    val lscLocationStitch = Stitch
      .collect {
        userId.map(locationServiceClient.getGeohashAndCountryCode)
      }.rescue {
        case _: Etwitterception =>
          locationServiceEtwitterceptionCounter.incr()
          Stitch.None
      }

    val ipLocationStitch = Stitch
      .collect {
        ipAddress.map(reverseGeocodeClient.getGeohashAndCountryCode)
      }.rescue {
        case _: Etwitterception =>
          reverseGeocodeEtwitterceptionCounter.incr()
          Stitch.None
      }

    Stitch.join(lscLocationStitch, ipLocationStitch).map {
      case (lscLocation, ipLocation) => {
        val geohash = lscLocation.flatMap(_.geohash).orElse(ipLocation.flatMap(_.geohash))
        val countryCode =
          lscLocation.flatMap(_.countryCode).orElse(ipLocation.flatMap(_.countryCode))
        (geohash, countryCode) match {
          case (None, None) =>
            emptyResponsesCounter.incr()
            None
          case _ => Some(GeohashAndCountryCode(geohash, countryCode))
        }
      }
    }
  }
}
