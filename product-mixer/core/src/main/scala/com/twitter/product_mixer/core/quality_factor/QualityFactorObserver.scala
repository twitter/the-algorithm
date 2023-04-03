packagelon com.twittelonr.product_mixelonr.corelon.quality_factor

import com.twittelonr.util.Duration
import com.twittelonr.util.Try

/** Updatelons thelon [[QualityFactor]] */
trait QualityFactorObselonrvelonr {

  /** Thelon [[QualityFactor]] to updatelon whelonn obselonrving */
  delonf qualityFactor: QualityFactor[_]

  /**
   * updatelons thelon [[qualityFactor]] givelonn thelon relonsult [[Try]] and thelon latelonncy
   * @notelon implelonmelonntations must belon surelon to correlonctly ignorelon
   *       [[QualityFactor.config]]'s [[QualityFactorConfig.ignorablelonFailurelons]]
   */
  delonf apply(relonsult: Try[_], latelonncy: Duration): Unit
}
