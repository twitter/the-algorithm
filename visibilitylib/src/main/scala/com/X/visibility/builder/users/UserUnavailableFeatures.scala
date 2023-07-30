package com.X.visibility.builder.users

import com.X.finagle.stats.StatsReceiver
import com.X.visibility.builder.FeatureMapBuilder
import com.X.visibility.common.user_result.UserVisibilityResultHelper
import com.X.visibility.features.AuthorBlocksViewer
import com.X.visibility.features.AuthorIsDeactivated
import com.X.visibility.features.AuthorIsErased
import com.X.visibility.features.AuthorIsOffboarded
import com.X.visibility.features.AuthorIsProtected
import com.X.visibility.features.AuthorIsSuspended
import com.X.visibility.features.AuthorIsUnavailable
import com.X.visibility.features.ViewerBlocksAuthor
import com.X.visibility.features.ViewerMutesAuthor
import com.X.visibility.models.UserUnavailableStateEnum

case class UserUnavailableFeatures(statsReceiver: StatsReceiver) {

  private[this] val scopedStatsReceiver = statsReceiver.scope("user_unavailable_features")
  private[this] val suspendedAuthorStats = scopedStatsReceiver.scope("suspended_author")
  private[this] val deactivatedAuthorStats = scopedStatsReceiver.scope("deactivated_author")
  private[this] val offboardedAuthorStats = scopedStatsReceiver.scope("offboarded_author")
  private[this] val erasedAuthorStats = scopedStatsReceiver.scope("erased_author")
  private[this] val protectedAuthorStats = scopedStatsReceiver.scope("protected_author")
  private[this] val authorBlocksViewerStats = scopedStatsReceiver.scope("author_blocks_viewer")
  private[this] val viewerBlocksAuthorStats = scopedStatsReceiver.scope("viewer_blocks_author")
  private[this] val viewerMutesAuthorStats = scopedStatsReceiver.scope("viewer_mutes_author")
  private[this] val unavailableStats = scopedStatsReceiver.scope("unavailable")

  def forState(state: UserUnavailableStateEnum): FeatureMapBuilder => FeatureMapBuilder = {
    builder =>
      builder
        .withConstantFeature(AuthorIsSuspended, isSuspended(state))
        .withConstantFeature(AuthorIsDeactivated, isDeactivated(state))
        .withConstantFeature(AuthorIsOffboarded, isOffboarded(state))
        .withConstantFeature(AuthorIsErased, isErased(state))
        .withConstantFeature(AuthorIsProtected, isProtected(state))
        .withConstantFeature(AuthorBlocksViewer, authorBlocksViewer(state))
        .withConstantFeature(ViewerBlocksAuthor, viewerBlocksAuthor(state))
        .withConstantFeature(ViewerMutesAuthor, viewerMutesAuthor(state))
        .withConstantFeature(AuthorIsUnavailable, isUnavailable(state))
  }

  private[this] def isSuspended(state: UserUnavailableStateEnum): Boolean =
    state match {
      case UserUnavailableStateEnum.Suspended =>
        suspendedAuthorStats.counter().incr()
        true
      case UserUnavailableStateEnum.Filtered(result)
          if UserVisibilityResultHelper.isDropSuspendedAuthor(result) =>
        suspendedAuthorStats.counter().incr()
        suspendedAuthorStats.counter("filtered").incr()
        true
      case _ => false
    }

  private[this] def isDeactivated(state: UserUnavailableStateEnum): Boolean =
    state match {
      case UserUnavailableStateEnum.Deactivated =>
        deactivatedAuthorStats.counter().incr()
        true
      case _ => false
    }

  private[this] def isOffboarded(state: UserUnavailableStateEnum): Boolean =
    state match {
      case UserUnavailableStateEnum.Offboarded =>
        offboardedAuthorStats.counter().incr()
        true
      case _ => false
    }

  private[this] def isErased(state: UserUnavailableStateEnum): Boolean =
    state match {
      case UserUnavailableStateEnum.Erased =>
        erasedAuthorStats.counter().incr()
        true
      case _ => false
    }

  private[this] def isProtected(state: UserUnavailableStateEnum): Boolean =
    state match {
      case UserUnavailableStateEnum.Protected =>
        protectedAuthorStats.counter().incr()
        true
      case UserUnavailableStateEnum.Filtered(result)
          if UserVisibilityResultHelper.isDropProtectedAuthor(result) =>
        protectedAuthorStats.counter().incr()
        protectedAuthorStats.counter("filtered").incr()
        true
      case _ => false
    }

  private[this] def authorBlocksViewer(state: UserUnavailableStateEnum): Boolean =
    state match {
      case UserUnavailableStateEnum.AuthorBlocksViewer =>
        authorBlocksViewerStats.counter().incr()
        true
      case UserUnavailableStateEnum.Filtered(result)
          if UserVisibilityResultHelper.isDropAuthorBlocksViewer(result) =>
        authorBlocksViewerStats.counter().incr()
        authorBlocksViewerStats.counter("filtered").incr()
        true
      case _ => false
    }

  private[this] def viewerBlocksAuthor(state: UserUnavailableStateEnum): Boolean =
    state match {
      case UserUnavailableStateEnum.ViewerBlocksAuthor =>
        viewerBlocksAuthorStats.counter().incr()
        true
      case UserUnavailableStateEnum.Filtered(result)
          if UserVisibilityResultHelper.isDropViewerBlocksAuthor(result) =>
        viewerBlocksAuthorStats.counter().incr()
        viewerBlocksAuthorStats.counter("filtered").incr()
        true
      case _ => false
    }

  private[this] def viewerMutesAuthor(state: UserUnavailableStateEnum): Boolean =
    state match {
      case UserUnavailableStateEnum.ViewerMutesAuthor =>
        viewerMutesAuthorStats.counter().incr()
        true
      case UserUnavailableStateEnum.Filtered(result)
          if UserVisibilityResultHelper.isDropViewerMutesAuthor(result) =>
        viewerMutesAuthorStats.counter().incr()
        viewerMutesAuthorStats.counter("filtered").incr()
        true
      case _ => false
    }

  private[this] def isUnavailable(state: UserUnavailableStateEnum): Boolean =
    state match {
      case UserUnavailableStateEnum.Unavailable =>
        unavailableStats.counter().incr()
        true
      case UserUnavailableStateEnum.Filtered(result)
          if UserVisibilityResultHelper.isDropUnspecifiedAuthor(result) =>
        unavailableStats.counter().incr()
        unavailableStats.counter("filtered").incr()
        true
      case _ => false
    }
}
