import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * This class provides all code necessary to take a query box and produce
 * a query result. The getMapRaster method must return a Map containing all
 * seven of the required fields, otherwise the front end code will probably
 * not draw the output correctly.
 */
public class Rasterer {

    public Rasterer() {
        // YOUR CODE HERE
    }

    /**
     * Takes a user query and finds the grid of images that best matches the query. These
     * images will be combined into one big image (rastered) by the front end. <br>
     *
     *     The grid of images must obey the following properties, where image in the
     *     grid is referred to as a "tile".
     *     <ul>
     *         <li>The tiles collected must cover the most longitudinal distance per pixel
     *         (LonDPP) possible, while still covering less than or equal to the amount of
     *         longitudinal distance per pixel in the query box for the user viewport size. </li>
     *         <li>Contains all tiles that intersect the query bounding box that fulfill the
     *         above condition.</li>
     *         <li>The tiles must be arranged in-order to reconstruct the full image.</li>
     *     </ul>
     *
     * @param params Map of the HTTP GET request's query parameters - the query box and
     *               the user viewport width and height.
     *
     * @return A map of results for the front end as specified: <br>
     * "render_grid"   : String[][], the files to display. <br>
     * "raster_ul_lon" : Number, the bounding upper left longitude of the rastered image. <br>
     * "raster_ul_lat" : Number, the bounding upper left latitude of the rastered image. <br>
     * "raster_lr_lon" : Number, the bounding lower right longitude of the rastered image. <br>
     * "raster_lr_lat" : Number, the bounding lower right latitude of the rastered image. <br>
     * "depth"         : Number, the depth of the nodes of the rastered image <br>
     * "query_success" : Boolean, whether the query was able to successfully complete; don't
     *                    forget to set this to true on success! <br>
     */
    public Map<String, Object> getMapRaster(Map<String, Double> params) {
//        System.out.println(params);
        boolean query_success = true;
        Map<String, Object> results = new HashMap<>();

//        System.out.println("Since you haven't implemented getMapRaster, nothing is displayed in "
//                           + "your browser.");
        // Step #1 get the depth of the nodes of the rastered image
        double boxullat = params.get("ullat");
        double boxullon = params.get("ullon");
        double boxlrlat = params.get("lrlat");
        double boxlrlon = params.get("lrlon");

        // Calculate the query LonDPP
        double LonDPP = (boxlrlon - boxullon)/params.get("w");  //distance per pixel (in longitude/pixel)
        //handle the edge case
        //If the server receives a query box that doesn’t make any sense,
        // eg. ullon, ullat is located to the right of lrlon, lrlat
        if (boxullat < boxlrlat || boxullon > boxlrlon){
            query_success = false;
        }
//        System.out.println(LonDPP);
        double[] imageLonDPP = new double[8];

        //You can also imagine that the user might drag the query box to a location
        // that is completely outside of the root longitude/latitudes
        if (boxullat > MapServer.ROOT_ULLAT || boxlrlat < MapServer.ROOT_LRLAT
                || boxlrlon < MapServer.ROOT_ULLON || boxlrlon > MapServer.ROOT_LRLON){
            query_success = false;
        }

        imageLonDPP[0] = (MapServer.ROOT_LRLON - MapServer.ROOT_ULLON) / MapServer.TILE_SIZE;
        for (int i = 1; i < 8; i++){
            imageLonDPP[i] = imageLonDPP[i-1] /2;
        }
//        System.out.println(Arrays.toString(imageLonDPP));
        // find the depth of the nodes of the rastered image
        int depth = 0;
        while((depth < imageLonDPP.length-1) && imageLonDPP[depth] > LonDPP){
            depth++;
        }
//        System.out.println(depth);
        //lat per tile
        double lat_per_tile = (MapServer.ROOT_ULLAT - MapServer.ROOT_LRLAT) / Math.pow(2, depth);
        //lon per tile
        double lon_per_tile = (MapServer.ROOT_LRLON - MapServer.ROOT_ULLON) / Math.pow(2, depth);

        int lat_start = (int)((MapServer.ROOT_ULLAT - boxullat) / lat_per_tile);
        double raster_ul_lat = MapServer.ROOT_ULLAT - lat_start * lat_per_tile;
        int lat_end = (int)((MapServer.ROOT_ULLAT - boxlrlat) / lat_per_tile)> Math.pow(2, depth) - 1
                ? (int) Math.pow(2, depth) - 1: (int)((MapServer.ROOT_ULLAT - boxlrlat) / lat_per_tile);
        double raster_lr_lat = MapServer.ROOT_ULLAT - (lat_end + 1) * lat_per_tile;
        int lon_start = (int)((boxullon - MapServer.ROOT_ULLON) / lon_per_tile);
        double raster_ul_lon = MapServer.ROOT_ULLON + lon_start * lon_per_tile;
        int lon_end = (int)((boxlrlon - MapServer.ROOT_ULLON)/lon_per_tile) > Math.pow(2, depth) - 1
                ? (int) Math.pow(2, depth) - 1: (int)((boxlrlon - MapServer.ROOT_ULLON)/lon_per_tile);
        double raster_lr_lon = MapServer.ROOT_ULLON + (lon_end + 1) * lon_per_tile;
        // x is the column and y is the row
        // pic name example: d1_x1_y0.png
        String[][] pics = new String[lat_end - lat_start + 1][lon_end - lon_start + 1];
        int lat = lat_start;
        for (int i = 0; i < pics.length; i++){
            int lon = lon_start;
            for (int j = 0; j < pics[0].length; j++){
                pics[i][j] = "d" + depth + "_x" + lon + "_y" + lat + ".png";
                lon++;
            }
            lat++;
        }
        results.put("render_grid", pics);
        results.put("raster_ul_lon", raster_ul_lon);
        results.put("raster_ul_lat", raster_ul_lat);
        results.put("raster_lr_lon", raster_lr_lon);
        results.put("raster_lr_lat", raster_lr_lat);
        results.put("depth", depth);
        results.put("query_success" ,query_success);
//        System.out.println(results);

        return results;
    }

}
