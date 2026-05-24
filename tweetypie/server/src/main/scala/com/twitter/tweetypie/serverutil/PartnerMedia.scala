package com.twitter.tweetypie.serverutil

import com.twitter.config.yaml.YamlMap
import scala.util.matching.Regex

object PartnerMedia {
  def load(yamlMap: YamlMap): Seq[Regex] =
    (httpOrHttps(yamlMap) ++ httpOnly(yamlMap)).map(_.r)

  private def httpOrHttps(yamlMap: YamlMap): Seq[String] =
    yamlMap.stringSeq("http_or_https").map("""^(?:https?\:\/\/)?""" + _)

  private def httpOnly(yamlMap: YamlMap): Seq[String] =
    yamlMap.stringSeq("http_only").map("""^(?:http\:\/\/)?""" + _)
}
