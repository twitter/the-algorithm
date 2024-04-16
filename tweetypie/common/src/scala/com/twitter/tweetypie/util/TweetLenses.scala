package com.tw ter.t etyp e.ut l

 mport com.tw ter.dataproducts.enr ch nts.thr ftscala.Prof leGeoEnr ch nt
 mport com.tw ter.expandodo.thr ftscala._
 mport com.tw ter. d aserv ces.commons.thr ftscala. d aKey
 mport com.tw ter. d aserv ces.commons.t et d a.thr ftscala._
 mport com.tw ter.servo.data.Lens
 mport com.tw ter.spam.rtf.thr ftscala.SafetyLabel
 mport com.tw ter.tseng.w hhold ng.thr ftscala.TakedownReason
 mport com.tw ter.t etyp e.thr ftscala._
 mport com.tw ter.t etyp e.un nt ons.thr ftscala.Un nt onData

object T etLenses {
   mport Lens.c ckEq

  def requ reSo [A, B](l: Lens[A, Opt on[B]]): Lens[A, B] =
    c ckEq[A, B](
      a => l.get(a).get,
      (a, b) => l.set(a, So (b))
    )

  def t etLens[A](get: T et => A, set: (T et, A) => T et): Lens[T et, A] =
    c ckEq[T et, A](get, set)

  val  d: Lens[T et, T et d] =
    t etLens[T et d](_. d, (t,  d) => t.copy( d =  d))

  val coreData: Lens[T et, Opt on[T etCoreData]] =
    t etLens[Opt on[T etCoreData]](_.coreData, (t, coreData) => t.copy(coreData = coreData))

  val requ redCoreData: Lens[T et, T etCoreData] =
    requ reSo (coreData)

  val optUrls: Lens[T et, Opt on[Seq[UrlEnt y]]] =
    t etLens[Opt on[Seq[UrlEnt y]]](_.urls, (t, urls) => t.copy(urls = urls))

  val urls: Lens[T et, Seq[UrlEnt y]] =
    t etLens[Seq[UrlEnt y]](_.urls.toSeq.flatten, (t, urls) => t.copy(urls = So (urls)))

  val opt nt ons: Lens[T et, Opt on[Seq[ nt onEnt y]]] =
    t etLens[Opt on[Seq[ nt onEnt y]]](_. nt ons, (t, v) => t.copy( nt ons = v))

  val  nt ons: Lens[T et, Seq[ nt onEnt y]] =
    t etLens[Seq[ nt onEnt y]](_. nt ons.toSeq.flatten, (t, v) => t.copy( nt ons = So (v)))

  val un nt onData: Lens[T et, Opt on[Un nt onData]] =
    t etLens[Opt on[Un nt onData]](_.un nt onData, (t, v) => t.copy(un nt onData = v))

  val optHashtags: Lens[T et, Opt on[Seq[HashtagEnt y]]] =
    t etLens[Opt on[Seq[HashtagEnt y]]](_.hashtags, (t, v) => t.copy(hashtags = v))

  val hashtags: Lens[T et, Seq[HashtagEnt y]] =
    t etLens[Seq[HashtagEnt y]](_.hashtags.toSeq.flatten, (t, v) => t.copy(hashtags = So (v)))

  val optCashtags: Lens[T et, Opt on[Seq[CashtagEnt y]]] =
    t etLens[Opt on[Seq[CashtagEnt y]]](_.cashtags, (t, v) => t.copy(cashtags = v))

  val cashtags: Lens[T et, Seq[CashtagEnt y]] =
    t etLens[Seq[CashtagEnt y]](_.cashtags.toSeq.flatten, (t, v) => t.copy(cashtags = So (v)))

  val opt d a: Lens[T et, Opt on[Seq[ d aEnt y]]] =
    t etLens[Opt on[Seq[ d aEnt y]]](_. d a, (t, v) => t.copy( d a = v))

  val  d a: Lens[T et, Seq[ d aEnt y]] =
    t etLens[Seq[ d aEnt y]](_. d a.toSeq.flatten, (t, v) => t.copy( d a = So (v)))

  val  d aKeys: Lens[T et, Seq[ d aKey]] =
    t etLens[Seq[ d aKey]](
      _. d aKeys.toSeq.flatten,
      {
        case (t, v) => t.copy( d aKeys = So (v))
      })

  val place: Lens[T et, Opt on[Place]] =
    t etLens[Opt on[Place]](
      _.place,
      {
        case (t, v) => t.copy(place = v)
      })

