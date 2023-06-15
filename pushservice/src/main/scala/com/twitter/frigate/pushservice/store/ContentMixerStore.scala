package com.twitter.frigate.pushservice.store

import com.twitter.content_mixer.thriftscala.ContentMixer
import com.twitter.content_mixer.thriftscala.ContentMixerRequest
import com.twitter.content_mixer.thriftscala.ContentMixerResponse
import com.twitter.storehaus.ReadableStore
import com.twitter.util.Future

case class ContentMixerStore(contentMixer: ContentMixer.MethodPerEndpoint)
    extends ReadableStore[ContentMixerRequest, ContentMixerResponse] {

  override def get(request: ContentMixerRequest): Future[Option[ContentMixerResponse]] = {
    contentMixer.getCandidates(request).map { response =>
      Some(response)
    }
  }
}
