'\nThis module contains common calibrate and export functions for calibrators.\n'
_i='batch_size'
_h='calibrate'
_g='labels'
_f='calibrator_train_steps'
_e='Chief training discretizer'
_d='num_workers'
_c='TWML_HOGWILD_TASK_TYPE'
_b='output'
_a='Chief training calibrator'
_Z='Trainer overwriting existing save directory: %s (params.overwrite_save_dir)'
_Y='Discretizer batch size'
_X='Path to save or load discretizer calibration'
_W='--discretizer.save_dir'
_V='isotonic_calibrator'
_U='calibrator_num_bins'
_T='--calibrator.save_dir'
_S='loss'
_R='train_op'
_Q='discretizer_parts_downsampling_rate'
_P='discretizer_keep_rate'
_O='discretizer_batch_size'
_N='discretizer_save_dir'
_M='Keep rate'
_L='store_true'
_K='calibrator_parts_downsampling_rate'
_J='calibrator_batch_size'
_I='calibrator_save_dir'
_H='Worker waiting for calibration at %s'
_G='tfhub_module.pb'
_F='Parts downsampling rate'
_E='TWML_HOGWILD_PORTS'
_D='weights'
_C=False
_B=True
_A=None
import copy,os,time
from absl import logging
import tensorflow.compat.v1 as tf,tensorflow_hub as hub,twml
from twml.argument_parser import SortingHelpFormatter
from twml.input_fns import data_record_input_fn
from twml.util import list_files_by_datetime,sanitize_hdfs_path
from twml.contrib.calibrators.isotonic import IsotonicCalibrator
def calibrator_arguments(parser):'\n  Calibrator Parameters to add to relevant parameters to the DataRecordTrainerParser.\n  Otherwise, if alone in a file, it just creates its own default parser.\n  Arguments:\n    parser:\n      Parser with the options to the model\n  ';A=parser;A.add_argument(_T,type=str,dest=_I,help='Path to save or load calibrator calibration');A.add_argument('--calibrator_batch_size',type=int,default=128,dest=_J,help='calibrator batch size');A.add_argument('--calibrator_parts_downsampling_rate',type=float,default=1,dest=_K,help=_F);A.add_argument('--calibrator_max_steps',type=int,default=_A,dest='calibrator_max_steps',help='Max Steps taken by calibrator to accumulate samples');A.add_argument('--calibrator_num_bins',type=int,default=22,dest=_U,help='Num bins of calibrator');A.add_argument('--isotonic_calibrator',dest=_V,action=_L,help='Isotonic Calibrator present');A.add_argument('--calibrator_keep_rate',type=float,default=1.0,dest='calibrator_keep_rate',help=_M);return A
def _generate_files_by_datetime(params):A=params;B=list_files_by_datetime(base_path=sanitize_hdfs_path(A.train_data_dir),start_datetime=A.train_start_datetime,end_datetime=A.train_end_datetime,datetime_prefix_format=A.datetime_format,extension='lzo',parallelism=1,hour_resolution=A.hour_resolution,sort=_B);return B
def get_calibrate_input_fn(parse_fn,params):'\n  Default input function used for the calibrator.\n  Arguments:\n    parse_fn:\n      Parse_fn\n    params:\n      Parameters\n  Returns:\n    input_fn\n  ';A=params;return lambda:data_record_input_fn(files=_generate_files_by_datetime(A),batch_size=A.calibrator_batch_size,parse_fn=parse_fn,num_threads=1,repeat=_C,keep_rate=A.calibrator_keep_rate,parts_downsampling_rate=A.calibrator_parts_downsampling_rate,shards=_A,shard_index=_A,shuffle=_B,shuffle_files=_B,interleave=_B)
def get_discretize_input_fn(parse_fn,params):'\n  Default input function used for the calibrator.\n  Arguments:\n    parse_fn:\n      Parse_fn\n    params:\n      Parameters\n  Returns:\n    input_fn\n  ';A=params;return lambda:data_record_input_fn(files=_generate_files_by_datetime(A),batch_size=A.discretizer_batch_size,parse_fn=parse_fn,num_threads=1,repeat=_C,keep_rate=A.discretizer_keep_rate,parts_downsampling_rate=A.discretizer_parts_downsampling_rate,shards=_A,shard_index=_A,shuffle=_B,shuffle_files=_B,interleave=_B)
def discretizer_arguments(parser=_A):
	'\n  Discretizer Parameters to add to relevant parameters to the DataRecordTrainerParser.\n  Otherwise, if alone in a file, it just creates its own default parser.\n  Arguments:\n    parser:\n      Parser with the options to the model. Defaults to None\n  ';A=parser
	if A is _A:A=twml.DefaultSubcommandArgParse(formatter_class=SortingHelpFormatter);A.add_argument('--overwrite_save_dir',dest='overwrite_save_dir',action=_L,help='Delete the contents of the current save_dir if it exists');A.add_argument('--train.data_dir','--train_data_dir',type=str,default=_A,dest='train_data_dir',help='Path to the training data directory.Supports local and HDFS (hdfs://default/<path> ) paths.');A.add_argument('--train.start_date','--train_start_datetime',type=str,default=_A,dest='train_start_datetime',help='Starting date for training inside the train data dir.The start datetime is inclusive.e.g. 2019/01/15');A.add_argument('--train.end_date','--train_end_datetime',type=str,default=_A,dest='train_end_datetime',help='Ending date for training inside the train data dir.The end datetime is inclusive.e.g. 2019/01/15');A.add_argument('--datetime_format',type=str,default='%Y/%m/%d',help='Date format for training and evaluation datasets.Has to be a format that is understood by python datetime.e.g. %Y/%m/%d for 2019/01/15.Used only if {train/eval}.{start/end}_date are provided.');A.add_argument('--hour_resolution',type=int,default=_A,help='Specify the hourly resolution of the stored data.');A.add_argument('--tensorboard_port',type=int,default=_A,help='Port for tensorboard to run on.');A.add_argument('--stats_port',type=int,default=_A,help='Port for stats server to run on.');A.add_argument('--health_port',type=int,default=_A,help='Port to listen on for health-related endpoints (e.g. graceful shutdown).Not user-facing as it is set automatically by the twml_cli.');A.add_argument('--data_spec',type=str,default=_A,help='Path to data specification JSON file. This file is used to decode DataRecords')
	A.add_argument(_W,type=str,dest=_N,help=_X);A.add_argument('--discretizer_batch_size',type=int,default=128,dest=_O,help=_Y);A.add_argument('--discretizer_keep_rate',type=float,default=0.0008,dest=_P,help=_M);A.add_argument('--discretizer_parts_downsampling_rate',type=float,default=0.2,dest=_Q,help=_F);A.add_argument('--discretizer_max_steps',type=int,default=_A,dest='discretizer_max_steps',help='Max Steps taken by discretizer to accumulate samples');return A
