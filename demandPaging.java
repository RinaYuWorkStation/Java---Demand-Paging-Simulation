
import java.util.ArrayList;

public class demandPaging {
    
    public static void main(String[] args) {
        int machineSize = Integer.parseInt(args[0]);
        int pageSize = Integer.parseInt(args[1]);
        int processSize = Integer.parseInt(args[2]);
        int jobMix = Integer.parseInt(args[3]);
        int numOfReferences = Integer.parseInt(args[4]);
        String replaceAlgo = args[5];
        
        System.out.println("The Machine size is " + machineSize);
        System.out.println("The Page size is " + pageSize);
        System.out.println("The Process size is " + processSize);
        System.out.println("The Job mix is " + jobMix);
        System.out.println("The Number of references per process is " + numOfReferences);
        System.out.println("The Replacement Algorithm is " + replaceAlgo);
         System.out.println("The level of debugging output is " + Integer.parseInt(args[6]));
        System.out.println();
        
        //	demandPaging pager = new demandPaging(machineSize,pageSize,processSize,jobMix,numOfReference,replaceAlgo);
        ArrayList<Process> processlist= new ArrayList<Process>();
        int frameNum = machineSize/pageSize;
        ArrayList<int[]> pageFrame= new ArrayList<int[]>();
        int pageFaultCount = 0;
        int evictedCount = 0;
        int runningSum = 0;
        //		 StringBuilder output = new StringBuilder();
        
        //creating frames . [0] is processNum, [1] is targetpage, [2] is last used cycle, [3] is added cycle
        for(int i = 0; i < frameNum ; i++)
        {
            int[] newframe = new int[4];
            newframe[0] = -1;
            newframe[1] = -1;
            newframe[2] = 0;
            newframe[3] = 0;
            pageFrame.add(newframe);
        }
        
        //adding process to processlist
        Process p0 = new Process(processSize, numOfReferences, 1, 0, 0, 1);
        Process p1 = new Process(processSize, numOfReferences, 0.75, 0.25, 0, 1);
        Process p2 = new Process(processSize, numOfReferences, 0.75, 0, 0.25, 2);
        Process p3 = new Process(processSize, numOfReferences, 0.75, 0.125, 0.125, 3);
        Process p4 = new Process(processSize, numOfReferences, 0.5, 0.125, 0.125, 4);
        
        
        
        switch(jobMix){
            case 1:  processlist.add(p0);
                break;
                
            case 2:	for(int i = 1; i <= 4; i++){
                Process newProcess = new Process(processSize, numOfReferences, 1, 0, 0, i);
                processlist.add(newProcess);}
                
                break;
            case 3: for(int i = 1; i <= 4; i++)
            {
                Process newProcess = new Process(processSize, numOfReferences, 0, 0, 0, i);
                processlist.add(newProcess);
            }
                
                break;
            case 4:
                processlist.add(p1);
                processlist.add(p2);
                processlist.add(p3);
                processlist.add(p4);
                
                break;
        }
        int cycleCount = 1;
        int RoundRobin = 3;
        while(true)
        {
            boolean allFinished = true;
            for(Process p : processlist)
            {
                allFinished &= p.isFinished();
            }
            if(allFinished){
                break; //if all the project is finished, get out of while loop
            }
            
            for (int m = 0; m< processlist.size(); m++){
                Process p = processlist.get(m);
                
                for(int i = 0; i < RoundRobin; i++)
                {
                    int targetPage = p.getCurrentReference()/pageSize;
                    boolean Hit= false;
                    
                    // check if the page is in the pageframe
                    for(int frame = pageFrame.size()-1; frame >= 0; frame--)
                    {
                        int[] currentFrame = pageFrame.get(frame);
                        
                        if (currentFrame[0] != -1) {// if the frame is initialized
                            if (currentFrame[0] != p.getProcessNum()) {
                                //do nothing
                            } else {
                                if (currentFrame[1] != targetPage) {
                                    // do nothing
                                } else {
                                    currentFrame[2] = cycleCount;
                                    Hit = true;
                                    break;
                                }
                            }
                        }
                        
                    }
                    // not hit, check for free frame
                    if(!Hit)
                    {
                        for(int frame = pageFrame.size()-1; frame >= 0; frame--)
                        {
                            int[] currentFrame = pageFrame.get(frame);
                            if(currentFrame[0] == -1)
                            {// update frame's info
                                currentFrame[0] = p.getProcessNum();
                                currentFrame[1] = targetPage;
                                currentFrame[2] = cycleCount;
                                currentFrame[3] = cycleCount;
                                Hit = true;
                                pageFaultCount++;
                                p.addFault();
                                break;
                            }
                        }
                    }
                    //no free frame
                    if(!Hit)
                    {
                        // fifo
                        if(replaceAlgo.equals("fifo")){
                            int min = pageFrame.get(0)[3];
                            int pos = 0;
                            for(int frame = pageFrame.size()-1; frame >= 0; frame--)
                            {
                                int[] currentFrame = pageFrame.get(frame);
                                if(currentFrame[3] < min)
                                {
                                    min = currentFrame[3];
                                    pos = frame;
                                }
                            }
                            int[] evictedFrame = pageFrame.get(pos);
                            // add number of evict
                            // update frame
                            evictedCount++;
                            runningSum +=  (cycleCount-evictedFrame[3]);
                            processlist.get(evictedFrame[0]-1).addEvicted();
                            processlist.get(evictedFrame[0]-1).addRunningSum( (cycleCount-evictedFrame[3]));
                            
                            evictedFrame[0] = p.getProcessNum();
                            evictedFrame[1] = targetPage;
                            evictedFrame[2] = cycleCount;
                            evictedFrame[3] = cycleCount;
                        }//change here
                        
                        // lru
                        else if(replaceAlgo.equals("lru")){int min = pageFrame.get(0)[2];
                            int pos = 0;
                            for(int frame = pageFrame.size()-1; frame > 0; frame--)
                            {
                                int[] currentFrame = pageFrame.get(frame);
                                if(currentFrame[2] < min)
                                {
                                    min = currentFrame[2];
                                    pos = frame;
                                }
                            }
                            int[] evictedFrame = pageFrame.get(pos);
                            // add number of evict
                            evictedCount++;
                            runningSum +=  (cycleCount-evictedFrame[3]);
                            processlist.get(evictedFrame[0]-1).addEvicted();
                            processlist.get(evictedFrame[0]-1).addRunningSum( (cycleCount-evictedFrame[3]));
                            
                            // update frame
                            evictedFrame[0] = p.getProcessNum();
                            evictedFrame[1] = targetPage;	
                            evictedFrame[2] = cycleCount;
                            evictedFrame[3] = cycleCount;
                        }
                        
                        //random
                        else{ 
                            int randomPage = randomNumberGenerator.getRandomNumber(p);
                            randomPage = (randomPage + pageFrame.size()) % pageFrame.size();
                            int[] evictedFrame = pageFrame.get(randomPage);
                            // add number of evict
                            evictedCount++;
                            runningSum +=  (cycleCount-evictedFrame[3]);
                            processlist.get(evictedFrame[0]-1).addEvicted();
                            processlist.get(evictedFrame[0]-1).addRunningSum( (cycleCount-evictedFrame[3]));
                            // update frame
                            evictedFrame[0] = p.getProcessNum();
                            evictedFrame[1] = targetPage;	
                            evictedFrame[2] = cycleCount;
                            evictedFrame[3] = cycleCount;}
                        
                        // add number of fault
                        p.addFault();	
                        pageFaultCount++;
                    }
                    // add step
                    cycleCount++;
                    p.getNextRef();
                    if(p.isFinished()) {break;}
                }
            }
        }
        
        //print output 
        for(Process p : processlist)
        {
            if(p.getResidency() != -1)
                System.out.println("Process " + p.getProcessNum() + " had " + p.getFaultNum() + " faults and " + p.getResidency() + " average residency\n");
            else
            {	System.out.println("Process " + p.getProcessNum() + " had " + p.getFaultNum() + " faults.\n");
                System.out.println("With no evictions, the average residence is undefined.\n");
            }
        }
      //  System.out.println("\n");
        if(evictedCount != 0)
        {
            System.out.println("The total number of faults is " + pageFaultCount + " and the overall average residency is " + (double)runningSum/evictedCount + "\n");
        }
        else
        {
            System.out.println("The total number of faults is " + pageFaultCount + "\n");
            System.out.println("With no evictions, the overall average residence is undefined.\n");
        }
        
    }
}

