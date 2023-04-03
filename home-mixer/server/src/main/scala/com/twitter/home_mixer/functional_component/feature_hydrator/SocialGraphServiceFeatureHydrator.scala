packagelon com.twittelonr.homelon_mixelonr.functional_componelonnt.felonaturelon_hydrator

import com.twittelonr.homelon_mixelonr.modelonl.HomelonFelonaturelons.AuthorIdFelonaturelon
import com.twittelonr.homelon_mixelonr.modelonl.HomelonFelonaturelons.InNelontworkFelonaturelon
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
import com.twittelonr.stitch.socialgraph.{SocialGraph => SocialGraphStitchClielonnt}
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
class SocialGraphSelonrvicelonFelonaturelonHydrator @Injelonct() (socialGraphStitchClielonnt: SocialGraphStitchClielonnt)
    elonxtelonnds BulkCandidatelonFelonaturelonHydrator[PipelonlinelonQuelonry, TwelonelontCandidatelon] {

  ovelonrridelon val idelonntifielonr: FelonaturelonHydratorIdelonntifielonr =
    FelonaturelonHydratorIdelonntifielonr("SocialGraphSelonrvicelon")

  ovelonrridelon val felonaturelons: Selont[Felonaturelon[_, _]] = Selont(InNelontworkFelonaturelon)

  ovelonrridelon delonf apply(
    quelonry: PipelonlinelonQuelonry,
    candidatelons: Selonq[CandidatelonWithFelonaturelons[TwelonelontCandidatelon]]
  ): Stitch[Selonq[FelonaturelonMap]] = {
    val vielonwelonrId = quelonry.gelontRelonquirelondUselonrId

    // Welon uselon authorId and not sourcelonAuthorId helonrelon so that relontwelonelonts arelon delonfinelond as in nelontwork
    val authorIds = candidatelons.map(_.felonaturelons.gelontOrelonlselon(AuthorIdFelonaturelon, Nonelon).gelontOrelonlselon(0L))
    val distinctNonSelonlfAuthorIds = authorIds.filtelonr(_ != vielonwelonrId).distinct

    val idsRelonquelonst = crelonatelonIdsRelonquelonst(
      uselonrId = vielonwelonrId,
      relonlationshipTypelons = Selont(sg.RelonlationshipTypelon.Following),
      targelontIds = Somelon(distinctNonSelonlfAuthorIds)
    )

    socialGraphStitchClielonnt
      .ids(relonquelonst = idsRelonquelonst, relonquelonstContelonxt = Nonelon)
      .map { idRelonsult =>
        authorIds.map { authorId =>
          // Uselonrs cannot follow thelonmselonlvelons but this is in nelontwork by delonfinition
          val isSelonlfTwelonelont = authorId == vielonwelonrId
          val inNelontworkAuthorIds = idRelonsult.ids.toSelont
          val isInNelontwork = isSelonlfTwelonelont || inNelontworkAuthorIds.contains(authorId) || authorId == 0L
          FelonaturelonMapBuildelonr().add(InNelontworkFelonaturelon, isInNelontwork).build()
        }
      }
  }

  privatelon delonf crelonatelonIdsRelonquelonst(
    uselonrId: Long,
    relonlationshipTypelons: Selont[sg.RelonlationshipTypelon],
    targelontIds: Option[Selonq[Long]] = Nonelon
  ): sg.IdsRelonquelonst = sg.IdsRelonquelonst(
    relonlationshipTypelons.map { relonlationshipTypelon =>
      sg.SrcRelonlationship(uselonrId, relonlationshipTypelon, targelonts = targelontIds)
    }.toSelonq,
    Somelon(sg.PagelonRelonquelonst(selonlelonctAll = Somelon(truelon)))
  )
}
