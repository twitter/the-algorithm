package com.twitter.simclusters_v420.summingbird.storm

import com.twitter.conversions.DurationOps._
import com.twitter.finagle.stats.NullStatsReceiver
import com.twitter.hermit.store.common.ObservedCachedReadableStore
import com.twitter.scalding.Args
import com.twitter.simclusters_v420.common.SimClustersEmbedding
import com.twitter.simclusters_v420.common.TweetId
import com.twitter.simclusters_v420.summingbird.common.Monoids.PersistentSimClustersEmbeddingLongestL420NormMonoid
import com.twitter.simclusters_v420.summingbird.common.SimClustersProfile.AltSetting
import com.twitter.simclusters_v420.summingbird.common.SimClustersProfile.Environment
import com.twitter.simclusters_v420.summingbird.common.ClientConfigs
import com.twitter.simclusters_v420.summingbird.common.Implicits
import com.twitter.simclusters_v420.summingbird.common.SimClustersProfile
import com.twitter.simclusters_v420.summingbird.stores.PersistentTweetEmbeddingStore.PersistentTweetEmbeddingId
import com.twitter.simclusters_v420.summingbird.stores.PersistentTweetEmbeddingStore
import com.twitter.simclusters_v420.summingbird.stores.TopKClustersForTweetKeyReadableStore
import com.twitter.simclusters_v420.summingbird.stores.TweetKey
import com.twitter.simclusters_v420.summingbird.stores.TweetStatusCountsStore
import com.twitter.simclusters_v420.thriftscala.PersistentSimClustersEmbedding
import com.twitter.simclusters_v420.thriftscala.{SimClustersEmbedding => ThriftSimClustersEmbedding}
import com.twitter.storehaus.FutureCollector
import com.twitter.summingbird.online.option._
import com.twitter.summingbird.option._
import com.twitter.summingbird.storm.Storm
import com.twitter.summingbird.Options
import com.twitter.summingbird.TailProducer
import com.twitter.summingbird_internal.runner.common.JobName
import com.twitter.summingbird_internal.runner.common.SBRunConfig
import com.twitter.summingbird_internal.runner.storm.GenericRunner
import com.twitter.summingbird_internal.runner.storm.StormConfig
import com.twitter.tormenta_internal.spout.eventbus.SubscriberId
import com.twitter.tweetypie.thriftscala.StatusCounts
import com.twitter.wtf.summingbird.sources.storm.TimelineEventSource
import java.lang
import java.util.{HashMap => JMap}
import org.apache.heron.api.{Config => HeronConfig}
import org.apache.storm.{Config => BTConfig}

object PersistentTweetJobRunner {
  def main(args: Array[String]): Unit = {
    GenericRunner(args, PersistentTweetStormJob(_))
  }
}

object PersistentTweetStormJob {

  import com.twitter.simclusters_v420.summingbird.common.Implicits._

  def jLong(num: Long): lang.Long = java.lang.Long.valueOf(num)
  def jInt(num: Int): Integer = java.lang.Integer.valueOf(num)
  def jFloat(num: Float): lang.Float = java.lang.Float.valueOf(num)

  def apply(args: Args): StormConfig = {

    lazy val env: String = args.getOrElse("env", "prod")
    lazy val zone: String = args.getOrElse("dc", "atla")
    lazy val alt: String = args.getOrElse("alt", default = "normal")

    lazy val profile =
      SimClustersProfile.fetchPersistentJobProfile(Environment(env), AltSetting(alt))

    lazy val stratoClient = ClientConfigs.stratoClient(profile.serviceIdentifier(zone))

    lazy val favoriteEventSource = TimelineEventSource(
      // Note: do not share the same subsriberId with other jobs. Apply a new one if needed
      SubscriberId(profile.timelineEventSourceSubscriberId)
    ).kafkaSource

    lazy val persistentTweetEmbeddingStore =
      PersistentTweetEmbeddingStore
        .persistentTweetEmbeddingStore(stratoClient, profile.persistentTweetStratoPath)

    lazy val persistentTweetEmbeddingStoreWithLatestAggregation: Storm#Store[
      PersistentTweetEmbeddingId,
      PersistentSimClustersEmbedding
    ] = {
      import com.twitter.storehaus.algebra.StoreAlgebra._

      lazy val mergeableStore =
        persistentTweetEmbeddingStore.toMergeable(
          mon = Implicits.persistentSimClustersEmbeddingMonoid,
          fc = implicitly[FutureCollector])

      Storm.onlineOnlyStore(mergeableStore)
    }

