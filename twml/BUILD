twml_s ces = [
    "twml/**/*.py",
]

twml_deps = [
    "3rdparty/python/c rrypy:default",
    "3rdparty/python/pyyaml:default",
    "3rdparty/python/absl-py:default",
    "3rdparty/python/jobl b:default",
    "3rdparty/python/kazoo:default",
    "3rdparty/python/python-dateut l:default",
    "3rdparty/python/pytz:default",
    "cortex/ml- tastore/src/ma n/python/com/tw ter/ml tastore/modelrepo/cl ent",
    "src/python/tw ter/common/app",
    "src/python/tw ter/common/app/modules:vars",
    "src/python/tw ter/common/ tr cs",
    "src/python/tw ter/deepb rd/compat/v1/opt m zers",
    "src/python/tw ter/deepb rd/compat/v1/rnn",
    "src/python/tw ter/deepb rd/hparam",
    "src/python/tw ter/deepb rd/ o",
    "src/python/tw ter/deepb rd/ o/legacy",
    "src/python/tw ter/deepb rd/logg ng",
    "src/python/tw ter/deepb rd/sparse",
    "src/python/tw ter/deepb rd/stats_server",
    "src/python/tw ter/deepb rd/ut l:s mple-data-record-handler",
    "src/python/tw ter/deepb rd/ut l/hash ng",
    "src/python/tw ter/ml/ap /dal",
    "src/python/tw ter/ml/common: tr cs",
    "src/python/tw ter/ml/common/kubernetes",
    "src/python/tw ter/ml/common:res ces",
    "src/python/tw ter/ml/twml/kubernetes",
    "src/python/tw ter/ml/twml:status",
    "src/thr ft/com/tw ter/dal:dal_no_constants-python",
    "src/thr ft/com/tw ter/stateb rd:comp led-v2-python",
]

python3_l brary(
    na  = "twml-test-common-deps",
    tags = ["no- py"],
    dependenc es = [
        "src/python/tw ter/deepb rd/ut l: nference",
        "src/python/tw ter/deepb rd/ut l/data",
        "src/thr ft/com/tw ter/ml/ap :data-python",
        "twml/tests/data:res ces",
    ],
)

python3_l brary(
    na  = "twml_packer_deps_no_tf",
    tags = [
        "bazel-compat ble",
        "no- py",
    ],
    dependenc es = [
        "3rdparty/python/numpy:default",
        "3rdparty/python/pandas:default",
        "3rdparty/python/pyyaml:default",
        "3rdparty/python/requests:default",
        "3rdparty/python/sc k -learn:default",
        "3rdparty/python/sc py:default",
        "3rdparty/python/tensorflow-hub:default",
        "3rdparty/python/thr ftpy2:default",
    ],
)

python3_l brary(
    na  = "twml_packer_deps_no_tf_py3",
    tags = [
        "known-to-fa l-j ra:CX-20246",
        "no- py",
    ],
    dependenc es = [
        ":twml_packer_deps_no_tf",
        "3rdparty/python/tensorflow-model-analys s",
    ],
)

al as(
    na  = "twml-test-shared",
    target = ":twml_common",
)

python3_l brary(
    na  = "twml_common",
    s ces = ["twml_common/**/*.py"],
    tags = [
        "bazel-compat ble",
        "no- py",
    ],
)

# Al as twml-dev to twml to avo d break ng user targets.
al as(
    na  = "twml-dev",
    target = "twml",
)

python3_l brary(
    na  = "twml-test-dev-deps",
    tags = [
        "bazel-compat ble",
        "no- py",
    ],
    dependenc es = [
        ":twml",
        ":twml-test-common-deps",
        ":twml-test-shared",
        "3rdparty/python/freezegun:default",
        "src/python/tw ter/deepb rd/keras/layers",
        "src/thr ft/com/tw ter/ml/ap :data-python",
        "src/thr ft/com/tw ter/ml/pred ct on_serv ce:pred ct on_serv ce-python",
    ],
)

python3_l brary(
    na  = "twml-dev-python",
    s ces = twml_s ces,
    tags = [
        "bazel-compat ble",
        "no- py",
    ],
    dependenc es = twml_deps + [
        ":twml_packer_deps_no_tf",
        "3rdparty/python/tensorflow",
        "3rdparty/python/twml:l btwml-un versal",
        "twml/l btwml:l btwml-python",
    ],
)

# Bu ld a smaller .pex f le that models can depend on.
# Tensorflow and ot r dependenc es are downloaded from Packer on Aurora.
# Note: T  gets t  C++ ops through 3rdparty art facts.
python3_l brary(
    na  = "twml-nodeps",
    s ces = twml_s ces,
    tags = [
        "bazel-compat ble",
        "no- py",
    ],
    dependenc es = twml_deps + [
        "3rdparty/python/twml:l btwml-un versal",
    ],
)

python3_l brary(
    na  = "twml",
    tags = [
        "bazel-compat ble",
        "no- py",
    ],
    dependenc es = [
        ":twml-nodeps",
        ":twml_packer_deps_no_tf",
        "3rdparty/python/tensorflow",
    ],
)

python37_b nary(
    na  = "tensorboard",
    s ce = "twml/tensorboard/__ma n__.py",
    dependenc es = [
        "3rdparty/python/_closures/twml:tensorboard",
        "3rdparty/python/tensorflow",
    ],
)

python37_b nary(
    na  = "saved_model_cl ",
    s ce = "twml/saved_model_cl /__ma n__.py",
    dependenc es = [
        "3rdparty/python/_closures/twml:saved_model_cl ",
        "3rdparty/python/tensorflow",
    ],
)

# T  target  s added so twml can be used regardless of t  Tensorflow vers on:
# T  target does not pull  n TensorFlow 1.x or t  related l btwml comp led us ng TF 1.x.
python3_l brary(
    na  = "twml-py-s ce-only",
    s ces = twml_s ces,
    tags = [
        "known-to-fa l-j ra:CX-23416",
        "no- py",
    ],
    dependenc es = twml_deps,
)
