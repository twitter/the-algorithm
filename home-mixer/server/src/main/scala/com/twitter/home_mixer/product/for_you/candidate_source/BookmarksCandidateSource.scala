package com.twitter.home_mixer.product.for_you.candidate_source

import com.twitter.frigate.bookmarks.thriftscala.BookmarkedTweet
import com.twitter.product_mixer.core.functional_component.candidate_source.CandidateSource
import com.twitter.product_mixer.core.model.common.identifier.CandidateSourceIdentifier
import com.twitter.stitch.Stitch
import com.twitter.strato.generated.client.frigate.bookmarks.WeeklyBookmarksOnUserClientColumn

import javax.inject.Inject
import javax.inject.Singleton
import scala.util.Random

@Singleton
class BookmarksCandidateSource @Inject() (
  weeklyBookmarksOnUserClientColumn: WeeklyBookmarksOnUserClientColumn)
    extends CandidateSource[
      Long,
      BookmarkedTweet
    ] {

  override val identifier: CandidateSourceIdentifier = CandidateSourceIdentifier("Bookmarks")

  private val MaxCandidates = 20
  private val fetcher = weeklyBookmarksOnUserClientColumn.fetcher

  // Remove duplicates and unbookmarked tweets
  private def filterValidBookmarks(bookmarks: Seq[BookmarkedTweet]): Seq[BookmarkedTweet] = {
    bookmarks
      .groupBy(_.tweetId)
      .collect {
        case (_, duplicatedBookmarks)
            if !duplicatedBookmarks.exists(_.unbookmark.getOrElse(false)) =>
          duplicatedBookmarks.head
      }.toSeq
  }

  override def apply(userId: Long): Stitch[Seq[BookmarkedTweet]] = {
    fetcher.fetch(userId, ()).map { res =>
      res.v
        .map { bookmarks =>
          val filteredBookmarks = filterValidBookmarks(bookmarks.bookmarkedTweets)
          Random.shuffle(filteredBookmarks).take(MaxCandidates)
        }.getOrElse(Seq.empty)
    }
  }
}
