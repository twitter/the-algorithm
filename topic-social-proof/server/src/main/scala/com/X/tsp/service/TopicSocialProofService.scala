package com.X.tsp.service

import com.X.abdecider.ABDeciderFactory
import com.X.abdecider.LoggingABDecider
import com.X.tsp.thriftscala.TspTweetInfo
import com.X.discovery.common.configapi.FeatureContextBuilder
import com.X.finagle.mtls.authentication.ServiceIdentifier
import com.X.finagle.stats.StatsReceiver
import com.X.gizmoduck.thriftscala.LookupContext
import com.X.gizmoduck.thriftscala.QueryFields
import com.X.gizmoduck.thriftscala.User
import com.X.gizmoduck.thriftscala.UserService
import com.X.hermit.store.gizmoduck.GizmoduckUserStore
import com.X.logging.Logger
import com.X.simclusters_v2.common.SemanticCoreEntityId
import com.X.simclusters_v2.common.TweetId
import com.X.simclusters_v2.common.UserId
import com.X.spam.rtf.thriftscala.SafetyLevel
import com.X.stitch.storehaus.StitchOfReadableStore
import com.X.storehaus.ReadableStore
import com.X.strato.client.{Client => StratoClient}
import com.X.timelines.configapi
import com.X.timelines.configapi.CompositeConfig
import com.X.tsp.common.FeatureSwitchConfig
import com.X.tsp.common.FeatureSwitchesBuilder
import com.X.tsp.common.LoadShedder
import com.X.tsp.common.ParamsBuilder
import com.X.tsp.common.RecTargetFactory
import com.X.tsp.common.TopicSocialProofDecider
import com.X.tsp.handlers.TopicSocialProofHandler
import com.X.tsp.stores.LocalizedUttRecommendableTopicsStore
import com.X.tsp.stores.LocalizedUttTopicNameRequest
import com.X.tsp.stores.TopicResponses
import com.X.tsp.stores.TopicSocialProofStore
import com.X.tsp.stores.TopicSocialProofStore.TopicSocialProof
import com.X.tsp.stores.TopicStore
import com.X.tsp.stores.UttTopicFilterStore
import com.X.tsp.thriftscala.TopicSocialProofRequest
import com.X.tsp.thriftscala.TopicSocialProofResponse
import com.X.util.JavaTimer
import com.X.util.Timer
import javax.inject.Inject
import javax.inject.Singleton
import com.X.topiclisting.TopicListing
import com.X.topiclisting.utt.UttLocalization

