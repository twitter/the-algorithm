package com.twitter.timelines.data_processing.ml_util.aggregation_framework.heron

import com.twitter.conversions.DurationOps._
import com.twitter.finagle.mtls.authentication.EmptyServiceIdentifier
import com.twitter.finagle.mtls.authentication.ServiceIdentifier
import com.twitter.finagle.ssl.OpportunisticTls
import com.twitter.storehaus_internal.nighthawk_kv.CacheClientNighthawkConfig
import com.twitter.storehaus_internal.util.TTL
import com.twitter.storehaus_internal.util.TableName
import com.twitter.summingbird_internal.runner.store_config.OnlineStoreOnlyConfig
import com.twitter.util.Duration

case class NighthawkUnderlyingStoreConfig(
  serversetPath: String = "",
  tableName: String = "",
  cacheTTL: Duration = 1.day)
    extends OnlineStoreOnlyConfig[CacheClientNighthawkConfig] {

  def online: CacheClientNighthawkConfig = online(EmptyServiceIdentifier)

  def online(
    serviceIdentifier: ServiceIdentifier = EmptyServiceIdentifier
  ): CacheClientNighthawkConfig =
    CacheClientNighthawkConfig(
      serversetPath,
      TableName(tableName),
      TTL(cacheTTL),
      serviceIdentifier = serviceIdentifier,
      opportunisticTlsLevel = OpportunisticTls.Required
    )
}
