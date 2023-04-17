"""
This module contains the experiment tracker for tracking training in ML Metastore
"""
import getpass
import hashlib
import os
import re
import sys
import time
from contextlib import contextmanager
from datetime import datetime
from typing import Any, Callable, Dict, List, Optional, Type, Union

import tensorflow.compat.v1 as tf
from absl import logging

from twml.hooks import MetricsUpdateHook

try:
    from urllib import quote as encode_url
except ImportError:
    from urllib.parse import quote as encode_url


try:
    # ML Metastore packages might not be available on GCP.
    # If they are not found, tracking is disabled
    import requests
    from com.twitter.mlmetastore.modelrepo.client import ModelRepoClient
    from com.twitter.mlmetastore.modelrepo.core import (
        DeepbirdRun,
        Experiment,
        FeatureConfig,
        FeatureConfigFeature,
        Model,
        ProgressReport,
        Project,
        StatusUpdate,
    )
    from com.twitter.mlmetastore.modelrepo.core.path import (
        check_valid_id,
        generate_id,
        get_components_from_id,
    )
except ImportError:
    ModelRepoClient = None


class ExperimentTracker(object):
    """
    A tracker that records twml runs in ML Metastore.
    """

    def __init__(self, params: dict, run_config: tf.estimator.RunConfig, save_dir: str):
        """
        Args:
            params (python dict):
                The trainer params. ExperimentTracker uses `params.experiment_tracking_path` (String) and
                `params.disable_experiment_tracking`.
                If `experiment_tracking_path` is set to None, the tracker tries to guess a path with
                save_dir.
                If `disable_experiment_tracking` is True, the tracker is disabled.
            run_config (tf.estimator.RunConfig):
                The run config used by the estimator.
            save_dir (str):
                save_dir of the trainer
        """
        if isinstance(params, dict):
            self._params = params
        else:
            # preserving backward compatibility for people still using HParams
            logging.warning(
                "Please stop using HParams and use python dicts. HParams are removed in TF 2"
            )
            self._params = dict(
                (k, v) for k, v in params.values().items() if v != "null"
            )
        self._run_config = run_config
        self._graceful_shutdown_port = self._params.get("health_port")

        self.tracking_path = self._params.get("experiment_tracking_path")
        is_tracking_path_too_long = (
            self.tracking_path is not None and len(self.tracking_path) > 256
        )

        if is_tracking_path_too_long:
            raise ValueError("Experiment Tracking Path longer than 256 characters")

        self.disabled = (
            self._params.get("disable_experiment_tracking", False)
            or not self._is_env_eligible_for_tracking()
            or ModelRepoClient is None
        )

        self._is_hogwild = bool(os.environ.get("TWML_HOGWILD_PORTS"))
        self._is_distributed = bool(os.environ.get("TF_CONFIG"))
        self._client = None if self.disabled else ModelRepoClient()

        run_name_from_environ = self.run_name_from_environ()
        run_name_can_be_inferred = (
            self.tracking_path is not None or run_name_from_environ is not None
        )

        # Turn the flags off as needed in hogwild / distributed
        if self._is_hogwild or self._is_distributed:
            self._env_eligible_for_recording_experiment = (
                self._run_config.task_type == "evaluator"
            )
            if run_name_can_be_inferred:
                self._env_eligible_for_recording_export_metadata = (
                    self._run_config.task_type == "chief"
                )
            else:
                logging.info(
                    "experiment_tracking_path is not set and can not be inferred. "
                    "Recording export metadata is disabled because the chief node and eval node "
                    "are setting different experiment tracking paths."
                )
                self._env_eligible_for_recording_export_metadata = False
        else:
            # Defaults to True
            self._env_eligible_for_recording_experiment = True
            self._env_eligible_for_recording_export_metadata = True

        if not self.disabled:
            # Sanitize passed in experiment tracking paths. e.g. own:proJ:exp:Run.Name
            # -> own:proj:exp:Run_Name
            if self.tracking_path:
                try:
                    check_valid_id(self.tracking_path)
                except ValueError as err:
                    logging.error(
                        f"Invalid experiment tracking path provided. Sanitizing: {self.tracking_path}\nError: {err}"
                    )
                    self.tracking_path = generate_id(
                        owner=self.path["owner"],
                        project_name=self.path["project_name"],
                        experiment_name=self.path["experiment_name"],
                        run_name=self.path["run_name"],
                    )
                    logging.error(
                        f"Generated sanitized experiment tracking path: {self.tracking_path}"
                    )
            else:
                logging.info(
                    "No experiment_tracking_path set. Experiment Tracker will try to guess a path"
                )
                self.tracking_path = self.guess_path(save_dir, run_name_from_environ)
                logging.info("Guessed path: %s", self.tracking_path)

            # additional check to see if generated path is valid
            try:
                check_valid_id(self.tracking_path)
            except ValueError as err:
                logging.error(
                    "Could not generate valid experiment tracking path. Disabling tracking. "
                    + f"Error:\n{err}"
                )
                self.disabled = True

        self.project_id = (
            None
            if self.disabled
            else f'{self.path["owner"]}:{self.path["project_name"]}'
        )
        self.base_run_id = None if self.disabled else self.tracking_path
        self._current_run_name_suffix = None
        self._current_tracker_hook = None

        if self.disabled:
            logging.info("Experiment Tracker is disabled")
        else:
            logging.info(
                "Experiment Tracker initialized with base run id: %s", self.base_run_id
            )

    @contextmanager
    def track_experiment(
        self,
        eval_hooks: List[tf.estimator.SessionRunHook],
        get_estimator_spec_fn: Callable[[], tf.estimator.EstimatorSpec],
        name: Optional[str] = None,
    ) -> tf.estimator.SessionRunHook:
        """
        A context manager for tracking experiment. It should wrap the training loop.
        An experiment tracker eval hook is appended to eval_hooks to collect metrics.

        Args:
            eval_hooks (list):
                The list of eval_hooks to be used. When it's not None, and does not contain any ,
                MetricsUpdateHook an experiment tracker eval hook is appended to it. When it contains
                any MetricsUpdateHook, this tracker is disabled to avoid conflict with legacy Model Repo
                tracker (`TrackRun`).
            get_estimator_spec_fn (func):
                A function to get the current EstimatorSpec of the trainer, used by the eval hook.
            name (str);
                Name of this training or evaluation. Used as a suffix of the run_id.

        Returns:
            The tracker's eval hook which is appended to eval_hooks.
        """

        # disable this tracker if legacy TrackRun hook is present
        # TODO: remove this once we completely deprecate the old TrackRun interface
        if eval_hooks is not None:
            self.disabled = self.disabled or any(
                isinstance(x, MetricsUpdateHook) for x in eval_hooks
            )

        logging.info(
            "Is environment eligible for recording experiment: %s",
            self._env_eligible_for_recording_experiment,
        )

        if self._env_eligible_for_recording_experiment and self._graceful_shutdown_port:
            requests.post(
                f"http://localhost:{self._graceful_shutdown_port}/track_training_start"
            )

        if self.disabled or eval_hooks is None:
            yield None
        else:
            assert (
                self._current_tracker_hook is None
            ), "experiment tracking has been started already"

            if name is not None:
                self._current_run_name_suffix = "_" + name

            logging.info("Starting experiment tracking. Path: %s", self._current_run_id)
            logging.info(
                "Is environment eligible for recording export metadata: %s",
                self._env_eligible_for_recording_export_metadata,
            )
            logging.info(
                "This run will be available at: http://go/mldash/experiments/%s",
                encode_url(self.experiment_id),
            )

            try:
                self._record_run()
                self._add_run_status(
                    StatusUpdate(self._current_run_id, status="RUNNING")
                )
                self._register_for_graceful_shutdown()

                self._current_tracker_hook = self.create_eval_hook(
                    get_estimator_spec_fn
                )
            except Exception as err:
                logging.error(
                    "Failed to record run. This experiment will not be tracked. Error: %s",
                    str(err),
                )
                self._current_tracker_hook = None

            if self._current_tracker_hook is None:
                yield None
            else:
                try:
                    eval_hooks.append(self._current_tracker_hook)
                    yield self._current_tracker_hook
                except Exception as err:
                    self._add_run_status(
                        StatusUpdate(
                            self._current_run_id, status="FAILED", description=str(err)
                        )
                    )
                    self._deregister_for_graceful_shutdown()
                    self._current_tracker_hook = None
                    self._current_run_name_suffix = None
                    logging.error("Experiment tracking done. Experiment failed.")
                    raise

                try:
                    if self._current_tracker_hook.metric_values:
                        self._record_update(self._current_tracker_hook.metric_values)
                    self._add_run_status(
                        StatusUpdate(self._current_run_id, status="SUCCESS")
                    )
                    logging.info("Experiment tracking done. Experiment succeeded.")
                except Exception as err:
                    logging.error(
                        "Failed to update mark run as successful. Error: %s", str(err)
                    )
                finally:
                    self._deregister_for_graceful_shutdown()
                    self._current_tracker_hook = None
                    self._current_run_name_suffix = None

    def create_eval_hook(
        self, get_estimator_spec_fn: Callable[[], tf.estimator.EstimatorSpec]
    ) -> tf.estimator.SessionRunHook:
        """
        Create an eval_hook to track eval metrics

        Args:
            get_estimator_spec_fn (func):
                A function that returns the current EstimatorSpec of the trainer.

        Returns:
            The tracker's eval hook.
        """
        return MetricsUpdateHook(
            get_estimator_spec_fn=get_estimator_spec_fn,
            add_metrics_fn=self._record_update,
        )

    def register_model(self, export_path: str) -> None:
        """
        Record the exported model.

        Args:
            export_path (str):
                The path to the exported model.
        """
        if self.disabled:
            return None

        try:
            logging.info(
                "Model is exported to %s. Computing hash of the model.", export_path
            )
            model_hash = self.compute_model_hash(export_path)
            logging.info("Model hash: %s. Registering it in ML Metastore.", model_hash)
            self._client.register_model(
                Model(model_hash, self.path["owner"], self.base_run_id)
            )
        except Exception as err:
            logging.error("Failed to register model. Error: %s", str(err))

    def export_feature_spec(self, feature_spec_dict: Dict[str, Any]) -> None:
        """
        Export feature spec to ML Metastore (go/ml-metastore).

        Please note that the feature list in FeatureConfig only keeps the list of feature hash ids due
        to the 1mb upper limit for values in manhattan, and more specific information (feature type,
        feature name) for each feature config feature is stored separately in FeatureConfigFeature dataset.

        Args:
            feature_spec_dict (dict): A dictionary obtained from FeatureConfig.get_feature_spec()
        """
        if self.disabled or not self._env_eligible_for_recording_export_metadata:
            return None

        try:
            logging.info("Exporting feature spec to ML Metastore.")
            feature_list = feature_spec_dict["features"]
            label_list = feature_spec_dict["labels"]
            weight_list = feature_spec_dict["weight"]
            self._client.add_feature_config(
                FeatureConfig(
                    self._current_run_id,
                    list(feature_list.keys()),
                    list(label_list.keys()),
                    list(weight_list.keys()),
                )
            )

            feature_config_features = [
                FeatureConfigFeature(
                    hash_id=_feature_hash_id,
                    feature_name=_feature["featureName"],
                    feature_type=_feature["featureType"],
                )
                for _feature_hash_id, _feature in zip(
                    feature_list.keys(), feature_list.values()
                )
            ]
            self._client.add_feature_config_features(
                list(feature_list.keys()), feature_config_features
            )

            feature_config_labels = [
                FeatureConfigFeature(
                    hash_id=_label_hash_id, feature_name=_label["featureName"]
                )
                for _label_hash_id, _label in zip(
                    label_list.keys(), label_list.values()
                )
            ]
            self._client.add_feature_config_features(
                list(label_list.keys()), feature_config_labels
            )

            feature_config_weights = [
                FeatureConfigFeature(
                    hash_id=_weight_hash_id,
                    feature_name=_weight["featureName"],
                    feature_type=_weight["featureType"],
                )
                for _weight_hash_id, _weight in zip(
                    weight_list.keys(), weight_list.values()
                )
            ]
            self._client.add_feature_config_features(
                list(weight_list.keys()), feature_config_weights
            )

        except Exception as err:
            logging.error("Failed to export feature spec. Error: %s", str(err))

    @property
    def path(self) -> Union[Dict[str, str], None]:
        if self.disabled:
            return None
        return get_components_from_id(self.tracking_path, ensure_valid_id=False)

    @property
    def experiment_id(self) -> Union[str, None]:
        """Return the experiment id."""
        if self.disabled:
            return None
        return f"{self.path['owner']}:{self.path['project_name']}:{self.path['experiment_name']}"

    @property
    def _current_run_name(self) -> str:
        """Return the current run name."""
        if self._current_run_name_suffix is not None:
            return self.path["run_name"] + self._current_run_name_suffix
        return self.path["run_name"]

    @property
    def _current_run_id(self) -> str:
        """Return the current run id."""
        if self._current_run_name_suffix is not None:
            return self.base_run_id + self._current_run_name_suffix
        return self.base_run_id

    def get_run_status(self) -> Union[StatusUpdate, None]:
        """Get the current run status."""
        if not self.disabled:
            return self._client.get_latest_dbv2_status(self._current_run_id)
        return None

    def _add_run_status(self, status: StatusUpdate) -> None:
        """
        Add run status with underlying client.

        Args:
            status (StatusUpdate):
                The status update to add.
        """
        if not self.disabled and self._env_eligible_for_recording_experiment:
            self._client.add_run_status(status)

    def _record_run(self) -> None:
        """Record the run in ML Metastore."""
        if self.disabled or not self._env_eligible_for_recording_experiment:
            return None

        if not self._client.project_exists(self.project_id):
            self._client.add_project(
                Project(self.path["project_name"], self.path["owner"])
            )
            time.sleep(1)

        if not self._client.experiment_exists(self.experiment_id):
            self._client.add_experiment(
                Experiment(
                    self.path["experiment_name"],
                    self.path["owner"],
                    self.project_id,
                    "",
                )
            )
            time.sleep(1)

        run = DeepbirdRun(
            self.experiment_id,
            self._current_run_name,
            "",
            {"raw_command": " ".join(sys.argv)},
            self._params,
        )
        self._client.add_deepbird_run(run, force=True)
        time.sleep(1)

    def _record_update(self, metrics: Dict[str, Any]) -> None:
        """
        Record metrics update in ML Metastore.

        Args:
            metrics (dict):
                The dict of the metrics and their values.
        """

        if self.disabled or not self._env_eligible_for_recording_experiment:
            return None

        reported_metrics = {}
        for k, v in metrics.items():
            if hasattr(v, "item"):
                reported_metrics[k] = v.item() if v.size == 1 else str(v.tolist())
            else:
                logging.warning(
                    "Ignoring %s because the value (%s) is not valid" % (k, str(v))
                )

        report = ProgressReport(self._current_run_id, reported_metrics)

        try:
            self._client.add_progress_report(report)
        except Exception as err:
            logging.error(f"Failed to record metrics in ML Metastore. Error: {err}")
            logging.error(f"Run ID: {self._current_run_id}")
            logging.error(f"Progress Report: {report.to_json_string()}")

    def _register_for_graceful_shutdown(self) -> Optional[requests.Response]:
        """
        Register the tracker with the health server, enabling graceful shutdown.

        Returns:
            (Response) health server response
        """
        if (
            self._graceful_shutdown_port
            and not self.disabled
            and self._env_eligible_for_recording_experiment
        ):
            return requests.post(
                f"http://localhost:{self._graceful_shutdown_port}/register_id/{self._current_run_id}"
            )
        return None

    def _deregister_for_graceful_shutdown(self) -> Optional[requests.Response]:
        """
        Deregister the tracker with the health server, disabling graceful shutdown.

        Returns:
            (Response) health server response
        """
        if (
            self._graceful_shutdown_port
            and not self.disabled
            and self._env_eligible_for_recording_experiment
        ):
            return requests.post(
                f"http://localhost:{self._graceful_shutdown_port}/deregister_id/{self._current_run_id}"
            )

    def _is_env_eligible_for_tracking(self) -> bool:
        """Determine if experiment tracking should run in the env."""
        is_unit_test = (
            os.environ.get("PYTEST_CURRENT_TEST") is not None
            and os.environ.get("TEST_EXP_TRACKER") is None
        )

        is_running_on_ci = (
            getpass.getuser() == "scoot-service"
            and os.environ.get("TEST_EXP_TRACKER") is None
        )

        return (not is_unit_test) and (not is_running_on_ci)

    @classmethod
    def run_name_from_environ(cls: Type["ExperimentTracker"]) -> Optional[str]:
        """
        Create run id from environment if possible.
        """
        job_name = os.environ.get("TWML_JOB_NAME")
        job_launch_time = os.environ.get("TWML_JOB_LAUNCH_TIME")

        if not job_name or not job_launch_time:
            return None

        try:
            # job_launch_time should be in isoformat
            # python2 doesnt support datetime.fromisoformat, so use hardcoded format string.
            job_launch_time_formatted = datetime.strptime(
                job_launch_time, "%Y-%m-%dT%H:%M:%S.%f"
            )
        except ValueError:
            # Fallback in case aurora config is generating datetime in a different format.
            job_launch_time_formatted = (
                job_launch_time.replace("-", "_")
                .replace("T", "_")
                .replace(":", "_")
                .replace(".", "_")
            )
        return f"{job_name}_{job_launch_time_formatted.strftime('%m_%d_%Y_%I_%M_%p')}"

    @classmethod
    def guess_path(
        cls: Type["ExperimentTracker"],
        save_dir: str,
        run_name: Optional[str] = None,
    ) -> str:
        """
        Guess an experiment tracking path based on save_dir.

        Args:
            save_dir (str): save directory
            run_name (str): run name

        Returns:
            (str) guessed path
        """
        if not run_name:
            run_name = f'Unnamed_{datetime.now().strftime("%m_%d_%Y_%I_%M_%p")}'

        if save_dir.startswith("hdfs://"):
            path_match = re.search(r"/user/([a-z0-9\-_]+)/([a-z0-9\-_]+)", save_dir)

            if path_match:
                groups = path_match.groups()
                user = groups[0]
                project_name = groups[1]

                return generate_id(user, "default", project_name, run_name)

        user = getpass.getuser()
        project_name = re.sub(r"^[a-z0-9\-_]", os.path.basename(save_dir), "")
        if not project_name:
            project_name = "unnamed"

        return generate_id(user, "default", project_name, run_name)

    @classmethod
    def compute_model_hash(cls, export_path: str) -> str:
        """
        Computes the hash of an exported model. This is a gfile version of
        twitter.mlmetastore.common.versioning.compute_hash. The two functions should generate
        the same hash when given the same model.

        Args:
            export_path (str): The path to the exported model.

        Returns:
            (str) hash of the exported model
        """
        paths = []
        for path, subdirs, files in tf.io.gfile.walk(export_path):
            for name in sorted(files):
                paths.append(os.path.join(path, name))

        paths.sort()
        hash_object = hashlib.new("sha1")

        for path in paths:
            with tf.io.gfile.GFile(path, "rb") as file:
                hash_object.update(file.read())

        return hash_object.hexdigest()
