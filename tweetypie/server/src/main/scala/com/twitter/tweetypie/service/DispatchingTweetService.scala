/** Copyr ght 2010 Tw ter,  nc. */
package com.tw ter.t etyp e
package serv ce

 mport com.tw ter.servo.except on.thr ftscala.Cl entError
 mport com.tw ter.servo.except on.thr ftscala.Cl entErrorCause
 mport com.tw ter.t etyp e.add  onalf elds.Add  onalF elds
 mport com.tw ter.t etyp e.cl ent_ d.Cl ent d lper
 mport com.tw ter.t etyp e.handler._
 mport com.tw ter.t etyp e.store._
 mport com.tw ter.t etyp e.thr ftscala._
 mport com.tw ter.ut l.Future

/**
 *  mple ntat on of t  T etServ ce wh ch d spatc s requests to underly ng
 * handlers and stores.
 */
class D spatch ngT etServ ce(
  asyncDeleteAdd  onalF eldsBu lder: AsyncDeleteAdd  onalF eldsBu lder.Type,
  asyncSetAdd  onalF eldsBu lder: AsyncSetAdd  onalF eldsBu lder.Type,
  deleteAdd  onalF eldsBu lder: DeleteAdd  onalF eldsBu lder.Type,
  deleteLocat onDataHandler: DeleteLocat onDataHandler.Type,
  deletePathHandler: T etDeletePathHandler,
  eraseUserT etsHandler: EraseUserT etsHandler,
  getDeletedT etsHandler: GetDeletedT etsHandler.Type,
  getStoredT etsHandler: GetStoredT etsHandler.Type,
  getStoredT etsByUserHandler: GetStoredT etsByUserHandler.Type,
  getT etCountsHandler: GetT etCountsHandler.Type,
  getT etsHandler: GetT etsHandler.Type,
  getT etF eldsHandler: GetT etF eldsHandler.Type,
  postT etHandler: PostT et.Type[PostT etRequest],
  postRet etHandler: PostT et.Type[Ret etRequest],
  quotedT etDeleteBu lder: QuotedT etDeleteEventBu lder.Type,
  quotedT etTakedownBu lder: QuotedT etTakedownEventBu lder.Type,
  scrubGeoScrubT etsBu lder: ScrubGeoEventBu lder.ScrubT ets.Type,
  scrubGeoUpdateUserT  stampBu lder: ScrubGeoEventBu lder.UpdateUserT  stamp.Type,
  setAdd  onalF eldsBu lder: SetAdd  onalF eldsBu lder.Type,
  setRet etV s b l yHandler: SetRet etV s b l yHandler.Type,
  statsRece ver: StatsRece ver,
  takedownHandler: TakedownHandler.Type,
  t etStore: TotalT etStore,
  undeleteT etHandler: UndeleteT etHandler.Type,
  unret etHandler: Unret etHandler.Type,
  updatePoss blySens  veT etHandler: UpdatePoss blySens  veT etHandler.Type,
  userTakedownHandler: UserTakedownHandler.Type,
  cl ent d lper: Cl ent d lper)
    extends Thr ftT etServ ce {
   mport Add  onalF elds._

  //  ncom ng reads

  overr de def getT ets(request: GetT etsRequest): Future[Seq[GetT etResult]] =
    getT etsHandler(request)

  overr de def getT etF elds(request: GetT etF eldsRequest): Future[Seq[GetT etF eldsResult]] =
    getT etF eldsHandler(request)

  overr de def getT etCounts(request: GetT etCountsRequest): Future[Seq[GetT etCountsResult]] =
    getT etCountsHandler(request)

  //  ncom ng deletes

  overr de def cascadedDeleteT et(request: CascadedDeleteT etRequest): Future[Un ] =
    deletePathHandler.cascadedDeleteT et(request)

  overr de def deleteT ets(request: DeleteT etsRequest): Future[Seq[DeleteT etResult]] =
    deletePathHandler.deleteT ets(request)

  //  ncom ng wr es

  overr de def postT et(request: PostT etRequest): Future[PostT etResult] =
    postT etHandler(request)

  overr de def postRet et(request: Ret etRequest): Future[PostT etResult] =
    postRet etHandler(request)

  overr de def setAdd  onalF elds(request: SetAdd  onalF eldsRequest): Future[Un ] = {
    val setF elds = Add  onalF elds.nonEmptyAdd  onalF eld ds(request.add  onalF elds)
     f (setF elds. sEmpty) {
      Future.except on(
        Cl entError(
          Cl entErrorCause.BadRequest,
          s"${SetAdd  onalF eldsRequest.Add  onalF eldsF eld.na }  s empty, t re must be at least one f eld to set"
        )
      )
    } else {

      unsettableAdd  onalF eld ds(request.add  onalF elds) match {
        case N l =>
          setAdd  onalF eldsBu lder(request).flatMap(t etStore.setAdd  onalF elds)
        case unsettableF eld ds =>
          Future.except on(
            Cl entError(
              Cl entErrorCause.BadRequest,
              unsettableAdd  onalF eld dsError ssage(unsettableF eld ds)
            )
          )
      }
    }
  }

  overr de def deleteAdd  onalF elds(request: DeleteAdd  onalF eldsRequest): Future[Un ] =
     f (request.t et ds. sEmpty || request.f eld ds. sEmpty) {
      Future.except on(
        Cl entError(Cl entErrorCause.BadRequest, "request conta ns empty t et  ds or f eld  ds")
      )
    } else  f (request.f eld ds.ex sts(! sAdd  onalF eld d(_))) {
      Future.except on(
        Cl entError(Cl entErrorCause.BadRequest, "cannot delete non-add  onal f elds")
      )
    } else {
      deleteAdd  onalF eldsBu lder(request).flatMap { events =>
        Future.jo n(events.map(t etStore.deleteAdd  onalF elds))
      }
    }

  overr de def async nsert(request: Async nsertRequest): Future[Un ] =
    Async nsertT et.Event.fromAsyncRequest(request) match {
      case T etStoreEventOrRetry.F rst(e) => t etStore.async nsertT et(e)
      case T etStoreEventOrRetry.Retry(e) => t etStore.retryAsync nsertT et(e)
    }

  overr de def asyncSetAdd  onalF elds(request: AsyncSetAdd  onalF eldsRequest): Future[Un ] =
    asyncSetAdd  onalF eldsBu lder(request).map {
      case T etStoreEventOrRetry.F rst(e) => t etStore.asyncSetAdd  onalF elds(e)
      case T etStoreEventOrRetry.Retry(e) => t etStore.retryAsyncSetAdd  onalF elds(e)
    }

  /**
   * Set  f a ret et should be  ncluded  n  s s ce t et's ret et count.
   *
   * T   s called by   Ret etV s b l y daemon w n a user enter/ex 
   * suspended or read-only state and all t  r ret ets v s b l y need to
   * be mod f ed.
   *
   * @see [[SetRet etV s b l yHandler]] for more  mple ntat on deta ls
   */
  overr de def setRet etV s b l y(request: SetRet etV s b l yRequest): Future[Un ] =
    setRet etV s b l yHandler(request)

  overr de def asyncSetRet etV s b l y(request: AsyncSetRet etV s b l yRequest): Future[Un ] =
    AsyncSetRet etV s b l y.Event.fromAsyncRequest(request) match {
      case T etStoreEventOrRetry.F rst(e) => t etStore.asyncSetRet etV s b l y(e)
      case T etStoreEventOrRetry.Retry(e) => t etStore.retryAsyncSetRet etV s b l y(e)
    }

  /**
   * W n a t et has been successfully undeleted from storage  n Manhattan t  endpo nt w ll
   * enqueue requests to three related endpo nts v a deferredRPC:
   *
   *   1. asyncUndeleteT et: Asynchronously handle aspects of t  undelete not requ red for t  response.
   *   2. repl catedUndeleteT et2: Send t  undeleted t et to ot r clusters for cac  cach ng.
   *
   * @see [[UndeleteT etHandler]] for t  core undelete  mple ntat on
   */
  overr de def undeleteT et(request: UndeleteT etRequest): Future[UndeleteT etResponse] =
    undeleteT etHandler(request)

  /**
   * T  async  thod that undeleteT et calls to handle not f y ng ot r serv ces of t  undelete
   * See [[T etStores.asyncUndeleteT etStore]] for all t  stores that handle t  event.
   */
  overr de def asyncUndeleteT et(request: AsyncUndeleteT etRequest): Future[Un ] =
    AsyncUndeleteT et.Event.fromAsyncRequest(request) match {
      case T etStoreEventOrRetry.F rst(e) => t etStore.asyncUndeleteT et(e)
      case T etStoreEventOrRetry.Retry(e) => t etStore.retryAsyncUndeleteT et(e)
    }

  overr de def getDeletedT ets(
    request: GetDeletedT etsRequest
  ): Future[Seq[GetDeletedT etResult]] =
    getDeletedT etsHandler(request)

  /**
   * Tr ggers t  delet on of all of a users t ets. Used by G zmoduck w n eras ng a user
   * after t y have been deact ved for so  number of days.
   */
  overr de def eraseUserT ets(request: EraseUserT etsRequest): Future[Un ] =
    eraseUserT etsHandler.eraseUserT etsRequest(request)

  overr de def asyncEraseUserT ets(request: AsyncEraseUserT etsRequest): Future[Un ] =
    eraseUserT etsHandler.asyncEraseUserT etsRequest(request)

  overr de def asyncDelete(request: AsyncDeleteRequest): Future[Un ] =
    AsyncDeleteT et.Event.fromAsyncRequest(request) match {
      case T etStoreEventOrRetry.F rst(e) => t etStore.asyncDeleteT et(e)
      case T etStoreEventOrRetry.Retry(e) => t etStore.retryAsyncDeleteT et(e)
    }

  /*
   * unret et a t et.
   *
   * T re are two ways to unret et:
   *  - call deleteT ets() w h t  ret et d
   *  - call unret et() w h t  ret eter user d and s ceT et d
   *
   * T   s useful  f   want to be able to undo a ret et w hout hav ng to
   * keep track of a ret et d
   *
   * Returns DeleteT etResult for any deleted ret ets.
   */
  overr de def unret et(request: Unret etRequest): Future[Unret etResult] =
    unret etHandler(request)

  overr de def asyncDeleteAdd  onalF elds(
    request: AsyncDeleteAdd  onalF eldsRequest
  ): Future[Un ] =
    asyncDeleteAdd  onalF eldsBu lder(request).map {
      case T etStoreEventOrRetry.F rst(e) => t etStore.asyncDeleteAdd  onalF elds(e)
      case T etStoreEventOrRetry.Retry(e) => t etStore.retryAsyncDeleteAdd  onalF elds(e)
    }

  overr de def  ncrT etFavCount(request:  ncrT etFavCountRequest): Future[Un ] =
    t etStore. ncrFavCount( ncrFavCount.Event(request.t et d, request.delta, T  .now))

  overr de def async ncrFavCount(request: Async ncrFavCountRequest): Future[Un ] =
    t etStore.async ncrFavCount(Async ncrFavCount.Event(request.t et d, request.delta, T  .now))

  overr de def  ncrT etBookmarkCount(request:  ncrT etBookmarkCountRequest): Future[Un ] =
    t etStore. ncrBookmarkCount( ncrBookmarkCount.Event(request.t et d, request.delta, T  .now))

  overr de def async ncrBookmarkCount(request: Async ncrBookmarkCountRequest): Future[Un ] =
    t etStore.async ncrBookmarkCount(
      Async ncrBookmarkCount.Event(request.t et d, request.delta, T  .now))

  overr de def scrubGeoUpdateUserT  stamp(request: DeleteLocat onData): Future[Un ] =
    scrubGeoUpdateUserT  stampBu lder(request).flatMap(t etStore.scrubGeoUpdateUserT  stamp)

  overr de def deleteLocat onData(request: DeleteLocat onDataRequest): Future[Un ] =
    deleteLocat onDataHandler(request)

  overr de def scrubGeo(request: GeoScrub): Future[Un ] =
    scrubGeoScrubT etsBu lder(request).flatMap(t etStore.scrubGeo)

  overr de def takedown(request: TakedownRequest): Future[Un ] =
    takedownHandler(request)

  overr de def quotedT etDelete(request: QuotedT etDeleteRequest): Future[Un ] =
    quotedT etDeleteBu lder(request).flatMap {
      case So (event) => t etStore.quotedT etDelete(event)
      case None => Future.Un 
    }

  overr de def quotedT etTakedown(request: QuotedT etTakedownRequest): Future[Un ] =
    quotedT etTakedownBu lder(request).flatMap {
      case So (event) => t etStore.quotedT etTakedown(event)
      case None => Future.Un 
    }

  overr de def asyncTakedown(request: AsyncTakedownRequest): Future[Un ] =
    AsyncTakedown.Event.fromAsyncRequest(request) match {
      case T etStoreEventOrRetry.F rst(e) => t etStore.asyncTakedown(e)
      case T etStoreEventOrRetry.Retry(e) => t etStore.retryAsyncTakedown(e)
    }

  overr de def setT etUserTakedown(request: SetT etUserTakedownRequest): Future[Un ] =
    userTakedownHandler(request)

  overr de def asyncUpdatePoss blySens  veT et(
    request: AsyncUpdatePoss blySens  veT etRequest
  ): Future[Un ] = {
    AsyncUpdatePoss blySens  veT et.Event.fromAsyncRequest(request) match {
      case T etStoreEventOrRetry.F rst(event) =>
        t etStore.asyncUpdatePoss blySens  veT et(event)
      case T etStoreEventOrRetry.Retry(event) =>
        t etStore.retryAsyncUpdatePoss blySens  veT et(event)
    }
  }

  overr de def flush(request: FlushRequest): Future[Un ] = {
    // T  logged "prev ous T et" value  s  ntended to be used w n  nteract vely debugg ng an
    //  ssue and an eng neer flus s t  t et manually, e.g. from t etyp e.cmdl ne console.
    // Don't log automated flus s or g nat ng from t etyp e-daemons to cut down no se.
    val logEx st ng = !cl ent d lper.effect veCl ent dRoot.ex sts(_ == "t etyp e-daemons")
    t etStore.flush(
      Flush.Event(request.t et ds, request.flushT ets, request.flushCounts, logEx st ng)
    )
  }

  //  ncom ng repl cat on events

  overr de def repl catedGetT etCounts(request: GetT etCountsRequest): Future[Un ] =
    getT etCounts(request).un 

  overr de def repl catedGetT etF elds(request: GetT etF eldsRequest): Future[Un ] =
    getT etF elds(request).un 

  overr de def repl catedGetT ets(request: GetT etsRequest): Future[Un ] =
    getT ets(request).un 

  overr de def repl cated nsertT et2(request: Repl cated nsertT et2Request): Future[Un ] =
    t etStore.repl cated nsertT et(
      Repl cated nsertT et
        .Event(
          request.cac dT et.t et,
          request.cac dT et,
          request.quoterHasAlreadyQuotedT et.getOrElse(false),
          request. n  alT etUpdateRequest
        )
    )

  overr de def repl catedDeleteT et2(request: Repl catedDeleteT et2Request): Future[Un ] =
    t etStore.repl catedDeleteT et(
      Repl catedDeleteT et.Event(
        t et = request.t et,
         sErasure = request. sErasure,
         sBounceDelete = request. sBounceDelete,
         sLastQuoteOfQuoter = request. sLastQuoteOfQuoter.getOrElse(false)
      )
    )

  overr de def repl cated ncrFavCount(t et d: T et d, delta:  nt): Future[Un ] =
    t etStore.repl cated ncrFavCount(Repl cated ncrFavCount.Event(t et d, delta))

  overr de def repl cated ncrBookmarkCount(t et d: T et d, delta:  nt): Future[Un ] =
    t etStore.repl cated ncrBookmarkCount(Repl cated ncrBookmarkCount.Event(t et d, delta))

  overr de def repl catedScrubGeo(t et ds: Seq[T et d]): Future[Un ] =
    t etStore.repl catedScrubGeo(Repl catedScrubGeo.Event(t et ds))

  overr de def repl catedSetAdd  onalF elds(request: SetAdd  onalF eldsRequest): Future[Un ] =
    t etStore.repl catedSetAdd  onalF elds(
      Repl catedSetAdd  onalF elds.Event(request.add  onalF elds)
    )

  overr de def repl catedSetRet etV s b l y(
    request: Repl catedSetRet etV s b l yRequest
  ): Future[Un ] =
    t etStore.repl catedSetRet etV s b l y(
      Repl catedSetRet etV s b l y.Event(request.src d, request.v s ble)
    )

  overr de def repl catedDeleteAdd  onalF elds(
    request: Repl catedDeleteAdd  onalF eldsRequest
  ): Future[Un ] =
    Future.jo n(
      request.f eldsMap.map {
        case (t et d, f eld ds) =>
          t etStore.repl catedDeleteAdd  onalF elds(
            Repl catedDeleteAdd  onalF elds.Event(t et d, f eld ds)
          )
      }.toSeq
    )

  overr de def repl catedUndeleteT et2(request: Repl catedUndeleteT et2Request): Future[Un ] =
    t etStore.repl catedUndeleteT et(
      Repl catedUndeleteT et
        .Event(
          request.cac dT et.t et,
          request.cac dT et,
          request.quoterHasAlreadyQuotedT et.getOrElse(false)
        ))

  overr de def repl catedTakedown(t et: T et): Future[Un ] =
    t etStore.repl catedTakedown(Repl catedTakedown.Event(t et))

  overr de def updatePoss blySens  veT et(
    request: UpdatePoss blySens  veT etRequest
  ): Future[Un ] =
    updatePoss blySens  veT etHandler(request)

  overr de def repl catedUpdatePoss blySens  veT et(t et: T et): Future[Un ] =
    t etStore.repl catedUpdatePoss blySens  veT et(
      Repl catedUpdatePoss blySens  veT et.Event(t et)
    )

  overr de def getStoredT ets(
    request: GetStoredT etsRequest
  ): Future[Seq[GetStoredT etsResult]] =
    getStoredT etsHandler(request)

  overr de def getStoredT etsByUser(
    request: GetStoredT etsByUserRequest
  ): Future[GetStoredT etsByUserResult] =
    getStoredT etsByUserHandler(request)
}
