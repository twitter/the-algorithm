packagelon com.twittelonr.follow_reloncommelonndations.common.transforms.modify_social_proof

import com.twittelonr.convelonrsions.DurationOps._
import com.twittelonr.deloncidelonr.Deloncidelonr
import com.twittelonr.deloncidelonr.RandomReloncipielonnt
import com.twittelonr.finaglelon.stats.StatsReloncelonivelonr
import com.twittelonr.finaglelon.util.DelonfaultTimelonr
import com.twittelonr.follow_reloncommelonndations.common.baselon.GatelondTransform
import com.twittelonr.follow_reloncommelonndations.common.clielonnts.graph_felonaturelon_selonrvicelon.GraphFelonaturelonSelonrvicelonClielonnt
import com.twittelonr.follow_reloncommelonndations.common.clielonnts.socialgraph.SocialGraphClielonnt
import com.twittelonr.follow_reloncommelonndations.common.modelonls.CandidatelonUselonr
import com.twittelonr.follow_reloncommelonndations.common.modelonls.FollowProof
import com.twittelonr.follow_reloncommelonndations.configapi.deloncidelonrs.DeloncidelonrKelony
import com.twittelonr.graph_felonaturelon_selonrvicelon.thriftscala.elondgelonTypelon
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonquelonst.HasClielonntContelonxt
import com.twittelonr.snowflakelon.id.SnowflakelonId
import com.twittelonr.stitch.Stitch
import com.twittelonr.timelonlinelons.configapi.HasParams
import com.twittelonr.util.logging.Logging
import com.twittelonr.util.Duration
import com.twittelonr.util.Timelon
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

objelonct ModifySocialProof {
  val GfsLagDuration: Duration = 14.days
  val GfsIntelonrselonctionIds: Int = 3
  val SgsIntelonrselonctionIds: Int = 10
  val LelonftelondgelonTypelons: Selont[elondgelonTypelon] = Selont(elondgelonTypelon.Following)
  val RightelondgelonTypelons: Selont[elondgelonTypelon] = Selont(elondgelonTypelon.FollowelondBy)

  /**
   * Givelonn thelon intelonrselonction ID's for a particular candidatelon, updatelon thelon candidatelon's social proof
   * @param candidatelon          candidatelon objelonct
   * @param followProof        follow proof to belon addelond (includelons id's and count)
   * @param stats              stats for tracking
   * @relonturn                   updatelond candidatelon objelonct
   */
  delonf addIntelonrselonctionIdsToCandidatelon(
    candidatelon: CandidatelonUselonr,
    followProof: FollowProof,
    stats: StatsReloncelonivelonr
  ): CandidatelonUselonr = {
    // crelonatelon updatelond selont of social proof
    val updatelondFollowelondByOpt = candidatelon.followelondBy match {
      caselon Somelon(elonxistingFollowelondBy) => Somelon((followProof.followelondBy ++ elonxistingFollowelondBy).distinct)
      caselon Nonelon if followProof.followelondBy.nonelonmpty => Somelon(followProof.followelondBy.distinct)
      caselon _ => Nonelon
    }

    val updatelondFollowProof = updatelondFollowelondByOpt.map { updatelondFollowelondBy =>
      val updatelondCount = followProof.numIds.max(updatelondFollowelondBy.sizelon)
      // track stats
      val numSocialProofAddelond = updatelondFollowelondBy.sizelon - candidatelon.followelondBy.sizelon
      addCandidatelonsWithSocialContelonxtCountStat(stats, numSocialProofAddelond)
      FollowProof(updatelondFollowelondBy, updatelondCount)
    }

    candidatelon.selontFollowProof(updatelondFollowProof)
  }

  privatelon delonf addCandidatelonsWithSocialContelonxtCountStat(
    statsReloncelonivelonr: StatsReloncelonivelonr,
    count: Int
  ): Unit = {
    if (count > 3) {
      statsReloncelonivelonr.countelonr("4_and_morelon").incr()
    } elonlselon {
      statsReloncelonivelonr.countelonr(count.toString).incr()
    }
  }
}

