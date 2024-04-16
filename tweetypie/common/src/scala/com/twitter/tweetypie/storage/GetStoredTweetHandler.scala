package com.tw ter.t etyp e.storage

 mport com.tw ter.convers ons.Durat onOps._
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.st ch.St ch
 mport com.tw ter.st ch.St chSeqGroup
 mport com.tw ter.t etyp e.storage.T etStorageCl ent.GetStoredT et
 mport com.tw ter.t etyp e.storage.T etStorageCl ent.GetStoredT et.Error
 mport com.tw ter.t etyp e.storage.T etStorageCl ent.GetStoredT et.Response._
 mport com.tw ter.t etyp e.storage.T etUt ls._
 mport com.tw ter.t etyp e.thr ftscala.T et
 mport com.tw ter.ut l.T  
 mport com.tw ter.ut l.Try
 mport scala.collect on.mutable

object GetStoredT etHandler {
  pr vate[t ] object DeletedState {
    def unapply(stateRecord: Opt on[T etStateRecord]): Opt on[T etStateRecord] =
      stateRecord match {
        case state @ (So (_: T etStateRecord.SoftDeleted) | So (
              _: T etStateRecord.HardDeleted) | So (_: T etStateRecord.BounceDeleted)) =>
          state
        case _ => None
      }
  }

  pr vate[t ] def deletedAtMs(stateRecord: Opt on[T etStateRecord]): Opt on[Long] =
    stateRecord match {
      case So (d: T etStateRecord.SoftDeleted) => So (d.createdAt)
      case So (d: T etStateRecord.BounceDeleted) => So (d.createdAt)
      case So (d: T etStateRecord.HardDeleted) => So (d.deletedAt)
      case _ => None
    }

  pr vate[t ] def t etResponseFromRecords(
    t et d: T et d,
    mhRecords: Seq[T etManhattanRecord],
    statsRece ver: StatsRece ver,
  ): GetStoredT et.Response = {
    val errs =
      mutable.Buffer[Error]()

    val hasStoredT etF elds: Boolean = mhRecords.ex sts {
      case T etManhattanRecord(T etKey(_, _: T etKey.LKey.F eldKey), _) => true
      case _ => false
    }

    val storedT et =  f (hasStoredT etF elds) {
      Try(bu ldStoredT et(t et d, mhRecords,  ncludeScrubbed = true))
        .onFa lure(_ => errs.append(Error.T et sCorrupt))
        .toOpt on
    } else {
      None
    }

    val scrubbedF elds: Set[F eld d] = extractScrubbedF elds(mhRecords)
    val t et: Opt on[T et] = storedT et.map(StorageConvers ons.fromStoredT etAllow nval d)
    val stateRecords: Seq[T etStateRecord] = T etStateRecord.fromT etMhRecords(mhRecords)
    val t etState: Opt on[T etStateRecord] = T etStateRecord.mostRecent(mhRecords)

    storedT et.foreach { storedT et =>
      val storedExpectedF elds = storedT et.getF eldBlobs(expectedF elds)
      val m ss ngExpectedF elds = expectedF elds.f lterNot(storedExpectedF elds.conta ns)
       f (m ss ngExpectedF elds.nonEmpty || ! sVal d(storedT et)) {
        errs.append(Error.T etF eldsM ss ngOr nval d)
      }

      val  nval dScrubbedF elds = storedT et.getF eldBlobs(scrubbedF elds).keys
       f ( nval dScrubbedF elds.nonEmpty) {
        errs.append(Error.ScrubbedF eldsPresent)
      }

       f (deletedAtMs(t etState).ex sts(_ < T  .now. nM ll seconds - 14.days. nM ll seconds)) {
        errs.append(Error.T etShouldBeHardDeleted)
      }
    }

    val err = Opt on(errs.toL st).f lter(_.nonEmpty)

    (t et, t etState, err) match {
      case (None, None, None) =>
        statsRece ver.counter("not_found"). ncr()
        NotFound(t et d)

      case (None, So (t etState: T etStateRecord.HardDeleted), None) =>
        statsRece ver.counter("hard_deleted"). ncr()
        HardDeleted(t et d, So (t etState), stateRecords, scrubbedF elds)

      case (None, _, So (errs)) =>
        statsRece ver.counter("fa led"). ncr()
        Fa led(t et d, t etState, stateRecords, scrubbedF elds, errs)

      case (So (t et), _, So (errs)) =>
        statsRece ver.counter("found_ nval d"). ncr()
        FoundW hErrors(t et, t etState, stateRecords, scrubbedF elds, errs)

      case (So (t et), DeletedState(state), None) =>
        statsRece ver.counter("deleted"). ncr()
        FoundDeleted(t et, So (state), stateRecords, scrubbedF elds)

      case (So (t et), _, None) =>
        statsRece ver.counter("found"). ncr()
        Found(t et, t etState, stateRecords, scrubbedF elds)
    }
  }

  def apply(read: ManhattanOperat ons.Read, statsRece ver: StatsRece ver): GetStoredT et = {

    object mhGroup extends St chSeqGroup[T et d, Seq[T etManhattanRecord]] {
      overr de def run(t et ds: Seq[T et d]): St ch[Seq[Seq[T etManhattanRecord]]] = {
        Stats.addW dthStat("getStoredT et", "t et ds", t et ds.s ze, statsRece ver)
        St ch.traverse(t et ds)(read(_))
      }
    }

    t et d =>
       f (t et d <= 0) {
        St ch.NotFound
      } else {
        St ch
          .call(t et d, mhGroup)
          .map(mhRecords =>
            t etResponseFromRecords(t et d, mhRecords, statsRece ver.scope("getStoredT et")))
      }
  }
}
