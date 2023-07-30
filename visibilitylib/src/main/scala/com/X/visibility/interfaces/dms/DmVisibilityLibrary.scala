package com.X.visibility.interfaces.dms

import com.X.servo.util.Gate
import com.X.stitch.Stitch
import com.X.strato.client.{Client => StratoClient}
import com.X.visibility.VisibilityLibrary
import com.X.visibility.builder.VisibilityResult
import com.X.visibility.builder.users.AuthorFeatures
import com.X.visibility.common.DmId
import com.X.visibility.common.UserId
import com.X.visibility.common.UserSource
import com.X.visibility.features.FeatureMap
import com.X.visibility.models.ContentId.{DmId => DmContentId}
import com.X.visibility.models.SafetyLevel.DirectMessages
import com.X.visibility.models.SafetyLevel
import com.X.visibility.models.ViewerContext
import com.X.visibility.rules.Drop
import com.X.visibility.rules.Reason.DeactivatedAuthor
import com.X.visibility.rules.Reason.ErasedAuthor
import com.X.visibility.rules.Reason.Nsfw

object DmVisibilityLibrary {
  type Type = DmVisibilityRequest => Stitch[DmVisibilityResponse]

  case class DmVisibilityRequest(
    dmId: DmId,
    dmAuthorUserId: UserId,
    viewerContext: ViewerContext)

  case class DmVisibilityResponse(isMessageNsfw: Boolean)

  val DefaultSafetyLevel: SafetyLevel = DirectMessages

  def apply(
    visibilityLibrary: VisibilityLibrary,
    stratoClient: StratoClient,
    userSource: UserSource,
    enableVfFeatureHydrationInShim: Gate[Unit] = Gate.False
  ): Type = {
    val libraryStatsReceiver = visibilityLibrary.statsReceiver
    val vfEngineCounter = libraryStatsReceiver.counter("vf_engine_requests")

    val authorFeatures = new AuthorFeatures(userSource, libraryStatsReceiver)

    { r: DmVisibilityRequest =>
      vfEngineCounter.incr()

      val contentId = DmContentId(r.dmId)
      val dmAuthorUserId = r.dmAuthorUserId
      val isVfFeatureHydrationEnabled = enableVfFeatureHydrationInShim()

      val featureMap =
        visibilityLibrary.featureMapBuilder(
          Seq(authorFeatures.forAuthorId(dmAuthorUserId))
        )

      val resp = if (isVfFeatureHydrationEnabled) {
        FeatureMap.resolve(featureMap, libraryStatsReceiver).flatMap { resolvedFeatureMap =>
          visibilityLibrary.runRuleEngine(
            contentId,
            resolvedFeatureMap,
            r.viewerContext,
            DefaultSafetyLevel
          )
        }
      } else {
        visibilityLibrary
          .runRuleEngine(
            contentId,
            featureMap,
            r.viewerContext,
            DefaultSafetyLevel
          )
      }

      resp.map(buildResponse)
    }
  }

  private[this] def buildResponse(visibilityResult: VisibilityResult) =
    visibilityResult.verdict match {
      case Drop(Nsfw | ErasedAuthor | DeactivatedAuthor, _) =>
        DmVisibilityResponse(isMessageNsfw = true)
      case _ =>
        DmVisibilityResponse(isMessageNsfw = false)
    }

}
