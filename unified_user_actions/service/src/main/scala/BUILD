jvm_binary(
    name = "uua-tls-favs-bin",
    basename = "uua-tls-favs-bin",
    main = "com.twitter.unified_user_actions.service.TlsFavsServiceMain",
    runtime_platform = "java11",
    tags = ["bazel-compatible"],
    dependencies = [
        "3rdparty/jvm/ch/qos/logback:logback-classic",
        "loglens/loglens-logback/src/main/scala/com/twitter/loglens/logback",
        "twitter-server-internal/src/main/scala",
        "twitter-server/logback-classic/src/main/scala",
        "unified_user_actions/service/src/main/resources",
        "unified_user_actions/service/src/main/scala/com/twitter/unified_user_actions/service:tls-favs",
    ],
)

jvm_app(
    name = "uua-tls-favs",
    archive = "zip",
    binary = ":uua-tls-favs-bin",
    bundles = [
        bundle(
            fileset = ["**/*"],
            owning_target = "unified_user_actions/service/src/main/resources:files",
            rel_path = "unified_user_actions/service/src/main/resources",
        ),
    ],
    tags = ["bazel-compatible"],
)

jvm_binary(
    name = "uua-client-event-bin",
    basename = "uua-client-event-bin",
    main = "com.twitter.unified_user_actions.service.ClientEventServiceMain",
    runtime_platform = "java11",
    tags = ["bazel-compatible"],
    dependencies = [
        "3rdparty/jvm/ch/qos/logback:logback-classic",
        "loglens/loglens-logback/src/main/scala/com/twitter/loglens/logback",
        "twitter-server-internal/src/main/scala",
        "twitter-server/logback-classic/src/main/scala",
        "unified_user_actions/service/src/main/resources",
        "unified_user_actions/service/src/main/scala/com/twitter/unified_user_actions/service:client-event",
    ],
)

jvm_app(
    name = "uua-client-event",
    archive = "zip",
    binary = ":uua-client-event-bin",
    bundles = [
        bundle(
            fileset = ["**/*"],
            owning_target = "unified_user_actions/service/src/main/resources:files",
            rel_path = "unified_user_actions/service/src/main/resources",
        ),
    ],
    tags = ["bazel-compatible"],
)


jvm_binary(
    name = "uua-tweetypie-event-bin",
    basename = "uua-tweetypie-event-bin",
    main = "com.twitter.unified_user_actions.service.TweetypieEventServiceMain",
    runtime_platform = "java11",
    tags = ["bazel-compatible"],
    dependencies = [
        "3rdparty/jvm/ch/qos/logback:logback-classic",
        "loglens/loglens-logback/src/main/scala/com/twitter/loglens/logback",
        "twitter-server-internal/src/main/scala",
        "twitter-server/logback-classic/src/main/scala",
        "unified_user_actions/service/src/main/resources",
        "unified_user_actions/service/src/main/scala/com/twitter/unified_user_actions/service:tweetypie-event",
    ],
)

jvm_app(
    name = "uua-tweetypie-event",
    archive = "zip",
    binary = ":uua-tweetypie-event-bin",
    bundles = [
        bundle(
            fileset = ["**/*"],
            owning_target = "unified_user_actions/service/src/main/resources:files",
            rel_path = "unified_user_actions/service/src/main/resources",
        ),
    ],
    tags = ["bazel-compatible"],
)

jvm_binary(
    name = "uua-social-graph-bin",
    basename = "uua-social-graph-bin",
    main = "com.twitter.unified_user_actions.service.SocialGraphServiceMain",
    runtime_platform = "java11",
    tags = ["bazel-compatible"],
    dependencies = [
        "3rdparty/jvm/ch/qos/logback:logback-classic",
        "loglens/loglens-logback/src/main/scala/com/twitter/loglens/logback",
        "twitter-server-internal/src/main/scala",
        "twitter-server/logback-classic/src/main/scala",
        "unified_user_actions/service/src/main/resources",
        "unified_user_actions/service/src/main/scala/com/twitter/unified_user_actions/service:social-graph-event",
    ],
)

jvm_app(
    name = "uua-social-graph",
    archive = "zip",
    binary = ":uua-social-graph-bin",
    bundles = [
        bundle(
            fileset = ["**/*"],
            owning_target = "unified_user_actions/service/src/main/resources:files",
            rel_path = "unified_user_actions/service/src/main/resources",
        ),
    ],
    tags = ["bazel-compatible"],
)

jvm_binary(
    name = "uua-email-notification-event-bin",
    basename = "uua-email-notification-event-bin",
    main = "com.twitter.unified_user_actions.service.EmailNotificationEventServiceMain",
    runtime_platform = "java11",
    tags = ["bazel-compatible"],
    dependencies = [
        "3rdparty/jvm/ch/qos/logback:logback-classic",
        "loglens/loglens-logback/src/main/scala/com/twitter/loglens/logback",
        "twitter-server-internal/src/main/scala",
        "twitter-server/logback-classic/src/main/scala",
        "unified_user_actions/service/src/main/resources",
        "unified_user_actions/service/src/main/scala/com/twitter/unified_user_actions/service:email-notification-event",
    ],
)

