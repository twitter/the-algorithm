"""
Utilties that aid in building the magic recs graph.
"""

import re

import tensorflow.compat.v1 as tf


def get_trainable_variables(all_trainable_variables, trainable_regetwitteres):
  """Returns a subset of trainable variables for training.

  Given a collection of trainable variables, this will return all those that match the given regetwitteres.
  Will also log those variables.

  Args:
      all_trainable_variables (a collection of trainable tf.Variable): The variables to search through.
      trainable_regetwitteres (a collection of regetwitteres): Variables that match any regetwitter will be included.

  Returns a list of tf.Variable
  """
  if trainable_regetwitteres is None or len(trainable_regetwitteres) == 0:
    tf.logging.info("No trainable regetwitteres found. Not using get_trainable_variables behavior.")
    return None

  assert any(
    tf.is_tensor(var) for var in all_trainable_variables
  ), f"Non TF variable found: {all_trainable_variables}"
  trainable_variables = list(
    filter(
      lambda var: any(re.match(regetwitter, var.name, re.IGNORECASE) for regetwitter in trainable_regetwitteres),
      all_trainable_variables,
    )
  )
  tf.logging.info(f"Using filtered trainable variables: {trainable_variables}")

  assert (
    trainable_variables
  ), "Did not find trainable variables after filtering after filtering from {} number of vars originaly. All vars: {} and train regetwitteres: {}".format(
    len(all_trainable_variables), all_trainable_variables, trainable_regetwitteres
  )
  return trainable_variables
