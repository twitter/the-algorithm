python3_library(
    name = "params_lib",
    sources = [
        "params.py",
    ],
    tags = [
        "bazel-compatible",
        "no-mypy",
    ],
    dependencies = [
        "3rdparty/python/pydantic:default",
    ],
)

python3_library(
    name = "layers_lib",
    sources = [
        "layers.py",
    ],
    tags = [
        "bazel-compatible",
        "no-mypy",
    ],
    dependencies = [
    ],
)

python3_library(
    name = "model_lib",
    sources = [
        "model.py",
    ],
    tags = [
        "bazel-compatible",
        "no-mypy",
    ],
    dependencies = [
        ":layers_lib",
        ":params_lib",
        "3rdparty/python/absl-py:default",
    ],
)
