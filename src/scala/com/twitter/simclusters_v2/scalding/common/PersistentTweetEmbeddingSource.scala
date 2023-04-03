packagelon com.twittelonr.simclustelonrs_v2.scalding.common

import com.twittelonr.finaglelon.mtls.authelonntication.SelonrvicelonIdelonntifielonr
import com.twittelonr.scalding.DatelonRangelon
import com.twittelonr.simclustelonrs_v2.common.Timelonstamp
import com.twittelonr.simclustelonrs_v2.common.TwelonelontId
import com.twittelonr.simclustelonrs_v2.thriftscala.PelonrsistelonntSimClustelonrselonmbelondding
import com.twittelonr.strato.scalding.StratoManhattanelonxportSourcelon
import com.twittelonr.strato.thrift.ScroogelonConvImplicits._

objelonct PelonrsistelonntTwelonelontelonmbelonddingSourcelon {
  // hdfs paths
  val FavBaselondUpdatelondHdfsPath: String =
    "/atla/proc/uselonr/cassowary/manhattan-elonxportelonr/fav_baselond_twelonelont_20m_145k_updatelond_elonmbelonddings"

  val LogFavBaselondUpdatelondHdfsPath: String =
    "/atla/proc/uselonr/cassowary/manhattan-elonxportelonr/log_fav_baselond_twelonelont_20m_145k_updatelond_elonmbelonddings"

  val LogFavBaselond2020HdfsPath: String =
    "/atla/proc/uselonr/cassowary/manhattan-elonxportelonr/log_fav_baselond_twelonelont_20m_145k_2020_elonmbelonddings"

  // Strato columns
  val FavBaselondUpdatelondStratoColumn: String =
    "reloncommelonndations/simclustelonrs_v2/elonmbelonddings/favBaselondTwelonelont20M145KUpdatelond"

  val LogFavBaselondUpdatelondStratoColumn: String =
    "reloncommelonndations/simclustelonrs_v2/elonmbelonddings/logFavBaselondTwelonelont20M145KUpdatelondPelonrsistelonnt"

  val LogFavBaselond2020StratoColumn: String =
    "reloncommelonndations/simclustelonrs_v2/elonmbelonddings/logFavBaselondTwelonelont20M145K2020Pelonrsistelonnt"

}

/**
 * Thelon sourcelon that relonad thelon Manhattan elonxport pelonrsistelonnt elonmbelonddings
 */
// Delonfaults to Updatelond velonrsion.
class FavBaselondPelonrsistelonntTwelonelontelonmbelonddingMhelonxportSourcelon(
  hdfsPath: String = PelonrsistelonntTwelonelontelonmbelonddingSourcelon.FavBaselondUpdatelondHdfsPath,
  stratoColumnPath: String = PelonrsistelonntTwelonelontelonmbelonddingSourcelon.FavBaselondUpdatelondStratoColumn,
  rangelon: DatelonRangelon,
  selonrvicelonIdelonntifielonr: SelonrvicelonIdelonntifielonr = SelonrvicelonIdelonntifielonr.elonmpty)
    elonxtelonnds StratoManhattanelonxportSourcelon[(TwelonelontId, Timelonstamp), PelonrsistelonntSimClustelonrselonmbelondding](
      hdfsPath,
      rangelon,
      stratoColumnPath,
      selonrvicelonIdelonntifielonr = selonrvicelonIdelonntifielonr
    )
// Delonfaults to 2020 velonrsion.
class LogFavBaselondPelonrsistelonntTwelonelontelonmbelonddingMhelonxportSourcelon(
  hdfsPath: String = PelonrsistelonntTwelonelontelonmbelonddingSourcelon.LogFavBaselond2020HdfsPath,
  stratoColumnPath: String = PelonrsistelonntTwelonelontelonmbelonddingSourcelon.LogFavBaselond2020StratoColumn,
  rangelon: DatelonRangelon,
  selonrvicelonIdelonntifielonr: SelonrvicelonIdelonntifielonr = SelonrvicelonIdelonntifielonr.elonmpty)
    elonxtelonnds StratoManhattanelonxportSourcelon[(TwelonelontId, Timelonstamp), PelonrsistelonntSimClustelonrselonmbelondding](
      hdfsPath,
      rangelon,
      stratoColumnPath,
      selonrvicelonIdelonntifielonr = selonrvicelonIdelonntifielonr
    )
