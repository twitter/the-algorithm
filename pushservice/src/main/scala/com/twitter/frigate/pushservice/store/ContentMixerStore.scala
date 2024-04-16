package com.tw ter.fr gate.pushserv ce.store

 mport com.tw ter.content_m xer.thr ftscala.ContentM xer
 mport com.tw ter.content_m xer.thr ftscala.ContentM xerRequest
 mport com.tw ter.content_m xer.thr ftscala.ContentM xerResponse
 mport com.tw ter.storehaus.ReadableStore
 mport com.tw ter.ut l.Future

case class ContentM xerStore(contentM xer: ContentM xer. thodPerEndpo nt)
    extends ReadableStore[ContentM xerRequest, ContentM xerResponse] {

  overr de def get(request: ContentM xerRequest): Future[Opt on[ContentM xerResponse]] = {
    contentM xer.getCand dates(request).map { response =>
      So (response)
    }
  }
}
