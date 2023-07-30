import sys

from .parsers import DBv2DataExampleParser
from .reader import LollyModelReader
from .scorer import LollyModelScorer


if __name__ == "__main__":
  lolly_model_reader = LollyModelReader(lolly_model_file_path=sys.argv[1])
  lolly_model_scorer = LollyModelScorer(data_example_parser=DBv2DataExampleParser(lolly_model_reader))

  score = lolly_model_scorer.score(data_example=sys.argv[2])
  print(score)