jvm_app(
    name = "uua-email-notification-event",
    archive = "zip",
    binary = ":uua-email-notification-event-bin",
    bundles = [
        bundle(
            fileset = ["**/*"],
            owning_target = "unified_user_actions/service/src/main/resources:files",
            rel_path = "unified_user_actions/service/src/main/resources",
        ),
    ],
    tags = ["bazel-compatible"],
)

jvm_binary(
    name = "uua-user-modification-bin",
    basename = "uua-user-modification-bin",
    main = "com.twitter.unified_user_actions.service.UserModificationServiceMain",
    runtime_platform = "java11",
    tags = ["bazel-compatible"],
    dependencies = [
        "3rdparty/jvm/ch/qos/logback:logback-classic",
        "loglens/loglens-logback/src/main/scala/com/twitter/loglens/logback",
        "twitter-server-internal/src/main/scala",
        "twitter-server/logback-classic/src/main/scala",
        "unified_user_actions/service/src/main/resources",
        "unified_user_actions/service/src/main/scala/com/twitter/unified_user_actions/service:user-modification-event",
    ],
)

jvm_app(
    name = "uua-user-modification",
    archive = "zip",
    binary = ":uua-user-modification-bin",
    bundles = [
        bundle(
            fileset = ["**/*"],
            owning_target = "unified_user_actions/service/src/main/resources:files",
            rel_path = "unified_user_actions/service/src/main/resources",
        ),
    ],
    tags = ["bazel-compatible"],
)

jvm_binary(
    name = "uua-ads-callback-engagements-bin",
    basename = "uua-ads-callback-engagements-bin",
    main = "com.twitter.unified_user_actions.service.AdsCallbackEngagementsServiceMain",
    runtime_platform = "java11",
    tags = ["bazel-compatible"],
    dependencies = [
        "3rdparty/jvm/ch/qos/logback:logback-classic",
        "loglens/loglens-logback/src/main/scala/com/twitter/loglens/logback",
        "twitter-server-internal/src/main/scala",
        "twitter-server/logback-classic/src/main/scala",
        "unified_user_actions/service/src/main/resources",
        "unified_user_actions/service/src/main/scala/com/twitter/unified_user_actions/service:ads-callback-engagements",
    ],
)

jvm_app(
    name = "uua-ads-callback-engagements",
    archive = "zip",
    binary = ":uua-ads-callback-engagements-bin",
    bundles = [
        bundle(
            fileset = ["**/*"],
            owning_target = "unified_user_actions/service/src/main/resources:files",
            rel_path = "unified_user_actions/service/src/main/resources",
        ),
    ],
    tags = ["bazel-compatible"],
)

jvm_binary(
    name = "uua-favorite-archival-events-bin",
    basename = "uua-favorite-archival-events-bin",
    main = "com.twitter.unified_user_actions.service.FavoriteArchivalEventsServiceMain",
    runtime_platform = "java11",
    tags = ["bazel-compatible"],
    dependencies = [
        "3rdparty/jvm/ch/qos/logback:logback-classic",
        "loglens/loglens-logback/src/main/scala/com/twitter/loglens/logback",
        "twitter-server-internal/src/main/scala",
        "twitter-server/logback-classic/src/main/scala",
        "unified_user_actions/service/src/main/resources",
        "unified_user_actions/service/src/main/scala/com/twitter/unified_user_actions/service:favorite-archival-events",
    ],
)

jvm_app(
    name = "uua-favorite-archival-events",
    archive = "zip",
    binary = ":uua-favorite-archival-events-bin",
    bundles = [
        bundle(
            fileset = ["**/*"],
            owning_target = "unified_user_actions/service/src/main/resources:files",
            rel_path = "unified_user_actions/service/src/main/resources",
        ),
    ],
    tags = ["bazel-compatible"],
)

jvm_binary(
    name = "uua-retweet-archival-events-bin",
    basename = "uua-retweet-archival-events-bin",
    main = "com.twitter.unified_user_actions.service.RetweetArchivalEventsServiceMain",
    runtime_platform = "java11",
    tags = ["bazel-compatible"],
    dependencies = [
        "3rdparty/jvm/ch/qos/logback:logback-classic",
        "loglens/loglens-logback/src/main/scala/com/twitter/loglens/logback",
        "twitter-server-internal/src/main/scala",
        "twitter-server/logback-classic/src/main/scala",
        "unified_user_actions/service/src/main/resources",
        "unified_user_actions/service/src/main/scala/com/twitter/unified_user_actions/service:retweet-archival-events",
    ],
)

