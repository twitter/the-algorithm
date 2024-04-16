package com.tw ter.tsp.serv ce

 mport com.tw ter.abdec der.ABDec derFactory
 mport com.tw ter.abdec der.Logg ngABDec der
 mport com.tw ter.tsp.thr ftscala.TspT et nfo
 mport com.tw ter.d scovery.common.conf gap .FeatureContextBu lder
 mport com.tw ter.f nagle.mtls.aut nt cat on.Serv ce dent f er
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.g zmoduck.thr ftscala.LookupContext
 mport com.tw ter.g zmoduck.thr ftscala.QueryF elds
 mport com.tw ter.g zmoduck.thr ftscala.User
 mport com.tw ter.g zmoduck.thr ftscala.UserServ ce
 mport com.tw ter. rm .store.g zmoduck.G zmoduckUserStore
 mport com.tw ter.logg ng.Logger
 mport com.tw ter.s mclusters_v2.common.Semant cCoreEnt y d
 mport com.tw ter.s mclusters_v2.common.T et d
 mport com.tw ter.s mclusters_v2.common.User d
 mport com.tw ter.spam.rtf.thr ftscala.SafetyLevel
 mport com.tw ter.st ch.storehaus.St chOfReadableStore
 mport com.tw ter.storehaus.ReadableStore
 mport com.tw ter.strato.cl ent.{Cl ent => StratoCl ent}
 mport com.tw ter.t  l nes.conf gap 
 mport com.tw ter.t  l nes.conf gap .Compos eConf g
 mport com.tw ter.tsp.common.FeatureSw chConf g
 mport com.tw ter.tsp.common.FeatureSw c sBu lder
 mport com.tw ter.tsp.common.LoadS dder
 mport com.tw ter.tsp.common.ParamsBu lder
 mport com.tw ter.tsp.common.RecTargetFactory
 mport com.tw ter.tsp.common.Top cSoc alProofDec der
 mport com.tw ter.tsp.handlers.Top cSoc alProofHandler
 mport com.tw ter.tsp.stores.Local zedUttRecom ndableTop csStore
 mport com.tw ter.tsp.stores.Local zedUttTop cNa Request
 mport com.tw ter.tsp.stores.Top cResponses
 mport com.tw ter.tsp.stores.Top cSoc alProofStore
 mport com.tw ter.tsp.stores.Top cSoc alProofStore.Top cSoc alProof
 mport com.tw ter.tsp.stores.Top cStore
 mport com.tw ter.tsp.stores.UttTop cF lterStore
 mport com.tw ter.tsp.thr ftscala.Top cSoc alProofRequest
 mport com.tw ter.tsp.thr ftscala.Top cSoc alProofResponse
 mport com.tw ter.ut l.JavaT  r
 mport com.tw ter.ut l.T  r
 mport javax. nject. nject
 mport javax. nject.S ngleton
 mport com.tw ter.top cl st ng.Top cL st ng
 mport com.tw ter.top cl st ng.utt.UttLocal zat on

