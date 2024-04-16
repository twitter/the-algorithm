package com.tw ter.t etyp e
package handler

 mport com.tw ter.flockdb.cl ent.Cursor
 mport com.tw ter.flockdb.cl ent.PageResult
 mport com.tw ter.flockdb.cl ent.Select
 mport com.tw ter.flockdb.cl ent.StatusGraph
 mport com.tw ter.flockdb.cl ent.UserT  l neGraph
 mport com.tw ter.flockdb.cl ent.thr ftscala.EdgeState
 mport com.tw ter.snowflake. d.Snowflake d
 mport com.tw ter.st ch.St ch
 mport com.tw ter.t etyp e.storage.T etStorageCl ent
 mport com.tw ter.t etyp e.storage.T etStorageCl ent.GetStoredT et
 mport com.tw ter.t etyp e.thr ftscala.GetStoredT etsByUserOpt ons
 mport com.tw ter.t etyp e.thr ftscala.GetStoredT etsByUserRequest
 mport com.tw ter.t etyp e.thr ftscala.GetStoredT etsByUserResult
 mport com.tw ter.t etyp e.thr ftscala.GetStoredT etsOpt ons
 mport com.tw ter.t etyp e.thr ftscala.GetStoredT etsRequest

object GetStoredT etsByUserHandler {
  type Type = FutureArrow[GetStoredT etsByUserRequest, GetStoredT etsByUserResult]

  def apply(
    getStoredT etsHandler: GetStoredT etsHandler.Type,
    getStoredT et: T etStorageCl ent.GetStoredT et,
    selectPage: FutureArrow[Select[StatusGraph], PageResult[Long]],
    maxPages:  nt
  ): Type = {
    FutureArrow { request =>
      val opt ons = request.opt ons.getOrElse(GetStoredT etsByUserOpt ons())

      val startT  Msec: Long = opt ons.startT  Msec.getOrElse(0L)
      val endT  Msec: Long = opt ons.endT  Msec.getOrElse(T  .now. nM ll s)
      val cursor = opt ons.cursor.map(Cursor(_)).getOrElse {
         f (opt ons.startFromOldest) Cursor.lo st else Cursor.h g st
      }

      getNextT et ds nT  Range(
        request.user d,
        startT  Msec,
        endT  Msec,
        cursor,
        selectPage,
        getStoredT et,
        maxPages,
        numTr es = 0
      ).flatMap {
        case (t et ds, cursor) =>
          val getStoredT etsRequest = toGetStoredT etsRequest(t et ds, request.user d, opt ons)

          getStoredT etsHandler(getStoredT etsRequest)
            .map { getStoredT etsResults =>
              GetStoredT etsByUserResult(
                storedT ets = getStoredT etsResults.map(_.storedT et),
                cursor =  f (cursor. sEnd) None else So (cursor.value)
              )
            }
      }
    }
  }

  pr vate def toGetStoredT etsRequest(
    t et ds: Seq[T et d],
    user d: User d,
    getStoredT etsByUserOpt ons: GetStoredT etsByUserOpt ons
  ): GetStoredT etsRequest = {

    val opt ons: GetStoredT etsOpt ons = GetStoredT etsOpt ons(
      bypassV s b l yF lter ng = getStoredT etsByUserOpt ons.bypassV s b l yF lter ng,
      forUser d =  f (getStoredT etsByUserOpt ons.setForUser d) So (user d) else None,
      add  onalF eld ds = getStoredT etsByUserOpt ons.add  onalF eld ds
    )

    GetStoredT etsRequest(
      t et ds = t et ds,
      opt ons = So (opt ons)
    )
  }

