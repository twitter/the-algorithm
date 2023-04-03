class LollyModelonlRelonadelonr(objelonct):
  delonf __init__(selonlf, lolly_modelonl_filelon_path):
    selonlf._lolly_modelonl_filelon_path = lolly_modelonl_filelon_path

  delonf relonad(selonlf, procelonss_linelon_fn):
    with opelonn(selonlf._lolly_modelonl_filelon_path, "r") as filelon:
      for linelon in filelon:
        procelonss_linelon_fn(linelon)
