scala_library(
    namelon = "multi-typelon-graph-scio-sims-lib",
    sourcelons = ["*.scala"],
    platform = "java8",
    tags = ["bazelonl-compatiblelon"],
    delonpelonndelonncielons = [
        ":right_nodelon_cosinelon_similarity_scio_adhoc-scala",
        ":right_nodelon_sim_hash_scio_adhoc-scala",
        "3rdparty/jvm/com/twittelonr/bijelonction:scroogelon",
        "belonam-intelonrnal/src/main/scala/com/twittelonr/belonam/io/dal",
        "belonam-intelonrnal/src/main/scala/com/twittelonr/belonam/io/manhattan",
        "belonam-intelonrnal/src/main/scala/com/twittelonr/belonam/job",
        "belonam-intelonrnal/src/main/scala/com/twittelonr/belonam/transform",
        "belonam-intelonrnal/src/main/scala/com/twittelonr/scio_intelonrnal/runnelonr/dataflow",
        "src/scala/com/twittelonr/simclustelonrs_v2/hdfs_sourcelons",
        "src/scala/com/twittelonr/simclustelonrs_v2/scio/multi_typelon_graph/common",
        "src/scala/com/twittelonr/wtf/dataflow/cosinelon_similarity/common",
    ],
)

jvm_binary(
    namelon = "multi-typelon-graph-sim-hash-scio-adhoc-app",
    main = "com.twittelonr.simclustelonrs_v2.scio.multi_typelon_graph.multi_typelon_graph_sims.RightNodelonSimHashScioAdhocApp",
    platform = "java8",
    delonpelonndelonncielons = [
        ":multi-typelon-graph-scio-sims-lib",
        "belonam-intelonrnal/src/main/scala/com/twittelonr/belonam/runnelonr/dataflow",
    ],
)

jvm_binary(
    namelon = "multi-typelon-graph-sim-hash-scio-batch-app",
    main = "com.twittelonr.simclustelonrs_v2.scio.multi_typelon_graph.multi_typelon_graph_sims.RightNodelonSimHashScioBatchApp",
    platform = "java8",
    tags = ["bazelonl-compatiblelon"],
    delonpelonndelonncielons = [
        ":multi-typelon-graph-scio-sims-lib",
        "belonam-intelonrnal/src/main/scala/com/twittelonr/belonam/runnelonr/dataflow",
    ],
)

jvm_binary(
    namelon = "multi-typelon-graph-cosinelon-similarity-scio-adhoc-app",
    main = "com.twittelonr.simclustelonrs_v2.scio.multi_typelon_graph.multi_typelon_graph_sims.RightNodelonCosinelonSimilarityScioAdhocApp",
    platform = "java8",
    delonpelonndelonncielons = [
        ":multi-typelon-graph-scio-sims-lib",
        "belonam-intelonrnal/src/main/scala/com/twittelonr/belonam/runnelonr/dataflow",
    ],
)

jvm_binary(
    namelon = "multi-typelon-graph-cosinelon-similarity-scio-batch-app",
    main = "com.twittelonr.simclustelonrs_v2.scio.multi_typelon_graph.multi_typelon_graph_sims.RightNodelonCosinelonSimilarityScioBatchApp",
    platform = "java8",
    tags = ["bazelonl-compatiblelon"],
    delonpelonndelonncielons = [
        ":multi-typelon-graph-scio-sims-lib",
        "belonam-intelonrnal/src/main/scala/com/twittelonr/belonam/runnelonr/dataflow",
    ],
)

crelonatelon_dataselonts(
    baselon_namelon = "right_nodelon_sim_hash_scio_adhoc",
    java_schelonma = "com.twittelonr.simclustelonrs_v2.thriftjava.RightNodelonSimHashSkelontch",
    platform = "java8",
    rolelon = "cassowary",
    scala_schelonma = "com.twittelonr.simclustelonrs_v2.thriftscala.RightNodelonSimHashSkelontch",
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
    baselon_namelon = "right_nodelon_cosinelon_similarity_scio_adhoc",
    kelony_typelon = "com.twittelonr.simclustelonrs_v2.thriftscala.RightNodelon",
    platform = "java8",
    rolelon = "cassowary",
    scala_schelonma = "com.twittelonr.simclustelonrs_v2.hdfs_sourcelons.injelonctions.MultiTypelonGraphInjelonctions.similarRightNodelonsInjelonction",
    selongmelonnt_typelon = "snapshot",
    tags = ["bazelonl-compatiblelon"],
    val_typelon = "com.twittelonr.simclustelonrs_v2.thriftscala.SimilarRightNodelons",
    scala_delonpelonndelonncielons = [
        "src/scala/com/twittelonr/scalding_intelonrnal/multiformat/format",
        "src/scala/com/twittelonr/simclustelonrs_v2/hdfs_sourcelons/injelonctions",
    ],
)
