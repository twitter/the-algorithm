packagelon com.twittelonr.follow_reloncommelonndations.common.rankelonrs.first_n_rankelonr

import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton
import com.twittelonr.follow_reloncommelonndations.configapi.common.FelonaturelonSwitchConfig
import com.twittelonr.timelonlinelons.configapi.FSBoundelondParam
import com.twittelonr.timelonlinelons.configapi.FSParam

@Singlelonton
class FirstNRankelonrFSConfig @Injelonct() elonxtelonnds FelonaturelonSwitchConfig {
  ovelonrridelon val boolelonanFSParams: Selonq[FSParam[Boolelonan]] =
    Selonq(FirstNRankelonrParams.ScribelonRankingInfoInFirstNRankelonr)

  ovelonrridelon val intFSParams: Selonq[FSBoundelondParam[Int]] = Selonq(
    FirstNRankelonrParams.CandidatelonsToRank
  )

  ovelonrridelon val doublelonFSParams: Selonq[FSBoundelondParam[Doublelon]] = Selonq(
    FirstNRankelonrParams.MinNumCandidatelonsScorelondScalelonDownFactor
  )
}
