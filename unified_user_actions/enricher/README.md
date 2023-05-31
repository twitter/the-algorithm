## Aurora deploy

## From master branch

```
aurora workflow build unified_user_actions/service/deploy/uua-partitioner-staging.workflow
```

## From your own branch

```
git push origin <LDAP>/<BRANCH>
aurora workflow build  --build-branch=<LDAP>/<BRANCH> unified_user_actions/service/deploy/uua-partitioner-staging.workflow
```

* Check build status:
  * Dev
    * https://workflows.twitter.biz/workflow/discode/uua-partitioner-staging/

## Monitor output topic EPS 
  * Prod
    * unified_user_actions: https://monitoring.twitter.biz/tiny/2942881
  * Dev
    * unified_user_action_sample1: https://monitoring.twitter.biz/tiny/2942879
