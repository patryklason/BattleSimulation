package com.simulation;

import java.util.ArrayList;
import java.util.List;

/**
 * Creates 2D array of fields
 */
class Map{
    /**
     * size of the side of the square map
     */
    final int size;
    final private List<List<Field>> mapList = new ArrayList<>();

    /**
     * constructor for Map which is square of fields
     * @param size size of the side of the square map
     */
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
