package com.twitter.home_mixer.module

import com.google.common.primitives.Longs
import com.google.inject.Provides
import com.twitter.bijection.Injection
import com.twitter.bijection.scrooge.BinaryScalaCodec
import com.twitter.bijection.scrooge.CompactScalaCodec
import com.twitter.bijection.thrift.ThriftCodec
import com.twitter.conversions.DurationOps._
import com.twitter.finagle.mtls.authentication.ServiceIdentifier
import com.twitter.home_mixer.param.HomeMixerInjectionNames._
import com.twitter.home_mixer.util.InjectionTransformerImplicits._
import com.twitter.home_mixer.util.LanguageUtil
import com.twitter.home_mixer.util.TensorFlowUtil
import com.twitter.inject.TwitterModule
import com.twitter.manhattan.v1.{thriftscala => mh}
import com.twitter.ml.api.{thriftscala => ml}
import com.twitter.ml.featurestore.lib.UserId
import com.twitter.ml.featurestore.{thriftscala => fs}
import com.twitter.product_mixer.shared_library.manhattan_client.ManhattanClientBuilder
import com.twitter.scalding_internal.multiformat.format.keyval.KeyValInjection.ScalaBinaryThrift
import com.twitter.search.common.constants.{thriftscala => scc}
import com.twitter.service.metastore.gen.{thriftscala => smg}
import com.twitter.servo.cache._
import com.twitter.servo.manhattan.ManhattanKeyValueRepository
import com.twitter.servo.repository.CachingKeyValueRepository
import com.twitter.servo.repository.ChunkingStrategy
import com.twitter.servo.repository.KeyValueRepository
import com.twitter.servo.repository.Repository
import com.twitter.servo.repository.keysAsQuery
import com.twitter.servo.util.Transformer
import com.twitter.storage.client.manhattan.bijections.Bijections
import com.twitter.storehaus_internal.manhattan.ManhattanClusters
import com.twitter.timelines.author_features.v1.{thriftjava => af}
import com.twitter.timelines.suggests.common.dense_data_record.{thriftscala => ddr}
import com.twitter.user_session_store.{thriftjava => uss}
import com.twitter.user_session_store.{thriftscala => uss_scala}
import com.twitter.util.Duration
import com.twitter.util.Try
import java.nio.ByteBuffer
import javax.inject.Named
import javax.inject.Singleton
import org.apache.thrift.protocol.TCompactProtocol
import org.apache.thrift.transport.TMemoryInputTransport
import org.apache.thrift.transport.TTransport

object ManhattanFeatureRepositoryModule extends TwitterModule {

  private val DEFAULT_RPC_CHUNK_SIZE = 50

  private val ThriftEntityIdInjection = ScalaBinaryThrift(fs.EntityId)

  private val FeatureStoreUserIdKeyTransformer = new Transformer[Long, ByteBuffer] {
    override def to(userId: Long): Try[ByteBuffer] = {
      Try(ByteBuffer.wrap(ThriftEntityIdInjection.apply(UserId(userId).toThrift)))
    }
    override def from(b: ByteBuffer): Try[Long] = ???
  }

  private val LongUserIdKeyTransformer = new Transformer[Long, ByteBuffer] {
    override def to(userId: Long): Try[ByteBuffer] = {
      Try(ByteBuffer.wrap(Longs.toByteArray(userId)))
    }
    override def from(b: ByteBuffer): Try[Long] = ???
  }

  private val FloatTensorTransformer = new Transformer[ByteBuffer, ml.FloatTensor] {
    override def to(input: ByteBuffer): Try[ml.FloatTensor] = {
      val floatTensor = TensorFlowUtil.embeddingByteBufferToFloatTensor(input)
      Try(floatTensor)
    }
    override def from(b: ml.FloatTensor): Try[ByteBuffer] = ???
  }

  private val EmbeddingTransformer = new Transformer[ByteBuffer, ml.FloatTensor] {
    override def to(input: ByteBuffer): Try[ml.FloatTensor] = {
      Try.fromScala(
        Bijections
          .BinaryScalaInjection(ml.Embedding).andThen(Bijections.byteBuffer2Buf.inverse).invert(
            input).map { embedding =>
            embedding.tensor.map { tensor =>
              TensorFlowUtil.embeddingNoHeaderByteBufferToFloatTensor(tensor.content)
            }.get
          }
      )
    }

    override def from(b: ml.FloatTensor): Try[ByteBuffer] = ???
  }

