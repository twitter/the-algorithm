# pyl nt: d sable=argu nts-d ffer,  nval d-na 
"""
T  module conta ns t  ``DataRecordTra ner``.
Unl ke t  parent ``Tra ner`` class, t  ``DataRecordTra ner``
 s used spec f cally for process ng data records.
  abstracts away a lot of t   ntr cac es of work ng w h DataRecords.
`DataRecord <http://go/datarecord>`_  s t  ma n p p ng format for data samples.
T  `DataRecordTra ner` assu s tra n ng data and product on responses and requests
to be organ zed as t  `Thr ft pred ct on serv ce AP 

A ``DataRecord``  s a Thr ft struct that def nes how to encode t  data:

::

  struct DataRecord {
    1: opt onal set< 64> b naryFeatures;                     // stores B NARY features
    2: opt onal map< 64, double> cont nuousFeatures;         // stores CONT NUOUS features
    3: opt onal map< 64,  64> d screteFeatures;              // stores D SCRETE features
    4: opt onal map< 64, str ng> str ngFeatures;             // stores STR NG features
    5: opt onal map< 64, set<str ng>> sparseB naryFeatures;  // stores sparse B NARY features
    6: opt onal map< 64, map<str ng, double>> sparseCont nuousFeatures; // sparse CONT NUOUS feature
    7: opt onal map< 64, b nary> blobFeatures; // stores features as BLOBs (b nary large objects)
    8: opt onal map< 64, tensor.GeneralTensor> tensors; // stores TENSOR features
    9: opt onal map< 64, tensor.SparseTensor> sparseTensors; // stores SPARSE_TENSOR features
  }


A s gn f cant port on of Tw ter data  s hydrated
and t n temporar ly stored on HDFS as DataRecords.
T  f les are compressed (.gz or .lzo) part  ons of data records.
T se form superv sed datasets. Each sample captures t  relat onsh p
bet en  nput and output (cause and effect).
To create y  own dataset, please see https://g hub.com/tw ter/elephant-b rd.

T  default ``DataRecordTra ner.[tra n,evaluate,learn]()`` reads t se datarecords.
T  data  s a read from mult ple ``part-*.[compress on]`` f les.
T  default behav or of ``DataRecordTra ner``  s to read sparse features from ``DataRecords``.
T   s a legacy default p p ng format at Tw ter.
T  ``DataRecordTra ner``  s flex ble enough for research and yet s mple enough
for a new beg nner ML pract oner.

By  ans of t  feature str ng to key hash ng funct on,
t  ``[tra n,eval]_feature_conf g`` constructor argu nts
control wh ch features can be used as sample labels, sample   ghts,
or sample features.
Samples  ds, and feature keys, feature values and feature   ghts
can be sk pped,  ncluded, excluded or used as labels,   ghts, or features.
T  allows   to eas ly def ne and control sparse d str but ons of
na d features.

Yet sparse data  s d ff cult to work w h.   are currently work ng to
opt m ze t  sparse operat ons due to  neff c enc es  n t  grad ent descent
and para ter update processes. T re are efforts underway
to m n m ze t  footpr nt of sparse data as    s  neff c ent to process.
CPUs and GPUs much prefer dense tensor data.
"""

 mport datet  

 mport tensorflow.compat.v1 as tf
from tw ter.deepb rd. o.dal  mport dal_to_hdfs_path,  s_dal_path
 mport twml
from twml.tra ners  mport Tra ner
from twml.contr b.feature_ mportances.feature_ mportances  mport (
  compute_feature_ mportances,
  TREE,
  wr e_feature_ mportances_to_hdfs,
  wr e_feature_ mportances_to_ml_dash)
from absl  mport logg ng


