from typing import Callable, List, Union

import tensorflow.compat.v1 as tf
from twitter.deepbird.compat.v1.rnn import stack_bidirectional_dynamic_rnn

import twml


def _get_rnn_cell_creator(cell_type: str):
    if cell_type == "LSTM":
        Cell = tf.nn.rnn_cell.LSTMCell
    elif cell_type == "GRU":
        Cell = tf.nn.rnn_cell.GRUCell
    else:
        raise ValueError(
            "cell_type: %s is not supported."
            "It should be one of 'LSTM' or 'GRU'." % cell_type
        )
    return Cell


def _apply_dropout_wrapper(
    rnn_cells: List[tf.nn.rnn_cell.RNNCell],
    dropout: List[float],
) -> List[tf.nn.rnn_cell.RNNCell]:
    """Apply dropout wrapper around each cell if necessary"""

    if any(
        [rnn_cells is None, len(rnn_cells) == 0, dropout is None, len(dropout) == 0]
    ):
        return None

    cells = []
    for i, dropout_rate in enumerate(dropout):
        cell = rnn_cells[i]
        if dropout_rate > 0:
            cell = tf.nn.rnn_cell.DropoutWrapper(
                cell, input_keep_prob=(1.0 - dropout_rate)
            )
        cells.append(cell)
    return cells


def _create_bidirectional_rnn_cell(
    num_units: List[int],
    dropout: List[float],
    cell_type: str,
) -> Callable[[tf.Tensor, tf.Tensor], tf.Tensor]:
    """Create a bidirectional RNN cell."""

    scope_name = "lstm" if cell_type else "gru"
    with tf.variable_scope(scope_name):
        Cell = _get_rnn_cell_creator(cell_type)
        cells_forward = [Cell(output_size) for output_size in num_units]
        cells_backward = [Cell(output_size) for output_size in num_units]
        cells_forward = _apply_dropout_wrapper(cells_forward, dropout)
        cells_backward = _apply_dropout_wrapper(cells_backward, dropout)

    def stacked_rnn_cell(inputs: tf.Tensor, sequence_lengths: tf.Tensor) -> tf.Tensor:
        """Create a bidirectional RNN cell."""

        with tf.variable_scope(scope_name):
            outputs, final_states, _ = stack_bidirectional_dynamic_rnn(
                cells_fw=cells_forward,
                cells_bw=cells_backward,
                inputs=inputs,
                sequence_length=sequence_lengths,
                dtype=inputs.dtype,
            )
            return final_states[-1][-1]

    return stacked_rnn_cell


def _create_unidirectional_rnn_cell(
    num_units: List[int],
    dropout: List[float],
    cell_type: str,
) -> Callable[[tf.Tensor, tf.Tensor], tf.Tensor]:
    """Create a unidirectional RNN cell."""

    scope_name = "lstm" if cell_type else "gru"
    with tf.variable_scope(scope_name):
        Cell = _get_rnn_cell_creator(cell_type)
        cells = [Cell(output_size) for output_size in num_units]
        cells = _apply_dropout_wrapper(cells, dropout)
        multi_cell = tf.nn.rnn_cell.MultiRNNCell(cells)

    def stacked_rnn_cell(inputs: tf.Tensor, sequence_lengths: tf.Tensor) -> tf.Tensor:
        """Create a unidirectional RNN cell."""

        with tf.variable_scope(scope_name):
            outputs, final_states = tf.nn.static_rnn(
                multi_cell,
                tf.unstack(inputs, axis=1),
                dtype=inputs.dtype,
                sequence_length=sequence_lengths,
            )
            return final_states[-1].h

    return stacked_rnn_cell


def _create_regular_rnn_cell(
    num_units: List[int],
    dropout: List[float],
    cell_type: str,
    is_bidirectional: bool = True,
) -> Callable[[tf.Tensor, tf.Tensor], tf.Tensor]:
    if is_bidirectional:
        return _create_bidirectional_rnn_cell(num_units, dropout, cell_type)
    return _create_unidirectional_rnn_cell(num_units, dropout, cell_type)


