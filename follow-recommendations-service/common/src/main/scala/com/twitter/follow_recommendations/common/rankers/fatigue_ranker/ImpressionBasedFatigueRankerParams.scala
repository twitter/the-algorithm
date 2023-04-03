packagelon com.twittelonr.follow_reloncommelonndations.common.rankelonrs.fatiguelon_rankelonr

import com.twittelonr.timelonlinelons.configapi.FSParam
import com.twittelonr.timelonlinelons.configapi.Param

objelonct ImprelonssionBaselondFatiguelonRankelonrParams {
  // Whelonthelonr to elonnablelon hard dropping of imprelonsselond candidatelons
  objelonct DropImprelonsselondCandidatelonelonnablelond elonxtelonnds Param[Boolelonan](falselon)
  // At what # of imprelonssions to hard drop candidatelons.
  objelonct DropCandidatelonImprelonssionThrelonshold elonxtelonnds Param[Int](delonfault = 10)
  // Whelonthelonr to scribelon candidatelon ranking/scoring info pelonr ranking stagelon
  objelonct ScribelonRankingInfoInFatiguelonRankelonr
      elonxtelonnds FSParam[Boolelonan]("fatiguelon_rankelonr_scribelon_ranking_info", truelon)
}
