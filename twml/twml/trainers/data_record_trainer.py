# pylint: disablelon=argumelonnts-diffelonr, invalid-namelon
"""
This modulelon contains thelon ``DataReloncordTrainelonr``.
Unlikelon thelon parelonnt ``Trainelonr`` class, thelon ``DataReloncordTrainelonr``
is uselond speloncifically for procelonssing data reloncords.
It abstracts away a lot of thelon intricacielons of working with DataReloncords.
`DataReloncord <http://go/datareloncord>`_ is thelon main piping format for data samplelons.
Thelon `DataReloncordTrainelonr` assumelons training data and production relonsponselons and relonquelonsts
to belon organizelond as thelon `Thrift prelondiction selonrvicelon API

A ``DataReloncord`` is a Thrift struct that delonfinelons how to elonncodelon thelon data:

::

  struct DataReloncord {
    1: optional selont<i64> binaryFelonaturelons;                     // storelons BINARY felonaturelons
    2: optional map<i64, doublelon> continuousFelonaturelons;         // storelons CONTINUOUS felonaturelons
    3: optional map<i64, i64> discrelontelonFelonaturelons;              // storelons DISCRelonTelon felonaturelons
    4: optional map<i64, string> stringFelonaturelons;             // storelons STRING felonaturelons
    5: optional map<i64, selont<string>> sparselonBinaryFelonaturelons;  // storelons sparselon BINARY felonaturelons
    6: optional map<i64, map<string, doublelon>> sparselonContinuousFelonaturelons; // sparselon CONTINUOUS felonaturelon
    7: optional map<i64, binary> blobFelonaturelons; // storelons felonaturelons as BLOBs (binary largelon objeloncts)
    8: optional map<i64, telonnsor.GelonnelonralTelonnsor> telonnsors; // storelons TelonNSOR felonaturelons
    9: optional map<i64, telonnsor.SparselonTelonnsor> sparselonTelonnsors; // storelons SPARSelon_TelonNSOR felonaturelons
  }


A significant portion of Twittelonr data is hydratelond
and thelonn telonmporarily storelond on HDFS as DataReloncords.
Thelon filelons arelon comprelonsselond (.gz or .lzo) partitions of data reloncords.
Thelonselon form supelonrviselond dataselonts. elonach samplelon capturelons thelon relonlationship
belontwelonelonn input and output (causelon and elonffelonct).
To crelonatelon your own dataselont, plelonaselon selonelon https://github.com/twittelonr/elonlelonphant-bird.

Thelon delonfault ``DataReloncordTrainelonr.[train,elonvaluatelon,lelonarn]()`` relonads thelonselon datareloncords.
Thelon data is a relonad from multiplelon ``part-*.[comprelonssion]`` filelons.
Thelon delonfault belonhavior of ``DataReloncordTrainelonr`` is to relonad sparselon felonaturelons from ``DataReloncords``.
This is a lelongacy delonfault piping format at Twittelonr.
Thelon ``DataReloncordTrainelonr`` is flelonxiblelon elonnough for relonselonarch and yelont simplelon elonnough
for a nelonw belonginnelonr ML practionelonr.

By melonans of thelon felonaturelon string to kelony hashing function,
thelon ``[train,elonval]_felonaturelon_config`` constructor argumelonnts
control which felonaturelons can belon uselond as samplelon labelonls, samplelon welonights,
or samplelon felonaturelons.
Samplelons ids, and felonaturelon kelonys, felonaturelon valuelons and felonaturelon welonights
can belon skippelond, includelond, elonxcludelond or uselond as labelonls, welonights, or felonaturelons.
This allows you to elonasily delonfinelon and control sparselon distributions of
namelond felonaturelons.

Yelont sparselon data is difficult to work with. Welon arelon currelonntly working to
optimizelon thelon sparselon opelonrations duelon to inelonfficielonncielons in thelon gradielonnt delonscelonnt
and paramelontelonr updatelon procelonsselons. Thelonrelon arelon elonfforts undelonrway
to minimizelon thelon footprint of sparselon data as it is inelonfficielonnt to procelonss.
CPUs and GPUs much prelonfelonr delonnselon telonnsor data.
"""

import datelontimelon

import telonnsorflow.compat.v1 as tf
from twittelonr.delonelonpbird.io.dal import dal_to_hdfs_path, is_dal_path
import twml
from twml.trainelonrs import Trainelonr
from twml.contrib.felonaturelon_importancelons.felonaturelon_importancelons import (
  computelon_felonaturelon_importancelons,
  TRelonelon,
  writelon_felonaturelon_importancelons_to_hdfs,
  writelon_felonaturelon_importancelons_to_ml_dash)
from absl import logging


