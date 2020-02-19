import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;

public class Dijkstra {

	public static void main(String[] args) throws FileNotFoundException {
		// TODO Auto-generated method stub
		File file = new File("In0304.txt");
        Scanner in = new Scanner(file);
        PrintWriter zapis = new PrintWriter("Out0304.txt");
        int n = in.nextInt(); // ilsoc wiercho³ków
        int s = in.nextInt(); // zrodlo
        int inf=Integer.MAX_VALUE;
        
        int[][] tab = new int[n][n];
        for(int i=0; i < n; i++) { //dla kazdej krawedzi
        	for(int j=0;j<n;j++) {
        	int a = in.nextInt();
        	if(a == 99) {
        		a =inf;
        		}
        		tab[i][j] = a;
        	}
        }
        
        int id_s = 0;
        int u =s;
        int[] dist = new int[n];
        int[] pred = new int[n]; 
        int[] fin = new int[n];
        int[] q = new int[n]; //wierzcho³ki
        int[] policzone = new int[n];
        
        for(int i=0; i<n;i++) {
        	dist[i] = inf;
        	pred[i] = -1;
        	q[i] = i+1;
        }
        
        dist[s-1] = 0; //koszt dojœcia do samego siebie
        
        
        boolean empty;
        while(true) {
        	empty = true;
        	for(int i=0;i<n;i++) {
        		if(q[i] != 0) empty = false;
        	}
        	if(empty) break;

        	
        	for(int i=0;i<n;i++) {
        		if(q[i] != 0) empty = false;
        	}        	
        	
        	int min = Integer.MAX_VALUE;
        	for(int i=0;i<n;i++) {
        		if(dist[i] <= min && q[i]!= 0) { //&& q[i]!= 0 nie zosta³ usuniêty ???????????????
        			min = dist[i];
        			u = i; // u - indeks wierzcho³ka z minimalnym kosztem dojœcia
        		}
        	}
        		//Z Q do S przenieœ wierzcho³ek u o najmniejszym d [ u ]
        		policzone[id_s] = q[u];
        		id_s++;
        		q[u] = 0;
        		
        		for(int w =0;w<n;w++) {// przegl¹damy s¹siadów przeniesionego wierzcho³ka
        			if(tab[u][w] != inf ) { //&& w != u
        				//s¹siedzi
        				//Jeœli w nie jest w Q
        				boolean wq = false;
        				for(int k =0;k<n;k++) {
        					if(q[k] == w+1) {
        						wq = true;
        					}
        				}
        				if(wq) {// szukamy s¹siadów obecnych w Q
        					if(dist[ w ] > dist[ u ] + tab[u][w]) { // koszt dojœcia
        						dist[w] = dist[u] + tab[u][w];
        						pred[w] = u+1;
        					}
        				}
        			}
        		}
        	}   
        System.out.println("dist ="+Arrays.toString(dist));	
        System.out.println("pred ="+Arrays.toString(pred));
        zapis.println("dist"+Arrays.toString(dist));
        zapis.println("pred"+Arrays.toString(pred));
        zapis.close();
        }
	}


	