package com.twitter.product_mitwitterer.core.model.marshalling.request

import com.twitter.timelines.configapi.{FeatureValue => ConfigApiFeatureValue}

case class DebugParams(
  featureOverrides: Option[Map[String, ConfigApiFeatureValue]],
  override val debugOptions: Option[DebugOptions])
    etwittertends HasDebugOptions
