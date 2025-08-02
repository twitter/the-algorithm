package com.twitter.visibility.features

import com.twitter.gizmoduck.thriftscala.MentionFilter
import com.twitter.util.Duration

case object ViewerFiltersNoConfirmedEmail etwittertends Feature[Boolean]

case object ViewerFiltersNoConfirmedPhone etwittertends Feature[Boolean]

case object ViewerFiltersDefaultProfileImage etwittertends Feature[Boolean]

case object ViewerFiltersNewUsers etwittertends Feature[Boolean]

case object ViewerFiltersNotFollowedBy etwittertends Feature[Boolean]

case object ViewerMentionFilter etwittertends Feature[MentionFilter]

case object AuthorHasConfirmedEmail etwittertends Feature[Boolean]

case object AuthorHasVerifiedPhone etwittertends Feature[Boolean]

case object AuthorHasDefaultProfileImage etwittertends Feature[Boolean]

case object AuthorAccountAge etwittertends Feature[Duration]
