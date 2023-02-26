<h1>ChatRMI</h1>

Distributed java messaging application using java RMI.

<h2> Compiling the Project </h2

Detailed descriptions on how to compile the project. This is important because already compiled projects might not be able to run on different versions of Java.

<h3> Prerequisites </h3> 

To compile this project, you'll need the Java Development Kit (Version 11) and its tool javac. 

<h3> Compiling </h3> 

In this project, we have created a folder "classes" that contain all the classses compiled. So to compile the sources: 
```
javac -d classes -cp classes *.java
```

<h2>Running the Project</h2>

<h3>Detailed descriptions on how to run the project.</h3>
Prerequisites

To run this project, you'll need the Java Development Kit (Version 11) and its tool java.

<h3> List of software and/or hardware required to run the project. </h3>
1. Define your CLASSPATH variable, with the path of "classes" folder.


```
export CLASSPATH=<yourPath>\classes
```

2. Run the rmiregistry 
```
rmiregistry &
``` 
<h3>Running</h3>


Step-by-step instructions on how to run the project.
1. Run the Server:
```
java Server or java -cp classes Serve
```
2. Run the Client: 
```
java Client localhost <clientId>
```
<h2> Project Functionalities </h2
  In this project, clients can join and leave the chatgroup and send messages.
  
  >The commands are:
  
  ```
  join
  send <message>
  leave
  ```
  
  
Additional Resources

If applicable, list of additional resources such as documentation, sample code, or related projects.

<h2>Authors</h2>

Maria Albino
Lorteau Erwan

<h2>License</h2> 
Free to use, copy, commercialize