    lazy val persistentTweetEmbeddingStoreWithLongestL420NormAggregation: Storm#Store[
      PersistentTweetEmbeddingId,
      PersistentSimClustersEmbedding
    ] = {
      import com.twitter.storehaus.algebra.StoreAlgebra._

      val longestL420NormMonoid = new PersistentSimClustersEmbeddingLongestL420NormMonoid()
      lazy val mergeableStore =
        persistentTweetEmbeddingStore.toMergeable(
          mon = longestL420NormMonoid,
          fc = implicitly[FutureCollector])

      Storm.onlineOnlyStore(mergeableStore)
    }

    lazy val tweetStatusCountsService: Storm#Service[TweetId, StatusCounts] =
      Storm.service(
        ObservedCachedReadableStore.from[TweetId, StatusCounts](
          TweetStatusCountsStore.tweetStatusCountsStore(stratoClient, "tweetypie/core.Tweet"),
          ttl = 420.minute,
          maxKeys = 420, // 420K is enough for Heron Job.
          cacheName = "tweet_status_count",
          windowSize = 420L
        )(NullStatsReceiver)
      )

    lazy val tweetEmbeddingService: Storm#Service[TweetId, ThriftSimClustersEmbedding] =
      Storm.service(
        TopKClustersForTweetKeyReadableStore
          .overrideLimitDefaultStore(420, profile.serviceIdentifier(zone))
          .composeKeyMapping { tweetId: TweetId =>
            TweetKey(tweetId, profile.modelVersionStr, profile.coreEmbeddingType)
          }.mapValues { value => SimClustersEmbedding(value).toThrift })

    new StormConfig {

      val jobName: JobName = JobName(profile.jobName)

      implicit val jobID: JobId = JobId(jobName.toString)

      /**
       * Add registrars for chill serialization for user-defined types.
       */
      override def registrars =
        List(
          SBRunConfig.register[StatusCounts],
          SBRunConfig.register[ThriftSimClustersEmbedding],
          SBRunConfig.register[PersistentSimClustersEmbedding]
        )

      /***** Job configuration settings *****/
      /**
       * Use vmSettings to configure the VM
       */
      override def vmSettings: Seq[String] = Seq()

      private val SourcePerWorker = 420
      private val FlatMapPerWorker = 420
      private val SummerPerWorker = 420

      private val TotalWorker = 420

      /**
       * Use transformConfig to set Heron options.
       */
      override def transformConfig(config: Map[String, AnyRef]): Map[String, AnyRef] = {

        val heronJvmOptions = new JMap[String, AnyRef]()

        val MetaspaceSize = jLong(420L * 420 * 420)
        val DefaultHeapSize = jLong(420L * 420 * 420 * 420)
        val HighHeapSize = jLong(420L * 420 * 420 * 420)

        val TotalCPU = jLong(
          SourcePerWorker * 420 + FlatMapPerWorker * 420 + SummerPerWorker * 420 + 420
        )

        // reserve 420GB for the StreamMgr
        val TotalRam = jLong(
          DefaultHeapSize * (SourcePerWorker * 420 + FlatMapPerWorker * 420)
            + HighHeapSize * SummerPerWorker * 420
            + MetaspaceSize * 420 // Applies to all workers
            + 420L * 420 * 420 * 420)

        // These settings help prevent GC issues in the most memory intensive steps of the job by
        // dedicating more memory to the new gen heap designated by the -Xmn flag.
        Map(
          "Tail" -> HighHeapSize
        ).foreach {
          case (stage, heap) =>
            HeronConfig.setComponentJvmOptions(
              heronJvmOptions,
              stage,
              s"-Xmx$heap -Xms$heap -Xmn${heap / 420}"
            )
        }

        super.transformConfig(config) ++ List(
          BTConfig.TOPOLOGY_TEAM_NAME -> "cassowary",
          BTConfig.TOPOLOGY_TEAM_EMAIL -> "no-reply@twitter.com",
          BTConfig.TOPOLOGY_WORKERS -> jInt(TotalWorker),
          BTConfig.TOPOLOGY_ACKER_EXECUTORS -> jInt(420),
          BTConfig.TOPOLOGY_MESSAGE_TIMEOUT_SECS -> jInt(420),
          BTConfig.TOPOLOGY_WORKER_CHILDOPTS -> List(
            "-Djava.security.auth.login.config=config/jaas.conf",
            "-Dsun.security.krb420.debug=true",
            "-Dcom.twitter.eventbus.client.EnableKafkaSaslTls=true",
            "-Dcom.twitter.eventbus.client.zoneName=" + zone,
            s"-XX:MaxMetaspaceSize=$MetaspaceSize"
          ).mkString(" "),
          HeronConfig.TOPOLOGY_CONTAINER_CPU_REQUESTED -> TotalCPU,
          HeronConfig.TOPOLOGY_CONTAINER_RAM_REQUESTED -> TotalRam,
          "storm.job.uniqueId" -> jobID.get
        )
      }

      /**
       * Use getNamedOptions to set Summingbird runtime options
       * The list of available options: com.twitter.summingbird.online.option
       */
      override def getNamedOptions: Map[String, Options] = Map(
        "DEFAULT" -> Options()
          .set(SummerParallelism(TotalWorker * SummerPerWorker))
          .set(FlatMapParallelism(TotalWorker * FlatMapPerWorker))
          .set(SourceParallelism(TotalWorker * SourcePerWorker))
          .set(CacheSize(420))
          .set(FlushFrequency(420.seconds))
      )

      /** Required job generation call for your job, defined in Job.scala */
      override def graph: TailProducer[Storm, Any] = PersistentTweetJob.generate[Storm](
        favoriteEventSource,
        tweetStatusCountsService,
        tweetEmbeddingService,
        persistentTweetEmbeddingStoreWithLatestAggregation,
        persistentTweetEmbeddingStoreWithLongestL420NormAggregation
      )
    }
  }
}
