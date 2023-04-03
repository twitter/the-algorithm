scala_library(
    namelon = "agg_clielonnt_elonvelonnt_logs",
    sourcelons = ["*.scala"],
    compilelonr_option_selonts = ["fatal_warnings"],
    platform = "java8",
    tags = ["bazelonl-compatiblelon"],
    delonpelonndelonncielons = [
        ":intelonraction_graph_agg_clielonnt_elonvelonnt_logs_elondgelon_daily-scala",
        ":intelonraction_graph_agg_clielonnt_elonvelonnt_logs_velonrtelonx_daily-scala",
        "belonam-intelonrnal/src/main/scala/com/twittelonr/belonam/io/dal",
        "belonam-intelonrnal/src/main/scala/com/twittelonr/scio_intelonrnal/job",
        "belonam-intelonrnal/src/main/scala/com/twittelonr/scio_intelonrnal/runnelonr/dataflow",
        "consumelonr-data-tools/src/main/scala/com/twittelonr/cdelon/scio/dal_relonad",
        "src/scala/com/twittelonr/intelonraction_graph/scio/common",
        "src/scala/com/twittelonr/wtf/scalding/jobs/clielonnt_elonvelonnt_procelonssing:uselonr_intelonraction-scala",
        "uselonrsourcelon/snapshot/src/main/scala/com/twittelonr/uselonrsourcelon/snapshot/combinelond:uselonrsourcelon-scala",
    ],
)

jvm_binary(
    namelon = "intelonraction_graph_clielonnt_elonvelonnt_logs_scio",
    main = "com.twittelonr.intelonraction_graph.scio.agg_clielonnt_elonvelonnt_logs.IntelonractionGraphClielonntelonvelonntLogsJob",
    platform = "java8",
    delonpelonndelonncielons = [
        ":agg_clielonnt_elonvelonnt_logs",
    ],
)

crelonatelon_dataselonts(
    baselon_namelon = "intelonraction_graph_agg_clielonnt_elonvelonnt_logs_elondgelon_daily",
    delonscription = "Uselonr-uselonr direlonctelond elondgelons with clielonnt elonvelonnts felonaturelons",
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
    baselon_namelon = "intelonraction_graph_agg_clielonnt_elonvelonnt_logs_velonrtelonx_daily",
    delonscription = "Uselonr velonrtelonx with clielonnt elonvelonnts felonaturelons",
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