  val quotedT et: Lens[T et, Opt on[QuotedT et]] =
    t etLens[Opt on[QuotedT et]](
      _.quotedT et,
      {
        case (t, v) => t.copy(quotedT et = v)
      })

  val selfThread tadata: Lens[T et, Opt on[SelfThread tadata]] =
    t etLens[Opt on[SelfThread tadata]](
      _.selfThread tadata,
      {
        case (t, v) => t.copy(selfThread tadata = v)
      })

  val composerS ce: Lens[T et, Opt on[ComposerS ce]] =
    t etLens[Opt on[ComposerS ce]](
      _.composerS ce,
      {
        case (t, v) => t.copy(composerS ce = v)
      })

  val dev ceS ce: Lens[T et, Opt on[Dev ceS ce]] =
    t etLens[Opt on[Dev ceS ce]](
      _.dev ceS ce,
      {
        case (t, v) => t.copy(dev ceS ce = v)
      })

  val perspect ve: Lens[T et, Opt on[StatusPerspect ve]] =
    t etLens[Opt on[StatusPerspect ve]](
      _.perspect ve,
      {
        case (t, v) => t.copy(perspect ve = v)
      })

  val cards: Lens[T et, Opt on[Seq[Card]]] =
    t etLens[Opt on[Seq[Card]]](
      _.cards,
      {
        case (t, v) => t.copy(cards = v)
      })

  val card2: Lens[T et, Opt on[Card2]] =
    t etLens[Opt on[Card2]](
      _.card2,
      {
        case (t, v) => t.copy(card2 = v)
      })

  val cardReference: Lens[T et, Opt on[CardReference]] =
    t etLens[Opt on[CardReference]](
      _.cardReference,
      {
        case (t, v) => t.copy(cardReference = v)
      })

  val spamLabel: Lens[T et, Opt on[SafetyLabel]] =
    t etLens[Opt on[SafetyLabel]](
      _.spamLabel,
      {
        case (t, v) => t.copy(spamLabel = v)
      })

  val lowQual yLabel: Lens[T et, Opt on[SafetyLabel]] =
    t etLens[Opt on[SafetyLabel]](
      _.lowQual yLabel,
      {
        case (t, v) => t.copy(lowQual yLabel = v)
      })

  val nsfwH ghPrec s onLabel: Lens[T et, Opt on[SafetyLabel]] =
    t etLens[Opt on[SafetyLabel]](
      _.nsfwH ghPrec s onLabel,
      {
        case (t, v) => t.copy(nsfwH ghPrec s onLabel = v)
      })

  val bounceLabel: Lens[T et, Opt on[SafetyLabel]] =
    t etLens[Opt on[SafetyLabel]](
      _.bounceLabel,
      {
        case (t, v) => t.copy(bounceLabel = v)
      })

  val takedownCountryCodes: Lens[T et, Opt on[Seq[Str ng]]] =
    t etLens[Opt on[Seq[Str ng]]](
      _.takedownCountryCodes,
      {
        case (t, v) => t.copy(takedownCountryCodes = v)
      })

  val takedownReasons: Lens[T et, Opt on[Seq[TakedownReason]]] =
    t etLens[Opt on[Seq[TakedownReason]]](
      _.takedownReasons,
      {
        case (t, v) => t.copy(takedownReasons = v)
      })

  val contr butor: Lens[T et, Opt on[Contr butor]] =
    t etLens[Opt on[Contr butor]](
      _.contr butor,
      {
        case (t, v) => t.copy(contr butor = v)
      })

  val  d aTags: Lens[T et, Opt on[T et d aTags]] =
    t etLens[Opt on[T et d aTags]](
      _. d aTags,
      {
        case (t, v) => t.copy( d aTags = v)
      })

  val  d aTagMap: Lens[T et, Map[ d a d, Seq[ d aTag]]] =
    t etLens[Map[ d a d, Seq[ d aTag]]](
      _. d aTags.map { case T et d aTags(tagMap) => tagMap.toMap }.getOrElse(Map.empty),
      (t, v) => {
        val cleanMap = v.f lter { case (_, tags) => tags.nonEmpty }
        t.copy( d aTags =  f (cleanMap.nonEmpty) So (T et d aTags(cleanMap)) else None)
      }
    )

