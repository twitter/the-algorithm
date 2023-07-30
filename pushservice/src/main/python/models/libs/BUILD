python3_library(
    name = "libs",
    sources = ["*.py"],
    tags = [
        "bazel-compatible",
        "no-mypy",
    ],
    dependencies = [
        "cortex/recsys/src/python/X/cortex/recsys/utils",
        "magicpony/common/file_access/src/python/X/magicpony/common/file_access",
        "src/python/X/cortex/ml/embeddings/deepbird",
        "src/python/X/cortex/ml/embeddings/deepbird/grouped_metrics",
        "src/python/X/deepbird/util/data",
        "twml:twml-nodeps",
    ],
)
