package ngrams;

import java.util.Collection;
import edu.princeton.cs.algs4.In;
import java.util.HashMap;
import java.util.Map;

import static ngrams.TimeSeries.MAX_YEAR;
import static ngrams.TimeSeries.MIN_YEAR;

/**
 * An object that provides utility methods for making queries on the
 * Google NGrams dataset (or a subset thereof).
 *
 * An NGramMap stores pertinent data from a "words file" and a "counts
 * file". It is not a map in the strict sense, but it does provide additional
 * functionality.
 *
 * @author Josh Hug
 */
public class NGramMap {

    private Map<String, TimeSeries> MAP = new HashMap<>();
    
    
    /**
     * Constructs an NGramMap from WORDSFILENAME and COUNTSFILENAME.
     */
    public NGramMap(String wordsFilename, String countsFilename) {
        // The crux of our work will be to parse through data files and store in a
        // data structure. As we know, from our first incursion into this haunting and
        // unfamiliar codebase, the TimeSeries are essentially wrappers around a 
        // TreeMap structure, through which we can "bundle" a given word's 
        // "history" (or more simply the list of its years) and any corresponding
        // data for each year, e.g., occurrences of the word.
        
        // Here we enter deeper forests...

        // NGramMap is an interface that we'll define to help us create TimeSeries.
        // Our interface needs to bundle the appearances of a given word within a time period.
        // To create a time series, we'll need to reach this black box, find the relevant keyword,
        // and store the bounds to be used in our TimeSeries. 
        // What could this "black box" possibly be? Maybe a dictionary...
        
        // This is what our NGramMap will be... a hashmap that indexes the entire history of 
        // language evolution based on any given word!

        // So how do we do this? The first thought is to parse our words file, extract all unigrams
        // and insert them into a hashmap. Our values will consist of tuples storing both the year
        // and appearances.

        // On second thought, this latter tuple looks a lot like a TimeSeries!
        // We could modify our conceptual hashmap to hold words as keys and TimeSeries
        // as values. 
        In in = new In(wordsFilename);

        // Retrieve words from words_file
        while(!in.isEmpty()) {
            String nextLine = in.readLine();
            String[] splitLine = nextLine.split("\t");
            String w = splitLine[0];
            int year = Integer.parseInt(splitLine[1]);
            double appearances = Double.parseDouble(splitLine[2]);

            // if the dictionary is empty
            if (MAP.get(w) == null) {
                TimeSeries ts = new TimeSeries();
                ts.put(year, appearances);
                MAP.put(w, ts);
            } else {
                MAP.get(w).put(year, appearances);
            }
        }
    }

    /**
     * Provides the history of WORD between STARTYEAR and ENDYEAR, inclusive of both ends. The
     * returned TimeSeries should be a copy, not a link to this NGramMap's TimeSeries. In other
     * words, changes made to the object returned by this function should not also affect the
     * NGramMap. This is also known as a "defensive copy". If the word is not in the data files,
     * returns an empty TimeSeries.
     */
    public TimeSeries countHistory(String word, int startYear, int endYear) {
        // retrieve ts
        TimeSeries ts = new TimeSeries();
        if (MAP.get(word) == null) return ts;
        ts = MAP.get(word);
        TimeSeries bounded_ts = new TimeSeries(ts, startYear, endYear);
        return bounded_ts;
    }

    /**
     * Provides the history of WORD. The returned TimeSeries should be a copy, not a link to this
     * NGramMap's TimeSeries. In other words, changes made to the object returned by this function
     * should not also affect the NGramMap. This is also known as a "defensive copy". If the word
     * is not in the data files, returns an empty TimeSeries.
     */
    public TimeSeries countHistory(String word) {
        // retrieve dictionary based on word
        TimeSeries ts = new TimeSeries();
        if (MAP.get(word) == null) return ts;
        ts = MAP.get(word);
        return ts;
    }

    /**
     * Returns a defensive copy of the total number of words recorded per year in all volumes.
     */
    public TimeSeries totalCountHistory() {
        return null;
    }

    /**
     * Provides a TimeSeries containing the relative frequency per year of WORD between STARTYEAR
     * and ENDYEAR, inclusive of both ends. If the word is not in the data files, returns an empty
     * TimeSeries.
     */
    public TimeSeries weightHistory(String word, int startYear, int endYear) {
        return null;
    }

    /**
     * Provides a TimeSeries containing the relative frequency per year of WORD compared to all
     * words recorded in that year. If the word is not in the data files, returns an empty
     * TimeSeries.
     */
    public TimeSeries weightHistory(String word) {
        return null;
    }

    /**
     * Provides the summed relative frequency per year of all words in WORDS between STARTYEAR and
     * ENDYEAR, inclusive of both ends. If a word does not exist in this time frame, ignore it
     * rather than throwing an exception.
     */
    public TimeSeries summedWeightHistory(Collection<String> words,
                                          int startYear, int endYear) {
        return null;
    }

    /**
     * Returns the summed relative frequency per year of all words in WORDS. If a word does not
     * exist in this time frame, ignore it rather than throwing an exception.
     */
    public TimeSeries summedWeightHistory(Collection<String> words) {
        return null;
    }
}
