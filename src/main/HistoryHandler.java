package main;

import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;
import ngrams.NGramMap;
import ngrams.TimeSeries;
import plotting.Plotter;
import org.knowm.xchart.XYChart;
import java.util.ArrayList;
import java.util.List;

public class HistoryHandler extends NgordnetQueryHandler {

    NGramMap map;

    public HistoryHandler(NGramMap map) {
        this.map = map;
    }

    @Override
    public String handle(NgordnetQuery q) {
        // Get words, startyear, and endyear
       List<String> words = q.words();
       int startYear = q.startYear();
       int endYear = q.endYear();

        // Create graph elements, i.e., labels and latitudes
        ArrayList<TimeSeries> lts = new ArrayList<>();
        ArrayList<String> labels = new ArrayList<>();

        // Create a TimeSeries for each unigram, populating its history
        // between our specified year boundaries.
        for (String word : words) {
            TimeSeries ts = map.countHistory(word, startYear, endYear);
            labels.add(word);
            lts.add(ts);
        }
        
        XYChart chart = Plotter.generateTimeSeriesChart(labels, lts);
        String encodedImage = Plotter.encodeChartAsString(chart);

        return encodedImage;
    }
        
}
