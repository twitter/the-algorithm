package com.X.visibility.rules

import com.X.visibility.models.TweetSafetyLabelType
import com.X.visibility.rules.Condition.And
import com.X.visibility.rules.Condition.HasSearchCandidateCountGreaterThan45
import com.X.visibility.rules.Condition.IsFirstPageSearchResult
import com.X.visibility.rules.Condition.Not
import com.X.visibility.rules.Reason.FirstPageSearchResult

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
