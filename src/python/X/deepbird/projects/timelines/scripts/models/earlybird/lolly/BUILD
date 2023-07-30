python3_library(
    name = "libs_py3",
    sources = ["*.py"],
    dependencies = [
        "src/python/X/deepbird/io",
        "twml:twml-nodeps",
    ],
)

python37_binary(
    name = "score",
    source = "score.py",
    dependencies = [
        ":libs_py3",
        "3rdparty/python/_closures/src/python/X/deepbird/projects/timelines/scripts/models/earlybird/lolly:score",
        "twml",
    ],
)
