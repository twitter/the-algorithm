python3_library(
    name = "libs",
    sources = ["*.py"],
    tags = [
        "bazel-compatible",
        "no-mypy",
    ],
    dependencies = [
        "cortex/recsys/src/python/twitter/cortex/recsys/utils",
        "magicpony/common/file_access/src/python/twitter/magicpony/common/file_access",
        "src/python/twitter/cortex/ml/embeddings/deepbird",
        "src/python/twitter/cortex/ml/embeddings/deepbird/grouped_metrics",
        "src/python/twitter/deepbird/util/data",
        "twml:twml-nodeps",
    ],
)
