package com.tw ter.ho _m xer.module

 mport com.tw ter.convers ons.Durat onOps.R chDurat on
 mport com.tw ter.ho _m xer.param.Ho M xerFlagNa 
 mport com.tw ter. nject.Tw terModule
 mport com.tw ter.ut l.Durat on

object Ho M xerFlagsModule extends Tw terModule {

   mport Ho M xerFlagNa ._

  flag[Boolean](
    na  = Scr beCl entEventsFlag,
    default = false,
     lp = "Toggles logg ng cl ent events to Scr be"
  )

  flag[Boolean](
    na  = Scr beServedCand datesFlag,
    default = false,
     lp = "Toggles logg ng served cand dates to Scr be"
  )

  flag[Boolean](
    na  = Scr beScoredCand datesFlag,
    default = false,
     lp = "Toggles logg ng scored cand dates to Scr be"
  )

  flag[Boolean](
    na  = Scr beServedCommonFeaturesAndCand dateFeaturesFlag,
    default = false,
     lp = "Toggles logg ng served common features and cand dates features to Scr be"
  )

  flag[Str ng](
    na  = DataRecord tadataStoreConf gsYmlFlag,
    default = "",
     lp = "T  YML f le that conta ns t  necessary  nfo for creat ng  tadata store  SQL cl ent."
  )

  flag[Str ng](
    na  = DarkTraff cF lterDec derKey,
    default = "dark_traff c_f lter",
     lp = "Dark traff c f lter dec der key"
  )

  flag[Durat on](
    TargetFetchLatency,
    300.m ll s,
    "Target fetch latency from cand date s ces for Qual y Factor"
  )

  flag[Durat on](
    TargetScor ngLatency,
    700.m ll s,
    "Target scor ng latency for Qual y Factor"
  )
}
