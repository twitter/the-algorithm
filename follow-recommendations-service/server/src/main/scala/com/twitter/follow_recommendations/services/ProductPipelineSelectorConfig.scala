package com.tw ter.follow_recom ndat ons.serv ces

 mport com.tw ter.follow_recom ndat ons.common.models.D splayLocat on
 mport com.tw ter.t  l nes.conf gap .FSParam
 mport com.tw ter.t  l nes.conf gap .Param
 mport javax. nject.S ngleton

@S ngleton
class ProductP pel neSelectorConf g {
  pr vate val paramsMap: Map[D splayLocat on, DarkReadAndExpParams] = Map.empty

  def getDarkReadAndExpParams(
    d splayLocat on: D splayLocat on
  ): Opt on[DarkReadAndExpParams] = {
    paramsMap.get(d splayLocat on)
  }
}

case class DarkReadAndExpParams(darkReadParam: Param[Boolean], expParam: FSParam[Boolean])
