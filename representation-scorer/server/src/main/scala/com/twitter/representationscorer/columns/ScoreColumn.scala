package com.tw ter.representat onscorer.columns

 mport com.tw ter.contentrecom nder.thr ftscala.Scor ngResponse
 mport com.tw ter.representat onscorer.scorestore.ScoreStore
 mport com.tw ter.s mclusters_v2.thr ftscala.Score d
 mport com.tw ter.st ch
 mport com.tw ter.st ch.St ch
 mport com.tw ter.strato.conf g.Contact nfo
 mport com.tw ter.strato.conf g.Pol cy
 mport com.tw ter.strato.catalog.Op tadata
 mport com.tw ter.strato.data.Conv
 mport com.tw ter.strato.data.L fecycle
 mport com.tw ter.strato.data.Descr pt on.Pla nText
 mport com.tw ter.strato.fed._
 mport com.tw ter.strato.thr ft.ScroogeConv
 mport javax. nject. nject

class ScoreColumn @ nject() (scoreStore: ScoreStore)
    extends StratoFed.Column("recom ndat ons/representat on_scorer/score")
    w h StratoFed.Fetch.St ch {

  overr de val pol cy: Pol cy = Common.rsxReadPol cy

  overr de type Key = Score d
  overr de type V ew = Un 
  overr de type Value = Scor ngResponse

  overr de val keyConv: Conv[Key] = ScroogeConv.fromStruct[Score d]
  overr de val v ewConv: Conv[V ew] = Conv.ofType
  overr de val valueConv: Conv[Value] = ScroogeConv.fromStruct[Scor ngResponse]

  overr de val contact nfo: Contact nfo =  nfo.contact nfo

  overr de val  tadata: Op tadata = Op tadata(
    l fecycle = So (L fecycle.Product on),
    descr pt on = So (Pla nText(
      "T  Un form Scor ng Endpo nt  n Representat on Scorer for t  Content-Recom nder." +
        " TDD: http://go/representat on-scorer-tdd Gu del ne: http://go/un form-scor ng-gu del ne"))
  )

  overr de def fetch(key: Key, v ew: V ew): St ch[Result[Value]] =
    scoreStore
      .un formScor ngStoreSt ch(key)
      .map(score => found(Scor ngResponse(So (score))))
      .handle {
        case st ch.NotFound => m ss ng
      }
}
