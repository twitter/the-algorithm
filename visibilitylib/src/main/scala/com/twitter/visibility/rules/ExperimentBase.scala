package com.twitter.visibility.rules

import com.twitter.timelines.configapi.Params
import com.twitter.visibility.configapi.params.LabelSourceParam
import com.twitter.visibility.models.LabelSource

object ExperimentBase {
  val sourceToParamMap: Map[LabelSource, LabelSourceParam] = Map.empty

  final def shouldFilterForSource(params: Params, labelSourceOpt: Option[LabelSource]): Boolean =
    labelSourceOpt
      .fold(true)(source => ExperimentBase.sourceToParamMap.get(source).fold(true)(params.apply))
}
