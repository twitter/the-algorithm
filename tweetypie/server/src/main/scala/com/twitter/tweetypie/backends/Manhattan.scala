package com.twitter.tweetypie
package backends

import com.twitter.servo.etwitterception.thriftscala
import com.twitter.servo.etwitterception.thriftscala.ClientErrorCause
import com.twitter.stitch.Stitch
import com.twitter.storage.client.manhattan.kv.TimeoutManhattanEtwitterception
import com.twitter.tweetypie.core.OverCapacity
import com.twitter.tweetypie.storage.TweetStorageClient.Ping
import com.twitter.tweetypie.storage.ClientError
import com.twitter.tweetypie.storage.RateLimited
import com.twitter.tweetypie.storage.TweetStorageClient
import com.twitter.tweetypie.util.StitchUtils
import com.twitter.util.TimeoutEtwitterception

object Manhattan {
  def fromClient(underlying: TweetStorageClient): TweetStorageClient =
    new TweetStorageClient {
      val addTweet = translateEtwitterceptions(underlying.addTweet)
      val deleteAdditionalFields = translateEtwitterceptions(underlying.deleteAdditionalFields)
      val getDeletedTweets = translateEtwitterceptions(underlying.getDeletedTweets)
      val getTweet = translateEtwitterceptions(underlying.getTweet)
      val getStoredTweet = translateEtwitterceptions(underlying.getStoredTweet)
      val scrub = translateEtwitterceptions(underlying.scrub)
      val softDelete = translateEtwitterceptions(underlying.softDelete)
      val undelete = translateEtwitterceptions(underlying.undelete)
      val updateTweet = translateEtwitterceptions(underlying.updateTweet)
      val hardDeleteTweet = translateEtwitterceptions(underlying.hardDeleteTweet)
      val ping: Ping = underlying.ping
      val bounceDelete = translateEtwitterceptions(underlying.bounceDelete)
    }

  private[backends] object translateEtwitterceptions {
    private[this] def pf: PartialFunction[Throwable, Throwable] = {
      case e: RateLimited => OverCapacity(s"storage: ${e.getMessage}")
      case e: TimeoutManhattanEtwitterception => new TimeoutEtwitterception(e.getMessage)
      case e: ClientError => thriftscala.ClientError(ClientErrorCause.BadRequest, e.message)
    }

    def apply[A, B](f: A => Stitch[B]): A => Stitch[B] =
      a => StitchUtils.translateEtwitterceptions(f(a), pf)

    def apply[A, B, C](f: (A, B) => Stitch[C]): (A, B) => Stitch[C] =
      (a, b) => StitchUtils.translateEtwitterceptions(f(a, b), pf)
  }
}
