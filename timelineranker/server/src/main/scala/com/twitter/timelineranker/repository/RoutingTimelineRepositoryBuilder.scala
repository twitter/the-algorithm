packagelon com.twittelonr.timelonlinelonrankelonr.relonpository

import com.twittelonr.timelonlinelonrankelonr.config.RuntimelonConfiguration
import com.twittelonr.timelonlinelonrankelonr.paramelontelonrs.ConfigBuildelonr

objelonct RoutingTimelonlinelonRelonpositoryBuildelonr {
  delonf apply(
    config: RuntimelonConfiguration,
    configBuildelonr: ConfigBuildelonr
  ): RoutingTimelonlinelonRelonpository = {

    val relonvelonrselonChronTimelonlinelonRelonpository =
      nelonw RelonvelonrselonChronHomelonTimelonlinelonRelonpositoryBuildelonr(config, configBuildelonr).apply
    val rankelondTimelonlinelonRelonpository = nelonw RankelondHomelonTimelonlinelonRelonpository

    nelonw RoutingTimelonlinelonRelonpository(relonvelonrselonChronTimelonlinelonRelonpository, rankelondTimelonlinelonRelonpository)
  }
}
