package com.twitter.follow_recommendations.common.clients.geoduck

import com.twitter.follow_recommendations.common.models.GeohashAndCountryCode
import com.twitter.geoduck.common.thriftscala.LocationSource
import com.twitter.geoduck.common.thriftscala.PlaceQuery
import com.twitter.geoduck.common.thriftscala.TransactionLocation
import com.twitter.geoduck.common.thriftscala.UserLocationRequest
import com.twitter.geoduck.thriftscala.LocationService
import com.twitter.stitch.Stitch
import javax.inject.{Inject, Singleton}

@Singleton
class LocationServiceClient @Inject() (locationService: LocationService.MethodPerEndpoint) {
  def getGeohashAndCountryCode(userId: Long): Stitch[GeohashAndCountryCode] = {
    val request = UserLocationRequest(
      Seq(userId),
      Some(PlaceQuery(allPlaceTypes = Some(true))),
      simpleReverseGeocode = true
    )

    for {
      transactionLocation <- locationService.userLocation(request).map(_.found(userId))
      geohashOpt <- getGeohashFromTransactionLocation(transactionLocation)
      countryCodeOpt = transactionLocation.simpleRgcResult.flatMap(_.countryCodeAlpha2)
    } yield GeohashAndCountryCode(geohashOpt, countryCodeOpt)
  }

  private[this] def getGeohashFromTransactionLocation(
    transactionLocation: TransactionLocation
  ): Stitch[Option[String]] = Stitch.from {
    transactionLocation.geohash match {
      case Some(geohash) =>
        val geohashPrefixLength = transactionLocation.locationSource match {
          case Some(LocationSource.Logical) => Some(4)
          case Some(LocationSource.Physical) =>
            transactionLocation.coordinate.flatMap { coordinate =>
              coordinate.accuracy match {
                case Some(accuracy) if accuracy < 50 => Some(7)
                case Some(accuracy) if accuracy < 200 => Some(6)
                case Some(accuracy) if accuracy < 1000 => Some(5)
                case Some(accuracy) if accuracy < 50000 => Some(4)
                case Some(accuracy) if accuracy < 100000 => Some(3)
                case _ => None
              }
            }
          case Some(LocationSource.Model) => Some(4)
          case _ => None
        }

        geohashPrefixLength match {
          case Some(l: Int) => Stitch.value(Some(geohash.stringGeohash.take(l)))
          case _ => Stitch.value(None)
        }
      case _ => Stitch.value(None)
    }
  }
}
