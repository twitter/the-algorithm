'''
Contains implemenations of DataRecordTrainer.get_export_output_fns that specify how to
export model graph outputs from build_graph to DataRecords for prediction servers.

Modelers can use the functions in this module as the export_output_fn parameter of
the DataRecordTrainer constructor to customize how to export their model outputs.

Modelers may also provide a custom implementation of export_output_fn using these as reference.
'''

# pylint: disable=invalid-name
from twitter.deepbird.io.legacy.export_output_fns import (
  batch_prediction_continuous_output_fn,  # noqa: F401
  batch_prediction_tensor_output_fn,  # noqa: F401
  default_output_fn,  # noqa: F401
  variable_length_continuous_output_fn,  # noqa: F401
)
