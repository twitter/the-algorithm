{
  "role": "discode",
  "name": "uua-kill-staging-services",
  "config-files": [],
  "build": {
    "play": true,
    "trigger": {
      "cron-schedule": "0 17 * * 1"
    },
    "dependencies": [],
    "steps": []
  },
  "targets": [
    {
      "type": "script",
      "name": "uua-kill-staging-services",
      "keytab": "/var/lib/tss/keys/fluffy/keytabs/client/discode.keytab",
      "repository": "source",
      "command": "bash unified_user_actions/scripts/kill_staging.sh",
      "dependencies": [{
         "version": "latest",
         "role": "aurora",
         "name": "aurora"
      }],
      "timeout": "10.minutes"
    }
  ],
  "subscriptions": [
   {
     "type": "SLACK",
     "recipients": [
       {
         "to": "unified_user_actions_dev"
       }
     ],
     "events": ["WORKFLOW_SUCCESS"]
   },
   {
     "type": "SLACK",
     "recipients": [{
       "to": "unified_user_actions_dev"
     }],
     "events": ["*FAILED"]
   }
  ]
}
