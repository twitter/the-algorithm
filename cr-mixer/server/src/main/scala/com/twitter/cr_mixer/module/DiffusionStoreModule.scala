package com.ExTwitter.cr_mixer.module

import com.google.inject.Provides
import com.ExTwitter.bijection.Injection
import com.ExTwitter.bijection.scrooge.BinaryScalaCodec
import com.ExTwitter.cr_mixer.model.ModuleNames
import com.ExTwitter.finagle.mtls.authentication.ServiceIdentifier
import com.ExTwitter.inject.ExTwitterModule
import com.ExTwitter.simclusters_v2.thriftscala.TweetsWithScore
import com.ExTwitter.storage.client.manhattan.kv.ManhattanKVClientMtlsParams
import com.ExTwitter.storehaus.ReadableStore
import com.ExTwitter.storehaus_internal.manhattan.Apollo
import com.ExTwitter.storehaus_internal.manhattan.ManhattanRO
import com.ExTwitter.storehaus_internal.manhattan.ManhattanROConfig
import com.ExTwitter.storehaus_internal.util.ApplicationID
import com.ExTwitter.storehaus_internal.util.DatasetName
import com.ExTwitter.storehaus_internal.util.HDFSPath
import javax.inject.Named
import javax.inject.Singleton

object DiffusionStoreModule extends ExTwitterModule {
  type UserId = Long
  implicit val longCodec = implicitly[Injection[Long, Array[Byte]]]
  implicit val tweetRecsInjection: Injection[TweetsWithScore, Array[Byte]] =
    BinaryScalaCodec(TweetsWithScore)

  @Provides
  @Singleton
  @Named(ModuleNames.RetweetBasedDiffusionRecsMhStore)
  def retweetBasedDiffusionRecsMhStore(
    serviceIdentifier: ServiceIdentifier
  ): ReadableStore[Long, TweetsWithScore] = {
    val manhattanROConfig = ManhattanROConfig(
      HDFSPath(""), // not needed
      ApplicationID("cr_mixer_apollo"),
      DatasetName("diffusion_retweet_tweet_recs"),
      Apollo
    )

    buildTweetRecsStore(serviceIdentifier, manhattanROConfig)
  }

  private def buildTweetRecsStore(
    serviceIdentifier: ServiceIdentifier,
    manhattanROConfig: ManhattanROConfig
  ): ReadableStore[Long, TweetsWithScore] = {

    ManhattanRO
      .getReadableStoreWithMtls[Long, TweetsWithScore](
        manhattanROConfig,
        ManhattanKVClientMtlsParams(serviceIdentifier)
      )(longCodec, tweetRecsInjection)
  }
}
