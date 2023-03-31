package com.twitter.timelineranker.parameters.util

import com.twitter.servo.decider.DeciderGateBuilder
import com.twitter.servo.decider.DeciderKeyName
import com.twitter.timelines.configapi._
import com.twitter.timelines.configapi.decider.DeciderIntSpaceOverrideValue
import com.twitter.timelines.configapi.decider.DeciderSwitchOverrideValue
import com.twitter.timelines.configapi.decider.DeciderValueConverter
import com.twitter.timelines.configapi.decider.RecipientBuilder

class ConfigHelper(
  deciderByParam: Map[Param[_], DeciderKeyName],
  deciderGateBuilder: DeciderGateBuilder) {
  def createDeciderBasedBooleanOverrides(
    parameters: Seq[Param[Boolean]]
  ): Seq[OptionalOverride[Boolean]] = {
    parameters.map { parameter =>
      parameter.optionalOverrideValue(
        DeciderSwitchOverrideValue(
          feature = deciderGateBuilder.keyToFeature(deciderByParam(parameter)),
          recipientBuilder = RecipientBuilder.User,
          enabledValue = true,
          disabledValueOption = Some(false)
        )
      )
    }
  }

  def createDeciderBasedOverrides[T](
    parameters: Seq[Param[T] with DeciderValueConverter[T]]
  ): Seq[OptionalOverride[T]] = {
    parameters.map { parameter =>
      parameter.optionalOverrideValue(
        DeciderIntSpaceOverrideValue(
          feature = deciderGateBuilder.keyToFeature(deciderByParam(parameter)),
          conversion = parameter.convert
        )
      )
    }
  }
}
