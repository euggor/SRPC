Simple Remote Procedure Call Demo Application
=============================================

A demo application which implements a simple remote procedure call application.

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
    * Services and methods are case sensitive.
    * remoteCall() method throws SRPCClientException, if call failed.
    
### TODO
    * Product
        - An own product building system like Ant.
        - Logger usage instead of println.
        - A clear exception flow in server code.
        - More precise handling of method signatures.
        - Named and position-free input parameters for client and
          server.
        - Ability to specify services.properties file located from
          anywhere.
        - Multi-threaded version of the client which able to make batch
          calls to services at one invocation.
    * Tests
        - Fully automated self-checker tests under a harness like JUnit
          which able to run the server part.
        - Self-checking tests verifying the server part: wrong services,
          wrong client calls, etc.
    
## Author
[Yevgeny Go](yevgor@gmail.com)
