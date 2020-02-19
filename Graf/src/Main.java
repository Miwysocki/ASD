import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) throws FileNotFoundException {
		// TODO Auto-generated method stub
		File file = new File("In0502.txt");
        Scanner in = new Scanner(file);
        PrintWriter out = new PrintWriter("Out0502.txt");
        int n =  in.nextInt(); //ilosc wierzcho³ków
        in.nextLine();
        int[][] incydencja = new int[n+1][n+1];
        
        for(int i=0;i<=n;i++){
        	for(int j=0;j<=	n;j++) {
        		incydencja[i][j] = Integer.MAX_VALUE;
        	}
        }
        
        //wczytanie list incydencji
        //incydencja [startowy] [docelowy] = koszt
        int v =1; //lista ktorego wierzcholka    fix
        while(in.hasNextLine()) { 
        	String l = in.nextLine();
        	char[] lista = l.toCharArray(); 
        	
        	//stworzenie listy int       	
        	int[] list = new int[2*n] ; 
        	int k =0;
        	boolean negative = false;
        	
        	for(int y=0;y<lista.length;y++) {
        		if(lista[y] == ' ') y++;
        		if( lista[y] == '–' || lista[y] == '-') {
        			negative=true;
        			y++;
        			}
        		
        		list [k] = Character.getNumericValue(lista[y]); 
        		if(negative) list [k] *= -1;
        		negative = false;
        		k++;
        		
        	}

        	for(int j =0; j<list.length ; ) {
        		int docelowy = list[j];
        		j++;
        		int koszt = list[j];
        		j++;
        	//	System.out.println("docelowy "+docelowy + " koszt: "+koszt);
        		if(docelowy != 0) incydencja[v][docelowy] = koszt;
        	}
        	v++; //kolejny wierzcho³ek
        }
        int [][] t = incydencja;
        //po wczytaniu dodanie s0
        int dist[] = new int[incydencja.length];
        dist[0] =0;
        for(int f=1; f<incydencja.length;f++)  { 
        	incydencja[0][f] = 0;
        	dist[f] =0;	
        	}
        
        //Bellman-Ford
        for(int a=0; a< n-1;a++) {
        for(int p=0; p < incydencja.length;p++)
        {
	        	for(int k=0;k < incydencja.length;k++)
	        {
	        		int koszt = incydencja[p][k];
		        if (dist[p] != Integer.MAX_VALUE && dist[p] + koszt < dist[k] ) {
		        	if(k != 0)dist[k] = dist[p] + koszt;
		        }
		        
		      
	        }
        }
	}      
        
        for(int w=0; w < incydencja.length;w++) {
        	out.print(dist[w]+" ");
        	System.out.print(dist[w]+" ");
        }
        
        
        //wagi
        int[][] modyfikowana = new int[n+1][n+1];
        for(int i=0;i<=n;i++){
        	for(int j=0;j<=	n;j++) {
        		if(incydencja[i][j] != Integer.MAX_VALUE) {
        			modyfikowana[i][j] = incydencja[i][j] + dist[i] - dist[j]; 
        		}
        		else {
        			modyfikowana[i][j] = Integer.MAX_VALUE;
        		}
        	}
        }        
        
        System.out.println("");
        for(int i=0;i<=n;i++){
        	System.out.print("["+i+"]");
        	for(int j=0;j<=n;j++) {
        		if(modyfikowana[i][j] != Integer.MAX_VALUE)System.out.print(j+"("+modyfikowana[i][j]+") ");;
        		//System.out.println(i+"  "+j+" "+modyfikowana[i][j]);;
        	}
        	System.out.println("");
        }
        
        //Djikstra
        
        for(int i =1; i< n+1; i++) {
        	System.out.print("Delta^");
        	Dj(i,n,modyfikowana);
        	System.out.print(", D");
        	Dj(i,n,t);
        	System.out.println("");
        }
        
        
        
        
        
        out.close();
	}

	
	
	
	
	
	
	
	
	static void Dj ( int s, int n, int tab[][]){
	    int inf=Integer.MAX_VALUE;   
	    int id_s = 0;
	    int u =s;
	    int[] dist = new int[n+1];
	    int[] pred = new int[n+1]; 
	    int[] q = new int[n+1]; //wierzcho³ki
	    int[] policzone = new int[n+1];
	    
	    for(int i=0; i<=n;i++) {
	    	dist[i] = inf;
	    	pred[i] = -1;
	    	q[i] = i+1;
	    }
	    
	    dist[s] = 0; //koszt dojœcia do samego siebie
	    
	    
	    boolean empty;
	    while(true) {
	    	empty = true;
	    	for(int i=0;i<=n;i++) {
	    		if(q[i] != 0) empty = false;
	    	}
	    	if(empty) break;

	    	
	    	for(int i=0;i<=n;i++) {
	    		if(q[i] != 0) empty = false;
	    	}        	
	    	
	    	int min = Integer.MAX_VALUE;
	    	for(int i=0;i<=n;i++) {
	    		if(dist[i] <= min && q[i]!= 0) { //&& q[i]!= 0 nie zosta³ usuniêty ???????????????
	    			min = dist[i];
	    			u = i; // u - indeks wierzcho³ka z minimalnym kosztem dojœcia
	    		}
	    	}
	    		//Z Q do S przenieœ wierzcho³ek u o najmniejszym d [ u ]
	    		policzone[id_s] = q[u];
	    		id_s++;
	    		q[u] = 0;
	    		
	    		for(int w =0;w<=n;w++) {// przegl¹damy s¹siadów przeniesionego wierzcho³ka
	    			if(tab[u][w] != inf ) { //&& w != u
	    				//s¹siedzi
	    				//Jeœli w nie jest w Q
	    				boolean wq = false;
	    				for(int k =0;k<n+1;k++) {
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
	 //   System.out.println("dist ="+Arrays.toString(dist));
	    System.out.print("[");
	    for(int i=1; i<n+1;i++) {
	    	System.out.print(dist[i]+", ");
	    }
	    System.out.print("]");
	}
}
