package com.tw ter.t etyp e.thr ftscala

 mport com.tw ter.ut l.Future

/**
 * A tra  for T etServ ce  mple ntat ons that wrap an underly ng
 * T etServ ce and need to mod fy only so  of t   thods.
 */
tra  T etServ ceProxy extends T etServ ce. thodPerEndpo nt {
  protected def underly ng: T etServ ce. thodPerEndpo nt

  /**
   * Default  mple ntat on s mply passes through t  Future but log c can be added to wrap each
   *  nvocat on to t  underly ng T etServ ce
   */
  protected def wrap[A](f: => Future[A]): Future[A] =
    f

  overr de def getT ets(request: GetT etsRequest): Future[Seq[GetT etResult]] =
    wrap(underly ng.getT ets(request))

  overr de def getT etF elds(request: GetT etF eldsRequest): Future[Seq[GetT etF eldsResult]] =
    wrap(underly ng.getT etF elds(request))

  overr de def getT etCounts(request: GetT etCountsRequest): Future[Seq[GetT etCountsResult]] =
    wrap(underly ng.getT etCounts(request))

  overr de def setAdd  onalF elds(request: SetAdd  onalF eldsRequest): Future[Un ] =
    wrap(underly ng.setAdd  onalF elds(request))

  overr de def deleteAdd  onalF elds(request: DeleteAdd  onalF eldsRequest): Future[Un ] =
    wrap(underly ng.deleteAdd  onalF elds(request))

  overr de def postT et(request: PostT etRequest): Future[PostT etResult] =
    wrap(underly ng.postT et(request))

  overr de def postRet et(request: Ret etRequest): Future[PostT etResult] =
    wrap(underly ng.postRet et(request))

  overr de def unret et(request: Unret etRequest): Future[Unret etResult] =
    wrap(underly ng.unret et(request))

  overr de def getDeletedT ets(
    request: GetDeletedT etsRequest
  ): Future[Seq[GetDeletedT etResult]] =
    wrap(underly ng.getDeletedT ets(request))

  overr de def deleteT ets(request: DeleteT etsRequest): Future[Seq[DeleteT etResult]] =
    wrap(underly ng.deleteT ets(request))

  overr de def updatePoss blySens  veT et(
    request: UpdatePoss blySens  veT etRequest
  ): Future[Un ] =
    wrap(underly ng.updatePoss blySens  veT et(request))

  overr de def undeleteT et(request: UndeleteT etRequest): Future[UndeleteT etResponse] =
    wrap(underly ng.undeleteT et(request))

  overr de def eraseUserT ets(request: EraseUserT etsRequest): Future[Un ] =
    wrap(underly ng.eraseUserT ets(request))

  overr de def  ncrT etFavCount(request:  ncrT etFavCountRequest): Future[Un ] =
    wrap(underly ng. ncrT etFavCount(request))

  overr de def deleteLocat onData(request: DeleteLocat onDataRequest): Future[Un ] =
    wrap(underly ng.deleteLocat onData(request))

  overr de def scrubGeo(request: GeoScrub): Future[Un ] =
    wrap(underly ng.scrubGeo(request))

  overr de def takedown(request: TakedownRequest): Future[Un ] =
    wrap(underly ng.takedown(request))

  overr de def flush(request: FlushRequest): Future[Un ] =
    wrap(underly ng.flush(request))

  overr de def  ncrT etBookmarkCount(request:  ncrT etBookmarkCountRequest): Future[Un ] =
    wrap(underly ng. ncrT etBookmarkCount(request))
}
