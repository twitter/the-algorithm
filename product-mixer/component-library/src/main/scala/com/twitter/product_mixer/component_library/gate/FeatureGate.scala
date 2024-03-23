package com.ExTwitter.product_mixer.component_library.gate

import com.ExTwitter.product_mixer.core.feature.Feature
import com.ExTwitter.product_mixer.core.feature.featuremap.MissingFeatureException
import com.ExTwitter.product_mixer.core.functional_component.gate.Gate
import com.ExTwitter.product_mixer.core.functional_component.gate.GateResult
import com.ExTwitter.product_mixer.core.model.common.identifier.GateIdentifier
import com.ExTwitter.product_mixer.core.pipeline.PipelineQuery
import com.ExTwitter.product_mixer.core.pipeline.pipeline_failure.MisconfiguredFeatureMapFailure
import com.ExTwitter.product_mixer.core.pipeline.pipeline_failure.PipelineFailure
import com.ExTwitter.stitch.Stitch
import com.ExTwitter.util.Return
import com.ExTwitter.util.Throw

trait ShouldContinue[Value] {

  /** Given the [[Feature]] value, returns whether the execution should continue */
  def apply(featureValue: Value): Boolean

  /** If the [[Feature]] is a failure, use this value */
  def onFailedFeature(t: Throwable): GateResult = GateResult.Stop

  /**
   * If the [[Feature]], or [[com.ExTwitter.product_mixer.core.feature.featuremap.FeatureMap]],
   * is missing use this value
   */
  def onMissingFeature: GateResult = GateResult.Stop
}

object FeatureGate {

  def fromFeature(
    feature: Feature[_, Boolean]
  ): FeatureGate[Boolean] =
    FeatureGate.fromFeature(GateIdentifier(feature.toString), feature)

  def fromNegatedFeature(
    feature: Feature[_, Boolean]
  ): FeatureGate[Boolean] =
    FeatureGate.fromNegatedFeature(GateIdentifier(feature.toString), feature)

  def fromFeature(
    gateIdentifier: GateIdentifier,
    feature: Feature[_, Boolean]
  ): FeatureGate[Boolean] =
    FeatureGate[Boolean](gateIdentifier, feature, identity)

  def fromNegatedFeature(
    gateIdentifier: GateIdentifier,
    feature: Feature[_, Boolean]
  ): FeatureGate[Boolean] =
    FeatureGate[Boolean](gateIdentifier, feature, !identity(_))

}

/**
 * A [[Gate]] that is actuated based upon the value of the provided feature
 */
case class FeatureGate[Value](
  gateIdentifier: GateIdentifier,
  feature: Feature[_, Value],
  continue: ShouldContinue[Value])
    extends Gate[PipelineQuery] {

  override val identifier: GateIdentifier = gateIdentifier

  override def shouldContinue(query: PipelineQuery): Stitch[Boolean] = {
    Stitch
      .value(
        query.features.map(_.getTry(feature)) match {
          case Some(Return(value)) => continue(value)
          case Some(Throw(_: MissingFeatureException)) => continue.onMissingFeature.continue
          case Some(Throw(t)) => continue.onFailedFeature(t).continue
          case None =>
            throw PipelineFailure(
              MisconfiguredFeatureMapFailure,
              "Expected a FeatureMap to be present but none was found, ensure that your" +
                "PipelineQuery has a FeatureMap configured before gating on Feature values"
            )
        }
      )
  }
}
