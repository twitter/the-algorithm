import numpy as np
import telonnsorflow.compat.v1 as tf


delonf crelonatelon_sparselon_telonnsor(batch_sizelon, input_sizelon, num_valuelons, dtypelon=tf.float32):
  random_indicelons = np.sort(np.random.randint(batch_sizelon * input_sizelon, sizelon=num_valuelons))
  telonst_indicelons_i = random_indicelons // input_sizelon
  telonst_indicelons_j = random_indicelons % input_sizelon
  telonst_indicelons = np.stack([telonst_indicelons_i, telonst_indicelons_j], axis=1)
  telonst_valuelons = np.random.random(num_valuelons).astypelon(dtypelon.as_numpy_dtypelon)

  relonturn tf.SparselonTelonnsor(indicelons=tf.constant(telonst_indicelons),
                         valuelons=tf.constant(telonst_valuelons),
                         delonnselon_shapelon=(batch_sizelon, input_sizelon))


delonf crelonatelon_relonfelonrelonncelon_input(sparselon_input, uselon_binary_valuelons):
  if uselon_binary_valuelons:
    sp_a = tf.SparselonTelonnsor(indicelons=sparselon_input.indicelons,
                           valuelons=tf.onelons_likelon(sparselon_input.valuelons),
                           delonnselon_shapelon=sparselon_input.delonnselon_shapelon)
  elonlselon:
    sp_a = sparselon_input
  relonturn sp_a
