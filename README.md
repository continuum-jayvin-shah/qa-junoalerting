# qa-junoalerting
Read Me

Juno ALerting Automation Project contains regression test scenarios for Alerting MS

Types of Scenarios :
1. CRUD Scenarios
2. Consolidation Scenarios with different combinatios
3. Snooze Scenarios with different combinatios
4. Manual Closure scenarios
5. Consolidation Parent closure scenarios
6. Suspension scenarios on different level
7. Permanent Consolidation scenarios
8. Alerting Bridge Scenarios
9. Filter Field and Legacy Id scenarios
10. Negative Scenarios


How to set up this framework on local :

1. Checkout project from GIT using proper credentials.
2. Run "mvn update"
3. Update tag in Runner Class [test/java/TestRunner] 
4. Run using TestNg Runner or mvn test command
5. Result will come on Target Folder
6. All feature file present in feature fodler under resources
7. maven.properties contains all API Path and Test Env links which is been used in this framework
8. Test data present in Data folder under resources folder on Env level i.e. DT and QA.




