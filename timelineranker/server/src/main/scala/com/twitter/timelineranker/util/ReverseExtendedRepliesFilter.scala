package com.tw ter.t  l neranker.ut l

 mport com.tw ter.t  l nes.model.T et d
 mport com.tw ter.t  l nes.model.User d
 mport com.tw ter.t  l nes.model.t et.HydratedT et

object ReverseExtendedRepl esF lter {
  pr vate[ut l] def  sQual f edReverseExtendedReply(
    t et: HydratedT et,
    currentUser d: User d,
    follo dUser ds: Seq[User d],
    mutedUser ds: Set[User d],
    s ceT etsBy d: Map[T et d, HydratedT et]
  ): Boolean = {
    // t et author  s out of t  current user's network
    !follo dUser ds.conta ns(t et.user d) &&
    // t et author  s not muted
    !mutedUser ds.conta ns(t et.user d) &&
    // t et  s not a ret et
    !t et. sRet et &&
    // t re must be a s ce t et
    t et. nReplyToT et d
      .flatMap(s ceT etsBy d.get)
      .f lter { s ceT et =>
        (!s ceT et. sRet et) && // and  's not a ret et
        (!s ceT et.hasReply) && // and  's not a reply
        (s ceT et.user d != 0) && // and t  author's  d must be non zero
        follo dUser ds.conta ns(s ceT et.user d) // and t  author  s follo d
      } // and t  author has not been muted
      .ex sts(s ceT et => !mutedUser ds.conta ns(s ceT et.user d))
  }
}
