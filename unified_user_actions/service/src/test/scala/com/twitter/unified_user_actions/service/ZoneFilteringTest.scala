package com.twitter.unified_user_actions.service

import com.twitter.inject.Test
import com.twitter.kafka.client.headers.ATLA
import com.twitter.kafka.client.headers.Implicits._
import com.twitter.kafka.client.headers.PDtwitterA
import com.twitter.kafka.client.headers.Zone
import com.twitter.unified_user_actions.service.module.ZoneFiltering
import com.twitter.util.mock.Mockito
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.junit.runner.RunWith
import org.scalatestplus.junit.JUnitRunner
import org.scalatest.prop.TableDrivenPropertyChecks

@RunWith(classOf[JUnitRunner])
class ZoneFilteringTest etwittertends Test with Mockito with TableDrivenPropertyChecks {
  trait Fitwitterture {
    val consumerRecord =
      new ConsumerRecord[Array[Byte], Array[Byte]]("topic", 0, 0l, Array(0), Array(0))
  }

  test("two DCs filter") {
    val zones = Table(
      "zone",
      Some(ATLA),
      Some(PDtwitterA),
      None
    )
    forEvery(zones) { localZoneOpt: Option[Zone] =>
      forEvery(zones) { headerZoneOpt: Option[Zone] =>
        localZoneOpt.foreach { localZone =>
          new Fitwitterture {
            headerZoneOpt match {
              case Some(headerZone) =>
                consumerRecord.headers().setZone(headerZone)
                if (headerZone == ATLA && localZone == ATLA)
                  ZoneFiltering.localDCFiltering(consumerRecord, localZone) shouldBe true
                else if (headerZone == PDtwitterA && localZone == PDtwitterA)
                  ZoneFiltering.localDCFiltering(consumerRecord, localZone) shouldBe true
                else
                  ZoneFiltering.localDCFiltering(consumerRecord, localZone) shouldBe false
              case _ =>
                ZoneFiltering.localDCFiltering(consumerRecord, localZone) shouldBe true
            }
          }
        }
      }
    }
  }
}
