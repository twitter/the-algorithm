'\nThis module implements custom tf.data.datasets for twml.\n'
_C=False
_B=True
_A=None
import numbers
from absl import logging
from kazoo.client import KazooClient
from libtwml import OPLIB
import tensorflow.compat.v1 as tf
from twml.constants import DEFAULT_ZOOKEEPER_BASE_ZNODE,DEFAULT_ZOOKEEPER_HOST
class BlockFormatDataset(tf.data.Dataset):
	'A ``tf.data.Dataset`` comprising records from one or more TFRecord files.'
	def __init__(A,filenames,compression_type='auto',buffer_size=1<<20):"\n    Creates a ``BlockFormatDataset``.\n\n    Args:\n      filenames:\n        A `tf.string` tensor containing one or more filenames.\n      compression_type:\n        A string specifying the compression type.\n        Can be one of 'gz' (or 'gzip'), 'none', 'auto' (default).\n        When compression_type == 'auto', it is inferred from file extension.\n      buffer_size:\n        Buffer size to be used during decompression. default: 1<<20.\n    ";A._filenames=tf.convert_to_tensor(filenames,dtype=tf.string,name='filenames');A._compression_type=tf.convert_to_tensor(compression_type.lower(),name='compression_type');A._buffer_size=tf.convert_to_tensor(buffer_size,dtype=tf.int64,name='buffer_size');super(BlockFormatDataset,A).__init__()
	def _as_variant_tensor(A):
		'\n    Create the resource handle for the dataset.\n    '
		try:B=__import__('libtwml_internal').OPLIB.block_format_dataset;return B(A._filenames)
		except ImportError:B=OPLIB.block_format_dataset_v2;return B(A._filenames,A._compression_type,A._buffer_size)
	def _inputs(A):return[]
	@property
	def output_shapes(self):'Return output shapes';return tf.TensorShape([])
	@property
	def output_types(self):'Return output types';return tf.string
	@property
	def output_classes(self):'Return output classes';return tf.Tensor
def downsample_dataset(dataset,sample_rate,rate_name):
	'\n  Downsample a tf.data.Dataset at sample_rate\n  ';B=rate_name;C=dataset;A=sample_rate
	if A is _A or A==1.0:return C
	elif not isinstance(A,numbers.Real):raise TypeError('dataset %s must be a real number'%B)
	elif A<=0 or A>1:raise ValueError('dataset %s must be in range (0, 1])'%B)
	return C.filter(lambda _:tf.squeeze(tf.random_uniform([1]))<A)
def _filenames_dataset(files,shards=_A,shard_index=_A):
	'\n  Get a tf.data.Dataset with file names from a list of files\n  Optionally shard the file list (see stream_block_format_dataset)\n  ';B=shard_index;C=shards;A=files;A=tf.data.Dataset.from_tensor_slices(A)
	if[C,B]!=[_A,_A]:logging.info('Sharding files dataset (index: %d, shards: %d)'%(B,C));A=A.shard(num_shards=C,index=B)
	return A
