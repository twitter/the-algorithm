package com.tw ter.t  l neranker.ut l

 mport com.tw ter.t  l nes.model.User d
 mport com.tw ter.t  l nes.model.t et.HydratedT et

object Recom ndedRepl esF lter {
  pr vate[ut l] def  sRecom ndedReply(
    t et: HydratedT et,
    follo dUser ds: Seq[User d]
  ): Boolean = {
    t et.hasReply && t et. nReplyToT et d.nonEmpty &&
    (!follo dUser ds.conta ns(t et.user d))
  }

  pr vate[ut l] def  sRecom ndedReplyToNotFollo dUser(
    t et: HydratedT et,
    v ew ngUser d: User d,
    follo dUser ds: Seq[User d],
    mutedUser ds: Set[User d]
  ): Boolean = {
    val  sVal dRecom ndedReply =
      !t et. sRet et &&
        t et. nReplyToUser d.ex sts(follo dUser ds.conta ns(_)) &&
        !mutedUser ds.conta ns(t et.user d)

     sRecom ndedReply(t et, follo dUser ds) && ! sVal dRecom ndedReply
  }
}
