scala_library(
    name = "tls-favs",
    sources = ["TlsFavsService.scala"],
    tags = ["bazel-compatible"],
    dependencies = [
        "3rdparty/jvm/org/apache/kafka:kafka-clients",
        "decider/src/main/scala",
        "finatra-internal/decider/src/main/scala",
        "finatra/inject/inject-server/src/main/scala/com/twitter/inject/server",
        "kafka/finagle-kafka/finatra-kafka/src/main/scala",
        "kafka/libs/src/main/scala/com/twitter/kafka/client/processor",
        "src/thrift/com/twitter/timelineservice/server/internal:thrift-scala",
        "twitter-server/server/src/main/scala",
        "unified_user_actions/service/src/main/scala/com/twitter/unified_user_actions/service/module:tls-favs",
        "unified_user_actions/thrift/src/main/thrift/com/twitter/unified_user_actions:unified_user_actions-scala",
        "util/util-app/src/main/scala",
        "util/util-stats/src/main/scala/com/twitter/finagle/stats",
    ],
)

scala_library(
    name = "client-event",
    sources = ["ClientEventService.scala"],
    tags = ["bazel-compatible"],
    dependencies = [
        "3rdparty/jvm/org/apache/kafka:kafka-clients",
        "decider/src/main/scala",
        "finatra-internal/decider/src/main/scala",
        "finatra/inject/inject-server/src/main/scala/com/twitter/inject/server",
        "kafka/finagle-kafka/finatra-kafka/src/main/scala",
        "kafka/libs/src/main/scala/com/twitter/kafka/client/processor",
        "twadoop_config/configuration/log_categories/group/scribelib:client_event-scala",
        "twitter-server/server/src/main/scala",
        "unified_user_actions/service/src/main/scala/com/twitter/unified_user_actions/service/module:client-event",
        "unified_user_actions/thrift/src/main/thrift/com/twitter/unified_user_actions:unified_user_actions-scala",
        "util/util-app/src/main/scala",
        "util/util-stats/src/main/scala/com/twitter/finagle/stats",
    ],
)

scala_library(
    name = "tweetypie-event",
    sources = ["TweetypieEventService.scala"],
    tags = ["bazel-compatible"],
    dependencies = [
        "3rdparty/jvm/org/apache/kafka:kafka-clients",
        "decider/src/main/scala",
        "finatra-internal/decider/src/main/scala",
        "finatra/inject/inject-server/src/main/scala/com/twitter/inject/server",
        "kafka/finagle-kafka/finatra-kafka/src/main/scala",
        "kafka/libs/src/main/scala/com/twitter/kafka/client/processor",
        "twadoop_config/configuration/log_categories/group/scribelib:client_event-scala",
        "twitter-server/server/src/main/scala",
        "unified_user_actions/service/src/main/scala/com/twitter/unified_user_actions/service/module:tweetypie-event",
        "unified_user_actions/thrift/src/main/thrift/com/twitter/unified_user_actions:unified_user_actions-scala",
        "util/util-app/src/main/scala",
        "util/util-stats/src/main/scala/com/twitter/finagle/stats",
    ],
)

scala_library(
    name = "social-graph-event",
    sources = ["SocialGraphService.scala"],
    tags = ["bazel-compatible"],
    dependencies = [
        "3rdparty/jvm/org/apache/kafka:kafka-clients",
        "decider/src/main/scala",
        "finatra-internal/decider/src/main/scala",
        "finatra/inject/inject-server/src/main/scala/com/twitter/inject/server",
        "kafka/finagle-kafka/finatra-kafka/src/main/scala",
        "kafka/libs/src/main/scala/com/twitter/kafka/client/processor",
        "twitter-server/server/src/main/scala",
        "unified_user_actions/service/src/main/scala/com/twitter/unified_user_actions/service/module:social-graph-event",
        "unified_user_actions/thrift/src/main/thrift/com/twitter/unified_user_actions:unified_user_actions-scala",
        "util/util-app/src/main/scala",
        "util/util-stats/src/main/scala/com/twitter/finagle/stats",
    ],
)

scala_library(
    name = "email-notification-event",
    sources = ["EmailNotificationEventService.scala"],
    tags = ["bazel-compatible"],
    dependencies = [
        "3rdparty/jvm/org/apache/kafka:kafka-clients",
        "decider/src/main/scala",
        "finatra-internal/decider/src/main/scala",
        "finatra/inject/inject-server/src/main/scala/com/twitter/inject/server",
        "kafka/finagle-kafka/finatra-kafka/src/main/scala",
        "kafka/libs/src/main/scala/com/twitter/kafka/client/processor",
        "twitter-server/server/src/main/scala",
        "unified_user_actions/service/src/main/scala/com/twitter/unified_user_actions/service/module:email-notification-event",
        "unified_user_actions/thrift/src/main/thrift/com/twitter/unified_user_actions:unified_user_actions-scala",
        "util/util-app/src/main/scala",
        "util/util-stats/src/main/scala/com/twitter/finagle/stats",
    ],
)

