_C='epoch'
_B=False
_A=None
from collections import defaultdict
import os
from toxicity_ml_pipeline.settings.default_settings_tox import REMOTE_LOGDIR
from toxicity_ml_pipeline.settings.default_settings_abs import LABEL_NAMES
from toxicity_ml_pipeline.utils.absv_utils import parse_labeled_data
from toxicity_ml_pipeline.utils.helpers import compute_precision_fixed_recall,execute_command
from sklearn.metrics import average_precision_score,roc_auc_score
import tensorflow as tf,wandb
class NothingCallback(tf.keras.callbacks.Callback):
	def on_epoch_begin(A,epoch,logs=_A):print('ici, ',epoch)
	def on_epoch_end(A,epoch,logs=_A):print('fin ',epoch)
	def on_train_batch_end(A,batch,logs=_A):print('fin de batch ',batch)
class ControlledStoppingCheckpointCallback(tf.keras.callbacks.ModelCheckpoint):
	def __init__(A,stopping_epoch,*B,**C):super().__init__(*(B),**C);A.stopping_epoch=stopping_epoch
	def on_epoch_end(A,epoch,logs=_A):
		B=epoch;super().on_epoch_end(B,logs)
		if B==A.stopping_epoch:A.model.stop_training=True
class SyncingTensorBoard(tf.keras.callbacks.TensorBoard):
	def __init__(B,remote_logdir=_A,*C,**D):A=remote_logdir;super().__init__(*(C),**D);B.remote_logdir=A if A is not _A else REMOTE_LOGDIR
	def on_epoch_end(A,epoch,logs=_A):super().on_epoch_end(epoch,logs=logs);A.synchronize()
	def synchronize(A):B=os.path.dirname(A.log_dir);C=f"gsutil -m rsync -r {B} {A.remote_logdir}";execute_command(C)
class GradientLoggingTensorBoard(SyncingTensorBoard):
	def __init__(A,loader,val_data,freq,*C,**D):super().__init__(*(C),**D);E=loader.get_balanced_dataset(training_data=val_data,size_limit=50,return_as_batch=_B);B=list(E.batch(32).take(1))[0];A.x_batch,A.y_batch=B[0],B[1];A.freq=freq;A.counter=0
	def _log_gradients(A):
		B=A._train_writer
		with B.as_default():
			with tf.GradientTape()as C:D=A.model(A.x_batch);E=A.model.compiled_loss(y_true=A.y_batch,y_pred=D);F=tf.linalg.global_norm(C.gradient(E,A.model.trainable_weights))
			tf.summary.scalar('gradient_norm',data=F,step=A.counter)
		B.flush()
	def on_train_batch_end(A,batch,logs=_A):
		B=batch;super().on_batch_end(B,logs=logs);A.counter+=1
		if B%A.freq==0:A._log_gradients()
class AdditionalResultLogger(tf.keras.callbacks.Callback):
	def __init__(A,data,set_,fixed_recall=0.85,from_logits=_B,dataset_transform_func=_A,batch_size=64,dual_head=_A,*H,**I):
		F=dataset_transform_func;G=from_logits;D=dual_head;E=batch_size;C=fixed_recall;B=data;super().__init__(*(H),**I);A.set_=set_
		if B is _A:return _A
		A.single_head=True
		try:A.labels=B.int_label.values
		except AttributeError:A.labels=B.to_dataframe()[LABEL_NAMES].values.astype('int');A.data=B.to_tf_dataset().map(parse_labeled_data).batch(E);A.label_names=LABEL_NAMES
		else:
			A.label_names=['']
			if D:A.label_names=[f"{A}_label"for A in D];A.labels={f"{A}_output":B[f"{A}_label"].values for A in D};A.single_head=_B
			if F is _A:A.data=B.text.values
			else:A.data=F(B,mb_size=E,shuffle=_B)
		finally:
			if len(A.label_names)==1:A.metric_kw={}
			else:A.metric_kw={'average':_A}
			A.counter=0;A.best_metrics=defaultdict(float);A.from_logits=G;print(f"Loaded callback for {set_}, from_logits: {G}, labels {A.label_names}")
			if 1<C<=100:C=C/100
			elif not 0<C<=100:raise ValueError('Threshold should be between 0 and 1, or 0 and 100')
			A.fixed_recall=C;A.batch_size=E
	def compute_precision_fixed_recall(A,labels,preds):B,C=compute_precision_fixed_recall(labels=labels,preds=preds,fixed_recall=A.fixed_recall);return B
	def on_epoch_end(A,epoch,logs=_A):A.additional_evaluations(step=epoch,eval_time=_C)
	def on_train_batch_end(A,batch,logs=_A):
		A.counter+=1
		if A.counter%2000==0:A.additional_evaluations(step=A.counter,eval_time='batch')
	def _binary_evaluations(C,preds,label_name=_A,class_index=_A):
		E=class_index;F=label_name;B=preds;D=_A;A=C.labels
		if F is not _A:
			A=C.labels[F]
			if E is not _A:A=(A==E).astype(int)
		if-1 in A:D=A!=-1;A=A[D];B=B[D]
		return{f"precision_recall{C.fixed_recall}":C.compute_precision_fixed_recall(labels=A,preds=B),'pr_auc':average_precision_score(y_true=A,y_score=B),'roc_auc':roc_auc_score(y_true=A,y_score=B)}
	def _multiclass_evaluations(A,preds):
		C=preds;F=average_precision_score(y_true=A.labels,y_score=C,**A.metric_kw);G=roc_auc_score(y_true=A.labels,y_score=C,**A.metric_kw);B={}
		for (D,E) in enumerate(A.label_names):B[f"pr_auc_{E}"]=F[D];B[f"roc_auc_{E}"]=G[D]
		return B
	def additional_evaluations(A,step,eval_time):
		G=eval_time;print('Evaluating ',A.set_,G,step);B=A.model.predict(x=A.data,batch_size=A.batch_size)
		if A.from_logits:B=tf.keras.activations.sigmoid(B.logits).numpy()
		if A.single_head:
			if len(A.label_names)==1:C=A._binary_evaluations(B)
			else:C=A._multiclass_evaluations(B)
		else:
			if B[0].shape[1]==1:H=B[0];D=B[1]
			else:H=B[1];D=B[0]
			E=A._binary_evaluations(H,label_name='target_output');C={f"{A}_target":B for(A,B)in E.items()};J=D.shape[1]
			for F in range(J):E=A._binary_evaluations(D[:,F],label_name='content_output',class_index=F);C.update({f"{A}_content_{F}":B for(A,B)in E.items()})
		for (I,K) in C.items():A.best_metrics[f"max_{I}"]=max(K,A.best_metrics[f"max_{I}"])
		A.log_metrics(C,step=step,eval_time=G)
	def log_metrics(A,metrics_d,step,eval_time):
		C=_B if A.set_=='validation'else True;B={A.set_:{**metrics_d,**A.best_metrics}}
		if eval_time==_C:B[_C]=step
		wandb.log(B,commit=C)