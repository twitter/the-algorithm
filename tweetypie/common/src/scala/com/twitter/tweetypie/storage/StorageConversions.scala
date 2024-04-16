package com.tw ter.t etyp e.storage

 mport com.tw ter. d aserv ces.commons.t et d a.thr ftscala._
 mport com.tw ter.scrooge.TF eldBlob
 mport com.tw ter.t etyp e.add  onalf elds.Add  onalF elds
 mport com.tw ter.t etyp e.storage_ nternal.thr ftscala._
 mport com.tw ter.t etyp e.thr ftscala._
 mport com.tw ter.t etyp e.ut l.T etLenses

object StorageConvers ons {
  pr vate val tbT etComp ledAdd  onalF eld ds =
    StoredT et. taData.f elds.map(_. d).f lter(Add  onalF elds. sAdd  onalF eld d)

  def toStoredReply(reply: Reply, conversat on d: Opt on[T et d]): StoredReply =
    StoredReply(
       nReplyToStatus d = reply. nReplyToStatus d.getOrElse(0),
       nReplyToUser d = reply. nReplyToUser d,
      conversat on d = conversat on d
    )

  def toStoredShare(share: Share): StoredShare =
    StoredShare(
      share.s ceStatus d,
      share.s ceUser d,
      share.parentStatus d
    )

  def toStoredQuotedT et(qt: QuotedT et, text: Str ng): Opt on[StoredQuotedT et] =
    qt.permal nk
      .f lterNot { p =>
        text.conta ns(p.shortUrl)
      } // om  StoredQuotedT et w n url already  n text
      .map { p =>
        StoredQuotedT et(
          qt.t et d,
          qt.user d,
          p.shortUrl
        )
      }

  def toStoredGeo(t et: T et): Opt on[StoredGeo] =
    T etLenses.geoCoord nates.get(t et) match {
      case None =>
        T etLenses.place d.get(t et) match {
          case None => None
          case So (place d) =>
            So (
              StoredGeo(
                lat ude = 0.0,
                long ude = 0.0,
                geoPrec s on = 0,
                ent y d = 0,
                na  = So (place d)
              )
            )
        }
      case So (coords) =>
        So (
          StoredGeo(
            lat ude = coords.lat ude,
            long ude = coords.long ude,
            geoPrec s on = coords.geoPrec s on,
            ent y d =  f (coords.d splay) 2 else 0,
            na  = T etLenses.place d.get(t et)
          )
        )
    }

  def toStored d a( d aL st: Seq[ d aEnt y]): Seq[Stored d aEnt y] =
     d aL st.f lter(_.s ceStatus d. sEmpty).flatMap(toStored d aEnt y)

  def toStored d aEnt y( d a:  d aEnt y): Opt on[Stored d aEnt y] =
     d a.s zes.f nd(_.s zeType ==  d aS zeType.Or g).map { or gS ze =>
      Stored d aEnt y(
         d =  d a. d a d,
         d aType = or gS ze.deprecatedContentType.value.toByte,
        w dth = or gS ze.w dth.toShort,
          ght = or gS ze.  ght.toShort
      )
    }

  // T  language and  ds f elds are for compat b l y w h ex st ng t ets stored  n manhattan.
  def toStoredNarrowcast(narrowcast: Narrowcast): StoredNarrowcast =
    StoredNarrowcast(
      language = So (Seq.empty),
      locat on = So (narrowcast.locat on),
       ds = So (Seq.empty)
    )

  def toStoredAdd  onalF elds(from: Seq[TF eldBlob], to: StoredT et): StoredT et =
    from.foldLeft(to) { case (t, f) => t.setF eld(f) }

  def toStoredAdd  onalF elds(from: T et, to: StoredT et): StoredT et =
    toStoredAdd  onalF elds(Add  onalF elds.add  onalF elds(from), to)

  def toStoredT et(t et: T et): StoredT et = {
    val storedT et =
      StoredT et(
         d = t et. d,
        user d = So (T etLenses.user d(t et)),
        text = So (T etLenses.text(t et)),
        createdV a = So (T etLenses.createdV a(t et)),
        createdAtSec = So (T etLenses.createdAt(t et)),
        reply =
          T etLenses.reply(t et).map { r => toStoredReply(r, T etLenses.conversat on d(t et)) },
        share = T etLenses.share(t et).map(toStoredShare),
        contr butor d = t et.contr butor.map(_.user d),
        geo = toStoredGeo(t et),
        hasTakedown = So (T etLenses.hasTakedown(t et)),
        nsfwUser = So (T etLenses.nsfwUser(t et)),
        nsfwAdm n = So (T etLenses.nsfwAdm n(t et)),
         d a = t et. d a.map(toStored d a),
        narrowcast = T etLenses.narrowcast(t et).map(toStoredNarrowcast),
        nullcast = So (T etLenses.nullcast(t et)),
        track ng d = T etLenses.track ng d(t et),
        quotedT et = T etLenses.quotedT et(t et).flatMap { qt =>
          toStoredQuotedT et(qt, T etLenses.text(t et))
        }
      )
    toStoredAdd  onalF elds(t et, storedT et)
  }

