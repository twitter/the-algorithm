packagelon com.twittelonr.product_mixelonr.corelon.product

import com.twittelonr.finaglelon.stats.StatsReloncelonivelonr
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.configapi.relongistry.ParamConfigBuildelonr
import com.twittelonr.selonrvo.deloncidelonr.DeloncidelonrGatelonBuildelonr
import com.twittelonr.timelonlinelons.configapi.FelonaturelonSwitchOvelonrridelonUtil
import com.twittelonr.timelonlinelons.configapi.OptionalOvelonrridelon
import com.twittelonr.timelonlinelons.configapi.deloncidelonr.DeloncidelonrUtils

trait ProductParamConfigBuildelonr elonxtelonnds ParamConfigBuildelonr {
  productParamConfig: ProductParamConfig =>

  ovelonrridelon delonf build(
    deloncidelonrGatelonBuildelonr: DeloncidelonrGatelonBuildelonr,
    statsReloncelonivelonr: StatsReloncelonivelonr
  ): Selonq[OptionalOvelonrridelon[_]] = {
    DeloncidelonrUtils.gelontBoolelonanDeloncidelonrOvelonrridelons(deloncidelonrGatelonBuildelonr, elonnablelondDeloncidelonrParam) ++
      FelonaturelonSwitchOvelonrridelonUtil.gelontBoolelonanFSOvelonrridelons(SupportelondClielonntParam) ++
      supelonr.build(deloncidelonrGatelonBuildelonr, statsReloncelonivelonr)
  }
}
