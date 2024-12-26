package com.cgvsu.model;

import com.cgvsu.model.utils.NormalCalculator;
import com.cgvsu.model.utils.Triangulator;

import java.util.ArrayList;

public class PreparationRender {
    public static void prepareForRendering(Model model) {
        triangulatePolygons(model);
        recalculateNormals(model);
    }

    private static void triangulatePolygons(Model model) {
        ArrayList<Polygon> newPolygons = new ArrayList<>();
        for (Polygon polygon : model.polygons)
            newPolygons.addAll(Triangulator.triangulate(polygon, model.vertices));
        model.polygons = newPolygons;
    }

    private static void recalculateNormals(Model model) {
        model.normals.clear();
        for (Polygon polygon : model.polygons)
            model.normals.add(NormalCalculator.calculateNormal(polygon, model.vertices));
    }
}