  val esc rb rdEnt yAnnotat ons: Lens[T et, Opt on[Esc rb rdEnt yAnnotat ons]] =
    t etLens[Opt on[Esc rb rdEnt yAnnotat ons]](
      _.esc rb rdEnt yAnnotat ons,
      {
        case (t, v) => t.copy(esc rb rdEnt yAnnotat ons = v)
      })

  val commun  es: Lens[T et, Opt on[Commun  es]] =
    t etLens[Opt on[Commun  es]](
      _.commun  es,
      {
        case (t, v) => t.copy(commun  es = v)
      })

  val t etyp eOnlyTakedownCountryCodes: Lens[T et, Opt on[Seq[Str ng]]] =
    t etLens[Opt on[Seq[Str ng]]](
      _.t etyp eOnlyTakedownCountryCodes,
      {
        case (t, v) => t.copy(t etyp eOnlyTakedownCountryCodes = v)
      })

  val t etyp eOnlyTakedownReasons: Lens[T et, Opt on[Seq[TakedownReason]]] =
    t etLens[Opt on[Seq[TakedownReason]]](
      _.t etyp eOnlyTakedownReasons,
      {
        case (t, v) => t.copy(t etyp eOnlyTakedownReasons = v)
      })

  val prof leGeo: Lens[T et, Opt on[Prof leGeoEnr ch nt]] =
    t etLens[Opt on[Prof leGeoEnr ch nt]](
      _.prof leGeoEnr ch nt,
      (t, v) => t.copy(prof leGeoEnr ch nt = v)
    )

  val v s bleTextRange: Lens[T et, Opt on[TextRange]] =
    t etLens[Opt on[TextRange]](
      _.v s bleTextRange,
      {
        case (t, v) => t.copy(v s bleTextRange = v)
      })

  val selfPermal nk: Lens[T et, Opt on[ShortenedUrl]] =
    t etLens[Opt on[ShortenedUrl]](
      _.selfPermal nk,
      {
        case (t, v) => t.copy(selfPermal nk = v)
      })

  val extendedT et tadata: Lens[T et, Opt on[ExtendedT et tadata]] =
    t etLens[Opt on[ExtendedT et tadata]](
      _.extendedT et tadata,
      {
        case (t, v) => t.copy(extendedT et tadata = v)
      })

  object T etCoreData {
    val user d: Lens[T etCoreData, User d] = c ckEq[T etCoreData, User d](
      _.user d,
      { (c, v) =>
        // Pleases t  comp ler: https://g hub.com/scala/bug/ ssues/9171
        val user d = v
        c.copy(user d = user d)
      })
    val text: Lens[T etCoreData, Str ng] = c ckEq[T etCoreData, Str ng](
      _.text,
      { (c, v) =>
        // Pleases t  comp ler: https://g hub.com/scala/bug/ ssues/9171
        val text = v
        c.copy(text = text)
      })
    val createdAt: Lens[T etCoreData, T et d] =
      c ckEq[T etCoreData, Long](_.createdAtSecs, (c, v) => c.copy(createdAtSecs = v))
    val createdV a: Lens[T etCoreData, Str ng] =
      c ckEq[T etCoreData, Str ng](
        _.createdV a,
        {
          case (c, v) => c.copy(createdV a = v)
        })
    val hasTakedown: Lens[T etCoreData, Boolean] =
      c ckEq[T etCoreData, Boolean](
        _.hasTakedown,
        {
          case (c, v) => c.copy(hasTakedown = v)
        })
    val nullcast: Lens[T etCoreData, Boolean] =
      c ckEq[T etCoreData, Boolean](
        _.nullcast,
        {
          case (c, v) => c.copy(nullcast = v)
        })
    val nsfwUser: Lens[T etCoreData, Boolean] =
      c ckEq[T etCoreData, Boolean](
        _.nsfwUser,
        {
          case (c, v) => c.copy(nsfwUser = v)
        })
    val nsfwAdm n: Lens[T etCoreData, Boolean] =
      c ckEq[T etCoreData, Boolean](
        _.nsfwAdm n,
        {
          case (c, v) => c.copy(nsfwAdm n = v)
        })
    val reply: Lens[T etCoreData, Opt on[Reply]] =
      c ckEq[T etCoreData, Opt on[Reply]](
        _.reply,
        {
          case (c, v) => c.copy(reply = v)
        })
    val share: Lens[T etCoreData, Opt on[Share]] =
      c ckEq[T etCoreData, Opt on[Share]](
        _.share,
        {
          case (c, v) => c.copy(share = v)
        })
    val narrowcast: Lens[T etCoreData, Opt on[Narrowcast]] =
      c ckEq[T etCoreData, Opt on[Narrowcast]](
        _.narrowcast,
        {
          case (c, v) => c.copy(narrowcast = v)
        })
    val d rectedAtUser: Lens[T etCoreData, Opt on[D rectedAtUser]] =
      c ckEq[T etCoreData, Opt on[D rectedAtUser]](
        _.d rectedAtUser,
        {
          case (c, v) => c.copy(d rectedAtUser = v)
        })
    val conversat on d: Lens[T etCoreData, Opt on[Conversat on d]] =
      c ckEq[T etCoreData, Opt on[Conversat on d]](
        _.conversat on d,
        {
          case (c, v) => c.copy(conversat on d = v)
        })
    val place d: Lens[T etCoreData, Opt on[Str ng]] =
      c ckEq[T etCoreData, Opt on[Str ng]](
        _.place d,
        {
          case (c, v) => c.copy(place d = v)
        })
    val geoCoord nates: Lens[T etCoreData, Opt on[GeoCoord nates]] =
      c ckEq[T etCoreData, Opt on[GeoCoord nates]](
        _.coord nates,
        (c, v) => c.copy(coord nates = v)
      )
    val track ng d: Lens[T etCoreData, Opt on[T et d]] =
      c ckEq[T etCoreData, Opt on[Long]](
        _.track ng d,
        {
          case (c, v) => c.copy(track ng d = v)
        })
    val has d a: Lens[T etCoreData, Opt on[Boolean]] =
      c ckEq[T etCoreData, Opt on[Boolean]](
        _.has d a,
        {
          case (c, v) => c.copy(has d a = v)
        })
  }

