package com.tw ter.tsp.columns

 mport com.tw ter.st ch
 mport com.tw ter.st ch.St ch
 mport com.tw ter.strato.catalog.Op tadata
 mport com.tw ter.strato.conf g._
 mport com.tw ter.strato.conf g.AllowAll
 mport com.tw ter.strato.conf g.Contact nfo
 mport com.tw ter.strato.conf g.Pol cy
 mport com.tw ter.strato.data.Conv
 mport com.tw ter.strato.data.Descr pt on.Pla nText
 mport com.tw ter.strato.data.L fecycle.Product on
 mport com.tw ter.strato.fed.StratoFed
 mport com.tw ter.strato.thr ft.ScroogeConv
 mport com.tw ter.tsp.thr ftscala.Top cSoc alProofRequest
 mport com.tw ter.tsp.thr ftscala.Top cSoc alProofResponse
 mport com.tw ter.tsp.serv ce.Top cSoc alProofServ ce
 mport javax. nject. nject

class Top cSoc alProofColumn @ nject() (
  top cSoc alProofServ ce: Top cSoc alProofServ ce)
    extends StratoFed.Column(Top cSoc alProofColumn.Path)
    w h StratoFed.Fetch.St ch {

  overr de type Key = Top cSoc alProofRequest
  overr de type V ew = Un 
  overr de type Value = Top cSoc alProofResponse

  overr de val keyConv: Conv[Key] = ScroogeConv.fromStruct[Top cSoc alProofRequest]
  overr de val v ewConv: Conv[V ew] = Conv.ofType
  overr de val valueConv: Conv[Value] = ScroogeConv.fromStruct[Top cSoc alProofResponse]
  overr de val  tadata: Op tadata =
    Op tadata(l fecycle = So (Product on), So (Pla nText("Top c Soc al Proof Federated Column")))

  overr de def fetch(key: Key, v ew: V ew): St ch[Result[Value]] = {
    top cSoc alProofServ ce
      .top cSoc alProofHandlerStoreSt ch(key)
      .map { result => found(result) }
      .handle {
        case st ch.NotFound => m ss ng
      }
  }
}

object Top cSoc alProofColumn {
  val Path = "top c-s gnals/tsp/top c-soc al-proof"
}
