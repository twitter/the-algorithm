jvm_binary(
    name = "bin",
    basename = "topic-social-proof",
    main = "com.X.tsp.TopicSocialProofStratoFedServerMain",
    runtime_platform = "java11",
    tags = [
        "bazel-compatible",
    ],
    dependencies = [
        "strato/src/main/scala/com/X/strato/logging/logback",
        "topic-social-proof/server/src/main/resources",
        "topic-social-proof/server/src/main/scala/com/X/tsp",
    ],
)

#  Aurora Workflows build phase convention requires a jvm_app named with ${project-name}-app
jvm_app(
    name = "topic-social-proof-app",
    archive = "zip",
    binary = ":bin",
    tags = [
        "bazel-compatible",
    ],
)