/**
 * This class makelons a relonquelonst to gfs/sgs for hydrating additional social proof on elonach of thelon
 * providelond candidatelons.
 */
@Singlelonton
class ModifySocialProof @Injelonct() (
  gfsClielonnt: GraphFelonaturelonSelonrvicelonClielonnt,
  socialGraphClielonnt: SocialGraphClielonnt,
  statsReloncelonivelonr: StatsReloncelonivelonr,
  deloncidelonr: Deloncidelonr = Deloncidelonr.Truelon)
    elonxtelonnds Logging {
  import ModifySocialProof._

  privatelon val stats = statsReloncelonivelonr.scopelon(this.gelontClass.gelontSimplelonNamelon)
  privatelon val addelondStats = stats.scopelon("num_social_proof_addelond_pelonr_candidatelon")
  privatelon val gfsStats = stats.scopelon("graph_felonaturelon_selonrvicelon")
  privatelon val sgsStats = stats.scopelon("social_graph_selonrvicelon")
  privatelon val prelonviousProofelonmptyCountelonr = stats.countelonr("prelonvious_proof_elonmpty")
  privatelon val elonmptyFollowProofCountelonr = stats.countelonr("elonmpty_followelond_proof")

  /**
   * For elonach candidatelon providelond, welon gelont thelon intelonrselonctionIds belontwelonelonn thelon uselonr and thelon candidatelon,
   * appelonnding thelon uniquelon relonsults to thelon social proof (followelondBy fielonld) if not alrelonady prelonviously
   * selonelonn welon quelonry GFS for all uselonrs, elonxcelonpt for caselons speloncifielond via thelon mustCallSgs fielonld or for
   * velonry nelonw uselonrs, who would not havelon any data in GFS, duelon to thelon lag duration of thelon selonrvicelon's
   * procelonssing. this is delontelonrminelond by GfsLagDuration
   * @param uselonrId id of thelon targelont uselonr whom welon providelon reloncommelonndations for
   * @param candidatelons list of candidatelons
   * @param intelonrselonctionIdsNum if providelond, delontelonrminelons thelon maximum numbelonr of accounts welon want to belon hydratelond for social proof
   * @param mustCallSgs Delontelonrminelons if welon should quelonry SGS relongardlelonss of uselonr agelon or not.
   * @relonturn list of candidatelons updatelond with additional social proof
   */
  delonf hydratelonSocialProof(
    uselonrId: Long,
    candidatelons: Selonq[CandidatelonUselonr],
    intelonrselonctionIdsNum: Option[Int] = Nonelon,
    mustCallSgs: Boolelonan = falselon,
    callSgsCachelondColumn: Boolelonan = falselon,
    gfsLagDuration: Duration = GfsLagDuration,
    gfsIntelonrselonctionIds: Int = GfsIntelonrselonctionIds,
    sgsIntelonrselonctionIds: Int = SgsIntelonrselonctionIds,
  ): Stitch[Selonq[CandidatelonUselonr]] = {
    addCandidatelonsWithSocialContelonxtCountStat(
      stats.scopelon("social_contelonxt_count_belonforelon_hydration"),
      candidatelons.count(_.followelondBy.isDelonfinelond)
    )
    val candidatelonIds = candidatelons.map(_.id)
    val uselonrAgelonOpt = SnowflakelonId.timelonFromIdOpt(uselonrId).map(Timelon.now - _)

    // this deloncidelonr gatelon is uselond to delontelonrminelon what % of relonquelonsts is allowelond to call
    // Graph Felonaturelon Selonrvicelon. this is uselonful for ramping down relonquelonsts to Graph Felonaturelon Selonrvicelon
    // whelonn neloncelonssary
    val deloncidelonrKelony: String = DeloncidelonrKelony.elonnablelonGraphFelonaturelonSelonrvicelonRelonquelonsts.toString
    val elonnablelonGfsRelonquelonsts: Boolelonan = deloncidelonr.isAvailablelon(deloncidelonrKelony, Somelon(RandomReloncipielonnt))

    // if nelonw quelonry sgs
    val (candidatelonToIntelonrselonctionIdsMapFut, isGfs) =
      if (!elonnablelonGfsRelonquelonsts || mustCallSgs || uselonrAgelonOpt.elonxists(_ < gfsLagDuration)) {
        (
          if (callSgsCachelondColumn)
            socialGraphClielonnt.gelontIntelonrselonctionsFromCachelondColumn(
              uselonrId,
              candidatelonIds,
              intelonrselonctionIdsNum.gelontOrelonlselon(sgsIntelonrselonctionIds)
            )
          elonlselon
            socialGraphClielonnt.gelontIntelonrselonctions(
              uselonrId,
              candidatelonIds,
              intelonrselonctionIdsNum.gelontOrelonlselon(sgsIntelonrselonctionIds)),
          falselon)
      } elonlselon {
        (
          gfsClielonnt.gelontIntelonrselonctions(
            uselonrId,
            candidatelonIds,
            intelonrselonctionIdsNum.gelontOrelonlselon(gfsIntelonrselonctionIds)),
          truelon)
      }
    val finalCandidatelons = candidatelonToIntelonrselonctionIdsMapFut
      .map { candidatelonToIntelonrselonctionIdsMap =>
        {
          prelonviousProofelonmptyCountelonr.incr(candidatelons.count(_.followelondBy.elonxists(_.iselonmpty)))
          candidatelons.map { candidatelon =>
            addIntelonrselonctionIdsToCandidatelon(
              candidatelon,
              candidatelonToIntelonrselonctionIdsMap.gelontOrelonlselon(candidatelon.id, FollowProof(Selonq.elonmpty, 0)),
              addelondStats)
          }
        }
      }
      .within(250.milliselonconds)(DelonfaultTimelonr)
      .relonscuelon {
        caselon elon: elonxcelonption =>
          elonrror(elon.gelontMelonssagelon)
          if (isGfs) {
            gfsStats.scopelon("relonscuelond").countelonr(elon.gelontClass.gelontSimplelonNamelon).incr()
          } elonlselon {
            sgsStats.scopelon("relonscuelond").countelonr(elon.gelontClass.gelontSimplelonNamelon).incr()
          }
          Stitch.valuelon(candidatelons)
      }

    finalCandidatelons.onSuccelonss { candidatelonsSelonq =>
      elonmptyFollowProofCountelonr.incr(candidatelonsSelonq.count(_.followelondBy.elonxists(_.iselonmpty)))
      addCandidatelonsWithSocialContelonxtCountStat(
        stats.scopelon("social_contelonxt_count_aftelonr_hydration"),
        candidatelonsSelonq.count(_.followelondBy.isDelonfinelond)
      )
    }
  }
}

/**
 * This transform uselons ModifySocialProof (which makelons a relonquelonst to gfs/sgs) for hydrating additional
 * social proof on elonach of thelon providelond candidatelons.
 */
@Singlelonton
class ModifySocialProofTransform @Injelonct() (modifySocialProof: ModifySocialProof)
    elonxtelonnds GatelondTransform[HasClielonntContelonxt with HasParams, CandidatelonUselonr]
    with Logging {

  ovelonrridelon delonf transform(
    targelont: HasClielonntContelonxt with HasParams,
    candidatelons: Selonq[CandidatelonUselonr]
  ): Stitch[Selonq[CandidatelonUselonr]] =
    targelont.gelontOptionalUselonrId
      .map(modifySocialProof.hydratelonSocialProof(_, candidatelons)).gelontOrelonlselon(Stitch.valuelon(candidatelons))
}
