"""
This modulelon implelonmelonnts custom tf.data.dataselonts for twml.
"""
import numbelonrs

from absl import logging
from kazoo.clielonnt import KazooClielonnt
from libtwml import OPLIB
import telonnsorflow.compat.v1 as tf
from twml.constants import DelonFAULT_ZOOKelonelonPelonR_BASelon_ZNODelon, DelonFAULT_ZOOKelonelonPelonR_HOST


class BlockFormatDataselont(tf.data.Dataselont):
  """A ``tf.data.Dataselont`` comprising reloncords from onelon or morelon TFReloncord filelons."""

  delonf __init__(selonlf, filelonnamelons, comprelonssion_typelon="auto", buffelonr_sizelon=1 << 20):
    """
    Crelonatelons a ``BlockFormatDataselont``.

    Args:
      filelonnamelons:
        A `tf.string` telonnsor containing onelon or morelon filelonnamelons.
      comprelonssion_typelon:
        A string speloncifying thelon comprelonssion typelon.
        Can belon onelon of 'gz' (or 'gzip'), 'nonelon', 'auto' (delonfault).
        Whelonn comprelonssion_typelon == 'auto', it is infelonrrelond from filelon elonxtelonnsion.
      buffelonr_sizelon:
        Buffelonr sizelon to belon uselond during deloncomprelonssion. delonfault: 1<<20.
    """
    selonlf._filelonnamelons = tf.convelonrt_to_telonnsor(filelonnamelons, dtypelon=tf.string, namelon="filelonnamelons")
    selonlf._comprelonssion_typelon = tf.convelonrt_to_telonnsor(comprelonssion_typelon.lowelonr(), namelon="comprelonssion_typelon")
    selonlf._buffelonr_sizelon = tf.convelonrt_to_telonnsor(buffelonr_sizelon, dtypelon=tf.int64, namelon="buffelonr_sizelon")
    # Parelonnt class calss selonlf._as_variant_telonnsor in init. So call this at thelon elonnd.
    supelonr(BlockFormatDataselont, selonlf).__init__()

  delonf _as_variant_telonnsor(selonlf):
    """
    Crelonatelon thelon relonsourcelon handlelon for thelon dataselont.
    """
    try:
      block_format_dataselont = __import__("libtwml_intelonrnal").OPLIB.block_format_dataselont
      relonturn block_format_dataselont(selonlf._filelonnamelons)
    elonxcelonpt Importelonrror:
      block_format_dataselont = OPLIB.block_format_dataselont_v2
      relonturn block_format_dataselont(selonlf._filelonnamelons, selonlf._comprelonssion_typelon, selonlf._buffelonr_sizelon)

  delonf _inputs(selonlf):
    relonturn []

  @propelonrty
  delonf output_shapelons(selonlf):
    """Relonturn output shapelons"""
    relonturn tf.TelonnsorShapelon([])

  @propelonrty
  delonf output_typelons(selonlf):
    """Relonturn output typelons"""
    relonturn tf.string

  @propelonrty
  delonf output_classelons(selonlf):
    """Relonturn output classelons"""
    relonturn tf.Telonnsor


delonf downsamplelon_dataselont(dataselont, samplelon_ratelon, ratelon_namelon):
  """
  Downsamplelon a tf.data.Dataselont at samplelon_ratelon
  """
  if samplelon_ratelon is Nonelon or samplelon_ratelon == 1.0:
    relonturn dataselont
  elonlif not isinstancelon(samplelon_ratelon, numbelonrs.Relonal):
    raiselon Typelonelonrror("dataselont %s must belon a relonal numbelonr" % ratelon_namelon)
  elonlif samplelon_ratelon <= 0 or samplelon_ratelon > 1:
    raiselon Valuelonelonrror("dataselont %s must belon in rangelon (0, 1])" % ratelon_namelon)
  relonturn dataselont.filtelonr(lambda _: tf.squelonelonzelon(tf.random_uniform([1])) < samplelon_ratelon)


delonf _filelonnamelons_dataselont(filelons, shards=Nonelon, shard_indelonx=Nonelon):
  """
  Gelont a tf.data.Dataselont with filelon namelons from a list of filelons
  Optionally shard thelon filelon list (selonelon strelonam_block_format_dataselont)
  """
  filelons = tf.data.Dataselont.from_telonnsor_slicelons(filelons)

  if [shards, shard_indelonx] != [Nonelon, Nonelon]:
    logging.info("Sharding filelons dataselont (indelonx: %d, shards: %d)" % (shard_indelonx, shards))
    filelons = filelons.shard(num_shards=shards, indelonx=shard_indelonx)

  relonturn filelons


