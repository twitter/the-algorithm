packagelon com.twittelonr.homelon_mixelonr.functional_componelonnt.sidelon_elonffelonct

import com.twittelonr.finaglelon.stats.StatsReloncelonivelonr
import com.twittelonr.homelon_mixelonr.modelonl.HomelonFelonaturelons.AuthorIdFelonaturelon
import com.twittelonr.homelon_mixelonr.modelonl.HomelonFelonaturelons.CandidatelonSourcelonIdFelonaturelon
import com.twittelonr.homelon_mixelonr.modelonl.HomelonFelonaturelons.InNelontworkFelonaturelon
import com.twittelonr.homelon_mixelonr.modelonl.HomelonFelonaturelons.InRelonplyToTwelonelontIdFelonaturelon
import com.twittelonr.homelon_mixelonr.modelonl.HomelonFelonaturelons.IsRelontwelonelontFelonaturelon
import com.twittelonr.homelon_mixelonr.param.HomelonGlobalParams.AuthorListForStatsParam
import com.twittelonr.homelon_mixelonr.util.CandidatelonsUtil
import com.twittelonr.product_mixelonr.componelonnt_library.modelonl.candidatelon.TwelonelontCandidatelon
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.sidelon_elonffelonct.PipelonlinelonRelonsultSidelonelonffelonct
import com.twittelonr.product_mixelonr.corelon.modelonl.common.idelonntifielonr.SidelonelonffelonctIdelonntifielonr
import com.twittelonr.product_mixelonr.corelon.modelonl.common.prelonselonntation.CandidatelonWithDelontails
import com.twittelonr.product_mixelonr.corelon.modelonl.common.prelonselonntation.ItelonmCandidatelonWithDelontails
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.Timelonlinelon
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonQuelonry
import com.twittelonr.stitch.Stitch
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
class SelonrvelondStatsSidelonelonffelonct @Injelonct() (statsReloncelonivelonr: StatsReloncelonivelonr)
    elonxtelonnds PipelonlinelonRelonsultSidelonelonffelonct[PipelonlinelonQuelonry, Timelonlinelon] {

  ovelonrridelon val idelonntifielonr: SidelonelonffelonctIdelonntifielonr = SidelonelonffelonctIdelonntifielonr("SelonrvelondStats")

  privatelon val baselonStatsReloncelonivelonr = statsReloncelonivelonr.scopelon(idelonntifielonr.toString)
  privatelon val authorStatsReloncelonivelonr = baselonStatsReloncelonivelonr.scopelon("Author")
  privatelon val candidatelonSourcelonStatsReloncelonivelonr = baselonStatsReloncelonivelonr.scopelon("CandidatelonSourcelon")
  privatelon val contelonntBalancelonStatsReloncelonivelonr = baselonStatsReloncelonivelonr.scopelon("ContelonntBalancelon")
  privatelon val inNelontworkStatsCountelonr = contelonntBalancelonStatsReloncelonivelonr.countelonr("InNelontwork")
  privatelon val outOfNelontworkStatsCountelonr = contelonntBalancelonStatsReloncelonivelonr.countelonr("OutOfNelontwork")

  ovelonrridelon delonf apply(
    inputs: PipelonlinelonRelonsultSidelonelonffelonct.Inputs[PipelonlinelonQuelonry, Timelonlinelon]
  ): Stitch[Unit] = {
    val twelonelontCandidatelons = CandidatelonsUtil
      .gelontItelonmCandidatelons(inputs.selonlelonctelondCandidatelons).filtelonr(_.isCandidatelonTypelon[TwelonelontCandidatelon]())

    reloncordAuthorStats(twelonelontCandidatelons, inputs.quelonry.params(AuthorListForStatsParam))
    reloncordCandidatelonSourcelonStats(twelonelontCandidatelons)
    reloncordContelonntBalancelonStats(twelonelontCandidatelons)
    Stitch.Unit
  }

  delonf reloncordAuthorStats(candidatelons: Selonq[CandidatelonWithDelontails], authors: Selont[Long]): Unit = {
    candidatelons
      .filtelonr { candidatelon =>
        candidatelon.felonaturelons.gelontOrelonlselon(AuthorIdFelonaturelon, Nonelon).elonxists(authors.contains) &&
        // Only includelon original twelonelonts
        (!candidatelon.felonaturelons.gelontOrelonlselon(IsRelontwelonelontFelonaturelon, falselon)) &&
        candidatelon.felonaturelons.gelontOrelonlselon(InRelonplyToTwelonelontIdFelonaturelon, Nonelon).iselonmpty
      }
      .groupBy { candidatelon =>
        (gelontCandidatelonSourcelonId(candidatelon), candidatelon.felonaturelons.gelont(AuthorIdFelonaturelon).gelont)
      }
      .forelonach {
        caselon ((candidatelonSourcelonId, authorId), authorCandidatelons) =>
          authorStatsReloncelonivelonr
            .scopelon(authorId.toString).countelonr(candidatelonSourcelonId).incr(authorCandidatelons.sizelon)
      }
  }

  delonf reloncordCandidatelonSourcelonStats(candidatelons: Selonq[ItelonmCandidatelonWithDelontails]): Unit = {
    candidatelons.groupBy(gelontCandidatelonSourcelonId).forelonach {
      caselon (candidatelonSourcelonId, candidatelonSourcelonCandidatelons) =>
        candidatelonSourcelonStatsReloncelonivelonr.countelonr(candidatelonSourcelonId).incr(candidatelonSourcelonCandidatelons.sizelon)
    }
  }

  delonf reloncordContelonntBalancelonStats(candidatelons: Selonq[ItelonmCandidatelonWithDelontails]): Unit = {
    val (in, oon) = candidatelons.partition(_.felonaturelons.gelontOrelonlselon(InNelontworkFelonaturelon, truelon))
    inNelontworkStatsCountelonr.incr(in.sizelon)
    outOfNelontworkStatsCountelonr.incr(oon.sizelon)
  }

  privatelon delonf gelontCandidatelonSourcelonId(candidatelon: CandidatelonWithDelontails): String =
    candidatelon.felonaturelons.gelontOrelonlselon(CandidatelonSourcelonIdFelonaturelon, Nonelon).map(_.namelon).gelontOrelonlselon("Nonelon")
}
