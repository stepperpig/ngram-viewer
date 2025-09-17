package ngrams;

import java.sql.Time;
import java.util.List;
import java.util.ArrayList;
import java.util.Objects;
import java.util.TreeMap;
import java.lang.Math.*;

/**
 * An object for mapping a year number (e.g. 1996) to numerical data. Provides
 * utility methods useful for data analysis.
 *
 * @author Josh Hug
 */
public class TimeSeries extends TreeMap<Integer, Double> {

    /** If it helps speed up your code, you can assume year arguments to your NGramMap
     * are between 1400 and 2100. We've stored these values as the constants
     * MIN_YEAR and MAX_YEAR here. */
    public static final int MIN_YEAR = 1400;
    public static final int MAX_YEAR = 2100;

    /**
     * Constructs a new empty TimeSeries.
     */
    public TimeSeries() {
        super();
    }

    /**
     * Creates a copy of TS, but only between STARTYEAR and ENDYEAR,
     * inclusive of both end points.
     */
    public TimeSeries(TimeSeries ts, int startYear, int endYear) {
        super();
        // Do some error checking
        if (startYear > endYear) {
            throw new IllegalArgumentException("Start year cannot be after end year.");
        }
        
        // Copy years between bounds
        for (Integer year : ts.keySet()) {
            if (startYear <= year && year <= endYear) {
                this.put(year, ts.get(year));
            }
        }
    }

    /**
     * Returns all years for this TimeSeries (in any order).
     */
    public List<Integer> years() {
        List<Integer> years = new ArrayList<>();
        for (Integer year : this.keySet()) {
            years.add(year);
        }
        return years;
    }

    /**
     * Returns all data for this TimeSeries (in any order).
     * Must be in the same order as years().
     */
    public List<Double> data() {
        // TODO: Fill in this method.
        List<Double> data = new ArrayList<>();
        for (Double datum : this.values()) {
            data.add(datum);
        }
        return data;
    }

    /**
     * Returns the year-wise sum of this TimeSeries with the given TS. In other words, for
     * each year, sum the data from this TimeSeries with the data from TS. Should return a
     * new TimeSeries (does not modify this TimeSeries).
     *
     * If both TimeSeries don't contain any years, return an empty TimeSeries.
     * If one TimeSeries contains a year that the other one doesn't, the returned TimeSeries
     * should store the value from the TimeSeries that contains that year.
     */
    public TimeSeries plus(TimeSeries ts) {
        TimeSeries sum = new TimeSeries();

        if (this.years().isEmpty() && ts.years().isEmpty()) return sum;

        int startYear = Math.min(this.firstKey(), ts.firstKey());
        int endYear = Math.max(this.lastKey(), ts.lastKey());

        // loop through years
        for (int i = startYear; i <= endYear; i++) {

            // if we don't have a year
            if (this.get(i) == null) {
                // ...but the other does
                if (ts.get(i) != null) {
                    sum.put(i, ts.get(i));
                } else {
                    continue;
                }
            }
            // but if we do have a year
            else {
                // and the other doesn't
                if (ts.get(i) == null) {
                    sum.put(i, this.get(i));
                } else {
                    sum.put(i, this.get(i) + ts.get(i));
                }
            }
        }

        return sum;
    }

    /**
     * Returns the quotient of the value for each year this TimeSeries divided by the
     * value for the same year in TS. Should return a new TimeSeries (does not modify this
     * TimeSeries).
     *
     * If TS is missing a year that exists in this TimeSeries, throw an
     * IllegalArgumentException.
     * If TS has a year that is not in this TimeSeries, ignore it.
     */
    public TimeSeries dividedBy(TimeSeries ts) {
        TimeSeries quotient = new TimeSeries();

        if (this.years().isEmpty() && ts.years().isEmpty()) return quotient;

        int startYear = Math.min(this.MIN_YEAR, ts.MIN_YEAR);
        int endYear = Math.max(this.MAX_YEAR, ts.MAX_YEAR);

        for (int i = startYear; i <= endYear; i++) {
            if (this.get(i) == null) {
                if (ts.get(i) != null) {
                    continue;
                }
            } else {
                if (ts.get(i) == null) {
                    throw new IllegalArgumentException("Can't divide by a year that doesn't exist.");
                } else {
                    quotient.put(i, this.get(i) / ts.get(i));
                }
            }
        }

        return quotient;
    }
}
