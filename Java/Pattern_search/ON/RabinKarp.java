/**
 *@Project  :   pattern_research
 *@Package  :   ON
 *@Revision :   2.0.1
 *@Created  :   24/04/2017 19:15
 *@Author   :   Stephane OVERLEN
 */

package ON;

import java.util.ArrayList;
import java.util.List;

/**
 * <h1>Rabin - Karp pattern finder</h1>
 * <a>Use by Main class.</a>
 * <h2>Long description</h2>
 * <p>This class is using Rabin - Karp algorithm for finding pattern into a text or a string.</p>
 * <h3>Author(s)</h3>
 * <p>Stephane OVERLEN</p>
 * <p><b>version</b> : 1.201</p>
 */
public class RabinKarp {

	private String pattern;
	private String text;
	private int p;
	private int q;
	private int occurences;
	private List<Integer> IndexOfOccurrences;
	private int hash;
	/**
	 * Constructor of the class:
	 *      This constructor require the string of the pattern to find.
	 *
	 * @param pattern    (String)    : The pattern used for finding pattern on a text
	 * @param text      (String)    : The string used to find pattern
	 */
	public RabinKarp(String pattern, String text) {
		this.text = text;
		this.pattern = pattern;
		int patternLength = pattern.length();                                                                           // length of pattern
		this.p = 3355439;
		this.q = 256;

		for (int i = 0; i < patternLength; i++) {
			this.p = pattern.charAt(i) - 48 + 10 * this.p;                                                               // get p = M[m] + 10(M[m − 1] + 10(M[m − 2] + ... + 10M[1])...)
		}

		this.p %= this.q;                                                                                               // p % q
		this.hash = hash(this.pattern);
	}


	/**
	 * Function to hash string.
	 *
	 * @param text (String) : string to hash
	 * @return (int)
	 */
	private int hash (String text) {
		int result = 0;

		for (int i = 0; i < text.length(); i++) {
			result = (result * q + text.charAt(i)) % p;
		}
		return result;
	}

	/**
	 * Function to find pattern on the given string using Rabin - Karp method.
	 */
	public void findSymbol() {
		this.IndexOfOccurrences = new ArrayList<Integer>();
		this.occurences = 0;
		int textLength = this.text.length(), patternLength = this.pattern.length();

		int hashText = hash(text.substring(0, patternLength));

		for (int i = 0; i <= (textLength-patternLength); i++) {

			if (hashText == this.hash) {
				if (text.substring(i, i + patternLength).equals(this.pattern)) {
					this.IndexOfOccurrences.add(i);
					this.occurences++;
				}
			}

			if (i != (textLength - patternLength))
				hashText = hash(text.substring(i + 1, i + patternLength + 1));
		}
	}

	/**
	 * Function to print the results of the find.
	 */
	public void printResult() {
		System.out.println(this.occurences);
		for (int i = 0; i < this.IndexOfOccurrences.size(); i++) {
			System.out.print(i == this.IndexOfOccurrences.size() - 1 ? this.IndexOfOccurrences.get(i) : this.IndexOfOccurrences.get(i) + " ");
		}
		System.out.print("\n");
	}

	/**
	 * Function to get the string of the base, the prime number and the hash of the pattern.
	 *
	 * @return (String)
	 */
	public String toString() {
		String str = "";
		System.out.println(this.q + " " + this.p + " " + this.hash(this.pattern));
		return str;
	}
}