  private val LanguageTransformer = new Transformer[ByteBuffer, Seq[scc.ThriftLanguage]] {
    override def to(input: ByteBuffer): Try[Seq[scc.ThriftLanguage]] = {
      Try.fromScala(
        Bijections
          .BinaryScalaInjection(smg.UserLanguages)
          .andThen(Bijections.byteBuffer2Buf.inverse)
          .invert(input).map(LanguageUtil.computeLanguages(_)))
    }

    override def from(b: Seq[scc.ThriftLanguage]): Try[ByteBuffer] = ???
  }

  private val LongKeyTransformer = Injection
    .connect[Long, Array[Byte]]
    .toByteBufferTransformer()

  // manhattan clients

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
  @Named(ManhattanOmegaClient)
  def providesManhattanOmegaClient(
    serviceIdentifier: ServiceIdentifier
  ): mh.ManhattanCoordinator.MethodPerEndpoint = {
    ManhattanClientBuilder
      .buildManhattanV1FinagleClient(
        ManhattanClusters.omega,
        serviceIdentifier
      )
  }

  @Provides
  @Singleton
  @Named(ManhattanStarbuckClient)
  def providesManhattanStarbuckClient(
    serviceIdentifier: ServiceIdentifier
  ): mh.ManhattanCoordinator.MethodPerEndpoint = {
    ManhattanClientBuilder
      .buildManhattanV1FinagleClient(
        ManhattanClusters.starbuck,
        serviceIdentifier
      )
  }

  // non-cached manhattan repositories
  /**
   * A repository of the offline aggregate feature metadata necessary to decode
   * DenseCompactDataRecords.
   *
   * This repository is expected to virtually always pick up the metadata form the local cache with
   * nearly 0 latency.
   */
  @Provides
  @Singleton
  @Named(TimelineAggregateMetadataRepository)
  def providesTimelineAggregateMetadataRepository(
    @Named(ManhattanAthenaClient) client: mh.ManhattanCoordinator.MethodPerEndpoint
  ): Repository[Int, Option[ddr.DenseFeatureMetadata]] = {

    val keyTransformer = Injection
      .connect[Int, Array[Byte]]
      .toByteBufferTransformer()

    val valueTransformer = new Transformer[ByteBuffer, ddr.DenseFeatureMetadata] {
      private val compactProtocolFactory = new TCompactProtocol.Factory

      def to(buffer: ByteBuffer): Try[ddr.DenseFeatureMetadata] = Try {
        val transport = transportFromByteBuffer(buffer)
        ddr.DenseFeatureMetadata.decode(compactProtocolFactory.getProtocol(transport))
      }

      // Encoding intentionally not implemented as it is never used
      def from(metadata: ddr.DenseFeatureMetadata): Try[ByteBuffer] = ???
    }

    val inProcessCache: Cache[Int, Cached[ddr.DenseFeatureMetadata]] = InProcessLruCacheFactory(
      ttl = Duration.fromMinutes(20),
      lruSize = 30
    ).apply(serializer = Transformer(_ => ???, _ => ???)) // Serialization is not necessary here.

    val keyValueRepository = new ManhattanKeyValueRepository(
      client = client,
      keyTransformer = keyTransformer,
      valueTransformer = valueTransformer,
      appId = "timelines_dense_aggregates_encoding_metadata", // Expected QPS is negligible.
      dataset = "user_session_dense_feature_metadata",
      timeoutInMillis = 100
    )

    KeyValueRepository
      .singular(
        new CachingKeyValueRepository[Seq[Int], Int, ddr.DenseFeatureMetadata](
          keyValueRepository,
          new NonLockingCache(inProcessCache),
          keysAsQuery[Int]
        )
      )
  }

  @Provides
  @Singleton
  @Named(RealGraphFeatureRepository)
  def providesRealGraphFeatureRepository(
    @Named(ManhattanApolloClient) client: mh.ManhattanCoordinator.MethodPerEndpoint
  ): Repository[Long, Option[uss_scala.UserSession]] = {
    val valueTransformer = CompactScalaCodec(uss_scala.UserSession).toByteBufferTransformer().flip

    KeyValueRepository.singular(
      new ManhattanKeyValueRepository(
        client = client,
        keyTransformer = LongKeyTransformer,
        valueTransformer = valueTransformer,
        appId = "real_graph",
        dataset = "real_graph_user_features",
        timeoutInMillis = 100,
      )
    )
  }

  // cached manhattan repositories

