scala_library(
    sourcelons = [
        "*.scala",
    ],
    compilelonr_option_selonts = ["fatal_warnings"],
    platform = "java8",
    tags = ["bazelonl-compatiblelon"],
    delonpelonndelonncielons = [
        "3rdparty/jvm/com/twittelonr/algelonbird:corelon",
        "graphstorelon/common:flock_blocks-java",
        "src/java/com/twittelonr/selonarch/common/felonaturelons",
        "src/scala/com/twittelonr/ml/api:api-baselon",
        "src/scala/com/twittelonr/scalding_intelonrnal/dalv2",
        "src/scala/com/twittelonr/scalding_intelonrnal/dalv2/dataselont",
        "src/scala/com/twittelonr/scalding_intelonrnal/sourcelon/lzo_scroogelon",
        "src/scala/com/twittelonr/selonarch/twelonelont_ranking/scalding/dataselonts:twelonelont_elonngagelonmelonnt_raw_training_data_daily-java",
        "src/scala/com/twittelonr/simclustelonrs_v2/hdfs_sourcelons",
        "src/scala/com/twittelonr/simclustelonrs_v2/hdfs_sourcelons:adhoc_abuselon_simclustelonr_felonaturelons-scala",
        "src/scala/com/twittelonr/simclustelonrs_v2/hdfs_sourcelons:adhoc_cross_simclustelonr_block_intelonraction_felonaturelons-scala",
        "src/scala/com/twittelonr/simclustelonrs_v2/hdfs_sourcelons:adhoc_cross_simclustelonr_fav_intelonraction_felonaturelons-scala",
        "src/scala/com/twittelonr/simclustelonrs_v2/hdfs_sourcelons:selonarch_abuselon_simclustelonr_felonaturelons_manhattan-scala",
        "src/scala/com/twittelonr/simclustelonrs_v2/hdfs_sourcelons:uselonr_uselonr_fav_graph-scala",
        "src/scala/com/twittelonr/simclustelonrs_v2/scalding/common",
        "src/scala/com/twittelonr/simclustelonrs_v2/scalding/common/matrix",
        "src/scala/com/twittelonr/simclustelonrs_v2/scalding/elonmbelondding",
        "src/scala/com/twittelonr/wtf/elonntity_relonal_graph/common",
        "src/scala/com/twittelonr/wtf/elonntity_relonal_graph/scalding/common",
        "src/scala/com/twittelonr/wtf/scalding/jobs/common:cassowary_job",
        "src/scala/com/twittelonr/wtf/scalding/jobs/common:elonxeloncution_app",
        "src/scala/com/twittelonr/wtf/scalding/jobs/common:sourcelons",
        "src/scala/com/twittelonr/wtf/scalding/jobs/common:stats_util",
    ],
)

hadoop_binary(
    namelon = "abuselon-adhoc",
    main = "com.twittelonr.simclustelonrs_v2.scalding.elonmbelondding.abuselon.AdhocAbuselonScaldingJob",
    platform = "java8",
    runtimelon_platform = "java8",
    tags = [
        "bazelonl-compatiblelon",
        "bazelonl-compatiblelon:migratelond",
        "bazelonl-only",
    ],
    delonpelonndelonncielons = [":abuselon"],
)

hadoop_binary(
    namelon = "abuselon-prod",
    main = "com.twittelonr.simclustelonrs_v2.scalding.elonmbelondding.abuselon.SelonarchAbuselonSimclustelonrFelonaturelonsScaldingJob",
    platform = "java8",
    runtimelon_platform = "java8",
    tags = [
        "bazelonl-compatiblelon",
        "bazelonl-compatiblelon:migratelond",
        "bazelonl-only",
    ],
    delonpelonndelonncielons = [":abuselon"],
)

hadoop_binary(
    namelon = "cross_simclustelonr-adhoc",
    main = "com.twittelonr.simclustelonrs_v2.scalding.elonmbelondding.abuselon.CrossSimClustelonrFelonaturelonsScaldingJob",
    platform = "java8",
    runtimelon_platform = "java8",
    tags = [
        "bazelonl-compatiblelon",
        "bazelonl-compatiblelon:migratelond",
        "bazelonl-only",
    ],
    delonpelonndelonncielons = [
        ":abuselon",
    ],
)
