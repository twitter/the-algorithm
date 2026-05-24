package com.twitter.home_mixer.store

import com.twitter.conversions.DurationOps.richDurationFromInt
import com.twitter.finagle.mtls.authentication.ServiceIdentifier
import com.twitter.finagle.stats.StatsReceiver
import com.twitter.hermit.store.common.ObservedMemcachedReadableStore
import com.twitter.storage.client.manhattan.bijections.Bijections
import com.twitter.storage.client.manhattan.kv.ManhattanKVClient
import com.twitter.storage.client.manhattan.kv.ManhattanKVClientMtlsParams
import com.twitter.storage.client.manhattan.kv.ManhattanKVEndpointBuilder
import com.twitter.storage.client.manhattan.kv.impl.ReadOnlyKeyDescriptor
import com.twitter.storage.client.manhattan.kv.impl.ValueDescriptor
import com.twitter.storehaus.ReadableStore
import com.twitter.storehaus_internal.memcache.MemcacheStore
import com.twitter.storehaus_internal.util.ClientName
import com.twitter.storehaus_internal.util.ZkEndPoint
import com.twitter.bijection.Bijection

trait MediaClusterIdStoreTrait {
  val statsReceiver: StatsReceiver
  val serviceIdentifier: ServiceIdentifier

  // Abstract methods that subclasses must implement
  def appId: String
  def dataset: String
  def memcacheDest: String
  def keyPrefix: String
  def storeName: String // For logging

  private val ManhattanDest = "/s/manhattan/nash.native-thrift"

  lazy val clusterIdStore: ReadableStore[Long, Long] = {
    val manhattanStore = createManhattanStore()
    val cachedStore = createCachedStore(manhattanStore)
    cachedStore
  }

  private def createCachedStore(
    underlyingStore: ReadableStore[Long, Long]
  ): ReadableStore[Long, Long] = {
    val memcacheStats = statsReceiver.scope(s"memcache_${keyPrefix}")
    val underlyingCacheClient = MemcacheStore.memcachedClient(
      name = ClientName(serviceIdentifier.name),
      dest = ZkEndPoint(memcacheDest),
      statsReceiver = memcacheStats,
      serviceIdentifier = serviceIdentifier,
      timeout = 80.milliseconds
    )

    val memcacheStore = ObservedMemcachedReadableStore.fromCacheClient(
      backingStore = underlyingStore,
      cacheClient = underlyingCacheClient,
      ttl = 3.days, // From config: foundTtl = 3.days
    )(
      valueInjection = Bijections.long2ByteArray,
      statsReceiver = memcacheStats,
      keyToString = { key: Long =>
        val cacheKey = if (keyPrefix.isEmpty) key.toString else s"${keyPrefix}_${key}"
        cacheKey
      }
    )

    memcacheStore
  }

  private def createManhattanStore(): ReadableStore[Long, Long] = {

    // Use proper key and value descriptors with correct encodings
    // Sign flip for NativeEncoding (flip high bit to match server-side ordering)
    def signFlip(a: Array[Byte]): Array[Byte] = { a(0) = (a(0) ^ 0x80.toByte).toByte; a }
    val signBijection = Bijection.build[Array[Byte], Array[Byte]](signFlip _)(signFlip _)

    val keyInjection =
      Bijections.long2ByteArray.andThen(signBijection).andThen(Bijections.byteArray2Buf)
    val keyDesc = ReadOnlyKeyDescriptor(keyInjection)
    val datasetKey = keyDesc.withDataset(dataset)

    val valueInjection = Bijections.long2ByteArray.andThen(Bijections.byteArray2Buf)
    val valueDesc = ValueDescriptor(valueInjection)

    val client = ManhattanKVClient(
      appId = appId,
      dest = ManhattanDest,
      mtlsParams = ManhattanKVClientMtlsParams(serviceIdentifier),
      label = storeName
    )

    val endpoint = ManhattanKVEndpointBuilder(client)
      .maxRetryCount(3)
      .defaultMaxTimeout(120.milliseconds)
      .build()

    new ReadableStore[Long, Long] {
      override def get(key: Long) = {
        import com.twitter.stitch.Stitch
        val future = Stitch.run(endpoint.get(datasetKey.withPkey(key), valueDesc))
        future.map(_.map(_.contents))
      }
    }
  }

}
