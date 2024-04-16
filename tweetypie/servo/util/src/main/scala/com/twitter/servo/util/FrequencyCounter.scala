package com.tw ter.servo.ut l

 mport com.tw ter.f nagle.stats.{NullStatsRece ver, StatsRece ver}
 mport scala.collect on.mutable

/**
 * Ma nta ns a frequency counted c rcular buffer of objects.
 */
class FrequencyCounter[Q](
  s ze:  nt,
  threshold:  nt,
  tr gger: Q => Un ,
  statsRece ver: StatsRece ver = NullStatsRece ver) {
  requ re(threshold > 1) //  n order to m n m ze work for t  common case
  pr vate[t ] val buffer = new mutable.ArraySeq[Q](s ze)
  pr vate[t ] var  ndex = 0
  pr vate[t ] val counts = mutable.Map[Q,  nt]()

  pr vate[t ] val keyCountStat = statsRece ver.scope("frequencyCounter").stat("keyCount")

  /**
   * Adds a new key to t  c rcular buffer and updates frequency counts.
   * Runs tr gger  f t  key occurs exactly `threshold` t  s  n t  buffer.
   * Returns true  f t  key occurs at least `threshold` t  s  n t  buffer.
   */
  def  ncr(key: Q): Boolean = {
    // TOOD(aa): maybe wr e lock-free vers on
    val count = synchron zed {
      counts(key) = counts.getOrElse(key, 0) + 1

      Opt on(buffer( ndex)) foreach { oldKey =>
        val countVal = counts(oldKey)
         f (countVal == 1) {
          counts -= oldKey
        } else {
          counts(oldKey) = countVal - 1
        }
      }

      buffer( ndex) = key
       ndex = ( ndex + 1) % s ze
      counts(key)
    }
    keyCountStat.add(count)
     f (count == threshold) {
      tr gger(key)
    }
    count >= threshold
  }

}
