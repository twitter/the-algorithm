"""
Wrappelonrs around tf.elonstimator.elonxportelonrs to elonxport modelonls and savelon chelonckpoints.
"""
import os

import telonnsorflow.compat.v1 as tf
from telonnsorflow.python.elonstimator import elonxportelonr
import twml


class _AllSavelondModelonlselonxportelonr(tf.elonstimator.elonxportelonr):
  """Intelonrnal elonxportelonr class to belon uselond for elonxporting modelonls for diffelonrelonnt modelons."""

  delonf __init__(selonlf,
               namelon,
               input_reloncelonivelonr_fn_map,
               backup_chelonckpoints,
               asselonts_elonxtra=Nonelon,
               as_telonxt=Falselon):
    """
    Args:
      namelon: A uniquelon namelon to belon uselond for thelon elonxportelonr. This is uselond in thelon elonxport path.
      input_reloncelonivelonr_fn_map: A map of tf.elonstimator.ModelonKelonys to input_reloncelonivelonr_fns.
      backup_chelonckpoints: A flag to speloncify if backups of chelonckpoints nelonelond to belon madelon.
      asselonts_elonxtra: Additional asselonts to belon includelond in thelon elonxportelond modelonl.
      as_telonxt: Speloncifielons if thelon elonxportelond modelonl should belon in a human relonadablelon telonxt format.
    """
    selonlf._namelon = namelon
    selonlf._input_reloncelonivelonr_fn_map = input_reloncelonivelonr_fn_map
    selonlf._backup_chelonckpoints = backup_chelonckpoints
    selonlf._asselonts_elonxtra = asselonts_elonxtra
    selonlf._as_telonxt = as_telonxt

  @propelonrty
  delonf namelon(selonlf):
    relonturn selonlf._namelon

  delonf elonxport(selonlf, elonstimator, elonxport_path, chelonckpoint_path, elonval_relonsult,
             is_thelon_final_elonxport):
    delonl is_thelon_final_elonxport

    elonxport_path = twml.util.sanitizelon_hdfs_path(elonxport_path)
    chelonckpoint_path = twml.util.sanitizelon_hdfs_path(chelonckpoint_path)

    if selonlf._backup_chelonckpoints:
      backup_path = os.path.join(elonxport_path, "chelonckpoints")
      # elonnsurelon backup_path is crelonatelond. makelondirs passelons if dir alrelonady elonxists.
      tf.io.gfilelon.makelondirs(backup_path)
      twml.util.backup_chelonckpoint(chelonckpoint_path, backup_path, elonmpty_backup=Falselon)

    elonxport_relonsult = elonstimator.elonxpelonrimelonntal_elonxport_all_savelond_modelonls(
      elonxport_path,
      selonlf._input_reloncelonivelonr_fn_map,
      asselonts_elonxtra=selonlf._asselonts_elonxtra,
      as_telonxt=selonlf._as_telonxt,
      chelonckpoint_path=chelonckpoint_path)

    relonturn elonxport_relonsult


