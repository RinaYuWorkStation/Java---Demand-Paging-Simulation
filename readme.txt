Demand Paging Simulation
------------------------

##This program simulates demand paging in operating systems. It predicts how many page faults are generated, depending on page size, program/process size, replacement algorithm (FIFO, RANDOM or LRU), and job mix. A driver generates memory reference, and a demand paging simulator decides if the reference causes a page fault. The driver uses round robin scheduling with q references for every process. 

#How to run:
please use terminal and 
javac demandPaging.java
java demandPaging (input string)

#The program is invoked with 6 command line arguments, 5 integers and a string
M, the machine size in words. 

P, the page size in words. 
S, the size of a process, i.e., the references are to virtual addresses 0..S-1. 
J, the ‘‘job mix’’, which determines A, B, and C, as described below. 
N, the number of references for each process. 
R, the replacement algorithm, FIFO, RANDOM, or LRU.

#Memory Address types: assume sequential disk memory and the next address is 
• w+1 mod S with probability A  , the address one higher than the current, sequential memory reference
• w-5 mod S with probability B , a lower address, backward branch
• w+4 mod S with probability C , a nearby higher address, jump in a else/then block
• a random value in 0..S-1 each with probability (1-A-B-C)/S, random address

#4 possible sets of processes: J=1,2,3,4
•	One process with A=1 and B=C=0, the simplest (fully sequential) case.
•	Four processes, each with A=1 and B=C=0.
•	Four processes, each with A=B=C=0 (fully random references).
•	One process with A=.75, B=.25 and C=0; one process with A=.75, B=0, and C=.25; one process with A=.75, B=.125 and C=.125; and one process with A=.5, B=.125 and C=.125.

#After the program reads in the input, it produces # page faults and average residency time (time page evicted – time page loaded) for each process. And finally after all processes, the total # page faults and overall average residency time. 



