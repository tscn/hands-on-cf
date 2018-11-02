
# Hands-on

## Prerequisites
Bundled zip containing
* jdk
* git
* cf cli
* 30 accounts in SFP with printed account names and passwords to hand out

* Demo app:
- Spring MVC with start page at / (template from pivotal-workshop)
- cf push
- cf logs
- cf scale
- cf set-env
- cf restart-app-instance
- "kill" (cf ssh, if infrastructure present)
- cf events
- cf map-route
- cf marketplace / create-service / bind-service
- cf restage / restart
- cf env (VCAP_SERVICES) -> spring cloud
- healthchecks (tbd)
- "make unhealthy"


* Find bug // i.e. remove throw new RuntimeException("find me")
Alternatives:
* cf push new version
* blue-green-deployment with auto-pilot

Hands-on for Cloudfoundry app development

1. start.sping.io
1. Open project in IntelliJ
1. Controller Hello ABBS townhall
1. gradlew build
1. cf api / login / target
1. cf push
1. Buildpacks explained
1. Apps Manager
1. Routes explained
~

