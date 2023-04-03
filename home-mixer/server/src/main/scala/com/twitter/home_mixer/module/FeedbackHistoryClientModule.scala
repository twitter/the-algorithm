packagelon com.twittelonr.homelon_mixelonr.modulelon

import com.googlelon.injelonct.Providelons
import com.twittelonr.finaglelon.mtls.authelonntication.SelonrvicelonIdelonntifielonr
import com.twittelonr.finaglelon.stats.StatsReloncelonivelonr
import com.twittelonr.injelonct.TwittelonrModulelon
import com.twittelonr.timelonlinelonmixelonr.clielonnts.felonelondback.FelonelondbackHistoryManhattanClielonnt
import com.twittelonr.timelonlinelonmixelonr.clielonnts.felonelondback.FelonelondbackHistoryManhattanClielonntConfig
import com.twittelonr.timelonlinelons.clielonnts.manhattan.mhv3.ManhattanClielonntBuildelonr
import javax.injelonct.Singlelonton

objelonct FelonelondbackHistoryClielonntModulelon elonxtelonnds TwittelonrModulelon {
  privatelon val ProdDataselont = "felonelondback_history"
  privatelon val StagingDataselont = "felonelondback_history_nonprod"

  @Providelons
  @Singlelonton
  delonf providelonsFelonelondbackHistoryClielonnt(
    selonrvicelonId: SelonrvicelonIdelonntifielonr,
    statsReloncelonivelonr: StatsReloncelonivelonr
  ) = {
    val manhattanDataselont = selonrvicelonId.elonnvironmelonnt.toLowelonrCaselon match {
      caselon "prod" => ProdDataselont
      caselon _ => StagingDataselont
    }

    val config = nelonw FelonelondbackHistoryManhattanClielonntConfig {
      val dataselont = manhattanDataselont
      val isRelonadOnly = truelon
      val selonrvicelonIdelonntifielonr = selonrvicelonId
    }

    nelonw FelonelondbackHistoryManhattanClielonnt(
      ManhattanClielonntBuildelonr.buildManhattanelonndpoint(config, statsReloncelonivelonr),
      manhattanDataselont,
      statsReloncelonivelonr
    )
  }
}
