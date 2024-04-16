from twml.tra ners  mport DataRecordTra ner

from .features  mport FEATURE_L ST_DEFAULT_PATH


def get_tra n ng_arg_parser():
  parser = DataRecordTra ner.add_parser_argu nts()

  parser.add_argu nt(
    "--feature_l st",
    default=FEATURE_L ST_DEFAULT_PATH,
    type=str,
     lp="Wh ch features to use for tra n ng",
  )

  parser.add_argu nt(
    "--param_f le",
    default=None,
    type=str,
     lp="Path to JSON f le conta n ng t  graph para ters.  f None, model w ll load default para ters.",
  )

  parser.add_argu nt(
    "--d rectly_export_best",
    default=False,
    act on="store_true",
     lp="w t r to d rectly_export best_c ckpo nt",
  )

  parser.add_argu nt(
    "--warm_start_from", default=None, type=str,  lp="model d r to warm start from"
  )

  parser.add_argu nt(
    "--warm_start_base_d r",
    default=None,
    type=str,
     lp="latest ckpt  n t  folder w ll be used to ",
  )

  parser.add_argu nt(
    "--model_type",
    default=None,
    type=str,
     lp="Wh ch type of model to tra n.",
  )
  return parser


def get_eval_arg_parser():
  parser = get_tra n ng_arg_parser()
  parser.add_argu nt(
    "--eval_c ckpo nt",
    default=None,
    type=str,
     lp="Wh ch c ckpo nt to use for evaluat on",
  )

  return parser
