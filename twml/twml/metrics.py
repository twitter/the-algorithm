'\nThis module contains custom tensorflow metrics used at Twitter.\nIts components conform to conventions used by the ``tf.metrics`` module.\n\n'
_o='Cannot find the metric named '
_n='Metric should be either string or tuple of length 3.'
_m='careful_interpolation'
_l='pr_auc'
_k='roc_auc'
_j='recall'
_i='precision'
_h='accuracy'
_g='predicted_ctr'
_f='weight'
_e='weighted_labels'
_d='weighted_loss'
_c='total_weight_update'
_b='total_loss_update'
_a='weighted_logloss'
_Z='logloss'
_Y='clip_p'
_X='total_loss'
_W='total_positive'
_V='weight_to_float'
_U='predictions_to_float'
_T='label_to_float'
_S='weighted_preds'
_R='lolly_nrce'
_Q='ctr'
_P='total_prediction_update'
_O='total_prediction'
_N='pred_std_dev'
_M='output'
_L='rce_std_err'
_K='rce'
_J='num_samples'
_I='update_op'
_H='hard_output'
_G='default_weight'
_F='total_weight'
_E='threshold'
_D=1.0
_C=True
_B=False
_A=None
from collections import OrderedDict
from functools import partial
import numpy as np,tensorboard as tb,tensorflow.compat.v1 as tf
CLAMP_EPSILON=1e-05
def total_weight_metric(labels,predictions,weights=_A,metrics_collections=_A,updates_collections=_A,name=_A):
	C=updates_collections;D=metrics_collections;E=labels;A=weights
	with tf.variable_scope(name,_F,(E,predictions,A)):
		B=_metric_variable(name=_F,shape=[],dtype=tf.float64)
		if A is _A:A=tf.cast(tf.size(E),B.dtype,name=_G)
		else:A=tf.cast(A,B.dtype)
		H=tf.assign_add(B,tf.reduce_sum(A),name=_I);F=tf.identity(B);G=tf.identity(H)
		if D:tf.add_to_collections(D,F)
		if C:tf.add_to_collections(C,G)
		return F,G
def num_samples_metric(labels,predictions,weights=_A,metrics_collections=_A,updates_collections=_A,name=_A):
	B=updates_collections;C=metrics_collections;D=labels
	with tf.variable_scope(name,_J,(D,predictions,weights)):
		A=_metric_variable(name=_J,shape=[],dtype=tf.float64);G=tf.assign_add(A,tf.cast(tf.size(D),A.dtype),name=_I);E=tf.identity(A);F=tf.identity(G)
		if C:tf.add_to_collections(C,E)
		if B:tf.add_to_collections(B,F)
		return E,F
def ctr(labels,predictions,weights=_A,metrics_collections=_A,updates_collections=_A,name=_A):'\n  Compute the weighted average positive sample ratio based on labels\n  (i.e. weighted average percentage of positive labels).\n  The name `ctr` (click-through-rate) is from legacy.\n\n  Args:\n    labels: the ground truth value.\n    predictions: the predicted values, whose shape must match labels. Ignored for CTR computation.\n    weights: optional weights, whose shape must match labels . Weight is 1 if not set.\n    metrics_collections: optional list of collections to add this metric into.\n    updates_collections: optional list of collections to add the associated update_op into.\n    name: an optional variable_scope name.\n\n  Return:\n    ctr: A `Tensor` representing positive sample ratio.\n    update_op: A update operation used to accumulate data into this metric.\n  ';return tf.metrics.mean(values=labels,weights=weights,metrics_collections=metrics_collections,updates_collections=updates_collections,name=name)
def predicted_ctr(labels,predictions,weights=_A,metrics_collections=_A,updates_collections=_A,name=_A):'\n  Compute the weighted average positive ratio based on predictions,\n  (i.e. weighted averaged predicted positive probability).\n  The name `ctr` (click-through-rate) is from legacy.\n\n  Args:\n    labels: the ground truth value.\n    predictions: the predicted values, whose shape must match labels. Ignored for CTR computation.\n    weights: optional weights, whose shape must match labels . Weight is 1 if not set.\n    metrics_collections: optional list of collections to add this metric into.\n    updates_collections: optional list of collections to add the associated update_op into.\n    name: an optional variable_scope name.\n\n  Return:\n    predicted_ctr: A `Tensor` representing the predicted positive ratio.\n    update_op: A update operation used to accumulate data into this metric.\n  ';return tf.metrics.mean(values=predictions,weights=weights,metrics_collections=metrics_collections,updates_collections=updates_collections,name=name)
def prediction_std_dev(labels,predictions,weights=_A,metrics_collections=_A,updates_collections=_A,name=_A):
	'\n  Compute the weighted standard deviation of the predictions.\n  Note - this is not a confidence interval metric.\n\n  Args:\n    labels: the ground truth value.\n    predictions: the predicted values, whose shape must match labels. Ignored for CTR computation.\n    weights: optional weights, whose shape must match labels . Weight is 1 if not set.\n    metrics_collections: optional list of collections to add this metric into.\n    updates_collections: optional list of collections to add the associated update_op into.\n    name: an optional variable_scope name.\n\n  Return:\n    metric value: A `Tensor` representing the value of the metric on the data accumulated so far.\n    update_op: A update operation used to accumulate data into this metric.\n  ';D=updates_collections;E=metrics_collections;C=labels;B=predictions;A=weights
	with tf.variable_scope(name,_N,(C,B,A)):
		C=tf.cast(C,tf.float64);B=tf.cast(B,tf.float64)
		if A is _A:A=tf.ones(shape=tf.shape(C),dtype=tf.float64,name=_G)
		else:A=tf.cast(A,tf.float64)
		F=_metric_variable(name='total_weighted_preds',shape=[],dtype=tf.float64);G=_metric_variable(name='total_weighted_preds_sq',shape=[],dtype=tf.float64);H=_metric_variable(name='total_weights',shape=[],dtype=tf.float64);L=tf.assign_add(F,tf.reduce_sum(A*B));M=tf.assign_add(G,tf.reduce_sum(A*B*B));N=tf.assign_add(H,tf.reduce_sum(A))
		def I(tot_w,tot_wp,tot_wpp):A=tot_w;return tf.math.sqrt(tot_wpp/A-(tot_wp/A)**2)
		J=I(H,F,G);K=I(N,L,M)
		if E:tf.add_to_collections(E,J)
		if D:tf.add_to_collections(D,K)
		return J,K
