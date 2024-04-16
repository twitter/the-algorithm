package com.tw ter.t etyp e
package handler

 mport com.tw ter.f nagle.stats.Counter
 mport com.tw ter.st ch.St ch
 mport com.tw ter.t etyp e.repos ory.PlaceKey
 mport com.tw ter.t etyp e.repos ory.PlaceRepos ory
 mport com.tw ter.t etyp e.serverut l.Except onCounter
 mport com.tw ter.t etyp e.thr ftscala._

object GeoStats {
  val topTenCountryCodes: Set[PlaceLanguage] =
    Set("US", "JP", "GB", " D", "BR", "SA", "TR", "MX", "ES", "CA")

  def apply(stats: StatsRece ver): Effect[Opt on[Place]] = {
    val totalCount = stats.counter("total")
    val notFoundCount = stats.counter("not_found")
    val countryStats: Map[Str ng, Counter] =
      topTenCountryCodes.map(cc => cc -> stats.scope("w h_country_code").counter(cc)).toMap

    val placeTypeStats: Map[PlaceType, Counter] =
      Map(
        PlaceType.Adm n -> stats.counter("adm n"),
        PlaceType.C y -> stats.counter("c y"),
        PlaceType.Country -> stats.counter("country"),
        PlaceType.Ne ghborhood -> stats.counter("ne ghborhood"),
        PlaceType.Po  -> stats.counter("po "),
        PlaceType.Unknown -> stats.counter("unknown")
      )

    Effect.fromPart al {
      case So (place) => {
        totalCount. ncr()
        placeTypeStats(place.`type`). ncr()
        place.countryCode.foreach(cc => countryStats.get(cc).foreach(_. ncr()))
      }
      case None => notFoundCount. ncr()
    }
  }
}

object GeoBu lder {
  case class Request(createGeo: T etCreateGeo, userGeoEnabled: Boolean, language: Str ng)

  case class Result(geoCoord nates: Opt on[GeoCoord nates], place d: Opt on[Place d])

  type Type = FutureArrow[Request, Result]

  def apply(placeRepo: PlaceRepos ory.Type, rgc: ReverseGeocoder, stats: StatsRece ver): Type = {
    val except onCounters = Except onCounter(stats)

    def  gnoreFa lures[A](future: Future[Opt on[A]]): Future[Opt on[A]] =
      except onCounters(future).handle { case _ => None }

    def  sVal dPlace d(place d: Str ng) = Place dRegex.pattern.matc r(place d).matc s

    def  sVal dLatLon(lat ude: Double, long ude: Double): Boolean =
      lat ude >= -90.0 && lat ude <= 90.0 &&
        long ude >= -180.0 && long ude <= 180.0 &&
        // so  cl ents send (0.0, 0.0) for unknown reasons, but t   s h ghly unl kely to be
        // val d and should be treated as  f no coord nates  re sent.   f a place  d  s prov ded,
        // that w ll st ll be used.
        (lat ude != 0.0 || long ude != 0.0)

    // Count t  number of t  s   erase geo  nformat on based on user preferences.
    val geoErasedCounter = stats.counter("geo_erased")
    // Count t  number of t  s   overr de a user's preferences and add geo anyway.
    val geoOverr ddenCounter = stats.counter("geo_overr dden")

    val geoScope = stats.scope("create_geotagged_t et")

    // Counter for geo t ets w h ne  r lat lon nor place  d data
    val noGeoCounter = geoScope.counter("no_geo_ nfo")
    val  nval dCoord nates = geoScope.counter(" nval d_coord nates")
    val  nVal dPlace d = geoScope.counter(" nval d_place_ d")
    val latlonStatsEffect = GeoStats(geoScope.scope("from_latlon"))
    val place dStatsEffect = GeoStats(geoScope.scope("from_place_ d"))

    def val dateCoord nates(coords: GeoCoord nates): Opt on[GeoCoord nates] =
       f ( sVal dLatLon(coords.lat ude, coords.long ude)) So (coords)
      else {
         nval dCoord nates. ncr()
        None
      }

    def val datePlace d(place d: Str ng): Opt on[Str ng] =
       f ( sVal dPlace d(place d)) So (place d)
      else {
         nVal dPlace d. ncr()
        None
      }

    def getPlaceByRGC(coord nates: GeoCoord nates, language: Str ng): Future[Opt on[Place]] =
       gnoreFa lures(
        rgc((coord nates, language)).onSuccess(latlonStatsEffect)
      )

    def getPlaceBy d(place d: Str ng, language: Str ng): Future[Opt on[Place]] =
       gnoreFa lures(
        St ch
          .run(placeRepo(PlaceKey(place d, language)).l ftNotFoundToOpt on)
          .onSuccess(place dStatsEffect)
      )

    FutureArrow[Request, Result] { request =>
      val createGeo = request.createGeo
      val allowGeo = createGeo.overr deUserGeoSett ng || request.userGeoEnabled
      val overr deGeo = createGeo.overr deUserGeoSett ng && !request.userGeoEnabled

       f (createGeo.place d. sEmpty && createGeo.coord nates. sEmpty) {
        noGeoCounter. ncr()
        Future.value(Result(None, None))
      } else  f (!allowGeo) {
        // Record that   had geo  nformat on but had to erase   based on user preferences.
        geoErasedCounter. ncr()
        Future.value(Result(None, None))
      } else {
         f (overr deGeo) geoOverr ddenCounter. ncr()

        // treat  nval date coord nates t  sa  as no-coord nates
        val val datedCoord nates = createGeo.coord nates.flatMap(val dateCoord nates)
        val val datedPlace d = createGeo.place d.flatMap(val datePlace d)

        for {
          place <- (createGeo.place d, val datedPlace d, val datedCoord nates) match {
            //  f t  request conta ns an  nval d place  d,   want to return None for t 
            // place  nstead of reverse-geocod ng t  coord nates
            case (So (_), None, _) => Future.None
            case (_, So (place d), _) => getPlaceBy d(place d, request.language)
            case (_, _, So (coords)) => getPlaceByRGC(coords, request.language)
            case _ => Future.None
          }
        } y eld Result(val datedCoord nates, place.map(_. d))
      }
    }
  }
}
