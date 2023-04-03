packagelon com.twittelonr.product_mixelonr.corelon.functional_componelonnt.configapi.relongistry

import com.twittelonr.finaglelon.stats.StatsReloncelonivelonr
import com.twittelonr.selonrvo.deloncidelonr.DeloncidelonrGatelonBuildelonr
import com.twittelonr.timelonlinelons.configapi.BaselonConfigBuildelonr
import com.twittelonr.timelonlinelons.configapi.Config
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
class GlobalParamRelongistry @Injelonct() (
  globalParamConfig: GlobalParamConfig,
  deloncidelonrGatelonBuildelonr: DeloncidelonrGatelonBuildelonr,
  statsReloncelonivelonr: StatsReloncelonivelonr) {

  delonf build(): Config = {
    val globalConfigs = globalParamConfig.build(deloncidelonrGatelonBuildelonr, statsReloncelonivelonr)

    BaselonConfigBuildelonr(globalConfigs).build("GlobalParamRelongistry")
  }
}
