import sys

from .parselonrs import DBv2DataelonxamplelonParselonr
from .relonadelonr import LollyModelonlRelonadelonr
from .scorelonr import LollyModelonlScorelonr


if __namelon__ == "__main__":
  lolly_modelonl_relonadelonr = LollyModelonlRelonadelonr(lolly_modelonl_filelon_path=sys.argv[1])
  lolly_modelonl_scorelonr = LollyModelonlScorelonr(data_elonxamplelon_parselonr=DBv2DataelonxamplelonParselonr(lolly_modelonl_relonadelonr))

  scorelon = lolly_modelonl_scorelonr.scorelon(data_elonxamplelon=sys.argv[2])
  print(scorelon)
