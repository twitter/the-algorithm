'\nModule containing extra tensorflow metrics used at Twitter.\nThis module conforms to conventions used by tf.metrics.*.\nIn particular, each metric constructs two subgraphs: value_op and update_op:\n  - The value op is used to fetch the current metric value.\n  - The update_op is used to accumulate into the metric.\n\nNote: similar to tf.metrics.*, metrics in here do not support multi-label learning.\nWe will have to write wrapper classes to create one metric per label.\n\nNote: similar to tf.metrics.*, batches added into a metric via its update_op are cumulative!\n\n'
_F='unweighted_'
_E='mean_gated_numeric_label_topk'
_D='mean_numeric_label_topk'
_C='Invalid Prediction Column Index !'
_B='output'
_A=None
from collections import OrderedDict
import tensorflow.compat.v1 as tf
from twml.metrics import get_multi_binary_class_metric_fn
def get_partial_multi_binary_class_metric_fn(metrics,classes=_A,class_dim=1,predcols=_A):
	B=predcols;C=class_dim
	def A(graph_output,labels,weights):
		E='hard_output';F='threshold';G=labels;A=graph_output
		if B is _A:I=A[_B]
		else:
			if isinstance(B,int):D=[B]
			else:D=list(B)
			for J in D:assert 0<=J<A[_B].shape[C],_C
			I=tf.gather(A[_B],indices=D,axis=C);G=tf.gather(G,indices=D,axis=C)
		H={_B:I}
		if F in A:H[F]=A[F]
		if E in A:H[E]=A[E]
		K=get_multi_binary_class_metric_fn(metrics,classes,C);L=K(H,G,weights);return L
	return A
def mean_numeric_label_topK(labels,predictions,weights,name,topK_id):A=tf.gather(params=labels,indices=topK_id,axis=0);return tf.metrics.mean(values=A,name=name)
def mean_gated_numeric_label_topK(labels,predictions,weights,name,topK_id,bar=2.0):A=bar;assert isinstance(A,int)or isinstance(A,float),'bar must be int or float';B=tf.gather(params=labels,indices=topK_id,axis=0);C=tf.cast(B>A*1.0,tf.int32);return tf.metrics.mean(values=C,name=name)
SUPPORTED_NUMERIC_METRICS={_D:mean_numeric_label_topK,_E:mean_gated_numeric_label_topK}
DEFAULT_NUMERIC_METRICS=[_D,_E]
def get_metric_topK_fn_helper(targetMetrics,supportedMetrics_op,metrics=_A,topK=(5,5,5),predcol=_A,labelcol=_A):
	'\n  :param targetMetrics:        Target Metric List\n  :param supportedMetrics_op:  Supported Metric Operators             Dict\n  :param metrics:              Metric Set to evaluate\n  :param topK:                 (topK_min, topK_max, topK_delta)       Tuple\n  :param predcol:              Prediction Column Index\n  :param labelcol:             Label Column Index\n  :return:\n  ';M=labelcol;F=predcol;G=supportedMetrics_op;A=topK;B=metrics;C=targetMetrics
	if C is _A or G is _A:raise ValueError('Invalid Target Metric List/op !')
	C=set([A.lower()for A in C])
	if B is _A:B=list(C)
	else:B=[A.lower()for A in B if A.lower()in C]
	E=int((A[1]-A[0])/A[2]+1);D=[A[0]+B*A[2]for B in range(E)]
	if 1 not in D:D=[1]+D
	def H(graph_output,labels,weights):
		'\n    graph_output:\n      dict that is returned by build_graph given input features.\n    labels:\n      target labels associated to batch.\n    weights:\n      weights of the samples..\n    ';N='__k_';O=weights;E=labels;C=graph_output;H=OrderedDict()
		if F is _A:I=C[_B]
		else:assert 0<=F<C[_B].shape[1],_C;assert M is not _A;I=tf.reshape(C[_B][:,F],shape=[-1,1]);E=tf.reshape(E[:,M],shape=[-1,1])
		R=C[_B].shape[1];P=tf.reshape(C[_B][:,R-1],shape=[-1,1])
		for A in B:
			A=A.lower()
			if A in G:
				Q=G.get(A)
				if'topk'not in A:J,K=Q(labels=E,predictions=I,weights=O,name=A);H[A]=J,K
				else:
					for L in D:S=tf.minimum(L,tf.shape(P)[0]);T=tf.nn.top_k(tf.reshape(P,shape=[-1]),k=S)[1];J,K=Q(labels=E,predictions=I,weights=O,name=A+N+str(L),topK_id=T);H[A+N+str(L)]=J,K
			else:raise ValueError('Cannot find the metric named '+A)
		return H
	return H
def get_numeric_metric_fn(metrics=_A,topK=(5,5,5),predcol=_A,labelcol=_A):
	A=metrics
	if A is _A:A=list(DEFAULT_NUMERIC_METRICS)
	A=list(set(A));B=get_metric_topK_fn_helper(targetMetrics=list(DEFAULT_NUMERIC_METRICS),supportedMetrics_op=SUPPORTED_NUMERIC_METRICS,metrics=A,topK=topK,predcol=predcol,labelcol=labelcol);return B
def get_single_binary_task_metric_fn(metrics,classnames,topK=(5,5,5),use_topK=False):
	"\n  graph_output['output']:        [BatchSz, 1]        [pred_Task1]\n  labels:                        [BatchSz, 2]        [Task1, NumericLabel]\n  ";D=classnames;E=metrics
	def A(graph_output,labels,weights):
		F=weights;A=labels;B=graph_output;G=get_partial_multi_binary_class_metric_fn(E,predcols=0,classes=D);H=[_F+A for A in D];I=get_partial_multi_binary_class_metric_fn(E,predcols=0,classes=H);C=G(B,A,F);J=I(B,A,_A);C.update(J)
		if use_topK:K=get_numeric_metric_fn(metrics=_A,topK=topK,predcol=0,labelcol=1);L=K(B,A,F);C.update(L)
		return C
	return A
def get_dual_binary_tasks_metric_fn(metrics,classnames,topK=(5,5,5),use_topK=False):
	"\n  graph_output['output']:        [BatchSz, 3]        [pred_Task1, pred_Task2, Score]\n  labels:                        [BatchSz, 3]        [Task1, Task2, NumericLabel]\n  ";D=classnames;E=metrics
	def A(graph_output,labels,weights):
		F=weights;A=labels;B=graph_output;G=get_partial_multi_binary_class_metric_fn(E,predcols=[0,1],classes=D);H=[_F+A for A in D];I=get_partial_multi_binary_class_metric_fn(E,predcols=[0,1],classes=H);C=G(B,A,F);J=I(B,A,_A);C.update(J)
		if use_topK:K=get_numeric_metric_fn(metrics=_A,topK=topK,predcol=2,labelcol=2);L=K(B,A,F);C.update(L)
		return C
	return A