@Singleton
class TopicSocialProofService @Inject() (
  topicSocialProofStore: ReadableStore[TopicSocialProofStore.Query, Seq[TopicSocialProof]],
  tweetInfoStore: ReadableStore[TweetId, TspTweetInfo],
  serviceIdentifier: ServiceIdentifier,
  stratoClient: StratoClient,
  gizmoduck: UserService.MethodPerEndpoint,
  topicListing: TopicListing,
  uttLocalization: UttLocalization,
  decider: TopicSocialProofDecider,
  loadShedder: LoadShedder,
  stats: StatsReceiver) {

  import TopicSocialProofService._

  private val statsReceiver = stats.scope("topic-social-proof-management")

  private val isProd: Boolean = serviceIdentifier.environment == "prod"

  private val optOutStratoStorePath: String =
    if (isProd) "interests/optOutInterests" else "interests/staging/optOutInterests"

  private val notInterestedInStorePath: String =
    if (isProd) "interests/notInterestedTopicsGetter"
    else "interests/staging/notInterestedTopicsGetter"

  private val userOptOutTopicsStore: ReadableStore[UserId, TopicResponses] =
    TopicStore.userOptOutTopicStore(stratoClient, optOutStratoStorePath)(
      statsReceiver.scope("ints_interests_opt_out_store"))
  private val explicitFollowingTopicsStore: ReadableStore[UserId, TopicResponses] =
    TopicStore.explicitFollowingTopicStore(stratoClient)(
      statsReceiver.scope("ints_explicit_following_interests_store"))
  private val userNotInterestedInTopicsStore: ReadableStore[UserId, TopicResponses] =
    TopicStore.notInterestedInTopicsStore(stratoClient, notInterestedInStorePath)(
      statsReceiver.scope("ints_not_interested_in_store"))

  private lazy val localizedUttRecommendableTopicsStore: ReadableStore[
    LocalizedUttTopicNameRequest,
    Set[
      SemanticCoreEntityId
    ]
  ] = new LocalizedUttRecommendableTopicsStore(uttLocalization)

  implicit val timer: Timer = new JavaTimer(true)

  private lazy val uttTopicFilterStore = new UttTopicFilterStore(
    topicListing = topicListing,
    userOptOutTopicsStore = userOptOutTopicsStore,
    explicitFollowingTopicsStore = explicitFollowingTopicsStore,
    notInterestedTopicsStore = userNotInterestedInTopicsStore,
    localizedUttRecommendableTopicsStore = localizedUttRecommendableTopicsStore,
    timer = timer,
    stats = statsReceiver.scope("UttTopicFilterStore")
  )

  private lazy val scribeLogger: Option[Logger] = Some(Logger.get("client_event"))

  private lazy val abDecider: LoggingABDecider =
    ABDeciderFactory(
      abDeciderYmlPath = configRepoDirectory + "/abdecider/abdecider.yml",
      scribeLogger = scribeLogger,
      decider = None,
      environment = Some("production"),
    ).buildWithLogging()

  private val builder: FeatureSwitchesBuilder = FeatureSwitchesBuilder(
    statsReceiver = statsReceiver.scope("featureswitches-v2"),
    abDecider = abDecider,
    featuresDirectory = "features/topic-social-proof/main",
    configRepoDirectory = configRepoDirectory,
    addServiceDetailsFromAurora = !serviceIdentifier.isLocal,
    fastRefresh = !isProd
  )

  private lazy val overridesConfig: configapi.Config = {
    new CompositeConfig(
      Seq(
        FeatureSwitchConfig.config
      )
    )
  }

  private val featureContextBuilder: FeatureContextBuilder = FeatureContextBuilder(builder.build())

  private val paramsBuilder: ParamsBuilder = ParamsBuilder(
    featureContextBuilder,
    abDecider,
    overridesConfig,
    statsReceiver.scope("params")
  )

  private val userStore: ReadableStore[UserId, User] = {
    val queryFields: Set[QueryFields] = Set(
      QueryFields.Profile,
      QueryFields.Account,
      QueryFields.Roles,
      QueryFields.Discoverability,
      QueryFields.Safety,
      QueryFields.Takedowns
    )
    val context: LookupContext = LookupContext(safetyLevel = Some(SafetyLevel.Recommendations))

    GizmoduckUserStore(
      client = gizmoduck,
      queryFields = queryFields,
      context = context,
      statsReceiver = statsReceiver.scope("gizmoduck")
    )
  }

  private val recTargetFactory: RecTargetFactory = RecTargetFactory(
    abDecider,
    userStore,
    paramsBuilder,
    statsReceiver
  )

  private val topicSocialProofHandler =
    new TopicSocialProofHandler(
      topicSocialProofStore,
      tweetInfoStore,
      uttTopicFilterStore,
      recTargetFactory,
      decider,
      statsReceiver.scope("TopicSocialProofHandler"),
      loadShedder,
      timer)

  val topicSocialProofHandlerStoreStitch: TopicSocialProofRequest => com.X.stitch.Stitch[
    TopicSocialProofResponse
  ] = StitchOfReadableStore(topicSocialProofHandler.toReadableStore)
}

object TopicSocialProofService {
  private val configRepoDirectory = "/usr/local/config"
}
