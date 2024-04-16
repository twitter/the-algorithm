python37_b nary(
    na  = "update_warm_start_c ckpo nt",
    s ce = "update_warm_start_c ckpo nt.py",
    tags = ["no- py"],
    dependenc es = [
        ":deep_norm_l b",
        "3rdparty/python/_closures/fr gate/fr gate-pushserv ce-opens ce/src/ma n/python/models/ avy_rank ng:update_warm_start_c ckpo nt",
    ],
)

python3_l brary(
    na  = "params_l b",
    s ces = ["params.py"],
    tags = ["no- py"],
    dependenc es = [
        "3rdparty/python/pydant c:default",
        "src/python/tw ter/deepb rd/projects/mag c_recs/v11/l b:params_l b",
    ],
)

python3_l brary(
    na  = "features_l b",
    s ces = ["features.py"],
    tags = ["no- py"],
    dependenc es = [
        ":params_l b",
        "src/python/tw ter/deepb rd/projects/mag c_recs/l bs",
        "twml:twml-nodeps",
    ],
)

python3_l brary(
    na  = "model_pools_l b",
    s ces = ["model_pools.py"],
    tags = ["no- py"],
    dependenc es = [
        ":features_l b",
        ":params_l b",
        "src/python/tw ter/deepb rd/projects/mag c_recs/v11/l b:model_l b",
    ],
)

python3_l brary(
    na  = "graph_l b",
    s ces = ["graph.py"],
    tags = ["no- py"],
    dependenc es = [
        ":params_l b",
        "src/python/tw ter/deepb rd/projects/mag c_recs/l bs",
    ],
)

python3_l brary(
    na  = "run_args_l b",
    s ces = ["run_args.py"],
    tags = ["no- py"],
    dependenc es = [
        ":features_l b",
        ":params_l b",
        "twml:twml-nodeps",
    ],
)

python3_l brary(
    na  = "deep_norm_l b",
    s ces = ["deep_norm.py"],
    tags = ["no- py"],
    dependenc es = [
        ":features_l b",
        ":graph_l b",
        ":model_pools_l b",
        ":params_l b",
        ":run_args_l b",
        "src/python/tw ter/deepb rd/projects/mag c_recs/l bs",
        "src/python/tw ter/deepb rd/ut l/data",
        "twml:twml-nodeps",
    ],
)

python3_l brary(
    na  = "eval_l b",
    s ces = ["eval.py"],
    tags = ["no- py"],
    dependenc es = [
        ":features_l b",
        ":graph_l b",
        ":model_pools_l b",
        ":params_l b",
        ":run_args_l b",
        "src/python/tw ter/deepb rd/projects/mag c_recs/l bs",
        "twml:twml-nodeps",
    ],
)

python37_b nary(
    na  = "deep_norm",
    s ce = "deep_norm.py",
    dependenc es = [
        ":deep_norm_l b",
        "3rdparty/python/_closures/fr gate/fr gate-pushserv ce-opens ce/src/ma n/python/models/ avy_rank ng:deep_norm",
        "twml",
    ],
)

python37_b nary(
    na  = "eval",
    s ce = "eval.py",
    dependenc es = [
        ":eval_l b",
        "3rdparty/python/_closures/fr gate/fr gate-pushserv ce-opens ce/src/ma n/python/models/ avy_rank ng:eval",
        "twml",
    ],
)

python3_l brary(
    na  = "mlwf_l bs",
    tags = ["no- py"],
    dependenc es = [
        ":deep_norm_l b",
        "twml",
    ],
)

python37_b nary(
    na  = "tra n_model",
    s ce = "deep_norm.py",
    dependenc es = [
        ":deep_norm_l b",
        "3rdparty/python/_closures/fr gate/fr gate-pushserv ce-opens ce/src/ma n/python/models/ avy_rank ng:tra n_model",
    ],
)

python37_b nary(
    na  = "tra n_model_local",
    s ce = "deep_norm.py",
    dependenc es = [
        ":deep_norm_l b",
        "3rdparty/python/_closures/fr gate/fr gate-pushserv ce-opens ce/src/ma n/python/models/ avy_rank ng:tra n_model_local",
        "twml",
    ],
)

python37_b nary(
    na  = "eval_model_local",
    s ce = "eval.py",
    dependenc es = [
        ":eval_l b",
        "3rdparty/python/_closures/fr gate/fr gate-pushserv ce-opens ce/src/ma n/python/models/ avy_rank ng:eval_model_local",
        "twml",
    ],
)

python37_b nary(
    na  = "eval_model",
    s ce = "eval.py",
    dependenc es = [
        ":eval_l b",
        "3rdparty/python/_closures/fr gate/fr gate-pushserv ce-opens ce/src/ma n/python/models/ avy_rank ng:eval_model",
    ],
)

python37_b nary(
    na  = "mlwf_model",
    s ce = "deep_norm.py",
    dependenc es = [
        ":mlwf_l bs",
        "3rdparty/python/_closures/fr gate/fr gate-pushserv ce-opens ce/src/ma n/python/models/ avy_rank ng:mlwf_model",
    ],
)
