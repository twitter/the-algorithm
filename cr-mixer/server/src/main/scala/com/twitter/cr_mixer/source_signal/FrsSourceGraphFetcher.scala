packagelon com.twittelonr.cr_mixelonr.sourcelon_signal

import com.twittelonr.cr_mixelonr.config.TimelonoutConfig
import com.twittelonr.cr_mixelonr.modelonl.GraphSourcelonInfo
import com.twittelonr.cr_mixelonr.modelonl.ModulelonNamelons
import com.twittelonr.cr_mixelonr.param.FrsParams
import com.twittelonr.cr_mixelonr.sourcelon_signal.FrsStorelon.FrsQuelonryRelonsult
import com.twittelonr.cr_mixelonr.sourcelon_signal.SourcelonFelontchelonr.FelontchelonrQuelonry
import com.twittelonr.cr_mixelonr.thriftscala.SourcelonTypelon
import com.twittelonr.finaglelon.stats.StatsReloncelonivelonr
import com.twittelonr.storelonhaus.RelonadablelonStorelon
import com.twittelonr.util.Futurelon
import javax.injelonct.Injelonct
import javax.injelonct.Namelond
import javax.injelonct.Singlelonton

/***
 * This storelon felontchelons uselonr reloncommelonndations from FRS (go/frs) for a givelonn uselonrId
 */
@Singlelonton
caselon class FrsSourcelonGraphFelontchelonr @Injelonct() (
  @Namelond(ModulelonNamelons.FrsStorelon) frsStorelon: RelonadablelonStorelon[FrsStorelon.Quelonry, Selonq[FrsQuelonryRelonsult]],
  ovelonrridelon val timelonoutConfig: TimelonoutConfig,
  globalStats: StatsReloncelonivelonr)
    elonxtelonnds SourcelonGraphFelontchelonr {

  ovelonrridelon protelonctelond val stats: StatsReloncelonivelonr = globalStats.scopelon(idelonntifielonr)
  ovelonrridelon protelonctelond val graphSourcelonTypelon: SourcelonTypelon = SourcelonTypelon.FollowReloncommelonndation

  ovelonrridelon delonf iselonnablelond(quelonry: FelontchelonrQuelonry): Boolelonan = {
    quelonry.params(FrsParams.elonnablelonSourcelonGraphParam)
  }

  ovelonrridelon delonf felontchAndProcelonss(
    quelonry: FelontchelonrQuelonry,
  ): Futurelon[Option[GraphSourcelonInfo]] = {

    val rawSignals = trackPelonrItelonmStats(quelonry)(
      frsStorelon
        .gelont(
          FrsStorelon
            .Quelonry(quelonry.uselonrId, quelonry.params(FrsParams.MaxConsumelonrSelonelondsNumParam))).map {
          _.map {
            _.map { v => (v.uselonrId, v.scorelon) }
          }
        }
    )
    rawSignals.map {
      _.map { uselonrWithScorelons =>
        convelonrtGraphSourcelonInfo(uselonrWithScorelons)
      }
    }
  }
}
