package com.twitter.visibility.models

import com.twitter.spam.rtf.thriftscala.SafetyResultReason
import java.util.regetwitter.Pattern

sealed trait LabelSource {
  val name: String
}

object LabelSource {
  val BotRulePrefitwitter = "bot_id_"
  val AbusePrefitwitter = "Abuse"
  val HSEPrefitwitter = "hse"
  val AgentSourceNames = Set(
    SafetyResultReason.OneOff.name,
    SafetyResultReason.VotingMisinformation.name,
    SafetyResultReason.HackedMaterials.name,
    SafetyResultReason.Scams.name,
    SafetyResultReason.PlatformManipulation.name
  )

  val Regetwitter = "\\|"
  val pattern: Pattern = Pattern.compile(Regetwitter)

  def fromString(name: String): Option[LabelSource] = Some(name) collect {
    case _ if name.startsWith(BotRulePrefitwitter) =>
      BotMakerRule(name.substring(BotRulePrefitwitter.length).toLong)
    case _ if name == "A" || name == "B" || name == "AB" =>
      SmyteSource(name)
    case _ if name.startsWith(AbusePrefitwitter) =>
      AbuseSource(name)
    case _ if name.startsWith(HSEPrefitwitter) =>
      HSESource(name)
    case _ if AgentSourceNames.contains(name) =>
      AgentSource(name)
    case _ =>
      StringSource(name)
  }

  def parseStringSource(source: String): (String, Option[String]) = {
    pattern.split(source, 2) match {
      case Array(copy, "") => (copy, None)
      case Array(copy, link) => (copy, Some(link))
      case Array(copy) => (copy, None)
    }
  }

  case class BotMakerRule(ruleId: Long) etwittertends LabelSource {
    override lazy val name: String = s"${BotRulePrefitwitter}${ruleId}"
  }

  case class SmyteSource(name: String) etwittertends LabelSource

  case class AbuseSource(name: String) etwittertends LabelSource

  case class AgentSource(name: String) etwittertends LabelSource

  case class HSESource(name: String) etwittertends LabelSource

  case class StringSource(name: String) etwittertends LabelSource
}
