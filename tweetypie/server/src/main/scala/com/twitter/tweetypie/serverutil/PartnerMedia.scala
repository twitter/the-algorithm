package com.tw ter.t etyp e.serverut l

 mport com.tw ter.conf g.yaml.YamlMap
 mport scala.ut l.match ng.Regex

object Partner d a {
  def load(yamlMap: YamlMap): Seq[Regex] =
    (httpOrHttps(yamlMap) ++ httpOnly(yamlMap)).map(_.r)

  pr vate def httpOrHttps(yamlMap: YamlMap): Seq[Str ng] =
    yamlMap.str ngSeq("http_or_https").map("""^(?:https?\:\/\/)?""" + _)

  pr vate def httpOnly(yamlMap: YamlMap): Seq[Str ng] =
    yamlMap.str ngSeq("http_only").map("""^(?:http\:\/\/)?""" + _)
}
