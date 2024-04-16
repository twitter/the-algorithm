"""
T  module  mple nts custom tf.data.datasets for twml.
"""
 mport numbers

from absl  mport logg ng
from kazoo.cl ent  mport KazooCl ent
from l btwml  mport OPL B
 mport tensorflow.compat.v1 as tf
from twml.constants  mport DEFAULT_ZOOKEEPER_BASE_ZNODE, DEFAULT_ZOOKEEPER_HOST


class BlockFormatDataset(tf.data.Dataset):
  """A ``tf.data.Dataset`` compr s ng records from one or more TFRecord f les."""

  def __ n __(self, f lena s, compress on_type="auto", buffer_s ze=1 << 20):
    """
    Creates a ``BlockFormatDataset``.

    Args:
      f lena s:
        A `tf.str ng` tensor conta n ng one or more f lena s.
      compress on_type:
        A str ng spec fy ng t  compress on type.
        Can be one of 'gz' (or 'gz p'), 'none', 'auto' (default).
        W n compress on_type == 'auto',    s  nferred from f le extens on.
      buffer_s ze:
        Buffer s ze to be used dur ng decompress on. default: 1<<20.
    """
    self._f lena s = tf.convert_to_tensor(f lena s, dtype=tf.str ng, na ="f lena s")
    self._compress on_type = tf.convert_to_tensor(compress on_type.lo r(), na ="compress on_type")
    self._buffer_s ze = tf.convert_to_tensor(buffer_s ze, dtype=tf. nt64, na ="buffer_s ze")
    # Parent class calss self._as_var ant_tensor  n  n . So call t  at t  end.
    super(BlockFormatDataset, self).__ n __()

  def _as_var ant_tensor(self):
    """
    Create t  res ce handle for t  dataset.
    """
    try:
      block_format_dataset = __ mport__("l btwml_ nternal").OPL B.block_format_dataset
      return block_format_dataset(self._f lena s)
    except  mportError:
      block_format_dataset = OPL B.block_format_dataset_v2
      return block_format_dataset(self._f lena s, self._compress on_type, self._buffer_s ze)

  def _ nputs(self):
    return []

  @property
  def output_shapes(self):
    """Return output shapes"""
    return tf.TensorShape([])

  @property
  def output_types(self):
    """Return output types"""
    return tf.str ng

  @property
  def output_classes(self):
    """Return output classes"""
    return tf.Tensor


def downsample_dataset(dataset, sample_rate, rate_na ):
  """
  Downsample a tf.data.Dataset at sample_rate
  """
   f sample_rate  s None or sample_rate == 1.0:
    return dataset
  el f not  s nstance(sample_rate, numbers.Real):
    ra se TypeError("dataset %s must be a real number" % rate_na )
  el f sample_rate <= 0 or sample_rate > 1:
    ra se ValueError("dataset %s must be  n range (0, 1])" % rate_na )
  return dataset.f lter(lambda _: tf.squeeze(tf.random_un form([1])) < sample_rate)


def _f lena s_dataset(f les, shards=None, shard_ ndex=None):
  """
  Get a tf.data.Dataset w h f le na s from a l st of f les
  Opt onally shard t  f le l st (see stream_block_format_dataset)
  """
  f les = tf.data.Dataset.from_tensor_sl ces(f les)

   f [shards, shard_ ndex] != [None, None]:
    logg ng. nfo("Shard ng f les dataset ( ndex: %d, shards: %d)" % (shard_ ndex, shards))
    f les = f les.shard(num_shards=shards,  ndex=shard_ ndex)

  return f les