def _get_arce_predictions(predictions,weights,label_weighted,labels,up_weight,deprecated_rce,total_positive,update_total_positive):
	'\n  Returns the ARCE predictions, total_positive, update_total_positive and weights\n  used by the rest of the twml.metrics.rce metric computation.\n  ';H=total_positive;I=deprecated_rce;F=label_weighted;D=update_total_positive;B=weights;C=predictions;A=labels;G=tf.multiply(C,B,name=_S);J=tf.subtract(tf.reduce_sum(B),tf.reduce_sum(F));O=tf.subtract(tf.reduce_sum(B),tf.reduce_sum(G));K=J/O
	if up_weight is _B:
		L=_metric_variable(name='total_positive_unweighted',shape=[],dtype=tf.float32);M=tf.assign_add(L,tf.reduce_sum(A),name='total_positive_unweighted_update')
		if I:E=tf.reduce_sum(A)/tf.reduce_sum(F)
		else:E=M/D
		P=tf.subtract(tf.to_float(tf.size(A)),tf.reduce_sum(A));K=P/J;B=tf.ones(shape=tf.shape(A),dtype=tf.float32,name=_G);H=L;D=M
	elif I:E=tf.reduce_sum(F)/tf.reduce_sum(G)
	else:Q=_metric_variable(name=_O,shape=[],dtype=tf.float32);R=tf.assign_add(Q,tf.reduce_sum(G),name=_P);E=D/R
	S=tf.subtract(tf.ones(shape=tf.shape(A),dtype=tf.float32),C);T=tf.multiply(S,K,name='normalized_predictions_comp');N=tf.multiply(C,E,name='normalized_pred_numerator');U=tf.add(N,T,name='normalized_pred_denominator');C=N/U;return C,H,D,B
def rce(labels,predictions,weights=_A,normalize=_B,arce=_B,up_weight=_C,metrics_collections=_A,updates_collections=_A,name=_A,deprecated_rce=_B):
	"\n  Compute the relative cross entropy (RCE).\n  The RCE is a relative measurement compared to the baseline model's performance.\n  The baseline model always predicts average click-through-rate (CTR).\n  The RCE measures, in percentage, how much better the predictions are, compared\n  to the baseline model, in terms of cross entropy loss.\n\n  y = label; p = prediction;\n  binary cross entropy = y * log(p) + (1-y) * log(1-p)\n\n  Args:\n    labels:\n      the ground true value.\n    predictions:\n      the predicted values, whose shape must match labels.\n    weights:\n      optional weights, whose shape must match labels . Weight is 1 if not set.\n    normalize:\n      if set to true, produce NRCEs used at Twitter. (normalize preds by weights first)\n      NOTE: if you don't understand what NRCE is, please don't use it.\n    arce:\n      if set to true, produces `ARCE <http://go/arce>`_.\n      This can only be activated if `normalize=True`.\n    up_weight:\n      if set to true, produces arce in the up_weighted space (considers CTR after up_weighting\n      data), while False gives arce in the original space (only considers CTR before up_weighting).\n      In the actual version, this flag can only be activated if arce is True.\n      Notice that the actual version of NRCE corresponds to up_weight=True.\n    metrics_collections:\n      optional list of collections to add this metric into.\n    updates_collections:\n      optional list of collections to add the associated update_op into.\n    name:\n      an optional variable_scope name.\n    deprecated_rce:\n      enables the previous NRCE/ARCE calculations which calculated some label metrics\n      on the batch instead of on all batches seen so far. Note that the older metric\n      calculation is less stable, especially for smaller batch sizes. You should probably\n      never have to set this to True.\n\n  Return:\n    rce_value:\n      A ``Tensor`` representing the RCE.\n    update_op:\n      A update operation used to accumulate data into this metric.\n\n  .. note:: Must have at least 1 positive and 1 negative sample accumulated,\n     or RCE will come out as NaN.\n  ";H=deprecated_rce;I=updates_collections;J=metrics_collections;K=normalize;C=labels;B=predictions;A=weights
	with tf.variable_scope(name,_K,(C,B,A)):
		C=tf.to_float(C,name=_T);B=tf.to_float(B,name=_U)
		if A is _A:A=tf.ones(shape=tf.shape(C),dtype=tf.float32,name=_G)
		else:A=tf.to_float(A,name=_V)
		D=_metric_variable(name=_W,shape=[],dtype=tf.float32);L=_metric_variable(name=_X,shape=[],dtype=tf.float32);F=_metric_variable(name=_F,shape=[],dtype=tf.float32);G=tf.multiply(C,A,name='weighted_label');E=tf.assign_add(D,tf.reduce_sum(G),name='total_pos_update')
		if arce:
			if K is _B:raise ValueError('This configuration of parameters is not actually allowed')
			B,D,E,A=_get_arce_predictions(predictions=B,weights=A,deprecated_rce=H,label_weighted=G,labels=C,up_weight=up_weight,total_positive=D,update_total_positive=E)
		elif K:
			M=tf.multiply(B,A,name=_S)
			if H:N=tf.reduce_sum(G)/tf.reduce_sum(M)
			else:T=_metric_variable(name=_O,shape=[],dtype=tf.float32);U=tf.assign_add(T,tf.reduce_sum(M),name=_P);N=E/U
			B=tf.multiply(B,N,name='normalized_predictions')
		V=tf.clip_by_value(B,CLAMP_EPSILON,_D-CLAMP_EPSILON,name=_Y);W=_binary_cross_entropy(pred=V,target=C,name=_Z);X=tf.multiply(W,A,name=_a);Y=tf.assign_add(L,tf.reduce_sum(X),name=_b);O=tf.assign_add(F,tf.reduce_sum(A),name=_c);P=tf.truediv(D,F,name=_Q);Z=_binary_cross_entropy(pred=P,target=P,name='baseline_ce');a=tf.truediv(L,F,name='pred_ce');Q=tf.multiply(_D-tf.truediv(a,Z),100,name=_K);R=tf.truediv(E,O,name='ctr_update');b=_binary_cross_entropy(pred=R,target=R,name='baseline_ce_update');c=tf.truediv(Y,O,name='pred_ce_update');S=tf.multiply(_D-tf.truediv(c,b),100,name=_I)
		if J:tf.add_to_collections(J,Q)
		if I:tf.add_to_collections(I,S)
		return Q,S
