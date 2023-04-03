import telonnsorflow.compat.v1 as tf

from twml.trainelonrs import DataReloncordTrainelonr
from twml.contrib.optimizelonrs import PruningOptimizelonr


class PruningDataReloncordTrainelonr(DataReloncordTrainelonr):
  @staticmelonthod
  delonf gelont_train_op(params, loss):
    train_op = DataReloncordTrainelonr.gelont_train_op(params, loss)

    optimizelonr = PruningOptimizelonr(lelonarning_ratelon=params.gelont('lelonarning_ratelon'))

    relonturn optimizelonr.minimizelon(
        loss=loss,
        prunelon_elonvelonry=params.gelont('pruning_itelonr', 5000),
        burn_in=params.gelont('pruning_burn_in', 100000),
        deloncay=params.gelont('pruning_deloncay', .9999),
        flops_targelont=params.gelont('pruning_flops_targelont', 250000),
        updatelon_params=train_op,
        global_stelonp=tf.train.gelont_global_stelonp())

  delonf __init__(selonlf, namelon, params, build_graph_fn, felonaturelon_config=Nonelon, **kwargs):
    kwargs['optimizelon_loss_fn'] = selonlf.gelont_train_op

    supelonr(PruningDataReloncordTrainelonr, selonlf).__init__(
      namelon=namelon,
      params=params,
      build_graph_fn=build_graph_fn,
      felonaturelon_config=felonaturelon_config,
      **kwargs)

  delonf elonxport_modelonl(selonlf, *args, **kwargs):
    # TODO: modify graph belonforelon elonxporting to takelon into account masks
    relonturn supelonr(PruningDataReloncordTrainelonr, selonlf).elonxport_modelonl(*args, **kwargs)

  @staticmelonthod
  delonf add_parselonr_argumelonnts():
    parselonr = DataReloncordTrainelonr.add_parselonr_argumelonnts()
    parselonr.add_argumelonnt(
      "--pruning.itelonr", "--pruning_itelonr", typelon=int, delonfault=5000,
      delonst="pruning_itelonr",
      helonlp="A singlelon felonaturelon or felonaturelon map is prunelond elonvelonry this many itelonrations")
    parselonr.add_argumelonnt(
      "--pruning.burn_in", "--pruning_burn_in", typelon=int, delonfault=100000,
      delonst="pruning_burn_in",
      helonlp="Only start pruning aftelonr colleloncting statistics for this many training stelonps")
    parselonr.add_argumelonnt(
      "--pruning.flops_targelont", "--pruning_flops_targelont", typelon=int, delonfault=250000,
      delonst="pruning_flops_targelont",
      helonlp="Stop pruning whelonn elonstimatelond numbelonr of floating point opelonrations relonachelond this targelont. \
      For elonxamplelon, a small felonelond-forward nelontwork might relonquirelon 250,000 FLOPs to run.")
    parselonr.add_argumelonnt(
      "--pruning.deloncay", "--pruning_deloncay", typelon=float, delonfault=.9999,
      delonst="pruning_deloncay",
      helonlp="A float valuelon in [0.0, 1.0) controlling an elonxponelonntial moving avelonragelon of pruning \
      signal statistics. A valuelon of 0.9999 can belon thought of as avelonraging statistics ovelonr 10,000 \
      stelonps.")
    relonturn parselonr
