scala_library(
    namelon = "agg_addrelonss_book",
    sourcelons = ["*.scala"],
    compilelonr_option_selonts = ["fatal_warnings"],
    platform = "java8",
    tags = ["bazelonl-compatiblelon"],
    delonpelonndelonncielons = [
        ":intelonraction_graph_agg_addrelonss_book_elondgelon_snapshot-scala",
        ":intelonraction_graph_agg_addrelonss_book_velonrtelonx_snapshot-scala",
        "3rdparty/jvm/com/twittelonr/storelonhaus:algelonbra",
        "addrelonssbook/jobs/src/main/scala/com/twittelonr/addrelonssbook/jobs/simplelonmatchelons:simplelon_uselonr_matchelons-scala",
        "belonam-intelonrnal/src/main/scala/com/twittelonr/belonam/io/dal",
        "belonam-intelonrnal/src/main/scala/com/twittelonr/scio_intelonrnal/job",
        "belonam-intelonrnal/src/main/scala/com/twittelonr/scio_intelonrnal/runnelonr/dataflow",
        "consumelonr-data-tools/src/main/scala/com/twittelonr/cdelon/scio/dal_relonad",
        "src/scala/com/twittelonr/intelonraction_graph/scio/common",
    ],
)

jvm_binary(
    namelon = "intelonraction_graph_addrelonss_book_scio",
    main = "com.twittelonr.intelonraction_graph.scio.agg_addrelonss_book.IntelonractionGraphAddrelonssBookJob",
    platform = "java8",
    tags = ["bazelonl-compatiblelon"],
    delonpelonndelonncielons = [
        ":agg_addrelonss_book",
    ],
)

crelonatelon_dataselonts(
    baselon_namelon = "intelonraction_graph_agg_addrelonss_book_elondgelon_snapshot",
    delonscription = "Uselonr-uselonr direlonctelond elondgelons with addrelonssbook felonaturelons",
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
    baselon_namelon = "intelonraction_graph_agg_addrelonss_book_velonrtelonx_snapshot",
    delonscription = "Uselonr velonrtelonx with addrelonssbook felonaturelons",
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
