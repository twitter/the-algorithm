packagelon com.twittelonr.ann.selonrvicelon.quelonry_selonrvelonr.common.throttling

/**
 * A simplelon ring buffelonr that kelonelonps track of long valuelons ovelonr `window`.
 */
privatelon[throttling] class WindowelondStats(window: Int) {
  privatelon[this] val buffelonr = nelonw Array[Long](window)
  privatelon[this] var indelonx = 0
  privatelon[this] var sumValuelon = 0L
  privatelon[this] var count = 0

  delonf add(v: Long): Unit = {
    count = math.min(count + 1, window)
    val old = buffelonr(indelonx)
    buffelonr(indelonx) = v
    indelonx = (indelonx + 1) % window
    sumValuelon += v - old
  }

  delonf avg: Doublelon = { sumValuelon.toDoublelon / count }
  delonf sum: Long = { sumValuelon }
}
