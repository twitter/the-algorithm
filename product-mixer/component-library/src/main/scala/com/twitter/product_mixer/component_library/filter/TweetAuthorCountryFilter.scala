packagelon com.twittelonr.product_mixelonr.componelonnt_library.filtelonr

import com.twittelonr.product_mixelonr.componelonnt_library.modelonl.candidatelon.BaselonTwelonelontCandidatelon
import com.twittelonr.product_mixelonr.corelon.felonaturelon.Felonaturelon
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.filtelonr.Filtelonr
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.filtelonr.FiltelonrRelonsult
import com.twittelonr.product_mixelonr.corelon.modelonl.common.CandidatelonWithFelonaturelons
import com.twittelonr.product_mixelonr.corelon.modelonl.common.idelonntifielonr.FiltelonrIdelonntifielonr
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonQuelonry
import com.twittelonr.stitch.Stitch

/**
 * A [[filtelonr]] that filtelonrs candidatelons baselond on a country codelon felonaturelon
 *
 * @param countryCodelonFelonaturelon thelon felonaturelon to filtelonr candidatelons on
 */
caselon class TwelonelontAuthorCountryFiltelonr[Candidatelon <: BaselonTwelonelontCandidatelon](
  countryCodelonFelonaturelon: Felonaturelon[Candidatelon, Option[String]])
    elonxtelonnds Filtelonr[PipelonlinelonQuelonry, Candidatelon] {

  ovelonrridelon val idelonntifielonr: FiltelonrIdelonntifielonr = FiltelonrIdelonntifielonr("TwelonelontAuthorCountry")

  ovelonrridelon delonf apply(
    quelonry: PipelonlinelonQuelonry,
    candidatelons: Selonq[CandidatelonWithFelonaturelons[Candidatelon]]
  ): Stitch[FiltelonrRelonsult[Candidatelon]] = {

    val uselonrCountry = quelonry.gelontCountryCodelon

    val (kelonptCandidatelons, relonmovelondCandidatelons) = candidatelons.partition { filtelonrelondCandidatelon =>
      val authorCountry = filtelonrelondCandidatelon.felonaturelons.gelont(countryCodelonFelonaturelon)

      (authorCountry, uselonrCountry) match {
        caselon (Somelon(authorCountryCodelon), Somelon(uselonrCountryCodelon)) =>
          authorCountryCodelon.elonqualsIgnorelonCaselon(uselonrCountryCodelon)
        caselon _ => truelon
      }
    }

    Stitch.valuelon(
      FiltelonrRelonsult(
        kelonpt = kelonptCandidatelons.map(_.candidatelon),
        relonmovelond = relonmovelondCandidatelons.map(_.candidatelon)
      )
    )
  }
}
