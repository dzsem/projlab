package projlab.TestProcessor;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Tester {
	// Ne példányosítsuk.
	private Tester() {
	}

	/**
	 * Megnyit egy adott elérési úton található fájlt olvasásra.
	 * 
	 * @param path A fájl elérési útja
	 * @return A fájlhoz tartozó BufferedReader.
	 * @throws FileNotFoundException ha a fájl nem megnyitható.
	 */
	private static BufferedReader GetFileReader(String path) throws FileNotFoundException {
		FileReader reader = new FileReader(path);
		BufferedReader bufferedReader = new BufferedReader(reader);

		return bufferedReader;
	}

	/**
	 * Visszaadja a reader-ből kiolvasott összes sort, ami nem komment,
	 * azaz nem '#' karakterrel kezdődik.
	 * 
	 * A sorokból az összes kezdő és végző whitespace ('\t', ' ') eltávolításra
	 * kerül.
	 * 
	 * @param reader A megnyitott bemeneti fájl BufferedReader-e.
	 * @return Egy (readonly) lista az összes nem komment és nem üres sorból.
	 */
	private static List<String> GetAllUsefulLines(BufferedReader reader) {
		return reader
				.lines()
				.map(line -> line.trim())
				.filter(trimmedLine -> !(trimmedLine.isEmpty() || trimmedLine.startsWith("#")))
				.toList();
	}

	public static void Assert(String assertFile, String outputFile) {
		BufferedReader assertReader = null;
		BufferedReader outputReader = null;

		try {
			assertReader = GetFileReader(assertFile);
			outputReader = GetFileReader(outputFile);

			// Az összes (nem komment és nem üres) sor kigyűjtése a fájlokból
			List<String> outputLines = GetAllUsefulLines(outputReader);
			List<String> assertLines = GetAllUsefulLines(assertReader);

			// A fájlok bezárása.
			outputReader.close();
			outputReader = null;

			assertReader.close();
			assertReader = null;

			// A hibák listája. Ide kerül az összes sor az assert fájlból, aminek nem
			// volt párja az output fájlban.
			List<String> errors = new ArrayList<>();

			for (String assertLine : assertLines) {
				boolean found = false;
				for (String outputLine : outputLines) {
					if (outputLine.equals(assertLine)) {
						found = true;
						break;
					}
				}

				if (!found) {
					errors.add(assertLine);
				}
			}

			// Amennyiben az assert fájl minden sora egyezik az output fájl
			// valamely sorával, a teszt sikeres. Különben az olyan sorok, amiknek
			// nincs párja, a FAIL üzenet után sorban kiírásra kerülnek.
			if (errors.isEmpty()) {
				System.out.println("SUCCESS");
			} else {
				System.out.println("FAIL");
				for (String error : errors) {
					System.out.println(error);
				}
			}

		} catch (FileNotFoundException exc) {
			System.err.println("Error while opening files:");
			System.err.println(exc);
		} catch (IOException exc) {
			System.err.println("Error while reading files:");
			System.err.println(exc);
		} finally {
			try {
				if (assertReader != null)
					assertReader.close();

				if (outputReader != null)
					outputReader.close();
			} catch (IOException exc) {
				System.err.println("Error closing files:");
				System.err.println(exc);
			}
		}
	}
}
