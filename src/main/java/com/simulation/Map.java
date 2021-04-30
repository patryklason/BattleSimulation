package com.simulation;

import java.util.ArrayList;
import java.util.List;


/**
 * @version 1.0.1
 * @author Patryk Lason, Hubert Belkot
 */
class Map{
    final int size;
    final private List<List<Field>> mapList = new ArrayList<>();

    public Map(int size){
        this.size = size;
        for(int i = 0; i < size; ++i){
            mapList.add(new ArrayList<>());
            for(int j = 0; j < size; ++j) {
                mapList.get(i).add(new Field(i,j));
            }
        }
    }

    public List<List<Field>> getMapList() {
        return mapList;
    }
}
