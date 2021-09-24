ATM Command Line Application with Shell 2 and Spring Boot 2
===========================================================

### Build
Execute the following command from the parent directory to build the jar file:
```
mvn clean install
```

### Run
From the parent directory, execute the following command to start the application:
```
java -jar target/atm-cli-1.0-SNAPSHOT.jar
```
You should notice the application starting up:
```
  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::             (v2.0.0.M5)

2021-09-22 20:16:14.324  INFO 46005 --- [           main] com.takeoff.atmcli.Application           : Starting Application on Olgas-MBP with PID 46005 (/Users/olga.zakharenko/Documents/Projects/atm-cli/target/atm-cli-1.0-SNAPSHOT.jar started by olga.zakharenko in /Users/olga.zakharenko/Documents/Projects/atm-cli)
2021-09-22 20:16:14.326  INFO 46005 --- [           main] com.takeoff.atmcli.Application           : No active profile set, falling back to default profiles: default
2021-09-22 20:16:14.370  INFO 46005 --- [           main] s.c.a.AnnotationConfigApplicationContext : Refreshing org.springframework.context.annotation.AnnotationConfigApplicationContext@35851384: startup date [Wed Sep 22 20:16:14 EDT 2021]; root of context hierarchy
2021-09-22 20:16:14.755  INFO 46005 --- [           main] f.a.AutowiredAnnotationBeanPostProcessor : JSR-330 'javax.inject.Inject' annotation found and supported for autowiring
2021-09-22 20:16:15.553  INFO 46005 --- [           main] o.s.j.e.a.AnnotationMBeanExporter        : Registering beans for JMX exposure on startup
2021-09-22 20:16:15.562  INFO 46005 --- [           main] s.a.ScheduledAnnotationBeanPostProcessor : No TaskScheduler/ScheduledExecutorService bean found for scheduled processing
>
```
###Commands

Type in command line to see list of available commands
```
>help
```
Output:
```
AVAILABLE COMMANDS

Account
        authorize: Authorize account.
      * balance: Returns the account’s current balance.
      * history: Returns the account’s transaction history.
      * logout: Deactivates the currently authorized account.

Atm
      * deposit: Adds value to the authorized account.
      * withdraw: Removes value from the authorized account.

Built-In Commands
        clear: Clear the shell screen.
        exit, quit: Exit the shell.
        help: Display help about available commands.
        script: Read and execute commands from a file.
        stacktrace: Display the full stacktrace of the last error.

Commands marked with (*) are currently unavailable.
Type `help <command>` to learn more.
```

Type in to learn more about command 
```
help <command>
```
Example:

>help authorize


```
NAME
	authorize - Authorize account.

SYNOPSYS
	authorize [--user-id] string  [--pin] string  

OPTIONS
	--user-id  string
		
		[Mandatory]

	--pin  string
		
		[Mandatory]
```
