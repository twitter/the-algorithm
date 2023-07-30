package com.X.visibility.interfaces.users

import com.X.decider.Decider
import com.X.gizmoduck.thriftscala.User
import com.X.servo.decider.DeciderGateBuilder
import com.X.stitch.Stitch
import com.X.strato.client.Client
import com.X.visibility.VisibilityLibrary
import com.X.visibility.builder.users.AuthorFeatures
import com.X.visibility.builder.users.RelationshipFeatures
import com.X.visibility.builder.users.ViewerAdvancedFilteringFeatures
import com.X.visibility.builder.users.ViewerFeatures
import com.X.visibility.builder.users.ViewerSearchSafetyFeatures
import com.X.visibility.builder.VisibilityResult
import com.X.visibility.builder.users.SearchFeatures
import com.X.visibility.common.UserRelationshipSource
import com.X.visibility.common.UserSearchSafetySource
import com.X.visibility.common.UserSource
import com.X.visibility.configapi.configs.VisibilityDeciderGates
import com.X.visibility.context.thriftscala.UserVisibilityFilteringContext
import com.X.visibility.models.ContentId.UserId
import com.X.visibility.models.SafetyLevel
import com.X.visibility.models.ViewerContext
import com.X.visibility.rules.Reason.Unspecified
import com.X.visibility.rules.Allow
import com.X.visibility.rules.Drop
import com.X.visibility.rules.RuleBase

object UserVisibilityLibrary {
  type Type =
    (User, SafetyLevel, ViewerContext, UserVisibilityFilteringContext) => Stitch[VisibilityResult]

  def apply(
    visibilityLibrary: VisibilityLibrary,
    userSource: UserSource = UserSource.empty,
    userRelationshipSource: UserRelationshipSource = UserRelationshipSource.empty,
    stratoClient: Client,
    decider: Decider
  ): Type = {
    val libraryStatsReceiver = visibilityLibrary.statsReceiver.scope("user_library")
    val stratoClientStatsReceiver = visibilityLibrary.statsReceiver.scope("strato")

    val visibilityDeciderGates = VisibilityDeciderGates(decider)

    val vfEngineCounter = libraryStatsReceiver.counter("vf_engine_requests")
    val noUserRulesCounter = libraryStatsReceiver.counter("no_user_rules_requests")
    val viewerIsAuthorCounter = libraryStatsReceiver.counter("viewer_is_author_requests")

    val authorFeatures = new AuthorFeatures(userSource, libraryStatsReceiver)
    val viewerFeatures = new ViewerFeatures(userSource, libraryStatsReceiver)
    val relationshipFeatures =
      new RelationshipFeatures(userRelationshipSource, libraryStatsReceiver)
    val searchFeatures = new SearchFeatures(libraryStatsReceiver)

    val viewerSafeSearchFeatures = new ViewerSearchSafetyFeatures(
      UserSearchSafetySource.fromStrato(stratoClient, stratoClientStatsReceiver),
      libraryStatsReceiver)

    val deciderGateBuilder = new DeciderGateBuilder(decider)
    val advancedFilteringFeatures =
      new ViewerAdvancedFilteringFeatures(userSource, libraryStatsReceiver)

    (user, safetyLevel, viewerContext, userVisibilityFilteringContext) => {
      val contentId = UserId(user.id)
      val viewerId = viewerContext.userId

      if (!RuleBase.hasUserRules(safetyLevel)) {
        noUserRulesCounter.incr()
        Stitch.value(VisibilityResult(contentId = contentId, verdict = Allow))
      } else {
        if (viewerId.contains(user.id)) {
          viewerIsAuthorCounter.incr()

          Stitch.value(VisibilityResult(contentId = contentId, verdict = Allow))
        } else {
          vfEngineCounter.incr()

          val featureMap =
            visibilityLibrary.featureMapBuilder(
              Seq(
                viewerFeatures.forViewerContext(viewerContext),
                viewerSafeSearchFeatures.forViewerId(viewerId),
                relationshipFeatures.forAuthor(user, viewerId),
                authorFeatures.forAuthor(user),
                advancedFilteringFeatures.forViewerId(viewerId),
                searchFeatures.forSearchContext(userVisibilityFilteringContext.searchContext)
              )
            )

          visibilityLibrary.runRuleEngine(
            contentId,
            featureMap,
            viewerContext,
            safetyLevel
          )

        }
      }
    }
  }

  def Const(shouldDrop: Boolean): Type =
    (user, _, _, _) =>
      Stitch.value(
        VisibilityResult(
          contentId = UserId(user.id),
          verdict = if (shouldDrop) Drop(Unspecified) else Allow,
          finished = true
        )
      )
}
