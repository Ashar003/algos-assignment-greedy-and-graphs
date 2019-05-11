
/**
 * Public Transit
 * Author: Akash Sharma and Carolyn Yao
 * Does this compile? Y
 */

/**
 * This class contains solutions to the Public, Public Transit problem in the
 * shortestTimeToTravelTo method. There is an existing implementation of a
 * shortest-paths algorithm. As it is, you can run this class and get the solutions
 * from the existing shortest-path algorithm.
 */
import java.util.Arrays;
public class FastestRoutePublicTransit {

  /**
   * The algorithm that could solve for shortest travel time from a station S
   * to a station T given various tables of information about each edge (u,v)
   *
   * @param S the s th vertex/station in the transit map, start From
   * @param T the t th vertex/station in the transit map, end at
   * @param startTime the start time in terms of number of minutes from 5:30am
   * @param lengths lengths[u][v] The time it takes for a train to get between two adjacent stations u and v
   * @param first first[u][v] The time of the first train that stops at u on its way to v, int in minutes from 5:30am
   * @param freq freq[u][v] How frequently is the train that stops at u on its way to v
   * @return shortest travel time between S and T
   */
  public int myShortestTravelTime(
    int S,
    int T,
    int startTime,
    int[][] lengths,
    int[][] first,
    int[][] freq
  ) {
    // Your code along with comments here. Feel free to borrow code from any
    // of the existing method. You can also make new helper methods.
    return 0;
    
    //Intializations
    //Arrays(fill) helper method, which is quicker than a for loop
    int[] shortestTime = new int[lengths[0].length]; // I created an array here the size of the number of vertices which houses the shortest shortestTime
    Arrays.fill(shortestTime,Integer.MAX_VALUE );  // Integer.Max_Value is greatest value Java can have and we put it in the vertice's position. Why? Because we can adjust it later for the actual value and comparsions
    int count, index = 0;//count is for the while loop
                        //index fo rhelper function
    shortestTime[S] = 0; // The distance required to navigate to source vertex from the source vertex is zero
    
    Boolean[] trueFalsed= new Boolean[lengths[0].length]; // this array here the size of the number of vertices house whether or not we have checked it
    Arrays.fill(trueFalsed, false);// we haven't seen the vertices yet.. Intilization 
    int verticesNumber = lengths[0].length; // Acquire the value to store it: The length of the vertice
    verticesNumber = verticesNumber -1;
  
      while( count < verticesNumber){ // while count is less than number of vertices minus one because we already are looking at one
        
      trueFalsed[findNextToProcess(shortestTime, trueFalsed)] = true; // Since we have looked at it we set it to true
      int which = findNextToProcess(shortestTime, trueFalsed); // obtain the value from the array of vertices that has not been processed and has a relative minimum distance

      for (int vertex = 0; vertex < verticesNumber; vertex++) {// Using the shortest vertex , the for loop will help in calulating the time needed to get to its neighboring vertices
       int trainTime =nextTrain(first[which][vertex], freq[which][vertex], startTime, index ); // Acquire the time left to the next train
        if (trainTime + shortestTime[which]+lengths[which][vertex] < shortestTime[vertex] && (lengths[which][vertex]!=0 && !trueFalsed[vertex] && shortestTime[which] != Integer.MAX_VALUE) ) { 
          //using the nextTrain
          //Is the vertex I'm looking has not been updated yet in terms of time
          //Have we looked at the vertex yet
          //train and vertex is is it still not updated
          shortestTime[vertex] = trainTime + shortestTime[which] + lengths[which][vertex]; // ShortestTime of the vertex we are looking. Essentially, we adding up here time that is left to the next train + the shortest time of vertex and length 
          startTime+=shortestTime[vertex];  // startTime is equal the shorestTIme value of that vertice, calculaed up here
        }
      }//for loop
      count++;// increment count for while loop so it can end ultimately
    }//while loop
    return shortestTime[T]; 
  }

  
  int nextTrain(int first, int frequency, int startTime, int index) {
    while (!false) { // while the for loop is not false doesn't matter because we exit with a return
      if (first + (index * frequency) >= startTime) { //if the value first of two adjacevnt stations, we are looking at, added to index multiplied with frequency value (two adjacent vertices and their consecutive schedule ) and it's greater than the start Time ( mins from 5:30)
        return first + (index * frequency) - startTime; // then we return the amount of time that passes
      }
      index++; // increase index to assist with how many times we go between a certain station from the same station; Above
    }//while loop
  }//nextTrain
      
  // * @param S the s th vertex/station in the transit map, start From
  // * @param T the t th vertex/station in the transit map, end at
  // * @param startTime the start time in terms of number of minutes from 5:30am
  // * @param lengths lengths[u][v] The time it takes for a train to get between two adjacent stations u and v
  // * @param first first[u][v] The time of the first train that stops at u on its way to v, int in minutes from 5:30am
  // * @param freq freq[u][v] How frequently is the train that stops at u on its way to v
  // * @return shortest travel time between S and T

  /**
   * Finds the vertex with the minimum time from the source that has not been
   * processed yet.
   * @param times The shortest times from the source
   * @param processed boolean array tells you which vertices have been fully processed
   * @return the index of the vertex that is next vertex to process
   */
  public int findNextToProcess(int[] times, Boolean[] processed) {
    int min = Integer.MAX_VALUE;
    int minIndex = -1;

    for (int i = 0; i < times.length; i++) {
      if (processed[i] == false && times[i] <= min) {
        min = times[i];
        minIndex = i;
      }
    }
    return minIndex;
  }

