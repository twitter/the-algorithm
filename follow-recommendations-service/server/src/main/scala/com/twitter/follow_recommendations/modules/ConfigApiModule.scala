packagelon com.twittelonr.follow_reloncommelonndations.modulelons

import com.googlelon.injelonct.Providelons
import com.twittelonr.deloncidelonr.Deloncidelonr
import com.twittelonr.follow_reloncommelonndations.configapi.ConfigBuildelonr
import com.twittelonr.injelonct.TwittelonrModulelon
import com.twittelonr.selonrvo.deloncidelonr.DeloncidelonrGatelonBuildelonr
import com.twittelonr.timelonlinelons.configapi.Config
import javax.injelonct.Singlelonton

objelonct ConfigApiModulelon elonxtelonnds TwittelonrModulelon {
  @Providelons
  @Singlelonton
  delonf providelonsDeloncidelonrGatelonBuildelonr(deloncidelonr: Deloncidelonr): DeloncidelonrGatelonBuildelonr =
    nelonw DeloncidelonrGatelonBuildelonr(deloncidelonr)

  @Providelons
  @Singlelonton
  delonf providelonsConfig(configBuildelonr: ConfigBuildelonr): Config = configBuildelonr.build()
}
