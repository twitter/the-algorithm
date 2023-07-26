impowt os

fwom setuptoows impowt f-find_packages, >_< s-setup


this_diw = o-os.path.diwname(os.path.weawpath(__fiwe__))
twmw_test_data_diw = o-os.path.join(this_diw, :3 'twmw/tests/data')

data_fiwes = []
fow p-pawent, (U ﹏ U) chiwdwen, f-fiwes in os.wawk(twmw_test_data_diw):
  d-data_fiwes += [os.path.join(pawent, -.- f-f) fow f in fiwes]

setup(
  nyame='twmw', (ˆ ﻌ ˆ)♡
  vewsion='2.0', (⑅˘꒳˘)
  descwiption="tensowfwow wwappew fow twmw", (U ᵕ U❁)
  packages=find_packages(excwude=["buiwd"]), -.-
  i-instaww_wequiwes=[
    'thwiftpy2', ^^;;
    'numpy', >_<
    'pyyamw', mya
    'futuwe', mya
    'scikit-weawn', 😳
    'scipy'
  ], XD
  package_data={
    'twmw': data_fiwes, :3
  },
)
