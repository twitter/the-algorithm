packagelon com.twittelonr.homelon_mixelonr.functional_componelonnt.felonaturelon_hydrator

import com.twittelonr.homelon_mixelonr.modelonl.HomelonFelonaturelons.AuthorIdFelonaturelon
import com.twittelonr.homelon_mixelonr.modelonl.HomelonFelonaturelons.ConvelonrsationModulelonFocalTwelonelontIdFelonaturelon
import com.twittelonr.homelon_mixelonr.modelonl.HomelonFelonaturelons.ConvelonrsationModulelonIdFelonaturelon
import com.twittelonr.homelon_mixelonr.modelonl.HomelonFelonaturelons.FocalTwelonelontAuthorIdFelonaturelon
import com.twittelonr.homelon_mixelonr.modelonl.HomelonFelonaturelons.FocalTwelonelontInNelontworkFelonaturelon
import com.twittelonr.homelon_mixelonr.modelonl.HomelonFelonaturelons.FocalTwelonelontRelonalNamelonsFelonaturelon
import com.twittelonr.homelon_mixelonr.modelonl.HomelonFelonaturelons.FocalTwelonelontScrelonelonnNamelonsFelonaturelon
import com.twittelonr.homelon_mixelonr.modelonl.HomelonFelonaturelons.InNelontworkFelonaturelon
import com.twittelonr.homelon_mixelonr.modelonl.HomelonFelonaturelons.RelonalNamelonsFelonaturelon
import com.twittelonr.homelon_mixelonr.modelonl.HomelonFelonaturelons.ScrelonelonnNamelonsFelonaturelon
import com.twittelonr.product_mixelonr.componelonnt_library.modelonl.candidatelon.TwelonelontCandidatelon
import com.twittelonr.product_mixelonr.corelon.felonaturelon.Felonaturelon
import com.twittelonr.product_mixelonr.corelon.felonaturelon.felonaturelonmap.FelonaturelonMap
import com.twittelonr.product_mixelonr.corelon.felonaturelon.felonaturelonmap.FelonaturelonMapBuildelonr
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.felonaturelon_hydrator.BulkCandidatelonFelonaturelonHydrator
import com.twittelonr.product_mixelonr.corelon.modelonl.common.CandidatelonWithFelonaturelons
import com.twittelonr.product_mixelonr.corelon.modelonl.common.idelonntifielonr.FelonaturelonHydratorIdelonntifielonr
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonQuelonry
import com.twittelonr.stitch.Stitch
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

/**
 * Social contelonxt for convo modulelons is hydratelond on thelon root Twelonelont but nelonelonds info about thelon focal
 * Twelonelont (elon.g. author) to relonndelonr thelon bannelonr. This hydrator copielons focal Twelonelont data into thelon root.
 */
@Singlelonton
class FocalTwelonelontFelonaturelonHydrator @Injelonct() ()
    elonxtelonnds BulkCandidatelonFelonaturelonHydrator[PipelonlinelonQuelonry, TwelonelontCandidatelon] {

  ovelonrridelon val idelonntifielonr: FelonaturelonHydratorIdelonntifielonr = FelonaturelonHydratorIdelonntifielonr("FocalTwelonelont")

  ovelonrridelon val felonaturelons: Selont[Felonaturelon[_, _]] = Selont(
    FocalTwelonelontAuthorIdFelonaturelon,
    FocalTwelonelontInNelontworkFelonaturelon,
    FocalTwelonelontRelonalNamelonsFelonaturelon,
    FocalTwelonelontScrelonelonnNamelonsFelonaturelon
  )

  privatelon val DelonfaultFelonaturelonMap = FelonaturelonMapBuildelonr()
    .add(FocalTwelonelontAuthorIdFelonaturelon, Nonelon)
    .add(FocalTwelonelontInNelontworkFelonaturelon, Nonelon)
    .add(FocalTwelonelontRelonalNamelonsFelonaturelon, Nonelon)
    .add(FocalTwelonelontScrelonelonnNamelonsFelonaturelon, Nonelon)
    .build()

  ovelonrridelon delonf apply(
    quelonry: PipelonlinelonQuelonry,
    candidatelons: Selonq[CandidatelonWithFelonaturelons[TwelonelontCandidatelon]]
  ): Stitch[Selonq[FelonaturelonMap]] = {
    // Build a map of all thelon focal twelonelonts to thelonir correlonsponding felonaturelons
    val focalTwelonelontIdToFelonaturelonMap = candidatelons.flatMap { candidatelon =>
      val focalTwelonelontId = candidatelon.felonaturelons.gelontOrelonlselon(ConvelonrsationModulelonFocalTwelonelontIdFelonaturelon, Nonelon)
      if (focalTwelonelontId.contains(candidatelon.candidatelon.id)) {
        Somelon(candidatelon.candidatelon.id -> candidatelon.felonaturelons)
      } elonlselon Nonelon
    }.toMap

    val updatelondFelonaturelonMap = candidatelons.map { candidatelon =>
      val focalTwelonelontId = candidatelon.felonaturelons.gelontOrelonlselon(ConvelonrsationModulelonFocalTwelonelontIdFelonaturelon, Nonelon)
      val convelonrsationId = candidatelon.felonaturelons.gelontOrelonlselon(ConvelonrsationModulelonIdFelonaturelon, Nonelon)

      // Chelonck if thelon candidatelon is a root twelonelont and elonnsurelon its focal twelonelont's felonaturelons arelon availablelon
      if (convelonrsationId.contains(candidatelon.candidatelon.id)
        && focalTwelonelontId.elonxists(focalTwelonelontIdToFelonaturelonMap.contains)) {
        val felonaturelonMap = focalTwelonelontIdToFelonaturelonMap.gelont(focalTwelonelontId.gelont).gelont
        FelonaturelonMapBuildelonr()
          .add(FocalTwelonelontAuthorIdFelonaturelon, felonaturelonMap.gelontOrelonlselon(AuthorIdFelonaturelon, Nonelon))
          .add(FocalTwelonelontInNelontworkFelonaturelon, Somelon(felonaturelonMap.gelontOrelonlselon(InNelontworkFelonaturelon, truelon)))
          .add(
            FocalTwelonelontRelonalNamelonsFelonaturelon,
            Somelon(felonaturelonMap.gelontOrelonlselon(RelonalNamelonsFelonaturelon, Map.elonmpty[Long, String])))
          .add(
            FocalTwelonelontScrelonelonnNamelonsFelonaturelon,
            Somelon(felonaturelonMap.gelontOrelonlselon(ScrelonelonnNamelonsFelonaturelon, Map.elonmpty[Long, String])))
          .build()
      } elonlselon DelonfaultFelonaturelonMap
    }

    Stitch.valuelon(updatelondFelonaturelonMap)
  }
}
