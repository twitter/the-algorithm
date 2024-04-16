package com.tw ter.ho _m xer.marshaller.t  l ne_logg ng

 mport com.tw ter.product_m xer.core.model.common.presentat on. emCand dateW hDeta ls
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. em.user.User em
 mport com.tw ter.t  l nes.t  l ne_logg ng.{thr ftscala => thr ftlog}

object WhoToFollowDeta lsMarshaller {

  def apply(entry: User em, cand date:  emCand dateW hDeta ls): thr ftlog.WhoToFollowDeta ls =
    thr ftlog.WhoToFollowDeta ls(
      enableReact veBlend ng = entry.enableReact veBlend ng,
       mpress on d = entry.promoted tadata.flatMap(_. mpress onStr ng),
      advert ser d = entry.promoted tadata.map(_.advert ser d)
    )
}
