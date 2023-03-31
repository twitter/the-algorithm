"\nThis is a temporary close gap solution that allows TensorFlow users to do exploration and\nexperimentation using Keras models, and production training using twml Trainer.\n\nAs of now (Q4 2019), Keras model training using `model.fit()` has various issues, making it unfit\nfor production training:\n  1. `model.fit()` is slow in TF 1.14. This will be fixed with future TensorFlow updates.\n  2. `model.fit()` crashes during model saving or in eager mode when the input has SparseTensor.\n  3. Models saved using TF 2.0 API cannot be served by TensorFlow's Java API.\n\nUntil MLCE team resolves the above issues, MLCE team recommends the following:\n  - Please feel free to use Keras models for experimentation and exploration.\n  - Please stick to twml Trainer for production training & exporting,\n    especially if you want to serve your model using Twitter's prediction servers.\n\nThis module provide tooling for easily training keras models using twml Trainer.\n\nThis module takes a Keras model that performs binary classification, and returns a\n`twml.trainers.Trainer` object performing the same task.\nThe common way to use the returned Trainer object is to call its\n`train`, `evaluate`, `learn`, or `train_and_evaluate` method with an input function.\nThis input function can be created from the tf.data.Dataset you used with your Keras model.\n\n.. note: this util handles the most common case. If you have cases not satisfied by this util,\n         consider writing your own build_graph to wrap your keras models.\n"
_A=None
from twitter.deepbird.hparam import HParams
import tensorflow,tensorflow.compat.v2 as tf,twml
def build_keras_trainer(name,model_factory,save_dir,loss_fn=_A,metrics_fn=_A,**D):
	"\n  Compile the given model_factory into a twml Trainer.\n\n  Args:\n    name: a string name for the returned twml Trainer.\n\n    model_factory: a callable that returns a keras model when called.\n      This keras model is expected to solve a binary classification problem.\n      This keras model takes a dict of tensors as input, and outputs a logit or probability.\n\n    save_dir: a directory where the trainer saves data. Can be an HDFS path.\n\n    loss_fn: the loss function to use. Defaults to tf.keras.losses.BinaryCrossentropy.\n\n    metrics_fn: metrics function used by TensorFlow estimators.\n    Defaults to twml.metrics.get_binary_class_metric_fn().\n\n    **kwargs: for people familiar with twml Trainer's options, they can be passed in here\n      as kwargs, and they will be forwarded to Trainer as opts.\n      See https://cgit.twitter.biz/source/tree/twml/twml/argument_parser.py#n43 for available args.\n\n  Returns:\n    a twml.trainers.Trainer object which can be used for training and exporting models.\n  ";B=save_dir;A=metrics_fn;E=create_build_graph_fn(model_factory,loss_fn)
	if A is _A:A=twml.metrics.get_binary_class_metric_fn()
	C=HParams(**D);C.add_hparam('save_dir',B);return twml.trainers.Trainer(name,C,build_graph_fn=E,save_dir=B,metric_fn=A)
def create_build_graph_fn(model_factory,loss_fn=_A):
	'Create a build graph function from the given keras model.';C=loss_fn
	def A(features,label,mode,params,config=_A):
		D='loss';E=features;G=model_factory()
		if C is _A:F=tf.keras.losses.BinaryCrossentropy(from_logits=False)
		else:F=C
		A=G(E)
		if mode=='infer':B=_A
		else:H=E.get('weights',_A);B=F(y_true=label,y_pred=A,sample_weight=H)
		if isinstance(A,dict):
			if B is _A:return A
			else:A[D]=B;return A
		else:return{'output':A,D:B}
	return A