python3_library(
    name = "libs",
    sources = ["*.py"],
    tags = [
        "bazel-compatible",
        "no-mypy",
    ],
    dependencies = [
        "cortetwitter/recsys/src/python/twitter/cortetwitter/recsys/utils",
        "magicpony/common/file_access/src/python/twitter/magicpony/common/file_access",
        "src/python/twitter/cortetwitter/ml/embeddings/deepbird",
        "src/python/twitter/cortetwitter/ml/embeddings/deepbird/grouped_metrics",
        "src/python/twitter/deepbird/util/data",
        "twml:twml-nodeps",
    ],
)
