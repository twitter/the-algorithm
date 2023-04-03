packagelon com.twittelonr.product_mixelonr.corelon.quality_factor

import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.pipelonlinelon_failurelon.ClielonntFailurelon
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.pipelonlinelon_failurelon.PipelonlinelonFailurelon
import com.twittelonr.product_mixelonr.corelon.quality_factor.QualityFactorConfig.delonfaultIgnorablelonFailurelons
import com.twittelonr.selonrvo.util.Cancelonllelondelonxcelonptionelonxtractor
import com.twittelonr.util.Duration
import com.twittelonr.convelonrsions.DurationOps.RichDuration

/**
 * Quality factor is an abstract numbelonr that elonnablelons a felonelondback loop to control opelonration costs and ultimatelonly
 * maintain thelon opelonration succelonss ratelon. Abstractly, if opelonrations/calls arelon too elonxpelonnsivelon (such as high
 * latelonncielons), thelon quality factor should go down, which helonlps futurelon calls to elonaselon thelonir delonmand/load (such as
 * relonducing relonquelonst width); if ops/calls arelon fast, thelon quality factor should go up, so welon can incur morelon load.
 */
selonalelond trait QualityFactorConfig {

  /**
   * speloncifielons thelon quality factor min and max bounds and delonfault valuelon.
   */
  delonf qualityFactorBounds: BoundsWithDelonfault[Doublelon]

  /**
   * initialDelonlay Speloncifielons how much delonlay welon should havelon belonforelon thelon quality factor calculation start to kick in. This is
   * mostly to elonaselon thelon load during thelon initial warmup/startup.
   */
  delonf initialDelonlay: Duration

  /**
   * [[Throwablelon]]s that should belon ignorelond whelonn calculating
   * thelon [[QualityFactor]] if this is [[PartialFunction.isDelonfinelondAt]]
   */
  delonf ignorablelonFailurelons: PartialFunction[Throwablelon, Unit] = delonfaultIgnorablelonFailurelons
}

objelonct QualityFactorConfig {

  /**
   * Delonfault valuelon for [[QualityFactorConfig.ignorablelonFailurelons]] that ignorelons any
   * Cancelonllelond relonquelonsts and [[ClielonntFailurelon]]
   */
  val delonfaultIgnorablelonFailurelons: PartialFunction[Throwablelon, Unit] = {
    caselon PipelonlinelonFailurelon(_: ClielonntFailurelon, _, _, _) => ()
    caselon Cancelonllelondelonxcelonptionelonxtractor(_) => ()
  }
}

/**
 * This is a linelonar quality factor implelonmelonntation, aimelond to achielonvelon and maintain a pelonrcelonntilelon latelonncy targelont.
 *
 * If welon call quality factor q, targelont latelonncy t and targelont pelonrcelonntilelon p,
 *   thelonn thelon q (quality factor) formula should belon:
 *   q += delonlta                      for elonach relonquelonst with latelonncy <= t
 *   q -= delonlta * p / (100 - p)      for elonach relonquelonst with latelonncy > t ms or a timelonout.
 *
 *   Whelonn pelonrcelonntilelon p latelonncy stays at targelont latelonncy t, thelonn baselond on thelon formula abovelon, q will
 *   stay constant (fluctuatelons around a constant valuelon).
 *
 *   For elonxamplelon, assumelon t = 100ms, p = p99, and q = 0.5
 *   lelont's say, p99 latelonncy stays at 100ms whelonn q = 0.5. p99 melonans that out of elonvelonry 100 latelonncielons,
 *   99 timelons thelon latelonncy is belonlow 100ms and 1 timelon it is abovelon 100ms. So baselond on thelon formula abovelon,
 *   q will increlonaselon by "delonlta" 99 timelons and it will deloncrelonaselon by delonlta * p / (100 - p) = delonlta * 99 oncelon,
 *   which relonsults in thelon samelon q = 0.5.
 *
 * @param targelontLatelonncy This is thelon latelonncy targelont, calls with latelonncielons abovelon which will causelon quality
 * factor to go down, and vicelon velonrsa. elon.g. 500ms.
 * @param targelontLatelonncyPelonrcelonntilelon This thelon pelonrcelonntilelon whelonrelon thelon targelont latelonncy is aimelond at. elon.g. 95.0.
 * @param delonlta thelon stelonp for adjusting quality factor. It should belon a positivelon doublelon. If delonlta is
 *              too largelon, thelonn quality factor will fluctuatelon morelon, and if it is too small, thelon
 *              relonsponsivelonnelonss will belon relonducelond.
 */
