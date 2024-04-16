package com.tw ter.fr gate.pushserv ce.model.ntab

 mport com.tw ter.fr gate.common.base.Top cCand date
 mport com.tw ter.fr gate.common.base.T etAuthorDeta ls
 mport com.tw ter.fr gate.common.base.T etCand date
 mport com.tw ter.fr gate.common.base.T etDeta ls
 mport com.tw ter.fr gate.common.rec_types.RecTypes._
 mport com.tw ter.fr gate.common.ut l.MrNtabCopyObjects
 mport com.tw ter.fr gate.pushserv ce.except on.T etNTabRequestHydratorExcept on
 mport com.tw ter.fr gate.pushserv ce.model.PushTypes.PushCand date
 mport com.tw ter.fr gate.pushserv ce.params.PushFeatureSw chParams
 mport com.tw ter.fr gate.pushserv ce.take.Not f cat onServ ceSender
 mport com.tw ter.not f cat onserv ce.thr ftscala.D splayText
 mport com.tw ter.not f cat onserv ce.thr ftscala.D splayTextEnt y
 mport com.tw ter.not f cat onserv ce.thr ftscala.TextValue
 mport com.tw ter.ut l.Future

tra  OutOfNetworkT etNTabRequestHydrator extends T etNTabRequestHydrator {
  self: PushCand date
    w h T etCand date
    w h T etAuthorDeta ls
    w h Top cCand date
    w h T etDeta ls =>

  lazy val useTop cCopyForMBCGNtab = mrModel ngBasedTypes.conta ns(commonRecType) && target.params(
    PushFeatureSw chParams.EnableMrModel ngBasedCand datesTop cCopy)
  lazy val useTop cCopyForFrsNtab = frsTypes.conta ns(commonRecType) && target.params(
    PushFeatureSw chParams.EnableFrsT etCand datesTop cCopy)
  lazy val useTop cCopyForTagspaceNtab = tagspaceTypes.conta ns(commonRecType) && target.params(
    PushFeatureSw chParams.EnableHashspaceCand datesTop cCopy)

  overr de lazy val tapThroughFut: Future[Str ng] = {
     f (hasV deo && self.target.params(
        PushFeatureSw chParams.EnableLaunchV deos n m rs veExplore)) {
      Future.value(
        s" / m rs ve_t  l ne?d splay_locat on=not f cat on& nclude_p nned_t et=true&p nned_t et_ d=${t et d}&tl_type= mv")
    } else {
      t etAuthor.map {
        case So (author) =>
          val authorProf le = author.prof le.getOrElse(
            throw new T etNTabRequestHydratorExcept on(
              s"Unable to obta n author prof le for: ${author. d}"))
          s"${authorProf le.screenNa }/status/${t et d.toStr ng}"
        case _ =>
          throw new T etNTabRequestHydratorExcept on(
            s"Unable to obta n author and target deta ls to generate tap through for T et: $t et d")
      }
    }
  }

  overr de lazy val d splayTextEnt  esFut: Future[Seq[D splayTextEnt y]] =
     f (local zedUttEnt y. sDef ned &&
      (useTop cCopyForMBCGNtab || useTop cCopyForFrsNtab || useTop cCopyForTagspaceNtab)) {
      Not f cat onServ ceSender
        .getD splayTextEnt yFromUser(t etAuthor, "t etAuthorNa ",  sBold = true).map(_.toSeq)
    } else {
      Not f cat onServ ceSender
        .getD splayTextEnt yFromUser(t etAuthor, "author",  sBold = true).map(_.toSeq)
    }

  overr de lazy val refreshableType: Opt on[Str ng] = {
     f (local zedUttEnt y. sDef ned &&
      (useTop cCopyForMBCGNtab || useTop cCopyForFrsNtab || useTop cCopyForTagspaceNtab)) {
      MrNtabCopyObjects.Top cT et.refreshableType
    } else ntabCopy.refreshableType
  }

  overr de def soc alProofD splayText: Opt on[D splayText] = {
     f (local zedUttEnt y. sDef ned &&
      (useTop cCopyForMBCGNtab || useTop cCopyForFrsNtab || useTop cCopyForTagspaceNtab)) {
      local zedUttEnt y.map(uttEnt y =>
        D splayText(values =
          Seq(D splayTextEnt y("top c_na ", TextValue.Text(uttEnt y.local zedNa ForD splay)))))
    } else None
  }

  overr de lazy val facep leUsersFut: Future[Seq[Long]] = sender dFut.map(Seq(_))
}
