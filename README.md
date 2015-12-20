# Mind map as presentation

XMind mind map to [impress.js](https://github.com/impress/impress.js) presentation converter.

## Building jar

To build JAR, use following maven command:
```
mvn clean package assembly:single
```
## Running the application

To run the application, execute:
```
java -jar mmap-1.0-jar-with-dependencies.jar input_mind_map.xmind
```
To visit parent node after visiting all its children, use ``--revisit-parent`` or ``-R`` run parameter.
## LICENSE
Copyright 2015 Krzysztof Gądek

Released under GPL license.
