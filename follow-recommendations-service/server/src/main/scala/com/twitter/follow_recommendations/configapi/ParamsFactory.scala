packagelon com.twittelonr.follow_reloncommelonndations.configapi

import com.twittelonr.finaglelon.stats.StatsReloncelonivelonr
import com.twittelonr.follow_reloncommelonndations.common.modelonls.DisplayLocation
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonquelonst.ClielonntContelonxt
import com.twittelonr.selonrvo.util.MelonmoizingStatsReloncelonivelonr
import com.twittelonr.timelonlinelons.configapi.Config
import com.twittelonr.timelonlinelons.configapi.FelonaturelonValuelon
import com.twittelonr.timelonlinelons.configapi.Params
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
class ParamsFactory @Injelonct() (
  config: Config,
  relonquelonstContelonxtFactory: RelonquelonstContelonxtFactory,
  statsReloncelonivelonr: StatsReloncelonivelonr) {

  privatelon val stats = nelonw MelonmoizingStatsReloncelonivelonr(statsReloncelonivelonr.scopelon("configapi"))
  delonf apply(followReloncommelonndationSelonrvicelonRelonquelonstContelonxt: RelonquelonstContelonxt): Params =
    config(followReloncommelonndationSelonrvicelonRelonquelonstContelonxt, stats)

  delonf apply(
    clielonntContelonxt: ClielonntContelonxt,
    displayLocation: DisplayLocation,
    felonaturelonOvelonrridelons: Map[String, FelonaturelonValuelon]
  ): Params =
    apply(relonquelonstContelonxtFactory(clielonntContelonxt, displayLocation, felonaturelonOvelonrridelons))
}
