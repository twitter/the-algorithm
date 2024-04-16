package com.tw ter.fr gate.pushserv ce.refresh_handler.cross

 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.fr gate.common.ut l.MRNtabCopy
 mport com.tw ter.fr gate.common.ut l.MRPushCopy
 mport com.tw ter.fr gate.pushserv ce.model.PushTypes.RawCand date
 mport com.tw ter.ut l.Future

abstract class BaseCopyFra work(statsRece ver: StatsRece ver) {

  pr vate val NoAva lableCopyStat = statsRece ver.scope("no_copy_for_crt")
  pr vate val NoAva lableNtabCopyStat = statsRece ver.scope("no_ntab_copy")

  /**
   *  nstant ate push copy f lters
   */
  protected f nal val copyF lters = new CopyF lters(statsRece ver.scope("f lters"))

  /**
   *
   * T  follow ng  thod fetc s all t  push cop es for a [[com.tw ter.fr gate.thr ftscala.CommonRecom ndat onType]]
   * assoc ated w h a cand date and t n f lters t  el g ble cop es based on
   * [[PushTypes.PushCand date]] features. T se f lters are def ned  n
   * [[CopyF lters]]
   *
   * @param rawCand date - [[RawCand date]] object represent ng a recom ndat on cand date
   *
   * @return - set of el g ble push cop es for a g ven cand date
   */
  protected[cross] f nal def getEl g blePushCop esFromCand date(
    rawCand date: RawCand date
  ): Future[Seq[MRPushCopy]] = {
    val pushCop esFromRectype = Cand dateToCopy.getPushCop esFromRectype(rawCand date.commonRecType)

     f (pushCop esFromRectype. sEmpty) {
      NoAva lableCopyStat.counter(rawCand date.commonRecType.na ). ncr()
      throw new  llegalStateExcept on(s"No Copy def ned for CRT: " + rawCand date.commonRecType)
    }
    pushCop esFromRectype
      .map(pushCopySet => copyF lters.execute(rawCand date, pushCopySet.toSeq))
      .getOrElse(Future.value(Seq.empty))
  }

  /**
   *
   * T   thod essent ally forms t  base for cross-step for t  Mag cRecs Copy Fra work. G ven
   * a recom ndat on type t  returns a set of tuples w re n each tuple  s a pa r of push and
   * ntab copy el g ble for t  sa d recom ndat on type
   *
   * @param rawCand date - [[RawCand date]] object represent ng a recom ndat on cand date
   * @return    - Set of el g ble [[MRPushCopy]], Opt on[[MRNtabCopy]] for a g ven recom ndat on type
   */
  protected[cross] f nal def getEl g blePushAndNtabCop esFromCand date(
    rawCand date: RawCand date
  ): Future[Seq[(MRPushCopy, Opt on[MRNtabCopy])]] = {

    val el g blePushCop es = getEl g blePushCop esFromCand date(rawCand date)

    el g blePushCop es.map { pushCop es =>
      val setBu lder = Set.newBu lder[(MRPushCopy, Opt on[MRNtabCopy])]
      pushCop es.foreach { pushCopy =>
        val ntabCop es = Cand dateToCopy.getNtabcop esFromPushcopy(pushCopy)
        val pushNtabCopyPa rs = ntabCop es match {
          case So (ntabCopySet) =>
             f (ntabCopySet. sEmpty) {
              NoAva lableNtabCopyStat.counter(s"copy_ d: ${pushCopy.copy d}"). ncr()
              Set(pushCopy -> None)
            } // push copy only
            else ntabCopySet.map(pushCopy -> So (_))

          case None =>
            Set.empty[(MRPushCopy, Opt on[MRNtabCopy])] // no push or ntab copy
        }
        setBu lder ++= pushNtabCopyPa rs
      }
      setBu lder.result().toSeq
    }
  }
}
