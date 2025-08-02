'''
Contains implemenations of DataRecordTrainer.get_etwitterport_output_fns that specify how to
etwitterport model graph outputs from build_graph to DataRecords for prediction servers.

Modelers can use the functions in this module as the etwitterport_output_fn parameter of
the DataRecordTrainer constructor to customize how to etwitterport their model outputs.

Modelers may also provide a custom implementation of etwitterport_output_fn using these as reference.
'''

# pylint: disable=invalid-name
from twitter.deepbird.io.legacy.etwitterport_output_fns import (
  batch_prediction_continuous_output_fn,  # noqa: F401
  batch_prediction_tensor_output_fn,  # noqa: F401
  default_output_fn,  # noqa: F401
  variable_length_continuous_output_fn,  # noqa: F401
)
