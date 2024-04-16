package com.tw ter.fr gate.pushserv ce.pred cate

 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.fr gate.common.base.T etDeta ls
 mport com.tw ter.fr gate.pushserv ce.model.PushTypes.PushCand date
 mport com.tw ter. rm .pred cate.t etyp e.UserLocat onAndT et
 mport com.tw ter. rm .pred cate.t etyp e.W h ldT etPred cate
 mport com.tw ter. rm .pred cate.Na dPred cate
 mport com.tw ter. rm .pred cate.Pred cate
 mport com.tw ter.serv ce. tastore.gen.thr ftscala.Locat on
 mport com.tw ter.ut l.Future

object T etW h ldContentPred cate {
  val na  = "w h ld_content"
  val defaultLocat on = Locat on(c y = "", reg on = "", countryCode = "", conf dence = 0.0)

  def apply(
  )(
     mpl c  statsRece ver: StatsRece ver
  ): Na dPred cate[PushCand date w h T etDeta ls] = {
    Pred cate
      .fromAsync { cand date: PushCand date w h T etDeta ls =>
        cand date.t et match {
          case So (t et) =>
            W h ldT etPred cate(c ckAllCountr es = true)
              .apply(Seq(UserLocat onAndT et(defaultLocat on, t et)))
              .map(_. ad)
          case None =>
            Future.value(false)
        }
      }
      .w hStats(statsRece ver.scope(s"pred cate_$na "))
      .w hNa (na )
  }
}
