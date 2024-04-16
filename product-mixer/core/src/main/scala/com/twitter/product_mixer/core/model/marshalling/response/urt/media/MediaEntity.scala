package com.tw ter.product_m xer.core.model.marshall ng.response.urt. d a

 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata. mageVar ant

sealed tra   d aEnt y

case class T et d a(
  t et d: Long,
  mo nt d: Opt on[Long])
    extends  d aEnt y

case class Broadcast d( d: Str ng) extends  d aEnt y

case class  mage( mage:  mageVar ant) extends  d aEnt y
