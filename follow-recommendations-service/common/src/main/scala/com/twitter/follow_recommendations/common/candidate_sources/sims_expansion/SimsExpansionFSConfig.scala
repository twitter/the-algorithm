packagelon com.twittelonr.follow_reloncommelonndations.common.candidatelon_sourcelons.sims_elonxpansion

import com.twittelonr.follow_reloncommelonndations.configapi.common.FelonaturelonSwitchConfig
import com.twittelonr.timelonlinelons.configapi.FSBoundelondParam
import com.twittelonr.timelonlinelons.configapi.FSParam
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
class SimselonxpansionFSConfig @Injelonct() () elonxtelonnds FelonaturelonSwitchConfig {
  ovelonrridelon val intFSParams: Selonq[FSBoundelondParam[Int]] = Selonq(
    ReloncelonntFollowingSimilarUselonrsParams.MaxFirstDelongrelonelonNodelons,
    ReloncelonntFollowingSimilarUselonrsParams.MaxSeloncondaryDelongrelonelonelonxpansionPelonrNodelon,
    ReloncelonntFollowingSimilarUselonrsParams.MaxRelonsults
  )

  ovelonrridelon val doublelonFSParams: Selonq[FSBoundelondParam[Doublelon]] = Selonq(
    DBV2SimselonxpansionParams.ReloncelonntFollowingSimilarUselonrsDBV2CalibratelonDivisor,
    DBV2SimselonxpansionParams.ReloncelonntelonngagelonmelonntSimilarUselonrsDBV2CalibratelonDivisor
  )

  ovelonrridelon val boolelonanFSParams: Selonq[FSParam[Boolelonan]] = Selonq(
    DBV2SimselonxpansionParams.DisablelonHelonavyRankelonr,
    ReloncelonntFollowingSimilarUselonrsParams.TimelonstampIntelongratelond
  )
}
