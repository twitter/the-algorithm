load("@io_bazel_rules_scala//scala:scala.bzl", "scala_library", "scala_binary", "scala_test")

scala_library(
    name = "follow-recommendations-service_lib",
    srcs = glob(["server/src/main/scala/**/*.scala"]),
    deps = [
        "//finagle/finagle-zipkin-scribe/src/main/scala",
        "//finatra/inject/inject-logback/src/main/scala",
        "//loglens/loglens-logback/src/main/scala/com/twitter/loglens/logback",
        "//twitter-server-internal/src/main/scala",
        "//twitter-server/logback-classic/src/main/scala",
    ],
    visibility = ["//visibility:public"],
)

scala_binary(
    name = "follow-recommendations-service",
    main_class = "com.twitter.follow_recommendations.FollowRecommendationsServiceThriftServerMain",
    deps = [
        ":follow-recommendations-service_lib",
        "//3rdparty/jvm/ch/qos/logback:logback-classic",
    ],
)

scala_test(
    name = "follow-recommendations-service-test",
    srcs = glob(["server/src/test/scala/**/*.scala"]),
    deps = [":follow-recommendations-service_lib"],
    visibility = ["//visibility:public"],
)

# Aurora Workflows build phase convention requires a jvm_app named with ${project-name}-app
# This is a sample definition, please adjust it to your needs
java_binary(
    name = "follow-recommendations-service-app",
    srcs = [],
    main_class = "com.twitter.follow_recommendations.FollowRecommendationsServiceThriftServerMain",
    deps = [":follow-recommendations-service"],
    resources = glob(["server/src/main/resources/**"]),
)
