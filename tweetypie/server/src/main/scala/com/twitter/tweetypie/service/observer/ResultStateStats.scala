package com.tw ter.t etyp e
package serv ce
package observer

 mport com.tw ter.f nagle.stats.StatsRece ver

/**
 * "Result State"  s, for every s ngular t et read,   categor ze t  t et
 * result as a success or fa lure.
 * T se stats enable us to track true TPS success rates.
 */
pr vate[serv ce] case class ResultStateStats(pr vate val underly ng: StatsRece ver) {
  pr vate val stats = underly ng.scope("result_state")
  pr vate val successCounter = stats.counter("success")
  pr vate val fa ledCounter = stats.counter("fa led")

  def success(delta: Long = 1): Un  = successCounter. ncr(delta)
  def fa led(delta: Long = 1): Un  = fa ledCounter. ncr(delta)
}
