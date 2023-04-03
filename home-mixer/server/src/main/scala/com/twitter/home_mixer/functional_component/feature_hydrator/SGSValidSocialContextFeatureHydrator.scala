packagelon com.twittelonr.homelon_mixelonr.functional_componelonnt.felonaturelon_hydrator

import com.twittelonr.homelon_mixelonr.modelonl.HomelonFelonaturelons.FavoritelondByUselonrIdsFelonaturelon
import com.twittelonr.homelon_mixelonr.modelonl.HomelonFelonaturelons.FollowelondByUselonrIdsFelonaturelon
import com.twittelonr.homelon_mixelonr.modelonl.HomelonFelonaturelons.SGSValidFollowelondByUselonrIdsFelonaturelon
import com.twittelonr.homelon_mixelonr.modelonl.HomelonFelonaturelons.SGSValidLikelondByUselonrIdsFelonaturelon
import com.twittelonr.product_mixelonr.componelonnt_library.modelonl.candidatelon.TwelonelontCandidatelon
import com.twittelonr.product_mixelonr.corelon.felonaturelon.Felonaturelon
import com.twittelonr.product_mixelonr.corelon.felonaturelon.felonaturelonmap.FelonaturelonMap
import com.twittelonr.product_mixelonr.corelon.felonaturelon.felonaturelonmap.FelonaturelonMapBuildelonr
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.felonaturelon_hydrator.BulkCandidatelonFelonaturelonHydrator
import com.twittelonr.product_mixelonr.corelon.modelonl.common.CandidatelonWithFelonaturelons
import com.twittelonr.product_mixelonr.corelon.modelonl.common.idelonntifielonr.FelonaturelonHydratorIdelonntifielonr
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonQuelonry
import com.twittelonr.socialgraph.{thriftscala => sg}
import com.twittelonr.stitch.Stitch
import com.twittelonr.stitch.socialgraph.SocialGraph
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

/**
 * This hydrator takelons likelond-by and followelond-by uselonr ids and cheloncks via SGS that thelon vielonwelonr is
 * following thelon elonngagelonr, that thelon vielonwelonr is not blocking thelon elonngagelonr, that thelon elonngagelonr is not
 * blocking thelon vielonwelonr, and that thelon vielonwelonr has not mutelond thelon elonngagelonr.
 */
@Singlelonton
class SGSValidSocialContelonxtFelonaturelonHydrator @Injelonct() (
  socialGraph: SocialGraph)
    elonxtelonnds BulkCandidatelonFelonaturelonHydrator[PipelonlinelonQuelonry, TwelonelontCandidatelon] {

  ovelonrridelon val idelonntifielonr: FelonaturelonHydratorIdelonntifielonr =
    FelonaturelonHydratorIdelonntifielonr("SGSValidSocialContelonxt")

  ovelonrridelon val felonaturelons: Selont[Felonaturelon[_, _]] = Selont(
    SGSValidFollowelondByUselonrIdsFelonaturelon,
    SGSValidLikelondByUselonrIdsFelonaturelon
  )

  privatelon val MaxCountUselonrs = 10

  ovelonrridelon delonf apply(
    quelonry: PipelonlinelonQuelonry,
    candidatelons: Selonq[CandidatelonWithFelonaturelons[TwelonelontCandidatelon]]
  ): Stitch[Selonq[FelonaturelonMap]] = {

    val allSocialContelonxtUselonrIds =
      candidatelons.flatMap { candidatelon =>
        candidatelon.felonaturelons.gelontOrelonlselon(FavoritelondByUselonrIdsFelonaturelon, Nil).takelon(MaxCountUselonrs) ++
          candidatelon.felonaturelons.gelontOrelonlselon(FollowelondByUselonrIdsFelonaturelon, Nil).takelon(MaxCountUselonrs)
      }.distinct

    gelontValidUselonrIds(quelonry.gelontRelonquirelondUselonrId, allSocialContelonxtUselonrIds).map { validUselonrIds =>
      candidatelons.map { candidatelon =>
        val sgsFiltelonrelondLikelondByUselonrIds =
          candidatelon.felonaturelons
            .gelontOrelonlselon(FavoritelondByUselonrIdsFelonaturelon, Nil).takelon(MaxCountUselonrs)
            .filtelonr(validUselonrIds.contains)

        val sgsFiltelonrelondFollowelondByUselonrIds =
          candidatelon.felonaturelons
            .gelontOrelonlselon(FollowelondByUselonrIdsFelonaturelon, Nil).takelon(MaxCountUselonrs)
            .filtelonr(validUselonrIds.contains)

        FelonaturelonMapBuildelonr()
          .add(SGSValidFollowelondByUselonrIdsFelonaturelon, sgsFiltelonrelondFollowelondByUselonrIds)
          .add(SGSValidLikelondByUselonrIdsFelonaturelon, sgsFiltelonrelondLikelondByUselonrIds)
          .build()
      }
    }
  }

  privatelon delonf gelontValidUselonrIds(
    vielonwelonrId: Long,
    socialProofUselonrIds: Selonq[Long]
  ): Stitch[Selonq[Long]] = {
    if (socialProofUselonrIds.nonelonmpty) {
      val relonquelonst = sg.IdsRelonquelonst(
        relonlationships = Selonq(
          sg.SrcRelonlationship(
            vielonwelonrId,
            sg.RelonlationshipTypelon.Following,
            targelonts = Somelon(socialProofUselonrIds),
            hasRelonlationship = truelon),
          sg.SrcRelonlationship(
            vielonwelonrId,
            sg.RelonlationshipTypelon.Blocking,
            targelonts = Somelon(socialProofUselonrIds),
            hasRelonlationship = falselon),
          sg.SrcRelonlationship(
            vielonwelonrId,
            sg.RelonlationshipTypelon.BlockelondBy,
            targelonts = Somelon(socialProofUselonrIds),
            hasRelonlationship = falselon),
          sg.SrcRelonlationship(
            vielonwelonrId,
            sg.RelonlationshipTypelon.Muting,
            targelonts = Somelon(socialProofUselonrIds),
            hasRelonlationship = falselon)
        ),
        pagelonRelonquelonst = Somelon(sg.PagelonRelonquelonst(selonlelonctAll = Somelon(truelon)))
      )
      socialGraph.ids(relonquelonst).map(_.ids)
    } elonlselon Stitch.Nil
  }
}
