package com.tw ter.v s b l y. nterfaces.not f cat ons

 mport com.tw ter.v s b l y.features.Feature
 mport com.tw ter.v s b l y.rules.Act on

tra  Not f cat onsPlatformF lter ngResponse

case object AllowVerd ct extends Not f cat onsPlatformF lter ngResponse

case class F lteredVerd ct(act on: Act on) extends Not f cat onsPlatformF lter ngResponse

case class Fa ledVerd ct(featuresMap: Map[Feature[_], Str ng])
    extends Not f cat onsPlatformF lter ngResponse