def ce(p_true,p_est=_A):
	B=p_true;A=p_est
	if A is _A:A=B
	return _binary_cross_entropy(pred=A,target=B,name=_A)
def rce_transform(outputs,labels,weights):'\n  Construct an OrderedDict of quantities to aggregate over eval batches\n  outputs, labels, weights are TensorFlow tensors, and are assumed to\n    be of shape [N] for batch_size = N\n  Each entry in the output OrderedDict should also be of shape [N]\n  ';C=labels;B=weights;A=OrderedDict();A[_d]=B*ce(p_true=C,p_est=outputs);A[_e]=C*B;A[_f]=B;return A
def rce_metric(aggregates):'\n  input ``aggregates`` is an OrderedDict with the same keys as those created\n    by rce_transform(). The dict values are the aggregates (reduce_sum)\n    of the values produced by rce_transform(), and should be scalars.\n  output is the value of RCE\n  ';A=aggregates;C=A[_d];D=A[_e];B=A[_f];E=C/B;F=ce(D/B);return 100.0*(1-E/F)
def metric_std_err(labels,predictions,weights=_A,transform=rce_transform,metric=rce_metric,metrics_collections=_A,updates_collections=_A,name=_L):
	'\n  Compute the weighted standard error of the RCE metric on this eval set.\n  This can be used for confidence intervals and unpaired hypothesis tests.\n\n  Args:\n    labels: the ground truth value.\n    predictions: the predicted values, whose shape must match labels.\n    weights: optional weights, whose shape must match labels . Weight is 1 if not set.\n    transform: a function of the following form:\n\n      .. code-block:: python\n\n        def transform(outputs, labels, weights):\n          out_vals = OrderedDict()\n          ...\n          return out_vals\n\n      where outputs, labels, and weights are all tensors of shape [eval_batch_size].\n      The returned OrderedDict() should have values that are tensors of shape  [eval_batch_size].\n      These will be aggregated across many batches in the eval dataset, to produce\n      one scalar value per key of out_vals.\n    metric: a function of the following form\n\n      .. code-block:: python\n\n        def metric(aggregates):\n          ...\n          return metric_value\n\n      where aggregates is an OrderedDict() having the same keys created by transform().\n      Each of the corresponding dict values is the reduce_sum of the values produced by\n      transform(), and is a TF scalar. The return value should be a scalar representing\n      the value of the desired metric.\n    metrics_collections: optional list of collections to add this metric into.\n    updates_collections: optional list of collections to add the associated update_op into.\n    name: an optional variable_scope name.\n\n  Return:\n    metric value: A `Tensor` representing the value of the metric on the data accumulated so far.\n    update_op: A update operation used to accumulate data into this metric.\n  ';H=updates_collections;I=metrics_collections;A=weights;B=predictions;C=labels
	with tf.variable_scope(name,'metric_std_err',(C,B,A)):
		C=tf.cast(C,tf.float64);B=tf.cast(B,tf.float64)
		if A is _A:A=tf.ones_like(C,dtype=tf.float64,name=_G)
		else:A=tf.cast(A,tf.float64)
		C=tf.reshape(C,[-1]);B=tf.reshape(B,[-1]);B=tf.clip_by_value(B,CLAMP_EPSILON,_D-CLAMP_EPSILON,name=_Y);A=tf.reshape(A,[-1]);E=transform(B,C,A);F=_metric_variable(name='sample_count',shape=[],dtype=tf.int64);O=tf.assign_add(F,tf.size(C,out_type=F.dtype));D=len(E);G=tf.stack(list(E.values()),axis=1);J=_metric_variable(name='aggregates_1',shape=[D],dtype=tf.float64);P=tf.assign_add(J,tf.reduce_sum(G,axis=0));K=_metric_variable(name='aggregates_2',shape=[D,D],dtype=tf.float64);Q=tf.reshape(G,shape=[-1,D,1])*tf.reshape(G,shape=[-1,1,D]);R=tf.assign_add(K,tf.reduce_sum(Q,axis=0))
		def L(agg_1,agg_2,samp_cnt):
			A=agg_1;C=OrderedDict()
			for (F,G) in enumerate(E.keys()):C[G]=A[F]
			H=metric(C);D=tf.gradients(H,A,stop_gradients=A);I=agg_2-tf.reshape(A,shape=[-1,1])@tf.reshape(A,shape=[1,-1])/tf.cast(samp_cnt,dtype=tf.float64);B=tf.reshape(D,shape=[1,-1])@I@tf.reshape(D,shape=[-1,1]);B=B[0][0];J=tf.sqrt(B);return J
		M=L(J,K,F);N=L(P,R,O)
		if I:tf.add_to_collections(I,M)
		if H:tf.add_to_collections(H,N)
		return M,N
