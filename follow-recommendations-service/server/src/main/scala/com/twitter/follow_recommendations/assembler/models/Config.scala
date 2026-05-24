package com.twitter.follow_recommendations.assembler.models

import com.twitter.stringcenter.client.core.ExternalString

case class HeaderConfig(title: TitleConfig)
case class TitleConfig(text: ExternalString)
case class FooterConfig(actionConfig: Option[ActionConfig])
case class ActionConfig(footerText: ExternalString, actionURL: String)
