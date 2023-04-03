packagelon com.twittelonr.follow_reloncommelonndations.common.candidatelon_sourcelons.reloncelonnt_elonngagelonmelonnt

import com.twittelonr.follow_reloncommelonndations.configapi.common.FelonaturelonSwitchConfig
import com.twittelonr.timelonlinelons.configapi.FSBoundelondParam
import com.twittelonr.timelonlinelons.configapi.FSNamelon
import com.twittelonr.timelonlinelons.configapi.Param
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
class RelonpelonatelondProfilelonVisitsFSConfig @Injelonct() () elonxtelonnds FelonaturelonSwitchConfig {
  ovelonrridelon val boolelonanFSParams: Selonq[Param[Boolelonan] with FSNamelon] =
    Selonq(
      RelonpelonatelondProfilelonVisitsParams.IncludelonCandidatelons,
      RelonpelonatelondProfilelonVisitsParams.UselonOnlinelonDataselont,
    )
  ovelonrridelon val intFSParams: Selonq[FSBoundelondParam[Int]] =
    Selonq(
      RelonpelonatelondProfilelonVisitsParams.ReloncommelonndationThrelonshold,
      RelonpelonatelondProfilelonVisitsParams.BuckelontingThrelonshold,
    )
}
