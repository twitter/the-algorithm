'\nThis module contains utility functions for twml.\n'
_D=True
_C='/'
_B=False
_A=None
import argparse
from datetime import datetime
import itertools,json,logging as _logging,os,re
from twitter.ml.common.resources import AuroraPath
from twitter.deepbird.hparam import HParams
from twitter.deepbird.io.util import _get_feature_id,feature_id,preprocess_feature_regex,preprocess_path,sanitize_hdfs_path,is_string,list_files,match_files
from twitter.deepbird.io.legacy.util import batch_apply,boolean_mask,fixed_length_tensor
from twitter.deepbird.sparse.util import convert_to_sparse,limit_bits
from dateutil import rrule
from joblib import delayed,Parallel
from six import string_types
from absl import logging
from libtwml import CLIB,OPLIB
import tensorflow.compat.v1 as tf
from tensorflow.python.platform import tf_logging
import twml
from twml.feature_config import FeatureConfigBuilder
HASHING_PRIME=2479700537
def multiplicative_hash(input,hash_constant=HASHING_PRIME):return input*hash_constant
def _return_tensors_from_checkpoint_folder(init_dir,model_name=_A):
	'Returns tensors list from a checkpoint folder\n\n  Args:\n    init_dir: Name of the checkpoint directory.\n    model_name: the model which we will use to obtain the checkpoint\n      (e.g. model.ckpt-50000) if set to None it will default to the\n      latest model saved in the checkpont file.\n\n  '
	if model_name is _A:
		model_path=tf.train.latest_checkpoint(init_dir)
		if model_path is _A:raise ValueError('Could not find a valid model checkpoint inside the directory')
	else:model_path=os.path.join(init_dir,model_name)
	reader=tf.train.NewCheckpointReader(model_path)
	try:return reader.debug_string().decode('utf-8')
	except OSError:logging.error('Could not decode the string')
def get_scope_dict(init_dir,incoming_scope_name,current_scope_name,model_name=_A):
	'Returns tensors map from a checkpoint file.\n\n  Args:\n    file_name:\n      Name of the checkpoint directory.\n    incoming_scope_name:\n      scope name of the previous phase\n    current_scope_name:\n      scope name of current phase\n    model_name:\n      the model which we will use to obtain the checkpoint\n      (e.g. model.ckpt-50000) if set to None it will default\n      to the latest model saved in the checkpoint file.\n  Returns:\n    init_map:\n      init_map which will be inputted to the checkpoint\n  ';init_map={};reader_dump=_return_tensors_from_checkpoint_folder(init_dir=init_dir,model_name=model_name).splitlines()
	for member in reader_dump:
		if'global_step'not in member:
			saved_variables=str(member.split(' ')[0]);saved_scope=saved_variables.rsplit(_C,1)[0]+_C;new_scope=saved_scope.replace(incoming_scope_name,current_scope_name,1)
			if saved_scope not in init_map.keys():init_map[saved_scope]=new_scope
	return init_map
