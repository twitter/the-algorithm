package com.tw ter.tsp

 mport com.google. nject.Module
 mport com.tw ter.strato.fed._
 mport com.tw ter.strato.fed.server._
 mport com.tw ter.strato.warmup.War r
 mport com.tw ter.tsp.columns.Top cSoc alProofColumn
 mport com.tw ter.tsp.columns.Top cSoc alProofBatchColumn
 mport com.tw ter.tsp.handlers.UttCh ldrenWarmupHandler
 mport com.tw ter.tsp.modules.Representat onScorerStoreModule
 mport com.tw ter.tsp.modules.G zmoduckUserModule
 mport com.tw ter.tsp.modules.TSPCl ent dModule
 mport com.tw ter.tsp.modules.Top cL st ngModule
 mport com.tw ter.tsp.modules.Top cSoc alProofStoreModule
 mport com.tw ter.tsp.modules.Top cT etCos neS m lar yAggregateStoreModule
 mport com.tw ter.tsp.modules.T et nfoStoreModule
 mport com.tw ter.tsp.modules.T etyP eCl entModule
 mport com.tw ter.tsp.modules.UttCl entModule
 mport com.tw ter.tsp.modules.UttLocal zat onModule
 mport com.tw ter.ut l.Future

object Top cSoc alProofStratoFedServerMa n extends Top cSoc alProofStratoFedServer

tra  Top cSoc alProofStratoFedServer extends StratoFedServer {
  overr de def dest: Str ng = "/s/top c-soc al-proof/top c-soc al-proof"

  overr de val modules: Seq[Module] =
    Seq(
      G zmoduckUserModule,
      Representat onScorerStoreModule,
      Top cSoc alProofStoreModule,
      Top cL st ngModule,
      Top cT etCos neS m lar yAggregateStoreModule,
      TSPCl ent dModule,
      T et nfoStoreModule,
      T etyP eCl entModule,
      UttCl entModule,
      UttLocal zat onModule
    )

  overr de def columns: Seq[Class[_ <: StratoFed.Column]] =
    Seq(
      classOf[Top cSoc alProofColumn],
      classOf[Top cSoc alProofBatchColumn]
    )

  overr de def conf gureWar r(war r: War r): Un  = {
    war r.add(
      "uttCh ldrenWarmupHandler",
      () => {
        handle[UttCh ldrenWarmupHandler]()
        Future.Un 
      }
    )
  }
}
