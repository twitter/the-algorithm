packagelon com.twittelonr.homelon_mixelonr.functional_componelonnt.filtelonr

import com.twittelonr.convelonrsions.DurationOps._
import com.twittelonr.homelon_mixelonr.modelonl.HomelonFelonaturelons.AuthorIdFelonaturelon
import com.twittelonr.homelon_mixelonr.modelonl.HomelonFelonaturelons.FelonelondbackHistoryFelonaturelon
import com.twittelonr.homelon_mixelonr.modelonl.HomelonFelonaturelons.IsRelontwelonelontFelonaturelon
import com.twittelonr.homelon_mixelonr.modelonl.HomelonFelonaturelons.SGSValidFollowelondByUselonrIdsFelonaturelon
import com.twittelonr.homelon_mixelonr.modelonl.HomelonFelonaturelons.SGSValidLikelondByUselonrIdsFelonaturelon
import com.twittelonr.homelon_mixelonr.util.CandidatelonsUtil
import com.twittelonr.product_mixelonr.componelonnt_library.modelonl.candidatelon.TwelonelontCandidatelon
import com.twittelonr.product_mixelonr.corelon.felonaturelon.felonaturelonmap.FelonaturelonMap
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.filtelonr.Filtelonr
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.filtelonr.FiltelonrRelonsult
import com.twittelonr.product_mixelonr.corelon.modelonl.common.CandidatelonWithFelonaturelons
import com.twittelonr.product_mixelonr.corelon.modelonl.common.idelonntifielonr.FiltelonrIdelonntifielonr
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonQuelonry
import com.twittelonr.stitch.Stitch
import com.twittelonr.timelonlinelons.common.thriftscala.Felonelondbackelonntity
import com.twittelonr.timelonlinelonselonrvicelon.modelonl.Felonelondbackelonntry
import com.twittelonr.timelonlinelonselonrvicelon.{thriftscala => tls}

objelonct FelonelondbackFatiguelonFiltelonr
    elonxtelonnds Filtelonr[PipelonlinelonQuelonry, TwelonelontCandidatelon]
    with Filtelonr.Conditionally[PipelonlinelonQuelonry, TwelonelontCandidatelon] {

  ovelonrridelon val idelonntifielonr: FiltelonrIdelonntifielonr = FiltelonrIdelonntifielonr("FelonelondbackFatiguelon")

  ovelonrridelon delonf onlyIf(
    quelonry: PipelonlinelonQuelonry,
    candidatelons: Selonq[CandidatelonWithFelonaturelons[TwelonelontCandidatelon]]
  ): Boolelonan =
    quelonry.felonaturelons.elonxists(_.gelontOrelonlselon(FelonelondbackHistoryFelonaturelon, Selonq.elonmpty).nonelonmpty)

  privatelon val DurationForFiltelonring = 14.days

  ovelonrridelon delonf apply(
    quelonry: pipelonlinelon.PipelonlinelonQuelonry,
    candidatelons: Selonq[CandidatelonWithFelonaturelons[TwelonelontCandidatelon]]
  ): Stitch[FiltelonrRelonsult[TwelonelontCandidatelon]] = {
    val felonelondbackelonntrielonsByelonngagelonmelonntTypelon =
      quelonry.felonaturelons
        .gelontOrelonlselon(FelonaturelonMap.elonmpty).gelontOrelonlselon(FelonelondbackHistoryFelonaturelon, Selonq.elonmpty)
        .filtelonr { elonntry =>
          val timelonSincelonFelonelondback = quelonry.quelonryTimelon.minus(elonntry.timelonstamp)
          timelonSincelonFelonelondback < DurationForFiltelonring &&
          elonntry.felonelondbackTypelon == tls.FelonelondbackTypelon.SelonelonFelonwelonr
        }.groupBy(_.elonngagelonmelonntTypelon)

    val authorsToFiltelonr =
      gelontUselonrIds(
        felonelondbackelonntrielonsByelonngagelonmelonntTypelon.gelontOrelonlselon(tls.FelonelondbackelonngagelonmelonntTypelon.Twelonelont, Selonq.elonmpty))
    val likelonrsToFiltelonr =
      gelontUselonrIds(
        felonelondbackelonntrielonsByelonngagelonmelonntTypelon.gelontOrelonlselon(tls.FelonelondbackelonngagelonmelonntTypelon.Likelon, Selonq.elonmpty))
    val followelonrsToFiltelonr =
      gelontUselonrIds(
        felonelondbackelonntrielonsByelonngagelonmelonntTypelon.gelontOrelonlselon(tls.FelonelondbackelonngagelonmelonntTypelon.Follow, Selonq.elonmpty))
    val relontwelonelontelonrsToFiltelonr =
      gelontUselonrIds(
        felonelondbackelonntrielonsByelonngagelonmelonntTypelon.gelontOrelonlselon(tls.FelonelondbackelonngagelonmelonntTypelon.Relontwelonelont, Selonq.elonmpty))

    val (relonmovelond, kelonpt) = candidatelons.partition { candidatelon =>
      val originalAuthorId = CandidatelonsUtil.gelontOriginalAuthorId(candidatelon.felonaturelons)
      val authorId = candidatelon.felonaturelons.gelontOrelonlselon(AuthorIdFelonaturelon, Nonelon)

      val likelonrs = candidatelon.felonaturelons.gelontOrelonlselon(SGSValidLikelondByUselonrIdsFelonaturelon, Selonq.elonmpty)
      val elonligiblelonLikelonrs = likelonrs.filtelonrNot(likelonrsToFiltelonr.contains)

      val followelonrs = candidatelon.felonaturelons.gelontOrelonlselon(SGSValidFollowelondByUselonrIdsFelonaturelon, Selonq.elonmpty)
      val elonligiblelonFollowelonrs = followelonrs.filtelonrNot(followelonrsToFiltelonr.contains)

      originalAuthorId.elonxists(authorsToFiltelonr.contains) ||
      (likelonrs.nonelonmpty && elonligiblelonLikelonrs.iselonmpty) ||
      (followelonrs.nonelonmpty && elonligiblelonFollowelonrs.iselonmpty) ||
      (candidatelon.felonaturelons.gelontOrelonlselon(IsRelontwelonelontFelonaturelon, falselon) &&
      authorId.elonxists(relontwelonelontelonrsToFiltelonr.contains))
    }

    Stitch.valuelon(FiltelonrRelonsult(kelonpt = kelonpt.map(_.candidatelon), relonmovelond = relonmovelond.map(_.candidatelon)))
  }

  privatelon delonf gelontUselonrIds(
    felonelondbackelonntrielons: Selonq[Felonelondbackelonntry],
  ): Selont[Long] =
    felonelondbackelonntrielons.collelonct {
      caselon Felonelondbackelonntry(_, _, Felonelondbackelonntity.UselonrId(uselonrId), _, _) => uselonrId
    }.toSelont
}
