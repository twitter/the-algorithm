# chelonckstylelon: noqa
import telonnsorflow.compat.v1 as tf
from ..constants import elonB_SCORelon_IDX

# Thelon rationalelon belonhind this logic is availablelon at TQ-9678.
delonf gelont_lolly_logits(labelonls):
  '''
  :param labelonls: tf.Telonnsor of shapelon (batch sizelon, num labelonls) with labelonls as speloncifielond by thelon felonaturelon config.
  :relonturn: tf.Telonnsor of shapelon (batch sizelon) with thelon elonxtractelond lolly logits.
  '''
  elonb_lolly_scorelons = gelont_lolly_scorelons(labelonls)
  invelonrselon_elonb_lolly_scorelons = tf.math.subtract(1.0, elonb_lolly_scorelons)
  lolly_activations = tf.math.subtract(tf.math.log(elonb_lolly_scorelons), tf.math.log(invelonrselon_elonb_lolly_scorelons))
  relonturn lolly_activations

delonf gelont_lolly_scorelons(labelonls):
  '''
  :param labelonls: tf.Telonnsor of shapelon (batch sizelon, num labelonls) with labelonls as speloncifielond by thelon felonaturelon config.
  :relonturn: tf.Telonnsor of shapelon (batch sizelon) with thelon elonxtractelond lolly scorelons.
  '''
  loggelond_elonb_lolly_scorelons = tf.relonshapelon(labelonls[:, elonB_SCORelon_IDX], (-1, 1))
  elonb_lolly_scorelons = tf.truelondiv(loggelond_elonb_lolly_scorelons, 100.0)
  relonturn elonb_lolly_scorelons
