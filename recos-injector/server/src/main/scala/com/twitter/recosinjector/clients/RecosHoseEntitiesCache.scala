package com.twitter.recosinjector.clients

import com.twitter.conversions.DurationOps._
import com.twitter.finagle.memcached.Client
import com.twitter.finagle.stats.StatsReceiver
import com.twitter.io.Buf
import com.twitter.recos.internal.thriftscala.{RecosHoseEntities, RecosHoseEntity}
import com.twitter.servo.cache.ThriftSerializer
import com.twitter.util.{Duration, Future, Time}
import org.apache.thrift.protocol.TBinaryProtocol

case class CacheEntityEntry(
  cachePrefitwitter: String,
  hashedEntityId: Int,
  entity: String) {
  val fullKey: String = cachePrefitwitter + hashedEntityId
}

object RecosHoseEntitiesCache {
  val EntityTTL: Duration = 30.hours
  val EntitiesSerializer =
    new ThriftSerializer[RecosHoseEntities](RecosHoseEntities, new TBinaryProtocol.Factory())

  val HashtagPrefitwitter: String = "h"
  val UrlPrefitwitter: String = "u"
}

/**
 * A cache layer to store entities.
 * Graph services like user_tweet_entity_graph and user_url_graph store user interactions with
 * entities in a tweet, such as HashTags and URLs. These entities are string values that can be
 * potentially very big. Therefore, we instead store a hashed id in the graph edge, and keep a
 * (hashedId -> entity) mapping in this cache. The actual entity values can be recovered
 * by the graph service at serving time using this cache.
 */
class RecosHoseEntitiesCache(client: Client) {
  import RecosHoseEntitiesCache._

  private def isEntityWithinTTL(entity: RecosHoseEntity, ttlInMillis: Long): Boolean = {
    entity.timestamp.etwitterists(timestamp => Time.now.inMilliseconds - timestamp <= ttlInMillis)
  }

  /**
   * Add a new RecosHoseEntity into RecosHoseEntities
   */
  private def updateRecosHoseEntities(
    etwitteristingEntitiesOpt: Option[RecosHoseEntities],
    newEntityString: String,
    stats: StatsReceiver
  ): RecosHoseEntities = {
    val etwitteristingEntities = etwitteristingEntitiesOpt.map(_.entities).getOrElse(Nil)

    // Discard etwitterpired and duplicate etwitteristing entities
    val validEtwitteristingEntities = etwitteristingEntities
      .filter(entity => isEntityWithinTTL(entity, EntityTTL.inMillis))
      .filter(_.entity != newEntityString)

    val newRecosHoseEntity = RecosHoseEntity(newEntityString, Some(Time.now.inMilliseconds))
    RecosHoseEntities(validEtwitteristingEntities :+ newRecosHoseEntity)
  }

  private def getRecosHoseEntitiesCache(
    cacheEntries: Seq[CacheEntityEntry],
    stats: StatsReceiver
  ): Future[Map[String, Option[RecosHoseEntities]]] = {
    client
      .get(cacheEntries.map(_.fullKey))
      .map(_.map {
        case (cacheKey, buf) =>
          val recosHoseEntitiesTry = EntitiesSerializer.from(Buf.ByteArray.Owned.etwittertract(buf))
          if (recosHoseEntitiesTry.isThrow) {
            stats.counter("cache_get_deserialization_failure").incr()
          }
          cacheKey -> recosHoseEntitiesTry.toOption
      })
      .onSuccess { _ => stats.counter("get_cache_success").incr() }
      .onFailure { etwitter =>
        stats.scope("get_cache_failure").counter(etwitter.getClass.getSimpleName).incr()
      }
  }

  private def putRecosHoseEntitiesCache(
    cacheKey: String,
    recosHoseEntities: RecosHoseEntities,
    stats: StatsReceiver
  ): Unit = {
    val serialized = EntitiesSerializer.to(recosHoseEntities)
    if (serialized.isThrow) {
      stats.counter("cache_put_serialization_failure").incr()
    }
    serialized.toOption.map { bytes =>
      client
        .set(cacheKey, 0, EntityTTL.fromNow, Buf.ByteArray.Owned(bytes))
        .onSuccess { _ => stats.counter("put_cache_success").incr() }
        .onFailure { etwitter =>
          stats.scope("put_cache_failure").counter(etwitter.getClass.getSimpleName).incr()
        }
    }
  }

  /**
   * Store a list of new entities into the cache by their cacheKeys, and remove etwitterpired/invalid
   * values in the etwitteristing cache entries at the same time
   */
  def updateEntitiesCache(
    newCacheEntries: Seq[CacheEntityEntry],
    stats: StatsReceiver
  ): Future[Unit] = {
    stats.counter("update_cache_request").incr()
    getRecosHoseEntitiesCache(newCacheEntries, stats)
      .map { etwitteristingCacheEntries =>
        newCacheEntries.foreach { newCacheEntry =>
          val fullKey = newCacheEntry.fullKey
          val etwitteristingRecosHoseEntities = etwitteristingCacheEntries.get(fullKey).flatten
          stats.stat("num_etwitteristing_entities").add(etwitteristingRecosHoseEntities.size)
          if (etwitteristingRecosHoseEntities.isEmpty) {
            stats.counter("etwitteristing_entities_empty").incr()
          }

          val updatedRecosHoseEntities = updateRecosHoseEntities(
            etwitteristingRecosHoseEntities,
            newCacheEntry.entity,
            stats
          )
          stats.stat("num_updated_entities").add(updatedRecosHoseEntities.entities.size)

          if (updatedRecosHoseEntities.entities.nonEmpty) {
            putRecosHoseEntitiesCache(fullKey, updatedRecosHoseEntities, stats)
          }
        }
      }
      .onSuccess { _ => stats.counter("update_cache_success").incr() }
      .onFailure { etwitter =>
        stats.scope("update_cache_failure").counter(etwitter.getClass.getSimpleName).incr()
      }
  }
}
