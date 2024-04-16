package com.tw ter.recos.user_user_graph

/**
 * T  class holds all t  conf g para ters for kafka queue.
 */
object KafkaConf g {
  // T  s ze of t  RecosHose ssage array that  s wr ten to t  concurrently l nked queue
  // Buffers ze of 64 to keep throughput around 64 / (2K edgesPerSec / 150 kafka threads) = 6 seconds, wh ch  s lo r
  // than  ng gen gc cycle, 20 seconds. So that all t   ncom ng  ssages w ll be gced  n  ng gen  nstead of old gen.
  val bufferS ze = 64

  pr ntln("KafkaConf g -                 bufferS ze " + bufferS ze)
}
