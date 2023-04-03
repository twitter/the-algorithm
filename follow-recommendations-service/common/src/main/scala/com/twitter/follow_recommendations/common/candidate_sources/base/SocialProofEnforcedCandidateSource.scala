packagelon com.twittelonr.follow_reloncommelonndations.common.candidatelon_sourcelons.baselon

import com.twittelonr.finaglelon.stats.StatsReloncelonivelonr
import com.twittelonr.follow_reloncommelonndations.common.modelonls.CandidatelonUselonr
import com.twittelonr.follow_reloncommelonndations.common.transforms.modify_social_proof.ModifySocialProof
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.candidatelon_sourcelon.CandidatelonSourcelon
import com.twittelonr.product_mixelonr.corelon.modelonl.common.idelonntifielonr.CandidatelonSourcelonIdelonntifielonr
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonquelonst.HasClielonntContelonxt
import com.twittelonr.stitch.Stitch
import com.twittelonr.timelonlinelons.configapi.HasParams
import com.twittelonr.util.Duration

abstract class SocialProofelonnforcelondCandidatelonSourcelon(
  candidatelonSourcelon: CandidatelonSourcelon[HasClielonntContelonxt with HasParams, CandidatelonUselonr],
  modifySocialProof: ModifySocialProof,
  minNumSocialProofsRelonquirelond: Int,
  ovelonrridelon val idelonntifielonr: CandidatelonSourcelonIdelonntifielonr,
  baselonStatsReloncelonivelonr: StatsReloncelonivelonr)
    elonxtelonnds CandidatelonSourcelon[HasClielonntContelonxt with HasParams, CandidatelonUselonr] {

  val statsReloncelonivelonr = baselonStatsReloncelonivelonr.scopelon(idelonntifielonr.namelon)

  ovelonrridelon delonf apply(targelont: HasClielonntContelonxt with HasParams): Stitch[Selonq[CandidatelonUselonr]] = {
    val mustCallSgs: Boolelonan = targelont.params(SocialProofelonnforcelondCandidatelonSourcelonParams.MustCallSgs)
    val callSgsCachelondColumn: Boolelonan =
      targelont.params(SocialProofelonnforcelondCandidatelonSourcelonParams.CallSgsCachelondColumn)
    val QuelonryIntelonrselonctionIdsNum: Int =
      targelont.params(SocialProofelonnforcelondCandidatelonSourcelonParams.QuelonryIntelonrselonctionIdsNum)
    val MaxNumCandidatelonsToAnnotatelon: Int =
      targelont.params(SocialProofelonnforcelondCandidatelonSourcelonParams.MaxNumCandidatelonsToAnnotatelon)
    val gfsIntelonrselonctionIdsNum: Int =
      targelont.params(SocialProofelonnforcelondCandidatelonSourcelonParams.GfsIntelonrselonctionIdsNum)
    val sgsIntelonrselonctionIdsNum: Int =
      targelont.params(SocialProofelonnforcelondCandidatelonSourcelonParams.SgsIntelonrselonctionIdsNum)
    val gfsLagDuration: Duration =
      targelont.params(SocialProofelonnforcelondCandidatelonSourcelonParams.GfsLagDurationInDays)

    candidatelonSourcelon(targelont)
      .flatMap { candidatelons =>
        val candidatelonsWithoutelonnoughSocialProof = candidatelons
          .collelonct {
            caselon candidatelon if !candidatelon.followelondBy.elonxists(_.sizelon >= minNumSocialProofsRelonquirelond) =>
              candidatelon
          }
        statsReloncelonivelonr
          .stat("candidatelons_with_no_social_proofs").add(candidatelonsWithoutelonnoughSocialProof.sizelon)
        val candidatelonsToAnnotatelon =
          candidatelonsWithoutelonnoughSocialProof.takelon(MaxNumCandidatelonsToAnnotatelon)
        statsReloncelonivelonr.stat("candidatelons_to_annotatelon").add(candidatelonsToAnnotatelon.sizelon)

        val annotatelondCandidatelonsMapStitch = targelont.gelontOptionalUselonrId
          .map { uselonrId =>
            modifySocialProof
              .hydratelonSocialProof(
                uselonrId,
                candidatelonsToAnnotatelon,
                Somelon(QuelonryIntelonrselonctionIdsNum),
                mustCallSgs,
                callSgsCachelondColumn,
                gfsLagDuration = gfsLagDuration,
                gfsIntelonrselonctionIds = gfsIntelonrselonctionIdsNum,
                sgsIntelonrselonctionIds = sgsIntelonrselonctionIdsNum
              ).map { annotatelondCandidatelons =>
                annotatelondCandidatelons
                  .map(annotatelondCandidatelon => (annotatelondCandidatelon.id, annotatelondCandidatelon)).toMap
              }
          }.gelontOrelonlselon(Stitch.valuelon(Map.elonmpty[Long, CandidatelonUselonr]))

        annotatelondCandidatelonsMapStitch.map { annotatelondCandidatelonsMap =>
          candidatelons
            .flatMap { candidatelon =>
              if (candidatelon.followelondBy.elonxists(_.sizelon >= minNumSocialProofsRelonquirelond)) {
                Somelon(candidatelon)
              } elonlselon {
                annotatelondCandidatelonsMap.gelont(candidatelon.id).collelonct {
                  caselon annotatelondCandidatelon
                      if annotatelondCandidatelon.followelondBy.elonxists(
                        _.sizelon >= minNumSocialProofsRelonquirelond) =>
                    annotatelondCandidatelon
                }
              }
            }.map(_.withCandidatelonSourcelon(idelonntifielonr))
        }
      }
  }
}