scala_library(
    name = "user-modification-event",
    sources = ["UserModificationService.scala"],
    tags = [
        "bazel-compatible",
        "bazel-only",
    ],
    dependencies = [
        "3rdparty/jvm/org/apache/kafka:kafka-clients",
        "decider/src/main/scala",
        "finatra-internal/decider/src/main/scala",
        "finatra/inject/inject-server/src/main/scala/com/twitter/inject/server",
        "kafka/finagle-kafka/finatra-kafka/src/main/scala",
        "kafka/libs/src/main/scala/com/twitter/kafka/client/processor",
        "twitter-server/server/src/main/scala",
        "unified_user_actions/service/src/main/scala/com/twitter/unified_user_actions/service/module:user-modification-event",
        "unified_user_actions/thrift/src/main/thrift/com/twitter/unified_user_actions:unified_user_actions-scala",
        "util/util-app/src/main/scala",
        "util/util-stats/src/main/scala/com/twitter/finagle/stats",
    ],
)

scala_library(
    name = "ads-callback-engagements",
    sources = ["AdsCallbackEngagementsService.scala"],
    tags = [
        "bazel-compatible",
        "bazel-only",
    ],
    dependencies = [
        "3rdparty/jvm/org/apache/kafka:kafka-clients",
        "decider/src/main/scala",
        "finatra-internal/decider/src/main/scala",
        "finatra/inject/inject-server/src/main/scala/com/twitter/inject/server",
        "kafka/finagle-kafka/finatra-kafka/src/main/scala",
        "kafka/libs/src/main/scala/com/twitter/kafka/client/processor",
        "twitter-server/server/src/main/scala",
        "unified_user_actions/service/src/main/scala/com/twitter/unified_user_actions/service/module:ads-callback-engagements",
        "unified_user_actions/thrift/src/main/thrift/com/twitter/unified_user_actions:unified_user_actions-scala",
        "util/util-app/src/main/scala",
        "util/util-stats/src/main/scala/com/twitter/finagle/stats",
    ],
)

scala_library(
    name = "favorite-archival-events",
    sources = ["FavoriteArchivalEventsService.scala"],
    tags = ["bazel-compatible"],
    dependencies = [
        "3rdparty/jvm/org/apache/kafka:kafka-clients",
        "decider/src/main/scala",
        "finatra-internal/decider/src/main/scala",
        "finatra/inject/inject-server/src/main/scala/com/twitter/inject/server",
        "kafka/finagle-kafka/finatra-kafka/src/main/scala",
        "kafka/libs/src/main/scala/com/twitter/kafka/client/processor",
        "twitter-server/server/src/main/scala",
        "unified_user_actions/service/src/main/scala/com/twitter/unified_user_actions/service/module:favorite-archival-events",
        "unified_user_actions/thrift/src/main/thrift/com/twitter/unified_user_actions:unified_user_actions-scala",
        "util/util-app/src/main/scala",
        "util/util-stats/src/main/scala/com/twitter/finagle/stats",
    ],
)

scala_library(
    name = "retweet-archival-events",
    sources = ["RetweetArchivalEventsService.scala"],
    tags = ["bazel-compatible"],
    dependencies = [
        "3rdparty/jvm/org/apache/kafka:kafka-clients",
        "decider/src/main/scala",
        "finatra-internal/decider/src/main/scala",
        "finatra/inject/inject-server/src/main/scala/com/twitter/inject/server",
        "kafka/finagle-kafka/finatra-kafka/src/main/scala",
        "kafka/libs/src/main/scala/com/twitter/kafka/client/processor",
        "twitter-server/server/src/main/scala",
        "unified_user_actions/service/src/main/scala/com/twitter/unified_user_actions/service/module:retweet-archival-events",
        "unified_user_actions/thrift/src/main/thrift/com/twitter/unified_user_actions:unified_user_actions-scala",
        "util/util-app/src/main/scala",
        "util/util-stats/src/main/scala/com/twitter/finagle/stats",
    ],
)

