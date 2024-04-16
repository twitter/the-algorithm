package com.tw ter.t etyp e.storage

 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.st ch.St ch
 mport com.tw ter.t etyp e.storage.T etKey.LKey.ForceAddedStateKey
 mport com.tw ter.t etyp e.storage.T etStorageCl ent.HardDeleteT et
 mport com.tw ter.t etyp e.storage.T etStorageCl ent.HardDeleteT et.Response._
 mport com.tw ter.t etyp e.storage.T etUt ls._
 mport com.tw ter.ut l.Return
 mport com.tw ter.ut l.Throw
 mport com.tw ter.ut l.T  
 mport com.tw ter.ut l.Try

object HardDeleteT etHandler {

  /**
   * W n a t et  s removed lkeys w h t se pref xes w ll be deleted permanently.
   */
  pr vate[storage] def  sKeyToBeDeleted(key: T etKey): Boolean =
    key.lKey match {
      case (T etKey.LKey.CoreF eldsKey | T etKey.LKey. nternalF eldsKey(_) |
          T etKey.LKey.Add  onalF eldsKey(_) | T etKey.LKey.SoftDelet onStateKey |
          T etKey.LKey.BounceDelet onStateKey | T etKey.LKey.UnDelet onStateKey |
          T etKey.LKey.ForceAddedStateKey) =>
        true
      case _ => false
    }

  /**
   * W n hard delet ng, t re are two act ons, wr  ng t  record and
   * remov ng t  t et data.  f   are perform ng any act on,   w ll
   * always try to remove t  t et data.  f t  t et does not yet have a
   * hard delet on record, t n   w ll need to wr e one. T   thod
   * returns t  HardDeleted record  f   needs to be wr ten, and None
   *  f   has already been wr ten.
   *
   *  f t  t et  s not  n a deleted state   s gnal t  w h a
   * Throw(NotDeleted).
   */
  pr vate[storage] def getHardDeleteStateRecord(
    t et d: T et d,
    records: Seq[T etManhattanRecord],
    mhT  stamp: T  ,
    stats: StatsRece ver
  ): Try[Opt on[T etStateRecord.HardDeleted]] = {
    val mostRecent = T etStateRecord.mostRecent(records)
    val currentStateStr = mostRecent.map(_.na ).getOrElse("no_t et_state_record")
    stats.counter(currentStateStr). ncr()

    mostRecent match {
      case So (
            record @ (T etStateRecord.SoftDeleted(_, _) | T etStateRecord.BounceDeleted(_, _))) =>
        Return(
          So (
            T etStateRecord.HardDeleted(
              t et d = t et d,
              // createdAt  s t  hard delet on t  stamp w n deal ng w h hard deletes  n Manhattan
              createdAt = mhT  stamp. nM ll s,
              // deletedAt  s t  soft delet on t  stamp w n deal ng w h hard deletes  n Manhattan
              deletedAt = record.createdAt
            )
          )
        )

      case So (_: T etStateRecord.HardDeleted) =>
        Return(None)

      case So (_: T etStateRecord.ForceAdded) =>
        Throw(NotDeleted(t et d, So (ForceAddedStateKey)))

      case So (_: T etStateRecord.Undeleted) =>
        Throw(NotDeleted(t et d, So (T etKey.LKey.UnDelet onStateKey)))

      case None =>
        Throw(NotDeleted(t et d, None))
    }
  }

  /**
   * T  handler returns HardDeleteT et.Response.Deleted  f data assoc ated w h t  t et  s deleted,
   * e  r as a result of t  request or a prev ous one.
   *
   * T  most recently added record determ nes t  t et's state. T   thod w ll only delete data
   * for t ets  n t  soft-delete or hard-delete state. (Call ng hardDeleteT et for t ets that have
   * already been hard-deleted w ll remove any lkeys that may not have been deleted prev ously).
   */
  def apply(
    read: ManhattanOperat ons.Read,
     nsert: ManhattanOperat ons. nsert,
    delete: ManhattanOperat ons.Delete,
    scr be: Scr be,
    stats: StatsRece ver
  ): T et d => St ch[HardDeleteT et.Response] = {
    val hardDeleteStats = stats.scope("hardDeleteT et")
    val hardDeleteT etCancelled = hardDeleteStats.counter("cancelled")
    val beforeStateStats = hardDeleteStats.scope("before_state")

    def removeRecords(keys: Seq[T etKey], mhT  stamp: T  ): St ch[Un ] =
      St ch
        .collect(keys.map(key => delete(key, So (mhT  stamp)).l ftToTry))
        .map(collectW hRateL m C ck)
        .lo rFromTry

    def wr eRecord(record: Opt on[T etStateRecord.HardDeleted]): St ch[Un ] =
      record match {
        case So (r) =>
           nsert(r.toT etMhRecord).onSuccess { _ =>
            scr be.logRemoved(
              r.t et d,
              T  .fromM ll seconds(r.createdAt),
               sSoftDeleted = false
            )
          }
        case None => St ch.Un 
      }

    t et d =>
      read(t et d)
        .flatMap { records =>
          val hardDelet onT  stamp = T  .now

          val keysToBeDeleted: Seq[T etKey] = records.map(_.key).f lter( sKeyToBeDeleted)

          getHardDeleteStateRecord(
            t et d,
            records,
            hardDelet onT  stamp,
            beforeStateStats) match {
            case Return(record) =>
              St ch
                .jo n(
                  wr eRecord(record),
                  removeRecords(keysToBeDeleted, hardDelet onT  stamp)
                ).map(_ =>
                  //  f t  t et d  s non-snowflake and has prev ously been hard deleted
                  // t re w ll be no coreData record to fall back on to get t  t et
                  // creat on t   and createdAtM ll s w ll be None.
                  Deleted(
                    // deletedAtM ll s: w n t  t et was hard deleted
                    deletedAtM ll s = So (hardDelet onT  stamp. nM ll s),
                    // createdAtM ll s: w n t  t et  self was created
                    // (as opposed to w n t  delet on record was created)
                    createdAtM ll s =
                      T etUt ls.creat onT  FromT et dOrMHRecords(t et d, records)
                  ))
            case Throw(notDeleted: NotDeleted) =>
              hardDeleteT etCancelled. ncr()
              St ch.value(notDeleted)
            case Throw(e) => St ch.except on(e) // t  should never happen
          }
        }
  }
}
