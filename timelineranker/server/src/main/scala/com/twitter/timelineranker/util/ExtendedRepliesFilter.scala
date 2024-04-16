package com.tw ter.t  l neranker.ut l

 mport com.tw ter.t  l nes.model.T et d
 mport com.tw ter.t  l nes.model.User d
 mport com.tw ter.t  l nes.model.t et.HydratedT et

object ExtendedRepl esF lter {
  pr vate[ut l] def  sExtendedReply(t et: HydratedT et, follo dUser ds: Seq[User d]): Boolean = {
    t et.hasReply &&
    t et.d rectedAtUser.ex sts(!follo dUser ds.conta ns(_)) &&
    follo dUser ds.conta ns(t et.user d)
  }

  pr vate[ut l] def  sNotQual f edExtendedReply(
    t et: HydratedT et,
    user d: User d,
    follo dUser ds: Seq[User d],
    mutedUser ds: Set[User d],
    s ceT etsBy d: Map[T et d, HydratedT et]
  ): Boolean = {
    val currentUser d = user d
     sExtendedReply(t et, follo dUser ds) &&
    !(
      !t et. sRet et &&
        // and t  extended reply must be d rected at so one ot r than t  current user
        t et.d rectedAtUser.ex sts(_ != currentUser d) &&
        // T re must be a s ce t et
        t et. nReplyToT et d
          .flatMap(s ceT etsBy d.get)
          .f lter { c =>
            // and t  author of t  s ce t et must be non zero
            (c.user d != 0) &&
            (c.user d != currentUser d) && // and not by t  current user
            (!c.hasReply) && // and a root t et,  .e. not a reply
            (!c. sRet et) && // and not a ret et
            (c.user d != t et.user d) // and not a by t  sa  user
          }
          // and not by a muted user
          .ex sts(s ceT et => !mutedUser ds.conta ns(s ceT et.user d))
    )
  }

  pr vate[ut l] def  sNotVal dExpandedExtendedReply(
    t et: HydratedT et,
    v ew ngUser d: User d,
    follo dUser ds: Seq[User d],
    mutedUser ds: Set[User d],
    s ceT etsBy d: Map[T et d, HydratedT et]
  ): Boolean = {
    // An extended reply  s val d  f   hydrated t   n-reply to t et
    val  sVal dExtendedReply =
      !t et. sRet et && // extended repl es must be s ce t ets
        t et.d rectedAtUser.ex sts(
          _ != v ew ngUser d) && // t  extended reply must be d rected at so one ot r than t  v ew ng user
        t et. nReplyToT et d
          .flatMap(
            s ceT etsBy d.get
          ) // t re must be an  n-reply-to t et match ng t  follow ng proper  es
          .ex sts {  nReplyToT et =>
            ( nReplyToT et.user d > 0) && // and t   n-reply to author  s val d
            ( nReplyToT et.user d != v ew ngUser d) && // t  reply can not be  n reply to t  v ew ng user's t et
            ! nReplyToT et. sRet et && // and t   n-reply-to t et  s not a ret et (t  should always be true?)
            !mutedUser ds.conta ns(
               nReplyToT et.user d) && // and t   n-reply-to user  s not muted
             nReplyToT et. nReplyToUser d.forall(r =>
              !mutedUser ds
                .conta ns(r)) //  f t re  s an  n-reply-to- n-reply-to user t y are not muted
          }
    // f lter any  nval d extended reply
     sExtendedReply(t et, follo dUser ds) && ! sVal dExtendedReply
  }
}
