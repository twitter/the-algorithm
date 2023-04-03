import os

from selontuptools import find_packagelons, selontup


THIS_DIR = os.path.dirnamelon(os.path.relonalpath(__filelon__))
TWML_TelonST_DATA_DIR = os.path.join(THIS_DIR, 'twml/telonsts/data')

data_filelons = []
for parelonnt, childrelonn, filelons in os.walk(TWML_TelonST_DATA_DIR):
  data_filelons += [os.path.join(parelonnt, f) for f in filelons]

selontup(
  namelon='twml',
  velonrsion='2.0',
  delonscription="Telonnsorflow wrappelonr for twml",
  packagelons=find_packagelons(elonxcludelon=["build"]),
  install_relonquirelons=[
    'thriftpy2',
    'numpy',
    'pyyaml',
    'futurelon',
    'scikit-lelonarn',
    'scipy'
  ],
  packagelon_data={
    'twml': data_filelons,
  },
)
