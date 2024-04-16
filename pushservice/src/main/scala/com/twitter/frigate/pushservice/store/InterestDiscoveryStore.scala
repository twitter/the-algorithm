package com.tw ter.fr gate.pushserv ce.store

 mport com.tw ter. nterests_d scovery.thr ftscala. nterestsD scoveryServ ce
 mport com.tw ter. nterests_d scovery.thr ftscala.Recom ndedL stsRequest
 mport com.tw ter. nterests_d scovery.thr ftscala.Recom ndedL stsResponse
 mport com.tw ter.storehaus.ReadableStore
 mport com.tw ter.ut l.Future

case class  nterestD scoveryStore(
  cl ent:  nterestsD scoveryServ ce. thodPerEndpo nt)
    extends ReadableStore[Recom ndedL stsRequest, Recom ndedL stsResponse] {

  overr de def get(request: Recom ndedL stsRequest): Future[Opt on[Recom ndedL stsResponse]] = {
    cl ent.getL stRecos(request).map(So (_))
  }
}
