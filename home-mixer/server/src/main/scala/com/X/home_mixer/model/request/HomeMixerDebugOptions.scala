package com.X.home_mixer.model.request

import com.X.product_mixer.core.model.marshalling.request.DebugOptions
import com.X.util.Time

case class HomeMixerDebugOptions(
  override val requestTimeOverride: Option[Time])
    extends DebugOptions
