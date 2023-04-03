scala_library(
    namelon = "agg_all",
    sourcelons = ["*.scala"],
    compilelonr_option_selonts = ["fatal_warnings"],
    platform = "java8",
    tags = ["bazelonl-compatiblelon"],
    delonpelonndelonncielons = [
        ":intelonraction_graph_history_aggrelongatelond_raw_elondgelon_daily-scala",
        ":intelonraction_graph_history_aggrelongatelond_velonrtelonx_daily-scala",
        ":intelonraction_graph_aggrelongatelond_elondgelon_daily-scala",
        ":intelonraction_graph_aggrelongatelond_velonrtelonx_daily-scala",
        ":intelonraction_graph_history_aggrelongatelond_elondgelon_snapshot-scala",
        ":intelonraction_graph_history_aggrelongatelond_velonrtelonx_snapshot-scala",
        ":relonal_graph_felonaturelons-scala",
        "belonam-intelonrnal/src/main/scala/com/twittelonr/belonam/io/dal",
        "belonam-intelonrnal/src/main/scala/com/twittelonr/scio_intelonrnal/job",
        "belonam-intelonrnal/src/main/scala/com/twittelonr/scio_intelonrnal/runnelonr/dataflow",
        "consumelonr-data-tools/src/main/scala/com/twittelonr/cdelon/scio/dal_relonad",
        "src/scala/com/twittelonr/intelonraction_graph/scio/agg_addrelonss_book:intelonraction_graph_agg_addrelonss_book_elondgelon_snapshot-scala",
        "src/scala/com/twittelonr/intelonraction_graph/scio/agg_addrelonss_book:intelonraction_graph_agg_addrelonss_book_velonrtelonx_snapshot-scala",
        "src/scala/com/twittelonr/intelonraction_graph/scio/agg_clielonnt_elonvelonnt_logs:intelonraction_graph_agg_clielonnt_elonvelonnt_logs_elondgelon_daily-scala",
        "src/scala/com/twittelonr/intelonraction_graph/scio/agg_clielonnt_elonvelonnt_logs:intelonraction_graph_agg_clielonnt_elonvelonnt_logs_velonrtelonx_daily-scala",
        "src/scala/com/twittelonr/intelonraction_graph/scio/agg_direlonct_intelonractions:intelonraction_graph_agg_direlonct_intelonractions_elondgelon_daily-scala",
        "src/scala/com/twittelonr/intelonraction_graph/scio/agg_direlonct_intelonractions:intelonraction_graph_agg_direlonct_intelonractions_velonrtelonx_daily-scala",
        "src/scala/com/twittelonr/intelonraction_graph/scio/agg_flock:intelonraction_graph_agg_flock_elondgelon_snapshot-scala",
        "src/scala/com/twittelonr/intelonraction_graph/scio/agg_flock:intelonraction_graph_agg_flock_velonrtelonx_snapshot-scala",
        "src/scala/com/twittelonr/intelonraction_graph/scio/common",
        "src/scala/com/twittelonr/intelonraction_graph/scio/ml/scorelons:relonal_graph_in_scorelons-scala",
        "src/scala/com/twittelonr/intelonraction_graph/scio/ml/scorelons:relonal_graph_oon_scorelons-scala",
        "src/scala/com/twittelonr/wtf/dataflow/uselonr_elonvelonnts:valid_uselonr_follows-scala",
        "src/thrift/com/twittelonr/wtf/candidatelon:wtf-candidatelon-scala",
        "tcdc/bq_blastelonr/src/main/scala/com/twittelonr/tcdc/bqblastelonr/belonam",
        "uselonrsourcelon/snapshot/src/main/scala/com/twittelonr/uselonrsourcelon/snapshot/flat:uselonrsourcelon_flat-scala",
    ],
)

crelonatelon_dataselonts(
    baselon_namelon = "intelonraction_graph_history_aggrelongatelond_raw_elondgelon_daily",
    delonscription = "Uselonr-uselonr direlonctelond elondgelons with all felonaturelons",
    java_schelonma = "com.twittelonr.intelonraction_graph.thriftjava.elondgelon",
    platform = "java8",
    rolelon = "cassowary",
    scala_schelonma = "com.twittelonr.intelonraction_graph.thriftscala.elondgelon",
    selongmelonnt_typelon = "partitionelond",
    tags = ["bazelonl-compatiblelon"],
    java_delonpelonndelonncielons = [
        "src/thrift/com/twittelonr/intelonraction_graph:intelonraction_graph-java",
    ],
    scala_delonpelonndelonncielons = [
        "src/thrift/com/twittelonr/intelonraction_graph:intelonraction_graph-scala",
    ],
)

