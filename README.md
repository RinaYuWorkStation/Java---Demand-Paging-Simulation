This program simulates demand paging in operating systems. It predicts how many page faults are generated, depending on page size, program/process size, replacement algorithm (FIFO, RANDOM or LRU), and job mix. A driver generates memory reference, and a demand paging simulator decides if the reference causes a page fault. The driver uses round robin scheduling with q references for every process. 

How to run:
please use terminal and 
javac demandPaging.java
java demandPaging (input string)

The program is invoked with 6 command line arguments, 5 integers and a string

M, the machine size in words. 
P, the page size in words. 
S, the size of a process, i.e., the references are to virtual addresses 0..S-1. 
J, the ‘‘job mix’’, which determines A, B, and C, as described below. 
N, the number of references for each process. 
R, the replacement algorithm, FIFO, RANDOM, or LRU.

Job mix:
• w+1 mod S with probability A  , the address one higher than the current, sequential memory reference
• w-5 mod S with probability B , a lower address, backward branch
• w+4 mod S with probability C , a nearby higher address, jump in a else/then block
• a random value in 0..S-1 each with probability (1-A-B-C)/S, random address

4 possible sets of processes: J=1,2,3,4
•	One process with A=1 and B=C=0, the simplest (fully sequential) case.
•	Four processes, each with A=1 and B=C=0.
•	Four processes, each with A=B=C=0 (fully random references).
•	One process with A=.75, B=.25 and C=0; one process with A=.75, B=0, and C=.25; one process with A=.75, B=.125 and C=.125; and one process with A=.5, B=.125 and C=.125.

After the program reads in the input, it produces # page faults and average residency time (time page evicted – time page loaded) for each process. And finally after all processes, the total # page faults and overall average residency time. 


Sample input 1:
10 10 20 1 10 lru 0
Sampe output 1:
The machine size is 10.
The page size is 10.
The process size is 20.
The job mix number is 1.
The number of references per process is 10.
The replacement algorithm is lru.
The level of debugging output is 0

Process 1 had 2 faults and 9.0 average residency.

The total number of faults is 2 and the overall average residency is 9.0.

Sample input 2:
1000 40 400 4 5000 fifo 0

Sample output 2:
The machine size is 1000.
The page size is 40.
The process size is 400.
The job mix number is 4.
The number of references per process is 5000.
The replacement algorithm is fifo.
The level of debugging output is 0

Process 1 had 57 faults and 1191.673076923077 average residency.
Process 2 had 136 faults and 1212.1937984496124 average residency.
Process 3 had 77 faults and 1202.3098591549297 average residency.
Process 4 had 149 faults and 1254.387323943662 average residency.

The total number of faults is 419 and the overall average residency is 1222.9111675126903.

Sample input3:

20 10 10 4 10 random 0


Sample output3:
The machine size is 20.
The page size is 10.
The process size is 10.
The job mix number is 4.
The number of references per process is 10.
The replacement algorithm is random.
The level of debugging output is 0

Process 1 had 2 faults and 33.0 average residency.
Process 2 had 4 faults and 2.5 average residency.
Process 3 had 4 faults and 3.25 average residency.
Process 4 had 4 faults and 5.333333333333333 average residency.

The total number of faults is 14 and the overall average residency is 6.0.


