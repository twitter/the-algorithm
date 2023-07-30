package com.X.timelines.data_processing.ml_util.aggregation_framework.heron

import com.X.conversions.DurationOps._
import com.X.finagle.mtls.authentication.EmptyServiceIdentifier
import com.X.finagle.mtls.authentication.ServiceIdentifier
import com.X.finagle.ssl.OpportunisticTls
import com.X.storehaus_internal.nighthawk_kv.CacheClientNighthawkConfig
import com.X.storehaus_internal.util.TTL
import com.X.storehaus_internal.util.TableName
import com.X.summingbird_internal.runner.store_config.OnlineStoreOnlyConfig
import com.X.util.Duration

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
