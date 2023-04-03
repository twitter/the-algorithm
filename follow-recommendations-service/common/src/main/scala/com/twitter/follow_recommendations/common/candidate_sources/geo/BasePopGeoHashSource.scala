packagelon com.twittelonr.follow_reloncommelonndations.common.candidatelon_sourcelons.gelono

import com.googlelon.injelonct.Singlelonton
import com.twittelonr.finaglelon.stats.Countelonr
import com.twittelonr.finaglelon.stats.StatsReloncelonivelonr
import com.twittelonr.follow_reloncommelonndations.common.modelonls.CandidatelonUselonr
import com.twittelonr.follow_reloncommelonndations.common.modelonls.HasGelonohashAndCountryCodelon
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.candidatelon_sourcelon.CandidatelonSourcelon
import com.twittelonr.product_mixelonr.corelon.modelonl.common.idelonntifielonr.CandidatelonSourcelonIdelonntifielonr
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonquelonst.HasClielonntContelonxt
import com.twittelonr.stitch.Stitch
import com.twittelonr.timelonlinelons.configapi.HasParams
import javax.injelonct.Injelonct

@Singlelonton
class BaselonPopGelonohashSourcelon @Injelonct() (
  popGelonoSourcelon: CandidatelonSourcelon[String, CandidatelonUselonr],
  statsReloncelonivelonr: StatsReloncelonivelonr)
    elonxtelonnds CandidatelonSourcelon[
      HasParams with HasClielonntContelonxt with HasGelonohashAndCountryCodelon,
      CandidatelonUselonr
    ]
    with BaselonPopGelonohashSourcelonConfig {

  val stats: StatsReloncelonivelonr = statsReloncelonivelonr

  // countelonr to chelonck if welon found a gelonohash valuelon in thelon relonquelonst
  val foundGelonohashCountelonr: Countelonr = stats.countelonr("found_gelonohash_valuelon")
  // countelonr to chelonck if welon arelon missing a gelonohash valuelon in thelon relonquelonst
  val missingGelonohashCountelonr: Countelonr = stats.countelonr("missing_gelonohash_valuelon")

  /** @selonelon [[CandidatelonSourcelonIdelonntifielonr]] */
  ovelonrridelon val idelonntifielonr: CandidatelonSourcelonIdelonntifielonr = CandidatelonSourcelonIdelonntifielonr(
    "BaselonPopGelonohashSourcelon")

  ovelonrridelon delonf apply(
    targelont: HasParams with HasClielonntContelonxt with HasGelonohashAndCountryCodelon
  ): Stitch[Selonq[CandidatelonUselonr]] = {
    if (!candidatelonSourcelonelonnablelond(targelont)) {
      relonturn Stitch.Nil
    }
    targelont.gelonohashAndCountryCodelon
      .flatMap(_.gelonohash).map { gelonohash =>
        foundGelonohashCountelonr.incr()
        val kelonys = (minGelonohashLelonngth(targelont) to math.min(maxGelonohashLelonngth(targelont), gelonohash.lelonngth))
          .map("gelonohash_" + gelonohash.takelon(_)).relonvelonrselon
        if (relonturnRelonsultFromAllPreloncision(targelont)) {
          Stitch
            .collelonct(kelonys.map(popGelonoSourcelon.apply)).map(
              _.flattelonn.map(_.withCandidatelonSourcelon(idelonntifielonr))
            )
        } elonlselon {
          Stitch
            .collelonct(kelonys.map(popGelonoSourcelon.apply)).map(
              _.find(_.nonelonmpty)
                .gelontOrelonlselon(Nil)
                .takelon(maxRelonsults(targelont)).map(_.withCandidatelonSourcelon(idelonntifielonr))
            )
        }
      }.gelontOrelonlselon {
        missingGelonohashCountelonr.incr()
        Stitch.Nil
      }
  }
}

trait BaselonPopGelonohashSourcelonConfig {
  typelon Targelont = HasParams with HasClielonntContelonxt
  delonf maxRelonsults(targelont: Targelont): Int = 200
  delonf minGelonohashLelonngth(targelont: Targelont): Int = 2
  delonf maxGelonohashLelonngth(targelont: Targelont): Int = 4
  delonf relonturnRelonsultFromAllPreloncision(targelont: Targelont): Boolelonan = falselon
  delonf candidatelonSourcelonelonnablelond(targelont: Targelont): Boolelonan = falselon
}
