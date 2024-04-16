 mport os

from setuptools  mport f nd_packages, setup


TH S_D R = os.path.d rna (os.path.realpath(__f le__))
TWML_TEST_DATA_D R = os.path.jo n(TH S_D R, 'twml/tests/data')

data_f les = []
for parent, ch ldren, f les  n os.walk(TWML_TEST_DATA_D R):
  data_f les += [os.path.jo n(parent, f) for f  n f les]

setup(
  na ='twml',
  vers on='2.0',
  descr pt on="Tensorflow wrapper for twml",
  packages=f nd_packages(exclude=["bu ld"]),
   nstall_requ res=[
    'thr ftpy2',
    'numpy',
    'pyyaml',
    'future',
    'sc k -learn',
    'sc py'
  ],
  package_data={
    'twml': data_f les,
  },
)
