# ProducerConsumer
This task simulates receiving messages over a network stream and processing them efficiently.

The messages are received one at a time (serialized) so we are using a single producer,
but to possibly increase throughput, we want to use multiple processors.

We are asking you to use one thread to read the file one line at a time and a configurable 
number of threads to handle processing messages.

A sample file (messages.txt) is included that contains one message per line where values are delimited by '|'.

The first value in each line is the message ID.

  - message ID is of type String
  - message IDs can and will repeat
  - number of unique IDs is unknown
  - format of message IDs is unknown (ie. don't assume one character from A-Z)
 

The second value is how long it takes to process the message (in milliseconds) and the third value is the payload.
none of the values will contain the delimiter so you can assume there will at most be two '|' characters per line.

if message id is not present and processing time is present (|500|) then the producer needs to stop
producing for 500ms.  This simulates a pause in incoming messages.

The second value (the number of ms processing will take) cannot be used for anything other than sleeping.

In a real world scenario, processing time or delays between messages will not be know and processing will take whatever time it takes.
So this value must not be used in any decision making part of the algorithm.
 

**** All messages with the same ID must be processed in the order they appear in the file. ****

So for example, if these are the messages in the file:

A|1000|Monday

B|1000|Wednesday

D|3000|Friday

A|50|Tuesday

B|100|Thursday

|10000|

D|100|Saturday

 

then 'Monday', 'Wednesday', 'Friday' can be processed at the same time independently of each other (because IDs are different),

and 'Tuesday', 'Thursday', 'Saturday' can be processed at the same time independently of each other (because IDs are different),

but 'Tuesday' must be processed after 'Monday' (same ID of 'A'),

and 'Thursday' after 'Wednesday' (same ID of 'B')

and 'Saturday' after 'Friday' (same ID of 'D')

The producer will pause for 10 seconds before sending 'Saturday' for processing.

Each line provides the value for how long the consumer should sleep to simulate the message processing time.
You cannot sort the file, or read through the entire file before sending items to process.Must treat each line in the file as if you do not know the next line.