def get_init_map(init_from_dir,exclude_var_names=_A,exclude_name_scopes=_A,name_scope_to_remove=_A,name_scope_to_prepend=_A):
	"\n  Builds a map for initializing from a checkpoint (see tf.train.init_from_checkpoint).\n\n  It assumes that the latter part of the variable names are consistent between the checkpoint and\n  the new model, but their name_scopes may be different. If the checkpoint model has variable names\n  of the form old/scope/var/foo, and the corresponding variable names for the new model should be\n  my/new/scope/var/foo, then you should set name_scope_to_remove = 'old/' and\n  name_scope_to_prepend = 'my/new/'.\n\n  This function can be used to\n\n  1. Generate an ``init_map`` map that can be passed to the ``Trainer`` init or\n  2. Used to generate an ``init_map`` directly inside ``build_graph_fn``, in\n     which case it should be passed directly to ``tf.train.init_from_checkpoint`` inside\n     ``build_graph_fn``, in which case you do not also need to specify the ``init_map`` argument to\n     the trainer.\n\n  Parameters\n  ----------\n  init_from_dir: Directory containing checkpoint\n  exclude_var_names: list[str]\n    List of variables in the checkpoint that should be excluded from the map.\n  exclude_name_scopes: list[str]\n    List of name_scopes in the checkpoint model that should be excluded from the map.\n  name_scope_to_remove: str\n    portion of name_scope for checkpoint variables that should not be included in variable names\n    for new model.\n  name_scope_to_prepend: str\n    name_scope to prepend to variable names in checkpoint to give variable names for new model.\n\n  Returns\n  -------\n  dict\n    keys are variable names in the checkpoint and values are variable names in the new model,\n    into which the checkpoint parameters should be loaded.\n  ";vars_to_restore=get_checkpoint_variable_names(init_from_dir,exclude_var_names=exclude_var_names,exclude_scopes=exclude_name_scopes)
	if name_scope_to_prepend is not _A:
		if not name_scope_to_prepend.endswith(_C):name_scope_to_prepend+=_C
	if name_scope_to_remove is not _A:
		if not name_scope_to_remove.endswith(_C):name_scope_to_remove+=_C
	init_map={}
	for var_name in vars_to_restore:
		var_name_checkpoint=var_name
		if name_scope_to_remove is not _A:var_name=var_name.replace(name_scope_to_remove,'')
		var_name_new_model=var_name
		if name_scope_to_prepend is not _A:var_name_new_model=name_scope_to_prepend+var_name_new_model
		init_map[var_name_checkpoint]=var_name_new_model
	return init_map
def get_checkpoint_variable_names(model_dir,exclude_var_names=_A,exclude_scopes=_A):
	'\n  Gets a list of variable names from the latest checkpoint in model_dir.\n  Removes variables with scope defined by exclude_scopes, and/or with names defined by\n  exclude_var_names.\n\n  Args:\n    model_dir (str): Directory containing checkpoint file for the pre-trained model\n    exclude_var_names (list): Optional variable names to exclude (can include full/partial scope)\n    exclude_scopes (list): Optional scopes to exclude\n\n  Returns:\n    list: variable names\n  ';checkpoint_path=tf.train.latest_checkpoint(model_dir);variables_and_shapes=tf.train.list_variables(checkpoint_path)
	def _keep(name):
		if exclude_scopes and any((name.startswith(exc_scope)for exc_scope in exclude_scopes)):return _B
		if exclude_var_names and any((name.endswith(exc_var)for exc_var in exclude_var_names)):return _B
		return _D
	names=[x[0]for x in variables_and_shapes if _keep(x[0])];return names
def to_snake_case(name):
	'\n  Changes name to snake case\n  ';A='\\1_\\2';intermediate=re.sub('(.)([A-Z][a-z0-9]+)',A,name);insecure=re.sub('([a-z])([A-Z])',A,intermediate).lower()
	if insecure[0]!='_':return insecure
	return'private'+insecure
def copy_phase_inputs(init_dir,dest_dir):
	'Automatically copies the .json.tf from the init_dir to save_dir\n  so we can load multiple parameters at the same time.\n\n  Args:\n    init_dir:\n      Name of the checkpoint directory.\n    dest_dir:\n      Name of the output directory.\n  '
	if init_dir is not _A:
		for files in tf.io.gfile.listdir(init_dir):
			if files.endswith('.json.tf'):
				src_file=os.path.join(init_dir,files);dest_file=os.path.join(dest_dir,files)
				if not tf.io.gfile.exists(dest_dir):
					try:tf.io.gfile.makedirs(dest_dir)
					except OSError:
						if not tf.io.gfile.isdir(dest_dir):raise
				tf.io.gfile.copy(src_file,dest_file,overwrite=_D)
