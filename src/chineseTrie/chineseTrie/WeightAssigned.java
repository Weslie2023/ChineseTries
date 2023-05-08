package chineseTrie;

import java.util.HashMap;
import java.util.Map;
public class WeightAssigned {
    static Map<String, ColumnPair> extractCol= extractColumns.getExtractCol();

    public static Map<String,Double> getWeightMap(){
        Map<String,Double> weightMap =new HashMap<>();
        for(Map.Entry<String,ColumnPair> entry:extractCol.entrySet()){
            String shuangPin= ShuangPinTrie.getShuangpin(entry.getKey());
            ColumnPair pair=entry.getValue();
            Double weight= 1.0/Integer.parseInt(pair.getCol1());

            double count = weightMap.getOrDefault(shuangPin, 0.0) + 1*weight;
            weightMap.put(shuangPin,count);
        }
        return  weightMap;
    }

    public static Map<Character, HashMap<Character, Double>> getWeightPair(){
        Map<String,Double> weightMap=getWeightMap();
        Map<Character, HashMap<Character, Double>> weightPair = new HashMap<>();

        for(Map.Entry<String,Double> entry:weightMap.entrySet()){
            String shuangPin=entry.getKey();
            Double weight=entry.getValue();
            Character vowel=shuangPin.charAt(0);
            Character consonant=shuangPin.charAt(1);

            HashMap<Character,Double> innerMap = weightPair.getOrDefault(vowel, new HashMap<>());
            innerMap.put(consonant,weight);

            weightPair.put(vowel,innerMap);
        }
        return weightPair;
    }

    public static void main(String[] args) {
        System.out.println(getWeightMap());
        System.out.println(getWeightPair());
    }
}
