package com.twitter.visibility.rules

import com.twitter.gizmoduck.thriftscala.MentionFilter.Following
import com.twitter.visibility.features.ViewerMentionFilter
import com.twitter.visibility.rules.Condition._
import com.twitter.visibility.rules.Reason.Unspecified

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