  @Provides
  @Singleton
  @Named(AuthorFeatureRepository)
  def providesAuthorFeatureRepository(
    @Named(ManhattanAthenaClient) client: mh.ManhattanCoordinator.MethodPerEndpoint,
    @Named(HomeAuthorFeaturesCacheClient) cacheClient: Memcache
  ): KeyValueRepository[Seq[Long], Long, af.AuthorFeatures] = {

    val valueInjection = ThriftCodec
      .toCompact[af.AuthorFeatures]

    val keyValueRepository = batchedManhattanKeyValueRepository(
      client = client,
      keyTransformer = LongKeyTransformer,
      valueTransformer = valueInjection.toByteBufferTransformer().flip,
      appId = "timelines_author_feature_store_athena",
      dataset = "timelines_author_features",
      timeoutInMillis = 100
    )

    val remoteCacheRepo = buildMemCachedRepository(
      keyValueRepository = keyValueRepository,
      cacheClient = cacheClient,
      cachePrefix = "AuthorFeatureHydrator",
      ttl = 12.hours,
      valueInjection = valueInjection
    )

    remoteCacheRepo
  }

  @Provides
  @Singleton
  @Named(TwhinAuthorFollowFeatureRepository)
  def providesTwhinAuthorFollowFeatureRepository(
    @Named(ManhattanApolloClient) client: mh.ManhattanCoordinator.MethodPerEndpoint,
    @Named(TwhinAuthorFollowFeatureCacheClient) cacheClient: Memcache
  ): KeyValueRepository[Seq[Long], Long, ml.FloatTensor] = {
    val keyValueRepository =
      batchedManhattanKeyValueRepository(
        client = client,
        keyTransformer = FeatureStoreUserIdKeyTransformer,
        valueTransformer = FloatTensorTransformer,
        appId = "ml_features_apollo",
        dataset = "twhin_author_follow_embedding_fsv1__v1_thrift__embedding",
        timeoutInMillis = 100
      )

    val valueInjection: Injection[ml.FloatTensor, Array[Byte]] =
      BinaryScalaCodec(ml.FloatTensor)

    buildMemCachedRepository(
      keyValueRepository = keyValueRepository,
      cacheClient = cacheClient,
      cachePrefix = "twhinAuthorFollows",
      ttl = 24.hours,
      valueInjection = valueInjection
    )
  }

  @Provides
  @Singleton
  @Named(UserLanguagesRepository)
  def providesUserLanguagesFeatureRepository(
    @Named(ManhattanStarbuckClient) client: mh.ManhattanCoordinator.MethodPerEndpoint
  ): KeyValueRepository[Seq[Long], Long, Seq[scc.ThriftLanguage]] = {
    batchedManhattanKeyValueRepository(
      client = client,
      keyTransformer = LongKeyTransformer,
      valueTransformer = LanguageTransformer,
      appId = "user_metadata",
      dataset = "languages",
      timeoutInMillis = 70
    )
  }

  @Provides
  @Singleton
  @Named(TwhinUserFollowFeatureRepository)
  def providesTwhinUserFollowFeatureRepository(
    @Named(ManhattanApolloClient) client: mh.ManhattanCoordinator.MethodPerEndpoint
  ): KeyValueRepository[Seq[Long], Long, ml.FloatTensor] = {
    batchedManhattanKeyValueRepository(
      client = client,
      keyTransformer = FeatureStoreUserIdKeyTransformer,
      valueTransformer = FloatTensorTransformer,
      appId = "ml_features_apollo",
      dataset = "twhin_user_follow_embedding_fsv1__v1_thrift__embedding",
      timeoutInMillis = 100
    )
  }

  @Provides
  @Singleton
  @Named(TimelineAggregatePartARepository)
  def providesTimelineAggregatePartARepository(
    @Named(ManhattanApolloClient) client: mh.ManhattanCoordinator.MethodPerEndpoint,
  ): Repository[Long, Option[uss.UserSession]] =
    timelineAggregateRepository(
      mhClient = client,
      mhDataset = "timelines_aggregates_v2_features_by_user_part_a_apollo",
      mhAppId = "timelines_aggregates_v2_features_by_user_part_a_apollo"
    )

  @Provides
  @Singleton
  @Named(TimelineAggregatePartBRepository)
  def providesTimelineAggregatePartBRepository(
    @Named(ManhattanApolloClient) client: mh.ManhattanCoordinator.MethodPerEndpoint,
  ): Repository[Long, Option[uss.UserSession]] =
    timelineAggregateRepository(
      mhClient = client,
      mhDataset = "timelines_aggregates_v2_features_by_user_part_b_apollo",
      mhAppId = "timelines_aggregates_v2_features_by_user_part_b_apollo"
    )

