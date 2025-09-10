package com.twitter.tweet_mixer.module.thrift_client

import com.google.inject.Provides
import com.twitter.ann.common.EmbeddingProducer
import com.twitter.ann.manhattan.ManhattanEmbeddingProducer
import com.twitter.bijection.Injection
import com.twitter.conversions.DurationOps._
import com.twitter.finagle.mtls.authentication.ServiceIdentifier
import com.twitter.inject.TwitterModule
import com.twitter.storage.client.manhattan.kv.ManhattanKVClient
import com.twitter.storage.client.manhattan.kv.ManhattanKVClientMtlsParams
import com.twitter.storage.client.manhattan.kv.ManhattanKVEndpointBuilder
import com.twitter.tweet_mixer.model.ModuleNames
import javax.inject.Named
import javax.inject.Singleton

object AnnEmbeddingProducerModule extends TwitterModule {

  @Provides
  @Singleton
  @Named(ModuleNames.TwHINEmbeddingRegularUpdateMhEmbeddingProducer)
  def twHINEmbeddingRegularUpdateMhEmbeddingProducer(
    serviceIdentifier: ServiceIdentifier,
  ): EmbeddingProducer[Long] = {
    val client = ManhattanKVClient(
      "cr_mixer_apollo",
      "/s/manhattan/apollo.native-thrift",
      ManhattanKVClientMtlsParams(serviceIdentifier))
    val endpoint = ManhattanKVEndpointBuilder(client).defaultMaxTimeout(50.seconds).build()
    val longCodec = implicitly[Injection[Long, Array[Byte]]]
    ManhattanEmbeddingProducer("twhin_regular_update_tweet_embedding_apollo", longCodec, endpoint)
  }

  @Provides
  @Singleton
  @Named(ModuleNames.ConsumerBasedTwHINEmbeddingRegularUpdateMhEmbeddingProducer)
  def consumerBasedTwHINEmbeddingRegularUpdateMhEmbeddingProducer(
    serviceIdentifier: ServiceIdentifier,
  ): EmbeddingProducer[Long] = {
    val client = ManhattanKVClient(
      "cr_mixer_apollo",
      "/s/manhattan/apollo.native-thrift",
      ManhattanKVClientMtlsParams(serviceIdentifier))
    val endpoint = ManhattanKVEndpointBuilder(client).defaultMaxTimeout(50.seconds).build()
    val longCodec = implicitly[Injection[Long, Array[Byte]]]
    ManhattanEmbeddingProducer("twhin_user_embedding_regular_update_apollo", longCodec, endpoint)
  }
}
