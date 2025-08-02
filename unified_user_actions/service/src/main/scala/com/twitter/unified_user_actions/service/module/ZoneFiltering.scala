package com.twitter.unified_user_actions.service.module

import com.twitter.kafka.client.headers.ATLA
import com.twitter.kafka.client.headers.Implicits._
import com.twitter.kafka.client.headers.PDtwitterA
import com.twitter.kafka.client.headers.Zone
import org.apache.kafka.clients.consumer.ConsumerRecord

object ZoneFiltering {
  def zoneMapping(zone: String): Zone = zone.toLowerCase match {
    case "atla" => ATLA
    case "pdtwittera" => PDtwitterA
    case _ =>
      throw new IllegalArgumentEtwitterception(
        s"zone must be provided and must be one of [atla,pdtwittera], provided $zone")
  }

  def localDCFiltering[K, V](event: ConsumerRecord[K, V], localZone: Zone): Boolean =
    event.headers().isLocalZone(localZone)

  def noFiltering[K, V](event: ConsumerRecord[K, V], localZone: Zone): Boolean = true
}
