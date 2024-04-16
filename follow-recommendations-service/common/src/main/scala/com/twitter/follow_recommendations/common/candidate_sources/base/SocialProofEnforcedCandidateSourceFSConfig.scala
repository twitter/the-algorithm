package com.tw ter.follow_recom ndat ons.common.cand date_s ces.base

 mport com.tw ter.follow_recom ndat ons.conf gap .common.FeatureSw chConf g
 mport com.tw ter.t  l nes.conf gap .FSBoundedParam
 mport com.tw ter.t  l nes.conf gap .FSNa 
 mport com.tw ter.t  l nes.conf gap .HasDurat onConvers on
 mport com.tw ter.t  l nes.conf gap .Param
 mport com.tw ter.ut l.Durat on
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class Soc alProofEnforcedCand dateS ceFSConf g @ nject() () extends FeatureSw chConf g {
  overr de val booleanFSParams: Seq[Param[Boolean] w h FSNa ] =
    Seq(
      Soc alProofEnforcedCand dateS ceParams.MustCallSgs,
      Soc alProofEnforcedCand dateS ceParams.CallSgsCac dColumn,
    )
  overr de val  ntFSParams: Seq[FSBoundedParam[ nt]] =
    Seq(
      Soc alProofEnforcedCand dateS ceParams.Query ntersect on dsNum,
      Soc alProofEnforcedCand dateS ceParams.MaxNumCand datesToAnnotate,
      Soc alProofEnforcedCand dateS ceParams.Gfs ntersect on dsNum,
      Soc alProofEnforcedCand dateS ceParams.Sgs ntersect on dsNum,
    )

  overr de val durat onFSParams: Seq[FSBoundedParam[Durat on] w h HasDurat onConvers on] = Seq(
    Soc alProofEnforcedCand dateS ceParams.GfsLagDurat on nDays
  )
}
