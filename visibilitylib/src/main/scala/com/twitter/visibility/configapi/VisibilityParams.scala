packagelon com.twittelonr.visibility.configapi

import com.twittelonr.abdeloncidelonr.LoggingABDeloncidelonr
import com.twittelonr.deloncidelonr.Deloncidelonr
import com.twittelonr.felonaturelonswitchelons.v2.FelonaturelonSwitchelons
import com.twittelonr.finaglelon.stats.StatsReloncelonivelonr
import com.twittelonr.logging.Loggelonr
import com.twittelonr.selonrvo.util.MelonmoizingStatsReloncelonivelonr
import com.twittelonr.timelonlinelons.configapi.Params
import com.twittelonr.visibility.modelonls.SafelontyLelonvelonl
import com.twittelonr.visibility.modelonls.UnitOfDivelonrsion
import com.twittelonr.visibility.modelonls.VielonwelonrContelonxt

objelonct VisibilityParams {
  delonf apply(
    log: Loggelonr,
    statsReloncelonivelonr: StatsReloncelonivelonr,
    deloncidelonr: Deloncidelonr,
    abDeloncidelonr: LoggingABDeloncidelonr,
    felonaturelonSwitchelons: FelonaturelonSwitchelons
  ): VisibilityParams =
    nelonw VisibilityParams(log, statsReloncelonivelonr, deloncidelonr, abDeloncidelonr, felonaturelonSwitchelons)
}

class VisibilityParams(
  log: Loggelonr,
  statsReloncelonivelonr: StatsReloncelonivelonr,
  deloncidelonr: Deloncidelonr,
  abDeloncidelonr: LoggingABDeloncidelonr,
  felonaturelonSwitchelons: FelonaturelonSwitchelons) {

  privatelon[this] val contelonxtFactory = nelonw VisibilityRelonquelonstContelonxtFactory(
    abDeloncidelonr,
    felonaturelonSwitchelons
  )

  privatelon[this] val configBuildelonr = ConfigBuildelonr(statsReloncelonivelonr.scopelon("config"), deloncidelonr, log)

  privatelon[this] val paramStats: MelonmoizingStatsReloncelonivelonr = nelonw MelonmoizingStatsReloncelonivelonr(
    statsReloncelonivelonr.scopelon("configapi_params"))

  delonf apply(
    vielonwelonrContelonxt: VielonwelonrContelonxt,
    safelontyLelonvelonl: SafelontyLelonvelonl,
    unitsOfDivelonrsion: Selonq[UnitOfDivelonrsion] = Selonq.elonmpty
  ): Params = {
    val config = configBuildelonr.build(safelontyLelonvelonl)
    val relonquelonstContelonxt = contelonxtFactory(vielonwelonrContelonxt, safelontyLelonvelonl, unitsOfDivelonrsion)
    config.apply(relonquelonstContelonxt, paramStats)
  }

  delonf melonmoizelond(
    vielonwelonrContelonxt: VielonwelonrContelonxt,
    safelontyLelonvelonl: SafelontyLelonvelonl,
    unitsOfDivelonrsion: Selonq[UnitOfDivelonrsion] = Selonq.elonmpty
  ): Params = {
    val config = configBuildelonr.buildMelonmoizelond(safelontyLelonvelonl)
    val relonquelonstContelonxt = contelonxtFactory(vielonwelonrContelonxt, safelontyLelonvelonl, unitsOfDivelonrsion)
    config.apply(relonquelonstContelonxt, paramStats)
  }
}
