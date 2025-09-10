package com.twitter.tweet_mixer.module

import com.google.inject.Provides
import com.twitter.bijection.Injection
import com.twitter.bijection.scrooge.BinaryScalaCodec
import com.twitter.finagle.mtls.authentication.ServiceIdentifier
import com.twitter.inject.TwitterModule
import com.twitter.manhattan.v1.{thriftscala => mh}
import com.twitter.product_mixer.shared_library.manhattan_client.ManhattanClientBuilder
import com.twitter.servo.manhattan.ManhattanKeyValueRepository
import com.twitter.servo.repository.KeyValueRepository
import com.twitter.servo.repository.Repository
import com.twitter.storehaus_internal.manhattan.ManhattanClusters
import com.twitter.tweet_mixer.model.ModuleNames._
import com.twitter.tweet_mixer.utils.InjectionTransformerImplicits._
import com.twitter.wtf.candidate.thriftscala.CandidateSeq
import java.nio.ByteBuffer
import javax.inject.Named
import javax.inject.Singleton
import org.apache.thrift.transport.TMemoryInputTransport
import org.apache.thrift.transport.TTransport

object ManhattanFeatureRepositoryModule extends TwitterModule {

  @Provides
  @Singleton
  @Named(ManhattanAthenaClient)
  def providesManhattanAthenaClient(
    serviceIdentifier: ServiceIdentifier
  ): mh.ManhattanCoordinator.MethodPerEndpoint = {
    ManhattanClientBuilder
      .buildManhattanV1FinagleClient(
        ManhattanClusters.athena,
        serviceIdentifier
      )
  }

  @Provides
  @Singleton
  @Named(ManhattanApolloClient)
  def providesManhattanApolloClient(
    serviceIdentifier: ServiceIdentifier
  ): mh.ManhattanCoordinator.MethodPerEndpoint = {
    ManhattanClientBuilder
      .buildManhattanV1FinagleClient(
        ManhattanClusters.apollo,
        serviceIdentifier
      )
  }

  @Provides
  @Singleton
  @Named(RealGraphInNetworkScoresOnPremRepo)
  def providesRealGraphInNetworkScoresRepo(
    @Named(ManhattanApolloClient) client: mh.ManhattanCoordinator.MethodPerEndpoint
  ): Repository[Long, Option[CandidateSeq]] = {
    val keyTransformer = Injection
      .connect[Long, Array[Byte]]
      .toByteBufferTransformer()

    val valueTransformer =
      BinaryScalaCodec(CandidateSeq).toByteBufferTransformer().flip

    KeyValueRepository.singular(
      new ManhattanKeyValueRepository(
        client = client,
        keyTransformer = keyTransformer,
        valueTransformer = valueTransformer,
        appId = "real_graph",
        dataset = "real_graph_scores_in_v1",
        timeoutInMillis = 100
      )
    )
  }

  private def transportFromByteBuffer(buffer: ByteBuffer): TTransport =
    new TMemoryInputTransport(
      buffer.array(),
      buffer.arrayOffset() + buffer.position(),
      buffer.remaining()
    )
}
