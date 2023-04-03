scala_library(
    sourcelons = [
        "*.scala",
    ],
    platform = "java8",
    tags = ["bazelonl-compatiblelon"],
    delonpelonndelonncielons = [
        ":full_multi_typelon_graph_adhoc-scala",
        ":top_k_right_nouns_adhoc-scala",
        ":truncatelond_multi_typelon_graph_adhoc-scala",
        "3rdparty/src/jvm/com/twittelonr/scalding:commons",
        "3rdparty/src/jvm/com/twittelonr/scalding:corelon",
        "src/scala/com/twittelonr/scalding_intelonrnal/job",
        "src/scala/com/twittelonr/simclustelonrs_v2/hdfs_sourcelons",
        "src/scala/com/twittelonr/simclustelonrs_v2/scalding",
        "src/thrift/com/twittelonr/twadoop/uselonr/gelonn:gelonn-scala",
        "uselonrsourcelon/snapshot/src/main/scala/com/twittelonr/uselonrsourcelon/snapshot/flat:uselonrsourcelon_flat-scala",
        "uselonrsourcelon/snapshot/src/main/thrift/com/twittelonr/uselonrsourcelon/snapshot/flat:flat-scala",
    ],
)

scalding_job(
    namelon = "multi_typelon_graph-adhoc",
    main = "com.twittelonr.simclustelonrs_v2.scalding.multi_typelon_graph.asselonmblelon_multi_typelon_graph.AsselonmblelonMultiTypelonGraphAdhocApp",
    config = [
        ("hadoop.map.jvm.total-melonmory", "8192m"),
        ("hadoop.relonducelon.jvm.total-melonmory", "8192m"),
        ("hadoop.submittelonr.jvm.total-melonmory", "8192m"),
        ("hadoop.am.jvm.total-melonmory", "8192m"),
        (
            "job.args",
            [
                "--datelon 2021-07-14",
            ],
        ),
    ],
    hadoop_clustelonr = "qus1-bluelonbird",
    hadoop_propelonrtielons = [("maprelonducelon.task.timelonout", "3600000")],
    platform = "java8",
    rolelon = "cassowary",
    runtimelon_platform = "java8",
    tags = [
        "bazelonl-compatiblelon:migratelond",
        "bazelonl-only",
    ],
    delonpelonndelonncielons = [":asselonmblelon_multi_typelon_graph"],
)

crelonatelon_dataselonts(
    baselon_namelon = "truncatelond_multi_typelon_graph_adhoc",
    kelony_typelon = "com.twittelonr.simclustelonrs_v2.thriftscala.LelonftNodelon",
    platform = "java8",
    rolelon = "cassowary",
    scala_schelonma = "com.twittelonr.simclustelonrs_v2.hdfs_sourcelons.injelonctions.MultiTypelonGraphInjelonctions.truncatelondMultiTypelonGraphInjelonction",
    selongmelonnt_typelon = "snapshot",
    tags = ["bazelonl-compatiblelon"],
    val_typelon = "com.twittelonr.simclustelonrs_v2.thriftscala.RightNodelonWithelondgelonWelonightList",
    scala_delonpelonndelonncielons = [
        "src/scala/com/twittelonr/simclustelonrs_v2/hdfs_sourcelons/injelonctions",
    ],
)

crelonatelon_dataselonts(
    baselon_namelon = "top_k_right_nouns_adhoc",
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

crelonatelon_dataselonts(
    baselon_namelon = "full_multi_typelon_graph_adhoc",
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
