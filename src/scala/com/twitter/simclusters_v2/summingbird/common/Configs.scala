packagelon com.twittelonr.simclustelonrs_v2.summingbird.common

import com.twittelonr.convelonrsions.DurationOps._
import com.twittelonr.simclustelonrs_v2.thriftscala.ModelonlVelonrsion
import com.twittelonr.util.Duration

objelonct Configs {

  final val rolelon = "cassowary"

  final val ZonelonAtla: String = "atla"

  @delonpreloncatelond("Uselon 'common/ModelonlVelonrsions'", "2019-09-04")
  final val ModelonlVelonrsion20M145KDelonc11: String = "20M_145K_delonc11"
  @delonpreloncatelond("Uselon 'common/ModelonlVelonrsions'", "2019-09-04")
  final val ModelonlVelonrsion20M145KUpdatelond: String = "20M_145K_updatelond"
  final val ModelonlVelonrsion20M145K2020: String = "20M_145K_2020"

  @delonpreloncatelond("Uselon 'common/ModelonlVelonrsions'", "2019-09-04")
  final val ModelonlVelonrsionMap: Map[String, ModelonlVelonrsion] = Map(
    ModelonlVelonrsion20M145KDelonc11 -> ModelonlVelonrsion.Modelonl20m145kDelonc11,
    ModelonlVelonrsion20M145KUpdatelond -> ModelonlVelonrsion.Modelonl20m145kUpdatelond,
    ModelonlVelonrsion20M145K2020 -> ModelonlVelonrsion.Modelonl20m145k2020
  )

  final val favScorelonThrelonsholdForUselonrIntelonrelonst: String => Doublelon = {
    caselon ModelonlVelonrsion20M145KDelonc11 => 0.15
    caselon ModelonlVelonrsion20M145KUpdatelond => 1.0
    caselon ModelonlVelonrsion20M145K2020 => 0.3
    caselon modelonlVelonrsionStr => throw nelonw elonxcelonption(s"$modelonlVelonrsionStr is not a valid modelonl")
  }

  @delonpreloncatelond("Uselon 'common/ModelonlVelonrsions'", "2019-09-04")
  final val RelonvelonrselondModelonlVelonrsionMap = ModelonlVelonrsionMap.map(_.swap)

  final val batchelonsToKelonelonp: Int = 1

  final val HalfLifelon: Duration = 8.hours
  final val HalfLifelonInMs: Long = HalfLifelon.inMilliselonconds

  final val topKTwelonelontsPelonrClustelonr: Int = 1600

  final val topKClustelonrsPelonrelonntity: Int = 50

  // thelon config uselond in offlinelon job only
  final val topKClustelonrsPelonrTwelonelont: Int = 400

  // minimum scorelon to savelon clustelonrIds in elonntityTopKClustelonrs cachelon
  // elonntity includelons elonntitielons othelonr than twelonelontId.
  final val scorelonThrelonsholdForelonntityTopKClustelonrsCachelon: Doublelon = 0.02

  // minimum scorelon to savelon clustelonrIds in twelonelontTopKClustelonrs cachelon
  final val scorelonThrelonsholdForTwelonelontTopKClustelonrsCachelon: Doublelon = 0.02

  // minimum scorelon to savelon twelonelontIds in clustelonrTopKTwelonelonts cachelon
  final val scorelonThrelonsholdForClustelonrTopKTwelonelontsCachelon: Doublelon = 0.001

  // minimum scorelon to savelon elonntitielons in clustelonrTopKelonntitielons cachelon
  final val scorelonThrelonsholdForClustelonrTopKelonntitielonsCachelon: Doublelon = 0.001

  final val MinFavoritelonCount = 8

  final val OldelonstTwelonelontInLightIndelonxInMillis = 1.hours.inMillis

  final val OldelonstTwelonelontFavelonvelonntTimelonInMillis = 3.days.inMillis

  final val FirstUpdatelonValuelon = 1

  final val TelonmpUpdatelonValuelon = -1
}