def calibrate(trainer,params,build_graph,input_fn,debug=_C):
	'\n  Calibrate Isotonic Calibration\n  Arguments:\n    trainer:\n      Trainer\n    params:\n      Parameters\n    build_graph:\n      Build Graph used to be the input to the calibrator\n    input_fn:\n      Input Function specified by the user\n    debug:\n      Defaults to False. Returns the calibrator\n  ';E=trainer;A=params
	if E._estimator.config.is_chief:
		if A.overwrite_save_dir and tf.io.gfile.exists(A.calibrator_save_dir):logging.info(_Z%A.calibrator_save_dir);tf.io.gfile.rmtree(A.calibrator_save_dir)
		B=IsotonicCalibrator(A.calibrator_num_bins);logging.info(_a);C,I=input_fn()
		if _D not in C:raise ValueError('Weights need to be returned as part of the parse_fn')
		J=C.pop(_D);K=build_graph(features=C,label=_A,mode='infer',params=A,config=_A);L=tf.global_variables_initializer();M=tf.tables_initializer()
		with tf.Session()as D:
			D.run(L);D.run(M);F=0;G=A.calibrator_max_steps or-1
			while G<=0 or F<=G:
				try:N,O,P=D.run([J,I,K[_b]]);B.accumulate(P,O,N.flatten())
				except tf.errors.OutOfRangeError:break
				F+=1
		B.calibrate();B.save(A.calibrator_save_dir);E.estimator._params.isotonic_calibrator=_B
		if debug:return B
	else:
		H=twml.util.sanitize_hdfs_path(A.calibrator_save_dir)
		while not tf.io.gfile.exists(H+os.path.sep+_G):logging.info(_H%H);time.sleep(60)
