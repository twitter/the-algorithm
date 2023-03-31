'\nCommon build graphs that can be reused\n'
import tensorflow.compat.v1 as tf
def get_saved_modules_graph(input_graph_fn):
	'\n  Get common graph for stitching different saved modules for export.\n  This graph is used to save checkpoints; and then export the modules\n  as a unity.\n  Args:\n        features:\n          model features\n        params:\n          model params\n        input_graph_fn:\n          main logic for the stitching\n  Returns:\n    build_graph\n  '
	def A(features,label,mode,params,config=None):
		A=input_graph_fn(features,params)
		if mode=='train':B=tf.constant(1);C=tf.assign_add(tf.train.get_global_step(),1);return{'train_op':C,'loss':B}
		return A
	return A