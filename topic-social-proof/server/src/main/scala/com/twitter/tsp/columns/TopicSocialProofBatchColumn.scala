package com.tw ter.tsp.columns

 mport com.tw ter.st ch.SeqGroup
 mport com.tw ter.st ch.St ch
 mport com.tw ter.strato.catalog.Fetch
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
 mport com.tw ter.tsp.thr ftscala.Top cSoc alProofOpt ons
 mport com.tw ter.tsp.serv ce.Top cSoc alProofServ ce
 mport com.tw ter.tsp.thr ftscala.Top cW hScore
 mport com.tw ter.ut l.Future
 mport com.tw ter.ut l.Try
 mport javax. nject. nject

class Top cSoc alProofBatchColumn @ nject() (
  top cSoc alProofServ ce: Top cSoc alProofServ ce)
    extends StratoFed.Column(Top cSoc alProofBatchColumn.Path)
    w h StratoFed.Fetch.St ch {

  overr de val pol cy: Pol cy =
    ReadWr ePol cy(
      readPol cy = AllowAll,
      wr ePol cy = AllowKeyAut nt catedTw terUser d
    )

  overr de type Key = Long
  overr de type V ew = Top cSoc alProofOpt ons
  overr de type Value = Seq[Top cW hScore]

  overr de val keyConv: Conv[Key] = Conv.ofType
  overr de val v ewConv: Conv[V ew] = ScroogeConv.fromStruct[Top cSoc alProofOpt ons]
  overr de val valueConv: Conv[Value] = Conv.seq(ScroogeConv.fromStruct[Top cW hScore])
  overr de val  tadata: Op tadata =
    Op tadata(
      l fecycle = So (Product on),
      So (Pla nText("Top c Soc al Proof Batc d Federated Column")))

  case class TspsGroup(v ew: V ew) extends SeqGroup[Long, Fetch.Result[Value]] {
    overr de protected def run(keys: Seq[Long]): Future[Seq[Try[Result[Seq[Top cW hScore]]]]] = {
      val request = Top cSoc alProofRequest(
        user d = v ew.user d,
        t et ds = keys.toSet,
        d splayLocat on = v ew.d splayLocat on,
        top cL st ngSett ng = v ew.top cL st ngSett ng,
        context = v ew.context,
        bypassModes = v ew.bypassModes,
        tags = v ew.tags
      )

      val response = top cSoc alProofServ ce
        .top cSoc alProofHandlerStoreSt ch(request)
        .map(_.soc alProofs)
      St ch
        .run(response).map(r =>
          keys.map(key => {
            Try {
              val v = r.get(key)
               f (v.nonEmpty && v.get.nonEmpty) {
                found(v.get)
              } else {
                m ss ng
              }
            }
          }))
    }
  }

  overr de def fetch(key: Key, v ew: V ew): St ch[Result[Value]] = {
    St ch.call(key, TspsGroup(v ew))
  }
}

object Top cSoc alProofBatchColumn {
  val Path = "top c-s gnals/tsp/top c-soc al-proof-batc d"
}
