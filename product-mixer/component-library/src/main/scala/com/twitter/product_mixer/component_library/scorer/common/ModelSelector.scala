package com.twitter.product_mixer.component_library.scorer.common

import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.timelines.configapi.Param

/**
 * Selector for choosing which Model ID/Name to use when calling an underlying ML Model Service.
 */
trait ModelSelector[-Query <: PipelineQuery] {
  def apply(query: Query): Option[String]
}

/**
 * Simple Model ID Selector that chooses model based off of a Param object.
 * @param param ConfigAPI Param that decides the model id.
 */
case class ParamModelSelector[Query <: PipelineQuery](param: Param[String])
    extends ModelSelector[Query] {
  override def apply(query: Query): Option[String] = Some(query.params(param))
}

/**
 * Static Selector that chooses the same model name always
 * @param modelName The model name to use.
 */
case class StaticModelSelector(modelName: String) extends ModelSelector[PipelineQuery] {
  override def apply(query: PipelineQuery): Option[String] = Some(modelName)
}
