"""
Wrappers around tf.est mator.Exporters to export models and save c ckpo nts.
"""
 mport os

 mport tensorflow.compat.v1 as tf
from tensorflow.python.est mator  mport exporter
 mport twml


class _AllSavedModelsExporter(tf.est mator.Exporter):
  """ nternal exporter class to be used for export ng models for d fferent modes."""

  def __ n __(self,
               na ,
                nput_rece ver_fn_map,
               backup_c ckpo nts,
               assets_extra=None,
               as_text=False):
    """
    Args:
      na : A un que na  to be used for t  exporter. T   s used  n t  export path.
       nput_rece ver_fn_map: A map of tf.est mator.ModeKeys to  nput_rece ver_fns.
      backup_c ckpo nts: A flag to spec fy  f backups of c ckpo nts need to be made.
      assets_extra: Add  onal assets to be  ncluded  n t  exported model.
      as_text: Spec f es  f t  exported model should be  n a human readable text format.
    """
    self._na  = na 
    self._ nput_rece ver_fn_map =  nput_rece ver_fn_map
    self._backup_c ckpo nts = backup_c ckpo nts
    self._assets_extra = assets_extra
    self._as_text = as_text

  @property
  def na (self):
    return self._na 

  def export(self, est mator, export_path, c ckpo nt_path, eval_result,
              s_t _f nal_export):
    del  s_t _f nal_export

    export_path = twml.ut l.san  ze_hdfs_path(export_path)
    c ckpo nt_path = twml.ut l.san  ze_hdfs_path(c ckpo nt_path)

     f self._backup_c ckpo nts:
      backup_path = os.path.jo n(export_path, "c ckpo nts")
      # Ensure backup_path  s created. maked rs passes  f d r already ex sts.
      tf. o.gf le.maked rs(backup_path)
      twml.ut l.backup_c ckpo nt(c ckpo nt_path, backup_path, empty_backup=False)

    export_result = est mator.exper  ntal_export_all_saved_models(
      export_path,
      self._ nput_rece ver_fn_map,
      assets_extra=self._assets_extra,
      as_text=self._as_text,
      c ckpo nt_path=c ckpo nt_path)

    return export_result


class BestExporter(tf.est mator.BestExporter):
  """
  T  class  n r s from tf.est mator.BestExporter w h t  follow ng d fferences:
    -   also creates a backup of t  best c ckpo nt.
    -   can export t  model for mult ple modes.

  A backup / export  s perfor d everyt   t  evaluated  tr c  s better
  than prev ous models.
  """

  def __ n __(self,
               na ='best_exporter',
                nput_rece ver_fn_map=None,
               backup_c ckpo nts=True,
               event_f le_pattern='eval/*.tfevents.*',
               compare_fn=exporter._loss_smaller,
               assets_extra=None,
               as_text=False,
               exports_to_keep=5):
    """
    Args:
      na : A un que na  to be used for t  exporter. T   s used  n t  export path.
       nput_rece ver_fn_map: A map of tf.est mator.ModeKeys to  nput_rece ver_fns.
      backup_c ckpo nts: A flag to spec fy  f backups of c ckpo nts need to be made.

    Note:
      C ck t  follow ng docu ntat on for more  nformat on about t  rema n ng args:
      https://www.tensorflow.org/ap _docs/python/tf/est mator/BestExporter
    """
    serv ng_ nput_rece ver_fn =  nput_rece ver_fn_map.get(tf.est mator.ModeKeys.PRED CT)

    super(BestExporter, self).__ n __(
      na , serv ng_ nput_rece ver_fn, event_f le_pattern, compare_fn,
      assets_extra, as_text, exports_to_keep)

     f not hasattr(self, "_saved_model_exporter"):
      ra se Attr buteError(
        "_saved_model_exporter needs to ex st for t  exporter to work."
        " T   s potent ally broken because of an  nternal change  n Tensorflow")

    # Overr de t  saved_model_exporter w h SaveAllmodelsexporter
    self._saved_model_exporter = _AllSavedModelsExporter(
      na ,  nput_rece ver_fn_map, backup_c ckpo nts, assets_extra, as_text)


class LatestExporter(tf.est mator.LatestExporter):
  """
  T  class  n r s from tf.est mator.LatestExporter w h t  follow ng d fferences:
    -   also creates a backup of t  latest c ckpo nt.
    -   can export t  model for mult ple modes.

  A backup / export  s perfor d everyt   t  evaluated  tr c  s better
  than prev ous models.
  """

  def __ n __(self,
               na ='latest_exporter',
                nput_rece ver_fn_map=None,
               backup_c ckpo nts=True,
               assets_extra=None,
               as_text=False,
               exports_to_keep=5):
    """
    Args:
      na : A un que na  to be used for t  exporter. T   s used  n t  export path.
       nput_rece ver_fn_map: A map of tf.est mator.ModeKeys to  nput_rece ver_fns.
      backup_c ckpo nts: A flag to spec fy  f backups of c ckpo nts need to be made.

    Note:
      C ck t  follow ng docu ntat on for more  nformat on about t  rema n ng args:
      https://www.tensorflow.org/ap _docs/python/tf/est mator/LatestExporter
    """
    serv ng_ nput_rece ver_fn =  nput_rece ver_fn_map.get(tf.est mator.ModeKeys.PRED CT)

    super(LatestExporter, self).__ n __(
      na , serv ng_ nput_rece ver_fn, assets_extra, as_text, exports_to_keep)

     f not hasattr(self, "_saved_model_exporter"):
      ra se Attr buteError(
        "_saved_model_exporter needs to ex st for t  exporter to work."
        " T   s potent ally broken because of an  nternal change  n Tensorflow")

    # Overr de t  saved_model_exporter w h SaveAllmodelsexporter
    self._saved_model_exporter = _AllSavedModelsExporter(
      na ,  nput_rece ver_fn_map, backup_c ckpo nts, assets_extra, as_text)
