# The contents of this file are subject to the Common Public Attribution
# License Version 1.0. (the "License"); you may not use this file except in
# compliance with the License. You may obtain a copy of the License at
# http://code.reddit.com/LICENSE. The License is based on the Mozilla Public
# License Version 1.1, but Sections 14 and 15 have been added to cover use of
# software over a computer network and provide for limited attribution for the
# Original Developer. In addition, Exhibit A has been modified to be consistent
# with Exhibit B.
#
# Software distributed under the License is distributed on an "AS IS" basis,
# WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License for
# the specific language governing rights and limitations under the License.
#
# The Original Code is reddit.
#
# The Original Developer is the Initial Developer.  The Initial Developer of
# the Original Code is reddit Inc.
#
# All portions of the code written by reddit are Copyright (c) 2006-2015 reddit
# Inc. All Rights Reserved.
###############################################################################

from copy import copy
from pylons import app_globals as g
import os
from time import time, sleep

from boto.emr.step import InstallPigStep, PigStep
from boto.emr.bootstrap_action import BootstrapAction

from r2.lib.emr_helpers import (
    EmrException,
    EmrJob,
    get_live_clusters,
    get_step_state,
    LIVE_STATES,
    COMPLETED,
    PENDING,
    NOTFOUND,
)


class TrafficBase(EmrJob):

    """Base class for all traffic jobs.

    Includes required bootstrap actions and setup steps.

    """

    BOOTSTRAP_NAME = 'traffic binaries'
    BOOTSTRAP_SCRIPT = os.path.join(g.TRAFFIC_SRC_DIR, 'traffic_bootstrap.sh')
    _defaults = dict(master_instance_type='m1.small',
                     slave_instance_type='c3.2xlarge', num_slaves=1,
                     job_flow_role=g.emr_trafic_job_flow_role,
                     service_role=g.emr_traffic_service_role,
                     tags=g.emr_traffic_tags,
                )

    def __init__(self, emr_connection, jobflow_name, steps=None, **kw):
        combined_kw = copy(self._defaults)
        combined_kw.update(kw)
        bootstrap_actions = self._bootstrap_actions()
        setup_steps = self._setup_steps()
        steps = steps or []
        EmrJob.__init__(self, emr_connection, jobflow_name,
                        bootstrap_actions=bootstrap_actions,
                        setup_steps=setup_steps,
                        steps=steps,
                        **combined_kw)

    @classmethod
    def _bootstrap_actions(cls):
        name = cls.BOOTSTRAP_NAME
        path = cls.BOOTSTRAP_SCRIPT
        bootstrap_action_args = [g.TRAFFIC_SRC_DIR, g.tracking_secret]
        bootstrap = BootstrapAction(name, path, bootstrap_action_args)
        return [bootstrap]

    @classmethod
    def _setup_steps(self):
        return [InstallPigStep()]


class PigProcessHour(PigStep):
    STEP_NAME = 'pig process hour'
    PIG_FILE = os.path.join(g.TRAFFIC_SRC_DIR, 'mr_process_hour.pig')

    def __init__(self, log_path, output_path):
        self.log_path = log_path
        self.output_path = output_path
        self.name = '%s (%s)' % (self.STEP_NAME, self.log_path)
        pig_args = ['-p', 'OUTPUT=%s' % self.output_path,
                    '-p', 'LOGFILE=%s' % self.log_path]
        PigStep.__init__(self, self.name, self.PIG_FILE, pig_args=pig_args)


class PigAggregate(PigStep):
    STEP_NAME = 'pig aggregate'
    PIG_FILE = os.path.join(g.TRAFFIC_SRC_DIR, 'mr_aggregate.pig')

    def __init__(self, input_path, output_path):
        self.input_path = input_path
        self.output_path = output_path
        self.name = '%s (%s)' % (self.STEP_NAME, self.input_path)
        pig_args = ['-p', 'INPUT=%s' % self.input_path,
                    '-p', 'OUTPUT=%s' % self.output_path]
        PigStep.__init__(self, self.name, self.PIG_FILE, pig_args=pig_args)


