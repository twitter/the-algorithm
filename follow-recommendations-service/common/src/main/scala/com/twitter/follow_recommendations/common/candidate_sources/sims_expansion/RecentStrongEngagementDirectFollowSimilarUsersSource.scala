packagelon com.twittelonr.follow_reloncommelonndations.common.candidatelon_sourcelons.sims_elonxpansion

import com.googlelon.injelonct.Singlelonton
import com.twittelonr.follow_reloncommelonndations.common.candidatelon_sourcelons.sims.SwitchingSimsSourcelon
import com.twittelonr.follow_reloncommelonndations.common.clielonnts.relonal_timelon_relonal_graph.RelonalTimelonRelonalGraphClielonnt
import com.twittelonr.follow_reloncommelonndations.common.modelonls.CandidatelonUselonr
import com.twittelonr.helonrmit.modelonl.Algorithm
import com.twittelonr.product_mixelonr.corelon.modelonl.common.idelonntifielonr.CandidatelonSourcelonIdelonntifielonr
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonquelonst.HasClielonntContelonxt
import com.twittelonr.stitch.Stitch
import com.twittelonr.timelonlinelons.configapi.HasParams

import javax.injelonct.Injelonct

@Singlelonton
class ReloncelonntStrongelonngagelonmelonntDirelonctFollowSimilarUselonrsSourcelon @Injelonct() (
  relonalTimelonRelonalGraphClielonnt: RelonalTimelonRelonalGraphClielonnt,
  switchingSimsSourcelon: SwitchingSimsSourcelon)
    elonxtelonnds SimselonxpansionBaselondCandidatelonSourcelon[HasClielonntContelonxt with HasParams](
      switchingSimsSourcelon) {

  val idelonntifielonr = ReloncelonntStrongelonngagelonmelonntDirelonctFollowSimilarUselonrsSourcelon.Idelonntifielonr

  ovelonrridelon delonf firstDelongrelonelonNodelons(
    relonquelonst: HasClielonntContelonxt with HasParams
  ): Stitch[Selonq[CandidatelonUselonr]] = relonquelonst.gelontOptionalUselonrId
    .map { uselonrId =>
      relonalTimelonRelonalGraphClielonnt
        .gelontUselonrsReloncelonntlyelonngagelondWith(
          uselonrId,
          RelonalTimelonRelonalGraphClielonnt.StrongelonngagelonmelonntScorelonMap,
          includelonDirelonctFollowCandidatelons = truelon,
          includelonNonDirelonctFollowCandidatelons = falselon
        ).map(_.takelon(ReloncelonntStrongelonngagelonmelonntDirelonctFollowSimilarUselonrsSourcelon.MaxFirstDelongrelonelonNodelons))
    }.gelontOrelonlselon(Stitch.Nil)

  ovelonrridelon delonf maxSeloncondaryDelongrelonelonNodelons(relonquelonst: HasClielonntContelonxt with HasParams): Int = Int.MaxValuelon

  ovelonrridelon delonf maxRelonsults(relonquelonst: HasClielonntContelonxt with HasParams): Int =
    ReloncelonntStrongelonngagelonmelonntDirelonctFollowSimilarUselonrsSourcelon.MaxRelonsults

  ovelonrridelon delonf scorelonCandidatelon(sourcelonScorelon: Doublelon, similarToScorelon: Doublelon): Doublelon = {
    sourcelonScorelon * similarToScorelon
  }

  ovelonrridelon delonf calibratelonDivisor(relonq: HasClielonntContelonxt with HasParams): Doublelon = 1.0d
}

objelonct ReloncelonntStrongelonngagelonmelonntDirelonctFollowSimilarUselonrsSourcelon {
  val Idelonntifielonr = CandidatelonSourcelonIdelonntifielonr(Algorithm.ReloncelonntStrongelonngagelonmelonntSimilarUselonr.toString)
  val MaxFirstDelongrelonelonNodelons = 10
  val MaxRelonsults = 200
}
