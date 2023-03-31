package com.twitter.visibility.configapi.params

import com.twitter.timelines.configapi.Param

abstract class LabelSourceParam(override val default: Boolean) extends Param(default) {
  override val statName: String = s"LabelSourceParam/${this.getClass.getSimpleName}"
}

private[visibility] object LabelSourceParams {
  object FilterLabelsFromBot7174Param extends LabelSourceParam(false)

  object FilterTweetsSmyteAutomationParamA extends LabelSourceParam(false)
  object FilterTweetsSmyteAutomationParamB extends LabelSourceParam(false)
  object FilterTweetsSmyteAutomationParamAB extends LabelSourceParam(false)
}
