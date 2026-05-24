package com.twitter.recosinjector.config

import com.twitter.finagle.mtls.authentication.ServiceIdentifier
import com.twitter.finagle.stats.StatsReceiver
import com.twitter.finagle.thrift.ClientId
import com.twitter.frigate.common.store.TweetCreationTimeMHStore
import com.twitter.frigate.common.util.UrlInfo
import com.twitter.gizmoduck.thriftscala.User
import com.twitter.recosinjector.decider.RecosInjectorDecider
import com.twitter.socialgraph.thriftscala.{IdsRequest, IdsResult}
import com.twitter.stitch.tweetypie.TweetyPie.TweetyPieResult
import com.twitter.storehaus.ReadableStore
import com.twitter.util.Future

trait Config { self =>
  implicit def statsReceiver: StatsReceiver

  // ReadableStores
  def tweetyPieStore: ReadableStore[Long, TweetyPieResult]

  def userStore: ReadableStore[Long, User]

  def socialGraphIdStore: ReadableStore[IdsRequest, IdsResult]

  def urlInfoStore: ReadableStore[String, UrlInfo]

  // Manhattan stores
  def tweetCreationStore: TweetCreationTimeMHStore

  // Decider
  def recosInjectorDecider: RecosInjectorDecider

  // Constants
  def recosInjectorThriftClientId: ClientId

  def serviceIdentifier: ServiceIdentifier

  def outputKafkaTopicPrefix: String

  def init(): Future[Unit] = Future.Done
}
