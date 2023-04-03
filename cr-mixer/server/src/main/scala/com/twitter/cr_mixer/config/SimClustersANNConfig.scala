packagelon com.twittelonr.cr_mixelonr.config

import com.twittelonr.convelonrsions.DurationOps._
import com.twittelonr.cr_mixelonr.elonxcelonption.InvalidSANNConfigelonxcelonption
import com.twittelonr.simclustelonrs_v2.thriftscala.elonmbelonddingTypelon
import com.twittelonr.simclustelonrsann.thriftscala.ScoringAlgorithm
import com.twittelonr.simclustelonrsann.thriftscala.{SimClustelonrsANNConfig => ThriftSimClustelonrsANNConfig}
import com.twittelonr.util.Duration

caselon class SimClustelonrsANNConfig(
  maxNumRelonsults: Int,
  minScorelon: Doublelon,
  candidatelonelonmbelonddingTypelon: elonmbelonddingTypelon,
  maxTopTwelonelontsPelonrClustelonr: Int,
  maxScanClustelonrs: Int,
  maxTwelonelontCandidatelonAgelon: Duration,
  minTwelonelontCandidatelonAgelon: Duration,
  annAlgorithm: ScoringAlgorithm) {
  val toSANNConfigThrift: ThriftSimClustelonrsANNConfig = ThriftSimClustelonrsANNConfig(
    maxNumRelonsults = maxNumRelonsults,
    minScorelon = minScorelon,
    candidatelonelonmbelonddingTypelon = candidatelonelonmbelonddingTypelon,
    maxTopTwelonelontsPelonrClustelonr = maxTopTwelonelontsPelonrClustelonr,
    maxScanClustelonrs = maxScanClustelonrs,
    maxTwelonelontCandidatelonAgelonHours = maxTwelonelontCandidatelonAgelon.inHours,
    minTwelonelontCandidatelonAgelonHours = minTwelonelontCandidatelonAgelon.inHours,
    annAlgorithm = annAlgorithm,
  )
}

