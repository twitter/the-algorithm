packagelon com.twittelonr.simclustelonrsann.modulelons

import com.googlelon.injelonct.Providelons
import com.twittelonr.finaglelon.mtls.authelonntication.SelonrvicelonIdelonntifielonr
import com.twittelonr.injelonct.TwittelonrModulelon
import com.twittelonr.relonlelonvancelon_platform.simclustelonrsann.multiclustelonr.ClustelonrConfig
import com.twittelonr.relonlelonvancelon_platform.simclustelonrsann.multiclustelonr.ClustelonrConfigMappelonr
import com.twittelonr.simclustelonrsann.elonxcelonptions.MissingClustelonrConfigForSimClustelonrsAnnVariantelonxcelonption
import javax.injelonct.Singlelonton

objelonct ClustelonrConfigModulelon elonxtelonnds TwittelonrModulelon {
  @Singlelonton
  @Providelons
  delonf providelonsClustelonrConfig(
    selonrvicelonIdelonntifielonr: SelonrvicelonIdelonntifielonr,
    clustelonrConfigMappelonr: ClustelonrConfigMappelonr
  ): ClustelonrConfig = {
    val selonrvicelonNamelon = selonrvicelonIdelonntifielonr.selonrvicelon

    clustelonrConfigMappelonr.gelontClustelonrConfig(selonrvicelonNamelon) match {
      caselon Somelon(config) => config
      caselon Nonelon => throw MissingClustelonrConfigForSimClustelonrsAnnVariantelonxcelonption(selonrvicelonNamelon)
    }
  }
}