def rehash_sparse_features_nbits(sp_a,nbits,hash_fn=multiplicative_hash):'\n  Rehash the feature ids of the sparse tensor,\n  and limit the output to n bits.\n\n  This is useful for making the distribution of\n  feature_ids more uniform, which may improve performance\n  in some situations.\n\n  This would typically be used on the output of\n  PercentileDiscretizer, since it assigns many\n  bins to low-valued output feature ids.\n\n  Input feature IDs should take values less than 2**32,\n  and nbits should be less than 32\n\n  Args:\n    sp_a:\n      a tf.SparseTensor object\n    nbits:\n      integer number of bits to mask output feature_ids\n    hash_fn:\n      Function that takes integer values and returns hashes of these values.\n      The output does not need to be masked to the desired number of bits,\n      as this masking will be taken care of. Default value = multiplicative_hash.\n\n  Returns:\n    a new tf.SparseTensor\n  ';feature_ids=sp_a.indices[:,1];feature_ids=hash_fn(feature_ids);sample_ids=sp_a.indices[:,0];values=sp_a.values;dense_shape=sp_a.dense_shape;indices=tf.stack([sample_ids,feature_ids],axis=1);sp_a=tf.SparseTensor(indices,values,dense_shape);return limit_sparse_tensor_size(sp_a,nbits)
def convert_to_hparams(opt):
	"\n  Converts argparse.Namespace object to twitter.deepbird.hparam.hparam.HParams.\n  Note that tensorflow.contrib.training.HParams is gone in TF 2.x, and we forward ported\n  tensorflow.contrib.training.HParams to twitter.deepbird.hparam.hapram.HParams.\n\n  NOTE: If you are using estimators, please don't call this method and directly pass python dict\n  to TensorFlow estimator. Starting TensorFlow 2.0, Estimator will only accept dicts.\n  "
	if isinstance(opt,argparse.Namespace):params_dict=vars(opt)
	elif isinstance(opt,dict):params_dict=opt
	elif isinstance(opt,HParams):logging.warning('If you are using Estimator, please pass python dict directly to Estimator.');params_dict=opt.values()
	else:raise ValueError('Input can not be of type %s. It can be one of { argparse.Namespace, dict, twitter.deepbird.hparam.HParams}.'%type(opt))
	params=HParams()
	for (key,val) in params_dict.items():
		val=params_dict[key]
		if isinstance(val,str):params.add_hparam(key,sanitize_hdfs_path(val))
		else:params.add_hparam(key,val)
	return params
def dynamic_partition(features,partitions,num_partitions=2,name=_A):
	'\n  Partitions each of the tensor in features using the provided mask.\n\n  Args:\n    features:\n      A single tensor or an iterable of tensors (list, tuple, dict)\n    partitions:\n      A bool or integer tensor representing the partitions.\n\n  Returns partitioned outputs as a list. Each element of the list is the same type as features.\n\n  This uses tf.dynamic_partition but adds the following niceties:\n    - features can be a list or dict of different tensor types.\n    - only a partition tensor is used to partition all the feature tensors recursively.\n    - the partition tensor is automatically converted into an integer tensor.\n    - defaults to num_partitions == 2\n  '
	if not isinstance(features,(dict,list,tuple,tf.Tensor)):raise AssertionError('features container must be a dict, list, or tuple, tf.Tensor')
	if isinstance(partitions,tf.Tensor):partitions=tf.cast(partitions,tf.int32)
	if isinstance(features,tf.Tensor):return tf.dynamic_partition(features,partitions,num_partitions,name)
	outputs=[]
	for _ in range(num_partitions):
		if isinstance(features,(tuple,list)):outputs.append([_A for _ in range(len(features))])
		else:outputs.append(dict())
	iterable=features.items()if isinstance(features,dict)else enumerate(features)
	for (key,feature) in iterable:
		name_key=_A if name is _A else name+'_'+str(key)
		if isinstance(partitions,tf.Tensor):results=tf.dynamic_partition(feature,partitions,num_partitions,name_key)
		else:results=tf.dynamic_partition(feature,partitions[key],num_partitions[key],name_key)
		for (idx,result) in enumerate(results):outputs[idx][key]=result
	if isinstance(features,tuple):outputs=[type(features)(output)for output in outputs]
	return outputs
def write_file(filename,contents,encode=_B):
	"\n  Optionally encodes contents and writes contents to a file.\n\n  Arguments:\n    filename:\n      path to file where the contents will be saved.\n      Accepts HDFS and local paths.\n    contents:\n      contents to save to the file.\n      Must be a string when encode is False.\n    encode:\n      False | 'json'. When encode='json', contents is encoded\n      with json.dumps.\n  "
	if encode=='json':contents=json.dumps(contents)
	elif not is_string(contents):raise ValueError('Expecting string for encode=False')
	graph=tf.Graph()
	with graph.as_default():write=tf.write_file(filename,contents)
	with tf.Session(graph=graph)as sess:sess.run(write)
