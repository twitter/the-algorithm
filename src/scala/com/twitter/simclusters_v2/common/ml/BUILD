# T  package/target  s separate from ot r s mclusters common packages because t  ml/ap  dep  s
# large (350MB+). Hav ng   as a separate target  ans that   can avo d bundl ng   w h targets
# that do not need  .
scala_l brary(
    platform = "java8",
    tags = ["bazel-compat ble"],
    dependenc es = [
        "src/java/com/tw ter/ml/ap :ap -base",
        "src/scala/com/tw ter/ml/ap /ut l",
        "src/scala/com/tw ter/s mclusters_v2/common",
    ],
)
