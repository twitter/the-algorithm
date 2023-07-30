package com.X.cr_mixer.module

import com.google.inject.Provides
import com.X.bijection.Injection
import com.X.bijection.scrooge.BinaryScalaCodec
import com.X.cr_mixer.model.ModuleNames
import com.X.finagle.mtls.authentication.ServiceIdentifier
import com.X.inject.XModule
import com.X.simclusters_v2.thriftscala.TweetsWithScore
import com.X.storage.client.manhattan.kv.ManhattanKVClientMtlsParams
import com.X.storehaus.ReadableStore
import com.X.storehaus_internal.manhattan.Apollo
import com.X.storehaus_internal.manhattan.ManhattanRO
import com.X.storehaus_internal.manhattan.ManhattanROConfig
import com.X.storehaus_internal.util.ApplicationID
import com.X.storehaus_internal.util.DatasetName
import com.X.storehaus_internal.util.HDFSPath
import javax.inject.Named
import javax.inject.Singleton

object DiffusionStoreModule extends XModule {
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
