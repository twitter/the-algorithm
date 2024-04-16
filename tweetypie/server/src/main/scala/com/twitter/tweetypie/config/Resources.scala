package com.tw ter.t etyp e.conf g

 mport com.tw ter.conf g.yaml.YamlMap
 mport com.tw ter.t etyp e.serverut l.Partner d a
 mport scala.ut l.match ng.Regex

/**
 *  lpers for load ng res ces bundled w h T etyp e.   load t m
 * through t  AP   n order to be able to un  test t  res ce
 * load ng code.
 */
object Res ces {
  def loadPartner d aRegexes(): Seq[Regex] =
    Partner d a.load(YamlMap.load("/partner_ d a.yml"))
}
