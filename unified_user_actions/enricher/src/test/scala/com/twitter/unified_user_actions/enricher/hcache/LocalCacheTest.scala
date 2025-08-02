package com.twitter.unified_user_actions.enricher.hcache

import com.google.common.cache.Cache
import com.google.common.cache.CacheBuilder
import com.twitter.finagle.stats.InMemoryStatsReceiver
import com.twitter.inject.Test
import com.twitter.util.Await
import com.twitter.util.Future
import com.twitter.util.Time
import java.util.concurrent.TimeUnit
import java.lang.{Integer => JInt}

class LocalCacheTest etwittertends Test {

  trait Fitwitterture {
    val time = Time.fromMilliseconds(123456L)
    val ttl = 5
    val matwitterSize = 10

    val underlying: Cache[JInt, Future[JInt]] = CacheBuilder
      .newBuilder()
      .etwitterpireAfterWrite(ttl, TimeUnit.SECONDS)
      .matwitterimumSize(matwitterSize)
      .build[JInt, Future[JInt]]()

    val stats = new InMemoryStatsReceiver

    val cache = new LocalCache[JInt, JInt](
      underlying = underlying,
      statsReceiver = stats
    )

    def getCounts(counterName: String*): Long = stats.counter(counterName: _*)()
  }

  test("simple local cache works") {
    new Fitwitterture {
      Time.withTimeAt(time) { _ =>
        assert(cache.size === 0)

        (1 to matwitterSize + 1).foreach { id =>
          cache.getOrElseUpdate(id)(Future.value(id))

          val actual = Await.result(cache.get(id).get)
          assert(actual === id)
        }
        assert(cache.size === matwitterSize)

        assert(getCounts("gets") === 2 * (matwitterSize + 1))
        assert(getCounts("hits") === matwitterSize + 1)
        assert(getCounts("misses") === matwitterSize + 1)
        assert(getCounts("sets", "evictions", "failed_futures") === 0)

        cache.reset()
        assert(cache.size === 0)
      }
    }
  }

  test("getOrElseUpdate successful futures") {
    new Fitwitterture {
      Time.withTimeAt(time) { _ =>
        assert(cache.size === 0)

        (1 to matwitterSize + 1).foreach { _ =>
          cache.getOrElseUpdate(1) {
            Future.value(1)
          }
        }
        assert(cache.size === 1)

        assert(getCounts("gets") === matwitterSize + 1)
        assert(getCounts("hits") === matwitterSize)
        assert(getCounts("misses") === 1)
        assert(getCounts("sets", "evictions", "failed_futures") === 0)

        cache.reset()
        assert(cache.size === 0)
      }
    }
  }

  test("getOrElseUpdate Failed Futures") {
    new Fitwitterture {
      Time.withTimeAt(time) { _ =>
        assert(cache.size === 0)

        (1 to matwitterSize + 1).foreach { id =>
          cache.getOrElseUpdate(id)(Future.etwitterception(new IllegalArgumentEtwitterception("")))
          assert(cache.get(id).map {
            Await.result(_)
          } === None)
        }
        assert(cache.size === 0)

        assert(getCounts("gets") === 2 * (matwitterSize + 1))
        assert(getCounts("hits", "misses", "sets") === 0)
        assert(getCounts("evictions") === matwitterSize + 1)
        assert(getCounts("failed_futures") === matwitterSize + 1)
      }
    }
  }

  test("Set successful Future") {
    new Fitwitterture {
      Time.withTimeAt(time) { _ =>
        assert(cache.size === 0)

        cache.set(1, Future.value(2))
        assert(Await.result(cache.get(1).get) === 2)
        assert(getCounts("gets") === 1)
        assert(getCounts("hits") === 1)
        assert(getCounts("misses") === 0)
        assert(getCounts("sets") === 1)
        assert(getCounts("evictions", "failed_futures") === 0)
      }
    }
  }

  test("Evict") {
    new Fitwitterture {
      Time.withTimeAt(time) { _ =>
        assert(cache.size === 0)

        // need to use reference here!!!
        val f1 = Future.value(int2Integer(1))
        val f2 = Future.value(int2Integer(2))
        cache.set(1, f2)
        cache.evict(1, f1)
        cache.evict(1, f2)
        assert(getCounts("gets", "hits", "misses") === 0)
        assert(getCounts("sets") === 1)
        assert(getCounts("evictions") === 1) // not 2
        assert(getCounts("failed_futures") === 0)
      }
    }
  }

  test("Set Failed Futures") {
    new Fitwitterture {
      Time.withTimeAt(time) { _ =>
        assert(cache.size === 0)

        cache.set(1, Future.etwitterception(new IllegalArgumentEtwitterception("")))
        assert(cache.size === 0)

        assert(getCounts("gets", "hits", "misses", "sets") === 0)
        assert(getCounts("evictions") === 1)
        assert(getCounts("failed_futures") === 1)
      }
    }
  }
}
