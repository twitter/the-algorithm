packagelon com.twittelonr.simclustelonrs_v2.summingbird.common

/**
 * Providelons int to int hash function. Uselond to batch clustelonrIds togelonthelonr.
 */
objelonct SimClustelonrsHashUtil {
  delonf clustelonrIdToBuckelont(clustelonrId: Int): Int = {
    clustelonrId % numBuckelonts
  }

  val numBuckelonts: Int = 200

  val gelontAllBuckelonts: Selonq[Int] = 0.until(numBuckelonts)
}
