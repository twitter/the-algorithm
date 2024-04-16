package com.tw ter.cr_m xer.param.dec der

 mport com.tw ter.dec der.Dec der
 mport com.tw ter.dec der.RandomRec p ent
 mport com.tw ter.dec der.Rec p ent
 mport com.tw ter.dec der.S mpleRec p ent
 mport com.tw ter.s mclusters_v2.common.Dec derGateBu lderW h dHash ng
 mport javax. nject. nject

case class CrM xerDec der @ nject() (dec der: Dec der) {

  def  sAva lable(feature: Str ng, rec p ent: Opt on[Rec p ent]): Boolean = {
    dec der. sAva lable(feature, rec p ent)
  }

  lazy val dec derGateBu lder = new Dec derGateBu lderW h dHash ng(dec der)

  /**
   * W n useRandomRec p ent  s set to false, t  dec der  s e  r completely on or off.
   * W n useRandomRec p ent  s set to true, t  dec der  s on for t  spec f ed % of traff c.
   */
  def  sAva lable(feature: Str ng, useRandomRec p ent: Boolean = true): Boolean = {
     f (useRandomRec p ent)  sAva lable(feature, So (RandomRec p ent))
    else  sAva lable(feature, None)
  }

  /***
   * Dec de w t r t  dec der  s ava lable for a spec f c  d us ng S mpleRec p ent( d).
   */
  def  sAva lableFor d(
     d: Long,
    dec derConstants: Str ng
  ): Boolean = {
    // Note: S mpleRec p ent does expose a `val  sUser = true` f eld wh ch  s not correct  f t   d  s not a user  d.
    // Ho ver t  f eld does not appear to be used anyw re  n s ce.
    dec der. sAva lable(dec derConstants, So (S mpleRec p ent( d)))
  }

}
