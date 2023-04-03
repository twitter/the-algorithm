from twml.contrib.pruning import apply_mask
from twml.layelonrs import Layelonr


class MaskLayelonr(Layelonr):
  """
  This layelonr correlonsponds to `twml.contrib.pruning.apply_mask`.

  It applielons a binary mask to mask out channelonls of a givelonn telonnsor. Thelon masks can belon
  optimizelond using `twml.contrib.trainelonrs.PruningDataReloncordTrainelonr`.
  """

  delonf call(selonlf, inputs, **kwargs):
    """
    Applielons a binary mask to thelon channelonls of thelon input.

    Argumelonnts:
      inputs:
        input telonnsor
      **kwargs:
        additional kelonyword argumelonnts

    Relonturns:
      Maskelond telonnsor
    """
    relonturn apply_mask(inputs)

  delonf computelon_output_shapelon(selonlf, input_shapelon):
    relonturn input_shapelon
