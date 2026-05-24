twml_sources = [
    "twml/**/*.py",
]

twml_deps = [
    "3rdparty/python/cherrypy:default",
    "3rdparty/python/pyyaml:default",
    "3rdparty/python/absl-py:default",
    "3rdparty/python/joblib:default",
    "3rdparty/python/kazoo:default",
    "3rdparty/python/python-dateutil:default",
    "3rdparty/python/pytz:default",
    "cortex/ml-metastore/src/main/python/com/twitter/mlmetastore/modelrepo/client",
    "src/python/twitter/common/app",
    "src/python/twitter/common/app/modules:vars",
    "src/python/twitter/common/metrics",
    "src/python/twitter/deepbird/compat/v1/optimizers",
    "src/python/twitter/deepbird/compat/v1/rnn",
    "src/python/twitter/deepbird/hparam",
    "src/python/twitter/deepbird/io",
    "src/python/twitter/deepbird/io/legacy",
    "src/python/twitter/deepbird/logging",
    "src/python/twitter/deepbird/sparse",
    "src/python/twitter/deepbird/stats_server",
    "src/python/twitter/deepbird/util:simple-data-record-handler",
    "src/python/twitter/deepbird/util/hashing",
    "src/python/twitter/ml/api/dal",
    "src/python/twitter/ml/common:metrics",
    "src/python/twitter/ml/common/kubernetes",
    "src/python/twitter/ml/common:resources",
    "src/python/twitter/ml/twml/kubernetes",
    "src/python/twitter/ml/twml:status",
    "src/thrift/com/twitter/dal:dal_no_constants-python",
    "src/thrift/com/twitter/statebird:compiled-v2-python",
]

python3_library(
    name = "twml-test-common-deps",
    tags = ["no-mypy"],
    dependencies = [
        "src/python/twitter/deepbird/util:inference",
        "src/python/twitter/deepbird/util/data",
        "src/thrift/com/twitter/ml/api:data-python",
        "twml/tests/data:resources",
    ],
)

python3_library(
    name = "twml_packer_deps_no_tf",
    tags = [
        "bazel-compatible",
        "no-mypy",
    ],
    dependencies = [
        "3rdparty/python/numpy:default",
        "3rdparty/python/pandas:default",
        "3rdparty/python/pyyaml:default",
        "3rdparty/python/requests:default",
        "3rdparty/python/scikit-learn:default",
        "3rdparty/python/scipy:default",
        "3rdparty/python/tensorflow-hub:default",
        "3rdparty/python/thriftpy2:default",
    ],
)

python3_library(
    name = "twml_packer_deps_no_tf_py3",
    tags = [
        "known-to-fail-jira:CX-20246",
        "no-mypy",
    ],
    dependencies = [
        ":twml_packer_deps_no_tf",
        "3rdparty/python/tensorflow-model-analysis",
    ],
)

alias(
    name = "twml-test-shared",
    target = ":twml_common",
)

python3_library(
    name = "twml_common",
    sources = ["twml_common/**/*.py"],
    tags = [
        "bazel-compatible",
        "no-mypy",
    ],
)

# Alias twml-dev to twml to avoid breaking user targets.
alias(
    name = "twml-dev",
    target = "twml",
)

python3_library(
    name = "twml-test-dev-deps",
    tags = [
        "bazel-compatible",
        "no-mypy",
    ],
    dependencies = [
        ":twml",
        ":twml-test-common-deps",
        ":twml-test-shared",
        "3rdparty/python/freezegun:default",
        "src/python/twitter/deepbird/keras/layers",
        "src/thrift/com/twitter/ml/api:data-python",
        "src/thrift/com/twitter/ml/prediction_service:prediction_service-python",
    ],
)

python3_library(
    name = "twml-dev-python",
    sources = twml_sources,
    tags = [
        "bazel-compatible",
        "no-mypy",
    ],
    dependencies = twml_deps + [
        ":twml_packer_deps_no_tf",
        "3rdparty/python/tensorflow",
        "3rdparty/python/twml:libtwml-universal",
        "twml/libtwml:libtwml-python",
    ],
)

# Build a smaller .pex file that models can depend on.
# Tensorflow and other dependencies are downloaded from Packer on Aurora.
# Note: This gets the C++ ops through 3rdparty artifacts.
python3_library(
    name = "twml-nodeps",
    sources = twml_sources,
    tags = [
        "bazel-compatible",
        "no-mypy",
    ],
    dependencies = twml_deps + [
        "3rdparty/python/twml:libtwml-universal",
    ],
)

python3_library(
    name = "twml",
    tags = [
        "bazel-compatible",
        "no-mypy",
    ],
    dependencies = [
        ":twml-nodeps",
        ":twml_packer_deps_no_tf",
        "3rdparty/python/tensorflow",
    ],
)

python37_binary(
    name = "tensorboard",
    source = "twml/tensorboard/__main__.py",
    dependencies = [
        "3rdparty/python/_closures/twml:tensorboard",
        "3rdparty/python/tensorflow",
    ],
)

python37_binary(
    name = "saved_model_cli",
    source = "twml/saved_model_cli/__main__.py",
    dependencies = [
        "3rdparty/python/_closures/twml:saved_model_cli",
        "3rdparty/python/tensorflow",
    ],
)

# This target is added so twml can be used regardless of the Tensorflow version:
# This target does not pull in TensorFlow 1.x or the related libtwml compiled using TF 1.x.
python3_library(
    name = "twml-py-source-only",
    sources = twml_sources,
    tags = [
        "known-to-fail-jira:CX-23416",
        "no-mypy",
    ],
    dependencies = twml_deps,
)
