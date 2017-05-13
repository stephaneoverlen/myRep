/**
 *@Project  :   pattern_research
 *@Package  :   ON.File
 *@Revision :   1.0.1
 *@Created  :   27/04/2017 20:03
 *@Author   :   Frederick NEY and Stephane OVERLEN
 */

package ON.File;

import java.io.IOException;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

/**
 * <h1>Short description.</h1>
 * <a>Use by Main class.</a>
 * <h2>Long description</h2>
 * <p>Description</p>
 * <h3>Author(s)</h3>
 * <p>Stephane OVERLEN et Frederick NEY</p>
 * <p><b>version</b> : 1.101</p>
 */
public class Reader {

	private BufferedReader buffer;
	private String filename;

	/**
	 * Constructor of the Reader class.
	 * This constructor require the string of the filename to use for retrieving data.
	 *
	 * @param filename (String) : filename to read.
	 */
	public Reader(String filename) {
		this.filename = filename;
		if ((new File(filename)).exists() && !(new File(filename)).isDirectory())
			try {
				BufferedReader buffer = new BufferedReader(new FileReader(filename));
				this.buffer = buffer;
			}catch (IOException e) {
				System.err.println("Reader: Unable to open file '" + filename + "'.");
				e.printStackTrace();
				System.exit(-1);
			}
		else {
			System.err.println("Not a file");
			System.exit(-1);
		}
	}

	/**
	 * Function to get the content of a given filename while instancing this object.
	 *
	 * @return (String) - content of the file
	 */
	public String getContent() {
		String line;
		String text = "";
		try {
			while((line = buffer.readLine()) != null) {
				text += line;
			}
		} catch (IOException e) {
			System.err.println("Reader: Unable to read file '" + filename + "'.");
			e.printStackTrace();
			System.exit(-1);
		}
		return text;
	}
}
