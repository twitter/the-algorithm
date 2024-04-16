package com.tw ter.users gnalserv ce.columns

 mport com.tw ter.st ch.NotFound
 mport com.tw ter.st ch.St ch
 mport com.tw ter.strato.catalog.Op tadata
 mport com.tw ter.strato.catalog.Ops
 mport com.tw ter.strato.conf g.Pol cy
 mport com.tw ter.strato.conf g.ReadWr ePol cy
 mport com.tw ter.strato.data.Conv
 mport com.tw ter.strato.data.Descr pt on
 mport com.tw ter.strato.data.L fecycle
 mport com.tw ter.strato.fed.StratoFed
 mport com.tw ter.strato.thr ft.ScroogeConv
 mport com.tw ter.users gnalserv ce.serv ce.UserS gnalServ ce
 mport com.tw ter.users gnalserv ce.thr ftscala.BatchS gnalRequest
 mport com.tw ter.users gnalserv ce.thr ftscala.BatchS gnalResponse
 mport javax. nject. nject

class UserS gnalServ ceColumn @ nject() (userS gnalServ ce: UserS gnalServ ce)
    extends StratoFed.Column(UserS gnalServ ceColumn.Path)
    w h StratoFed.Fetch.St ch {

  overr de val  tadata: Op tadata = Op tadata(
    l fecycle = So (L fecycle.Product on),
    descr pt on = So (Descr pt on.Pla nText("User S gnal Serv ce Federated Column")))

  overr de def ops: Ops = super.ops

  overr de type Key = BatchS gnalRequest
  overr de type V ew = Un 
  overr de type Value = BatchS gnalResponse

  overr de val keyConv: Conv[Key] = ScroogeConv.fromStruct[BatchS gnalRequest]
  overr de val v ewConv: Conv[V ew] = Conv.ofType
  overr de val valueConv: Conv[Value] = ScroogeConv.fromStruct[BatchS gnalResponse]

  overr de def fetch(key: Key, v ew: V ew): St ch[Result[Value]] = {
    userS gnalServ ce
      .userS gnalServ ceHandlerStoreSt ch(key)
      .map(result => found(result))
      .handle {
        case NotFound => m ss ng
      }
  }
}

object UserS gnalServ ceColumn {
  val Path = "recom ndat ons/user-s gnal-serv ce/s gnals"
}
