package com.tw ter.representat onscorer.columns

 mport com.tw ter.representat onscorer.common.T et d
 mport com.tw ter.representat onscorer.common.User d
 mport com.tw ter.representat onscorer.thr ftscala.RecentEngage ntS m lar  esResponse
 mport com.tw ter.representat onscorer.tw stlyfeatures.Scorer
 mport com.tw ter.st ch
 mport com.tw ter.st ch.St ch
 mport com.tw ter.strato.catalog.Op tadata
 mport com.tw ter.strato.conf g.Contact nfo
 mport com.tw ter.strato.conf g.Pol cy
 mport com.tw ter.strato.data.Conv
 mport com.tw ter.strato.data.Descr pt on.Pla nText
 mport com.tw ter.strato.data.L fecycle
 mport com.tw ter.strato.fed._
 mport com.tw ter.strato.thr ft.ScroogeConv
 mport javax. nject. nject

class S mClustersRecentEngage ntS m lar yColumn @ nject() (scorer: Scorer)
    extends StratoFed.Column(
      "recom ndat ons/representat on_scorer/s mClustersRecentEngage ntS m lar y")
    w h StratoFed.Fetch.St ch {

  overr de val pol cy: Pol cy = Common.rsxReadPol cy

  overr de type Key = (User d, Seq[T et d])
  overr de type V ew = Un 
  overr de type Value = RecentEngage ntS m lar  esResponse

  overr de val keyConv: Conv[Key] = Conv.ofType[(Long, Seq[Long])]
  overr de val v ewConv: Conv[V ew] = Conv.ofType
  overr de val valueConv: Conv[Value] =
    ScroogeConv.fromStruct[RecentEngage ntS m lar  esResponse]

  overr de val contact nfo: Contact nfo =  nfo.contact nfo

  overr de val  tadata: Op tadata = Op tadata(
    l fecycle = So (L fecycle.Product on),
    descr pt on = So (
      Pla nText(
        "User-T et scores based on t  user's recent engage nts for mult ple t ets."
      ))
  )

  overr de def fetch(key: Key, v ew: V ew): St ch[Result[Value]] =
    scorer
      .get(key._1, key._2)
      .map(results => found(RecentEngage ntS m lar  esResponse(results)))
      .handle {
        case st ch.NotFound => m ss ng
      }
}
