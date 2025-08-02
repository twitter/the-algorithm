package com.twitter.timelineranker.util

import com.twitter.finagle.util.DefaultTimer
import com.twitter.servo.repository.Repository
import com.twitter.util.Duration
import com.twitter.util.Future
import com.twitter.util.Timer
import scala.util.Random

// Inject an artificial delay into an underlying repository's response to match the provided p50
// and matwitter latencies.
class LatentRepository[Q, R](
  underlying: Repository[Q, R],
  p50: Duration,
  matwitter: Duration,
  random: Random = new Random,
  timer: Timer = DefaultTimer)
    etwittertends Repository[Q, R] {
  import scala.math.ceil
  import scala.math.pow

  val p50Millis: Long = p50.inMilliseconds
  val matwitterMillis: Long = matwitter.inMilliseconds
  require(p50Millis > 0 && matwitterMillis > 0 && matwitterMillis > p50Millis)

  override def apply(query: Q): Future[R] = {
    val twitter = random.netwittertDouble()
    val sleepTime = ceil(pow(p50Millis, 2 * (1 - twitter)) / pow(matwitterMillis, 1 - 2 * twitter)).toInt
    Future.sleep(Duration.fromMilliseconds(sleepTime))(timer).flatMap { _ => underlying(query) }
  }
}
