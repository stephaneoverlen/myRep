/**
 *@Project  :   pattern_research
 *@Package  :   ON
 *@Revision :   2.0.1
 *@Created  :   24/04/2017 19:02
 *@Author   :   Stephane OVERLEN
 */

package ON;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * <h1>Boyer - Moore pattern finder</h1>
 * <a>Use by Main class.</a>
 * <h2>Long description</h2>
 * <p>This class is using Boyer - Moore algorithm for finding pattern into a text or a string.</p>
 * <h3>Author(s)</h3>
 * <p>Stephane OVERLEN</p>
 * <p><b>version</b> : 1.201</p>
 */
public class BoyerMoore {

	private String pattern;
	private int[] prefixes;
	private HashMap<Character, Integer> array;
	private int occurrences;
	private List<Integer> IndexOfOccurrences;

	/**
	 * Constructor of the class:
	 *      This constructor require the string of the pattern to find.
	 *
	 * @param pattern   (String)    : The pattern used for finding pattern on a text
	 */
	public BoyerMoore(String pattern) {
		this.pattern = pattern;
		int patternLength = this.pattern.length();                                                                      // length of pattern
		this.array = new HashMap<Character, Integer>();
		this.prefixes = new int[patternLength];
		int i;
		String currentSuffix;
		char notChar;
		int shift = 0;

		// fill array:
		for(i = 0; i < patternLength; i++){                                                                             // for each letter 'i' of the pattern
			if(!this.array.containsKey(this.pattern.charAt(i))){                                                        // if the letter isn't already in Tab1
				for(int j = 0; j < patternLength - i; j++){                                                             // for each letter from last to i
					if(this.pattern.charAt(patternLength - j - 1) == this.pattern.charAt(i)) {                          // if the letters match
						this.array.put(this.pattern.charAt(i), j);                                                      // put it in Tab 1
						break;                                                                                          // go to next letter
					}
				}
			}
		}
		this.array.put('*', patternLength);                                                                             // put it in Tab 1

		// fill prefixes
		i = 1;
		while (i <= patternLength) {
			currentSuffix = this.pattern.substring(patternLength - i, patternLength);
			if (patternLength - i == 0)
				notChar = 0;
			else
				notChar = this.pattern.charAt(patternLength - i - 1);
			for (int j = 1; j < patternLength; j++) {
				if (patternLength - i - j - 1 < 0) {
					currentSuffix = currentSuffix.substring(1, currentSuffix.length());
					shift++;
					if (currentSuffix.length() > 0) {
						notChar = currentSuffix.charAt(0);
						if (currentSuffix.length() == 1) {
							notChar = 0;
						}
					}
				}
				String tmpSuffix = this.pattern.substring(patternLength - i - j + shift, patternLength - j);
				if ((tmpSuffix.equals(currentSuffix)) &&
						(patternLength - i != 0) &&
						(this.pattern.charAt(patternLength - i - j - 1 + shift) != notChar)
				) {
					this.prefixes[i - 1] = j;
					break;
				} else if ((patternLength == i) &&
						(tmpSuffix.equals(currentSuffix))) {
					this.prefixes[i - 1] = j;
					break;
				} else if (j == patternLength - 1) {
					this.prefixes[i - 1] = patternLength;
				}
			}
			shift = 0;
			i++;
		}
	}

	/**
	* Function to find pattern on the given string using Boyer - More method.
	*
	* @param text       (String)    : The string used to find pattern
	*/
	public void findSymbolOn(String text) {
		this.IndexOfOccurrences = new ArrayList<Integer>();
		this.occurrences = 0;
		int patternLength = this.pattern.length();
		int textLength = text.length();
		int j;
		int index = patternLength;
		while (index <= textLength) {
			j = patternLength-1;
			while (j > 0 && text.charAt(index-patternLength + j) == this.pattern.charAt(j)) {
				j--; // go back from one position
			}
			if (j == 0) {
				this.occurrences++;
				this.IndexOfOccurrences.add(index - patternLength);
			}
			if (j == patternLength - 1) {
				if (this.array.containsKey(text.charAt(index - 1)))
					index = index + this.array.get(text.charAt(index - 1));
				else
					index = index + patternLength;
			} else {
				index = index + this.prefixes[patternLength - 2 - j];
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
		for (int j = 0; j < prefixes.length; j++) {
			str += (j == prefixes.length - 1) ? Integer.toString(prefixes[j]) : Integer.toString(prefixes[j]) + " ";
		}
		str += "\n";
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
