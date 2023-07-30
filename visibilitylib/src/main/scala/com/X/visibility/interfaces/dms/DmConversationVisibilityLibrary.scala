package com.X.visibility.interfaces.dms

import com.X.servo.util.Gate
import com.X.stitch.Stitch
import com.X.strato.client.{Client => StratoClient}
import com.X.visibility.VisibilityLibrary
import com.X.visibility.builder.VisibilityResult
import com.X.visibility.builder.dms.DmConversationFeatures
import com.X.visibility.builder.users.AuthorFeatures
import com.X.visibility.common.UserSource
import com.X.visibility.common.dm_sources.DmConversationSource
import com.X.visibility.common.stitch.StitchHelpers
import com.X.visibility.features.FeatureMap
import com.X.visibility.models.ContentId.DmConversationId
import com.X.visibility.rules.Drop
import com.X.visibility.rules.EvaluationContext
import com.X.visibility.rules.Reason
import com.X.visibility.rules.RuleBase
import com.X.visibility.rules.providers.ProvidedEvaluationContext
import com.X.visibility.rules.utils.ShimUtils

object DmConversationVisibilityLibrary {
  type Type = DmConversationVisibilityRequest => Stitch[VisibilityResult]

  def apply(
    visibilityLibrary: VisibilityLibrary,
    stratoClient: StratoClient,
    userSource: UserSource,
    enableVfFeatureHydrationInShim: Gate[Unit] = Gate.False
  ): Type = {
    val libraryStatsReceiver = visibilityLibrary.statsReceiver
    val stratoClientStatsReceiver = visibilityLibrary.statsReceiver.scope("strato")
    val vfLatencyStatsReceiver = visibilityLibrary.statsReceiver.scope("vf_latency")
    val vfEngineCounter = libraryStatsReceiver.counter("vf_engine_requests")

    val dmConversationSource =
      DmConversationSource.fromStrato(stratoClient, stratoClientStatsReceiver)
    val authorFeatures = new AuthorFeatures(userSource, libraryStatsReceiver)
    val dmConversationFeatures = new DmConversationFeatures(dmConversationSource, authorFeatures)

    { req: DmConversationVisibilityRequest =>
      val dmConversationId = req.dmConversationId
      val contentId = DmConversationId(dmConversationId)
      val safetyLevel = req.safetyLevel

      if (!RuleBase.hasDmConversationRules(safetyLevel)) {
        Stitch.value(VisibilityResult(contentId = contentId, verdict = Drop(Reason.Unspecified)))
      } else {
        vfEngineCounter.incr()

        val viewerContext = req.viewerContext
        val viewerId = viewerContext.userId
        val isVfFeatureHydrationEnabled: Boolean =
          enableVfFeatureHydrationInShim()

        val featureMap = visibilityLibrary.featureMapBuilder(
          Seq(dmConversationFeatures.forDmConversationId(dmConversationId, viewerId)))

        val resp = if (isVfFeatureHydrationEnabled) {
          val evaluationContext = ProvidedEvaluationContext.injectRuntimeRulesIntoEvaluationContext(
            evaluationContext = EvaluationContext(
              safetyLevel,
              visibilityLibrary.getParams(viewerContext, safetyLevel),
              visibilityLibrary.statsReceiver)
          )

          val preFilteredFeatureMap =
            ShimUtils.preFilterFeatureMap(featureMap, safetyLevel, contentId, evaluationContext)

          FeatureMap.resolve(preFilteredFeatureMap, libraryStatsReceiver).flatMap {
            resolvedFeatureMap =>
              visibilityLibrary
                .runRuleEngine(
                  contentId,
                  resolvedFeatureMap,
                  viewerContext,
                  safetyLevel
                )
          }
        } else {
          visibilityLibrary
            .runRuleEngine(
              contentId,
              featureMap,
              viewerContext,
              safetyLevel
            )
        }

        StitchHelpers.profileStitch(resp, Seq(vfLatencyStatsReceiver))
      }
    }
  }
}
