package com.tw ter.representat on_manager.common

 mport com.tw ter.dec der.Dec der
 mport com.tw ter.dec der.RandomRec p ent
 mport com.tw ter.dec der.Rec p ent
 mport com.tw ter.s mclusters_v2.common.Dec derGateBu lderW h dHash ng
 mport javax. nject. nject

case class Representat onManagerDec der @ nject() (dec der: Dec der) {

  val dec derGateBu lder = new Dec derGateBu lderW h dHash ng(dec der)

  def  sAva lable(feature: Str ng, rec p ent: Opt on[Rec p ent]): Boolean = {
    dec der. sAva lable(feature, rec p ent)
  }

  /**
   * W n useRandomRec p ent  s set to false, t  dec der  s e  r completely on or off.
   * W n useRandomRec p ent  s set to true, t  dec der  s on for t  spec f ed % of traff c.
   */
  def  sAva lable(feature: Str ng, useRandomRec p ent: Boolean = true): Boolean = {
     f (useRandomRec p ent)  sAva lable(feature, So (RandomRec p ent))
    else  sAva lable(feature, None)
  }
}