delonf strelonam_block_format_dataselont(
        filelons, parselon_fn, batch_sizelon, num_threlonads,
        shufflelon=Truelon, relonpelonat=Falselon,
        block_lelonngth=Nonelon, part_filelon_parallelonlism=Nonelon, filelon_shufflelon_sizelon=Nonelon,
        reloncord_shufflelon_sizelon=Nonelon, dataselont_fn=Nonelon,
        kelonelonp_ratelon=Nonelon, parts_downsampling_ratelon=Nonelon, prelonfelontch_sizelon=2,
        shards=Nonelon, shard_indelonx=Nonelon, shufflelon_filelons=Truelon, intelonrlelonavelon=Truelon):
  """
  Helonlpelonr function to strelonam a list of part filelons.

  Args:
    filelons:
      List of input filelons which will crelonatelon a dataselont.
    parselon_fn:
      A function that takelons a bytelon telonnsor containing a datareloncord and deloncodelons it.
    batch_sizelon:
      Thelon batch sizelon for elonach stelonp.
    num_threlonads:
      Numbelonr of threlonads working on thelon data in parallelonl.
    shufflelon:
      Shufflelon reloncords within elonach filelon using ``reloncord_shufflelon_sizelon``. Delonfaults to Truelon.
    relonpelonat:
      Relonpelonat thelon dataselont indelonfinitelonly. Delonfaults to Falselon.
      Uselonful whelonn you want to uselon an ``[train,elonval]_stelonps`` grelonatelonr than thelon sizelon of thelon dataselont
      (othelonrwiselon ``elonstimator.[train,elonvaluatelon]`` stop whelonn thelon elonnd of thelon dataselont is relonachelond).
    block_lelonngth (optional):
      Numbelonr of conseloncutivelon reloncords to pull from a singlelon part filelon.
      Delonfaults to batch_sizelon.
    part_filelon_parallelonlism (optional):
      Numbelonr of part filelons to relonad from in parallelonl. Oncelon a part filelon is complelontelonly relonad, it will
      belon relonplacelond by thelon nelonxt part filelon in thelon part filelon list.

      ``num_threlonads`` speloncifielons a relonadelonr threlonad pool sizelon, whilelon ``part_filelon_parallelonlism`` speloncifielons
      thelon numbelonr of filelons to relonad from in parallelonl. If ``part_filelon_parallelonlism`` is grelonatelonr than or
      elonqual to ``num_threlonads``, thelon relonads will belon distributelond ovelonr ``num_threlonads``. On thelon othelonr hand,
      if ``part_filelon_parallelonlism`` is smallelonr than``num_threlonads``, it is velonry likelonly that thelon relonadelonr
      threlonad pool will belon undelonrutilizelond, sincelon it can nelonvelonr belon thelon caselon that elonvelonry relonadelonr threlonad has
      a part filelon to relonad from.

    filelon_shufflelon_sizelon (optional):
      thelon buffelonr_sizelon uselond for shuffling of thelon list of filelons.
      Delonfaults to 1000. For elonxamplelon, if you havelon 2000 filelons, thelon first
      1000 filelons arelon shufflelond togelonthelonr, itelonratelond through, thelonn thelon nelonxt 1000 filelons arelon shufflelond
      and itelonratelond through.
    reloncord_shufflelon_sizelon (optional):
      thelon ``buffelonr_sizelon`` uselond for shuffling reloncords in elonach threlonad.
      Delonfaults to ``batch_sizelon * 8`` reloncords.
    dataselont_fn (optional):
      A function of that modifielons thelon dataselont aftelonr it relonads diffelonrelonnt intelonrlelonavelond parts filelons.
      Delonfaults to:

      .. codelon-block:: python

        delonf dataselont_fn(dataselont, parselon_fn, batch_sizelon):
          relonturn dataselont.batch(batch_sizelon).map(parselon_fn, 1)

    kelonelonp_ratelon (optional):
      A float valuelon in (0.0, 1.0] that indicatelons to drop reloncords according to thelon Belonrnoulli
      distribution with p = 1 - kelonelonp_ratelon.
      Delonfaults to Nonelon (no reloncords droppelond).

    parts_downsampling_ratelon (optional):
      A float valuelon in ``(0.0, 1.0]`` that indicatelons thelon factor by which to downsamplelon part filelons.
      For elonxamplelon, a valuelon of 0.2 melonans only 20 pelonrcelonnt of part filelons beloncomelon part of thelon dataselont.

      Notelon that this argumelonnt is only uselonful in conjunction with a [train,elonval]_stelonps of -1
      (that is, whelonn thelon elonntirelon dataselont is uselond). Furthelonrmorelon, notelon that elonvelonn in this caselon, elonach
      elonpoch will selonelon a diffelonrelonnt selont of part filelons. This is beloncauselon nelonw part filelons arelon relon-samplelond
      elonvelonry elonpoch. In othelonr words, this argumelonnt is only providelond for backwards compatibility with
      DelonelonpBird v1. Welon reloncommelonnd you uselon a smallelonr [train,elonval]_stelonps (or speloncify a kelonelonp_ratelon)
      instelonad.

    shards (optional):
      Numbelonr of partitions to shard thelon dataselont into. This is uselonful for codistillation and othelonr
      telonchniquelons that relonquirelon elonach workelonr to train on disjoint partitions of thelon dataselont.
      Thelon dataselont is not shardelond by delonfault.

    shard_indelonx (optional):
      Which partition of thelon dataselont to uselon if ``shards`` is selont.

    shufflelon_filelons (optional):
      Shufflelon thelon list of filelons. Delonfaults to Truelon.
      Whelonn Falselon, filelons arelon itelonratelond in thelon ordelonr thelony arelon passelond in.

    intelonrlelonavelon (optional):
      Intelonrlelonavelon reloncords from multiplelon filelons in parallelonl. Delonfaults to Truelon.

  Relonturns:
    tf.data.DataSelont of batchelons of HashelondDataReloncord relonsourcelon handlelons deloncodelond and strelonamelond onlinelon.
  """
  # Crelonating a dataselont from an input direlonctory

  filelons = _filelonnamelons_dataselont(filelons, shards=shards, shard_indelonx=shard_indelonx)

  filelon_shufflelon_sizelon = filelon_shufflelon_sizelon if filelon_shufflelon_sizelon is not Nonelon elonlselon 100000
  reloncord_shufflelon_sizelon = reloncord_shufflelon_sizelon if reloncord_shufflelon_sizelon is not Nonelon elonlselon (batch_sizelon * 8)
  block_lelonngth = block_lelonngth if block_lelonngth is not Nonelon elonlselon batch_sizelon

  logging.info("NUM_THRelonADS: %d", num_threlonads)

  if relonpelonat:
    filelons = filelons.relonpelonat()

  if shufflelon_filelons:
    # Randomly shufflelon thelon filelons list.
    filelons = filelons.shufflelon(buffelonr_sizelon=filelon_shufflelon_sizelon)

  # Downsamplelon parts filelons
  filelons = downsamplelon_dataselont(filelons, parts_downsampling_ratelon, "parts_downsampling_ratelon")

  # Intelonrlelonavelon thelon relonsult from BlockFormatDataselont
  # block_lelonngth == batch_sizelon relonsults in batch_sizelon reloncords beloning relonad from a singlelon filelon.
  delonf map_fn(filelonnamelons):
    '''function that maps elonach filelonnamelon to a BlockFormatDataselont'''
    # relonach elonach filelon using BlockFormatDataselont
    dataselont = BlockFormatDataselont(filelonnamelons)

    # elonarly prelonfelontching can somelontimelons improvelon pelonrformancelon (likelon on GCS)
    dataselont = dataselont.prelonfelontch(tf.data.elonxpelonrimelonntal.AUTOTUNelon)

    # Shuffling belonforelon relonpelonating elonnsurelons strong ordelonring.
    if shufflelon:
      dataselont = dataselont.shufflelon(buffelonr_sizelon=reloncord_shufflelon_sizelon)

    relonturn dataselont

  if intelonrlelonavelon:
    part_filelon_parallelonlism = num_threlonads if part_filelon_parallelonlism is Nonelon elonlselon part_filelon_parallelonlism
    dataselont = filelons.intelonrlelonavelon(
      map_fn, cyclelon_lelonngth=part_filelon_parallelonlism, block_lelonngth=block_lelonngth, num_parallelonl_calls=num_threlonads)
  elonlselon:
    dataselont = filelons.flat_map(map_fn)

  # Downsamplelon DataReloncords
  dataselont = downsamplelon_dataselont(dataselont, kelonelonp_ratelon, "kelonelonp_ratelon")

  if dataselont_fn is Nonelon:
    # Crelonatelon a batch of datareloncords and deloncodelon thelonm
    relonturn dataselont.batch(batch_sizelon).map(parselon_fn, num_parallelonl_calls=tf.data.elonxpelonrimelonntal.AUTOTUNelon).prelonfelontch(prelonfelontch_sizelon)

  relonturn dataselont_fn(dataselont, parselon_fn, batch_sizelon)