  /**
   * Does not need core data to be set. Constructs on d sk t et by avo d ng t  T etLenses object
   * and only extract ng t  spec f ed f elds.
   *
   * NOTE: Assu s that spec f ed f elds are set  n t  t et.
   *
   * @param tpT et T etyp e T et to be converted
   * @param f elds t  f elds to be populated  n t  on d sk T et
   *
   * @return an on d sk T et wh ch has only t  spec f ed f elds set
   */
  def toStoredT etForF elds(tpT et: T et, f elds: Set[F eld]): StoredT et = {

    // Make sure all t  passed  n f elds are known or add  onal f elds
    requ re(
      (f elds -- F eld.AllUpdatableComp ledF elds)
        .forall(f eld => Add  onalF elds. sAdd  onalF eld d(f eld. d))
    )

    val storedT et =
      StoredT et(
         d = tpT et. d,
        geo =  f (f elds.conta ns(F eld.Geo)) {
          tpT et.coreData.get.coord nates match {
            case None =>
              tpT et.coreData.get.place d match {
                case None => None
                case So (place d) =>
                  So (
                    StoredGeo(
                      lat ude = 0.0,
                      long ude = 0.0,
                      geoPrec s on = 0,
                      ent y d = 0,
                      na  = So (place d)
                    )
                  )
              }
            case So (coords) =>
              So (
                StoredGeo(
                  lat ude = coords.lat ude,
                  long ude = coords.long ude,
                  geoPrec s on = coords.geoPrec s on,
                  ent y d =  f (coords.d splay) 2 else 0,
                  na  = tpT et.coreData.get.place d
                )
              )
          }
        } else {
          None
        },
        hasTakedown =
           f (f elds.conta ns(F eld.HasTakedown))
            So (tpT et.coreData.get.hasTakedown)
          else
            None,
        nsfwUser =
           f (f elds.conta ns(F eld.NsfwUser))
            So (tpT et.coreData.get.nsfwUser)
          else
            None,
        nsfwAdm n =
           f (f elds.conta ns(F eld.NsfwAdm n))
            So (tpT et.coreData.get.nsfwAdm n)
          else
            None
      )

     f (f elds.map(_. d).ex sts(Add  onalF elds. sAdd  onalF eld d))
      toStoredAdd  onalF elds(tpT et, storedT et)
    else
      storedT et
  }

  def fromStoredReply(reply: StoredReply): Reply =
    Reply(
      So (reply. nReplyToStatus d).f lter(_ > 0),
      reply. nReplyToUser d
    )

  def fromStoredShare(share: StoredShare): Share =
    Share(
      share.s ceStatus d,
      share.s ceUser d,
      share.parentStatus d
    )

  def fromStoredQuotedT et(qt: StoredQuotedT et): QuotedT et =
    QuotedT et(
      qt.t et d,
      qt.user d,
      So (
        ShortenedUrl(
          shortUrl = qt.shortUrl,
          longUrl = "", // w ll be hydrated later v a t etyp e's QuotedT etRefUrlsHydrator
          d splayText = "" //w ll be hydrated later v a t etyp e's QuotedT etRefUrlsHydrator
        )
      )
    )

  def fromStoredGeo(geo: StoredGeo): GeoCoord nates =
    GeoCoord nates(
      lat ude = geo.lat ude,
      long ude = geo.long ude,
      geoPrec s on = geo.geoPrec s on,
      d splay = geo.ent y d == 2
    )

  def fromStored d aEnt y( d a: Stored d aEnt y):  d aEnt y =
     d aEnt y(
      from ndex = -1, // w ll get f lled  n later
      to ndex = -1, // w ll get f lled  n later
      url = null, // w ll get f lled  n later
       d aPath = "", // f eld  s obsolete
       d aUrl = null, // w ll get f lled  n later
       d aUrlHttps = null, // w ll get f lled  n later
      d splayUrl = null, // w ll get f lled  n later
      expandedUrl = null, // w ll get f lled  n later
       d a d =  d a. d,
      nsfw = false,
      s zes = Set(
         d aS ze(
          s zeType =  d aS zeType.Or g,
          res ze thod =  d aRes ze thod.F ,
          deprecatedContentType =  d aContentType( d a. d aType),
          w dth =  d a.w dth,
            ght =  d a.  ght
        )
      )
    )

