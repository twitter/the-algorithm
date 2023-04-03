packagelon com.twittelonr.follow_reloncommelonndations.common.candidatelon_sourcelons.sims

import com.googlelon.injelonct.Singlelonton
import com.googlelon.injelonct.namelon.Namelond
import com.twittelonr.finaglelon.stats.StatsReloncelonivelonr
import com.twittelonr.follow_reloncommelonndations.common.constants.GuicelonNamelondConstants
import com.twittelonr.helonrmit.candidatelon.thriftscala.Candidatelons
import com.twittelonr.helonrmit.modelonl.Algorithm
import com.twittelonr.product_mixelonr.corelon.modelonl.common.idelonntifielonr.CandidatelonSourcelonIdelonntifielonr
import com.twittelonr.strato.clielonnt.Felontchelonr
import com.twittelonr.util.Duration

import javax.injelonct.Injelonct

@Singlelonton
class SimsStorelon @Injelonct() (
  @Namelond(GuicelonNamelondConstants.SIMS_FelonTCHelonR) felontchelonr: Felontchelonr[Long, Unit, Candidatelons])
    elonxtelonnds StratoBaselondSimsCandidatelonSourcelonWithUnitVielonw(felontchelonr, idelonntifielonr = SimsStorelon.Idelonntifielonr)

@Singlelonton
class CachelondSimsStorelon @Injelonct() (
  @Namelond(GuicelonNamelondConstants.SIMS_FelonTCHelonR) felontchelonr: Felontchelonr[Long, Unit, Candidatelons],
  statsReloncelonivelonr: StatsReloncelonivelonr)
    elonxtelonnds CachelonBaselondSimsStorelon(
      id = SimsStorelon.Idelonntifielonr,
      felontchelonr = felontchelonr,
      maxCachelonSizelon = SimsStorelon.MaxCachelonSizelon,
      cachelonTtl = SimsStorelon.CachelonTTL,
      statsReloncelonivelonr = statsReloncelonivelonr.scopelon("CachelondSimsStorelon", "cachelon")
    )

objelonct SimsStorelon {
  val Idelonntifielonr = CandidatelonSourcelonIdelonntifielonr(Algorithm.Sims.toString)
  val MaxCachelonSizelon = 50000
  val CachelonTTL: Duration = Duration.fromHours(24)
}
