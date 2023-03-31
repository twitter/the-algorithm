package com.twitter.product_mixer.core.functional_component.transformer

import com.twitter.product_mixer.core.feature.Feature
import com.twitter.product_mixer.core.feature.featuremap.FeatureMap
import com.twitter.product_mixer.core.model.common.identifier.TransformerIdentifier

/**
 * [[FeatureTransformer]] allow you to populate a [[com.twitter.product_mixer.core.feature.Feature]]s
 * value which is already available or can be derived without making an RPC.
 *
 * A [[FeatureTransformer]] transforms a given [[Inputs]] into a [[FeatureMap]].
 * The transformer must specify which [[com.twitter.product_mixer.core.feature.Feature]]s it will populate using the `features` field
 * and the returned [[FeatureMap]] must always have the specified [[com.twitter.product_mixer.core.feature.Feature]]s populated.
 *
 * @note Unlike [[com.twitter.product_mixer.core.functional_component.feature_hydrator.FeatureHydrator]] implementations,
 *       an exception thrown in a [[FeatureTransformer]] will not be added to the [[FeatureMap]] and will instead be
 *       bubble up to the calling pipeline's [[com.twitter.product_mixer.core.pipeline.pipeline_failure.PipelineFailureClassifier]].
 */
trait FeatureTransformer[-Inputs] extends Transformer[Inputs, FeatureMap] {

  def features: Set[Feature[_, _]]

  override val identifier: TransformerIdentifier

  /** Hydrates a [[FeatureMap]] for a given [[Inputs]] */
  override def transform(input: Inputs): FeatureMap
}