def stream_block_format_dataset(files,parse_fn,batch_size,num_threads,shuffle=_B,repeat=_C,block_length=_A,part_file_parallelism=_A,file_shuffle_size=_A,record_shuffle_size=_A,dataset_fn=_A,keep_rate=_A,parts_downsampling_rate=_A,prefetch_size=2,shards=_A,shard_index=_A,shuffle_files=_B,interleave=_B):
	'\n  Helper function to stream a list of part files.\n\n  Args:\n    files:\n      List of input files which will create a dataset.\n    parse_fn:\n      A function that takes a byte tensor containing a datarecord and decodes it.\n    batch_size:\n      The batch size for each step.\n    num_threads:\n      Number of threads working on the data in parallel.\n    shuffle:\n      Shuffle records within each file using ``record_shuffle_size``. Defaults to True.\n    repeat:\n      Repeat the dataset indefinitely. Defaults to False.\n      Useful when you want to use an ``[train,eval]_steps`` greater than the size of the dataset\n      (otherwise ``Estimator.[train,evaluate]`` stop when the end of the dataset is reached).\n    block_length (optional):\n      Number of consecutive records to pull from a single part file.\n      Defaults to batch_size.\n    part_file_parallelism (optional):\n      Number of part files to read from in parallel. Once a part file is completely read, it will\n      be replaced by the next part file in the part file list.\n\n      ``num_threads`` specifies a reader thread pool size, while ``part_file_parallelism`` specifies\n      the number of files to read from in parallel. If ``part_file_parallelism`` is greater than or\n      equal to ``num_threads``, the reads will be distributed over ``num_threads``. On the other hand,\n      if ``part_file_parallelism`` is smaller than``num_threads``, it is very likely that the reader\n      thread pool will be underutilized, since it can never be the case that every reader thread has\n      a part file to read from.\n\n    file_shuffle_size (optional):\n      the buffer_size used for shuffling of the list of files.\n      Defaults to 1000. For example, if you have 2000 files, the first\n      1000 files are shuffled together, iterated through, then the next 1000 files are shuffled\n      and iterated through.\n    record_shuffle_size (optional):\n      the ``buffer_size`` used for shuffling records in each thread.\n      Defaults to ``batch_size * 8`` records.\n    dataset_fn (optional):\n      A function of that modifies the dataset after it reads different interleaved parts files.\n      Defaults to:\n\n      .. code-block:: python\n\n        def dataset_fn(dataset, parse_fn, batch_size):\n          return dataset.batch(batch_size).map(parse_fn, 1)\n\n    keep_rate (optional):\n      A float value in (0.0, 1.0] that indicates to drop records according to the Bernoulli\n      distribution with p = 1 - keep_rate.\n      Defaults to None (no records dropped).\n\n    parts_downsampling_rate (optional):\n      A float value in ``(0.0, 1.0]`` that indicates the factor by which to downsample part files.\n      For example, a value of 0.2 means only 20 percent of part files become part of the dataset.\n\n      Note that this argument is only useful in conjunction with a [train,eval]_steps of -1\n      (that is, when the entire dataset is used). Furthermore, note that even in this case, each\n      epoch will see a different set of part files. This is because new part files are re-sampled\n      every epoch. In other words, this argument is only provided for backwards compatibility with\n      DeepBird v1. We recommend you use a smaller [train,eval]_steps (or specify a keep_rate)\n      instead.\n\n    shards (optional):\n      Number of partitions to shard the dataset into. This is useful for codistillation and other\n      techniques that require each worker to train on disjoint partitions of the dataset.\n      The dataset is not sharded by default.\n\n    shard_index (optional):\n      Which partition of the dataset to use if ``shards`` is set.\n\n    shuffle_files (optional):\n      Shuffle the list of files. Defaults to True.\n      When False, files are iterated in the order they are passed in.\n\n    interleave (optional):\n      Interleave records from multiple files in parallel. Defaults to True.\n\n  Returns:\n    tf.data.DataSet of batches of HashedDataRecord resource handles decoded and streamed online.\n  ';I=dataset_fn;J=parse_fn;H=num_threads;C=record_shuffle_size;D=file_shuffle_size;E=part_file_parallelism;F=block_length;G=batch_size;A=files;A=_filenames_dataset(A,shards=shards,shard_index=shard_index);D=D if D is not _A else 100000;C=C if C is not _A else G*8;F=F if F is not _A else G;logging.info('NUM_THREADS: %d',H)
	if repeat:A=A.repeat()
	if shuffle_files:A=A.shuffle(buffer_size=D)
	A=downsample_dataset(A,parts_downsampling_rate,'parts_downsampling_rate')
	def K(filenames):
		'function that maps each filename to a BlockFormatDataset';A=BlockFormatDataset(filenames);A=A.prefetch(tf.data.experimental.AUTOTUNE)
		if shuffle:A=A.shuffle(buffer_size=C)
		return A
	if interleave:E=H if E is _A else E;B=A.interleave(K,cycle_length=E,block_length=F,num_parallel_calls=H)
	else:B=A.flat_map(K)
	B=downsample_dataset(B,keep_rate,'keep_rate')
	if I is _A:return B.batch(G).map(J,num_parallel_calls=tf.data.experimental.AUTOTUNE).prefetch(prefetch_size)
	return I(B,J,G)
def cx_zk_path(path):
	if path is _A:raise ValueError('Path for zookeeper dataset pointer is None. You must specify a path.')
	A='/'.join([DEFAULT_ZOOKEEPER_BASE_ZNODE,path]);logging.info('Zookeeper path is: {}'.format(A));return A
