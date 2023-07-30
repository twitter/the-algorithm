package com.X.product_mixer.core.model.marshalling.request

import com.X.timelines.configapi.{FeatureValue => ConfigApiFeatureValue}

case class DebugParams(
  featureOverrides: Option[Map[String, ConfigApiFeatureValue]],
  override val debugOptions: Option[DebugOptions])
    extends HasDebugOptions
