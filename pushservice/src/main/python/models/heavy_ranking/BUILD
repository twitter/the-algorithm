python37_binary(
    name = "update_warm_start_checkpoint",
    source = "update_warm_start_checkpoint.py",
    tags = ["no-mypy"],
    dependencies = [
        ":deep_norm_lib",
        "3rdparty/python/_closures/frigate/frigate-pushservice-opensource/src/main/python/models/heavy_ranking:update_warm_start_checkpoint",
    ],
)

python3_library(
    name = "params_lib",
    sources = ["params.py"],
    tags = ["no-mypy"],
    dependencies = [
        "3rdparty/python/pydantic:default",
        "src/python/twitter/deepbird/projects/magic_recs/v11/lib:params_lib",
    ],
)

python3_library(
    name = "features_lib",
    sources = ["features.py"],
    tags = ["no-mypy"],
    dependencies = [
        ":params_lib",
        "src/python/twitter/deepbird/projects/magic_recs/libs",
        "twml:twml-nodeps",
    ],
)

python3_library(
    name = "model_pools_lib",
    sources = ["model_pools.py"],
    tags = ["no-mypy"],
    dependencies = [
        ":features_lib",
        ":params_lib",
        "src/python/twitter/deepbird/projects/magic_recs/v11/lib:model_lib",
    ],
)

python3_library(
    name = "graph_lib",
    sources = ["graph.py"],
    tags = ["no-mypy"],
    dependencies = [
        ":params_lib",
        "src/python/twitter/deepbird/projects/magic_recs/libs",
    ],
)

python3_library(
    name = "run_args_lib",
    sources = ["run_args.py"],
    tags = ["no-mypy"],
    dependencies = [
        ":features_lib",
        ":params_lib",
        "twml:twml-nodeps",
    ],
)

python3_library(
    name = "deep_norm_lib",
    sources = ["deep_norm.py"],
    tags = ["no-mypy"],
    dependencies = [
        ":features_lib",
        ":graph_lib",
        ":model_pools_lib",
        ":params_lib",
        ":run_args_lib",
        "src/python/twitter/deepbird/projects/magic_recs/libs",
        "src/python/twitter/deepbird/util/data",
        "twml:twml-nodeps",
    ],
)

python3_library(
    name = "eval_lib",
    sources = ["eval.py"],
    tags = ["no-mypy"],
    dependencies = [
        ":features_lib",
        ":graph_lib",
        ":model_pools_lib",
        ":params_lib",
        ":run_args_lib",
        "src/python/twitter/deepbird/projects/magic_recs/libs",
        "twml:twml-nodeps",
    ],
)

python37_binary(
    name = "deep_norm",
    source = "deep_norm.py",
    dependencies = [
        ":deep_norm_lib",
        "3rdparty/python/_closures/frigate/frigate-pushservice-opensource/src/main/python/models/heavy_ranking:deep_norm",
        "twml",
    ],
)

python37_binary(
    name = "eval",
    source = "eval.py",
    dependencies = [
        ":eval_lib",
        "3rdparty/python/_closures/frigate/frigate-pushservice-opensource/src/main/python/models/heavy_ranking:eval",
        "twml",
    ],
)

python3_library(
    name = "mlwf_libs",
    tags = ["no-mypy"],
    dependencies = [
        ":deep_norm_lib",
        "twml",
    ],
)

python37_binary(
    name = "train_model",
    source = "deep_norm.py",
    dependencies = [
        ":deep_norm_lib",
        "3rdparty/python/_closures/frigate/frigate-pushservice-opensource/src/main/python/models/heavy_ranking:train_model",
    ],
)

python37_binary(
    name = "train_model_local",
    source = "deep_norm.py",
    dependencies = [
        ":deep_norm_lib",
        "3rdparty/python/_closures/frigate/frigate-pushservice-opensource/src/main/python/models/heavy_ranking:train_model_local",
        "twml",
    ],
)

python37_binary(
    name = "eval_model_local",
    source = "eval.py",
    dependencies = [
        ":eval_lib",
        "3rdparty/python/_closures/frigate/frigate-pushservice-opensource/src/main/python/models/heavy_ranking:eval_model_local",
        "twml",
    ],
)

python37_binary(
    name = "eval_model",
    source = "eval.py",
    dependencies = [
        ":eval_lib",
        "3rdparty/python/_closures/frigate/frigate-pushservice-opensource/src/main/python/models/heavy_ranking:eval_model",
    ],
)

python37_binary(
    name = "mlwf_model",
    source = "deep_norm.py",
    dependencies = [
        ":mlwf_libs",
        "3rdparty/python/_closures/frigate/frigate-pushservice-opensource/src/main/python/models/heavy_ranking:mlwf_model",
    ],
)