def read_file(filename,decode=_B):
	"\n  Reads contents from a file and optionally decodes it.\n\n  Arguments:\n    filename:\n      path to file where the contents will be loaded from.\n      Accepts HDFS and local paths.\n    decode:\n      False | 'json'. When decode='json', contents is decoded\n      with json.loads. When False, contents is returned as is.\n\n  Returns:\n    contents\n  ";graph=tf.Graph()
	with graph.as_default():read=tf.read_file(filename)
	with tf.Session(graph=graph)as sess:
		contents=sess.run(read)
		if not isinstance(contents,str):contents=contents.decode()
	if decode=='json':contents=json.loads(contents)
	return contents
def setup_tf_logging_formatter():
	A='info';formatter=_logging.Formatter('%(asctime)s [%(levelname)s] %(name)s: %(message)s',_A);logging.set_verbosity(A);logging.set_stderrthreshold(A);logging.get_absl_handler().setFormatter(formatter);tf.logging.set_verbosity(tf.logging.INFO)
	if len(tf_logging.get_logger().handlers)>0:tf_logging.get_logger().handlers[0].setFormatter(formatter)
def set_tensorflow_log_level(log_level):"\n  Sets tensorflow's default logging level.\n\n  0. all logs are shown.\n  1. filter out INFO logs.\n  2. filter out WARNINGs and INFOs.\n  3. filter out ERRORs, WARNINGs, and INFOs.\n\n  Note that tf.Print output are INFO logs, so setting log_level above 0 would hide\n  output from tf.Print.\n  ";assert isinstance(log_level,int)and log_level>=0 and log_level<=3;os.environ['TF_CPP_MIN_LOG_LEVEL']=str(log_level)
def weighted_average(values,weights):'\n  Compute a weighted average using the given values and weights.\n  E.g. this is usually used to compute a weighted loss given sample weights.\n  ';return tf.reduce_sum(tf.multiply(values,weights))/tf.reduce_sum(weights)
def backup_checkpoint(checkpoint_path_prefix,backup_path='backup',empty_backup=_D):
	'\n  Creates a backup copy of a checkpoint in backup_dir.\n  This function is used by the Trainer for early-stopping.\n\n  Arguments:\n    checkpoint_path_prefix:\n      Prefix of the path to the checkpoint files.\n    backup_path:\n      path to a directory where checkpoint files will be backed up.\n    empty_backup:\n      When True (the default), the current contents of the backup directory\n      are removed before the backup is performed.\n\n  Returns:\n    The number of backed up files.\n  ';checkpoint_file_prefix=os.path.basename(checkpoint_path_prefix)
	if tf.io.gfile.exists(backup_path)and empty_backup:tf.io.gfile.rmtree(backup_path)
	tf.io.gfile.mkdir(backup_path);n_backup=0
	try:
		checkpoint_files=tf.io.gfile.glob(checkpoint_path_prefix+'*')
		if len(checkpoint_files)==0:raise twml.errors.CheckpointNotFoundError('%s not found'%checkpoint_path_prefix)
		for filename in checkpoint_files:n_backup+=1;tf.io.gfile.copy(src=filename,dst=os.path.join(backup_path,os.path.basename(filename)))
	except tf.errors.OpError as ex:raise twml.errors.CheckpointNotFoundError(f"{str(ex)}\n {checkpoint_path_prefix} not found.")
	with tf.io.gfile.GFile(os.path.join(backup_path,'checkpoint'),'w')as f:f.write('model_checkpoint_path: "%s"\n'%checkpoint_file_prefix)
	return n_backup
