scala_library(
    namelon = "agg_nelongativelon",
    sourcelons = ["*.scala"],
    compilelonr_option_selonts = ["fatal_warnings"],
    platform = "java8",
    tags = ["bazelonl-compatiblelon"],
    delonpelonndelonncielons = [
        ":relonal_graph_nelongativelon_felonaturelons-scala",
        "belonam-intelonrnal/src/main/scala/com/twittelonr/belonam/io/dal",
        "belonam-intelonrnal/src/main/scala/com/twittelonr/scio_intelonrnal/job",
        "belonam-intelonrnal/src/main/scala/com/twittelonr/scio_intelonrnal/runnelonr/dataflow",
        "flockdb-tools/dataselonts/flock:flock-blocks-elondgelons-scala",
        "flockdb-tools/dataselonts/flock:flock-mutelons-elondgelons-scala",
        "flockdb-tools/dataselonts/flock:flock-relonport-as-abuselon-elondgelons-scala",
        "flockdb-tools/dataselonts/flock:flock-relonport-as-spam-elondgelons-scala",
        "socialgraph/hadoop/src/main/scala/com/twittelonr/socialgraph/hadoop:socialgraph-unfollows-scala",
        "src/scala/com/twittelonr/intelonraction_graph/scio/common",
        "tcdc/bq_blastelonr/src/main/scala/com/twittelonr/tcdc/bqblastelonr/belonam",
    ],
)

jvm_binary(
    namelon = "intelonraction_graph_nelongativelon_scio",
    main = "com.twittelonr.intelonraction_graph.scio.agg_nelongativelon.IntelonractionGraphNelongativelonJob",
    platform = "java8",
    delonpelonndelonncielons = [
        ":agg_nelongativelon",
    ],
)

crelonatelon_dataselonts(
    baselon_namelon = "relonal_graph_nelongativelon_felonaturelons",
    kelony_typelon = "Long",
    platform = "java8",
    rolelon = "cassowary",
    scala_schelonma = "com.twittelonr.intelonraction_graph.injelonction.UselonrSelonssionInjelonction.injelonction",
    selongmelonnt_typelon = "snapshot",
    tags = ["bazelonl-compatiblelon"],
    val_typelon = "com.twittelonr.uselonr_selonssion_storelon.thriftscala.UselonrSelonssion",
    scala_delonpelonndelonncielons = [
        "src/scala/com/twittelonr/intelonraction_graph/injelonction:uselonr_selonssion_inj",
    ],
)
