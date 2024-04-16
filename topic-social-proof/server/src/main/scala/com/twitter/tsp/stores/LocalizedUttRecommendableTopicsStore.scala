package com.tw ter.tsp.stores

 mport com.tw ter.storehaus.ReadableStore
 mport com.tw ter.top cl st ng.FollowableTop cProduct d
 mport com.tw ter.top cl st ng.Product d
 mport com.tw ter.top cl st ng.Semant cCoreEnt y d
 mport com.tw ter.top cl st ng.Top cL st ngV e rContext
 mport com.tw ter.top cl st ng.utt.UttLocal zat on
 mport com.tw ter.ut l.Future

case class Local zedUttTop cNa Request(
  product d: Product d.Value,
  v e rContext: Top cL st ngV e rContext,
  enable nternat onalTop cs: Boolean)

class Local zedUttRecom ndableTop csStore(uttLocal zat on: UttLocal zat on)
    extends ReadableStore[Local zedUttTop cNa Request, Set[Semant cCoreEnt y d]] {

  overr de def get(
    request: Local zedUttTop cNa Request
  ): Future[Opt on[Set[Semant cCoreEnt y d]]] = {
    uttLocal zat on
      .getRecom ndableTop cs(
        product d = request.product d,
        v e rContext = request.v e rContext,
        enable nternat onalTop cs = request.enable nternat onalTop cs,
        followableTop cProduct d = FollowableTop cProduct d.AllFollowable
      ).map { response => So (response) }
  }
}
