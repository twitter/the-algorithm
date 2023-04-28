jvm_binary(
    name = "bin",
    basename = "representation-scorer",
    main = "com.twitter.representationscorer.RepresentationScorerFedServerMain",
    platform = "java8",
    tags = ["bazel-compatible"],
    dependencies = [
        "finatra/inject/inject-logback/src/main/scala",
        "loglens/loglens-logback/src/main/scala/com/twitter/loglens/logback",
        "representation-scorer/server/src/main/resources",
        "representation-scorer/server/src/main/scala/com/twitter/representationscorer",
        "twitter-server/logback-classic/src/main/scala",
    ],
)

#  Aurora Workflows build phase convention requires a jvm_app named with ${project-name}-app
jvm_app(
    name = "representation-scorer-app",
    archive = "zip",
    binary = ":bin",
    tags = ["bazel-compatible"],
)
