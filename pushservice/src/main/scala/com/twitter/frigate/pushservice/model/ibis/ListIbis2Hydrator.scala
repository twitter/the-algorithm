package com.tw ter.fr gate.pushserv ce.model. b s

 mport com.tw ter.fr gate.pushserv ce.model.L stRecom ndat onPushCand date
 mport com.tw ter.ut l.Future

tra  L st b s2Hydrator extends  b s2HydratorForCand date {
  self: L stRecom ndat onPushCand date =>

  overr de lazy val sender d: Opt on[Long] = So (0L)

  overr de lazy val modelValues: Future[Map[Str ng, Str ng]] =
    Future.jo n(l stNa , l stOwner d).map {
      case (na Opt, author d) =>
        Map(
          "l st" -> l st d.toStr ng,
          "l st_na " -> na Opt
            .getOrElse(""),
          "l st_author" -> s"${author d.getOrElse(0L)}"
        )
    }
}
