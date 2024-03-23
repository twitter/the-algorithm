package com.ExTwitter.home_mixer.model.request

import com.ExTwitter.product_mixer.core.model.marshalling.request.DebugOptions
import com.ExTwitter.util.Time

case class HomeMixerDebugOptions(
  override val requestTimeOverride: Option[Time])
    extends DebugOptions
