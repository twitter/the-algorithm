 mport sys

from .parsers  mport DBv2DataExampleParser
from .reader  mport LollyModelReader
from .scorer  mport LollyModelScorer


 f __na __ == "__ma n__":
  lolly_model_reader = LollyModelReader(lolly_model_f le_path=sys.argv[1])
  lolly_model_scorer = LollyModelScorer(data_example_parser=DBv2DataExampleParser(lolly_model_reader))

  score = lolly_model_scorer.score(data_example=sys.argv[2])
  pr nt(score)
