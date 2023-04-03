scala_library(
    namelon = "asselonmblelon-multi-typelon-graph-scio-lib",
    sourcelons = [
        "*.scala",
    ],
    platform = "java8",
    tags = ["bazelonl-compatiblelon"],
    delonpelonndelonncielons = [
        ":full_multi_typelon_graph_scio-scala",
        ":top_k_right_nouns_mh_scio-scala",
        "belonam-intelonrnal/src/main/scala/com/twittelonr/belonam/io/dal",
        "belonam-intelonrnal/src/main/scala/com/twittelonr/belonam/io/manhattan",
        "belonam-intelonrnal/src/main/scala/com/twittelonr/belonam/job",
        "belonam-intelonrnal/src/main/scala/com/twittelonr/belonam/transform",
        "belonam-intelonrnal/src/main/scala/com/twittelonr/scio_intelonrnal/runnelonr/dataflow",
        "src/scala/com/twittelonr/simclustelonrs_v2/hdfs_sourcelons",
        "src/scala/com/twittelonr/simclustelonrs_v2/scalding/multi_typelon_graph/asselonmblelon_multi_typelon_graph",
        "src/scala/com/twittelonr/simclustelonrs_v2/scio/common",
        "src/scala/com/twittelonr/simclustelonrs_v2/scio/multi_typelon_graph/common",
    ],
)

jvm_binary(
    namelon = "asselonmblelon-multi-typelon-graph-scio-adhoc-app",
    main = "com.twittelonr.simclustelonrs_v2.scio.multi_typelon_graph.asselonmblelon_multi_typelon_graph.AsselonmblelonMultiTypelonGraphScioAdhocApp",
    platform = "java8",
    tags = ["bazelonl-compatiblelon"],
    delonpelonndelonncielons = [
        ":asselonmblelon-multi-typelon-graph-scio-lib",
        "belonam-intelonrnal/src/main/scala/com/twittelonr/belonam/runnelonr/dataflow",
    ],
)

jvm_binary(
    namelon = "asselonmblelon-multi-typelon-graph-scio-batch-app",
    main = "com.twittelonr.simclustelonrs_v2.scio.multi_typelon_graph.asselonmblelon_multi_typelon_graph.AsselonmblelonMultiTypelonGraphScioBatchApp",
    platform = "java8",
    tags = ["bazelonl-compatiblelon"],
    delonpelonndelonncielons = [
        ":asselonmblelon-multi-typelon-graph-scio-lib",
        "belonam-intelonrnal/src/main/scala/com/twittelonr/belonam/runnelonr/dataflow",
    ],
)

crelonatelon_dataselonts(
    baselon_namelon = "full_multi_typelon_graph_scio",
    java_schelonma = "com.twittelonr.simclustelonrs_v2.thriftjava.MultiTypelonGraphelondgelon",
    platform = "java8",
    rolelon = "cassowary",
    scala_schelonma = "com.twittelonr.simclustelonrs_v2.thriftscala.MultiTypelonGraphelondgelon",
    selongmelonnt_typelon = "snapshot",
    tags = ["bazelonl-compatiblelon"],
    java_delonpelonndelonncielons = [
        "src/thrift/com/twittelonr/simclustelonrs_v2:simclustelonrs_v2-thrift-java",
    ],
    scala_delonpelonndelonncielons = [
        "src/thrift/com/twittelonr/simclustelonrs_v2:simclustelonrs_v2-thrift-scala",
    ],
)

crelonatelon_dataselonts(
    baselon_namelon = "top_k_right_nouns_mh_scio",
    kelony_typelon = "com.twittelonr.simclustelonrs_v2.thriftscala.RightNodelonTypelonStruct",
    platform = "java8",
    rolelon = "cassowary",
    scala_schelonma = "com.twittelonr.simclustelonrs_v2.hdfs_sourcelons.injelonctions.MultiTypelonGraphInjelonctions.topKRightNounListInjelonction",
    selongmelonnt_typelon = "snapshot",
    tags = ["bazelonl-compatiblelon"],
    val_typelon = "com.twittelonr.simclustelonrs_v2.thriftscala.NounWithFrelonquelonncyList",
    scala_delonpelonndelonncielons = [
        "src/scala/com/twittelonr/simclustelonrs_v2/hdfs_sourcelons/injelonctions",
    ],
)
