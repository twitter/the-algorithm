package com.twitter.home_mixer.marshaller.request

import com.twitter.home_mixer.model.request.HomeMixerDebugOptions
import com.twitter.home_mixer.{thriftscala => t}
import com.twitter.product_mixer.core.functional_component.marshaller.request.FeatureValueUnmarshaller
import com.twitter.product_mixer.core.model.marshalling.request.DebugParams
import com.twitter.util.Time
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HomeMixerDebugParamsUnmarshaller @Inject() (
  featureValueUnmarshaller: FeatureValueUnmarshaller) {

  def apply(debugParams: t.DebugParams): DebugParams = {
    DebugParams(
      featureOverrides = debugParams.featureOverrides.map { map =>
        map.mapValues(featureValueUnmarshaller(_)).toMap
      },
      debugOptions = debugParams.debugOptions.map { options =>
        HomeMixerDebugOptions(
          requestTimeOverride = options.requestTimeOverrideMillis.map(Time.fromMilliseconds),
          showIntermediateLogs = options.showIntermediateLogs.orElse(Some(false))
        )
      }
    )
  }
}