@S ngleton
class Top cSoc alProofServ ce @ nject() (
  top cSoc alProofStore: ReadableStore[Top cSoc alProofStore.Query, Seq[Top cSoc alProof]],
  t et nfoStore: ReadableStore[T et d, TspT et nfo],
  serv ce dent f er: Serv ce dent f er,
  stratoCl ent: StratoCl ent,
  g zmoduck: UserServ ce. thodPerEndpo nt,
  top cL st ng: Top cL st ng,
  uttLocal zat on: UttLocal zat on,
  dec der: Top cSoc alProofDec der,
  loadS dder: LoadS dder,
  stats: StatsRece ver) {

   mport Top cSoc alProofServ ce._

  pr vate val statsRece ver = stats.scope("top c-soc al-proof-manage nt")

  pr vate val  sProd: Boolean = serv ce dent f er.env ron nt == "prod"

  pr vate val optOutStratoStorePath: Str ng =
     f ( sProd) " nterests/optOut nterests" else " nterests/stag ng/optOut nterests"

  pr vate val not nterested nStorePath: Str ng =
     f ( sProd) " nterests/not nterestedTop csGetter"
    else " nterests/stag ng/not nterestedTop csGetter"

  pr vate val userOptOutTop csStore: ReadableStore[User d, Top cResponses] =
    Top cStore.userOptOutTop cStore(stratoCl ent, optOutStratoStorePath)(
      statsRece ver.scope(" nts_ nterests_opt_out_store"))
  pr vate val expl c Follow ngTop csStore: ReadableStore[User d, Top cResponses] =
    Top cStore.expl c Follow ngTop cStore(stratoCl ent)(
      statsRece ver.scope(" nts_expl c _follow ng_ nterests_store"))
  pr vate val userNot nterested nTop csStore: ReadableStore[User d, Top cResponses] =
    Top cStore.not nterested nTop csStore(stratoCl ent, not nterested nStorePath)(
      statsRece ver.scope(" nts_not_ nterested_ n_store"))

  pr vate lazy val local zedUttRecom ndableTop csStore: ReadableStore[
    Local zedUttTop cNa Request,
    Set[
      Semant cCoreEnt y d
    ]
  ] = new Local zedUttRecom ndableTop csStore(uttLocal zat on)

   mpl c  val t  r: T  r = new JavaT  r(true)

  pr vate lazy val uttTop cF lterStore = new UttTop cF lterStore(
    top cL st ng = top cL st ng,
    userOptOutTop csStore = userOptOutTop csStore,
    expl c Follow ngTop csStore = expl c Follow ngTop csStore,
    not nterestedTop csStore = userNot nterested nTop csStore,
    local zedUttRecom ndableTop csStore = local zedUttRecom ndableTop csStore,
    t  r = t  r,
    stats = statsRece ver.scope("UttTop cF lterStore")
  )

  pr vate lazy val scr beLogger: Opt on[Logger] = So (Logger.get("cl ent_event"))

  pr vate lazy val abDec der: Logg ngABDec der =
    ABDec derFactory(
      abDec derYmlPath = conf gRepoD rectory + "/abdec der/abdec der.yml",
      scr beLogger = scr beLogger,
      dec der = None,
      env ron nt = So ("product on"),
    ).bu ldW hLogg ng()

  pr vate val bu lder: FeatureSw c sBu lder = FeatureSw c sBu lder(
    statsRece ver = statsRece ver.scope("featuresw c s-v2"),
    abDec der = abDec der,
    featuresD rectory = "features/top c-soc al-proof/ma n",
    conf gRepoD rectory = conf gRepoD rectory,
    addServ ceDeta lsFromAurora = !serv ce dent f er. sLocal,
    fastRefresh = ! sProd
  )

  pr vate lazy val overr desConf g: conf gap .Conf g = {
    new Compos eConf g(
      Seq(
        FeatureSw chConf g.conf g
      )
    )
  }

  pr vate val featureContextBu lder: FeatureContextBu lder = FeatureContextBu lder(bu lder.bu ld())

  pr vate val paramsBu lder: ParamsBu lder = ParamsBu lder(
    featureContextBu lder,
    abDec der,
    overr desConf g,
    statsRece ver.scope("params")
  )

  pr vate val userStore: ReadableStore[User d, User] = {
    val queryF elds: Set[QueryF elds] = Set(
      QueryF elds.Prof le,
      QueryF elds.Account,
      QueryF elds.Roles,
      QueryF elds.D scoverab l y,
      QueryF elds.Safety,
      QueryF elds.Takedowns
    )
    val context: LookupContext = LookupContext(safetyLevel = So (SafetyLevel.Recom ndat ons))

    G zmoduckUserStore(
      cl ent = g zmoduck,
      queryF elds = queryF elds,
      context = context,
      statsRece ver = statsRece ver.scope("g zmoduck")
    )
  }

  pr vate val recTargetFactory: RecTargetFactory = RecTargetFactory(
    abDec der,
    userStore,
    paramsBu lder,
    statsRece ver
  )

  pr vate val top cSoc alProofHandler =
    new Top cSoc alProofHandler(
      top cSoc alProofStore,
      t et nfoStore,
      uttTop cF lterStore,
      recTargetFactory,
      dec der,
      statsRece ver.scope("Top cSoc alProofHandler"),
      loadS dder,
      t  r)

  val top cSoc alProofHandlerStoreSt ch: Top cSoc alProofRequest => com.tw ter.st ch.St ch[
    Top cSoc alProofResponse
  ] = St chOfReadableStore(top cSoc alProofHandler.toReadableStore)
}

object Top cSoc alProofServ ce {
  pr vate val conf gRepoD rectory = "/usr/local/conf g"
}
