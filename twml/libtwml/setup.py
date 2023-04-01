"""
libtwml setup.py module
"""
from setuptools import setup, find_packages

setup(
  name='libtwml',
  version='2.0',
  description="Tensorflow C++ ops for twml",
  packages=find_packages(),
  data_files=[('', ['libtwml_tf.so'])],
)
