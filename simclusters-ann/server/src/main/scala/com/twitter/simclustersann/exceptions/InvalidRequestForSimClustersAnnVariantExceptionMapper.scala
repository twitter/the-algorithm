package com.tw ter.s mclustersann.except ons

 mport com.tw ter.f natra.thr ft.except ons.Except onMapper
 mport com.tw ter.f natra.thr ft.thr ftscala.Cl entError
 mport com.tw ter.f natra.thr ft.thr ftscala.Cl entErrorCause
 mport com.tw ter.ut l.Future
 mport com.tw ter.ut l.logg ng.Logg ng
 mport javax. nject.S ngleton

/**
 * An except on mapper des gned to handle
 * [[com.tw ter.s mclustersann.except ons. nval dRequestForS mClustersAnnVar antExcept on]]
 * by return ng a Thr ft  DL def ned Cl ent Error.
 */
@S ngleton
class  nval dRequestForS mClustersAnnVar antExcept onMapper
    extends Except onMapper[ nval dRequestForS mClustersAnnVar antExcept on, Noth ng]
    w h Logg ng {

  overr de def handleExcept on(
    throwable:  nval dRequestForS mClustersAnnVar antExcept on
  ): Future[Noth ng] = {
    error(" nval d Request For S mClusters Ann Var ant Except on", throwable)

    Future.except on(Cl entError(Cl entErrorCause.BadRequest, throwable.get ssage()))
  }
}
