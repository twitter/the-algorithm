package com.tw ter.fr gate.pushserv ce.refresh_handler.cross

 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.fr gate.common.base.Cand dateDeta ls
 mport com.tw ter.fr gate.common.ut l.MRNtabCopy
 mport com.tw ter.fr gate.common.ut l.MRPushCopy
 mport com.tw ter.fr gate.pushserv ce.model.PushTypes.RawCand date
 mport com.tw ter.fr gate.pushserv ce.model.cand date.Copy ds
 mport com.tw ter.ut l.Future

/**
 * @param statsRece ver - stats rece ver object
 */
class Cand dateCopyExpans on(statsRece ver: StatsRece ver)
    extends BaseCopyFra work(statsRece ver) {

  /**
   *
   * G ven a [[Cand dateDeta ls]] object represent ng a push recom ndat on cand date t   thod
   * expands   to mult ple cand dates, each tagged w h a push copy  d and ntab copy  d to
   * represent t  el g ble cop es for t  g ven recom ndat on cand date
   *
   * @param cand dateDeta ls - [[Cand dateDeta ls]] objects conta n ng a recom ndat on cand date
   *
   * @return - l st of tuples of [[PushTypes.RawCand date]] and [[Copy ds]]
   */
  pr vate f nal def crossCand dateDeta lsW hCopy d(
    cand dateDeta ls: Cand dateDeta ls[RawCand date]
  ): Future[Seq[(Cand dateDeta ls[RawCand date], Copy ds)]] = {
    val el g bleCopyPa rs = getEl g blePushAndNtabCop esFromCand date(cand dateDeta ls.cand date)
    val copyPa rs = el g bleCopyPa rs.map(_.map {
      case (pushCopy: MRPushCopy, ntabCopy: Opt on[MRNtabCopy]) =>
        Copy ds(
          pushCopy d = So (pushCopy.copy d),
          ntabCopy d = ntabCopy.map(_.copy d)
        )
    })

    copyPa rs.map(_.map((cand dateDeta ls, _)))
  }

  /**
   *
   * T   thod takes as  nput a l st of [[Cand dateDeta ls]] objects wh ch conta n t  push
   * recom ndat on cand dates for a g ven target user.   expands each  nput cand date  nto
   * mult ple cand dates, each tagged w h a push copy  d and ntab copy  d to represent t  el g ble
   * cop es for t  g ven recom ndat on cand date
   *
   * @param cand dateDeta lsSeq - l st of fetc d cand dates for push recom ndat on
   * @return - l st of tuples of [[RawCand date]] and [[Copy ds]]
   */
  f nal def expandCand datesW hCopy d(
    cand dateDeta lsSeq: Seq[Cand dateDeta ls[RawCand date]]
  ): Future[Seq[(Cand dateDeta ls[RawCand date], Copy ds)]] =
    Future.collect(cand dateDeta lsSeq.map(crossCand dateDeta lsW hCopy d)).map(_.flatten)
}