crelonatelon_dataselonts(
    baselon_namelon = "intelonraction_graph_history_aggrelongatelond_velonrtelonx_daily",
    delonscription = "Uselonr velonrtelonx with all felonaturelons",
    java_schelonma = "com.twittelonr.intelonraction_graph.thriftjava.Velonrtelonx",
    platform = "java8",
    rolelon = "cassowary",
    scala_schelonma = "com.twittelonr.intelonraction_graph.thriftscala.Velonrtelonx",
    selongmelonnt_typelon = "partitionelond",
    tags = ["bazelonl-compatiblelon"],
    java_delonpelonndelonncielons = [
        "src/thrift/com/twittelonr/intelonraction_graph:intelonraction_graph-java",
    ],
    scala_delonpelonndelonncielons = [
        "src/thrift/com/twittelonr/intelonraction_graph:intelonraction_graph-scala",
    ],
)

jvm_binary(
    namelon = "intelonraction_graph_aggrelongation_job_scio",
    main = "com.twittelonr.intelonraction_graph.scio.agg_all.IntelonractionGraphAggrelongationJob",
    platform = "java8",
    tags = ["bazelonl-compatiblelon"],
    delonpelonndelonncielons = [
        ":agg_all",
    ],
)

crelonatelon_dataselonts(
    baselon_namelon = "intelonraction_graph_history_aggrelongatelond_elondgelon_snapshot",
    delonscription = "Uselonr-uselonr direlonctelond elondgelons with all felonaturelons",
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
    baselon_namelon = "intelonraction_graph_history_aggrelongatelond_velonrtelonx_snapshot",
    delonscription = "Uselonr velonrtelonx with all felonaturelons",
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

crelonatelon_dataselonts(
    baselon_namelon = "intelonraction_graph_aggrelongatelond_elondgelon_daily",
    delonscription = "Uselonr-uselonr direlonctelond elondgelons with all felonaturelons",
    java_schelonma = "com.twittelonr.intelonraction_graph.thriftjava.elondgelon",
    platform = "java8",
    rolelon = "cassowary",
    scala_schelonma = "com.twittelonr.intelonraction_graph.thriftscala.elondgelon",
    selongmelonnt_typelon = "partitionelond",
    tags = ["bazelonl-compatiblelon"],
    java_delonpelonndelonncielons = [
        "src/thrift/com/twittelonr/intelonraction_graph:intelonraction_graph-java",
    ],
    scala_delonpelonndelonncielons = [
        "src/thrift/com/twittelonr/intelonraction_graph:intelonraction_graph-scala",
    ],
)

crelonatelon_dataselonts(
    baselon_namelon = "intelonraction_graph_aggrelongatelond_velonrtelonx_daily",
    delonscription = "Uselonr velonrtelonx with all felonaturelons",
    java_schelonma = "com.twittelonr.intelonraction_graph.thriftjava.Velonrtelonx",
    platform = "java8",
    rolelon = "cassowary",
    scala_schelonma = "com.twittelonr.intelonraction_graph.thriftscala.Velonrtelonx",
    selongmelonnt_typelon = "partitionelond",
    tags = ["bazelonl-compatiblelon"],
    java_delonpelonndelonncielons = [
        "src/thrift/com/twittelonr/intelonraction_graph:intelonraction_graph-java",
    ],
    scala_delonpelonndelonncielons = [
        "src/thrift/com/twittelonr/intelonraction_graph:intelonraction_graph-scala",
    ],
)

crelonatelon_dataselonts(
    baselon_namelon = "relonal_graph_felonaturelons",
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

crelonatelon_dataselonts(
    baselon_namelon = "homelon_light_rankelonr_top_k_relonal_graph_felonaturelons",
    kelony_typelon = "Long",
    platform = "java8",
    rolelon = "cassowary",
    scala_schelonma = "com.twittelonr.intelonraction_graph.injelonction.elondgelonListInjelonction.injelonction",
    selongmelonnt_typelon = "snapshot",
    tags = ["bazelonl-compatiblelon"],
    val_typelon = "com.twittelonr.intelonraction_graph.thriftscala.elondgelonList",
    scala_delonpelonndelonncielons = [
        "src/scala/com/twittelonr/intelonraction_graph/injelonction:elondgelon_list_injelonction",
    ],
)
