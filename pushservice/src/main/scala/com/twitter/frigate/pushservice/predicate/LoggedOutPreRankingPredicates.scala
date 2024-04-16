package com.tw ter.fr gate.pushserv ce.pred cate

 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.fr gate.common.base.T etAuthorDeta ls
 mport com.tw ter.fr gate.common.base.T etCand date
 mport com.tw ter.fr gate.common.base.T etDeta ls
 mport com.tw ter.fr gate.common.pred cate.t et._
 mport com.tw ter.fr gate.pushserv ce.model.PushTypes.PushCand date
 mport com.tw ter. rm .pred cate.Na dPred cate

class LoggedOutPreRank ngPred catesBu lder( mpl c  statsRece ver: StatsRece ver) {

  pr vate val T etPred cates = L st[Na dPred cate[PushCand date]](
    T etObjectEx stsPred cate[
      T etCand date w h T etDeta ls
    ].applyOnlyToT etCand datesW hT etDeta ls
      .w hNa ("t et_object_ex sts"),
    Pred catesForCand date.oldT etRecsPred cate.applyOnlyToT etCand dateW hTargetAndABDec derAndMaxT etAge
      .w hNa ("old_t et"),
    Pred catesForCand date.t et sNotAreply.applyOnlyToT etCand dateW houtSoc alContextW hT etDeta ls
      .w hNa ("t et_cand date_not_a_reply"),
    T etAuthorPred cates
      .recT etAuthorUnsu able[T etCand date w h T etAuthorDeta ls]
      .applyOnlyToT etCand dateW hT etAuthorDeta ls
      .w hNa ("t et_author_unsu able")
  )

  f nal def bu ld(): L st[Na dPred cate[PushCand date]] = {
    T etPred cates
  }

}

object LoggedOutPreRank ngPred cates {
  def apply(statsRece ver: StatsRece ver): L st[Na dPred cate[PushCand date]] =
    new LoggedOutPreRank ngPred catesBu lder()(statsRece ver).bu ld()
}
