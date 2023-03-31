'\nModule containing extra tensorflow metrics used at Twitter.\nThis module conforms to conventions used by tf.metrics.*.\nIn particular, each metric constructs two subgraphs: value_op and update_op:\n  - The value op is used to fetch the current metric value.\n  - The update_op is used to accumulate into the metric.\n\nNote: similar to tf.metrics.*, metrics in here do not support multi-label learning.\nWe will have to write wrapper classes to create one metric per label.\n\nNote: similar to tf.metrics.*, batches added into a metric via its update_op are cumulative!\n\n'
_D='ndcg'
_C=True
_B=False
_A=None
from collections import OrderedDict
from functools import partial
import tensorflow.compat.v1 as tf
from tensorflow.python.eager import context
from tensorflow.python.framework import dtypes,ops
from tensorflow.python.ops import array_ops,state_ops
import twml
from twml.contrib.utils import math_fns
def ndcg(labels,predictions,metrics_collections=_A,updates_collections=_A,name=_A,top_k_int=1):
	'\n  Compute full normalized discounted cumulative gain (ndcg) based on predictions\n  ndcg = dcg_k/idcg_k, k is a cut off ranking postion\n  There are a few variants of ndcg\n  The dcg (discounted cumulative gain) formula used in\n  twml.contrib.metrics.ndcg is::\n\n    \\sum_{i=1}^k \x0crac{2^{relevance\\_score} -1}{\\log_{2}(i + 1)}\n\n  k is the length of items to be ranked in a batch/query\n  Notice that whether k will be replaced with a fixed value requires discussions\n  The scores in predictions are transformed to order and relevance scores to calculate ndcg\n  A relevance score means how relevant a DataRecord is to a particular query\n\n  Arguments:\n    labels: the ground truth value.\n    predictions: the predicted values, whose shape must match labels. Ignored for CTR computation.\n    metrics_collections: optional list of collections to add this metric into.\n    updates_collections: optional list of collections to add the associated update_op into.\n    name: an optional variable_scope name.\n\n  Returns:\n    ndcg: A `Tensor` representing the ndcg score.\n    update_op: A update operation used to accumulate data into this metric.\n  ';B=updates_collections;C=metrics_collections;D=predictions;E=labels;A=top_k_int
	with tf.variable_scope(name,_D,(E,D)):
		L=tf.to_float(E,name='label_to_float');F=tf.to_float(D,name='predictions_to_float')
		if context.executing_eagerly():raise RuntimeError('ndcg is not supported when eager execution is enabled.')
		G=_metric_variable([],dtypes.float32,name='total_ndcg');H=_metric_variable([],dtypes.float32,name='query_count');M=array_ops.size(F);A=tf.minimum(M,A);I=math_fns.cal_ndcg(L,F,top_k_int=A);N=state_ops.assign_add(G,I)
		with ops.control_dependencies([I]):O=state_ops.assign_add(H,1)
		J=math_fns.safe_div(G,H,'mean_ndcg');K=math_fns.safe_div(N,O,'update_mean_ndcg_op')
		if C:ops.add_to_collections(C,J)
		if B:ops.add_to_collections(B,K)
		return J,K
