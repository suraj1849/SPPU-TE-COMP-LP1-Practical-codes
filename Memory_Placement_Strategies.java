package Everything;

public class Memory_Placement_Strategies {
/************************FIRST FIT************************
 package oscodes;

public class FirstFit {
	// Method to allocate memory to
	// blocks as per First fit algorithm
	static void firstFit(int blockSize[], int m,
	int processSize[], int n)
	{
	// Stores block id of the
	// block allocated to a process
	int allocation[] = new int[n];
	// Initially no block is assigned to any process
	for (int i = 0; i < allocation.length; i++)
	allocation[i] = -1;
	// pick each process and find suitable blocks
	// according to its size ad assign to it
	for (int i = 0; i < n; i++)
	{
	for (int j = 0; j < m; j++)
	{
	if (blockSize[j] >= processSize[i])
	{
	// allocate block j to p[i] process
	allocation[i] = j;
	// Reduce available memory in this block.
	blockSize[j] -= processSize[i];
	break;
	}
	}
	}
	System.out.println("\nProcess No.\tProcess Size\tBlock no.");
	for (int i = 0; i < n; i++)
	{
	System.out.print(" " + (i+1) + "\t\t" +
	processSize[i] + "\t\t");
	if (allocation[i] != -1)
	System.out.print(allocation[i] + 1);
	else
	System.out.print("Not Allocated");
	System.out.println();
	}
	}
	// Driver Code
	public static void main(String[] args)
	{
	 System.out.print("\n First Fit Memory Allocation \n");
	int blockSize[] = {100, 500, 200, 300, 600};
	int processSize[] = {212, 417, 112, 426};
	int m = blockSize.length;
	int n = processSize.length;
	firstFit(blockSize, m, processSize, n);
	}

}

*/
	
	
	
	
/************************NEXT FIT************************
	 package oscodes;
import java.util.Arrays;
public class NextFit {
	
	static void NextFit(int blockSize[], int m, int processSize[], int n) {
	
	int allocation[] = new int[n], j = 0;
	Arrays.fill(allocation, -1);
	
	for (int i = 0; i < n; i++) {
	
	int count =0;
	while (j < m) {
	count++; 
	if (blockSize[j] >= processSize[i]) {
	
	allocation[i] = j;
	
	blockSize[j] -= processSize[i];
	break;
	}
	j = (j + 1) % m;
	}
	}
	System.out.print("\nProcess No.\tProcess Size\tBlock no.\n");
	for (int i = 0; i < n; i++) {
	System.out.print( i + 1 + "\t\t" + processSize[i]
	+ "\t\t");
	if (allocation[i] != -1) {
	System.out.print(allocation[i] + 1);
	} else {
	System.out.print("Not Allocated");
	}
	System.out.println("");
	}
	}
	
	static public void main(String[] args) {
	 System.out.print("\n Next Fit Memory Allocation \n");
	int blockSize[] = {5, 10, 20};
	int processSize[] = {10, 20, 5};
	int m = blockSize.length;
	int n = processSize.length;
	NextFit(blockSize, m, processSize, n);
	}

}

*/
	
	
	
	
/************************BEST FIT************************
	 package oscodes;

public class BestFit {
	// Method to allocate memory to blocks as per Best fit
	// algorithm
	static void bestFit(int blockSize[], int m, int processSize[],
	int n)
	{
	// Stores block id of the block allocated to a
	// process
	int allocation[] = new int[n];
	// Initially no block is assigned to any process
	for (int i = 0; i < allocation.length; i++)
	allocation[i] = -1;
	// pick each process and find suitable blocks
	// according to its size ad assign to it
	for (int i=0; i<n; i++)
	{
	// Find the best fit block for current process
	int bestIdx = -1;
	for (int j=0; j<m; j++)
	{
	if (blockSize[j] >= processSize[i])
	{
	if (bestIdx == -1)
	bestIdx = j;
	else if (blockSize[bestIdx] > blockSize[j])
	bestIdx = j;
	}
	}
	// If we could find a block for current process
	if (bestIdx != -1)
	{
	// allocate block j to p[i] process
	allocation[i] = bestIdx;
	// Reduce available memory in this block.
	blockSize[bestIdx] -= processSize[i];
	}
	}
	System.out.println("\nProcess No.\tProcess Size\tBlock no.");
	for (int i = 0; i < n; i++)
	{
	System.out.print(" " + (i+1) + "\t\t" + processSize[i] + "\t\t");
	if (allocation[i] != -1)
	System.out.print(allocation[i] + 1);
	else
	System.out.print("Not Allocated");
	System.out.println();
	}
	}
	// Driver Method
	public static void main(String[] args)
	{
	 System.out.print("\n Best Fit Memory Allocation\n");
	int blockSize[] = {100, 500, 200, 300, 600};
	int processSize[] = {212, 417, 112, 426};
	int m = blockSize.length;
	int n = processSize.length;
	bestFit(blockSize, m, processSize, n);
	}

}

*/
	
	

	
/************************WORST************************
	 package oscodes;

public class WorstFit {
	// Method to allocate memory to blocks as per worst fit
	// algorithm
	static void worstFit(int blockSize[], int m, int processSize[],
	int n)
	{
	// Stores block id of the block allocated to a
	// process
	int allocation[] = new int[n];
	// Initially no block is assigned to any process
	for (int i = 0; i < allocation.length; i++)
	allocation[i] = -1;
	// pick each process and find suitable blocks
	// according to its size ad assign to it
	for (int i=0; i<n; i++)
	{
	// Find the best fit block for current process
	int wstIdx = -1;
	for (int j=0; j<m; j++)
	{
	if (blockSize[j] >= processSize[i])
	{
	if (wstIdx == -1)
	wstIdx = j;
	else if (blockSize[wstIdx] < blockSize[j])
	wstIdx = j;
	}
	}
	// If we could find a block for current process
	if (wstIdx != -1)
	{
	// allocate block j to p[i] process
	allocation[i] = wstIdx;
	// Reduce available memory in this block.
	blockSize[wstIdx] -= processSize[i];
	}
	}
	System.out.println("\nProcess No.\tProcess Size\tBlock no.");
	for (int i = 0; i < n; i++)
	{
	System.out.print(" " + (i+1) + "\t\t" + processSize[i] + "\t\t");
	if (allocation[i] != -1)
	System.out.print(allocation[i] + 1);
	else
	System.out.print("Not Allocated");
	System.out.println();
	}
	}
	// Driver Method
	public static void main(String[] args)
	{
	 System.out.print("\n Worst Fit Memory Allocation \n");
	int blockSize[] = {30,50,200,700,980};
	int processSize[] = {20,200,500,50};
	int m = blockSize.length;
	int n = processSize.length;
	worstFit(blockSize, m, processSize, n);
	}
}

*/
}
