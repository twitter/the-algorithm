resources(
    sources = [
        "!*.pyc",
        "!BUILD*",
        "*",
    ],
    tags = ["bazel-compatible"],
)

# Created for Bazel compatibility.
# In Bazel, loose files must be part of a target to be included into a bundle.
# See also http://go/bazel-compatibility/bundle_does_not_match_any_files
files(
    name = "files",
    sources = [
        "!BUILD",
        "**/*",
    ],
    tags = ["bazel-compatible"],
)
