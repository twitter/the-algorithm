package com.X.tweetypie.backends

import com.X.configbus.client.ConfigbusClientException
import com.X.configbus.client.file.PollingConfigSourceBuilder
import com.X.finagle.stats.StatsReceiver
import com.X.logging.Logger
import com.X.util.Activity
import com.X.util.Activity._
import com.X.conversions.DurationOps._
import com.X.io.Buf

trait ConfigBus {
  def file(path: String): Activity[String]
}

object ConfigBus {
  private[this] val basePath = "appservices/tweetypie"
  private[this] val log = Logger(getClass)

  def apply(stats: StatsReceiver, instanceId: Int, instanceCount: Int): ConfigBus = {

    val client = PollingConfigSourceBuilder()
      .statsReceiver(stats)
      .pollPeriod(30.seconds)
      .instanceId(instanceId)
      .numberOfInstances(instanceCount)
      .build()

    val validBuffer = stats.counter("valid_buffer")

    def subscribe(path: String) =
      client.subscribe(s"$basePath/$path").map(_.configs).map {
        case Buf.Utf8(string) =>
          validBuffer.incr()
          string
      }

    new ConfigBus {
      def file(path: String): Activity[String] = {
        val changes = subscribe(path).run.changes.dedupWith {
          case (Failed(e1: ConfigbusClientException), Failed(e2: ConfigbusClientException)) =>
            e1.getMessage == e2.getMessage
          case other =>
            false
        }
        Activity(changes)
      }
    }
  }
}
