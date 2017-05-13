/**
 *@Project  :   pattern_research
 *@Package  :   ON
 *@Revision :   2.0.1
 *@Created  :   24/04/2017 18:49
 *@Author   :   Frederick NEY and Stephane OVERLEN
 */

package ON;

import java.util.ArrayList;
import java.util.List;

/**
 * <h1>Knut - Morris - Prat pattern finder.</h1>
 * <a>Use by Main class.</a>
 * <h2>Long description</h2>
 * <p>This class is using Knut - Morris - Prat algorithm for finding pattern into a text or a string.</p>
 * <h3>Author(s)</h3>
 * <p>Stephane OVERLEN et Frederick NEY</p>
 * <p><b>version</b> : 1.201</p>
 */
public class KnutMorrisPratt {

	private String pattern;
	private int[] prefixes;
	private int occurrences;
	private List<Integer> IndexOfOccurrences;

	/**
	 * Constructor of the class:
	 *      This constructor require the string of the pattern to find.
	 *
	 * @param pattern   (String)    : The pattern used for finding pattern on a text
	 */
	public KnutMorrisPratt(String pattern) {
		this.pattern = pattern;
		int patternLength = pattern.length();
		this.prefixes = new int[patternLength];                  // array containing prefixes
		int i = 1, j = 0;
		prefixes[0] = 0;
		for (i = 1; i < this.pattern.length(); i++) {
			// Test si les caractères sont les meme, décaler le motif.
			if (pattern.charAt(i) == pattern.charAt(j)) {
				j++;
			}
			else {
				j = 0;
				// test pour le premier caractère.
				if (pattern.charAt(i) == pattern.charAt(j)) {
					j++;
				}
			}
			this.prefixes[i] = j;
		}
	}

	/**
	 * Function to find pattern on the given string using Knut - Morris - Pratt method.
	 *
	 * @param text      (String)    : The string used to find pattern
	 */
	public void findSymbolOn(String text) {
		int textLength = text.length(), patternLength = this.pattern.length();
		int q = 0;
		this.IndexOfOccurrences = new ArrayList<Integer>();

		for (int i = 0; i < textLength; i++) {
			while (q > 0 && this.pattern.charAt(q) != text.charAt(i)) {
				q = this.prefixes[q - 1];
			}
			if (this.pattern.charAt(q) == text.charAt(i))
				q++;
			if (q == patternLength) {
				this.occurrences++;
				this.IndexOfOccurrences.add((i - patternLength + 1));
				q = this.prefixes[q - 1];
			}
		}
	}

	/**
	 * Function to print the results of the find.
	 */
	public void printResult() {
		System.out.println(this.occurrences);
		for (int i = 0; i < this.IndexOfOccurrences.size(); i++) {
			System.out.print(i == this.IndexOfOccurrences.size() - 1 ? this.IndexOfOccurrences.get(i) : this.IndexOfOccurrences.get(i) + " ");
		}
		System.out.print("\n");
	}

	/**
	 * Function to get the array of the pattern and the array of the prefixes.
	 *
	 * @return (String)
	 */
	public String toString() {
		String str = "";
        for (int i = 0; i < this.pattern.length(); i++)
            str += (i == this.pattern.length() - 1) ? Integer.toString(i) : Integer.toString(i) + " ";
        str += "\n";
        for (int i = 0; i < this.pattern.length(); i++) {
            str += (i == this.pattern.length() - 1) ? this.pattern.charAt(i) :this.pattern.charAt(i) + " ";
        }
        str += "\n";
        for (int i = 0; i < this.pattern.length(); i++) {
			str += (i == this.pattern.length() - 1) ? Integer.toString(this.prefixes[i]) : Integer.toString(this.prefixes[i]) + " ";
        }
        str += "\n";
		return str;
	}
}
