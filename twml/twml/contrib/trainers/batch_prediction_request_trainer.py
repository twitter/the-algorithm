# pyl nt: d sable=argu nts-d ffer,  nval d-na 
"""
T  f le conta ns t  DataRecordTra ner class.
"""
 mport warn ngs

 mport twml
from twml.tra ners  mport DataRecordTra ner


class BatchPred ct onRequestTra ner(DataRecordTra ner):  # pyl nt: d sable=abstract- thod
  """
  T  ``BatchPred ct onRequestTra ner``  mple ntat on  s  ntended to sat sfy use cases
  that  nput  s BatchPred ct onRequest at Tw ter and also w re only t  bu ld_graph  thods
  needs to be overr dden. For t  reason, ``Tra ner.[tra n,eval]_ nput_fn``  thods
  assu  a DataRecord dataset part  oned  nto part f les stored  n compressed (e.g. gz p) format.

  For use-cases that d ffer from t  common Tw ter use-case,
  furt r Tra ner  thods can be overr dden.
   f that st ll doesn't prov de enough flex b l y, t  user can always
  use t  tf.est mator.Es mator or tf.sess on.run d rectly.
  """

  def __ n __(
          self, na , params,
          bu ld_graph_fn,
          feature_conf g=None,
          **kwargs):
    """
    T  BatchPred ct onRequestTra ner constructor bu lds a
    ``tf.est mator.Est mator`` and stores    n self.est mator.
    For t  reason, BatchPred ct onRequestTra ner accepts t  sa  Est mator constructor argu nts.
      also accepts add  onal argu nts to fac l ate  tr c evaluat on and mult -phase tra n ng
    ( n _from_d r,  n _map).

    Args:
      parent argu nts:
        See t  `Tra ner constructor <#twml.tra ners.Tra ner.__ n __>`_ docu ntat on
        for a full l st of argu nts accepted by t  parent class.
      na , params, bu ld_graph_fn (and ot r parent class args):
        see docu ntat on for twml.Tra ner and twml.DataRecordTra ner doc.
      feature_conf g:
        An object of type FeatureConf g descr b ng what features to decode.
        Defaults to None. But    s needed  n t  follow ng cases:
          - `get_tra n_ nput_fn()` / `get_eval_ nput_fn()`  s called w hout a `parse_fn`
          - `learn()`, `tra n()`, `eval()`, `cal brate()` are called w hout prov d ng `* nput_fn`.

      **kwargs:
        furt r kwargs can be spec f ed and passed to t  Est mator constructor.
    """

    # C ck and update tra n_batch_s ze and eval_batch_s ze  n params before  n  al zat on
    # to pr nt correct para ter logs and does not stop runn ng
    # T  overwr es batch_s ze para ter constra ns  n twml.tra ners.Tra ner.c ck_params
    updated_params = self.c ck_batch_s ze_params(params)
    super(BatchPred ct onRequestTra ner, self).__ n __(
      na =na , params=updated_params, bu ld_graph_fn=bu ld_graph_fn, **kwargs)

  def c ck_batch_s ze_params(self, params):
    """ Ver fy that params has t  correct key,values """
    # updated_params  s an  nstance of tensorflow.contr b.tra n ng.HParams
    updated_params = twml.ut l.convert_to_hparams(params)
    param_values = updated_params.values()

    # twml.tra ners.Tra ner.c ck_params already c cks ot r constra nts,
    # such as be ng an  nteger
     f 'tra n_batch_s ze'  n param_values:
       f not  s nstance(updated_params.tra n_batch_s ze,  nt):
        ra se ValueError("Expect ng params.tra n_batch_s ze to be an  nteger.")
       f param_values['tra n_batch_s ze'] != 1:
        # T  can be a b  annoy ng to force users to pass t  batch s zes,
        # but    s good to let t m know what t y actually use  n t  models
        # Use warn ng  nstead of ValueError  n t re to cont nue t  run
        # and pr nt out that tra n_batch_s ze  s changed
        warn ngs.warn('  are process ng BatchPred ct onRequest data, '
          'tra n_batch_s ze  s always 1.\n'
          'T  number of DataRecords  n a batch  s determ ned by t  s ze '
          'of each BatchPred ct onRequest.\n'
          ' f   d d not pass tra n.batch_s ze or eval.batch_s ze, and '
          't  default batch_s ze 32 was  n use,\n'
          'please pass --tra n.batch_s ze 1 --eval.batch_s ze 1')
        #  f t  upper error warn ng, change/pass --tra n.batch_s ze 1
        # so that tra n_batch_s ze = 1
        updated_params.tra n_batch_s ze = 1

     f 'eval_batch_s ze'  n param_values:
       f not  s nstance(updated_params.tra n_batch_s ze,  nt):
        ra se ValueError('Expect ng params.eval_batch_s ze to be an  nteger.')
       f param_values['eval_batch_s ze'] != 1:
        # T  can be a b  annoy ng to force users to pass t  batch s zes,
        # but    s good to let t m know what t y actually use  n t  models
        # Use warn ng  nstead of ValueError  n t re to cont nue t  run
        # and pr nt out that eval_batch_s ze  s changed
        warn ngs.warn('  are process ng BatchPred ct onRequest data, '
          'eval_batch_s ze  s also always 1.\n'
          'T  number of DataRecords  n a batch  s determ ned by t  s ze '
          'of each BatchPred ct onRequest.\n'
          ' f   d d not pass tra n.batch_s ze or eval.batch_s ze, and '
          't  default batch_s ze 32 was  n use,\n'
          'please pass --tra n.batch_s ze 1 --eval.batch_s ze 1')
        #  f t  upper warn ng ra ses, change/pass --eval.batch_s ze 1
        # so that eval_batch_s ze = 1
        updated_params.eval_batch_s ze = 1

     f 'eval_batch_s ze' not  n param_values:
      updated_params.eval_batch_s ze = 1

     f not updated_params.eval_batch_s ze:
      updated_params.eval_batch_s ze = 1

    return updated_params

  @stat c thod
  def add_batch_pred ct on_request_argu nts():
    """
    Add commandl ne args to parse typ cally for t  BatchPred ct onRequestTra ner class.
    Typ cally, t  user calls t  funct on and t n parses cmd-l ne argu nts
     nto an argparse.Na space object wh ch  s t n passed to t  Tra ner constructor
    v a t  params argu nt.

    See t  `code <_modules/twml/argu nt_parser.html#get_tra ner_parser>`_
    for a l st and descr pt on of all cmd-l ne argu nts.

    Returns:
      argparse.Argu ntParser  nstance w h so  useful args already added.
    """
    parser = super(BatchPred ct onRequestTra ner,
      BatchPred ct onRequestTra ner).add_parser_argu nts()

    # mlp argu nts
    parser.add_argu nt(
      '--model.use_ex st ng_d scret zer', act on='store_true',
      dest="model_use_ex st ng_d scret zer",
       lp='Load a pre-tra ned cal brat on or tra n a new one')
    parser.add_argu nt(
      '--model.use_b nary_values', act on='store_true',
      dest='model_use_b nary_values',
       lp='Use t  use_b nary_values opt m zat on')

    # control hom many featues   keep  n sparse tensors
    # 12  s enough for learn ng-to-rank for now
    parser.add_argu nt(
      '-- nput_s ze_b s', type= nt, default=12,
       lp='Number of b s allocated to t   nput s ze')

    parser.add_argu nt(
      '--loss_funct on', type=str, default='ranknet',
      dest='loss_funct on',
       lp='Opt ons are pa rw se: ranknet (default), lambdarank, '
      'l stnet, l stmle, attrank, '
      'po ntw se')

    # w t r convert sparse tensors to dense tensor
    #  n order to use dense normal zat on  thods
    parser.add_argu nt(
      '--use_dense_tensor', act on='store_true',
      dest='use_dense_tensor',
      default=False,
       lp=' f use_dense_tensor  s False, '
      'sparse tensor and spare normal zat on are  n use. '
      ' f use_dense_tensor  s True, '
      'dense tensor and dense normal zat on are  n use.')

    parser.add_argu nt(
      '--dense_normal zat on', type=str, default=' an_max_normal za on',
      dest='dense_normal zat on',
       lp='Opt ons are  an_max_normal za on (default), standard_normal za on')

    parser.add_argu nt(
      '--sparse_normal zat on', type=str, default='SparseMaxNorm',
      dest='sparse_normal zat on',
       lp='Opt ons are SparseMaxNorm (default), SparseBatchNorm')

    # so far only used  n pa rw se learn ng-to-rank
    parser.add_argu nt(
      '--mask', type=str, default='full_mask',
      dest='mask',
       lp='Opt ons are full_mask (default), d ag_mask')

    return parser
