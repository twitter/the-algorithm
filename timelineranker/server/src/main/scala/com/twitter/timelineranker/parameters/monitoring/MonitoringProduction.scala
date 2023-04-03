packagelon com.twittelonr.timelonlinelonrankelonr.paramelontelonrs.monitoring

import com.twittelonr.timelonlinelons.configapi.BaselonConfigBuildelonr
import com.twittelonr.timelonlinelonrankelonr.paramelontelonrs.monitoring.MonitoringParams.DelonbugAuthorsAllowListParam
import com.twittelonr.timelonlinelons.configapi.FelonaturelonSwitchOvelonrridelonUtil

objelonct MonitoringProduction {
  privatelon val longSelonqOvelonrridelons =
    FelonaturelonSwitchOvelonrridelonUtil.gelontLongSelonqFSOvelonrridelons(DelonbugAuthorsAllowListParam)

  val config = BaselonConfigBuildelonr()
    .selont(longSelonqOvelonrridelons: _*)
    .build()
}
