package com.tw ter.t  l neranker.model

 mport com.tw ter.search.earlyb rd.thr ftscala.Thr ftSearchResult
 mport com.tw ter.t  l nes.model.t et.HydratedT et
 mport com.tw ter.t  l nes.model.T et d
 mport com.tw ter.t  l nes.model.User d
 mport com.tw ter.t  l nes.ut l.SnowflakeSort ndex lper
 mport com.tw ter.t etyp e.{thr ftscala => t etyp e}

object Part allyHydratedT et {
  pr vate val  nval dValue = " nval d value"

  /**
   * Creates an  nstance of Part allyHydratedT et based on t  g ven search result.
   */
  def fromSearchResult(result: Thr ftSearchResult): Part allyHydratedT et = {
    val t et d = result. d
    val  tadata = result. tadata.getOrElse(
      throw new  llegalArgu ntExcept on(
        s"cannot  n  al ze Part allyHydratedT et $t et d w hout Thr ftSearchResult  tadata."
      )
    )

    val extra tadataOpt =  tadata.extra tadata

    val user d =  tadata.fromUser d

    // T  value of referencedT etAuthor d and sharedStatus d  s only cons dered val d  f    s greater than 0.
    val referencedT etAuthor d =
       f ( tadata.referencedT etAuthor d > 0) So ( tadata.referencedT etAuthor d) else None
    val sharedStatus d =  f ( tadata.sharedStatus d > 0) So ( tadata.sharedStatus d) else None

    val  sRet et =  tadata. sRet et.getOrElse(false)
    val ret etS ceT et d =  f ( sRet et) sharedStatus d else None
    val ret etS ceUser d =  f ( sRet et) referencedT etAuthor d else None

    // T  f elds sharedStatus d and referencedT etAuthor d have overloaded  an ng w n
    // t  t et  s not a ret et (for ret et, t re  s only 1  an ng).
    // W n not a ret et,
    //  f referencedT etAuthor d and sharedStatus d are both set,    s cons dered a reply
    //  f referencedT etAuthor d  s set and sharedStatus d  s not set,    s a d rected at t et.
    // References: SEARCH-8561 and SEARCH-13142
    val  nReplyToT et d =  f (! sRet et) sharedStatus d else None
    val  nReplyToUser d =  f (! sRet et) referencedT etAuthor d else None
    val  sReply =  tadata. sReply.conta ns(true)

    val quotedT et d = extra tadataOpt.flatMap(_.quotedT et d)
    val quotedUser d = extra tadataOpt.flatMap(_.quotedUser d)

    val  sNullcast =  tadata. sNullcast.conta ns(true)

    val conversat on d = extra tadataOpt.flatMap(_.conversat on d)

    // Root author  d for t  user who posts an exclus ve t et
    val exclus veConversat onAuthor d = extra tadataOpt.flatMap(_.exclus veConversat onAuthor d)

    // Card UR  assoc ated w h an attac d card to t  t et,  f   conta ns one
    val cardUr  = extra tadataOpt.flatMap(_.cardUr )

    val t et = makeT etyP eT et(
      t et d,
      user d,
       nReplyToT et d,
       nReplyToUser d,
      ret etS ceT et d,
      ret etS ceUser d,
      quotedT et d,
      quotedUser d,
       sNullcast,
       sReply,
      conversat on d,
      exclus veConversat onAuthor d,
      cardUr 
    )
    new Part allyHydratedT et(t et)
  }

  def makeT etyP eT et(
    t et d: T et d,
    user d: User d,
     nReplyToT et d: Opt on[T et d],
     nReplyToUser d: Opt on[T et d],
    ret etS ceT et d: Opt on[T et d],
    ret etS ceUser d: Opt on[User d],
    quotedT et d: Opt on[T et d],
    quotedUser d: Opt on[User d],
     sNullcast: Boolean,
     sReply: Boolean,
    conversat on d: Opt on[Long],
    exclus veConversat onAuthor d: Opt on[Long] = None,
    cardUr : Opt on[Str ng] = None
  ): t etyp e.T et = {
    val  sD rectedAt =  nReplyToUser d. sDef ned
    val  sRet et = ret etS ceT et d. sDef ned && ret etS ceUser d. sDef ned

    val reply =  f ( sReply) {
      So (
        t etyp e.Reply(
           nReplyToStatus d =  nReplyToT et d,
           nReplyToUser d =  nReplyToUser d.getOrElse(0L) // Requ red
        )
      )
    } else None

    val d rectedAt =  f ( sD rectedAt) {
      So (
        t etyp e.D rectedAtUser(
          user d =  nReplyToUser d.get,
          screenNa  = "" // not ava lable from search
        )
      )
    } else None

    val share =  f ( sRet et) {
      So (
        t etyp e.Share(
          s ceStatus d = ret etS ceT et d.get,
          s ceUser d = ret etS ceUser d.get,
          parentStatus d =
            ret etS ceT et d.get // Not always correct (eg, ret et of a ret et).
        )
      )
    } else None

    val quotedT et =
      for {
        t et d <- quotedT et d
        user d <- quotedUser d
      } y eld t etyp e.QuotedT et(t et d = t et d, user d = user d)

    val coreData = t etyp e.T etCoreData(
      user d = user d,
      text =  nval dValue,
      createdV a =  nval dValue,
      createdAtSecs = SnowflakeSort ndex lper. dToT  stamp(t et d). nSeconds,
      d rectedAtUser = d rectedAt,
      reply = reply,
      share = share,
      nullcast =  sNullcast,
      conversat on d = conversat on d
    )

    // Hydrate exclus veT etControl wh ch determ nes w t r t  user  s able to v ew an exclus ve / SuperFollow t et.
    val exclus veT etControl = exclus veConversat onAuthor d.map { author d =>
      t etyp e.Exclus veT etControl(conversat onAuthor d = author d)
    }

    val cardReference = cardUr .map { cardUr FromEB =>
      t etyp e.CardReference(cardUr  = cardUr FromEB)
    }

    t etyp e.T et(
       d = t et d,
      quotedT et = quotedT et,
      coreData = So (coreData),
      exclus veT etControl = exclus veT etControl,
      cardReference = cardReference
    )
  }
}

/**
 * Represents an  nstance of HydratedT et that  s hydrated us ng search result
 * ( nstead of be ng hydrated us ng T etyP e serv ce).
 *
 * Not all f elds are ava lable us ng search t refore such f elds  f accessed
 * throw UnsupportedOperat onExcept on to ensure that t y are not  nadvertently
 * accessed and rel ed upon.
 */
class Part allyHydratedT et(t et: t etyp e.T et) extends HydratedT et(t et) {
  overr de def parentT et d: Opt on[T et d] = throw notSupported("parentT et d")
  overr de def  nt onedUser ds: Seq[User d] = throw notSupported(" nt onedUser ds")
  overr de def takedownCountryCodes: Set[Str ng] = throw notSupported("takedownCountryCodes")
  overr de def has d a: Boolean = throw notSupported("has d a")
  overr de def  sNarrowcast: Boolean = throw notSupported(" sNarrowcast")
  overr de def hasTakedown: Boolean = throw notSupported("hasTakedown")
  overr de def  sNsfw: Boolean = throw notSupported(" sNsfw")
  overr de def  sNsfwUser: Boolean = throw notSupported(" sNsfwUser")
  overr de def  sNsfwAdm n: Boolean = throw notSupported(" sNsfwAdm n")

  pr vate def notSupported(na : Str ng): UnsupportedOperat onExcept on = {
    new UnsupportedOperat onExcept on(s"Not supported: $na ")
  }
}