def lolly_nrce(labels,predictions,weights=_A,metrics_collections=_A,updates_collections=_A,name=_A):
	'\n  Compute the Lolly NRCE.\n\n  Note: As this NRCE calculation uses Taylor expansion, it becomes inaccurate when the ctr is large,\n  especially when the adjusted ctr goes above 1.0.\n\n  Calculation:\n\n  ::\n\n    NRCE: lolly NRCE\n    BCE: baseline cross entropy\n    NCE: normalized cross entropy\n    CE: cross entropy\n    y_i: label of example i\n    p_i: prediction of example i\n    y: ctr\n    p: average prediction\n    a: normalizer\n\n    Assumes any p_i and a * p_i is within [0, 1)\n    NRCE = (1 - NCE / BCE) * 100\n    BCE = - sum_i(y_i * log(y) + (1 - y_i) * log(1 - y))\n        = - (y * log(y) + (1 - y) * log(1 - y))\n    a = y / p\n    CE = - sum_i(y_i * log(p_i) + (1 - y_i) * log(1 - p_i))\n    NCE = - sum_i(y_i * log(a * p_i) + (1 - y_i) * log(1 - a * p_i))\n        = - sum_i(y_i * log(p_i) + (1 - y_i) * log(1 - p_i))\n          - sum_i(y_i * log(a))\n          + sum_i((1 - y_i) * log(1 - p_i))\n          - sum_i((1 - y_i) * log(1 - a * p_i))\n        ~= CE - sum_i(y_i) * log(a)\n          + sum_i((1 - y_i) * (- sum_{j=1~5}(p_i^j / j)))\n          - sum_i((1 - y_i) * (- sum_{j=1~5}(a^j * p_i^j / j)))\n          # Takes 5 items from the Taylor expansion, can be increased if needed\n          # Error for each example is O(p_i^6)\n        = CE - sum_i(y_i) * log(a)\n          - sum_{j=1~5}(sum_i((1 - y_i) * p_i^j) / j)\n          + sum_{j=1~5}(sum_i((1 - y_i) * p_i^j) * a^j / j)\n        = CE - sum_i(y_i) * log(a)\n          + sum_{j=1~5}(sum_i((1 - y_i) * p_i^j) * (a^j - 1) / j)\n\n  Thus we keep track of CE, sum_i(y_i), sum_i((1 - y_i) * p_i^j) for j=1~5.\n  We also keep track of p and y by sum_i(y_i), sum_i(p_i), sum_i(1) so that\n  we can get a at the end, which leads to this NRCE.\n\n  NRCE uses ctr and average pctr to normalize the pctrs.\n  It removes the impact of prediction error from RCE.\n  Usually NRCE is higher as the prediction error impact on RCE is negative.\n  Removing prediction error in our model can make RCE closer to NRCE and thus improve RCE.\n\n  In Lolly NRCE we use ctr and average pctr of the whole dataset.\n  We thus remove the dataset level error in NRCE calculation.\n  In this case, when we want to improve RCE to the level of NRCE,\n  it is achievable as dataset level prediction error is easy to remove by calibration.\n  Lolly NRCE is thus a good estimate about the potential gain by adding calibration.\n\n  In DBv2 NRCE, we use per-batch ctr and average pctr. We remove the batch level error.\n  This error is difficult to remove by modeling improvement,\n  at least not by simple calibration.\n  It thus cannot indicate the same opportunity as the Lolly NRCE does.\n\n  Args:\n    labels:\n      the ground true value.\n    predictions:\n      the predicted values, whose shape must match labels.\n    weights:\n      optional weights, whose shape must match labels . Weight is 1 if not set.\n    metrics_collections:\n      optional list of collections to add this metric into.\n    updates_collections:\n      optional list of collections to add the associated update_op into.\n    name:\n      an optional variable_scope name.\n\n  Return:\n    rce_value:\n      A ``Tensor`` representing the RCE.\n    update_op:\n      A update operation used to accumulate data into this metric.\n\n  Note: Must have at least 1 positive and 1 negative sample accumulated,\n        or NRCE will come out as NaN.\n  ';J=updates_collections;K=metrics_collections;E=predictions;D=labels;C=weights
	with tf.variable_scope(name,_R,(D,E,C)):
		D=tf.to_float(D,name=_T);E=tf.to_float(E,name=_U)
		if C is _A:C=tf.ones(shape=tf.shape(D),dtype=tf.float32,name=_G)
		else:C=tf.to_float(C,name=_V)
		a=tf.multiply(D,C,name='positive_weights');b=tf.clip_by_value(E,CLAMP_EPSILON,_D-CLAMP_EPSILON,name='clip_predictions');c=tf.multiply(E,C,name='weighted_predictions');d=_binary_cross_entropy(pred=b,target=D,name=_Z);e=tf.multiply(d,C,name=_a);f=tf.subtract(tf.ones(shape=tf.shape(D),dtype=tf.float32),D,name='negatives');F=tf.multiply(E,f,name='negative_predictions');g=tf.multiply(F,C,name='weighted_negative_predictions');L=tf.multiply(F,F,name='negative_squared_predictions');h=tf.multiply(L,C,name='weighted_negative_squared_predictions');M=tf.multiply(L,F,name='negative_cubed_predictions');i=tf.multiply(M,C,name='weighted_negative_cubed_predictions');N=tf.multiply(M,F,name='negative_quartic_predictions');j=tf.multiply(N,C,name='weighted_negative_quartic_predictions');k=tf.multiply(N,F,name='negative_quintic_predictions');l=tf.multiply(k,C,name='weighted_negative_quintic_predictions');G=_metric_variable(name=_W,shape=[],dtype=tf.float32);H=_metric_variable(name=_F,shape=[],dtype=tf.float32);O=_metric_variable(name=_O,shape=[],dtype=tf.float32);P=_metric_variable(name='total_negative_prediction',shape=[],dtype=tf.float32);Q=_metric_variable(name='total_negative_squared_prediction',shape=[],dtype=tf.float32);R=_metric_variable(name='total_negative_cubed_prediction',shape=[],dtype=tf.float32);S=_metric_variable(name='total_negative_quartic_prediction',shape=[],dtype=tf.float32);T=_metric_variable(name='total_negative_quintic_prediction',shape=[],dtype=tf.float32);U=_metric_variable(name=_X,shape=[],dtype=tf.float32);I=tf.assign_add(G,tf.reduce_sum(a),name='total_positive_update');V=tf.assign_add(H,tf.reduce_sum(C),name=_c);m=tf.assign_add(O,tf.reduce_sum(c),name=_P);n=tf.assign_add(P,tf.reduce_sum(g),name='total_negative_prediction_update');o=tf.assign_add(Q,tf.reduce_sum(h),name='total_negative_squared_prediction_update');p=tf.assign_add(R,tf.reduce_sum(i),name='total_negative_cubed_prediction_update');q=tf.assign_add(S,tf.reduce_sum(j),name='total_negative_quartic_prediction_update');r=tf.assign_add(T,tf.reduce_sum(l),name='total_negative_quintic_prediction_update');s=tf.assign_add(U,tf.reduce_sum(e),name=_b);W=tf.truediv(G,H,name='positive_rate');t=_binary_cross_entropy(pred=W,target=W,name='baseline_loss');A=tf.truediv(G,O,name='normalizer');u=U-G*tf.log(A)+P*(A-1)+Q*(A*A-1)/2+R*(A*A*A-1)/3+S*(A*A*A*A-1)/4+T*(A*A*A*A*A-1)/5;v=tf.truediv(u,H,name='avg_loss');X=tf.multiply(_D-tf.truediv(v,t),100,name=_R);Y=tf.truediv(I,V,name='update_positive_rate');w=_binary_cross_entropy(pred=Y,target=Y,name='update_baseline_loss');B=tf.truediv(I,m,name='update_normalizer');x=s-I*tf.log(B)+n*(B-1)+o*(B*B-1)/2+p*(B*B*B-1)/3+q*(B*B*B*B-1)/4+r*(B*B*B*B*B-1)/5;y=tf.truediv(x,V,name='update_avg_loss');Z=tf.multiply(_D-tf.truediv(y,w),100,name=_I)
		if K:tf.add_to_collections(K,X)
		if J:tf.add_to_collections(J,Z)
		return X,Z