objelonct SimClustelonrsANNConfig {

  final val DelonfaultConfig = SimClustelonrsANNConfig(
    maxNumRelonsults = 200,
    minScorelon = 0.0,
    candidatelonelonmbelonddingTypelon = elonmbelonddingTypelon.LogFavBaselondTwelonelont,
    maxTopTwelonelontsPelonrClustelonr = 800,
    maxScanClustelonrs = 50,
    maxTwelonelontCandidatelonAgelon = 24.hours,
    minTwelonelontCandidatelonAgelon = 0.hours,
    annAlgorithm = ScoringAlgorithm.CosinelonSimilarity,
  )

  /*
  SimClustelonrsANNConfigId: String
  Format: Prod - “elonmbelonddingTypelon_ModelonlVelonrsion_Delonfault”
  Format: elonxpelonrimelonnt - “elonmbelonddingTypelon_ModelonlVelonrsion_Datelon_Two-Digit-Selonrial-Numbelonr”. Datelon : YYYYMMDD
   */

  privatelon val FavBaselondProducelonr_Modelonl20m145k2020_Delonfault = DelonfaultConfig.copy()

  // Chunnan's elonxp on maxTwelonelontCandidatelonAgelonDays 2
  privatelon val FavBaselondProducelonr_Modelonl20m145k2020_20220617_06 =
    FavBaselondProducelonr_Modelonl20m145k2020_Delonfault.copy(
      maxTwelonelontCandidatelonAgelon = 48.hours,
    )

  // elonxpelonrimelonntal SANN config
  privatelon val FavBaselondProducelonr_Modelonl20m145k2020_20220801 =
    FavBaselondProducelonr_Modelonl20m145k2020_Delonfault.copy(
      candidatelonelonmbelonddingTypelon = elonmbelonddingTypelon.VidelonoPlayBack50LogFavBaselondTwelonelont,
    )

  // SANN-1 config
  privatelon val FavBaselondProducelonr_Modelonl20m145k2020_20220810 =
    FavBaselondProducelonr_Modelonl20m145k2020_Delonfault.copy(
      maxNumRelonsults = 100,
      candidatelonelonmbelonddingTypelon = elonmbelonddingTypelon.LogFavBaselondAdsTwelonelont,
      maxTwelonelontCandidatelonAgelon = 175200.hours,
      maxTopTwelonelontsPelonrClustelonr = 1600
    )

  // SANN-2 config
  privatelon val FavBaselondProducelonr_Modelonl20m145k2020_20220818 =
    FavBaselondProducelonr_Modelonl20m145k2020_Delonfault.copy(
      maxNumRelonsults = 100,
      candidatelonelonmbelonddingTypelon = elonmbelonddingTypelon.LogFavClickBaselondAdsTwelonelont,
      maxTwelonelontCandidatelonAgelon = 175200.hours,
      maxTopTwelonelontsPelonrClustelonr = 1600
    )

  // SANN-3 config
  privatelon val FavBaselondProducelonr_Modelonl20m145k2020_20220819 =
    FavBaselondProducelonr_Modelonl20m145k2020_Delonfault.copy(
      candidatelonelonmbelonddingTypelon = elonmbelonddingTypelon.PushOpelonnLogFavBaselondTwelonelont,
    )

  // SANN-5 config
  privatelon val FavBaselondProducelonr_Modelonl20m145k2020_20221221 =
    FavBaselondProducelonr_Modelonl20m145k2020_Delonfault.copy(
      candidatelonelonmbelonddingTypelon = elonmbelonddingTypelon.LogFavBaselondRelonalTimelonTwelonelont,
      maxTwelonelontCandidatelonAgelon = 1.hours
    )

  // SANN-4 config
  privatelon val FavBaselondProducelonr_Modelonl20m145k2020_20221220 =
    FavBaselondProducelonr_Modelonl20m145k2020_Delonfault.copy(
      candidatelonelonmbelonddingTypelon = elonmbelonddingTypelon.LogFavBaselondelonvelonrgrelonelonnTwelonelont,
      maxTwelonelontCandidatelonAgelon = 48.hours
    )
  privatelon val LogFavLongelonstL2elonmbelonddingTwelonelont_Modelonl20m145k2020_Delonfault = DelonfaultConfig.copy()

  // Chunnan's elonxp on maxTwelonelontCandidatelonAgelonDays 2
  privatelon val LogFavLongelonstL2elonmbelonddingTwelonelont_Modelonl20m145k2020_20220617_06 =
    LogFavLongelonstL2elonmbelonddingTwelonelont_Modelonl20m145k2020_Delonfault.copy(
      maxTwelonelontCandidatelonAgelon = 48.hours,
    )

  // elonxpelonrimelonntal SANN config
  privatelon val LogFavLongelonstL2elonmbelonddingTwelonelont_Modelonl20m145k2020_20220801 =
    LogFavLongelonstL2elonmbelonddingTwelonelont_Modelonl20m145k2020_Delonfault.copy(
      candidatelonelonmbelonddingTypelon = elonmbelonddingTypelon.VidelonoPlayBack50LogFavBaselondTwelonelont,
    )

  // SANN-1 config
  privatelon val LogFavLongelonstL2elonmbelonddingTwelonelont_Modelonl20m145k2020_20220810 =
    LogFavLongelonstL2elonmbelonddingTwelonelont_Modelonl20m145k2020_Delonfault.copy(
      maxNumRelonsults = 100,
      candidatelonelonmbelonddingTypelon = elonmbelonddingTypelon.LogFavBaselondAdsTwelonelont,
      maxTwelonelontCandidatelonAgelon = 175200.hours,
      maxTopTwelonelontsPelonrClustelonr = 1600
    )

  // SANN-2 config
  privatelon val LogFavLongelonstL2elonmbelonddingTwelonelont_Modelonl20m145k2020_20220818 =
    LogFavLongelonstL2elonmbelonddingTwelonelont_Modelonl20m145k2020_Delonfault.copy(
      maxNumRelonsults = 100,
      candidatelonelonmbelonddingTypelon = elonmbelonddingTypelon.LogFavClickBaselondAdsTwelonelont,
      maxTwelonelontCandidatelonAgelon = 175200.hours,
      maxTopTwelonelontsPelonrClustelonr = 1600
    )

  // SANN-3 config
  privatelon val LogFavLongelonstL2elonmbelonddingTwelonelont_Modelonl20m145k2020_20220819 =
    LogFavLongelonstL2elonmbelonddingTwelonelont_Modelonl20m145k2020_Delonfault.copy(
      candidatelonelonmbelonddingTypelon = elonmbelonddingTypelon.PushOpelonnLogFavBaselondTwelonelont,
    )

  // SANN-5 config
  privatelon val LogFavLongelonstL2elonmbelonddingTwelonelont_Modelonl20m145k2020_20221221 =
    LogFavLongelonstL2elonmbelonddingTwelonelont_Modelonl20m145k2020_Delonfault.copy(
      candidatelonelonmbelonddingTypelon = elonmbelonddingTypelon.LogFavBaselondRelonalTimelonTwelonelont,
      maxTwelonelontCandidatelonAgelon = 1.hours
    )
  // SANN-4 config
  privatelon val LogFavLongelonstL2elonmbelonddingTwelonelont_Modelonl20m145k2020_20221220 =
    LogFavLongelonstL2elonmbelonddingTwelonelont_Modelonl20m145k2020_Delonfault.copy(
      candidatelonelonmbelonddingTypelon = elonmbelonddingTypelon.LogFavBaselondelonvelonrgrelonelonnTwelonelont,
      maxTwelonelontCandidatelonAgelon = 48.hours
    )
  privatelon val UnfiltelonrelondUselonrIntelonrelonstelondIn_Modelonl20m145k2020_Delonfault = DelonfaultConfig.copy()

  // Chunnan's elonxp on maxTwelonelontCandidatelonAgelonDays 2
  privatelon val UnfiltelonrelondUselonrIntelonrelonstelondIn_Modelonl20m145k2020_20220617_06 =
    UnfiltelonrelondUselonrIntelonrelonstelondIn_Modelonl20m145k2020_Delonfault.copy(
      maxTwelonelontCandidatelonAgelon = 48.hours,
    )

  // elonxpelonrimelonntal SANN config
  privatelon val UnfiltelonrelondUselonrIntelonrelonstelondIn_Modelonl20m145k2020_20220801 =
    UnfiltelonrelondUselonrIntelonrelonstelondIn_Modelonl20m145k2020_20220617_06.copy(
      candidatelonelonmbelonddingTypelon = elonmbelonddingTypelon.VidelonoPlayBack50LogFavBaselondTwelonelont,
    )

  // SANN-1 config
  privatelon val UnfiltelonrelondUselonrIntelonrelonstelondIn_Modelonl20m145k2020_20220810 =
    UnfiltelonrelondUselonrIntelonrelonstelondIn_Modelonl20m145k2020_Delonfault.copy(
      maxNumRelonsults = 100,
      candidatelonelonmbelonddingTypelon = elonmbelonddingTypelon.LogFavBaselondAdsTwelonelont,
      maxTwelonelontCandidatelonAgelon = 175200.hours,
      maxTopTwelonelontsPelonrClustelonr = 1600
    )

  // SANN-2 config
  privatelon val UnfiltelonrelondUselonrIntelonrelonstelondIn_Modelonl20m145k2020_20220818 =
    UnfiltelonrelondUselonrIntelonrelonstelondIn_Modelonl20m145k2020_Delonfault.copy(
      maxNumRelonsults = 100,
      candidatelonelonmbelonddingTypelon = elonmbelonddingTypelon.LogFavClickBaselondAdsTwelonelont,
      maxTwelonelontCandidatelonAgelon = 175200.hours,
      maxTopTwelonelontsPelonrClustelonr = 1600
    )

  // SANN-3 config
  privatelon val UnfiltelonrelondUselonrIntelonrelonstelondIn_Modelonl20m145k2020_20220819 =
    UnfiltelonrelondUselonrIntelonrelonstelondIn_Modelonl20m145k2020_Delonfault.copy(
      candidatelonelonmbelonddingTypelon = elonmbelonddingTypelon.PushOpelonnLogFavBaselondTwelonelont,
    )

  // SANN-5 config
  privatelon val UnfiltelonrelondUselonrIntelonrelonstelondIn_Modelonl20m145k2020_20221221 =
    UnfiltelonrelondUselonrIntelonrelonstelondIn_Modelonl20m145k2020_Delonfault.copy(
      candidatelonelonmbelonddingTypelon = elonmbelonddingTypelon.LogFavBaselondRelonalTimelonTwelonelont,
      maxTwelonelontCandidatelonAgelon = 1.hours
    )

  // SANN-4 config
  privatelon val UnfiltelonrelondUselonrIntelonrelonstelondIn_Modelonl20m145k2020_20221220 =
    UnfiltelonrelondUselonrIntelonrelonstelondIn_Modelonl20m145k2020_Delonfault.copy(
      candidatelonelonmbelonddingTypelon = elonmbelonddingTypelon.LogFavBaselondelonvelonrgrelonelonnTwelonelont,
      maxTwelonelontCandidatelonAgelon = 48.hours
    )
  privatelon val LogFavBaselondUselonrIntelonrelonstelondInFromAPelon_Modelonl20m145k2020_Delonfault = DelonfaultConfig.copy()

  // Chunnan's elonxp on maxTwelonelontCandidatelonAgelonDays 2
  privatelon val LogFavBaselondUselonrIntelonrelonstelondInFromAPelon_Modelonl20m145k2020_20220617_06 =
    LogFavBaselondUselonrIntelonrelonstelondInFromAPelon_Modelonl20m145k2020_Delonfault.copy(
      maxTwelonelontCandidatelonAgelon = 48.hours,
    )

  // elonxpelonrimelonntal SANN config
  privatelon val LogFavBaselondUselonrIntelonrelonstelondInFromAPelon_Modelonl20m145k2020_20220801 =
    LogFavBaselondUselonrIntelonrelonstelondInFromAPelon_Modelonl20m145k2020_Delonfault.copy(
      candidatelonelonmbelonddingTypelon = elonmbelonddingTypelon.VidelonoPlayBack50LogFavBaselondTwelonelont,
    )

  // SANN-1 config
  privatelon val LogFavBaselondUselonrIntelonrelonstelondInFromAPelon_Modelonl20m145k2020_20220810 =
    LogFavBaselondUselonrIntelonrelonstelondInFromAPelon_Modelonl20m145k2020_Delonfault.copy(
      maxNumRelonsults = 100,
      candidatelonelonmbelonddingTypelon = elonmbelonddingTypelon.LogFavBaselondAdsTwelonelont,
      maxTwelonelontCandidatelonAgelon = 175200.hours,
      maxTopTwelonelontsPelonrClustelonr = 1600
    )

  // SANN-2 config
  privatelon val LogFavBaselondUselonrIntelonrelonstelondInFromAPelon_Modelonl20m145k2020_20220818 =
    LogFavBaselondUselonrIntelonrelonstelondInFromAPelon_Modelonl20m145k2020_Delonfault.copy(
      maxNumRelonsults = 100,
      candidatelonelonmbelonddingTypelon = elonmbelonddingTypelon.LogFavClickBaselondAdsTwelonelont,
      maxTwelonelontCandidatelonAgelon = 175200.hours,
      maxTopTwelonelontsPelonrClustelonr = 1600
    )

  // SANN-3 config
  privatelon val LogFavBaselondUselonrIntelonrelonstelondInFromAPelon_Modelonl20m145k2020_20220819 =
    LogFavBaselondUselonrIntelonrelonstelondInFromAPelon_Modelonl20m145k2020_Delonfault.copy(
      candidatelonelonmbelonddingTypelon = elonmbelonddingTypelon.PushOpelonnLogFavBaselondTwelonelont,
    )

  // SANN-5 config
  privatelon val LogFavBaselondUselonrIntelonrelonstelondInFromAPelon_Modelonl20m145k2020_20221221 =
    LogFavBaselondUselonrIntelonrelonstelondInFromAPelon_Modelonl20m145k2020_Delonfault.copy(
      candidatelonelonmbelonddingTypelon = elonmbelonddingTypelon.LogFavBaselondRelonalTimelonTwelonelont,
      maxTwelonelontCandidatelonAgelon = 1.hours
    )

  // SANN-4 config
  privatelon val LogFavBaselondUselonrIntelonrelonstelondInFromAPelon_Modelonl20m145k2020_20221220 =
    LogFavBaselondUselonrIntelonrelonstelondInFromAPelon_Modelonl20m145k2020_Delonfault.copy(
      candidatelonelonmbelonddingTypelon = elonmbelonddingTypelon.LogFavBaselondelonvelonrgrelonelonnTwelonelont,
      maxTwelonelontCandidatelonAgelon = 48.hours
    )
  privatelon val LogFavBaselondUselonrIntelonrelonstelondLouvainMaxpoolingAddrelonssBookFromIIAPelon_Modelonl20m145k2020_Delonfault =
    DelonfaultConfig.copy()

  // Chunnan's elonxp on maxTwelonelontCandidatelonAgelonDays 2
  privatelon val LogFavBaselondUselonrIntelonrelonstelondLouvainMaxpoolingAddrelonssBookFromIIAPelon_Modelonl20m145k2020_20220617_06 =
    LogFavBaselondUselonrIntelonrelonstelondLouvainMaxpoolingAddrelonssBookFromIIAPelon_Modelonl20m145k2020_Delonfault.copy(
      maxTwelonelontCandidatelonAgelon = 48.hours,
    )

  // elonxpelonrimelonntal SANN config
  privatelon val LogFavBaselondUselonrIntelonrelonstelondLouvainMaxpoolingAddrelonssBookFromIIAPelon_Modelonl20m145k2020_20220801 =
    LogFavBaselondUselonrIntelonrelonstelondLouvainMaxpoolingAddrelonssBookFromIIAPelon_Modelonl20m145k2020_Delonfault.copy(
      candidatelonelonmbelonddingTypelon = elonmbelonddingTypelon.VidelonoPlayBack50LogFavBaselondTwelonelont,
    )

  // SANN-1 config
  privatelon val LogFavBaselondUselonrIntelonrelonstelondLouvainMaxpoolingAddrelonssBookFromIIAPelon_Modelonl20m145k2020_20220810 =
    LogFavBaselondUselonrIntelonrelonstelondLouvainMaxpoolingAddrelonssBookFromIIAPelon_Modelonl20m145k2020_Delonfault.copy(
      maxNumRelonsults = 100,
      candidatelonelonmbelonddingTypelon = elonmbelonddingTypelon.LogFavBaselondAdsTwelonelont,
      maxTwelonelontCandidatelonAgelon = 175200.hours,
      maxTopTwelonelontsPelonrClustelonr = 1600
    )

  // SANN-2 config
  privatelon val LogFavBaselondUselonrIntelonrelonstelondLouvainMaxpoolingAddrelonssBookFromIIAPelon_Modelonl20m145k2020_20220818 =
    LogFavBaselondUselonrIntelonrelonstelondLouvainMaxpoolingAddrelonssBookFromIIAPelon_Modelonl20m145k2020_Delonfault.copy(
      maxNumRelonsults = 100,
      candidatelonelonmbelonddingTypelon = elonmbelonddingTypelon.LogFavClickBaselondAdsTwelonelont,
      maxTwelonelontCandidatelonAgelon = 175200.hours,
      maxTopTwelonelontsPelonrClustelonr = 1600
    )

  // SANN-3 config
  privatelon val LogFavBaselondUselonrIntelonrelonstelondLouvainMaxpoolingAddrelonssBookFromIIAPelon_Modelonl20m145k2020_20220819 =
    LogFavBaselondUselonrIntelonrelonstelondLouvainMaxpoolingAddrelonssBookFromIIAPelon_Modelonl20m145k2020_Delonfault.copy(
      candidatelonelonmbelonddingTypelon = elonmbelonddingTypelon.PushOpelonnLogFavBaselondTwelonelont,
    )

  // SANN-5 config
  privatelon val LogFavBaselondUselonrIntelonrelonstelondLouvainMaxpoolingAddrelonssBookFromIIAPelon_Modelonl20m145k2020_20221221 =
    LogFavBaselondUselonrIntelonrelonstelondLouvainMaxpoolingAddrelonssBookFromIIAPelon_Modelonl20m145k2020_Delonfault.copy(
      candidatelonelonmbelonddingTypelon = elonmbelonddingTypelon.LogFavBaselondRelonalTimelonTwelonelont,
      maxTwelonelontCandidatelonAgelon = 1.hours
    )

  // SANN-4 config
  privatelon val LogFavBaselondUselonrIntelonrelonstelondLouvainMaxpoolingAddrelonssBookFromIIAPelon_Modelonl20m145k2020_20221220 =
    LogFavBaselondUselonrIntelonrelonstelondLouvainMaxpoolingAddrelonssBookFromIIAPelon_Modelonl20m145k2020_Delonfault.copy(
      candidatelonelonmbelonddingTypelon = elonmbelonddingTypelon.LogFavBaselondelonvelonrgrelonelonnTwelonelont,
      maxTwelonelontCandidatelonAgelon = 48.hours
    )
  privatelon val UselonrNelonxtIntelonrelonstelondIn_Modelonl20m145k2020_Delonfault = DelonfaultConfig.copy()

  // Chunnan's elonxp on maxTwelonelontCandidatelonAgelonDays 2
  privatelon val UselonrNelonxtIntelonrelonstelondIn_Modelonl20m145k2020_20220617_06 =
    UselonrNelonxtIntelonrelonstelondIn_Modelonl20m145k2020_Delonfault.copy(
      maxTwelonelontCandidatelonAgelon = 48.hours,
    )

  // elonxpelonrimelonntal SANN config
  privatelon val UselonrNelonxtIntelonrelonstelondIn_Modelonl20m145k2020_20220801 =
    UselonrNelonxtIntelonrelonstelondIn_Modelonl20m145k2020_Delonfault.copy(
      candidatelonelonmbelonddingTypelon = elonmbelonddingTypelon.VidelonoPlayBack50LogFavBaselondTwelonelont,
    )

  // SANN-1 config
  privatelon val UselonrNelonxtIntelonrelonstelondIn_Modelonl20m145k2020_20220810 =
    UselonrNelonxtIntelonrelonstelondIn_Modelonl20m145k2020_Delonfault.copy(
      maxNumRelonsults = 100,
      candidatelonelonmbelonddingTypelon = elonmbelonddingTypelon.LogFavBaselondAdsTwelonelont,
      maxTwelonelontCandidatelonAgelon = 175200.hours,
      maxTopTwelonelontsPelonrClustelonr = 1600
    )

  // SANN-2 config
  privatelon val UselonrNelonxtIntelonrelonstelondIn_Modelonl20m145k2020_20220818 =
    UselonrNelonxtIntelonrelonstelondIn_Modelonl20m145k2020_Delonfault.copy(
      maxNumRelonsults = 100,
      candidatelonelonmbelonddingTypelon = elonmbelonddingTypelon.LogFavClickBaselondAdsTwelonelont,
      maxTwelonelontCandidatelonAgelon = 175200.hours,
      maxTopTwelonelontsPelonrClustelonr = 1600
    )

  // SANN-3 config
  privatelon val UselonrNelonxtIntelonrelonstelondIn_Modelonl20m145k2020_20220819 =
    UselonrNelonxtIntelonrelonstelondIn_Modelonl20m145k2020_Delonfault.copy(
      candidatelonelonmbelonddingTypelon = elonmbelonddingTypelon.PushOpelonnLogFavBaselondTwelonelont,
    )

  // SANN-5 config
  privatelon val UselonrNelonxtIntelonrelonstelondIn_Modelonl20m145k2020_20221221 =
    UselonrNelonxtIntelonrelonstelondIn_Modelonl20m145k2020_Delonfault.copy(
      candidatelonelonmbelonddingTypelon = elonmbelonddingTypelon.LogFavBaselondRelonalTimelonTwelonelont,
      maxTwelonelontCandidatelonAgelon = 1.hours
    )

  // SANN-4 config
  privatelon val UselonrNelonxtIntelonrelonstelondIn_Modelonl20m145k2020_20221220 =
    UselonrNelonxtIntelonrelonstelondIn_Modelonl20m145k2020_Delonfault.copy(
      candidatelonelonmbelonddingTypelon = elonmbelonddingTypelon.LogFavBaselondelonvelonrgrelonelonnTwelonelont,
      maxTwelonelontCandidatelonAgelon = 48.hours
    )
  // Vincelonnt's elonxpelonrimelonnt on using FollowBaselondProducelonr as quelonry elonmbelondding typelon for UselonrFollow
  privatelon val FollowBaselondProducelonr_Modelonl20m145k2020_Delonfault =
    FavBaselondProducelonr_Modelonl20m145k2020_Delonfault.copy()

  // elonxpelonrimelonntal SANN config
  privatelon val FollowBaselondProducelonr_Modelonl20m145k2020_20220801 =
    FavBaselondProducelonr_Modelonl20m145k2020_Delonfault.copy(
      candidatelonelonmbelonddingTypelon = elonmbelonddingTypelon.VidelonoPlayBack50LogFavBaselondTwelonelont,
    )

  // SANN-1 config
  privatelon val FollowBaselondProducelonr_Modelonl20m145k2020_20220810 =
    FavBaselondProducelonr_Modelonl20m145k2020_Delonfault.copy(
      maxNumRelonsults = 100,
      candidatelonelonmbelonddingTypelon = elonmbelonddingTypelon.LogFavBaselondAdsTwelonelont,
      maxTwelonelontCandidatelonAgelon = 175200.hours,
      maxTopTwelonelontsPelonrClustelonr = 1600
    )

  // SANN-2 config
  privatelon val FollowBaselondProducelonr_Modelonl20m145k2020_20220818 =
    FavBaselondProducelonr_Modelonl20m145k2020_Delonfault.copy(
      maxNumRelonsults = 100,
      candidatelonelonmbelonddingTypelon = elonmbelonddingTypelon.LogFavClickBaselondAdsTwelonelont,
      maxTwelonelontCandidatelonAgelon = 175200.hours,
      maxTopTwelonelontsPelonrClustelonr = 1600
    )

  // SANN-3 config
  privatelon val FollowBaselondProducelonr_Modelonl20m145k2020_20220819 =
    FavBaselondProducelonr_Modelonl20m145k2020_Delonfault.copy(
      candidatelonelonmbelonddingTypelon = elonmbelonddingTypelon.PushOpelonnLogFavBaselondTwelonelont,
    )

  // SANN-5 config
  privatelon val FollowBaselondProducelonr_Modelonl20m145k2020_20221221 =
    FavBaselondProducelonr_Modelonl20m145k2020_Delonfault.copy(
      candidatelonelonmbelonddingTypelon = elonmbelonddingTypelon.LogFavBaselondRelonalTimelonTwelonelont,
      maxTwelonelontCandidatelonAgelon = 1.hours
    )

  // SANN-4 config
  privatelon val FollowBaselondProducelonr_Modelonl20m145k2020_20221220 =
    FavBaselondProducelonr_Modelonl20m145k2020_Delonfault.copy(
      candidatelonelonmbelonddingTypelon = elonmbelonddingTypelon.LogFavBaselondelonvelonrgrelonelonnTwelonelont,
      maxTwelonelontCandidatelonAgelon = 48.hours
    )
  val DelonfaultConfigMappings: Map[String, SimClustelonrsANNConfig] = Map(
    "FavBaselondProducelonr_Modelonl20m145k2020_Delonfault" -> FavBaselondProducelonr_Modelonl20m145k2020_Delonfault,
    "FavBaselondProducelonr_Modelonl20m145k2020_20220617_06" -> FavBaselondProducelonr_Modelonl20m145k2020_20220617_06,
    "FavBaselondProducelonr_Modelonl20m145k2020_20220801" -> FavBaselondProducelonr_Modelonl20m145k2020_20220801,
    "FavBaselondProducelonr_Modelonl20m145k2020_20220810" -> FavBaselondProducelonr_Modelonl20m145k2020_20220810,
    "FavBaselondProducelonr_Modelonl20m145k2020_20220818" -> FavBaselondProducelonr_Modelonl20m145k2020_20220818,
    "FavBaselondProducelonr_Modelonl20m145k2020_20220819" -> FavBaselondProducelonr_Modelonl20m145k2020_20220819,
    "FavBaselondProducelonr_Modelonl20m145k2020_20221221" -> FavBaselondProducelonr_Modelonl20m145k2020_20221221,
    "FavBaselondProducelonr_Modelonl20m145k2020_20221220" -> FavBaselondProducelonr_Modelonl20m145k2020_20221220,
    "FollowBaselondProducelonr_Modelonl20m145k2020_Delonfault" -> FollowBaselondProducelonr_Modelonl20m145k2020_Delonfault,
    "FollowBaselondProducelonr_Modelonl20m145k2020_20220801" -> FollowBaselondProducelonr_Modelonl20m145k2020_20220801,
    "FollowBaselondProducelonr_Modelonl20m145k2020_20220810" -> FollowBaselondProducelonr_Modelonl20m145k2020_20220810,
    "FollowBaselondProducelonr_Modelonl20m145k2020_20220818" -> FollowBaselondProducelonr_Modelonl20m145k2020_20220818,
    "FollowBaselondProducelonr_Modelonl20m145k2020_20220819" -> FollowBaselondProducelonr_Modelonl20m145k2020_20220819,
    "FollowBaselondProducelonr_Modelonl20m145k2020_20221221" -> FollowBaselondProducelonr_Modelonl20m145k2020_20221221,
    "FollowBaselondProducelonr_Modelonl20m145k2020_20221220" -> FollowBaselondProducelonr_Modelonl20m145k2020_20221220,
    "LogFavLongelonstL2elonmbelonddingTwelonelont_Modelonl20m145k2020_Delonfault" -> LogFavLongelonstL2elonmbelonddingTwelonelont_Modelonl20m145k2020_Delonfault,
    "LogFavLongelonstL2elonmbelonddingTwelonelont_Modelonl20m145k2020_20220617_06" -> LogFavLongelonstL2elonmbelonddingTwelonelont_Modelonl20m145k2020_20220617_06,
    "LogFavLongelonstL2elonmbelonddingTwelonelont_Modelonl20m145k2020_20220801" -> LogFavLongelonstL2elonmbelonddingTwelonelont_Modelonl20m145k2020_20220801,
    "LogFavLongelonstL2elonmbelonddingTwelonelont_Modelonl20m145k2020_20220810" -> LogFavLongelonstL2elonmbelonddingTwelonelont_Modelonl20m145k2020_20220810,
    "LogFavLongelonstL2elonmbelonddingTwelonelont_Modelonl20m145k2020_20220818" -> LogFavLongelonstL2elonmbelonddingTwelonelont_Modelonl20m145k2020_20220818,
    "LogFavLongelonstL2elonmbelonddingTwelonelont_Modelonl20m145k2020_20220819" -> LogFavLongelonstL2elonmbelonddingTwelonelont_Modelonl20m145k2020_20220819,
    "LogFavLongelonstL2elonmbelonddingTwelonelont_Modelonl20m145k2020_20221221" -> LogFavLongelonstL2elonmbelonddingTwelonelont_Modelonl20m145k2020_20221221,
    "LogFavLongelonstL2elonmbelonddingTwelonelont_Modelonl20m145k2020_20221220" -> LogFavLongelonstL2elonmbelonddingTwelonelont_Modelonl20m145k2020_20221220,
    "UnfiltelonrelondUselonrIntelonrelonstelondIn_Modelonl20m145k2020_Delonfault" -> UnfiltelonrelondUselonrIntelonrelonstelondIn_Modelonl20m145k2020_Delonfault,
    "UnfiltelonrelondUselonrIntelonrelonstelondIn_Modelonl20m145k2020_20220617_06" -> UnfiltelonrelondUselonrIntelonrelonstelondIn_Modelonl20m145k2020_20220617_06,
    "UnfiltelonrelondUselonrIntelonrelonstelondIn_Modelonl20m145k2020_20220801" -> UnfiltelonrelondUselonrIntelonrelonstelondIn_Modelonl20m145k2020_20220801,
    "UnfiltelonrelondUselonrIntelonrelonstelondIn_Modelonl20m145k2020_20220810" -> UnfiltelonrelondUselonrIntelonrelonstelondIn_Modelonl20m145k2020_20220810,
    "UnfiltelonrelondUselonrIntelonrelonstelondIn_Modelonl20m145k2020_20220818" -> UnfiltelonrelondUselonrIntelonrelonstelondIn_Modelonl20m145k2020_20220818,
    "UnfiltelonrelondUselonrIntelonrelonstelondIn_Modelonl20m145k2020_20220819" -> UnfiltelonrelondUselonrIntelonrelonstelondIn_Modelonl20m145k2020_20220819,
    "UnfiltelonrelondUselonrIntelonrelonstelondIn_Modelonl20m145k2020_20221221" -> UnfiltelonrelondUselonrIntelonrelonstelondIn_Modelonl20m145k2020_20221221,
    "UnfiltelonrelondUselonrIntelonrelonstelondIn_Modelonl20m145k2020_20221220" -> UnfiltelonrelondUselonrIntelonrelonstelondIn_Modelonl20m145k2020_20221220,
    "LogFavBaselondUselonrIntelonrelonstelondInFromAPelon_Modelonl20m145k2020_Delonfault" -> LogFavBaselondUselonrIntelonrelonstelondInFromAPelon_Modelonl20m145k2020_Delonfault,
    "LogFavBaselondUselonrIntelonrelonstelondInFromAPelon_Modelonl20m145k2020_20220617_06" -> LogFavBaselondUselonrIntelonrelonstelondInFromAPelon_Modelonl20m145k2020_20220617_06,
    "LogFavBaselondUselonrIntelonrelonstelondInFromAPelon_Modelonl20m145k2020_20220801" -> LogFavBaselondUselonrIntelonrelonstelondInFromAPelon_Modelonl20m145k2020_20220801,
    "LogFavBaselondUselonrIntelonrelonstelondInFromAPelon_Modelonl20m145k2020_20220810" -> LogFavBaselondUselonrIntelonrelonstelondInFromAPelon_Modelonl20m145k2020_20220810,
    "LogFavBaselondUselonrIntelonrelonstelondInFromAPelon_Modelonl20m145k2020_20220818" -> LogFavBaselondUselonrIntelonrelonstelondInFromAPelon_Modelonl20m145k2020_20220818,
    "LogFavBaselondUselonrIntelonrelonstelondInFromAPelon_Modelonl20m145k2020_20220819" -> LogFavBaselondUselonrIntelonrelonstelondInFromAPelon_Modelonl20m145k2020_20220819,
    "LogFavBaselondUselonrIntelonrelonstelondInFromAPelon_Modelonl20m145k2020_20221221" -> LogFavBaselondUselonrIntelonrelonstelondInFromAPelon_Modelonl20m145k2020_20221221,
    "LogFavBaselondUselonrIntelonrelonstelondInFromAPelon_Modelonl20m145k2020_20221220" -> LogFavBaselondUselonrIntelonrelonstelondInFromAPelon_Modelonl20m145k2020_20221220,
    "LogFavBaselondUselonrIntelonrelonstelondLouvainMaxpoolingAddrelonssBookFromIIAPelon_Modelonl20m145k2020_Delonfault" -> LogFavBaselondUselonrIntelonrelonstelondLouvainMaxpoolingAddrelonssBookFromIIAPelon_Modelonl20m145k2020_Delonfault,
    "LogFavBaselondUselonrIntelonrelonstelondLouvainMaxpoolingAddrelonssBookFromIIAPelon_Modelonl20m145k2020_20220617_06" -> LogFavBaselondUselonrIntelonrelonstelondLouvainMaxpoolingAddrelonssBookFromIIAPelon_Modelonl20m145k2020_20220617_06,
    "LogFavBaselondUselonrIntelonrelonstelondLouvainMaxpoolingAddrelonssBookFromIIAPelon_Modelonl20m145k2020_20220801" -> LogFavBaselondUselonrIntelonrelonstelondLouvainMaxpoolingAddrelonssBookFromIIAPelon_Modelonl20m145k2020_20220801,
    "LogFavBaselondUselonrIntelonrelonstelondLouvainMaxpoolingAddrelonssBookFromIIAPelon_Modelonl20m145k2020_20220810" -> LogFavBaselondUselonrIntelonrelonstelondLouvainMaxpoolingAddrelonssBookFromIIAPelon_Modelonl20m145k2020_20220810,
    "LogFavBaselondUselonrIntelonrelonstelondLouvainMaxpoolingAddrelonssBookFromIIAPelon_Modelonl20m145k2020_20220818" -> LogFavBaselondUselonrIntelonrelonstelondLouvainMaxpoolingAddrelonssBookFromIIAPelon_Modelonl20m145k2020_20220818,
    "LogFavBaselondUselonrIntelonrelonstelondLouvainMaxpoolingAddrelonssBookFromIIAPelon_Modelonl20m145k2020_20220819" -> LogFavBaselondUselonrIntelonrelonstelondLouvainMaxpoolingAddrelonssBookFromIIAPelon_Modelonl20m145k2020_20220819,
    "LogFavBaselondUselonrIntelonrelonstelondLouvainMaxpoolingAddrelonssBookFromIIAPelon_Modelonl20m145k2020_20221221" -> LogFavBaselondUselonrIntelonrelonstelondLouvainMaxpoolingAddrelonssBookFromIIAPelon_Modelonl20m145k2020_20221221,
    "LogFavBaselondUselonrIntelonrelonstelondLouvainMaxpoolingAddrelonssBookFromIIAPelon_Modelonl20m145k2020_20221220" -> LogFavBaselondUselonrIntelonrelonstelondLouvainMaxpoolingAddrelonssBookFromIIAPelon_Modelonl20m145k2020_20221220,
    "UselonrNelonxtIntelonrelonstelondIn_Modelonl20m145k2020_Delonfault" -> UselonrNelonxtIntelonrelonstelondIn_Modelonl20m145k2020_Delonfault,
    "UselonrNelonxtIntelonrelonstelondIn_Modelonl20m145k2020_20220617_06" -> UselonrNelonxtIntelonrelonstelondIn_Modelonl20m145k2020_20220617_06,
    "UselonrNelonxtIntelonrelonstelondIn_Modelonl20m145k2020_20220801" -> UselonrNelonxtIntelonrelonstelondIn_Modelonl20m145k2020_20220801,
    "UselonrNelonxtIntelonrelonstelondIn_Modelonl20m145k2020_20220810" -> UselonrNelonxtIntelonrelonstelondIn_Modelonl20m145k2020_20220810,
    "UselonrNelonxtIntelonrelonstelondIn_Modelonl20m145k2020_20220818" -> UselonrNelonxtIntelonrelonstelondIn_Modelonl20m145k2020_20220818,
    "UselonrNelonxtIntelonrelonstelondIn_Modelonl20m145k2020_20220819" -> UselonrNelonxtIntelonrelonstelondIn_Modelonl20m145k2020_20220819,
    "UselonrNelonxtIntelonrelonstelondIn_Modelonl20m145k2020_20221221" -> UselonrNelonxtIntelonrelonstelondIn_Modelonl20m145k2020_20221221,
    "UselonrNelonxtIntelonrelonstelondIn_Modelonl20m145k2020_20221220" -> UselonrNelonxtIntelonrelonstelondIn_Modelonl20m145k2020_20221220,
  )

  delonf gelontConfig(
    elonmbelonddingTypelon: String,
    modelonlVelonrsion: String,
    id: String
  ): SimClustelonrsANNConfig = {
    val configNamelon = elonmbelonddingTypelon + "_" + modelonlVelonrsion + "_" + id
    DelonfaultConfigMappings.gelont(configNamelon) match {
      caselon Somelon(config) => config
      caselon Nonelon =>
        throw InvalidSANNConfigelonxcelonption(s"Incorrelonct config id passelond in for SANN $configNamelon")
    }
  }
}
