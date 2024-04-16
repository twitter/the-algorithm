package com.tw ter.ho _m xer.marshaller.t  l ne_logg ng

 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. em.t et.T et em
 mport com.tw ter.t  l nes.t  l ne_logg ng.{thr ftscala => thr ftlog}

object PromotedT etDeta lsMarshaller {

  def apply(entry: T et em, pos  on:  nt): thr ftlog.PromotedT etDeta ls = {
    thr ftlog.PromotedT etDeta ls(
      advert ser d = So (entry.promoted tadata.map(_.advert ser d).getOrElse(0L)),
       nsertPos  on = So (pos  on),
       mpress on d = entry.promoted tadata.flatMap(_. mpress onStr ng)
    )
  }
}
