package com.tw ter.product_m xer.core.funct onal_component.marshaller

 mport com.tw ter.product_m xer.core.model.common.Component
 mport com.tw ter.product_m xer.core.model.common. dent f er.TransportMarshaller dent f er
 mport com.tw ter.product_m xer.core.model.marshall ng.HasMarshall ng

object TransportMarshaller {

  /** Avo d `malfor d class na ` except ons due to t  presence of t  `$` character */
  def getS mpleNa [T](c: Class[T]): Str ng = {
    c.getNa .last ndexOf("$") match {
      case -1 => c.getS mpleNa 
      case  ndex => c.getNa .substr ng( ndex + 1)
    }
  }
}

/**
 * Marshals a [[Marshaller nput]]  nto a type that can be sent over t  w re
 *
 * T  transformat on should be  chan cal and not conta n bus ness log c
 *
 * @note t   s d fferent from `com.tw ter.product_m xer.core.funct onal_component.premarshaller`
 *       wh ch can conta n bus ness log c.
 */
tra  TransportMarshaller[-Marshaller nput <: HasMarshall ng, +MarshallerOutput] extends Component {

  overr de val  dent f er: TransportMarshaller dent f er

  def apply( nput: Marshaller nput): MarshallerOutput
}

/**
 * No op marshall ng that passes through a [[HasMarshall ng]]  nto any type. T   s useful  f
 * t  response does not need to be sent over t  w re, such as w h a
 * [[com.tw ter.product_m xer.core.funct onal_component.cand date_s ce.product_p pel ne.ProductP pel neCand dateS ce]]
 */
object NoOpTransportMarshaller extends TransportMarshaller[HasMarshall ng, Any] {
  overr de val  dent f er: TransportMarshaller dent f er = TransportMarshaller dent f er("NoOp")

  overr de def apply( nput: HasMarshall ng): Any =  nput
}
