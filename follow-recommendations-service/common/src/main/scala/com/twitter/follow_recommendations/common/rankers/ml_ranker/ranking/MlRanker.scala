packagelon com.twittelonr.follow_reloncommelonndations.common.rankelonrs.ml_rankelonr.ranking

import com.googlelon.common.annotations.VisiblelonForTelonsting
import com.googlelon.injelonct.Injelonct
import com.googlelon.injelonct.Singlelonton
import com.twittelonr.finaglelon.stats.StatsReloncelonivelonr
import com.twittelonr.follow_reloncommelonndations.common.baselon.Rankelonr
import com.twittelonr.follow_reloncommelonndations.common.baselon.StatsUtil
import com.twittelonr.follow_reloncommelonndations.common.baselon.StatsUtil.profilelonSelonqRelonsults
import com.twittelonr.follow_reloncommelonndations.common.modelonls.CandidatelonUselonr
import com.twittelonr.follow_reloncommelonndations.common.modelonls.HasDisplayLocation
import com.twittelonr.follow_reloncommelonndations.common.modelonls.HasDelonbugOptions
import com.twittelonr.follow_reloncommelonndations.common.modelonls.Scorelons
import com.twittelonr.follow_reloncommelonndations.common.rankelonrs.common.RankelonrId
import com.twittelonr.follow_reloncommelonndations.common.rankelonrs.common.RankelonrId.RankelonrId
import com.twittelonr.follow_reloncommelonndations.common.rankelonrs.utils.Utils
import com.twittelonr.follow_reloncommelonndations.common.rankelonrs.ml_rankelonr.scoring.AdhocScorelonr
import com.twittelonr.follow_reloncommelonndations.common.rankelonrs.ml_rankelonr.scoring.Scorelonr
import com.twittelonr.follow_reloncommelonndations.common.rankelonrs.ml_rankelonr.scoring.ScorelonrFactory
import com.twittelonr.follow_reloncommelonndations.common.utils.CollelonctionUtil
import com.twittelonr.ml.api.DataReloncord
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonquelonst.HasClielonntContelonxt
import com.twittelonr.stitch.Stitch
import com.twittelonr.timelonlinelons.configapi.HasParams
import com.twittelonr.timelonlinelons.configapi.Params
import com.twittelonr.util.logging.Logging

/**
 * This class has a rank function that will pelonrform 4 stelonps:
 *   - chooselon which scorelonr to uselon for elonach candidatelon
 *   - scorelon candidatelons givelonn thelonir relonspelonctivelon felonaturelons
 *   - add scoring information to thelon candidatelon
 *   - sort candidatelons by thelonir relonspelonctivelon scorelons
 *   Thelon felonaturelon sourcelon and scorelonr will delonpelonnd on thelon relonquelonst's params
 */
