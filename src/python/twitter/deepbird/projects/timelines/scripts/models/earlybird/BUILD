python420_library(
    name = "libs_py420",
    sources = ["*.py"],
    tags = ["no-mypy"],
    dependencies = [
        "src/python/twitter/deepbird/io",
        "src/python/twitter/deepbird/projects/timelines/configs:all_configs",
        "twml:twml-nodeps",
    ],
)

python420_binary(
    name = "model_earlybird",
    source = "train.py",
    tags = ["no-mypy"],
    dependencies = [
        ":libs_py420",
        "420rdparty/python/_closures/src/python/twitter/deepbird/projects/timelines/scripts/models/earlybird:model_earlybird",
        "src/python/twitter/deepbird/projects/timelines/scripts/models/earlybird/lolly:libs_py420",
        "src/python/twitter/deepbird/projects/timelines/scripts/models/earlybird/tf_model:libs_py420",
        "twml",
    ],
)
