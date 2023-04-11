package com.twitter.visibility.features

import com.twitter.gizmoduck.thriftscala.MentionFilter
import com.twitter.util.Duration

case object ViewerFiltersNoConfirmedEmail extends Feature[Boolean]

case object ViewerFiltersNoConfirmedPhone extends Feature[Boolean]

case object ViewerFiltersDefaultProfileImage extends Feature[Boolean]

case object ViewerFiltersNewUsers extends Feature[Boolean]

case object ViewerFiltersNotFollowedBy extends Feature[Boolean]

case object ViewerMentionFilter extends Feature[MentionFilter]

case object AuthorHasConfirmedEmail extends Feature[Boolean]

case object AuthorHasVerifiedPhone extends Feature[Boolean]

case object AuthorHasDefaultProfileImage extends Feature[Boolean]

case object AuthorAccountAge extends Feature[Duration]
