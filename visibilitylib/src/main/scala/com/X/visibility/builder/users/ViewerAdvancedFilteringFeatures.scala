package com.X.visibility.builder.users

import com.X.finagle.stats.Counter
import com.X.finagle.stats.StatsReceiver
import com.X.gizmoduck.thriftscala.AdvancedFilters
import com.X.gizmoduck.thriftscala.MentionFilter
import com.X.stitch.NotFound
import com.X.stitch.Stitch
import com.X.visibility.builder.FeatureMapBuilder
import com.X.visibility.common.UserSource
import com.X.visibility.features.ViewerFiltersDefaultProfileImage
import com.X.visibility.features.ViewerFiltersNewUsers
import com.X.visibility.features.ViewerFiltersNoConfirmedEmail
import com.X.visibility.features.ViewerFiltersNoConfirmedPhone
import com.X.visibility.features.ViewerFiltersNotFollowedBy
import com.X.visibility.features.ViewerMentionFilter

class ViewerAdvancedFilteringFeatures(userSource: UserSource, statsReceiver: StatsReceiver) {
  private[this] val scopedStatsReceiver = statsReceiver.scope("viewer_advanced_filtering_features")

  private[this] val requests = scopedStatsReceiver.counter("requests")

  private[this] val viewerFiltersNoConfirmedEmail =
    scopedStatsReceiver.scope(ViewerFiltersNoConfirmedEmail.name).counter("requests")
  private[this] val viewerFiltersNoConfirmedPhone =
    scopedStatsReceiver.scope(ViewerFiltersNoConfirmedPhone.name).counter("requests")
  private[this] val viewerFiltersDefaultProfileImage =
    scopedStatsReceiver.scope(ViewerFiltersDefaultProfileImage.name).counter("requests")
  private[this] val viewerFiltersNewUsers =
    scopedStatsReceiver.scope(ViewerFiltersNewUsers.name).counter("requests")
  private[this] val viewerFiltersNotFollowedBy =
    scopedStatsReceiver.scope(ViewerFiltersNotFollowedBy.name).counter("requests")
  private[this] val viewerMentionFilter =
    scopedStatsReceiver.scope(ViewerMentionFilter.name).counter("requests")

  def forViewerId(viewerId: Option[Long]): FeatureMapBuilder => FeatureMapBuilder = {
    requests.incr()

    _.withFeature(ViewerFiltersNoConfirmedEmail, viewerFiltersNoConfirmedEmail(viewerId))
      .withFeature(ViewerFiltersNoConfirmedPhone, viewerFiltersNoConfirmedPhone(viewerId))
      .withFeature(ViewerFiltersDefaultProfileImage, viewerFiltersDefaultProfileImage(viewerId))
      .withFeature(ViewerFiltersNewUsers, viewerFiltersNewUsers(viewerId))
      .withFeature(ViewerFiltersNotFollowedBy, viewerFiltersNotFollowedBy(viewerId))
      .withFeature(ViewerMentionFilter, viewerMentionFilter(viewerId))
  }

  def viewerFiltersNoConfirmedEmail(viewerId: Option[Long]): Stitch[Boolean] =
    viewerAdvancedFilters(viewerId, af => af.filterNoConfirmedEmail, viewerFiltersNoConfirmedEmail)

  def viewerFiltersNoConfirmedPhone(viewerId: Option[Long]): Stitch[Boolean] =
    viewerAdvancedFilters(viewerId, af => af.filterNoConfirmedPhone, viewerFiltersNoConfirmedPhone)

  def viewerFiltersDefaultProfileImage(viewerId: Option[Long]): Stitch[Boolean] =
    viewerAdvancedFilters(
      viewerId,
      af => af.filterDefaultProfileImage,
      viewerFiltersDefaultProfileImage
    )

  def viewerFiltersNewUsers(viewerId: Option[Long]): Stitch[Boolean] =
    viewerAdvancedFilters(viewerId, af => af.filterNewUsers, viewerFiltersNewUsers)

  def viewerFiltersNotFollowedBy(viewerId: Option[Long]): Stitch[Boolean] =
    viewerAdvancedFilters(viewerId, af => af.filterNotFollowedBy, viewerFiltersNotFollowedBy)

  def viewerMentionFilter(viewerId: Option[Long]): Stitch[MentionFilter] = {
    viewerMentionFilter.incr()
    viewerId match {
      case Some(id) =>
        userSource.getMentionFilter(id).handle {
          case NotFound =>
            MentionFilter.Unfiltered
        }
      case _ => Stitch.value(MentionFilter.Unfiltered)
    }
  }

  private[this] def viewerAdvancedFilters(
    viewerId: Option[Long],
    advancedFilterCheck: AdvancedFilters => Boolean,
    featureCounter: Counter
  ): Stitch[Boolean] = {
    featureCounter.incr()

    val advancedFilters = viewerId match {
      case Some(id) => userSource.getAdvancedFilters(id)
      case _ => Stitch.value(AdvancedFilters())
    }

    advancedFilters.map(advancedFilterCheck)
  }
}