  val counts: Lens[T et, Opt on[StatusCounts]] =
    t etLens[Opt on[StatusCounts]](
      _.counts,
      {
        case (t, v) => t.copy(counts = v)
      })

  object StatusCounts {
    val ret etCount: Lens[StatusCounts, Opt on[T et d]] =
      c ckEq[StatusCounts, Opt on[Long]](
        _.ret etCount,
        (c, ret etCount) => c.copy(ret etCount = ret etCount)
      )

    val replyCount: Lens[StatusCounts, Opt on[T et d]] =
      c ckEq[StatusCounts, Opt on[Long]](
        _.replyCount,
        (c, replyCount) => c.copy(replyCount = replyCount)
      )

    val favor eCount: Lens[StatusCounts, Opt on[T et d]] =
      c ckEq[StatusCounts, Opt on[Long]](
        _.favor eCount,
        {
          case (c, v) => c.copy(favor eCount = v)
        })

    val quoteCount: Lens[StatusCounts, Opt on[T et d]] =
      c ckEq[StatusCounts, Opt on[Long]](
        _.quoteCount,
        {
          case (c, v) => c.copy(quoteCount = v)
        })
  }

  val user d: Lens[T et, User d] = requ redCoreData andT n T etCoreData.user d
  val text: Lens[T et, Str ng] = requ redCoreData andT n T etCoreData.text
  val createdV a: Lens[T et, Str ng] = requ redCoreData andT n T etCoreData.createdV a
  val createdAt: Lens[T et, Conversat on d] = requ redCoreData andT n T etCoreData.createdAt
  val reply: Lens[T et, Opt on[Reply]] = requ redCoreData andT n T etCoreData.reply
  val share: Lens[T et, Opt on[Share]] = requ redCoreData andT n T etCoreData.share
  val narrowcast: Lens[T et, Opt on[Narrowcast]] =
    requ redCoreData andT n T etCoreData.narrowcast
  val d rectedAtUser: Lens[T et, Opt on[D rectedAtUser]] =
    requ redCoreData andT n T etCoreData.d rectedAtUser
  val conversat on d: Lens[T et, Opt on[Conversat on d]] =
    requ redCoreData andT n T etCoreData.conversat on d
  val place d: Lens[T et, Opt on[Str ng]] = requ redCoreData andT n T etCoreData.place d
  val geoCoord nates: Lens[T et, Opt on[GeoCoord nates]] =
    requ redCoreData andT n T etCoreData.geoCoord nates
  val hasTakedown: Lens[T et, Boolean] = requ redCoreData andT n T etCoreData.hasTakedown
  val nsfwAdm n: Lens[T et, Boolean] = requ redCoreData andT n T etCoreData.nsfwAdm n
  val nsfwUser: Lens[T et, Boolean] = requ redCoreData andT n T etCoreData.nsfwUser
  val nullcast: Lens[T et, Boolean] = requ redCoreData andT n T etCoreData.nullcast
  val track ng d: Lens[T et, Opt on[Conversat on d]] =
    requ redCoreData andT n T etCoreData.track ng d
  val has d a: Lens[T et, Opt on[Boolean]] = requ redCoreData andT n T etCoreData.has d a