def discretize(params,feature_config,input_fn,debug=_C):
	'\n  Discretizes continuous features\n  Arguments:\n    params:\n      Parameters\n    input_fn:\n      Input Function specified by the user\n    debug:\n      Defaults to False. Returns the calibrator\n  ';A=params
	if os.environ.get(_c)=='chief'or _d not in A or A.num_workers is _A:
		if A.overwrite_save_dir and tf.io.gfile.exists(A.discretizer_save_dir):logging.info(_Z%A.discretizer_save_dir);tf.io.gfile.rmtree(A.discretizer_save_dir)
		H=feature_config();B=H['discretize_config'];logging.info(_e);I=input_fn()
		with tf.Session()as J:
			E=0;F=A.discretizer_max_steps or-1
			while F<=0 or E<=F:
				try:
					K=J.run(I)
					for (C,D) in B.items():D.accumulate_features(K[0],C)
				except tf.errors.OutOfRangeError:break
				E+=1
		def L():
			for (C,A) in B.items():A.calibrate();A.add_hub_signatures(C)
		M=hub.create_module_spec(L)
		with tf.Graph().as_default():
			N=hub.Module(M)
			with tf.Session()as O:N.export(A.discretizer_save_dir,O)
		for (C,D) in B.items():D.write_summary_json(A.discretizer_save_dir,C)
		if debug:return B
	else:
		time.sleep(60);G=twml.util.sanitize_hdfs_path(A.discretizer_save_dir)
		while not tf.io.gfile.exists(G+os.path.sep+_G):logging.info(_H%G);time.sleep(60)