def stream_block_format_dataset(
        f les, parse_fn, batch_s ze, num_threads,
        shuffle=True, repeat=False,
        block_length=None, part_f le_parallel sm=None, f le_shuffle_s ze=None,
        record_shuffle_s ze=None, dataset_fn=None,
        keep_rate=None, parts_downsampl ng_rate=None, prefetch_s ze=2,
        shards=None, shard_ ndex=None, shuffle_f les=True,  nterleave=True):
  """
   lper funct on to stream a l st of part f les.

  Args:
    f les:
      L st of  nput f les wh ch w ll create a dataset.
    parse_fn:
      A funct on that takes a byte tensor conta n ng a datarecord and decodes  .
    batch_s ze:
      T  batch s ze for each step.
    num_threads:
      Number of threads work ng on t  data  n parallel.
    shuffle:
      Shuffle records w h n each f le us ng ``record_shuffle_s ze``. Defaults to True.
    repeat:
      Repeat t  dataset  ndef n ely. Defaults to False.
      Useful w n   want to use an ``[tra n,eval]_steps`` greater than t  s ze of t  dataset
      (ot rw se ``Est mator.[tra n,evaluate]`` stop w n t  end of t  dataset  s reac d).
    block_length (opt onal):
      Number of consecut ve records to pull from a s ngle part f le.
      Defaults to batch_s ze.
    part_f le_parallel sm (opt onal):
      Number of part f les to read from  n parallel. Once a part f le  s completely read,   w ll
      be replaced by t  next part f le  n t  part f le l st.

      ``num_threads`` spec f es a reader thread pool s ze, wh le ``part_f le_parallel sm`` spec f es
      t  number of f les to read from  n parallel.  f ``part_f le_parallel sm``  s greater than or
      equal to ``num_threads``, t  reads w ll be d str buted over ``num_threads``. On t  ot r hand,
       f ``part_f le_parallel sm``  s smaller than``num_threads``,    s very l kely that t  reader
      thread pool w ll be underut l zed, s nce   can never be t  case that every reader thread has
      a part f le to read from.

    f le_shuffle_s ze (opt onal):
      t  buffer_s ze used for shuffl ng of t  l st of f les.
      Defaults to 1000. For example,  f   have 2000 f les, t  f rst
      1000 f les are shuffled toget r,  erated through, t n t  next 1000 f les are shuffled
      and  erated through.
    record_shuffle_s ze (opt onal):
      t  ``buffer_s ze`` used for shuffl ng records  n each thread.
      Defaults to ``batch_s ze * 8`` records.
    dataset_fn (opt onal):
      A funct on of that mod f es t  dataset after   reads d fferent  nterleaved parts f les.
      Defaults to:

      .. code-block:: python

        def dataset_fn(dataset, parse_fn, batch_s ze):
          return dataset.batch(batch_s ze).map(parse_fn, 1)

    keep_rate (opt onal):
      A float value  n (0.0, 1.0] that  nd cates to drop records accord ng to t  Bernoull 
      d str but on w h p = 1 - keep_rate.
      Defaults to None (no records dropped).

    parts_downsampl ng_rate (opt onal):
      A float value  n ``(0.0, 1.0]`` that  nd cates t  factor by wh ch to downsample part f les.
      For example, a value of 0.2  ans only 20 percent of part f les beco  part of t  dataset.

      Note that t  argu nt  s only useful  n conjunct on w h a [tra n,eval]_steps of -1
      (that  s, w n t  ent re dataset  s used). Furt rmore, note that even  n t  case, each
      epoch w ll see a d fferent set of part f les. T   s because new part f les are re-sampled
      every epoch.  n ot r words, t  argu nt  s only prov ded for backwards compat b l y w h
      DeepB rd v1.   recom nd   use a smaller [tra n,eval]_steps (or spec fy a keep_rate)
       nstead.

    shards (opt onal):
      Number of part  ons to shard t  dataset  nto. T   s useful for cod st llat on and ot r
      techn ques that requ re each worker to tra n on d sjo nt part  ons of t  dataset.
      T  dataset  s not sharded by default.

    shard_ ndex (opt onal):
      Wh ch part  on of t  dataset to use  f ``shards``  s set.

    shuffle_f les (opt onal):
      Shuffle t  l st of f les. Defaults to True.
      W n False, f les are  erated  n t  order t y are passed  n.

     nterleave (opt onal):
       nterleave records from mult ple f les  n parallel. Defaults to True.

  Returns:
    tf.data.DataSet of batc s of Has dDataRecord res ce handles decoded and strea d onl ne.
  """
  # Creat ng a dataset from an  nput d rectory

  f les = _f lena s_dataset(f les, shards=shards, shard_ ndex=shard_ ndex)

  f le_shuffle_s ze = f le_shuffle_s ze  f f le_shuffle_s ze  s not None else 100000
  record_shuffle_s ze = record_shuffle_s ze  f record_shuffle_s ze  s not None else (batch_s ze * 8)
  block_length = block_length  f block_length  s not None else batch_s ze

  logg ng. nfo("NUM_THREADS: %d", num_threads)

   f repeat:
    f les = f les.repeat()

   f shuffle_f les:
    # Randomly shuffle t  f les l st.
    f les = f les.shuffle(buffer_s ze=f le_shuffle_s ze)

  # Downsample parts f les
  f les = downsample_dataset(f les, parts_downsampl ng_rate, "parts_downsampl ng_rate")

  #  nterleave t  result from BlockFormatDataset
  # block_length == batch_s ze results  n batch_s ze records be ng read from a s ngle f le.
  def map_fn(f lena s):
    '''funct on that maps each f lena  to a BlockFormatDataset'''
    # reach each f le us ng BlockFormatDataset
    dataset = BlockFormatDataset(f lena s)

    # early prefetch ng can so t  s  mprove performance (l ke on GCS)
    dataset = dataset.prefetch(tf.data.exper  ntal.AUTOTUNE)

    # Shuffl ng before repeat ng ensures strong order ng.
     f shuffle:
      dataset = dataset.shuffle(buffer_s ze=record_shuffle_s ze)

    return dataset

   f  nterleave:
    part_f le_parallel sm = num_threads  f part_f le_parallel sm  s None else part_f le_parallel sm
    dataset = f les. nterleave(
      map_fn, cycle_length=part_f le_parallel sm, block_length=block_length, num_parallel_calls=num_threads)
  else:
    dataset = f les.flat_map(map_fn)

  # Downsample DataRecords
  dataset = downsample_dataset(dataset, keep_rate, "keep_rate")

   f dataset_fn  s None:
    # Create a batch of datarecords and decode t m
    return dataset.batch(batch_s ze).map(parse_fn, num_parallel_calls=tf.data.exper  ntal.AUTOTUNE).prefetch(prefetch_s ze)

  return dataset_fn(dataset, parse_fn, batch_s ze)


