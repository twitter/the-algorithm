' Contains PercentileDiscretizerFeature and PercentileDiscretizerCalibrator used     for PercentileDiscretizer calibration '
_I='default'
_H='bin_vals'
_G='bin_counts'
_F='values'
_E='out_bits'
_D='feature_ids'
_C='weights'
_B=False
_A=None
from .calibrator import CalibrationFeature,Calibrator
import os,numpy as np,tensorflow.compat.v1 as tf,tensorflow_hub as hub,twml,twml.layers
DEFAULT_SAMPLE_WEIGHT=1
class PercentileDiscretizerFeature(CalibrationFeature):
	' Accumulates and calibrates a single sparse PercentileDiscretizer feature. '
	@staticmethod
	def _gather_debug_info(values,indices,bin_vals,bin_counts_buffer):
		"\n    Determine how many training values fell into a given bin during calibration.\n    This is calculated by finding the index of the first appearance of each bin\n    boundary in values (values may repeat, so that isn't trivially in indices.)\n    Subtracting each bin boundary index from the next tells you how many values fall in\n    that bin.\n    To get this to calculate the last bin correctly, len(values) is appended to the\n    list of bound indices.\n\n    This assumes that ``bin_vals`` excludes np.inf bin boundaries when\n    PercentileDiscretizer was calibrated\n    with fewer values than bins.\n\n    Arguments:\n      values:\n        1D ndarray of the PercentileDiscretizerFeature's accumulated values, sorted ascending\n      indices:\n        1D int32 ndarray of the indices (in values) of the bin boundaries\n      bin_vals:\n        1D ndarray containing the bin boundaries\n      bin_counts_buffer:\n        ndarray buffer for returning the PercentileDiscretizer histogram\n    ";D=bin_counts_buffer;E=bin_vals;B=values;F=np.append(np.flatnonzero(np.diff(E)),len(E)-1);C=indices.take(F)
		for (G,H) in enumerate(C):
			A=H
			while A>0 and B[A]==B[A-1]:C[G]=A=A-1
		D[:]=0;D[F]=np.diff(np.append(C,B.size))
	def calibrate(E,bin_vals,percentiles,percentile_indices,bin_counts_buffer=_A):
		'Calibrates the PercentileDiscretizerFeature into bin values for\n    use in PercentileDiscretizerCalibrator.\n    Note that this method can only be called once.\n\n    Arguments:\n      bin_vals:\n        Row in the PercentileDiscretizerCalibrator.bin_vals matrix corresponding to this feature.\n        Will be updated with the results of the calibration.\n        A 1D ndarray.\n      percentiles:\n        1D array of size n_bin with values ranging from 0 to 1.\n        For example, ``percentiles = np.linspace(0, 1, num=self._n_bin+1, dtype=np.float32)``\n      percentile_indices:\n        Empty 1D array of size n_bin used to store intermediate results when\n        calling twml.twml_optim_nearest_interpolation().\n        For example, np.empty(self._n_bin + 1, dtype=np.float32).\n      bin_counts_buffer:\n        optional ndarray buffer used for retaining count of values per PercentileDiscretizer\n        bucket (for debug and feature exploration purposes)\n\n    Returns:\n      calibrated bin_vals for use by ``PercentileDiscretizerCalibrator``\n    ';I=percentile_indices;G=percentiles;F=bin_counts_buffer;B=bin_vals
		if E._calibrated:raise RuntimeError('Can only calibrate once')
		if B.ndim!=1:raise RuntimeError('Expecting bin_vals row')
		E._concat_arrays();J=E._features_dict[_F];K=E._features_dict[_C];C=np.argsort(J);D=J.take(C);A=K.take(C);A=np.cumsum(A,out=K);A-=A[0]
		if A[-1]>0:A/=A[-1]
		if D.size<B.size:
			B.fill(np.inf);B[0]=-np.inf;B[1:D.size+1]=D
			if F is not _A:M=np.arange(D.size,dtype=np.int32);F.fill(0);E._gather_debug_info(D,M,B[1:D.size+1],F[1:D.size+1])
		else:
			H=np.arange(0,A.size,dtype=np.float32);A=A.reshape(1,A.size);H=H.reshape(1,A.size);N=twml.Array(G.reshape(G.size,1));O=twml.Array(A);P=twml.Array(H);Q=twml.Array(I.reshape(G.size,1));L=twml.CLIB.twml_optim_nearest_interpolation(Q.handle,N.handle,O.handle,P.handle)
			if L!=1000:raise ValueError('twml.CLIB.twml_optim_nearest_interpolation\n          caught an error (see previous stdout). Error code: '%L)
			C=C[:B.size];C[:]=I;C[0]=0;C[-1]=A.size-1;D.take(C,out=B)
			if F is not _A:E._gather_debug_info(D,C,B,F)
		E._calibrated=True
