package com.X.timelineranker.parameters.util

import com.X.servo.decider.DeciderGateBuilder
import com.X.servo.decider.DeciderKeyName
import com.X.timelines.configapi._
import com.X.timelines.configapi.decider.DeciderIntSpaceOverrideValue
import com.X.timelines.configapi.decider.DeciderSwitchOverrideValue
import com.X.timelines.configapi.decider.DeciderValueConverter
import com.X.timelines.configapi.decider.RecipientBuilder

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
