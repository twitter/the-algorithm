package com.tw ter.v s b l y. nterfaces.not f cat ons

 mport com.tw ter.v s b l y.features.Feature
 mport com.tw ter.v s b l y.rules.Act on
 mport scala.collect on. mmutable.Set

sealed tra  Not f cat onsF lter ngResponse

case object Allow extends Not f cat onsF lter ngResponse

case class F ltered(act on: Act on) extends Not f cat onsF lter ngResponse

case class Fa led(features: Set[Feature[_]]) extends Not f cat onsF lter ngResponse
