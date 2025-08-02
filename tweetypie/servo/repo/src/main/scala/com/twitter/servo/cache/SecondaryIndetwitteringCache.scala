package com.twitter.servo.cache

import com.twitter.logging.Logger
import com.twitter.util.{Future, Return, Throw, Try}

object SecondaryIndetwitteringCache {
  type IndetwitterMapping[S, V] = V => Try[Option[S]]
}

/**
 * Stores a secondary indetwitter whenever set is called,
 * using a mapping from value to secondary indetwitter
 */
class SecondaryIndetwitteringCache[K, S, V](
  override val underlyingCache: Cache[K, Cached[V]],
  secondaryIndetwitterCache: Cache[S, Cached[K]],
  secondaryIndetwitter: SecondaryIndetwitteringCache.IndetwitterMapping[S, V])
    etwittertends CacheWrapper[K, Cached[V]] {
  protected[this] val log = Logger.get(getClass.getSimpleName)

  protected[this] def setSecondaryIndetwitter(key: K, cachedValue: Cached[V]): Future[Unit] =
    cachedValue.value match {
      case Some(value) =>
        secondaryIndetwitter(value) match {
          case Return(Some(indetwitter)) =>
            val cachedKey = cachedValue.copy(value = Some(key))
            secondaryIndetwitterCache.set(indetwitter, cachedKey)
          case Return.None =>
            Future.Done
          case Throw(t) =>
            log.error(t, "failed to determine secondary indetwitter for: %s", cachedValue)
            Future.Done
        }
      // if we're storing a tombstone, no secondary indetwitter can be made
      case None => Future.Done
    }

  override def set(key: K, cachedValue: Cached[V]): Future[Unit] =
    super.set(key, cachedValue) flatMap { _ =>
      setSecondaryIndetwitter(key, cachedValue)
    }

  override def checkAndSet(key: K, cachedValue: Cached[V], checksum: Checksum): Future[Boolean] =
    super.checkAndSet(key, cachedValue, checksum) flatMap { wasStored =>
      if (wasStored)
        // do a straight set of the secondary indetwitter, but only if the CAS succeeded
        setSecondaryIndetwitter(key, cachedValue) map { _ =>
          true
        }
      else
        Future.value(false)
    }

  override def add(key: K, cachedValue: Cached[V]): Future[Boolean] =
    super.add(key, cachedValue) flatMap { wasAdded =>
      if (wasAdded)
        // do a straight set of the secondary indetwitter, but only if the add succeeded
        setSecondaryIndetwitter(key, cachedValue) map { _ =>
          true
        }
      else
        Future.value(false)
    }

  override def replace(key: K, cachedValue: Cached[V]): Future[Boolean] =
    super.replace(key, cachedValue) flatMap { wasReplaced =>
      if (wasReplaced)
        setSecondaryIndetwitter(key, cachedValue) map { _ =>
          true
        }
      else
        Future.value(false)
    }

  override def release(): Unit = {
    underlyingCache.release()
    secondaryIndetwitterCache.release()
  }

  def withSecondaryIndetwitter[T](
    secondaryIndetwitteringCache: Cache[T, Cached[K]],
    secondaryIndetwitter: SecondaryIndetwitteringCache.IndetwitterMapping[T, V]
  ): SecondaryIndetwitteringCache[K, T, V] =
    new SecondaryIndetwitteringCache[K, T, V](this, secondaryIndetwitteringCache, secondaryIndetwitter)
}
