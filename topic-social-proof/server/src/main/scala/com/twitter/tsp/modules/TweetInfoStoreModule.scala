package com.tw ter.tsp.modules

 mport com.google. nject.Module
 mport com.google. nject.Prov des
 mport com.google. nject.S ngleton
 mport com.tw ter.b ject on.scrooge.B naryScalaCodec
 mport com.tw ter.convers ons.Durat onOps._
 mport com.tw ter.f nagle. mcac d.{Cl ent =>  mCl ent}
 mport com.tw ter.f nagle.mtls.aut nt cat on.Serv ce dent f er
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.fr gate.common.store. alth.T et althModelStore
 mport com.tw ter.fr gate.common.store. alth.T et althModelStore.T et althModelStoreConf g
 mport com.tw ter.fr gate.common.store. alth.User althModelStore
 mport com.tw ter.fr gate.common.store. nterests.User d
 mport com.tw ter.fr gate.thr ftscala.T et althScores
 mport com.tw ter.fr gate.thr ftscala.UserAgathaScores
 mport com.tw ter. rm .store.common.Dec derableReadableStore
 mport com.tw ter. rm .store.common.ObservedCac dReadableStore
 mport com.tw ter. rm .store.common.Observed mcac dReadableStore
 mport com.tw ter. nject.Tw terModule
 mport com.tw ter.s mclusters_v2.common.T et d
 mport com.tw ter.st ch.t etyp e.T etyP e
 mport com.tw ter.storehaus.ReadableStore
 mport com.tw ter.strato.cl ent.{Cl ent => StratoCl ent}
 mport com.tw ter.tsp.common.Dec derKey
 mport com.tw ter.tsp.common.Top cSoc alProofDec der
 mport com.tw ter.tsp.stores.T et nfoStore
 mport com.tw ter.tsp.stores.T etyP eF eldsStore
 mport com.tw ter.t etyp e.thr ftscala.T etServ ce
 mport com.tw ter.tsp.thr ftscala.TspT et nfo
 mport com.tw ter.ut l.JavaT  r
 mport com.tw ter.ut l.T  r

object T et nfoStoreModule extends Tw terModule {
  overr de def modules: Seq[Module] = Seq(Un f edCac Cl ent)
   mpl c  val t  r: T  r = new JavaT  r(true)

  @Prov des
  @S ngleton
  def prov desT et nfoStore(
    dec der: Top cSoc alProofDec der,
    serv ce dent f er: Serv ce dent f er,
    statsRece ver: StatsRece ver,
    stratoCl ent: StratoCl ent,
    tspUn f edCac Cl ent:  mCl ent,
    t etyP eServ ce: T etServ ce. thodPerEndpo nt
  ): ReadableStore[T et d, TspT et nfo] = {
    val t et althModelStore: ReadableStore[T et d, T et althScores] = {
      val underly ngStore = T et althModelStore.bu ldReadableStore(
        stratoCl ent,
        So (
          T et althModelStoreConf g(
            enablePBlock = true,
            enableTox c y = true,
            enablePSpam  = true,
            enablePReported = true,
            enableSpam T etContent = true,
            enablePNegMult modal = false))
      )(statsRece ver.scope("Underly ngT et althModelStore"))

      Dec derableReadableStore(
        Observed mcac dReadableStore.fromCac Cl ent(
          back ngStore = underly ngStore,
          cac Cl ent = tspUn f edCac Cl ent,
          ttl = 2.h s
        )(
          value nject on = B naryScalaCodec(T et althScores),
          statsRece ver = statsRece ver.scope("T et althModelStore"),
          keyToStr ng = { k: T et d => s"tHMS/$k" }
        ),
        dec der.dec derGateBu lder. dGate(Dec derKey.enable althS gnalsScoreDec derKey),
        statsRece ver.scope("T et althModelStore")
      )
    }

    val user althModelStore: ReadableStore[User d, UserAgathaScores] = {
      val underly ngStore =
        User althModelStore.bu ldReadableStore(stratoCl ent)(
          statsRece ver.scope("Underly ngUser althModelStore"))

      Dec derableReadableStore(
        Observed mcac dReadableStore.fromCac Cl ent(
          back ngStore = underly ngStore,
          cac Cl ent = tspUn f edCac Cl ent,
          ttl = 18.h s
        )(
          value nject on = B naryScalaCodec(UserAgathaScores),
          statsRece ver = statsRece ver.scope("User althModelStore"),
          keyToStr ng = { k: User d => s"uHMS/$k" }
        ),
        dec der.dec derGateBu lder. dGate(Dec derKey.enableUserAgathaScoreDec derKey),
        statsRece ver.scope("User althModelStore")
      )
    }

    val t et nfoStore: ReadableStore[T et d, TspT et nfo] = {
      val underly ngStore = T et nfoStore(
        T etyP eF eldsStore.getStoreFromT etyP e(T etyP e(t etyP eServ ce, statsRece ver)),
        t et althModelStore: ReadableStore[T et d, T et althScores],
        user althModelStore: ReadableStore[User d, UserAgathaScores],
        t  r: T  r
      )(statsRece ver.scope("t et nfoStore"))

      val  mcac dStore = Observed mcac dReadableStore.fromCac Cl ent(
        back ngStore = underly ngStore,
        cac Cl ent = tspUn f edCac Cl ent,
        ttl = 15.m nutes,
        // Hydrat ng t et nfo  s now a requ red step for all cand dates,
        //  nce   needed to tune t se thresholds.
        asyncUpdate = serv ce dent f er.env ron nt == "prod"
      )(
        value nject on = B naryScalaCodec(TspT et nfo),
        statsRece ver = statsRece ver.scope(" mCac dT et nfoStore"),
        keyToStr ng = { k: T et d => s"t S/$k" }
      )

      val  n moryStore = ObservedCac dReadableStore.from(
         mcac dStore,
        ttl = 15.m nutes,
        maxKeys = 8388607, // C ck T et nfo def n  on. s ze~92b. Around 736 MB
        w ndowS ze = 10000L,
        cac Na  = "t et_ nfo_cac ",
        maxMult GetS ze = 20
      )(statsRece ver.scope(" n moryCac dT et nfoStore"))

       n moryStore
    }
    t et nfoStore
  }
}
