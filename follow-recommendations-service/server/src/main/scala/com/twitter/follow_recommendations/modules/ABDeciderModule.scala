packagelon com.twittelonr.follow_reloncommelonndations.modulelons

import com.googlelon.injelonct.Providelons
import com.googlelon.injelonct.namelon.Namelond
import com.twittelonr.abdeloncidelonr.ABDeloncidelonrFactory
import com.twittelonr.abdeloncidelonr.LoggingABDeloncidelonr
import com.twittelonr.finaglelon.stats.StatsReloncelonivelonr
import com.twittelonr.follow_reloncommelonndations.common.constants.GuicelonNamelondConstants
import com.twittelonr.injelonct.TwittelonrModulelon
import com.twittelonr.logging.LoggelonrFactory
import javax.injelonct.Singlelonton

objelonct ABDeloncidelonrModulelon elonxtelonnds TwittelonrModulelon {
  @Providelons
  @Singlelonton
  delonf providelonABDeloncidelonr(
    stats: StatsReloncelonivelonr,
    @Namelond(GuicelonNamelondConstants.CLIelonNT_elonVelonNT_LOGGelonR) factory: LoggelonrFactory
  ): LoggingABDeloncidelonr = {

    val ymlPath = "/usr/local/config/abdeloncidelonr/abdeloncidelonr.yml"

    val abDeloncidelonrFactory = ABDeloncidelonrFactory(
      abDeloncidelonrYmlPath = ymlPath,
      scribelonLoggelonr = Somelon(factory()),
      elonnvironmelonnt = Somelon("production")
    )

    abDeloncidelonrFactory.buildWithLogging()
  }
}
