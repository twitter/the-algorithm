package com.X.visibility.interfaces.media

import com.X.stitch.Stitch
import com.X.strato.client.{Client => StratoClient}
import com.X.util.Stopwatch
import com.X.visibility.VisibilityLibrary
import com.X.visibility.builder.VisibilityResult
import com.X.visibility.builder.users.ViewerFeatures
import com.X.visibility.builder.media.MediaFeatures
import com.X.visibility.builder.media.MediaMetadataFeatures
import com.X.visibility.builder.media.StratoMediaLabelMaps
import com.X.visibility.common.MediaMetadataSource
import com.X.visibility.common.MediaSafetyLabelMapSource
import com.X.visibility.common.UserSource
import com.X.visibility.features.FeatureMap
import com.X.visibility.generators.TombstoneGenerator
import com.X.visibility.models.ContentId.MediaId
import com.X.visibility.rules.EvaluationContext
import com.X.visibility.rules.providers.ProvidedEvaluationContext
import com.X.visibility.rules.utils.ShimUtils

object MediaVisibilityLibrary {
  type Type = MediaVisibilityRequest => Stitch[VisibilityResult]

  def apply(
    visibilityLibrary: VisibilityLibrary,
    userSource: UserSource,
    tombstoneGenerator: TombstoneGenerator,
    stratoClient: StratoClient,
  ): Type = {
    val libraryStatsReceiver = visibilityLibrary.statsReceiver
    val vfEngineCounter = libraryStatsReceiver.counter("vf_engine_requests")
    val vfLatencyOverallStat = libraryStatsReceiver.stat("vf_latency_overall")
    val vfLatencyStitchRunStat = libraryStatsReceiver.stat("vf_latency_stitch_run")

    val stratoClientStatsReceiver = libraryStatsReceiver.scope("strato")

    val mediaMetadataFeatures = new MediaMetadataFeatures(
      MediaMetadataSource.fromStrato(stratoClient, stratoClientStatsReceiver),
      libraryStatsReceiver)

    val mediaLabelMaps = new StratoMediaLabelMaps(
      MediaSafetyLabelMapSource.fromStrato(stratoClient, stratoClientStatsReceiver))
    val mediaFeatures = new MediaFeatures(mediaLabelMaps, libraryStatsReceiver)

    val viewerFeatures = new ViewerFeatures(userSource, libraryStatsReceiver)

    { r: MediaVisibilityRequest =>
      vfEngineCounter.incr()

      val contentId = MediaId(r.mediaKey.toStringKey)
      val languageCode = r.viewerContext.requestLanguageCode.getOrElse("en")

      val featureMap = visibilityLibrary.featureMapBuilder(
        Seq(
          viewerFeatures.forViewerContext(r.viewerContext),
          mediaFeatures.forGenericMediaKey(r.mediaKey),
          mediaMetadataFeatures.forGenericMediaKey(r.mediaKey),
        )
      )

      val evaluationContext = ProvidedEvaluationContext.injectRuntimeRulesIntoEvaluationContext(
        evaluationContext = EvaluationContext(
          r.safetyLevel,
          visibilityLibrary.getParams(r.viewerContext, r.safetyLevel),
          visibilityLibrary.statsReceiver)
      )

      val preFilteredFeatureMap =
        ShimUtils.preFilterFeatureMap(featureMap, r.safetyLevel, contentId, evaluationContext)

      val elapsed = Stopwatch.start()
      FeatureMap.resolve(preFilteredFeatureMap, libraryStatsReceiver).flatMap {
        resolvedFeatureMap =>
          vfLatencyStitchRunStat.add(elapsed().inMilliseconds)

          visibilityLibrary
            .runRuleEngine(
              contentId,
              resolvedFeatureMap,
              r.viewerContext,
              r.safetyLevel
            )
            .map(tombstoneGenerator(_, languageCode))
            .onSuccess(_ => vfLatencyOverallStat.add(elapsed().inMilliseconds))
      }
    }
  }
}