  def fromStoredNarrowcast(narrowcast: StoredNarrowcast): Narrowcast =
    Narrowcast(
      locat on = narrowcast.locat on.getOrElse(Seq())
    )

  def fromStoredT et(storedT et: StoredT et): T et = {
    val coreData =
      T etCoreData(
        user d = storedT et.user d.get,
        text = storedT et.text.get,
        createdV a = storedT et.createdV a.get,
        createdAtSecs = storedT et.createdAtSec.get,
        reply = storedT et.reply.map(fromStoredReply),
        share = storedT et.share.map(fromStoredShare),
        hasTakedown = storedT et.hasTakedown.getOrElse(false),
        nsfwUser = storedT et.nsfwUser.getOrElse(false),
        nsfwAdm n = storedT et.nsfwAdm n.getOrElse(false),
        narrowcast = storedT et.narrowcast.map(fromStoredNarrowcast),
        nullcast = storedT et.nullcast.getOrElse(false),
        track ng d = storedT et.track ng d,
        conversat on d = storedT et.reply.flatMap(_.conversat on d),
        place d = storedT et.geo.flatMap(_.na ),
        coord nates = storedT et.geo.map(fromStoredGeo),
        has d a =  f (storedT et. d a.ex sts(_.nonEmpty)) So (true) else None
      )

    // ret ets should never have t  r  d a, but so  t ets  ncorrectly do.
    val stored d a =  f (coreData.share. sDef ned) N l else storedT et. d a.toSeq

    val tpT et =
      T et(
         d = storedT et. d,
        coreData = So (coreData),
        contr butor = storedT et.contr butor d.map(Contr butor(_)),
         d a = So (stored d a.flatten.map(fromStored d aEnt y)),
         nt ons = So (Seq.empty),
        urls = So (Seq.empty),
        cashtags = So (Seq.empty),
        hashtags = So (Seq.empty),
        quotedT et = storedT et.quotedT et.map(fromStoredQuotedT et)
      )
    fromStoredAdd  onalF elds(storedT et, tpT et)
  }

  def fromStoredT etAllow nval d(storedT et: StoredT et): T et = {
    fromStoredT et(
      storedT et.copy(
        user d = storedT et.user d.orElse(So (-1L)),
        text = storedT et.text.orElse(So ("")),
        createdV a = storedT et.createdV a.orElse(So ("")),
        createdAtSec = storedT et.createdAtSec.orElse(So (-1L))
      ))
  }

  def fromStoredAdd  onalF elds(from: StoredT et, to: T et): T et = {
    val passThroughAdd  onalF elds =
      from._passthroughF elds.f lterKeys(Add  onalF elds. sAdd  onalF eld d)
    val allAdd  onalF elds =
      from.getF eldBlobs(tbT etComp ledAdd  onalF eld ds) ++ passThroughAdd  onalF elds
    allAdd  onalF elds.values.foldLeft(to) { case (t, f) => t.setF eld(f) }
  }

  def toDeletedT et(storedT et: StoredT et): DeletedT et = {
    val noteT etBlob = storedT et.getF eldBlob(T et.NoteT etF eld. d)
    val noteT etOpt on = noteT etBlob.map(blob => NoteT et.decode(blob.read))
    DeletedT et(
       d = storedT et. d,
      user d = storedT et.user d,
      text = storedT et.text,
      createdAtSecs = storedT et.createdAtSec,
      share = storedT et.share.map(toDeletedShare),
       d a = storedT et. d a.map(_.map(toDeleted d aEnt y)),
      noteT et d = noteT etOpt on.map(_. d),
       sExpandable = noteT etOpt on.flatMap(_. sExpandable)
    )
  }

  def toDeletedShare(storedShare: StoredShare): DeletedT etShare =
    DeletedT etShare(
      s ceStatus d = storedShare.s ceStatus d,
      s ceUser d = storedShare.s ceUser d,
      parentStatus d = storedShare.parentStatus d
    )

  def toDeleted d aEnt y(stored d aEnt y: Stored d aEnt y): DeletedT et d aEnt y =
    DeletedT et d aEnt y(
       d = stored d aEnt y. d,
       d aType = stored d aEnt y. d aType,
      w dth = stored d aEnt y.w dth,
        ght = stored d aEnt y.  ght
    )
}
