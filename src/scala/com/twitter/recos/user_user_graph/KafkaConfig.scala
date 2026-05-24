package com.twitter.recos.user_user_graph

/**
 * The class holds all the config parameters for kafka queue.
 */
object KafkaConfig {
  // The size of the RecosHoseMessage array that is written to the concurrently linked queue
  // Buffersize of 64 to keep throughput around 64 / (2K edgesPerSec / 150 kafka threads) = 6 seconds, which is lower
  // than young gen gc cycle, 20 seconds. So that all the incoming messages will be gced in young gen instead of old gen.
  val bufferSize = 64

  println("KafkaConfig -                 bufferSize " + bufferSize)
}
