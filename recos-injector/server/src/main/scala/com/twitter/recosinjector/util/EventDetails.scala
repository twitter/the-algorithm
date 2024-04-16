package com.tw ter.recos njector.ut l

 mport com.tw ter.fr gate.common.base.T etUt l
 mport com.tw ter.g zmoduck.thr ftscala.User
 mport com.tw ter.recos.ut l.Act on.Act on
 mport com.tw ter.t etyp e.thr ftscala.T et

/**
 * T   s used to store  nformat on about a newly created t et
 * @param val dEnt yUser ds For users  nt oned or  d atagged  n t  t et, t se follow t 
 *                           engage user and only t y are are cons dered val d
 * @param s ceT etDeta ls For Reply, Quote, or RT, s ce t et  s t  t et be ng act oned on
 */
case class T etCreateEventDeta ls(
  userT etEngage nt: UserT etEngage nt,
  val dEnt yUser ds: Seq[Long],
  s ceT etDeta ls: Opt on[T etDeta ls]) {
  // A  nt on  s only val d  f t   nt oned user follows t  s ce user
  val val d nt onUser ds: Opt on[Seq[Long]] = {
    userT etEngage nt.t etDeta ls.flatMap(_. nt onUser ds.map(_. ntersect(val dEnt yUser ds)))
  }

  // A  d atag  s only val d  f t   d atagged user follows t  s ce user
  val val d d atagUser ds: Opt on[Seq[Long]] = {
    userT etEngage nt.t etDeta ls.flatMap(_. d atagUser ds.map(_. ntersect(val dEnt yUser ds)))
  }
}

/**
 * Stores  nformat on about a favor e/unfav engage nt.
 * NOTE: T  could e  r be L kes, or UNL KEs ( .e. w n user cancels t  L ke)
 * @param userT etEngage nt t  engage nt deta ls
 */
case class T etFavor eEventDeta ls(
  userT etEngage nt: UserT etEngage nt)

/**
 * Stores  nformat on about a un f ed user act on engage nt.
 * @param userT etEngage nt t  engage nt deta ls
 */
case class UuaEngage ntEventDeta ls(
  userT etEngage nt: UserT etEngage nt)

/**
 * Deta ls about a user-t et engage nt, l ke w n a user t eted/l ked a t et
 * @param engageUser d User that engaged w h t  t et
 * @param act on T  act on t  user took on t  t et
 * @param t et d T  type of engage nt t  user took on t  t et
 */
case class UserT etEngage nt(
  engageUser d: Long,
  engageUser: Opt on[User],
  act on: Act on,
  engage ntT  M ll s: Opt on[Long],
  t et d: Long,
  t etDeta ls: Opt on[T etDeta ls])

/**
 *  lper class that decomposes a t et object and prov des related deta ls about t  t et
 */
case class T etDeta ls(t et: T et) {
  val author d: Opt on[Long] = t et.coreData.map(_.user d)

  val urls: Opt on[Seq[Str ng]] = t et.urls.map(_.map(_.url))

  val  d aUrls: Opt on[Seq[Str ng]] = t et. d a.map(_.map(_.expandedUrl))

  val hashtags: Opt on[Seq[Str ng]] = t et.hashtags.map(_.map(_.text))

  //  nt onUser ds  nclude reply user  ds at t  beg nn ng of a t et
  val  nt onUser ds: Opt on[Seq[Long]] = t et. nt ons.map(_.flatMap(_.user d))

  val  d atagUser ds: Opt on[Seq[Long]] = t et. d aTags.map {
    _.tagMap.flatMap {
      case (_,  d aTag) =>  d aTag.flatMap(_.user d)
    }.toSeq
  }

  val replyS ce d: Opt on[Long] = t et.coreData.flatMap(_.reply.flatMap(_. nReplyToStatus d))
  val replyUser d: Opt on[Long] = t et.coreData.flatMap(_.reply.map(_. nReplyToUser d))

  val ret etS ce d: Opt on[Long] = t et.coreData.flatMap(_.share.map(_.s ceStatus d))
  val ret etUser d: Opt on[Long] = t et.coreData.flatMap(_.share.map(_.s ceUser d))

  val quoteS ce d: Opt on[Long] = t et.quotedT et.map(_.t et d)
  val quoteUser d: Opt on[Long] = t et.quotedT et.map(_.user d)
  val quoteT etUrl: Opt on[Str ng] = t et.quotedT et.flatMap(_.permal nk.map(_.shortUrl))

  // f t  t et  s ret et/reply/quote, t   s t  t et that t  new t et responds to
  val (s ceT et d, s ceT etUser d) = {
    (replyS ce d, ret etS ce d, quoteS ce d) match {
      case (So (reply d), _, _) =>
        (So (reply d), replyUser d)
      case (_, So (ret et d), _) =>
        (So (ret et d), ret etUser d)
      case (_, _, So (quote d)) =>
        (So (quote d), quoteUser d)
      case _ =>
        (None, None)
    }
  }

  // Boolean  nformat on
  val hasPhoto: Boolean = T etUt l.conta nsPhotoT et(t et)

  val hasV deo: Boolean = T etUt l.conta nsV deoT et(t et)

  // T etyP e does not populate url f elds  n a quote t et create event, even though  
  // cons der quote t ets as url t ets. T  boolean  lps make up for  .
  // Deta ls: https://groups.google.com/a/tw ter.com/d/msg/eng/BhK1XAcSSWE/F8Gc4_5uDwAJ
  val hasQuoteT etUrl: Boolean = t et.quotedT et.ex sts(_.permal nk. sDef ned)

  val hasUrl: Boolean = t .urls.ex sts(_.nonEmpty) || hasQuoteT etUrl

  val hasHashtag: Boolean = t .hashtags.ex sts(_.nonEmpty)

  val  sCard: Boolean = hasUrl | hasPhoto | hasV deo

   mpl c  def bool2Long(b: Boolean): Long =  f (b) 1L else 0L

  // Return a has d long that conta ns card type  nformat on of t  t et
  val card nfo: Long =  sCard | (hasUrl << 1) | (hasPhoto << 2) | (hasV deo << 3)

  // nullcast t et  s one that  s purposefully not broadcast to follo rs, ex. an ad t et.
  val  sNullCastT et: Boolean = t et.coreData.ex sts(_.nullcast)
}