def _binary_cross_entropy(pred,target,name):A=target;return-tf.add(A*tf.log(pred),(_D-A)*tf.log(_D-pred),name=name)
def _metric_variable(shape,dtype,validate_shape=_C,name=_A):'Create variable in `GraphKeys.(LOCAL|METRIC_VARIABLES`) collections.';return tf.Variable(lambda:tf.zeros(shape,dtype),trainable=_B,collections=[tf.GraphKeys.LOCAL_VARIABLES,tf.GraphKeys.METRIC_VARIABLES],validate_shape=validate_shape,name=name)
PERCENTILES=np.linspace(0,1,101,dtype=np.float32)
SUPPORTED_BINARY_CLASS_METRICS={_F:(total_weight_metric,_B),_J:(num_samples_metric,_B),_K:(rce,_B),_L:(partial(metric_std_err,transform=rce_transform,metric=rce_metric,name=_L),_B),'nrce':(partial(rce,normalize=_C),_B),_R:(lolly_nrce,_B),'arce':(partial(rce,normalize=_C,arce=_C),_B),'arce_original':(partial(rce,normalize=_C,arce=_C,up_weight=_B),_B),_Q:(ctr,_B),_g:(predicted_ctr,_B),_N:(prediction_std_dev,_B),_h:(tf.metrics.accuracy,_C),_i:(tf.metrics.precision,_C),_j:(tf.metrics.recall,_C),'false_positives':(tf.metrics.false_positives,_C),'false_negatives':(tf.metrics.false_negatives,_C),'true_positives':(tf.metrics.true_positives,_C),'true_negatives':(tf.metrics.true_negatives,_C),'precision_at_percentiles':(partial(tf.metrics.precision_at_thresholds,thresholds=PERCENTILES),_B),'recall_at_percentiles':(partial(tf.metrics.recall_at_thresholds,thresholds=PERCENTILES),_B),'false_positives_at_percentiles':(partial(tf.metrics.false_positives_at_thresholds,thresholds=PERCENTILES),_B),'false_negatives_at_percentiles':(partial(tf.metrics.false_negatives_at_thresholds,thresholds=PERCENTILES),_B),'true_positives_at_percentiles':(partial(tf.metrics.true_positives_at_thresholds,thresholds=PERCENTILES),_B),'true_negatives_at_percentiles':(partial(tf.metrics.true_negatives_at_thresholds,thresholds=PERCENTILES),_B),_k:(partial(tf.metrics.auc,curve='ROC',summation_method=_m),_B),_l:(partial(tf.metrics.auc,curve='PR',summation_method=_m),_B),'pr_curve':(tb.summary.v1.pr_curve_streaming_op,_B),'deprecated_nrce':(partial(rce,normalize=_C,deprecated_rce=_C),_B),'deprecated_arce':(partial(rce,normalize=_C,arce=_C,deprecated_rce=_C),_B),'deprecated_arce_original':(partial(rce,normalize=_C,arce=_C,up_weight=_B,deprecated_rce=_C),_B)}
DEFAULT_BINARY_CLASS_METRICS=[_F,_J,_K,_L,'nrce','arce',_Q,_g,_N,_h,_i,_j,_k,_l]
def get_binary_class_metric_fn(metrics=_A):
	"\n  Returns a function having signature:\n\n  .. code-block:: python\n\n    def get_eval_metric_ops(graph_output, labels, weights):\n      ...\n      return eval_metric_ops\n\n  where the returned eval_metric_ops is a dict of common evaluation metric\n  Ops for binary classification. See `tf.estimator.EstimatorSpec\n  <https://www.tensorflow.org/api_docs/python/tf/estimator/EstimatorSpec>`_\n  for a description of eval_metric_ops. The graph_output is a the result\n  dict returned by build_graph. Labels and weights are tf.Tensors.\n\n  The following graph_output keys are recognized:\n    output:\n      the raw predictions between 0 and 1. Required.\n    threshold:\n      A value between 0 and 1 used to threshold the output into a hard_output.\n      Defaults to 0.5 when threshold and hard_output are missing.\n      Either threshold or hard_output can be provided, but not both.\n    hard_output:\n      A thresholded output. Either threshold or hard_output can be provided, but not both.\n\n  Args:\n    metrics (list of String):\n      a list of metrics of interest. E.g. ['ctr', 'accuracy', 'rce']\n      Element in the list can be a string from following supported metrics, or can be a tuple\n      with three items: metric name, metric function, bool for thresholded output.\n\n      These metrics are evaluated and reported to tensorboard *during the eval phases only*.\n      Supported metrics:\n\n      - ctr (same as positive sample ratio.)\n      - rce (cross entropy loss compared to the baseline model of always predicting ctr)\n      - nrce (normalized rce, do not use this one if you do not understand what it is)\n      - `arce <http://go/arce>`_ (a more recent proposed improvment over NRCE)\n      - arce_original\n      - lolly_nrce (NRCE as it is computed in Lolly, with Taylor expansion)\n      - pr_auc\n      - roc_auc\n      - accuracy (percentage of predictions that are correct)\n      - precision (true positives) / (true positives + false positives)\n      - recall (true positives) / (true positives + false negatives)\n      - pr_curve (precision-recall curve)\n      - deprecated_arce (ARCE as it was calculated before a stability fix)\n      - deprecated_nrce (NRCE as it was calculated before a stability fix)\n\n      Example of metrics list with mixture of string and tuple:\n      metrics = [\n        'rce','nrce',\n        'roc_auc',  # default roc_auc metric\n        (\n          'roc_auc_500',  # give this metric a name\n          partial(tf.metrics.auc, curve='ROC', summation_method='careful_interpolation', num_thresholds=500),  # the metric fn\n          False,  # whether the metric requires thresholded output\n        )]\n\n      NOTE: When predicting rare events roc_auc can be underestimated. Increasing num_threshold\n      can reduce the underestimation. See go/roc-auc-pitfall for more details.\n\n      NOTE: accuracy / precision / recall apply to binary classification problems only.\n      I.e. a prediction is only considered correct if it matches the label. E.g. if the label\n      is 1.0, and the prediction is 0.99, it does not get credit.  If you want to use\n      precision / recall / accuracy metrics with soft predictions, you'll need to threshold\n      your predictions into hard 0/1 labels.\n\n      When metrics is None (the default), it defaults to:\n      [rce, nrce, arce, ctr, predicted_ctr, accuracy, precision, recall, prauc, roc_auc],\n  ";D=metrics
	if D is _A:D=list(DEFAULT_BINARY_CLASS_METRICS)
	def A(graph_output,labels,weights):
		'\n    graph_output:\n      dict that is returned by build_graph given input features.\n    labels:\n      target labels associated to batch.\n    weights:\n      weights of the samples..\n    ';C=graph_output;E=OrderedDict();H=C[_M];J=C[_E]if _E in C else 0.5;F=C.get(_H)
		if F is _A:F=tf.greater_equal(H,J)
		for B in D:
			if isinstance(B,tuple)and len(B)==3:A,G,I=B;A=A.lower()
			elif isinstance(B,str):A=B.lower();G,I=SUPPORTED_BINARY_CLASS_METRICS.get(A)
			else:raise ValueError(_n)
			if A in E:continue
			if G:K,L=G(labels=labels,predictions=F if I else H,weights=weights,name=A);E[A]=K,L
			else:raise ValueError(_o+A)
		return E
	return A
