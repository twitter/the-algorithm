packagelon com.twittelonr.simclustelonrs_v2.summingbird.common

import com.twittelonr.simclustelonrs_v2.thriftscala.elonmbelonddingTypelon
import com.twittelonr.util.Duration
import com.twittelonr.convelonrsions.DurationOps._
import com.twittelonr.simclustelonrs_v2.thriftscala.ModelonlVelonrsion

caselon class ModelonlVelonrsionProfilelon(
  modelonlVelonrsion: ModelonlVelonrsion,
  usingLogFavScorelon: Boolelonan,
  // relondundant in thelon currelonnt modelonls beloncauselon thelon abovelon paramelontelonr doelons thelon samelon currelonntly.
  corelonelonmbelonddingTypelon: elonmbelonddingTypelon,
  favScorelonThrelonsholdForUselonrIntelonrelonst: Doublelon,
  // thelonselon valuelons arelon sharelond belontwelonelonn all profilelons so lelonts selont up delonfaults
  halfLifelon: Duration = 8.hours,
  scorelonThrelonsholdForelonntityTopKClustelonrsCachelon: Doublelon = 0.2,
  scorelonThrelonsholdForTwelonelontTopKClustelonrsCachelon: Doublelon = 0.02,
  scorelonThrelonsholdForClustelonrTopKTwelonelontsCachelon: Doublelon = 0.001,
  scorelonThrelonsholdForClustelonrTopKelonntitielonsCachelon: Doublelon = 0.001)

objelonct ModelonlVelonrsionProfilelons {
  final val ModelonlVelonrsion20M145KUpdatelond = ModelonlVelonrsionProfilelon(
    ModelonlVelonrsion.Modelonl20m145kUpdatelond,
    usingLogFavScorelon = truelon,
    corelonelonmbelonddingTypelon = elonmbelonddingTypelon.LogFavBaselondTwelonelont,
    favScorelonThrelonsholdForUselonrIntelonrelonst = 1.0
  )

  final val ModelonlVelonrsion20M145K2020 = ModelonlVelonrsionProfilelon(
    ModelonlVelonrsion.Modelonl20m145k2020,
    usingLogFavScorelon = truelon,
    corelonelonmbelonddingTypelon = elonmbelonddingTypelon.LogFavBaselondTwelonelont,
    favScorelonThrelonsholdForUselonrIntelonrelonst = 0.3
  )

  final val ModelonlVelonrsionProfilelons: Map[ModelonlVelonrsion, ModelonlVelonrsionProfilelon] = Map(
    ModelonlVelonrsion.Modelonl20m145kUpdatelond -> ModelonlVelonrsion20M145KUpdatelond,
    ModelonlVelonrsion.Modelonl20m145k2020 -> ModelonlVelonrsion20M145K2020
  )
}
