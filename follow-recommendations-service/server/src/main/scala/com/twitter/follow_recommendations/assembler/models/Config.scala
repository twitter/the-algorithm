package com.twitter.follow_recommendations.assembler.models

import com.twitter.stringcenter.client.core.EtwitterternalString

case class HeaderConfig(title: TitleConfig)
case class TitleConfig(tetwittert: EtwitterternalString)
case class FooterConfig(actionConfig: Option[ActionConfig])
case class ActionConfig(footerTetwittert: EtwitterternalString, actionURL: String)
