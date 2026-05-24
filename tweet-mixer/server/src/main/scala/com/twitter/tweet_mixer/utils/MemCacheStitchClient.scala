package com.twitter.tweet_mixer.utils

import com.twitter.finagle.memcached.Client
import com.twitter.finagle.stats.StatsReceiver
import com.twitter.io.Buf
import com.twitter.stitch.MapGroup
import com.twitter.stitch.Stitch
import com.twitter.util.Future
import com.twitter.util.Return
import com.twitter.util.Throw
import com.twitter.util.Time
import com.twitter.util.Try

/***
 * This class provides a stitch wrapper around the finagle memcache future interface
 * to avoid having to do manual batch allocation.
 */
class MemcacheStitchClient(memcacheClient: Client, statsReceiver: StatsReceiver) {

  def get(key: String): Stitch[Option[Buf]] = {
    Stitch.call(key, getGroup)
  }

  // The memcache client doesn't provide a batch set interface so we have to just call future.
  def set(key: String, value: Buf, writeTtl: Int): Stitch[Unit] = {
    Stitch.callFuture(memcacheClient.set(key, 0, Time.fromSeconds(writeTtl), value))
  }

  private val getGroup = new GetGroup

  private class GetGroup extends MapGroup[String, Option[Buf]] {
    override def maxSize: Int = 50
    protected def run(keys: Seq[String]): Future[String => Try[Option[Buf]]] = {
      val future = memcacheClient.getResult(keys).map { result =>
        val hits = result.hits.transform { (_, v) =>
          Return(Some(v.value))
        }
        val misses = result.misses.toList.map { k =>
          k -> Return(Option.empty[Buf])
        }.toMap
        val failures = result.failures.transform { (_, f) =>
          Throw[Option[Buf]](f)
        }
        hits ++ misses ++ failures
      }
      future
    }
  }
}
