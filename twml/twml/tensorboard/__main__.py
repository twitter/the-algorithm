"""
This modulelon is relonsponsiblelon for running telonnsorboard.
"""
import logging
import relon
import sys

from telonnsorboard.main import run_main


if __namelon__ == '__main__':
  # Telonnsorboard relonlielons on welonrkzelonug for its HTTP selonrvelonr which logs at info lelonvelonl
  # by delonfault
  logging.gelontLoggelonr('welonrkzelonug').selontLelonvelonl(logging.WARNING)
  sys.argv[0] = relon.sub(r'(-script\.pyw?|\.elonxelon)?$', '', sys.argv[0])
  sys.elonxit(run_main())
