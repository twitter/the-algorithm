package com.tw ter.un f ed_user_act ons.serv ce.module

 mport com.tw ter.kafka.cl ent. aders.ATLA
 mport com.tw ter.kafka.cl ent. aders. mpl c s._
 mport com.tw ter.kafka.cl ent. aders.PDXA
 mport com.tw ter.kafka.cl ent. aders.Zone
 mport org.apac .kafka.cl ents.consu r.Consu rRecord

object ZoneF lter ng {
  def zoneMapp ng(zone: Str ng): Zone = zone.toLo rCase match {
    case "atla" => ATLA
    case "pdxa" => PDXA
    case _ =>
      throw new  llegalArgu ntExcept on(
        s"zone must be prov ded and must be one of [atla,pdxa], prov ded $zone")
  }

  def localDCF lter ng[K, V](event: Consu rRecord[K, V], localZone: Zone): Boolean =
    event. aders(). sLocalZone(localZone)

  def noF lter ng[K, V](event: Consu rRecord[K, V], localZone: Zone): Boolean = true
}
