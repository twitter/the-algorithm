package com.tw ter.follow_recom ndat ons.common.cl ents.geoduck

 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.follow_recom ndat ons.common.models.GeohashAndCountryCode
 mport com.tw ter.st ch.St ch

 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class UserLocat onFetc r @ nject() (
  locat onServ ceCl ent: Locat onServ ceCl ent,
  reverseGeocodeCl ent: ReverseGeocodeCl ent,
  statsRece ver: StatsRece ver) {

  pr vate val stats: StatsRece ver = statsRece ver.scope("user_locat on_fetc r")
  pr vate val totalRequestsCounter = stats.counter("requests")
  pr vate val emptyResponsesCounter = stats.counter("empty")
  pr vate val locat onServ ceExcept onCounter = stats.counter("locat on_serv ce_except on")
  pr vate val reverseGeocodeExcept onCounter = stats.counter("reverse_geocode_except on")

  def getGeohashAndCountryCode(
    user d: Opt on[Long],
     pAddress: Opt on[Str ng]
  ): St ch[Opt on[GeohashAndCountryCode]] = {
    totalRequestsCounter. ncr()
    val lscLocat onSt ch = St ch
      .collect {
        user d.map(locat onServ ceCl ent.getGeohashAndCountryCode)
      }.rescue {
        case _: Except on =>
          locat onServ ceExcept onCounter. ncr()
          St ch.None
      }

    val  pLocat onSt ch = St ch
      .collect {
         pAddress.map(reverseGeocodeCl ent.getGeohashAndCountryCode)
      }.rescue {
        case _: Except on =>
          reverseGeocodeExcept onCounter. ncr()
          St ch.None
      }

    St ch.jo n(lscLocat onSt ch,  pLocat onSt ch).map {
      case (lscLocat on,  pLocat on) => {
        val geohash = lscLocat on.flatMap(_.geohash).orElse( pLocat on.flatMap(_.geohash))
        val countryCode =
          lscLocat on.flatMap(_.countryCode).orElse( pLocat on.flatMap(_.countryCode))
        (geohash, countryCode) match {
          case (None, None) =>
            emptyResponsesCounter. ncr()
            None
          case _ => So (GeohashAndCountryCode(geohash, countryCode))
        }
      }
    }
  }
}
