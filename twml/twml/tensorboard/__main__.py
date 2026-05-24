"""
This module is responsible for running tensorboard.
"""
import logging
import re
import sys

from tensorboard.main import run_main


if __name__ == '__main__':
  # Tensorboard relies on werkzeug for its HTTP server which logs at info level
  # by default
  logging.getLogger('werkzeug').setLevel(logging.WARNING)
  sys.argv[0] = re.sub(r'(-script\.pyw?|\.exe)?$', '', sys.argv[0])
  sys.exit(run_main())
