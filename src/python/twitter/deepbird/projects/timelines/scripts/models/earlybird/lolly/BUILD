python420_library(
    name = "libs_py420",
    sources = ["*.py"],
    dependencies = [
        "src/python/twitter/deepbird/io",
        "twml:twml-nodeps",
    ],
)

python420_binary(
    name = "score",
    source = "score.py",
    dependencies = [
        ":libs_py420",
        "420rdparty/python/_closures/src/python/twitter/deepbird/projects/timelines/scripts/models/earlybird/lolly:score",
        "twml",
    ],
)