def zookeeper_ordered_dataset(files,parse_fn,batch_size,zk_counter_path,repeat=_C,num_threads=2,block_length=_A,part_file_parallelism=_A,batch_shuffle_size=_A,file_keep_rate=_A,record_keep_rate=_A,prefetch_size=2,interleave=_C,dataset_fn=_A,verbose=_C):
	"\n  Make a tf.Dataset given an ordered list of filenames, using Zookeeper to keep track of\n  which file to read, and to coordinate multiple workers.\n\n  Args:\n    files:\n      ordered list of (typically HDFS) filenames. This must remain consistent\n      between different workers, and between worker restarts (e.g. in the case\n      of instance failure or preemption).\n      To ensure this remains consistent, consider using the --train.files_list\n      option from DataRecordTrainer.\n    parse_fn:\n      A function that takes a byte tensor containing a datarecord and decodes it.\n    batch_size:\n      The batch size for each step.\n    zk_counter_path:\n      Path under the root node for the underlying zookeeper shared counter that\n      is used to coordinate distributed iteration over the list of files.\n      Full path will be `'/'.join([DEFAULT_ZOOKEEPER_BASE_ZNODE, zk_counter_path])`.\n    repeat:\n      Default False. Set True to repeat over the files forever.\n    num_threads:\n      Default 2. Number of threads working on the data in parallel.\n      Only used if interleave=True.\n    block_length:\n      Default None. Number of consecutive records to pull from a single part file.\n      If None, then block_length=batch_size will be used.\n      Only used if interleave=True.\n    part_file_parallelism:\n      Default None. Number of part files to read from in parallel. Once a part file is completely\n      read, it will be replaced by the next part file indicated by the zookeeper counter.\n      Only used if interleave=True.\n\n      ``num_threads`` specifies a reader thread pool size, while ``part_file_parallelism`` specifies\n      the number of files to read from in parallel. If ``part_file_parallelism`` is greater than or\n      equal to ``num_threads``, the reads will be distributed over ``num_threads``. On the other hand,\n      if ``part_file_parallelism`` is smaller than``num_threads``, it is very likely that the reader\n      thread pool will be underutilized, since it can never be the case that every reader thread has\n      a part file to read from.\n\n    batch_shuffle_size:\n      Default None. Size of shuffle buffer, for shuffling that will be applied after batching.\n      if None, then batches will not be shuffled. Ignored if dataset_fn is provided.\n    file_keep_rate:\n      Default None. Fraction of files to keep, or None to keep all files.\n    record_keep_rate:\n      Default None. Fraction of records to keep, or None to keep all records.\n    prefetch_size:\n      Default 2. Number of parsed batches to prefetch. Ignored if dataset_fn is provided.\n    interleave:\n      Default False. Set True to use tf.data.Dataset.interleave rather than flat_map.\n    dataset_fn:\n      A function that is applied to the dataset of individual records, after\n      these have been read from the parts files.\n      If ``None`` (the default), the behavior will be as though dataset_fn were set to:\n\n      .. code-block:: python\n\n        def dataset_fn(dataset, parse_fn, batch_size):\n          dataset = dataset.batch(batch_size)\n          dataset = dataset.map(parse_fn, tf.data.experimental.AUTOTUNE)\n          if batch_shuffle_size:\n            dataset = dataset.shuffle(batch_shuffle_size)\n          return dataset.prefetch(prefetch_size)\n\n    verbose:\n      Default False. Set True to log the names of files loaded by TF.\n  ";F=dataset_fn;G=batch_shuffle_size;H=num_threads;I=parse_fn;E=batch_size;C=part_file_parallelism;D=block_length;B=files;D=E if D is _A else D;C=H if C is _A else C
	def K(my_files=B):
		B=my_files;C=KazooClient(hosts=DEFAULT_ZOOKEEPER_HOST);C.start();D=C.Counter(cx_zk_path(zk_counter_path),default=0)
		while _B:
			D+=1;A=D.pre_value
			if repeat:A=A%len(B)
			if A>=len(B):break
			else:
				E=B[A]
				if verbose:logging.info('{}. yielding {}'.format(A,E))
				yield E
		C.stop()
	B=tf.data.Dataset.from_generator(K,tf.string);B=downsample_dataset(B,file_keep_rate,'file_keep_rate')
	def J(filenames):return BlockFormatDataset(filenames).prefetch(20)
	if interleave:A=B.interleave(J,cycle_length=C,block_length=D,num_parallel_calls=H)
	else:A=B.flat_map(J)
	A=downsample_dataset(A,record_keep_rate,'record_keep_rate')
	if F is _A:
		A=A.batch(E);A=A.map(I,num_parallel_calls=tf.data.experimental.AUTOTUNE)
		if G:A=A.shuffle(buffer_size=G)
		A=A.prefetch(prefetch_size)
	else:A=F(A,I,E)
	return A