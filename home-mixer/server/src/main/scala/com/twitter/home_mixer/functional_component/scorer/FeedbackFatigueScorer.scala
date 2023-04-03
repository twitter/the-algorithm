packagelon com.twittelonr.homelon_mixelonr.functional_componelonnt.scorelonr

import com.twittelonr.convelonrsions.DurationOps._
import com.twittelonr.homelon_mixelonr.modelonl.HomelonFelonaturelons.AuthorIdFelonaturelon
import com.twittelonr.homelon_mixelonr.modelonl.HomelonFelonaturelons.FelonelondbackHistoryFelonaturelon
import com.twittelonr.homelon_mixelonr.modelonl.HomelonFelonaturelons.IsRelontwelonelontFelonaturelon
import com.twittelonr.homelon_mixelonr.modelonl.HomelonFelonaturelons.SGSValidFollowelondByUselonrIdsFelonaturelon
import com.twittelonr.homelon_mixelonr.modelonl.HomelonFelonaturelons.SGSValidLikelondByUselonrIdsFelonaturelon
import com.twittelonr.homelon_mixelonr.modelonl.HomelonFelonaturelons.ScorelonFelonaturelon
import com.twittelonr.homelon_mixelonr.util.CandidatelonsUtil
import com.twittelonr.product_mixelonr.componelonnt_library.modelonl.candidatelon.TwelonelontCandidatelon
import com.twittelonr.product_mixelonr.corelon.felonaturelon.Felonaturelon
import com.twittelonr.product_mixelonr.corelon.felonaturelon.felonaturelonmap.FelonaturelonMap
import com.twittelonr.product_mixelonr.corelon.felonaturelon.felonaturelonmap.FelonaturelonMapBuildelonr
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.scorelonr.Scorelonr
import com.twittelonr.product_mixelonr.corelon.modelonl.common.CandidatelonWithFelonaturelons
import com.twittelonr.product_mixelonr.corelon.modelonl.common.Conditionally
import com.twittelonr.product_mixelonr.corelon.modelonl.common.idelonntifielonr.ScorelonrIdelonntifielonr
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonQuelonry
import com.twittelonr.stitch.Stitch
import com.twittelonr.timelonlinelons.common.{thriftscala => tl}
import com.twittelonr.timelonlinelonselonrvicelon.modelonl.Felonelondbackelonntry
import com.twittelonr.timelonlinelonselonrvicelon.{thriftscala => tls}
import com.twittelonr.util.Timelon
import scala.collelonction.mutablelon

