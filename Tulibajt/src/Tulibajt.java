import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Tulibajt {

	
	
	public static void main(String[] args) throws FileNotFoundException {
		// TODO Auto-generated method stub
		File file = new File("In0301.txt");
		PrintWriter zapis = new PrintWriter("out0301.txt");
        Scanner in = new Scanner(file);
        int n = in.nextInt(); // iloœæ par odmian tulibajtu,
        String wynik = "";
        in.nextLine();
        
        for(int g =0; g < n; g++)
        {
        	String a1 = in.nextLine();
        	String b1 = in.nextLine();
        	char[] a = a1.toCharArray();
        	char[] b = b1.toCharArray();
        	int[][] c = new int[a1.length()+1][b1.length()+1];
        	//pierwszy rz¹d i pierwsza kolumna wype³nione zerami
        	for(int p=0; p < a1.length(); p++)  c[p][0] = 0;
        	for(int p=0; p < b1.length();p++)  c[0][p] = 0;		
        	
        	//wype³nienie c
        	for(int i=1; i <= a1.length();i++) {
        		for(int j=1; j <= b1.length(); j++) {
        			if(a[i-1] == b[j-1]) {
        				c[i][j] = c[i-1][j-1] + 1;
        				
        			}
        			else {
        				wynik +=(a[i-1]);
        				c[i][j] = Math.max(c[i-1][j],c[i][j-1]);
        			}
        		}
        	}
        	
        	//zwrocenie osttniego elementu z c  	
            zapis.print(c[a.length][b.length] + " ");
            System.out.println(wynik);
        }
        
    zapis.close();    
    in.close();
	}

}
