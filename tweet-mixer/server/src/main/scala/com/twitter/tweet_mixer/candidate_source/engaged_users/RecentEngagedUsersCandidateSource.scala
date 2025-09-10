package com.twitter.tweet_mixer.candidate_source.engaged_users

import com.twitter.product_mixer.core.functional_component.candidate_source.strato.StratoKeyFetcherSource
import com.twitter.product_mixer.core.model.common.identifier.CandidateSourceIdentifier
import com.twitter.strato.client.Fetcher
import com.twitter.strato.generated.client.recommendations.twistly.TweetRecentEngagedUsersClientColumn
import com.twitter.tweet_mixer.feature.EntityTypes.UserId
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RecentEngagedUsersCandidateSource @Inject() (
  tweetRecentEngagedUsersClientColumn: TweetRecentEngagedUsersClientColumn)
    extends StratoKeyFetcherSource[
      TweetRecentEngagedUsersClientColumn.Key,
      TweetRecentEngagedUsersClientColumn.Value,
      UserId
    ] {

  override val fetcher: Fetcher[
    TweetRecentEngagedUsersClientColumn.Key,
    Unit,
    TweetRecentEngagedUsersClientColumn.Value
  ] =
    tweetRecentEngagedUsersClientColumn.fetcher

  override val identifier: CandidateSourceIdentifier = CandidateSourceIdentifier(
    "RecentEngagedUsers")

  override protected def stratoResultTransformer(
    stratoResult: tweetRecentEngagedUsersClientColumn.Value
  ): Seq[UserId] = {
    stratoResult.recentEngagedUsers.map(_.userId)
  }
}
