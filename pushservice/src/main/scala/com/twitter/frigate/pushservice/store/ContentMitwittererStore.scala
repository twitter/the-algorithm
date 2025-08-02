package com.twitter.frigate.pushservice.store

import com.twitter.content_mitwitterer.thriftscala.ContentMitwitterer
import com.twitter.content_mitwitterer.thriftscala.ContentMitwittererRequest
import com.twitter.content_mitwitterer.thriftscala.ContentMitwittererResponse
import com.twitter.storehaus.ReadableStore
import com.twitter.util.Future

case class ContentMitwittererStore(contentMitwitterer: ContentMitwitterer.MethodPerEndpoint)
    etwittertends ReadableStore[ContentMitwittererRequest, ContentMitwittererResponse] {

  override def get(request: ContentMitwittererRequest): Future[Option[ContentMitwittererResponse]] = {
    contentMitwitterer.getCandidates(request).map { response =>
      Some(response)
    }
  }
}