def get_multi_binary_class_metric_fn(metrics,classes=_A,class_dim=1):
	"\n  Returns a function having signature:\n\n  .. code-block:: python\n\n    def get_eval_metric_ops(graph_output, labels, weights):\n      ...\n      return eval_metric_ops\n\n  where the returned eval_metric_ops is a dict of common evaluation metric\n  Ops for concatenated binary classifications. See `tf.estimator.EstimatorSpec\n  <https://www.tensorflow.org/api_docs/python/tf/estimator/EstimatorSpec>`_\n  for a description of eval_metric_ops. The graph_output is a the result\n  dict returned by build_graph. Labels and weights are tf.Tensors.\n\n  In multiple binary classification problems, the\n  ``predictions`` (that is, ``graph_output['output']``)\n  are expected to have shape ``batch_size x n_classes``,\n  where ``n_classes`` is the number of binary classification.\n  Binary classification at output[i] is expected to discriminate between ``classes[i]`` (1)\n  and NOT ``classes[i]`` (0). The labels should be of the same shape as ``graph_output``\n  with binary values (0 or 1). The weights can be of size ``batch_size`` or\n  ``batch_size x n_classes``. The ``class_dim`` contain separate probabilities,\n  and need to have separate metrics.\n\n  The following graph_output keys are recognized:\n    output:\n      the raw predictions between 0 and 1. Required.\n    threshold:\n      A value between 0 and 1 used to threshold the output into a hard_output.\n      Defaults to 0.5 when threshold and hard_output are missing.\n      Either threshold or hard_output can be provided, but not both.\n    hard_output:\n      A thresholded output. Either threshold or hard_output can be provided, but not both.\n\n  Args:\n    metrics (list of Metrics):\n      a list of metrics of interest. E.g. ['ctr', 'accuracy', 'rce']\n      Element in the list can be a string from following supported metrics, or can be a tuple\n      with three items: metric name, metric function, bool for thresholded output.\n\n      These metrics are evaluated and reported to tensorboard *during the eval phases only*.\n      Supported metrics:\n\n      - ctr (same as positive sample ratio.)\n      - rce (cross entropy loss compared to the baseline model of always predicting ctr)\n      - nrce (normalized rce, do not use this one if you do not understand what it is)\n      - pr_auc\n      - roc_auc\n      - accuracy (percentage of predictions that are correct)\n      - precision (true positives) / (true positives + false positives)\n      - recall (true positives) / (true positives + false negatives)\n      - pr_curve (precision-recall curve)\n\n      Example of metrics list with mixture of string and tuple:\n      metrics = [\n        'rce','nrce',\n        'roc_auc',  # default roc_auc metric\n        (\n          'roc_auc_500',  # give this metric a name\n          partial(tf.metrics.auc, curve='ROC', summation_method='careful_interpolation', num_thresholds=500),  # the metric fn\n          False,  # whether the metric requires thresholded output\n        )]\n\n      NOTE: When prediction on rare events, roc_auc can be underestimated. Increase num_threshold\n      can reduce the underestimation. See go/roc-auc-pitfall for more details.\n\n      NOTE: accuracy / precision / recall apply to binary classification problems only.\n      I.e. a prediction is only considered correct if it matches the label. E.g. if the label\n      is 1.0, and the prediction is 0.99, it does not get credit.  If you want to use\n      precision / recall / accuracy metrics with soft predictions, you'll need to threshold\n      your predictions into hard 0/1 labels.\n\n      When metrics is None (the default), it defaults to:\n      [rce, nrce, arce, ctr, predicted_ctr, accuracy, precision, recall, prauc, roc_auc],\n\n    classes (list of strings):\n      In case of multiple binary class models, the names for each class or label.\n      These are used to display metrics on tensorboard.\n      If these are not specified, the index in the class or label dimension is used, and you'll\n      get metrics on tensorboard named like: accuracy_0, accuracy_1, etc.\n\n    class_dim (number):\n      Dimension of the classes in predictions. Defaults to 1, that is, batch_size x n_classes.\n  ";J=metrics;G=classes;A=class_dim
	if J is _A:J=list(DEFAULT_BINARY_CLASS_METRICS)
	def B(graph_output,labels,weights):
		'\n    graph_output:\n      dict that is returned by build_graph given input features.\n    labels:\n      target labels associated to batch.\n    weights:\n      weights of the samples..\n    ';Q=labels;H=weights;I=graph_output;K=OrderedDict();R=I[_M];U=I[_E]if _E in I else 0.5;L=I.get(_H)
		if L is _A:L=tf.greater_equal(R,U)
		S=Q.get_shape();assert len(S)>A,'Dimension specified by class_dim does not exist.';E=S[A];assert E is not _A,'The multi-metric dimension cannot be None.';assert G is _A or len(G)==E,'Number of classes must match the number of labels';M=H.get_shape()if H is not _A else _A
		if M is _A:B=_A
		elif len(M)>1:B=M[A]
		else:B=1
		for C in range(E):
			for F in J:
				if isinstance(F,tuple)and len(F)==3:D,N,T=F;D=D.lower()
				elif isinstance(F,str):D=F.lower();N,T=SUPPORTED_BINARY_CLASS_METRICS.get(D)
				else:raise ValueError(_n)
				O=D+'_'+(G[C]if G is not _A else str(C))
				if O in K:continue
				V=tf.gather(Q,indices=[C],axis=A);W=tf.gather(R,indices=[C],axis=A);X=tf.gather(L,indices=[C],axis=A)
				if B is _A:P=_A
				elif B==E:P=tf.gather(H,indices=[C],axis=A)
				elif B==1:P=H
				else:raise ValueError('num_weights (%d) and num_labels (%d) do not match'%(B,E))
				if N:Y,Z=N(labels=V,predictions=X if T else W,weights=P,name=O);K[O]=Y,Z
				else:raise ValueError(_o+D)
		return K
	return B