class PigCoalesce(PigStep):
    STEP_NAME = 'pig coalesce'
    PIG_FILE = os.path.join(g.TRAFFIC_SRC_DIR, 'mr_coalesce.pig')

    def __init__(self, input_path, output_path):
        self.input_path = input_path
        self.output_path = output_path
        self.name = '%s (%s)' % (self.STEP_NAME, self.input_path)
        pig_args = ['-p', 'INPUT=%s' % self.input_path,
                    '-p', 'OUTPUT=%s' % self.output_path]
        PigStep.__init__(self, self.name, self.PIG_FILE, pig_args=pig_args)


def _add_step(emr_connection, step, jobflow_name, **jobflow_kw):
    """Add step to a running jobflow.

    Append the step onto a jobflow with the specified name if one exists,
    otherwise create a new jobflow and run it. Returns the jobflowid.
    NOTE: jobflow_kw will be used to configure the jobflow ONLY if a new
    jobflow is created.

    """

    running = get_live_clusters(emr_connection)

    for cluster in running:
        # NOTE: the existing cluster's bootstrap actions aren't checked so we
        # are assuming that any cluster with the correct name is compatible
        # with our new step
        if cluster.name == jobflow_name:
            jobflowid = cluster.id
            emr_connection.add_jobflow_steps(jobflowid, step)
            print 'Added %s to jobflow %s' % (step.name, jobflowid)
            break
    else:
        base = TrafficBase(emr_connection, jobflow_name, steps=[step],
                           **jobflow_kw)
        base.run()
        jobflowid = base.jobflowid
        print 'Added %s to new jobflow %s' % (step.name, jobflowid)

    return jobflowid


def _wait_for_step(emr_connection, step, jobflowid, sleeptime):
    """Poll EMR and wait for a step to finish."""
    sleep(180)
    start = time()
    step_state = get_step_state(emr_connection, jobflowid, step.name,
                                update=True)
    while step_state in LIVE_STATES + [PENDING]:
        sleep(sleeptime)
        step_state = get_step_state(emr_connection, jobflowid, step.name)
    end = time()
    print '%s took %0.2fs (exit: %s)' % (step.name, end - start, step_state)
    return step_state


def run_traffic_step(emr_connection, step, jobflow_name,
                     wait=True, sleeptime=60, retries=1, **jobflow_kw):
    """Run a traffic processing step.

    Helper function to force all steps to be executed by the same jobflow
    (jobflow_name). Also can hold until complete (wait) and retry on
    failure (retries).

    """

    jobflowid = _add_step(emr_connection, step, jobflow_name, **jobflow_kw)

    if not wait:
        return

    attempts = 1
    exit_state = _wait_for_step(emr_connection, step, jobflowid, sleeptime)
    while attempts <= retries and exit_state != COMPLETED:
        jobflowid = _add_step(emr_connection, step, jobflow_name, **jobflow_kw)
        exit_state = _wait_for_step(emr_connection, step, jobflowid, sleeptime)
        attempts += 1

    if exit_state != COMPLETED:
        msg = '%s failed (exit: %s)' % (step.name, exit_state)
        if retries:
            msg += 'retried %s times' % retries
        raise EmrException(msg)


def extract_hour(emr_connection, jobflow_name, log_path, output_path,
                 **jobflow_kw):
    step = PigProcessHour(log_path, output_path)
    run_traffic_step(emr_connection, step, jobflow_name, **jobflow_kw)


def aggregate_interval(emr_connection, jobflow_name, input_path, output_path,
                       **jobflow_kw):
    step = PigAggregate(input_path, output_path)
    run_traffic_step(emr_connection, step, jobflow_name, **jobflow_kw)


def coalesce_interval(emr_connection, jobflow_name, input_path, output_path,
                      **jobflow_kw):
    step = PigCoalesce(input_path, output_path)
    run_traffic_step(emr_connection, step, jobflow_name, **jobflow_kw)
