package com.twitter.visibility.configapi.params

import com.twitter.timelines.configapi.Param

abstract class GlobalParam[T](override val default: T) extends Param(default) {
  override val statName: String = s"GlobalParam/${this.getClass.getSimpleName}"
}

private[visibility] object GlobalParams {
  object EnableFetchingLabelMap extends GlobalParam(false)
}
