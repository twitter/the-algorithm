_E='roc_auc'
_D='\n\nImportances computed for {} in {} seconds \n\n'
_C=False
_B='None'
_A=None
import time
from collections import defaultdict
from com.twitter.mlmetastore.modelrepo.client import ModelRepoClient
from com.twitter.mlmetastore.modelrepo.core import FeatureImportance,FeatureNames
from twitter.deepbird.io.util import match_feature_regex_list
from twml.contrib.feature_importances.helpers import _get_feature_name_from_config,_get_feature_types_from_records,_get_metrics_hook,_expand_prefix,longest_common_prefix,write_list_to_hdfs_gfile
from twml.contrib.feature_importances.feature_permutation import PermutedInputFnFactory
from twml.tracking import ExperimentTracker
from tensorflow.compat.v1 import logging
from requests.exceptions import HTTPError,RetryError
from queue import Queue
SERIAL='serial'
TREE='tree'
INDIVIDUAL='Individual'
GROUP='Group'
ROC_AUC=_E
RCE='rce'
LOSS='loss'
def _repartition(feature_list_queue,fnames_ftypes,split_feature_group_on_period):
	'\n  Iterate through letters to partition each feature by prefix, and then put each tuple\n    (prefix, feature_partition) into the feature_list_queue\n  Args:\n    prefix (str): The prefix shared by each feature in list_of_feature_types\n    feature_list_queue (Queue<(str, list<(str, str)>)>): The queue of feature groups\n    fnames_ftypes (list<(str, str)>): List of (fname, ftype) pairs. Each fname begins with prefix\n    split_feature_group_on_period (str): If true, require that feature groups end in a period\n  Returns:\n    Updated queue with each group in fnames_ftypes\n  ';E=feature_list_queue;A=fnames_ftypes;assert len(A)>1;B='.'if split_feature_group_on_period else _A;F=longest_common_prefix(strings=[A for(A,B)in A],split_character=B);G=defaultdict(list)
	for (C,J) in A:assert C.startswith(F);D=_expand_prefix(fname=C,prefix=F,split_character=B);G[D].append((C,J))
	for (D,H) in G.items():I=longest_common_prefix(strings=[A for(A,B)in H],split_character=B);assert I.startswith(D);E.put((I,H))
	return E
def _infer_if_is_metric_larger_the_better(stopping_metric):
	A=stopping_metric
	if A is _A:raise ValueError('Error: Stopping Metric cannot be None')
	elif A.startswith(LOSS):logging.info('Interpreting {} to be a metric where larger numbers are worse'.format(A));B=_C
	else:logging.info('Interpreting {} to be a metric where larger numbers are better'.format(A));B=True
	return B
def _check_whether_tree_should_expand(baseline_performance,computed_performance,sensitivity,stopping_metric,is_metric_larger_the_better):
	'\n  Returns True if\n    - the metric is positive (e.g. ROC_AUC) and computed_performance is nontrivially smaller than the baseline_performance\n    - the metric is negative (e.g. LOSS) and computed_performance is nontrivially larger than the baseline_performance\n  ';C=is_metric_larger_the_better;D=sensitivity;E=baseline_performance;B=stopping_metric;A=(E[B]-computed_performance[B])/E[B]
	if not C:A=-A
	logging.info('Found a {} difference of {}. Sensitivity is {}.'.format('positive'if C else'negative',A,D));return A>D