def set_only_checkpoint(source_path,dest_path,remove_source=_D):
	'\n  Removes the checkpoint and model.ckpt* files from dest_path.\n  Moves the latest checkpoint from source_path to dest_path.\n\n  Arguments:\n    source_path:\n      path to directory containing the latest checkpoint.\n      Should contain a valid checkpoint file and model.ckpt files.\n      For early-stopping, this should be the save_dir/best_checkpoint dir.\n    dest_path:\n      path to directory where the latest checkpoint files will be moved.\n      All its checkpoint and model.ckpt* files will be removed.\n      For early-stopping, this should be the save_dir.\n    remove_source:\n      When True (the default), deletes the source directory.\n      Note that even when False, its checkpoint files are moved to\n      dest_path anyway.\n      This deletes the source directory (and any remaining contents).\n  ';source_path_prefix=tf.train.latest_checkpoint(source_path)
	if source_path_prefix is not _A:
		for filename in tf.io.gfile.listdir(dest_path):
			if filename.startswith('model.ckpt'):tf.io.gfile.Remove(os.path.join(dest_path,filename))
		for filename in tf.io.gfile.listdir(source_path):tf.io.gfile.rename(oldname=os.path.join(source_path,filename),newname=os.path.join(dest_path,filename),overwrite=_D)
		if remove_source:tf.io.gfile.rmtree(source_path)
def list_files_by_datetime(base_path,start_datetime,end_datetime=_A,datetime_prefix_format='%Y/%m/%d/%H',extension='lzo',parallelism=1,hour_resolution=1,sort=_B):
	"List files matching `base_path/dt_prefix_format/*.extension` for the requested datetime range.\n\n  Args:\n    base_path:\n      The base path. If `None`, returns `None`.\n    start_datetime:\n      A `datetime.datetime` or string representing the start of the range (inclusive).\n      If `None`, it returns `list_files(base_path, extension, sort)`.\n    end_datetime:\n      A `datetime.datetime` or string representing the end of the range (inclusive).\n      If `None`, assumed to be the same as start_datetime.\n    datetime_prefix_format:\n      Format compatible with `datetime.datetime.strftime`\n      (https://docs.python.org/2/library/datetime.html#strftime-and-strptime-behavior).\n    extension:\n      The extension of the files composing the dataset (e.g. 'lzo').\n    parallelism:\n      The number of threads used to process list patterns (this is mostly useful\n      when dealing with filesystems such as HDFS in which listing files is a potentially expensive\n      operation).\n    hour_resolution:\n      The separation between consecutive hours. The default value is 1.\n    sort:\n      bool, whether to return a sorted list of files. Default False.\n\n  Returns:\n    A list with all the matching files.\n\n  Raises:\n    errors.OpError: If there are filesystem / directory listing errors.\n  "
	if hour_resolution is _A:hour_resolution=1
	if base_path is _A:return _A
	if start_datetime is _A:return list_files(base_path,extension,sort)
	if end_datetime is _A:end_datetime=start_datetime
	assert parallelism>0;assert start_datetime<=end_datetime
	if isinstance(start_datetime,str):start_datetime=datetime.strptime(start_datetime,datetime_prefix_format)
	if isinstance(end_datetime,str):end_datetime=datetime.strptime(end_datetime,datetime_prefix_format)
	assert isinstance(start_datetime,datetime);assert isinstance(end_datetime,datetime);base_path=preprocess_path(base_path)
	def _handle_missing_globs(pattern):
		try:return tf.io.gfile.glob(pattern)
		except tf.errors.NotFoundError as e:tf.logging.warning(e.message);return[]
	globs={os.path.join(base_path,dt.strftime(datetime_prefix_format),'*.%s'%extension)for dt in rrule.rrule(freq=rrule.HOURLY,interval=hour_resolution,dtstart=start_datetime,until=end_datetime)};nested_files=Parallel(n_jobs=parallelism,backend='threading')((delayed(_handle_missing_globs)(p)for p in globs));flattened_files=list(itertools.chain.from_iterable(nested_files))
	if not flattened_files:error_msg='Files list is empty: base_path={base_path}, start_datetime={start_datetime}, end_datetime={end_datetime}'.format(base_path=base_path,start_datetime=start_datetime,end_datetime=end_datetime);raise OSError(error_msg)
	if sort:flattened_files=sorted(flattened_files)
	return flattened_files
