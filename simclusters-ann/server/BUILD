jvm_binary(
    name = "bin",
    basename = "simclusters-ann",
    main = "com.X.simclustersann.SimClustersAnnServerMain",
    runtime_platform = "java11",
    tags = ["bazel-compatible"],
    dependencies = [
        "finagle/finagle-zipkin-scribe/src/main/scala",
        "finatra/inject/inject-logback/src/main/scala",
        "loglens/loglens-logback/src/main/scala/com/X/loglens/logback",
        "simclusters-ann/server/src/main/scala/com/X/simclustersann",
        "X-server-internal/src/main/scala",
        "X-server/logback-classic/src/main/scala",
    ],
)

#  Aurora Workflows build phase convention requires a jvm_app named with ${project-name}-app
jvm_app(
    name = "simclusters-ann-app",
    archive = "zip",
    binary = ":bin",
    tags = ["bazel-compatible"],
)
