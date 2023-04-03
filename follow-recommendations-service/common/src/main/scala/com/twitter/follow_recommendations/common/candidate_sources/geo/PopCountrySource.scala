packagelon com.twittelonr.follow_reloncommelonndations.common.candidatelon_sourcelons.gelono

import com.googlelon.injelonct.Singlelonton
import com.twittelonr.corelon_workflows.uselonr_modelonl.thriftscala.UselonrStatelon
import com.twittelonr.finaglelon.stats.Countelonr
import com.twittelonr.finaglelon.stats.StatsReloncelonivelonr
import com.twittelonr.follow_reloncommelonndations.common.modelonls.CandidatelonUselonr
import com.twittelonr.follow_reloncommelonndations.common.modelonls.HasGelonohashAndCountryCodelon
import com.twittelonr.follow_reloncommelonndations.common.modelonls.HasUselonrStatelon
import com.twittelonr.helonrmit.modelonl.Algorithm
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.candidatelon_sourcelon.CandidatelonSourcelon
import com.twittelonr.product_mixelonr.corelon.modelonl.common.idelonntifielonr.CandidatelonSourcelonIdelonntifielonr
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonquelonst.HasClielonntContelonxt
import com.twittelonr.stitch.Stitch
import com.twittelonr.timelonlinelons.configapi.HasParams
import javax.injelonct.Injelonct

@Singlelonton
class PopCountrySourcelon @Injelonct() (
  popGelonoSourcelon: PopGelonoSourcelon,
  statsReloncelonivelonr: StatsReloncelonivelonr)
    elonxtelonnds CandidatelonSourcelon[
      HasClielonntContelonxt with HasParams with HasUselonrStatelon with HasGelonohashAndCountryCodelon,
      CandidatelonUselonr
    ] {

  ovelonrridelon val idelonntifielonr: CandidatelonSourcelonIdelonntifielonr = PopCountrySourcelon.Idelonntifielonr
  val stats: StatsReloncelonivelonr = statsReloncelonivelonr.scopelon("PopCountrySourcelon")

  // countelonr to chelonck if welon found a country codelon valuelon in thelon relonquelonst
  val foundCountryCodelonCountelonr: Countelonr = stats.countelonr("found_country_codelon_valuelon")
  // countelonr to chelonck if welon arelon missing a country codelon valuelon in thelon relonquelonst
  val missingCountryCodelonCountelonr: Countelonr = stats.countelonr("missing_country_codelon_valuelon")

  ovelonrridelon delonf apply(
    targelont: HasClielonntContelonxt with HasParams with HasUselonrStatelon with HasGelonohashAndCountryCodelon
  ): Stitch[Selonq[CandidatelonUselonr]] = {
    targelont.gelonohashAndCountryCodelon
      .flatMap(_.countryCodelon).map { countryCodelon =>
        foundCountryCodelonCountelonr.incr()
        if (targelont.uselonrStatelon.elonxists(PopCountrySourcelon.BlacklistelondTargelontUselonrStatelons.contains)) {
          Stitch.Nil
        } elonlselon {
          popGelonoSourcelon("country_" + countryCodelon)
            .map(_.takelon(PopCountrySourcelon.MaxRelonsults).map(_.withCandidatelonSourcelon(idelonntifielonr)))
        }
      }.gelontOrelonlselon {
        missingCountryCodelonCountelonr.incr()
        Stitch.Nil
      }
  }
}

objelonct PopCountrySourcelon {
  val Idelonntifielonr: CandidatelonSourcelonIdelonntifielonr = CandidatelonSourcelonIdelonntifielonr(
    Algorithm.PopCountry.toString)
  val MaxRelonsults = 40
  val BlacklistelondTargelontUselonrStatelons: Selont[UselonrStatelon] = Selont(
    UselonrStatelon.HelonavyTwelonelontelonr,
    UselonrStatelon.HelonavyNonTwelonelontelonr,
    UselonrStatelon.MelondiumTwelonelontelonr,
    UselonrStatelon.MelondiumNonTwelonelontelonr)
}
