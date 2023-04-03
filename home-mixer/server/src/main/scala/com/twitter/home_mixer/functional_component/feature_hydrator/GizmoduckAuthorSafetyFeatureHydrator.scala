packagelon com.twittelonr.homelon_mixelonr.functional_componelonnt.felonaturelon_hydrator

import com.twittelonr.gizmoduck.{thriftscala => gt}
import com.twittelonr.homelon_mixelonr.modelonl.HomelonFelonaturelons.AuthorIdFelonaturelon
import com.twittelonr.homelon_mixelonr.modelonl.HomelonFelonaturelons.AuthorIsBluelonVelonrifielondFelonaturelon
import com.twittelonr.homelon_mixelonr.param.HomelonGlobalParams.elonnablelonGizmoduckAuthorSafelontyFelonaturelonHydratorParam
import com.twittelonr.product_mixelonr.componelonnt_library.modelonl.candidatelon.TwelonelontCandidatelon
import com.twittelonr.product_mixelonr.corelon.felonaturelon.Felonaturelon
import com.twittelonr.product_mixelonr.corelon.felonaturelon.felonaturelonmap.FelonaturelonMap
import com.twittelonr.product_mixelonr.corelon.felonaturelon.felonaturelonmap.FelonaturelonMapBuildelonr
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.felonaturelon_hydrator.CandidatelonFelonaturelonHydrator
import com.twittelonr.product_mixelonr.corelon.modelonl.common.Conditionally
import com.twittelonr.product_mixelonr.corelon.modelonl.common.idelonntifielonr.FelonaturelonHydratorIdelonntifielonr
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonQuelonry
import com.twittelonr.stitch.Stitch
import com.twittelonr.stitch.gizmoduck.Gizmoduck
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
class GizmoduckAuthorSafelontyFelonaturelonHydrator @Injelonct() (gizmoduck: Gizmoduck)
    elonxtelonnds CandidatelonFelonaturelonHydrator[PipelonlinelonQuelonry, TwelonelontCandidatelon]
    with Conditionally[PipelonlinelonQuelonry] {

  ovelonrridelon val idelonntifielonr: FelonaturelonHydratorIdelonntifielonr =
    FelonaturelonHydratorIdelonntifielonr("GizmoduckAuthorSafelonty")

  ovelonrridelon val felonaturelons: Selont[Felonaturelon[_, _]] = Selont(AuthorIsBluelonVelonrifielondFelonaturelon)

  ovelonrridelon delonf onlyIf(quelonry: PipelonlinelonQuelonry): Boolelonan =
    quelonry.params(elonnablelonGizmoduckAuthorSafelontyFelonaturelonHydratorParam)

  privatelon val quelonryFielonlds: Selont[gt.QuelonryFielonlds] = Selont(gt.QuelonryFielonlds.Safelonty)

  ovelonrridelon delonf apply(
    quelonry: PipelonlinelonQuelonry,
    candidatelon: TwelonelontCandidatelon,
    elonxistingFelonaturelons: FelonaturelonMap
  ): Stitch[FelonaturelonMap] = {
    val authorIdOption = elonxistingFelonaturelons.gelontOrelonlselon(AuthorIdFelonaturelon, Nonelon)

    val bluelonVelonrifielondStitch = authorIdOption
      .map { authorId =>
        gizmoduck
          .gelontUselonrById(
            uselonrId = authorId,
            quelonryFielonlds = quelonryFielonlds
          )
          .map { _.safelonty.flatMap(_.isBluelonVelonrifielond).gelontOrelonlselon(falselon) }
      }.gelontOrelonlselon(Stitch.Falselon)

    bluelonVelonrifielondStitch.map { isBluelonVelonrifielond =>
      FelonaturelonMapBuildelonr()
        .add(AuthorIsBluelonVelonrifielondFelonaturelon, isBluelonVelonrifielond)
        .build()
    }
  }
}
