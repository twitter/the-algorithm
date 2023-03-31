'\nContains implemenations of DataRecordTrainer.get_export_output_fns that specify how to\nexport model graph outputs from build_graph to DataRecords for prediction servers.\n\nModelers can use the functions in this module as the export_output_fn parameter of\nthe DataRecordTrainer constructor to customize how to export their model outputs.\n\nModelers may also provide a custom implementation of export_output_fn using these as reference.\n'
from twitter.deepbird.io.legacy.export_output_fns import batch_prediction_continuous_output_fn,batch_prediction_tensor_output_fn,default_output_fn,variable_length_continuous_output_fn