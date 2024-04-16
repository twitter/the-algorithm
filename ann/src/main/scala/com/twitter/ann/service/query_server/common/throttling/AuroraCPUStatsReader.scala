package com.tw ter.ann.serv ce.query_server.common.throttl ng

 mport com.tw ter.server.f lter.CgroupsCpu

class AuroraCPUStatsReader() {

  val cgroupsCpu = new CgroupsCpu()

  def throttledT  Nanos(): Opt on[Long] = cgroupsCpu.cpuStat.map { cs =>
    cs.throttledT  Nanos
  }

  /**
   * Read ass gned cpu number from  sos f les
   *
   * @return pos  ve number  s t  number of CPUs (can be fract onal).
   * -1  ans f le read fa led or  's not a val d  sos env ron nt.
   */
  def cpuQuota: Double = cgroupsCpu.cfsPer odM cros match {
    case -1L => -1.0
    case 0L => 0.0 // avo d d v de by 0
    case per odM cros =>
      cgroupsCpu.cfsQuotaM cros match {
        case -1L => -1.0
        case quotaM cros => quotaM cros.toDouble / per odM cros.toDouble
      }
  }
}
