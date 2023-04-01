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

from r2.lib.memoize import memoize

LIVE_STATES = ['RUNNING', 'STARTING', 'WAITING', 'BOOTSTRAPPING']
COMPLETED = 'COMPLETED'
PENDING = 'PENDING'
NOTFOUND = 'NOTFOUND'


def get_live_clusters(emr_connection):
    ret = emr_connection.list_clusters(cluster_states=LIVE_STATES)
    return ret.clusters or []


@memoize('get_step_states', time=60, timeout=60)
def get_step_states(emr_connection, jobflowid):
    """Return the names and states of all steps in the jobflow.

    Memoized to prevent ratelimiting.

    """

    ret = emr_connection.list_steps(jobflowid)
    steps = []
    steps.extend(ret.steps)
    while hasattr(ret, "marker"):
        ret = emr_connection.list_steps(jobflowid, marker=ret.marker)
        steps.extend(ret.steps)

    ret = []
    for step in steps:
        start_str = step.status.timeline.creationdatetime
        ret.append((step.name, step.status.state, start_str))
    return ret


def get_step_state(emr_connection, jobflowid, step_name, update=False):
    """Return the state of a step.

    If jobflowid/step_name combination is not unique this will return the state
    of the most recent step.

    """

    g.reset_caches()
    steps = get_step_states(emr_connection, jobflowid, _update=update)

    for name, state, start in sorted(steps, key=lambda t: t[2], reverse=True):
        if name == step_name:
            return state
    else:
        return NOTFOUND


def get_jobflow_id(emr_connection, name):
    """Return id of the live cluster with specified name."""
    ret = emr_connection.list_clusters(cluster_states=LIVE_STATES)
    clusters = ret.clusters

    try:
        # clusters appear to be ordered by creation time
        return [cluster.id for cluster in clusters if cluster.name == name][0]
    except IndexError:
        return


def terminate_jobflow(emr_connection, jobflow_name):
    jobflow_id = get_jobflow_id(emr_connection, jobflow_name)
    if jobflow_id:
        emr_connection.terminate_jobflow(jobflow_id)


def modify_slave_count(emr_connection, jobflow_name, num_slaves=1):
    jobflow_id = get_jobflow_id(emr_connection, jobflow_name)
    if not jobflow_id:
        return

    ret = emr_connection.list_instance_groups(jobflow_id)

    try:
        instancegroup = [i for i in ret.instancegroups if i.name == "slave"][0]
    except IndexError:
        # no slave instance group
        return

    if instancegroup.requestedinstancecount != num_slaves:
        return

    msg = 'Modifying slave instance count of %s (%s -> %s)'
    print msg % (jobflow_name, instancegroup.requestedinstancecount, num_slaves)
    emr_connection.modify_instance_groups(instancegroup.id, num_slaves)


class EmrJob(object):
    def __init__(self, emr_connection, name, steps=[], setup_steps=[],
                 bootstrap_actions=[], log_uri=None, keep_alive=True,
                 ec2_keyname=None, hadoop_version='1.0.3',
                 ami_version='latest', master_instance_type='m1.small',
                 slave_instance_type='m1.small', num_slaves=1,
                 visible_to_all_users=True, job_flow_role=None,
                 service_role=None, tags=None):

        self.jobflowid = None
        self.conn = emr_connection
        self.name = name
        self.steps = steps
        self.setup_steps = setup_steps
        self.bootstrap_actions = bootstrap_actions
        self.log_uri = log_uri
        self.enable_debugging = bool(log_uri)
        self.keep_alive = keep_alive
        self.ec2_keyname = ec2_keyname
        self.hadoop_version = hadoop_version
        self.ami_version = ami_version
        self.master_instance_type = master_instance_type
        self.slave_instance_type = slave_instance_type
        self.num_instances = num_slaves + 1
        self.visible_to_all_users = visible_to_all_users
        self.job_flow_role = job_flow_role
        self.service_role = service_role
        self.tags = tags or {}

    def run(self):
        steps = copy(self.setup_steps)
        steps.extend(self.steps)

        job_flow_args = dict(name=self.name,
            steps=steps, bootstrap_actions=self.bootstrap_actions,
            keep_alive=self.keep_alive, ec2_keyname=self.ec2_keyname,
            hadoop_version=self.hadoop_version, ami_version=self.ami_version,
            master_instance_type=self.master_instance_type,
            slave_instance_type=self.slave_instance_type,
            num_instances=self.num_instances,
            enable_debugging=self.enable_debugging,
            log_uri=self.log_uri,
            visible_to_all_users=self.visible_to_all_users,
            job_flow_role=self.job_flow_role, service_role=self.service_role)

        self.jobflowid = self.conn.run_jobflow(**job_flow_args)
        if self.tags:
            assert isinstance(self.tags, dict)
            # The EMR Cluster name is distinct from the Name tag. The latter
            # is exportable as a cost allocation tag.
            self.tags["Name"] = self.name
            self.conn.add_tags(self.jobflowid, self.tags)

    def terminate(self):
        terminate_jobflow(self.conn, self.name)

    def modify_slave_count(self, num_slaves=1):
        modify_slave_count(self.conn, self.name, num_slaves)


class EmrException(Exception):
    def __init__(self, msg):
        self.msg = msg

    def __str__(self):
        return self.msg