def add_discretizer_arguments(parser):'\n  Add discretizer-specific command-line arguments to a Trainer parser.\n\n  Arguments:\n    parser: argparse.ArgumentParser instance obtained from Trainer.get_trainer_parser\n\n  Returns:\n    argparse.ArgumentParser instance with discretizer-specific arguments added\n  ';A=parser;A.add_argument(_W,type=str,dest=_N,help=_X);A.add_argument('--discretizer.batch_size',type=int,default=128,dest=_O,help=_Y);A.add_argument('--discretizer.keep_rate',type=float,default=0.0008,dest=_P,help=_M);A.add_argument('--discretizer.parts_downsampling_rate',type=float,default=0.2,dest=_Q,help=_F);A.add_argument('--discretizer.num_bins',type=int,default=20,dest='discretizer_num_bins',help='Number of bins per feature');A.add_argument('--discretizer.output_size_bits',type=int,default=22,dest='discretizer_output_size_bits',help='Number of bits allocated to the output size');return A
def add_isotonic_calibrator_arguments(parser):'\n  Add discretizer-specific command-line arguments to a Trainer parser.\n\n  Arguments:\n    parser: argparse.ArgumentParser instance obtained from Trainer.get_trainer_parser\n\n  Returns:\n    argparse.ArgumentParser instance with discretizer-specific arguments added\n  ';A=parser;A.add_argument('--calibrator.num_bins',type=int,default=25000,dest=_U,help='number of bins for isotonic calibration');A.add_argument('--calibrator.parts_downsampling_rate',type=float,default=0.1,dest=_K,help=_F);A.add_argument(_T,type=str,dest=_I,help='Path to save or load calibrator output');A.add_argument('--calibrator.load_tensorflow_module',type=str,default=_A,dest='calibrator_load_tensorflow_module',help='Location from where to load a pretrained graph from.                            Typically, this is where the MLP graph is saved');A.add_argument('--calibrator.export_mlp_module_name',type=str,default='tf_hub_mlp',help='Name for loaded hub signature',dest='export_mlp_module_name');A.add_argument('--calibrator.export_isotonic_module_name',type=str,default='tf_hub_isotonic',dest='calibrator_export_module_name',help='export module name');A.add_argument('--calibrator.final_evaluation_steps',type=int,dest='calibrator_final_evaluation_steps',default=_A,help='number of steps for final evaluation');A.add_argument('--calibrator.train_steps',type=int,default=-1,dest=_f,help='number of steps for calibration');A.add_argument('--calibrator.batch_size',type=int,default=1024,dest=_J,help='Calibrator batch size');A.add_argument('--calibrator.is_calibrating',action=_L,dest='is_calibrating',help='Dummy argument to allow running in chief worker');return A
def calibrate_calibrator_and_export(name,calibrator,build_graph_fn,params,feature_config,run_eval=_B,input_fn=_A,metric_fn=_A,export_task_type_overrider=_A):
	'\n  Pre-set `isotonic calibrator` calibrator.\n  Args:\n    name:\n      scope name used for the calibrator\n    calibrator:\n      calibrator that will be calibrated and exported.\n    build_graph_fn:\n      build graph function for the calibrator\n    params:\n      params passed to the calibrator\n    feature_config:\n      feature config which will be passed to the trainer\n    export_task_type_overrider:\n      the task type for exporting the calibrator\n      if specified, this will override the default export task type in trainer.hub_export(..)\n  ';H='evaluate';E=metric_fn;F=feature_config;G=params;C=input_fn;A=copy.deepcopy(G);A.data_threads=1;A.num_workers=1;A.continue_from_checkpoint=_B;A.overwrite_save_dir=_C;A.stats_port=_A
	if A.calibrator_load_tensorflow_module is _A:K=os.path.join(G.save_dir,G.export_mlp_module_name);A.calibrator_load_tensorflow_module=K
	if _K in A:A.train_parts_downsampling_rate=A.calibrator_parts_downsampling_rate
	if _I in A:A.save_dir=A.calibrator_save_dir
	if _J in A:A.train_batch_size=A.calibrator_batch_size;A.eval_batch_size=A.calibrator_batch_size
	if _f in A:A.train_steps=A.calibrator_train_steps
	if E is _A:E=twml.metrics.get_multi_binary_class_metric_fn(_A)
	B=twml.trainers.DataRecordTrainer(name=name,params=A,feature_config=F,build_graph_fn=build_graph_fn,save_dir=A.save_dir,metric_fn=E)
	if B._estimator.config.is_chief:
		logging.info(_a);I=os.environ.get(_E);os.environ[_E]='';J=_A
		if A.calibrator_train_steps>0:J=[twml.hooks.StepProgressHook(A.calibrator_train_steps)]
		def L(input_x):C=F.get_parse_fn();A,B=C(input_x);A[_g]=B;return A,B
		if C is _A:C=B.get_train_input_fn(parse_fn=L,repeat=_C)
		B.estimator._params.mode=_h;B.calibrate(calibrator=calibrator,input_fn=C,steps=A.calibrator_train_steps,hooks=J);B.estimator._params.mode=H;B.train(input_fn=C,steps=1)
		if I is not _A:os.environ[_E]=I
	else:
		D=os.path.join(A.calibrator_save_dir,A.calibrator_export_module_name);D=twml.util.sanitize_hdfs_path(D)
		while not tf.io.gfile.exists(D+os.path.sep+_G):logging.info(_H%D);time.sleep(60)
	if run_eval:B.estimator._params.mode=H;B.evaluate(name='test',input_fn=C,steps=A.calibrator_final_evaluation_steps)
	B.hub_export(name=A.calibrator_export_module_name,export_task_type_overrider=export_task_type_overrider,serving_input_receiver_fn=F.get_serving_input_receiver_fn());return B
def calibrate_discretizer_and_export(name,calibrator,build_graph_fn,params,feature_config):
	'\n  Pre-set percentile discretizer calibrator.\n  Args:\n    name:\n      scope name used for the calibrator\n    calibrator:\n      calibrator that will be calibrated and exported.\n    build_graph_fn:\n      build graph function for the calibrator\n    params:\n      params passed to the calibrator\n    feature_config:\n      feature config or input_fn which will be passed to the trainer.\n  ';C=params;B=feature_config
	if os.environ.get(_c)=='chief'or _d not in C or C.num_workers is _A:
		logging.info(_e);D=os.environ.get(_E);os.environ[_E]='';A=copy.deepcopy(C);A.data_threads=1;A.train_steps=-1;A.train_max_steps=_A;A.eval_steps=-1;A.num_workers=1;A.tensorboard_port=_A;A.stats_port=_A
		if _O in A:A.train_batch_size=A.discretizer_batch_size;A.eval_batch_size=A.discretizer_batch_size
		if _P in A:A.train_keep_rate=A.discretizer_keep_rate
		if _Q in A:A.train_parts_downsampling_rate=A.discretizer_parts_downsampling_rate
		if _N in A:A.save_dir=A.discretizer_save_dir
		E=twml.trainers.DataRecordTrainer(name=name,params=A,build_graph_fn=build_graph_fn,save_dir=A.save_dir)
		if isinstance(B,twml.feature_config.FeatureConfig):I=twml.parsers.get_continuous_parse_fn(B);F=E.get_train_input_fn(parse_fn=I,repeat=_C)
		elif callable(B):F=B
		else:J=type(B).__name__;raise ValueError('Expecting feature_config to be FeatureConfig or function got %s'%J)
		G=_A
		if A.train_steps>0:G=[twml.hooks.StepProgressHook(A.train_steps)]
		E.calibrate(calibrator=calibrator,input_fn=F,steps=A.train_steps,hooks=G)
		if D is not _A:os.environ[_E]=D
	else:
		H=twml.util.sanitize_hdfs_path(C.discretizer_save_dir)
		while not tf.io.gfile.exists(H+os.path.sep+_G):logging.info(_H%H);time.sleep(60)
