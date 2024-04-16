package com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt.r chtext

 mport com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt. tadata.UrlMarshaller
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata.Url
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.r chtext.ReferenceObject
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.r chtext.R chTextCashtag
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.r chtext.R chTextHashtag
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.r chtext.R chTextL st
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.r chtext.R chText nt on
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.r chtext.R chTextUser
 mport com.tw ter.t  l nes.render.{thr ftscala => urt}
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class ReferenceObjectMarshaller @ nject() (urlMarshaller: UrlMarshaller) {

  def apply(ref: ReferenceObject): urt.ReferenceObject = ref match {
    case url: Url => urt.ReferenceObject.Url(urlMarshaller(url))
    case user: R chTextUser => urt.ReferenceObject.User(urt.R chTextUser( d = user. d))
    case  nt on: R chText nt on =>
      urt.ReferenceObject. nt on(
        urt.R chText nt on( d =  nt on. d, screenNa  =  nt on.screenNa ))
    case hashtag: R chTextHashtag =>
      urt.ReferenceObject.Hashtag(urt.R chTextHashtag(text = hashtag.text))
    case cashtag: R chTextCashtag =>
      urt.ReferenceObject.Cashtag(urt.R chTextCashtag(text = cashtag.text))
    case tw terL st: R chTextL st =>
      urt.ReferenceObject.Tw terL st(urt.R chTextL st( d = tw terL st. d, url = tw terL st.url))
  }
}