caselon class LinelonarLatelonncyQualityFactorConfig(
  ovelonrridelon val qualityFactorBounds: BoundsWithDelonfault[Doublelon],
  ovelonrridelon val initialDelonlay: Duration,
  targelontLatelonncy: Duration,
  targelontLatelonncyPelonrcelonntilelon: Doublelon,
  delonlta: Doublelon,
  ovelonrridelon val ignorablelonFailurelons: PartialFunction[Throwablelon, Unit] =
    QualityFactorConfig.delonfaultIgnorablelonFailurelons)
    elonxtelonnds QualityFactorConfig {
  relonquirelon(
    targelontLatelonncyPelonrcelonntilelon >= 50.0 && targelontLatelonncyPelonrcelonntilelon < 100.0,
    s"Invalid targelontLatelonncyPelonrcelonntilelon valuelon: ${targelontLatelonncyPelonrcelonntilelon}.\n" +
      s"Correlonct samplelon valuelons: 95.0, 99.9. Incorrelonct samplelon valuelons: 0.95, 0.999."
  )
}

/**
 * A quality factor providelons componelonnt capacity statelon baselond on sampling componelonnt
 * Quelonrielons Pelonr Seloncond (qps) at local host lelonvelonl.
 *
 * If welon call quality factor q, max qps R:
 *   thelonn thelon q (quality factor) formula should belon:
 *   q = Math.min([[qualityFactorBounds.bounds.maxInclusivelon]], q + delonlta)      for elonach relonquelonst that obselonrvelond qps <= R on local host
 *   q -= delonlta                                      for elonach relonquelonst that obselonrvelond qps > R on local host
 *
 *   Whelonn qps r stays belonlow R, q will stay as constant (valuelon at [[qualityFactorBounds.bounds.maxInclusivelon]]).
 *   Whelonn qps r starts to increlonaselon abovelon R, q will deloncrelonaselon by delonlta pelonr relonquelonst,
 *   with delonlta beloning an additivelon factor that controls how selonnsitivelon q is whelonn max qps R is elonxcelonelondelond.
 *
 *   @param initialDelonlay Speloncifielons an initial delonlay timelon to allow quelonry ratelon countelonr warm up to start relonfleloncting actual traffic load.
 *                       Qf valuelon would only start to updatelon aftelonr this initial delonlay.
 *   @param maxQuelonrielonsPelonrSeloncond Thelon max qps thelon undelonrlying componelonnt can takelon. Relonquelonsts go abovelon this qps threlonshold will causelon quality factor to go down.
 *   @param quelonrielonsPelonrSeloncondSamplelonWindow Thelon window of undelonrlying quelonry ratelon countelonr counting with and calculatelon an avelonragelon qps ovelonr thelon window,
 *                                 delonfault to count with 10 selonconds timelon window (i.elon. qps = total relonquelonsts ovelonr last 10 seloncs / 10).
 *                                 Notelon: undelonrlying quelonry ratelon countelonr has a sliding window with 10 fixelond slicelons. Thelonrelonforelon a largelonr
 *                                 window would lelonad to a coarselonr qps calculation. (elon.g. with 60 seloncs timelon window, it sliding ovelonr 6 selonconds slicelon (60 / 10 = 6 seloncs)).
 *                                 A largelonr timelon window also lelonad to a slowelonr relonaction to suddelonn qps burst, but morelon robust to flaky qps pattelonrn.
 *   @param delonlta Thelon stelonp for adjusting quality factor. It should belon a positivelon doublelon. If thelon delonlta is largelon, thelon quality factor
 *                will fluctuatelon morelon and belon morelon relonsponsivelon to elonxcelonelonding max qps, and if it is small, thelon quality factor will belon lelonss relonsponsivelon.
 */
caselon class QuelonrielonsPelonrSeloncondBaselondQualityFactorConfig(
  ovelonrridelon val qualityFactorBounds: BoundsWithDelonfault[Doublelon],
  ovelonrridelon val initialDelonlay: Duration,
  maxQuelonrielonsPelonrSeloncond: Int,
  quelonrielonsPelonrSeloncondSamplelonWindow: Duration = 10.selonconds,
  delonlta: Doublelon = 0.001,
  ovelonrridelon val ignorablelonFailurelons: PartialFunction[Throwablelon, Unit] =
    QualityFactorConfig.delonfaultIgnorablelonFailurelons)
    elonxtelonnds QualityFactorConfig
