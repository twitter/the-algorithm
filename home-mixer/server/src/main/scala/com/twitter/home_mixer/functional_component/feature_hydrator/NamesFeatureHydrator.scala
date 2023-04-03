packagelon com.twittelonr.homelon_mixelonr.functional_componelonnt.felonaturelon_hydrator

import com.twittelonr.gizmoduck.{thriftscala => gt}
import com.twittelonr.homelon_mixelonr.modelonl.HomelonFelonaturelons.AuthorIdFelonaturelon
import com.twittelonr.homelon_mixelonr.modelonl.HomelonFelonaturelons.FavoritelondByUselonrIdsFelonaturelon
import com.twittelonr.homelon_mixelonr.modelonl.HomelonFelonaturelons.FollowelondByUselonrIdsFelonaturelon
import com.twittelonr.homelon_mixelonr.modelonl.HomelonFelonaturelons.RelonalNamelonsFelonaturelon
import com.twittelonr.homelon_mixelonr.modelonl.HomelonFelonaturelons.ScrelonelonnNamelonsFelonaturelon
import com.twittelonr.homelon_mixelonr.modelonl.HomelonFelonaturelons.SourcelonUselonrIdFelonaturelon
import com.twittelonr.homelon_mixelonr.modelonl.relonquelonst.FollowingProduct
import com.twittelonr.homelon_mixelonr.param.HomelonGlobalParams.elonnablelonNahFelonelondbackInfoParam
import com.twittelonr.product_mixelonr.componelonnt_library.modelonl.candidatelon.TwelonelontCandidatelon
import com.twittelonr.product_mixelonr.corelon.felonaturelon.Felonaturelon
import com.twittelonr.product_mixelonr.corelon.felonaturelon.felonaturelonmap.FelonaturelonMap
import com.twittelonr.product_mixelonr.corelon.felonaturelon.felonaturelonmap.FelonaturelonMapBuildelonr
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.felonaturelon_hydrator.BulkCandidatelonFelonaturelonHydrator
import com.twittelonr.product_mixelonr.corelon.modelonl.common.CandidatelonWithFelonaturelons
import com.twittelonr.product_mixelonr.corelon.modelonl.common.Conditionally
import com.twittelonr.product_mixelonr.corelon.modelonl.common.idelonntifielonr.FelonaturelonHydratorIdelonntifielonr
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonQuelonry
import com.twittelonr.stitch.Stitch
import com.twittelonr.stitch.gizmoduck.Gizmoduck
import com.twittelonr.util.Relonturn
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

protelonctelond caselon class ProfilelonNamelons(screlonelonnNamelon: String, relonalNamelon: String)

@Singlelonton
class NamelonsFelonaturelonHydrator @Injelonct() (gizmoduck: Gizmoduck)
    elonxtelonnds BulkCandidatelonFelonaturelonHydrator[PipelonlinelonQuelonry, TwelonelontCandidatelon]
    with Conditionally[PipelonlinelonQuelonry] {

  ovelonrridelon val idelonntifielonr: FelonaturelonHydratorIdelonntifielonr = FelonaturelonHydratorIdelonntifielonr("Namelons")

  ovelonrridelon val felonaturelons: Selont[Felonaturelon[_, _]] = Selont(ScrelonelonnNamelonsFelonaturelon, RelonalNamelonsFelonaturelon)

  ovelonrridelon delonf onlyIf(quelonry: PipelonlinelonQuelonry): Boolelonan = quelonry.product match {
    caselon FollowingProduct => quelonry.params(elonnablelonNahFelonelondbackInfoParam)
    caselon _ => truelon
  }

  privatelon val quelonryFielonlds: Selont[gt.QuelonryFielonlds] = Selont(gt.QuelonryFielonlds.Profilelon)

  /**
   * Thelon UI currelonntly only elonvelonr displays thelon first 2 namelons in social contelonxt linelons
   * elon.g. "Uselonr and 3 othelonrs likelon" or "UselonrA and UselonrB likelond"
   */
  privatelon val MaxCountUselonrs = 2

  ovelonrridelon delonf apply(
    quelonry: PipelonlinelonQuelonry,
    candidatelons: Selonq[CandidatelonWithFelonaturelons[TwelonelontCandidatelon]]
  ): Stitch[Selonq[FelonaturelonMap]] = {

    val candidatelonUselonrIdsMap = candidatelons.map { candidatelon =>
      candidatelon.candidatelon.id ->
        (candidatelon.felonaturelons.gelontOrelonlselon(FavoritelondByUselonrIdsFelonaturelon, Nil).takelon(MaxCountUselonrs) ++
          candidatelon.felonaturelons.gelontOrelonlselon(FollowelondByUselonrIdsFelonaturelon, Nil).takelon(MaxCountUselonrs) ++
          candidatelon.felonaturelons.gelontOrelonlselon(AuthorIdFelonaturelon, Nonelon) ++
          candidatelon.felonaturelons.gelontOrelonlselon(SourcelonUselonrIdFelonaturelon, Nonelon)).distinct
    }.toMap

    val distinctUselonrIds = candidatelonUselonrIdsMap.valuelons.flattelonn.toSelonq.distinct

    Stitch
      .collelonctToTry(distinctUselonrIds.map(uselonrId => gizmoduck.gelontUselonrById(uselonrId, quelonryFielonlds)))
      .map { allUselonrs =>
        val idToProfilelonNamelonsMap = allUselonrs.flatMap {
          caselon Relonturn(allUselonr) =>
            allUselonr.profilelon
              .map(profilelon => allUselonr.id -> ProfilelonNamelons(profilelon.screlonelonnNamelon, profilelon.namelon))
          caselon _ => Nonelon
        }.toMap

        val validUselonrIds = idToProfilelonNamelonsMap.kelonySelont

        candidatelons.map { candidatelon =>
          val combinelondMap = candidatelonUselonrIdsMap
            .gelontOrelonlselon(candidatelon.candidatelon.id, Nil)
            .flatMap {
              caselon uselonrId if validUselonrIds.contains(uselonrId) =>
                idToProfilelonNamelonsMap.gelont(uselonrId).map(profilelonNamelons => uselonrId -> profilelonNamelons)
              caselon _ => Nonelon
            }

          val pelonrCandidatelonRelonalNamelonMap = combinelondMap.map { caselon (k, v) => k -> v.relonalNamelon }.toMap
          val pelonrCandidatelonScrelonelonnNamelonMap = combinelondMap.map { caselon (k, v) => k -> v.screlonelonnNamelon }.toMap

          FelonaturelonMapBuildelonr()
            .add(ScrelonelonnNamelonsFelonaturelon, pelonrCandidatelonScrelonelonnNamelonMap)
            .add(RelonalNamelonsFelonaturelon, pelonrCandidatelonRelonalNamelonMap)
            .build()
        }
      }
  }
}
