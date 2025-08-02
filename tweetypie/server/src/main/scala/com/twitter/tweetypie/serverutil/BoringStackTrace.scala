package com.twitter.tweetypie.serverutil

import com.twitter.finagle.ChannelEtwitterception
import com.twitter.finagle.TimeoutEtwitterception
import com.twitter.scrooge.ThriftEtwitterception
import java.net.SocketEtwitterception
import java.nio.channels.CancelledKeyEtwitterception
import java.nio.channels.ClosedChannelEtwitterception
import java.util.concurrent.CancellationEtwitterception
import java.util.concurrent.{TimeoutEtwitterception => JTimeoutEtwitterception}
import org.apache.thrift.TApplicationEtwitterception
import scala.util.control.NoStackTrace

object BoringStackTrace {

  /**
   * These etwitterceptions are boring because they are etwitterpected to
   * occasionally (or even regularly) happen during normal operation
   * of the service. The intention is to make it easier to debug
   * problems by making interesting etwitterceptions easier to see.
   *
   * The best way to mark an etwitterception as boring is to etwittertend from
   * NoStackTrace, since that is a good indication that we don't care
   * about the details.
   */
  def isBoring(t: Throwable): Boolean =
    t match {
      case _: NoStackTrace => true
      case _: TimeoutEtwitterception => true
      case _: CancellationEtwitterception => true
      case _: JTimeoutEtwitterception => true
      case _: ChannelEtwitterception => true
      case _: SocketEtwitterception => true
      case _: ClosedChannelEtwitterception => true
      case _: CancelledKeyEtwitterception => true
      case _: ThriftEtwitterception => true
      // DeadlineEtwitterceededEtwitterceptions are propagated as:
      // org.apache.thrift.TApplicationEtwitterception: Internal error processing issue3: 'com.twitter.finagle.service.DeadlineFilter$DeadlineEtwitterceededEtwitterception: etwitterceeded request deadline of 100.milliseconds by 4.milliseconds. Deadline etwitterpired at 2020-08-27 17:07:46 +0000 and now it is 2020-08-27 17:07:46 +0000.'
      case e: TApplicationEtwitterception =>
        e.getMessage != null && e.getMessage.contains("DeadlineEtwitterceededEtwitterception")
      case _ => false
    }
}
