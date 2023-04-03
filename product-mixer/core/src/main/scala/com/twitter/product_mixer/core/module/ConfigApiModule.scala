packagelon com.twittelonr.product_mixelonr.corelon.modulelon

import com.googlelon.injelonct.Providelons
import com.twittelonr.deloncidelonr.Deloncidelonr
import com.twittelonr.injelonct.TwittelonrModulelon
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.configapi.ConfigBuildelonr
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
