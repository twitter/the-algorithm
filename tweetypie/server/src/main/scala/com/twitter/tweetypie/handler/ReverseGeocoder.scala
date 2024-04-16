package com.tw ter.t etyp e
package handler

 mport com.tw ter.geoduck.backend.hydrat on.thr ftscala.Hydrat onContext
 mport com.tw ter.geoduck.common.thr ftscala.Constants
 mport com.tw ter.geoduck.common.thr ftscala.PlaceQuery
 mport com.tw ter.geoduck.common.thr ftscala.PlaceQueryF elds
 mport com.tw ter.geoduck.serv ce.common.cl entmodules.GeoduckGeohashLocate
 mport com.tw ter.geoduck.serv ce.thr ftscala.Locat onResponse
 mport com.tw ter.geoduck.ut l.pr m  ves.LatLon
 mport com.tw ter.geoduck.ut l.pr m  ves.{Geohash => GDGeohash}
 mport com.tw ter.geoduck.ut l.pr m  ves.{Place => GDPlace}
 mport com.tw ter.servo.ut l.FutureArrow
 mport com.tw ter.t etyp e.repos ory.GeoduckPlaceConverter
 mport com.tw ter.t etyp e.{thr ftscala => TP}

object ReverseGeocoder {
  val log: Logger = Logger(getClass)

  pr vate def val dat ngRGC(rgc: ReverseGeocoder): ReverseGeocoder =
    FutureArrow {
      case (coords: TP.GeoCoord nates, language: PlaceLanguage) =>
         f (LatLon. sVal d(coords.lat ude, coords.long ude))
          rgc((coords, language))
        else
          Future.None
    }

  /**
   * create a Geo backed ReverseGeocoder
   */
  def fromGeoduck(geohashLocate: GeoduckGeohashLocate): ReverseGeocoder =
    val dat ngRGC(
      FutureArrow {
        case (geo: TP.GeoCoord nates, language: PlaceLanguage) =>
           f (log. sDebugEnabled) {
            log.debug("RGC' ng " + geo.toStr ng() + " w h geoduck")
          }

          val hydrat onContext =
            Hydrat onContext(
              placeF elds = Set[PlaceQueryF elds](
                PlaceQueryF elds.PlaceNa s
              )
            )

          val gh = GDGeohash(LatLon(lat = geo.lat ude, lon = geo.long ude))
          val placeQuery = PlaceQuery(placeTypes = So (Constants.Consu rPlaceTypes))

          geohashLocate
            .locateGeohas s(Seq(gh.toThr ft), placeQuery, hydrat onContext)
            .onFa lure { case ex => log.warn("fa led to rgc " + geo.toStr ng(), ex) }
            .map {
              (resp: Seq[Try[Locat onResponse]]) =>
                resp. adOpt on.flatMap {
                  case Throw(ex) =>
                    log.warn("rgc fa led for coords: " + geo.toStr ng(), ex)
                    None
                  case Return(locat onResponse) =>
                    GDPlace.tryLocat onResponse(locat onResponse) match {
                      case Throw(ex) =>
                        log
                          .warn("rgc fa led  n response handl ng for coords: " + geo.toStr ng(), ex)
                        None
                      case Return(tplaces) =>
                        GDPlace.p ckConsu rLocat on(tplaces).map { place: GDPlace =>
                           f (log. sDebugEnabled) {
                            log.debug("successfully rgc'd " + geo + " to " + place. d)
                          }
                          GeoduckPlaceConverter(language, place)
                        }
                    }

                }
            }
      }
    )
}
