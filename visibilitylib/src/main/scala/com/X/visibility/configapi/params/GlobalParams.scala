package com.X.visibility.configapi.params

import com.X.timelines.configapi.Param

abstract class GlobalParam[T](override val default: T) extends Param(default) {
  override val statName: String = s"GlobalParam/${this.getClass.getSimpleName}"
}

private[visibility] object GlobalParams {
  object EnableFetchingLabelMap extends GlobalParam(false)
}
