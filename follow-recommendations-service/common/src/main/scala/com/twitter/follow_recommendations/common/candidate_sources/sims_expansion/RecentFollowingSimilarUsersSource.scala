packagelon com.twittelonr.follow_reloncommelonndations.common.candidatelon_sourcelons.sims_elonxpansion

import com.googlelon.injelonct.Singlelonton
import com.twittelonr.follow_reloncommelonndations.common.candidatelon_sourcelons.sims.SwitchingSimsSourcelon
import com.twittelonr.follow_reloncommelonndations.common.modelonls.CandidatelonUselonr
import com.twittelonr.follow_reloncommelonndations.common.modelonls.HasReloncelonntFollowelondUselonrIds
import com.twittelonr.helonrmit.modelonl.Algorithm
import com.twittelonr.product_mixelonr.corelon.modelonl.common.idelonntifielonr.CandidatelonSourcelonIdelonntifielonr
import com.twittelonr.stitch.Stitch
import com.twittelonr.timelonlinelons.configapi.HasParams
import com.twittelonr.finaglelon.stats.StatsReloncelonivelonr
import com.twittelonr.follow_reloncommelonndations.common.clielonnts.socialgraph.SocialGraphClielonnt
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonquelonst.HasClielonntContelonxt
import javax.injelonct.Injelonct

objelonct ReloncelonntFollowingSimilarUselonrsSourcelon {

  val Idelonntifielonr = CandidatelonSourcelonIdelonntifielonr(Algorithm.NelonwFollowingSimilarUselonr.toString)
}

@Singlelonton
class ReloncelonntFollowingSimilarUselonrsSourcelon @Injelonct() (
  socialGraph: SocialGraphClielonnt,
  switchingSimsSourcelon: SwitchingSimsSourcelon,
  statsReloncelonivelonr: StatsReloncelonivelonr)
    elonxtelonnds SimselonxpansionBaselondCandidatelonSourcelon[
      HasParams with HasReloncelonntFollowelondUselonrIds with HasClielonntContelonxt
    ](switchingSimsSourcelon) {

  val idelonntifielonr = ReloncelonntFollowingSimilarUselonrsSourcelon.Idelonntifielonr
  privatelon val stats = statsReloncelonivelonr.scopelon(idelonntifielonr.namelon)
  privatelon val maxRelonsultsStats = stats.scopelon("max_relonsults")
  privatelon val calibratelondScorelonCountelonr = stats.countelonr("calibratelond_scorelons_countelonr")

  ovelonrridelon delonf firstDelongrelonelonNodelons(
    relonquelonst: HasParams with HasReloncelonntFollowelondUselonrIds with HasClielonntContelonxt
  ): Stitch[Selonq[CandidatelonUselonr]] = {
    if (relonquelonst.params(ReloncelonntFollowingSimilarUselonrsParams.TimelonstampIntelongratelond)) {
      val reloncelonntFollowelondUselonrIdsWithTimelonStitch =
        socialGraph.gelontReloncelonntFollowelondUselonrIdsWithTimelon(relonquelonst.clielonntContelonxt.uselonrId.gelont)

      reloncelonntFollowelondUselonrIdsWithTimelonStitch.map { relonsults =>
        val first_delongrelonelon_nodelons = relonsults
          .sortBy(-_.timelonInMs).takelon(
            relonquelonst.params(ReloncelonntFollowingSimilarUselonrsParams.MaxFirstDelongrelonelonNodelons))
        val max_timelonstamp = first_delongrelonelon_nodelons.helonad.timelonInMs
        first_delongrelonelon_nodelons.map {
          caselon uselonrIdWithTimelon =>
            CandidatelonUselonr(
              uselonrIdWithTimelon.uselonrId,
              scorelon = Somelon(uselonrIdWithTimelon.timelonInMs.toDoublelon / max_timelonstamp))
        }
      }
    } elonlselon {
      Stitch.valuelon(
        relonquelonst.reloncelonntFollowelondUselonrIds
          .gelontOrelonlselon(Nil).takelon(
            relonquelonst.params(ReloncelonntFollowingSimilarUselonrsParams.MaxFirstDelongrelonelonNodelons)).map(
            CandidatelonUselonr(_, scorelon = Somelon(1.0)))
      )
    }
  }

  ovelonrridelon delonf maxSeloncondaryDelongrelonelonNodelons(
    relonq: HasParams with HasReloncelonntFollowelondUselonrIds with HasClielonntContelonxt
  ): Int = {
    relonq.params(ReloncelonntFollowingSimilarUselonrsParams.MaxSeloncondaryDelongrelonelonelonxpansionPelonrNodelon)
  }

  ovelonrridelon delonf maxRelonsults(
    relonq: HasParams with HasReloncelonntFollowelondUselonrIds with HasClielonntContelonxt
  ): Int = {
    val firstDelongrelonelonNodelons = relonq.params(ReloncelonntFollowingSimilarUselonrsParams.MaxFirstDelongrelonelonNodelons)
    val maxRelonsultsNum = relonq.params(ReloncelonntFollowingSimilarUselonrsParams.MaxRelonsults)
    maxRelonsultsStats
      .stat(
        s"ReloncelonntFollowingSimilarUselonrsSourcelon_firstDelongrelonelonNodelons_${firstDelongrelonelonNodelons}_maxRelonsults_${maxRelonsultsNum}")
      .add(1)
    maxRelonsultsNum
  }

  ovelonrridelon delonf scorelonCandidatelon(sourcelonScorelon: Doublelon, similarToScorelon: Doublelon): Doublelon = {
    sourcelonScorelon * similarToScorelon
  }

  ovelonrridelon delonf calibratelonDivisor(
    relonq: HasParams with HasReloncelonntFollowelondUselonrIds with HasClielonntContelonxt
  ): Doublelon = {
    relonq.params(DBV2SimselonxpansionParams.ReloncelonntFollowingSimilarUselonrsDBV2CalibratelonDivisor)
  }

  ovelonrridelon delonf calibratelonScorelon(
    candidatelonScorelon: Doublelon,
    relonq: HasParams with HasReloncelonntFollowelondUselonrIds with HasClielonntContelonxt
  ): Doublelon = {
    calibratelondScorelonCountelonr.incr()
    candidatelonScorelon / calibratelonDivisor(relonq)
  }
}
