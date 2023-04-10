scala_library(
    name = "default",
    sources = [
        "AbstractHydrator.scala",
        "DefaultHydrator.scala",
    ],
    tags = ["bazel-compatible"],
    dependencies = [
        ":base",
        "featureswitches/dynmap/src/main/scala/com/twitter/dynmap:dynmap-core",
        "unified_user_actions/enricher/src/main/scala/com/twitter/unified_user_actions/enricher",
        "unified_user_actions/enricher/src/main/scala/com/twitter/unified_user_actions/enricher/graphql",
        "unified_user_actions/enricher/src/main/scala/com/twitter/unified_user_actions/enricher/hcache",
        "unified_user_actions/enricher/src/main/thrift/com/twitter/unified_user_actions/enricher/internal:internal-scala",
    ],
)

scala_library(
    name = "noop",
    sources = ["NoopHydrator.scala"],
    tags = ["bazel-compatible"],
    dependencies = [
        ":base",
        "unified_user_actions/enricher/src/main/scala/com/twitter/unified_user_actions/enricher",
        "unified_user_actions/enricher/src/main/thrift/com/twitter/unified_user_actions/enricher/internal:internal-scala",
    ],
)

scala_library(
    name = "base",
    sources = ["Hydrator.scala"],
    tags = ["bazel-compatible"],
    dependencies = [
        "unified_user_actions/enricher/src/main/thrift/com/twitter/unified_user_actions/enricher/internal:internal-scala",
    ],
)
