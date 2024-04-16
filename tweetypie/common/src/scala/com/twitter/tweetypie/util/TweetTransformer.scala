package com.tw ter.t etyp e.ut l

 mport com.tw ter.t etyp e.thr ftscala._

object T etTransfor r {
  def toStatus(t et: T et): Status = {
    assert(t et.coreData.nonEmpty, "t et core data  s m ss ng")
    val coreData = t et.coreData.get

    val toGeo: Opt on[Geo] =
      coreData.coord nates match {
        case So (coords) =>
          So (
            Geo(
              lat ude = coords.lat ude,
              long ude = coords.long ude,
              geoPrec s on = coords.geoPrec s on,
              ent y d =  f (coords.d splay) 2 else 0,
              na  = coreData.place d,
              place = t et.place,
              place d = coreData.place d,
              coord nates = So (coords)
            )
          )
        case _ =>
          coreData.place d match {
            case None => None
            case So (_) =>
              So (Geo(na  = coreData.place d, place = t et.place, place d = coreData.place d))
          }
      }

    Status(
       d = t et. d,
      user d = coreData.user d,
      text = coreData.text,
      createdV a = coreData.createdV a,
      createdAt = coreData.createdAtSecs,
      urls = t et.urls.getOrElse(Seq.empty),
       nt ons = t et. nt ons.getOrElse(Seq.empty),
      hashtags = t et.hashtags.getOrElse(Seq.empty),
      cashtags = t et.cashtags.getOrElse(Seq.empty),
       d a = t et. d a.getOrElse(Seq.empty),
      reply = t et.coreData.flatMap(_.reply),
      d rectedAtUser = t et.coreData.flatMap(_.d rectedAtUser),
      share = t et.coreData.flatMap(_.share),
      quotedT et = t et.quotedT et,
      geo = toGeo,
      hasTakedown = coreData.hasTakedown,
      nsfwUser = coreData.nsfwUser,
      nsfwAdm n = coreData.nsfwAdm n,
      counts = t et.counts,
      dev ceS ce = t et.dev ceS ce,
      narrowcast = coreData.narrowcast,
      takedownCountryCodes = t et.takedownCountryCodes,
      perspect ve = t et.perspect ve,
      cards = t et.cards,
      card2 = t et.card2,
      nullcast = coreData.nullcast,
      conversat on d = coreData.conversat on d,
      language = t et.language,
      track ng d = coreData.track ng d,
      spamLabels = t et.spamLabels,
      has d a = coreData.has d a,
      contr butor = t et.contr butor,
       d aTags = t et. d aTags
    )
  }

  def toT et(status: Status): T et = {
    val coreData =
      T etCoreData(
        user d = status.user d,
        text = status.text,
        createdV a = status.createdV a,
        createdAtSecs = status.createdAt,
        reply = status.reply,
        d rectedAtUser = status.d rectedAtUser,
        share = status.share,
        hasTakedown = status.hasTakedown,
        nsfwUser = status.nsfwUser,
        nsfwAdm n = status.nsfwAdm n,
        nullcast = status.nullcast,
        narrowcast = status.narrowcast,
        track ng d = status.track ng d,
        conversat on d = status.conversat on d,
        has d a = status.has d a,
        coord nates = toCoords(status),
        place d = status.geo.flatMap(_.place d)
      )

    T et(
       d = status. d,
      coreData = So (coreData),
      urls = So (status.urls),
       nt ons = So (status. nt ons),
      hashtags = So (status.hashtags),
      cashtags = So (status.cashtags),
       d a = So (status. d a),
      place = status.geo.flatMap(_.place),
      quotedT et = status.quotedT et,
      takedownCountryCodes = status.takedownCountryCodes,
      counts = status.counts,
      dev ceS ce = status.dev ceS ce,
      perspect ve = status.perspect ve,
      cards = status.cards,
      card2 = status.card2,
      language = status.language,
      spamLabels = status.spamLabels,
      contr butor = status.contr butor,
       d aTags = status. d aTags
    )
  }

  pr vate def toCoords(status: Status): Opt on[GeoCoord nates] =
    status.geo.map { geo =>
       f (geo.coord nates.nonEmpty) geo.coord nates.get
      // Status from monora l have t  coord nates as t  top level f elds  n Geo,
      // wh le t  nested struct  s empty. So   need to copy from t  flat f elds.
      else
        GeoCoord nates(
          lat ude = geo.lat ude,
          long ude = geo.long ude,
          geoPrec s on = geo.geoPrec s on,
          d splay = geo.ent y d == 2
        )
    }
}
