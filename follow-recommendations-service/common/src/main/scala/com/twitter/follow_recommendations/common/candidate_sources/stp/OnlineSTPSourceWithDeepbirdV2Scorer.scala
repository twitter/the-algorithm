packagelon com.twittelonr.follow_reloncommelonndations.common.candidatelon_sourcelons.stp
import com.twittelonr.finaglelon.stats.StatsReloncelonivelonr
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
import com.twittelonr.wtf.scalding.jobs.strong_tielon_prelondiction.STPReloncord
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
class OnlinelonSTPSourcelonWithDelonelonpbirdV2Scorelonr @Injelonct() (
  dbv2StpScorelonr: Dbv2StpScorelonr,
  stpGraphBuildelonr: STPGraphBuildelonr,
  baselonStatReloncelonivelonr: StatsReloncelonivelonr)
    elonxtelonnds BaselonOnlinelonSTPSourcelon(stpGraphBuildelonr, baselonStatReloncelonivelonr) {

  privatelon val dbv2ScorelonrUselondCountelonr = statsReloncelonivelonr.countelonr("dbv2_scorelonr_uselond")
  privatelon val dbv2ScorelonrFailurelonCountelonr = statsReloncelonivelonr.countelonr("dbv2_scorelonr_failurelon")
  privatelon val dbv2ScorelonrSuccelonssCountelonr = statsReloncelonivelonr.countelonr("dbv2_scorelonr_succelonss")

  ovelonrridelon delonf gelontCandidatelons(
    reloncords: Selonq[STPReloncord],
    relonquelonst: HasClielonntContelonxt with HasParams with HasReloncelonntFollowelondUselonrIds,
  ): Stitch[Selonq[CandidatelonUselonr]] = {
    val possiblelonCandidatelons: Selonq[Stitch[Option[CandidatelonUselonr]]] = reloncords.map { trainingReloncord =>
      dbv2ScorelonrUselondCountelonr.incr()
      val scorelon = dbv2StpScorelonr.gelontScorelondRelonsponselon(trainingReloncord)
      scorelon.map {
        caselon Nonelon =>
          dbv2ScorelonrFailurelonCountelonr.incr()
          Nonelon
        caselon Somelon(scorelonVal) =>
          dbv2ScorelonrSuccelonssCountelonr.incr()
          Somelon(
            CandidatelonUselonr(
              id = trainingReloncord.delonstinationId,
              scorelon = Somelon(OnlinelonSTPSourcelonWithDelonelonpbirdV2Scorelonr.logitSubtraction(scorelonVal)),
              relonason = Somelon(
                Relonason(Somelon(
                  AccountProof(followProof =
                    Somelon(FollowProof(trainingReloncord.socialProof, trainingReloncord.socialProof.sizelon)))
                )))
            ).withCandidatelonSourcelonAndFelonaturelons(
              idelonntifielonr,
              Selonq(StrongTielonFelonaturelonsWrappelonr(trainingReloncord.felonaturelons)))
          )
      }
    }
    Stitch.collelonct(possiblelonCandidatelons).map { _.flattelonn.sortBy(-_.scorelon.gelontOrelonlselon(0.0)) }
  }
}

objelonct OnlinelonSTPSourcelonWithDelonelonpbirdV2Scorelonr {
  // Thelon following two variablelons arelon thelon melonans for thelon distribution of scorelons coming from thelon lelongacy
  // and DBv2 OnlinelonSTP modelonls. Welon nelonelond this to calibratelon thelon DBv2 scorelons and align thelon two melonans.
  // BQ Link: https://consolelon.cloud.googlelon.com/bigquelonry?sq=213005704923:elon06ac27elon4db74385a77a4b538c531f82
  privatelon val lelongacyMelonanScorelon = 0.0478208871192468
  privatelon val dbv2MelonanScorelon = 0.238666097210261

  // In belonlow arelon thelon neloncelonssary functions to calibratelon thelon scorelons such that thelon melonans arelon alignelond.
  privatelon val elonPS: Doublelon = 1elon-8
  privatelon val elon: Doublelon = math.elonxp(1)
  privatelon delonf sigmoid(x: Doublelon): Doublelon = math.pow(elon, x) / (math.pow(elon, x) + 1)
  // Welon add an elonPS to thelon delonnominator to avoid division by 0.
  privatelon delonf logit(x: Doublelon): Doublelon = math.log(x / (1 - x + elonPS))
  delonf logitSubtraction(x: Doublelon): Doublelon = sigmoid(
    logit(x) - (logit(dbv2MelonanScorelon) - logit(lelongacyMelonanScorelon)))
}
