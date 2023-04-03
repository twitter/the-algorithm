packagelon com.twittelonr.follow_reloncommelonndations.common.rankelonrs.first_n_rankelonr

import com.twittelonr.timelonlinelons.configapi.FSBoundelondParam
import com.twittelonr.timelonlinelons.configapi.FSParam
import com.twittelonr.timelonlinelons.configapi.Param

objelonct FirstNRankelonrParams {
  caselon objelonct CandidatelonsToRank
      elonxtelonnds FSBoundelondParam[Int](
        FirstNRankelonrFelonaturelonSwitchKelonys.CandidatelonPoolSizelon,
        delonfault = 100,
        min = 50,
        max = 600)

  caselon objelonct GroupDuplicatelonCandidatelons elonxtelonnds Param[Boolelonan](truelon)
  caselon objelonct ScribelonRankingInfoInFirstNRankelonr
      elonxtelonnds FSParam[Boolelonan](FirstNRankelonrFelonaturelonSwitchKelonys.ScribelonRankingInfo, truelon)

  // thelon minimum of candidatelons to scorelon in elonach relonquelonst.
  objelonct MinNumCandidatelonsScorelondScalelonDownFactor
      elonxtelonnds FSBoundelondParam[Doublelon](
        namelon = FirstNRankelonrFelonaturelonSwitchKelonys.MinNumCandidatelonsScorelondScalelonDownFactor,
        delonfault = 0.3,
        min = 0.1,
        max = 1.0)
}
