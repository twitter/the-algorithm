package com.ExTwitter.home_mixer.functional_component.gate

import com.ExTwitter.conversions.DurationOps._
import com.ExTwitter.product_mixer.core.feature.Feature
import com.ExTwitter.product_mixer.core.functional_component.gate.Gate
import com.ExTwitter.product_mixer.core.model.common.identifier.GateIdentifier
import com.ExTwitter.product_mixer.core.pipeline.PipelineQuery
import com.ExTwitter.stitch.Stitch
import com.ExTwitter.timelinemixer.clients.manhattan.DismissInfo
import com.ExTwitter.timelineservice.suggests.thriftscala.SuggestType
import com.ExTwitter.util.Duration

object DismissFatigueGate {
  // how long a dismiss action from user needs to be respected
  val DefaultBaseDismissDuration = 7.days
  val MaximumDismissalCountMultiplier = 4
}

case class DismissFatigueGate(
  suggestType: SuggestType,
  dismissInfoFeature: Feature[PipelineQuery, Map[SuggestType, Option[DismissInfo]]],
  baseDismissDuration: Duration = DismissFatigueGate.DefaultBaseDismissDuration,
) extends Gate[PipelineQuery] {

  override val identifier: GateIdentifier = GateIdentifier("DismissFatigue")

  override def shouldContinue(query: PipelineQuery): Stitch[Boolean] = {
    val dismissInfoMap = query.features.map(
      _.getOrElse(dismissInfoFeature, Map.empty[SuggestType, Option[DismissInfo]]))

    val isVisible = dismissInfoMap
      .flatMap(_.get(suggestType))
      .flatMap(_.map { info =>
        val currentDismissalDuration = query.queryTime.since(info.lastDismissed)
        val targetDismissalDuration = dismissDurationForCount(info.count, baseDismissDuration)

        currentDismissalDuration > targetDismissalDuration
      }).getOrElse(true)
    Stitch.value(isVisible)
  }

  private def dismissDurationForCount(
    dismissCount: Int,
    dismissDuration: Duration
  ): Duration =
    // limit to maximum dismissal duration
    dismissDuration * Math.min(dismissCount, DismissFatigueGate.MaximumDismissalCountMultiplier)
}
