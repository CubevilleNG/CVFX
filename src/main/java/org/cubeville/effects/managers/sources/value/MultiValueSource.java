package org.cubeville.effects.managers.sources.value;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import org.bukkit.configuration.serialization.SerializableAs;

@SerializableAs("MultiValueSource")
public class MultiValueSource implements ValueSource
{
    private List<ValueSource> valueSources;
    private List<Integer> durations;
    private List<Integer> offsets;
    private int repetitionOffset;

    public MultiValueSource() {
        valueSources = new ArrayList<>();
        durations = new ArrayList<>();
        offsets = new ArrayList<>();
        repetitionOffset = 0;
    }

    public void addValueSource(ValueSource valueSource, int duration, int offset) {
        valueSources.add(valueSource);
        if(duration < 0) {
            duration = -duration;
            repetitionOffset = duration;
        }
        durations.add(duration);
        offsets.add(offset);
    }

    public MultiValueSource(Map<String, Object> config) {
        valueSources = (List<ValueSource>) config.get("valueSources");
        durations = (List<Integer>) config.get("durations");
        offsets = (List<Integer>) config.get("offsets");
        repetitionOffset = config.get("repetitionOffset") != null ? (int) config.get("repetitionOffset") : 0;
    }

    public Map<String, Object> serialize() {
        Map<String, Object> ret = new HashMap<>();
        ret.put("valueSources", valueSources);
        ret.put("durations", durations);
        ret.put("offsets", offsets);
        ret.put("repetitionOffset", repetitionOffset);
        return ret;
    }

    public double getValue(int step) {
        if(valueSources.size() == 0) return 0.0;

        if(repetitionOffset != 0) {
            int totalLength = 0;
            for(Integer duration: durations)
                totalLength += duration;
            if(step >= totalLength) {
                int lastLength = durations.get(durations.size() - 1);
                while(step >= totalLength)
                    step -= lastLength * 2;
                if(step < 0) step = 0;
            }
        }

        int currentStartStep = 0;
        for(int i = 0; i < valueSources.size(); i++) {
            if(step >= currentStartStep && step < currentStartStep + durations.get(i)) {
                return valueSources.get(i).getValue(step - offsets.get(i));
            }
            currentStartStep += durations.get(i);
        }
        int len = valueSources.size();
        return valueSources.get(len - 1).getValue(currentStartStep - 1 - offsets.get(len - 1));
    }
    
    public String getInfo(boolean detailed) {
        if(detailed) {
            String ret = "Multi: ";
            for(int i = 0; i < valueSources.size(); i++) {
                if(i > 0) ret += ", ";
                ret += valueSources.get(i).getInfo(true) + " (" + durations.get(i) + "/" + offsets.get(i) + ")";
            }
            return ret;
        }
        else {
            return "Multi x" + valueSources.size();
        }
    }
}
