' Contains Isotonic Calibration'
_E='values'
_D='targets'
_C='weights'
_B=False
_A=None
from .calibrator import CalibrationFeature,Calibrator
from absl import logging
import numpy as np
from sklearn.isotonic import isotonic_regression
import tensorflow.compat.v1 as tf,tensorflow_hub as hub,twml,twml.layers
DEFAULT_SAMPLE_WEIGHT=1
def sort_values(inputs,target,weight,ascending=True):
	'\n  Sorts arrays based on the first array.\n\n  Arguments:\n    inputs:\n      1D array which will dictate the order which the remainder 2 arrays will be sorted\n    target:\n      1D array\n    weight:\n      1D array\n    ascending:\n      Boolean. If set to True (the default), sorts values in ascending order.\n\n  Returns:\n    sorted inputs:\n      1D array sorted by the order of `ascending`\n    sorted targets:\n      1D array\n    sorted weight:\n      1D array\n  ';C=weight;D=target;B=inputs
	if len(B)!=len(D):raise ValueError('Expecting inputs and target sizes to match')
	if len(B)!=len(C):raise ValueError('Expecting inputs and weight sizes to match')
	A=B.argsort()
	if not ascending:A=A[::-1]
	return B[A],D[A],C[A]
class IsotonicFeature(CalibrationFeature):
	'\n  IsotonicFeature adds values, weights and targets to each feature and then runs\n  isotonic regression by calling `sklearn.isotonic.isotonic_regression\n  <http://scikit-learn.org/stable/auto_examples/plot_isotonic_regression.html>`_\n  '
	def _get_bin_boundaries(E,n_samples,bins,similar_bins):
		'\n    Calculates the sample indices that define bin boundaries\n\n    Arguments:\n      n_samples:\n        (int) number of samples\n      bins:\n        (int) number of bins. Needs to be smaller or equal than n_samples.\n      similar_bins:\n        (bool) If True, samples will be distributed in bins of equal size (up to one sample).\n        If False bins will be filled with step = N_samples//bins, and last bin will contain all remaining samples.\n        Note that equal_bins=False can create a last bins with a very large number of samples.\n\n    Returns:\n      (list[int]) List of sample indices defining bin boundaries\n    ';A=bins;B=n_samples
		if A>B:raise ValueError('The number of bins needs to be less than or equal to the number of samples. Currently bins={0} and n_samples={1}.'.format(A,B))
		D=B//A
		if similar_bins:C=np.linspace(0,B-D,num=A,dtype=int)
		else:C=range(0,D*A,D)
		C=np.append(C,B);return C
	def calibrate(A,bins,similar_bins=_B,debug=_B):
		'Calibrates the IsotonicFeature into calibrated weights and bias.\n\n    1. Sorts the values of the feature class, based on the order of values\n    2. Performs isotonic regression using sklearn.isotonic.isotonic_regression\n    3. Performs the binning of the samples, in order to obtain the final weight and bias\n      which will be used for inference\n\n    Note that this method can only be called once.\n\n    Arguments:\n      bins:\n        number of bins.\n      similar_bins:\n        If True, samples will be distributed in bins of equal size (up to one sample).\n        If False bins will be filled with step = N_samples//bins, and last bin will contain all remaining samples.\n        Note that equal_bins=False can create a last bins with a very large number of samples.\n      debug:\n        Defaults to False. If debug is set to true, output other parameters useful for debugging.\n\n    Returns:\n      [calibrated weight, calibrated bias]\n    '
		if A._calibrated:raise RuntimeError('Can only calibrate once')
		A._concat_arrays();M=A._features_dict[_D];N=A._features_dict[_E];O=A._features_dict[_C];P,I,J=sort_values(inputs=N,target=M,weight=O);K=isotonic_regression(I,sample_weight=J);C=[];E=[];F=[];D=[];L=A._get_bin_boundaries(len(K),bins,similar_bins=similar_bins)
		for (G,H) in zip(L,L[1:]):Q=P[int(G):int(H)];R=K[int(G):int(H)];S=I[int(G):int(H)];B=J[int(G):int(H)];C.append(np.sum(Q*B)/np.squeeze(np.sum(B)));D.append(np.sum(R*B)/np.squeeze(np.sum(B)));E.append(np.sum(S*B)/np.squeeze(np.sum(B)));F.append(np.squeeze(np.sum(B)))
		C=np.asarray(C).T;D=np.asarray(D).T;E=np.asarray(E).T;F=np.asarray(F).T;A._calibrated=True
		if debug:return C,D,E,F
		return C,D
