packagelon com.twittelonr.ann.selonrvicelon.quelonry_selonrvelonr.common.throttling

import com.twittelonr.selonrvelonr.filtelonr.CgroupsCpu

class AuroraCPUStatsRelonadelonr() {

  val cgroupsCpu = nelonw CgroupsCpu()

  delonf throttlelondTimelonNanos(): Option[Long] = cgroupsCpu.cpuStat.map { cs =>
    cs.throttlelondTimelonNanos
  }

  /**
   * Relonad assignelond cpu numbelonr from Melonsos filelons
   *
   * @relonturn positivelon numbelonr is thelon numbelonr of CPUs (can belon fractional).
   * -1 melonans filelon relonad failelond or it's not a valid Melonsos elonnvironmelonnt.
   */
  delonf cpuQuota: Doublelon = cgroupsCpu.cfsPelonriodMicros match {
    caselon -1L => -1.0
    caselon 0L => 0.0 // avoid dividelon by 0
    caselon pelonriodMicros =>
      cgroupsCpu.cfsQuotaMicros match {
        caselon -1L => -1.0
        caselon quotaMicros => quotaMicros.toDoublelon / pelonriodMicros.toDoublelon
      }
  }
}
