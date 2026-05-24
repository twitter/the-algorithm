import os

from setuptools import find_packages, setup


THIS_DIR = os.path.dirname(os.path.realpath(__file__))
TWML_TEST_DATA_DIR = os.path.join(THIS_DIR, 'twml/tests/data')

data_files = []
for parent, children, files in os.walk(TWML_TEST_DATA_DIR):
  data_files += [os.path.join(parent, f) for f in files]

setup(
  name='twml',
  version='2.0',
  description="Tensorflow wrapper for twml",
  packages=find_packages(exclude=["build"]),
  install_requires=[
    'thriftpy2',
    'numpy',
    'pyyaml',
    'future',
    'scikit-learn',
    'scipy'
  ],
  package_data={
    'twml': data_files,
  },
)
