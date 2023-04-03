packagelon com.twittelonr.follow_reloncommelonndations.configapi.candidatelons

import com.twittelonr.finaglelon.stats.StatsReloncelonivelonr
import com.twittelonr.follow_reloncommelonndations.common.modelonls.CandidatelonUselonr
import com.twittelonr.follow_reloncommelonndations.common.modelonls.HasDisplayLocation
import com.twittelonr.follow_reloncommelonndations.configapi.params.GlobalParams
import com.twittelonr.selonrvo.util.MelonmoizingStatsReloncelonivelonr
import com.twittelonr.timelonlinelons.configapi.Config
import com.twittelonr.timelonlinelons.configapi.HasParams
import com.twittelonr.timelonlinelons.configapi.Params
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

/**
 * CandidatelonParamsFactory is primarily uselond for "producelonr sidelon" elonxpelonrimelonnts, don't uselon it on consumelonr sidelon elonxpelonrimelonnts
 */
@Singlelonton
class CandidatelonUselonrParamsFactory[T <: HasParams with HasDisplayLocation] @Injelonct() (
  config: Config,
  candidatelonContelonxtFactory: CandidatelonUselonrContelonxtFactory,
  statsReloncelonivelonr: StatsReloncelonivelonr) {
  privatelon val stats = nelonw MelonmoizingStatsReloncelonivelonr(statsReloncelonivelonr.scopelon("configapi_candidatelon_params"))
  delonf apply(candidatelonContelonxt: CandidatelonUselonr, relonquelonst: T): CandidatelonUselonr = {
    if (candidatelonContelonxt.params == Params.Invalid) {
      if (relonquelonst.params(GlobalParams.elonnablelonCandidatelonParamHydrations)) {
        candidatelonContelonxt.copy(params =
          config(candidatelonContelonxtFactory(candidatelonContelonxt, relonquelonst.displayLocation), stats))
      } elonlselon {
        candidatelonContelonxt.copy(params = Params.elonmpty)
      }
    } elonlselon {
      candidatelonContelonxt
    }
  }
}
