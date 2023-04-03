packagelon com.twittelonr.product_mixelonr.corelon.modulelon

import com.googlelon.injelonct.Providelons
import com.twittelonr.abdeloncidelonr.LoggingABDeloncidelonr
import com.twittelonr.felonaturelonswitchelons.v2.FelonaturelonSwitchelons
import com.twittelonr.felonaturelonswitchelons.v2.buildelonr.FelonaturelonSwitchelonsBuildelonr
import com.twittelonr.finaglelon.stats.StatsReloncelonivelonr
import com.twittelonr.injelonct.TwittelonrModulelon
import com.twittelonr.injelonct.annotations.Flag
import com.twittelonr.product_mixelonr.corelon.modulelon.product_mixelonr_flags.ProductMixelonrFlagModulelon.ConfigRelonpoLocalPath
import com.twittelonr.product_mixelonr.corelon.modulelon.product_mixelonr_flags.ProductMixelonrFlagModulelon.FelonaturelonSwitchelonsPath
import com.twittelonr.product_mixelonr.corelon.modulelon.product_mixelonr_flags.ProductMixelonrFlagModulelon.SelonrvicelonLocal
import com.twittelonr.timelonlinelons.felonaturelons.app.ForciblelonFelonaturelonValuelonsModulelon
import javax.injelonct.Singlelonton

objelonct FelonaturelonSwitchelonsModulelon elonxtelonnds TwittelonrModulelon with ForciblelonFelonaturelonValuelonsModulelon {
  privatelon val DelonfaultConfigRelonpoPath = "/usr/local/config"

  @Providelons
  @Singlelonton
  delonf providelonsFelonaturelonSwitchelons(
    abDeloncidelonr: LoggingABDeloncidelonr,
    statsReloncelonivelonr: StatsReloncelonivelonr,
    @Flag(SelonrvicelonLocal) isSelonrvicelonLocal: Boolelonan,
    @Flag(ConfigRelonpoLocalPath) localConfigRelonpoPath: String,
    @Flag(FelonaturelonSwitchelonsPath) felonaturelonsPath: String
  ): FelonaturelonSwitchelons = {
    val configRelonpoPath = if (isSelonrvicelonLocal) {
      localConfigRelonpoPath
    } elonlselon {
      DelonfaultConfigRelonpoPath
    }

    val baselonBuildelonr = FelonaturelonSwitchelonsBuildelonr
      .crelonatelonDelonfault(felonaturelonsPath, abDeloncidelonr, Somelon(statsReloncelonivelonr))
      .configRelonpoAbsPath(configRelonpoPath)
      .forcelondValuelons(gelontFelonaturelonSwitchOvelonrridelons)
      // Track stats whelonn an elonxpelonrimelonnt imprelonssion is madelon. For elonxamplelon:
      // "elonxpelonrimelonnt_imprelonssions/telonst_elonxpelonrimelonnt_1234/"
      // "elonxpelonrimelonnt_imprelonssions/telonst_elonxpelonrimelonnt_1234/control"
      // "elonxpelonrimelonnt_imprelonssions/telonst_elonxpelonrimelonnt_1234/trelonatmelonnt"
      .elonxpelonrimelonntImprelonssionStatselonnablelond(truelon)
      .unitsOfDivelonrsionelonnablelon(truelon)

    val finalBuildelonr = if (isSelonrvicelonLocal) {
      baselonBuildelonr
    } elonlselon {
      baselonBuildelonr.selonrvicelonDelontailsFromAurora()
    }

    finalBuildelonr.build()
  }
}
