packagelon com.twittelonr.follow_reloncommelonndations.common.candidatelon_sourcelons.baselon

import com.twittelonr.follow_reloncommelonndations.configapi.common.FelonaturelonSwitchConfig
import com.twittelonr.timelonlinelons.configapi.FSBoundelondParam
import com.twittelonr.timelonlinelons.configapi.FSNamelon
import com.twittelonr.timelonlinelons.configapi.HasDurationConvelonrsion
import com.twittelonr.timelonlinelons.configapi.Param
import com.twittelonr.util.Duration
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
class SocialProofelonnforcelondCandidatelonSourcelonFSConfig @Injelonct() () elonxtelonnds FelonaturelonSwitchConfig {
  ovelonrridelon val boolelonanFSParams: Selonq[Param[Boolelonan] with FSNamelon] =
    Selonq(
      SocialProofelonnforcelondCandidatelonSourcelonParams.MustCallSgs,
      SocialProofelonnforcelondCandidatelonSourcelonParams.CallSgsCachelondColumn,
    )
  ovelonrridelon val intFSParams: Selonq[FSBoundelondParam[Int]] =
    Selonq(
      SocialProofelonnforcelondCandidatelonSourcelonParams.QuelonryIntelonrselonctionIdsNum,
      SocialProofelonnforcelondCandidatelonSourcelonParams.MaxNumCandidatelonsToAnnotatelon,
      SocialProofelonnforcelondCandidatelonSourcelonParams.GfsIntelonrselonctionIdsNum,
      SocialProofelonnforcelondCandidatelonSourcelonParams.SgsIntelonrselonctionIdsNum,
    )

  ovelonrridelon val durationFSParams: Selonq[FSBoundelondParam[Duration] with HasDurationConvelonrsion] = Selonq(
    SocialProofelonnforcelondCandidatelonSourcelonParams.GfsLagDurationInDays
  )
}
