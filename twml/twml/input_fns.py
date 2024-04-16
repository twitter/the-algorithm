'''
Conta ns  mple ntat ons of funct ons to read  nput data.
'''
from .dataset  mport stream_block_format_dataset

 mport tensorflow.compat.v1 as tf


def data_record_ nput_fn(
        f les, batch_s ze, parse_fn,
        num_threads=2, repeat=False, dataset_fn=None,
        keep_rate=None, parts_downsampl ng_rate=None,
        shards=None, shard_ ndex=None, shuffle=True, shuffle_f les=True,  nterleave=True,
         n  al zable=False, log_tf_data_summar es=False,
        **kwargs):
  """
  Returns a nested structure of tf.Tensors conta n ng t  next ele nt.
  Used by ``tra n_ nput_fn`` and ``eval_ nput_fn``  n DataRecordTra ner.
  By default, works w h DataRecord dataset for compressed part  on f les.

  Args:
    f les:
      L st of f les that w ll be parsed.
    batch_s ze:
      number of samples per batch.
    parse_fn:
      funct on passed to data load ng for pars ng  nd v dual data records.
      Usually one of t  decoder funct ons l ke ``parsers.get_sparse_parse_fn``.
    num_threads (opt onal):
      number of threads used for load ng data. Defaults to 2.
    repeat (opt onal):
      Repeat t  dataset  ndef n ely. Defaults to False.
      Useful w n   want to use ``tra n_steps`` or ``eval_steps``
      greater than t  s ze of t  dataset
      (ot rw se Est mator.[tra n,evaluate] stops w n t  end of t  dataset  s reac d).
    dataset_fn (opt onal):
      A funct on that mod f es t  dataset after   reads d fferent  nterleaved parts f les.
      Defaults to:

      .. code-block:: python

        def dataset_fn(dataset, parse_fn, batch_s ze):
          return dataset.batch(batch_s ze).map(parse_fn, 1)

    keep_rate (opt onal):
      A float value  n (0.0, 1.0] that  nd cates to drop records accord ng to t  Bernoull 
      d str but on w h p = 1 - keep_rate.
      Defaults to None (no records dropped).

    parts_downsampl ng_rate (opt onal):
      A float value  n (0.0, 1.0] that  nd cates t  factor by wh ch to downsample part f les.
      For example, a value of 0.2  ans only 20 percent of part f les beco  part of t  dataset.

    shards (opt onal):
      Number of part  ons to shard t  dataset  nto. T   s useful for cod st llat on
      (https://arx v.org/pdf/1804.03235.pdf) and ot r techn ques that requ re each worker to
      tra n on d sjo nt part  ons of t  dataset.
      T  dataset  s not sharded by default.

    shard_ ndex (opt onal):
      Wh ch part  on of t  dataset to use  f ``shards``  s set.

    shuffle (opt onal):
      W t r to shuffle t  records. Defaults to True.

    shuffle_f les (opt onal):
      Shuffle t  l st of f les. Defaults to True.
      W n False, f les are  erated  n t  order t y are passed  n.

     nterleave (opt onal):
       nterleave records from mult ple f les  n parallel. Defaults to True.

     n  al zable (opt onal):
      A boolean  nd cator. W n t  Dataset  erator depends on so  res ce, e.g. a HashTable or
      a Tensor,  .e.  's an  n  al zable  erator, set   to True. Ot rw se, default value (false)
       s used for most pla n  erators.

      log_tf_data_summar es (opt onal):
        A boolean  nd cator denot ng w t r to add a `tf.data.exper  ntal.StatsAggregator` to t 
        tf.data p pel ne. T  adds summar es of p pel ne ut l zat on and buffer s zes to t  output
        events f les. T  requ res that ` n  al zable`  s `True` above.

  Returns:
     erator of ele nts of t  dataset.
  """
   f not parse_fn:
    ra se ValueError("default_ nput_fn requ res a parse_fn")

   f log_tf_data_summar es and not  n  al zable:
    ra se ValueError("Requ re ` n  al zable`  f `log_tf_data_summar es`.")

  dataset = stream_block_format_dataset(
    f les=f les,
    parse_fn=parse_fn,
    batch_s ze=batch_s ze,
    repeat=repeat,
    num_threads=num_threads,
    dataset_fn=dataset_fn,
    keep_rate=keep_rate,
    parts_downsampl ng_rate=parts_downsampl ng_rate,
    shards=shards,
    shard_ ndex=shard_ ndex,
    shuffle=shuffle,
    shuffle_f les=shuffle_f les,
     nterleave= nterleave,
    **kwargs
  )

  # Add a tf.data.exper  ntal.StatsAggregator
  # https://www.tensorflow.org/vers ons/r1.15/ap _docs/python/tf/data/exper  ntal/StatsAggregator
   f log_tf_data_summar es:
    aggregator = tf.data.exper  ntal.StatsAggregator()
    opt ons = tf.data.Opt ons()
    opt ons.exper  ntal_stats.aggregator = aggregator
    dataset = dataset.w h_opt ons(opt ons)
    stats_summary = aggregator.get_summary()
    tf.add_to_collect on(tf.GraphKeys.SUMMAR ES, stats_summary)

   f  n  al zable:
    # w n t  data pars ng dpends on so  HashTable or Tensor, t   erator  s  n al zable and
    # t refore   need to be run expl c ly
     erator = dataset.make_ n  al zable_ erator()
    tf.add_to_collect on(tf.GraphKeys.TABLE_ N T AL ZERS,  erator. n  al zer)
  else:
     erator = dataset.make_one_shot_ erator()
  return  erator.get_next()


default_ nput_fn = data_record_ nput_fn  # pyl nt: d sable= nval d-na 
