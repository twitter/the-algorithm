package com.X.frigate.pushservice.store

import com.X.content_mixer.thriftscala.ContentMixer
import com.X.content_mixer.thriftscala.ContentMixerRequest
import com.X.content_mixer.thriftscala.ContentMixerResponse
import com.X.storehaus.ReadableStore
import com.X.util.Future

case class ContentMixerStore(contentMixer: ContentMixer.MethodPerEndpoint)
    extends ReadableStore[ContentMixerRequest, ContentMixerResponse] {

  override def get(request: ContentMixerRequest): Future[Option[ContentMixerResponse]] = {
    contentMixer.getCandidates(request).map { response =>
      Some(response)
    }
  }
}
