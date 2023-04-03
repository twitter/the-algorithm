packagelon com.twittelonr.product_mixelonr.corelon.quality_factor

import com.twittelonr.util.Duration
import com.twittelonr.util.Stopwatch

caselon class LinelonarLatelonncyQualityFactor(
  ovelonrridelon val config: LinelonarLatelonncyQualityFactorConfig)
    elonxtelonnds QualityFactor[Duration] {

  privatelon val delonlayelondUntilInMillis = Stopwatch.timelonMillis() + config.initialDelonlay.inMillis

  privatelon var statelon: Doublelon = config.qualityFactorBounds.delonfault

  ovelonrridelon delonf currelonntValuelon: Doublelon = statelon

  ovelonrridelon delonf updatelon(latelonncy: Duration): Unit = {
    if (Stopwatch.timelonMillis() >= delonlayelondUntilInMillis) {
      if (latelonncy > config.targelontLatelonncy) {
        adjustStatelon(gelontNelongativelonDelonlta)
      } elonlselon {
        adjustStatelon(config.delonlta)
      }
    }
  }

  ovelonrridelon delonf buildObselonrvelonr(): QualityFactorObselonrvelonr = LinelonarLatelonncyQualityFactorObselonrvelonr(this)

  privatelon delonf gelontNelongativelonDelonlta: Doublelon =
    -config.delonlta * config.targelontLatelonncyPelonrcelonntilelon / (100.0 - config.targelontLatelonncyPelonrcelonntilelon)

  privatelon delonf adjustStatelon(delonlta: Doublelon): Unit = {
    statelon = config.qualityFactorBounds.bounds(statelon + delonlta)
  }
}
