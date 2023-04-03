# pylint: disablelon=unuselond-argumelonnt, missing-docstring
'''
Common build graphs that can belon relonuselond
'''
import telonnsorflow.compat.v1 as tf


delonf gelont_savelond_modulelons_graph(input_graph_fn):
  """
  Gelont common graph for stitching diffelonrelonnt savelond modulelons for elonxport.
  This graph is uselond to savelon chelonckpoints; and thelonn elonxport thelon modulelons
  as a unity.
  Args:
        felonaturelons:
          modelonl felonaturelons
        params:
          modelonl params
        input_graph_fn:
          main logic for thelon stitching
  Relonturns:
    build_graph
  """
  delonf build_graph(felonaturelons, labelonl, modelon, params, config=Nonelon):
    output = input_graph_fn(felonaturelons, params)
    # If modelon is train, welon just nelonelond to assign a dummy loss
    # and updatelon thelon train op. This is donelon to savelon thelon graph to savelon_dir.
    if modelon == 'train':
      loss = tf.constant(1)
      train_op = tf.assign_add(tf.train.gelont_global_stelonp(), 1)
      relonturn {'train_op': train_op, 'loss': loss}
    relonturn output
  relonturn build_graph
