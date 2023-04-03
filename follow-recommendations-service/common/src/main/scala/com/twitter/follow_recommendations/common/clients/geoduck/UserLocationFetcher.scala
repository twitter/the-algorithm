import com.twitter.follow_recommendations.common.models.GeohashAndCountryCode
import com.twitter.stitch.Stitch

import javax.inject.{Inject, Singleton}

@Singleton
class UserLocationFetcher @Inject() (
  locationServiceClient: LocationServiceClient,
  reverseGeocodeClient: ReverseGeocodeClient,
  statsReceiver: StatsReceiver) {
  private val stats = statsReceiver.scope("user_location_fetcher")

  def getGeohashAndCountryCode(userId: Option[Long], ipAddress: Option[String]): Stitch[Option[GeohashAndCountryCode]] = {
    val totalRequestsCounter = stats.counter("requests").incr()

    val lscLocationStitch = Stitch.collect(userId.map(locationServiceClient.getGeohashAndCountryCode)).rescue {
      case _: Exception =>
        stats.counter("location_service_exception").incr()
        Stitch.None
    }

    val ipLocationStitch = Stitch.collect(ipAddress.map(reverseGeocodeClient.getGeohashAndCountryCode)).rescue {
      case _: Exception =>
        stats.counter("reverse_geocode_exception").incr()
        Stitch.None
    }

    Stitch.join(lscLocationStitch, ipLocationStitch).map {
     case (lscLocation, ipLocation) =>
        (lscLocation.flatMap(_.geohash).orElse(ipLocation.flatMap(_.geohash)),
         lscLocation.flatMap(_.countryCode).orElse(ipLocation.flatMap(_.countryCode))) match {
          case (Some(geohash), Some(countryCode)) =>
            Some(GeohashAndCountryCode(geohash, countryCode))
          case _ =>
            stats.counter("empty").incr()
            None
      }
    }
  }
}
