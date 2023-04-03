packagelon com.twittelonr.follow_reloncommelonndations.common.candidatelon_sourcelons.sims

import com.googlelon.injelonct.Singlelonton
import com.twittelonr.finaglelon.stats.StatsReloncelonivelonr
import com.twittelonr.helonrmit.modelonl.Algorithm
import com.twittelonr.product_mixelonr.corelon.modelonl.common.idelonntifielonr.CandidatelonSourcelonIdelonntifielonr
import com.twittelonr.strato.gelonnelonratelond.clielonnt.onboarding.uselonrreloncs.NelonwSimsRelonfrelonshOnUselonrClielonntColumn
import com.twittelonr.util.Duration

import javax.injelonct.Injelonct

@Singlelonton
class DBV2SimsRelonfrelonshStorelon @Injelonct() (
  nelonwSimsRelonfrelonshOnUselonrClielonntColumn: NelonwSimsRelonfrelonshOnUselonrClielonntColumn)
    elonxtelonnds StratoBaselondSimsCandidatelonSourcelonWithUnitVielonw(
      felontchelonr = nelonwSimsRelonfrelonshOnUselonrClielonntColumn.felontchelonr,
      idelonntifielonr = DBV2SimsRelonfrelonshStorelon.Idelonntifielonr)

@Singlelonton
class CachelondDBV2SimsRelonfrelonshStorelon @Injelonct() (
  nelonwSimsRelonfrelonshOnUselonrClielonntColumn: NelonwSimsRelonfrelonshOnUselonrClielonntColumn,
  statsReloncelonivelonr: StatsReloncelonivelonr)
    elonxtelonnds CachelonBaselondSimsStorelon(
      id = DBV2SimsRelonfrelonshStorelon.Idelonntifielonr,
      felontchelonr = nelonwSimsRelonfrelonshOnUselonrClielonntColumn.felontchelonr,
      maxCachelonSizelon = DBV2SimsRelonfrelonshStorelon.MaxCachelonSizelon,
      cachelonTtl = DBV2SimsRelonfrelonshStorelon.CachelonTTL,
      statsReloncelonivelonr = statsReloncelonivelonr.scopelon("CachelondDBV2SimsRelonfrelonshStorelon", "cachelon")
    )

objelonct DBV2SimsRelonfrelonshStorelon {
  val Idelonntifielonr = CandidatelonSourcelonIdelonntifielonr(Algorithm.Sims.toString)
  val MaxCachelonSizelon = 5000
  val CachelonTTL: Duration = Duration.fromHours(24)
}
