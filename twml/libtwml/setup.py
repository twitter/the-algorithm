"""
l btwml setup.py module
"""
from setuptools  mport setup, f nd_packages

setup(
  na ='l btwml',
  vers on='2.0',
  descr pt on="Tensorflow C++ ops for twml",
  packages=f nd_packages(),
  data_f les=[('', ['l btwml_tf.so'])],
)
