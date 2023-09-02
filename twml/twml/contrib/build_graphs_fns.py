# pylint: disable=unused-argument, missing-docstring
"""
Common build graphs that can be reused
"""
import tensorflow.compat.v1 as tf


def get_saved_modules_graph(
    input_graph_fn: callable, params: dict, features: dict, mode: str = "train"
) -> dict:
    """
    Get common graph for stitching different saved modules for export.
    This graph is used to save checkpoints; and then export the modules
    as a unity.
    Args:
        features:
            model features
        params:
            model params
        input_graph_fn:
            main logic for the stitching
        mode:
            the mode of the graph
    Returns:
        output of input_graph_fn
    """

    output = input_graph_fn(features, params)
    # If mode is train, we just need to assign a dummy loss
    # and update the train op. This is done to save the graph to save_dir.
    if mode == "train":
        loss = tf.constant(1)
        train_op = tf.assign_add(tf.train.get_global_step(), 1)
        return {"train_op": train_op, "loss": loss}
    return output