class StackedRNN(twml.layers.Layer):
    """
    Layer for stacking RNN modules.
    This layer provides a unified interface for RNN modules that perform well on CPUs and GPUs.

    Args:
        num_units: int or list
            A list specifying the number of units per layer.
        dropout: float or list
            Dropout applied to the input of each cell.
            If list, has to dropout used for each layer.
            If number, the same amount of dropout is used everywhere.
            Defaults to 0.
        is_training: bool
            Flag to specify if the layer is used in training mode or not.
        cell_type: str
            Sepcifies the type of RNN. Can be "LSTM". "GRU" is not yet implemented.
        is_bidirectional: bool
            Specifies if the stacked RNN layer is bidirectional.
            This is for forward compatibility, this is not yet implemented.
            Defaults to False.
        name: str
            Name of the layer.
    """

    def __init__(
        self,
        num_units: Union[int, List[int]],
        dropout: Union[float, List[float]] = 0.0,
        is_training: bool = True,
        cell_type: str = "LSTM",
        is_bidirectional: bool = False,
        name: str = "stacked_rnn",
    ):
        super(StackedRNN, self).__init__(name=name)

        if is_bidirectional:
            raise NotImplementedError("Bidirectional RNN is not yet implemented")

        assert cell_type in ["LSTM", "GRU"]
        if cell_type != "LSTM":
            raise NotImplementedError("Only LSTMs are supported")

        # Make sure num_units is a list
        if not isinstance(num_units, (list, tuple)):
            num_units = [num_units]

        # Make sure dropout is a list
        self.num_layers = len(num_units)
        if not isinstance(dropout, (tuple, list)):
            dropout = [dropout] * self.num_layers

        # Check if all parameters are valid
        is_gpu_available = twml.contrib.utils.is_gpu_available()
        same_unit_size = all(size == num_units[0] for size in num_units)
        same_dropout_rate = any(val == dropout[0] for val in dropout)

        # set all class variables
        self.is_training = is_training
        self.stacked_rnn_cell = None
        self.num_units = num_units
        self.dropout = dropout
        self.cell_type = cell_type
        self.is_bidirectional = is_bidirectional

    def build(self, input_shape: tf.TensorShape):
        self.stacked_rnn_cell = _create_regular_rnn_cell(
            self.num_units, self.dropout, self.cell_type, self.is_bidirectional
        )

    def call(self, inputs: tf.Tensor, sequence_lengths: tf.Tensor) -> tf.Tensor:
        """
        Args:
            inputs:
                A tensor of size [batch_size, max_sequence_length, embedding_size].
            sequence_lengths:
                The length of each input sequence in the batch. Should be of size [batch_size].
        Returns:
            final_output
                The output of at the end of sequence_length.
        """
        return self.stacked_rnn_cell(inputs, sequence_lengths)


def stacked_rnn(
    inputs: tf.Tensor,
    sequence_lengths: tf.Tensor,
    num_units: List[int],
    dropout: Union[float, List[float]] = 0.0,
    is_training: bool = True,
    cell_type: str = "LSTM",
    is_bidirectional: bool = False,
    name: str = "stacked_rnn",
) -> StackedRNN:
    """Functional interface for StackedRNN

    Args:
        inputs:
            A tensor of size [batch_size, max_sequence_length, embedding_size].
        sequence_lengths:
            The length of each input sequence in the batch. Should be of size [batch_size].
        num_units:
            A list specifying the number of units per layer.
        dropout:
            Dropout applied to the input of each cell.
            If list, has to dropout used for each layer.
            If number, the same amount of dropout is used everywhere.
            Defaults to 0.
        is_training:
            Flag to specify if the layer is used in training mode or not.
        cell_type:
            Specifies the type of RNN. Can be "LSTM" or "GRU".
        is_bidirectional:
            Specifies if the stacked RNN layer is bidirectional.
            Defaults to False.

    Returns
        outputs, state.
    """
    rnn = StackedRNN(num_units, dropout, is_training, cell_type, is_bidirectional, name)
    return rnn(inputs, sequence_lengths)
