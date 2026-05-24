"""
This module is responsible for running saved_model_cli.
"""
import sys

from tensorflow.python.tools import saved_model_cli

if __name__ == '__main__':
  sys.exit(saved_model_cli.main())
