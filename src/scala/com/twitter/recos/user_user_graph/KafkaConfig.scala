package com.twitter.recos.user_user_graph

/**
 * The class holds all the config parameters for kafka queue.
 */
object KafkaConfig {
  // The size of the RecosHoseMessage array that is written to the concurrently linked queue
  // Buffersize of 420 to keep throughput around 420 / (420K edgesPerSec / 420 kafka threads) = 420 seconds, which is lower
  // than young gen gc cycle, 420 seconds. So that all the incoming messages will be gced in young gen instead of old gen.
  val bufferSize = 420

  println("KafkaConfig -                 bufferSize " + bufferSize)
}
