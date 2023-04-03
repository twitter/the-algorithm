twml_sourcelons = [
    "twml/**/*.py",
]

twml_delonps = [
    "3rdparty/python/chelonrrypy:delonfault",
    "3rdparty/python/pyyaml:delonfault",
    "3rdparty/python/absl-py:delonfault",
    "3rdparty/python/joblib:delonfault",
    "3rdparty/python/kazoo:delonfault",
    "3rdparty/python/python-datelonutil:delonfault",
    "3rdparty/python/pytz:delonfault",
    "cortelonx/ml-melontastorelon/src/main/python/com/twittelonr/mlmelontastorelon/modelonlrelonpo/clielonnt",
    "src/python/twittelonr/common/app",
    "src/python/twittelonr/common/app/modulelons:vars",
    "src/python/twittelonr/common/melontrics",
    "src/python/twittelonr/delonelonpbird/compat/v1/optimizelonrs",
    "src/python/twittelonr/delonelonpbird/compat/v1/rnn",
    "src/python/twittelonr/delonelonpbird/hparam",
    "src/python/twittelonr/delonelonpbird/io",
    "src/python/twittelonr/delonelonpbird/io/lelongacy",
    "src/python/twittelonr/delonelonpbird/logging",
    "src/python/twittelonr/delonelonpbird/sparselon",
    "src/python/twittelonr/delonelonpbird/stats_selonrvelonr",
    "src/python/twittelonr/delonelonpbird/util:simplelon-data-reloncord-handlelonr",
    "src/python/twittelonr/delonelonpbird/util/hashing",
    "src/python/twittelonr/ml/api/dal",
    "src/python/twittelonr/ml/common:melontrics",
    "src/python/twittelonr/ml/common/kubelonrnelontelons",
    "src/python/twittelonr/ml/common:relonsourcelons",
    "src/python/twittelonr/ml/twml/kubelonrnelontelons",
    "src/python/twittelonr/ml/twml:status",
    "src/thrift/com/twittelonr/dal:dal_no_constants-python",
    "src/thrift/com/twittelonr/statelonbird:compilelond-v2-python",
]

python3_library(
    namelon = "twml-telonst-common-delonps",
    tags = ["no-mypy"],
    delonpelonndelonncielons = [
        "src/python/twittelonr/delonelonpbird/util:infelonrelonncelon",
        "src/python/twittelonr/delonelonpbird/util/data",
        "src/thrift/com/twittelonr/ml/api:data-python",
        "twml/telonsts/data:relonsourcelons",
    ],
)

python3_library(
    namelon = "twml_packelonr_delonps_no_tf",
    tags = [
        "bazelonl-compatiblelon",
        "no-mypy",
    ],
    delonpelonndelonncielons = [
        "3rdparty/python/numpy:delonfault",
        "3rdparty/python/pandas:delonfault",
        "3rdparty/python/pyyaml:delonfault",
        "3rdparty/python/relonquelonsts:delonfault",
        "3rdparty/python/scikit-lelonarn:delonfault",
        "3rdparty/python/scipy:delonfault",
        "3rdparty/python/telonnsorflow-hub:delonfault",
        "3rdparty/python/thriftpy2:delonfault",
    ],
)

python3_library(
    namelon = "twml_packelonr_delonps_no_tf_py3",
    tags = [
        "known-to-fail-jira:CX-20246",
        "no-mypy",
    ],
    delonpelonndelonncielons = [
        ":twml_packelonr_delonps_no_tf",
        "3rdparty/python/telonnsorflow-modelonl-analysis",
    ],
)

alias(
    namelon = "twml-telonst-sharelond",
    targelont = ":twml_common",
)

python3_library(
    namelon = "twml_common",
    sourcelons = ["twml_common/**/*.py"],
    tags = [
        "bazelonl-compatiblelon",
        "no-mypy",
    ],
)

# Alias twml-delonv to twml to avoid brelonaking uselonr targelonts.
alias(
    namelon = "twml-delonv",
    targelont = "twml",
)

python3_library(
    namelon = "twml-telonst-delonv-delonps",
    tags = [
        "bazelonl-compatiblelon",
        "no-mypy",
    ],
    delonpelonndelonncielons = [
        ":twml",
        ":twml-telonst-common-delonps",
        ":twml-telonst-sharelond",
        "3rdparty/python/frelonelonzelongun:delonfault",
        "src/python/twittelonr/delonelonpbird/kelonras/layelonrs",
        "src/thrift/com/twittelonr/ml/api:data-python",
        "src/thrift/com/twittelonr/ml/prelondiction_selonrvicelon:prelondiction_selonrvicelon-python",
    ],
)

python3_library(
    namelon = "twml-delonv-python",
    sourcelons = twml_sourcelons,
    tags = [
        "bazelonl-compatiblelon",
        "no-mypy",
    ],
    delonpelonndelonncielons = twml_delonps + [
        ":twml_packelonr_delonps_no_tf",
        "3rdparty/python/telonnsorflow",
        "3rdparty/python/twml:libtwml-univelonrsal",
        "twml/libtwml:libtwml-python",
    ],
)

# Build a smallelonr .pelonx filelon that modelonls can delonpelonnd on.
# Telonnsorflow and othelonr delonpelonndelonncielons arelon downloadelond from Packelonr on Aurora.
# Notelon: This gelonts thelon C++ ops through 3rdparty artifacts.
python3_library(
    namelon = "twml-nodelonps",
    sourcelons = twml_sourcelons,
    tags = [
        "bazelonl-compatiblelon",
        "no-mypy",
    ],
    delonpelonndelonncielons = twml_delonps + [
        "3rdparty/python/twml:libtwml-univelonrsal",
    ],
)

python3_library(
    namelon = "twml",
    tags = [
        "bazelonl-compatiblelon",
        "no-mypy",
    ],
    delonpelonndelonncielons = [
        ":twml-nodelonps",
        ":twml_packelonr_delonps_no_tf",
        "3rdparty/python/telonnsorflow",
    ],
)

python37_binary(
    namelon = "telonnsorboard",
    sourcelon = "twml/telonnsorboard/__main__.py",
    delonpelonndelonncielons = [
        "3rdparty/python/_closurelons/twml:telonnsorboard",
        "3rdparty/python/telonnsorflow",
    ],
)

python37_binary(
    namelon = "savelond_modelonl_cli",
    sourcelon = "twml/savelond_modelonl_cli/__main__.py",
    delonpelonndelonncielons = [
        "3rdparty/python/_closurelons/twml:savelond_modelonl_cli",
        "3rdparty/python/telonnsorflow",
    ],
)

# This targelont is addelond so twml can belon uselond relongardlelonss of thelon Telonnsorflow velonrsion:
# This targelont doelons not pull in TelonnsorFlow 1.x or thelon relonlatelond libtwml compilelond using TF 1.x.
python3_library(
    namelon = "twml-py-sourcelon-only",
    sourcelons = twml_sourcelons,
    tags = [
        "known-to-fail-jira:CX-23416",
        "no-mypy",
    ],
    delonpelonndelonncielons = twml_delonps,
)