class IsotonicCalibrator(Calibrator):
	" Accumulates features and their respective values for isotonic calibration.\n  Internally, each feature's values is accumulated via its own isotonicFeature object.\n  The steps for calibration are typically as follows:\n\n   1. accumulate feature values from batches by calling ``accumulate()``;\n   2. calibrate all feature into Isotonic ``bpreds``, ``rpreds`` by calling ``calibrate()``; and\n   3. convert to a ``twml.layers.Isotonic`` layer by calling ``to_layer()``.\n\n  "
	def __init__(A,n_bin,similar_bins=_B,**B):' Constructs an isotonicCalibrator instance.\n\n    Arguments:\n      n_bin:\n        the number of bins per feature to use for isotonic.\n        Note that each feature actually maps to ``n_bin+1`` output IDs.\n    ';super(IsotonicCalibrator,A).__init__(**B);A._n_bin=n_bin;A._similar_bins=similar_bins;A._ys_input=[];A._xs_input=[];A._isotonic_feature_dict={}
	def accumulate_feature(B,output):'\n    Wrapper around accumulate for trainer API.\n    Arguments:\n      output: output of prediction of build_graph for calibrator\n    ';A=output;C=A[_C]if _C in A else _A;return B.accumulate(A['predictions'],A[_D],C)
	def accumulate(D,predictions,targets,weights=_A):
		'\n    Accumulate a single batch of class predictions, class targets and class weights.\n    These are accumulated until calibrate() is called.\n\n    Arguments:\n      predictions:\n        float matrix of class values. Each dimension corresponds to a different class.\n        Shape is ``[n, d]``, where d is the number of classes.\n      targets:\n        float matrix of class targets. Each dimension corresponds to a different class.\n        Shape ``[n, d]``, where d is the number of classes.\n      weights:\n        Defaults to weights of 1.\n        1D array containing the weights of each prediction.\n    ';E=targets;B=predictions;A=weights
		if B.shape!=E.shape:raise ValueError('Expecting predictions.shape == targets.shape, got %s and %s instead'%(str(B.shape),str(E.shape)))
		if A is not _A:
			if A.ndim!=1:raise ValueError('Expecting 1D weight, got %dD instead'%A.ndim)
			elif A.size!=B.shape[0]:raise ValueError('Expecting predictions.shape[0] == weights.size, got %d != %d instead'%(B.shape[0],A.size))
		if A is _A:A=np.full(B.shape[0],fill_value=DEFAULT_SAMPLE_WEIGHT)
		for C in range(B.shape[1]):
			G=B[:,C];H=E[:,C]
			if C not in D._isotonic_feature_dict:F=IsotonicFeature(C);D._isotonic_feature_dict[C]=F
			else:F=D._isotonic_feature_dict[C]
			F.add_values({_E:G,_C:A,_D:H})
	def calibrate(A,debug=_B):
		'\n    Calibrates each IsotonicFeature after accumulation is complete.\n    Results are stored in ``self._ys_input`` and ``self._xs_input``\n\n    Arguments:\n      debug:\n        Defaults to False. If set to true, returns the ``xs_input`` and ``ys_input``.\n    ';super(IsotonicCalibrator,A).calibrate();B=[];C=[];logging.info('Beginning isotonic calibration.');D=A._isotonic_feature_dict
		for E in D:F,G=D[E].calibrate(bins=A._n_bin,similar_bins=A._similar_bins);C.append(F);B.append(G)
		A._xs_input=np.array(C,dtype=np.float32);A._ys_input=np.array(B,dtype=np.float32);logging.info('Isotonic calibration finished.')
		if debug:return np.array(C),np.array(B)
		return _A
	def save(A,save_dir,name='default',verbose=_B):
		'Save the calibrator into the given save_directory.\n    Arguments:\n      save_dir:\n        name of the saving directory. Default (string): "default".\n    '
		if not A._calibrated:raise RuntimeError('Expecting prior call to calibrate().Cannot save() prior to calibrate()')
		logging.info('You probably do not need to save the isotonic layer.                   So feel free to set save to False in the Trainer.                   Additionally this only saves the layer not the whole graph.')
		def B():'\n      Way to save Isotonic layer\n      ';B=tf.placeholder(tf.float32);C=A.to_layer();D=C(B);hub.add_signature(inputs=B,outputs=D,name=name)
		C=hub.create_module_spec(B)
		with tf.Graph().as_default():
			D=hub.Module(C)
			with tf.Session()as E:D.export(save_dir,E)
	def to_layer(A):
		' Returns a twml.layers.Isotonic Layer that can be used for feature discretization.\n    '
		if not A._calibrated:raise RuntimeError('Expecting prior call to calibrate()')
		B=twml.layers.Isotonic(n_unit=A._xs_input.shape[0],n_bin=A._xs_input.shape[1],xs_input=A._xs_input,ys_input=A._ys_input,**A._kwargs);return B
	def get_layer_args(A,name=_A):' Returns layer args. See ``Calibrator.get_layer_args`` for more detailed documentation ';return{'n_unit':A._xs_input.shape[0],'n_bin':A._xs_input.shape[1]}