class PercentileDiscretizerCalibrator(Calibrator):
	" Accumulates features and their respective values for PercentileDiscretizer calibration.\n  Internally, each feature's values is accumulated via its own\n  ``PercentileDiscretizerFeature`` object.\n  The steps for calibration are typically as follows:\n\n   1. accumulate feature values from batches by calling ``accumulate()``;\n   2. calibrate all feature into PercentileDiscretizer bin_vals by calling ``calibrate()``; and\n   3. convert to a twml.layers.PercentileDiscretizer layer by calling ``to_layer()``.\n\n  "
	def __init__(A,n_bin,out_bits,bin_histogram=True,allow_empty_calibration=_B,**B):' Constructs an PercentileDiscretizerCalibrator instance.\n\n    Arguments:\n      n_bin:\n        the number of bins per feature to use for PercentileDiscretizer.\n        Note that each feature actually maps to n_bin+1 output IDs.\n      out_bits:\n        The maximum number of bits to use for the output IDs.\n        2**out_bits must be greater than bin_ids.size or an error is raised.\n      bin_histogram:\n        When True (the default), gathers information during calibration\n        to build a bin_histogram.\n      allow_empty_calibration:\n        allows operation where we might not calibrate any features.\n        Default False to error out if no features were calibrated.\n        Typically, values of uncalibrated features pass through discretizers\n        untouched (though the feature ids will be truncated to obey out_bits).\n    ';super(PercentileDiscretizerCalibrator,A).__init__(**B);A._n_bin=n_bin;A._out_bits=out_bits;A._bin_ids=_A;A._bin_vals=np.empty(0,dtype=np.float32);A._bin_histogram=bin_histogram;A._bin_histogram_dict=_A;A._hash_map_counter=0;A._hash_map={};A._discretizer_feature_dict={};A._allow_empty_calibration=allow_empty_calibration
	@property
	def bin_ids(self):'\n    Gets bin_ids\n    ';return self._bin_ids
	@property
	def bin_vals(self):'\n    Gets bin_vals\n    ';return self._bin_vals
	@property
	def hash_map(self):'\n    Gets hash_map\n    ';return self._hash_map
	@property
	def discretizer_feature_dict(self):'\n    Gets feature_dict\n    ';return self._discretizer_feature_dict
	def accumulate_features(C,inputs,name):'\n    Wrapper around accumulate for PercentileDiscretizer.\n    Arguments:\n      inputs:\n        batch that will be accumulated\n      name:\n        name of the tensor that will be accumulated\n\n    ';B=inputs;A=B[name];D=A.indices[:,1];E=A.indices[:,0];F=np.take(B[_C],E);return C.accumulate(D,A.values,F)
	def accumulate_feature(B,output):'\n    Wrapper around accumulate for trainer API.\n    Arguments:\n      output:\n        output of prediction of build_graph for calibrator\n    ';A=output;return B.accumulate(A[_D],A['feature_values'],A[_C])
	def accumulate(C,feature_keys,feature_vals,weights=_A):
		'Accumulate a single batch of feature keys, values and weights.\n\n    These are accumulate until ``calibrate()`` is called.\n\n    Arguments:\n      feature_keys:\n        1D int64 array of feature keys.\n      feature_vals:\n        1D float array of feature values. Each element of this array\n        maps to the commensurate element in ``feature_keys``.\n      weights:\n        Defaults to weights of 1.\n        1D array containing the weights of each feature key, value pair.\n        Typically, this is the weight of each sample (but you still need\n        to provide one weight per key,value pair).\n        Each element of this array maps to the commensurate element in feature_keys.\n    ';D=feature_vals;B=feature_keys;A=weights
		if B.ndim!=1:raise ValueError('Expecting 1D feature_keys, got %dD'%B.ndim)
		if D.ndim!=1:raise ValueError('Expecting 1D feature_values, got %dD'%D.ndim)
		if D.size!=B.size:raise ValueError('Expecting feature_keys.size == feature_values.size, got %d != %d'%(B.size,D.size))
		if A is not _A:
			A=np.squeeze(A)
			if A.ndim!=1:raise ValueError('Expecting 1D weights, got %dD'%A.ndim)
			elif A.size!=B.size:raise ValueError('Expecting feature_keys.size == weights.size, got %d != %d'%(B.size,A.size))
		if A is _A:A=np.full(D.size,fill_value=DEFAULT_SAMPLE_WEIGHT)
		H=np.unique(B)
		for E in H:
			G=np.where(B==E)
			if E not in C._discretizer_feature_dict:C._hash_map[E]=C._hash_map_counter;C._hash_map_counter+=1;F=PercentileDiscretizerFeature(E);C._discretizer_feature_dict[E]=F
			else:F=C._discretizer_feature_dict[E]
			F.add_values({_F:D[G],_C:A[G]})
	def calibrate(A,debug=_B):
		'\n    Calibrates each PercentileDiscretizer feature after accumulation is complete.\n\n    Arguments:\n      debug:\n        Boolean to request debug info be returned by the method.\n        (see Returns section below)\n\n    The calibration results are stored in two matrices:\n      bin_ids:\n        2D array of size number of accumulate ``features x n_bin+1``.\n        Contains the new IDs generated by PercentileDiscretizer. Each row maps to a feature.\n        Each row maps to different value bins. The IDs\n        are in the range ``1 -> bin_ids.size+1``\n      bin_vals:\n        2D array of the same size as bin_ids.\n        Each row maps to a feature. Each row contains the bin boundaries.\n        These boundaries represent feature values.\n\n    Returns:\n      if debug is True, the method returns\n\n        - 1D int64 array of feature_ids\n        - 2D float32 array copy of bin_vals (the bin boundaries) for each feature\n        - 2D int64 array of bin counts corresponding to the bin boundaries\n\n    ';D=debug;B=len(A._discretizer_feature_dict)
		if B==0 and not A._allow_empty_calibration:raise RuntimeError('Need to accumulate some features for calibration\nLikely, the calibration data is empty. This can\nhappen if the dataset is small, or if the following\ncli args are set too low:\n  --discretizer_keep_rate (default=0.0008)\n  --discretizer_parts_downsampling_rate (default=0.2)\nConsider increasing the values of these args.\nTo allow empty calibration data (and degenerate discretizer),\nuse the allow_empty_calibration input of the constructor.')
		A._bin_ids=np.arange(1,B*(A._n_bin+1)+1);A._bin_ids=A._bin_ids.reshape(B,A._n_bin+1);A._bin_vals.resize(B,A._n_bin+1);J=np.empty(A._n_bin+1,dtype=np.float32);K=np.linspace(0,1,num=A._n_bin+1,dtype=np.float32)
		if D or A._bin_histogram:E=np.empty(B,dtype=np.int64);F=np.empty((B,A._n_bin+1),dtype=np.int64)
		L=tf.keras.utils.Progbar(B);H=A._discretizer_feature_dict
		for (G,C) in enumerate(H):
			if D or A._bin_histogram:E[A._hash_map[C]]=C;I=F[A._hash_map[C]]
			else:I=_A
			H[C].calibrate(A._bin_vals[A._hash_map[C]],K,J,bin_counts_buffer=I)
			if G%max(1.0,round(B/20))==0 or G==B-1:L.update(G+1)
		super(PercentileDiscretizerCalibrator,A).calibrate()
		if A._bin_histogram:A._bin_histogram_dict={_D:E,_G:F,_H:A._bin_vals,_E:A._out_bits}
		if D:return E,A._bin_vals.copy(),F
		return _A
	def _create_discretizer_layer(A,n_feature,hash_map_keys,hash_map_values,feature_offsets,name):return twml.layers.PercentileDiscretizer(n_feature=n_feature,n_bin=A._n_bin,out_bits=A._out_bits,bin_values=A._bin_vals.flatten(),hash_keys=hash_map_keys,hash_values=hash_map_values.astype(np.int64),bin_ids=A._bin_ids.flatten().astype(np.int64),feature_offsets=feature_offsets,name=name,**A._kwargs)
	def to_layer(A,name=_A):
		'\n    Returns a twml.layers.PercentileDiscretizer Layer\n    that can be used for feature discretization.\n\n    Arguments:\n      name:\n        name-scope of the PercentileDiscretizer layer\n    ';B=len(A._discretizer_feature_dict);C=B*(A._n_bin+1)
		if not A._calibrated:raise RuntimeError('Expecting prior call to calibrate()')
		if A._bin_ids.shape[0]!=B:raise RuntimeError('Expecting self._bin_ids.shape[0]         != len(self._discretizer_feature_dict)')
		if A._bin_vals.shape[0]!=B:raise RuntimeError('Expecting self._bin_vals.shape[0]         != len(self._discretizer_feature_dict)')
		if 2**A._out_bits<=C:raise ValueError('Maximum number of features created by discretizer is\n        %d but requested that the output be limited to %d values (%d bits),\n        which is smaller than that. Please ensure the output has enough bits\n        to represent at least the new features'%(C,2**A._out_bits,A._out_bits))
		D=np.arange(0,C,A._n_bin+1,dtype='int64');E=np.array(list(A._hash_map.keys()),dtype=np.int64);F=np.array(list(A._hash_map.values()),dtype=np.float32);G=A._create_discretizer_layer(B,E,F,D,name);return G
	def get_layer_args(A):'\n    Returns layer arguments required to implement multi-phase training.\n    See twml.calibrator.Calibrator.get_layer_args for more detailed documentation.\n    ';B={'n_feature':len(A._discretizer_feature_dict),'n_bin':A._n_bin,_E:A._out_bits};return B
	def add_hub_signatures(B,name):'\n    Add Hub Signatures for each calibrator\n\n    Arguments:\n      name:\n        Calibrator name\n    ';A=tf.sparse_placeholder(tf.float32);C=B.to_layer();hub.add_signature(inputs=A,outputs=C(A,keep_inputs=_B),name=name)
	def write_summary(D,writer,sess=_A):
		'\n    This method is called by save() to write a histogram of\n    PercentileDiscretizer feature bins to disk. A histogram is included for each\n    feature.\n\n    Arguments:\n      writer:\n        tf.summary.FilteWriter instance.\n        used to add summaries to event files for inclusion in tensorboard.\n      sess:\n        tf.Session instance. Used to produces summaries for the writer.\n    ';A=tf.placeholder(tf.int64);B=D._bin_histogram_dict[_G];E=tf.summary.histogram('discretizer_feature_bin_counts',A)
		for C in range(B.shape[0]):F=sess.run(E,feed_dict={A:B[C]});writer.add_summary(F,global_step=C)
	def write_summary_json(A,save_dir,name=_I):'\n    Export bin information to HDFS.\n    \n    Arguments:\n      save_dir:\n        name of the saving directory.\n      name:\n        prefix of the saved hub signature. Default (string): "default".\n    ';B=os.path.join(save_dir,name+'_bin.json');C={_D:A._bin_histogram_dict[_D].tolist(),'bin_boundaries':A._bin_histogram_dict[_H].tolist(),'output_bits':A._bin_histogram_dict[_E]};twml.write_file(B,C,encode='json')
	def save(B,save_dir,name=_I,verbose=_B):
		'Save the calibrator into the given save_directory using TF Hub.\n    Arguments:\n      save_dir:\n        name of the saving directory.\n      name:\n        prefix of the saved hub signature. Default (string): "default".\n    ';A=save_dir;C=name
		if not B._calibrated:raise RuntimeError('Expecting prior call to calibrate().Cannot save() prior to calibrate()')
		def D():A=tf.sparse_placeholder(tf.float32);D=B.to_layer();hub.add_signature(inputs=A,outputs=D(A,keep_inputs=_B),name=C);hub.add_signature(inputs=A,outputs=D(A,keep_inputs=True),name=C+'_keep_inputs')
		E=hub.create_module_spec(D)
		with tf.Graph().as_default():
			F=hub.Module(E)
			with tf.Session()as G:F.export(A,G)
		B.write_summary_json(A,C)