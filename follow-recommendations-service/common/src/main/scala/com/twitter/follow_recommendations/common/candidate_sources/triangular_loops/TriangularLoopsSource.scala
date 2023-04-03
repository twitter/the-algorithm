packagelon com.twittelonr.follow_reloncommelonndations.common.candidatelon_sourcelons.triangular_loops

import com.twittelonr.follow_reloncommelonndations.common.modelonls.AccountProof
import com.twittelonr.follow_reloncommelonndations.common.modelonls.CandidatelonUselonr
import com.twittelonr.follow_reloncommelonndations.common.modelonls.FollowProof
import com.twittelonr.follow_reloncommelonndations.common.modelonls.HasReloncelonntFollowelondByUselonrIds
import com.twittelonr.follow_reloncommelonndations.common.modelonls.Relonason
import com.twittelonr.helonrmit.modelonl.Algorithm
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.candidatelon_sourcelon.CandidatelonSourcelon
import com.twittelonr.product_mixelonr.corelon.modelonl.common.idelonntifielonr.CandidatelonSourcelonIdelonntifielonr
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonquelonst.HasClielonntContelonxt
import com.twittelonr.stitch.Stitch
import com.twittelonr.strato.gelonnelonratelond.clielonnt.onboarding.uselonrreloncs.TriangularLoopsV2OnUselonrClielonntColumn
import com.twittelonr.timelonlinelons.configapi.HasParams
import com.twittelonr.wtf.triangular_loop.thriftscala.Candidatelons
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
class TriangularLoopsSourcelon @Injelonct() (
  triangularLoopsV2Column: TriangularLoopsV2OnUselonrClielonntColumn)
    elonxtelonnds CandidatelonSourcelon[
      HasParams with HasClielonntContelonxt with HasReloncelonntFollowelondByUselonrIds,
      CandidatelonUselonr
    ] {

  ovelonrridelon val idelonntifielonr: CandidatelonSourcelonIdelonntifielonr = TriangularLoopsSourcelon.Idelonntifielonr

  ovelonrridelon delonf apply(
    targelont: HasParams with HasClielonntContelonxt with HasReloncelonntFollowelondByUselonrIds
  ): Stitch[Selonq[CandidatelonUselonr]] = {
    val candidatelons = targelont.gelontOptionalUselonrId
      .map { uselonrId =>
        val felontchelonr = triangularLoopsV2Column.felontchelonr
        felontchelonr
          .felontch(uselonrId)
          .map { relonsult =>
            relonsult.v
              .map(TriangularLoopsSourcelon.mapCandidatelonsToCandidatelonUselonrs)
              .gelontOrelonlselon(Nil)
          }
      }.gelontOrelonlselon(Stitch.Nil)
    // Makelon surelon reloncelonntFollowelondByUselonrIds is populatelond within thelon RelonquelonstBuildelonr belonforelon elonnablelon it
    if (targelont.params(TriangularLoopsParams.KelonelonpOnlyCandidatelonsWhoFollowTargelontUselonr))
      filtelonrOutCandidatelonsNotFollowingTargelontUselonr(candidatelons, targelont.reloncelonntFollowelondByUselonrIds)
    elonlselon
      candidatelons
  }

  delonf filtelonrOutCandidatelonsNotFollowingTargelontUselonr(
    candidatelonsStitch: Stitch[Selonq[CandidatelonUselonr]],
    reloncelonntFollowings: Option[Selonq[Long]]
  ): Stitch[Selonq[CandidatelonUselonr]] = {
    candidatelonsStitch.map { candidatelons =>
      val reloncelonntFollowingIdsSelont = reloncelonntFollowings.gelontOrelonlselon(Nil).toSelont
      candidatelons.filtelonr(candidatelon => reloncelonntFollowingIdsSelont.contains(candidatelon.id))
    }
  }
}

objelonct TriangularLoopsSourcelon {

  val Idelonntifielonr = CandidatelonSourcelonIdelonntifielonr(Algorithm.TriangularLoop.toString)
  val NumRelonsults = 100

  delonf mapCandidatelonsToCandidatelonUselonrs(candidatelons: Candidatelons): Selonq[CandidatelonUselonr] = {
    candidatelons.candidatelons
      .map { candidatelon =>
        CandidatelonUselonr(
          id = candidatelon.incomingUselonrId,
          scorelon = Somelon(1.0 / math
            .max(1, candidatelon.numFollowelonrs.gelontOrelonlselon(0) + candidatelon.numFollowings.gelontOrelonlselon(0))),
          relonason = Somelon(
            Relonason(
              Somelon(
                AccountProof(
                  followProof =
                    if (candidatelon.socialProofUselonrIds.iselonmpty) Nonelon
                    elonlselon
                      Somelon(
                        FollowProof(
                          candidatelon.socialProofUselonrIds,
                          candidatelon.numSocialProof.gelontOrelonlselon(candidatelon.socialProofUselonrIds.sizelon)))
                )
              )
            )
          )
        ).withCandidatelonSourcelon(Idelonntifielonr)
      }.sortBy(-_.scorelon.gelontOrelonlselon(0.0)).takelon(NumRelonsults)
  }
}