class DataRecordTra ner(Tra ner):  # pyl nt: d sable=abstract- thod
  """
  T  ``DataRecordTra ner``  mple ntat on  s  ntended to sat sfy t  most common use cases
  at Tw ter w re only t  bu ld_graph  thods needs to be overr dden.
  For t  reason, ``Tra ner.[tra n,eval]_ nput_fn``  thods
  assu  a DataRecord dataset part  oned  nto part f les stored  n compressed (e.g. gz p) format.

  For use-cases that d ffer from t  common Tw ter use-case,
  furt r Tra ner  thods can be overr dden.
   f that st ll doesn't prov de enough flex b l y, t  user can always
  use t  tf.est mator.Es mator or tf.sess on.run d rectly.
  """

  def __ n __(
          self, na , params,
          bu ld_graph_fn,
          feature_conf g=None,
          **kwargs):
    """
    T  DataRecordTra ner constructor bu lds a
    ``tf.est mator.Est mator`` and stores    n self.est mator.
    For t  reason, DataRecordTra ner accepts t  sa  Est mator constructor argu nts.
      also accepts add  onal argu nts to fac l ate  tr c evaluat on and mult -phase tra n ng
    ( n _from_d r,  n _map).

    Args:
      parent argu nts:
        See t  `Tra ner constructor <#twml.tra ners.Tra ner.__ n __>`_ docu ntat on
        for a full l st of argu nts accepted by t  parent class.
      na , params, bu ld_graph_fn (and ot r parent class args):
        see docu ntat on for twml.Tra ner doc.
      feature_conf g:
        An object of type FeatureConf g descr b ng what features to decode.
        Defaults to None. But    s needed  n t  follow ng cases:
          - `get_tra n_ nput_fn()` / `get_eval_ nput_fn()`  s called w hout a `parse_fn`
          - `learn()`, `tra n()`, `eval()`, `cal brate()` are called w hout prov d ng `* nput_fn`.

      **kwargs:
        furt r kwargs can be spec f ed and passed to t  Est mator constructor.
    """

    # NOTE: DO NOT MOD FY `params` BEFORE TH S CALL.
    super(DataRecordTra ner, self).__ n __(
      na =na , params=params, bu ld_graph_fn=bu ld_graph_fn, **kwargs)

    self._feature_conf g = feature_conf g

    # date range para ters common to both tra n ng and evaluat on data:
    h _resolut on = self.params.get("h _resolut on", 1)
    data_threads = self.params.get("data_threads", 4)
    datet  _format = self.params.get("datet  _format", "%Y/%m/%d")

    # retr eve t  des red tra n ng dataset f les
    self._tra n_f les = self.bu ld_f les_l st(
      f les_l st_path=self.params.get("tra n_f les_l st", None),
      data_d r=self.params.get("tra n_data_d r", None),
      start_datet  =self.params.get("tra n_start_datet  ", None),
      end_datet  =self.params.get("tra n_end_datet  ", None),
      datet  _format=datet  _format, data_threads=data_threads,
      h _resolut on=h _resolut on, maybe_save=self. s_ch ef(),
      overwr e=self.params.get("tra n_overwr e_f les_l st", False),
    )

    # retr eve t  des red evaluat on dataset f les
    eval_na  = self.params.get("eval_na ", None)

     f eval_na  == "tra n":
      self._eval_f les = self._tra n_f les
    else:
      self._eval_f les = self.bu ld_f les_l st(
        f les_l st_path=self.params.get("eval_f les_l st", None),
        data_d r=self.params.get("eval_data_d r", None),
        start_datet  =self.params.get("eval_start_datet  ", None),
        end_datet  =self.params.get("eval_end_datet  ", None),
        datet  _format=datet  _format, data_threads=data_threads,
        h _resolut on=h _resolut on, maybe_save=self. s_ch ef(),
        overwr e=self.params.get("eval_overwr e_f les_l st", False),
      )

       f not self.params.get("allow_tra n_eval_overlap"):
        #  f t re  s overlap bet en tra n and eval, error out!
         f self._tra n_f les and self._eval_f les:
          overlap_f les = set(self._tra n_f les) & set(self._eval_f les)
        else:
          overlap_f les = set()
         f overlap_f les:
          ra se ValueError("T re  s an overlap bet en tra n and eval f les:\n %s" %
                           (overlap_f les))

  @stat c thod
  def bu ld_hdfs_f les_l st(
      f les_l st_path, data_d r,
      start_datet  , end_datet  , datet  _format,
      data_threads, h _resolut on, maybe_save, overwr e):
     f f les_l st_path:
      f les_l st_path = twml.ut l.preprocess_path(f les_l st_path)

     f  s nstance(start_datet  , datet  .datet  ):
      start_datet   = start_datet  .strft  (datet  _format)
     f  s nstance(end_datet  , datet  .datet  ):
      end_datet   = end_datet  .strft  (datet  _format)

    l st_f les_by_datet  _args = {
      "base_path": data_d r,
      "start_datet  ": start_datet  ,
      "end_datet  ": end_datet  ,
      "datet  _pref x_format": datet  _format,
      "extens on": "lzo",
      "parallel sm": data_threads,
      "h _resolut on": h _resolut on,
      "sort": True,
    }

    # no cac  of data f le paths, just get t  l st by scrap ng t  d rectory
     f not f les_l st_path or not tf. o.gf le.ex sts(f les_l st_path):
      # twml.ut l.l st_f les_by_datet   returns None  f data_d r  s None.
      # twml.ut l.l st_f les_by_datet   passes through data_d r  f data_d r  s a l st
      f les_l st = twml.ut l.l st_f les_by_datet  (**l st_f les_by_datet  _args)
    else:
      # t  cac d data f le paths f le ex sts.
      f les_ nfo = twml.ut l.read_f le(f les_l st_path, decode="json")
      # use t  cac d l st  f data params match current params,
      #  or  f current params are None
      # Not  nclud ng None c cks for datet  _format and h _resolut on,
      #  s nce those are shared bet en eval and tra n ng.
       f (all(param  s None for param  n [data_d r, start_datet  , end_datet  ]) or
          (f les_ nfo["data_d r"] == data_d r and
           f les_ nfo["start_datet  "] == start_datet   and
           f les_ nfo["end_datet  "] == end_datet   and
           f les_ nfo["datet  _format"] == datet  _format and
           f les_ nfo["h _resolut on"] == h _resolut on)):
        f les_l st = f les_ nfo["f les"]
      el f overwr e:
        # current params are not none and don't match saved params
        # `overwr e`  nd cates   should thus update t  l st
        f les_l st = twml.ut l.l st_f les_by_datet  (**l st_f les_by_datet  _args)
      else:
        # dont update t  cac d l st
        ra se ValueError(" nformat on  n f les_l st  s  ncons stent w h prov ded args.\n"
                         "D d    ntend to overwr e f les_l st us ng "
                         "--tra n.overwr e_f les_l st or --eval.overwr e_f les_l st?\n"
                         " f    nstead want to use t  paths  n f les_l st, ensure that "
                         "data_d r, start_datet  , and end_datet   are None.")

     f maybe_save and f les_l st_path and (overwr e or not tf. o.gf le.ex sts(f les_l st_path)):
      save_d ct = {}
      save_d ct["f les"] = f les_l st
      save_d ct["data_d r"] = data_d r
      save_d ct["start_datet  "] = start_datet  
      save_d ct["end_datet  "] = end_datet  
      save_d ct["datet  _format"] = datet  _format
      save_d ct["h _resolut on"] = h _resolut on
      twml.ut l.wr e_f le(f les_l st_path, save_d ct, encode="json")

    return f les_l st

  @stat c thod
  def bu ld_f les_l st(f les_l st_path, data_d r,
                        start_datet  , end_datet  , datet  _format,
                        data_threads, h _resolut on, maybe_save, overwr e):
    '''
    W n spec fy ng DAL datasets, only data_d r, start_date  , and end_datet  
    should be g ven w h t  format:

    dal://{cluster}/{role}/{dataset_na }/{env}

    '''
     f not data_d r or not  s_dal_path(data_d r):
      logg ng.warn(f"Please cons der spec fy ng a dal:// dataset rat r than pass ng a phys cal hdfs path.")
      return DataRecordTra ner.bu ld_hdfs_f les_l st(
        f les_l st_path, data_d r,
        start_datet  , end_datet  , datet  _format,
        data_threads, h _resolut on, maybe_save, overwr e)

    del datet  _format
    del data_threads
    del h _resolut on
    del maybe_save
    del overwr e

    return dal_to_hdfs_path(
      path=data_d r,
      start_datet  =start_datet  ,
      end_datet  =end_datet  ,
    )

  @property
  def tra n_f les(self):
    return self._tra n_f les

  @property
  def eval_f les(self):
    return self._eval_f les

  @stat c thod
  def add_parser_argu nts():
    """
    Add common commandl ne args to parse for t  Tra ner class.
    Typ cally, t  user calls t  funct on and t n parses cmd-l ne argu nts
     nto an argparse.Na space object wh ch  s t n passed to t  Tra ner constructor
    v a t  params argu nt.

    See t  `Tra ner code <_modules/twml/tra ners/tra ner.html#Tra ner.add_parser_argu nts>`_
    and `DataRecordTra ner code
    <_modules/twml/tra ners/tra ner.html#DataRecordTra ner.add_parser_argu nts>`_
    for a l st and descr pt on of all cmd-l ne argu nts.

    Args:
      learn ng_rate_decay:
        Defaults to False. W n True, parses learn ng rate decay argu nts.

    Returns:
      argparse.Argu ntParser  nstance w h so  useful args already added.
    """
    parser = super(DataRecordTra ner, DataRecordTra ner).add_parser_argu nts()
    parser.add_argu nt(
      "--tra n.f les_l st", "--tra n_f les_l st", type=str, default=None,
      dest="tra n_f les_l st",
       lp="Path for a json f le stor ng  nformat on on tra n ng data.\n"
           "Spec f cally, t  f le at f les_l st should conta n t  dataset para ters "
           "for construct ng t  l st of data f les, and t  l st of data f le paths.\n"
           " f t  json f le does not ex st, ot r args are used to construct t  "
           "tra n ng f les l st, and that l st w ll be saved to t   nd cated json f le.\n"
           " f t  json f le does ex st, and current args are cons stent w h "
           "saved args, or are all None, t n t  saved f les l st w ll be used.\n"
           " f current args are not cons stent w h t  saved args, t n error out "
           " f tra n_overwr e_f les_l st==False, else overwr e f les_l st w h "
           "a newly constructed l st.")
    parser.add_argu nt(
      "--tra n.overwr e_f les_l st", "--tra n_overwr e_f les_l st", act on="store_true", default=False,
      dest="tra n_overwr e_f les_l st",
       lp="W n t  --tra n.f les_l st param  s used,  nd cates w t r to "
           "overwr e t  ex st ng --tra n.f les_l st w n t re are d fferences "
           "bet en t  current and saved dataset args. Default (False)  s to "
           "error out  f f les_l st ex sts and d ffers from current params.")
    parser.add_argu nt(
      "--tra n.data_d r", "--tra n_data_d r", type=str, default=None,
      dest="tra n_data_d r",
       lp="Path to t  tra n ng data d rectory."
           "Supports local, dal://{cluster}-{reg on}/{role}/{dataset_na }/{env ron nt}, "
           "and HDFS (hdfs://default/<path> ) paths.")
    parser.add_argu nt(
      "--tra n.start_date", "--tra n_start_datet  ",
      type=str, default=None,
      dest="tra n_start_datet  ",
       lp="Start ng date for tra n ng  ns de t  tra n data d r."
           "T  start datet    s  nclus ve."
           "e.g. 2019/01/15")
    parser.add_argu nt(
      "--tra n.end_date", "--tra n_end_datet  ", type=str, default=None,
      dest="tra n_end_datet  ",
       lp="End ng date for tra n ng  ns de t  tra n data d r."
           "T  end datet    s  nclus ve."
           "e.g. 2019/01/15")
    parser.add_argu nt(
      "--eval.f les_l st", "--eval_f les_l st", type=str, default=None,
      dest="eval_f les_l st",
       lp="Path for a json f le stor ng  nformat on on evaluat on data.\n"
           "Spec f cally, t  f le at f les_l st should conta n t  dataset para ters "
           "for construct ng t  l st of data f les, and t  l st of data f le paths.\n"
           " f t  json f le does not ex st, ot r args are used to construct t  "
           "evaluat on f les l st, and that l st w ll be saved to t   nd cated json f le.\n"
           " f t  json f le does ex st, and current args are cons stent w h "
           "saved args, or are all None, t n t  saved f les l st w ll be used.\n"
           " f current args are not cons stent w h t  saved args, t n error out "
           " f eval_overwr e_f les_l st==False, else overwr e f les_l st w h "
           "a newly constructed l st.")
    parser.add_argu nt(
      "--eval.overwr e_f les_l st", "--eval_overwr e_f les_l st", act on="store_true", default=False,
      dest="eval_overwr e_f les_l st",
       lp="W n t  --eval.f les_l st param  s used,  nd cates w t r to "
           "overwr e t  ex st ng --eval.f les_l st w n t re are d fferences "
           "bet en t  current and saved dataset args. Default (False)  s to "
           "error out  f f les_l st ex sts and d ffers from current params.")
    parser.add_argu nt(
      "--eval.data_d r", "--eval_data_d r", type=str, default=None,
      dest="eval_data_d r",
       lp="Path to t  cross-val dat on data d rectory."
           "Supports local, dal://{cluster}-{reg on}/{role}/{dataset_na }/{env ron nt}, "
           "and HDFS (hdfs://default/<path> ) paths.")
    parser.add_argu nt(
      "--eval.start_date", "--eval_start_datet  ",
      type=str, default=None,
      dest="eval_start_datet  ",
       lp="Start ng date for evaluat ng  ns de t  eval data d r."
           "T  start datet    s  nclus ve."
           "e.g. 2019/01/15")
    parser.add_argu nt(
      "--eval.end_date", "--eval_end_datet  ", type=str, default=None,
      dest="eval_end_datet  ",
       lp="End ng date for evaluat ng  ns de t  eval data d r."
           "T  end datet    s  nclus ve."
           "e.g. 2019/01/15")
    parser.add_argu nt(
      "--datet  _format", type=str, default="%Y/%m/%d",
       lp="Date format for tra n ng and evaluat on datasets."
           "Has to be a format that  s understood by python datet  ."
           "e.g. %%Y/%%m/%%d for 2019/01/15."
           "Used only  f {tra n/eval}.{start/end}_date are prov ded.")
    parser.add_argu nt(
      "--h _resolut on", type= nt, default=None,
       lp="Spec fy t  h ly resolut on of t  stored data.")
    parser.add_argu nt(
      "--data_spec", type=str, requ red=True,
       lp="Path to data spec f cat on JSON f le. T  f le  s used to decode DataRecords")
    parser.add_argu nt(
      "--tra n.keep_rate", "--tra n_keep_rate", type=float, default=None,
      dest="tra n_keep_rate",
       lp="A float value  n (0.0, 1.0] that  nd cates to drop records accord ng to t  Bernoull  \
      d str but on w h p = 1 - keep_rate.")
    parser.add_argu nt(
      "--eval.keep_rate", "--eval_keep_rate", type=float, default=None,
      dest="eval_keep_rate",
       lp="A float value  n (0.0, 1.0] that  nd cates to drop records accord ng to t  Bernoull  \
      d str but on w h p = 1 - keep_rate.")
    parser.add_argu nt(
      "--tra n.parts_downsampl ng_rate", "--tra n_parts_downsampl ng_rate",
      dest="tra n_parts_downsampl ng_rate",
      type=float, default=None,
       lp="A float value  n (0.0, 1.0] that  nd cates t  factor by wh ch to downsample part \
      f les. For example, a value of 0.2  ans only 20 percent of part f les beco  part of t  \
      dataset.")
    parser.add_argu nt(
      "--eval.parts_downsampl ng_rate", "--eval_parts_downsampl ng_rate",
      dest="eval_parts_downsampl ng_rate",
      type=float, default=None,
       lp="A float value  n (0.0, 1.0] that  nd cates t  factor by wh ch to downsample part \
      f les. For example, a value of 0.2  ans only 20 percent of part f les beco  part of t  \
      dataset.")
    parser.add_argu nt(
      "--allow_tra n_eval_overlap",
      dest="allow_tra n_eval_overlap",
      act on="store_true",
       lp="Allow overlap bet en tra n and eval datasets."
    )
    parser.add_argu nt(
      "--eval_na ", type=str, default=None,
       lp="Str ng denot ng what   want to na  t  eval.  f t   s `tra n`, t n   eval on \
      t  tra n ng dataset."
    )
    return parser

  def contr b_run_feature_ mportances(self, feature_ mportances_parse_fn=None, wr e_to_hdfs=True, extra_groups=None, datarecord_f lter_fn=None, datarecord_f lter_run_na =None):
    """Compute feature  mportances on a tra ned model (t   s a contr b feature)
    Args:
      feature_ mportances_parse_fn (fn): T  sa  parse_fn that   use for tra n ng/evaluat on.
        Defaults to feature_conf g.get_parse_fn()
      wr e_to_hdfs (bool): Sett ng t  to True wr es t  feature  mportance  tr cs to HDFS
    extra_groups (d ct<str, l st<str>>): A d ct onary mapp ng t  na  of extra feature groups to t  l st of
      t  na s of t  features  n t  group
    datarecord_f lter_fn (funct on): a funct on takes a s ngle data sample  n com.tw ter.ml.ap .ttypes.DataRecord format
        and return a boolean value, to  nd cate  f t  data record should be kept  n feature  mportance module or not.
    """
    logg ng. nfo("Comput ng feature  mportance")
    algor hm = self._params.feature_ mportance_algor hm

    kwargs = {}
     f algor hm == TREE:
      kwargs["spl _feature_group_on_per od"] = self._params.spl _feature_group_on_per od
      kwargs["stopp ng_ tr c"] = self._params.feature_ mportance_ tr c
      kwargs["sens  v y"] = self._params.feature_ mportance_sens  v y
      kwargs["dont_bu ld_tree"] = self._params.dont_bu ld_tree
      kwargs["extra_groups"] = extra_groups
       f self._params.feature_ mportance_ s_ tr c_larger_t _better:
        # T  user has spec f ed that t  stopp ng  tr c  s one w re larger values are better (e.g. ROC_AUC)
        kwargs[" s_ tr c_larger_t _better"] = True
      el f self._params.feature_ mportance_ s_ tr c_smaller_t _better:
        # T  user has spec f ed that t  stopp ng  tr c  s one w re smaller values are better (e.g. LOSS)
        kwargs[" s_ tr c_larger_t _better"] = False
      else:
        # T  user has not spec f ed wh ch d rect on  s better for t  stopp ng  tr c
        kwargs[" s_ tr c_larger_t _better"] = None
      logg ng. nfo("Us ng t  tree algor hm w h kwargs {}".format(kwargs))

    feature_ mportances = compute_feature_ mportances(
      tra ner=self,
      data_d r=self._params.get('feature_ mportance_data_d r'),
      feature_conf g=self._feature_conf g,
      algor hm=algor hm,
      record_count=self._params.feature_ mportance_example_count,
      parse_fn=feature_ mportances_parse_fn,
      datarecord_f lter_fn=datarecord_f lter_fn,
      **kwargs)

     f not feature_ mportances:
      logg ng. nfo("Feature  mportances returned None")
    else:
       f wr e_to_hdfs:
        logg ng. nfo("Wr  ng feature  mportance to HDFS")
        wr e_feature_ mportances_to_hdfs(
          tra ner=self,
          feature_ mportances=feature_ mportances,
          output_path=datarecord_f lter_run_na ,
           tr c=self._params.get('feature_ mportance_ tr c'))
      else:
        logg ng. nfo("Not wr  ng feature  mportance to HDFS")

      logg ng. nfo("Wr  ng feature  mportance to ML  tastore")
      wr e_feature_ mportances_to_ml_dash(
        tra ner=self, feature_ mportances=feature_ mportances)
    return feature_ mportances

  def export_model(self, serv ng_ nput_rece ver_fn=None,
                   export_output_fn=None,
                   export_d r=None, c ckpo nt_path=None,
                   feature_spec=None):
    """
    Export t  model for pred ct on. Typ cally, t  exported model
    w ll later be run  n product on servers. T   thod  s called
    by t  user to export t  PRED CT graph to d sk.

     nternally, t   thod calls `tf.est mator.Est mator.export_savedmodel
    <https://www.tensorflow.org/ap _docs/python/tf/est mator/Est mator#export_savedmodel>`_.

    Args:
      serv ng_ nput_rece ver_fn (Funct on):
        funct on prepar ng t  model for  nference requests.
         f not set; defaults to t  t  serv ng  nput rece ver fn set by t  FeatureConf g.
      export_output_fn (Funct on):
        Funct on to export t  graph_output (output of bu ld_graph) for
        pred ct on. Takes a graph_output d ct as sole argu nt and returns
        t  export_output_fns d ct.
        Defaults to ``twml.export_output_fns.batch_pred ct on_cont nuous_output_fn``.
      export_d r:
        d rectory to export a SavedModel for pred ct on servers.
        Defaults to ``[save_d r]/exported_models``.
      c ckpo nt_path:
        t  c ckpo nt path to export.  f None (t  default), t  most recent c ckpo nt
        found w h n t  model d rectory ``save_d r``  s chosen.

    Returns:
      T  export d rectory w re t  PRED CT graph  s saved.
    """
     f serv ng_ nput_rece ver_fn  s None:
       f self._feature_conf g  s None:
        ra se ValueError("`feature_conf g` was not passed to `DataRecordTra ner`")
      serv ng_ nput_rece ver_fn = self._feature_conf g.get_serv ng_ nput_rece ver_fn()

     f feature_spec  s None:
       f self._feature_conf g  s None:
        ra se ValueError("feature_spec can not be  nferred."
                         "Please pass feature_spec=feature_conf g.get_feature_spec() to t  tra ner.export_model  thod")
      else:
        feature_spec = self._feature_conf g.get_feature_spec()

     f  s nstance(serv ng_ nput_rece ver_fn, twml.feature_conf g.FeatureConf g):
      ra se ValueError("Cannot pass FeatureConf g as a para ter to serv ng_ nput_rece ver_fn")
    el f not callable(serv ng_ nput_rece ver_fn):
      ra se ValueError("Expect ng Funct on for serv ng_ nput_rece ver_fn")

     f export_output_fn  s None:
      export_output_fn = twml.export_output_fns.batch_pred ct on_cont nuous_output_fn

    return super(DataRecordTra ner, self).export_model(
      export_d r=export_d r,
      serv ng_ nput_rece ver_fn=serv ng_ nput_rece ver_fn,
      c ckpo nt_path=c ckpo nt_path,
      export_output_fn=export_output_fn,
      feature_spec=feature_spec,
    )

  def get_tra n_ nput_fn(
      self, parse_fn=None, repeat=None, shuffle=True,  nterleave=True, shuffle_f les=None,
       n  al zable=False, log_tf_data_summar es=False, **kwargs):
    """
    T   thod  s used to create  nput funct on used by est mator.tra n().

    Args:
      parse_fn:
        Funct on to parse a data record  nto a set of features.
        Defaults to t  parser returned by t  FeatureConf g selected
      repeat (opt onal):
        Spec f es  f t  dataset  s to be repeated. Defaults to `params.tra n_steps > 0`.
        T  ensures t  tra n ng  s run for atleast `params.tra n_steps`.
        Toggl ng t  to `False` results  n tra n ng f n sh ng w n one of t  follow ng happens:
          - T  ent re dataset has been tra ned upon once.
          - `params.tra n_steps` has been reac d.
      shuffle (opt onal):
        Spec f es  f t  f les and records  n t  f les need to be shuffled.
        W n `True`,  f les are shuffled, and records of each f les are shuffled.
        W n `False`, f les are read  n alpha-nu r cal order. Also w n `False`
        t  dataset  s sharded among workers for Hogw ld and d str buted tra n ng
         f no shard ng conf gurat on  s prov ded  n `params.tra n_dataset_shards`.
        Defaults to `True`.
       nterleave (opt onal):
        Spec f es  f records from mult ple f les need to be  nterleaved  n parallel.
        Defaults to `True`.
      shuffle_f les (opt onal):
        Shuffle t  l st of f les. Defaults to 'Shuffle'  f not prov ded.
       n  al zable (opt onal):
        A boolean  nd cator. W n t  pars ng funct on depends on so  res ce, e.g. a HashTable or
        a Tensor,  .e.  's an  n  al zable  erator, set   to True. Ot rw se, default value
        (false)  s used for most pla n  erators.
      log_tf_data_summar es (opt onal):
        A boolean  nd cator denot ng w t r to add a `tf.data.exper  ntal.StatsAggregator` to t 
        tf.data p pel ne. T  adds summar es of p pel ne ut l zat on and buffer s zes to t  output
        events f les. T  requ res that ` n  al zable`  s `True` above.

    Returns:
      An  nput_fn that can be consu d by `est mator.tra n()`.
    """
     f parse_fn  s None:
       f self._feature_conf g  s None:
        ra se ValueError("`feature_conf g` was not passed to `DataRecordTra ner`")
      parse_fn = self._feature_conf g.get_parse_fn()

     f not callable(parse_fn):
      ra se ValueError("Expect ng parse_fn to be a funct on.")

     f log_tf_data_summar es and not  n  al zable:
      ra se ValueError("Requ re ` n  al zable`  f `log_tf_data_summar es`.")

     f repeat  s None:
      repeat = self.params.tra n_steps > 0 or self.params.get('d str buted', False)

     f not shuffle and self.num_workers > 1 and self.params.tra n_dataset_shards  s None:
      num_shards = self.num_workers
      shard_ ndex = self.worker_ ndex
    else:
      num_shards = self.params.tra n_dataset_shards
      shard_ ndex = self.params.tra n_dataset_shard_ ndex

    return lambda: twml. nput_fns.default_ nput_fn(
      f les=self._tra n_f les,
      batch_s ze=self.params.tra n_batch_s ze,
      parse_fn=parse_fn,
      num_threads=self.params.num_threads,
      repeat=repeat,
      keep_rate=self.params.tra n_keep_rate,
      parts_downsampl ng_rate=self.params.tra n_parts_downsampl ng_rate,
      shards=num_shards,
      shard_ ndex=shard_ ndex,
      shuffle=shuffle,
      shuffle_f les=(shuffle  f shuffle_f les  s None else shuffle_f les),
       nterleave= nterleave,
       n  al zable= n  al zable,
      log_tf_data_summar es=log_tf_data_summar es,
      **kwargs)

  def get_eval_ nput_fn(
      self, parse_fn=None, repeat=None,
      shuffle=True,  nterleave=True,
      shuffle_f les=None,  n  al zable=False, log_tf_data_summar es=False, **kwargs):
    """
    T   thod  s used to create  nput funct on used by est mator.eval().

    Args:
      parse_fn:
        Funct on to parse a data record  nto a set of features.
        Defaults to twml.parsers.get_sparse_parse_fn(feature_conf g).
      repeat (opt onal):
        Spec f es  f t  dataset  s to be repeated. Defaults to `params.eval_steps > 0`.
        T  ensures t  evaluat on  s run for atleast `params.eval_steps`.
        Toggl ng t  to `False` results  n evaluat on f n sh ng w n one of t  follow ng happens:
          - T  ent re dataset has been evaled upon once.
          - `params.eval_steps` has been reac d.
      shuffle (opt onal):
        Spec f es  f t  f les and records  n t  f les need to be shuffled.
        W n `False`, f les are read  n alpha-nu r cal order.
        W n `True`,  f les are shuffled, and records of each f les are shuffled.
        Defaults to `True`.
       nterleave (opt onal):
        Spec f es  f records from mult ple f les need to be  nterleaved  n parallel.
        Defaults to `True`.
      shuffle_f les (opt onal):
        Shuffles t  l st of f les. Defaults to 'Shuffle'  f not prov ded.
       n  al zable (opt onal):
        A boolean  nd cator. W n t  pars ng funct on depends on so  res ce, e.g. a HashTable or
        a Tensor,  .e.  's an  n  al zable  erator, set   to True. Ot rw se, default value
        (false)  s used for most pla n  erators.
      log_tf_data_summar es (opt onal):
        A boolean  nd cator denot ng w t r to add a `tf.data.exper  ntal.StatsAggregator` to t 
        tf.data p pel ne. T  adds summar es of p pel ne ut l zat on and buffer s zes to t  output
        events f les. T  requ res that ` n  al zable`  s `True` above.

    Returns:
      An  nput_fn that can be consu d by `est mator.eval()`.
    """
     f parse_fn  s None:
       f self._feature_conf g  s None:
        ra se ValueError("`feature_conf g` was not passed to `DataRecordTra ner`")
      parse_fn = self._feature_conf g.get_parse_fn()

     f not self._eval_f les:
      ra se ValueError("`eval_f les` was not present  n `params` passed to `DataRecordTra ner`")

     f not callable(parse_fn):
      ra se ValueError("Expect ng parse_fn to be a funct on.")

     f log_tf_data_summar es and not  n  al zable:
      ra se ValueError("Requ re ` n  al zable`  f `log_tf_data_summar es`.")

     f repeat  s None:
      repeat = self.params.eval_steps > 0

    return lambda: twml. nput_fns.default_ nput_fn(
      f les=self._eval_f les,
      batch_s ze=self.params.eval_batch_s ze,
      parse_fn=parse_fn,
      num_threads=self.params.num_threads,
      repeat=repeat,
      keep_rate=self.params.eval_keep_rate,
      parts_downsampl ng_rate=self.params.eval_parts_downsampl ng_rate,
      shuffle=shuffle,
      shuffle_f les=(shuffle  f shuffle_f les  s None else shuffle_f les),
       nterleave= nterleave,
       n  al zable= n  al zable,
      log_tf_data_summar es=log_tf_data_summar es,
      **kwargs
    )

  def _assert_tra n_f les(self):
     f not self._tra n_f les:
      ra se ValueError("tra n.data_d r was not set  n params passed to DataRecordTra ner.")

  def _assert_eval_f les(self):
     f not self._eval_f les:
      ra se ValueError("eval.data_d r was not set  n params passed to DataRecordTra ner.")

  def tra n(self,  nput_fn=None, steps=None, hooks=None):
    """
    Makes  nput funct ons opt onal.  nput_fn defaults to self.get_tra n_ nput_fn().
    See Tra ner for more deta led docu ntat on docu ntat on.
    """
     f  nput_fn  s None:
      self._assert_tra n_f les()
     nput_fn =  nput_fn  f  nput_fn else self.get_tra n_ nput_fn()
    super(DataRecordTra ner, self).tra n( nput_fn= nput_fn, steps=steps, hooks=hooks)

  def evaluate(self,  nput_fn=None, steps=None, hooks=None, na =None):
    """
    Makes  nput funct ons opt onal.  nput_fn defaults to self.get_eval_ nput_fn().
    See Tra ner for more deta led docu ntat on.
    """
     f  nput_fn  s None:
      self._assert_eval_f les()
     nput_fn =  nput_fn  f  nput_fn else self.get_eval_ nput_fn(repeat=False)
    return super(DataRecordTra ner, self).evaluate(
       nput_fn= nput_fn,
      steps=steps,
      hooks=hooks,
      na =na 
    )

  def learn(self, tra n_ nput_fn=None, eval_ nput_fn=None, **kwargs):
    """
    Overr des ``Tra ner.learn`` to make `` nput_fn`` funct ons opt onal.
    Respect vely, ``tra n_ nput_fn`` and ``eval_ nput_fn`` default to
    ``self.tra n_ nput_fn`` and ``self.eval_ nput_fn``.
    See ``Tra ner.learn`` for more deta led docu ntat on.
    """
     f tra n_ nput_fn  s None:
      self._assert_tra n_f les()
     f eval_ nput_fn  s None:
      self._assert_eval_f les()
    tra n_ nput_fn = tra n_ nput_fn  f tra n_ nput_fn else self.get_tra n_ nput_fn()
    eval_ nput_fn = eval_ nput_fn  f eval_ nput_fn else self.get_eval_ nput_fn()

    super(DataRecordTra ner, self).learn(
      tra n_ nput_fn=tra n_ nput_fn,
      eval_ nput_fn=eval_ nput_fn,
      **kwargs
    )

  def tra n_and_evaluate(self,
                         tra n_ nput_fn=None, eval_ nput_fn=None,
                          **kwargs):
    """
    Overr des ``Tra ner.tra n_and_evaluate`` to make `` nput_fn`` funct ons opt onal.
    Respect vely, ``tra n_ nput_fn`` and ``eval_ nput_fn`` default to
    ``self.tra n_ nput_fn`` and ``self.eval_ nput_fn``.
    See ``Tra ner.tra n_and_evaluate`` for deta led docu ntat on.
    """
     f tra n_ nput_fn  s None:
      self._assert_tra n_f les()
     f eval_ nput_fn  s None:
      self._assert_eval_f les()
    tra n_ nput_fn = tra n_ nput_fn  f tra n_ nput_fn else self.get_tra n_ nput_fn()
    eval_ nput_fn = eval_ nput_fn  f eval_ nput_fn else self.get_eval_ nput_fn()

    super(DataRecordTra ner, self).tra n_and_evaluate(
      tra n_ nput_fn=tra n_ nput_fn,
      eval_ nput_fn=eval_ nput_fn,
      **kwargs
    )

  def _model_fn(self, features, labels, mode, params, conf g=None):
    """
    Overr des t  _model_fn to correct for t  features shape of t  sparse features
    extracted w h t  contr b.FeatureConf g
    """
     f  s nstance(self._feature_conf g, twml.contr b.feature_conf g.FeatureConf g):
      # F x t  shape of t  features. T  features d ct onary w ll be mod f ed to
      # conta n t  shape changes.
      twml.ut l.f x_shape_sparse(features, self._feature_conf g)
    return super(DataRecordTra ner, self)._model_fn(
      features=features,
      labels=labels,
      mode=mode,
      params=params,
      conf g=conf g
    )

  def cal brate(self,
                cal brator,
                 nput_fn=None,
                steps=None,
                save_cal brator=True,
                hooks=None):
    """
    Makes  nput funct ons opt onal.  nput_fn defaults to self.tra n_ nput_fn.
    See Tra ner for more deta led docu ntat on.
    """
     f  nput_fn  s None:
      self._assert_tra n_f les()
     nput_fn =  nput_fn  f  nput_fn else self.get_tra n_ nput_fn()
    super(DataRecordTra ner, self).cal brate(cal brator=cal brator,
                                              nput_fn= nput_fn,
                                             steps=steps,
                                             save_cal brator=save_cal brator,
                                             hooks=hooks)

  def save_c ckpo nts_and_export_model(self,
                                        serv ng_ nput_rece ver_fn,
                                        export_output_fn=None,
                                        export_d r=None,
                                        c ckpo nt_path=None,
                                         nput_fn=None):
    """
    Exports saved module after sav ng c ckpo nt to save_d r.
    Please note that to use t   thod,   need to ass gn a loss to t  output
    of t  bu ld_graph (for t  tra n mode).
    See export_model for more deta led  nformat on.
    """
    self.tra n( nput_fn= nput_fn, steps=1)
    self.export_model(serv ng_ nput_rece ver_fn, export_output_fn, export_d r, c ckpo nt_path)

  def save_c ckpo nts_and_evaluate(self,
                                     nput_fn=None,
                                    steps=None,
                                    hooks=None,
                                    na =None):
    """
    Evaluates model after sav ng c ckpo nt to save_d r.
    Please note that to use t   thod,   need to ass gn a loss to t  output
    of t  bu ld_graph (for t  tra n mode).
    See evaluate for more deta led  nformat on.
    """
    self.tra n( nput_fn= nput_fn, steps=1)
    self.evaluate( nput_fn, steps, hooks, na )