  public void printShortestTimes(int times[]) {
    System.out.println("Vertex Distances (time) from Source");
    for (int i = 0; i < times.length; i++)
        System.out.println(i + ": " + times[i] + " minutes");
  }

  /**
   * Given an adjacency matrix of a graph, implements
   * @param graph The connected, directed graph in an adjacency matrix where
   *              if graph[i][j] != 0 there is an edge with the weight graph[i][j]
   * @param source The starting vertex
   */
  public void shortestTime(int graph[][], int source) {
    int numVertices = graph[0].length;

    // This is the array where we'll store all the final shortest times
    int[] times = new int[numVertices];

    // processed[i] will true if vertex i's shortest time is already finalized
    Boolean[] processed = new Boolean[numVertices];

    // Initialize all distances as INFINITE and processed[] as false
    for (int v = 0; v < numVertices; v++) {
      times[v] = Integer.MAX_VALUE;
      processed[v] = false;
    }

    // Distance of source vertex from itself is always 0
    times[source] = 0;

    // Find shortest path to all the vertices
    for (int count = 0; count < numVertices - 1 ; count++) {
      // Pick the minimum distance vertex from the set of vertices not yet processed.
      // u is always equal to source in first iteration.
      // Mark u as processed.
      int u = findNextToProcess(times, processed);
      processed[u] = true;

      // Update time value of all the adjacent vertices of the picked vertex.
      for (int v = 0; v < numVertices; v++) {
        // Update time[v] only if is not processed yet, there is an edge from u to v,
        // and total weight of path from source to v through u is smaller than current value of time[v]
        if (!processed[v] && graph[u][v]!=0 && times[u] != Integer.MAX_VALUE && times[u]+graph[u][v] < times[v]) {
          times[v] = times[u] + graph[u][v];
        }
      }
    }

    printShortestTimes(times);
  }

  public static void main (String[] args) {
    /* length(e) */
    int lengthTimeGraph[][] = new int[][]{
      {0, 4, 0, 0, 0, 0, 0, 8, 0},
      {4, 0, 8, 0, 0, 0, 0, 11, 0},
      {0, 8, 0, 7, 0, 4, 0, 0, 2},
      {0, 0, 7, 0, 9, 14, 0, 0, 0},
      {0, 0, 0, 9, 0, 10, 0, 0, 0},
      {0, 0, 4, 14, 10, 0, 2, 0, 0},
      {0, 0, 0, 0, 0, 2, 0, 1, 6},
      {8, 11, 0, 0, 0, 0, 1, 0, 7},
      {0, 0, 2, 0, 0, 0, 6, 7, 0}
    };
    FastestRoutePublicTransit t = new FastestRoutePublicTransit();
    t.shortestTime(lengthTimeGraph, 0);

    // You can create a test case for your implemented method for extra credit below
    int eclengthTimeGraph[][] = new int[][]{
      {0, 9, 0, 0, 0, 0, 0, 17, 0},
      {9, 0, 13, 0, 0, 0, 0, 20, 0},
      {0, 13, 0, 8, 0, 5, 0, 0, 7},
      {0, 0, 12, 0, 14, 19, 0, 0, 0},
      {0, 0, 0, 10, 0, 11, 0, 0, 0},
      {0, 0, 9, 19, 15, 0, 11, 0, 0},
      {0, 0, 0, 0, 0, 7, 0, 6, 15},
      {13, 16, 0, 0, 0, 0, 6, 0, 16},
      {0, 0, 7, 0, 0, 0, 11, 12, 0}
    };

    int ecFirstGraph[][] = new int[][]{
      {0, 5, 0, 0, 0, 0, 0, 9, 0},
      {5, 0, 7, 0, 0, 0, 0, 12, 0},
      {0, 9, 0, 8, 0, 3, 0, 0, 5},
      {0, 0, 8, 0, 10, 15, 0, 0, 0},
      {0, 0, 0, 10, 0, 9, 0, 0, 0},
      {0, 0, 5, 15, 11, 0, 3, 0, 0},
      {0, 0, 0, 0, 0, 1, 0, 0, 7},
      {9, 12, 0, 0, 0, 0, 0, 0, 8},
      {0, 0, 3, 0, 0, 0, 7, 8, 0}
    };

    int ecFreqGraph[][] = new int[][]{
      {0, 9, 0, 0, 0, 0, 0, 13, 0},
      {9, 0, 10, 0, 0, 0, 0, 16, 0},
      {0, 10, 0, 12, 0, 9, 0, 0, 7},
      {0, 0, 9, 0, 14, 19, 0, 0, 0},
      {0, 0, 0, 11, 0, 15, 0, 0, 0},
      {0, 0, 6, 16, 15, 0, 4, 0, 0},
      {0, 0, 0, 0, 0, 4, 0, 6, 11},
      {13, 16, 0, 0, 0, 0, 6, 0, 12},
      {0, 0, 7, 0, 0, 0, 11, 12, 0}
    };
    FastestRoutePublicTransit testEc = new FastestRoutePublicTransit();
    testEc.myShortestTravelTime(0, 2, 13, eclengthTimeGraph, ecFirstGraph, ecFreqGraph);
  }
}