jvm_app(
    name = "uua-retweet-archival-events",
    archive = "zip",
    binary = ":uua-retweet-archival-events-bin",
    bundles = [
        bundle(
            fileset = ["**/*"],
            owning_target = "unified_user_actions/service/src/main/resources:files",
            rel_path = "unified_user_actions/service/src/main/resources",
        ),
    ],
    tags = ["bazel-compatible"],
)

jvm_binary(
    name = "rekey-uua-bin",
    basename = "rekey-uua-bin",
    main = "com.twitter.unified_user_actions.service.RekeyUuaServiceMain",
    runtime_platform = "java11",
    tags = ["bazel-compatible"],
    dependencies = [
        "3rdparty/jvm/ch/qos/logback:logback-classic",
        "loglens/loglens-logback/src/main/scala/com/twitter/loglens/logback",
        "twitter-server-internal/src/main/scala",
        "twitter-server/logback-classic/src/main/scala",
        "unified_user_actions/service/src/main/resources",
        "unified_user_actions/service/src/main/scala/com/twitter/unified_user_actions/service:rekey-uua",
    ],
)

jvm_app(
    name = "rekey-uua",
    archive = "zip",
    binary = ":rekey-uua-bin",
    bundles = [
        bundle(
            fileset = ["**/*"],
            owning_target = "unified_user_actions/service/src/main/resources:files",
            rel_path = "unified_user_actions/service/src/main/resources",
        ),
    ],
    tags = ["bazel-compatible"],
)

jvm_binary(
    name = "rekey-uua-iesource-bin",
    basename = "rekey-uua-iesource-bin",
    main = "com.twitter.unified_user_actions.service.RekeyUuaIesourceServiceMain",
    runtime_platform = "java11",
    tags = ["bazel-compatible"],
    dependencies = [
        "3rdparty/jvm/ch/qos/logback:logback-classic",
        "loglens/loglens-logback/src/main/scala/com/twitter/loglens/logback",
        "twitter-server-internal/src/main/scala",
        "twitter-server/logback-classic/src/main/scala",
        "unified_user_actions/service/src/main/resources",
        "unified_user_actions/service/src/main/scala/com/twitter/unified_user_actions/service:rekey-uua-iesource",
    ],
)

jvm_app(
    name = "rekey-uua-iesource",
    archive = "zip",
    binary = ":rekey-uua-iesource-bin",
    bundles = [
        bundle(
            fileset = ["**/*"],
            owning_target = "unified_user_actions/service/src/main/resources:files",
            rel_path = "unified_user_actions/service/src/main/resources",
        ),
    ],
    tags = ["bazel-compatible"],
)

jvm_binary(
    name = "uua-enrichment-planner-bin",
    basename = "uua-enrichment-planner-bin",
    main = "com.twitter.unified_user_actions.service.EnrichmentPlannerServiceMain",
    runtime_platform = "java11",
    tags = ["bazel-compatible"],
    dependencies = [
        "3rdparty/jvm/ch/qos/logback:logback-classic",
        "loglens/loglens-logback/src/main/scala/com/twitter/loglens/logback",
        "twitter-server-internal/src/main/scala",
        "twitter-server/logback-classic/src/main/scala",
        "unified_user_actions/service/src/main/resources",
        "unified_user_actions/service/src/main/scala/com/twitter/unified_user_actions/service:enrichment-planner",
    ],
)

jvm_app(
    name = "uua-enrichment-planner",
    archive = "zip",
    binary = ":uua-enrichment-planner-bin",
    bundles = [
        bundle(
            fileset = ["**/*"],
            owning_target = "unified_user_actions/service/src/main/resources:files",
            rel_path = "unified_user_actions/service/src/main/resources",
        ),
    ],
    tags = ["bazel-compatible"],
)

jvm_binary(
    name = "uua-enricher-bin",
    basename = "uua-enricher-bin",
    main = "com.twitter.unified_user_actions.service.EnricherServiceMain",
    runtime_platform = "java11",
    tags = ["bazel-compatible"],
    dependencies = [
        "3rdparty/jvm/ch/qos/logback:logback-classic",
        "loglens/loglens-logback/src/main/scala/com/twitter/loglens/logback",
        "twitter-server-internal/src/main/scala",
        "twitter-server/logback-classic/src/main/scala",
        "unified_user_actions/service/src/main/resources",
        "unified_user_actions/service/src/main/scala/com/twitter/unified_user_actions/service:enricher",
    ],
)

jvm_app(
    name = "uua-enricher",
    archive = "zip",
    binary = ":uua-enricher-bin",
    bundles = [
        bundle(
            fileset = ["**/*"],
            owning_target = "unified_user_actions/service/src/main/resources:files",
            rel_path = "unified_user_actions/service/src/main/resources",
        ),
    ],
    tags = ["bazel-compatible"],
)
