package com.tw ter.t etyp e. d a

 mport com.tw ter. d aserv ces.commons.thr ftscala. d aKey
 mport com.tw ter. d aserv ces.commons.thr ftscala. d aCategory

object  d aKeyClass f er {

  class Class f er(categor es: Set[ d aCategory]) {

    def apply( d aKey:  d aKey): Boolean =
      categor es.conta ns( d aKey. d aCategory)

    def unapply( d aKey:  d aKey): Opt on[ d aKey] =
      apply( d aKey) match {
        case false => None
        case true => So ( d aKey)
      }
  }

  val  s mage: Class f er = new Class f er(Set( d aCategory.T et mage))
  val  sG f: Class f er = new Class f er(Set( d aCategory.T etG f))
  val  sV deo: Class f er = new Class f er(
    Set( d aCategory.T etV deo,  d aCategory.Ampl fyV deo)
  )
}
