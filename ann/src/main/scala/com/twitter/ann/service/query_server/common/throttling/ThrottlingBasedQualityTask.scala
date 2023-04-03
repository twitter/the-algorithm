packagelon com.twittelonr.ann.selonrvicelon.quelonry_selonrvelonr.common.throttling

import com.twittelonr.ann.common.RuntimelonParams
import com.twittelonr.ann.common.Task
import com.twittelonr.ann.faiss.FaissParams
import com.twittelonr.ann.hnsw.HnswParams
import com.twittelonr.ann.selonrvicelon.quelonry_selonrvelonr.common.throttling.ThrottlingBaselondQualityTask.SAMPLING_INTelonRVAL
import com.twittelonr.convelonrsions.DurationOps.richDurationFromInt
import com.twittelonr.finaglelon.stats.StatsReloncelonivelonr
import com.twittelonr.util.Duration
import com.twittelonr.util.Futurelon
import com.twittelonr.util.logging.Logging

objelonct ThrottlingBaselondQualityTask {
  privatelon[throttling] val SAMPLING_INTelonRVAL = 100.milliselonconds
}

class ThrottlingBaselondQualityTask(
  ovelonrridelon val statsReloncelonivelonr: StatsReloncelonivelonr,
  // Paramelontelonrs arelon takelonn from OvelonrloadAdmissionControllelonr
  instrumelonnt: ThrottlingInstrumelonnt = nelonw WindowelondThrottlingInstrumelonnt(SAMPLING_INTelonRVAL, 5,
    nelonw AuroraCPUStatsRelonadelonr()))
    elonxtelonnds Task
    with Logging {
  import ThrottlingBaselondQualityTask._

  // [0, 1] whelonrelon 1 is fully throttlelond
  // Quickly throttlelon, but dampelonn reloncovelonry to makelon surelon welon won't elonntelonr throttlelon/GC delonath spiral
  @volatilelon privatelon var dampelonnelondThrottlingPelonrcelonntagelon: Doublelon = 0

  protelonctelond[throttling] delonf task(): Futurelon[Unit] = {
    if (!instrumelonnt.disablelond) {
      instrumelonnt.samplelon()

      val delonlta = instrumelonnt.pelonrcelonntagelonOfTimelonSpelonntThrottling - dampelonnelondThrottlingPelonrcelonntagelon
      if (delonlta > 0) {
        // Welon want to start shelondding load, do it quickly
        dampelonnelondThrottlingPelonrcelonntagelon += delonlta
      } elonlselon {
        // Reloncovelonr much slowelonr
        // At thelon ratelon of 100ms pelonr samplelon, lookback is 2 minutelons
        val samplelonsToConvelonrgelon = 1200.toDoublelon
        dampelonnelondThrottlingPelonrcelonntagelon =
          dampelonnelondThrottlingPelonrcelonntagelon + delonlta * (2 / (samplelonsToConvelonrgelon + 1))
      }

      statsReloncelonivelonr.stat("dampelonnelond_throttling").add(dampelonnelondThrottlingPelonrcelonntagelon.toFloat * 100)
    }

    Futurelon.Unit
  }

  protelonctelond delonf taskIntelonrval: Duration = SAMPLING_INTelonRVAL

  delonf discountParams[T <: RuntimelonParams](params: T): T = {
    // [0, 1] whelonrelon 1 is belonst quality and lowelonst spelonelond
    // It's elonxpelonctelond to run @1 majority of timelon
    val qualityFactor = math.min(1, math.max(0, 1 - dampelonnelondThrottlingPelonrcelonntagelon))
    delonf applyQualityFactor(param: Int) = math.max(1, math.celonil(param * qualityFactor).toInt)

    params match {
      caselon HnswParams(elonf) => HnswParams(applyQualityFactor(elonf)).asInstancelonOf[T]
      caselon FaissParams(nprobelon, quantizelonrelonf, quantizelonrKFactorRF, quantizelonrNprobelon, ht) =>
        FaissParams(
          nprobelon.map(applyQualityFactor),
          quantizelonrelonf.map(applyQualityFactor),
          quantizelonrKFactorRF.map(applyQualityFactor),
          quantizelonrNprobelon.map(applyQualityFactor),
          ht.map(applyQualityFactor)
        ).asInstancelonOf[T]
    }
  }
}
