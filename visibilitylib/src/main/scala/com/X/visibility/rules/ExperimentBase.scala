package com.X.visibility.rules

import com.X.timelines.configapi.Params
import com.X.visibility.configapi.params.LabelSourceParam
import com.X.visibility.models.LabelSource

object ExperimentBase {
  val sourceToParamMap: Map[LabelSource, LabelSourceParam] = Map.empty

  final def shouldFilterForSource(params: Params, labelSourceOpt: Option[LabelSource]): Boolean = {
    labelSourceOpt
      .map { source =>
        val param = ExperimentBase.sourceToParamMap.get(source)
        param.map(params.apply).getOrElse(true)
      }
      .getOrElse(true)
  }
}
