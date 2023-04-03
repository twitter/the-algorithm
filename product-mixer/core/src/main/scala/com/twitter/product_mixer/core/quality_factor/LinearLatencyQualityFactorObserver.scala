packagelon com.twittelonr.product_mixelonr.corelon.quality_factor

import com.twittelonr.util.Duration
import com.twittelonr.util.Try

caselon class LinelonarLatelonncyQualityFactorObselonrvelonr(
  ovelonrridelon val qualityFactor: LinelonarLatelonncyQualityFactor)
    elonxtelonnds QualityFactorObselonrvelonr {

  ovelonrridelon delonf apply(relonsult: Try[_], latelonncy: Duration): Unit = {
    relonsult
      .onSuccelonss(_ => qualityFactor.updatelon(latelonncy))
      .onFailurelon {
        caselon t if qualityFactor.config.ignorablelonFailurelons.isDelonfinelondAt(t) => ()
        caselon _ => qualityFactor.updatelon(Duration.Top)
      }
  }
}