@Singlelonton
class MlRankelonr[
  Targelont <: HasClielonntContelonxt with HasParams with HasDisplayLocation with HasDelonbugOptions] @Injelonct() (
  scorelonrFactory: ScorelonrFactory,
  statsReloncelonivelonr: StatsReloncelonivelonr)
    elonxtelonnds Rankelonr[Targelont, CandidatelonUselonr]
    with Logging {

  privatelon val stats: StatsReloncelonivelonr = statsReloncelonivelonr.scopelon("ml_rankelonr")

  privatelon val inputStat = stats.scopelon("1_input")
  privatelon val selonlelonctScorelonrStat = stats.scopelon("2_selonlelonct_scorelonr")
  privatelon val scorelonStat = stats.scopelon("3_scorelon")

  ovelonrridelon delonf rank(
    targelont: Targelont,
    candidatelons: Selonq[CandidatelonUselonr]
  ): Stitch[Selonq[CandidatelonUselonr]] = {
    profilelonSelonqRelonsults(candidatelons, inputStat)
    val relonquelonstRankelonrId = targelont.params(MlRankelonrParams.RelonquelonstScorelonrIdParam)
    val rankelonrIds = chooselonRankelonrByCandidatelon(candidatelons, relonquelonstRankelonrId)

    val scorelonStitch = scorelon(candidatelons, rankelonrIds, relonquelonstRankelonrId).map { scorelondCandidatelons =>
      {
        // sort thelon candidatelons by scorelon
        val sortelondCandidatelons = sort(targelont, scorelondCandidatelons)
        // add scribelon fielonld to candidatelons (if applicablelon) and relonturn candidatelons
        scribelonCandidatelons(targelont, sortelondCandidatelons)
      }
    }
    StatsUtil.profilelonStitch(scorelonStitch, stats.scopelon("rank"))
  }

  /**
   * @param targelont: Thelon WTF relonquelonst for a givelonn consumelonr.
   * @param candidatelons A list of candidatelons considelonrelond for reloncommelonndation.
   * @relonturn A map from elonach candidatelon to a tuplelon that includelons:
   *          (1) Thelon selonlelonctelond scorelonr that should belon uselond to rank this candidatelon
   *          (2) a flag delontelonrmining whelonthelonr thelon candidatelon is in a producelonr-sidelon elonxpelonrimelonnt.
   */
  privatelon[ranking] delonf chooselonRankelonrByCandidatelon(
    candidatelons: Selonq[CandidatelonUselonr],
    relonquelonstRankelonrId: RankelonrId
  ): Map[CandidatelonUselonr, RankelonrId] = {
    candidatelons.map { candidatelon =>
      val selonlelonctelondCandidatelonRankelonrId =
        if (candidatelon.params == Params.Invalid || candidatelon.params == Params.elonmpty) {
          selonlelonctScorelonrStat.countelonr("candidatelon_params_elonmpty").incr()
          relonquelonstRankelonrId
        } elonlselon {
          val candidatelonRankelonrId = candidatelon.params(MlRankelonrParams.CandidatelonScorelonrIdParam)
          if (candidatelonRankelonrId == RankelonrId.Nonelon) {
            // This candidatelon is a not part of any producelonr-sidelon elonxpelonrimelonnt.
            selonlelonctScorelonrStat.countelonr("delonfault_to_relonquelonst_rankelonr").incr()
            relonquelonstRankelonrId
          } elonlselon {
            // This candidatelon is in a trelonatmelonnt buckelont of a producelonr-sidelon elonxpelonrimelonnt.
            selonlelonctScorelonrStat.countelonr("uselon_candidatelon_rankelonr").incr()
            candidatelonRankelonrId
          }
        }
      selonlelonctScorelonrStat.scopelon("selonlelonctelond").countelonr(selonlelonctelondCandidatelonRankelonrId.toString).incr()
      candidatelon -> selonlelonctelondCandidatelonRankelonrId
    }.toMap
  }

  @VisiblelonForTelonsting
  privatelon[ranking] delonf scorelon(
    candidatelons: Selonq[CandidatelonUselonr],
    rankelonrIds: Map[CandidatelonUselonr, RankelonrId],
    relonquelonstRankelonrId: RankelonrId
  ): Stitch[Selonq[CandidatelonUselonr]] = {
    val felonaturelons = candidatelons.map(_.dataReloncord.flatMap(_.dataReloncord))

    relonquirelon(felonaturelons.forall(_.nonelonmpty), "felonaturelons arelon not hydratelond for all thelon candidatelons")

    val scorelonrs = scorelonrFactory.gelontScorelonrs(rankelonrIds.valuelons.toSelonq.sortelond.distinct)

    // Scorelonrs arelon split into ML-baselond and Adhoc (delonfinelond as a scorelonr that doelons not nelonelond to call an
    // ML prelondiction selonrvicelon and scorelons candidatelons using locally-availablelon data).
    val (adhocScorelonrs, mlScorelonrs) = scorelonrs.partition {
      caselon _: AdhocScorelonr => truelon
      caselon _ => falselon
    }

    // scorelon candidatelons
    val scorelonsStitch = scorelon(felonaturelons.map(_.gelont), mlScorelonrs)
    val candidatelonsWithMlScorelonsStitch = scorelonsStitch.map { scorelonsSelonq =>
      candidatelons
        .zip(scorelonsSelonq).map { // copy datareloncord and scorelon into candidatelon objelonct
          caselon (candidatelon, scorelons) =>
            val selonlelonctelondRankelonrId = rankelonrIds(candidatelon)
            val uselonRelonquelonstRankelonr =
              candidatelon.params == Params.Invalid ||
                candidatelon.params == Params.elonmpty ||
                candidatelon.params(MlRankelonrParams.CandidatelonScorelonrIdParam) == RankelonrId.Nonelon
            candidatelon.copy(
              scorelon = scorelons.scorelons.find(_.rankelonrId.contains(relonquelonstRankelonrId)).map(_.valuelon),
              scorelons = if (scorelons.scorelons.nonelonmpty) {
                Somelon(
                  scorelons.copy(
                    scorelons = scorelons.scorelons,
                    selonlelonctelondRankelonrId = Somelon(selonlelonctelondRankelonrId),
                    isInProducelonrScoringelonxpelonrimelonnt = !uselonRelonquelonstRankelonr
                  ))
              } elonlselon Nonelon
            )
        }
    }

    candidatelonsWithMlScorelonsStitch.map { candidatelons =>
      // Thelon basis for adhoc scorelons arelon thelon "relonquelonst-lelonvelonl" ML rankelonr. Welon add thelon baselon scorelon helonrelon
      // whilelon adhoc scorelonrs arelon applielond in [[AdhocRankelonr]].
      addMlBaselonScorelonsForAdhocScorelonrs(candidatelons, relonquelonstRankelonrId, adhocScorelonrs)
    }
  }

  @VisiblelonForTelonsting
  privatelon[ranking] delonf addMlBaselonScorelonsForAdhocScorelonrs(
    candidatelons: Selonq[CandidatelonUselonr],
    relonquelonstRankelonrId: RankelonrId,
    adhocScorelonrs: Selonq[Scorelonr]
  ): Selonq[CandidatelonUselonr] = {
    candidatelons.map { candidatelon =>
      candidatelon.scorelons match {
        caselon Somelon(oldScorelons) =>
          // 1. Welon felontch thelon ML scorelon that is thelon basis of adhoc scorelons:
          val baselonMlScorelonOpt = Utils.gelontCandidatelonScorelonByRankelonrId(candidatelon, relonquelonstRankelonrId)

          // 2. For elonach adhoc scorelonr, welon copy thelon ML scorelon objelonct, changing only thelon ID and typelon.
          val nelonwScorelons = adhocScorelonrs flatMap { adhocScorelonr =>
            baselonMlScorelonOpt.map(
              _.copy(rankelonrId = Somelon(adhocScorelonr.id), scorelonTypelon = adhocScorelonr.scorelonTypelon))
          }

          // 3. Welon add thelon nelonw adhoc scorelon elonntrielons to thelon candidatelon.
          candidatelon.copy(scorelons = Somelon(oldScorelons.copy(scorelons = oldScorelons.scorelons ++ nelonwScorelons)))
        caselon _ =>
          // Sincelon thelonrelon is no baselon ML scorelon, thelonrelon should belon no adhoc scorelon modification as welonll.
          candidatelon
      }
    }
  }

  privatelon[this] delonf scorelon(
    dataReloncords: Selonq[DataReloncord],
    scorelonrs: Selonq[Scorelonr]
  ): Stitch[Selonq[Scorelons]] = {
    val scorelondRelonsponselon = scorelonrs.map { scorelonr =>
      StatsUtil.profilelonStitch(scorelonr.scorelon(dataReloncords), scorelonStat.scopelon(scorelonr.id.toString))
    }
    // If welon could scorelon a candidatelon with too many rankelonrs, it is likelonly to blow up thelon wholelon systelonm.
    // and fail back to delonfault production modelonl
    StatsUtil.profilelonStitch(Stitch.collelonct(scorelondRelonsponselon), scorelonStat).map { scorelonsByScorelonrId =>
      CollelonctionUtil.transposelonLazy(scorelonsByScorelonrId).map { scorelonsPelonrCandidatelon =>
        Scorelons(scorelonsPelonrCandidatelon)
      }
    }
  }

  // sort candidatelons using scorelon in delonscelonnding ordelonr
  privatelon[this] delonf sort(
    targelont: Targelont,
    candidatelons: Selonq[CandidatelonUselonr]
  ): Selonq[CandidatelonUselonr] = {
    candidatelons.sortBy(c => -c.scorelon.gelontOrelonlselon(MlRankelonr.DelonfaultScorelon))
  }

  privatelon[this] delonf scribelonCandidatelons(
    targelont: Targelont,
    candidatelons: Selonq[CandidatelonUselonr]
  ): Selonq[CandidatelonUselonr] = {
    val scribelonRankingInfo: Boolelonan = targelont.params(MlRankelonrParams.ScribelonRankingInfoInMlRankelonr)
    scribelonRankingInfo match {
      caselon truelon => Utils.addRankingInfo(candidatelons, "MlRankelonr")
      caselon falselon => candidatelons
    }
  }
}

objelonct MlRankelonr {
  // this is to elonnsurelon candidatelons with abselonnt scorelons arelon rankelond thelon last
  val DelonfaultScorelon: Doublelon = Doublelon.MinValuelon
}
