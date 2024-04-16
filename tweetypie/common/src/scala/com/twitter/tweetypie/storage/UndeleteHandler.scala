package com.tw ter.t etyp e.storage

 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.st ch.St ch
 mport com.tw ter.t etyp e.storage.T etStorageCl ent.Undelete
 mport com.tw ter.t etyp e.storage.T etUt ls._
 mport com.tw ter.ut l.T  

object UndeleteHandler {
  def apply(
    read: ManhattanOperat ons.Read,
    local nsert: ManhattanOperat ons. nsert,
    remote nsert: ManhattanOperat ons. nsert,
    delete: ManhattanOperat ons.Delete,
    undeleteW ndowH s:  nt,
    stats: StatsRece ver
  ): Undelete = {
    def w h nUndeleteW ndow(t  stampMs: Long) =
      (T  .now - T  .fromM ll seconds(t  stampMs)). nH s < undeleteW ndowH s

    def prepareUndelete(
      t et d: T et d,
      records: Seq[T etManhattanRecord]
    ): (Undelete.Response, Opt on[T etManhattanRecord]) = {
      val undeleteRecord =
        So (T etStateRecord.Undeleted(t et d, T  .now. nM ll s).toT etMhRecord)

      T etStateRecord.mostRecent(records) match {
        // c ck  f   need to undo a soft delet on
        case So (T etStateRecord.SoftDeleted(_, createdAt)) =>
           f (createdAt > 0) {
             f (w h nUndeleteW ndow(createdAt)) {
              (
                mkSuccessfulUndeleteResponse(t et d, records, So (createdAt)),
                undeleteRecord
              )
            } else {
              (Undelete.Response(Undelete.UndeleteResponseCode.BackupNotFound), None)
            }
          } else {
            throw  nternalError(s"T  stamp unava lable for $t et d")
          }

        // BounceDeleted t ets may not be undeleted. see go/bouncedt et
        case So (_: T etStateRecord.HardDeleted | _: T etStateRecord.BounceDeleted) =>
          (Undelete.Response(Undelete.UndeleteResponseCode.BackupNotFound), None)

        case So (_: T etStateRecord.Undeleted) =>
          //   st ll want to wr e t  undelete record, because at t  po nt,   only know that t  local DC's
          // w nn ng record  s not a soft/hard delet on record, wh le  s poss ble that t  remote DC's w nn ng
          // record m ght st ll be a soft delet on record. Hav ng sa d that,   don't want to set   to true
          //  f t  w nn ng record  s forceAdd, as t  forceAdd call should have ensured that both DCs had t 
          // forceAdd record.
          (mkSuccessfulUndeleteResponse(t et d, records), undeleteRecord)

        case So (_: T etStateRecord.ForceAdded) =>
          (mkSuccessfulUndeleteResponse(t et d, records), None)

        // lets wr e t  undelet on record just  n case t re  s a softdelet on record  n fl ght
        case None => (mkSuccessfulUndeleteResponse(t et d, records), undeleteRecord)
      }
    }

    // Wr e t  undelete record both locally and remotely to protect
    // aga nst races w h hard delete repl cat on.   only need t 
    // protect on for t   nsert on of t  undelete record.
    def mult  nsert(record: T etManhattanRecord): St ch[Un ] =
      St ch
        .collect(
          Seq(
            local nsert(record).l ftToTry,
            remote nsert(record).l ftToTry
          )
        )
        .map(collectW hRateL m C ck)
        .lo rFromTry

    def deleteSoftDeleteRecord(t et d: T et d): St ch[Un ] = {
      val mhKey = T etKey.softDelet onStateKey(t et d)
      delete(mhKey, None)
    }

    t et d =>
      for {
        records <- read(t et d)
        (response, undeleteRecord) = prepareUndelete(t et d, records)
        _ <- St ch.collect(undeleteRecord.map(mult  nsert)).un 
        _ <- deleteSoftDeleteRecord(t et d)
      } y eld {
        response
      }
  }

  pr vate[storage] def mkSuccessfulUndeleteResponse(
    t et d: T et d,
    records: Seq[T etManhattanRecord],
    t  stampOpt: Opt on[Long] = None
  ) =
    Undelete.Response(
      Undelete.UndeleteResponseCode.Success,
      So (
        StorageConvers ons.fromStoredT et(bu ldStoredT et(t et d, records))
      ),
      arch vedAtM ll s = t  stampOpt
    )
}
