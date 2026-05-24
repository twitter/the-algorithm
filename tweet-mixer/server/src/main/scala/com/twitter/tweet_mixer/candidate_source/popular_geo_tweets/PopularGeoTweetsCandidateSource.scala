package com.twitter.tweet_mixer.candidate_source.popular_geo_tweets

import com.twitter.finagle.stats.StatsReceiver
import com.twitter.product_mixer.core.functional_component.candidate_source.CandidateSource
import com.twitter.product_mixer.core.model.common.identifier.CandidateSourceIdentifier
import com.twitter.product_mixer.core.util.OffloadFuturePools
import com.twitter.stitch.Stitch
import com.twitter.strato.generated.client.trends.trip.TripTweetsProdClientColumn
import com.twitter.trends.trip_v1.trip_tweets.thriftscala.TripDomain
import com.twitter.trends.trip_v1.trip_tweets.{thriftscala => t}
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PopularGeoTweetsCandidateSource @Inject() (
  tripStratoColumn: TripTweetsProdClientColumn,
  inputStatsReceiver: StatsReceiver)
    extends CandidateSource[TripStratoGeoQuery, t.TripTweet] {

  private val scopedStats = inputStatsReceiver.scope(getClass.getSimpleName)
  private val emptyDomainCounter = scopedStats.counter("emptyDomain")
  override val identifier: CandidateSourceIdentifier = CandidateSourceIdentifier("PopularGeoTweets")
  private val unifiedTripTweetScore: Double = 0D

  override def apply(request: TripStratoGeoQuery): Stitch[Seq[t.TripTweet]] = {
    if (request.domains.isEmpty) emptyDomainCounter.incr()

    getTweetsSortedByDomain(
      domains = request.domains,
      maxCandidatesPerSource = request.maxCandidatesPerSource,
      maxPopGeoCandidates = request.maxPopGeoCandidates
    )
  }

  private def getTweetsSortedByDomain(
    domains: Seq[TripDomain],
    maxCandidatesPerSource: Int,
    maxPopGeoCandidates: Int
  ): Stitch[Seq[t.TripTweet]] = OffloadFuturePools.offloadStitch {
    val fetcher = tripStratoColumn.fetcher

    Stitch
      .collect(domains.zipWithIndex.map {
        case (tripDomain: TripDomain, domainIndex: Int) =>
          fetcher.fetch(tripDomain).map {
            stratoValue =>
              val tripTweetsSeq = stratoValue.v
                .map { response =>
                  response.tweets
                    .take(maxCandidatesPerSource)
                    .zipWithIndex.map {
                      case (tripTweet, tweetIndex) =>
                        val plainTripTweet =
                          t.TripTweet(
                            tweetId = tripTweet.tweetId,
                            score = unifiedTripTweetScore
                          )
                        (tweetIndex, domainIndex, plainTripTweet)
                    }
                }.getOrElse(Seq.empty)

              scopedStats.counter(tripDomain.sourceId).incr(tripTweetsSeq.size)
              tripTweetsSeq
          }
      }).map(_.flatten).map { tweetsWithIndicesSeq: Seq[(Int, Int, t.TripTweet)] =>
        // sort by tweetIndex first, then domainIndex,
        // so each domain (sourceId + others) can have equal weights being selected
        tweetsWithIndicesSeq
          .sortBy(t => (t._1, t._2)).map(_._3).distinct.take(maxPopGeoCandidates)
      }
  }
}
