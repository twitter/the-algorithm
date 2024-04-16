"""
T  module  s respons ble for runn ng tensorboard.
"""
 mport logg ng
 mport re
 mport sys

from tensorboard.ma n  mport run_ma n


 f __na __ == '__ma n__':
  # Tensorboard rel es on  rkzeug for  s HTTP server wh ch logs at  nfo level
  # by default
  logg ng.getLogger(' rkzeug').setLevel(logg ng.WARN NG)
  sys.argv[0] = re.sub(r'(-scr pt\.pyw?|\.exe)?$', '', sys.argv[0])
  sys.ex (run_ma n())
