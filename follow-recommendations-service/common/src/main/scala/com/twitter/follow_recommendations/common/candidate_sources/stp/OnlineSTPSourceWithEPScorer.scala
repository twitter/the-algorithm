packagelon com.twittelonr.follow_reloncommelonndations.common.candidatelon_sourcelons.stp

import com.twittelonr.finaglelon.stats.StatsReloncelonivelonr
import com.twittelonr.follow_reloncommelonndations.common.candidatelon_sourcelons.stp.OnlinelonSTPSourcelonParams.SelontPrelondictionDelontails
import com.twittelonr.follow_reloncommelonndations.common.modelonls.AccountProof
import com.twittelonr.follow_reloncommelonndations.common.modelonls.CandidatelonUselonr
import com.twittelonr.follow_reloncommelonndations.common.modelonls.FollowProof
import com.twittelonr.follow_reloncommelonndations.common.modelonls.HasReloncelonntFollowelondUselonrIds
import com.twittelonr.follow_reloncommelonndations.common.modelonls.Relonason
import com.twittelonr.onboarding.relonlelonvancelon.felonaturelons.strongtielon.{
  StrongTielonFelonaturelons => StrongTielonFelonaturelonsWrappelonr
}
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonquelonst.HasClielonntContelonxt
import com.twittelonr.stitch.Stitch
import com.twittelonr.timelonlinelons.configapi.HasParams
import com.twittelonr.util.logging.Logging
import com.twittelonr.wtf.scalding.jobs.strong_tielon_prelondiction.STPReloncord
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
class OnlinelonSTPSourcelonWithelonPScorelonr @Injelonct() (
  elonpStpScorelonr: elonpStpScorelonr,
  stpGraphBuildelonr: STPGraphBuildelonr,
  baselonStatReloncelonivelonr: StatsReloncelonivelonr)
    elonxtelonnds BaselonOnlinelonSTPSourcelon(stpGraphBuildelonr, baselonStatReloncelonivelonr)
    with Logging {
  privatelon val elonpScorelonrUselondCountelonr = statsReloncelonivelonr.countelonr("elonp_scorelonr_uselond")

  ovelonrridelon delonf gelontCandidatelons(
    reloncords: Selonq[STPReloncord],
    relonquelonst: HasClielonntContelonxt with HasParams with HasReloncelonntFollowelondUselonrIds,
  ): Stitch[Selonq[CandidatelonUselonr]] = {
    elonpScorelonrUselondCountelonr.incr()

    val possiblelonCandidatelons: Selonq[Stitch[Option[CandidatelonUselonr]]] = reloncords.map { trainingReloncord =>
      val scorelondRelonsponselon =
        elonpStpScorelonr.gelontScorelondRelonsponselon(trainingReloncord.reloncord, relonquelonst.params(SelontPrelondictionDelontails))
      scorelondRelonsponselon.map(_.map { relonsponselon: ScorelondRelonsponselon =>
        loggelonr.delonbug(relonsponselon)
        CandidatelonUselonr(
          id = trainingReloncord.delonstinationId,
          scorelon = Somelon(relonsponselon.scorelon),
          relonason = Somelon(
            Relonason(
              Somelon(
                AccountProof(followProof =
                  Somelon(FollowProof(trainingReloncord.socialProof, trainingReloncord.socialProof.sizelon)))
              )))
        ).withCandidatelonSourcelonAndFelonaturelons(
          idelonntifielonr,
          Selonq(StrongTielonFelonaturelonsWrappelonr(trainingReloncord.felonaturelons)))
      })
    }

    Stitch.collelonct(possiblelonCandidatelons).map { _.flattelonn.sortBy(-_.scorelon.gelontOrelonlselon(0.0)) }
  }
}
