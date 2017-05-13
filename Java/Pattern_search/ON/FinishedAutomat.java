/**
 *@Project  :   pattern_research
 *@Package  :   ON
 *@Revision :   2.0.1
 *@Created  :   24/04/2017 18:48
 *@Author   :   Frederick NEY and Stephane OVERLEN
 */

package ON;

import java.util.ArrayList;

/**
 * <h1>finished automate pattern finder.</h1>
 * <a>Use by Main class.</a>
 * <h2>Long description</h2>
 * <p>This class is using finished automate algorithm for finding pattern into a text or a string.</p>
 * <h3>Author(s)</h3>
 * <p>Stephane OVERLEN et Frederick NEY</p>
 * <p><b>version</b> : 1.201</p>
 */
public class FinishedAutomat {

	private ArrayList<Character> array;
    private String pattern;
	private int [][] stateMachine;
	private int occurrences;
	private ArrayList<Integer> IndexOfOccurrences;

	/**
	 * Constructor of the class:
	 *      This constructor require the string of the pattern to find.
	 *
	 * @param pattern   (String)    : The pattern used for finding pattern on a text
	 */
	public FinishedAutomat(String pattern) {
		this.pattern = pattern;

		int patternLength = pattern.length();
		char [] tmp = new char[pattern.length()];
		this.array = new ArrayList<Character>();
		for (Character c : pattern.toCharArray()) {
			if (!this.array.contains(c)) {
				this.array.add(c);
			}
		}
		this.stateMachine = new int[patternLength + 1][this.array.size()];
		for (int i = 0; i < patternLength + 1; i++) {
			for (int j = 0; j < this.array.size(); j++) {
				if (i != patternLength && this.array.get(j) == (pattern.charAt(i))) {
					this.stateMachine[i][j] = i + 1;
				}
				else {
					String subPattern = this.pattern.substring(0, i);
					subPattern += this.array.get(j);
					int iPattern = i;

					if (i == patternLength) {
						subPattern = subPattern.substring(1);
						iPattern = i - 1;
					}
					for (int k = 0; k <= patternLength - 1; k++) {
						if (subPattern.substring(k, subPattern.length()).equals(this.pattern.substring(0, iPattern - k + 1))) {
							this.stateMachine[i][j] = subPattern.length() - k;
							break;
						}
					}
				}
			}
		}
	}

	/**
	 * Function to find pattern on the given string using finished automate method.
	 *
	 * @param text      (String)    : The string used to find pattern
	 */
	public void findSymbolOn(String text) {
        this.IndexOfOccurrences = new ArrayList<Integer>();
        this.occurrences = 0;
		int q = 0;
		for (int i = 0; i < text.length() - 1; i++) {
			if (this.array.indexOf(text.charAt(i)) != -1) {
				q = this.stateMachine[q][this.array.indexOf(text.charAt(i))];
				if (q == pattern.length()) {
					this.occurrences++;
					this.IndexOfOccurrences.add(i - (pattern.length() - 1));
				}
			}
		}
	}

	/**
	 * Function to get the array of the pattern and the array of the prefixes.
	 *
	 * @return (String)
	 */
	public String toString() {
		String str = "";
		for (int[] prefixLine : this.stateMachine) {
			for (int j = 0; j < prefixLine.length; j++) {
				str += (j == prefixLine.length - 1) ? Integer.toString(prefixLine[j]) : Integer.toString(prefixLine[j]) + " ";
			}
			str += "\n";
		}
		return str;
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
}