def build_percentile_discretizer_graph(features,label,mode,params,config=_A):
	'\n  Pre-set Percentile Discretizer Build Graph\n  Follows the same signature as build_graph\n  ';B=features;A=twml.util.convert_to_sparse(B,params.input_size_bits);C=tf.reshape(B[_D],tf.reshape(B[_i],[1]))
	if isinstance(A,tf.SparseTensor):D=A.indices[:,1];E=A.indices[:,0]
	elif isinstance(A,twml.SparseTensor):D=A.indices;E=A.ids
	C=tf.gather(params=C,indices=E);F=D;G=A.values;H=tf.assign_add(tf.train.get_global_step(),1);I=tf.constant(1)
	if mode=='train':return{_R:H,_S:I}
	return{'feature_ids':F,'feature_values':G,_D:C}
def isotonic_module(mode,params):'\n  Common Isotonic Calibrator module for Hub Export\n  ';B='sparse_input';A=params;C=tf.sparse_placeholder(tf.float32,name=B);D=hub.Module(A.calibrator_load_tensorflow_module);E=D(C,signature=A.export_mlp_module_name);F=hub.Module(A.save_dir);G=F(E,signature=_V);hub.add_signature(inputs={B:C},outputs={'default':G},name=A.calibrator_export_module_name)
def build_isotonic_graph_from_inputs(inputs,features,label,mode,params,config=_A,isotonic_fn=_A):
	'\n  Helper function to build_isotonic_graph\n  Pre-set Isotonic Calibrator Build Graph\n  Follows the same signature as build_graph\n  ';G=isotonic_fn;H=inputs;C=mode;D=features;A=params
	if A.mode==_h:
		K=hub.Module(A.calibrator_load_tensorflow_module);L=K(H,signature=A.export_mlp_module_name);M=tf.reshape(D[_D],tf.reshape(D[_i],[1]));E=tf.assign_add(tf.train.get_global_step(),1);F=tf.constant(1)
		if C=='train':return{_R:E,_S:F}
		return{'predictions':L,'targets':D[_g],_D:M}
	else:
		if G is _A:I=twml.util.create_module_spec(mlp_fn=isotonic_module,mode=C,params=A)
		else:I=twml.util.create_module_spec(mlp_fn=G,mode=C,params=A)
		J=hub.Module(I,name=A.calibrator_export_module_name);hub.register_module_for_export(J,A.calibrator_export_module_name);B=J(H,signature=A.calibrator_export_module_name);B=tf.clip_by_value(B,0,1);F=tf.reduce_sum(tf.stop_gradient(B));E=tf.assign_add(tf.train.get_global_step(),1);return{_R:E,_S:F,_b:B}
def build_isotonic_graph(features,label,mode,params,config=_A,export_discretizer=_B):
	'\n  Pre-set Isotonic Calibrator Build Graph\n  Follows the same signature as build_graph\n  This assumes that MLP already contains all modules (include percentile\n  discretizer); if export_discretizer is set\n  then it does not export the MDL phase.\n  ';C=config;D=label;B=features;A=params;E=twml.util.convert_to_sparse(B,A.input_size_bits)
	if export_discretizer:return build_isotonic_graph_from_inputs(E,B,D,mode,A,C)
	G=hub.Module(A.discretizer_path)
	if A.discretizer_signature is _A:F='percentile_discretizer_calibrator'
	else:F=A.discretizer_signature
	H=G(E,signature=F);return build_isotonic_graph_from_inputs(H,B,D,mode,A,C)