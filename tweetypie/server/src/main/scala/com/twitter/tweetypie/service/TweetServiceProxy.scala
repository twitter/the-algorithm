/** Copyr ght 2012 Tw ter,  nc. */
package com.tw ter.t etyp e
package serv ce

 mport com.tw ter.f nagle.thr ft.Cl ent d
 mport com.tw ter.t etyp e.thr ftscala.{T etServ ceProxy => BaseT etServ ceProxy, _}

/**
 * A tra  for T etServ ce  mple ntat ons that wrap an underly ng T etServ ce and need to mod fy
 * only so  of t   thods.
 *
 * T  proxy  s t  sa  as [[com.tw ter.t etyp e.thr ftscala.T etServ ceProxy]], except   also
 * extends [[com.tw ter.t etyp e.thr ftscala.T etServ ce nternal]] wh ch g ves us access to all
 * of t  async*  thods.
 */
tra  T etServ ceProxy extends BaseT etServ ceProxy w h Thr ftT etServ ce {
  protected overr de def underly ng: Thr ftT etServ ce

  overr de def repl catedGetT etCounts(request: GetT etCountsRequest): Future[Un ] =
    wrap(underly ng.repl catedGetT etCounts(request))

  overr de def repl catedGetT etF elds(request: GetT etF eldsRequest): Future[Un ] =
    wrap(underly ng.repl catedGetT etF elds(request))

  overr de def repl catedGetT ets(request: GetT etsRequest): Future[Un ] =
    wrap(underly ng.repl catedGetT ets(request))

  overr de def asyncSetAdd  onalF elds(request: AsyncSetAdd  onalF eldsRequest): Future[Un ] =
    wrap(underly ng.asyncSetAdd  onalF elds(request))

  overr de def asyncDeleteAdd  onalF elds(
    request: AsyncDeleteAdd  onalF eldsRequest
  ): Future[Un ] =
    wrap(underly ng.asyncDeleteAdd  onalF elds(request))

  overr de def cascadedDeleteT et(request: CascadedDeleteT etRequest): Future[Un ] =
    wrap(underly ng.cascadedDeleteT et(request))

  overr de def async nsert(request: Async nsertRequest): Future[Un ] =
    wrap(underly ng.async nsert(request))

  overr de def repl catedUpdatePoss blySens  veT et(t et: T et): Future[Un ] =
    wrap(underly ng.repl catedUpdatePoss blySens  veT et(t et))

  overr de def asyncUpdatePoss blySens  veT et(
    request: AsyncUpdatePoss blySens  veT etRequest
  ): Future[Un ] =
    wrap(underly ng.asyncUpdatePoss blySens  veT et(request))

  overr de def asyncUndeleteT et(request: AsyncUndeleteT etRequest): Future[Un ] =
    wrap(underly ng.asyncUndeleteT et(request))

  overr de def eraseUserT ets(request: EraseUserT etsRequest): Future[Un ] =
    wrap(underly ng.eraseUserT ets(request))

  overr de def asyncEraseUserT ets(request: AsyncEraseUserT etsRequest): Future[Un ] =
    wrap(underly ng.asyncEraseUserT ets(request))

  overr de def asyncDelete(request: AsyncDeleteRequest): Future[Un ] =
    wrap(underly ng.asyncDelete(request))

  overr de def async ncrFavCount(request: Async ncrFavCountRequest): Future[Un ] =
    wrap(underly ng.async ncrFavCount(request))

  overr de def async ncrBookmarkCount(request: Async ncrBookmarkCountRequest): Future[Un ] =
    wrap(underly ng.async ncrBookmarkCount(request))

  overr de def scrubGeoUpdateUserT  stamp(request: DeleteLocat onData): Future[Un ] =
    wrap(underly ng.scrubGeoUpdateUserT  stamp(request))

  overr de def asyncSetRet etV s b l y(request: AsyncSetRet etV s b l yRequest): Future[Un ] =
    wrap(underly ng.asyncSetRet etV s b l y(request))

  overr de def setRet etV s b l y(request: SetRet etV s b l yRequest): Future[Un ] =
    wrap(underly ng.setRet etV s b l y(request))

  overr de def asyncTakedown(request: AsyncTakedownRequest): Future[Un ] =
    wrap(underly ng.asyncTakedown(request))

  overr de def setT etUserTakedown(request: SetT etUserTakedownRequest): Future[Un ] =
    wrap(underly ng.setT etUserTakedown(request))

  overr de def repl catedUndeleteT et2(request: Repl catedUndeleteT et2Request): Future[Un ] =
    wrap(underly ng.repl catedUndeleteT et2(request))

  overr de def repl cated nsertT et2(request: Repl cated nsertT et2Request): Future[Un ] =
    wrap(underly ng.repl cated nsertT et2(request))

  overr de def repl catedDeleteT et2(request: Repl catedDeleteT et2Request): Future[Un ] =
    wrap(underly ng.repl catedDeleteT et2(request))

  overr de def repl cated ncrFavCount(t et d: T et d, delta:  nt): Future[Un ] =
    wrap(underly ng.repl cated ncrFavCount(t et d, delta))

  overr de def repl cated ncrBookmarkCount(t et d: T et d, delta:  nt): Future[Un ] =
    wrap(underly ng.repl cated ncrBookmarkCount(t et d, delta))

  overr de def repl catedSetRet etV s b l y(
    request: Repl catedSetRet etV s b l yRequest
  ): Future[Un ] =
    wrap(underly ng.repl catedSetRet etV s b l y(request))

  overr de def repl catedScrubGeo(t et ds: Seq[T et d]): Future[Un ] =
    wrap(underly ng.repl catedScrubGeo(t et ds))

  overr de def repl catedSetAdd  onalF elds(request: SetAdd  onalF eldsRequest): Future[Un ] =
    wrap(underly ng.repl catedSetAdd  onalF elds(request))

  overr de def repl catedDeleteAdd  onalF elds(
    request: Repl catedDeleteAdd  onalF eldsRequest
  ): Future[Un ] =
    wrap(underly ng.repl catedDeleteAdd  onalF elds(request))

  overr de def repl catedTakedown(t et: T et): Future[Un ] =
    wrap(underly ng.repl catedTakedown(t et))

  overr de def quotedT etDelete(request: QuotedT etDeleteRequest): Future[Un ] =
    wrap(underly ng.quotedT etDelete(request))

  overr de def quotedT etTakedown(request: QuotedT etTakedownRequest): Future[Un ] =
    wrap(underly ng.quotedT etTakedown(request))

  overr de def getStoredT ets(
    request: GetStoredT etsRequest
  ): Future[Seq[GetStoredT etsResult]] =
    wrap(underly ng.getStoredT ets(request))

  overr de def getStoredT etsByUser(
    request: GetStoredT etsByUserRequest
  ): Future[GetStoredT etsByUserResult] =
    wrap(underly ng.getStoredT etsByUser(request))
}

/**
 * A T etServ ceProxy w h a mutable underly ng f eld.
 */
class MutableT etServ ceProxy(var underly ng: Thr ftT etServ ce) extends T etServ ceProxy

/**
 * A T etServ ceProxy that sets t  Cl ent d context before execut ng t   thod.
 */
class Cl ent dSett ngT etServ ceProxy(cl ent d: Cl ent d, val underly ng: Thr ftT etServ ce)
    extends T etServ ceProxy {
  overr de def wrap[A](f: => Future[A]): Future[A] =
    cl ent d.asCurrent(f)
}
