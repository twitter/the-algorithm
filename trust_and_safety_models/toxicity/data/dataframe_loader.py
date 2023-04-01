from abc import ABC, abstractmethod
from datetime import date
from importlib import import_module
import pickle

from toxicity_ml_pipeline.settings.default_settings_tox import (
  CLIENT,
  EXISTING_TASK_VERSIONS,
  GCS_ADDRESS,
  TRAINING_DATA_LOCATION,
)
from toxicity_ml_pipeline.utils.helpers import execute_command, execute_query
from toxicity_ml_pipeline.utils.queries import (
  FULL_QUERY,
  FULL_QUERY_W_TWEET_TYPES,
  PARSER_UDF,
  QUERY_SETTINGS,
)

import numpy as np
import pandas


class DataframeLoader(ABC):

  def __init__(self, project):
    self.project = project

  @abstractmethod
  def produce_query(self):
    pass

  @abstractmethod
  def load_data(self, test=False):
    pass


class ENLoader(DataframeLoader):
  def __init__(self, project, setting_file):
    super(ENLoader, self).__init__(project=project)
    self.date_begin = setting_file.DATE_BEGIN
    self.date_end = setting_file.DATE_END
    TASK_VERSION = setting_file.TASK_VERSION
    if TASK_VERSION not in EXISTING_TASK_VERSIONS:
      raise ValueError
    self.task_version = TASK_VERSION
    self.query_settings = dict(QUERY_SETTINGS)
    self.full_query = FULL_QUERY

  def produce_query(self, date_begin, date_end, task_version=None, **keys):
    task_version = self.task_version if task_version is None else task_version

    if task_version in keys["table"]:
      table_name = keys["table"][task_version]
      print(f"Loading {table_name}")

      main_query = keys["main"].format(
        table=table_name,
        parser_udf=PARSER_UDF[task_version],
        date_begin=date_begin,
        date_end=date_end,
      )

      return self.full_query.format(
        main_table_query=main_query, date_begin=date_begin, date_end=date_end
      )
    return ""

  def _reload(self, test, file_keyword):
    query = f"SELECT * from `{TRAINING_DATA_LOCATION.format(project=self.project)}_{file_keyword}`"

    if test:
      query += " ORDER BY RAND() LIMIT 1000"
    try:
      df = execute_query(client=CLIENT, query=query)
    except Exception:
      print(
        "Loading from BQ failed, trying to load from GCS. "
        "NB: use this option only for intermediate files, which will be deleted at the end of "
        "the project."
      )
      copy_cmd = f"gsutil cp {GCS_ADDRESS.format(project=self.project)}/training_data/{file_keyword}.pkl ."
      execute_command(copy_cmd)
      try:
        with open(f"{file_keyword}.pkl", "rb") as file:
          df = pickle.load(file)
      except Exception:
        return None

      if test:
        df = df.sample(frac=1)
        return df.iloc[:1000]

    return df

  def load_data(self, test=False, **kwargs):
    if "reload" in kwargs and kwargs["reload"]:
      df = self._reload(test, kwargs["reload"])
      if df is not None and df.shape[0] > 0:
        return df

    df = None
    query_settings = self.query_settings
    if test:
      query_settings = {"fairness": self.query_settings["fairness"]}
      query_settings["fairness"]["main"] += " LIMIT 500"

    for table, query_info in query_settings.items():
      curr_query = self.produce_query(
        date_begin=self.date_begin, date_end=self.date_end, **query_info
      )
      if curr_query == "":
        continue
      curr_df = execute_query(client=CLIENT, query=curr_query)
      curr_df["origin"] = table
      df = curr_df if df is None else pandas.concat((df, curr_df))

    df["loading_date"] = date.today()
    df["date"] = pandas.to_datetime(df.date)
    return df

  def load_precision_set(
    self, begin_date="...", end_date="...", with_tweet_types=False, task_version=3.5
  ):
    if with_tweet_types:
      self.full_query = FULL_QUERY_W_TWEET_TYPES

    query_settings = self.query_settings
    curr_query = self.produce_query(
      date_begin=begin_date,
      date_end=end_date,
      task_version=task_version,
      **query_settings["precision"],
    )
    curr_df = execute_query(client=CLIENT, query=curr_query)

    curr_df.rename(columns={"media_url": "media_presence"}, inplace=True)
    return curr_df