  pr vate def getNextT et ds nT  Range(
    user d: User d,
    startT  Msec: Long,
    endT  Msec: Long,
    cursor: Cursor,
    selectPage: FutureArrow[Select[StatusGraph], PageResult[Long]],
    getStoredT et: T etStorageCl ent.GetStoredT et,
    maxPages:  nt,
    numTr es:  nt
  ): Future[(Seq[T et d], Cursor)] = {
    val select = Select(
      s ce d = user d,
      graph = UserT  l neGraph,
      state ds =
        So (Seq(EdgeState.Arch ved.value, EdgeState.Pos  ve.value, EdgeState.Removed.value))
    ).w hCursor(cursor)

    def  nT  Range(t  stamp: Long): Boolean =
      t  stamp >= startT  Msec && t  stamp <= endT  Msec
    def pastT  Range(t  stamps: Seq[Long]) = {
       f (cursor. sAscend ng) {
        t  stamps.max > endT  Msec
      } else {
        t  stamps.m n < startT  Msec
      }
    }

    val pageResultFuture: Future[PageResult[Long]] = selectPage(select)

    pageResultFuture.flatMap { pageResult =>
      val grouped ds = pageResult.entr es.groupBy(Snowflake d. sSnowflake d)
      val nextCursor =  f (cursor. sAscend ng) pageResult.prev ousCursor else pageResult.nextCursor

      // T  stamps for t  creat on of T ets w h snowflake  Ds can be calculated from t   Ds
      // t mselves.
      val snowflake dsT  stamps: Seq[(Long, Long)] = grouped ds.getOrElse(true, Seq()).map {  d =>
        val snowflakeT  M ll s = Snowflake d.un xT  M ll sFrom d( d)
        ( d, snowflakeT  M ll s)
      }

      // For non-snowflake T ets,   need to fetch t  T et data from Manhattan to see w n t 
      // T et was created.
      val nonSnowflake dsT  stamps: Future[Seq[(Long, Long)]] = St ch.run(
        St ch
          .traverse(grouped ds.getOrElse(false, Seq()))(getStoredT et)
          .map {
            _.flatMap {
              case GetStoredT et.Response.FoundAny(t et, _, _, _, _) => {
                 f (t et.coreData.ex sts(_.createdAtSecs > 0)) {
                  So ((t et. d, t et.coreData.get.createdAtSecs))
                } else None
              }
              case _ => None
            }
          })

      nonSnowflake dsT  stamps.flatMap { nonSnowflakeL st =>
        val allT et dsAndT  stamps = snowflake dsT  stamps ++ nonSnowflakeL st
        val f lteredT et ds = allT et dsAndT  stamps
          .f lter {
            case (_, ts) =>  nT  Range(ts)
          }
          .map(_._1)

         f (nextCursor. sEnd) {
          //  've cons dered t  last T et for t  User. T re are no more T ets to return.
          Future.value((f lteredT et ds, Cursor.end))
        } else  f (allT et dsAndT  stamps.nonEmpty &&
          pastT  Range(allT et dsAndT  stamps.map(_._2))) {
          // At least one T et returned from Tflock has a t  stamp past   t   range,  .e.
          // greater than t  end t   ( f  're fetch ng  n an ascend ng order) or lo r than t 
          // start t   ( f  're fetch ng  n a descend ng order). T re  s no po nt  n look ng at
          // any more T ets from t  User as t y'll all be outs de t  t   range.
          Future.value((f lteredT et ds, Cursor.end))
        } else  f (f lteredT et ds. sEmpty) {
          //  're  re because one of two th ngs happened:
          // 1. allT et dsAndT  stamps  s empty: E  r Tflock has returned an empty page of T ets
          //    or    ren't able to fetch t  stamps for any of t  T ets Tflock returned.  n t 
          //    case,   fetch t  next page of T ets.
          // 2. allT et dsAndT  stamps  s non-empty but f lteredT et ds  s empty: T  current page
          //    has no T ets  ns de t  requested t   range.   fetch t  next page of T ets and
          //    try aga n.
          //  f   h  t  l m  for t  max mum number of pages from tflock to be requested,  
          // return an empty l st of T ets w h t  cursor for t  caller to try aga n.

           f (numTr es == maxPages) {
            Future.value((f lteredT et ds, nextCursor))
          } else {
            getNextT et ds nT  Range(
              user d = user d,
              startT  Msec = startT  Msec,
              endT  Msec = endT  Msec,
              cursor = nextCursor,
              selectPage = selectPage,
              getStoredT et = getStoredT et,
              maxPages = maxPages,
              numTr es = numTr es + 1
            )
          }
        } else {
          // f lteredT et ds  s non-empty: T re are so  T ets  n t  page that are w h n t 
          // requested t   range, and   aren't out of t  t   range yet.   return t  T ets  
          // have and set t  cursor forward for t  next request.
          Future.value((f lteredT et ds, nextCursor))
        }
      }
    }
  }
}