scala_library(
    name = "rekey-uua",
    sources = ["RekeyUuaService.scala"],
    tags = [
        "bazel-compatible",
        "bazel-only",
    ],
    dependencies = [
        "3rdparty/jvm/org/apache/kafka:kafka-clients",
        "decider/src/main/scala",
        "finatra-internal/decider/src/main/scala",
        "finatra/inject/inject-server/src/main/scala/com/twitter/inject/server",
        "kafka/finagle-kafka/finatra-kafka/src/main/scala",
        "kafka/libs/src/main/scala/com/twitter/kafka/client/processor",
        "twitter-server/server/src/main/scala",
        "unified_user_actions/service/src/main/scala/com/twitter/unified_user_actions/service/module:rekey-uua",
        "unified_user_actions/thrift/src/main/thrift/com/twitter/unified_user_actions:unified_user_actions-scala",
        "util/util-app/src/main/scala",
        "util/util-stats/src/main/scala/com/twitter/finagle/stats",
    ],
)

scala_library(
    name = "rekey-uua-iesource",
    sources = ["RekeyUuaIesourceService.scala"],
    tags = [
        "bazel-compatible",
        "bazel-only",
    ],
    dependencies = [
        "3rdparty/jvm/org/apache/kafka:kafka-clients",
        "decider/src/main/scala",
        "finatra-internal/decider/src/main/scala",
        "finatra/inject/inject-server/src/main/scala/com/twitter/inject/server",
        "kafka/finagle-kafka/finatra-kafka/src/main/scala",
        "kafka/libs/src/main/scala/com/twitter/kafka/client/processor",
        "twitter-server/server/src/main/scala",
        "unified_user_actions/service/src/main/scala/com/twitter/unified_user_actions/service/module:rekey-uua-iesource",
        "unified_user_actions/thrift/src/main/thrift/com/twitter/unified_user_actions:unified_user_actions-scala",
        "util/util-app/src/main/scala",
        "util/util-stats/src/main/scala/com/twitter/finagle/stats",
    ],
)

scala_library(
    name = "enrichment-planner",
    sources = ["EnrichmentPlannerService.scala"],
    tags = ["bazel-compatible"],
    dependencies = [
        "decider/src/main/scala",
        "finatra-internal/decider/src/main/scala",
        "finatra-internal/kafka-streams/kafka-streams/src/main/scala",
        "finatra-internal/kafka/src/main/scala/com/twitter/finatra/kafka/producers",
        "finatra-internal/mtls-thriftmux/src/main/scala",
        "finatra-internal/mtls/src/main/scala",
        "kafka/finagle-kafka/finatra-kafka-streams/kafka-streams-static-partitioning/src/main/scala",
        "kafka/finagle-kafka/finatra-kafka-streams/kafka-streams/src/main/scala",
        "kafka/finagle-kafka/finatra-kafka/src/main/scala",
        "unified_user_actions/enricher/src/main/scala/com/twitter/unified_user_actions/enricher/driver",
        "unified_user_actions/enricher/src/main/scala/com/twitter/unified_user_actions/enricher/hydrator:noop",
        "unified_user_actions/enricher/src/main/scala/com/twitter/unified_user_actions/enricher/partitioner:default",
        "unified_user_actions/enricher/src/main/thrift/com/twitter/unified_user_actions/enricher/internal:internal-scala",
        "unified_user_actions/thrift/src/main/thrift/com/twitter/unified_user_actions:unified_user_actions-scala",
    ],
)

scala_library(
    name = "enricher",
    sources = ["EnricherService.scala"],
    tags = ["bazel-compatible"],
    dependencies = [
        "finatra-internal/kafka-streams/kafka-streams/src/main/scala",
        "finatra-internal/mtls/src/main/scala",
        "finatra/inject/inject-server/src/main/scala/com/twitter/inject/server",
        "graphql/thrift/src/main/thrift/com/twitter/graphql:graphql-scala",
        "kafka/finagle-kafka/finatra-kafka-streams/kafka-streams-static-partitioning/src/main/scala",
        "kafka/finagle-kafka/finatra-kafka-streams/kafka-streams/src/main/scala",
        "kafka/finagle-kafka/finatra-kafka/src/main/scala",
        "twitter-server/server/src/main/scala",
        "unified_user_actions/enricher/src/main/scala/com/twitter/unified_user_actions/enricher/driver",
        "unified_user_actions/enricher/src/main/scala/com/twitter/unified_user_actions/enricher/graphql",
        "unified_user_actions/enricher/src/main/scala/com/twitter/unified_user_actions/enricher/hydrator:default",
        "unified_user_actions/enricher/src/main/scala/com/twitter/unified_user_actions/enricher/partitioner:default",
        "unified_user_actions/service/src/main/scala/com/twitter/unified_user_actions/service/module:cache",
        "unified_user_actions/service/src/main/scala/com/twitter/unified_user_actions/service/module:graphql-client",
        "unified_user_actions/thrift/src/main/thrift/com/twitter/unified_user_actions:unified_user_actions-scala",
        "util/util-app/src/main/scala",
        "util/util-stats/src/main/scala/com/twitter/finagle/stats",
    ],
)
