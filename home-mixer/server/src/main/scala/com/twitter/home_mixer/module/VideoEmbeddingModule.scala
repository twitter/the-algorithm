package com.twitter.home_mixer.module

import com.google.inject.Provides
import com.twitter.home_mixer.param.HomeMixerInjectionNames.VideoEmbeddingMHStore
import com.twitter.home_mixer.store.VideoEmbeddingMHStore
import com.twitter.inject.TwitterModule
import com.twitter.media_understanding.video_summary.thriftscala.VideoEmbedding
import com.twitter.storehaus.ReadableStore
import javax.inject.Named
import javax.inject.Singleton

object VideoEmbeddingModule extends TwitterModule {

  @Provides
  @Singleton
  @Named(VideoEmbeddingMHStore)
  def providesVideoEmbeddingMHStore(
    videoEmbeddingMHStore: VideoEmbeddingMHStore
  ): ReadableStore[Long, VideoEmbedding] = {
    videoEmbeddingMHStore.videoEmbeddingMHStore
  }
}
