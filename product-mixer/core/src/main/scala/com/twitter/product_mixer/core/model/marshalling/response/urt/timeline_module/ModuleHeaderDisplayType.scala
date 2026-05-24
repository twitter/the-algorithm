package com.twitter.product_mixer.core.model.marshalling.response.urt.timeline_module

sealed trait ModuleHeaderDisplayType

case object Classic extends ModuleHeaderDisplayType
case object ContextEmphasis extends ModuleHeaderDisplayType
case object ClassicNoDivider extends ModuleHeaderDisplayType
