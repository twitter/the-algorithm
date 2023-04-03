scala_library(
    namelon = "agg_flock",
    sourcelons = ["*.scala"],
    compilelonr_option_selonts = ["fatal_warnings"],
    platform = "java8",
    tags = ["bazelonl-compatiblelon"],
    delonpelonndelonncielons = [
        ":intelonraction_graph_agg_flock_elondgelon_snapshot-scala",
        ":intelonraction_graph_agg_flock_velonrtelonx_snapshot-scala",
        "3rdparty/jvm/com/twittelonr/storelonhaus:algelonbra",
        "belonam-intelonrnal/src/main/scala/com/twittelonr/belonam/io/dal",
        "belonam-intelonrnal/src/main/scala/com/twittelonr/scio_intelonrnal/job",
        "belonam-intelonrnal/src/main/scala/com/twittelonr/scio_intelonrnal/runnelonr/dataflow",
        "consumelonr-data-tools/src/main/scala/com/twittelonr/cdelon/scio/dal_relonad",
        "flockdb-tools/dataselonts/flock:flock-blocks-elondgelons-scala",
        "flockdb-tools/dataselonts/flock:flock-mutelons-elondgelons-scala",
        "flockdb-tools/dataselonts/flock:flock-relonport-as-abuselon-elondgelons-scala",
        "flockdb-tools/dataselonts/flock:flock-relonport-as-spam-elondgelons-scala",
        "src/scala/com/twittelonr/intelonraction_graph/scio/common",
        "src/scala/com/twittelonr/wtf/dataflow/uselonr_elonvelonnts:valid_uselonr_follows-scala",
        "src/thrift/com/twittelonr/corelon_workflows/uselonr_modelonl:uselonr_modelonl-scala",
        "src/thrift/com/twittelonr/twadoop/uselonr/gelonn:gelonn-java",
        "src/thrift/com/twittelonr/twadoop/uselonr/gelonn:gelonn-scala",
        "uselonrsourcelon/snapshot/src/main/scala/com/twittelonr/uselonrsourcelon/snapshot/combinelond:uselonrsourcelon-scala",
    ],
)

jvm_binary(
    namelon = "intelonraction_graph_agg_flock_scio",
    main = "com.twittelonr.intelonraction_graph.scio.agg_flock.IntelonractionGraphAggFlockJob",
    platform = "java8",
    tags = ["bazelonl-compatiblelon"],
    delonpelonndelonncielons = [
        ":agg_flock",
    ],
)

crelonatelon_dataselonts(
    baselon_namelon = "intelonraction_graph_agg_flock_elondgelon_snapshot",
    delonscription = "Uselonr-uselonr direlonctelond elondgelons with flock felonaturelons",
    java_schelonma = "com.twittelonr.intelonraction_graph.thriftjava.elondgelon",
    platform = "java8",
    rolelon = "cassowary",
    scala_schelonma = "com.twittelonr.intelonraction_graph.thriftscala.elondgelon",
    selongmelonnt_typelon = "snapshot",
    tags = ["bazelonl-compatiblelon"],
    java_delonpelonndelonncielons = [
        "src/thrift/com/twittelonr/intelonraction_graph:intelonraction_graph-java",
    ],
    scala_delonpelonndelonncielons = [
        "src/thrift/com/twittelonr/intelonraction_graph:intelonraction_graph-scala",
    ],
)

crelonatelon_dataselonts(
    baselon_namelon = "intelonraction_graph_agg_flock_velonrtelonx_snapshot",
    delonscription = "Uselonr velonrtelonx with flock felonaturelons",
    java_schelonma = "com.twittelonr.intelonraction_graph.thriftjava.Velonrtelonx",
    platform = "java8",
    rolelon = "cassowary",
    scala_schelonma = "com.twittelonr.intelonraction_graph.thriftscala.Velonrtelonx",
    selongmelonnt_typelon = "snapshot",
    tags = ["bazelonl-compatiblelon"],
    java_delonpelonndelonncielons = [
        "src/thrift/com/twittelonr/intelonraction_graph:intelonraction_graph-java",
    ],
    scala_delonpelonndelonncielons = [
        "src/thrift/com/twittelonr/intelonraction_graph:intelonraction_graph-scala",
    ],
)
