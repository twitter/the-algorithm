# pylint: disable=arguments-differ, invalid-name
"""
This file contains the DataRecordTrainer class.
"""
import warnings

import twml
from twml.trainers import DataRecordTrainer


class BatchPredictionRequestTrainer(DataRecordTrainer):  # pylint: disable=abstract-method
  """
  The ``BatchPredictionRequestTrainer`` implementation is intended to satisfy use cases
  that input is BatchPredictionRequest at Twitter and also where only the build_graph methods
  needs to be overridden. For this reason, ``Trainer.[train,eval]_input_fn`` methods
  assume a DataRecord dataset partitioned into part files stored in compressed (e.g. gzip) format.

  For use-cases that differ from this common Twitter use-case,
  further Trainer methods can be overridden.
  If that still doesn't provide enough flexibility, the user can always
  use the tf.estimator.Esimator or tf.session.run directly.
  """

  def __init__(
          self, name, params,
          build_graph_fn,
          feature_config=None,
          **kwargs):
    """
    The BatchPredictionRequestTrainer constructor builds a
    ``tf.estimator.Estimator`` and stores it in self.estimator.
    For this reason, BatchPredictionRequestTrainer accepts the same Estimator constructor arguments.
    It also accepts additional arguments to facilitate metric evaluation and multi-phase training
    (init_from_dir, init_map).

    Args:
      parent arguments:
        See the `Trainer constructor <#twml.trainers.Trainer.__init__>`_ documentation
        for a full list of arguments accepted by the parent class.
      name, params, build_graph_fn (and other parent class args):
        see documentation for twml.Trainer and twml.DataRecordTrainer doc.
      feature_config:
        An object of type FeatureConfig describing what features to decode.
        Defaults to None. But it is needed in the following cases:
          - `get_train_input_fn()` / `get_eval_input_fn()` is called without a `parse_fn`
          - `learn()`, `train()`, `eval()`, `calibrate()` are called without providing `*input_fn`.

      **kwargs:
        further kwargs can be specified and passed to the Estimator constructor.
    """

    # Check and update train_batch_size and eval_batch_size in params before initialization
    # to print correct parameter logs and does not stop running
    # This overwrites batch_size parameter constrains in twml.trainers.Trainer.check_params
    updated_params = self.check_batch_size_params(params)
    super(BatchPredictionRequestTrainer, self).__init__(
      name=name, params=updated_params, build_graph_fn=build_graph_fn, **kwargs)

  def check_batch_size_params(self, params):
    """ Verify that params has the correct key,values """
    # updated_params is an instance of tensorflow.contrib.training.HParams
    updated_params = twml.util.convert_to_hparams(params)
    param_values = updated_params.values()

    # twml.trainers.Trainer.check_params already checks other constraints,
    # such as being an integer
    if 'train_batch_size' in param_values:
      if not isinstance(updated_params.train_batch_size, int):
        raise ValueError("Expecting params.train_batch_size to be an integer.")
      if param_values['train_batch_size'] != 1:
        # This can be a bit annoying to force users to pass the batch sizes,
        # but it is good to let them know what they actually use in the models
        # Use warning instead of ValueError in there to continue the run
        # and print out that train_batch_size is changed
        warnings.warn('You are processing BatchPredictionRequest data, '
          'train_batch_size is always 1.\n'
          'The number of DataRecords in a batch is determined by the size '
          'of each BatchPredictionRequest.\n'
          'If you did not pass train.batch_size or eval.batch_size, and '
          'the default batch_size 32 was in use,\n'
          'please pass --train.batch_size 1 --eval.batch_size 1')
        # If the upper error warning, change/pass --train.batch_size 1
        # so that train_batch_size = 1
        updated_params.train_batch_size = 1

    if 'eval_batch_size' in param_values:
      if not isinstance(updated_params.train_batch_size, int):
        raise ValueError('Expecting params.eval_batch_size to be an integer.')
      if param_values['eval_batch_size'] != 1:
        # This can be a bit annoying to force users to pass the batch sizes,
        # but it is good to let them know what they actually use in the models
        # Use warning instead of ValueError in there to continue the run
        # and print out that eval_batch_size is changed
        warnings.warn('You are processing BatchPredictionRequest data, '
          'eval_batch_size is also always 1.\n'
          'The number of DataRecords in a batch is determined by the size '
          'of each BatchPredictionRequest.\n'
          'If you did not pass train.batch_size or eval.batch_size, and '
          'the default batch_size 32 was in use,\n'
          'please pass --train.batch_size 1 --eval.batch_size 1')
        # If the upper warning raises, change/pass --eval.batch_size 1
        # so that eval_batch_size = 1
        updated_params.eval_batch_size = 1

    if 'eval_batch_size' not in param_values:
      updated_params.eval_batch_size = 1

    if not updated_params.eval_batch_size:
      updated_params.eval_batch_size = 1

    return updated_params

  @staticmethod
  def add_batch_prediction_request_arguments():
    """
    Add commandline args to parse typically for the BatchPredictionRequestTrainer class.
    Typically, the user calls this function and then parses cmd-line arguments
    into an argparse.Namespace object which is then passed to the Trainer constructor
    via the params argument.

    See the `code <_modules/twml/argument_parser.html#get_trainer_parser>`_
    for a list and description of all cmd-line arguments.

    Returns:
      argparse.ArgumentParser instance with some useful args already added.
    """
    parser = super(BatchPredictionRequestTrainer,
      BatchPredictionRequestTrainer).add_parser_arguments()

    # mlp arguments
    parser.add_argument(
      '--model.use_existing_discretizer', action='store_true',
      dest="model_use_existing_discretizer",
      help='Load a pre-trained calibration or train a new one')
    parser.add_argument(
      '--model.use_binary_values', action='store_true',
      dest='model_use_binary_values',
      help='Use the use_binary_values optimization')

    # control hom many featues we keep in sparse tensors
    # 12 is enough for learning-to-rank for now
    parser.add_argument(
      '--input_size_bits', type=int, default=12,
      help='Number of bits allocated to the input size')

    parser.add_argument(
      '--loss_function', type=str, default='ranknet',
      dest='loss_function',
      help='Options are pairwise: ranknet (default), lambdarank, '
      'listnet, listmle, attrank, '
      'pointwise')

    # whether convert sparse tensors to dense tensor
    # in order to use dense normalization methods
    parser.add_argument(
      '--use_dense_tensor', action='store_true',
      dest='use_dense_tensor',
      default=False,
      help='If use_dense_tensor is False, '
      'sparse tensor and spare normalization are in use. '
      'If use_dense_tensor is True, '
      'dense tensor and dense normalization are in use.')

    parser.add_argument(
      '--dense_normalization', type=str, default='mean_max_normalizaiton',
      dest='dense_normalization',
      help='Options are mean_max_normalizaiton (default), standard_normalizaiton')

    parser.add_argument(
      '--sparse_normalization', type=str, default='SparseMaxNorm',
      dest='sparse_normalization',
      help='Options are SparseMaxNorm (default), SparseBatchNorm')

    # so far only used in pairwise learning-to-rank
    parser.add_argument(
      '--mask', type=str, default='full_mask',
      dest='mask',
      help='Options are full_mask (default), diag_mask')

    return parser