def limit_sparse_tensor_size(sparse_tf,input_size_bits,mask_indices=_D):
	'\n  Returns a ``tf.SparseTensor`` which is the input SparseTensor\n  limited to the specified input_size_bits\n\n  Args:\n    sparse_tf:\n      twml.SparseTensor or tf.SparseTensor\n    input_size_bits:\n      The number of bits allocated to the input size.\n      Input size will be power(2,input_size_bits).\n      Note that twml.limit_bits truncates any feature keys that\n      exceed the input size.\n    mask_indices:\n      If mask indices is False; only the shape is changed. Defaults to True.\n  '
	if isinstance(sparse_tf,twml.SparseTensor):sparse_tf=sparse_tf.to_tf()
	if not isinstance(sparse_tf,tf.SparseTensor):raise TypeError('Input argument `sparse_tf` should either be of typetwml.SparseTensor of tf.SparseTensor. Found type: {}'.format(type(sparse_tf)))
	if mask_indices:indices=twml.limit_bits(sparse_tf.indices,input_size_bits)
	else:indices=sparse_tf.indices
	dense_shape=tf.stack([sparse_tf.dense_shape[0],1<<input_size_bits]);return tf.SparseTensor(indices=indices,values=sparse_tf.values,dense_shape=dense_shape)
def create_module_spec(mlp_fn,mode,params,drop_collections=_A):'\n  Creates a standard tags_and_args which should be passed to the create_module_spec\n  spec = hub.create_module_spec(mlp_fn, tags_and_args=tags_and_args).\n\n  Args:\n    module_fn:\n      a function to build a graph for the Module.\n    mode:\n      mode in which the Estimator is run\n    params:\n      parameters passed to the Estimator\n  ';B='mode';A='params';import tensorflow_hub as hub;tags_and_args=[(set(),{A:params,B:mode}),({'train'},{A:params,B:mode})];spec=hub.create_module_spec(mlp_fn,tags_and_args=tags_and_args,drop_collections=drop_collections);return spec
def change_name_scope_from_dir(init_scope_name,final_scope_name,save_dir):
	"\n  Changes the name of the saved scope to the desired name and saves it\n  to the same save_dir.\n\n  Args:\n    init_scope_name:\n      initial scope name\n    final_scope_name:\n      desired (final) scope name\n    save_dir:\n      directory which the scopes are saved\n\n  In the follwing section we:\n    - Read all the variables from the latest checkpoint.\n    - Make a copy of the variables with new name scope.\n    - Store both sets of variables into the latest checkpoint.\n  This essentially doubles up the size of the checkpoint.\n  But when a job is restarted after this part is done, the checkpoint size doubles again.\n  To avoid doing this, we create a copy in backup if a backup isn't found.\n  This allows us always read (from backup) and write same sized checkpoint files.\n  ";backup_dir=os.path.join(save_dir,'change_name_scope_backups');tf.io.gfile.makedirs(backup_dir);latest_checkpoint=tf.train.latest_checkpoint(save_dir)
	if latest_checkpoint is _A:raise OSError('No checkpoints found in save_dir: %s'%save_dir)
	latest_backup_checkpoint=tf.train.latest_checkpoint(backup_dir)
	if latest_backup_checkpoint is _A or os.path.basename(latest_checkpoint)!=os.path.basename(latest_backup_checkpoint):backup_checkpoint(latest_checkpoint,backup_dir,empty_backup=_B)
	variables=tf.train.list_variables(backup_dir)
	with tf.Graph().as_default(),tf.Session().as_default()as sess:
		new_variables=[]
		for (name,_) in variables:var=tf.train.load_variable(backup_dir,name);new_variables.append(tf.Variable(var,name=name.replace(init_scope_name,final_scope_name)));new_variables.append(tf.Variable(var,name=name))
		saver=tf.train.Saver(new_variables);sess.run(tf.global_variables_initializer());saver.save(sess,latest_checkpoint)
def hub_import(input,module,module_name,trainable=_B):'\n  Loads exported hub module.\n\n  Args:\n    input:\n      input to hub module\n    module:\n      module path\n    module_name:\n      signature of the exported hub module\n  ';import tensorflow_hub as hub;hub_module=hub.Module(module,trainable=trainable);output=hub_module(input,signature=module_name);return output
def _extract_hash_space_bits(feature_config):
	'\n  Extract Sparse Shapes for contrib.FeatureConfig.\n  Arguments:\n    feature_config:\n      Feature Configuration of the type contrib.FeatureConfig\n  Returns:\n    Dictionary of tensor names and hash space bits.\n  '
	if not isinstance(feature_config,twml.contrib.feature_config.FeatureConfig):fc_type=type(feature_config);raise TypeError(f"Feature config must be of type contrib.FeatureConfig: {fc_type}")
	sparse_shapes_dict={}
	for config in feature_config.sparse_extraction_configs:sparse_shapes_dict[config.output_name]=config.hash_space_bits
	return sparse_shapes_dict
