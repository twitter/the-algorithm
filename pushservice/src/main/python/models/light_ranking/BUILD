#":mlwf_libs",

python37_binary(
    name = "eval_model",
    source = "eval_model.py",
    dependencies = [
        ":libs",
        "3rdparty/python/_closures/frigate/frigate-pushservice-opensource/src/main/python/models/light_ranking:eval_model",
    ],
)

python37_binary(
    name = "train_model",
    source = "deep_norm.py",
    dependencies = [
        ":libs",
        "3rdparty/python/_closures/frigate/frigate-pushservice-opensource/src/main/python/models/light_ranking:train_model",
    ],
)

python37_binary(
    name = "train_model_local",
    source = "deep_norm.py",
    dependencies = [
        ":libs",
        "3rdparty/python/_closures/frigate/frigate-pushservice-opensource/src/main/python/models/light_ranking:train_model_local",
        "twml",
    ],
)

python37_binary(
    name = "eval_model_local",
    source = "eval_model.py",
    dependencies = [
        ":libs",
        "3rdparty/python/_closures/frigate/frigate-pushservice-opensource/src/main/python/models/light_ranking:eval_model_local",
        "twml",
    ],
)

python37_binary(
    name = "mlwf_model",
    source = "deep_norm.py",
    dependencies = [
        ":mlwf_libs",
        "3rdparty/python/_closures/frigate/frigate-pushservice-opensource/src/main/python/models/light_ranking:mlwf_model",
    ],
)

python3_library(
    name = "libs",
    sources = ["**/*.py"],
    tags = ["no-mypy"],
    dependencies = [
        "src/python/twitter/deepbird/projects/magic_recs/libs",
        "src/python/twitter/deepbird/util/data",
        "twml:twml-nodeps",
    ],
)

python3_library(
    name = "mlwf_libs",
    sources = ["**/*.py"],
    tags = ["no-mypy"],
    dependencies = [
        "src/python/twitter/deepbird/projects/magic_recs/libs",
        "twml",
    ],
)
