jvm_binary(
    name = "bin",
    basename = "simclusters-ann",
    main = "com.twitter.simclustersann.SimClustersAnnServerMain",
    runtime_platform = "java11",
    tags = ["bazel-compatible"],
    dependencies = [
        "finagle/finagle-zipkin-scribe/src/main/scala",
        "finatra/inject/inject-logback/src/main/scala",
        "loglens/loglens-logback/src/main/scala/com/twitter/loglens/logback",
        "simclusters-ann/server/src/main/scala/com/twitter/simclustersann",
        "twitter-server-internal/src/main/scala",
        "twitter-server/logback-classic/src/main/scala",
    ],
)

#  Aurora Workflows build phase convention requires a jvm_app named with ${project-name}-app
jvm_app(
    name = "simclusters-ann-app",
    archive = "zip",
    binary = ":bin",
    tags = ["bazel-compatible"],
)
