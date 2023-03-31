package com.twitter.home_mixer.module

import com.google.inject.Provides
import com.twitter.bijection.Injection
import com.twitter.bijection.scrooge.BinaryScalaCodec
import com.twitter.bijection.scrooge.CompactScalaCodec
import com.twitter.bijection.thrift.ThriftCodec
import com.twitter.conversions.DurationOps._
import com.twitter.finagle.mtls.authentication.ServiceIdentifier
import com.twitter.home_mixer.param.HomeMixerInjectionNames._
import com.twitter.home_mixer.util.InjectionTransformerImplicits._
import com.twitter.home_mixer.util.TensorFlowUtil
import com.twitter.inject.TwitterModule
import com.twitter.manhattan.v1.thriftscala.ManhattanCoordinator
import com.twitter.manhattan.v1.{thriftscala => mh}
import com.twitter.ml.api.thriftscala.FloatTensor
import com.twitter.ml.api.{thriftscala => ml}
import com.twitter.ml.featurestore.lib.UserId
import com.twitter.ml.featurestore.thriftscala.EntityId
import com.twitter.onboarding.relevance.features.{thriftjava => rf}
import com.twitter.product_mixer.shared_library.manhattan_client.ManhattanClientBuilder
import com.twitter.scalding_internal.multiformat.format.keyval.KeyValInjection.ScalaBinaryThrift
import com.twitter.servo.cache._
import com.twitter.servo.manhattan.ManhattanKeyValueRepository
import com.twitter.servo.repository.CachingKeyValueRepository
import com.twitter.servo.repository.ChunkingStrategy
import com.twitter.servo.repository.KeyValueRepository
import com.twitter.servo.repository.Repository
import com.twitter.servo.repository.keysAsQuery
import com.twitter.servo.util.Transformer
import com.twitter.storehaus_internal.manhattan.ManhattanClusters
import com.twitter.timelines.author_features.v1.{thriftjava => af}
import com.twitter.timelines.suggests.common.dense_data_record.thriftscala.DenseFeatureMetadata
import com.twitter.user_session_store.thriftscala.UserSession
import com.twitter.user_session_store.{thriftjava => uss}
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

  private val ThriftEntityIdInjection = ScalaBinaryThrift(EntityId)

  val UserIdKeyTransformer = new Transformer[Long, ByteBuffer] {
    override def to(userId: Long): Try[ByteBuffer] = {
      Try(ByteBuffer.wrap(ThriftEntityIdInjection.apply(UserId(userId).toThrift)))
    }
    override def from(b: ByteBuffer): Try[Long] = ???
  }

  val FloatTensorTransformer = new Transformer[ByteBuffer, FloatTensor] {
    override def to(input: ByteBuffer): Try[FloatTensor] = {
      val floatTensor = TensorFlowUtil.embeddingByteBufferToFloatTensor(input)
      Try(floatTensor)
    }

    override def from(b: FloatTensor): Try[ByteBuffer] = ???
  }

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

  @Provides
  @Singleton
  @Named(MetricCenterUserCountingFeatureRepository)
  def providesMetricCenterUserCountingFeatureRepository(
    @Named(ManhattanStarbuckClient) client: mh.ManhattanCoordinator.MethodPerEndpoint
  ): KeyValueRepository[Seq[Long], Long, rf.MCUserCountingFeatures] = {

    val keyTransformer = Injection
      .connect[Long, Array[Byte]]
      .toByteBufferTransformer()

    val valueTransformer = ThriftCodec
      .toCompact[rf.MCUserCountingFeatures]
      .toByteBufferTransformer()
      .flip

    batchedManhattanKeyValueRepository[Long, rf.MCUserCountingFeatures](
      client = client,
      keyTransformer = keyTransformer,
      valueTransformer = valueTransformer,
      appId = "wtf_ml",
      dataset = "mc_user_counting_features_v0_starbuck",
      timeoutInMillis = 100
    )
  }

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
  ): Repository[Int, Option[DenseFeatureMetadata]] = {

    val keyTransformer = Injection
      .connect[Int, Array[Byte]]
      .toByteBufferTransformer()

    val valueTransformer = new Transformer[ByteBuffer, DenseFeatureMetadata] {
      private val compactProtocolFactory = new TCompactProtocol.Factory

      def to(buffer: ByteBuffer): Try[DenseFeatureMetadata] = Try {
        val transport = transportFromByteBuffer(buffer)
        DenseFeatureMetadata.decode(compactProtocolFactory.getProtocol(transport))
      }

      // Encoding intentionally not implemented as it is never used
      def from(metadata: DenseFeatureMetadata): Try[ByteBuffer] = ???
    }

    val inProcessCache: Cache[Int, Cached[DenseFeatureMetadata]] = InProcessLruCacheFactory(
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
        new CachingKeyValueRepository[Seq[Int], Int, DenseFeatureMetadata](
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
  ): Repository[Long, Option[UserSession]] = {
    val valueTransformer = CompactScalaCodec(UserSession).toByteBufferTransformer().flip

    KeyValueRepository.singular(
      new ManhattanKeyValueRepository(
        client = client,
        keyTransformer = UserIdKeyTransformer,
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

    val keyTransformer = Injection
      .connect[Long, Array[Byte]]
      .toByteBufferTransformer()

    val valueInjection = ThriftCodec
      .toCompact[af.AuthorFeatures]

    val keyValueRepository = batchedManhattanKeyValueRepository(
      client = client,
      keyTransformer = keyTransformer,
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
      valueInjection = valueInjection)

    buildInProcessCachedRepository(
      keyValueRepository = remoteCacheRepo,
      ttl = 15.minutes,
      size = 8000,
      valueInjection = valueInjection
    )
  }

  @Provides
  @Singleton
  @Named(TwhinAuthorFollow20200101FeatureRepository)
  def providesTwhinAuthorFollow20200101FeatureRepository(
    @Named(ManhattanApolloClient) client: mh.ManhattanCoordinator.MethodPerEndpoint,
    @Named(TwhinAuthorFollow20200101FeatureCacheClient) cacheClient: Memcache
  ): KeyValueRepository[Seq[Long], Long, ml.Embedding] = {

    val keyTransformer = Injection
      .connect[Long, Array[Byte]]
      .toByteBufferTransformer()

    val valueInjection: Injection[ml.Embedding, Array[Byte]] =
      BinaryScalaCodec(ml.Embedding)

    val keyValueRepository = batchedManhattanKeyValueRepository(
      client = client,
      keyTransformer = keyTransformer,
      valueTransformer = valueInjection.toByteBufferTransformer().flip,
      appId = "twhin",
      dataset = "twhinauthor_follow_0101",
      timeoutInMillis = 100
    )

    buildMemCachedRepository(
      keyValueRepository = keyValueRepository,
      cacheClient = cacheClient,
      cachePrefix = "TwhinAuthorFollow20200101FeatureHydrator",
      ttl = 48.hours,
      valueInjection = valueInjection
    )
  }

  @Provides
  @Singleton
  @Named(TwhinUserFollowFeatureRepository)
  def providesTwhinUserFollowFeatureRepository(
    @Named(ManhattanApolloClient) client: mh.ManhattanCoordinator.MethodPerEndpoint
  ): KeyValueRepository[Seq[Long], Long, FloatTensor] = {

    batchedManhattanKeyValueRepository(
      client = client,
      keyTransformer = UserIdKeyTransformer,
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
  ): KeyValueRepository[Seq[Long], Long, FloatTensor] = {

    batchedManhattanKeyValueRepository(
      client = client,
      keyTransformer = UserIdKeyTransformer,
      valueTransformer = FloatTensorTransformer,
      appId = "ml_features_apollo",
      dataset = "twhin_user_engagement_embedding_fsv1__v1_thrift__embedding",
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
    client: ManhattanCoordinator.MethodPerEndpoint,
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
    val keyTransformer = Injection
      .connect[Long, Array[Byte]]
      .toByteBufferTransformer()

    val valueInjection = ThriftCodec
      .toCompact[uss.UserSession]

    KeyValueRepository.singular(
      new ManhattanKeyValueRepository(
        client = mhClient,
        keyTransformer = keyTransformer,
        valueTransformer = valueInjection.toByteBufferTransformer().flip,
        appId = mhAppId,
        dataset = mhDataset,
        timeoutInMillis = 100
      )
    )

  }

}
