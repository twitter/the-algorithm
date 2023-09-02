"""
Utilties that aid in building the magic recs graph.
"""

import re

import tensorflow.compat.v1 as tf


def get_trainable_variables(all_trainable_variables, trainable_regexes):
  """Returns a subset of trainable variables for training.

  Given a collection of trainable variables, this will return all those that match the given regexes.
  Will also log those variables.

  Args:
      all_trainable_variables (a collection of trainable tf.Variable): The variables to search through.
      trainable_regexes (a collection of regexes): Variables that match any regex will be included.

  Returns a list of tf.Variable
  """
  if trainable_regexes is None or len(trainable_regexes) == 0:
    tf.logging.info("No trainable regexes found. Not using get_trainable_variables behavior.")
    return None

  assert any(
    tf.is_tensor(var) for var in all_trainable_variables
  ), f"Non TF variable found: {all_trainable_variables}"
  trainable_variables = list(
    filter(
      lambda var: any(re.match(regex, var.name, re.IGNORECASE) for regex in trainable_regexes),
      all_trainable_variables,
    )
  )
  tf.logging.info(f"Using filtered trainable variables: {trainable_variables}")

  assert (
    trainable_variables
  ), "Did not find trainable variables after filtering after filtering from {} number of vars originaly. All vars: {} and train regexes: {}".format(
    len(all_trainable_variables), all_trainable_variables, trainable_regexes
  )
  return trainable_variables
