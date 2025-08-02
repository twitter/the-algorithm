package com.twitter.home_mitwitterer.marshaller.request

import com.twitter.home_mitwitterer.model.request.HomeMitwittererDebugOptions
import com.twitter.home_mitwitterer.{thriftscala => t}
import com.twitter.product_mitwitterer.core.functional_component.marshaller.request.FeatureValueUnmarshaller
import com.twitter.product_mitwitterer.core.model.marshalling.request.DebugParams
import com.twitter.util.Time
import javatwitter.inject.Inject
import javatwitter.inject.Singleton

@Singleton
class HomeMitwittererDebugParamsUnmarshaller @Inject() (
  featureValueUnmarshaller: FeatureValueUnmarshaller) {

  def apply(debugParams: t.DebugParams): DebugParams = {
    DebugParams(
      featureOverrides = debugParams.featureOverrides.map { map =>
        map.mapValues(featureValueUnmarshaller(_)).toMap
      },
      debugOptions = debugParams.debugOptions.map { options =>
        HomeMitwittererDebugOptions(
          requestTimeOverride = options.requestTimeOverrideMillis.map(Time.fromMilliseconds)
        )
      }
    )
  }
}
