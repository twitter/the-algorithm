target(
    name = "ingester-lib",
    dependencies = [
        "src/java/com/X/search/common/converter/earlybird",
        "src/java/com/X/search/ingester/model",
        "src/java/com/X/search/ingester/pipeline/app",
        "src/java/com/X/search/ingester/pipeline/X",
        "src/java/com/X/search/ingester/pipeline/X/engagements",
        "src/java/com/X/search/ingester/pipeline/X/filters",
        "src/java/com/X/search/ingester/pipeline/X/kafka",
        "src/java/com/X/search/ingester/pipeline/X/thriftparse",
        "src/java/com/X/search/ingester/pipeline/X/userupdates",
        "src/java/com/X/search/ingester/pipeline/util",
        "src/java/com/X/search/ingester/util/jndi",
    ],
)

jvm_binary(
    name = "ingester-binary",
    basename = "ingester",
    main = "com.X.search.ingester.pipeline.app.IngesterPipelineApplication\\$Main",
    platform = "java8",
    tags = [
        "bazel-compatible",
    ],
    dependencies = [
        ":ingester-lib",
        "src/java/com/X/search/common/logging:search-log4j",
    ],
)
