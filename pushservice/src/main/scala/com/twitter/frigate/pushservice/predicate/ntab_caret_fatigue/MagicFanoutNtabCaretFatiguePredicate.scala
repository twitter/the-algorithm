package com.tw ter.fr gate.pushserv ce.pred cate.ntab_caret_fat gue

 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.fr gate.common.pred cate.ntab_caret_fat gue.NtabCaretCl ckFat guePred cate lper
 mport com.tw ter.fr gate.common.rec_types.RecTypes
 mport com.tw ter.fr gate.pushserv ce.model.PushTypes.PushCand date
 mport com.tw ter. rm .pred cate.Na dPred cate

object Mag cFanoutNtabCaretFat guePred cate {
  val na  = "Mag cFanoutNtabCaretFat guePred cateForCand date"

  pr vate val Mo ntsCategory = "Mo nts"
  pr vate val Mo ntsV aMag cRecsCategory = "Mo ntsV aMag cRecs"

  def apply()( mpl c  globalStats: StatsRece ver): Na dPred cate[PushCand date] = {
    val scopedStats = globalStats.scope(na )
    val gener cTypeCategor es = Seq(Mo ntsCategory, Mo ntsV aMag cRecsCategory)
    val crts = RecTypes.mag cFanoutEventTypes
    RecTypeNtabCaretCl ckFat guePred cate
      .apply(
        gener cTypeCategor es,
        crts,
        NtabCaretCl ckFat guePred cate lper.calculateFat guePer odMag cRecs,
        useMostRecentD sl keT   = true,
        na  = na 
      ).w hStats(scopedStats).w hNa (na )
  }
}