  @Provides
  @Singleton
  @Named(TwhinUserEngagementFeatureRepository)
  def providesTwhinUserEngagementFeatureRepository(
    @Named(ManhattanApolloClient) client: mh.ManhattanCoordinator.MethodPerEndpoint
  ): KeyValueRepository[Seq[Long], Long, ml.FloatTensor] = {

    batchedManhattanKeyValueRepository(
      client = client,
      keyTransformer = FeatureStoreUserIdKeyTransformer,
      valueTransformer = FloatTensorTransformer,
      appId = "ml_features_apollo",
      dataset = "twhin_user_engagement_embedding_fsv1__v1_thrift__embedding",
      timeoutInMillis = 100
    )
  }

  @Provides
  @Singleton
  @Named(TwhinRebuildUserEngagementFeatureRepository)
  def providesTwhinRebuildUserEngagementFeatureRepository(
    @Named(ManhattanApolloClient) client: mh.ManhattanCoordinator.MethodPerEndpoint
  ): KeyValueRepository[Seq[Long], Long, ml.FloatTensor] = {

    batchedManhattanKeyValueRepository(
      client = client,
      keyTransformer = LongUserIdKeyTransformer,
      valueTransformer = EmbeddingTransformer,
      appId = "twhin_embeddings_apollo",
      dataset = "twhin_refreshed_user_eng_emb",
      timeoutInMillis = 100
    )
  }

  private def buildMemCachedRepository[K, V](
    keyValueRepository: KeyValueRepository[Seq[K], K, V],
    cacheClient: Memcache,
    cachePrefix: String,
    ttl: Duration,
    valueInjection: Injection[V, Array[Byte]]
  ): CachingKeyValueRepository[Seq[K], K, V] = {
    val cachedSerializer = CachedSerializer.binary(
      valueInjection.toByteArrayTransformer()
    )

    val cache = MemcacheCacheFactory(
      cacheClient,
      ttl,
      PrefixKeyTransformerFactory(cachePrefix)
    )[K, Cached[V]](cachedSerializer)

    new CachingKeyValueRepository(
      keyValueRepository,
      new NonLockingCache(cache),
      keysAsQuery[K]
    )
  }

  private def buildInProcessCachedRepository[K, V](
    keyValueRepository: KeyValueRepository[Seq[K], K, V],
    ttl: Duration,
    size: Int,
    valueInjection: Injection[V, Array[Byte]]
  ): CachingKeyValueRepository[Seq[K], K, V] = {
    val cachedSerializer = CachedSerializer.binary(
      valueInjection.toByteArrayTransformer()
    )

    val cache = InProcessLruCacheFactory(
      ttl = ttl,
      lruSize = size
    )[K, Cached[V]](cachedSerializer)

    new CachingKeyValueRepository(
      keyValueRepository,
      new NonLockingCache(cache),
      keysAsQuery[K]
    )
  }

  private def batchedManhattanKeyValueRepository[K, V](
    client: mh.ManhattanCoordinator.MethodPerEndpoint,
    keyTransformer: Transformer[K, ByteBuffer],
    valueTransformer: Transformer[ByteBuffer, V],
    appId: String,
    dataset: String,
    timeoutInMillis: Int,
    chunkSize: Int = DEFAULT_RPC_CHUNK_SIZE
  ): KeyValueRepository[Seq[K], K, V] =
    KeyValueRepository.chunked(
      new ManhattanKeyValueRepository(
        client = client,
        keyTransformer = keyTransformer,
        valueTransformer = valueTransformer,
        appId = appId,
        dataset = dataset,
        timeoutInMillis = timeoutInMillis
      ),
      chunker = ChunkingStrategy.equalSize(chunkSize)
    )

  private def transportFromByteBuffer(buffer: ByteBuffer): TTransport =
    new TMemoryInputTransport(
      buffer.array(),
      buffer.arrayOffset() + buffer.position(),
      buffer.remaining())

  private def timelineAggregateRepository(
    mhClient: mh.ManhattanCoordinator.MethodPerEndpoint,
    mhDataset: String,
    mhAppId: String
  ): Repository[Long, Option[uss.UserSession]] = {
    val valueInjection = ThriftCodec
      .toCompact[uss.UserSession]

    KeyValueRepository.singular(
      new ManhattanKeyValueRepository(
        client = mhClient,
        keyTransformer = LongKeyTransformer,
        valueTransformer = valueInjection.toByteBufferTransformer().flip,
        appId = mhAppId,
        dataset = mhDataset,
        timeoutInMillis = 100
      )
    )
  }
}