def fix_shape_sparse(features,feature_config):
	'\n  Modifies the shape of features which are extracted using the hashing trick.\n  Features itself is changed by this function.\n  Arguments:\n    features:\n      Feature dictionary extracted by the feature config\n    feature_config:\n      Feature Configuration of the type contrib.FeatureConfig\n  '
	if not isinstance(feature_config,twml.contrib.feature_config.FeatureConfig):raise TypeError(f"Feature config must be of type contrib.FeatureConfig, currently of {type(feature_config)}")
	sparse_shape=_extract_hash_space_bits(feature_config)
	if not isinstance(features,dict):raise TypeError(f"features must be of dictionary type, it is of {type(features)} type")
	for key in set(features)&set(sparse_shape):features[key]=limit_sparse_tensor_size(features[key],sparse_shape[key],mask_indices=_B)
def touch_file_in_dir(directory,filename):
	'\n  Creates a file named filename in directory.\n\n  Arguments:\n    filename: (str)\n    directory: (str)\n  ';file_path=os.path.join(directory,filename)
	with tf.io.gfile.GFile(file_path,'w')as f:f.write('')
def file_exist_in_dir(directory,filename):file_path=os.path.join(directory,filename);return tf.io.gfile.exists(file_path)
def copy_to_local(remote,local,filename,overwrite=_B):'Function to file from remote directory to local directory.';assert'hdfs://'not in local;tf.io.gfile.makedirs(local);return tf.io.gfile.copy(os.path.join(remote,filename),os.path.join(local,filename),overwrite=overwrite)
def copy_recursive(src,dst,overwrite=_B):
	'\n  Function to copy a directory recursively.\n\n  Arguments:\n    src: Source directory.\n    dst: Destination directory.\n    overwrite: Specifies if files are to be overwritten if they exist.\n  ';src=src.rstrip(_C);dst=dst.rstrip(_C)
	for (dirname,subdirs,files) in tf.io.gfile.walk(src):
		dst_dirname=dirname.replace(src,dst);tf.io.gfile.makedirs(dst_dirname)
		for f in files:src_f=os.path.join(dirname,f);dst_f=os.path.join(dst_dirname,f);tf.logging.info(f"Copying {src_f} to {dst_f}");tf.io.gfile.copy(src_f,dst_f,overwrite=overwrite)
def delete_file_or_dir(path):
	'\n  Delete the file or directory given by `path`\n  Arguments:\n    path:\n      string indicating path of file or directory to remove\n  '
	if tf.io.gfile.isdir(path):tf.io.gfile.rmtree(path)
	else:tf.io.gfile.remove(path)
def get_distributed_training_job_path():"\n  Function to get distributed training job path.\n  Note: distributed training has three jobs, one parameter server job,\n  one worker job and one evaluator job. All of these three jobs' name\n  share a common base job name.\n  ";job_path=AuroraPath(dc=os.environ.get('TWML_JOB_CLUSTER'),role=os.environ.get('TWML_JOB_ROLE'),env=os.environ.get('TWML_JOB_ENV'),job_name=os.environ.get('TWML_DISTRIBUTED_BASE_JOBNAME'));return job_path
def do_every_n_steps(action,num_steps):'\n  Execute a sequence of TensorFlow operations only once in a while.\n  Specifically, `action` is performed if `global_step` is a\n    multiple of `num_steps`\n\n  Args:\n    action: callable to be performed at regular intervals. This callable\n      must return a TF op with no output tensors.\n    num_steps: period of performing the action, as measured\n      in number of training steps\n\n  Returns:\n    A TensorFlow op with no output tensors, like a tf.print() or tf.no_op().\n    You must use tf.control_dependencies() to execute the op.\n\n  ';global_step=tf.train.get_or_create_global_step();condition=tf.math.equal(tf.math.floormod(global_step,num_steps),0);return tf.cond(condition,action,lambda:tf.no_op())