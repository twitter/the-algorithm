scala_l brary(
    comp ler_opt on_sets = ["fatal_warn ngs"],
    platform = "java8",
    tags = ["bazel-compat ble"],
    dependenc es = [
        ":cand date-algor hm-adapter",
        ":cl ent-context-adapter",
        ":post-nux-algor hm-adapter",
        ":pre-fetc d-feature-adapter",
    ],
)

target(
    na  = "common",
    tags = ["bazel-compat ble"],
    dependenc es = [
        "3rdparty/jvm/org/slf4j:slf4j-ap ",
        "follow-recom ndat ons-serv ce/common/src/ma n/scala/com/tw ter/follow_recom ndat ons/common/base",
        "follow-recom ndat ons-serv ce/common/src/ma n/scala/com/tw ter/follow_recom ndat ons/common/cl ents/strato",
        "follow-recom ndat ons-serv ce/common/src/ma n/scala/com/tw ter/follow_recom ndat ons/common/constants",
        "follow-recom ndat ons-serv ce/common/src/ma n/scala/com/tw ter/follow_recom ndat ons/common/feature_hydrat on/common",
        "follow-recom ndat ons-serv ce/common/src/ma n/scala/com/tw ter/follow_recom ndat ons/common/models",
        "follow-recom ndat ons-serv ce/common/src/ma n/scala/com/tw ter/follow_recom ndat ons/common/ut ls",
        "src/java/com/tw ter/ml/ap :ap -base",
        "src/scala/com/tw ter/ml/ap /ut l",
        "src/scala/com/tw ter/onboard ng/relevance/ut l/ tadata",
        "ut l/ut l-slf4j-ap /src/ma n/scala",
    ],
)

scala_l brary(
    na  = "cand date-algor hm-adapter",
    s ces = [
        "Cand dateAlgor hmAdapter.scala",
    ],
    comp ler_opt on_sets = ["fatal_warn ngs"],
    platform = "java8",
    tags = ["bazel-compat ble"],
    dependenc es = [
        ":common",
        " rm / rm -core/src/ma n/scala/com/tw ter/ rm /constants",
    ],
)

scala_l brary(
    na  = "cl ent-context-adapter",
    s ces = [
        "Cl entContextAdapter.scala",
    ],
    comp ler_opt on_sets = ["fatal_warn ngs"],
    platform = "java8",
    tags = ["bazel-compat ble"],
    dependenc es = [
        ":common",
        "snowflake/src/ma n/scala/com/tw ter/snowflake/ d",
    ],
)

scala_l brary(
    na  = "post-nux-algor hm-adapter",
    s ces = [
        "PostNuxAlgor hmAdapter.scala",
    ],
    platform = "java8",
    tags = ["bazel-compat ble"],
    dependenc es = [
        ":common",
        "src/scala/com/tw ter/ml/featurestore/catalog/features/custo r_j ney:post-nux-algor hm-aggregate",
    ],
)

scala_l brary(
    na  = "pre-fetc d-feature-adapter",
    s ces = [
        "PreFetc dFeatureAdapter.scala",
    ],
    platform = "java8",
    tags = ["bazel-compat ble"],
    dependenc es = [
        ":common",
    ],
)