  object CashtagEnt y {
    val  nd ces: Lens[CashtagEnt y, (Short, Short)] =
      c ckEq[CashtagEnt y, (Short, Short)](
        t => (t.from ndex, t.to ndex),
        (t, v) => t.copy(from ndex = v._1, to ndex = v._2)
      )
    val text: Lens[CashtagEnt y, Str ng] =
      c ckEq[CashtagEnt y, Str ng](_.text, (t, text) => t.copy(text = text))
  }

  object HashtagEnt y {
    val  nd ces: Lens[HashtagEnt y, (Short, Short)] =
      c ckEq[HashtagEnt y, (Short, Short)](
        t => (t.from ndex, t.to ndex),
        (t, v) => t.copy(from ndex = v._1, to ndex = v._2)
      )
    val text: Lens[HashtagEnt y, Str ng] =
      c ckEq[HashtagEnt y, Str ng](_.text, (t, text) => t.copy(text = text))
  }

  object  d aEnt y {
    val  nd ces: Lens[ d aEnt y, (Short, Short)] =
      c ckEq[ d aEnt y, (Short, Short)](
        t => (t.from ndex, t.to ndex),
        (t, v) => t.copy(from ndex = v._1, to ndex = v._2)
      )
    val  d aS zes: Lens[ d aEnt y, collect on.Set[ d aS ze]] =
      c ckEq[ d aEnt y, scala.collect on.Set[ d aS ze]](
        _.s zes,
        (m, s zes) => m.copy(s zes = s zes)
      )
    val url: Lens[ d aEnt y, Str ng] =
      c ckEq[ d aEnt y, Str ng](
        _.url,
        {
          case (t, v) => t.copy(url = v)
        })
    val  d a nfo: Lens[ d aEnt y, Opt on[ d a nfo]] =
      c ckEq[ d aEnt y, Opt on[ d a nfo]](
        _. d a nfo,
        {
          case (t, v) => t.copy( d a nfo = v)
        })
  }

  object  nt onEnt y {
    val  nd ces: Lens[ nt onEnt y, (Short, Short)] =
      c ckEq[ nt onEnt y, (Short, Short)](
        t => (t.from ndex, t.to ndex),
        (t, v) => t.copy(from ndex = v._1, to ndex = v._2)
      )
    val screenNa : Lens[ nt onEnt y, Str ng] =
      c ckEq[ nt onEnt y, Str ng](
        _.screenNa ,
        (t, screenNa ) => t.copy(screenNa  = screenNa )
      )
  }

  object UrlEnt y {
    val  nd ces: Lens[UrlEnt y, (Short, Short)] =
      c ckEq[UrlEnt y, (Short, Short)](
        t => (t.from ndex, t.to ndex),
        (t, v) => t.copy(from ndex = v._1, to ndex = v._2)
      )
    val url: Lens[UrlEnt y, Str ng] =
      c ckEq[UrlEnt y, Str ng](_.url, (t, url) => t.copy(url = url))
  }

  object Contr butor {
    val screenNa : Lens[Contr butor, Opt on[Str ng]] =
      c ckEq[Contr butor, Opt on[Str ng]](
        _.screenNa ,
        (c, screenNa ) => c.copy(screenNa  = screenNa )
      )
  }

  object Reply {
    val  nReplyToScreenNa : Lens[Reply, Opt on[Str ng]] =
      c ckEq[Reply, Opt on[Str ng]](
        _. nReplyToScreenNa ,
        (c,  nReplyToScreenNa ) => c.copy( nReplyToScreenNa  =  nReplyToScreenNa )
      )

    val  nReplyToStatus d: Lens[Reply, Opt on[T et d]] =
      c ckEq[Reply, Opt on[T et d]](
        _. nReplyToStatus d,
        (c,  nReplyToStatus d) => c.copy( nReplyToStatus d =  nReplyToStatus d)
      )
  }
}
