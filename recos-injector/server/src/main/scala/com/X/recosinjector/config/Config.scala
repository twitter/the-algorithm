package com.X.recosinjector.config

import com.X.finagle.mtls.authentication.ServiceIdentifier
import com.X.finagle.stats.StatsReceiver
import com.X.finagle.thrift.ClientId
import com.X.frigate.common.store.TweetCreationTimeMHStore
import com.X.frigate.common.util.UrlInfo
import com.X.gizmoduck.thriftscala.User
import com.X.recosinjector.decider.RecosInjectorDecider
import com.X.socialgraph.thriftscala.{IdsRequest, IdsResult}
import com.X.stitch.tweetypie.TweetyPie.TweetyPieResult
import com.X.storehaus.ReadableStore
import com.X.util.Future

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