def _compute_multiple_permuted_performances_from_trainer(factory,fname_ftypes,trainer,parse_fn,record_count):'Compute performances with fname and fype permuted\n  ';A=trainer;B=_get_metrics_hook(A);A._estimator.evaluate(input_fn=factory.get_permuted_input_fn(batch_size=A._params.eval_batch_size,parse_fn=parse_fn,fname_ftypes=fname_ftypes),steps=(record_count+A._params.eval_batch_size)//A._params.eval_batch_size,hooks=[B],checkpoint_path=A.best_or_latest_checkpoint);return B.metric_values
def _get_extra_feature_group_performances(factory,trainer,parse_fn,extra_groups,feature_to_type,record_count):
	'Compute performance differences for the extra feature groups\n  ';B=feature_to_type;C={}
	for (A,E) in extra_groups.items():F=time.time();G=match_feature_regex_list(features=B.keys(),feature_regex_list=[A for A in E],preprocess=_C,as_dict=_C);D=[(A,B[A])for A in G];logging.info('Extracted extra group {} with features {}'.format(A,D));C[A]=_compute_multiple_permuted_performances_from_trainer(factory=factory,fname_ftypes=D,trainer=trainer,parse_fn=parse_fn,record_count=record_count);logging.info(_D.format(A,int(time.time()-F)))
	return C
def _feature_importances_tree_algorithm(data_dir,trainer,parse_fn,fnames,stopping_metric,file_list=_A,datarecord_filter_fn=_A,split_feature_group_on_period=True,record_count=99999,is_metric_larger_the_better=_A,sensitivity=0.025,extra_groups=_A,dont_build_tree=_C):
	"Tree algorithm for feature and feature group importances. This algorithm build a prefix tree of\n  the feature names and then traverses the tree with a BFS. At each node (aka group of features with\n  a shared prefix) the algorithm computes the performance of the model when we permute all features\n  in the group. The algorithm only zooms-in on groups that impact the performance by more than\n  sensitivity. As a result, features that affect the model performance by less than sensitivity will\n  not have an exact importance.\n  Args:\n    data_dir: (str): The location of the training or testing data to compute importances over.\n      If None, the trainer._eval_files are used\n    trainer: (DataRecordTrainer): A DataRecordTrainer object\n    parse_fn: (function): The parse_fn used by eval_input_fn\n    fnames (list<string>): The list of feature names\n    stopping_metric (str): The metric to use to determine when to stop expanding trees\n    file_list (list<str>): The list of filenames. Exactly one of file_list and data_dir should be\n      provided\n    datarecord_filter_fn (function): a function takes a single data sample in com.twitter.ml.api.ttypes.DataRecord format\n        and return a boolean value, to indicate if this data record should be kept in feature importance module or not.\n    split_feature_group_on_period (boolean): If true, split feature groups by period rather than on\n      optimal prefix\n    record_count (int): The number of records to compute importances over\n    is_metric_larger_the_better (boolean): If true, assume that stopping_metric is a metric where larger\n      values are better (e.g. ROC-AUC)\n    sensitivity (float): The smallest change in performance to continue to expand the tree\n    extra_groups (dict<str, list<str>>): A dictionary mapping the name of extra feature groups to the list of\n      the names of the features in the group. You should only supply a value for this argument if you have a set\n      of features that you want to evaluate as a group but don't share a prefix\n    dont_build_tree (boolean): If True, don't build the tree and only compute the extra_groups importances\n  Returns:\n    A dictionary that contains the individual and group feature importances\n  ";M=split_feature_group_on_period;I=extra_groups;J=parse_fn;K=trainer;E=is_metric_larger_the_better;F=record_count;C=stopping_metric;G=PermutedInputFnFactory(data_dir=data_dir,record_count=F,file_list=file_list,datarecord_filter_fn=datarecord_filter_fn);H=_compute_multiple_permuted_performances_from_trainer(factory=G,fname_ftypes=[],trainer=K,parse_fn=J,record_count=F);R={_B:H}
	if C not in H:raise ValueError("The stopping metric '{}' not found in baseline_performance. Metrics are {}".format(C,list(H.keys())))
	E=E if E is not _A else _infer_if_is_metric_larger_the_better(C);logging.info('Using {} as the stopping metric for the tree algorithm'.format(C));N=_get_feature_types_from_records(records=G.records,fnames=fnames);S=list(N.items());O={};P={}
	if dont_build_tree:logging.info('Not building feature importance trie. Will only compute importances for the extra_groups')
	else:
		logging.info('Building feature importance trie');D=_repartition(feature_list_queue=Queue(),fnames_ftypes=S,split_feature_group_on_period=M)
		while not D.empty():
			B,A=D.get();assert len(A)>0;logging.info('\n\nComputing importances for {} ({}...). {} elements left in the queue \n\n'.format(B,A[:5],D.qsize()));T=time.time();L=_compute_multiple_permuted_performances_from_trainer(factory=G,fname_ftypes=A,trainer=K,parse_fn=J,record_count=F);logging.info(_D.format(B,int(time.time()-T)))
			if len(A)==1:O[A[0][0]]=L
			else:P[B]=L
			logging.info('Checking performance for {} ({}...)'.format(B,A[:5]));U=_check_whether_tree_should_expand(baseline_performance=H,computed_performance=L,sensitivity=sensitivity,stopping_metric=C,is_metric_larger_the_better=E)
			if len(A)>1 and U:logging.info('Expanding {} ({}...)'.format(B,A[:5]));D=_repartition(feature_list_queue=D,fnames_ftypes=A,split_feature_group_on_period=M)
			else:logging.info('Not expanding {} ({}...)'.format(B,A[:5]))
	V=dict(R,**{A:B for(A,B)in O.items()});Q={A:B for(A,B)in P.items()}
	if I is not _A:
		logging.info('Computing performances for extra groups {}'.format(I.keys()))
		for (W,X) in _get_extra_feature_group_performances(factory=G,trainer=K,parse_fn=J,extra_groups=I,feature_to_type=N,record_count=F).items():Q[W]=X
	else:logging.info('Not computing performances for extra groups')
	return{INDIVIDUAL:V,GROUP:Q}
def _feature_importances_serial_algorithm(data_dir,trainer,parse_fn,fnames,file_list=_A,datarecord_filter_fn=_A,factory=_A,record_count=99999):
	'Serial algorithm for feature importances. This algorithm computes the\n  importance of each feature.\n  ';C=record_count;B=factory;B=PermutedInputFnFactory(data_dir=data_dir,record_count=C,file_list=file_list,datarecord_filter_fn=datarecord_filter_fn);E=_get_feature_types_from_records(records=B.records,fnames=fnames);D={}
	for (A,F) in list(E.items())+[(_A,_A)]:logging.info('\n\nComputing importances for {}\n\n'.format(A));G=time.time();H=[(A,F)]if A is not _A else[];D[str(A)]=_compute_multiple_permuted_performances_from_trainer(factory=B,fname_ftypes=H,trainer=trainer,parse_fn=parse_fn,record_count=C);logging.info(_D.format(A,int(time.time()-G)))
	return{INDIVIDUAL:D,GROUP:{}}
def _process_feature_name_for_mldash(feature_name):return feature_name.replace('/','__')
def compute_feature_importances(trainer,data_dir=_A,feature_config=_A,algorithm=TREE,parse_fn=_A,datarecord_filter_fn=_A,**I):
	'Perform a feature importance analysis on a trained model\n  Args:\n    trainer: (DataRecordTrainer): A DataRecordTrainer object\n    data_dir: (str): The location of the training or testing data to compute importances over.\n      If None, the trainer._eval_files are used\n    feature_config (contrib.FeatureConfig): The feature config object. If this is not provided, it\n      is taken from the trainer\n    algorithm (str): The algorithm to use\n    parse_fn: (function): The parse_fn used by eval_input_fn. By default this is\n      feature_config.get_parse_fn()\n    datarecord_filter_fn (function): a function takes a single data sample in com.twitter.ml.api.ttypes.DataRecord format\n        and return a boolean value, to indicate if this data record should be kept in feature importance module or not.\n  ';E=data_dir;B=parse_fn;C=algorithm;D=trainer;A=feature_config
	if E is _A:logging.info('Using trainer._eval_files (found {} as files)'.format(D._eval_files));G=D._eval_files
	else:logging.info('data_dir provided. Looking at {} for data.'.format(E));G=_A
	A=A or D._feature_config;F={}
	if not A:logging.warn('WARN: Not computing feature importance because trainer._feature_config is None');F=_A
	else:B=B if B is not _A else A.get_parse_fn();H=_get_feature_name_from_config(A);logging.info('Computing importances for {}'.format(H));logging.info('Using the {} feature importance computation algorithm'.format(C));C={SERIAL:_feature_importances_serial_algorithm,TREE:_feature_importances_tree_algorithm}[C];F=C(data_dir=E,trainer=D,parse_fn=B,fnames=H,file_list=G,datarecord_filter_fn=datarecord_filter_fn,**I)
	return F
def write_feature_importances_to_hdfs(trainer,feature_importances,output_path=_A,metric=_E):
	'Publish a feature importance analysis to hdfs as a tsv\n  Args:\n    (see compute_feature_importances for other args)\n    trainer (Trainer)\n    feature_importances (dict): Dictionary of feature importances\n    output_path (str): The remote or local file to write the feature importances to. If not\n      provided, this is inferred to be the trainer save dir\n    metric (str): The metric to write to tsv\n  ';E='drop';C=trainer;B=output_path;A={'{} ({})'.format(A,B)if A!=_B else A:D[metric]for(B,C)in feature_importances.items()for(A,D)in C.items()};B='{}/feature_importances-{}'.format(C._save_dir[:-1]if C._save_dir.endswith('/')else C._save_dir,B if B is not _A else str(time.time()))
	if len(A)>0:
		logging.info('Writing feature_importances for {} to hdfs'.format(A.keys()));F=[{'name':B,E:A[_B]-A[B],'pdrop':100*(A[_B]-A[B])/(A[_B]+1e-08),'perf':A[B]}for B in A.keys()];D=['Name\tPerformance Drop\tPercent Performance Drop\tPerformance']
		for G in sorted(F,key=lambda d:d[E]):D.append('{name}\t{drop}\t{pdrop}%\t{perf}'.format(**G))
		logging.info('\n'.join(D));write_list_to_hdfs_gfile(D,B);logging.info('Wrote feature feature_importances to {}'.format(B))
	else:logging.info('Not writing feature_importances to hdfs')
	return B
def write_feature_importances_to_ml_dash(trainer,feature_importances,feature_config=_A):
	'Publish feature importances + all feature names to ML Metastore\n  Args:\n    trainer: (DataRecordTrainer): A DataRecordTrainer object\n    feature_config (contrib.FeatureConfig): The feature config object. If this is not provided, it\n      is taken from the trainer\n    feature_importances (dict, default=None): Dictionary of precomputed feature importances\n    feature_importance_metric (str, default=None): The metric to write to ML Dashboard\n  ';G=feature_importances;C=feature_config;A=trainer;D=A.experiment_tracker.tracking_path if A.experiment_tracker.tracking_path else ExperimentTracker.guess_path(A._save_dir);logging.info('Computing feature importances for run: {}'.format(D));H=[]
	for I in G:
		for (J,E) in G[I].items():
			logging.info('FEATURE NAME: {}'.format(J));L=J.split(' (').pop(0)
			for (F,B) in E.items():
				try:E[F]=float(B);logging.info('Wrote feature importance value {} for metric: {}'.format(str(B),F))
				except Exception as M:logging.error('Skipping writing metric:{} to ML Metastore due to invalid metric value: {} or value type: {}. Exception: {}'.format(F,str(B),type(B),str(M)));pass
			H.append(FeatureImportance(run_id=D,feature_name=_process_feature_name_for_mldash(L),feature_importance_metrics=E,is_group=I==GROUP))
	C=C or A._feature_config;N=FeatureNames(run_id=D,names=list(C.features.keys()))
	try:K=ModelRepoClient();logging.info('Writing feature importances to ML Metastore');K.add_feature_importances(H);logging.info('Writing feature names to ML Metastore');K.add_feature_names(N)
	except (HTTPError,RetryError)as O:logging.error('Feature importance is not being written due to: HTTPError when attempting to write to ML Metastore: \n{}.'.format(O))