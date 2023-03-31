import tensorflow.compat.v1 as tf

from twml.trainers import DataRecordTrainer
from twml.contrib.optimizers import PruningOptimizer


class PruningDataRecordTrainer(DataRecordTrainer):
  @staticmethod
  def get_train_op(params, loss):
    train_op = DataRecordTrainer.get_train_op(params, loss)

    optimizer = PruningOptimizer(learning_rate=params.get('learning_rate'))

    return optimizer.minimize(
        loss=loss,
        prune_every=params.get('pruning_iter', 5000),
        burn_in=params.get('pruning_burn_in', 100000),
        decay=params.get('pruning_decay', .9999),
        flops_target=params.get('pruning_flops_target', 250000),
        update_params=train_op,
        global_step=tf.train.get_global_step())

  def __init__(self, name, params, build_graph_fn, feature_config=None, **kwargs):
    kwargs['optimize_loss_fn'] = self.get_train_op

    super(PruningDataRecordTrainer, self).__init__(
      name=name,
      params=params,
      build_graph_fn=build_graph_fn,
      feature_config=feature_config,
      **kwargs)

  def export_model(self, *args, **kwargs):
    # TODO: modify graph before exporting to take into account masks
    return super(PruningDataRecordTrainer, self).export_model(*args, **kwargs)

  @staticmethod
  def add_parser_arguments():
    parser = DataRecordTrainer.add_parser_arguments()
    parser.add_argument(
      "--pruning.iter", "--pruning_iter", type=int, default=5000,
      dest="pruning_iter",
      help="A single feature or feature map is pruned every this many iterations")
    parser.add_argument(
      "--pruning.burn_in", "--pruning_burn_in", type=int, default=100000,
      dest="pruning_burn_in",
      help="Only start pruning after collecting statistics for this many training steps")
    parser.add_argument(
      "--pruning.flops_target", "--pruning_flops_target", type=int, default=250000,
      dest="pruning_flops_target",
      help="Stop pruning when estimated number of floating point operations reached this target. \
      For example, a small feed-forward network might require 250,000 FLOPs to run.")
    parser.add_argument(
      "--pruning.decay", "--pruning_decay", type=float, default=.9999,
      dest="pruning_decay",
      help="A float value in [0.0, 1.0) controlling an exponential moving average of pruning \
      signal statistics. A value of 0.9999 can be thought of as averaging statistics over 10,000 \
      steps.")
    return parser
