package com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urp

 mport com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt.T  l neScr beConf gMarshaller
 mport com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt. tadata.Art cleDeta lsMarshaller
 mport com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt. tadata.Cl entEventDeta lsMarshaller
 mport com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt. tadata.Cl entEvent nfoMarshaller
 mport com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt. tadata.Com rceDeta lsMarshaller
 mport com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt. tadata.Conversat onDeta lsMarshaller
 mport com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt. tadata.Conversat onSect onMarshaller
 mport com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt. tadata.L veEventDeta lsMarshaller
 mport com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt. tadata.T  l nesDeta lsMarshaller
 mport com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt. tadata.UrlMarshaller
 mport com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt. tadata.UrlTypeMarshaller
 mport com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt. tadata.UrtEndpo ntOpt onsMarshaller

object UrpTransportMarshallerBu lder {
  // Conven ence constructor for serv ces not us ng dependency  nject on and un  tests.  f us ng
  // dependency  nject on,  nstead @ nject an  nstance of UrpTransportMarshaller to construct.

  val t  l neKeyMarshaller = new T  l neKeyMarshaller
  val t  l neScr beConf gMarshaller = new T  l neScr beConf gMarshaller
  val urlMarshaller = new UrlMarshaller(new UrlTypeMarshaller, new UrtEndpo ntOpt onsMarshaller)
  val cl entEvent nfoMarshaller = new Cl entEvent nfoMarshaller(
    new Cl entEventDeta lsMarshaller(
      new Conversat onDeta lsMarshaller(new Conversat onSect onMarshaller),
      new T  l nesDeta lsMarshaller,
      new Art cleDeta lsMarshaller,
      new L veEventDeta lsMarshaller,
      new Com rceDeta lsMarshaller)
  )

  val seg ntedT  l neMarshaller =
    new Seg ntedT  l neMarshaller(t  l neKeyMarshaller, t  l neScr beConf gMarshaller)
  val seg ntedT  l nesMarshaller = new Seg ntedT  l nesMarshaller(seg ntedT  l neMarshaller)

  val pageBodyMarshaller: PageBodyMarshaller = new PageBodyMarshaller(
    t  l neKeyMarshaller,
    seg ntedT  l nesMarshaller
  )

  val top cPage aderFacep leMarshaller = new Top cPage aderFacep leMarshaller(urlMarshaller)
  val top cPage aderD splayTypeMarshaller = new Top cPage aderD splayTypeMarshaller
  val top cPage aderMarshaller = new Top cPage aderMarshaller(
    top cPage aderFacep leMarshaller,
    cl entEvent nfoMarshaller,
    top cPage aderD splayTypeMarshaller
  )
  val page aderMarshaller: Page aderMarshaller = new Page aderMarshaller(
    top cPage aderMarshaller)

  val top cPageNavBarMarshaller = new Top cPageNavBarMarshaller(cl entEvent nfoMarshaller)
  val t leNavBarMarshaller = new T leNavBarMarshaller(cl entEvent nfoMarshaller)
  val pageNavBarMarshaller: PageNavBarMarshaller = new PageNavBarMarshaller(
    top cPageNavBarMarshaller,
    t leNavBarMarshaller
  )

  val marshaller: UrpTransportMarshaller =
    new UrpTransportMarshaller(
      pageBodyMarshaller = pageBodyMarshaller,
      t  l neScr beConf gMarshaller = t  l neScr beConf gMarshaller,
      page aderMarshaller = page aderMarshaller,
      pageNavBarMarshaller = pageNavBarMarshaller
    )
}
