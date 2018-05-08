
public class Process
{
    private int processSize;
    private int processNum;
    private int numReferences;
    private int referenceIndex;
    private int currentReference;
    private int pageFault;
    private int evictedTime;
    private int runningSum;
    private double A;
    private double B;
    private double C;
    
    public Process(){
        
    }
    
    
    public Process(int processSize, int numReferences, double A, double B, double C, int processNum)
    {
        this.A = A;
        this.B = B;
        this.C = C;
        this.processSize = processSize;
        this.numReferences =numReferences;
        this.referenceIndex = 0;
        this.processNum = processNum;
        this.pageFault = 0;
        this.evictedTime = 0;
        this.runningSum = 0;
        this.currentReference = (111*processNum+processSize)%processSize;;
    }
    
    public int getNextRef()
    {
        int r = randomNumberGenerator.getRandomNumber(this);
        double y = r/(Integer.MAX_VALUE+1d);
        if(y < A)
        {
            currentReference = (currentReference+1+processSize)%processSize;
        }
        else if(y < A+B)
        {
            currentReference = (currentReference-5+processSize)%processSize;
        }
        else if(y < A+B+C)
        {
            currentReference = (currentReference+4+processSize)%processSize;
        }
        else
        {
            int rd = randomNumberGenerator.getRandomNumber(this);;
            currentReference = (rd + processSize) % processSize;
        }
        referenceIndex++;
        return currentReference;
    }
    
    public void addFault()
    {
        this.pageFault++;
    }
    
    public void addRunningSum(int sum)
    {
        this.runningSum = this.runningSum + sum;
    }
    
    public void addEvicted()
    {
        this.evictedTime++;
    }
    
    public int getRunningSum()
    {
        return this.runningSum;
    }
    
    public int getFaultNum()
    {
        return this.pageFault;
    }
    
    public int getProcessNum()
    {
        return this.processNum;
    }
    
    public int getCurrentReference()
    {
        return this.currentReference;
    }
    
    public int getEvictedTime()
    {
        return this.evictedTime;
    }
    
    public int getProcessSize()
    {
        return this.processSize;
    }
    
    public double getResidency()
    {
        if(this.getEvictedTime() == 0)
        {
            return -1;
        }
        else
        {
            return (double)this.runningSum/this.getEvictedTime();
        }
    }
    
    public boolean isFinished()
    {
        return referenceIndex >= numReferences;
    }
}

