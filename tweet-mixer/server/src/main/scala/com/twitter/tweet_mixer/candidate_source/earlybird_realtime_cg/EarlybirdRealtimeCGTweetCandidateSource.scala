package com.twitter.tweet_mixer.candidate_source.earlybird_realtime_cg

import com.twitter.finagle.stats.StatsReceiver
import com.twitter.product_mixer.core.functional_component.candidate_source.CandidateSource
import com.twitter.product_mixer.core.model.common.identifier.CandidateSourceIdentifier
import com.twitter.search.earlybird.{thriftscala => t}
import com.twitter.stitch.Stitch
import com.twitter.tweet_mixer.model.ModuleNames.EarlybirdRealtimeCGEndpoint
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton
import com.twitter.product_mixer.core.util.OffloadFuturePools
import com.twitter.timelines.clients.haplolite.TweetTimelineHaploCodec
import com.twitter.haplolite.{thriftscala => hl}
import com.twitter.timelineservice.model.Tweet
import com.twitter.timelineservice.model.core.TimelineKind

@Singleton
class EarlybirdRealtimeCGTweetCandidateSource @Inject() (
  haploliteClient: hl.Haplolite.MethodPerEndpoint,
  @Named(EarlybirdRealtimeCGEndpoint) earlybirdService: t.EarlybirdService.MethodPerEndpoint,
  statsReceiver: StatsReceiver)
    extends CandidateSource[InNetworkRequest, t.ThriftSearchResult] {

  override val identifier: CandidateSourceIdentifier =
    CandidateSourceIdentifier("EarlybirdRealtimeCGTweets")

  private val tweetTimelineHaploCodec = new TweetTimelineHaploCodec(statsReceiver)

  private val maxDepth = 2000

  val haploliteSetSuccessCounter = statsReceiver.counter("HaploliteSetSuccess")
  val haploliteSetFailureCounter = statsReceiver.counter("HaploliteSetFailure")

  private def convertToTimelineEntry(ebResult: t.ThriftSearchResult): Tweet = {
    val id = ebResult.id
    val userId = ebResult.metadata.map(_.fromUserId)
    val isRetweet = ebResult.metadata.flatMap(_.isRetweet)
    val isReply = ebResult.metadata.flatMap(_.isReply)
    val sourceTweetId =
      if (isRetweet.getOrElse(false))
        ebResult.metadata.map(_.sharedStatusId)
      else None
    val sourceUserId =
      if (isRetweet.getOrElse(false))
        ebResult.metadata.map(_.referencedTweetAuthorId)
      else None
    val conversationId =
      if (isReply.getOrElse(false))
        ebResult.metadata.flatMap(_.extraMetadata).flatMap(_.conversationId)
      else None
    val inReplyToTweetId =
      if (isReply.getOrElse(false))
        ebResult.metadata.map(_.sharedStatusId)
      else None
    Tweet(
      tweetId = id,
      userId = userId,
      sourceTweetId = sourceTweetId,
      sourceUserId = sourceUserId,
      conversationId = conversationId,
      inReplyToTweetId = inReplyToTweetId)
  }

  private def updateHaploCache(userId: Long, ebResults: Seq[t.ThriftSearchResult]) = {
    val timelineEntries = ebResults.take(maxDepth).map(convertToTimelineEntry(_))
    val encodedEntries = tweetTimelineHaploCodec.encode(timelineEntries, TimelineKind.home)
    val operation = hl.Operation(
      Seq(hl.BulkKeys(hl.KeyNamespace.Home.value, Seq(userId))),
      Some(hl.TimelineOperation(encodedEntries))
    )
    val setEntriesResult = haploliteClient.setEntries(hl.SetEntriesRequest(Seq(operation)))
    setEntriesResult
      .onSuccess {
        case _ => haploliteSetSuccessCounter.incr()
      }.onFailure {
        case _ => haploliteSetFailureCounter.incr()
      }.unit
  }

  override def apply(
    request: InNetworkRequest
  ): Stitch[Seq[t.ThriftSearchResult]] = {
    val ebRequest = request.earlybirdRequest
    val excludedIds = request.excludedIds
    val ebStitchResults = OffloadFuturePools.offloadFuture(
      earlybirdService
        .search(ebRequest)
        .map { response: t.EarlybirdResponse =>
          response.searchResults
            .map(_.results)
            .getOrElse(Seq.empty)
        })
    ebStitchResults.applyEffect {
      case ebResults =>
        if (request.writeBackToHaplo) {
          Stitch.callFuture(
            updateHaploCache(ebRequest.searchQuery.searcherId.getOrElse(-1L), ebResults))
        } else {
          Stitch.Unit
        }
    }
  }
}
