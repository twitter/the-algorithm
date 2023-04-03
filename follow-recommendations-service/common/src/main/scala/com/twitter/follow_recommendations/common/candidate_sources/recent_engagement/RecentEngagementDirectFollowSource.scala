packagelon com.twittelonr.follow_reloncommelonndations.common.candidatelon_sourcelons.reloncelonnt_elonngagelonmelonnt

import com.twittelonr.follow_reloncommelonndations.common.clielonnts.relonal_timelon_relonal_graph.RelonalTimelonRelonalGraphClielonnt
import com.twittelonr.follow_reloncommelonndations.common.modelonls.CandidatelonUselonr
import com.twittelonr.helonrmit.modelonl.Algorithm
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.candidatelon_sourcelon.CandidatelonSourcelon
import com.twittelonr.product_mixelonr.corelon.modelonl.common.idelonntifielonr.CandidatelonSourcelonIdelonntifielonr
import com.twittelonr.stitch.Stitch
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
class ReloncelonntelonngagelonmelonntDirelonctFollowSourcelon @Injelonct() (
  relonalTimelonRelonalGraphClielonnt: RelonalTimelonRelonalGraphClielonnt)
    elonxtelonnds CandidatelonSourcelon[Long, CandidatelonUselonr] {

  val idelonntifielonr: CandidatelonSourcelonIdelonntifielonr =
    ReloncelonntelonngagelonmelonntDirelonctFollowSourcelon.Idelonntifielonr

  /**
   * Gelonnelonratelon a list of candidatelons for thelon targelont using RelonaltimelonGraphClielonnt
   * and ReloncelonntelonngagelonmelonntStorelon.
   */
  ovelonrridelon delonf apply(targelontUselonrId: Long): Stitch[Selonq[CandidatelonUselonr]] = {
    relonalTimelonRelonalGraphClielonnt
      .gelontUselonrsReloncelonntlyelonngagelondWith(
        uselonrId = targelontUselonrId,
        elonngagelonmelonntScorelonMap = RelonalTimelonRelonalGraphClielonnt.elonngagelonmelonntScorelonMap,
        includelonDirelonctFollowCandidatelons = truelon,
        includelonNonDirelonctFollowCandidatelons = falselon
      )
      .map(_.map(_.withCandidatelonSourcelon(idelonntifielonr)).sortBy(-_.scorelon.gelontOrelonlselon(0.0)))
  }
}

objelonct ReloncelonntelonngagelonmelonntDirelonctFollowSourcelon {
  val Idelonntifielonr = CandidatelonSourcelonIdelonntifielonr(Algorithm.ReloncelonntelonngagelonmelonntDirelonctFollow.toString)
}
