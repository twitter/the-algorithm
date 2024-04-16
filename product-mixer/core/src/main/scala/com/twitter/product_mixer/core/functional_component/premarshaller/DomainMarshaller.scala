package com.tw ter.product_m xer.core.funct onal_component.premarshaller

 mport com.tw ter.product_m xer.core.funct onal_component.marshaller.TransportMarshaller
 mport com.tw ter.product_m xer.core.model.common.Component
 mport com.tw ter.product_m xer.core.model.common. dent f er.Component dent f er
 mport com.tw ter.product_m xer.core.model.common. dent f er.Doma nMarshaller dent f er
 mport com.tw ter.product_m xer.core.model.common.presentat on.Cand dateW hDeta ls
 mport com.tw ter.product_m xer.core.model.common.presentat on.ModulePresentat on
 mport com.tw ter.product_m xer.core.model.common.presentat on.Un versalPresentat on
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery

/**
 * Transforms t  `select ons`  nto a [[Doma nResponseType]] object (often URT, Sl ce, etc)
 *
 * [[Doma nMarshaller]]s may conta n bus ness log c
 *
 * @note T   s d fferent from `com.tw ter.product_m xer.core.marshaller`s
 *       wh ch transforms  nto a w re-compat ble type
 */
tra  Doma nMarshaller[-Query <: P pel neQuery, Doma nResponseType] extends Component {

  overr de val  dent f er: Doma nMarshaller dent f er

  /** Transforms t  `select ons`  nto a [[Doma nResponseType]] object */
  def apply(
    query: Query,
    select ons: Seq[Cand dateW hDeta ls]
  ): Doma nResponseType
}

class UnsupportedCand dateDoma nMarshallerExcept on(
  cand date: Any,
  cand dateS ce: Component dent f er)
    extends UnsupportedOperat onExcept on(
      s"Doma n marshaller does not support cand date ${TransportMarshaller.getS mpleNa (
        cand date.getClass)} from s ce $cand dateS ce")

class UndecoratedCand dateDoma nMarshallerExcept on(
  cand date: Any,
  cand dateS ce: Component dent f er)
    extends UnsupportedOperat onExcept on(
      s"Doma n marshaller does not support undecorated cand date ${TransportMarshaller
        .getS mpleNa (cand date.getClass)} from s ce $cand dateS ce")

class UnsupportedPresentat onDoma nMarshallerExcept on(
  cand date: Any,
  presentat on: Un versalPresentat on,
  cand dateS ce: Component dent f er)
    extends UnsupportedOperat onExcept on(
      s"Doma n marshaller does not support decorator presentat on ${TransportMarshaller
        .getS mpleNa (presentat on.getClass)} for cand date ${TransportMarshaller.getS mpleNa (
        cand date.getClass)} from s ce $cand dateS ce")

class UnsupportedModuleDoma nMarshallerExcept on(
  presentat on: Opt on[ModulePresentat on],
  cand dateS ce: Component dent f er)
    extends UnsupportedOperat onExcept on(
      s"Doma n marshaller does not support module presentat on ${presentat on
        .map(p =>
          TransportMarshaller
            .getS mpleNa (presentat on.getClass)).getOrElse("")} but was g ven a module from s ce $cand dateS ce")

class UndecoratedModuleDoma nMarshallerExcept on(
  cand dateS ce: Component dent f er)
    extends UnsupportedOperat onExcept on(
      s"Doma n marshaller does not support undecorated module from s ce $cand dateS ce")
