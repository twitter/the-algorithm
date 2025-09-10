package com.twitter.home_mixer.product.scored_tweets.candidate_source

import com.twitter.conversions.DurationOps.richDurationFromInt
import com.twitter.product_mixer.core.functional_component.candidate_source.CandidateSource
import com.twitter.product_mixer.core.model.common.identifier.CandidateSourceIdentifier
import com.twitter.servo.cache.ExpiringLruInProcessCache
import com.twitter.servo.cache.InProcessCache
import com.twitter.stitch.Stitch
import com.twitter.strato.client.Fetcher
import com.twitter.strato.generated.client.timelines.pintweet.PinnedPostsClientColumn
import com.twitter.timelines.pinned_timelines.{thriftscala => pt}
import javax.inject.Inject
import javax.inject.Singleton
import scala.util.Random

case class StaticSourceRequest(countryCodes: Seq[String], following: Set[Long])

object StaticPostsCandidateSource {
  private val BaseTTL = 30
  private val StaticKey = 0
  private val TTL = (BaseTTL + Random.nextInt(15)).minutes

  val cache: InProcessCache[Int, Option[pt.PinnedPost]] =
    new ExpiringLruInProcessCache(ttl = TTL, maximumSize = 100)
}

@Singleton
class StaticPostsCandidateSource @Inject() (pinnedPostsClientColumn: PinnedPostsClientColumn)
    extends CandidateSource[StaticSourceRequest, Long] {

  import StaticPostsCandidateSource._

  override val identifier: CandidateSourceIdentifier = CandidateSourceIdentifier("StaticPosts")

  private val fetcher: Fetcher[Int, Unit, pt.PinnedPost] = pinnedPostsClientColumn.fetcher

  override def apply(request: StaticSourceRequest): Stitch[Seq[Long]] = {
    val postsStitch = cache.get(StaticKey).map(Stitch.value(_)).getOrElse {
      fetcher.fetch(StaticKey).map { response =>
        cache.set(StaticKey, response.v)
        response.v
      }
    }

    postsStitch.map {
      _.map { post =>
        val isInNetwork = request.following.contains(post.authorId)
        val countryMatches = request.countryCodes.exists { code =>
          post.countryCodes.forall(_.map(_.toLowerCase).contains(code.toLowerCase))
        }
        if (countryMatches && ((post.inNetwork && isInNetwork) || (post.outOfNetwork && !isInNetwork)))
          Seq(post.postId)
        else Seq.empty
      }.getOrElse(Seq.empty)
    }
  }
}
