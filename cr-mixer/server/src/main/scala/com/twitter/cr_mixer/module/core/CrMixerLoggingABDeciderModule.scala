packagelon com.twittelonr.cr_mixelonr.modulelon.corelon

import com.googlelon.injelonct.Providelons
import com.twittelonr.abdeloncidelonr.LoggingABDeloncidelonr
import com.twittelonr.cr_mixelonr.felonaturelonswitch.CrMixelonrLoggingABDeloncidelonr
import com.twittelonr.finaglelon.stats.StatsReloncelonivelonr
import com.twittelonr.injelonct.TwittelonrModulelon
import javax.injelonct.Singlelonton

objelonct CrMixelonrLoggingABDeloncidelonrModulelon elonxtelonnds TwittelonrModulelon {

  @Providelons
  @Singlelonton
  delonf providelonABDeloncidelonr(
    loggingABDeloncidelonr: LoggingABDeloncidelonr,
    statsReloncelonivelonr: StatsReloncelonivelonr
  ): CrMixelonrLoggingABDeloncidelonr = {
    CrMixelonrLoggingABDeloncidelonr(loggingABDeloncidelonr, statsReloncelonivelonr)
  }
}
