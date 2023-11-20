package Everything;

public class cpu_scheduling {
	
/*********************FCFS***********************
import java.util.*;
public class FCFS {
	public static void main(String args[])
	{
	Scanner sc = new Scanner(System.in);
	System.out.println("Enter no of process: ");
	int n = sc.nextInt();
	int pid[] = new int[n]; // process ids
	int ar[] = new int[n]; // arrival times
	int bt[] = new int[n]; // burst or execution times
	int ct[] = new int[n]; // completion times
	int ta[] = new int[n]; // turn around times
	int wt[] = new int[n]; // waiting times
	int temp;
	float avgwt=0,avgta=0;
	for(int i = 0; i < n; i++)
	{
	System.out.println("Enter process " + (i+1) + " arrival time: ");
	ar[i] = sc.nextInt();
	System.out.println("Enter process " + (i+1) + " burst time: ");
	bt[i] = sc.nextInt();
	pid[i] = i+1;
	}
	//sorting according to arrival times
	for(int i = 0 ; i <n; i++)
	{
	for(int j=0; j < n-(i+1) ; j++)
	{
	if( ar[j] > ar[j+1] )
	{
	temp = ar[j];
	ar[j] = ar[j+1];
	ar[j+1] = temp;
	temp = bt[j];
	bt[j] = bt[j+1];
	bt[j+1] = temp;
	temp = pid[j];
	pid[j] = pid[j+1];
	pid[j+1] = temp;
	}
	}
	}
	// finding completion times
	for(int i = 0 ; i < n; i++)
	{
	if( i == 0)
	{
	ct[i] = ar[i] + bt[i];
	}
	else
	{
	if( ar[i] > ct[i-1])
	{
	ct[i] = ar[i] + bt[i];
	}
	else
	ct[i] = ct[i-1] + bt[i];
	}
	ta[i] = ct[i] - ar[i] ; // turnaround time= completion time- arrival time
	wt[i] = ta[i] - bt[i] ; // waiting time= turnaround time- burst time
	avgwt += wt[i] ; // total waiting time
	avgta += ta[i] ; // total turnaround time
	}
	System.out.println("\n ------ FCFS ------ \n");
	System.out.println("==========================================================================================");
	System.out.println("\nProcess\t\tArrivalT\tBurstT\t\tCompleteT\tTurnT\t\tWaitingT");
	System.out.println("=========================================================================================");
	for(int i = 0 ; i< n; i++)
	{
	System.out.println(pid[i] + "\t\t" + ar[i] + "\t\t" + bt[i] + "\t\t" + ct[i] +"\t\t" + ta[i] + "\t\t" +
	wt[i] ) ;
	}
	sc.close();
	System.out.println("\nAverage waiting time: "+ (avgwt/n)); // printing average waiting time.
	System.out.println("Average turnaround time:"+(avgta/n)); // printing average turnaround time.
	}

}
 */
	
	
	
	
/******************SJF************************
import java.util.*;
public class SJF {
	public static void main (String args[])
	{
	Scanner sc =new Scanner(System.in);
	System.out.println("Enter no of process: ");
	int n= sc.nextInt();
	int pid[] = new int[n]; // it takes pid of process
	int at[] = new int[n]; // at means arrival time
	int bt[] = new int[n]; // bt means burst time
	int ct[] = new int[n]; // ct means complete time
	int ta[] = new int[n];// ta means turn around time
	int wt[] = new int[n]; // wt means waiting time
	int f[] = new int[n]; // f means it is flag it checks process is completed or not
	int k[]= new int[n]; // it is also stores brust time
	 int i, st=0, tot=0;
	 float avgwt=0, avgta=0;
	 for (i=0;i<n;i++)
	 {
	 pid[i]= i+1;
	 System.out.println("Enter process " + (i+1) + " arrival time: ");
	 at[i]= sc.nextInt();
	 System.out.println("Enter process " + (i+1) + " burst time: ");
	 bt[i]= sc.nextInt();
	 k[i]= bt[i];
	 f[i]= 0;
	 }

	 while(true){
	 int min=99,c=n;
	 if (tot==n)
	 break;

	 for ( i=0;i<n;i++)
	 {
	 if ((at[i]<=st) && (f[i]==0) && (bt[i]<min))
	 {
	 min=bt[i];
	 c=i;
	 }
	 }

	 if (c==n)
	 st++;
	 else
	 {
	 bt[c]--;
	 st++;
	 if (bt[c]==0)
	 {
	 ct[c]= st;
	 f[c]=1;
	 tot++;
	 }
	 }
	 }

	 for(i=0;i<n;i++)
	 {
	 ta[i] = ct[i] - at[i];
	 wt[i] = ta[i] - k[i];
	 avgwt+= wt[i];
	 avgta+= ta[i];
	 }
	 System.out.println("\n ------ SJF ------ \n");

	System.out.println("==========================================================================================");
	 System.out.println("\nProcess\t\tArrivalT\tBurstT\t\tCompleteT\tTurnT\t\tWaitingT");

	System.out.println("==========================================================================================");
	 for(i=0;i<n;i++)
	 {
	 System.out.println(pid[i] +"\t\t"+ at[i]+"\t\t"+ k[i] +"\t\t"+ ct[i] +"\t\t"+ ta[i] +"\t\t"+ wt[i]);
	 }

	 System.out.println("\nAverage waiting time: "+ (avgwt/n));
	 System.out.println("Average turnaround time:"+(avgta/n));
	 sc.close();
	}
}
 
*/
	
	
	
	
	
/****************Round Robin******************
package oscodes;
import java.util.Scanner; 
public class RoundRobin {
	public static void main(String args[])
	{
	int n,i,qt,count=0,temp,sq=0,bt[],wt[],tat[],rem_bt[];
	float awt=0,atat=0;
	bt = new int[10];
	wt = new int[10];
	tat = new int[10];
	rem_bt = new int[10];
	Scanner s=new Scanner(System.in);
	System.out.print("Enter the number of process (maximum 10) = ");
	n = s.nextInt();
	System.out.print("Enter the burst time of the process\n");
	for (i=0;i<n;i++)
	{
	System.out.print("P"+i+" = ");
	bt[i] = s.nextInt();
	rem_bt[i] = bt[i];
	}
	System.out.print("Enter the quantum time: ");
	qt = s.nextInt();
	while(true)
	{
	for (i=0,count=0;i<n;i++)
	{
	temp = qt;
	if(rem_bt[i] == 0)
	{
	count++;
	continue;
	}
	if(rem_bt[i]>qt)
	rem_bt[i]= rem_bt[i] - qt;
	else
	if(rem_bt[i]>=0)
	{
	temp = rem_bt[i];
	rem_bt[i] = 0;
	}
	sq = sq + temp;
	tat[i] = sq;
	}
	if(n == count)
	break;
	}
	System.out.print("--------------------------------------------------------------------------------");
	System.out.print("\nProcess\t BT \t TAT \t WT \n");
	System.out.print("--------------------------------------------------------------------------------");
	for(i=0;i<n;i++)
	{
	wt[i]=tat[i]-bt[i];
	awt=awt+wt[i];
	atat=atat+tat[i];
	System.out.print("\n "+(i+1)+"\t"+bt[i]+"\t"+tat[i]+"\t "+wt[i]+"\n");
	}
	awt=awt/n;
	atat=atat/n;
	System.out.println("\nAverage waiting Time = "+awt+"\n");
	System.out.println("Average turnaround time = "+atat);
	} 
}
*/
	
	
	
	
	
	
	
/********************Priority***********************
package oscodes;
import java.util.*;
import static java.lang.System.*;
public class Priority {
	public static void main(String [] args)
	{
	int n,i,j,k,temp;
	String temp1;
	Scanner sc= new Scanner(System.in);
	System.out.println("Enter the Number of process :");
	n=sc.nextInt();
	String task_id [] = new String [n];
	int arrival_time []= new int[n];
	int burst_time []= new int[n];
	int scheduled_time[]= new int[n];
	int total_time[] =new int[n];
	int waiting_time[]=new int[n];
	int priority[]=new int[n];
	j=0;k=0;
	for(i=0;i<n;i++)
	{
	System.out.println("Enter the Task_id :");
	task_id [i]=sc.next();
	System.out.println("Enter the arrival_time :");
	arrival_time [i]=sc.nextInt();
	System.out.println("Enter the burst_time :");
	burst_time [i]=sc.nextInt();
	System.out.println("Enter the priority of Process :");
	priority[i]=sc.nextInt();
	}
	System.out.println("Process Details :\n");
	for(i=0;i<n;i++)
	{
	System.out.println(task_id[i]+"\t"+arrival_time[i]+"\t"+burst_time[i]+"\t"+priority[i]+"\t");
	}
	//pripority Scheduling Code
	for(i=0;i<n-1;i++)
	{
	for(j=i+1;j<n;j++)
	{
	if(priority[i]>priority[j])
	{
	temp=arrival_time[j];
	arrival_time[j]=arrival_time[i];
	arrival_time[i]=temp;
	temp=burst_time[j];
	burst_time[j]=burst_time[i];
	burst_time[i]=temp;
	temp1=task_id[j];
	task_id[j]=task_id[i];
	task_id[i]=temp1;
	temp=priority[j];
	priority[j]=priority[i];
	priority[i]=temp;
	}
	}
	}
	j=0;
	k=0;
	for(i=0;i<n;i++)
	{
	j=burst_time[i];
	scheduled_time[i]=k;
	k=j+k;
	waiting_time[i]=scheduled_time[i]-arrival_time[i];
	total_time[i]=waiting_time[i]+burst_time[i];
	}
	System.out.println("\nProcess Details after priority scheduling\n");
	System.out.println("\nTask_id"+"\t"+"AT"+"\t"+"BT"+"\t"+"ST"+"\t"+"WT"+"\t"+"TAT");
	for(i=0;i<n;i++)
	{
	System.out.println(task_id[i]+"\t"+arrival_time[i]+"\t"+burst_time[i]+"\t"+scheduled_time[i]+"\t"+waiting_time[i]+"\t"+total_time[i]);
	}
	}
}
*/
	
}