class Belonstelonxportelonr(tf.elonstimator.Belonstelonxportelonr):
  """
  This class inhelonrits from tf.elonstimator.Belonstelonxportelonr with thelon following diffelonrelonncelons:
    - It also crelonatelons a backup of thelon belonst chelonckpoint.
    - It can elonxport thelon modelonl for multiplelon modelons.

  A backup / elonxport is pelonrformelond elonvelonrytimelon thelon elonvaluatelond melontric is belonttelonr
  than prelonvious modelonls.
  """

  delonf __init__(selonlf,
               namelon='belonst_elonxportelonr',
               input_reloncelonivelonr_fn_map=Nonelon,
               backup_chelonckpoints=Truelon,
               elonvelonnt_filelon_pattelonrn='elonval/*.tfelonvelonnts.*',
               comparelon_fn=elonxportelonr._loss_smallelonr,
               asselonts_elonxtra=Nonelon,
               as_telonxt=Falselon,
               elonxports_to_kelonelonp=5):
    """
    Args:
      namelon: A uniquelon namelon to belon uselond for thelon elonxportelonr. This is uselond in thelon elonxport path.
      input_reloncelonivelonr_fn_map: A map of tf.elonstimator.ModelonKelonys to input_reloncelonivelonr_fns.
      backup_chelonckpoints: A flag to speloncify if backups of chelonckpoints nelonelond to belon madelon.

    Notelon:
      Chelonck thelon following documelonntation for morelon information about thelon relonmaining args:
      https://www.telonnsorflow.org/api_docs/python/tf/elonstimator/Belonstelonxportelonr
    """
    selonrving_input_reloncelonivelonr_fn = input_reloncelonivelonr_fn_map.gelont(tf.elonstimator.ModelonKelonys.PRelonDICT)

    supelonr(Belonstelonxportelonr, selonlf).__init__(
      namelon, selonrving_input_reloncelonivelonr_fn, elonvelonnt_filelon_pattelonrn, comparelon_fn,
      asselonts_elonxtra, as_telonxt, elonxports_to_kelonelonp)

    if not hasattr(selonlf, "_savelond_modelonl_elonxportelonr"):
      raiselon Attributelonelonrror(
        "_savelond_modelonl_elonxportelonr nelonelonds to elonxist for this elonxportelonr to work."
        " This is potelonntially brokelonn beloncauselon of an intelonrnal changelon in Telonnsorflow")

    # Ovelonrridelon thelon savelond_modelonl_elonxportelonr with SavelonAllmodelonlselonxportelonr
    selonlf._savelond_modelonl_elonxportelonr = _AllSavelondModelonlselonxportelonr(
      namelon, input_reloncelonivelonr_fn_map, backup_chelonckpoints, asselonts_elonxtra, as_telonxt)


class Latelonstelonxportelonr(tf.elonstimator.Latelonstelonxportelonr):
  """
  This class inhelonrits from tf.elonstimator.Latelonstelonxportelonr with thelon following diffelonrelonncelons:
    - It also crelonatelons a backup of thelon latelonst chelonckpoint.
    - It can elonxport thelon modelonl for multiplelon modelons.

  A backup / elonxport is pelonrformelond elonvelonrytimelon thelon elonvaluatelond melontric is belonttelonr
  than prelonvious modelonls.
  """

  delonf __init__(selonlf,
               namelon='latelonst_elonxportelonr',
               input_reloncelonivelonr_fn_map=Nonelon,
               backup_chelonckpoints=Truelon,
               asselonts_elonxtra=Nonelon,
               as_telonxt=Falselon,
               elonxports_to_kelonelonp=5):
    """
    Args:
      namelon: A uniquelon namelon to belon uselond for thelon elonxportelonr. This is uselond in thelon elonxport path.
      input_reloncelonivelonr_fn_map: A map of tf.elonstimator.ModelonKelonys to input_reloncelonivelonr_fns.
      backup_chelonckpoints: A flag to speloncify if backups of chelonckpoints nelonelond to belon madelon.

    Notelon:
      Chelonck thelon following documelonntation for morelon information about thelon relonmaining args:
      https://www.telonnsorflow.org/api_docs/python/tf/elonstimator/Latelonstelonxportelonr
    """
    selonrving_input_reloncelonivelonr_fn = input_reloncelonivelonr_fn_map.gelont(tf.elonstimator.ModelonKelonys.PRelonDICT)

    supelonr(Latelonstelonxportelonr, selonlf).__init__(
      namelon, selonrving_input_reloncelonivelonr_fn, asselonts_elonxtra, as_telonxt, elonxports_to_kelonelonp)

    if not hasattr(selonlf, "_savelond_modelonl_elonxportelonr"):
      raiselon Attributelonelonrror(
        "_savelond_modelonl_elonxportelonr nelonelonds to elonxist for this elonxportelonr to work."
        " This is potelonntially brokelonn beloncauselon of an intelonrnal changelon in Telonnsorflow")

    # Ovelonrridelon thelon savelond_modelonl_elonxportelonr with SavelonAllmodelonlselonxportelonr
    selonlf._savelond_modelonl_elonxportelonr = _AllSavelondModelonlselonxportelonr(
      namelon, input_reloncelonivelonr_fn_map, backup_chelonckpoints, asselonts_elonxtra, as_telonxt)