def _get_uncalibrated_metric_fn(calibrated_metric_fn,keep_weight=_C):
	'\n  Returns a function having signature:\n\n  .. code-block:: python\n\n    def get_eval_metric_ops(graph_output, labels, weights):\n      ...\n      return eval_metric_ops\n\n  where the returned eval_metric_ops is a dict of common evaluation metric\n  Ops with uncalibrated output.\n\n  The following graph_output keys are recognized:\n    uncalibrated_output:\n      the uncalibrated raw predictions between 0 and 1. Required.\n    output:\n      the calibrated predictions between 0 and 1.\n    threshold:\n      A value between 0 and 1 used to threshold the output into a hard_output.\n      Defaults to 0.5 when threshold and hard_output are missing.\n      Either threshold or hard_output can be provided, but not both.\n    hard_output:\n      A thresholded output. Either threshold or hard_output can be provided, but not both.\n\n  Args:\n    calibrated_metric_fn: metrics function with calibration and weight.\n    keep_weight: Bool indicating whether we keep weight.\n  ';B=keep_weight;C='uncalibrated'if B else'unweighted'
	def A(graph_output,labels,weights):
		'\n    graph_output:\n      dict that is returned by build_graph given input features.\n    labels:\n      target labels associated to batch.\n    weights:\n      weights of the samples..\n    ';D='uncalibrated_output';E=weights;A=graph_output
		with tf.variable_scope(C):
			if D not in A:raise Exception('Missing uncalibrated_output in graph_output!')
			F=E if B else tf.ones_like(E);G={_M:A[D],_E:A.get(_E,0.5),_H:A.get(_H),**{A:B for(A,B)in A.items()if A not in[_M,_E,_H]}};H=calibrated_metric_fn(G,labels,F);I={f"{C}_{A}":B for(A,B)in H.items()};return I
	return A
