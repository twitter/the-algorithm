package com.X.follow_recommendations.common.clients.geoduck

import com.X.finagle.stats.StatsReceiver
import com.X.follow_recommendations.common.models.GeohashAndCountryCode
import com.X.stitch.Stitch

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserLocationFetcher @Inject() (
  locationServiceClient: LocationServiceClient,
  reverseGeocodeClient: ReverseGeocodeClient,
  statsReceiver: StatsReceiver) {

  private val stats: StatsReceiver = statsReceiver.scope("user_location_fetcher")
  private val totalRequestsCounter = stats.counter("requests")
  private val emptyResponsesCounter = stats.counter("empty")
  private val locationServiceExceptionCounter = stats.counter("location_service_exception")
  private val reverseGeocodeExceptionCounter = stats.counter("reverse_geocode_exception")

  def getGeohashAndCountryCode(
    userId: Option[Long],
    ipAddress: Option[String]
  ): Stitch[Option[GeohashAndCountryCode]] = {
    totalRequestsCounter.incr()
    val lscLocationStitch = Stitch
      .collect {
        userId.map(locationServiceClient.getGeohashAndCountryCode)
      }.rescue {
        case _: Exception =>
          locationServiceExceptionCounter.incr()
          Stitch.None
      }

    val ipLocationStitch = Stitch
      .collect {
        ipAddress.map(reverseGeocodeClient.getGeohashAndCountryCode)
      }.rescue {
        case _: Exception =>
          reverseGeocodeExceptionCounter.incr()
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
