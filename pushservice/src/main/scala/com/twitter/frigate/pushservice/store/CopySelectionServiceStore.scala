package com.tw ter.fr gate.pushserv ce.store

 mport com.tw ter.copyselect onserv ce.thr ftscala._
 mport com.tw ter.storehaus.ReadableStore
 mport com.tw ter.ut l.Future

class CopySelect onServ ceStore(copySelect onServ ceCl ent: CopySelect onServ ce.F nagledCl ent)
    extends ReadableStore[CopySelect onRequestV1, Copy] {
  overr de def get(k: CopySelect onRequestV1): Future[Opt on[Copy]] =
    copySelect onServ ceCl ent.getSelectedCopy(CopySelect onRequest.V1(k)).map {
      case CopySelect onResponse.V1(response) =>
        So (response.selectedCopy)
      case _ => throw CopyServ ceExcept on(CopyServ ceErrorCode.Vers onNotFound)
    }
}