def _metric_variable(shape,dtype,validate_shape=_C,name=_A):'Create variable in `GraphKeys.(LOCAL|METRIC_VARIABLES`) collections.';return tf.Variable(lambda:tf.zeros(shape,dtype),trainable=_B,collections=[tf.GraphKeys.LOCAL_VARIABLES,tf.GraphKeys.METRIC_VARIABLES],validate_shape=validate_shape,name=name)
SUPPORTED_BINARY_CLASS_METRICS={'rce':(twml.metrics.rce,_B),'nrce':(partial(twml.metrics.rce,normalize=_C),_B),'ctr':(twml.metrics.ctr,_B),'predicted_ctr':(twml.metrics.predicted_ctr,_B),'accuracy':(tf.metrics.accuracy,_C),'precision':(tf.metrics.precision,_C),'recall':(tf.metrics.recall,_C),'roc_auc':(partial(tf.metrics.auc,curve='ROC'),_B),'pr_auc':(partial(tf.metrics.auc,curve='PR'),_B)}
SUPPORTED_SEARCH_METRICS={_D:ndcg}
def get_search_metric_fn(binary_metrics=_A,search_metrics=_A,ndcg_top_ks=[1,3,5,10],use_binary_metrics=_B):
	"\n  Returns a function having signature:\n\n  .. code-block:: python\n\n    def get_eval_metric_ops(graph_output, labels, weights):\n      ...\n      return eval_metric_ops\n\n  where the returned eval_metric_ops is a dict of common evaluation metric\n  Ops for ranking. See `tf.estimator.EstimatorSpec\n  <https://www.tensorflow.org/api_docs/python/tf/estimator/EstimatorSpec>`_\n  for a description of eval_metric_ops. The graph_output is a the result\n  dict returned by build_graph. Labels and weights are tf.Tensors.\n\n  The following graph_output keys are recognized:\n    output:\n      the raw predictions. Required.\n    threshold:\n      Only used in SUPPORTED_BINARY_CLASS_METRICS\n      If the lables are 0s and 1s\n      A value between 0 and 1 used to threshold the output into a hard_output.\n      Defaults to 0.5 when threshold and hard_output are missing.\n      Either threshold or hard_output can be provided, but not both.\n    hard_output:\n      Only used in SUPPORTED_BINARY_CLASS_METRICS\n      A thresholded output. Either threshold or hard_output can be provided, but not both.\n\n  Arguments:\n    only used in pointwise learning-to-rank\n\n    binary_metrics (list of String):\n      a list of metrics of interest. E.g. ['ctr', 'accuracy', 'rce']\n      These metrics are evaluated and reported to tensorboard *during the eval phases only*.\n      Supported metrics:\n        - ctr (same as positive sample ratio.)\n        - rce (cross entropy loss compared to the baseline model of always predicting ctr)\n        - nrce (normalized rce, do not use this one if you do not understand what it is)\n        - pr_auc\n        - roc_auc\n        - accuracy (percentage of predictions that are correct)\n        - precision (true positives) / (true positives + false positives)\n        - recall (true positives) / (true positives + false negatives)\n\n      NOTE: accuracy / precision / recall apply to binary classification problems only.\n      I.e. a prediction is only considered correct if it matches the label. E.g. if the label\n      is 1.0, and the prediction is 0.99, it does not get credit.  If you want to use\n      precision / recall / accuracy metrics with soft predictions, you'll need to threshold\n      your predictions into hard 0/1 labels.\n\n      When binary_metrics is None (the default), it defaults to all supported metrics\n\n    search_metrics (list of String):\n      a list of metrics of interest. E.g. ['ndcg']\n      These metrics are evaluated and reported to tensorboard *during the eval phases only*.\n      Supported metrics:\n        - ndcg\n\n      NOTE: ndcg works for ranking-relatd problems.\n      A batch contains all DataRecords that belong to the same query\n      If pair_in_batch_mode used in scalding -- a batch contains a pair of DataRecords\n      that belong to the same query and have different labels -- ndcg does not apply in here.\n\n      When search_metrics is None (the default), it defaults to all supported search metrics\n      currently only 'ndcg'\n\n    ndcg_top_ks (list of integers):\n      The cut-off ranking postions for a query\n      When ndcg_top_ks is None or empty (the default), it defaults to [1, 3, 5, 10]\n\n    use_binary_metrics:\n      False (default)\n      Only set it to true in pointwise learning-to-rank\n  ";K=use_binary_metrics;F=search_metrics;G=binary_metrics;C=ndcg_top_ks
	if C is _A or not C:C=[1,3,5,10]
	if F is _A:F=list(SUPPORTED_SEARCH_METRICS.keys())
	if G is _A and K:G=list(SUPPORTED_BINARY_CLASS_METRICS.keys())
	def A(graph_output,labels,weights):
		'\n    graph_output:\n      dict that is returned by build_graph given input features.\n    labels:\n      target labels associated to batch.\n    weights:\n      weights of the samples..\n    ';L='threshold';M=labels;D=graph_output;B=OrderedDict();H=D['output'];R=D[L]if L in D else 0.5;E=D.get('hard_output')
		if E is _A or tf.equal(tf.size(E),0):E=tf.greater_equal(H,R)
		for A in F:
			A=A.lower()
			if A in B:continue
			N=SUPPORTED_SEARCH_METRICS.get(A)
			if N:
				if A==_D:
					for O in C:P=A+'_'+str(O);S=tf.constant(O,dtype=tf.int32);I,J=N(labels=M,predictions=H,name=P,top_k_int=S);B[P]=I,J
			else:raise ValueError('Cannot find the search metric named '+A)
		if K:
			for A in G:
				if A in B:continue
				A=A.lower();Q,T=SUPPORTED_BINARY_CLASS_METRICS.get(A)
				if Q:I,J=Q(labels=M,predictions=E if T else H,weights=weights,name=A);B[A]=I,J
				else:raise ValueError('Cannot find the binary metric named '+A)
		return B
	return A