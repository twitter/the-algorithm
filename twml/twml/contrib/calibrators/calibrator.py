' Contains the base classes for CalibrationFeature and Calibrator '
_B=False
_A=None
from collections import defaultdict
import numpy as np,tensorflow.compat.v1 as tf,tensorflow_hub as hub,twml,twml.util
class CalibrationFeature:
	'\n  Accumulates values and weights for individual features.\n  Typically, each unique feature defined in the accumulated SparseTensor or Tensor\n  would have its own CalibrationFeature instance.\n  '
	def __init__(A,feature_id):' Constructs a CalibrationFeature\n\n    Arguments:\n      feature_id:\n        number identifying the feature.\n    ';A.feature_id=feature_id;A._calibrated=_B;A._features_dict=defaultdict(list)
	def add_values(C,new_features):
		'\n    Extends lists to contain the values in this batch\n    ';A=new_features
		for B in A:C._features_dict[B].append(A[B])
	def _concat_arrays(A):'\n    This class calls this function after you have added all the values.\n    It creates a dictionary with the concatanated arrays\n    ';A._features_dict.update(((A,np.concatenate(B))for(A,B)in A._features_dict.items()))
	def calibrate(A,*B,**C):raise NotImplementedError
class Calibrator:
	'\n  Accumulates features and their respective values for Calibration\n  The steps for calibration are typically as follows:\n\n   1. accumulate feature values from batches by calling ``accumulate()`` and;\n   2. calibrate by calling ``calibrate()``;\n   3. convert to a twml.layers layer by calling ``to_layer()``.\n\n  Note you can only use one calibrator per Trainer.\n  '
	def __init__(A,calibrator_name=_A,**C):
		'\n    Arguments:\n      calibrator_name.\n        Default: if set to None it will be the same as the class name.\n        Please be reminded that if in the model there are many calibrators\n        of the same type the calibrator_name should be changed to avoid confusion.\n    ';B=calibrator_name;A._calibrated=_B
		if B is _A:B=twml.util.to_snake_case(A.__class__.__name__)
		A._calibrator_name=B;A._kwargs=C
	@property
	def is_calibrated(self):return self._calibrated
	@property
	def name(self):return self._calibrator_name
	def accumulate(A,*B,**C):'Accumulates features and their respective values for Calibration.';raise NotImplementedError
	def calibrate(A):'Calibrates after the accumulation has ended.';A._calibrated=True
	def to_layer(A,name=_A):'\n    Returns a twml.layers.Layer instance with the result of calibrator.\n\n    Arguments:\n      name:\n        name-scope of the layer\n    ';raise NotImplementedError
	def get_layer_args(A):'\n    Returns layer arguments required to implement multi-phase training.\n\n    Returns:\n      dictionary of Layer constructor arguments to initialize the\n      layer Variables. Typically, this should contain enough information\n      to initialize empty layer Variables of the correct size, which will then\n      be filled with the right data using init_map.\n    ';raise NotImplementedError
	def save(A,save_dir,name='default',verbose=_B):
		'Save the calibrator into the given save_directory.\n    Arguments:\n      save_dir:\n        name of the saving directory. Default (string): "default".\n      name:\n        name for the calibrator.\n    '
		if not A._calibrated:raise RuntimeError('Expecting prior call to calibrate().Cannot save() prior to calibrate()')
		def B():B=tf.sparse_placeholder(tf.float32);C=A.to_layer();D=C(B);hub.add_signature(inputs=B,outputs=D,name=name)
		C=hub.create_module_spec(B)
		with tf.Graph().as_default():
			D=hub.Module(C)
			with tf.Session()as E:D.export(save_dir,E)
	def write_summary(A,writer,sess=_A):'\n    This method is called by save() to write tensorboard summaries to disk.\n    See MDLCalibrator.write_summary for an example.\n    By default, the method does nothing. It can be overloaded by child-classes.\n\n    Arguments:\n      writer:\n        `tf.summary.FilteWriter\n        <https://www.tensorflow.org/versions/master/api_docs/python/tf/summary/FileWriter>`_\n        instance.\n        The ``writer`` is used to add summaries to event files for inclusion in tensorboard.\n      sess (optional):\n        `tf.Session <https://www.tensorflow.org/versions/master/api_docs/python/tf/Session>`_\n        instance. The ``sess`` is used to produces summaries for the writer.\n    '