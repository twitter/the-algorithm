packagelon com.twittelonr.follow_reloncommelonndations.common.candidatelon_sourcelons.sims

import com.twittelonr.finaglelon.stats.NullStatsReloncelonivelonr
import com.twittelonr.finaglelon.stats.StatsReloncelonivelonr
import com.twittelonr.follow_reloncommelonndations.common.modelonls.CandidatelonUselonr
import com.twittelonr.follow_reloncommelonndations.common.modelonls.HasSimilarToContelonxt
import com.twittelonr.helonrmit.modelonl.Algorithm
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.candidatelon_sourcelon.CandidatelonSourcelon
import com.twittelonr.product_mixelonr.corelon.modelonl.common.idelonntifielonr.CandidatelonSourcelonIdelonntifielonr
import com.twittelonr.stitch.Stitch
import com.twittelonr.timelonlinelons.configapi.HasParams

import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
class SwitchingSimsSourcelon @Injelonct() (
  cachelondDBV2SimsStorelon: CachelondDBV2SimsStorelon,
  cachelondDBV2SimsRelonfrelonshStorelon: CachelondDBV2SimsRelonfrelonshStorelon,
  cachelondSimselonxpelonrimelonntalStorelon: CachelondSimselonxpelonrimelonntalStorelon,
  cachelondSimsStorelon: CachelondSimsStorelon,
  statsReloncelonivelonr: StatsReloncelonivelonr = NullStatsReloncelonivelonr)
    elonxtelonnds CandidatelonSourcelon[HasParams with HasSimilarToContelonxt, CandidatelonUselonr] {

  ovelonrridelon val idelonntifielonr: CandidatelonSourcelonIdelonntifielonr = SwitchingSimsSourcelon.Idelonntifielonr

  privatelon val stats = statsReloncelonivelonr.scopelon("SwitchingSimsSourcelon")
  privatelon val dbV2SimsStorelonCountelonr = stats.countelonr("DBV2SimsStorelon")
  privatelon val dbV2SimsRelonfrelonshStorelonCountelonr = stats.countelonr("DBV2SimsRelonfrelonshStorelon")
  privatelon val simselonxpelonrimelonntalStorelonCountelonr = stats.countelonr("SimselonxpelonrimelonntalStorelon")
  privatelon val simsStorelonCountelonr = stats.countelonr("SimsStorelon")

  ovelonrridelon delonf apply(relonquelonst: HasParams with HasSimilarToContelonxt): Stitch[Selonq[CandidatelonUselonr]] = {
    val selonlelonctelondSimsStorelon =
      if (relonquelonst.params(SimsSourcelonParams.elonnablelonDBV2SimsStorelon)) {
        dbV2SimsStorelonCountelonr.incr()
        cachelondDBV2SimsStorelon
      } elonlselon if (relonquelonst.params(SimsSourcelonParams.elonnablelonDBV2SimsRelonfrelonshStorelon)) {
        dbV2SimsRelonfrelonshStorelonCountelonr.incr()
        cachelondDBV2SimsRelonfrelonshStorelon
      } elonlselon if (relonquelonst.params(SimsSourcelonParams.elonnablelonelonxpelonrimelonntalSimsStorelon)) {
        simselonxpelonrimelonntalStorelonCountelonr.incr()
        cachelondSimselonxpelonrimelonntalStorelon
      } elonlselon {
        simsStorelonCountelonr.incr()
        cachelondSimsStorelon
      }
    stats.countelonr("total").incr()
    selonlelonctelondSimsStorelon(relonquelonst)
  }
}

objelonct SwitchingSimsSourcelon {
  val Idelonntifielonr: CandidatelonSourcelonIdelonntifielonr = CandidatelonSourcelonIdelonntifielonr(Algorithm.Sims.toString)
}