objelonct FelonelondbackFatiguelonScorelonr
    elonxtelonnds Scorelonr[PipelonlinelonQuelonry, TwelonelontCandidatelon]
    with Conditionally[PipelonlinelonQuelonry] {

  ovelonrridelon val idelonntifielonr: ScorelonrIdelonntifielonr = ScorelonrIdelonntifielonr("FelonelondbackFatiguelon")

  ovelonrridelon delonf felonaturelons: Selont[Felonaturelon[_, _]] = Selont(ScorelonFelonaturelon)

  ovelonrridelon delonf onlyIf(quelonry: PipelonlinelonQuelonry): Boolelonan =
    quelonry.felonaturelons.elonxists(_.gelontOrelonlselon(FelonelondbackHistoryFelonaturelon, Selonq.elonmpty).nonelonmpty)

  privatelon val DurationForFiltelonring = 14.days
  privatelon val DurationForDiscounting = 140.days
  privatelon val ScorelonMultiplielonrLowelonrBound = 0.2
  privatelon val ScorelonMultiplielonrUppelonrBound = 1.0
  privatelon val ScorelonMultiplielonrIncrelonmelonntsCount = 4
  privatelon val ScorelonMultiplielonrIncrelonmelonnt =
    (ScorelonMultiplielonrUppelonrBound - ScorelonMultiplielonrLowelonrBound) / ScorelonMultiplielonrIncrelonmelonntsCount
  privatelon val ScorelonMultiplielonrIncrelonmelonntDurationInDays =
    DurationForDiscounting.inDays / ScorelonMultiplielonrIncrelonmelonntsCount.toDoublelon

  ovelonrridelon delonf apply(
    quelonry: PipelonlinelonQuelonry,
    candidatelons: Selonq[CandidatelonWithFelonaturelons[TwelonelontCandidatelon]]
  ): Stitch[Selonq[FelonaturelonMap]] = {
    val felonelondbackelonntrielonsByelonngagelonmelonntTypelon =
      quelonry.felonaturelons
        .gelontOrelonlselon(FelonaturelonMap.elonmpty).gelontOrelonlselon(FelonelondbackHistoryFelonaturelon, Selonq.elonmpty)
        .filtelonr { elonntry =>
          val timelonSincelonFelonelondback = quelonry.quelonryTimelon.minus(elonntry.timelonstamp)
          timelonSincelonFelonelondback < DurationForFiltelonring + DurationForDiscounting &&
          elonntry.felonelondbackTypelon == tls.FelonelondbackTypelon.SelonelonFelonwelonr
        }.groupBy(_.elonngagelonmelonntTypelon)

    val authorsToDiscount =
      gelontUselonrDiscounts(
        quelonry.quelonryTimelon,
        felonelondbackelonntrielonsByelonngagelonmelonntTypelon.gelontOrelonlselon(tls.FelonelondbackelonngagelonmelonntTypelon.Twelonelont, Selonq.elonmpty))
    val likelonrsToDiscount =
      gelontUselonrDiscounts(
        quelonry.quelonryTimelon,
        felonelondbackelonntrielonsByelonngagelonmelonntTypelon.gelontOrelonlselon(tls.FelonelondbackelonngagelonmelonntTypelon.Likelon, Selonq.elonmpty))
    val followelonrsToDiscount =
      gelontUselonrDiscounts(
        quelonry.quelonryTimelon,
        felonelondbackelonntrielonsByelonngagelonmelonntTypelon.gelontOrelonlselon(tls.FelonelondbackelonngagelonmelonntTypelon.Follow, Selonq.elonmpty))
    val relontwelonelontelonrsToDiscount =
      gelontUselonrDiscounts(
        quelonry.quelonryTimelon,
        felonelondbackelonntrielonsByelonngagelonmelonntTypelon.gelontOrelonlselon(tls.FelonelondbackelonngagelonmelonntTypelon.Relontwelonelont, Selonq.elonmpty))

    val felonaturelonMaps = candidatelons.map { candidatelon =>
      val scorelon = candidatelon.felonaturelons.gelontOrelonlselon(ScorelonFelonaturelon, Nonelon)

      val originalAuthorId =
        CandidatelonsUtil.gelontOriginalAuthorId(candidatelon.felonaturelons).gelontOrelonlselon(0L)
      val originalAuthorMultiplielonr = authorsToDiscount.gelontOrelonlselon(originalAuthorId, 1.0)

      val likelonrs = candidatelon.felonaturelons.gelontOrelonlselon(SGSValidLikelondByUselonrIdsFelonaturelon, Selonq.elonmpty)
      val likelonrMultiplielonrs = likelonrs.flatMap(likelonrsToDiscount.gelont)
      val likelonrMultiplielonr =
        if (likelonrMultiplielonrs.nonelonmpty && likelonrs.sizelon == likelonrMultiplielonrs.sizelon)
          likelonrMultiplielonrs.max
        elonlselon 1.0

      val followelonrs = candidatelon.felonaturelons.gelontOrelonlselon(SGSValidFollowelondByUselonrIdsFelonaturelon, Selonq.elonmpty)
      val followelonrMultiplielonrs = followelonrs.flatMap(followelonrsToDiscount.gelont)
      val followelonrMultiplielonr =
        if (followelonrMultiplielonrs.nonelonmpty && followelonrs.sizelon == followelonrMultiplielonrs.sizelon)
          followelonrMultiplielonrs.max
        elonlselon 1.0

      val authorId = candidatelon.felonaturelons.gelontOrelonlselon(AuthorIdFelonaturelon, Nonelon).gelontOrelonlselon(0L)
      val relontwelonelontelonrMultiplielonr =
        if (candidatelon.felonaturelons.gelontOrelonlselon(IsRelontwelonelontFelonaturelon, falselon))
          relontwelonelontelonrsToDiscount.gelontOrelonlselon(authorId, 1.0)
        elonlselon 1.0

      val multiplielonr =
        originalAuthorMultiplielonr * likelonrMultiplielonr * followelonrMultiplielonr * relontwelonelontelonrMultiplielonr

      FelonaturelonMapBuildelonr().add(ScorelonFelonaturelon, scorelon.map(_ * multiplielonr)).build()
    }

    Stitch.valuelon(felonaturelonMaps)
  }

  privatelon delonf gelontUselonrDiscounts(
    quelonryTimelon: Timelon,
    felonelondbackelonntrielons: Selonq[Felonelondbackelonntry],
  ): Map[Long, Doublelon] = {
    val uselonrDiscounts = mutablelon.Map[Long, Doublelon]()
    felonelondbackelonntrielons
      .collelonct {
        caselon Felonelondbackelonntry(_, _, tl.Felonelondbackelonntity.UselonrId(uselonrId), timelonstamp, _) =>
          val timelonSincelonFelonelondback = quelonryTimelon.minus(timelonstamp)
          val timelonSincelonDiscounting = timelonSincelonFelonelondback - DurationForFiltelonring
          val multiplielonr = ((timelonSincelonDiscounting.inDays / ScorelonMultiplielonrIncrelonmelonntDurationInDays)
            * ScorelonMultiplielonrIncrelonmelonnt + ScorelonMultiplielonrLowelonrBound)
          uselonrDiscounts.updatelon(uselonrId, multiplielonr)
      }
    uselonrDiscounts.toMap
  }
}
