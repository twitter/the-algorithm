package com.tw ter.follow_recom ndat ons.common.cl ents.geoduck

 mport com.tw ter.follow_recom ndat ons.common.models.GeohashAndCountryCode
 mport com.tw ter.geoduck.common.thr ftscala.Locat onS ce
 mport com.tw ter.geoduck.common.thr ftscala.PlaceQuery
 mport com.tw ter.geoduck.common.thr ftscala.Transact onLocat on
 mport com.tw ter.geoduck.common.thr ftscala.UserLocat onRequest
 mport com.tw ter.geoduck.thr ftscala.Locat onServ ce
 mport com.tw ter.st ch.St ch
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class Locat onServ ceCl ent @ nject() (locat onServ ce: Locat onServ ce. thodPerEndpo nt) {
  def getGeohashAndCountryCode(user d: Long): St ch[GeohashAndCountryCode] = {
    St ch
      .callFuture {
        locat onServ ce
          .userLocat on(
            UserLocat onRequest(
              Seq(user d),
              So (PlaceQuery(allPlaceTypes = So (true))),
              s mpleReverseGeocode = true))
          .map(_.found.get(user d)).map { transact onLocat onOpt =>
            val geohashOpt = transact onLocat onOpt.flatMap(getGeohashFromTransact onLocat on)
            val countryCodeOpt =
              transact onLocat onOpt.flatMap(_.s mpleRgcResult.flatMap(_.countryCodeAlpha2))
            GeohashAndCountryCode(geohashOpt, countryCodeOpt)
          }
      }
  }

  pr vate[t ] def getGeohashFromTransact onLocat on(
    transact onLocat on: Transact onLocat on
  ): Opt on[Str ng] = {
    transact onLocat on.geohash.flatMap { geohash =>
      val geohashPref xLength = transact onLocat on.locat onS ce match {
        //  f locat on s ce  s log cal, keep t  f rst 4 chars  n geohash
        case So (Locat onS ce.Log cal) => So (4)
        //  f locat on s ce  s phys cal, keep t  pref x accord ng to accuracy
        // accuracy  s t  accuracy of GPS read ngs  n t  un  of  ter
        case So (Locat onS ce.Phys cal) =>
          transact onLocat on.coord nate.flatMap { coord nate =>
            coord nate.accuracy match {
              case So (accuracy)  f (accuracy < 50) => So (7)
              case So (accuracy)  f (accuracy < 200) => So (6)
              case So (accuracy)  f (accuracy < 1000) => So (5)
              case So (accuracy)  f (accuracy < 50000) => So (4)
              case So (accuracy)  f (accuracy < 100000) => So (3)
              case _ => None
            }
          }
        case So (Locat onS ce.Model) => So (4)
        case _ => None
      }
      geohashPref xLength match {
        case So (l:  nt) => geohash.str ngGeohash.map(_.take(l))
        case _ => None
      }
    }
  }
}
