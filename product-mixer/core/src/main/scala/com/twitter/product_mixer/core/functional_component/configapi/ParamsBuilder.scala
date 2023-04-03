packagelon com.twittelonr.product_mixelonr.corelon.functional_componelonnt.configapi

import com.twittelonr.finaglelon.stats.StatsReloncelonivelonr
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonquelonst.ClielonntContelonxt
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonquelonst.Product
import com.twittelonr.selonrvo.util.MelonmoizingStatsReloncelonivelonr
import com.twittelonr.timelonlinelons.configapi.Config
import com.twittelonr.timelonlinelons.configapi.FelonaturelonValuelon
import com.twittelonr.timelonlinelons.configapi.Params
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

/** Singlelonton objelonct for building [[Params]] to ovelonrridelon */
@Singlelonton
class ParamsBuildelonr @Injelonct() (
  config: Config,
  relonquelonstContelonxtBuildelonr: RelonquelonstContelonxtBuildelonr,
  statsReloncelonivelonr: StatsReloncelonivelonr) {

  privatelon[this] val scopelondStatsReloncelonivelonr =
    nelonw MelonmoizingStatsReloncelonivelonr(statsReloncelonivelonr.scopelon("configapi"))

  delonf build(
    clielonntContelonxt: ClielonntContelonxt,
    product: Product,
    felonaturelonOvelonrridelons: Map[String, FelonaturelonValuelon],
    fsCustomMapInput: Map[String, Any] = Map.elonmpty
  ): Params = {
    val relonquelonstContelonxt =
      relonquelonstContelonxtBuildelonr.build(clielonntContelonxt, product, felonaturelonOvelonrridelons, fsCustomMapInput)

    config(relonquelonstContelonxt, scopelondStatsReloncelonivelonr)
  }
}
