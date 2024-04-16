package com.tw ter.follow_recom ndat ons.common.cl ents.geoduck

 mport com.tw ter.follow_recom ndat ons.common.models.GeohashAndCountryCode
 mport com.tw ter.geoduck.common.thr ftscala.Locat on
 mport com.tw ter.geoduck.common.thr ftscala.PlaceQuery
 mport com.tw ter.geoduck.common.thr ftscala.ReverseGeocode PRequest
 mport com.tw ter.geoduck.serv ce.thr ftscala.GeoContext
 mport com.tw ter.geoduck.thr ftscala.ReverseGeocoder
 mport com.tw ter.st ch.St ch
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class ReverseGeocodeCl ent @ nject() (rgcServ ce: ReverseGeocoder. thodPerEndpo nt) {
  def getGeohashAndCountryCode( pAddress: Str ng): St ch[GeohashAndCountryCode] = {
    St ch
      .callFuture {
        rgcServ ce
          .reverseGeocode p(
            ReverseGeocode PRequest(
              Seq( pAddress),
              PlaceQuery(None),
              s mpleReverseGeocode = true
            ) // note: s mpleReverseGeocode  ans that country code w ll be  ncluded  n response
          ).map { response =>
            response.found.get( pAddress) match {
              case So (locat on) => getGeohashAndCountryCodeFromLocat on(locat on)
              case _ => GeohashAndCountryCode(None, None)
            }
          }
      }
  }

  pr vate def getGeohashAndCountryCodeFromLocat on(locat on: Locat on): GeohashAndCountryCode = {
    val countryCode: Opt on[Str ng] = locat on.s mpleRgcResult.flatMap { _.countryCodeAlpha2 }

    val geohashStr ng: Opt on[Str ng] = locat on.geohash.flatMap { hash =>
      hash.str ngGeohash.flatMap { hashStr ng =>
        So (ReverseGeocodeCl ent.truncate(hashStr ng))
      }
    }

    GeohashAndCountryCode(geohashStr ng, countryCode)
  }

}

object ReverseGeocodeCl ent {

  val DefaultGeoduck PRequestContext: GeoContext =
    GeoContext(allPlaceTypes = true,  ncludeGeohash = true,  ncludeCountryCode = true)

  // All t se geohas s are guessed by  P (Log cal Locat on S ce).
  // So take t  f  letters to make sure    s cons stent w h Locat onServ ceCl ent
  val GeohashLengthAfterTruncat on = 4
  def truncate(geohash: Str ng): Str ng = geohash.take(GeohashLengthAfterTruncat on)
}
