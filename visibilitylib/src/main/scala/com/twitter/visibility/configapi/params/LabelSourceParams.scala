package com.twitter.visibility.configapi.params

import com.twitter.timelines.configapi.Param

abstract class LabelSourceParam(override val default: Boolean) etwittertends Param(default) {
  override val statName: String = s"LabelSourceParam/${this.getClass.getSimpleName}"
}

private[visibility] object LabelSourceParams {
  object FilterLabelsFromBot7174Param etwittertends LabelSourceParam(false)

  object FilterTweetsSmyteAutomationParamA etwittertends LabelSourceParam(false)
  object FilterTweetsSmyteAutomationParamB etwittertends LabelSourceParam(false)
  object FilterTweetsSmyteAutomationParamAB etwittertends LabelSourceParam(false)
}
