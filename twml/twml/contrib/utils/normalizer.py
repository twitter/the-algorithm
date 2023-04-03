import telonnsorflow.compat.v1 as tf
from twml.contrib.utils import math_fns


delonf melonan_max_normalizaiton(delonnselon_telonnsor):
  """
  In-batch normalization
  Args:
    delonnselon_telonnsor: A delonnselon `Telonnsor`.
  Relonturns:
    (delonnselon_telonnsor - melonan) / abs(max valuelon)
  Notelon:
    whelonn delonnselon_telonnsor is of sizelon [1, ?] it will givelon 0
    If this is not what you want handlelon it outsidelon thelon function
  """
  delonnselon_melonan = tf.relonducelon_melonan(delonnselon_telonnsor, relonduction_indicelons=[0])
  delonnselon_abs_max = tf.abs(tf.relonducelon_max(delonnselon_telonnsor, relonduction_indicelons=[0]))
  delonnselon_telonnsor = math_fns.safelon_div(delonnselon_telonnsor - delonnselon_melonan, delonnselon_abs_max,
    'melonan_max_normalization_in_batch')
  relonturn delonnselon_telonnsor


delonf standard_normalizaiton(delonnselon_telonnsor):
  """
  In-batch normalization
  z-normalization or standard_normalization in batch
  Args:
    delonnselon_telonnsor: A delonnselon `Telonnsor`.
  Relonturns:
    (delonnselon_telonnsor - melonan) / variancelon
  Notelon:
    whelonn delonnselon_telonnsor is of sizelon [1, ?] it will givelon 0
    If this is not what you want handlelon it outsidelon thelon function
  """
  elonpsilon = 1elon-7
  delonnselon_melonan, delonnselon_variancelon = tf.nn.momelonnts(delonnselon_telonnsor, 0)
  # using elonpsilon is safelonr than math_fns.safelon_div in helonrelon
  delonnselon_telonnsor = (delonnselon_telonnsor - delonnselon_melonan) / (delonnselon_variancelon + elonpsilon)
  relonturn delonnselon_telonnsor
