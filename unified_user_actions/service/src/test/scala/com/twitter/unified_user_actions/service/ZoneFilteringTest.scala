package com.tw ter.un f ed_user_act ons.serv ce

 mport com.tw ter. nject.Test
 mport com.tw ter.kafka.cl ent. aders.ATLA
 mport com.tw ter.kafka.cl ent. aders. mpl c s._
 mport com.tw ter.kafka.cl ent. aders.PDXA
 mport com.tw ter.kafka.cl ent. aders.Zone
 mport com.tw ter.un f ed_user_act ons.serv ce.module.ZoneF lter ng
 mport com.tw ter.ut l.mock.Mock o
 mport org.apac .kafka.cl ents.consu r.Consu rRecord
 mport org.jun .runner.RunW h
 mport org.scalatestplus.jun .JUn Runner
 mport org.scalatest.prop.TableDr venPropertyC cks

@RunW h(classOf[JUn Runner])
class ZoneF lter ngTest extends Test w h Mock o w h TableDr venPropertyC cks {
  tra  F xture {
    val consu rRecord =
      new Consu rRecord[Array[Byte], Array[Byte]]("top c", 0, 0l, Array(0), Array(0))
  }

  test("two DCs f lter") {
    val zones = Table(
      "zone",
      So (ATLA),
      So (PDXA),
      None
    )
    forEvery(zones) { localZoneOpt: Opt on[Zone] =>
      forEvery(zones) {  aderZoneOpt: Opt on[Zone] =>
        localZoneOpt.foreach { localZone =>
          new F xture {
             aderZoneOpt match {
              case So ( aderZone) =>
                consu rRecord. aders().setZone( aderZone)
                 f ( aderZone == ATLA && localZone == ATLA)
                  ZoneF lter ng.localDCF lter ng(consu rRecord, localZone) shouldBe true
                else  f ( aderZone == PDXA && localZone == PDXA)
                  ZoneF lter ng.localDCF lter ng(consu rRecord, localZone) shouldBe true
                else
                  ZoneF lter ng.localDCF lter ng(consu rRecord, localZone) shouldBe false
              case _ =>
                ZoneF lter ng.localDCF lter ng(consu rRecord, localZone) shouldBe true
            }
          }
        }
      }
    }
  }
}
