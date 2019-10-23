package aaa;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Sejf {

	 
	public static void main(String[] args) throws FileNotFoundException {
		File plik = new File("In0105.txt");
		Scanner s = new Scanner(new File("In0105.txt"));
		String a  = s.next();
		int szerokosc = Integer.parseInt(a); 
		a  = s.next();
		int prety = Integer.parseInt(a);
		int[] tab = new int[szerokosc+1];
		int strefa = 0;
		int licznik = 0;
		 int[] start = new int[szerokosc+1];
		 int[] koniec = new int[szerokosc+1];
		
		//wczytanie prêtów
		for(int i=0; i < prety; i++) {
			 //przypisanie danych pretów (x1i, y1i, x2i, y2i) 
				a  = s.next();  // x1
				a =  s.next();  // y1
				int y1 = Integer.parseInt(a);
				a  = s.next(); // x2
				a  = s.next(); //y2
				int y2 = Integer.parseInt(a);
				tab[y1] = y2; 
			
		}
	
		
		
		
	for(int i =0; i <= szerokosc; ) {
		if(tab[i] == 0 && i != szerokosc) { // poczatek strefy
			strefa++;
			start[strefa] = i;
			i++;//iteracja po rozpoczeciu
			licznik++;
			while(true) {
				if(tab[i] != 0 && tab[i] != i) {
					//przerwanie iterowania po strefie ale nie gdy poziomy
					koniec[strefa] = i;
					i = tab[i];
					break;
					}
				if(tab[i] == i) { //przerwanie gdy poziomy
					koniec[strefa] = i;
					strefa++;
					start[strefa] = i;
					tab[i] = 0;
										
					}
				if(i<szerokosc && tab[i] == 0) {i++; //iteracja po strefie  
					licznik++; }
				if(i == szerokosc) { //koniec korytarza
					koniec[strefa] = i;
					break;
					}
				}

			}
		
		else if(tab[i] != 0) { //jesli nie jest poczatkiem strefy
				i = tab[i];
				licznik++;
			}
		if(i == szerokosc) i++;
		}
		
		
		PrintWriter zapis = new PrintWriter("Out0105.txt");
		
		
		zapis.println("liczba przedzia³ów="+strefa+";licz="+licznik); //ile operacji fix
		for(int l=1; l <= strefa; l++) {
			zapis.println(start[l]+" "+koniec[l]);
		}		
		
		zapis.close();
		s.close();
	}

}