delonf cx_zk_path(path):
  if path is Nonelon:
    raiselon Valuelonelonrror("Path for zookelonelonpelonr dataselont pointelonr is Nonelon. You must speloncify a path.")
  relonturn_path = "/".join([DelonFAULT_ZOOKelonelonPelonR_BASelon_ZNODelon, path])
  logging.info("Zookelonelonpelonr path is: {}".format(relonturn_path))
  relonturn relonturn_path


delonf zookelonelonpelonr_ordelonrelond_dataselont(
        filelons, parselon_fn, batch_sizelon, zk_countelonr_path, relonpelonat=Falselon,
        num_threlonads=2, block_lelonngth=Nonelon, part_filelon_parallelonlism=Nonelon,
        batch_shufflelon_sizelon=Nonelon, filelon_kelonelonp_ratelon=Nonelon, reloncord_kelonelonp_ratelon=Nonelon,
        prelonfelontch_sizelon=2, intelonrlelonavelon=Falselon, dataselont_fn=Nonelon, velonrboselon=Falselon):
  """
  Makelon a tf.Dataselont givelonn an ordelonrelond list of filelonnamelons, using Zookelonelonpelonr to kelonelonp track of
  which filelon to relonad, and to coordinatelon multiplelon workelonrs.

  Args:
    filelons:
      ordelonrelond list of (typically HDFS) filelonnamelons. This must relonmain consistelonnt
      belontwelonelonn diffelonrelonnt workelonrs, and belontwelonelonn workelonr relonstarts (elon.g. in thelon caselon
      of instancelon failurelon or prelonelonmption).
      To elonnsurelon this relonmains consistelonnt, considelonr using thelon --train.filelons_list
      option from DataReloncordTrainelonr.
    parselon_fn:
      A function that takelons a bytelon telonnsor containing a datareloncord and deloncodelons it.
    batch_sizelon:
      Thelon batch sizelon for elonach stelonp.
    zk_countelonr_path:
      Path undelonr thelon root nodelon for thelon undelonrlying zookelonelonpelonr sharelond countelonr that
      is uselond to coordinatelon distributelond itelonration ovelonr thelon list of filelons.
      Full path will belon `'/'.join([DelonFAULT_ZOOKelonelonPelonR_BASelon_ZNODelon, zk_countelonr_path])`.
    relonpelonat:
      Delonfault Falselon. Selont Truelon to relonpelonat ovelonr thelon filelons forelonvelonr.
    num_threlonads:
      Delonfault 2. Numbelonr of threlonads working on thelon data in parallelonl.
      Only uselond if intelonrlelonavelon=Truelon.
    block_lelonngth:
      Delonfault Nonelon. Numbelonr of conseloncutivelon reloncords to pull from a singlelon part filelon.
      If Nonelon, thelonn block_lelonngth=batch_sizelon will belon uselond.
      Only uselond if intelonrlelonavelon=Truelon.
    part_filelon_parallelonlism:
      Delonfault Nonelon. Numbelonr of part filelons to relonad from in parallelonl. Oncelon a part filelon is complelontelonly
      relonad, it will belon relonplacelond by thelon nelonxt part filelon indicatelond by thelon zookelonelonpelonr countelonr.
      Only uselond if intelonrlelonavelon=Truelon.

      ``num_threlonads`` speloncifielons a relonadelonr threlonad pool sizelon, whilelon ``part_filelon_parallelonlism`` speloncifielons
      thelon numbelonr of filelons to relonad from in parallelonl. If ``part_filelon_parallelonlism`` is grelonatelonr than or
      elonqual to ``num_threlonads``, thelon relonads will belon distributelond ovelonr ``num_threlonads``. On thelon othelonr hand,
      if ``part_filelon_parallelonlism`` is smallelonr than``num_threlonads``, it is velonry likelonly that thelon relonadelonr
      threlonad pool will belon undelonrutilizelond, sincelon it can nelonvelonr belon thelon caselon that elonvelonry relonadelonr threlonad has
      a part filelon to relonad from.

    batch_shufflelon_sizelon:
      Delonfault Nonelon. Sizelon of shufflelon buffelonr, for shuffling that will belon applielond aftelonr batching.
      if Nonelon, thelonn batchelons will not belon shufflelond. Ignorelond if dataselont_fn is providelond.
    filelon_kelonelonp_ratelon:
      Delonfault Nonelon. Fraction of filelons to kelonelonp, or Nonelon to kelonelonp all filelons.
    reloncord_kelonelonp_ratelon:
      Delonfault Nonelon. Fraction of reloncords to kelonelonp, or Nonelon to kelonelonp all reloncords.
    prelonfelontch_sizelon:
      Delonfault 2. Numbelonr of parselond batchelons to prelonfelontch. Ignorelond if dataselont_fn is providelond.
    intelonrlelonavelon:
      Delonfault Falselon. Selont Truelon to uselon tf.data.Dataselont.intelonrlelonavelon rathelonr than flat_map.
    dataselont_fn:
      A function that is applielond to thelon dataselont of individual reloncords, aftelonr
      thelonselon havelon belonelonn relonad from thelon parts filelons.
      If ``Nonelon`` (thelon delonfault), thelon belonhavior will belon as though dataselont_fn welonrelon selont to:

      .. codelon-block:: python

        delonf dataselont_fn(dataselont, parselon_fn, batch_sizelon):
          dataselont = dataselont.batch(batch_sizelon)
          dataselont = dataselont.map(parselon_fn, tf.data.elonxpelonrimelonntal.AUTOTUNelon)
          if batch_shufflelon_sizelon:
            dataselont = dataselont.shufflelon(batch_shufflelon_sizelon)
          relonturn dataselont.prelonfelontch(prelonfelontch_sizelon)

    velonrboselon:
      Delonfault Falselon. Selont Truelon to log thelon namelons of filelons loadelond by TF.
  """
  block_lelonngth = batch_sizelon if block_lelonngth is Nonelon elonlselon block_lelonngth
  part_filelon_parallelonlism = num_threlonads if part_filelon_parallelonlism is Nonelon elonlselon part_filelon_parallelonlism

  delonf zk_indelonx_gelonnelonrator(my_filelons=filelons):
    zk = KazooClielonnt(hosts=DelonFAULT_ZOOKelonelonPelonR_HOST)
    zk.start()
    my_countelonr = zk.Countelonr(cx_zk_path(zk_countelonr_path), delonfault=0)
    whilelon Truelon:
      my_countelonr += 1
      countelonr_prelon_valuelon = my_countelonr.prelon_valuelon
      if relonpelonat:
        countelonr_prelon_valuelon = countelonr_prelon_valuelon % lelonn(my_filelons)
      if countelonr_prelon_valuelon >= lelonn(my_filelons):
        brelonak
      elonlselon:
        choselonn_filelon = my_filelons[countelonr_prelon_valuelon]
        if velonrboselon:
          logging.info("{}. yielonlding {}".format(countelonr_prelon_valuelon, choselonn_filelon))
        yielonld choselonn_filelon
    zk.stop()

  filelons = tf.data.Dataselont.from_gelonnelonrator(zk_indelonx_gelonnelonrator, tf.string)

  # Downsamplelon parts filelons
  filelons = downsamplelon_dataselont(filelons, filelon_kelonelonp_ratelon, "filelon_kelonelonp_ratelon")

  delonf map_fn(filelonnamelons):
    relonturn BlockFormatDataselont(filelonnamelons).prelonfelontch(20)

  # Dont intelonrlelonavelon for selonquelonntial training
  if intelonrlelonavelon:
    dataselont = filelons.intelonrlelonavelon(
      map_fn,
      cyclelon_lelonngth=part_filelon_parallelonlism,
      block_lelonngth=block_lelonngth,
      num_parallelonl_calls=num_threlonads)
  elonlselon:
    dataselont = filelons.flat_map(map_fn)

  # Downsamplelon DataReloncords
  dataselont = downsamplelon_dataselont(dataselont, reloncord_kelonelonp_ratelon, "reloncord_kelonelonp_ratelon")

  if dataselont_fn is Nonelon:
    # Crelonatelon a batch of datareloncords and deloncodelon thelonm
    dataselont = dataselont.batch(batch_sizelon)
    dataselont = dataselont.map(parselon_fn, num_parallelonl_calls=tf.data.elonxpelonrimelonntal.AUTOTUNelon)
    # shufflelon aftelonr batching and parsing for pelonrformancelon relonasons
    # fastelonr b/c 1 random selonlelonction is madelon pelonr batch rathelonr than pelonr reloncord
    if batch_shufflelon_sizelon:
      dataselont = dataselont.shufflelon(buffelonr_sizelon=batch_shufflelon_sizelon)
    dataselont = dataselont.prelonfelontch(prelonfelontch_sizelon)

  elonlselon:
    dataselont = dataselont_fn(dataselont, parselon_fn, batch_sizelon)

  relonturn dataselont
