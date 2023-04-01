import re

from twitter.deepbird.io.util import _get_feature_id


class Parser(object):
  def parse(self, line):
    match = re.search(self.pattern(), line)
    if match:
      return self._parse_match(match)
    return None

  def pattern(self):
    raise NotImplementedError

  def _parse_match(self, match):
    raise NotImplementedError


class BiasParser(Parser):
  '''
  Parses the bias feature available in lolly model tsv files.
  '''

  def pattern(self):
    '''
    Matches lines like:
      unified_engagement	bias	-0.935945
    :return: a RegEx that extracts feature weight.
    '''
    return r"\t(bias)\t([^\s]+)"

  def _parse_match(self, match):
    return float(match.group(2))


class BinaryFeatureParser(Parser):
  '''
  Parses binary features available in lolly model tsv files.
  '''

  def pattern(self):
    '''
    Matches lines like:
      unified_engagement	encoded_tweet_features.is_user_spam_flag	-0.181130
    :return: a RegEx that extracts feature name and weight.
    '''
    return r"\t([\w\.]+)\t([^\s]+)"

  def _parse_match(self, match):
    return (match.group(1), float(match.group(2)))


class DiscretizedFeatureParser(Parser):
  '''
  Parses discretized features available in lolly model tsv files.
  '''

  def pattern(self):
    '''
    Matches lines like:
      unified_engagement	encoded_tweet_features.user_reputation.dz/dz_model=mdl/dz_range=1.000000e+00_2.000000e+00	0.031004
    :return: a RegEx that extracts feature name, bin boundaries and weight.
    '''
    return r"([\w\.]+)\.dz\/dz_model=mdl\/dz_range=([^\s]+)\t([^\s]+)"

  def _parse_match(self, match):
    left_bin_side, right_bin_side = [float(number) for number in match.group(2).split("_")]
    return (
      match.group(1),
      left_bin_side,
      right_bin_side,
      float(match.group(3))
    )


class LollyModelFeaturesParser(Parser):
  def __init__(self, bias_parser=BiasParser(), binary_feature_parser=BinaryFeatureParser(), discretized_feature_parser=DiscretizedFeatureParser()):
    self._bias_parser = bias_parser
    self._binary_feature_parser = binary_feature_parser
    self._discretized_feature_parser = discretized_feature_parser

  def parse(self, lolly_model_reader):
    parsed_features = {
      "bias": None,
      "binary": {},
      "discretized": {}
    }
    def process_line_fn(line):
      bias_parser_result = self._bias_parser.parse(line)
      if bias_parser_result:
        parsed_features["bias"] = bias_parser_result
        return

      binary_feature_parser_result = self._binary_feature_parser.parse(line)
      if binary_feature_parser_result:
        name, value = binary_feature_parser_result
        parsed_features["binary"][name] = value
        return

      discretized_feature_parser_result = self._discretized_feature_parser.parse(line)
      if discretized_feature_parser_result:
        name, left_bin, right_bin, weight = discretized_feature_parser_result
        discretized_features = parsed_features["discretized"]
        if name not in discretized_features:
          discretized_features[name] = []
        discretized_features[name].append((left_bin, right_bin, weight))

    lolly_model_reader.read(process_line_fn)

    return parsed_features


class DBv2DataExampleParser(Parser):
  '''
  Parses data records printed by the DBv2 train.py build_graph function.
  Format: [[dbv2 logit]][[logged lolly logit]][[space separated feature ids]][[space separated feature values]]
  '''

  def __init__(self, lolly_model_reader, lolly_model_features_parser=LollyModelFeaturesParser()):
    self.features = lolly_model_features_parser.parse(lolly_model_reader)
    self.feature_name_by_dbv2_id = {}

    for feature_name in list(self.features["binary"].keys()) + list(self.features["discretized"].keys()):
      self.feature_name_by_dbv2_id[str(_get_feature_id(feature_name))] = feature_name

  def pattern(self):
    '''
    :return: a RegEx that extracts dbv2 logit, logged lolly logit, feature ids and feature values.
    '''
    return r"\[\[([\w\.\-]+)\]\]\[\[([\w\.\-]+)\]\]\[\[([\w\.\- ]+)\]\]\[\[([\w\. ]+)\]\]"

  def _parse_match(self, match):
    feature_ids = match.group(3).split(" ")
    feature_values = match.group(4).split(" ")

    value_by_feature_name = {}
    for index in range(len(feature_ids)):
      feature_id = feature_ids[index]
      if feature_id not in self.feature_name_by_dbv2_id:
        print("Missing feature with id: " + str(feature_id))
        continue
      value_by_feature_name[self.feature_name_by_dbv2_id[feature_id]] = float(feature_values[index])

    return value_by_feature_name
