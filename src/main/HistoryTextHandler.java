package main;

import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;
import ngrams.NGramMap;
import ngrams.TimeSeries;

import java.util.List;
import java.util.Map;

import java.util.ArrayList;
import java.util.HashMap;

public class HistoryTextHandler extends NgordnetQueryHandler {
    
    NGramMap ngm;

    public HistoryTextHandler(NGramMap ngm) {
        this.ngm = ngm;
    }

    @Override
    public String handle(NgordnetQuery q) {
        List<String> words = q.words();
        int startYear = q.startYear();
        int endYear = q.endYear();
        
        String res = "";

        HashMap<String, TimeSeries> histories = new HashMap<>();
        
        for (String word : words) {
            histories.put(word, ngm.weightHistory(word, startYear, endYear));
        }

        for (Map.Entry<String, TimeSeries> p : histories.entrySet()) {
            res += p.getKey() + ": ";
            res += p.getValue().toString();
            res += "\n";
        }

        return res;
    }
}
