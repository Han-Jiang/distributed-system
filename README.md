#distributed-system
====
分布式去重系统

#程序功能：
> 给定一个很大的文本文件，文件的内容为一行一行的数据，行数据无序且有重复，
> 程序需要使用分布式的思想来对这个大文件去重, 并得到一个最终的有序的去重后的文件

**假定** 单台机器的内存无法处理，并有足够多的同类型的机器，机器之间内部已经组好网络

hosts.cfg 文件指明了一群可供使用的计算机的主机地址

系统分为主机端和计算集群
主机端会将信息分配给每一个计算单元，计算单元将结果处理完后会返回给主机端，主机端负责将所有计算单元返回来的结果合并为最终的结果

关键字：`MapReduce`，`Socket编程`，`ExcutorService`, `生产者消费者模式`, `ArrayBlockingQueue`,`ConcurrentSkipListMap`,`Future`



# The Abstract
The server will forward the strings by the ASCII code of the first character of the string module the number of the machine, apart from the data, a line number is also send with the string. The client only forward data and merge the result from the server. All the other jobs are handled in the server.

The server also split the data it receive using the mechanism of modulo of the ASCII value of the second character of the string, this ensure all the strings are equally distributed in different file and also ensure all the string with the same value will be in the same file. Then the server can get rid of the repeat value file by file using a hash set and save it back into a file.

Lastly, the client can request data from the server by repeatedly send a request data command with a start line number and an end line number. (This is what the counter use for). All the result in the range requested will be merged using a thread safe concurrent skip list and then save into a file. Finally, all the client need to do is read the data file by file and output it.

The Design Graph 
The Design of the Client side I – Forwarding Data


The forward mechanism

The Design of the Client side II – Receive Results and Get Output


The Design of the Server Side

The Performance



We can see form the chart that when using more machines, less time is needed to handle the data, however, when using more than a certain number of machines, here is about 8 or 9, the more machines are used, the more time is needed. This may because the IO cost will be too high when using too much machines while the speed of handling the data is already quick enough.

A drawback of this design is that the program is very slow when handling with very big file. This may because too much data is transfer through the network (add a counter to every string increase the size).
Detail Explanation

# How to send a command
A command is send by a string start with "#", if the original data begin with "#", a "*" will be added as the first character. After the host receive a string begin with "*" by network, the machine will take the "*" off.
Initial actions of the Client
The client will create a producer thread to receive data form system in and N consumer threads to send the data to the server.

# How the client handle the input data
The producer thread will put all the input data into different string buffer with a counter, which string buffer to put the data is decided by the ASCII code of the first character of the data modulo the number of machine
How the consumer handle the data
The consumer will take data from its buffer, it will keep taking data from the buffer until there is no any data and the producer has stop to produce any data. Each consumer will connect to an available machine and send the data to it.

# Initial actions of the Server
When a connection is establish, just like the client, the server will create a producer thread to receive data and many file saver thread to save data. 

# How the server handle the data
The producer thread of the server will put all the data into different buffer by the ASCII code of the second character of the string modulo the number of file it split. A number of file saver thread is also created, these thread will keep taking data from the buffer and save it into file. When the client send a "#ODF" command (Original Data Finished), the serve will call a number of reducer thread, each of the thread will read all the data from a file and put it into a hash set, and then save all the element back into a new file and delete the old file. 

# How the client request data
The client will send a command like "#RequestData*number*number" where the number is a start number and an end number, for example "#RequestData*0*100". 

# How the server send back to the request data
When the server receive data like "#RequestData*1500000*3000000" the server will create an executor service and a number of thread to read the file in the file system, for each file reader thread, they will read out the data with the counter in the range of 1500000 and 3000000, all these data is put into a concurrent skip list, when all this reader thread finish, the sender thread will send all the data in the concurrent skip list to the Client.

# How the client receive request data
After the client have receive the data form the serve, all the data is put into a single concurrent skip list (This ensure the data is in order), when all the servers has send back a command "#SRDF"(Send Request Data Finished) the client will call a file saver thread and save the file into the disk.

# How the client get all the result data
When the client producer receive data, it will keep a counter for the total line of the data, then the client can repeatedly send request data command by gradually increase the start line number and end line number. When the end line number is bigger than the total line number, the end line number will be replace by the total line number and it is consider as the last request data command the client should send.
