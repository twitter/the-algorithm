scala_library(
    sources = ["*.scala"],
    compiler_option_sets = ["fatal_warnings"],
    platform = "java8",
    tags = ["bazel-compatible"],
    dependencies = [
        ":real_graph_in_scores-scala",
        ":real_graph_oon_scores-scala",
        "beam-internal/src/main/scala/com/X/beam/io/dal",
        "beam-internal/src/main/scala/com/X/scio_internal/job",
        "beam-internal/src/main/scala/com/X/scio_internal/runner/dataflow",
        "tcdc/bq_blaster/src/main/scala/com/X/tcdc/bqblaster/beam",
    ],
)

jvm_binary(
    name = "interaction_graph_scores_scio",
    main = "com.X.interaction_graph.scio.ml.scores.InteractionGraphScoreExportJob",
    platform = "java8",
    tags = ["bazel-compatible"],
    dependencies = [
        ":scores",
    ],
)

create_datasets(
    base_name = "real_graph_in_scores",
    description = "Real Graph in network scores",
    key_type = "Long",
    platform = "java8",
    role = "cassowary",
    scala_schema = "com.X.wtf.scalding.jobs.injection.CandidateSeqInjection.injection",
    segment_type = "snapshot",
    tags = ["bazel-compatible"],
    val_type = "com.X.wtf.candidate.thriftscala.CandidateSeq",
    scala_dependencies = [
        "src/scala/com/X/wtf/scalding/jobs/injection",
    ],
)

create_datasets(
    base_name = "real_graph_oon_scores",
    description = "Real Graph OON Scores",
    key_type = "Long",
    platform = "java8",
    role = "cassowary",
    scala_schema = "com.X.wtf.scalding.jobs.injection.CandidateSeqInjection.injection",
    segment_type = "snapshot",
    tags = ["bazel-compatible"],
    val_type = "com.X.wtf.candidate.thriftscala.CandidateSeq",
    scala_dependencies = [
        "src/scala/com/X/wtf/scalding/jobs/injection",
    ],
)
