target(
    name = "ingester-lib",
    dependencies = [
        "src/java/com/twitter/search/common/converter/earlybird",
        "src/java/com/twitter/search/ingester/model",
        "src/java/com/twitter/search/ingester/pipeline/app",
        "src/java/com/twitter/search/ingester/pipeline/twitter",
        "src/java/com/twitter/search/ingester/pipeline/twitter/engagements",
        "src/java/com/twitter/search/ingester/pipeline/twitter/filters",
        "src/java/com/twitter/search/ingester/pipeline/twitter/kafka",
        "src/java/com/twitter/search/ingester/pipeline/twitter/thriftparse",
        "src/java/com/twitter/search/ingester/pipeline/twitter/userupdates",
        "src/java/com/twitter/search/ingester/pipeline/util",
        "src/java/com/twitter/search/ingester/util/jndi",
    ],
)

jvm_binary(
    name = "ingester-binary",
    basename = "ingester",
    main = "com.twitter.search.ingester.pipeline.app.IngesterPipelineApplication\\$Main",
    platform = "java8",
    tags = [
        "bazel-compatible",
    ],
    dependencies = [
        ":ingester-lib",
        "src/java/com/twitter/search/common/logging:search-log4j",
    ],
)
