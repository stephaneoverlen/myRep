import ON.BoyerMoore;
import ON.FinishedAutomat;
import ON.KnutMorrisPratt;
import ON.RabinKarp;
import ON.File.Reader;

import java.io.IOException;

public class Main {

	public static void main(String[] args) throws IOException, NumberFormatException {
		String fileName = null;
		String motif = null;
		String content;
		int algo = 0;
		switch(args.length) {
			case 3:
				fileName = args[2];
			case 2:
				algo = Integer.parseInt(args[1]);
				motif = args[0];
				break;
			default:
				System.err.println("usage: java Main <motif> <algo> (<fichier_texte>)");
				System.exit(1);
		}
		Reader reader = (fileName == null) ? null : new Reader(fileName);
		content = (reader == null) ? null : reader.getContent();
		switch(algo) {
			case 1: //Rabin-Karp
				if (content == null) {
					RabinKarp rabinKarp = new RabinKarp(motif, null);
					System.out.print(rabinKarp.toString());
				}
				else {
					if(motif.length() > content.length()){                                                              // if pattern bigger than text
						System.err.println("Pattern too long, must be less than text's length.");
						System.exit(0);                                                                                 // exit program
					}
					else {
						RabinKarp rabinKarp = new RabinKarp(motif, content);
						rabinKarp.findSymbol();
						rabinKarp.printResult();
					}
				}
				break;
			case 2: //Automate fini
				if (fileName == null) {
					FinishedAutomat finishedAutomat = new FinishedAutomat(motif);
					System.out.print(finishedAutomat.toString());
				}
				else {
					if(motif.length() > content.length()){                                                              // if pattern bigger than text
						System.err.println("Pattern too long, must be less than text's length.");
						System.exit(0);                                                                                 // exit program
					}
					else {
						if(motif.length() > content.length()){                                                          // if pattern bigger than text
							System.err.println("Pattern too long, must be less than text's length.");
							System.exit(0);                                                                             // exit program
						}
						else {
							FinishedAutomat finishedAutomat = new FinishedAutomat(motif);
							finishedAutomat.findSymbolOn(content);
							finishedAutomat.printResult();
						}
					}
				}
				break;
			case 3: //Knut-Morris-Pratt
				if (content == null){
					KnutMorrisPratt knutMorrisPratt = new KnutMorrisPratt(motif);
					System.out.print(knutMorrisPratt.toString());
				}
				else {
					if(motif.length() > content.length()){                                                              // if pattern bigger than text
						System.err.println("Pattern too long, must be less than text's length.");
						System.exit(0);                                                                                 // exit program
					}
					else {
						KnutMorrisPratt knutMorrisPratt = new KnutMorrisPratt(motif);
						knutMorrisPratt.findSymbolOn(content);
						knutMorrisPratt.printResult();
					}
				}
				break;
			case 4: //Boyer-Moore
				if (content == null) {
					BoyerMoore boyerMoore = new BoyerMoore(motif);
					System.out.print(boyerMoore.toString());
				}
				else {
					if(motif.length() > content.length()){                                                              // if pattern bigger than text
						System.err.println("Pattern too long, must be less than text's length.");
						System.exit(0);                                                                                 // exit program
					}
					else {
						BoyerMoore boyerMoore = new BoyerMoore(motif);
						boyerMoore.findSymbolOn(content);
						boyerMoore.printResult();
					}
				}
				break;
			default:
				System.err.println("Algorithm not implemented");
				System.exit(2);
		}
	}
}
