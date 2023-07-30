package com.X.visibility.rules

import com.X.gizmoduck.thriftscala.MentionFilter.Following
import com.X.visibility.features.ViewerMentionFilter
import com.X.visibility.rules.Condition._
import com.X.visibility.rules.Reason.Unspecified

object NoConfirmedEmailRule
    extends RuleWithConstantAction(
      Drop(Unspecified),
      And(
        NonAuthorViewer,
        Not(ViewerDoesFollowAuthor),
        ViewerFiltersNoConfirmedEmail,
        Not(AuthorHasConfirmedEmail)
      )
    )

object NoConfirmedPhoneRule
    extends RuleWithConstantAction(
      Drop(Unspecified),
      And(
        NonAuthorViewer,
        Not(ViewerDoesFollowAuthor),
        ViewerFiltersNoConfirmedPhone,
        Not(AuthorHasVerifiedPhone)
      )
    )

object NoDefaultProfileImageRule
    extends RuleWithConstantAction(
      Drop(Unspecified),
      And(
        NonAuthorViewer,
        Not(ViewerDoesFollowAuthor),
        ViewerFiltersDefaultProfileImage,
        AuthorHasDefaultProfileImage
      )
    )

object NoNewUsersRule
    extends RuleWithConstantAction(
      Drop(Unspecified),
      And(
        NonAuthorViewer,
        Not(ViewerDoesFollowAuthor),
        AuthorIsNewAccount
      )
    )

object NoNotFollowedByRule
    extends RuleWithConstantAction(
      Drop(Unspecified),
      And(
        NonAuthorViewer,
        Not(ViewerDoesFollowAuthor),
        ViewerFiltersNotFollowedBy,
        Not(AuthorDoesFollowViewer)
      )
    )

object OnlyPeopleIFollowRule
    extends RuleWithConstantAction(
      Drop(Unspecified),
      And(
        NonAuthorViewer,
        Not(ViewerDoesFollowAuthor),
        Equals(ViewerMentionFilter, Following),
        Not(NotificationIsOnCommunityTweet)
      )
    )
