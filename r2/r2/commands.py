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

import os, sys

import paste.fixture
from paste.script import command
from paste.deploy import loadapp

from r2.lib.log import RavenErrorReporter


class RunCommand(command.Command):
    max_args = 2
    min_args = 1

    usage = "CONFIGFILE CMDFILE.py"
    summary = "Executed CMDFILE with pylons support"
    group_name = "Reddit"


    parser = command.Command.standard_parser(verbose=True)
    parser.add_option('-c', '--command',
                      dest='command',
                      help="execute command in module")
    parser.add_option("", "--proctitle",
                      dest="proctitle",
                      help="set the title seen by ps and top")

    def command(self):
        try:
            if self.options.proctitle:
                import setproctitle
                setproctitle.setproctitle("paster " + self.options.proctitle)
        except ImportError:
            pass

        config_file = self.args[0]
        config_name = 'config:%s' % config_file

        report_to_sentry = "REDDIT_ERRORS_TO_SENTRY" in os.environ

        here_dir = os.getcwd()

        # Load locals and populate with objects for use in shell
        sys.path.insert(0, here_dir)

        # Load the wsgi app first so that everything is initialized right
        global_conf = {
            'running_as_script': "true",
        }
        wsgiapp = loadapp(
            config_name, relative_to=here_dir, global_conf=global_conf)
        test_app = paste.fixture.TestApp(wsgiapp)

        # Query the test app to setup the environment
        tresponse = test_app.get('/_test_vars')
        request_id = int(tresponse.body)

        # Disable restoration during test_app requests
        test_app.pre_request_hook = lambda self: \
            paste.registry.restorer.restoration_end()
        test_app.post_request_hook = lambda self: \
            paste.registry.restorer.restoration_begin(request_id)

        # Restore the state of the Pylons special objects
        # (StackedObjectProxies)
        paste.registry.restorer.restoration_begin(request_id)

        loaded_namespace = {}

        try:
            if self.args[1:]:
                execfile(self.args[1], loaded_namespace)

            if self.options.command:
                exec self.options.command in loaded_namespace
        except Exception:
            if report_to_sentry:
                exc_info = sys.exc_info()
                RavenErrorReporter.capture_exception(exc_info=exc_info)
            raise
