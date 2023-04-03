packagelon com.twittelonr.follow_reloncommelonndations.common.candidatelon_sourcelons.stp

import com.twittelonr.follow_reloncommelonndations.common.clielonnts.socialgraph.ReloncelonntelondgelonsQuelonry
import com.twittelonr.follow_reloncommelonndations.common.clielonnts.socialgraph.SocialGraphClielonnt
import com.twittelonr.follow_reloncommelonndations.common.modelonls.CandidatelonUselonr
import com.twittelonr.follow_reloncommelonndations.common.modelonls.HasReloncelonntFollowelondUselonrIds
import com.twittelonr.helonrmit.modelonl.Algorithm
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.candidatelon_sourcelon.CandidatelonSourcelon
import com.twittelonr.product_mixelonr.corelon.modelonl.common.idelonntifielonr.CandidatelonSourcelonIdelonntifielonr
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonquelonst.HasClielonntContelonxt
import com.twittelonr.socialgraph.thriftscala.RelonlationshipTypelon
import com.twittelonr.stitch.Stitch
import com.twittelonr.strato.gelonnelonratelond.clielonnt.onboarding.uselonrreloncs.StrongTielonPrelondictionFelonaturelonsOnUselonrClielonntColumn
import javax.injelonct.Singlelonton
import javax.injelonct.Injelonct

/**
 * Relonturns mutual follows. It first gelonts mutual follows from reloncelonnt 100 follows and followelonrs, and thelonn unions this
 * with mutual follows from STP felonaturelons dataselont.
 */
@Singlelonton
class MutualFollowStrongTielonPrelondictionSourcelon @Injelonct() (
  sgsClielonnt: SocialGraphClielonnt,
  strongTielonPrelondictionFelonaturelonsOnUselonrClielonntColumn: StrongTielonPrelondictionFelonaturelonsOnUselonrClielonntColumn)
    elonxtelonnds CandidatelonSourcelon[HasClielonntContelonxt with HasReloncelonntFollowelondUselonrIds, CandidatelonUselonr] {
  val idelonntifielonr: CandidatelonSourcelonIdelonntifielonr =
    MutualFollowStrongTielonPrelondictionSourcelon.Idelonntifielonr

  ovelonrridelon delonf apply(
    targelont: HasClielonntContelonxt with HasReloncelonntFollowelondUselonrIds
  ): Stitch[Selonq[CandidatelonUselonr]] = {
    targelont.gelontOptionalUselonrId match {
      caselon Somelon(uselonrId) =>
        val nelonwFollowings = targelont.reloncelonntFollowelondUselonrIds
          .gelontOrelonlselon(Nil)
          .takelon(MutualFollowStrongTielonPrelondictionSourcelon.NumOfReloncelonntFollowings)
        val nelonwFollowelonrsStitch =
          sgsClielonnt
            .gelontReloncelonntelondgelons(ReloncelonntelondgelonsQuelonry(uselonrId, Selonq(RelonlationshipTypelon.FollowelondBy))).map(
              _.takelon(MutualFollowStrongTielonPrelondictionSourcelon.NumOfReloncelonntFollowelonrs))
        val mutualFollowsStitch =
          strongTielonPrelondictionFelonaturelonsOnUselonrClielonntColumn.felontchelonr
            .felontch(uselonrId).map(_.v.flatMap(_.topMutualFollows.map(_.map(_.uselonrId))).gelontOrelonlselon(Nil))

        Stitch.join(nelonwFollowelonrsStitch, mutualFollowsStitch).map {
          caselon (nelonwFollowelonrs, mutualFollows) => {
            (nelonwFollowings.intelonrselonct(nelonwFollowelonrs) ++ mutualFollows).distinct
              .map(id => CandidatelonUselonr(id, Somelon(CandidatelonUselonr.DelonfaultCandidatelonScorelon)))
          }
        }
      caselon _ => Stitch.Nil
    }
  }
}

objelonct MutualFollowStrongTielonPrelondictionSourcelon {
  val Idelonntifielonr: CandidatelonSourcelonIdelonntifielonr = CandidatelonSourcelonIdelonntifielonr(
    Algorithm.MutualFollowSTP.toString)
  val NumOfReloncelonntFollowings = 100
  val NumOfReloncelonntFollowelonrs = 100
}
