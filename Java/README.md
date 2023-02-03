# Java version of the solution.

Implementation based on the series "Number of partitions of n into at least two distinct parts." http://oeis.org/A111133

## Recursive tree

Effectively, this algorithm constructs a binary tree of (𝑛,𝑘) values for each step-down. Due to the nature of 𝑓(𝑛−𝑘,𝑘) converging to 0, for >> numbers this results in a rather deep recursion. Due to this, a rather optimistic stack size is needed with ~20m of stack needed to fully recurse the algorithm for 300000.

## Memoization

As stated above, memoization is the integral part of this implementation, with processing being in 𝑂(3/2𝑛) time. With each subsequent iteration of 𝑘, a cache matrix is updated with the 𝑘=i; => 𝑓(𝑛,𝑘) combinations. The computed numbers as recursion is computed, grow exponentially.

## How to Run
Requires:
- Java 8
- Maven (for Java 8)
Jenv(with mvn plugin) is recommended for both maven and java.

Build `jar` with `mvn clean install`. 

Run from the source dir with `java -Xss${StackSize} -Xmx${HeapSize} -XX:+UseG1GC -jar target/untitled1-1.0-SNAPSHOT-jar-with-dependencies.jar n`.

### Example runs

 - n=30 (`java -XX:+UseG1GC -jar algo.jar 30`) execTime < 0s
 
 `295`
 
 - n=300 (`java -XX:+UseG1GC -jar algo.jar 300`) execTime < 0s
 
 `114872472063`
 
 - n=3k (`java -XX:+UseG1GC -jar algo.jar 3000`) execTime < 0s
 
 `6528822291254875226798745765109274957311`
 
 - n=30k (`java -Xss3m -Xmx500m -XX:+UseG1GC -jar algo.jar 30000`) execTime ~2s
 
 `2280495429685307410285127149614617420323397876626261673669342386904544041248139597528319024597884807009441686523360116682536825407999`
 
  - n=300k (for systems that can handle) (`java -Xss20m -Xmx28g -XX:+UseG1GC -jar algo.jar 300000`) execTime ~106s
 
 `4210862274208599320132457681361205631766332745531366097251182523840271778374041776996777544268557708791556972634240204931182275636709667458901634960056418648460211352341024814062465102985788332978802711358792090676627722144184552304071441127403307150949156714152542445860792515401696279114099266905863851501599751992437947170596759659111476740607340695732652666851170071652280194048602757002299800991344524743320843401058188959`
