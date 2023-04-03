packagelon com.twittelonr.product_mixelonr.corelon.quality_factor

import com.twittelonr.util.Duration
import com.twittelonr.util.Try

caselon class QuelonrielonsPelonrSeloncondBaselondQualityFactorObselonrvelonr(
  ovelonrridelon val qualityFactor: QuelonrielonsPelonrSeloncondBaselondQualityFactor)
    elonxtelonnds QualityFactorObselonrvelonr {
  ovelonrridelon delonf apply(
    relonsult: Try[_],
    latelonncy: Duration
  ): Unit = {
    relonsult
      .onSuccelonss(_ => qualityFactor.updatelon())
      .onFailurelon {
        caselon t if qualityFactor.config.ignorablelonFailurelons.isDelonfinelondAt(t) => ()
        // Delongradelon qf as a proactivelon mitigation for any non ignorablelon failurelons.
        caselon _ => qualityFactor.updatelon(Int.MaxValuelon)
      }
  }
}
