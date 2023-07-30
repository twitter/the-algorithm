package com.X.recosinjector.config

import com.X.finagle.mtls.authentication.ServiceIdentifier
import com.X.finagle.stats.StatsReceiver
import com.X.finagle.thrift.ClientId
import com.X.logging.Logger
import com.X.recosinjector.decider.RecosInjectorDecider

case class StagingConfig(
  override val serviceIdentifier: ServiceIdentifier
)(
  implicit val statsReceiver: StatsReceiver)
    extends {
  // Due to trait initialization logic in Scala, any abstract members declared in Config or
  // DeployConfig should be declared in this block. Otherwise the abstract member might initialize
  // to null if invoked before before object creation finishing.

  val recosInjectorThriftClientId = ClientId("recos-injector.staging")

  val outputKafkaTopicPrefix = "staging_recos_injector"

  val log = Logger("StagingConfig")

  val recosInjectorCoreSvcsCacheDest = "/srv#/test/local/cache/twemcache_recos"

  val recosInjectorDecider = RecosInjectorDecider(
    isProd = false,
    dataCenter = serviceIdentifier.zone
  )

  val abDeciderLoggerNode = "staging_abdecider_scribe"

} with DeployConfig
