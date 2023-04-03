package com.twitter.follow_recommendations.common.clients.geoduck

import com.twitter.follow_recommendations.common.models.GeohashAndCountryCode
import com.twitter.geoduck.common.thriftscala.PlaceQuery
import com.twitter.geoduck.common.thriftscala.ReverseGeocodeIPRequest
import com.twitter.geoduck.service.thriftscala.GeoContext
import com.twitter.geoduck.thriftscala.ReverseGeocoder
import com.twitter.stitch.Stitch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ReverseGeocodeClient @Inject() (rgcService: ReverseGeocoder.MethodPerEndpoint) {
  def getGeohashAndCountryCode(ipAddress: String): Stitch[GeohashAndCountryCode] =
    Stitch.callFuture {
      rgcService.reverseGeocodeIp(
        ReverseGeocodeIPRequest(Seq(ipAddress), PlaceQuery(None), simpleReverseGeocode = true)
      ).map { response =>
        response.found.get(ipAddress) match {
          case Some(location) => getGeohashAndCountryCodeFromLocation(location)
          case _ => GeohashAndCountryCode(None, None)
        }
      }
    }

  private def getGeohashAndCountryCodeFromLocation(location: Location): GeohashAndCountryCode = {
    val countryCode = location.simpleRgcResult.flatMap(_.countryCodeAlpha2)
    val geohashString = location.geohash.flatMap(_.stringGeohash.map(_.take(4)))
    GeohashAndCountryCode(geohashString, countryCode)
  }
}
