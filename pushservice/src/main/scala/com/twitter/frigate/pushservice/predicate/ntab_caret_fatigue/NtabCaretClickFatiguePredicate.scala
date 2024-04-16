package com.tw ter.fr gate.pushserv ce.pred cate.ntab_caret_fat gue

 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.fr gate.common.pred cate.ntab_caret_fat gue.NtabCaretCl ckFat guePred cate lper
 mport com.tw ter.fr gate.common.rec_types.RecTypes
 mport com.tw ter.fr gate.pushserv ce.model.PushTypes.PushCand date
 mport com.tw ter. rm .pred cate.Na dPred cate
 mport com.tw ter. rm .pred cate.Pred cate
 mport com.tw ter.ut l.Future

object NtabCaretCl ckFat guePred cate {
  val na  = "NtabCaretCl ckFat guePred cate"

  def  sSpacesTypeAndTeam mber(cand date: PushCand date): Future[Boolean] = {
    cand date.target. sTeam mber.map {  sTeam mber =>
      val  sSpacesType = RecTypes. sRecom ndedSpacesType(cand date.commonRecType)
       sTeam mber &&  sSpacesType
    }
  }

  def apply()( mpl c  globalStats: StatsRece ver): Na dPred cate[PushCand date] = {
    val scopedStats = globalStats.scope(na )
    val gener cTypeCategor es = Seq("Mag cRecs")
    val crts = RecTypes.sharedNTabCaretFat gueTypes
    val recTypeNtabCaretCl ckFat guePred cate =
      RecTypeNtabCaretCl ckFat guePred cate.apply(
        gener cTypeCategor es,
        crts,
        NtabCaretCl ckFat guePred cate lper.calculateFat guePer odMag cRecs,
        useMostRecentD sl keT   = false
      )
    Pred cate
      .fromAsync { cand date: PushCand date =>
         sSpacesTypeAndTeam mber(cand date).flatMap {  sSpacesTypeAndTeam mber =>
           f (RecTypes.sharedNTabCaretFat gueTypes(
              cand date.commonRecType) && ! sSpacesTypeAndTeam mber) {
            recTypeNtabCaretCl ckFat guePred cate
              .apply(Seq(cand date)).map(_. adOpt on.getOrElse(false))
          } else {
            Future.True
          }
        }
      }
      .w hStats(scopedStats)
      .w hNa (na )
  }
}
