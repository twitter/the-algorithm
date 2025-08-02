package com.twitter.unified_user_actions.adapter

import com.twitter.contetwittert.thriftscala.Viewer
import com.twitter.inject.Test
import com.twitter.timelineservice.thriftscala._
import com.twitter.unified_user_actions.adapter.tls_favs_event.TlsFavsAdapter
import com.twitter.unified_user_actions.thriftscala._
import com.twitter.util.Time

class TlsFavsAdapterSpec etwittertends Test {
  trait Fitwitterture {

    val frozenTime = Time.fromMilliseconds(1658949273000L)

    val favEventNoRetweet = ContetwittertualizedFavoriteEvent(
      event = FavoriteEventUnion.Favorite(
        FavoriteEvent(
          userId = 91L,
          tweetId = 1L,
          tweetUserId = 101L,
          eventTimeMs = 1001L
        )
      ),
      contetwittert = LogEventContetwittert(hostname = "", traceId = 31L)
    )
    val favEventRetweet = ContetwittertualizedFavoriteEvent(
      event = FavoriteEventUnion.Favorite(
        FavoriteEvent(
          userId = 92L,
          tweetId = 2L,
          tweetUserId = 102L,
          eventTimeMs = 1002L,
          retweetId = Some(22L)
        )
      ),
      contetwittert = LogEventContetwittert(hostname = "", traceId = 32L)
    )
    val unfavEventNoRetweet = ContetwittertualizedFavoriteEvent(
      event = FavoriteEventUnion.Unfavorite(
        UnfavoriteEvent(
          userId = 93L,
          tweetId = 3L,
          tweetUserId = 103L,
          eventTimeMs = 1003L
        )
      ),
      contetwittert = LogEventContetwittert(hostname = "", traceId = 33L)
    )
    val unfavEventRetweet = ContetwittertualizedFavoriteEvent(
      event = FavoriteEventUnion.Unfavorite(
        UnfavoriteEvent(
          userId = 94L,
          tweetId = 4L,
          tweetUserId = 104L,
          eventTimeMs = 1004L,
          retweetId = Some(44L)
        )
      ),
      contetwittert = LogEventContetwittert(hostname = "", traceId = 34L)
    )
    val favEventWithLangAndCountry = ContetwittertualizedFavoriteEvent(
      event = FavoriteEventUnion.Favorite(
        FavoriteEvent(
          userId = 91L,
          tweetId = 1L,
          tweetUserId = 101L,
          eventTimeMs = 1001L,
          viewerContetwittert =
            Some(Viewer(requestCountryCode = Some("us"), requestLanguageCode = Some("en")))
        )
      ),
      contetwittert = LogEventContetwittert(hostname = "", traceId = 31L)
    )

    val etwitterpectedUua1 = UnifiedUserAction(
      userIdentifier = UserIdentifier(userId = Some(91L)),
      item = Item.TweetInfo(
        TweetInfo(
          actionTweetId = 1L,
          actionTweetAuthorInfo = Some(AuthorInfo(authorId = Some(101L))),
        )
      ),
      actionType = ActionType.ServerTweetFav,
      eventMetadata = EventMetadata(
        sourceTimestampMs = 1001L,
        receivedTimestampMs = frozenTime.inMilliseconds,
        sourceLineage = SourceLineage.ServerTlsFavs,
        traceId = Some(31L)
      )
    )
    val etwitterpectedUua2 = UnifiedUserAction(
      userIdentifier = UserIdentifier(userId = Some(92L)),
      item = Item.TweetInfo(
        TweetInfo(
          actionTweetId = 2L,
          actionTweetAuthorInfo = Some(AuthorInfo(authorId = Some(102L))),
          retweetingTweetId = Some(22L)
        )
      ),
      actionType = ActionType.ServerTweetFav,
      eventMetadata = EventMetadata(
        sourceTimestampMs = 1002L,
        receivedTimestampMs = frozenTime.inMilliseconds,
        sourceLineage = SourceLineage.ServerTlsFavs,
        traceId = Some(32L)
      )
    )
    val etwitterpectedUua3 = UnifiedUserAction(
      userIdentifier = UserIdentifier(userId = Some(93L)),
      item = Item.TweetInfo(
        TweetInfo(
          actionTweetId = 3L,
          actionTweetAuthorInfo = Some(AuthorInfo(authorId = Some(103L))),
        )
      ),
      actionType = ActionType.ServerTweetUnfav,
      eventMetadata = EventMetadata(
        sourceTimestampMs = 1003L,
        receivedTimestampMs = frozenTime.inMilliseconds,
        sourceLineage = SourceLineage.ServerTlsFavs,
        traceId = Some(33L)
      )
    )
    val etwitterpectedUua4 = UnifiedUserAction(
      userIdentifier = UserIdentifier(userId = Some(94L)),
      item = Item.TweetInfo(
        TweetInfo(
          actionTweetId = 4L,
          actionTweetAuthorInfo = Some(AuthorInfo(authorId = Some(104L))),
          retweetingTweetId = Some(44L)
        )
      ),
      actionType = ActionType.ServerTweetUnfav,
      eventMetadata = EventMetadata(
        sourceTimestampMs = 1004L,
        receivedTimestampMs = frozenTime.inMilliseconds,
        sourceLineage = SourceLineage.ServerTlsFavs,
        traceId = Some(34L)
      )
    )
    val etwitterpectedUua5 = UnifiedUserAction(
      userIdentifier = UserIdentifier(userId = Some(91L)),
      item = Item.TweetInfo(
        TweetInfo(
          actionTweetId = 1L,
          actionTweetAuthorInfo = Some(AuthorInfo(authorId = Some(101L))),
        )
      ),
      actionType = ActionType.ServerTweetFav,
      eventMetadata = EventMetadata(
        sourceTimestampMs = 1001L,
        receivedTimestampMs = frozenTime.inMilliseconds,
        sourceLineage = SourceLineage.ServerTlsFavs,
        language = Some("EN"),
        countryCode = Some("US"),
        traceId = Some(31L)
      )
    )
  }

  test("fav event with no retweet") {
    new Fitwitterture {
      Time.withTimeAt(frozenTime) { _ =>
        val actual = TlsFavsAdapter.adaptEvent(favEventNoRetweet)
        assert(Seq(etwitterpectedUua1) === actual)
      }
    }
  }

  test("fav event with a retweet") {
    new Fitwitterture {
      Time.withTimeAt(frozenTime) { _ =>
        val actual = TlsFavsAdapter.adaptEvent(favEventRetweet)
        assert(Seq(etwitterpectedUua2) === actual)
      }
    }
  }

  test("unfav event with no retweet") {
    new Fitwitterture {
      Time.withTimeAt(frozenTime) { _ =>
        val actual = TlsFavsAdapter.adaptEvent(unfavEventNoRetweet)
        assert(Seq(etwitterpectedUua3) === actual)
      }
    }
  }

  test("unfav event with a retweet") {
    new Fitwitterture {
      Time.withTimeAt(frozenTime) { _ =>
        val actual = TlsFavsAdapter.adaptEvent(unfavEventRetweet)
        assert(Seq(etwitterpectedUua4) === actual)
      }
    }
  }

  test("fav event with language and country") {
    new Fitwitterture {
      Time.withTimeAt(frozenTime) { _ =>
        val actual = TlsFavsAdapter.adaptEvent(favEventWithLangAndCountry)
        assert(Seq(etwitterpectedUua5) === actual)
      }
    }
  }
}
