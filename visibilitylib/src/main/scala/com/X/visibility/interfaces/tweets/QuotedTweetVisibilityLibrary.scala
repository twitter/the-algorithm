package com.X.visibility.interfaces.tweets

import com.X.decider.Decider
import com.X.servo.util.Gate
import com.X.stitch.Stitch
import com.X.visibility.VisibilityLibrary
import com.X.visibility.builder.VisibilityResult
import com.X.visibility.builder.users.AuthorFeatures
import com.X.visibility.builder.users.QuotedTweetFeatures
import com.X.visibility.builder.users.RelationshipFeatures
import com.X.visibility.builder.users.ViewerFeatures
import com.X.visibility.common.UserRelationshipSource
import com.X.visibility.common.UserSource
import com.X.visibility.configapi.configs.VisibilityDeciderGates
import com.X.visibility.features.FeatureMap
import com.X.visibility.models.ContentId.QuotedTweetRelationship
import com.X.visibility.models.SafetyLevel
import com.X.visibility.models.UserUnavailableStateEnum
import com.X.visibility.models.ViewerContext
import com.X.visibility.rules.Drop
import com.X.visibility.rules.EvaluationContext
import com.X.visibility.rules.Reason.AuthorBlocksViewer
import com.X.visibility.rules.Reason.DeactivatedAuthor
import com.X.visibility.rules.Reason.ErasedAuthor
import com.X.visibility.rules.Reason.OffboardedAuthor
import com.X.visibility.rules.Reason.ProtectedAuthor
import com.X.visibility.rules.Reason.SuspendedAuthor
import com.X.visibility.rules.Reason.ViewerBlocksAuthor
import com.X.visibility.rules.Reason.ViewerHardMutedAuthor
import com.X.visibility.rules.Reason.ViewerMutesAuthor
import com.X.visibility.rules.providers.ProvidedEvaluationContext
import com.X.visibility.rules.utils.ShimUtils

case class TweetAndAuthor(tweetId: Long, authorId: Long)

case class QuotedTweetVisibilityRequest(
  quotedTweet: TweetAndAuthor,
  outerTweet: TweetAndAuthor,
  viewerContext: ViewerContext,
  safetyLevel: SafetyLevel)

object QuotedTweetVisibilityLibrary {

  type Type = QuotedTweetVisibilityRequest => Stitch[VisibilityResult]

  def apply(
    visibilityLibrary: VisibilityLibrary,
    userSource: UserSource,
    userRelationshipSource: UserRelationshipSource,
    decider: Decider,
    userStateVisibilityLibrary: UserUnavailableStateVisibilityLibrary.Type,
    enableVfFeatureHydration: Gate[Unit] = Gate.False
  ): Type = {
    val libraryStatsReceiver = visibilityLibrary.statsReceiver
    val visibilityDeciderGates = VisibilityDeciderGates(decider)
    val vfEngineCounter = libraryStatsReceiver.counter("vf_engine_requests")

    {
      case QuotedTweetVisibilityRequest(quotedTweet, outerTweet, viewerContext, safetyLevel) =>
        vfEngineCounter.incr()
        val contentId = QuotedTweetRelationship(
          outer = outerTweet.tweetId,
          inner = quotedTweet.tweetId
        )

        val innerAuthorId = quotedTweet.authorId
        val outerAuthorId = outerTweet.authorId
        val viewerId = viewerContext.userId
        val isFeatureHydrationInShimEnabled = enableVfFeatureHydration()

        val authorFeatures = new AuthorFeatures(userSource, libraryStatsReceiver)
        val viewerFeatures = new ViewerFeatures(userSource, libraryStatsReceiver)
        val relationshipFeatures =
          new RelationshipFeatures(userRelationshipSource, libraryStatsReceiver)
        val quotedTweetFeatures =
          new QuotedTweetFeatures(relationshipFeatures, libraryStatsReceiver)

        val featureMap = visibilityLibrary.featureMapBuilder(
          Seq(
            viewerFeatures.forViewerContext(viewerContext),
            authorFeatures.forAuthorId(innerAuthorId),
            relationshipFeatures.forAuthorId(innerAuthorId, viewerId),
            quotedTweetFeatures.forOuterAuthor(outerAuthorId, innerAuthorId)
          )
        )

        val resp = if (isFeatureHydrationInShimEnabled) {
          val evaluationContext = ProvidedEvaluationContext.injectRuntimeRulesIntoEvaluationContext(
            evaluationContext = EvaluationContext(
              SafetyLevel.QuotedTweetRules,
              visibilityLibrary.getParams(viewerContext, SafetyLevel.QuotedTweetRules),
              visibilityLibrary.statsReceiver)
          )

          val preFilteredFeatureMap =
            ShimUtils.preFilterFeatureMap(
              featureMap,
              SafetyLevel.QuotedTweetRules,
              contentId,
              evaluationContext)

          FeatureMap.resolve(preFilteredFeatureMap, libraryStatsReceiver).flatMap {
            resolvedFeatureMap =>
              visibilityLibrary
                .runRuleEngine(
                  contentId,
                  resolvedFeatureMap,
                  viewerContext,
                  SafetyLevel.QuotedTweetRules
                )
          }
        } else {
          visibilityLibrary
            .runRuleEngine(
              contentId,
              featureMap,
              viewerContext,
              SafetyLevel.QuotedTweetRules
            )
        }

        resp.flatMap { visResult =>
          val userStateOpt = visResult.verdict match {
            case Drop(DeactivatedAuthor, _) => Some(UserUnavailableStateEnum.Deactivated)
            case Drop(OffboardedAuthor, _) => Some(UserUnavailableStateEnum.Offboarded)
            case Drop(ErasedAuthor, _) => Some(UserUnavailableStateEnum.Erased)
            case Drop(ProtectedAuthor, _) => Some(UserUnavailableStateEnum.Protected)
            case Drop(SuspendedAuthor, _) => Some(UserUnavailableStateEnum.Suspended)
            case Drop(AuthorBlocksViewer, _) => Some(UserUnavailableStateEnum.AuthorBlocksViewer)
            case Drop(ViewerBlocksAuthor, _) => Some(UserUnavailableStateEnum.ViewerBlocksAuthor)
            case Drop(ViewerMutesAuthor, _) => Some(UserUnavailableStateEnum.ViewerMutesAuthor)
            case Drop(ViewerHardMutedAuthor, _) => Some(UserUnavailableStateEnum.ViewerMutesAuthor)
            case _ => None
          }

          userStateOpt
            .map(userState =>
              userStateVisibilityLibrary(
                UserUnavailableStateVisibilityRequest(
                  safetyLevel,
                  quotedTweet.tweetId,
                  viewerContext,
                  userState,
                  isRetweet = false,
                  isInnerQuotedTweet = true,
                ))).getOrElse(Stitch.value(visResult))
        }
    }
  }
}
