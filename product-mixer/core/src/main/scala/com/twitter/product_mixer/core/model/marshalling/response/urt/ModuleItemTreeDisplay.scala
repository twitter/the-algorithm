package com.twitter.product_mixer.core.model.marshalling.response.urt

import com.twitter.product_mixer.core.model.marshalling.response.urt.timeline_module.ModuleDisplayType

case class ModuleItemTreeDisplay(
  parentModuleEntryItemId: Option[String],
  indentFromParent: Option[Boolean],
  displayType: Option[ModuleDisplayType],
  isAnchorChild: Option[Boolean])