class ENLoaderWithSampling(ENLoader):

  keywords = {
    "politics": [
...
    ],
    "insults": [
...    
    ],
    "race": [
...
    ],
  }
  n = ...
  N = ...

  def __init__(self, project):
    self.raw_loader = ENLoader(project=project)
    if project == ...:
      self.project = project
    else:
      raise ValueError

  def sample_with_weights(self, df, n):
    w = df["label"].value_counts(normalize=True)[1]
    dist = np.full((df.shape[0],), w)
    sampled_df = df.sample(n=n, weights=dist, replace=False)
    return sampled_df

  def sample_keywords(self, df, N, group):
    print("\nmatching", group, "keywords...")

    keyword_list = self.keywords[group]
    match_df = df.loc[df.text.str.lower().str.contains("|".join(keyword_list), regex=True)]

    print("sampling N/3 from", group)
    if match_df.shape[0] <= N / 3:
      print(
        "WARNING: Sampling only",
        match_df.shape[0],
        "instead of",
        N / 3,
        "examples from race focused tweets due to insufficient data",
      )
      sample_df = match_df

    else:
      print(
        "sampling",
        group,
        "at",
        round(match_df["label"].value_counts(normalize=True)[1], 3),
        "% action rate",
      )
      sample_df = self.sample_with_weights(match_df, int(N / 3))
    print(sample_df.shape)
    print(sample_df.label.value_counts(normalize=True))

    print("\nshape of df before dropping sampled rows after", group, "matching..", df.shape[0])
    df = df.loc[
      df.index.difference(sample_df.index),
    ]
    print("\nshape of df after dropping sampled rows after", group, "matching..", df.shape[0])

    return df, sample_df

  def sample_first_set_helper(self, train_df, first_set, new_n):
    if first_set == "prev":
      fset = train_df.loc[train_df["origin"].isin(["prevalence", "causal prevalence"])]
      print(
        "sampling prev at", round(fset["label"].value_counts(normalize=True)[1], 3), "% action rate"
      )
    else:
      fset = train_df

    n_fset = self.sample_with_weights(fset, new_n)
    print("len of sampled first set", n_fset.shape[0])
    print(n_fset.label.value_counts(normalize=True))

    return n_fset

  def sample(self, df, first_set, second_set, keyword_sampling, n, N):
    train_df = df[df.origin != "precision"]
    val_test_df = df[df.origin == "precision"]

    print("\nsampling first set of data")
    new_n = n - N if second_set is not None else n
    n_fset = self.sample_first_set_helper(train_df, first_set, new_n)

    print("\nsampling second set of data")
    train_df = train_df.loc[
      train_df.index.difference(n_fset.index),
    ]

    if second_set is None:
      print("no second set sampling being done")
      df = n_fset.append(val_test_df)
      return df

    if second_set == "prev":
      sset = train_df.loc[train_df["origin"].isin(["prevalence", "causal prevalence"])]

    elif second_set == "fdr":
      sset = train_df.loc[train_df["origin"] == "fdr"]

    else: 
      sset = train_df

    if keyword_sampling == True:
      print("sampling based off of keywords defined...")
      print("second set is", second_set, "with length", sset.shape[0])

      sset, n_politics = self.sample_keywords(sset, N, "politics")
      sset, n_insults = self.sample_keywords(sset, N, "insults")
      sset, n_race = self.sample_keywords(sset, N, "race")

      n_sset = n_politics.append([n_insults, n_race]) 
      print("len of sampled second set", n_sset.shape[0])

    else:
      print(
        "No keyword sampling. Instead random sampling from",
        second_set,
        "at",
        round(sset["label"].value_counts(normalize=True)[1], 3),
        "% action rate",
      )
      n_sset = self.sample_with_weights(sset, N)
      print("len of sampled second set", n_sset.shape[0])
      print(n_sset.label.value_counts(normalize=True))

    df = n_fset.append([n_sset, val_test_df])
    df = df.sample(frac=1).reset_index(drop=True)

    return df

  def load_data(
    self, first_set="prev", second_set=None, keyword_sampling=False, test=False, **kwargs
  ):
    n = kwargs.get("n", self.n)
    N = kwargs.get("N", self.N)

    df = self.raw_loader.load_data(test=test, **kwargs)
    return self.sample(df, first_set, second_set, keyword_sampling, n, N)


class I18nLoader(DataframeLoader):
  def __init__(self):
    super().__init__(project=...)
    from archive.settings.... import ACCEPTED_LANGUAGES, QUERY_SETTINGS

    self.accepted_languages = ACCEPTED_LANGUAGES
    self.query_settings = dict(QUERY_SETTINGS)

  def produce_query(self, language, query, dataset, table, lang):
    query = query.format(dataset=dataset, table=table)
    add_query = f"AND reviewed.{lang}='{language}'"
    query += add_query

    return query

  def query_keys(self, language, task=2, size="50"):
    if task == 2:
      if language == "ar":
        self.query_settings["adhoc_v2"]["table"] = "..."
      elif language == "tr":
        self.query_settings["adhoc_v2"]["table"] = "..."
      elif language == "es":
        self.query_settings["adhoc_v2"]["table"] = f"..."
      else:
        self.query_settings["adhoc_v2"]["table"] = "..."

      return self.query_settings["adhoc_v2"]

    if task == 3:
      return self.query_settings["adhoc_v3"]

    raise ValueError(f"There are no other tasks than 2 or 3. {task} does not exist.")

  def load_data(self, language, test=False, task=2):
    if language not in self.accepted_languages:
      raise ValueError(
        f"Language not in the data {language}. Accepted values are " f"{self.accepted_languages}"
      )

    print(".... adhoc data")
    key_dict = self.query_keys(language=language, task=task)
    query_adhoc = self.produce_query(language=language, **key_dict)
    if test:
      query_adhoc += " LIMIT 500"
    adhoc_df = execute_query(CLIENT, query_adhoc)

    if not (test or language == "tr" or task == 3):
      if language == "es":
        print(".... additional adhoc data")
        key_dict = self.query_keys(language=language, size="100")
        query_adhoc = self.produce_query(language=language, **key_dict)
        adhoc_df = pandas.concat(
          (adhoc_df, execute_query(CLIENT, query_adhoc)), axis=0, ignore_index=True
        )

      print(".... prevalence data")
      query_prev = self.produce_query(language=language, **self.query_settings["prevalence_v2"])
      prev_df = execute_query(CLIENT, query_prev)
      prev_df["description"] = "Prevalence"
      adhoc_df = pandas.concat((adhoc_df, prev_df), axis=0, ignore_index=True)

    return self.clean(adhoc_df)
