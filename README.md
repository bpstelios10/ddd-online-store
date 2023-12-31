# SPRING WEB WITH CLEAN ARCHITECTURE

COMMIT BY COMMIT IMPLEMENTATION OF A SPRING WEB APP THAT IMPLEMENTS DDD WITH ONION ARCHITECTURE

Implementation will follow this specific onion architecture (since some keep repositories at the outmost layer):

<img src="docs-resources/OnionArchitecture.png" style="margin-left: 20px; display: block;" width="350" height="310" alt="architecture">

Notes: for technical documentation/guides have a look [here](docs-resources/TECH-DOC.md)

## IMPLEMENTATION NOTES

### DATABASES

For data storage, implementation uses an H2 for local executions and MONGODB for any deployed environment (docker-compose is treated as deployed one).
