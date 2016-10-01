Simple Remote Procedure Call Demo Application
=============================================

A demo application with implements a simple remote procedure call application.

## Configuration
Map between service and implementing class is specified via property file
located by default here _resources/services.properties_

## Usage
### Server
Run the following command in console:
    
    java Invoker server <tcp_port_number> [<number_of_threads_in_execution_pool>]

### Client
Run the following command in console:
    
    java Invoker client <server_host> <server_tcp_port_number> <service> <method> <method_parameters>

## Notes
    
* TODO

    * Own product building system like Ant
    * Self-checker tests
    
## Author
[Yevgeny](yevgor@gmail.com)