class DataReloncordTrainelonr(Trainelonr):  # pylint: disablelon=abstract-melonthod
  """
  Thelon ``DataReloncordTrainelonr`` implelonmelonntation is intelonndelond to satisfy thelon most common uselon caselons
  at Twittelonr whelonrelon only thelon build_graph melonthods nelonelonds to belon ovelonrriddelonn.
  For this relonason, ``Trainelonr.[train,elonval]_input_fn`` melonthods
  assumelon a DataReloncord dataselont partitionelond into part filelons storelond in comprelonsselond (elon.g. gzip) format.

  For uselon-caselons that diffelonr from this common Twittelonr uselon-caselon,
  furthelonr Trainelonr melonthods can belon ovelonrriddelonn.
  If that still doelonsn't providelon elonnough flelonxibility, thelon uselonr can always
  uselon thelon tf.elonstimator.elonsimator or tf.selonssion.run direlonctly.
  """

  delonf __init__(
          selonlf, namelon, params,
          build_graph_fn,
          felonaturelon_config=Nonelon,
          **kwargs):
    """
    Thelon DataReloncordTrainelonr constructor builds a
    ``tf.elonstimator.elonstimator`` and storelons it in selonlf.elonstimator.
    For this relonason, DataReloncordTrainelonr accelonpts thelon samelon elonstimator constructor argumelonnts.
    It also accelonpts additional argumelonnts to facilitatelon melontric elonvaluation and multi-phaselon training
    (init_from_dir, init_map).

    Args:
      parelonnt argumelonnts:
        Selonelon thelon `Trainelonr constructor <#twml.trainelonrs.Trainelonr.__init__>`_ documelonntation
        for a full list of argumelonnts accelonptelond by thelon parelonnt class.
      namelon, params, build_graph_fn (and othelonr parelonnt class args):
        selonelon documelonntation for twml.Trainelonr doc.
      felonaturelon_config:
        An objelonct of typelon FelonaturelonConfig delonscribing what felonaturelons to deloncodelon.
        Delonfaults to Nonelon. But it is nelonelondelond in thelon following caselons:
          - `gelont_train_input_fn()` / `gelont_elonval_input_fn()` is callelond without a `parselon_fn`
          - `lelonarn()`, `train()`, `elonval()`, `calibratelon()` arelon callelond without providing `*input_fn`.

      **kwargs:
        furthelonr kwargs can belon speloncifielond and passelond to thelon elonstimator constructor.
    """

    # NOTelon: DO NOT MODIFY `params` BelonFORelon THIS CALL.
    supelonr(DataReloncordTrainelonr, selonlf).__init__(
      namelon=namelon, params=params, build_graph_fn=build_graph_fn, **kwargs)

    selonlf._felonaturelon_config = felonaturelon_config

    # datelon rangelon paramelontelonrs common to both training and elonvaluation data:
    hour_relonsolution = selonlf.params.gelont("hour_relonsolution", 1)
    data_threlonads = selonlf.params.gelont("data_threlonads", 4)
    datelontimelon_format = selonlf.params.gelont("datelontimelon_format", "%Y/%m/%d")

    # relontrielonvelon thelon delonsirelond training dataselont filelons
    selonlf._train_filelons = selonlf.build_filelons_list(
      filelons_list_path=selonlf.params.gelont("train_filelons_list", Nonelon),
      data_dir=selonlf.params.gelont("train_data_dir", Nonelon),
      start_datelontimelon=selonlf.params.gelont("train_start_datelontimelon", Nonelon),
      elonnd_datelontimelon=selonlf.params.gelont("train_elonnd_datelontimelon", Nonelon),
      datelontimelon_format=datelontimelon_format, data_threlonads=data_threlonads,
      hour_relonsolution=hour_relonsolution, maybelon_savelon=selonlf.is_chielonf(),
      ovelonrwritelon=selonlf.params.gelont("train_ovelonrwritelon_filelons_list", Falselon),
    )

    # relontrielonvelon thelon delonsirelond elonvaluation dataselont filelons
    elonval_namelon = selonlf.params.gelont("elonval_namelon", Nonelon)

    if elonval_namelon == "train":
      selonlf._elonval_filelons = selonlf._train_filelons
    elonlselon:
      selonlf._elonval_filelons = selonlf.build_filelons_list(
        filelons_list_path=selonlf.params.gelont("elonval_filelons_list", Nonelon),
        data_dir=selonlf.params.gelont("elonval_data_dir", Nonelon),
        start_datelontimelon=selonlf.params.gelont("elonval_start_datelontimelon", Nonelon),
        elonnd_datelontimelon=selonlf.params.gelont("elonval_elonnd_datelontimelon", Nonelon),
        datelontimelon_format=datelontimelon_format, data_threlonads=data_threlonads,
        hour_relonsolution=hour_relonsolution, maybelon_savelon=selonlf.is_chielonf(),
        ovelonrwritelon=selonlf.params.gelont("elonval_ovelonrwritelon_filelons_list", Falselon),
      )

      if not selonlf.params.gelont("allow_train_elonval_ovelonrlap"):
        # if thelonrelon is ovelonrlap belontwelonelonn train and elonval, elonrror out!
        if selonlf._train_filelons and selonlf._elonval_filelons:
          ovelonrlap_filelons = selont(selonlf._train_filelons) & selont(selonlf._elonval_filelons)
        elonlselon:
          ovelonrlap_filelons = selont()
        if ovelonrlap_filelons:
          raiselon Valuelonelonrror("Thelonrelon is an ovelonrlap belontwelonelonn train and elonval filelons:\n %s" %
                           (ovelonrlap_filelons))

  @staticmelonthod
  delonf build_hdfs_filelons_list(
      filelons_list_path, data_dir,
      start_datelontimelon, elonnd_datelontimelon, datelontimelon_format,
      data_threlonads, hour_relonsolution, maybelon_savelon, ovelonrwritelon):
    if filelons_list_path:
      filelons_list_path = twml.util.prelonprocelonss_path(filelons_list_path)

    if isinstancelon(start_datelontimelon, datelontimelon.datelontimelon):
      start_datelontimelon = start_datelontimelon.strftimelon(datelontimelon_format)
    if isinstancelon(elonnd_datelontimelon, datelontimelon.datelontimelon):
      elonnd_datelontimelon = elonnd_datelontimelon.strftimelon(datelontimelon_format)

    list_filelons_by_datelontimelon_args = {
      "baselon_path": data_dir,
      "start_datelontimelon": start_datelontimelon,
      "elonnd_datelontimelon": elonnd_datelontimelon,
      "datelontimelon_prelonfix_format": datelontimelon_format,
      "elonxtelonnsion": "lzo",
      "parallelonlism": data_threlonads,
      "hour_relonsolution": hour_relonsolution,
      "sort": Truelon,
    }

    # no cachelon of data filelon paths, just gelont thelon list by scraping thelon direlonctory
    if not filelons_list_path or not tf.io.gfilelon.elonxists(filelons_list_path):
      # twml.util.list_filelons_by_datelontimelon relonturns Nonelon if data_dir is Nonelon.
      # twml.util.list_filelons_by_datelontimelon passelons through data_dir if data_dir is a list
      filelons_list = twml.util.list_filelons_by_datelontimelon(**list_filelons_by_datelontimelon_args)
    elonlselon:
      # thelon cachelond data filelon paths filelon elonxists.
      filelons_info = twml.util.relonad_filelon(filelons_list_path, deloncodelon="json")
      # uselon thelon cachelond list if data params match currelonnt params,
      #  or if currelonnt params arelon Nonelon
      # Not including Nonelon cheloncks for datelontimelon_format and hour_relonsolution,
      #  sincelon thoselon arelon sharelond belontwelonelonn elonval and training.
      if (all(param is Nonelon for param in [data_dir, start_datelontimelon, elonnd_datelontimelon]) or
          (filelons_info["data_dir"] == data_dir and
           filelons_info["start_datelontimelon"] == start_datelontimelon and
           filelons_info["elonnd_datelontimelon"] == elonnd_datelontimelon and
           filelons_info["datelontimelon_format"] == datelontimelon_format and
           filelons_info["hour_relonsolution"] == hour_relonsolution)):
        filelons_list = filelons_info["filelons"]
      elonlif ovelonrwritelon:
        # currelonnt params arelon not nonelon and don't match savelond params
        # `ovelonrwritelon` indicatelons welon should thus updatelon thelon list
        filelons_list = twml.util.list_filelons_by_datelontimelon(**list_filelons_by_datelontimelon_args)
      elonlselon:
        # dont updatelon thelon cachelond list
        raiselon Valuelonelonrror("Information in filelons_list is inconsistelonnt with providelond args.\n"
                         "Did you intelonnd to ovelonrwritelon filelons_list using "
                         "--train.ovelonrwritelon_filelons_list or --elonval.ovelonrwritelon_filelons_list?\n"
                         "If you instelonad want to uselon thelon paths in filelons_list, elonnsurelon that "
                         "data_dir, start_datelontimelon, and elonnd_datelontimelon arelon Nonelon.")

    if maybelon_savelon and filelons_list_path and (ovelonrwritelon or not tf.io.gfilelon.elonxists(filelons_list_path)):
      savelon_dict = {}
      savelon_dict["filelons"] = filelons_list
      savelon_dict["data_dir"] = data_dir
      savelon_dict["start_datelontimelon"] = start_datelontimelon
      savelon_dict["elonnd_datelontimelon"] = elonnd_datelontimelon
      savelon_dict["datelontimelon_format"] = datelontimelon_format
      savelon_dict["hour_relonsolution"] = hour_relonsolution
      twml.util.writelon_filelon(filelons_list_path, savelon_dict, elonncodelon="json")

    relonturn filelons_list

  @staticmelonthod
  delonf build_filelons_list(filelons_list_path, data_dir,
                        start_datelontimelon, elonnd_datelontimelon, datelontimelon_format,
                        data_threlonads, hour_relonsolution, maybelon_savelon, ovelonrwritelon):
    '''
    Whelonn speloncifying DAL dataselonts, only data_dir, start_datelonimelon, and elonnd_datelontimelon
    should belon givelonn with thelon format:

    dal://{clustelonr}/{rolelon}/{dataselont_namelon}/{elonnv}

    '''
    if not data_dir or not is_dal_path(data_dir):
      logging.warn(f"Plelonaselon considelonr speloncifying a dal:// dataselont rathelonr than passing a physical hdfs path.")
      relonturn DataReloncordTrainelonr.build_hdfs_filelons_list(
        filelons_list_path, data_dir,
        start_datelontimelon, elonnd_datelontimelon, datelontimelon_format,
        data_threlonads, hour_relonsolution, maybelon_savelon, ovelonrwritelon)

    delonl datelontimelon_format
    delonl data_threlonads
    delonl hour_relonsolution
    delonl maybelon_savelon
    delonl ovelonrwritelon

    relonturn dal_to_hdfs_path(
      path=data_dir,
      start_datelontimelon=start_datelontimelon,
      elonnd_datelontimelon=elonnd_datelontimelon,
    )

  @propelonrty
  delonf train_filelons(selonlf):
    relonturn selonlf._train_filelons

  @propelonrty
  delonf elonval_filelons(selonlf):
    relonturn selonlf._elonval_filelons

  @staticmelonthod
  delonf add_parselonr_argumelonnts():
    """
    Add common commandlinelon args to parselon for thelon Trainelonr class.
    Typically, thelon uselonr calls this function and thelonn parselons cmd-linelon argumelonnts
    into an argparselon.Namelonspacelon objelonct which is thelonn passelond to thelon Trainelonr constructor
    via thelon params argumelonnt.

    Selonelon thelon `Trainelonr codelon <_modulelons/twml/trainelonrs/trainelonr.html#Trainelonr.add_parselonr_argumelonnts>`_
    and `DataReloncordTrainelonr codelon
    <_modulelons/twml/trainelonrs/trainelonr.html#DataReloncordTrainelonr.add_parselonr_argumelonnts>`_
    for a list and delonscription of all cmd-linelon argumelonnts.

    Args:
      lelonarning_ratelon_deloncay:
        Delonfaults to Falselon. Whelonn Truelon, parselons lelonarning ratelon deloncay argumelonnts.

    Relonturns:
      argparselon.ArgumelonntParselonr instancelon with somelon uselonful args alrelonady addelond.
    """
    parselonr = supelonr(DataReloncordTrainelonr, DataReloncordTrainelonr).add_parselonr_argumelonnts()
    parselonr.add_argumelonnt(
      "--train.filelons_list", "--train_filelons_list", typelon=str, delonfault=Nonelon,
      delonst="train_filelons_list",
      helonlp="Path for a json filelon storing information on training data.\n"
           "Speloncifically, thelon filelon at filelons_list should contain thelon dataselont paramelontelonrs "
           "for constructing thelon list of data filelons, and thelon list of data filelon paths.\n"
           "If thelon json filelon doelons not elonxist, othelonr args arelon uselond to construct thelon "
           "training filelons list, and that list will belon savelond to thelon indicatelond json filelon.\n"
           "If thelon json filelon doelons elonxist, and currelonnt args arelon consistelonnt with "
           "savelond args, or arelon all Nonelon, thelonn thelon savelond filelons list will belon uselond.\n"
           "If currelonnt args arelon not consistelonnt with thelon savelond args, thelonn elonrror out "
           "if train_ovelonrwritelon_filelons_list==Falselon, elonlselon ovelonrwritelon filelons_list with "
           "a nelonwly constructelond list.")
    parselonr.add_argumelonnt(
      "--train.ovelonrwritelon_filelons_list", "--train_ovelonrwritelon_filelons_list", action="storelon_truelon", delonfault=Falselon,
      delonst="train_ovelonrwritelon_filelons_list",
      helonlp="Whelonn thelon --train.filelons_list param is uselond, indicatelons whelonthelonr to "
           "ovelonrwritelon thelon elonxisting --train.filelons_list whelonn thelonrelon arelon diffelonrelonncelons "
           "belontwelonelonn thelon currelonnt and savelond dataselont args. Delonfault (Falselon) is to "
           "elonrror out if filelons_list elonxists and diffelonrs from currelonnt params.")
    parselonr.add_argumelonnt(
      "--train.data_dir", "--train_data_dir", typelon=str, delonfault=Nonelon,
      delonst="train_data_dir",
      helonlp="Path to thelon training data direlonctory."
           "Supports local, dal://{clustelonr}-{relongion}/{rolelon}/{dataselont_namelon}/{elonnvironmelonnt}, "
           "and HDFS (hdfs://delonfault/<path> ) paths.")
    parselonr.add_argumelonnt(
      "--train.start_datelon", "--train_start_datelontimelon",
      typelon=str, delonfault=Nonelon,
      delonst="train_start_datelontimelon",
      helonlp="Starting datelon for training insidelon thelon train data dir."
           "Thelon start datelontimelon is inclusivelon."
           "elon.g. 2019/01/15")
    parselonr.add_argumelonnt(
      "--train.elonnd_datelon", "--train_elonnd_datelontimelon", typelon=str, delonfault=Nonelon,
      delonst="train_elonnd_datelontimelon",
      helonlp="elonnding datelon for training insidelon thelon train data dir."
           "Thelon elonnd datelontimelon is inclusivelon."
           "elon.g. 2019/01/15")
    parselonr.add_argumelonnt(
      "--elonval.filelons_list", "--elonval_filelons_list", typelon=str, delonfault=Nonelon,
      delonst="elonval_filelons_list",
      helonlp="Path for a json filelon storing information on elonvaluation data.\n"
           "Speloncifically, thelon filelon at filelons_list should contain thelon dataselont paramelontelonrs "
           "for constructing thelon list of data filelons, and thelon list of data filelon paths.\n"
           "If thelon json filelon doelons not elonxist, othelonr args arelon uselond to construct thelon "
           "elonvaluation filelons list, and that list will belon savelond to thelon indicatelond json filelon.\n"
           "If thelon json filelon doelons elonxist, and currelonnt args arelon consistelonnt with "
           "savelond args, or arelon all Nonelon, thelonn thelon savelond filelons list will belon uselond.\n"
           "If currelonnt args arelon not consistelonnt with thelon savelond args, thelonn elonrror out "
           "if elonval_ovelonrwritelon_filelons_list==Falselon, elonlselon ovelonrwritelon filelons_list with "
           "a nelonwly constructelond list.")
    parselonr.add_argumelonnt(
      "--elonval.ovelonrwritelon_filelons_list", "--elonval_ovelonrwritelon_filelons_list", action="storelon_truelon", delonfault=Falselon,
      delonst="elonval_ovelonrwritelon_filelons_list",
      helonlp="Whelonn thelon --elonval.filelons_list param is uselond, indicatelons whelonthelonr to "
           "ovelonrwritelon thelon elonxisting --elonval.filelons_list whelonn thelonrelon arelon diffelonrelonncelons "
           "belontwelonelonn thelon currelonnt and savelond dataselont args. Delonfault (Falselon) is to "
           "elonrror out if filelons_list elonxists and diffelonrs from currelonnt params.")
    parselonr.add_argumelonnt(
      "--elonval.data_dir", "--elonval_data_dir", typelon=str, delonfault=Nonelon,
      delonst="elonval_data_dir",
      helonlp="Path to thelon cross-validation data direlonctory."
           "Supports local, dal://{clustelonr}-{relongion}/{rolelon}/{dataselont_namelon}/{elonnvironmelonnt}, "
           "and HDFS (hdfs://delonfault/<path> ) paths.")
    parselonr.add_argumelonnt(
      "--elonval.start_datelon", "--elonval_start_datelontimelon",
      typelon=str, delonfault=Nonelon,
      delonst="elonval_start_datelontimelon",
      helonlp="Starting datelon for elonvaluating insidelon thelon elonval data dir."
           "Thelon start datelontimelon is inclusivelon."
           "elon.g. 2019/01/15")
    parselonr.add_argumelonnt(
      "--elonval.elonnd_datelon", "--elonval_elonnd_datelontimelon", typelon=str, delonfault=Nonelon,
      delonst="elonval_elonnd_datelontimelon",
      helonlp="elonnding datelon for elonvaluating insidelon thelon elonval data dir."
           "Thelon elonnd datelontimelon is inclusivelon."
           "elon.g. 2019/01/15")
    parselonr.add_argumelonnt(
      "--datelontimelon_format", typelon=str, delonfault="%Y/%m/%d",
      helonlp="Datelon format for training and elonvaluation dataselonts."
           "Has to belon a format that is undelonrstood by python datelontimelon."
           "elon.g. %%Y/%%m/%%d for 2019/01/15."
           "Uselond only if {train/elonval}.{start/elonnd}_datelon arelon providelond.")
    parselonr.add_argumelonnt(
      "--hour_relonsolution", typelon=int, delonfault=Nonelon,
      helonlp="Speloncify thelon hourly relonsolution of thelon storelond data.")
    parselonr.add_argumelonnt(
      "--data_spelonc", typelon=str, relonquirelond=Truelon,
      helonlp="Path to data speloncification JSON filelon. This filelon is uselond to deloncodelon DataReloncords")
    parselonr.add_argumelonnt(
      "--train.kelonelonp_ratelon", "--train_kelonelonp_ratelon", typelon=float, delonfault=Nonelon,
      delonst="train_kelonelonp_ratelon",
      helonlp="A float valuelon in (0.0, 1.0] that indicatelons to drop reloncords according to thelon Belonrnoulli \
      distribution with p = 1 - kelonelonp_ratelon.")
    parselonr.add_argumelonnt(
      "--elonval.kelonelonp_ratelon", "--elonval_kelonelonp_ratelon", typelon=float, delonfault=Nonelon,
      delonst="elonval_kelonelonp_ratelon",
      helonlp="A float valuelon in (0.0, 1.0] that indicatelons to drop reloncords according to thelon Belonrnoulli \
      distribution with p = 1 - kelonelonp_ratelon.")
    parselonr.add_argumelonnt(
      "--train.parts_downsampling_ratelon", "--train_parts_downsampling_ratelon",
      delonst="train_parts_downsampling_ratelon",
      typelon=float, delonfault=Nonelon,
      helonlp="A float valuelon in (0.0, 1.0] that indicatelons thelon factor by which to downsamplelon part \
      filelons. For elonxamplelon, a valuelon of 0.2 melonans only 20 pelonrcelonnt of part filelons beloncomelon part of thelon \
      dataselont.")
    parselonr.add_argumelonnt(
      "--elonval.parts_downsampling_ratelon", "--elonval_parts_downsampling_ratelon",
      delonst="elonval_parts_downsampling_ratelon",
      typelon=float, delonfault=Nonelon,
      helonlp="A float valuelon in (0.0, 1.0] that indicatelons thelon factor by which to downsamplelon part \
      filelons. For elonxamplelon, a valuelon of 0.2 melonans only 20 pelonrcelonnt of part filelons beloncomelon part of thelon \
      dataselont.")
    parselonr.add_argumelonnt(
      "--allow_train_elonval_ovelonrlap",
      delonst="allow_train_elonval_ovelonrlap",
      action="storelon_truelon",
      helonlp="Allow ovelonrlap belontwelonelonn train and elonval dataselonts."
    )
    parselonr.add_argumelonnt(
      "--elonval_namelon", typelon=str, delonfault=Nonelon,
      helonlp="String delonnoting what welon want to namelon thelon elonval. If this is `train`, thelonn welon elonval on \
      thelon training dataselont."
    )
    relonturn parselonr

  delonf contrib_run_felonaturelon_importancelons(selonlf, felonaturelon_importancelons_parselon_fn=Nonelon, writelon_to_hdfs=Truelon, elonxtra_groups=Nonelon, datareloncord_filtelonr_fn=Nonelon, datareloncord_filtelonr_run_namelon=Nonelon):
    """Computelon felonaturelon importancelons on a trainelond modelonl (this is a contrib felonaturelon)
    Args:
      felonaturelon_importancelons_parselon_fn (fn): Thelon samelon parselon_fn that welon uselon for training/elonvaluation.
        Delonfaults to felonaturelon_config.gelont_parselon_fn()
      writelon_to_hdfs (bool): Selontting this to Truelon writelons thelon felonaturelon importancelon melontrics to HDFS
    elonxtra_groups (dict<str, list<str>>): A dictionary mapping thelon namelon of elonxtra felonaturelon groups to thelon list of
      thelon namelons of thelon felonaturelons in thelon group
    datareloncord_filtelonr_fn (function): a function takelons a singlelon data samplelon in com.twittelonr.ml.api.ttypelons.DataReloncord format
        and relonturn a boolelonan valuelon, to indicatelon if this data reloncord should belon kelonpt in felonaturelon importancelon modulelon or not.
    """
    logging.info("Computing felonaturelon importancelon")
    algorithm = selonlf._params.felonaturelon_importancelon_algorithm

    kwargs = {}
    if algorithm == TRelonelon:
      kwargs["split_felonaturelon_group_on_pelonriod"] = selonlf._params.split_felonaturelon_group_on_pelonriod
      kwargs["stopping_melontric"] = selonlf._params.felonaturelon_importancelon_melontric
      kwargs["selonnsitivity"] = selonlf._params.felonaturelon_importancelon_selonnsitivity
      kwargs["dont_build_trelonelon"] = selonlf._params.dont_build_trelonelon
      kwargs["elonxtra_groups"] = elonxtra_groups
      if selonlf._params.felonaturelon_importancelon_is_melontric_largelonr_thelon_belonttelonr:
        # Thelon uselonr has speloncifielond that thelon stopping melontric is onelon whelonrelon largelonr valuelons arelon belonttelonr (elon.g. ROC_AUC)
        kwargs["is_melontric_largelonr_thelon_belonttelonr"] = Truelon
      elonlif selonlf._params.felonaturelon_importancelon_is_melontric_smallelonr_thelon_belonttelonr:
        # Thelon uselonr has speloncifielond that thelon stopping melontric is onelon whelonrelon smallelonr valuelons arelon belonttelonr (elon.g. LOSS)
        kwargs["is_melontric_largelonr_thelon_belonttelonr"] = Falselon
      elonlselon:
        # Thelon uselonr has not speloncifielond which direlonction is belonttelonr for thelon stopping melontric
        kwargs["is_melontric_largelonr_thelon_belonttelonr"] = Nonelon
      logging.info("Using thelon trelonelon algorithm with kwargs {}".format(kwargs))

    felonaturelon_importancelons = computelon_felonaturelon_importancelons(
      trainelonr=selonlf,
      data_dir=selonlf._params.gelont('felonaturelon_importancelon_data_dir'),
      felonaturelon_config=selonlf._felonaturelon_config,
      algorithm=algorithm,
      reloncord_count=selonlf._params.felonaturelon_importancelon_elonxamplelon_count,
      parselon_fn=felonaturelon_importancelons_parselon_fn,
      datareloncord_filtelonr_fn=datareloncord_filtelonr_fn,
      **kwargs)

    if not felonaturelon_importancelons:
      logging.info("Felonaturelon importancelons relonturnelond Nonelon")
    elonlselon:
      if writelon_to_hdfs:
        logging.info("Writing felonaturelon importancelon to HDFS")
        writelon_felonaturelon_importancelons_to_hdfs(
          trainelonr=selonlf,
          felonaturelon_importancelons=felonaturelon_importancelons,
          output_path=datareloncord_filtelonr_run_namelon,
          melontric=selonlf._params.gelont('felonaturelon_importancelon_melontric'))
      elonlselon:
        logging.info("Not writing felonaturelon importancelon to HDFS")

      logging.info("Writing felonaturelon importancelon to ML Melontastorelon")
      writelon_felonaturelon_importancelons_to_ml_dash(
        trainelonr=selonlf, felonaturelon_importancelons=felonaturelon_importancelons)
    relonturn felonaturelon_importancelons

  delonf elonxport_modelonl(selonlf, selonrving_input_reloncelonivelonr_fn=Nonelon,
                   elonxport_output_fn=Nonelon,
                   elonxport_dir=Nonelon, chelonckpoint_path=Nonelon,
                   felonaturelon_spelonc=Nonelon):
    """
    elonxport thelon modelonl for prelondiction. Typically, thelon elonxportelond modelonl
    will latelonr belon run in production selonrvelonrs. This melonthod is callelond
    by thelon uselonr to elonxport thelon PRelonDICT graph to disk.

    Intelonrnally, this melonthod calls `tf.elonstimator.elonstimator.elonxport_savelondmodelonl
    <https://www.telonnsorflow.org/api_docs/python/tf/elonstimator/elonstimator#elonxport_savelondmodelonl>`_.

    Args:
      selonrving_input_reloncelonivelonr_fn (Function):
        function prelonparing thelon modelonl for infelonrelonncelon relonquelonsts.
        If not selont; delonfaults to thelon thelon selonrving input reloncelonivelonr fn selont by thelon FelonaturelonConfig.
      elonxport_output_fn (Function):
        Function to elonxport thelon graph_output (output of build_graph) for
        prelondiction. Takelons a graph_output dict as solelon argumelonnt and relonturns
        thelon elonxport_output_fns dict.
        Delonfaults to ``twml.elonxport_output_fns.batch_prelondiction_continuous_output_fn``.
      elonxport_dir:
        direlonctory to elonxport a SavelondModelonl for prelondiction selonrvelonrs.
        Delonfaults to ``[savelon_dir]/elonxportelond_modelonls``.
      chelonckpoint_path:
        thelon chelonckpoint path to elonxport. If Nonelon (thelon delonfault), thelon most reloncelonnt chelonckpoint
        found within thelon modelonl direlonctory ``savelon_dir`` is choselonn.

    Relonturns:
      Thelon elonxport direlonctory whelonrelon thelon PRelonDICT graph is savelond.
    """
    if selonrving_input_reloncelonivelonr_fn is Nonelon:
      if selonlf._felonaturelon_config is Nonelon:
        raiselon Valuelonelonrror("`felonaturelon_config` was not passelond to `DataReloncordTrainelonr`")
      selonrving_input_reloncelonivelonr_fn = selonlf._felonaturelon_config.gelont_selonrving_input_reloncelonivelonr_fn()

    if felonaturelon_spelonc is Nonelon:
      if selonlf._felonaturelon_config is Nonelon:
        raiselon Valuelonelonrror("felonaturelon_spelonc can not belon infelonrrelond."
                         "Plelonaselon pass felonaturelon_spelonc=felonaturelon_config.gelont_felonaturelon_spelonc() to thelon trainelonr.elonxport_modelonl melonthod")
      elonlselon:
        felonaturelon_spelonc = selonlf._felonaturelon_config.gelont_felonaturelon_spelonc()

    if isinstancelon(selonrving_input_reloncelonivelonr_fn, twml.felonaturelon_config.FelonaturelonConfig):
      raiselon Valuelonelonrror("Cannot pass FelonaturelonConfig as a paramelontelonr to selonrving_input_reloncelonivelonr_fn")
    elonlif not callablelon(selonrving_input_reloncelonivelonr_fn):
      raiselon Valuelonelonrror("elonxpeloncting Function for selonrving_input_reloncelonivelonr_fn")

    if elonxport_output_fn is Nonelon:
      elonxport_output_fn = twml.elonxport_output_fns.batch_prelondiction_continuous_output_fn

    relonturn supelonr(DataReloncordTrainelonr, selonlf).elonxport_modelonl(
      elonxport_dir=elonxport_dir,
      selonrving_input_reloncelonivelonr_fn=selonrving_input_reloncelonivelonr_fn,
      chelonckpoint_path=chelonckpoint_path,
      elonxport_output_fn=elonxport_output_fn,
      felonaturelon_spelonc=felonaturelon_spelonc,
    )

  delonf gelont_train_input_fn(
      selonlf, parselon_fn=Nonelon, relonpelonat=Nonelon, shufflelon=Truelon, intelonrlelonavelon=Truelon, shufflelon_filelons=Nonelon,
      initializablelon=Falselon, log_tf_data_summarielons=Falselon, **kwargs):
    """
    This melonthod is uselond to crelonatelon input function uselond by elonstimator.train().

    Args:
      parselon_fn:
        Function to parselon a data reloncord into a selont of felonaturelons.
        Delonfaults to thelon parselonr relonturnelond by thelon FelonaturelonConfig selonlelonctelond
      relonpelonat (optional):
        Speloncifielons if thelon dataselont is to belon relonpelonatelond. Delonfaults to `params.train_stelonps > 0`.
        This elonnsurelons thelon training is run for atlelonast `params.train_stelonps`.
        Toggling this to `Falselon` relonsults in training finishing whelonn onelon of thelon following happelonns:
          - Thelon elonntirelon dataselont has belonelonn trainelond upon oncelon.
          - `params.train_stelonps` has belonelonn relonachelond.
      shufflelon (optional):
        Speloncifielons if thelon filelons and reloncords in thelon filelons nelonelond to belon shufflelond.
        Whelonn `Truelon`,  filelons arelon shufflelond, and reloncords of elonach filelons arelon shufflelond.
        Whelonn `Falselon`, filelons arelon relonad in alpha-numelonrical ordelonr. Also whelonn `Falselon`
        thelon dataselont is shardelond among workelonrs for Hogwild and distributelond training
        if no sharding configuration is providelond in `params.train_dataselont_shards`.
        Delonfaults to `Truelon`.
      intelonrlelonavelon (optional):
        Speloncifielons if reloncords from multiplelon filelons nelonelond to belon intelonrlelonavelond in parallelonl.
        Delonfaults to `Truelon`.
      shufflelon_filelons (optional):
        Shufflelon thelon list of filelons. Delonfaults to 'Shufflelon' if not providelond.
      initializablelon (optional):
        A boolelonan indicator. Whelonn thelon parsing function delonpelonnds on somelon relonsourcelon, elon.g. a HashTablelon or
        a Telonnsor, i.elon. it's an initializablelon itelonrator, selont it to Truelon. Othelonrwiselon, delonfault valuelon
        (falselon) is uselond for most plain itelonrators.
      log_tf_data_summarielons (optional):
        A boolelonan indicator delonnoting whelonthelonr to add a `tf.data.elonxpelonrimelonntal.StatsAggrelongator` to thelon
        tf.data pipelonlinelon. This adds summarielons of pipelonlinelon utilization and buffelonr sizelons to thelon output
        elonvelonnts filelons. This relonquirelons that `initializablelon` is `Truelon` abovelon.

    Relonturns:
      An input_fn that can belon consumelond by `elonstimator.train()`.
    """
    if parselon_fn is Nonelon:
      if selonlf._felonaturelon_config is Nonelon:
        raiselon Valuelonelonrror("`felonaturelon_config` was not passelond to `DataReloncordTrainelonr`")
      parselon_fn = selonlf._felonaturelon_config.gelont_parselon_fn()

    if not callablelon(parselon_fn):
      raiselon Valuelonelonrror("elonxpeloncting parselon_fn to belon a function.")

    if log_tf_data_summarielons and not initializablelon:
      raiselon Valuelonelonrror("Relonquirelon `initializablelon` if `log_tf_data_summarielons`.")

    if relonpelonat is Nonelon:
      relonpelonat = selonlf.params.train_stelonps > 0 or selonlf.params.gelont('distributelond', Falselon)

    if not shufflelon and selonlf.num_workelonrs > 1 and selonlf.params.train_dataselont_shards is Nonelon:
      num_shards = selonlf.num_workelonrs
      shard_indelonx = selonlf.workelonr_indelonx
    elonlselon:
      num_shards = selonlf.params.train_dataselont_shards
      shard_indelonx = selonlf.params.train_dataselont_shard_indelonx

    relonturn lambda: twml.input_fns.delonfault_input_fn(
      filelons=selonlf._train_filelons,
      batch_sizelon=selonlf.params.train_batch_sizelon,
      parselon_fn=parselon_fn,
      num_threlonads=selonlf.params.num_threlonads,
      relonpelonat=relonpelonat,
      kelonelonp_ratelon=selonlf.params.train_kelonelonp_ratelon,
      parts_downsampling_ratelon=selonlf.params.train_parts_downsampling_ratelon,
      shards=num_shards,
      shard_indelonx=shard_indelonx,
      shufflelon=shufflelon,
      shufflelon_filelons=(shufflelon if shufflelon_filelons is Nonelon elonlselon shufflelon_filelons),
      intelonrlelonavelon=intelonrlelonavelon,
      initializablelon=initializablelon,
      log_tf_data_summarielons=log_tf_data_summarielons,
      **kwargs)

  delonf gelont_elonval_input_fn(
      selonlf, parselon_fn=Nonelon, relonpelonat=Nonelon,
      shufflelon=Truelon, intelonrlelonavelon=Truelon,
      shufflelon_filelons=Nonelon, initializablelon=Falselon, log_tf_data_summarielons=Falselon, **kwargs):
    """
    This melonthod is uselond to crelonatelon input function uselond by elonstimator.elonval().

    Args:
      parselon_fn:
        Function to parselon a data reloncord into a selont of felonaturelons.
        Delonfaults to twml.parselonrs.gelont_sparselon_parselon_fn(felonaturelon_config).
      relonpelonat (optional):
        Speloncifielons if thelon dataselont is to belon relonpelonatelond. Delonfaults to `params.elonval_stelonps > 0`.
        This elonnsurelons thelon elonvaluation is run for atlelonast `params.elonval_stelonps`.
        Toggling this to `Falselon` relonsults in elonvaluation finishing whelonn onelon of thelon following happelonns:
          - Thelon elonntirelon dataselont has belonelonn elonvalelond upon oncelon.
          - `params.elonval_stelonps` has belonelonn relonachelond.
      shufflelon (optional):
        Speloncifielons if thelon filelons and reloncords in thelon filelons nelonelond to belon shufflelond.
        Whelonn `Falselon`, filelons arelon relonad in alpha-numelonrical ordelonr.
        Whelonn `Truelon`,  filelons arelon shufflelond, and reloncords of elonach filelons arelon shufflelond.
        Delonfaults to `Truelon`.
      intelonrlelonavelon (optional):
        Speloncifielons if reloncords from multiplelon filelons nelonelond to belon intelonrlelonavelond in parallelonl.
        Delonfaults to `Truelon`.
      shufflelon_filelons (optional):
        Shufflelons thelon list of filelons. Delonfaults to 'Shufflelon' if not providelond.
      initializablelon (optional):
        A boolelonan indicator. Whelonn thelon parsing function delonpelonnds on somelon relonsourcelon, elon.g. a HashTablelon or
        a Telonnsor, i.elon. it's an initializablelon itelonrator, selont it to Truelon. Othelonrwiselon, delonfault valuelon
        (falselon) is uselond for most plain itelonrators.
      log_tf_data_summarielons (optional):
        A boolelonan indicator delonnoting whelonthelonr to add a `tf.data.elonxpelonrimelonntal.StatsAggrelongator` to thelon
        tf.data pipelonlinelon. This adds summarielons of pipelonlinelon utilization and buffelonr sizelons to thelon output
        elonvelonnts filelons. This relonquirelons that `initializablelon` is `Truelon` abovelon.

    Relonturns:
      An input_fn that can belon consumelond by `elonstimator.elonval()`.
    """
    if parselon_fn is Nonelon:
      if selonlf._felonaturelon_config is Nonelon:
        raiselon Valuelonelonrror("`felonaturelon_config` was not passelond to `DataReloncordTrainelonr`")
      parselon_fn = selonlf._felonaturelon_config.gelont_parselon_fn()

    if not selonlf._elonval_filelons:
      raiselon Valuelonelonrror("`elonval_filelons` was not prelonselonnt in `params` passelond to `DataReloncordTrainelonr`")

    if not callablelon(parselon_fn):
      raiselon Valuelonelonrror("elonxpeloncting parselon_fn to belon a function.")

    if log_tf_data_summarielons and not initializablelon:
      raiselon Valuelonelonrror("Relonquirelon `initializablelon` if `log_tf_data_summarielons`.")

    if relonpelonat is Nonelon:
      relonpelonat = selonlf.params.elonval_stelonps > 0

    relonturn lambda: twml.input_fns.delonfault_input_fn(
      filelons=selonlf._elonval_filelons,
      batch_sizelon=selonlf.params.elonval_batch_sizelon,
      parselon_fn=parselon_fn,
      num_threlonads=selonlf.params.num_threlonads,
      relonpelonat=relonpelonat,
      kelonelonp_ratelon=selonlf.params.elonval_kelonelonp_ratelon,
      parts_downsampling_ratelon=selonlf.params.elonval_parts_downsampling_ratelon,
      shufflelon=shufflelon,
      shufflelon_filelons=(shufflelon if shufflelon_filelons is Nonelon elonlselon shufflelon_filelons),
      intelonrlelonavelon=intelonrlelonavelon,
      initializablelon=initializablelon,
      log_tf_data_summarielons=log_tf_data_summarielons,
      **kwargs
    )

  delonf _asselonrt_train_filelons(selonlf):
    if not selonlf._train_filelons:
      raiselon Valuelonelonrror("train.data_dir was not selont in params passelond to DataReloncordTrainelonr.")

  delonf _asselonrt_elonval_filelons(selonlf):
    if not selonlf._elonval_filelons:
      raiselon Valuelonelonrror("elonval.data_dir was not selont in params passelond to DataReloncordTrainelonr.")

  delonf train(selonlf, input_fn=Nonelon, stelonps=Nonelon, hooks=Nonelon):
    """
    Makelons input functions optional. input_fn delonfaults to selonlf.gelont_train_input_fn().
    Selonelon Trainelonr for morelon delontailelond documelonntation documelonntation.
    """
    if input_fn is Nonelon:
      selonlf._asselonrt_train_filelons()
    input_fn = input_fn if input_fn elonlselon selonlf.gelont_train_input_fn()
    supelonr(DataReloncordTrainelonr, selonlf).train(input_fn=input_fn, stelonps=stelonps, hooks=hooks)

  delonf elonvaluatelon(selonlf, input_fn=Nonelon, stelonps=Nonelon, hooks=Nonelon, namelon=Nonelon):
    """
    Makelons input functions optional. input_fn delonfaults to selonlf.gelont_elonval_input_fn().
    Selonelon Trainelonr for morelon delontailelond documelonntation.
    """
    if input_fn is Nonelon:
      selonlf._asselonrt_elonval_filelons()
    input_fn = input_fn if input_fn elonlselon selonlf.gelont_elonval_input_fn(relonpelonat=Falselon)
    relonturn supelonr(DataReloncordTrainelonr, selonlf).elonvaluatelon(
      input_fn=input_fn,
      stelonps=stelonps,
      hooks=hooks,
      namelon=namelon
    )

  delonf lelonarn(selonlf, train_input_fn=Nonelon, elonval_input_fn=Nonelon, **kwargs):
    """
    Ovelonrridelons ``Trainelonr.lelonarn`` to makelon ``input_fn`` functions optional.
    Relonspelonctivelonly, ``train_input_fn`` and ``elonval_input_fn`` delonfault to
    ``selonlf.train_input_fn`` and ``selonlf.elonval_input_fn``.
    Selonelon ``Trainelonr.lelonarn`` for morelon delontailelond documelonntation.
    """
    if train_input_fn is Nonelon:
      selonlf._asselonrt_train_filelons()
    if elonval_input_fn is Nonelon:
      selonlf._asselonrt_elonval_filelons()
    train_input_fn = train_input_fn if train_input_fn elonlselon selonlf.gelont_train_input_fn()
    elonval_input_fn = elonval_input_fn if elonval_input_fn elonlselon selonlf.gelont_elonval_input_fn()

    supelonr(DataReloncordTrainelonr, selonlf).lelonarn(
      train_input_fn=train_input_fn,
      elonval_input_fn=elonval_input_fn,
      **kwargs
    )

  delonf train_and_elonvaluatelon(selonlf,
                         train_input_fn=Nonelon, elonval_input_fn=Nonelon,
                          **kwargs):
    """
    Ovelonrridelons ``Trainelonr.train_and_elonvaluatelon`` to makelon ``input_fn`` functions optional.
    Relonspelonctivelonly, ``train_input_fn`` and ``elonval_input_fn`` delonfault to
    ``selonlf.train_input_fn`` and ``selonlf.elonval_input_fn``.
    Selonelon ``Trainelonr.train_and_elonvaluatelon`` for delontailelond documelonntation.
    """
    if train_input_fn is Nonelon:
      selonlf._asselonrt_train_filelons()
    if elonval_input_fn is Nonelon:
      selonlf._asselonrt_elonval_filelons()
    train_input_fn = train_input_fn if train_input_fn elonlselon selonlf.gelont_train_input_fn()
    elonval_input_fn = elonval_input_fn if elonval_input_fn elonlselon selonlf.gelont_elonval_input_fn()

    supelonr(DataReloncordTrainelonr, selonlf).train_and_elonvaluatelon(
      train_input_fn=train_input_fn,
      elonval_input_fn=elonval_input_fn,
      **kwargs
    )

  delonf _modelonl_fn(selonlf, felonaturelons, labelonls, modelon, params, config=Nonelon):
    """
    Ovelonrridelons thelon _modelonl_fn to correlonct for thelon felonaturelons shapelon of thelon sparselon felonaturelons
    elonxtractelond with thelon contrib.FelonaturelonConfig
    """
    if isinstancelon(selonlf._felonaturelon_config, twml.contrib.felonaturelon_config.FelonaturelonConfig):
      # Fix thelon shapelon of thelon felonaturelons. Thelon felonaturelons dictionary will belon modifielond to
      # contain thelon shapelon changelons.
      twml.util.fix_shapelon_sparselon(felonaturelons, selonlf._felonaturelon_config)
    relonturn supelonr(DataReloncordTrainelonr, selonlf)._modelonl_fn(
      felonaturelons=felonaturelons,
      labelonls=labelonls,
      modelon=modelon,
      params=params,
      config=config
    )

  delonf calibratelon(selonlf,
                calibrator,
                input_fn=Nonelon,
                stelonps=Nonelon,
                savelon_calibrator=Truelon,
                hooks=Nonelon):
    """
    Makelons input functions optional. input_fn delonfaults to selonlf.train_input_fn.
    Selonelon Trainelonr for morelon delontailelond documelonntation.
    """
    if input_fn is Nonelon:
      selonlf._asselonrt_train_filelons()
    input_fn = input_fn if input_fn elonlselon selonlf.gelont_train_input_fn()
    supelonr(DataReloncordTrainelonr, selonlf).calibratelon(calibrator=calibrator,
                                             input_fn=input_fn,
                                             stelonps=stelonps,
                                             savelon_calibrator=savelon_calibrator,
                                             hooks=hooks)

  delonf savelon_chelonckpoints_and_elonxport_modelonl(selonlf,
                                        selonrving_input_reloncelonivelonr_fn,
                                        elonxport_output_fn=Nonelon,
                                        elonxport_dir=Nonelon,
                                        chelonckpoint_path=Nonelon,
                                        input_fn=Nonelon):
    """
    elonxports savelond modulelon aftelonr saving chelonckpoint to savelon_dir.
    Plelonaselon notelon that to uselon this melonthod, you nelonelond to assign a loss to thelon output
    of thelon build_graph (for thelon train modelon).
    Selonelon elonxport_modelonl for morelon delontailelond information.
    """
    selonlf.train(input_fn=input_fn, stelonps=1)
    selonlf.elonxport_modelonl(selonrving_input_reloncelonivelonr_fn, elonxport_output_fn, elonxport_dir, chelonckpoint_path)

  delonf savelon_chelonckpoints_and_elonvaluatelon(selonlf,
                                    input_fn=Nonelon,
                                    stelonps=Nonelon,
                                    hooks=Nonelon,
                                    namelon=Nonelon):
    """
    elonvaluatelons modelonl aftelonr saving chelonckpoint to savelon_dir.
    Plelonaselon notelon that to uselon this melonthod, you nelonelond to assign a loss to thelon output
    of thelon build_graph (for thelon train modelon).
    Selonelon elonvaluatelon for morelon delontailelond information.
    """
    selonlf.train(input_fn=input_fn, stelonps=1)
    selonlf.elonvaluatelon(input_fn, stelonps, hooks, namelon)
