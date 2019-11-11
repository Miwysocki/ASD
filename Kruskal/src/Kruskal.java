import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;

public class Kruskal {

	public static class Edge implements Comparable<Edge>{
		public Edge(int weight, int beg, int end) {
			super();
			this.weight = weight;
			this.beg = beg;
			this.end = end;
		}
		int weight;
		int beg,end;
		@Override
		public int compareTo(Edge o) {
			// TODO Auto-generated method stub
			int compareWeight = ((Edge) o).weight;
			return this.weight - compareWeight; //asceniding order
		}
	}

	public static Edge[] e;
	public static int it;
	
	public static void main(String[] args) throws FileNotFoundException {
		// TODO Auto-generated method stub
		
		File file = new File("In0303.txt");
		PrintWriter zapis = new PrintWriter("out0303.txt");
        Scanner in = new Scanner(file);
        int n = in.nextInt(); //ilosc wierzcholków
        int m = in.nextInt(); //ilosæ krawêdzi
        Edge[] e = new Edge[m*2];
        String p = in.nextLine();//przejscie do 2 linii
        int linia =2;
        int suma_wag=0;
        it=0;
        System.out.println(m);
        
    while(in.hasNextLine()) {
        String s = in.nextLine();
        String[] tab = s.split(" "); //tablica stringów     
        for(int j =0; j <tab.length; j++) {
        	int end = Integer.parseInt( tab[j] );
        	j++;
        	int weight = Integer.parseInt( tab[j] );
        	e[it] = new Edge(weight,linia-1,end);
        	it++;
        }
        linia++;
    }
        
       
        
        Arrays.sort(e);
        
        for(Edge o: e) {
        	
        	if(o != null) System.out.println(o.beg+" -> "+o.end+" "+o.weight);
        }        
        
        //usuniecie powtórzeñ
        boolean bylo;
        int h=0;
        int dl;
        if(e.length%2 ==0)	 dl=0;
        else dl =1;
        Edge[] ps = new Edge[e.length/2+dl];
        for(int i=0;i<e.length;i++) {
        	bylo = false;
        	for(int j =0;j<ps.length;j++) {
        		if(ps[j] != null && e[i].beg==ps[j].end && e[i].end==ps[j].beg ) {bylo = true;}
        	}
        	if(!bylo) {ps[h] = e[i];h++;}        	
        }
        
        System.out.println("SKROCONE");
for(Edge o: ps) {
        	
        	if(o != null) System.out.println(o.beg+" -> "+o.end+" "+o.weight);
        }
		
		//Na pocz¹tku ka¿dy wierzcho³ek bêdzie w osobnym zbiorze
		 int[][] zbior = new int[n][n];
		 for(int i=0; i<n; i++) {
			 for(int j=0; j<n; j++) {
				 zbior[i][j] = 0;
			 }
		 }
		 
		 
		for(int i=0; i<n; i++) { 
			zbior[i][0] = i+1;
		}

		//wybranie krawêdzi o najmniejszej wadze
		int min =0;
		boolean znaleziono = false;
		int dolaczone = 0;
		Edge[] wynik = new Edge[ps.length-1];
		int z=0;
		
		int stop=0;
		while(dolaczone < n-1) { //Jeœli liczba krawêdzi do³¹czonych do rozwi¹zania wynosi v-1  zakoñcz dzia³anie algorytmu.
			for(Edge o: ps) {if(o != null) {
				//krawedzie posortowane wagami wiec obecna jest minimalna
				while(min<o.weight) min++;
				if(o.weight == min) {
					//znaleziono o wadze minimalnej
					znaleziono = true;
					boolean zn_beg = false;
					boolean zn_end = false;
					boolean cykl = false;
					int z1 = -1;
					int z2 = -1;
					//sprawdzenie czy dodanie wierzcho³ka nie stworzy cyklu
					//sprawdzamy, czy wierzcho³ki znajduj¹ siê w ró¿nych zbiorach. Jeœli tak, krawêdŸ do³¹czamy do rozwi¹zania, a te dwa zbiory scalamy.
					for(int i=0; i<n; i++){
						for(int j=0;j<n;j++) {
							if(zbior[i][j] == o.beg ) {
								zn_beg = true;
								z1 =i;
							}
							if(zbior[i][j] == o.end ) {
								zn_end = true;
								z2=i;}

						}
						if(zn_beg==true && zn_end==true) { //oba w tym samym zbiorze
							cykl = true;
						}
						zn_beg=false; zn_end =false;
					}
					if(cykl) ;//jesli znajduja sie w jednym nie do³¹czamy
					else {// krawêdŸ do³¹czamy do rozwi¹zania, a te dwa zbiory scalamy.
						suma_wag += o.weight;
						wynik[z] = o;
						z++;
						dolaczone++;
						//scalanie
						
						//przepisanie z1 do nowej temp 
						int temp[] = new int[n];
						for(int i=0;i<n;i++) {
							temp[i] = zbior[z1][i];
						}
						//dopisanie temp 
						int id_dopisywanej=0;
						for(int i=0;i<n;i++) {
							if(zbior[z2][i] ==0) {
								zbior[z2][i] = temp[id_dopisywanej];
								id_dopisywanej++;
							}
						}
						//wyzerowanie z1
						for(int i=0;i<n;i++) {
							zbior[z1][i] = 0;
						}
						
						
						//wypisanie scalonych zbiorów
						for(int i=0;i<n;i++) {
							for(int j=0;j<n;j++) {
						//		System.out.println("element "+j+" zbioru "+i+" = "+zbior[i][j]);
							}
						}

					}

				}
        	else min++;
        }
		znaleziono = false;
	}
			
	}
		
		System.out.println("minimalna liczba krawêdzi= "+z);
		System.out.println("suma wag = "+suma_wag);
		zapis.println(suma_wag);
	for(Edge o: wynik) {
		if(o != null) {
			System.out.println(o.beg+" "+o.end+" ["+o.weight+"]");
			zapis.print(o.beg+" "+o.end+" ["+o.weight+"], ");
		}
		
	}
		in.close();
        zapis.close();
	}


}
