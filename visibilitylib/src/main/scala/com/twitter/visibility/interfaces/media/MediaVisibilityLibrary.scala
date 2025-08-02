package com.twitter.visibility.interfaces.media

import com.twitter.stitch.Stitch
import com.twitter.strato.client.{Client => StratoClient}
import com.twitter.util.Stopwatch
import com.twitter.visibility.VisibilityLibrary
import com.twitter.visibility.builder.VisibilityResult
import com.twitter.visibility.builder.users.ViewerFeatures
import com.twitter.visibility.builder.media.MediaFeatures
import com.twitter.visibility.builder.media.MediaMetadataFeatures
import com.twitter.visibility.builder.media.StratoMediaLabelMaps
import com.twitter.visibility.common.MediaMetadataSource
import com.twitter.visibility.common.MediaSafetyLabelMapSource
import com.twitter.visibility.common.UserSource
import com.twitter.visibility.features.FeatureMap
import com.twitter.visibility.generators.TombstoneGenerator
import com.twitter.visibility.models.ContentId.MediaId
import com.twitter.visibility.rules.EvaluationContetwittert
import com.twitter.visibility.rules.providers.ProvidedEvaluationContetwittert
import com.twitter.visibility.rules.utils.ShimUtils

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
      val languageCode = r.viewerContetwittert.requestLanguageCode.getOrElse("en")

      val featureMap = visibilityLibrary.featureMapBuilder(
        Seq(
          viewerFeatures.forViewerContetwittert(r.viewerContetwittert),
          mediaFeatures.forGenericMediaKey(r.mediaKey),
          mediaMetadataFeatures.forGenericMediaKey(r.mediaKey),
        )
      )

      val evaluationContetwittert = ProvidedEvaluationContetwittert.injectRuntimeRulesIntoEvaluationContetwittert(
        evaluationContetwittert = EvaluationContetwittert(
          r.safetyLevel,
          visibilityLibrary.getParams(r.viewerContetwittert, r.safetyLevel),
          visibilityLibrary.statsReceiver)
      )

      val preFilteredFeatureMap =
        ShimUtils.preFilterFeatureMap(featureMap, r.safetyLevel, contentId, evaluationContetwittert)

      val elapsed = Stopwatch.start()
      FeatureMap.resolve(preFilteredFeatureMap, libraryStatsReceiver).flatMap {
        resolvedFeatureMap =>
          vfLatencyStitchRunStat.add(elapsed().inMilliseconds)

          visibilityLibrary
            .runRuleEngine(
              contentId,
              resolvedFeatureMap,
              r.viewerContetwittert,
              r.safetyLevel
            )
            .map(tombstoneGenerator(_, languageCode))
            .onSuccess(_ => vfLatencyOverallStat.add(elapsed().inMilliseconds))
      }
    }
  }
}
