package com.twitter.visibility.rules

import com.twitter.visibility.models.TweetSafetyLabelType
import com.twitter.visibility.rules.Condition.And
import com.twitter.visibility.rules.Condition.HasSearchCandidateCountGreaterThan45
import com.twitter.visibility.rules.Condition.IsFirstPageSearchResult
import com.twitter.visibility.rules.Condition.Not
import com.twitter.visibility.rules.Reason.FirstPageSearchResult

abstract class FirstPageSearchResultWithTweetLabelRule(
  action: Action,
  tweetSafetyLabelType: TweetSafetyLabelType)
    extends ConditionWithTweetLabelRule(
      action,
      IsFirstPageSearchResult,
      tweetSafetyLabelType
    )

abstract class FirstPageSearchResultSmartOutOfNetworkWithTweetLabelRule(
  action: Action,
  tweetSafetyLabelType: TweetSafetyLabelType)
    extends ConditionWithTweetLabelRule(
      action,
      And(
        IsFirstPageSearchResult,
        HasSearchCandidateCountGreaterThan45,
        Condition.NonAuthorViewer,
        Not(Condition.ViewerDoesFollowAuthor),
        Not(Condition.VerifiedAuthor)
      ),
      tweetSafetyLabelType
    )

object FirstPageSearchResultAgathaSpamDropRule
    extends FirstPageSearchResultWithTweetLabelRule(
      Drop(FirstPageSearchResult),
      TweetSafetyLabelType.AgathaSpam)
