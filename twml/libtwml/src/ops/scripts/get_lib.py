"""Gets the path of headers for the current Tensorflow library"""

import tensorflow.compat.v1 as tf

print(tf.sysconfig.get_lib(), end='')
