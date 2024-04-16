package com.tw ter.ann.serv ce.query_server.common.throttl ng

 mport com.tw ter.ut l.Durat on

tra  Throttl ng nstru nt {
  def sample(): Un 
  def percentageOfT  SpentThrottl ng(): Double
  def d sabled: Boolean
}

class W ndo dThrottl ng nstru nt(
  stepFrequency: Durat on,
  w ndowLength nFrequencySteps:  nt,
  reader: AuroraCPUStatsReader)
    extends Throttl ng nstru nt {
  pr vate[t ] val throttl ngChange tory: W ndo dStats = new W ndo dStats(
    w ndowLength nFrequencySteps)

  pr vate[t ] val cpuQuota: Double = reader.cpuQuota

  // T  total number of allotted CPU t   per step ( n nanos).
  pr vate[t ] val ass gnedCpu: Durat on = stepFrequency * cpuQuota
  pr vate[t ] val ass gnedCpuNs: Long = ass gnedCpu. nNanoseconds

  @volat le pr vate[t ] var prev ousThrottledT  Ns: Long = 0

  /**
   *  f t re  sn't a l m  on how much cpu t  conta ner can use, aurora
   * throttl ng w ll never k ck  n.
   */
  f nal def d sabled: Boolean = cpuQuota <= 0

  def sample(): Un  = sampleThrottl ng() match {
    case So (load) =>
      throttl ngChange tory.add(load)
    case None => ()
  }

  pr vate[t ] def sampleThrottl ng(): Opt on[Long] = reader.throttledT  Nanos().map {
    throttledT  Ns =>
      val throttl ngChange = throttledT  Ns - prev ousThrottledT  Ns
      prev ousThrottledT  Ns = throttledT  Ns
      throttl ngChange
  }

  // T   spent throttl ng over w ndowLength, normal zed by number of CPUs
  def percentageOfT  SpentThrottl ng(): Double = {
    math.m n(1, throttl ngChange tory.sum.toDouble / ass gnedCpuNs)
  }
}