def cx_zk_path(path):
   f path  s None:
    ra se ValueError("Path for zookeeper dataset po nter  s None.   must spec fy a path.")
  return_path = "/".jo n([DEFAULT_ZOOKEEPER_BASE_ZNODE, path])
  logg ng. nfo("Zookeeper path  s: {}".format(return_path))
  return return_path


def zookeeper_ordered_dataset(
        f les, parse_fn, batch_s ze, zk_counter_path, repeat=False,
        num_threads=2, block_length=None, part_f le_parallel sm=None,
        batch_shuffle_s ze=None, f le_keep_rate=None, record_keep_rate=None,
        prefetch_s ze=2,  nterleave=False, dataset_fn=None, verbose=False):
  """
  Make a tf.Dataset g ven an ordered l st of f lena s, us ng Zookeeper to keep track of
  wh ch f le to read, and to coord nate mult ple workers.

  Args:
    f les:
      ordered l st of (typ cally HDFS) f lena s. T  must rema n cons stent
      bet en d fferent workers, and bet en worker restarts (e.g.  n t  case
      of  nstance fa lure or preempt on).
      To ensure t  rema ns cons stent, cons der us ng t  --tra n.f les_l st
      opt on from DataRecordTra ner.
    parse_fn:
      A funct on that takes a byte tensor conta n ng a datarecord and decodes  .
    batch_s ze:
      T  batch s ze for each step.
    zk_counter_path:
      Path under t  root node for t  underly ng zookeeper shared counter that
       s used to coord nate d str buted  erat on over t  l st of f les.
      Full path w ll be `'/'.jo n([DEFAULT_ZOOKEEPER_BASE_ZNODE, zk_counter_path])`.
    repeat:
      Default False. Set True to repeat over t  f les forever.
    num_threads:
      Default 2. Number of threads work ng on t  data  n parallel.
      Only used  f  nterleave=True.
    block_length:
      Default None. Number of consecut ve records to pull from a s ngle part f le.
       f None, t n block_length=batch_s ze w ll be used.
      Only used  f  nterleave=True.
    part_f le_parallel sm:
      Default None. Number of part f les to read from  n parallel. Once a part f le  s completely
      read,   w ll be replaced by t  next part f le  nd cated by t  zookeeper counter.
      Only used  f  nterleave=True.

      ``num_threads`` spec f es a reader thread pool s ze, wh le ``part_f le_parallel sm`` spec f es
      t  number of f les to read from  n parallel.  f ``part_f le_parallel sm``  s greater than or
      equal to ``num_threads``, t  reads w ll be d str buted over ``num_threads``. On t  ot r hand,
       f ``part_f le_parallel sm``  s smaller than``num_threads``,    s very l kely that t  reader
      thread pool w ll be underut l zed, s nce   can never be t  case that every reader thread has
      a part f le to read from.

    batch_shuffle_s ze:
      Default None. S ze of shuffle buffer, for shuffl ng that w ll be appl ed after batch ng.
       f None, t n batc s w ll not be shuffled.  gnored  f dataset_fn  s prov ded.
    f le_keep_rate:
      Default None. Fract on of f les to keep, or None to keep all f les.
    record_keep_rate:
      Default None. Fract on of records to keep, or None to keep all records.
    prefetch_s ze:
      Default 2. Number of parsed batc s to prefetch.  gnored  f dataset_fn  s prov ded.
     nterleave:
      Default False. Set True to use tf.data.Dataset. nterleave rat r than flat_map.
    dataset_fn:
      A funct on that  s appl ed to t  dataset of  nd v dual records, after
      t se have been read from t  parts f les.
       f ``None`` (t  default), t  behav or w ll be as though dataset_fn  re set to:

      .. code-block:: python

        def dataset_fn(dataset, parse_fn, batch_s ze):
          dataset = dataset.batch(batch_s ze)
          dataset = dataset.map(parse_fn, tf.data.exper  ntal.AUTOTUNE)
           f batch_shuffle_s ze:
            dataset = dataset.shuffle(batch_shuffle_s ze)
          return dataset.prefetch(prefetch_s ze)

    verbose:
      Default False. Set True to log t  na s of f les loaded by TF.
  """
  block_length = batch_s ze  f block_length  s None else block_length
  part_f le_parallel sm = num_threads  f part_f le_parallel sm  s None else part_f le_parallel sm

  def zk_ ndex_generator( _f les=f les):
    zk = KazooCl ent(hosts=DEFAULT_ZOOKEEPER_HOST)
    zk.start()
     _counter = zk.Counter(cx_zk_path(zk_counter_path), default=0)
    wh le True:
       _counter += 1
      counter_pre_value =  _counter.pre_value
       f repeat:
        counter_pre_value = counter_pre_value % len( _f les)
       f counter_pre_value >= len( _f les):
        break
      else:
        chosen_f le =  _f les[counter_pre_value]
         f verbose:
          logg ng. nfo("{}. y eld ng {}".format(counter_pre_value, chosen_f le))
        y eld chosen_f le
    zk.stop()

  f les = tf.data.Dataset.from_generator(zk_ ndex_generator, tf.str ng)

  # Downsample parts f les
  f les = downsample_dataset(f les, f le_keep_rate, "f le_keep_rate")

  def map_fn(f lena s):
    return BlockFormatDataset(f lena s).prefetch(20)

  # Dont  nterleave for sequent al tra n ng
   f  nterleave:
    dataset = f les. nterleave(
      map_fn,
      cycle_length=part_f le_parallel sm,
      block_length=block_length,
      num_parallel_calls=num_threads)
  else:
    dataset = f les.flat_map(map_fn)

  # Downsample DataRecords
  dataset = downsample_dataset(dataset, record_keep_rate, "record_keep_rate")

   f dataset_fn  s None:
    # Create a batch of datarecords and decode t m
    dataset = dataset.batch(batch_s ze)
    dataset = dataset.map(parse_fn, num_parallel_calls=tf.data.exper  ntal.AUTOTUNE)
    # shuffle after batch ng and pars ng for performance reasons
    # faster b/c 1 random select on  s made per batch rat r than per record
     f batch_shuffle_s ze:
      dataset = dataset.shuffle(buffer_s ze=batch_shuffle_s ze)
    dataset = dataset.prefetch(prefetch_s ze)

  else:
    dataset = dataset_fn(dataset, parse_fn, batch_s ze)

  return dataset
