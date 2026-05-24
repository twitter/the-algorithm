package com.twitter.ann.service.query_server.common.throttling

import com.twitter.server.filter.CgroupsCpu

class AuroraCPUStatsReader() {

  val cgroupsCpu = new CgroupsCpu()

  def throttledTimeNanos(): Option[Long] = cgroupsCpu.cpuStat.map { cs =>
    cs.throttledTimeNanos
  }

  /**
   * Read assigned cpu number from Mesos files
   *
   * @return positive number is the number of CPUs (can be fractional).
   * -1 means file read failed or it's not a valid Mesos environment.
   */
  def cpuQuota: Double = cgroupsCpu.cfsPeriodMicros match {
    case -1L => -1.0
    case 0L => 0.0 // avoid divide by 0
    case periodMicros =>
      cgroupsCpu.cfsQuotaMicros match {
        case -1L => -1.0
        case quotaMicros => quotaMicros.toDouble / periodMicros.toDouble
      }
  }
}