def get_multi_binary_class_uncalibrated_metric_fn(metrics,classes=_A,class_dim=1,keep_weight=_C):"\n  Returns a function having signature:\n\n  .. code-block:: python\n\n    def get_eval_metric_ops(graph_output, labels, weights):\n      ...\n      return eval_metric_ops\n\n  where the returned eval_metric_ops is a dict of common evaluation metric\n  Ops for concatenated binary classifications without calibration.\n\n  Note: 'uncalibrated_output' is required key in graph_output.\n\n  The main use case for this function is:\n\n  1) To calculated roc-auc for rare event.\n  Calibrated prediction score for rare events will be concentrated near zero. As a result,\n  the roc-auc can be seriously underestimated with current implementation in tf.metric.auc.\n  Since roc-auc is invariant against calibration, we can directly use uncalibrated score for roc-auc.\n  For more details, please refer to: go/roc-auc-invariance.\n\n  2) To set keep_weight=False and get unweighted and uncalibrated metrics.\n  This is useful to eval how the model is fitted to its actual training data, since\n  often time the model is trained without weight.\n\n  Args:\n    metrics (list of String):\n      a list of metrics of interest. E.g. ['ctr', 'accuracy', 'rce']\n      Element in the list can be a string from supported metrics, or can be a tuple\n      with three items: metric name, metric function, bool for thresholded output.\n      These metrics are evaluated and reported to tensorboard *during the eval phases only*.\n\n      When metrics is None (the default), it defaults to:\n      [rce, nrce, arce, ctr, predicted_ctr, accuracy, precision, recall, prauc, roc_auc],\n\n    classes (list of strings):\n      In case of multiple binary class models, the names for each class or label.\n      These are used to display metrics on tensorboard.\n      If these are not specified, the index in the class or label dimension is used, and you'll\n      get metrics on tensorboard named like: accuracy_0, accuracy_1, etc.\n\n    class_dim (number):\n      Dimension of the classes in predictions. Defaults to 1, that is, batch_size x n_classes.\n\n    keep_weight (bool):\n      Whether to keep weights for the metric.\n  ";A=get_multi_binary_class_metric_fn(metrics,classes=classes,class_dim=class_dim);return _get_uncalibrated_metric_fn(A,keep_weight=keep_weight)
def combine_metric_fns(*B):
	'\n  Combine multiple metric functions.\n  For example, we can combine metrics function generated by\n  get_multi_binary_class_metric_fn and get_multi_binary_class_uncalibrated_metric_fn.\n\n  Args:\n    *fn_list: Multiple metric functions to be combined\n\n  Returns:\n    Combined metric function.\n  '
	def A(*C,**D):
		A=OrderedDict()
		for E in B:A.update(E(*(C),**D))
		return A
	return A