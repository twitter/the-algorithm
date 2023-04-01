package com.twitter.home_mixer.functional_component.gate

import com.twitter.product_mixer.core.feature.Feature
import com.twitter.product_mixer.core.functional_component.gate.Gate
import com.twitter.product_mixer.core.model.common.identifier.GateIdentifier
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.stitch.Stitch
import scala.reflect.runtime.universe._

case class NonEmptySeqFeatureGate[T: TypeTag](
  feature: Feature[PipelineQuery, Seq[T]])
    extends Gate[PipelineQuery] {

  override val identifier: GateIdentifier = GateIdentifier(s"NonEmptySeq$feature")

  override def shouldContinue(query: PipelineQuery): Stitch[Boolean] =
    Stitch.value(query.features.exists(_.get(feature).nonEmpty))
}
