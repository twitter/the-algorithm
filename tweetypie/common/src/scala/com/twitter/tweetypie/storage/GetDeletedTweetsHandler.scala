package com.tw ter.t etyp e.storage

 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.st ch.St ch
 mport com.tw ter.storage.cl ent.manhattan.kv.Den edManhattanExcept on
 mport com.tw ter.t etyp e.storage.Response.T etResponseCode
 mport com.tw ter.t etyp e.storage.T etUt ls._
 mport com.tw ter.t etyp e.storage_ nternal.thr ftscala.StoredT et
 mport com.tw ter.t etyp e.thr ftscala.DeletedT et
 mport scala.ut l.control.NonFatal

sealed tra  DeleteState
object DeleteState {

  /**
   * T  t et  s deleted but has not been permanently deleted from Manhattan. T ets  n t  state
   * may be undeleted.
   */
  case object SoftDeleted extends DeleteState

  /**
   * T  t et  s deleted after be ng bounced for v olat ng t  Tw ter Rules but has not been
   * permanently deleted from Manhattan. T ets  n t  state may NOT be undeleted.
   */
  case object BounceDeleted extends DeleteState

  /**
   * T  t et has been permanently deleted from Manhattan.
   */
  case object HardDeleted extends DeleteState

  /**
   * T re  s no data  n Manhattan to d st ngu sh t  t et  d from one that never ex sted.
   */
  case object NotFound extends DeleteState

  /**
   * T  t et ex sts and  s not  n a deleted state.
   */
  case object NotDeleted extends DeleteState
}

case class DeletedT etResponse(
  t et d: T et d,
  overallResponse: T etResponseCode,
  deleteState: DeleteState,
  t et: Opt on[DeletedT et])

object GetDeletedT etsHandler {
  def apply(
    read: ManhattanOperat ons.Read,
    stats: StatsRece ver
  ): T etStorageCl ent.GetDeletedT ets =
    (unf lteredT et ds: Seq[T et d]) => {
      val t et ds = unf lteredT et ds.f lter(_ > 0)

      Stats.addW dthStat("getDeletedT ets", "t et ds", t et ds.s ze, stats)

      val st c s = t et ds.map { t et d =>
        read(t et d)
          .map { mhRecords =>
            val storedT et = bu ldStoredT et(t et d, mhRecords)

            T etStateRecord.mostRecent(mhRecords) match {
              case So (m: T etStateRecord.SoftDeleted) => softDeleted(m, storedT et)
              case So (m: T etStateRecord.BounceDeleted) => bounceDeleted(m, storedT et)
              case So (m: T etStateRecord.HardDeleted) => hardDeleted(m, storedT et)
              case _  f storedT et.getF eldBlobs(expectedF elds). sEmpty => notFound(t et d)
              case _ => notDeleted(t et d, storedT et)
            }
          }
          .handle {
            case _: Den edManhattanExcept on =>
              DeletedT etResponse(
                t et d,
                T etResponseCode.OverCapac y,
                DeleteState.NotFound,
                None
              )

            case NonFatal(ex) =>
              T etUt ls.log.warn ng(
                ex,
                s"Unhandled except on  n GetDeletedT etsHandler for t et d: $t et d"
              )
              DeletedT etResponse(t et d, T etResponseCode.Fa lure, DeleteState.NotFound, None)
          }
      }

      St ch.collect(st c s)
    }

  pr vate def notFound(t et d: T et d) =
    DeletedT etResponse(
      t et d = t et d,
      overallResponse = T etResponseCode.Success,
      deleteState = DeleteState.NotFound,
      t et = None
    )

  pr vate def softDeleted(record: T etStateRecord.SoftDeleted, storedT et: StoredT et) =
    DeletedT etResponse(
      record.t et d,
      T etResponseCode.Success,
      DeleteState.SoftDeleted,
      So (
        StorageConvers ons
          .toDeletedT et(storedT et)
          .copy(deletedAtMsec = So (record.createdAt))
      )
    )

  pr vate def bounceDeleted(record: T etStateRecord.BounceDeleted, storedT et: StoredT et) =
    DeletedT etResponse(
      record.t et d,
      T etResponseCode.Success,
      DeleteState.BounceDeleted,
      So (
        StorageConvers ons
          .toDeletedT et(storedT et)
          .copy(deletedAtMsec = So (record.createdAt))
      )
    )

  pr vate def hardDeleted(record: T etStateRecord.HardDeleted, storedT et: StoredT et) =
    DeletedT etResponse(
      record.t et d,
      T etResponseCode.Success,
      DeleteState.HardDeleted,
      So (
        StorageConvers ons
          .toDeletedT et(storedT et)
          .copy(
            hardDeletedAtMsec = So (record.createdAt),
            deletedAtMsec = So (record.deletedAt)
          )
      )
    )

  /**
   * notDeleted returns a t et to s mpl fy t etyp e.handler.UndeleteT etHandler
   */
  pr vate def notDeleted(t et d: T et d, storedT et: StoredT et) =
    DeletedT etResponse(
      t et d = t et d,
      overallResponse = T etResponseCode.Success,
      deleteState = DeleteState.NotDeleted,
      t et = So (StorageConvers ons.toDeletedT et(storedT et))
    )
}
