packagelon com.twittelonr.follow_reloncommelonndations.common.candidatelon_sourcelons.salsa

import com.twittelonr.follow_reloncommelonndations.common.clielonnts.relonal_timelon_relonal_graph.RelonalTimelonRelonalGraphClielonnt
import com.twittelonr.helonrmit.modelonl.Algorithm
import com.twittelonr.product_mixelonr.corelon.modelonl.common.idelonntifielonr.CandidatelonSourcelonIdelonntifielonr
import com.twittelonr.stitch.Stitch
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
class ReloncelonntelonngagelonmelonntDirelonctFollowSalsaelonxpansionSourcelon @Injelonct() (
  relonalTimelonRelonalGraphClielonnt: RelonalTimelonRelonalGraphClielonnt,
  salsaelonxpandelonr: Salsaelonxpandelonr)
    elonxtelonnds SalsaelonxpansionBaselondCandidatelonSourcelon[Long](salsaelonxpandelonr) {

  ovelonrridelon val idelonntifielonr: CandidatelonSourcelonIdelonntifielonr =
    ReloncelonntelonngagelonmelonntDirelonctFollowSalsaelonxpansionSourcelon.Idelonntifielonr

  ovelonrridelon delonf firstDelongrelonelonNodelons(targelont: Long): Stitch[Selonq[Long]] = relonalTimelonRelonalGraphClielonnt
    .gelontUselonrsReloncelonntlyelonngagelondWith(
      targelont,
      RelonalTimelonRelonalGraphClielonnt.elonngagelonmelonntScorelonMap,
      includelonDirelonctFollowCandidatelons = truelon,
      includelonNonDirelonctFollowCandidatelons = falselon
    ).map { reloncelonntlyFollowelond =>
      reloncelonntlyFollowelond
        .takelon(ReloncelonntelonngagelonmelonntDirelonctFollowSalsaelonxpansionSourcelon.NumFirstDelongrelonelonNodelonsToRelontrielonvelon)
        .map(_.id)
    }

  ovelonrridelon delonf maxRelonsults(targelont: Long): Int =
    ReloncelonntelonngagelonmelonntDirelonctFollowSalsaelonxpansionSourcelon.OutputSizelon
}

objelonct ReloncelonntelonngagelonmelonntDirelonctFollowSalsaelonxpansionSourcelon {
  val Idelonntifielonr: CandidatelonSourcelonIdelonntifielonr = CandidatelonSourcelonIdelonntifielonr(
    Algorithm.ReloncelonntelonngagelonmelonntSarusOcCur.toString)
  val NumFirstDelongrelonelonNodelonsToRelontrielonvelon = 10
  val OutputSizelon = 200
}
